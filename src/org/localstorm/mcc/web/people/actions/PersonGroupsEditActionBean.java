package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by nil
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/nil/EditPersonGroups")
public class PersonGroupsEditActionBean extends PeopleBaseActionBean {

    private Collection<PersonGroup> result;

    public Collection<PersonGroup> getArchiveGroups() {
        return result;
    }

    public void setArchiveGroups(Collection<PersonGroup> result) {
        this.result = result;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() {
        PersonManager pm = super.getPersonManager();
        result = pm.getArchivedGroups(super.getUser());
        return new ForwardResolution(Views.EDIT_GROUPS);
    }
    
    
}