package org.localstorm.mcc.web.people.actions;


import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.AttributeType;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by person id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/AddPersonAttribute")
public class PersonAttributeAddActionBean extends PersonViewActionBean
{
    @Validate(required=true)
    private Integer typeId;

    @Validate(required=true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (this.value!=null) {
            this.value = this.value.trim();
        }
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());

        AttributeType type = pm.findAttributeType(this.getTypeId());

        pm.create(new Attribute(p, type, this.getValue()), p);

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
