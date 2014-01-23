package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.entity.Hint;
import java.sql.SQLException;
import org.localstorm.mcc.ejb.gtd.dao.FiredHintsReportBean;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.gtd.dao.HintReportsDao;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class HintManagerBean extends AbstractManager<Hint>
                             implements HintManagerLocal
{
    @Resource(mappedName=Constants.DEFAULT_DS)
    private DataSource ds;

    public HintManagerBean()
    {
        super(Hint.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Hint> getHintsForTask(Task t)
    {
        Query tq = em.createNamedQuery(Hint.Queries.FIND_BY_TASK);
        tq.setParameter(Hint.Properties.TASK, t);

        return (List<Hint>) tq.getResultList();
    }

    @Override
    public FiredHintsReportBean getFiredHintsReport(User user)
    {
        HintReportsDao hrd = new HintReportsDao(ds);

        try {
            return hrd.getFiredHintsReport(user);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void updateHintsForTask(Task t)
    {
        Query tq = em.createNamedQuery(Hint.Queries.UPDATE_BY_TASK);
        
        tq.setParameter(Hint.Properties.LAST_UPDATE, new Date());
        tq.setParameter(Hint.Properties.TASK, t);
        tq.executeUpdate();
    }

    @Override
    public void discardHintsForTask(Task t) {
        Query tq = em.createNamedQuery(Hint.Queries.DISCARD_BY_TASK);

        tq.setParameter(Hint.Properties.TASK, t);
        tq.executeUpdate();
    }


}
