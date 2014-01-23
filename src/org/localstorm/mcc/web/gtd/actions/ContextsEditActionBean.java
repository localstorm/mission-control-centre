package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/EditContexts")
public class ContextsEditActionBean extends GtdBaseActionBean {

    private List<Context> result;

    public List<Context> getArchiveContexts() {
        return result;
    }

    public void setArchiveContexts(List<Context> result) {
        this.result = result;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() {
        super.clearCurrent();
        
        result = getContextManager().getArchived(super.getUser());
        return new ForwardResolution(Views.EDIT_CTXS);
    }
    
    
}