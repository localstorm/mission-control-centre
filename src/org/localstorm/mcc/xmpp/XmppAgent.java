package org.localstorm.mcc.xmpp;

/**
 *
 * @author Alexey Kuznetsov
 */
public class XmppAgent 
{
    private JID jid;
    private String host;
    private int port;
    private boolean secure;
    private String password;
    private int uid;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public JID getJID()
    {
        return jid;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isSecure()
    {
        return secure;
    }

    public String getPassword()
    {
        return password;
    }

    public void setJID(JID jid)
    {
        this.jid = jid;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setSecure(boolean secure)
    {
        this.secure = secure;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
