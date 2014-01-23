package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.ListManager;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/RenameList")
public class ListRenameActionBean extends ListViewActionBean
{
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
        ListManager lm = this.getListManager();
        
        GTDList list   = lm.findById(super.getListId());
        list.setName(this.getName());
        lm.update(list);

        GtdClipboard clip = super.getClipboard();
        if (clip.isListInClipboard(list))
        {
            clip.copyList(list);
        }

        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter(ListViewActionBean.IncomingParameters.LIST_ID, super.getListId());
        }
        return rr;
    }
    
    public static interface IncomingParameters {
        public static final String LIST_ID = "listId";
    }
    
}
