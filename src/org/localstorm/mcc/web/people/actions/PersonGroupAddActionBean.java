package org.localstorm.mcc.web.people.actions;

import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @secure-by nil
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/nil/AddPersonGroup")
public class PersonGroupAddActionBean extends PersonGroupsEditActionBean {

    @Validate( required=true )
    private String name;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    @DefaultHandler
    @Logged
    public Resolution addPg() throws Exception {
        User user = super.getUser();

        PersonGroup g = new PersonGroup(this.getName());
        g.setOwner(user);
        g.setArchived(false);

        super.getPersonManager().create(g);

        SessionUtil.clear(super.getSession(), PeopleSessionKeys.PERSON_GROUPS);
   
        return new RedirectResolution( PersonGroupsEditActionBean.class );
    }
    
}