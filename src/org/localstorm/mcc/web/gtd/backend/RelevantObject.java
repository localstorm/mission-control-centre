/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.gtd.backend;

public class RelevantObject<T> implements Comparable<RelevantObject>
{
    private T object;
    private float relevance;

    public RelevantObject(T object, float relevance) {
        this.object    = object;
        this.relevance = relevance;
    }

    public float getRelevance() {
        return relevance;
    }

    public T getObject() {
        return object;
    }

    @Override
    public int compareTo(RelevantObject o) {
        if (this.relevance<o.getRelevance()) {
            return 1;
        }
        if (this.relevance>o.getRelevance()) {
            return -1;
        }
        return 0;
    }
}