package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by person id
 * @author localstorm
 */
@UrlBinding("/actions/ppl/group/person/RemovePerson")
public class PersonRemoveActionBean extends PeopleBaseActionBean {

    @Validate(required=true)
    private Integer personId;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        Person         p = pm.findPerson(this.getPersonId());
        PersonGroup group= pm.getGroup(p);

        PeopleClipboard clip = super.getClipboard();
        clip.pickPerson(p.getId());

        pm.remove(p);

        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncomingParameters.GROUP_ID,
                            group.getId());
        }

        return rr;
    }

}