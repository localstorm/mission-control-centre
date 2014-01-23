package org.localstorm.mcc.ejb;


import java.text.MessageFormat;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;


public class ContextLookup<T>
{
    public static final String USER_TRANSACTION_JNDI = "UserTransaction";
    private static final String JNDI_LOOKUP_REMOTE = "gtdmcc/{0}/remote";
    private static final String JNDI_LOOKUP_LOCAL = "gtdmcc/{0}/local";
    
    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> cl, String beanName)  throws RuntimeException
    {
        try {
            InitialContext ic = new InitialContext();
            Object o = ic.lookup(MessageFormat.format(JNDI_LOOKUP_LOCAL, beanName));
            ic.close();
            return (T) o;
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T lookupLocal(Class<T> cl, String beanName)  throws RuntimeException
    {
        try {
            InitialContext ic = new InitialContext();
            Object o = ic.lookup(MessageFormat.format(JNDI_LOOKUP_LOCAL, beanName));
            ic.close();
            return (T) o;
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T nativeLookup(Class<T> cl, String jndi)  throws RuntimeException
    {
        try {
            InitialContext ic = new InitialContext();
            return (T)ic.lookup(jndi);
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static UserTransaction lookupTransaction()
    {
        try {
            return (UserTransaction) (new InitialContext()).lookup(USER_TRANSACTION_JNDI);
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }

}

