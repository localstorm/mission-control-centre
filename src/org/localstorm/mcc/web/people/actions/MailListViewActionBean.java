package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import java.util.LinkedList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/ViewMailList")
public class MailListViewActionBean extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;

    private Collection<PersonToMailList> mailListContent;

    private Collection<Person> expired;

    private MailList mailList;

    public MailList getMailList()
    {
        return mailList;
    }

    public void setMailList(MailList mailList)
    {
        this.mailList = mailList;
    }

    public Collection<Person> getExpired()
    {
        return expired;
    }

    public void setExpired(Collection<Person> expired)
    {
        this.expired = expired;
    }

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    public void setMailListContent(Collection<PersonToMailList> mailListContent)
    {
        this.mailListContent = mailListContent;
    }

    public Collection<PersonToMailList> getMailListContent()
    {
        return mailListContent;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        
        MailListManager mlm = super.getMailListManager();
        MailList ml = mlm.find(this.getMailListId());
        
        Collection<PersonToMailList> cont = mlm.getMailListContent(ml);
        Collection<Person> exp = new LinkedList<Person>();
        
        for (PersonToMailList p: cont) {
            if (p.getAttribute()==null) {
                exp.add(p.getPerson());
            }
        }

        this.setMailList(ml);
        this.setMailListContent(cont);
        this.setExpired(exp);

        ReturnPageBean rpb = new ReturnPageBean(Pages.MAIL_LIST_VIEW.toString());
        {
            rpb.setParam(IncomingParameters.MAIL_LIST_ID, this.getMailListId().toString());
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.VIEW_MAIL_LIST);
    }


    public static interface IncomingParameters {
        public static final String MAIL_LIST_ID = "mailListId";
    }

}
