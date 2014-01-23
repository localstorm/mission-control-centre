package org.localstorm.mcc.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Alexey Kuznetsov
 */
public final class WebUtil
{
    @SuppressWarnings("unchecked")
    public static Collection<Integer> stringToIds(String ids)
    {
        if (ids==null) {
            return Collections.EMPTY_LIST;
        }

        String[] idsSplt = ids.split(",");
        ArrayList<Integer> arr = new ArrayList<Integer>(idsSplt.length);

        for (String idss: idsSplt) {
            arr.add(Integer.parseInt(idss));
        }
        return arr;
    }
}
