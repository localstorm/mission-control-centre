package org.localstorm.mcc.web.gtd.actions.wrap;

import java.util.Date;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.util.EscapeUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NoteWrapper extends Note
{
    private Note note;
    private ReferencedObject ro;
    private static final long serialVersionUID = -6745552170532948365L;

    public NoteWrapper(Note note) {
        this.note = note;
    }

    public NoteWrapper(Note note, ReferencedObject ro) {
        this(note);
        this.ro = ro;
    }

    @Override
    public Integer getId() {
        return this.note.getId();
    }
    
    @Override
    public Date getCreation() {
        return this.note.getCreation();
    }

    @Override
    public String getDescription() {
        return this.note.getDescription();
    }

    @Override
    public String getNote() {
        return this.note.getNote();
    }

    @Override
    public String getType() {
        return this.note.getType();
    }

    @Override
    public void setDescription(String description) {
        this.note.setDescription(description);
    }

    @Override
    public void setNote(String note) {
        this.note.setNote(note);
    }

    @Override
    public void setType(String type) {
        this.note.setType(type);
    }

    public String getNoteHtmlEscaped() {
        return EscapeUtil.forHTML(this.getNote());
    }

    public ReferencedObject getRefObject() {
        return this.ro;
    }
}
