package org.localstorm.mcc.web.people.actions;


import java.text.SimpleDateFormat;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.mcc.web.util.DateUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by person-id, group-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/UpdatePerson")
public class PersonUpdateActionBean extends PersonViewActionBean
{
    @Validate(required=true)
    private String firstName;

    @Validate(required=true)
    private Integer groupId;

    private String lastName;

    private String patronymicName;

    private String birthDate;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public void setPatronymicName(String pname) {
        this.patronymicName = pname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() ) {
            super.getRequest().setAttribute("updateForm", Boolean.TRUE);
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    @Logged
    public Resolution filling() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());
        {
            p.setPatronymicName(this.getPatronymicName());
            p.setName(this.getFirstName());
            p.setLastName(this.getLastName());
            p.setBirthDate(DateUtil.parse(this.getBirthDate(), sdf));
        }

        PeopleClipboard clip = super.getClipboard();
        if (clip.isPersonInClipboard(p))
        {
            clip.copyPerson(p);
        }

        pm.update(p);

        PersonGroup pg = pm.getGroup(p);
        if (!pg.getId().equals(this.getGroupId()))
        {
            pm.movePersonToGroup(p, pm.findGroup(this.getGroupId()));
        }

        RedirectResolution rr = new RedirectResolution(PersonViewActionBean.class);
        {
            rr.addParameter(PersonViewActionBean.IncomingParameters.PERSON_ID, this.getPersonId());
        }

        return rr;
    }
    
    public static interface IncomingParameters {
        public static final String PERSON_ID = "personId";
    }


}
