package org.localstorm.mcc.web.gtd.actions.wrap;

import java.util.*;

import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.Hint;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WrapUtil 
{
    public static Collection<TaskWrapper> genWrappers(Collection<Task> taskList,
                                                      Collection<Task> currentFp) 
    {
        Map<Integer, Boolean> mp = new HashMap<Integer, Boolean>();
        for (Task t: currentFp)
        {
            mp.put( t.getId(), Boolean.TRUE );
        }
        
        Collection<TaskWrapper> result = new ArrayList<TaskWrapper>(taskList.size());
        for (Task t: taskList)
        {
            result.add(new TaskWrapper(t, mp.containsKey(t.getId())));
        }
        
        return result;
    }

    public static TaskWrapper genWrapper(Task t, Collection<Task> currentFp, Collection<Hint> hints)
    {
        boolean fp = false;
        for (Task task: currentFp)
        {
            if (task.getId().equals(t.getId()))
            {
                fp = true;
                break;
            }
        }

        return new TaskWrapper(t, fp, hints);
    }
    
    public static Collection<NoteWrapper> genWrappers(Collection<Note> notes) 
    {
        ArrayList<NoteWrapper> nws = new ArrayList<NoteWrapper>(notes.size());
        
        for (Note note : notes)
        {
            nws.add(new NoteWrapper(note));
        }
        
        return nws;
    }

    public static Collection<InboxEntryWrapper> genWrappers(List<InboxEntry> ibx) {
        ArrayList<InboxEntryWrapper> nws = new ArrayList<InboxEntryWrapper>(ibx.size());

        for (InboxEntry entry : ibx)
        {
            nws.add(new InboxEntryWrapper(entry));
        }

        return nws;
    }
}
