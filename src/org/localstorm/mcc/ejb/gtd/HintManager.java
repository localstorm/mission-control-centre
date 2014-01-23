package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.entity.Hint;
import org.localstorm.mcc.ejb.gtd.dao.FiredHintsReportBean;
import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
public interface HintManager extends BaseManager<Hint>
{
    public static final String BEAN_NAME = "HintManagerBean";

    public void discardHintsForTask(Task t);
    public Collection<Hint> getHintsForTask(Task t);
    public FiredHintsReportBean getFiredHintsReport(User user);
    public void updateHintsForTask(Task t);
}
