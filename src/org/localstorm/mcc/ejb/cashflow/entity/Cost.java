package org.localstorm.mcc.ejb.cashflow.entity;

import org.localstorm.mcc.ejb.AbstractEntity;
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

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="COSTS")
@NamedQueries({
    @NamedQuery(
        name = Cost.Queries.FIND_COSTS_BY_VO_DESC,
        query= "SELECT o FROM Cost o WHERE o.valuable=:valuable and o.actuationDate>:minDate ORDER BY o.actuationDate DESC"
    )
})
public class Cost extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="buy", unique=false, updatable=true, nullable=true )
    private BigDecimal buy;

    @Column(name="sell", unique=false, updatable=true, nullable=true )
    private BigDecimal sell;

    @Column(name="actuation_date", unique=false, updatable=true, nullable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date actuationDate;

    @JoinColumn(name="valuable_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private ValuableObject valuable;
    private static final long serialVersionUID = 5813286831938101824L;

    protected Cost()
    {
        this.actuationDate = new Date();
    }

    public Cost(ValuableObject vo)
    {
        this.valuable = vo;
        this.actuationDate = new Date();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public ValuableObject getValuable() {
        return valuable;
    }

    public void setActuationDate(Date actuationDate) {
        this.actuationDate = actuationDate;
    }

    public Date getActuationDate() {
        return actuationDate;
    }

    public void setValuable(ValuableObject vo) {
        this.valuable = vo;
    }

    public static interface Queries
    {
        public static final String FIND_COSTS_BY_VO_DESC = "findCostsByVoDesc";
    }

    public static interface Properties
    {
        public static final String VALUABLE = "valuable";
        public static final String MIN_DATE = "minDate";
    }

}
