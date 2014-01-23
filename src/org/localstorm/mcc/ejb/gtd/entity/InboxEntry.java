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
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.util.EscapeUtil;


/**
 * @author localstorm
 */
@Entity
@Table(name="INBOX")
@NamedQueries({
    @NamedQuery(
        name = InboxEntry.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM InboxEntry o WHERE o.owner=:owner ORDER BY o.creation DESC"
    )
})
public class InboxEntry extends AbstractEntity implements Identifiable, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="content", unique=false, updatable=true, nullable=false )
    private String content;
    
    @JoinColumn(name="owner", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private User owner;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    private static final long serialVersionUID = -4448210307719080190L;

    public InboxEntry() 
    {
    
    }

    public InboxEntry( String content, User owner ) {
        this.content  = content;
        this.owner    = owner;
        this.creation = new Date();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Date getCreation() {
        return creation;
    }

    public String getContent() {
        return content;
    }

    public User getOwner() {
        return owner;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER = "findInboxEntriesByOwner";
    }
    
    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
}
