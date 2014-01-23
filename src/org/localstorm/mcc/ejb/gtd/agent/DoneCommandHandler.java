package org.localstorm.mcc.ejb.gtd.agent;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public class DoneCommandHandler implements CommandHandler
{
    @Override
    public String handle(int uid, String from, String to, String param)
    {
        int taskId = -1;
        try {
            taskId = Integer.parseInt(param);
        } catch(NumberFormatException e) {
            return "invalid task ID: ["+param+"]";
        }


        TaskManager tm = ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
        HintManager hm = ContextLookup.lookup(HintManager.class, HintManager.BEAN_NAME);

        try {
            
            Task t = tm.findById(taskId);
            if (!t.getList().getContext().getOwner().getId().equals(uid)) {
                return "access denied";
            }

            if (hm.getHintsForTask(t).isEmpty()) {
                t.setDelegated(false);
                t.setRuntimeNote(null);
                t.setFinished(true);
                tm.update(t);
            } else {
                hm.updateHintsForTask(t);
            }

            return "done: ["+t.getSummary()+"]";
        } catch(ObjectNotFoundException e) {
            return "no such task: ["+taskId+"]";
        }
    }
}
