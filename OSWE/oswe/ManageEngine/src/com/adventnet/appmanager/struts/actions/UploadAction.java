/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.SNMPUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UploadAction
/*     */   extends Action
/*     */ {
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  40 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/*  41 */       return mapping.findForward("accessRestricted");
/*     */     }
/*  43 */     ActionMessages messages = new ActionMessages();
/*  44 */     AMConnectionPool.getInstance();ResultSet result = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='FILEUPLOADENABLED'");
/*  45 */     if (result.next())
/*     */     {
/*  47 */       request.setAttribute("enabled", result.getString("VALUE"));
/*     */     }
/*     */     
/*  50 */     String uploadenabled = DBUtil.getServerConfigValue("am.upload.enabled");
/*     */     
/*  52 */     if ((uploadenabled == null) || (uploadenabled.equals("false"))) {
/*  53 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.restricted"));
/*  54 */       saveMessages(request, messages);
/*  55 */       if (request.getParameter("returnpath") != null) {
/*  56 */         return new ActionForward(request.getParameter("returnpath"));
/*     */       }
/*  58 */       return new ActionForward("Tile.AdminConf");
/*     */     }
/*     */     
/*  61 */     if ((form instanceof AMActionForm))
/*     */     {
/*     */ 
/*  64 */       AMActionForm theForm = (AMActionForm)form;
/*     */       
/*     */ 
/*  67 */       FormFile file = theForm.getTheFile();
/*  68 */       if (file == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  73 */         return mapping.findForward("success");
/*     */       }
/*     */       
/*  76 */       String fileName = file.getFileName();
/*     */       
/*     */ 
/*  79 */       String contentType = file.getContentType();
/*     */       
/*     */ 
/*     */ 
/*  83 */       String size = file.getFileSize() + " bytes";
/*     */       
/*  85 */       String data = null;
/*     */       
/*     */       try
/*     */       {
/*  89 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  90 */         InputStream stream = file.getInputStream();
/*     */         
/*  92 */         String uploadDir = request.getParameter("uploadDir");
/*  93 */         if (uploadDir == null)
/*     */         {
/*  95 */           uploadDir = "../lib/ext/";
/*     */         }
/*  97 */         OutputStream bos = new FileOutputStream(uploadDir + fileName);
/*  98 */         int bytesRead = 0;
/*  99 */         byte[] buffer = new byte[' '];
/* 100 */         while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/* 101 */           bos.write(buffer, 0, bytesRead);
/*     */         }
/* 103 */         bos.close();
/* 104 */         data = "The file has been written to \"" + uploadDir + fileName + "\"";
/*     */         
/*     */ 
/* 107 */         stream.close();
/* 108 */         if (uploadDir.indexOf("mibs") != -1) {
/* 109 */           SNMPUtil.loadMib(fileName);
/*     */         }
/* 111 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.success", fileName));
/* 112 */         saveMessages(request, messages);
/*     */       }
/*     */       catch (FileNotFoundException fnfe) {
/* 115 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 116 */         saveMessages(request, messages);
/* 117 */         return mapping.findForward("success");
/*     */       }
/*     */       catch (IOException ioe) {
/* 120 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 121 */         saveMessages(request, messages);
/* 122 */         return mapping.findForward("success");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 128 */       file.destroy();
/*     */       
/*     */ 
/* 131 */       if (request.getParameter("returnpath") != null)
/*     */       {
/* 133 */         return new ActionForward(request.getParameter("returnpath"));
/*     */       }
/* 135 */       return mapping.findForward("success");
/*     */     }
/*     */     
/*     */ 
/* 139 */     return mapping.findForward("success");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\UploadAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */