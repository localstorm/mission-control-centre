/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb;

/**
 * @author Alexey Kuznetsov
 */
public interface Retireable 
{
    public boolean isArchived();
    
    public void setArchived(boolean archived);
}
