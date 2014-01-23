package org.localstorm.mcc.ejb.gtd.agent;

import java.util.Collection;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class OverdueCommandHandler implements CommandHandler
{

    @Override
    public String handle(int uid, String from, String to, String param)
    {
        TaskManager tm = ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
        UserManager um = ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);

        User    owner = um.findById(uid);
        
        return this.buildResponse(tm.findRedlined(owner, null), tm.findDeadlined(owner, null));
    }

    private String buildResponse(Collection<Task> red, Collection<Task> dead)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- DEAD LINE tasks ---\n");
        for (Task t: dead)
        {
            sb.append('[');
            sb.append(t.getId());
            sb.append("] ");
            sb.append(t.getSummary());
            sb.append('\n');
            sb.append("--------------------\n");
        }

        sb.append("--- RED LINE tasks ---\n");
        for (Task t: red)
        {
            sb.append('[');
            sb.append(t.getId());
            sb.append("] ");
            sb.append(t.getSummary());
            sb.append('\n');
            sb.append("--------------------\n");
        }

        return sb.toString();
    }

    
}
