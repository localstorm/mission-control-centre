package org.localstorm.mcc.ejb.gtd.agent;

import java.util.Collection;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.dao.FiredHintsReportBean;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class HintCommandHandler implements CommandHandler
{

    @Override
    public String handle(int uid, String from, String to, String param)
    {
        HintManager   hm = ContextLookup.lookup(HintManager.class, HintManager.BEAN_NAME);
        UserManager   um = ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);

        FiredHintsReportBean fhrb = hm.getFiredHintsReport(um.findById(uid));
        
        return this.buildResponse(fhrb);
    }

    private String buildResponse(FiredHintsReportBean fhrb)
    {
        Collection<FiredHintsReportBean.TaskStub> fired = fhrb.getFired();
        StringBuilder sb = new StringBuilder();

        sb.append("--- HINTED tasks ---\n");
        for (FiredHintsReportBean.TaskStub ts: fired)
        {
            sb.append('[');
            sb.append(ts.getId());
            sb.append("] ");
            sb.append(ts.getSummary());
            sb.append('\n');
            sb.append("--------------------\n");
        }

        return sb.toString();
    }

    
}
