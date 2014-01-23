package org.localstorm.mcc.web;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SecurityRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = -5984759852885155463L;

    public SecurityRuntimeException(String msg)
    {
        super(msg);
    }

}
