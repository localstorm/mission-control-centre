package org.localstorm.mcc.ejb.people.impl;

import java.util.Arrays;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.Pair;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class MailListManagerBean extends PeopleStatelessBean implements MailListManagerLocal
{
    public MailListManagerBean() {
        
    }

    @Override
    public MailList find(Integer mailListId)
    {
        return em.find(MailList.class, mailListId);
    }

    @Override
    public Collection<MailList> getJoinableMailLists(Person p, User user)
    {
        Query q = em.createNamedQuery(MailList.Queries.FIND_JOINABLE_MLS);
        q.setParameter(MailList.Properties.OWNER, user);
        q.setParameter(MailList.Properties.PERSON, p);

        return (Collection<MailList>) q.getResultList();
    }

    @Override
    public PregeneratedMailList tryManualResolveBrokenEmails(MailList ml)
    {
        Collection<Person> fromMailList = super.getPersonManager().getPersons(ml);
        PregeneratedMailList pml = this.generateMailList(fromMailList, null);

        Collection<Pair<Person, Collection<Attribute>>> many = pml.getManyEmails();
        Set<Integer> manyEmailedPersons = new HashSet<Integer>();
        for (Pair<Person, Collection<Attribute>> pair: many) {
            manyEmailedPersons.add(pair.getFirst().getId());
        }

        Collection<PersonToMailList> cont = this.getMailListContent(ml);
        for (PersonToMailList p2ml: cont) {
            if (p2ml.getAttribute()!=null && manyEmailedPersons.contains(p2ml.getPerson().getId())) {
                pml.resolveMultiEmail(p2ml.getPerson(), p2ml.getAttribute());
            }
        }

        return pml;
    }

    @Override
    public void repair(MailList ml, PregeneratedMailList pml, User user)
    {
        PersonManager pm = super.getPersonManager();

        Map<Integer, Attribute> resolvedMap = new HashMap<Integer, Attribute>();
        Collection<Pair<Person, Attribute>> resolv = pml.getResolved();
        for (Pair<Person, Attribute> pair: resolv) {
            resolvedMap.put(pair.getFirst().getId(), pair.getSecond());
        }

        Collection<PersonToMailList> cont = this.getMailListContent(ml);
        for (PersonToMailList p2ml : cont) {
            if (p2ml.getAttribute()==null) {

                Attribute attr = resolvedMap.get(p2ml.getPerson().getId());
                if (attr!=null) {
                    p2ml.setAttribute(attr);
                    em.merge(p2ml);
                    continue;
                }

            }
        }
    }

    @Override
    public void tryAutoResolveBrokenEmails(MailList ml)
    {
        PersonManager pm = super.getPersonManager();

        Collection<PersonToMailList> cont = this.getMailListContent(ml);
        for (PersonToMailList p2ml : cont) {
            if (p2ml.getAttribute()==null) {
                Collection<Attribute> emails = pm.getEmailAttributes(p2ml.getPerson());

                if (emails.size()==1) {
                    p2ml.setAttribute(emails.iterator().next());
                    em.persist(p2ml);
                }
            }
        }
    }

    @Override
    public PregeneratedMailList generateMailList(Collection<Person> persons, Integer[] attributes)
    {
        PersonManager pm = super.getPersonManager();
        PregeneratedMailList pml = new PregeneratedMailList();

        Set<Integer> suggested = new HashSet<Integer>();
        if (attributes!=null) {
            suggested.addAll(Arrays.asList(attributes));
        }

    
 outer: for (Person p : persons) {
            Collection<Attribute> attrs = pm.getEmailAttributes(p);
            
            if (attrs.isEmpty()) {
                pml.addNoEmailPerson(p);
                continue;
            }

            if (attrs.size()==1) {
                pml.addResolvedPerson(p, attrs.iterator().next());
                continue;
            }

            for (Attribute attr: attrs)
            {
                if (suggested.contains(attr.getId())) {
                    pml.addResolvedPerson(p, attr);
                    continue outer;
                }
            }

            pml.addManyEmailPerson(p, attrs);
        }

        return pml;
    }

    @Override
    public MailList create(PregeneratedMailList pml, String name, User user)
    {
        if (pml.isReady()) {

            MailList ml = new MailList();
            {
                ml.setName(name);
                ml.setOwner(user);
                ml.setArchived(false);
            }
            em.persist(ml);

            for (Pair<Person, Attribute> mlEntry : pml.getResolved()) {
                PersonToMailList p2ml = new PersonToMailList(mlEntry.getFirst(), ml, mlEntry.getSecond());
                em.persist(p2ml);
            }

            return ml;
            
        } else {
            throw new RuntimeException("PregeneratedMailList is not ready!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<MailList> getMailLists(User u)
    {
        Query q = em.createNamedQuery(MailList.Queries.FIND_MLS_BY_OWNER);
        q.setParameter(MailList.Properties.OWNER, u);

        return (Collection<MailList>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<MailList> getArchivedMailLists(User u)
    {
        Query q = em.createNamedQuery(MailList.Queries.FIND_ARCHIVED_MLS_BY_OWNER);
        q.setParameter(MailList.Properties.OWNER, u);

        return (Collection<MailList>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PersonToMailList> getMailListContent(MailList ml)
    {
        Query q = em.createNamedQuery(PersonToMailList.Queries.FIND_P2ML_BY_ML);
        q.setParameter(PersonToMailList.Properties.MAIL_LIST, ml);

        return (Collection<PersonToMailList>) q.getResultList();
    }

    @Override
    public void joinMailList(MailList ml, Person p, Attribute a)
    {
        Collection<Person> persons = super.getPersonManager().getPersons(ml);
        for (Person person: persons)
        {
            if (person.getId().equals(p.getId())) {
                return;
            }
        }
        
        em.persist(new PersonToMailList(p, ml, a));
    }

    @Override
    public void leaveMailList(MailList ml, Person p)
    {
        Query q = em.createNamedQuery(PersonToMailList.Queries.LEAVE_ML);
        q.setParameter(PersonToMailList.Properties.MAIL_LIST, ml);
        q.setParameter(PersonToMailList.Properties.PERSON, p);
        q.executeUpdate();
    }

    @Override
    public void remove(MailList ml)
    {
        em.remove(em.getReference(MailList.class, ml.getId()));
    }

    @Override
    public void update(MailList ml)
    {
        em.merge(ml);
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
