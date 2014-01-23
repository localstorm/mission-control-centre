package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/person/LeaveMailList")
public class MailListLeaveActionBean extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;

    @Validate(required=true)
    private Integer personId;

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception
    {
        PersonManager    pm = super.getPersonManager();
        MailListManager mlm = super.getMailListManager();
        
        MailList ml = mlm.find(this.getMailListId());
        Person p    = pm.findPerson(this.getPersonId());

        mlm.leaveMailList(ml, p);

        RedirectResolution rr = new RedirectResolution(MailListViewActionBean.class);
        {
            rr.addParameter(MailListViewActionBean.IncomingParameters.MAIL_LIST_ID, this.getMailListId());
        }

        return rr;
    }


    public static interface IncomingParameters {
        public static final String MAIL_LIST_ID = "mailListId";
        public static final String PERSON_ID = "personId";
    }

}
