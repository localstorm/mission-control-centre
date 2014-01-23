package org.localstorm.mcc.web.gtd.actions.wrap;

import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.util.EscapeUtil;

import java.util.Date;

/**
 *
 * @author Alexey Kuznetsov
 */
public class InboxEntryWrapper extends InboxEntry
{
    private InboxEntry entry;
  
    public InboxEntryWrapper(InboxEntry entry) {
        this.entry = entry;
    }

    @Override
    public Integer getId() {
        return entry.getId();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Date getCreation() {
        return entry.getCreation();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getContent() {
        return entry.getContent();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public User getOwner() {
        return entry.getOwner();   
    }

    public String getContentHtmlEscaped() {
        return EscapeUtil.forHTML(entry.getContent());
    }

}