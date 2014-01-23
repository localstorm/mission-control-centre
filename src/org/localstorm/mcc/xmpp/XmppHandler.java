package org.localstorm.mcc.xmpp;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface XmppHandler
{
    public String handle(int uid, JID from, JID to, String message);
}
