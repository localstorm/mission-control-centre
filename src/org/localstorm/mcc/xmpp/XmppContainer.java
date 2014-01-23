package org.localstorm.mcc.xmpp;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListMap;
import org.jivesoftware.smack.XMPPConnection;

/**
 *
 * @author Alexey Kuznetsov
 */
public class XmppContainer
{
    private final static long RESPAWN_PERIOD = 5000;

    private XmppHandler handler;
    private ConcurrentSkipListMap<JID, XMPPConnection> connections;
    private ConcurrentSkipListMap<JID, XmppAgent>      agents;
    private Timer timer;

    public XmppContainer(XmppHandler handler)
    {
        this.connections = new ConcurrentSkipListMap<JID, XMPPConnection>();
        this.agents      = new ConcurrentSkipListMap<JID, XmppAgent>();
        this.timer       = new Timer("XMPP Connection Respawner", true);
        this.handler     = handler;
    }

    public synchronized void start()
    {
        TimerTask crtt = new ConnectionRespawnerTimerTask(this.handler,
                                                          this.agents,
                                                          this.connections);
        this.timer.schedule(crtt, 0, RESPAWN_PERIOD);
    }

    public synchronized void stop()
    {
        this.timer.cancel();

        for (XMPPConnection conn: connections.values())
        {
            XmppUtils.closeQuietly(conn);
        }
    }

    public synchronized void createOrReplaceXmppAgent(XmppAgent agent)
    {
        JID jid = agent.getJID();
        XmppAgent old = this.agents.put(jid, agent);
        if (old != null)
        {
            XmppUtils.closeQuietlySync(this.connections.get(jid));
        }
    }

    public synchronized void removeXmppAgent(JID jid)
    {
        XmppAgent old = this.agents.remove(jid);
        if (old != null)
        {
            XmppUtils.closeQuietlySync(this.connections.get(jid));
        }
    }
   
}
