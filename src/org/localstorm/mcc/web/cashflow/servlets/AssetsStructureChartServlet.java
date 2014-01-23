package org.localstorm.mcc.web.cashflow.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.cashflow.charting.AssetsStructureChartGenerator;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author localstorm
 */
public class AssetsStructureChartServlet extends HttpServlet
{
    private static final long serialVersionUID = -6194493889278623422L;

    @Override
    @Logged
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession(true);
        User user = (User) SessionUtil.getValue(sess, CashflowSessionKeys.USER);

        if (user==null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String _byAssetClass = req.getParameter("byAssetClass");
        Boolean byAssetClass = (_byAssetClass==null) ? false : Boolean.valueOf(_byAssetClass);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String curDate = sdf.format(new Date());

        String title = null;
        if (byAssetClass) {
            title = "Assets structure by class (" + curDate + ")";
        } else {
            title = "Assets structure details (" + curDate + ")";
        }

        
        JFreeChart chart = AssetsStructureChartGenerator.getChart(user, title, byAssetClass);

        resp.setContentType(Constants.PNG_CONTENT_TYPE);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 640, 640);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
   
}
