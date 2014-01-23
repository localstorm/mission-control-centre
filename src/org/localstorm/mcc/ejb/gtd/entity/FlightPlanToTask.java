package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
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
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="TASKS_TO_FLIGHT_PLANS")
@NamedQueries({
    @NamedQuery(
        name = FlightPlanToTask.Queries.FIND_CONNECTORS_BY_TASK_AND_PLAN,
        query= "SELECT o FROM FlightPlanToTask o WHERE o.flightPlan=:flightPlan and o.task=:task"
    ),
    @NamedQuery(
        name = FlightPlanToTask.Queries.FIND_TASKS_BY_PLAN,
        query= "SELECT o.task FROM FlightPlanToTask o JOIN FETCH o.task.list JOIN FETCH o.task.list.context WHERE o.flightPlan=:flightPlan" +
               " ORDER BY o.task.list.context.name, o.task.list.name, o.task.effort"
    ),
    @NamedQuery(
        name = FlightPlanToTask.Queries.FIND_TASKS_BY_PLAN_AND_CTX,
        query= "SELECT o.task FROM FlightPlanToTask o JOIN FETCH o.task.list JOIN FETCH o.task.list.context WHERE o.flightPlan=:flightPlan and o.task.list.context=:ctx" +
               " ORDER BY o.task.list.name, o.task.effort"
    )
})
public class FlightPlanToTask extends AbstractEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @JoinColumn(name="plan_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private FlightPlan flightPlan;
    
    @JoinColumn(name="task_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Task task;
    private static final long serialVersionUID = 551690240853897389L;

    public FlightPlanToTask() {
    }

    public FlightPlanToTask(FlightPlan fp, Task task) {
        this.id = null;
        this.flightPlan = fp;
        this.task = task;
    }
    
    public Integer getId() {
        return id;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(FlightPlan fp) {
        this.flightPlan = fp;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    
    public static interface Queries 
    {
        public static final String FIND_CONNECTORS_BY_TASK_AND_PLAN = "findByTaskAndPlan";
        public static final String FIND_TASKS_BY_PLAN               = "findByPlan";
        public static final String FIND_TASKS_BY_PLAN_AND_CTX       = "findByPlanAndCtx";
    }
    
    public static interface Properties 
    {
        public static final String TASK = "task";
        public static final String FLIGHT_PLAN = "flightPlan";
        public static final String CONTEXT = "ctx";
    }
    
}
