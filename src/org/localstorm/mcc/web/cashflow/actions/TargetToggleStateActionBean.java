package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.cashflow.TargetManager;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by target Id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/cash/target/ToggleStateTarget")
public class TargetToggleStateActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int targetId;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    
    @DefaultHandler
    @Logged
    public Resolution toggle() throws Exception {

        TargetManager tm = super.getTargetManager();
        Target target = tm.find(this.getTargetId());

        
        target.setArchived(!target.isArchived());
        
        tm.update(target);
        
        SessionUtil.clear(getSession(), CashflowSessionKeys.TARGETS);
        return new RedirectResolution(TargetsEditActionBean.class);
    }
}
