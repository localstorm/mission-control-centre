package org.localstorm.mcc.ejb.people.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author localstorm
 */
public class DashboardReportBean  extends AbstractEntity
{

    private List<PersonWrapper> red;
    private List<PersonWrapper> yellow;
    private List<PersonWrapper> green;
    private static final long serialVersionUID = 7027069440638777578L;

    public DashboardReportBean() {
        red    = new LinkedList<PersonWrapper>();
        yellow = new LinkedList<PersonWrapper>();
        green  = new LinkedList<PersonWrapper>();
    }

    public void addPerson(PersonWrapper pw) {
        if (pw.getRemains()<8) {
            red.add(pw);
            return;
        }
        if (pw.getRemains()<22) {
            yellow.add(pw);
            return;
        }
        if (pw.getRemains()<91) {
            green.add(pw);
        }
    }

    public Collection<PersonWrapper> getRedPersons() {
        return Collections.unmodifiableList(red);
    }

    public Collection<PersonWrapper> getYellowPersons() {
        return Collections.unmodifiableList(yellow);
    }

    public Collection<PersonWrapper> getGreenPersons() {
        return Collections.unmodifiableList(green);
    }


}
