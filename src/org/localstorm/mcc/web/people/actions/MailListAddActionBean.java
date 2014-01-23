package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.WebUtil;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.people.RequestAttributes;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;
import org.localstorm.mcc.ejb.people.entity.MailList;

/**
 * TODO: Special security check!!
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/special/AddMailList")
public class MailListAddActionBean extends PeopleBaseActionBean
{
    private String manyEmailsPersonIds;
    private String resolvedPersonIds;

    private Integer[] attributes;

    @Validate(required=true)
    private String name;

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            PeopleClipboard clip = super.getClipboard();
            MailListManager mlm = super.getMailListManager();
            PregeneratedMailList pml = mlm.generateMailList(clip.getPersons(), null);
            super.getRequest().setAttribute(RequestAttributes.PREGENERATED_MAIL_LIST, pml);
        }
    }

    public Integer[] getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Integer[] attributes)
    {
        this.attributes = attributes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

        Collection<Integer> resolved = WebUtil.stringToIds(resolvedPersonIds);
        Collection<Integer> manyEmails = WebUtil.stringToIds(manyEmailsPersonIds);

        Collection<Person> resolvedPersons = pm.convertPersonIdToReferences(resolved);
        Collection<Person> manyEmailsPersons = pm.convertPersonIdToReferences(manyEmails);

        resolvedPersons.addAll(manyEmailsPersons);

        PregeneratedMailList pml = mlm.generateMailList(resolvedPersons, attributes);

        if (pml.isReady())  {
            MailList created = mlm.create(pml, this.getName(), super.getUser());
            HttpSession sess = super.getSession();

            SessionUtil.clear(sess, PeopleSessionKeys.MAIL_LISTS);
            SessionUtil.clear(sess, PeopleSessionKeys.ACCESSIBLE_MAIL_LISTS_MAP);

            super.getClipboard().clearPersons();

            ReturnPageBean rpb = super.getReturnPageBean();
            return NextDestinationUtil.getRedirection(rpb);
        } else {
            super.getRequest().setAttribute(RequestAttributes.PREGENERATED_MAIL_LIST, pml);
            return new ForwardResolution(Views.VIEW_RESOLVE_EMAILS);
        }
    }

}
