package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.gtd.GtdSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/AddContext")
public class ContextAddActionBean extends ContextsEditActionBean {

    @Validate( required=true )
    private String name;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }
    
    //Adding context
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    @DefaultHandler
    @Logged
    public Resolution addContext() throws Exception {
        User user = super.getUser();

        Context ctx = new Context(this.getName(), user);

        super.getContextManager().create(ctx);

        SessionUtil.clear(super.getSession(), GtdSessionKeys.CONTEXTS);

        return new RedirectResolution( ContextsEditActionBean.class );
    }
    
}