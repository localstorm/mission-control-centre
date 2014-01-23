package org.localstorm.mcc.ejb.people.impl;

import java.util.ArrayList;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.PersonToGroup;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.entity.AttributeType;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.NullResultGuard;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class PersonManagerBean extends PeopleStatelessBean implements PersonManagerLocal
{
    public PersonManagerBean() {
        
    }

    @Override
    public Collection<Person> convertPersonIdToReferences(Collection<Integer> ids)
    {
        // Too primitive. Memcached!!!
        Collection<Person> res = new ArrayList<Person>(ids.size());
        for (Integer id: ids) {
            res.add(em.getReference(Person.class, id));
        }
        return res;
    }



    @Override
    @SuppressWarnings("unchecked")
    public Collection<Person> getPersons(MailList ml)
    {
        Query q = em.createNamedQuery(PersonToMailList.Queries.FIND_PERSONS_BY_ML);
        q.setParameter(PersonToMailList.Properties.MAIL_LIST, ml);

        return (Collection<Person>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<AttributeType> getAllAttributeTypes() {
        Query q = em.createNamedQuery(AttributeType.Queries.FIND_ALL);
        return (Collection<AttributeType>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Attribute> getEmailAttributes(Person p)
    {
        Query q = em.createNamedQuery(Attribute.Queries.FIND_EMAILS_BY_PERSON);
        q.setParameter(Attribute.Properties.PERSON, p);

        return (Collection<Attribute>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Attribute> getAttributes(Person p) {
        Query q = em.createNamedQuery(Attribute.Queries.FIND_BY_PERSON);
        q.setParameter(Attribute.Properties.PERSON, p);

        return (Collection<Attribute>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PersonGroup> getGroups(User user) {
        Query q = em.createNamedQuery(PersonGroup.Queries.FIND_BY_OWNER);
        q.setParameter(PersonGroup.Properties.OWNER, user);

        return (Collection<PersonGroup>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Person> getPersons(PersonGroup g) {
        Query q = em.createNamedQuery(Person.Queries.FIND_BY_GROUP);
        q.setParameter(Person.Properties.GROUP, g);

        return (Collection<Person>) q.getResultList();
    }

    @Override
    public PersonGroup getGroup(Person p) {
        Query q = em.createNamedQuery(PersonToGroup.Queries.FIND_GROUP_BY_PERSON);
        q.setParameter(PersonToGroup.Properties.PERSON, p);

        return (PersonGroup) q.getSingleResult();
    }

    @Override
    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException {
        
        PersonGroup pg = em.find(PersonGroup.class, groupId);
        return NullResultGuard.checkNotNull(pg);
    }

    @Override
    public Person findPerson(Integer personId) throws ObjectNotFoundException {
        Person p = em.find(Person.class, personId);
        return NullResultGuard.checkNotNull(p);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PersonGroup> getArchivedGroups(User user) {
        Query q = em.createNamedQuery(PersonGroup.Queries.FIND_ARCHIVED_BY_OWNER);
        q.setParameter(PersonGroup.Properties.OWNER, user);

        return (Collection<PersonGroup>) q.getResultList();
    }

    @Override
    public void movePersonToGroup(Person p, PersonGroup newGroup)
    {
        Query q = em.createNamedQuery(PersonToGroup.Queries.MOVE_PERSON_TO_GROUP);
        
        q.setParameter(PersonToGroup.Properties.PERSON, p);
        q.setParameter(PersonToGroup.Properties.GROUP, newGroup);

        q.executeUpdate();
    }

    @Override
    public void remove(PersonGroup g) {
        g = em.getReference(PersonGroup.class, g.getId());

        Query q = em.createNamedQuery(PersonGroup.Queries.DELETE_ORPHAN_PERSONS);
        q.setParameter(PersonGroup.Properties.GROUP, g);
        q.executeUpdate();

        em.remove(g);
    }

    @Override
    public void remove(Attribute a) {
        a = em.getReference(Attribute.class, a.getId());
        em.remove(a);
    }

    @Override
    public void remove(Person a) {
        a = em.getReference(Person.class, a.getId());
        em.remove(a);
    }

    @Override
    public Attribute findAttribute(int attributeId) throws ObjectNotFoundException {
        Attribute a = em.find(Attribute.class, attributeId);
        return NullResultGuard.checkNotNull(a);
    }

    @Override
    public void create(Person p, PersonGroup g) {
        em.persist(p);
        em.persist(new PersonToGroup(p, g));
    }

    @Override
    public void create(PersonGroup g) {
        em.persist(g);
    }

    @Override
    public void update(PersonGroup g) {
        em.merge(g);
    }

    @Override
    public void update(Person p) {
        em.merge(p);
    }

    @Override
    public void create(Attribute attribute, Person p) {
        attribute.setPerson(p);
        em.persist(attribute);
    }

    @Override
    public AttributeType findAttributeType(Integer typeId) throws ObjectNotFoundException {
        AttributeType t = em.find(AttributeType.class, typeId);
        return NullResultGuard.checkNotNull(t);
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
