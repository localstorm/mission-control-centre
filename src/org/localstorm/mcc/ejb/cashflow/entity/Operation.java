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
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="OPERATIONS")
@NamedQueries({

    @NamedQuery(
        name = Operation.Queries.FIND_BY_VO_DESC_LIMITED,
        query= "SELECT o FROM Operation o WHERE o.cost.valuable=:valuable AND o.operationDate>:minDate ORDER BY o.operationDate DESC"
    ),
    @NamedQuery(
        name = Operation.Queries.FIND_BY_VO_DESC,
        query= "SELECT o FROM Operation o WHERE o.cost.valuable=:valuable ORDER BY o.operationDate DESC"
    ),
    @NamedQuery(
        name = Operation.Queries.SUM_BOUGHT_BY_VO,
        query= "SELECT SUM(o.cost.buy*o.amount) FROM Operation o WHERE o.cost.valuable=:valuable and o.type='BUY' and o.cost.buy IS NOT NULL"
    ),
    @NamedQuery(
        name = Operation.Queries.SUM_SELL_BY_VO,
        query= "SELECT SUM(o.cost.sell*o.amount) FROM Operation o WHERE o.cost.valuable=:valuable and o.type='SELL' and o.cost.sell IS NOT NULL"
    ),
    @NamedQuery(
        name = Operation.Queries.SUM_AMOUNT_BY_VO,
        query= "SELECT SUM(o.amount) FROM Operation o WHERE o.cost.valuable=:valuable"
    )
})
public class Operation extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="type", unique=false, updatable=true, nullable=false )
    private String type;

    @Column(name="comment", unique=false, updatable=true, nullable=true )
    private String comment;

    @Column(name="amount", unique=false, updatable=true, nullable=false)
    private BigDecimal amount;

    @Column(name="operation_date", unique=false, updatable=true, nullable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationDate;

    @JoinColumn(name="cost_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Cost cost;
    private static final long serialVersionUID = -2760618946095185157L;

    @Override
    public Integer getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static interface Queries
    {
        public static final String FIND_BY_VO_DESC_LIMITED = "findOpsByVoDescLimited";
        public static final String FIND_BY_VO_DESC = "findOpsByVoDesc";
        public static final String SUM_BOUGHT_BY_VO = "sumBought";
        public static final String SUM_AMOUNT_BY_VO = "totalAmount";
        public static final String SUM_SELL_BY_VO   = "sumSell";
    }

    public static interface Properties
    {
        public static final String VALUABLE = "valuable";
        public static final String MIN_DATE = "minDate";
    }
}
