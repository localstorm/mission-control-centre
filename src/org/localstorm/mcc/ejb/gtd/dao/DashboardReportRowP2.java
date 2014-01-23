package org.localstorm.mcc.ejb.gtd.dao;

import org.localstorm.mcc.ejb.AbstractEntity;

/**
 * @author localstorm
 */
public class DashboardReportRowP2 extends AbstractEntity {
    private int contextId;
    private int elementary;
    private int easy;
    private int medium;
    private int difficult;
    private int veryDifficult;
    private int hinted;
    private static final long serialVersionUID = -90782187999011944L;

    public int getHinted() {
        return hinted;
    }


    public int getContextId() {
        return contextId;
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

    public void setContextId(int contextId) {
        this.contextId = contextId;
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
