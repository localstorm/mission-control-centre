package org.localstorm.mcc.web.people.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WrapUtil 
{

    public static Collection<PersonWrapper> genWrappers(Collection<Person> personList,
                                                      PersonGroup group)
    {
        Collection<PersonWrapper> result = new ArrayList<PersonWrapper>(personList.size());
        for (Person p: personList)
        {
            result.add(new PersonWrapper(p, group));
        }
        
        return result;
    }

    public static PersonWrapper genWrapper(Person person,
                                            PersonGroup group)
    {
        return new PersonWrapper(person, group);
    }

}
