package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by group-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/ToggleStatePersonGroup")
public class PersonGroupToggleStateActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id) {
        this.groupId = id;
    }
    
    @DefaultHandler
    @Logged
    public Resolution toggle() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        
        PersonGroup g = pm.findGroup(this.getGroupId());
        g.setArchived( !g.isArchived() );
        pm.update(g);
        
        SessionUtil.clear(getSession(), PeopleSessionKeys.PERSON_GROUPS);
        SessionUtil.clear(getSession(), PeopleSessionKeys.ARCHIVE_PERSON_GROUPS);
        return new RedirectResolution(PersonGroupsEditActionBean.class);
    }
}
