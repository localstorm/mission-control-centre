package org.localstorm.mcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.tools.aop.runtime.Logged;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthServlet extends HttpServlet 
{
    private static final long serialVersionUID = 3907696271015680047L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    @Logged
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String login = req.getParameter(CGIParams.AUTH_LOGIN);
        String pwd   = req.getParameter(CGIParams.AUTH_PASSWORD);

        login = (login!=null) ? login : "";
        pwd   =  (pwd!=null)  ? pwd   : "";
                
        UserManager um = ContextLookup.lookup(UserManager.class, 
                                              UserManager.BEAN_NAME);
        User u = um.login(login, pwd);
        HttpSession sess = req.getSession(true);

        if (u!=null) {
            sess.setAttribute(CommonSessionKeys.USER, u);

            ReturnPageBean rpb = (ReturnPageBean) sess.getAttribute(CommonSessionKeys.ORIGINAL_REQUEST);
            
            if (rpb==null) {
                res.sendRedirect(Views.DASH_REDIRECT);
            } else {
                res.sendRedirect(buildUrl(rpb));
            }
        } else {
            req.setAttribute(RequestAttributes.INVALID_LOGIN_OR_PASSWORD, Boolean.TRUE);
            req.getRequestDispatcher(Views.LOGIN).forward(req, res);
        }
    }

    private String buildUrl(ReturnPageBean rpb) throws UnsupportedEncodingException
    {
        StringBuilder            url = new StringBuilder(rpb.getPageToken());
        Map<String, String[]> params = rpb.getParams();

        if (!params.isEmpty()) {
            url.append('?');

            for (Iterator<Map.Entry<String, String[]>> eit = params.entrySet().iterator(); eit.hasNext(); ) {
                Map.Entry<String, String[]> entry = eit.next();
                String    paramName = entry.getKey();
                List<String> values = Arrays.asList(entry.getValue());

                for (Iterator<String> vit = values.iterator(); vit.hasNext(); ) {
                    url.append(paramName);
                    url.append('=');
                    url.append(URLEncoder.encode(vit.next(), "UTF-8"));
                    if (vit.hasNext() || eit.hasNext()) {
                        url.append("&");
                    }
                }
            }
        }

        return url.toString();
    }

    
}
