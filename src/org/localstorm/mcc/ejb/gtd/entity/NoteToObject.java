package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="NOTES_TO_OBJECTS")
@NamedQueries({
    @NamedQuery(
        name = NoteToObject.Queries.FIND_OBJECT_BY_NOTE,
        query= "SELECT o.refObject FROM NoteToObject o WHERE o.note=:note"
    ),
    @NamedQuery(
        name = NoteToObject.Queries.FIND_NOTES_BY_OBJECT,
        query= "SELECT o.note FROM NoteToObject o WHERE o.refObject=:obj"
    ),
    @NamedQuery(
        name = NoteToObject.Queries.FIND_LINKS_BY_OBJECT_AND_NOTE,
        query= "SELECT o FROM NoteToObject o WHERE o.refObject=:obj and o.note=:note"
    ),
    @NamedQuery(
        name = NoteToObject.Queries.FIND_LINKS_BY_NOTE,
        query= "SELECT o FROM NoteToObject o WHERE o.note=:note"
    )
})
public class NoteToObject extends AbstractEntity implements Serializable
{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "note_id")
    private Note note;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_id")
    private ReferencedObject refObject;
    private static final long serialVersionUID = 2446491633646981850L;

    public NoteToObject() {
    }

    public NoteToObject(Note note, ReferencedObject refObject) {
        this.id        = null;
        this.note      = note;
        this.refObject = refObject;
    }
    
    public Note getNote() {
        return note;
    }

    public ReferencedObject getRefObject() {
        return refObject;
    }

    public void setRefObject(ReferencedObject ro)
    {
        this.refObject = ro;
    }

    public static class Queries {
        public static final String FIND_NOTES_BY_OBJECT = "findNotesByObject";
        public static final String FIND_LINKS_BY_OBJECT_AND_NOTE = "findLinksByObjectAndNote";
        public static final String FIND_LINKS_BY_NOTE   = "findLinksByNote";
        public static final String FIND_OBJECT_BY_NOTE  = "findObjectByNote";
    }

    public static interface Properties
    {
        public static final String OBJECT = "obj";
        public static final String NOTE   = "note";
    }
    
}
