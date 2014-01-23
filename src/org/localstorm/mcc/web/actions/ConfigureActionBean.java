package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/Configure")
public class ConfigureActionBean extends BaseActionBean {


    @DefaultHandler
    @Logged
    public Resolution configure() {
        return new ForwardResolution( Views.CONFIGURE );
    }
    
}