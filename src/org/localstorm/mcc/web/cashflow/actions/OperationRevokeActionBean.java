package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.entity.Operation;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by operationId parameter
 * @author localstorm
 */
@UrlBinding("/actions/cash/op/RevokeOperation")
public class OperationRevokeActionBean extends CashflowBaseActionBean {

    @Validate(required=true)
    private Integer operationId;

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }


    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {

        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();

        Operation op = om.findOperation(this.getOperationId());
        Asset asset  = am.find(op.getCost().getValuable());

        om.remove(op);

        ReturnPageBean rpb = super.getReturnPageBean();
        if (rpb!= null) {
            return NextDestinationUtil.getRedirection(rpb);
        } else {
            RedirectResolution rr = new RedirectResolution(OperationsViewActionBean.class);
            {
                rr.addParameter(OperationsViewActionBean.IncomingParameters.ASSET_ID, asset.getId());
            }
            return rr;
        }
    }


}
