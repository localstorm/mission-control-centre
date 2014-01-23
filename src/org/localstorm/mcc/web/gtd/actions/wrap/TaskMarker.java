package org.localstorm.mcc.web.gtd.actions.wrap;

import java.util.Date;
import org.localstorm.mcc.ejb.gtd.entity.Task;

/**
 *
 * @author localstorm
 */
public class TaskMarker {

    private Task task;
    private Boolean redCrossed;
    private Boolean redNonCrossed;
    private int timeRemainsCrossed;
    private int timeRemainsNonCrossed;
    private static final long MILLISECS_PER_DAY = 1000*60*60*24;

    public TaskMarker(Task t)
    {
        this.task = t;
        this.redCrossed    = this.checkRedCrossed();
        this.redNonCrossed = this.checkRedNonCrossed();
        this.timeRemainsCrossed      = this.checkRemainsCrossed();
        this.timeRemainsNonCrossed   = this.checkRemainsNonCrossed();
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isRedlineCrossed() {
        Date rl = task.getRedline();
        return (rl!=null && rl.before(new Date()));
    }

    public boolean isDeadlineCrossed() {
        Date dl = task.getDeadline();
        return (dl!=null && dl.before(new Date()));
    }

    public Boolean getRedCrossed()
    {
        return this.redCrossed;
    }

    public Boolean getRedNonCrossed()
    {
        return this.redNonCrossed;
    }

    public int getTimeRemainsCrossed() {
        return this.timeRemainsCrossed;
    }

    public int getTimeRemainsNonCrossed() {
        return timeRemainsNonCrossed;
    }

    private int daysDelta(Date redline, Date today) {
        long endL   = redline.getTime();
        long startL = today.getTime();
        return (int) ((endL - startL) / MILLISECS_PER_DAY);
    }

    private Boolean isCorrect() {
        Date rl = task.getRedline();
        Date dl = task.getDeadline();

        return (rl!=null && dl!=null) ? rl.getTime()<=dl.getTime() : true;
    }

    private Boolean checkRedCrossed() {
        if (isRedlineCrossed() && (!isDeadlineCrossed())) {
            return true;
        }
        if ((!isRedlineCrossed()) && isDeadlineCrossed()) {
            return false;
        }
        if ((!isRedlineCrossed()) && (!isDeadlineCrossed())) {
            return null;
        }
        if (isRedlineCrossed() && isDeadlineCrossed()) {
            return !this.isCorrect();
        }
        throw new RuntimeException("Unexpected case");
    }

    private Boolean checkRedNonCrossed() {
        if (isRedlineCrossed() && (!isDeadlineCrossed())) {
            if (task.getDeadline() == null) {
                return null;
            } else {
                return false;
            }
        }
        if ((!isRedlineCrossed()) && isDeadlineCrossed()) {
            if (task.getRedline() == null) {
                return null;
            } else {
                return true;
            }
        }
        if ((!isRedlineCrossed()) && (!isDeadlineCrossed())) {
            if (task.getRedline()==null && task.getDeadline()==null) {
                return null;
            }
            if (task.getRedline()!=null && task.getDeadline()==null) {
                return true;
            }
            if (task.getRedline()==null && task.getDeadline()!=null) {
                return false;
            }
            if (task.getRedline()!=null && task.getDeadline()!=null) {
                return this.isCorrect();
            }
        }
        if (isRedlineCrossed() && isDeadlineCrossed()) {
            return null;
        }
        throw new RuntimeException("Unexpected case");
    }


    private int checkRemainsCrossed() {
        Date today = new Date();

        Boolean rc = getRedCrossed();
        if (rc!=null) {
            if (rc) {
                return daysDelta(this.task.getRedline(), today);
            } else {
                return daysDelta(this.task.getDeadline(), today);
            }
        }

        return -1;
    }

    private int checkRemainsNonCrossed() {
        Date today = new Date();

        Boolean rc = getRedNonCrossed();
        if (rc!=null) {
            if (rc) {
                if (this.task.getRedline()==null) {
                    throw new RuntimeException("Unexpected case");
                }
                return daysDelta(this.task.getRedline(), today);
            } else {
                if (this.task.getDeadline()==null) {
                    throw new RuntimeException("Unexpected case");
                }
                return daysDelta(this.task.getDeadline(), today);
            }
        }

        return -1;
    }

}
