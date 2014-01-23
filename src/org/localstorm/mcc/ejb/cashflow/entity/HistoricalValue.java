package org.localstorm.mcc.ejb.cashflow.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="HISTORICAL_VALUES")
@NamedQueries({
    @NamedQuery (
        name = HistoricalValue.Queries.FIND_BY_VALUE_TAG,
        query= "SELECT o FROM HistoricalValue o WHERE o.valueTag=:tag and o.owner=:owner and o.fixDate>:minDate ORDER BY o.fixDate DESC"
    ),
    @NamedQuery (
        name = HistoricalValue.Queries.FIND_BY_VALUE_AND_OBJECT_ID_TAG,
        query= "SELECT o FROM HistoricalValue o WHERE o.valueTag=:tag and o.owner=:owner and o.objectId=:objectId and o.fixDate>:minDate ORDER BY o.fixDate DESC"
    ),
    @NamedQuery (
        name = HistoricalValue.Queries.TRUNCATE_BY_VALUE_TAG_AND_OBJECT_ID,
        query= "DELETE HistoricalValue o WHERE o.valueTag=:tag and o.owner=:owner and o.objectId=:objectId and o.fixDate<:maxDate"
    ),
    @NamedQuery (
        name = HistoricalValue.Queries.TRUNCATE_BY_VALUE_TAG,
        query= "DELETE HistoricalValue o WHERE o.valueTag=:tag and o.owner=:owner and o.fixDate<:maxDate"
    )
})
public class HistoricalValue implements Identifiable, Serializable {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="value_tag", unique=false, updatable=true, nullable=true )
    private String valueTag;

    @Column(name="val", unique=false, updatable=true, nullable=false)
    private BigDecimal val;

    @Column(name="fix_date", unique=false, updatable=true, nullable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date fixDate;

    @Column(name="object_id", unique=false, updatable=true, nullable=true )
    private Integer objectId;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;
    private static final long serialVersionUID = 7274317512556950336L;

    @Override
    public Integer getId() {
        return id;
    }

    public Date getFixDate() {
        return fixDate;
    }

    public void setFixDate(Date fixDate) {
        this.fixDate = fixDate;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getVal() {
        return val;
    }

    public void setVal(BigDecimal value) {
        this.val = value;
    }

    public void setValueTag(ValueType valueTag) {
        this.valueTag = valueTag.toString();
    }

    public ValueType getValueTag() {
        return ValueType.valueOf(valueTag);
    }

    public static class Queries {
        public static final String FIND_BY_VALUE_TAG = "findHVByValueTag";
        public static final String FIND_BY_VALUE_AND_OBJECT_ID_TAG = "findHVByValueTagAndObjectId";
        public static final String TRUNCATE_BY_VALUE_TAG_AND_OBJECT_ID = "trucateHVByValueTagAndObjectId";
        public static final String TRUNCATE_BY_VALUE_TAG = "trucateHVByValueTag";
    }

    public static interface Properties
    {
        public static final String MAX_DATE  = "maxDate";
        public static final String MIN_DATE  = "minDate";
        public static final String VALUE_TAG = "tag";
        public static final String OWNER     = "owner";
        public static final String OBJECT_ID = "objectId";
    }
}
