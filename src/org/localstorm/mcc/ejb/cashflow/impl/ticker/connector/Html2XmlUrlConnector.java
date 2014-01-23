package org.localstorm.mcc.ejb.cashflow.impl.ticker.connector;

import org.cyberneko.html.parsers.DOMParser;
import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.PriceConnector;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.PriceParser;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:14:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Html2XmlUrlConnector implements PriceConnector {
    private String url;
    private Map<String, PriceXPath> tickets;
    private PriceParser priceParser;

    public Html2XmlUrlConnector(String url, Map<String, PriceXPath> tickets, PriceParser priceParser) {
        this.url = url;
        this.tickets = tickets;
        this.priceParser = priceParser;
    }

    public Map<String, Price> fetch() throws Exception {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        InputStream is = conn.getInputStream();
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(is));
        Document doc = parser.getDocument();

        Map<String, Price> res = new ConcurrentHashMap<String, Price>();
        for (Map.Entry<String, PriceXPath> e : tickets.entrySet()) {
            String buy = eval(e.getValue().getBuyXPath(), doc);
            String sell = eval(e.getValue().getSellXPath(), doc);
            res.put(e.getKey(), new Price(priceParser.parse(buy), priceParser.parse(sell)));
        }

        return res;
    }

    private static String eval(String ex, Document doc) throws Exception {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(ex);
        String s = (String) expr.evaluate(doc, XPathConstants.STRING);
        return s.trim();
    }
}
