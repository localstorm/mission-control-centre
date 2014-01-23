package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.web.gtd.GtdSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/EraseContext")
public class ContextEraseActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int contextId;

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int id) {
        this.contextId = id;
    }
    
    @DefaultHandler
    @Logged
    public Resolution deletingContext() throws Exception {
        
        ContextManager cm = getContextManager();
        Context ctx = cm.findById(getContextId());
        cm.remove(ctx);

        SessionUtil.clear(getSession(), GtdSessionKeys.CONTEXTS);
        SessionUtil.clear(getSession(), GtdSessionKeys.REFERENCE_OBJECTS);
        return new RedirectResolution(ContextsEditActionBean.class);
    }
}
