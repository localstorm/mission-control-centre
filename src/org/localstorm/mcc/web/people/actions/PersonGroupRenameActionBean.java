package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by group-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/RenamePersonGroup")
public class PersonGroupRenameActionBean extends PersonGroupViewActionBean {

    @Validate( required=true )
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        this.getContext().getRequest().setAttribute("renameForm", Boolean.TRUE);
        
        if ( this.getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    @Logged
    public Resolution filling() throws Exception {
        PersonManager pm = super.getPersonManager();
        
        PersonGroup g = pm.findGroup(super.getGroupId());

        g.setName(this.getName());
        pm.update(g);
        
        SessionUtil.clear(getSession(), PeopleSessionKeys.PERSON_GROUPS);
        SessionUtil.clear(getSession(), PeopleSessionKeys.ARCHIVE_PERSON_GROUPS);
        
        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncomingParameters.GROUP_ID,
                            super.getGroupId());
        }
        return rr;
    }
    
}
