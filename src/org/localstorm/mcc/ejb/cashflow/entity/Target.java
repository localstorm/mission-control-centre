package org.localstorm.mcc.ejb.cashflow.entity;

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
@Table(name="TARGETS")
@NamedQueries({
    @NamedQuery(
        name = Target.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM Target o WHERE o.valuable.owner=:owner and o.archived = false"
    )
})
public class Target extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @JoinColumn(name="valuable_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private ValuableObject valuable;

    @Column(name="is_archived", updatable=true, nullable=false )
    private boolean archived;
    private static final long serialVersionUID = -4518360416297538455L;

    public Target() {
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public ValuableObject getValuable() {
        return valuable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValuable(ValuableObject valuable) {
        this.valuable = valuable;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER = "findTargetsByOwner";
    }

    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
}
