package org.localstorm.mcc.web.cashflow.actions;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.entity.Operation;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by assetId parameter
 * @author localstorm
 */
@UrlBinding("/actions/cash/asset/ViewOperations")
public class OperationsViewActionBean extends CashflowBaseActionBean {


    @Validate(required=true)
    private Integer assetId;

    @Validate(required=false)
    private Boolean thisMonth;

    private Asset asset;
    
    private Collection<Operation> operations;

    public Boolean isThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(Boolean thisMonth) {
        this.thisMonth = thisMonth;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {

        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();

        Asset           ass = am.find(this.getAssetId());
        ValuableObject   vo = ass.getValuable();

        Date minDate = null;
        if (thisMonth!=null)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            minDate = cal.getTime();
        }

        Collection<Operation> ops = om.getOperations(vo, minDate);

        this.setOperations(ops);
        this.setAsset(ass);

        ReturnPageBean rpb = new ReturnPageBean(Pages.OPS_HISTORY.toString());
        {
            rpb.setParam(IncomingParameters.ASSET_ID, Integer.toString(this.getAssetId()));
            if (isThisMonth()!=null) {
                rpb.setParam(IncomingParameters.THIS_MONTH, "true");  
            }
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.OPS_LOG);
    }

    public static interface IncomingParameters {
        public static final String ASSET_ID = "assetId";
        public static final String THIS_MONTH = "thisMonth";
    }

}
