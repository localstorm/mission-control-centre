package org.localstorm.mcc.web.cashflow.charting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author localstorm
 */
public class AssetsLocationChartGenerator {

    public static final String NO_NAME = "Other";

    public static JFreeChart getChart(User user, String title, String clazz) {

        JFreeChart chart = ChartFactory.createPieChart3D(title,
                getWealthDataset(user, clazz),
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

    private static PieDataset getWealthDataset(User user, String clazz) {

        OperationManager om = ContextLookup.lookup(OperationManager.class, OperationManager.BEAN_NAME);
        AssetManager am = ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);

        Collection<Asset> assets = am.getAssets(user);
        ArrayList<Asset> assetsList = new ArrayList<Asset>(WrapUtil.wrapAssets(assets, om));

        DefaultPieDataset result = new DefaultPieDataset();
        Map<String, BigDecimal> summator = new LinkedHashMap<String, BigDecimal>();

        Collections.sort(assetsList, new AssetNameComparator());
        clazz = normalize(clazz);

        for (Asset a : assetsList) {
            AssetWrapper aw = (AssetWrapper) a;

            if (aw.getValuable().isDebt()){
                continue;
            }

            String assetClass = normalize(aw.getAssetClass());
            if (clazz.equals(assetClass)) {
                BigDecimal nw = aw.getNetWealth();
                BigDecimal hundred = new BigDecimal(100);
                BigDecimal nwRounded = nw.multiply(hundred);
                nwRounded = (new BigDecimal(nwRounded.longValue())).divide(hundred);

                sum(summator, aw.getName(), nwRounded);
            }
        }

        for (Map.Entry<String, BigDecimal> en : summator.entrySet()) {
            BigDecimal sum = en.getValue();
            result.setValue(en.getKey() + " = " + sum.toPlainString(), sum);
        }

        return result;

    }

    private static String normalize(String clazz) {
        return (clazz == null || clazz.trim().length() == 0) ? NO_NAME : clazz.trim();
    }

    private static void sum(Map<String, BigDecimal> summator, String name, BigDecimal nwRounded) {
        name= (name==null) ? NO_NAME : name;
        BigDecimal val = summator.get(name);
        val = (val == null) ? BigDecimal.ZERO : val;
        summator.put(name, val.add(nwRounded));
    }

    private static class AssetNameComparator implements Comparator<Asset> {
        @Override
        public int compare(Asset a1, Asset a2) {
            String an1 = a1.getName();
            String an2 = a2.getName();
            if (an1 == null || an1.trim().length()==0) {
                an1 = "N/A";
            }
            if (an2 == null || an2.trim().length()==0) {
                an2 = "N/A";
            }
            return an1.compareTo(an2);
        }
    }
}
