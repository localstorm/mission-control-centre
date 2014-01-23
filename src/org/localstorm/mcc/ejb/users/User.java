package org.localstorm.mcc.ejb.users;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang.math.RandomUtils;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 * @author localstorm
 */
@Entity
@Table(name="USERS")
@NamedQueries({
    @NamedQuery(
        name = User.Queries.FIND_BY_LOGIN_AND_PASS,
        query= "SELECT o FROM User o WHERE o.login=:login and o.passHash=:password and o.blocked=false"
    ),
    @NamedQuery(
        name = User.Queries.FIND_BY_LOGIN,
        query= "SELECT o FROM User o WHERE o.login=:login"
    )
})
public class User  extends AbstractEntity implements Identifiable, Serializable
{
    @Id
    @Column(name="id", unique=true, updatable=false )
    private Integer id;
     
    @Column(name="fname")
    private String firstName;
     
    @Column(name="login", unique=true, updatable=false )
    private String login;
     
    @Column(name="lname")
    private String lastName;
     
    @Column(name="pass_hash")
    private String passHash;
     
    @Column(name="is_blocked")
    private boolean blocked;
    private static final long serialVersionUID = 1801591188632778874L;

    public User() 
    {
        
    }
    
    public User(String login, String firstName, String lastName, String passHash) 
    {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.passHash  = passHash;
        this.blocked   = false;
        this.login     = login;
        this.id        = RandomUtils.nextInt();
    }
     
    public String getFirstName() 
    {
        return firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getPasswordHash() 
    {
        return passHash;
    }
 

    public boolean isBlocked() 
    {
        return blocked;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) 
    {
        this.id = id;
    }
    
    public void setPasswordHash(String hash) 
    {
        this.passHash = hash;
    }

    public void setBlocked(boolean blocked) 
    {
        this.blocked = blocked;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }
    
    public static interface Queries {
        public static final String FIND_BY_LOGIN_AND_PASS = "findByLoginAndPass";
        public static final String FIND_BY_LOGIN          = "findByLogin";  
    }
    
    public static interface Properties {
        public static final String PASSWORD = "password";
        public static final String LOGIN    = "login"; 
    }
}
