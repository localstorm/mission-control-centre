package org.localstorm.mcc.ejb.gtd.dao;

import org.localstorm.mcc.ejb.AbstractEntity;

/**
 * @author localstorm
 */
public class DashboardReportRowP1 extends AbstractEntity {
    private String contextName;
    private int contextId;
    private int pending;
    private int awaited;
    private int flightPlan;
    private int red;
    private int dead;
    private int done;
    private static final long serialVersionUID = 4145688403130841354L;

    public int getAwaited() {
        return awaited;
    }

    public int getContextId() {
        return contextId;
    }

    public String getContextName() {
        return contextName;
    }

    public int getDead() {
        return dead;
    }

    public int getFlightPlan() {
        return flightPlan;
    }

    public int getRed() {
        return red;
    }

    public int getPending() {
        return pending;
    }

    public int getDone() {
        return done;
    }

    public void setAwaited(int awaited) {
        this.awaited = awaited;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public void setFlightPlan(int flightPlan) {
        this.flightPlan = flightPlan;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setPending(int total) {
        this.pending = total;
    }

    public void setDone(int done) {
        this.done = done;
    }

    
}
