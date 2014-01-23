package org.localstorm.mcc.web.starter;

import java.util.Collection;
import org.localstorm.mcc.xmpp.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.AgentManager;
import org.localstorm.mcc.ejb.gtd.entity.Agent;

/**
 *
 * @author Alexey Kuznetsov
 */
public class XmppServletContextListener implements ServletContextListener
{
    private static final Logger log = Logger.getLogger(XmppServletContextListener.class);
    private static final XmppContainer container = new XmppContainer(new AssistantXmppHandler());

    @Override
    public void contextInitialized(ServletContextEvent arg0)
    {
        container.start();

        AgentManager am = ContextLookup.lookup(AgentManager.class, AgentManager.BEAN_NAME);

        UserTransaction ut = null;
        try
        {
            ut = ContextLookup.lookupTransaction();
            ut.begin();
            Collection<Agent> agents = am.getAgents();

            for (Agent a:agents)
            {
                XmppAgent agent = this.convert(a);
                container.createOrReplaceXmppAgent(agent);
            }
            ut.commit();

        } catch (Exception e)
        {
            log.error("Transaction failed:", e);
            try {
                if (ut != null && Status.STATUS_COMMITTED != ut.getStatus() &&
                        ut.getStatus() != Status.STATUS_NO_TRANSACTION)
                {
                    ut.setRollbackOnly();
                }
            } catch(SystemException ex) {
                log.fatal("Transaction rolback failed: ", ex);
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        container.stop();
    }

    private XmppAgent convert(Agent a)
    {
        XmppAgent agent = new XmppAgent();
        {
            agent.setHost(a.getHostName());
            agent.setPort(a.getPort());
            agent.setJID(new JID(a.getJid()));
            agent.setPassword(a.getPassword());
            agent.setSecure(a.isSecure());
            agent.setUid(a.getOwner().getId());
        }
        return agent;
    }
}
