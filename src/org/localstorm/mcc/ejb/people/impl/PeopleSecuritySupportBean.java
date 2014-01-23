package org.localstorm.mcc.ejb.people.impl;

import java.text.MessageFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.CsvUtil;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class PeopleSecuritySupportBean extends PeopleStatelessBean implements PeopleSecuritySupportLocal
{
    public PeopleSecuritySupportBean() {
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkAttributesAccess(Integer[] attributeIds, User user)
    {
        if (attributeIds.length==0) {
            return true;
        }

        String template = "SELECT DISTINCT o.group.owner FROM PersonToGroup o where " +
                          "o.person IN (SELECT a.person FROM Attribute a WHERE a.id IN ({0}))";

        String sql = MessageFormat.format(template, CsvUtil.getCsvString("?", attributeIds.length));

        Query q = em.createQuery(sql);

        int i=1;
        for (Integer attrId: attributeIds){
            q.setParameter(i++, attrId);
        }

        List<User> users = q.getResultList();
        if (users.size()!=1) {
            return false;
        }

        User owner = users.get(0);
        return (owner.getId().equals(user.getId()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkPersonsAccess(Integer[] personIds, User user)
    {
        if (personIds.length==0) {
            return true;
        }

        String template = "SELECT DISTINCT o.group.owner FROM PersonToGroup o where " +
                          "o.person.id IN ({0})";

        String sql = MessageFormat.format(template, CsvUtil.getCsvString("?", personIds.length));

        Query q = em.createQuery(sql);

        int i=1;
        for (Integer pid: personIds){
            q.setParameter(i++, pid);
        }

        List<User> users = q.getResultList();
        if (users.size()!=1) {
            return false;
        }

        User owner = users.get(0);
        return (owner.getId().equals(user.getId()));
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
