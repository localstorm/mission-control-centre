package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.web.WebUtil;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/special/ManualResolveMailList")
public class MailListManualResolveActionBean extends PeopleBaseActionBean
{
    private String manyEmailsPersonIds;
    private String resolvedPersonIds;

    private Integer[] attributes;
    private Integer mailListId;

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    public Integer[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Integer[] attributes)
    {
        this.attributes = attributes;
    }

    public void setResolvedPersonIds(String resolvedPersonIds)
    {
        this.resolvedPersonIds = resolvedPersonIds;
    }

    public String getResolvedPersonIds()
    {
        return resolvedPersonIds;
    }

    public String getManyEmailsPersonIds()
    {
        return manyEmailsPersonIds;
    }

    public void setManyEmailsPersonIds(String manyEmailsPersonIds)
    {
        this.manyEmailsPersonIds = manyEmailsPersonIds;
    }

    @DefaultHandler
    @Logged
    public Resolution handle() throws Exception
    {
        MailListManager mlm = super.getMailListManager();
        PersonManager   pm  = super.getPersonManager();
        MailList        ml  = mlm.find(this.getMailListId());

        Collection<Integer> resolved = WebUtil.stringToIds(resolvedPersonIds);
        Collection<Integer> manyEmails = WebUtil.stringToIds(manyEmailsPersonIds);

        Collection<Person> resolvedPersons = pm.convertPersonIdToReferences(resolved);
        Collection<Person> manyEmailsPersons = pm.convertPersonIdToReferences(manyEmails);

        resolvedPersons.addAll(manyEmailsPersons);

        PregeneratedMailList pml = mlm.generateMailList(resolvedPersons, attributes);
        mlm.repair(ml, pml, super.getUser());

        RedirectResolution rr = new RedirectResolution(MailListViewActionBean.class);
        {
            rr.addParameter(MailListViewActionBean.IncomingParameters.MAIL_LIST_ID, this.getMailListId());
        }
        return rr;
    }
}
