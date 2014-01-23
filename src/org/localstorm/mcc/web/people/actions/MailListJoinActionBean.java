package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/person/attr/JoinMailList")
public class MailListJoinActionBean extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;
    
    @Validate(required=true)
    private Integer personId;

    @Validate(required=true)
    private Integer attributeId;

    public void setAttributeId(Integer attributeId)
    {
        this.attributeId = attributeId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }

    public Integer getAttributeId()
    {
        return attributeId;
    }

    public Integer getMailListId()
    {
        return mailListId;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    @DefaultHandler
    @Logged
    public Resolution handle() throws Exception
    {
        MailListManager mlm = super.getMailListManager();
        PersonManager   pm  = super.getPersonManager();

        // We can use references or IDs here
        Person    p = pm.findPerson(personId);
        Attribute a = pm.findAttribute(attributeId);
        MailList ml = mlm.find(mailListId);

        mlm.joinMailList(ml, p, a);

        RedirectResolution rr = new RedirectResolution(PersonViewActionBean.class);
        {
            rr.addParameter(PersonViewActionBean.IncomingParameters.PERSON_ID, this.getPersonId());
        }

        return rr;
    }
}
