/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author localstorm
 */
public class QueriesLoader {
    

    private final Map<String, String> queries;

    private QueriesLoader() {
        this.queries = new HashMap<String, String>();

        for (String res : GTD_QUERIES_RESOURCES) {
            InputStream is = QueriesLoader.class.getResourceAsStream(res);
            if (is!=null) {
                this.loadResource(is, res);
            } else {
                log.warn("Resource ["+res+"] not found!");
            }
        }
    }

    public static QueriesLoader getInstance() {
        return QueriesLoader.loader;
    }

    public String getQuery(String key) {
        return this.queries.get(key);
    }

    private void loadResource(InputStream is, String res) {
        try
        {
            this.queries.put(res, IOUtils.toString(is));
        } catch(IOException e) {
            log.error(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static final String GTD_DASHBOARD_REPORT_P1 = "/META-INF/gtd-dash-rpt1.sql";
    public static final String GTD_DASHBOARD_REPORT_P2 = "/META-INF/gtd-dash-rpt2.sql";
    public static final String PPL_DASHBOARD_REPORT = "/META-INF/people-dashboard-report.sql";
    public static final String FIRED_HINTS_REPORT   = "/META-INF/fired-hints-report.sql";

    private static final String[] GTD_QUERIES_RESOURCES = new String[] {
        GTD_DASHBOARD_REPORT_P1,
        GTD_DASHBOARD_REPORT_P2,
        PPL_DASHBOARD_REPORT,
        FIRED_HINTS_REPORT,
    };

    private static final QueriesLoader loader = new QueriesLoader();

    private static final Logger log = Logger.getLogger(QueriesLoader.class);
}
