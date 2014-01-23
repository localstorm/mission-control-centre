package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.entity.FileAttachment;
import org.localstorm.mcc.ejb.gtd.FileManager;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/obj/file/DownloadFile")
public class FileDownloadActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int fileId;

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        HttpServletResponse resp = this.getContext().getResponse();
        FileManager fm           = this.getFileManager();
        
        FileAttachment fa = fm.findById(fileId);
        if (fa==null)
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        
        resp.setContentType(fa.getMimeType());
        resp.addHeader("Content-Disposition", "attachment; filename=\""+fixNonAsciiString(fa.getName())+"\"");
        fm.download(fa, resp.getOutputStream());
        
        return null;
    }

    public static String fixNonAsciiString (String s) throws Exception {

        byte bytearray []  = s.getBytes();

        for (int i=0; i<bytearray.length; i++)
        {
            byte b = bytearray[i];
            if (b<0)
            {
                bytearray[i]=95; // underscore
            }
        }

        return new String(bytearray);
    }

    
    public static interface IncomingParameters {
        public static final String FILE_ID = "fileId";
    }
    
}
