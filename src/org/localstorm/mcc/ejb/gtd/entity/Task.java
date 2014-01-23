package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;


/**
 * @author localstorm
 */
@Entity
@Table(name="TASKS")
@NamedQueries({
    @NamedQuery(
        name = Task.Queries.FIND_LOE,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and " +
        "o.awaited=false and o.delegated=false and o.list.context.owner=:user " +
        "and o.effort=:effort and o NOT IN (SELECT h.task from Hint h)" +
        " ORDER BY o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_LOE_BY_CTX,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and o.awaited=false and " +
               "o.delegated=false and o.list.context=:ctx and " +
               "o.effort=:effort and o NOT IN (SELECT h.task from Hint h)" +
        " ORDER BY o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_FINISHED_BY_CTX,
        query= "SELECT o FROM Task o WHERE (o.cancelled=true or o.finished=true) and o.list.context=:ctx" +
        " ORDER BY o.list.name, o.effort"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_FINISHED,
        query= "SELECT o FROM Task o WHERE (o.cancelled=true or o.finished=true) and o.list.context.owner=:user" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_AWAITED_BY_CTX,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and o.delegated=true and o.list.context=:ctx" +
        " ORDER BY o.list.name, o.effort"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_AWAITED,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and o.delegated=true and o.list.context.owner=:user" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_HINTED_BY_USER,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and o.delegated=false and o.list.context.owner=:user" +
        " and o in (SELECT h.task FROM Hint h)" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_HINTED_BY_CTX,
        query= "SELECT o FROM Task o WHERE o.cancelled=false and o.finished=false and o.delegated=false and o.list.context=:ctx" +
        " and o in (SELECT h.task FROM Hint h)" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_UNFINISHED,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user and " +
        "o.finished=false and o.cancelled=false and o.delegated=false and " +
        "o NOT IN (SELECT h.task from Hint h) and " +
        "o.awaited=false ORDER BY o.list.context.name, o.list.name ASC"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_UNFINISHED_BY_CTX,
        query= "SELECT o FROM Task o WHERE o.list.context=:ctx and o.finished=false and " +
        "o.cancelled=false and o.delegated=false and o NOT IN (SELECT h.task from Hint h) " +
        "and o.awaited=false ORDER BY o.list.name, o.effort ASC"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_USER,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_OLDEST_BY_CTX,
        query= "SELECT o FROM Task o WHERE o.list.context=:ctx and o.finished=false and " +
        "o.cancelled=false and o NOT IN (SELECT h.task from Hint h) " +
        "ORDER BY o.creation ASC"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_PENDING_CONSTRAINED_BY_USER,
        query= "SELECT o FROM Task o JOIN FETCH o.list JOIN FETCH o.list.context WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and (o.redline IS NOT NULL or o.deadline IS NOT NULL)"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_CLEANABLE_BY_USER,
        query= "SELECT o FROM Task o WHERE o.list.context.owner=:user and (o.finished=true or o.cancelled=true)"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_DEADLINED,
        query= "SELECT o FROM Task o  JOIN FETCH o.list JOIN FETCH o.list.context WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and o.deadline<=:now" +
        " ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_DEADLINED_BY_CTX,
        query= "SELECT o FROM Task o  JOIN FETCH o.list JOIN FETCH o.list.context WHERE o.list.context=:ctx and o.finished=false and o.cancelled=false and o.deadline<=:now" +
        " ORDER BY o.list.name, o.effort"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_REDLINED,
        query= "SELECT o FROM Task o JOIN FETCH o.list JOIN FETCH o.list.context WHERE o.list.context.owner=:user and o.finished=false and o.cancelled=false and o.redline<=:now and (o.deadline>:now or o.deadline is NULL)" +
        "  ORDER BY o.list.context.name, o.list.name"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_REDLINED_BY_CTX,
        query= "SELECT o FROM Task o JOIN FETCH o.list JOIN FETCH o.list.context WHERE o.list.context=:ctx and o.finished=false and o.cancelled=false and o.redline<=:now and (o.deadline>:now or o.deadline is NULL)" +
        "  ORDER BY o.list.name, o.effort"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST,
        query= "SELECT o FROM Task o WHERE o.list=:list and o.finished=false and o.delegated=false and o.cancelled=false ORDER BY o.effort, o.summary"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST_ARCHIVED,
        query= "SELECT o FROM Task o WHERE o.list=:list and (o.finished=true or o.cancelled=true)  ORDER BY o.summary"
    ),
    @NamedQuery(
        name = Task.Queries.FIND_BY_LIST_AWAITED,
        query= "SELECT o FROM Task o WHERE o.list=:list and o.finished=false and o.delegated=true  ORDER BY o.summary"
    )
})
public class Task extends AbstractEntity implements Identifiable, Serializable
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="summary", unique=false, updatable=true, nullable=false )
    private String summary;
    
    @Column(name="details", unique=false, updatable=true, nullable=true )
    private String details;
    
    @Column(name="runtime_note", unique=false, updatable=true, nullable=true )
    private String runtimeNote;
    
    @Column(name="effort", unique=false, updatable=true, nullable=false )    
    private int effort;
    
    @JoinColumn(name="list_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private GTDList list;
    
    @Column(name="is_cancelled", unique=false, updatable=true, nullable=false )    
    private boolean cancelled;
    
    @Column(name="is_finished", unique=false, updatable=true, nullable=false )       
    private boolean finished;
    
    @Column(name="is_awaited", unique=false, updatable=true, nullable=false )    
    private boolean awaited;
    
    @Column(name="is_paused", unique=false, updatable=true, nullable=false )    
    private boolean paused;
    
    @Column(name="is_delegated", unique=false, updatable=true, nullable=false )    
    private boolean delegated;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @Column(name="deadline", unique=false, updatable=true, nullable=true )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    
    @Column(name="redline", unique=false, updatable=true, nullable=true )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date redline;
    private static final long serialVersionUID = 3044154145418166222L;

    public Task() 
    {
    
    }

    public Task(String summary, String details, GTDList list, Date deadline, Date redline) {
        this.summary = summary;
        this.details = details;
        this.list = list;
        this.deadline = deadline;
        this.redline = redline;
        this.finished = false;
        this.awaited = false;
        this.paused = true;
        this.delegated = false;
        this.creation = new Date();
        this.effort = 3;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getCreation() {
        return creation;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getRedline() {
        return redline;
    }

    public GTDList getList() {
        return list;
    }

    public String getDetails() {
        return details;
    }

    public String getSummary() {
        return summary;
    }

    public String getRuntimeNote() {
        return runtimeNote;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isAwaited() {
        return awaited;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getEffort() {
        return effort;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled  = cancelled;
    }

    public void setFinished(boolean accomplished) {
        this.finished = accomplished;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDelegated(boolean delegated) {
        this.delegated = delegated;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setList(GTDList list) {
        this.list = list;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setRedline(Date redline) {
        this.redline = redline;
    }

    public void setRuntimeNote(String runtimeNote) {
        this.runtimeNote = runtimeNote;
    }

    public void setAwaited(boolean started) {
        this.awaited = started;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }
    
    public static interface Queries {
        public static final String FIND_UNFINISHED        = "findUnfinished";
        public static final String FIND_UNFINISHED_BY_CTX = "findUnfinishedByCtx";
        public static final String FIND_AWAITED          = "findAwaitedTasks";
        public static final String FIND_AWAITED_BY_CTX   = "findAwaitedTasksByCtx";
        public static final String FIND_BY_LIST          = "findByList";
        public static final String FIND_BY_LIST_ARCHIVED = "findByListArchived";
        public static final String FIND_BY_LIST_AWAITED  = "findByListAwaited";
        public static final String FIND_REDLINED         = "findRedlined";
        public static final String FIND_REDLINED_BY_CTX  = "findRedlinedByCtx";
        public static final String FIND_DEADLINED        = "findDeadlined";
        public static final String FIND_DEADLINED_BY_CTX = "findDeadlinedByCtx";
        public static final String FIND_CLEANABLE_BY_USER= "findCleanableTasksUpByUser";
        public static final String FIND_PENDING_CONSTRAINED_BY_USER = "findPendingConstrByUser";
        public static final String FIND_HINTED_BY_USER   = "findHintedByUser";
        public static final String FIND_HINTED_BY_CTX    = "findHintedByCtx";
        public static final String FIND_OLDEST_BY_CTX    = "finOldestTasksByCtx";
        public static final String FIND_BY_USER          = "findAllTasksByUser";
        public static final String FIND_FINISHED         = "findFinished";
        public static final String FIND_FINISHED_BY_CTX  = "findFinishedByCtx";
        public static final String FIND_LOE              = "findLoE";
        public static final String FIND_LOE_BY_CTX       = "findLoEByCtx";
    }
    
    public static interface Properties
    {
        public static final String CTX    = "ctx";
        public static final String LIST   = "list";
        public static final String USER   = "user";
        public static final String NOW    = "now";
        public static final String EFFORT = "effort";
    }

}
