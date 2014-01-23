package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by ml
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/ToggleStateMailList")
public class MailListToggleStateActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int mailListId;

    public int getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(int mailListId)
    {
        this.mailListId = mailListId;
    }

    @DefaultHandler
    @Logged
    public Resolution toggle() throws Exception {
        
        MailListManager mlm = super.getMailListManager();
        
        MailList ml = mlm.find(this.getMailListId());

        ml.setArchived( !ml.isArchived() );
        mlm.update(ml);
        
        SessionUtil.clear(getSession(), PeopleSessionKeys.MAIL_LISTS);
        SessionUtil.clear(getSession(), PeopleSessionKeys.ARCHIVE_MAIL_LISTS);

        return new RedirectResolution(MailListsEditActionBean.class);
    }
}
