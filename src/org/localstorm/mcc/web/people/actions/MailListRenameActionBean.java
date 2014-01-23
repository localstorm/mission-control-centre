package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by ml
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/RenameMailList")
public class MailListRenameActionBean extends MailListViewActionBean {

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

    
    @Override
    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        MailListManager mlm = super.getMailListManager();
        
        MailList ml = mlm.find(super.getMailListId());

        ml.setName(this.getName());
        mlm.update(ml);
        
        SessionUtil.clear(getSession(), PeopleSessionKeys.MAIL_LISTS);
        SessionUtil.clear(getSession(), PeopleSessionKeys.ARCHIVE_MAIL_LISTS);
        
        RedirectResolution rr = new RedirectResolution(MailListViewActionBean.class);
        {
            rr.addParameter(MailListViewActionBean.IncomingParameters.MAIL_LIST_ID,
                            super.getMailListId());
        }
        return rr;
    }
    
}
