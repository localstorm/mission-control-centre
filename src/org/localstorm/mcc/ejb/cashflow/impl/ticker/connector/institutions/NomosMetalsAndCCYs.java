package org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.institutions;

import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.Html2XmlUrlConnector;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.PriceXPath;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.i18n.RussianPriceParser;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class NomosMetalsAndCCYs extends Html2XmlUrlConnector {

    private final static String URL = "http://www.nomos.ru/personal/rates/";
    private static final Map<String, PriceXPath> TICKETS = new HashMap<String, PriceXPath>();

    static {
        TICKETS.put("NOMOS/USD", new PriceXPath("//DIV[@id='sell_USD']/text()",
                "//DIV[@id='buy_USD']/text()"));

        TICKETS.put("NOMOS/EUR", new PriceXPath("//DIV[@id='sell_EUR']/text()",
                "//DIV[@id='buy_EUR']/text()"));

        TICKETS.put("NOMOS/GLD", new PriceXPath("//DIV[@id='sell_Au']/text()",
                "//DIV[@id='buy_Au']/text()"));

        TICKETS.put("NOMOS/SLVR", new PriceXPath("//DIV[@id='sell_Ag']/text()",
                "//DIV[@id='buy_Ag']/text()"));

        TICKETS.put("NOMOS/PT", new PriceXPath("//DIV[@id='sell_Pt']/text()",
                "//DIV[@id='buy_Pt']/text()"));

        TICKETS.put("NOMOS/PLD", new PriceXPath("//DIV[@id='sell_Pd']/text()",
                "//DIV[@id='buy_Pd']/text()"));
    }

    public NomosMetalsAndCCYs() {
        super(URL, TICKETS, new RussianPriceParser());
    }

}