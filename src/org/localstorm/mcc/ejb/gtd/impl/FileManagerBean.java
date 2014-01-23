package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.entity.FileToRefObject;
import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.gtd.dao.FileDao;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.gtd.entity.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless 
public class FileManagerBean implements FileManagerLocal
{
    public static final Logger log = Logger.getLogger(FileManagerBean.class);
    
    @Resource(mappedName=Constants.DEFAULT_DS)
    private DataSource ds;
    
    public FileManagerBean()
    {
    }
    
    @Override
    public void attachToObject(FileAttachment fa, ReferencedObject ro, InputStream is)
    {
        FileDao fileDao = new FileDao(ds);

        try {
            em.persist(fa);
            em.flush();
            Integer fileId = fa.getId();

            FileToRefObject link = new FileToRefObject(fa, ro);
            em.persist(link);

            fileDao.uploadFile(is, fileId);
        } catch(Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<FileAttachment> findAllByObject(ReferencedObject ro) {
        Query uq = em.createNamedQuery(FileToRefObject.Queries.FIND_FILES_BY_OBJECT);
        uq.setParameter(FileToRefObject.Properties.OBJECT, ro);

        return (List<FileAttachment>) uq.getResultList();
    }

    @Override
    public FileAttachment findById(Integer id)
    {
        return em.find(FileAttachment.class, id);
    }

    @Override
    public ReferencedObject findByFileAttachment(FileAttachment fa)
    {
        Query uq = em.createNamedQuery(FileToRefObject.Queries.FIND_OBJECT_BY_FILE);
        uq.setParameter(FileToRefObject.Properties.FILE, fa);
        return (ReferencedObject) uq.getSingleResult();
    }

    @Override
    public void download(FileAttachment fa, OutputStream os) throws IOException 
    {
        try {
            Integer fileId = fa.getId();
            FileDao fileDao = new FileDao(ds);
            fileDao.downloadFile(os, fileId);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void detach(FileAttachment fa, ReferencedObject ro) 
    {
        try {
            FileDao fileDao = new FileDao(ds);
            fileDao.deleteBody(fa.getId());

            Query uq = em.createNamedQuery(FileToRefObject.Queries.FIND_LINKS_BY_FILE);
            uq.setParameter(FileToRefObject.Properties.FILE, fa);  
            List<FileToRefObject> links = (List<FileToRefObject>) uq.getResultList();
            
            for (FileToRefObject fto: links) {
                em.remove(fto);
            }
            
            em.remove(em.find(FileAttachment.class, fa.getId()));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reattach(FileAttachment att, ReferencedObject ro)
    {
        Query lq = em.createNamedQuery(FileToRefObject.Queries.FIND_LINKS_BY_FILE);
        lq.setParameter(FileToRefObject.Properties.FILE,   att);

        List<FileToRefObject> list = lq.getResultList();

        for (FileToRefObject n: list)
        {
            n.setRefObject(ro);
            em.merge(ro);
        }
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
