package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by nil
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/nil/EditMailLists")
public class MailListsEditActionBean extends PeopleBaseActionBean {

    private Collection<MailList> result;

    public Collection<MailList> getArchiveMailLists() {
        return result;
    }

    public void setArchiveMailLists(Collection<MailList> result) {
        this.result = result;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() {
        MailListManager mlm = super.getMailListManager();
        result = mlm.getArchivedMailLists(super.getUser());

        return new ForwardResolution(Views.EDIT_MAIL_LISTS);
    }
    
    
}