package org.localstorm.mcc.web.util;

import java.util.Collection;
import java.util.Iterator;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 *
 * @author localstorm
 */
public class FilterUtil {

    public static void applyContextFilter(Collection<Task> tasks, Integer ctxId)
    {
        if ( ctxId<0 ) {
            return;
        }

        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task t = it.next();
            
            // This "t.getList().getContext().getId()" is a bottleneck
            if (!ctxId.equals(t.getList().getContext().getId())) {
                it.remove();
            }
        }
    }

    public static void applyContextFilter(Collection<Task> tasks, Integer ctxId, boolean removeFinished)
    {
        if ( ctxId<0 && !removeFinished ) {
            return;
        }

        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task t = it.next();
            if (ctxId>=0) {
                // This "t.getList().getContext().getId()" is a bottleneck
                if (!ctxId.equals(t.getList().getContext().getId())) {
                    it.remove();
                    continue;
                }
                if (removeFinished && (t.isCancelled() || t.isFinished())) {
                    it.remove();
                    continue;
                }
            }
        }
    }
}
