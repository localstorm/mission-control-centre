package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.Collection;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/ViewInbox")
public class InboxViewActionBean extends GtdBaseActionBean
{
    private Collection<? extends InboxEntry> inbox;

    public Collection<? extends InboxEntry> getInbox()
    {
        return inbox;
    }

    public void setInbox(Collection<? extends InboxEntry> inbox)
    {
        this.inbox = inbox;
    }
    
    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        InboxManager im   = super.getInboxManager();
        List<InboxEntry> ibx = im.getInboxEntries(super.getUser());
        this.setInbox(WrapUtil.genWrappers(ibx));
        return new ForwardResolution(Views.VIEW_INBOX);
    }
    
}
