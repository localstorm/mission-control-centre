package org.localstorm.mcc.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.tools.aop.runtime.Logged;

/**
 *
 * @author Alexey Kuznetsov
 */
public class PollingServlet extends HttpServlet 
{
    private static final long serialVersionUID = -7021029001862071061L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    @Logged
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        HttpSession sess = req.getSession();

        if (sess==null || sess.getAttribute(CommonSessionKeys.USER)==null) {
            res.sendError(403);
            res.getWriter().print("Not authorized");
        } else {
            res.sendError(200);
            res.getWriter().print("Ok");
        }
    }
}
