package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/ViewContext")
public class ContextViewActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int contextId;

    private Context contextResult;
    private Collection<GTDList> contextLists;
    private Collection<GTDList> archivedLists;
    private Collection<GTDList> pinnedLists;
    
    
    public int getContextId() {
        return contextId;
    }

    public void setContextId(int id) {
        this.contextId = id;
    }

    public Collection<GTDList> getPinnedLists() {
        return pinnedLists;
    }
    
    public Collection<GTDList> getContextLists() {
        return contextLists;
    }

    public void setContextLists(Collection<GTDList> contextLists) {
        this.contextLists = contextLists;
    }

    public Collection<GTDList> getArchivedLists() {
        return archivedLists;
    }

    public void setArchivedLists(Collection<GTDList> archivedLists) {
        this.archivedLists = archivedLists;
    }

    public Context getContextResult() {
        return contextResult;
    }

    public void setContextResult( Context contextResult ) {
        this.contextResult = contextResult;
    }
    
    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        contextResult   = getContextManager().findById(getContextId());
        
        super.setCurrent(contextResult);
        
        ListManager lm  = getListManager();
        
        pinnedLists     = new LinkedList<GTDList>();
        contextLists    = lm.findByContext(contextResult);
        archivedLists   = lm.findByContextArchived(contextResult);
        
        for (Iterator<GTDList> it = contextLists.iterator(); it.hasNext(); ) {
            GTDList list = it.next();
            if (list.isPinned()) {
                it.remove();
                pinnedLists.add(list);
            }
        }
    
        return new ForwardResolution(Views.VIEW_CTX);
    }
    
    public static interface IncomingParameters {
        public static final String CTX_ID = "contextId";
    }
}
