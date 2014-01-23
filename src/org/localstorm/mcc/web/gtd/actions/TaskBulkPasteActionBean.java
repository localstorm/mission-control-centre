package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/BulkPasteTask")
public class TaskBulkPasteActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    
    @DefaultHandler
    @Logged
    public Resolution pasting() throws Exception {
        
        ListManager lm = super.getListManager();
        TaskManager tm = super.getTaskManager();
        GtdClipboard clip = super.getClipboard();

        GTDList dst = lm.findById(this.getListId());

        for (Task task : clip.getTasks())
        {
            task.setList(dst);
            tm.update(task);
        }

        clip.clearTasks();
        
        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter(ListViewActionBean.IncomingParameters.LIST_ID, dst.getId());
        }
        
        return rr;
    }
}
