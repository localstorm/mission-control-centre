package org.localstorm.mcc.ejb.gtd.dao;

import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author localstorm
 */
public class DashboardReportRow extends AbstractEntity {
    private String contextName;
    private int contextId;
    private int pending;
    private int awaited;
    private int flightPlan;
    private int red;
    private int dead;
    private int done;
    private int elementary;
    private int easy;
    private int medium;
    private int difficult;
    private int veryDifficult;
    private int hinted;
    private static final long serialVersionUID = 8919509196078405450L;

    public int getHinted() {
        return hinted;
    }

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

    public int getDifficult() {
        return difficult;
    }

    public int getEasy() {
        return easy;
    }

    public int getElementary() {
        return elementary;
    }

    public int getMedium() {
        return medium;
    }

    public int getVeryDifficult() {
        return veryDifficult;
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

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public void setElementary(int elementary) {
        this.elementary = elementary;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public void setVeryDifficult(int veryDifficult) {
        this.veryDifficult = veryDifficult;
    }

    public void setHinted(int hinted) {
        this.hinted = hinted;
    }
    
}
