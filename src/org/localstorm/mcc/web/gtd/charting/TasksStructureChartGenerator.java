package org.localstorm.mcc.web.gtd.charting;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.gtd.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.gtd.dao.DashboardReportRow;
import org.localstorm.mcc.ejb.gtd.GtdReporter;
import org.localstorm.mcc.ejb.gtd.entity.Effort;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;


/**
 *
 * @author localstorm
 */
public class TasksStructureChartGenerator {

     public static JFreeChart getChartByLoE(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String curDate = sdf.format(new Date());

        PieDataset ds    = TasksStructureChartGenerator.getTasksStructureDataset(user);
        JFreeChart chart = ChartFactory.createPieChart3D("Tasks structure by LoE (" + curDate + ")",
                                                         ds,
                                                         true,
                                                         true,
                                                         false);


        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        {
            plot.setDarkerSides(true);
            plot.setIgnoreZeroValues(true);
            plot.setCircular(true);
            plot.setStartAngle(120);
            plot.setDirection(Rotation.CLOCKWISE);
            plot.setForegroundAlpha(0.7f);
            plot.setNoDataMessage("No data to display");
        }
        
        Color elementary = new Color(0, 128, 0);
        Color easy = new Color(128, 255, 128);
        Color medium = new Color(255, 255, 128);
        Color difficult = new Color(255, 128, 0);
        Color vd = new Color(255, 0, 0);

        Color[] colors = new Color[] {
            elementary, easy, medium, difficult, vd
        };

        int i=0;
        for (Object ko : ds.getKeys())
        {
            plot.setSectionPaint(ko.toString(), colors[i++]);
        }


        return chart;
    }

     public static JFreeChart getChartByContext(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String curDate = sdf.format(new Date());

        PieDataset ds    = TasksStructureChartGenerator.getTasksContextStructureDataset(user);
        JFreeChart chart = ChartFactory.createPieChart3D("Tasks structure by context (" + curDate + ")",
                                                         ds,
                                                         true,
                                                         true,
                                                         false);


        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        {
            plot.setDarkerSides(true);
            plot.setIgnoreZeroValues(true);
            plot.setCircular(true);
            plot.setStartAngle(120);
            plot.setDirection(Rotation.CLOCKWISE);
            plot.setForegroundAlpha(0.7f);
            plot.setNoDataMessage("No data to display");
        }

        return chart;
    }

    private static PieDataset getTasksContextStructureDataset(User user) {
        ContextManager cm = ContextLookup.lookup(ContextManager.class, ContextManager.BEAN_NAME);
        
        Collection<Context> ctxs     = cm.getContexts(user);
        Map<Integer, Integer> ctxMap = new TreeMap<Integer, Integer>();

        GtdReporter reporter = ContextLookup.lookup(GtdReporter.class, GtdReporter.BEAN_NAME);
        DashboardReportBean report = reporter.getDashboardReport(user);

        int[] ctxSpread = new int[ctxs.size()];
        
        int i=0;
        List<DashboardReportRow>   rows = report.getRows();
        Iterator<DashboardReportRow> it = rows.iterator();
        for (Context ctx: ctxs) {
            ctxSpread[i] = it.next().getPending();
            ctxMap.put(ctx.getId(), i++);
        }

        DefaultPieDataset result = new DefaultPieDataset();

        for (Context ctx: ctxs) {
            int val = ctxSpread[ctxMap.get(ctx.getId())];
            result.setValue(ctx.getName()+" ("+val+")", val);
        }

        return result;
    }

    private static PieDataset getTasksStructureDataset(User user) {

        int[] loeSpread = new int[Effort.values().length+1];

        DefaultPieDataset result = new DefaultPieDataset();

        GtdReporter reporter = ContextLookup.lookup(GtdReporter.class, GtdReporter.BEAN_NAME);
        DashboardReportBean report = reporter.getDashboardReport(user);
        DashboardReportRow totals = report.getTotals();

        loeSpread[Effort.ELEMENTARY.getEffort()] = totals.getElementary();
        loeSpread[Effort.EASY.getEffort()]       = totals.getEasy();
        loeSpread[Effort.MEDIUM.getEffort()]     = totals.getMedium();
        loeSpread[Effort.DIFFICULT.getEffort()]  = totals.getDifficult();
        loeSpread[Effort.VERY_DIFFICULT.getEffort()] = totals.getVeryDifficult();

        for (Effort effort : Effort.values()) {
            result.setValue(effort.getLatinName()+" ("+loeSpread[effort.getEffort()]+")", loeSpread[effort.getEffort()]);
        }

        return result;
    }

    private static int getTasksCount(DashboardReportRow row) {
        
        return row.getElementary()+
               row.getEasy()+
               row.getMedium()+
               row.getDifficult()+
               row.getVeryDifficult();
    }
   
}
