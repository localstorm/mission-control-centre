package org.localstorm.mcc.web.cashflow.filter.security;

import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CashflowAssetSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String ASSET_ID_PARAM = "assetId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String aid = req.getParameter(ASSET_ID_PARAM);

        if (aid!=null)
        {
            Integer assetId = Integer.parseInt(aid);
            SecurityUtil.checkAssetSecurity(req.getSession(true), assetId, user, log);
        }
    }

}