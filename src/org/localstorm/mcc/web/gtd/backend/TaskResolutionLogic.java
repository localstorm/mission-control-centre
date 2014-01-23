package org.localstorm.mcc.web.gtd.backend;

import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.gtd.entity.TaskResolutionAction;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.GtdClipboard;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TaskResolutionLogic 
{
    public static GTDList resolveTask( Integer taskId,
                                       TaskResolutionAction action,
                                       String runtimeNote,
                                       GTDList currentList,
                                       GtdClipboard clip,
                                       TaskManager tm,
                                       ListManager lm,
                                       FlightPlanManager fpm,
                                       User u) throws ObjectNotFoundException
    {
        Task t = tm.findById(taskId);
        
        FlightPlan fp = fpm.findByUser(u);
        
        switch (action) {
            case COPY:
                clip.copyTask(t);
                break;
            case PASTE:
                t = clip.pickTask(taskId);
                if (t!=null)
                {
                    t.setList(currentList);
                } else {
                    t = tm.findById(taskId);
                }
                break;
            case UNFLIGHT:
                fpm.removeTaskFromFlightPlan(t, fp);
                break;
            case FLIGHT:
                fpm.addTaskToFlightPlan(t, fp);
                break;
            case UNRESOLVE:
                t.setFinished(false);
                t.setRuntimeNote(null);
                t.setDelegated(false);
                t.setPaused(false);
                t.setAwaited(false);
                t.setCancelled(false);
                break;
            case UNDELEGATE:
                // Undelegating
                t.setRuntimeNote(null);
                t.setAwaited(false);
                t.setDelegated(false);
                break;
            case DELEGATE:
                // Delegating
                t.setRuntimeNote(runtimeNote);
                t.setAwaited(true);
                t.setDelegated(true);
                break;
            case CANCEL:
                // Cancelling tasks
                t.setDelegated(false);
                t.setRuntimeNote(null);
                t.setCancelled(true);
                t.setFinished(false);
                break;
            case FINISH:
                // Finishing tasks
                t.setDelegated(false);
                t.setRuntimeNote(null);
                t.setFinished(true);
                t.setCancelled(false);
                break;
            default:
                throw new RuntimeException("Unexpected action:"+action);
        }

        GTDList list = t.getList();

        tm.update(t);

        // List retirement?
        list.setArchived( (!list.isPinned()) && noMoreTasksPending(tm, list) );
        lm.update(list);
        
        return list;
    }
    
     

    private static boolean noMoreTasksPending(TaskManager tm, GTDList list) {
        return ( tm.findAwaitedByList(list).isEmpty() 
                 &&
                 tm.findOpeartiveByList(list).isEmpty() );
    }
}
