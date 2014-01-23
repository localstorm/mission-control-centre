package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.NoteManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/obj/note/DetachNoteRefObj")
public class RefObjDetachNoteActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int objectId;
    
    @Validate( required=true )
    private int noteId;
    
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }


    @DefaultHandler
    @Logged
    public Resolution handling() throws Exception {
        
        ReferencedObject ro = super.getRefObjectManager().findById(this.getObjectId());
        NoteManager nm      = super.getNoteManager();
        Note note           = nm.findById(this.getNoteId());
        GtdClipboard clip      = super.getClipboard();

        clip.pickNote(this.getNoteId());
        nm.detach(note, ro);
        
        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncomingParameters.OBJECT_ID, this.getObjectId());
        }
        return rr;
    }

    public static interface IncomingParameters {
        public static final String OBJECT_ID = "objectId";
        public static final String NOTE_ID = "noteId";
    }
    
}
