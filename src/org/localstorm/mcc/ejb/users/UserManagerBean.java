package org.localstorm.mcc.ejb.users;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;

/**
 *
 * @author localstorm
 */
@Stateless
public class UserManagerBean implements UserManagerLocal, UserManagerRemote 
{
    public UserManagerBean() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public User login(String login, String pwd) 
    {
        Query uq = em.createNamedQuery(User.Queries.FIND_BY_LOGIN_AND_PASS);
        uq.setParameter(User.Properties.LOGIN, login);
        
        uq.setParameter(User.Properties.PASSWORD, this.getHashString(pwd) );
                
        List<User> l = uq.getResultList();
        if ( l.isEmpty() ) {
            return null;
        } else {
            User user  = l.get(0);
            return user;
        }
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean subscribe(String login, String pwd) 
    {
        Query uq = em.createNamedQuery(User.Queries.FIND_BY_LOGIN);
        uq.setParameter(User.Properties.LOGIN, login);
        
        List<User> l = uq.getResultList();
        if ( l.isEmpty() ) {
            try {
                User u = new User(login, "First Name", "Last Name", this.getHashString(pwd));
                em.persist(u);
                return true;
            } catch(EntityExistsException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void changePassword(User user, String password) {
        try {
            user.setPasswordHash(this.getHashString(password));
            em.merge(user);
        } catch(EntityExistsException e) {
        }
    }

    @Override
    public User findById(int uid)
    {
        return em.find(User.class, uid);
    }
    
    private String getHashString(String s)
    {
        return MD5Util.md5ToString(MD5Util.getMD5(s));
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
