package org.localstorm.mcc.web;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collection;
import javax.servlet.http.*;
import javax.servlet.*;
import org.localstorm.tools.aop.runtime.CallLogger;
import org.localstorm.tools.aop.runtime.CallProcessor;
import org.localstorm.tools.aop.runtime.HashMapUniversalCallProcessor;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CallTimingServlet extends HttpServlet
{
    private static final long serialVersionUID = 3610120234518462647L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {

            ServletOutputStream sos = res.getOutputStream();
        
            CallProcessor callProcessor = CallLogger.getInstance().getProcessor();
            if (callProcessor instanceof HashMapUniversalCallProcessor) {
                HashMapUniversalCallProcessor hm = (HashMapUniversalCallProcessor) callProcessor;

                Collection<HashMapUniversalCallProcessor.CallInfo> calls = hm.getStatistics();
                for (HashMapUniversalCallProcessor.CallInfo ci: calls) {
                    sos.println(ci.toString());
                }

            } else {
                sos.print("Inspect your log files, please. Runtime information is not available.");
            }
            
            sos.flush();

        } catch(IOException e) {
            throw new ServerException(e.getMessage(), e);
        }
    }


}
