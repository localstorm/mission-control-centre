package org.localstorm.mcc.web;

import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.OnwardResolution;

/**
 *
 * @author localstorm
 */
public class ReturnPageBean {
    
    private String pageToken;
    private Map<String, String[]> params;

    public ReturnPageBean(String pageToken) {
        this.pageToken = pageToken;
        this.params = new TreeMap<String, String[]>();
    }

    public ReturnPageBean(HttpServletRequest req) {
        this.pageToken = req.getRequestURI();
        this.params = new TreeMap<String, String[]>();

        @SuppressWarnings("unchecked")
        Map<String, String[]> m = req.getParameterMap();
        this.params.putAll(m);
    }

    public String getPageToken() {
        return pageToken;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public void setParam(String name, String value) {
        this.params.put(name, new String[]{value});
    }

    public void setParam(String name, String[] values) {
        this.params.put(name, values);
    }

    public void appendParams(OnwardResolution res) {
        for (Map.Entry<String, String[]> e: this.params.entrySet()) {
	    System.out.println("PARAM: "+e.getValue()[0]);
            res.addParameter(e.getKey(), (Object[]) e.getValue());
        }
    }

}
