package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ibx/EraseInboxEntry")
public class InboxEntryEraseActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int entryId;

    public int getEntryId()
    {
        return entryId;
    }

    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }

    @DefaultHandler
    @Logged
    public Resolution deletingEntry() throws Exception {
        
        InboxManager  im = super.getInboxManager();

        InboxEntry ie = im.findById(this.getEntryId());
        if (ie!=null) {
            im.removeNote(ie);
        }

        return new RedirectResolution(InboxViewActionBean.class);
    }
}
