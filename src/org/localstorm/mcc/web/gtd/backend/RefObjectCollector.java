package org.localstorm.mcc.web.gtd.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.entity.Note;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;
import org.localstorm.mcc.web.gtd.actions.wrap.FileAttachmentWrapper;
import org.localstorm.mcc.web.gtd.actions.wrap.NoteWrapper;
import org.localstorm.mcc.web.gtd.actions.wrap.RoSearchResult;

/**
 *
 * @author localstorm
 */
public class RefObjectCollector extends HitCollector {

    private RoSearchResult sr;
    private IndexSearcher  searcher;
    private String         idField;
    private String         typeField;
    private Map<Integer, Note> noteMap;
    private Map<Integer, FileAttachment> fileMap;
    private Map<Integer, ReferencedObject> note2ro;
    private Map<Integer, ReferencedObject> file2ro;
    
    public RefObjectCollector(IndexSearcher is,
                              String idField,
                              String typeField,
                              Collection<ReferencedObject> ros,
                              Collection<Note> notes,
                              Collection<FileAttachment> files,
                              Map<Integer, ReferencedObject> noteToRefObject,
                              Map<Integer, ReferencedObject> fileToRefObject) {
        
        this.sr = new RoSearchResult();
        this.searcher = is;
        this.idField = idField;
        this.typeField = typeField;

        this.noteMap = new HashMap<Integer, Note>();
        this.fileMap = new HashMap<Integer, FileAttachment>();

        for (Note note: notes) {
            this.noteMap.put(note.getId(), note);
        }

        for (FileAttachment file: files) {
            this.fileMap.put(file.getId(), file);
        }

        this.note2ro = noteToRefObject;
        this.file2ro = fileToRefObject;
    }

    @Override
    public void collect(int docId, float relevance) {
        try {

            Document doc = searcher.doc(docId);
            Field idf    = doc.getField(this.idField);
            Field tf     = doc.getField(this.typeField);
            
            int id       = Integer.parseInt(idf.stringValue());
            DocType type = DocType.valueOf(tf.stringValue());

            Note note    = null;
            switch (type) {
                case TEXT:
                    note = this.noteMap.get(id);
                    NoteWrapper nw1 = new NoteWrapper(note, note2ro.get(id));
                    this.sr.addTextualNote(nw1, relevance);
                    break;
                case URL:
                    note = this.noteMap.get(id);
                    NoteWrapper nw2 = new NoteWrapper(note, note2ro.get(id));
                    this.sr.addUrlNote(nw2, relevance);
                    break;
                case FILE:
                    FileAttachment file = this.fileMap.get(id);
                    FileAttachmentWrapper fw = new FileAttachmentWrapper(file, file2ro.get(id));
                    this.sr.addFileResult(fw, relevance);
                    break;
                default:
                    throw new RuntimeException("Unexpected case!");
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RoSearchResult getSearchResult() {
        this.sr.close();
        return this.sr;
    }

    private static enum DocType
    {
        FILE,
        TEXT,
        URL
    }

}
