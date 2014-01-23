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
@Table(name="REFERENCED_OBJECTS")
@NamedQueries({
    @NamedQuery(
        name = ReferencedObject.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM ReferencedObject o JOIN FETCH o.context WHERE o.context.owner=:owner ORDER BY o.context.name, o.name"
    ),
    @NamedQuery(
        name = ReferencedObject.Queries.FIND_OPERATIVE_BY_OWNER,
        query= "SELECT o FROM ReferencedObject o JOIN FETCH o.context WHERE o.context.archived=false and o.context.owner=:owner and o.archived=false ORDER BY o.context.name, o.name"
    ),
    @NamedQuery(
        name = ReferencedObject.Queries.FIND_OPERATIVE_BY_OWNER_NO_CTX_SORT,
        query= "SELECT o FROM ReferencedObject o JOIN FETCH o.context WHERE o.context.archived=false and o.context.owner=:owner and o.archived=false ORDER BY o.name"
    ),
    @NamedQuery(
        name = ReferencedObject.Queries.FIND_ARCHIVED_BY_OWNER,
        query= "SELECT o FROM ReferencedObject o JOIN FETCH o.context WHERE o.context.owner=:owner and (o.archived=true or o.context.archived=true) ORDER BY o.context.name, o.name"
    )
})
public class ReferencedObject extends AbstractEntity implements Serializable, Identifiable
{   
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @JoinColumn(name="context_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Context context;
    private static final long serialVersionUID = -8627094131049744578L;


    public ReferencedObject() 
    {
    
    }

    public ReferencedObject( String name, Context ctx ) {
        this.name      = name;
        this.archived  = false;
        this.creation  = new Date();
        this.context   = ctx;
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    public Context getContext() {
        return context;
    }
    
    public Date getCreation() {
        return creation;
    }

    public String getName() {
        return name;
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
    
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    
    public static interface Queries
    {
        public static final String FIND_BY_OWNER = "findROByOwner";
        public static final String FIND_OPERATIVE_BY_OWNER = "findOpROByOwner";
        public static final String FIND_ARCHIVED_BY_OWNER = "findArROByOwner";
        public static final String FIND_OPERATIVE_BY_OWNER_NO_CTX_SORT = "findOpROByOwnerNoCtxSort";
    }
    
    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
    
}