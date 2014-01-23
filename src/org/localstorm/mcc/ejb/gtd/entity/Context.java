package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
import java.util.Comparator;
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
import org.localstorm.mcc.ejb.Retireable;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="CONTEXTS")
@NamedQueries({
    @NamedQuery(
        name = Context.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM Context o WHERE o.owner=:owner and o.archived=false ORDER BY o.name"
    ),
    @NamedQuery(
        name = Context.Queries.FIND_BY_OWNER_ARCHIVED,
        query= "SELECT o FROM Context o WHERE o.owner=:owner and o.archived=true"
    )
})
public class Context extends AbstractEntity implements Identifiable, Retireable, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;
    private static final long serialVersionUID = -5454239459488634070L;

    public Context() 
    {
    
    }

    public Context( String name, User owner ) {
        this.name = name;
        this.owner = owner;
        this.archived = false;
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    
    public static interface Queries
    {
        public static final String FIND_BY_OWNER = "findByUser";
        public static final String FIND_BY_OWNER_ARCHIVED = "findByUserArchived";
    }
    
    public static interface Properties
    {
        public static final String OWNER = "owner";
    }

    public static final class NameAscComparator implements Comparator<Context>
    {
        @Override
        public int compare(Context o1, Context o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
