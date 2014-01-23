package org.localstorm.mcc.ejb.people;

import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public interface PeopleSecuritySupport
{
    public static final String BEAN_NAME="PeopleSecuritySupportBean";

    public boolean checkAttributesAccess(Integer[] attributeIds, User user);

    public boolean checkPersonsAccess(Integer[] personIds, User user);
}
