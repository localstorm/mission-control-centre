package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.gtd.GtdSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @secure-by object Id parameter, context Id parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ctx/obj/UpdateRefObj")
public class RefObjUpdateActionBean extends RefObjViewActionBean
{
    @Validate( required=true )
    private String name;

    @Validate( required=true )
    private Integer contextId;

    public void setName(String name) {
        this.name = name;
    }

    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }

    public String getName() {
        return name;
    }

    public Integer getContextId() {
        return contextId;
    }

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    @Logged
    public Resolution filling() throws Exception {
        
        RefObjectManager rom = super.getRefObjectManager();
        ReferencedObject ro = rom.findById(super.getObjectId());
        Context ctx = super.getContextManager().findById(this.getContextId());

        ro.setName(this.getName());
        ro.setContext(ctx);

        rom.update(ro);
        SessionUtil.clear(this.getSession(), GtdSessionKeys.REFERENCE_OBJECTS);
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncomingParameters.OBJECT_ID, super.getObjectId());
        }

        return rr;
    }

}
