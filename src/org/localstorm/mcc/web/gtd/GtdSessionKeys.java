package org.localstorm.mcc.web.gtd;

import org.localstorm.mcc.web.CommonSessionKeys;

/**
 * @author Alexey Kuznetsov
 */
public interface GtdSessionKeys extends CommonSessionKeys
{
    public static final String CONTEXTS                = "contexts";
    public static final String REFERENCE_OBJECTS       = "refObjects";
    
    public static final String CURR_CTX                = "currContext";
    public static final String CURR_LIST               = "currList";
    public static final String CURR_TASK               = "currTask";

    public static final String GTD_CLIPBOARD           = "clipboard";

    public static final String FILTER_CONTEXT          = "filterCtx";
    public static final String CURR_OBJ                = "currRefObject";
    public static final String ACCESSIBLE_CONTEXTS_MAP = "accessibleContexts";
}
