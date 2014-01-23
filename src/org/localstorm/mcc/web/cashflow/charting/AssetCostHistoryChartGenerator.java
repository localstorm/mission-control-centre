package org.localstorm.mcc.web.cashflow.charting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;

import static org.localstorm.mcc.web.cashflow.charting.DecimalUtil.*;

/**
 *
 * @author localstorm
 */
public class AssetCostHistoryChartGenerator {

    private static XYDataset getAssetCostDataset(User user, Integer assetId, Integer daysPeriod) throws Exception {

        Calendar cal = Calendar.getInstance();

        if ( daysPeriod==null ) {
            cal.add(Calendar.YEAR, -50); // 50 years
        } else {
            cal.add(Calendar.DATE, -daysPeriod);
        }

        OperationManager om = ContextLookup.lookup(OperationManager.class, OperationManager.BEAN_NAME);
        AssetManager     am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
        Asset         asset = am.find(assetId);
        ValuableObject vo = asset.getValuable();

        if (!user.getId().equals(vo.getOwner().getId())) {
            return null;
        }

        Collection<Cost> costs = om.getCostHistory(vo, cal.getTime());
        Cost current = om.getCurrentCost(vo);

        if (costs.isEmpty())
        {
            Cost cost = new Cost(vo);
            {
                cost.setBuy(current.getBuy());
                cost.setSell(current.getSell());
                cost.setActuationDate(cal.getTime());
            }

            costs = Collections.singletonList(cost);
        }

        TimeSeries sell     = new TimeSeries(asset.getName()+" sell cost");
        TimeSeries buy      = new TimeSeries(asset.getName()+" buy cost");

        TimeSeriesCollection tsc = new TimeSeriesCollection();

        for (Cost c: costs) {
            sell.addOrUpdate(new Day(c.getActuationDate()), c.getSell());
            buy.addOrUpdate(new Day(c.getActuationDate()), c.getBuy());
        }

        buy.addOrUpdate(new Day(new Date()), current.getBuy());
        sell.addOrUpdate(new Day(new Date()), current.getSell());

        tsc.addSeries(buy);
        tsc.addSeries(sell);

        return tsc;
    }

    public static JFreeChart getChart(User user, Integer assetId, Integer daysOffset, String name) throws Exception {
        XYDataset dataset = AssetCostHistoryChartGenerator.getAssetCostDataset(user, assetId, daysOffset);
        
        if (dataset==null) {
            return null;
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(name,
                                                              "Time line",
                                                              "Costs",
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
