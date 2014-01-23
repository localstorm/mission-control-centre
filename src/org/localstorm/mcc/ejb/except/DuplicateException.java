/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.except;

/**
 *
 * @author localstorm
 */
public class DuplicateException extends Exception {
    private static final long serialVersionUID = -1047044062833851797L;

    public DuplicateException() {
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(String message) {
        super(message);
    }
    

}
