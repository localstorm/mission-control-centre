package org.localstorm.mcc.xmpp;

/**
 *
 * @author Alexey Kuznetsov
 */
public class JID implements Comparable<JID>
{
    private String name;
    private String service;

    public JID(String jid) {
        
        jid = this.removeTrailingJidInfo(jid);

        String[] array = jid.split("@");
        if (array.length!=2) {
            throw new IllegalArgumentException("Invalid JID:"+jid);
        }

        this.name    = array[0];
        this.service = array[1];
    }

    public String getName() {
        return name;
    }

    public String getService() {
        return service;
    }

    @Override
    public String toString() {
        return name+"@"+service;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj==null)
        {
            return false;
        }

        if (!(obj instanceof JID))
        {
            return false;
        }

        JID jid = (JID) obj;

        return this.name.equals(jid.getName()) && this.service.equals(jid.getService());
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 71 * hash + (this.service != null ? this.service.hashCode() : 0);
        return hash;
    }

    private String removeTrailingJidInfo(String jid)
    {
        int index = jid.indexOf("/");
        if (index>=0)
        {
            return jid.substring(0, index);
        }
        return jid;
    }

    public int compareTo(JID o)
    {
        return this.toString().compareTo(o.toString());
    }


}
