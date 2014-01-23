package org.localstorm.mcc.ejb.people.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.localstorm.mcc.ejb.Pair;

/**
 *
 * @author Alexey Kuznetsov
 */
public class PregeneratedMailList implements Serializable
{
    private Collection<Person> noEmail;
    private Collection<Pair<Person, Attribute>> resolved;
    private Collection<Pair<Person, Collection<Attribute>>> manyEmails;
    private static final long serialVersionUID = -2548696795780126762L;

    public PregeneratedMailList()
    {
        noEmail    = new LinkedList<Person>();
        resolved   = new LinkedList<Pair<Person, Attribute>>();
        manyEmails = new LinkedList<Pair<Person, Collection<Attribute>>>();
    }

    public void addManyEmailPerson(Person p, Collection<Attribute> attrs)
    {
        manyEmails.add(new Pair<Person, Collection<Attribute>>(p, attrs));
    }

    public void addNoEmailPerson(Person p)
    {
        noEmail.add(p);
    }

    public void addResolvedPerson(Person p, Attribute next)
    {
        resolved.add(new Pair<Person, Attribute>(p, next));
    }

    public boolean isReady()
    {
        return noEmail.isEmpty() && manyEmails.isEmpty();
    }

    public boolean isPartiallyReady()
    {
        return manyEmails.isEmpty();
    }

    public Collection<Pair<Person, Collection<Attribute>>> getManyEmails()
    {
        return manyEmails;
    }

    public Collection<Person> getNoEmail()
    {
        return noEmail;
    }

    public Collection<Pair<Person, Attribute>> getResolved()
    {
        return resolved;
    }

    public void resolveMultiEmail(Person person, Attribute attribute)
    {
        Integer pid = person.getId();
        for (Iterator<Pair<Person, Collection<Attribute>>> it =manyEmails.iterator(); it.hasNext(); ) {
            Pair<Person, Collection<Attribute>> pair = it.next();
            if (pair.getFirst().getId().equals(pid)) {
                it.remove();
                resolved.add(new Pair<Person, Attribute>(pair.getFirst(), attribute));
                break;
            }
        }
    }

    public void resolveUnresolvedEmail(Person person, Attribute attr)
    {
        Integer pid = person.getId();
        for (Iterator<Person> it= noEmail.iterator(); it.hasNext(); ) {
            Person p = it.next();
            if (p.getId().equals(pid)) {
                it.remove();
                resolved.add(new Pair<Person, Attribute>(person, attr));
                break;
            }
        }
    }
    
}
