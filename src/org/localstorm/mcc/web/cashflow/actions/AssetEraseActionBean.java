package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by assetId parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/cash/asset/EraseAsset")
public class AssetEraseActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int assetId;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int id) {
        this.assetId = id;
    }
    
    @DefaultHandler
    @Logged
    public Resolution deletingContext() throws Exception {
        
        OperationManager om = super.getOperationManager();
        AssetManager     am = super.getAssetManager();
        Asset         asset = am.find(this.getAssetId());

        AssetWrapper aw = (AssetWrapper) WrapUtil.wrapAsset(asset, om);
        if (aw.getAmount().compareTo(BigDecimal.ZERO)==1) {
            throw new RuntimeException("Attempt to delete non sold amount!");
        }

        am.remove(asset);

        SessionUtil.clear(getSession(), CashflowSessionKeys.ASSETS);
        return new RedirectResolution(AssetsEditActionBean.class);
    }
}
