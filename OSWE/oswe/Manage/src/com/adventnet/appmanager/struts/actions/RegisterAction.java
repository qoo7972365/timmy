/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.filter.UriCollector;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AMServerStartup;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.tools.prevalent.CMDClass;
/*     */ import com.adventnet.tools.prevalent.DataClass;
/*     */ import com.adventnet.tools.prevalent.InputFileParser;
/*     */ import com.adventnet.tools.prevalent.LUtil;
/*     */ import com.adventnet.tools.prevalent.User;
/*     */ import com.adventnet.tools.prevalent.Validation;
/*     */ import com.adventnet.tools.prevalent.Wield;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
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
/*     */ public class RegisterAction
/*     */   extends Action
/*     */ {
/*     */   public RegisterAction() {}
/*     */   
/*     */   public RegisterAction(String aa) {}
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  72 */     ActionMessages messages = new ActionMessages();
/*  73 */     String success = "false";
/*  74 */     boolean reg = false;
/*  75 */     if ((form instanceof AMActionForm))
/*     */     {
/*  77 */       AMActionForm theForm = (AMActionForm)form;
/*     */       
/*     */ 
/*  80 */       FormFile file = theForm.getTheFile();
/*     */       
/*  82 */       if (file == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */         System.out.println("File obtained is empty");
/*  92 */         return mapping.findForward("success");
/*     */       }
/*     */       
/*  95 */       String fileName = "RegisteredLicense.xml";
/*     */       
/*     */ 
/*  98 */       String contentType = file.getContentType();
/*     */       
/*     */ 
/* 101 */       String size = file.getFileSize() + " bytes";
/*     */       
/* 103 */       String data = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 108 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 109 */         InputStream stream = file.getInputStream();
/*     */         
/* 111 */         String uploadDir = "./classes/";
/* 112 */         OutputStream bos = new FileOutputStream(uploadDir + fileName);
/* 113 */         int bytesRead = 0;
/* 114 */         byte[] buffer = new byte[' '];
/* 115 */         while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/* 116 */           bos.write(buffer, 0, bytesRead);
/*     */         }
/* 118 */         bos.close();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 123 */         stream.close();
/* 124 */         System.out.println("File uploaded ");
/* 125 */         String extra = request.getParameter("extra");
/*     */         
/* 127 */         reg = registerLicense(messages, fileName, extra);
/*     */         try {
/* 129 */           new AMCacheHandler().cacheAMSProps();
/*     */         } catch (Exception e) {
/* 131 */           e.printStackTrace();
/*     */         }
/* 133 */         saveMessages(request, messages);
/*     */ 
/*     */       }
/*     */       catch (FileNotFoundException fnfe)
/*     */       {
/* 138 */         fnfe.printStackTrace();
/* 139 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 140 */         saveMessages(request, messages);
/* 141 */         return mapping.findForward("success");
/*     */       } catch (IOException ioe) {
/* 143 */         ioe.printStackTrace();
/* 144 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/* 145 */         saveMessages(request, messages);
/* 146 */         return mapping.findForward("success");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 153 */       forITMSandApm();
/*     */       
/* 155 */       if (reg)
/*     */       {
/* 157 */         success = "true";
/* 158 */         request.setAttribute("success", success);
/*     */       }
/* 160 */       return mapping.findForward("success");
/*     */     }
/*     */     
/* 163 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void forITMSandApm()
/*     */   {
/*     */     try
/*     */     {
/* 172 */       FreeEditionDetails f = FreeEditionDetails.getFreeEditionDetails();
/* 173 */       if (f.getCategory() == null)
/*     */       {
/* 175 */         AMServerStartup.initFreeEdition();
/*     */       }
/* 177 */       Constants.setCategorytype(f.getCategory());
/* 178 */       Constants.setUserType(f.getUserType());
/* 179 */       Constants.callFirst();
/* 180 */       Constants.callAfterMysqlStartUp();
/*     */       
/* 182 */       Constants.loadDiscoveryFile();
/*     */       
/* 184 */       DataCollectionControllerUtil.setUnmanaged_nodes(DataCollectionControllerUtil.getUnManagedNodes());
/* 185 */       DataCollectionControllerUtil.setManaged_nodes(DataCollectionControllerUtil.getManagedNodes());
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 189 */       exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean registerLicense(String filename)
/*     */   {
/* 195 */     ActionMessages messages = new ActionMessages();
/* 196 */     String extra = null;
/* 197 */     return registerLicense(messages, filename, extra);
/*     */   }
/*     */   
/*     */   public void regLicense(String filename)
/*     */   {
/* 202 */     ActionMessages messages = new ActionMessages();
/* 203 */     String extra = null;
/* 204 */     registerLicense(messages, filename, extra);
/* 205 */     forITMSandApm();
/*     */   }
/*     */   
/*     */   private boolean registerLicense(ActionMessages messages, String filename, String extra)
/*     */   {
/* 210 */     CMDClass cmd = new CMDClass();
/* 211 */     Vector users = cmd.getUserList("." + File.separator + "classes" + File.separator + filename);
/* 212 */     if ((users == null) || (users.size() == 0))
/*     */     {
/* 214 */       AMLog.debug("REGISTER ACTION: Users is null or Users size is 0");
/* 215 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("license.registration.invalidfile"));
/* 216 */       return false;
/*     */     }
/* 218 */     String user = (String)users.elementAt(0);
/* 219 */     LUtil.setISMP(true);
/* 220 */     Wield w = Wield.getInstance();
/* 221 */     Validation valid = Validation.getInstance();
/* 222 */     System.setProperty("IsWeb", "true");
/* 223 */     FreeEditionDetails freeEd = FreeEditionDetails.getFreeEditionDetails();
/* 224 */     boolean success = valid.doValidation(".", user, "." + File.separator + "classes" + File.separator + filename, false, false);
/* 225 */     if (Locale.getDefault().toString().equals("zh_CN"))
/*     */     {
/*     */       try
/*     */       {
/* 229 */         InputFileParser parser = null;
/*     */         try
/*     */         {
/* 232 */           parser = new InputFileParser("." + File.separator + "classes" + File.separator + filename);
/*     */         }
/*     */         catch (Exception exp)
/*     */         {
/* 236 */           exp.printStackTrace();
/*     */         }
/* 238 */         DataClass data = parser.getDataClass();
/* 239 */         ArrayList users1 = data.getUsers();
/* 240 */         User user1 = data.getUserObject((String)users1.get(0));
/* 241 */         user1.getMacId();
/*     */         
/* 243 */         if ((user1.getMacId() != null) && (user1.getMacId().equals("NO")) && (EnterpriseUtil.getServerType().equals("NORM")))
/*     */         {
/* 245 */           AMLog.debug("REGISTER ACTION: Registration failed because of macid validation failure");
/* 246 */           String message = FormatUtil.getString("license.registration.failed", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/* 247 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message));
/* 248 */           return false;
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 253 */         ex.printStackTrace();
/*     */       }
/*     */     }
/* 256 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/*     */ 
/* 259 */       if ((valid.getProductCategoryString().equals("MSPEdition")) || (valid.getProductCategoryString().equals("EnterpriseEdition")))
/*     */       {
/*     */ 
/* 262 */         EnterpriseUtil.setLicenseUpgradeRequiredForManagedServers(true);
/* 263 */         CommDBUtil.setLicenseStatusInMASServer();
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 268 */         System.out.println("Enterprise: Non Enterprise License used.." + valid.getProductCategoryString());
/* 269 */         String message = FormatUtil.getString("license.registration.failed.nonenterprise.used", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/* 270 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message));
/* 271 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 275 */     if (success) {
/* 276 */       valid.copyLicenseFile(".", "." + File.separator + "classes" + File.separator + filename);
/*     */       
/*     */       try
/*     */       {
/* 280 */         UriCollector.setAccess(true);
/* 281 */         System.out.println("The License file has been registered successfully ");
/* 282 */         System.out.println("Company Name    : " + w.getCompanyName());
/* 283 */         System.out.println("License Type    : " + w.getLicenseTypeString() + "(" + w.getLicenseType() + ")");
/* 284 */         System.out.println("User Name       : " + w.getUserName());
/* 285 */         System.out.println("Product Name    : " + w.getProductName());
/* 286 */         System.out.println("Product Version : " + w.getProductVersion());
/* 287 */         System.out.println("Product Category: " + w.getProductCategoryString());
/* 288 */         FreeEditionDetails.getFreeEditionDetails().update();
/* 289 */         com.adventnet.appmanager.server.framework.AMServerFramework.toXtraMonitorsPage = true;
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 294 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("license.registration.success"));
/* 295 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("license.registration.information"));
/* 296 */       if (extra != null)
/*     */       {
/* 298 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("license.registration.additionalinfo"));
/*     */       }
/*     */     }
/*     */     else {
/* 302 */       String message1 = FormatUtil.getString("license.registration.failed", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/* 303 */       System.out.println("<br>The error code " + LUtil.getErrorCode());
/* 304 */       System.out.println("<br>The error message " + LUtil.getErrorMessage());
/*     */       
/* 306 */       System.out.println("<br>The error code " + LUtil.getDetailedErrorMessage());
/*     */       
/* 308 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message1, LUtil.getErrorCode(), LUtil.getErrorMessage()));
/*     */       
/*     */ 
/* 311 */       FreeEditionDetails.getFreeEditionDetails().update();
/*     */     }
/* 313 */     return success;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\RegisterAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */