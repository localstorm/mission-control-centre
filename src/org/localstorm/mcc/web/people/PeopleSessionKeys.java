package org.localstorm.mcc.web.people;

import org.localstorm.mcc.web.CommonSessionKeys;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface PeopleSessionKeys extends CommonSessionKeys
{
    public static final String PEOPLE_CLIPBOARD     = "pclipboard";
    public static final String PERSON_GROUPS        = "groups";
    public static final String MAIL_LISTS           = "mailLists";
    public static final String ARCHIVE_MAIL_LISTS   = "archivedMailLists";
    public static final String ARCHIVE_PERSON_GROUPS= "archivedGroups";
    public static final String ACCESSIBLE_GROUPS_MAP= "accessiblePersonGroups";
    public static final String ACCESSIBLE_MAIL_LISTS_MAP="accessibleMailLists";
}
