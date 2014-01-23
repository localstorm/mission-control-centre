package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface FileManager 
{
    public static final String BEAN_NAME = "FileManagerBean";
    
    public void download(FileAttachment fa, OutputStream os) throws IOException;
    public void attachToObject(FileAttachment fa, ReferencedObject ro, InputStream is);
    public void detach(FileAttachment fa, ReferencedObject ro);
    
    public Collection<FileAttachment> findAllByObject(ReferencedObject ro);
    
    public FileAttachment findById(Integer id);

    public ReferencedObject findByFileAttachment(FileAttachment fa);

    public void reattach(FileAttachment att, ReferencedObject ro);
}
