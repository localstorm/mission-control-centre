package org.localstorm.mcc.web.cashflow.actions;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @secure-by target Id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/cash/target/ViewTarget")
public class TargetViewActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int targetId;

    private Target targetResult;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public Target getTargetResult() {
        return targetResult;
    }

    public void setTargetResult(Target targetResult) {
        this.targetResult = targetResult;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        Target target = super.getTargetManager().find(this.getTargetId());
        
        this.setTargetResult( WrapUtil.wrapTarget(target, super.getOperationManager()) );
        
        return new ForwardResolution(Views.VIEW_TARGET);
    }
    
    public static interface IncomingParameters {
        public static final String TARGET_ID = "targetId";
    }
}
