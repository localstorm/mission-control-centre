package org.localstorm.mcc.ejb.people;

import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.entity.AttributeType;
import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public interface PersonManager
{
    public static final String BEAN_NAME="PersonManagerBean";

    public Collection<Person> convertPersonIdToReferences(Collection<Integer> ids);

    // PersonGroups
    
    public void create(PersonGroup g);

    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException;

    public Collection<PersonGroup> getGroups(User user);

    public PersonGroup getGroup(Person p);

    public Collection<PersonGroup> getArchivedGroups(User user);

    public void remove(PersonGroup g);

    public void update(PersonGroup g);

    // Persons

    public void create(Person p, PersonGroup g);

    public Person findPerson(Integer personId) throws ObjectNotFoundException;

    public Collection<Person> getPersons(PersonGroup g);

    public Collection<Person> getPersons(MailList ml);

    public void movePersonToGroup(Person p, PersonGroup findGroup);

    public void remove(Person p);

    public void update(Person p);

    // Attributes

    public Attribute findAttribute(int attributeId)  throws ObjectNotFoundException;

    public AttributeType findAttributeType(Integer typeId) throws ObjectNotFoundException;
    
    public Collection<AttributeType> getAllAttributeTypes();

    public Collection<Attribute> getAttributes(Person p);

    public Collection<Attribute> getEmailAttributes(Person p);
    
    public void create(Attribute attribute, Person p);

    public void remove(Attribute a);
    
}
