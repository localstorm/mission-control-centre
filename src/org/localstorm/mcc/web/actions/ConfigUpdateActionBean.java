package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;
import org.localstorm.mcc.ejb.users.UserManager;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.Views;
import org.localstorm.tools.aop.runtime.Logged;



/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UpdateConfig")
public class ConfigUpdateActionBean extends BaseActionBean {

    @Validate(required=true)
    private String oldPassword;
    
    @Validate(minlength=5, required=true)
    private String password;
    
    @Validate(minlength=5, required=true)
    private String password2;

    @ValidationMethod(when=ValidationState.NO_ERRORS) 
    public void validatePassword(ValidationErrors errors) { 
        UserManager um = this.getUserManager();

        if (um.login(this.getUser().getLogin(), oldPassword)==null) {
            errors.add(IncomingParameters.OLD_PASSW,
                       new SimpleError("Old passwrod is not valid."));
        }

        if (!password.equals(password2)) {
            errors.add(IncomingParameters.PASSWORD,
                       new SimpleError("New password values are not the same."));
        }
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    @DefaultHandler
    @Logged
    public Resolution configure() {
        UserManager um = this.getUserManager();
        um.changePassword(this.getUser(), this.password);
        return new ForwardResolution( Views.CONFIGURE );
    }
    
    public static interface IncomingParameters {
        public static final String PASSWORD  = "password";
        public static final String PASSWORD2 = "password2";
        public static final String OLD_PASSW = "oldPassword";
    }
}