package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by person id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/UnclipPerson")
public class PersonUnclipActionBean extends PeopleBaseActionBean
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

        PeopleClipboard clip = super.getClipboard();
        clip.pickPerson(p.getId());

        return NextDestinationUtil.getRedirection(super.getReturnPageBean());
    }
    
    public static interface IncomingParameters {
        public static final String PERSON_ID = "personId";
    }
}
