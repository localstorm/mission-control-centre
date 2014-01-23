package org.localstorm.mcc.web.starter;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.agent.AgendaCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.AgentExecutionFrontend;
import org.localstorm.mcc.ejb.gtd.agent.DefaultCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.DoneCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.FlightCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.HintCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.InboxCommandHandler;
import org.localstorm.mcc.ejb.gtd.agent.OverdueCommandHandler;
import org.localstorm.mcc.xmpp.JID;
import org.localstorm.mcc.xmpp.XmppHandler;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AssistantXmppHandler implements XmppHandler
{
    private static final Logger log = Logger.getLogger(AssistantXmppHandler.class);

    private AgentExecutionFrontend aef;

    public AssistantXmppHandler()
    {
        this.aef = new AgentExecutionFrontend();
        this.aef.setDefaultCommandHandler(new DefaultCommandHandler());
        this.aef.addCommandHandler("inbox",  new InboxCommandHandler());
        this.aef.addCommandHandler("flight", new FlightCommandHandler());
        this.aef.addCommandHandler("hints",  new HintCommandHandler());
        this.aef.addCommandHandler("agenda", new AgendaCommandHandler());
        this.aef.addCommandHandler("overdue", new OverdueCommandHandler());
        this.aef.addCommandHandler("done",   new DoneCommandHandler());
    }

    @Override
    public String handle(int uid, JID from, JID to, String message)
    {
        // TODO: make solid security solution
        if (!from.toString().equals("localstorm@gmail.com"))
        {
            return "Fuck off, nigger.";
        }

        UserTransaction ut = null;
        
        try {
            ut = ContextLookup.lookupTransaction();

            String response = null;
            ut.begin();
                response = aef.handle(uid, from.toString(), to.toString(), message);
            ut.commit();
            return response;
        } catch(Exception e) {
            log.fatal(e.getMessage(), e);
        } finally {
            rollbackSilently(ut);
        }

        return "Server error. Contact service administrator please.";
        
    }

    private void rollbackSilently(UserTransaction ut)
    {
        try {
            if (ut!=null &&
                Status.STATUS_COMMITTED!=ut.getStatus() &&
                ut.getStatus()!=Status.STATUS_NO_TRANSACTION)
            {
                ut.rollback();
            }
        } catch(SystemException e) {
            log.error(e);
        }
    }

}
