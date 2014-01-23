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
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="FILES_TO_OBJECTS")
@NamedQueries({
    @NamedQuery(
        name = FileToRefObject.Queries.FIND_OBJECT_BY_FILE,
        query= "SELECT o.refObject FROM FileToRefObject o WHERE o.file=:"+
                FileToRefObject.Properties.FILE
    ),
    @NamedQuery(
        name = FileToRefObject.Queries.FIND_FILES_BY_OBJECT,
        query= "SELECT o.file FROM FileToRefObject o WHERE o.refObject=:"+
                FileToRefObject.Properties.OBJECT
    ),
    @NamedQuery(
        name = FileToRefObject.Queries.FIND_LINKS_BY_FILE,
        query= "SELECT o FROM FileToRefObject o WHERE o.file=:"+
                FileToRefObject.Properties.FILE
    )
})
public class FileToRefObject extends AbstractEntity implements Serializable, Identifiable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private FileAttachment file;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_id")
    private ReferencedObject refObject;
    private static final long serialVersionUID = -1258433527506114783L;

    public FileToRefObject() {
    }

    public FileToRefObject(FileAttachment fa, ReferencedObject refObject) {
        this.id        = null;
        this.file      = fa;
        this.refObject = refObject;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public ReferencedObject getRefObject() {
        return refObject;
    }

    public FileAttachment getFile() {
        return file;
    }

    public void setRefObject(ReferencedObject refObject) {
        this.refObject = refObject;
    }

    public void setFile(FileAttachment file) {
        this.file = file;
    }
    
    public static interface Queries
    {
        public static final String FIND_FILES_BY_OBJECT = "findFilesByObject";
        public static final String FIND_LINKS_BY_FILE   = "findLinksByFile";
        public static final String FIND_OBJECT_BY_FILE  = "findObjectByFile";
    }
    
    public static interface Properties 
    {
        public static final String OBJECT = "obj";
        public static final String FILE   = "file";
    }

}
