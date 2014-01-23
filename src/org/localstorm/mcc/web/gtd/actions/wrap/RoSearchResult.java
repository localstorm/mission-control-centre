/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.gtd.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.web.gtd.backend.RelevantObject;

/**
 *
 * @author localstorm
 */
public class RoSearchResult {

    private List<RelevantObject<Note>> textualNotes;
    private List<RelevantObject<Note>> urlNotes;
    private List<RelevantObject<FileAttachment>> files;

    private List<Note> textual;
    private List<Note> url;
    private List<FileAttachment> file;

    public RoSearchResult() {
        this.textualNotes = new ArrayList<RelevantObject<Note>>();
        this.urlNotes     = new ArrayList<RelevantObject<Note>>();
        this.files        = new ArrayList<RelevantObject<FileAttachment>>();
    }

    public void addFileResult(FileAttachment file, float relevance) {
        this.files.add(new RelevantObject<FileAttachment>(file, relevance));
    }

    public void addTextualNote(Note note, float relevance) {
        this.textualNotes.add(new RelevantObject<Note>(note, relevance));
    }

    public void addUrlNote(Note note, float relevance) {
        this.urlNotes.add(new RelevantObject<Note>(note, relevance));
    }

    public void close() // TaskHit!
    {
        this.textual = this.getOrderedNotes(this.textualNotes);
        this.url     = this.getOrderedNotes(this.urlNotes);
        this.file    = this.getOrderedFiles(this.files);
    }

    public Collection<FileAttachment> getFilesFound() {
        return this.file;
    }

    public Collection<Note> getTextualNotesFound(){
        return this.textual;
    }

    public Collection<Note> getUrlNotesFound(){
        return this.url;
    }

    public boolean isEmpty() {
        return textualNotes.isEmpty() && urlNotes.isEmpty() && files.isEmpty();
    }

    private List<FileAttachment> getOrderedFiles(List<RelevantObject<FileAttachment>> files) {
        Collections.sort(files);
        List<FileAttachment> ordered = new ArrayList<FileAttachment>();
        for (RelevantObject<FileAttachment> r : files) {
            ordered.add(r.getObject());
        }

        return ordered;
    }

    private List<Note> getOrderedNotes(List<RelevantObject<Note>> notes) {
        Collections.sort(notes);
        List<Note> ordered = new ArrayList<Note>();
        for (RelevantObject<Note> r : notes) {
            ordered.add(r.getObject());
        }

        return ordered;
    }

}
