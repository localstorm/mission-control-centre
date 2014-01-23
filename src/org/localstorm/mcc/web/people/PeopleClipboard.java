package org.localstorm.mcc.web.people;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.localstorm.mcc.ejb.people.entity.Person;

/**
 * @author Alexey Kuznetsov
 */
public class PeopleClipboard
{
    private Map<Integer, Person> persons;
    
    public PeopleClipboard() {
        persons = new TreeMap<Integer, Person>();
    }

    public synchronized Collection<Person> getPersons()
    {
        ArrayList<Person> res = new ArrayList<Person>(this.persons.values());
        Collections.sort(res, new PeopleComparator());
        return res;
    }

    public boolean isPersonInClipboard(Person p)
    {
        return this.persons.containsKey(p.getId());
    }
    
    public synchronized Person pickPerson(Integer id)
    {
        return this.persons.remove(id);
    }
    
    public synchronized void copyPerson(Person t)
    {
        this.persons.put(t.getId(), t);
    }

    public synchronized void clearPersons()
    {
        this.persons.clear();
    }

    private final static class PeopleComparator implements Comparator<Person>
    {
        @Override
        public int compare(Person o1, Person o2)
        {
            return o1.getShortName().compareTo(o2.getShortName());
        }
    }

}
