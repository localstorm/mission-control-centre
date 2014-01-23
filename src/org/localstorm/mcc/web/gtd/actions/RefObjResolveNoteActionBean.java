package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.NoteManager;
import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/obj/note/ResolveRefObjNote")
public class RefObjResolveNoteActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private String action;

    @Validate( required=true )
    private Integer objectId;

    @Validate( required=true )
    private Integer noteId;

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public Integer getObjectId()
    {
        return objectId;
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
    }

    public Integer getNoteId()
    {
        return noteId;
    }

    public void setNoteId(Integer noteId)
    {
        this.noteId = noteId;
    }
    
    @DefaultHandler
    @Logged
    public Resolution handling() throws Exception
    {
        Action      a  = Action.valueOf(this.getAction());
        
        RefObjectManager rom = super.getRefObjectManager();
        NoteManager nm = super.getNoteManager();
        GtdClipboard clip = super.getClipboard();

        Note             no = null;
        ReferencedObject ro = rom.findById(this.getObjectId());
        
        switch (a)
        {
            case CUT_NOTE:
                no = nm.findById(this.getNoteId());
                clip.copyNote(no);
                break;
            case PASTE_NOTES:
                for (Note note : clip.getNotes()) {
                    nm.reattach(note, ro);
                }
                clip.clearNotes();
                break;
            default:
                throw new RuntimeException("Unexpected case!");
        }

        RedirectResolution rr = new RedirectResolution(RefObjViewActionBean.class);
        {
            rr.addParameter(RefObjViewActionBean.IncomingParameters.OBJECT_ID, this.getObjectId());
        }

        return rr;
    }

    public static interface IncomingParameters {
        public static final String OBJECT_ID     = "destId";
        public static final String NOTE_ID       = "noteId";
        public static final String ACTION        = "action";
    }

    private static enum Action
    {
        CUT_NOTE,
        PASTE_NOTES
    }
    
}
