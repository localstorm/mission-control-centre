package org.localstorm.mcc.web.gtd;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 * @author Alexey Kuznetsov
 */
public class GtdClipboard
{
    private Map<Integer, GTDList> lists;
    private Map<Integer, Task>    tasks;
    private Map<Integer, Note>           notes;
    private Map<Integer, FileAttachment> files;
    
    public GtdClipboard() {
        lists = new TreeMap<Integer, GTDList>();
        tasks = new TreeMap<Integer, Task>();
        notes = new TreeMap<Integer, Note>();
        files = new TreeMap<Integer, FileAttachment>();
    }

    public synchronized Collection<FileAttachment> getFiles()
    {
        return this.files.values();
    }
    
    public synchronized Collection<GTDList> getLists()
    {
        return this.lists.values();
    }

    public synchronized Collection<Task> getTasks() {
        return this.tasks.values();
    }

    public synchronized Collection<Note> getNotes() {
        return this.notes.values();
    }

    public boolean isTaskInClipboard(Task t)
    {
        return this.tasks.containsKey(t.getId());
    }

    public boolean isListInClipboard(GTDList l)
    {
        return this.lists.containsKey(l.getId());
    }
    
    public synchronized GTDList pickList(Integer id)
    {
        return this.lists.remove(id);
    }
    
    public synchronized Task pickTask(Integer id)
    {
        return this.tasks.remove(id);
    }

    public synchronized Note pickNote(Integer id)
    {
        return this.notes.remove(id);
    }
    
    public synchronized FileAttachment pickFile(Integer id)
    {
        return this.files.remove(id);
    }

    public synchronized void copyTask(Task t)
    {
        this.tasks.put(t.getId(), t);
    }
    
    public synchronized void copyList(GTDList l)
    {
        this.lists.put(l.getId(), l);
    }

    public synchronized void copyNote(Note n)
    {
        this.notes.put(n.getId(), n);
    }

    public synchronized void copyFile(FileAttachment n)
    {
        this.files.put(n.getId(), n);
    }

    public synchronized void clearLists()
    {
        this.lists.clear();
    }

    public synchronized void clearTasks()
    {
        this.tasks.clear();
    }

    public synchronized void clearNotes()
    {
        this.notes.clear();
    }

    public synchronized void clearFiles()
    {
        this.files.clear();
    }
}
