package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.NoteToObject;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;

/**
 *
 * @author localstorm
 */
@Stateless
public class NoteManagerBean implements NoteManagerLocal
{

    @Override
    public void attachToObject(Note note, ReferencedObject obj) {
        em.persist(note);
        em.persist(new NoteToObject(note, obj));
    }
   
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Note> findAllByObject(ReferencedObject obj) {
        Query lq = em.createNamedQuery(NoteToObject.Queries.FIND_NOTES_BY_OBJECT);
        lq.setParameter(NoteToObject.Properties.OBJECT, obj);

        return (List<Note>) lq.getResultList();
    }
    
    @Override
    public Note findById(int noteId) {
        return em.find(Note.class, noteId);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void detach(Note note, ReferencedObject obj)
    {
        Query lq = em.createNamedQuery(NoteToObject.Queries.FIND_LINKS_BY_OBJECT_AND_NOTE);
        lq.setParameter(NoteToObject.Properties.OBJECT, obj);
        lq.setParameter(NoteToObject.Properties.NOTE,   note);
        
        List<NoteToObject> list = lq.getResultList();
        
        for (NoteToObject n: list)
        {
            em.remove(n);
        }
        
        note = this.findById(note.getId());
        em.remove(note);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void reattach(Note note, ReferencedObject ro)
    {
        Query lq = em.createNamedQuery(NoteToObject.Queries.FIND_LINKS_BY_NOTE);
        lq.setParameter(NoteToObject.Properties.NOTE,   note);

        List<NoteToObject> list = lq.getResultList();

        for (NoteToObject n: list)
        {
            n.setRefObject(ro);
            em.merge(ro);
        }
    }

    @Override
    public ReferencedObject findByNote(Note note)
    {
        Query uq = em.createNamedQuery(NoteToObject.Queries.FIND_OBJECT_BY_NOTE);
        uq.setParameter(NoteToObject.Properties.NOTE, note);
        return (ReferencedObject) uq.getSingleResult();
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
