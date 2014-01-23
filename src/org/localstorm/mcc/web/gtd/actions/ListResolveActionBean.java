package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/ResolveList")
public class ListResolveActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    @Validate( required=true )
    private String action;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @DefaultHandler
    @Logged
    public Resolution resolvingList() throws Exception {
        
        ListManager lm = getListManager();
        TaskManager tm = getTaskManager();
        
        GTDList list   = lm.findById(this.getListId());
        Context dstCtx = list.getContext();
        GtdClipboard clip = super.getClipboard();
        
        switch (ACTIONS.valueOf(this.getAction())) {
            case PIN:
                list.setPinned(!list.isPinned());
                lm.update(list);
                break;
            case PASTE:
                GTDList l = clip.pickList(this.getListId());
                if (l!=null)
                {
                    l.setContext(super.getCurrentContext());
                    lm.update(l);

                    dstCtx = super.getCurrentContext();
                }
                break;
            case COPY:
                clip.copyList(list);
                break;
            case ERASE:
                clip.pickList(this.getListId());
                lm.remove(list);
                break;
            case UNRESOLVE:
                list.setArchived(false);
                lm.update(list);
                break;
            case CANCEL:
                this.cancelList(list, tm);
                lm.update(list);
                break;
            case FINISH:
                this.finishList(list, tm);
                lm.update(list);
                break;
            default:
                throw new RuntimeException("Unexpected action:"+this.getAction());
        }
        
        RedirectResolution rr = new RedirectResolution(ContextViewActionBean.class);
        {
            rr.addParameter(ContextViewActionBean.IncomingParameters.CTX_ID, dstCtx.getId());
        }
        return rr;
    }
    
    private void cancelList(GTDList list, TaskManager tm) {
        Collection<Task> tasks = tm.findOpeartiveByList(list);
        tasks.addAll(tm.findAwaitedByList(list));
        
        for (Task t: tasks)
        {
            t.setDelegated(false);
            t.setCancelled(true);
            tm.update(t);
        }
        list.setArchived(true);
    }
    
    private void finishList(GTDList list, TaskManager tm) {
        Collection<Task> tasks = tm.findOpeartiveByList(list);
        tasks.addAll(tm.findAwaitedByList(list));
        
        for (Task t: tasks)
        {
            t.setDelegated(false);
            t.setFinished(true);
            tm.update(t);
        }
        list.setArchived(true);
    }
    
    private static enum ACTIONS 
    {
        PIN,
        PASTE,
        COPY,
        ERASE,
        CANCEL,
        FINISH,
        UNRESOLVE
    }
}
