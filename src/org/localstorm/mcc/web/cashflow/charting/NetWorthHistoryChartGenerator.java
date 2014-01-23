package org.localstorm.mcc.web.cashflow.charting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.cashflow.TargetManager;
import org.localstorm.mcc.ejb.cashflow.entity.HistoricalValue;
import org.localstorm.mcc.ejb.cashflow.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.entity.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.cashflow.actions.wrap.TargetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @author localstorm
 */
public class NetWorthHistoryChartGenerator {

    private static XYDataset getNetWealthDataset(User user,
                                                 Integer daysPeriod,
                                                 boolean showTargets,
                                                 boolean includeDebt) {

        Calendar cal = Calendar.getInstance();

        if (daysPeriod == null) {
            cal.add(Calendar.YEAR, -50); // 50 years
        } else {
            cal.add(Calendar.DATE, -daysPeriod);
        }

        HistoricalValuesManager hvm = ContextLookup.lookup(HistoricalValuesManager.class,
                HistoricalValuesManager.BEAN_NAME);


        ValueType valueType = (includeDebt) ? ValueType.NET_WEALTH_CHECKPOINT
                : ValueType.NET_WEALTH_WO_DEBT_CHECKPOINT;

        Collection<HistoricalValue> hvs = hvm.getHistory(valueType,
                user,
                cal.getTime());

        HistoricalValue last = hvm.getLastHistoricalValue(valueType,
                BigDecimal.ZERO,
                user);

        Date minDate = new Date();

        if (hvs.isEmpty()) {
            HistoricalValue first = new HistoricalValue();
            {
                first.setFixDate(cal.getTime());
                first.setObjectId(null);
                first.setOwner(user);
                first.setVal(last.getVal());
                first.setValueTag(valueType);
            }
            hvs.add(first);
        }

        HistoricalValue right = new HistoricalValue();
        {
            right.setFixDate(new Date());
            right.setObjectId(null);
            right.setOwner(user);
            right.setVal(last.getVal());
            right.setValueTag(valueType);
        }

        hvs.add(right);

        TimeSeries netWealth = new TimeSeries("Net Wealth");

        TimeSeriesCollection tsc = new TimeSeriesCollection();

        for (HistoricalValue hv : hvs) {
            Date fixDate = hv.getFixDate();
            netWealth.addOrUpdate(new Day(fixDate), hv.getVal());

            if (minDate.after(fixDate)) {
                minDate = fixDate;
            }
        }

        tsc.addSeries(netWealth);

        if (showTargets) {
            OperationManager om = ContextLookup.lookup(OperationManager.class,
                    OperationManager.BEAN_NAME);

            TargetManager tm = ContextLookup.lookup(TargetManager.class,
                    TargetManager.BEAN_NAME);

            Collection<Target> tgts = tm.find(user);
            List<Target> tgtsList = new ArrayList<Target>(WrapUtil.wrapTargets(tgts, om));
            Collections.sort(tgtsList, TargetWrapper.getCurrentCostComparator());

            for (Target tgt : tgtsList) {
                TargetWrapper tgtw = (TargetWrapper) tgt;
                Cost c = tgtw.getCurrentCost();

                TimeSeries ts = new TimeSeries(tgtw.getName());
                {
                    ts.addOrUpdate(new Day(minDate), c.getBuy());
                    ts.addOrUpdate(new Day(new Date()), c.getBuy());
                }

                tsc.addSeries(ts);
            }
        }

        return tsc;
    }

    public static JFreeChart getChart(User user,
                                      Integer daysOffset,
                                      String name,
                                      boolean showTargets,
                                      boolean includeDebt) {

        XYDataset dataset = getNetWealthDataset(user, daysOffset, showTargets, includeDebt);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(name,
                "Time line",
                "Money",
                dataset,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();
        {
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat(Constants.REDUCED_DATE_FORMAT));
            plot.setForegroundAlpha(0.7f);
            plot.setNoDataMessage("No data to display");
            plot.setRangeGridlinePaint(Color.BLUE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setBackgroundPaint(Color.WHITE);


            XYItemRenderer ir = plot.getRenderer();
            ir.setBaseStroke(new BasicStroke(2.0f));
        }

        return chart;
    }

}
