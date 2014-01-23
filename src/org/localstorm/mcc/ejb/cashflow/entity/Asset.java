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
 * @author localstorm
 */
@Entity
@Table(name="ASSETS")
@NamedQueries({
    @NamedQuery(
        name = Asset.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM Asset o WHERE o.valuable.owner=:owner and o.archived = false ORDER BY o.name"
    ),
    @NamedQuery(
        name = Asset.Queries.FIND_BY_VALUABLE,
        query= "SELECT o FROM Asset o WHERE o.valuable=:valuable"
    ),
    @NamedQuery(
        name = Asset.Queries.FIND_ARCHIVED_BY_OWNER,
        query= "SELECT o FROM Asset o WHERE o.valuable.owner=:owner and o.archived = true ORDER BY o.name"
    )
})
public class Asset extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @Column(name="asset_class", unique=false, updatable=true, nullable=true )
    private String assetClass;

    @JoinColumn(name="valuable_id", updatable=false, nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private ValuableObject valuable;

    @Column(name="is_archived", updatable=true, nullable=false )
    private boolean archived;
    private static final long serialVersionUID = 8316404842745606918L;

    public Asset() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ValuableObject getValuable() {
        return valuable;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValuable(ValuableObject valuable) {
        this.valuable = valuable;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isArchived() {
        return archived;
    }

    public static interface Queries
    {
        public static final String FIND_BY_VALUABLE       = "findAssetByValuable";
        public static final String FIND_BY_OWNER          = "findAssetsByUser";
        public static final String FIND_ARCHIVED_BY_OWNER = "findArchivedAssetsByUser";
    }

    public static interface Properties
    {
        public static final String VALUABLE = "valuable";
        public static final String OWNER = "owner";
        public static final String ID    = "id";
    }
}
