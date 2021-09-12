/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.snmp.beans.SnmpTarget;
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
/*     */ 
/*     */ public class MibUploadAction
/*     */   extends Action
/*     */ {
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  41 */     ActionMessages messages = new ActionMessages();
/*  42 */     AMConnectionPool.getInstance();ResultSet result = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='FILEUPLOADENABLED'");
/*  43 */     if (result.next())
/*     */     {
/*  45 */       request.setAttribute("enabled", result.getString("VALUE"));
/*     */     }
/*     */     
/*  48 */     String uploadenabled = DBUtil.getServerConfigValue("am.upload.enabled");
/*     */     
/*  50 */     if ((uploadenabled == null) || (uploadenabled.equals("false"))) {
/*  51 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.restricted"));
/*  52 */       saveMessages(request, messages);
/*  53 */       if (request.getParameter("returnpath") != null) {
/*  54 */         return new ActionForward(request.getParameter("returnpath"));
/*     */       }
/*     */       
/*  57 */       return new ActionForward("Tile.AdminConf");
/*     */     }
/*     */     
/*  60 */     if ((form instanceof AMActionForm))
/*     */     {
/*     */ 
/*  63 */       AMActionForm theForm = (AMActionForm)form;
/*     */       
/*     */ 
/*  66 */       FormFile file = theForm.getTheFile();
/*  67 */       if (file == null)
/*     */       {
/*     */ 
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
/*  95 */           uploadDir = "./mibs/";
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
/*     */         
/* 109 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.success", fileName));
/* 110 */         saveMessages(request, messages);
/* 111 */         SnmpTarget snmptarget = new SnmpTarget();
/* 112 */         snmptarget.loadMibs(FaultUtil.getMibsToLoad());
/*     */       }
/*     */       catch (FileNotFoundException fnfe)
/*     */       {
/* 116 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 117 */         saveMessages(request, messages);
/* 118 */         return mapping.findForward("success");
/*     */       }
/*     */       catch (IOException ioe) {
/* 121 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 122 */         saveMessages(request, messages);
/* 123 */         return mapping.findForward("success");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 129 */       file.destroy();
/*     */       
/*     */ 
/* 132 */       if (request.getParameter("returnpath") != null)
/*     */       {
/* 134 */         return new ActionForward(request.getParameter("returnpath"));
/*     */       }
/* 136 */       return mapping.findForward("success");
/*     */     }
/*     */     
/*     */ 
/* 140 */     return mapping.findForward("success");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MibUploadAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */