package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
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
 * @author localstorm
 */
@Entity
@Table(name="LISTS")
@NamedQueries({
    @NamedQuery(
        name = GTDList.Queries.FIND_BY_CTX,
        query= "SELECT o FROM GTDList o WHERE o.context=:context and o.archived=false ORDER BY o.name"
    ),
    @NamedQuery(
        name = GTDList.Queries.FIND_BY_CTX_ARCHIVED,
        query= "SELECT o FROM GTDList o WHERE o.context=:context and o.archived=true  ORDER BY o.name"
    )
})
public class GTDList extends AbstractEntity implements Identifiable, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @JoinColumn(name="context_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Context context;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    
    @Column(name="is_pinned", unique=false, updatable=true)    
    private boolean pinned;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    private static final long serialVersionUID = -2321503383647833036L;

    public GTDList() 
    {
    
    }

    public GTDList( String name, Context ctx ) {
        this.name       = name;
        this.context    = ctx;
        this.archived   = false;
        this.creation   = new Date();
        this.pinned     = false;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
    
    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    public Date getCreation() {
        return creation;
    }

    public boolean isArchived() {
        return archived;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public static interface Queries
    {
        public static final String FIND_BY_CTX = "findByContext";
        public static final String FIND_BY_CTX_ARCHIVED = "findByContextArchived";
    }
    
    public static interface Properties
    {
        public static final String CONTEXT = "context";
    }
}
