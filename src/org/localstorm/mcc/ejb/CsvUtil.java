package org.localstorm.mcc.ejb;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CsvUtil
{

    /**
     * Returns <code>"template,template,template..."</code> string
     * @param template String template
     * @param size How many times to copy
     * @return <code>"template,template,template..."</code> string
     */
    public static String getCsvString(String template, int size)
    {
        StringBuilder sb = new StringBuilder();

        int stop = size-1;
        for (int i=0; i<size; i++)
        {
            sb.append(template);
            if (i<stop)
            {
                sb.append(CsvUtil.COMMA);
            }
        }

        return sb.toString();
    }

    private static final String COMMA = ",";

}