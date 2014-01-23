package org.localstorm.mcc.web.people.actions;


import java.util.ArrayList;
import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.AttributeType;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.people.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by personId
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/ViewPerson")
public class PersonViewActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int personId;

    private boolean needEmail;

    private Person person;

    private Collection<Attribute> attributes;

    private Collection<AttributeType> attributeTypes;

    private Collection<MailList> joinableMailLists;

    private Collection<Attribute> emails;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collection<Attribute> getEmails()
    {
        return emails;
    }

    public void setEmails(Collection<Attribute> emails)
    {
        this.emails = emails;
    }

    public Collection<MailList> getJoinableMailLists()
    {
        return joinableMailLists;
    }

    public void setJoinableMailLists(Collection<MailList> joinableMailLists)
    {
        this.joinableMailLists = joinableMailLists;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public boolean isNeedEmail()
    {
        return needEmail;
    }

    public void setNeedEmail(boolean needEmail)
    {
        this.needEmail = needEmail;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Collection<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(Collection<AttributeType> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        MailListManager mlm = super.getMailListManager();
        PersonManager   pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());
        PersonGroup group = pm.getGroup(p);
        
        Collection<Attribute> attrs  = pm.getAttributes(p);
        Collection<Attribute> mails = new ArrayList<Attribute>(5);

        for (Attribute attr: attrs) {
            if (attr.getType().isEmail())
            {
                mails.add(attr);
            }
        }

        this.setEmails(mails);
        this.setAttributes(attrs);
        this.setPerson(WrapUtil.genWrapper(p, group));
        this.setAttributeTypes(pm.getAllAttributeTypes());
        this.setJoinableMailLists(mlm.getJoinableMailLists(p, super.getUser()));


        ReturnPageBean rpb = new ReturnPageBean(Pages.PERSON_VIEW.toString());
        {
            rpb.setParam(IncomingParameters.PERSON_ID, Integer.toString(this.personId));
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.VIEW_PERSON);
    }
    
    public static interface IncomingParameters {
        public static final String PERSON_ID = "personId";
        public static final String NEED_EMAIL= "needEmail";
    }
}
