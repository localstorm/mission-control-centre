package org.localstorm.mcc.web.gtd.actions.wrap;

import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
public class FileAttachmentWrapper extends FileAttachment
{
    private FileAttachment   fa;
    private ReferencedObject ro;
    private static final long serialVersionUID = 3115378256272010804L;

    public FileAttachmentWrapper(FileAttachment fa, ReferencedObject ro) {
        this.fa = fa;
        this.ro = ro;
    }
    
    @Override
    public Integer getId() {
        return this.fa.getId();
    }

    @Override
    public String getMimeType() {
        return this.fa.getMimeType();
    }

    @Override
    public void setMimeType(String mimeType) {
        this.fa.setMimeType(mimeType);
    }

    @Override
    public String getName() {
        return this.fa.getName();
    }

    @Override
    public void setName(String name) {
        this.fa.setName(name);
    }

    @Override
    public String getDescription() {
        return this.fa.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.fa.setDescription(description);
    }

    public ReferencedObject getRefObject()
    {
        return ro;
    }

}
