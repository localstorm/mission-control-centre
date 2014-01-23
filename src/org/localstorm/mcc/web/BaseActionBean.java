package org.localstorm.mcc.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class BaseActionBean implements ActionBean 
{
    private ActionBeanContext context;
    
    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    
    protected HttpSession getSession()
    {
        return this.getRequest().getSession(true);
    }

    protected HttpServletRequest getRequest() {
        return this.context.getRequest();
    }

    protected UserManager getUserManager() {
        return ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);
    }
    
    protected User getUser() 
    {
        User user = (User)this.getSession().getAttribute(CommonSessionKeys.USER);
            
        if (user==null) {
            throw new NullPointerException("USER IS NULL!");
        }
        
        return user;
    }

    protected ReturnPageBean getReturnPageBean()
    {
        HttpServletRequest req = this.getRequest();
        HttpSession       sess = this.getSession();

        String token = req.getParameter(RequestAttributes.RETURN_PAGE_TOKEN);
        if (token==null) {
            token = (String) req.getAttribute(RequestAttributes.RETURN_PAGE_TOKEN);
        }
        
        if (token!=null) {
            return (ReturnPageBean) SessionUtil.getExpirableValue(sess, CommonSessionKeys.RETURN_PAGE_BEAN_REFIX+token);
        } else {
            return null;
        }
    }

    protected void setReturnPageBean(ReturnPageBean rpb)
    {
        String token = TokenGenerator.getToken();

        HttpSession       sess = this.getSession();
        HttpServletRequest req = this.getRequest();

        req.setAttribute(RequestAttributes.RETURN_PAGE_TOKEN, token);
        SessionUtil.fillExpirable(sess, CommonSessionKeys.RETURN_PAGE_BEAN_REFIX+token, rpb);
    }

    
 }
