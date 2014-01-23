package org.localstorm.mcc.ejb.gtd.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author localstorm
 */
public class DashboardReportBean extends AbstractEntity {

    private Map<Integer, DashboardReportRow> merger;
    private List<DashboardReportRow> rows;
    private DashboardReportRow totals;
    private static final long serialVersionUID = -8594071705479152121L;

    public DashboardReportBean()
    {
        this.rows   = new LinkedList<DashboardReportRow>();
        this.merger = new HashMap<Integer, DashboardReportRow>();
        this.totals = new DashboardReportRow();
    }

    public List<DashboardReportRow> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public DashboardReportRow getTotals() {
        return this.totals;
    }

    public void addReportRowP1(DashboardReportRowP1 row) {
        totals.setPending(totals.getPending()+row.getPending());
        totals.setAwaited(totals.getAwaited()+row.getAwaited());
        totals.setFlightPlan(totals.getFlightPlan()+row.getFlightPlan());
        totals.setRed(totals.getRed()+row.getRed());
        totals.setDead(totals.getDead()+row.getDead());
        totals.setDone(totals.getDone()+row.getDone());

        DashboardReportRow half = new DashboardReportRow();
        half.setContextName(row.getContextName());
        half.setContextId(row.getContextId());
        half.setPending(row.getPending());
        half.setAwaited(row.getAwaited());
        half.setFlightPlan(row.getFlightPlan());
        half.setRed(row.getRed());
        half.setDead(row.getDead());
        half.setDone(row.getDone());

        rows.add(half);
        merger.put(row.getContextId(), half);
    }


    public void addReportRowP2(DashboardReportRowP2 row) {
        totals.setHinted(totals.getHinted()+row.getHinted());
        totals.setElementary(totals.getElementary()+row.getElementary());
        totals.setEasy(totals.getEasy()+row.getEasy());
        totals.setMedium(totals.getMedium()+row.getMedium());
        totals.setDifficult(totals.getDifficult()+row.getDifficult());
        totals.setVeryDifficult(totals.getVeryDifficult()+row.getVeryDifficult());
        
        DashboardReportRow half = merger.get(row.getContextId());
        if (half == null) {
            throw new IllegalStateException("Row not found for ctxId="+row.getContextId());
        }

        half.setHinted(row.getHinted());
        half.setElementary(row.getElementary());
        half.setEasy(row.getEasy());
        half.setMedium(row.getMedium());
        half.setDifficult(row.getDifficult());
        half.setVeryDifficult(row.getVeryDifficult());
    }

}
