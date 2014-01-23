package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/EditRefObj")
public class RefObjEditActionBean extends GtdBaseActionBean {

    private Collection<ReferencedObject> refObjects;
    private Collection<ReferencedObject> archiveObjects;
    
    public Collection<ReferencedObject> getRefObjects() {
        return refObjects;
    }

    public void setRefObjects(Collection<ReferencedObject> refObjects) {
        this.refObjects = refObjects;
    }

    public Collection<ReferencedObject> getArchiveObjects() {
        return archiveObjects;
    }

    public void setArchiveObjects(Collection<ReferencedObject> archiveObjects) {
        this.archiveObjects = archiveObjects;
    }
        
    
    @DefaultHandler
    @Logged
    public Resolution filling() {
        RefObjectManager rom = super.getRefObjectManager();
        User user = super.getUser();
        
        this.setRefObjects(     rom.findOperativeByOwner(user, true) );
        this.setArchiveObjects( rom.findAllArchivedByOwner(user) );
        return new ForwardResolution( Views.EDIT_RO );
    }

}
