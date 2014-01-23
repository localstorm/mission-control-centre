package org.localstorm.mcc.ejb.gtd.agent;

/**
 * @author Alexey Kuznetsov
 */
public interface CommandHandler
{
    public String handle(int uid, String from, String to, String param);
}
