package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.*;
import org.localstorm.mcc.web.util.RoundUtil;
import java.math.BigDecimal;
import java.math.MathContext;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.MoneyMathContext;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by assetId parameter
 * @author localstorm
 */
@UrlBinding("/actions/cash/asset/UpdateAsset")
public class AssetUpdateActionBean extends AssetViewActionBean {

    @Validate( required=true, minvalue=-9999999999L, maxvalue=9999999999L )
    private BigDecimal buy;

    @Validate( required=true, minvalue=-9999999999L, maxvalue=9999999999L )
    private BigDecimal sell;

    @Validate( required=true )
    private String name;

    @Validate( required=false )
    private String assetClass;

    private boolean usedInBalance;

    private boolean debt;

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public boolean isUsedInBalance() {
        return usedInBalance;
    }

    public void setUsedInBalance(boolean usedInBalance) {
        this.usedInBalance = usedInBalance;
    }

    public boolean isDebt() {
        return debt;
    }

    public void setDebt(boolean debt) {
        this.debt = debt;
    }

    @HandlesEvent("fix")
    @Logged
    public Resolution fix() throws Exception {
        return updateOrFix(true);
    }
    
    @DefaultHandler
    @Logged
    public Resolution update() throws Exception {
        return updateOrFix(false);
    }

    public Resolution updateOrFix(boolean fix) throws  Exception {
        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();
        Asset         asset = am.find(this.getAssetId());
        ValuableObject vo = asset.getValuable();

        MathContext rounding = MoneyMathContext.ROUNDING;

        Cost cost = null;
        if (!fix) {
            cost = new Cost(vo);
            {
                cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
                cost.setSell(RoundUtil.round(this.getSell(), rounding));
            }
        } else {
            cost = om.getCurrentCost(vo);
            {
                cost.setBuy(RoundUtil.round(this.getBuy(), rounding));
                cost.setSell(RoundUtil.round(this.getSell(), rounding));
            }
        }

        vo.setUsedInBalance(this.isUsedInBalance());
        vo.setDebt(this.isDebt());
        asset.setName(this.getName());
        asset.setAssetClass(this.getAssetClass());

        am.update(asset);
        om.updateCost(vo, cost);
        om.update(vo);

        SessionUtil.clear(super.getSession(), CashflowSessionKeys.ASSETS);

        RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
        {
            rr.addParameter(AssetViewActionBean.IncomingParameters.ASSET_ID, this.getAssetId());
        }

        return rr;
    }


}
