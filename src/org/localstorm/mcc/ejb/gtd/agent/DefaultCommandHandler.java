package org.localstorm.mcc.ejb.gtd.agent;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class DefaultCommandHandler implements CommandHandler
{
    @Override
    public String handle(int uid, String from, String to, String param)
    {
        InboxManager im = ContextLookup.lookup(InboxManager.class, InboxManager.BEAN_NAME);
        UserManager  um = ContextLookup.lookup(UserManager.class,  UserManager.BEAN_NAME);

        User user = um.findById(uid);
        InboxEntry note = new InboxEntry(param, user);
        im.submitNote(note);

        return "accepted.";
    }
}
