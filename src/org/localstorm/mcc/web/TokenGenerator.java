package org.localstorm.mcc.web;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author localstorm
 */
public class TokenGenerator {

    public static String getToken()
    {
        return RandomStringUtils.randomAlphabetic(25);
    }
    
}
