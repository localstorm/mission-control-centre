package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.entity.Effort;
import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface TaskManager extends BaseManager<Task>
{
    public static final String BEAN_NAME = "TaskManagerBean";

    public Collection<Task> findAllByUser(User user);

    public Collection<Task> findByLoE(User user, Context ctx, Effort effort);

    public Collection<Task> findAwaited(User user, Context ctx);

    public Collection<Task> findDeadlined(User user, Context ctx);

    public Collection<Task> findFinished(User user, Context context);

    public Collection<Task> findHinted(User user, Context context);

    public Collection<Task> findRedlined(User user, Context ctx);

    public Collection<Task> findPending(User user, Context ctx);

    public void removeFinishedTasks(User user);
    
    public Collection<Task> findOpeartiveByList(GTDList l);
    
    public Collection<Task> findAwaitedByList(GTDList l);
    
    public Collection<Task> findArchiveByList(GTDList l);
    
    /* Doesn't return archived contexts */

    public Collection<Task> findOldestOperative(Context ctx, int maxOldestTasks);

    public Collection<Task> findPendingTimeConstrainedTasks(User user);



}
