/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.except;

/**
 *
 * @author localstorm
 */
public class ObjectNotFoundException extends Exception {
    private static final long serialVersionUID = 5917998262540282790L;

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException() {
    }

}
