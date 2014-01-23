package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.web.gtd.backend.TaskHitCollector;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.RAMDirectory;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.TaskWrapper;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/SubmitTaskSearch")
public class TaskSearchSubmitActionBean extends GtdBaseActionBean
{
    private static final String ID_FIELD   = "id";
    private static final String SEARCHABLE = "text";
    private static final char SPACE = ' ';

    private boolean found;

    @Validate(required=true)
    private String text;

    private List<Task> tasks;
    private List<Task> archive;
    private List<Task> awaited;


    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public List<Task> getOperativeTasks() {
        return tasks;
    }

    public List<Task> getAwaitedTasks() {
        return awaited;
    }

    public List<Task> getArchiveTasks() {
        return archive;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        
        this.tasks   = new LinkedList<Task>();
        this.awaited = new LinkedList<Task>();
        this.archive = new LinkedList<Task>();


        TaskManager tm = this.getTaskManager();
        User user = this.getUser();
        
        Collection<Task> ts = tm.findAllByUser(user);

        RAMDirectory ramd = this.fetchRamDirectory(ts);

        this.setFound(false);
        Collection<Task> result = this.search(ramd, ts, text);

        if (result.size()>0) {
            FlightPlanManager fp = this.getFlightPlanManager();
            Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findByUser(user));
            Collection<TaskWrapper> r1 = WrapUtil.genWrappers(result, fpt);

            for (Iterator<TaskWrapper> it = r1.iterator(); it.hasNext(); )
            {
                Task t = it.next();
                if (t.isFinished() || t.isCancelled()){
                    it.remove();
                    archive.add(t);
                    continue;
                }
                if (t.isAwaited() || t.isDelegated())
                {
                    it.remove();
                    awaited.add(t);
                    continue;
                }

                tasks.add(t);
            }

            this.setFound(true);
        }

        ReturnPageBean rpb = new ReturnPageBean(Pages.TASK_SEARCH_SUBMIT.toString());
        {
            rpb.setParam(IncomingParameters.TEXT, this.getText());
        }
        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.SEARCH_TASKS);
    }

    private RAMDirectory fetchRamDirectory(Collection<Task> tasks) throws IOException {

        RAMDirectory ramd = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(ramd, new StandardAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);

        for (Task t: tasks)
        {
            Document doc       = new Document();
            String searchable  = this.handleTask(t);
            doc.add(new Field(SEARCHABLE, searchable, Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field(ID_FIELD,   t.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            indexWriter.addDocument(doc);
        }

        indexWriter.close();

        return ramd;
    }

    private String handleTask(Task t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.getSummary());

        if (t.getDetails()!=null) {
            sb.append(SPACE);
            sb.append(t.getDetails());
        }

        if (t.getRuntimeNote()!=null) {
            sb.append(SPACE);
            sb.append(t.getRuntimeNote());
        }

        sb.append(SPACE);
        sb.append(t.getList().getName());

        sb.append(SPACE);
        sb.append(t.getList().getContext().getName());
        
        String searchable = sb.toString();
        return searchable;
    }

    private Collection<Task> search(RAMDirectory ramd, Collection<Task> ts, String text) throws Exception {
        Analyzer analyzer   = new StandardAnalyzer();
        IndexSearcher is    = new IndexSearcher(ramd);
        QueryParser parser  = new QueryParser(SEARCHABLE, analyzer);
        Query query         = parser.parse(text);
        TaskHitCollector hc = new TaskHitCollector(is, ID_FIELD, ts);

        is.search(query, hc);
        hc.close();

        return hc.getTasks();
    }

    public static interface IncomingParameters {
        public static final String TEXT = "text";
    }

    
}
