package org.localstorm.mcc.ejb.people.entity;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name="ATTRIBUTES")
@NamedQueries({
    @NamedQuery(
        name = Attribute.Queries.FIND_BY_PERSON,
        query= "SELECT o FROM Attribute o WHERE o.person=:person ORDER BY o.type.name"
    ),
    @NamedQuery(
        name = Attribute.Queries.FIND_EMAILS_BY_PERSON,
        query= "SELECT o FROM Attribute o WHERE o.person=:person AND o.type IN (SELECT t FROM AttributeType t WHERE t.email=true)"
    )
})
public class Attribute extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="val", unique=false, updatable=true, nullable=false )
    private String val;
    
    @JoinColumn(name="person_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Person person;

    @JoinColumn(name="type_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private AttributeType type;
    private static final long serialVersionUID = -1586053884724533456L;

    public Attribute() {
    }


    public Attribute(Person p, AttributeType type, String value) {
        this.person = p;
        this.type = type;
        this.val = value;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Person getPerson() {
        return person;
    }

    public AttributeType getType() {
        return type;
    }

    public String getVal() {
        return val;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public void setVal(String value) {
        this.val = value;
    }

    public static interface Queries
    {
        public static final String FIND_BY_PERSON = "findAttrByPerson";
        public static final String FIND_EMAILS_BY_PERSON = "findEmailAttrsByPerson";
    }

    public static interface Properties
    {
        public static final String PERSON = "person";
    }

}
