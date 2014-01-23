package org.localstorm.mcc.web.cashflow.charting;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;


/**
 * @author localstorm
 */
public class AssetsStructureChartGenerator {

    public static JFreeChart getChart(User user, String title, boolean byAssetClass) {

        JFreeChart chart = ChartFactory.createPieChart3D(title,
                getWealthDataset(user, byAssetClass),
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

    private static PieDataset getWealthDataset(User user, boolean byAssetClass) {

        OperationManager om = ContextLookup.lookup(OperationManager.class, OperationManager.BEAN_NAME);
        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);

        Collection<Asset> assets = am.getAssets(user);

        assets = WrapUtil.wrapAssets(assets, om);

        DefaultPieDataset result = new DefaultPieDataset();
        Map<String, BigDecimal> summator = new HashMap<String, BigDecimal>();


        for (Asset a : assets) {
            AssetWrapper aw = (AssetWrapper) a;

            if (aw.getValuable().isDebt()){
                continue;
            }

            BigDecimal nw = aw.getNetWealth();
            BigDecimal hundred = new BigDecimal(100);

            BigDecimal nwRounded = nw.multiply(hundred);
            nwRounded = (new BigDecimal(nwRounded.longValue())).divide(hundred);
            if (!byAssetClass) {
                sum(summator, aw.getName(), nwRounded);
            } else {
                sum(summator, aw.getAssetClass(), nwRounded);
            }
        }

        for (Map.Entry<String, BigDecimal> en : summator.entrySet()) {
            BigDecimal sum = en.getValue();
            result.setValue(en.getKey() + " = " + sum.toPlainString(), sum);
        }

        return result;

    }

    private static void sum(Map<String, BigDecimal> summator, String name, BigDecimal nwRounded) {
        name= (name==null) ? "N/A" : name;
        BigDecimal val = summator.get(name);
        val = (val == null) ? BigDecimal.ZERO : val;
        summator.put(name, val.add(nwRounded));
    }
}
