package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.RequestAttributes;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 *
 * @author Alexey Kuznetsov
*/
@UrlBinding("/actions/ppl/ml/ManualResolveMailListProblems")
public class MailListProblemsManualResolve extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception
    {
        MailListManager mlm = super.getMailListManager();
        MailList         ml = mlm.find(this.getMailListId());

        PregeneratedMailList pml = mlm.tryManualResolveBrokenEmails(ml);
        super.getRequest().setAttribute(RequestAttributes.PREGENERATED_MAIL_LIST, pml);
        return new ForwardResolution(Views.VIEW_MANUAL_RESOLVE_EMAILS);
    }
}
