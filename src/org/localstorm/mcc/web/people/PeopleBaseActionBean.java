package org.localstorm.mcc.web.people;

import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author localstorm
 */
public class PeopleBaseActionBean extends BaseActionBean {

    public MailListManager getMailListManager()
    {
        return ContextLookup.lookup(MailListManager.class, MailListManager.BEAN_NAME);
    }

    public PersonManager getPersonManager() {
        return ContextLookup.lookup(PersonManager.class, PersonManager.BEAN_NAME);
    }

    protected PeopleClipboard getClipboard()
    {
        HttpSession sess = this.getSession();
        PeopleClipboard clip = (PeopleClipboard) SessionUtil.getValue(sess, PeopleSessionKeys.PEOPLE_CLIPBOARD);
        if (clip==null) {
            clip = new PeopleClipboard();
            SessionUtil.fill(sess, PeopleSessionKeys.PEOPLE_CLIPBOARD, clip);
        }

        return clip;
    }


}
