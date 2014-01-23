package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by person-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/ClipPerson")
public class PersonClipActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        PersonManager  pm = super.getPersonManager();
        Person          p = pm.findPerson(this.getPersonId());
        PersonGroup group = pm.getGroup(p);

        Collection<Attribute> emails = pm.getEmailAttributes(p);

        if (emails.isEmpty()) {
            RedirectResolution rr = new RedirectResolution(PersonViewActionBean.class);
            {
                rr.addParameter(PersonViewActionBean.IncomingParameters.PERSON_ID, this.getPersonId());
                rr.addParameter(PersonViewActionBean.IncomingParameters.NEED_EMAIL, Boolean.TRUE);
            }

            return rr;
        } else {

            PeopleClipboard clip = super.getClipboard();
            clip.copyPerson(p);

            RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
            {
                rr.addParameter(PersonGroupViewActionBean.IncomingParameters.GROUP_ID, group.getId());
            }

            return rr;
        }
    }
    
    public static interface IncomingParameters {
        public static final String PERSON_ID = "personId";
    }
}
