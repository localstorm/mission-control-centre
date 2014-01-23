package org.localstorm.mcc.web.gtd.actions;

import java.util.Collection;
import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.FileManager;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.NoteManager;
import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.mcc.web.gtd.GtdSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/obj/EraseRefObj")
public class RefObjEraseActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int objectId;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @DefaultHandler
    @Logged
    public Resolution deletingRefObj() throws Exception {
        
       RefObjectManager rom = super.getRefObjectManager();
       ReferencedObject ro  = rom.findById(this.getObjectId());
       NoteManager      nm  = super.getNoteManager();
       FileManager      fm  = super.getFileManager();

       Collection<Note>           notes = nm.findAllByObject(ro);
       Collection<FileAttachment> files = fm.findAllByObject(ro);

       GtdClipboard clip = super.getClipboard();
       for (Note note: notes)
       {
            clip.pickNote(note.getId());
       }
       for (FileAttachment file: files)
       {
            clip.pickFile(file.getId());
       }

       rom.remove(ro);
       
       SessionUtil.clear(getSession(), GtdSessionKeys.REFERENCE_OBJECTS);
       return new RedirectResolution(RefObjEditActionBean.class);
    }
}
