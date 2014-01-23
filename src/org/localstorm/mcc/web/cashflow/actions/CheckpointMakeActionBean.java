package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.cashflow.entity.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.entity.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/MakeCheckpoint")
public class CheckpointMakeActionBean extends CashflowBaseActionBean
{
    @DefaultHandler
    @Logged
    public Resolution filling() {
        User             user = super.getUser();
        AssetManager     am   = super.getAssetManager();
        OperationManager om   = super.getOperationManager();
        
        HistoricalValuesManager hvm = super.getHistoricalValuesManager();

        Collection<Asset> assets = am.getAssets(user);
        assets = WrapUtil.wrapAssets(assets, om);

        BigDecimal netWealthWoDebt = BigDecimal.ZERO;
        BigDecimal netWealth       = BigDecimal.ZERO;
        BigDecimal balance         = BigDecimal.ZERO;
        BigDecimal debt            = BigDecimal.ZERO;

        for (Asset a: assets)
        {
            AssetWrapper aw = (AssetWrapper) a;

            ValuableObject vo = aw.getValuable();
            BigDecimal worth = aw.getNetWealth();

            if (!vo.isDebt()) {
                netWealth = netWealth.add(worth);
                netWealthWoDebt = netWealthWoDebt.add(worth);
            } else {
                debt = debt.subtract(worth);
                netWealthWoDebt = netWealthWoDebt.add(worth);
            }

            if (vo.isUsedInBalance()) {
                balance   = balance.add(aw.getBalance());
            }
        }

        HistoricalValue netWealthWoDebtHV = new HistoricalValue();
        {
            netWealthWoDebtHV.setFixDate(new Date());
            netWealthWoDebtHV.setObjectId(null);
            netWealthWoDebtHV.setOwner(user);
            netWealthWoDebtHV.setValueTag(ValueType.NET_WEALTH_WO_DEBT_CHECKPOINT);
            netWealthWoDebtHV.setVal(netWealthWoDebt);
        }

        HistoricalValue netWealthHV = new HistoricalValue();
        {
            netWealthHV.setFixDate(new Date());
            netWealthHV.setObjectId(null);
            netWealthHV.setOwner(user);
            netWealthHV.setValueTag(ValueType.NET_WEALTH_CHECKPOINT);
            netWealthHV.setVal(netWealth);
        }

        HistoricalValue balanceHV = new HistoricalValue();
        {
            balanceHV.setFixDate(new Date());
            balanceHV.setObjectId(null);
            balanceHV.setOwner(user);
            balanceHV.setValueTag(ValueType.BALANCE_CHECKPOINT);
            balanceHV.setVal(balance);
        }

        HistoricalValue debtHV = new HistoricalValue();
        {
            debtHV.setFixDate(new Date());
            debtHV.setObjectId(null);
            debtHV.setOwner(user);
            debtHV.setValueTag(ValueType.DEBT_CHECKPOINT);
            debtHV.setVal(debt);
        }

        hvm.log(netWealthWoDebtHV);
        hvm.log(netWealthHV);
        hvm.log(balanceHV);
        hvm.log(debtHV);

        return new RedirectResolution(AssetsViewActionBean.class);
    }

}
