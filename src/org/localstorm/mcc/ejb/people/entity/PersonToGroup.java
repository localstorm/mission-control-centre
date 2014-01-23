package org.localstorm.mcc.ejb.people.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSONS_TO_GROUPS")
@NamedQueries({
    @NamedQuery(
        name = PersonToGroup.Queries.FIND_GROUP_BY_PERSON,
        query= "SELECT o.group FROM PersonToGroup o WHERE o.person=:person"
    ),
    @NamedQuery(
        name = PersonToGroup.Queries.MOVE_PERSON_TO_GROUP,
        query= "update PersonToGroup o set o.group = :group where o.person = :person"
    )
})
public class PersonToGroup extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="person_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Person person;

    @JoinColumn(name="group_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private PersonGroup group;
    private static final long serialVersionUID = 2488086926089958019L;

    public PersonToGroup() {
    }

    public PersonToGroup(Person p, PersonGroup g) {
        this.person = p;
        this.group  = g;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Person getPerson() {
        return person;
    }

    public PersonGroup getGroup() {
        return group;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGroup(PersonGroup group) {
        this.group = group;
    }

    public static interface Queries
    {
        public static final String FIND_GROUP_BY_PERSON = "findPGroupByPerson";
        public static final String MOVE_PERSON_TO_GROUP = "movePersonToGroup";
    }

    public static interface Properties
    {
        public static final String PERSON = "person";
        public static final String GROUP  = "group";
    }

}
