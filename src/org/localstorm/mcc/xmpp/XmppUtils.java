package org.localstorm.mcc.xmpp;

import java.io.IOException;
import java.io.InputStream;
import javax.net.ssl.SSLSocketFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

/**
 *
 * @author Alexey Kuznetsov
 */
public class XmppUtils 
{
    public static boolean sendSilently(String message, Chat chat)
    {
        if (message!=null && message.length()>0) {
            try {
                chat.sendMessage(message);
                return true;
            } catch(Throwable e) {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static ConnectionConfiguration getConnectionCfg(JID jid,
                                                           String host,
                                                           int port,
                                                           boolean secure) 
    {
        ConnectionConfiguration cc = null;
        
        if (jid.getService().equals(host)) {
            cc = new ConnectionConfiguration(host, port);
        } else {
            cc = new ConnectionConfiguration(host, port, jid.getService());
        }
        
        cc.setReconnectionAllowed(false);
        cc.setCompressionEnabled(true);
        cc.setSASLAuthenticationEnabled(true);

        if (secure) {
            cc.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
            SSLSocketFactory sockFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
            cc.setSocketFactory(sockFact);
        }

        return cc;
    }
    
    public static boolean closeQuietly(InputStream is) {
        if (is!=null) {
            try {
                is.close();
                return true;
            } catch (IOException e){
                return false;
            }
        }
        return true;
    }

    public static void closeQuietly(XMPPConnection conn)
    {
        try {
            if (conn!=null && conn.isConnected())
            {
                Presence p = new Presence(Presence.Type.available);
                conn.disconnect(p);
            }
        } catch(Exception e) {
            //TODO: logging
        }
    }

    public synchronized static void closeQuietlySync(XMPPConnection conn)
    {
        closeQuietly(conn);
    }

}
