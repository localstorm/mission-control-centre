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
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="MAIL_LISTS")
@NamedQueries({
    @NamedQuery(
        name = MailList.Queries.FIND_MLS_BY_OWNER,
        query= "SELECT ml FROM MailList ml WHERE ml.archived=false and ml.owner=:owner ORDER BY ml.name ASC"
    ),
    @NamedQuery(
        name = MailList.Queries.FIND_ARCHIVED_MLS_BY_OWNER,
        query= "SELECT ml FROM MailList ml WHERE ml.archived=true and ml.owner=:owner ORDER BY ml.name ASC"
    ),
    @NamedQuery(
        name = MailList.Queries.FIND_JOINABLE_MLS,
        query= "SELECT DISTINCT p2ml.mailList FROM PersonToMailList p2ml WHERE p2ml.mailList IN (" +
        "SELECT ml FROM MailList ml where ml.owner=:owner) AND p2ml.mailList NOT IN (" +
        "SELECT p2ml2.mailList FROM PersonToMailList p2ml2 WHERE p2ml2.person=:person"+
        ") ORDER BY p2ml.mailList.name ASC"
    )
})
public class MailList  extends AbstractEntity implements Identifiable, Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @JoinColumn(name="owner", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;

    @Column(name="is_archived", unique=false, updatable=true, nullable=false )
    private boolean archived;
    private static final long serialVersionUID = 2668167181333769288L;

    public MailList()
    {
    }
    
    @Override
    public Integer getId()
    {
        return this.id;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }
    
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isArchived()
    {
        return archived;
    }

    public void setArchived(boolean archived)
    {
        this.archived = archived;
    }

    public static interface Queries
    {
        public static final String FIND_MLS_BY_OWNER = "findMailListsByOwner";
        public static final String FIND_ARCHIVED_MLS_BY_OWNER = "findArchivedMailListsByOwner";
        public static final String FIND_JOINABLE_MLS = "findJoinableMailLists";
    }

    public static interface Properties
    {
        public static final String PERSON = "person";
        public static final String OWNER  = "owner";
    }
}
