package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/BulkPasteList")
public class ListBulkPasteActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int contextId;
    
    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    
    @DefaultHandler
    @Logged
    public Resolution pasting() throws Exception {
        
        ListManager lm    = super.getListManager();
        ContextManager cm = super.getContextManager();
        GtdClipboard clip    = super.getClipboard();

        Context dst = cm.findById(this.getContextId());

        for (GTDList list : clip.getLists())
        {
            list.setContext(dst);
            lm.update(list);
        }

        clip.clearLists();
        
        RedirectResolution rr = new RedirectResolution(ContextViewActionBean.class);
        {
            rr.addParameter(ContextViewActionBean.IncomingParameters.CTX_ID, dst.getId());
        }
        
        return rr;
    }
}
