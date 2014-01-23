package org.localstorm.mcc.ejb.gtd.entity;

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
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name = "AGENTS")
@NamedQueries({
    @NamedQuery(
        name = Agent.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM Agent o WHERE o.owner=:owner ORDER BY o.jid"
    ),
    @NamedQuery(
        name = Agent.Queries.FIND_ALL,
        query= "SELECT o FROM Agent o"
    )
})
public class Agent extends AbstractEntity implements Identifiable, Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "jid", unique = false, updatable = true, nullable = false)
    private String jid;
    
    @Column(name = "host_name", unique = false, updatable = true, nullable = false)
    private String hostName;
    
    @Column(name = "port", unique = false, updatable = true, nullable = false)
    private int port;
    
    @Column(name = "is_secure", unique = false, updatable = true, nullable = false)
    private boolean secure;
    
    @Column(name = "password", unique = false, updatable = true, nullable = false)
    private String password;
    
    @JoinColumn(name = "owner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    private static final long serialVersionUID = -6841950642664672980L;

    @Override
    public Integer getId()
    {
        return id;
    }

    public String getJid()
    {
        return jid;
    }

    public void setJid(String jid)
    {
        this.jid = jid;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public String getHostName()
    {
        return hostName;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isSecure()
    {
        return secure;
    }

    public String getPassword()
    {
        return password;
    }

    public void setHostName(String host)
    {
        this.hostName = host;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setSecure(boolean secure)
    {
        this.secure = secure;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER = "findAgentsByUser";
        public static final String FIND_ALL      = "findAllAgents";
    }

    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
}
