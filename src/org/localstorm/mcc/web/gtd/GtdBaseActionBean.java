package org.localstorm.mcc.web.gtd;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.gtd.FileManager;
import org.localstorm.mcc.ejb.gtd.impl.FileManagerLocal;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.ejb.gtd.NoteManager;
import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.ejb.gtd.entity.Effort;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author localstorm
 */
public class GtdBaseActionBean extends BaseActionBean {

    public Effort[] getEfforts() {
        return Effort.values();
    }

    protected InboxManager getInboxManager() {
        return ContextLookup.lookup(InboxManager.class, InboxManager.BEAN_NAME);
    }

    protected HintManager getHintManager() {
        return ContextLookup.lookup(HintManager.class, HintManager.BEAN_NAME);
    }

    protected ContextManager getContextManager() {
        return ContextLookup.lookup(ContextManager.class, ContextManager.BEAN_NAME);
    }

    protected ListManager getListManager() {
        return ContextLookup.lookup(ListManager.class, ListManager.BEAN_NAME);
    }

    protected TaskManager getTaskManager() {
        return ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
    }

    protected FileManager getFileManager() {
        return ContextLookup.lookupLocal(FileManagerLocal.class, FileManager.BEAN_NAME);
    }

    protected FlightPlanManager getFlightPlanManager() {
        return ContextLookup.lookup(FlightPlanManager.class, FlightPlanManager.BEAN_NAME);
    }

    protected RefObjectManager getRefObjectManager() {
        return ContextLookup.lookup(RefObjectManager.class, RefObjectManager.BEAN_NAME);
    }

    protected NoteManager getNoteManager() {
        return ContextLookup.lookup(NoteManager.class, NoteManager.BEAN_NAME);
    }

    public void setCurrent(ReferencedObject objectResult)
    {
        HttpSession sess = this.getSession();
        SessionUtil.fill(sess, GtdSessionKeys.CURR_CTX, objectResult.getContext());
        SessionUtil.fill(sess, GtdSessionKeys.CURR_OBJ, objectResult);
    }

    protected void setCurrent(GTDList list)
    {
        HttpSession sess = this.getSession();
        SessionUtil.fill(sess,  GtdSessionKeys.CURR_CTX, list.getContext());
        SessionUtil.fill(sess,  GtdSessionKeys.CURR_LIST, list);
        SessionUtil.clear(sess, GtdSessionKeys.CURR_TASK);
    }

    protected void setCurrent(Task t)
    {
        HttpSession sess = this.getSession();
        SessionUtil.fill(sess, GtdSessionKeys.CURR_CTX,  t.getList().getContext());
        SessionUtil.fill(sess, GtdSessionKeys.CURR_LIST, t.getList());
        SessionUtil.fill(sess, GtdSessionKeys.CURR_TASK, t);
    }

    protected void setCurrent(Context t)
    {
        HttpSession sess = this.getSession();
        SessionUtil.clear(sess, GtdSessionKeys.CURR_TASK);
        SessionUtil.clear(sess, GtdSessionKeys.CURR_LIST);
        SessionUtil.fill(sess,  GtdSessionKeys.CURR_CTX,  t);
    }

    protected void clearCurrent()
    {
        HttpSession sess = this.getSession();
        SessionUtil.clear(sess, GtdSessionKeys.CURR_TASK);
        SessionUtil.clear(sess, GtdSessionKeys.CURR_LIST);
        SessionUtil.clear(sess, GtdSessionKeys.CURR_CTX);
    }

    public Context getCurrentContext()
    {
        return (Context) SessionUtil.getValue(this.getSession(), GtdSessionKeys.CURR_CTX);
    }

    public GTDList getCurrentList()
    {
        return (GTDList) SessionUtil.getValue(this.getSession(), GtdSessionKeys.CURR_LIST);
    }

    public Task getCurrentTask()
    {
        return (Task) SessionUtil.getValue(this.getSession(), GtdSessionKeys.CURR_TASK);
    }

    protected GtdClipboard getClipboard()
    {
        HttpSession sess = this.getSession();
        GtdClipboard clip = (GtdClipboard) SessionUtil.getValue(sess, GtdSessionKeys.GTD_CLIPBOARD);
        if (clip==null) {
            clip = new GtdClipboard();
            SessionUtil.fill(sess, GtdSessionKeys.GTD_CLIPBOARD, clip);
        }

        return clip;
    }

    protected Integer getContextIdFilter()
    {
        Integer ctx = (Integer) SessionUtil.getValue(this.getSession(), GtdSessionKeys.FILTER_CONTEXT);

        if (ctx==null) {
            return -1;
        }

        return ctx;
    }

    protected void setContextIdFilter(Integer contextId)
    {
        if (contextId>=0) {
            ContextManager cm = this.getContextManager();
            try
            {
                cm.findById(contextId);
            }catch(ObjectNotFoundException e){
                contextId = -1;
            }
        } else {
            contextId = -1;
        }

        SessionUtil.fill(this.getSession(), GtdSessionKeys.FILTER_CONTEXT, contextId);
    }

    public void setAffectedContexts(Collection<Context> ctxs) {
        Map<Integer, Boolean> affectedContexts = new HashMap<Integer, Boolean>();

        for (Context ctx: ctxs) {
            affectedContexts.put(ctx.getId(), Boolean.TRUE);
        }

        if (affectedContexts.isEmpty()) {
            affectedContexts.put(-1, Boolean.FALSE);
        }

        HttpServletRequest req = this.getContext().getRequest();
        req.setAttribute(RequestAttributes.AFFECTED_CONTEXTS, affectedContexts);
    }

    public void setAffectedContexts(Collection<Task> ... cols) {
        Integer ctxId = this.getContextIdFilter();
        
        Map<Integer, Boolean> affectedContexts = new HashMap<Integer, Boolean>();
        {
            if (ctxId > 0) {
                affectedContexts.put(ctxId, Boolean.TRUE);
            } else {
                for (Collection<Task> col: cols) {
                    this.appendAffectedCtxs(col, affectedContexts);
                }
                if (affectedContexts.isEmpty()) {
                    affectedContexts.put(-1, Boolean.FALSE);
                }
            }
        }

        HttpServletRequest req = this.getContext().getRequest();
        req.setAttribute(RequestAttributes.AFFECTED_CONTEXTS, affectedContexts);
    }

    private void appendAffectedCtxs(Collection<Task> tasks, Map<Integer, Boolean> affectedContexts) {
        for (Task t : tasks) {
            affectedContexts.put(t.getList().getContext().getId(), Boolean.TRUE);
        }
    }

}
