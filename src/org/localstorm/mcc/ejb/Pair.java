package org.localstorm.mcc.ejb;

import java.io.Serializable;

/**
 *
 * @author Alexey Kuznetsov
 */
public final class Pair<T, K> implements Serializable
{
    private T first;
    private K second;
    private static final long serialVersionUID = -8438470398589678650L;

    public Pair(T first, K second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public K getSecond()
    {
        return second;
    }
}
