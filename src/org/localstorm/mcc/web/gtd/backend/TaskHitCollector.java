/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.gtd.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;

/**
 *
 * @author localstorm
 */
public class TaskHitCollector extends HitCollector {

    private IndexSearcher searcher;
    private Collection<Task> tasks;
    private String idFieldName;
    private Map<Integer, Task> tMap;
    private List<RelevantObject<Task>> result;

    public TaskHitCollector(IndexSearcher is, String idFieldName, Collection<Task> ts) {
        this.searcher    = is;
        this.tasks       = ts;
        this.idFieldName = idFieldName;
        this.tMap        = new TreeMap<Integer, Task>();

        for (Task t: tasks) {
            tMap.put(t.getId(), t);
        }

        result = new LinkedList<RelevantObject<Task>>();
    }

    @Override
    public void collect(int docId, float relevance) {
        try {

            Document doc = searcher.doc(docId);
            Field idf = doc.getField(this.idFieldName);
            String taskId = idf.stringValue();
            int tid = Integer.parseInt(taskId);

            Task t = this.tMap.get(tid);
            this.result.add(new RelevantObject(t, relevance));
            
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close()
    {
        Collections.sort(this.result);
    }

    public Collection<Task> getTasks() {
        List<Task> ordered = new ArrayList<Task>();

        for (RelevantObject<Task> r: this.result) {
            ordered.add(r.getObject());
        }

        return ordered;
    }

    
}
