package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.util.RoundUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.MoneyMathContext;
import org.localstorm.mcc.ejb.cashflow.entity.OperationType;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by assetId parameter
 * @author localstorm
 */
@UrlBinding("/actions/cash/asset/OperateAsset")
public class OperateAssetActionBean extends AssetViewActionBean {

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            getContext().getRequest().setAttribute("operationName", this.getOperationName());
            super.filling();
        }
    }

    @Validate( required=true )
    private String operationName;

    @Validate( required=true, minvalue=-9999999999L, maxvalue=9999999999L )
    private BigDecimal amount;

    @Validate( required=true )
    private String comment;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @DefaultHandler
    @Logged
    @SuppressWarnings("fallthrough")
    public Resolution operate() throws Exception {

        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();
        Asset         asset = am.find(super.getAssetId());
        ValuableObject   vo = asset.getValuable();

        MathContext rounding = MoneyMathContext.ROUNDING;

        switch (OperationType.valueOf(this.getOperationName()))
        {
            case BUY:
                om.buy(vo, RoundUtil.round(this.getAmount(), rounding), this.getComment());
                break;
            case SELL:
                om.sell(vo, RoundUtil.round(this.getAmount(), rounding), this.getComment());
                break;
            default:
                throw new RuntimeException("Unexpected operation: "+this.getOperationName());
        }

        SessionUtil.clear(getSession(), CashflowSessionKeys.ASSETS);
        
        RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
        {
            rr.addParameter(AssetViewActionBean.IncomingParameters.ASSET_ID, asset.getId());
        }
        return rr;
    }
}
