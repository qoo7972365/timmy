/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.server.discovery.BulkDiscoveryJob;
/*      */ import com.adventnet.appmanager.server.framework.AMServerStartup;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.manageengine.appmanager.server.discovery.DiscoveryProcess;
/*      */ import com.manageengine.appmanager.server.discovery.DiscoveryProcess.DiscoveryThread;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BulkAddMonitors
/*      */   extends DispatchAction
/*      */ {
/*   61 */   public static String usermessage = "";
/*      */   
/*      */   public ActionForward showBulkImportForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   65 */     boolean sheduleridle = isSchedulerIdle();
/*      */     
/*      */ 
/*      */ 
/*   69 */     String bulkDiscoveryStarted = (String)request.getAttribute("bulkdiscoverystarted");
/*   70 */     String productEdition = Constants.getCategorytype();
/*   71 */     request.setAttribute("productEdition", productEdition);
/*   72 */     if ((!sheduleridle) && (bulkDiscoveryStarted != null) && (!bulkDiscoveryStarted.equals("success")))
/*      */     {
/*      */ 
/*   75 */       ActionErrors errors = new ActionErrors();
/*   76 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(usermessage));
/*   77 */       saveErrors(request, errors);
/*      */     }
/*   79 */     if (!Constants.sqlManager)
/*      */     {
/*   81 */       getConfMonitors(request);
/*      */     }
/*   83 */     return new ActionForward("/jsp/Bulk_AddMonitors.jsp?showUploadForm=true");
/*      */   }
/*      */   
/*      */   public ActionForward uploadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   88 */     boolean sheduleridle = isSchedulerIdle();
/*   89 */     AMActionForm theForm = (AMActionForm)form;
/*   90 */     String monitortype = theForm.getType();
/*   91 */     if (!sheduleridle)
/*      */     {
/*      */ 
/*   94 */       ActionErrors errors = new ActionErrors();
/*   95 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(usermessage));
/*   96 */       saveErrors(request, errors);
/*   97 */       return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */     }
/*      */     
/*      */ 
/*  101 */     ActionMessages messages = new ActionMessages();
/*      */     
/*  103 */     FormFile file = theForm.getTheFile();
/*  104 */     if (file == null)
/*      */     {
/*  106 */       return mapping.findForward("success");
/*      */     }
/*      */     
/*  109 */     String fileName = file.getFileName();
/*      */     
/*  111 */     if (!fileName.endsWith(".csv"))
/*      */     {
/*  113 */       ActionErrors errors = new ActionErrors();
/*  114 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.admin.bulkimport.file.notsupported.text")));
/*  115 */       saveErrors(request, errors);
/*  116 */       return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */     }
/*      */     
/*      */ 
/*  120 */     String contentType = file.getContentType();
/*      */     
/*      */ 
/*      */ 
/*  124 */     String size = file.getFileSize() + " bytes";
/*  125 */     String data = null;
/*      */     
/*      */     try
/*      */     {
/*  129 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  130 */       InputStream stream = file.getInputStream();
/*      */       
/*  132 */       String uploadDir = "./bulkadd/";
/*  133 */       File bulk_dir = new File("." + File.separator + "bulkadd");
/*  134 */       if (!bulk_dir.exists())
/*      */       {
/*  136 */         bulk_dir.mkdir();
/*      */       }
/*  138 */       if (uploadDir == null)
/*      */       {
/*  140 */         uploadDir = "../lib/ext/";
/*      */       }
/*  142 */       OutputStream bos = new FileOutputStream(uploadDir + fileName);
/*  143 */       int bytesRead = 0;
/*  144 */       byte[] buffer = new byte[' '];
/*  145 */       while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/*  146 */         bos.write(buffer, 0, bytesRead);
/*      */       }
/*  148 */       bos.close();
/*  149 */       data = "The file has been written to \"" + uploadDir + fileName + "\"";
/*      */       
/*      */ 
/*  152 */       stream.close();
/*      */     }
/*      */     catch (FileNotFoundException fnfe) {
/*  155 */       fnfe.printStackTrace();
/*  156 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/*  157 */       saveMessages(request, messages);
/*  158 */       return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm");
/*      */     }
/*      */     catch (IOException ioe) {
/*  161 */       ioe.printStackTrace();
/*  162 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/*  163 */       saveMessages(request, messages);
/*  164 */       return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm");
/*      */     }
/*      */     
/*      */ 
/*  168 */     file.destroy();
/*  169 */     return showInfoinTableFormat(monitortype, fileName, mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward showInfoinTableFormat(String monitortype, String filename, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  176 */       String filename_loc = "." + File.separator + "bulkadd" + File.separator + filename;
/*  177 */       HashMap result = readfromCSV_todisplay(filename_loc);
/*  178 */       String headerpresent = (String)result.get("headerpresent");
/*  179 */       if (headerpresent.equals("false"))
/*      */       {
/*  181 */         deletefile(filename_loc);
/*  182 */         ActionErrors errors = new ActionErrors();
/*  183 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.admin.bulkimport.header.missing.text")));
/*  184 */         saveErrors(request, errors);
/*  185 */         return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */       }
/*  187 */       if (headerpresent.equals("NoCustomerSiteAvailable"))
/*      */       {
/*  189 */         deletefile(filename_loc);
/*  190 */         ActionErrors errors = new ActionErrors();
/*  191 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.admin.bulkimport.monitorgroup.missing.text")));
/*  192 */         saveErrors(request, errors);
/*  193 */         return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */       }
/*  195 */       ArrayList data_table = (ArrayList)result.get("monitors");
/*      */       
/*  197 */       if ((Constants.isIt360) && (monitortype != null) && (!monitortype.equals("")))
/*      */       {
/*      */ 
/*      */ 
/*  201 */         Properties props = new Properties();
/*  202 */         int osIndex; if (monitortype.equalsIgnoreCase("SYSTEM"))
/*      */         {
/*  204 */           if (headerpresent.equals("true"))
/*      */           {
/*  206 */             ArrayList headerlist = (ArrayList)result.get("headerlist");
/*  207 */             osIndex = headerlist.indexOf("OperatingSystem");
/*  208 */             if (osIndex != -1)
/*      */             {
/*  210 */               for (Object temp : data_table)
/*      */               {
/*  212 */                 ArrayList monitorDetails = (ArrayList)temp;
/*  213 */                 if ((monitorDetails != null) && (!monitorDetails.isEmpty()))
/*      */                 {
/*  215 */                   String os = monitorDetails.get(osIndex).toString();
/*  216 */                   if ((os != null) && (!os.trim().equals("")))
/*      */                   {
/*  218 */                     String actResourceType = ClientDBUtil.getActualResourceType(os);
/*  219 */                     if ((actResourceType != null) && (!actResourceType.trim().equals("")))
/*      */                     {
/*  221 */                       int deviceCount = 1;
/*  222 */                       if (props.containsKey(actResourceType))
/*      */                       {
/*  224 */                         deviceCount = Integer.parseInt(props.getProperty(actResourceType)) + 1;
/*      */                       }
/*  226 */                       props.setProperty(actResourceType, Integer.toString(deviceCount));
/*      */                     }
/*      */                     
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/*  236 */           props.setProperty(ClientDBUtil.getActualResourceType(monitortype), Integer.toString(data_table.size()));
/*      */         }
/*  238 */         if ((props != null) && (!props.isEmpty()))
/*      */         {
/*  240 */           Vector<String> violMessages = ClientDBUtil.getLicenseViolationMessages(props, "BULK_IMPORT");
/*  241 */           if ((violMessages != null) && (!violMessages.isEmpty()))
/*      */           {
/*  243 */             deletefile(filename_loc);
/*  244 */             ActionErrors errors = new ActionErrors();
/*  245 */             for (String message : violMessages)
/*      */             {
/*  247 */               errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(message));
/*      */             }
/*  249 */             saveErrors(request, errors);
/*  250 */             return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  255 */       ArrayList headerlist = (ArrayList)result.get("headerlist");
/*      */       
/*  257 */       boolean isHeaderFomatCorrect = false;
/*  258 */       Hashtable confmonitors = Constants.getCustomMonitors();
/*  259 */       ArrayList passWordColumns = new ArrayList();
/*  260 */       if (confmonitors.containsKey(monitortype)) {
/*  261 */         if (monitortype.equalsIgnoreCase("OfficeSharePointServer")) {
/*  262 */           headerlist.add("allowEdit");
/*      */         }
/*  264 */         isHeaderFomatCorrect = checkConfHeader(monitortype, headerlist, passWordColumns);
/*  265 */         if (monitortype.equalsIgnoreCase("OfficeSharePointServer")) {
/*  266 */           headerlist.remove("allowEdit");
/*      */         }
/*      */       }
/*      */       else {
/*  270 */         isHeaderFomatCorrect = checkHeader(monitortype, headerlist);
/*      */       }
/*      */       
/*  273 */       if (!isHeaderFomatCorrect)
/*      */       {
/*  275 */         deletefile(filename_loc);
/*  276 */         ActionErrors errors = new ActionErrors();
/*  277 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.admin.bulkimport.header.parameter.missing.text")));
/*  278 */         saveErrors(request, errors);
/*  279 */         return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm&type=" + monitortype);
/*      */       }
/*      */       
/*      */ 
/*  283 */       int modeOfMonIdx = headerlist.indexOf("ModeofMonitoring");
/*  284 */       int eventLogStatusIdx = headerlist.indexOf("EnableEventLogMonitoring");
/*  285 */       if ((modeOfMonIdx != -1) && (eventLogStatusIdx != -1) && (data_table != null) && (data_table.size() > 0)) {
/*  286 */         for (int i = 0; i < data_table.size(); i++) {
/*  287 */           ArrayList eachMonList = (ArrayList)data_table.get(i);
/*  288 */           if ((eachMonList != null) && (eachMonList.size() != 0))
/*      */           {
/*      */ 
/*  291 */             String mode = (String)eachMonList.get(modeOfMonIdx);
/*      */             try {
/*  293 */               String s1 = (String)eachMonList.get(eventLogStatusIdx);
/*  294 */               if ((mode != null) && (mode.equalsIgnoreCase("wmi"))) {
/*  295 */                 if ((s1 == null) || (s1.trim().length() == 0)) {
/*  296 */                   eachMonList.set(eventLogStatusIdx, "no");
/*      */                 }
/*      */               } else {
/*  299 */                 eachMonList.set(eventLogStatusIdx, " - ");
/*      */               }
/*      */             } catch (IndexOutOfBoundsException ex) {
/*  302 */               if ((mode != null) && (mode.equalsIgnoreCase("wmi"))) {
/*  303 */                 eachMonList.add("no");
/*      */               } else {
/*  305 */                 eachMonList.add(" - ");
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  313 */       String display_filename = "&lt;Product-Home&gt;" + File.separator + "working" + File.separator + "bulkadd" + File.separator + filename;
/*  314 */       ActionMessages messages = new ActionMessages();
/*  315 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.admin.bulkimport.upload.success.text", new String[] { OEMUtil.getOEMString("product.name"), display_filename })));
/*  316 */       saveMessages(request, messages);
/*  317 */       request.setAttribute("headerlist", headerlist);
/*  318 */       request.setAttribute("password_col", result.get("password_col"));
/*  319 */       request.setAttribute("passWordColumns", passWordColumns);
/*  320 */       request.setAttribute("data_table", data_table);
/*  321 */       request.setAttribute("filename", filename);
/*  322 */       request.setAttribute("monitortype", monitortype);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  326 */       ex.printStackTrace();
/*      */     }
/*  328 */     return new ActionForward("/jsp/Bulk_AddMonitors.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap readfromCSV_todisplay(String filename)
/*      */   {
/*  336 */     HashMap toreturn = new HashMap();
/*  337 */     String headerpresent = "false";
/*  338 */     ArrayList monitors = new ArrayList();
/*  339 */     ArrayList headerlist = new ArrayList();
/*  340 */     BufferedReader wrapperreader = null;
/*  341 */     boolean headerCaptured = false;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  346 */       File BulkMonitor_CSV = new File(filename);
/*  347 */       if (BulkMonitor_CSV.exists())
/*      */       {
/*  349 */         wrapperreader = new BufferedReader(new FileReader(BulkMonitor_CSV));
/*  350 */         String line = null;
/*  351 */         while ((line = wrapperreader.readLine()) != null)
/*      */         {
/*  353 */           line = line.trim();
/*  354 */           if (!line.equals(""))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  359 */             if (line.startsWith("#"))
/*      */             {
/*      */ 
/*  362 */               line = line.substring(1);
/*  363 */               line = line.trim();
/*  364 */               if ((!headerCaptured) && ((line.startsWith("Header:")) || (line.startsWith("header:"))))
/*      */               {
/*      */ 
/*      */                 try
/*      */                 {
/*  369 */                   line = line.substring(7);
/*  370 */                   line = line.trim();
/*  371 */                   headerCaptured = true;
/*      */                 } catch (Exception ex) {
/*  373 */                   ex.printStackTrace(); }
/*  374 */                 String[] tokens = line.split(",");
/*  375 */                 for (int x = 0; x < tokens.length; x++)
/*      */                 {
/*  377 */                   if (tokens[x].equalsIgnoreCase("Password"))
/*      */                   {
/*  379 */                     toreturn.put("password_col", String.valueOf(x));
/*      */                   }
/*  381 */                   headerlist.add(tokens[x].trim());
/*      */ 
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */             }
/*  391 */             else if (headerCaptured)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  399 */               line = line + ",stub";
/*  400 */               String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
/*  401 */               ArrayList templist = new ArrayList();
/*  402 */               for (int x = 0; x < tokens.length - 1; x++)
/*      */               {
/*  404 */                 if ((tokens[x].startsWith("\"")) && (tokens[x].endsWith("\""))) {
/*  405 */                   tokens[x] = tokens[x].substring(1, tokens[x].length() - 1);
/*      */                 }
/*  407 */                 templist.add(tokens[x].trim());
/*      */               }
/*  409 */               monitors.add(templist);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  425 */         if (wrapperreader != null) {
/*  426 */           wrapperreader.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/*  430 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  433 */       toreturn.put("monitors", monitors);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  421 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  425 */         if (wrapperreader != null) {
/*  426 */           wrapperreader.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/*  430 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  434 */     toreturn.put("headerpresent", String.valueOf(headerCaptured));
/*  435 */     toreturn.put("headerlist", headerlist);
/*  436 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward startBulkDiscovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  445 */       boolean sheduleridle = isSchedulerIdle();
/*      */       
/*  447 */       if (!sheduleridle)
/*      */       {
/*      */ 
/*  450 */         ActionErrors errors = new ActionErrors();
/*  451 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(usermessage));
/*  452 */         saveErrors(request, errors);
/*  453 */         return new ActionForward("/jsp/Bulk_AddMonitors.jsp?showUploadForm=true");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  458 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  459 */       Scheduler bulkaddSchedular = Scheduler.createScheduler("BulkAddMonitors", 3);
/*  460 */       if (!bulkaddSchedular.isAlive()) {
/*  461 */         bulkaddSchedular.start();
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  465 */       ex.printStackTrace();
/*      */     }
/*  467 */     String filename = request.getParameter("filename");
/*  468 */     String monitortype = request.getParameter("monitortype");
/*      */     
/*  470 */     filename = URLDecoder.decode(filename);
/*  471 */     String filename_loc = "." + File.separator + "bulkadd" + File.separator + filename;
/*  472 */     String logfile = "." + File.separator + "bulkadd" + File.separator + filename + "_log.txt";
/*  473 */     AMLog.writetolog(logfile, "******** " + FormatUtil.getString("am.webclient.admin.bulkimport.logfile.discoverystarted.text", new String[] { filename }) + "  *********\n");
/*      */     
/*  475 */     if (EnterpriseUtil.isAdminServer) {
/*  476 */       AMLog.debug("Bulk CSV in Admin Server");
/*  477 */       AMLog.debug("Bulk CSV in Admin Server : filename_loc : " + filename_loc);
/*  478 */       File csvFile = new File(filename_loc);
/*  479 */       DiscoveryProcess dp = new DiscoveryProcess(); DiscoveryProcess 
/*  480 */         tmp302_300 = dp;tmp302_300.getClass();DiscoveryProcess.DiscoveryThread dthread = new DiscoveryProcess.DiscoveryThread(tmp302_300, csvFile, 1, monitortype);
/*  481 */       dthread.setLogfile(logfile);
/*      */       
/*  483 */       new Thread(dthread).start();
/*      */     } else {
/*  485 */       ArrayList csvlist = new ArrayList();
/*      */       
/*  487 */       Hashtable confmonitors = Constants.getCustomMonitors();
/*      */       
/*  489 */       if (confmonitors.containsKey(monitortype))
/*      */       {
/*  491 */         csvlist = readfromCSVForConf(filename_loc, monitortype);
/*      */       }
/*      */       else
/*      */       {
/*  495 */         csvlist = readfromCSV(filename_loc);
/*      */       }
/*      */       
/*  498 */       if ((csvlist == null) || (csvlist.size() == 0))
/*      */       {
/*  500 */         AMLog.writetolog(logfile, FormatUtil.getString("am.webclient.admin.bulkimport.logfile.nomonitors.text", new String[] { filename }) + "\n");
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  505 */         HashMap dc_extra_props = new HashMap();
/*      */         
/*  507 */         dc_extra_props.put("monitortype", monitortype);
/*  508 */         dc_extra_props.put("logfile", logfile);
/*      */         
/*  510 */         if ((monitortype.equals("UrlMonitor")) || (monitortype.equals("Web Service")))
/*      */         {
/*  512 */           scheduleDiscoveryforURL(csvlist, dc_extra_props);
/*      */         }
/*      */         else
/*      */         {
/*  516 */           scheduleDiscoveryforMonitors(csvlist, dc_extra_props);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  521 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  526 */       deletefile(filename_loc);
/*  527 */       String display_filename = "&lt;Product-Home&gt;" + File.separator + "working" + File.separator + "bulkadd" + File.separator + filename;
/*  528 */       String display_logfile = display_filename + "_log.txt";
/*  529 */       usermessage = FormatUtil.getString("am.webclient.admin.bulkimport.monitoring.alreadyrunning", new String[] { filename, "<a href='/bulkadd/" + filename + "_log.txt' target=\"_blank\" class=\"staticlinks\">" + FormatUtil.getString("am.webclient.admin.bulkimport.viewlogs") + "</a>" });
/*  530 */       ActionMessages messages = new ActionMessages();
/*  531 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.admin.bulkimport.monitoring.success", new String[] { "<a href='/bulkadd/" + filename + "_log.txt' target=\"_blank\" class=\"staticlinks\">" + FormatUtil.getString("am.webclient.admin.bulkimport.viewlogs") + "</a>" })));
/*  532 */       saveMessages(request, messages);
/*      */       
/*  534 */       request.setAttribute("bulkdiscoverystarted", "success");
/*      */     } catch (Exception e) {
/*  536 */       e.printStackTrace();
/*      */     }
/*  538 */     return new ActionForward("/BulkAddMonitors.do?method=showBulkImportForm");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList readfromCSVForConf(String filename, String monitorType)
/*      */   {
/*  545 */     toreturn = new ArrayList();
/*  546 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*      */     
/*  548 */     BufferedReader wrapperreader = null;
/*  549 */     File BulkMonitor_CSV = null;
/*      */     try {
/*  551 */       BulkMonitor_CSV = new File(filename);
/*  552 */       if (BulkMonitor_CSV.exists())
/*      */       {
/*      */ 
/*  555 */         wrapperreader = new BufferedReader(new FileReader(BulkMonitor_CSV));
/*  556 */         String line = null;
/*  557 */         boolean headerCaptured = false;
/*      */         
/*  559 */         ArrayList args = NewMonitorUtil.getArgsforConfMon(monitorType);
/*  560 */         ArrayList args_name = (ArrayList)args.get(0);
/*  561 */         Properties discoverPageUIDetails = ConfMonitorConfiguration.getInstance().getTypeDescription(monitorType);
/*  562 */         String isAgentEnabled = "NO";
/*  563 */         if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null)) {
/*  564 */           isAgentEnabled = discoverPageUIDetails.getProperty("IS-AGENT-ENABLED");
/*      */         }
/*      */         
/*  567 */         ArrayList argsDisplayNames = (ArrayList)args.get(5);
/*      */         
/*  569 */         String[] header = null;
/*  570 */         while ((line = wrapperreader.readLine()) != null) {
/*  571 */           String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
/*  572 */           line = line.trim();
/*  573 */           HashMap singlemonitor = new HashMap();
/*  574 */           if (!line.equals(""))
/*      */           {
/*      */ 
/*      */ 
/*  578 */             if (line.startsWith("#"))
/*      */             {
/*      */ 
/*  581 */               line = line.substring(1);
/*  582 */               line = line.trim();
/*      */               
/*  584 */               if ((headerCaptured) || ((!line.startsWith("Header:")) && (!line.startsWith("header:"))))
/*      */                 continue;
/*  586 */               try { line = line.substring(7);
/*  587 */                 line = line.trim();
/*  588 */                 if (monitorType.equalsIgnoreCase("OfficeSharePointServer"))
/*      */                 {
/*  590 */                   line = line + ",allowEdit";
/*      */                 }
/*  592 */                 header = line.split(",");
/*      */                 
/*  594 */                 headerCaptured = true;
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  598 */                 ex.printStackTrace();
/*      */ 
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*  607 */             else if (headerCaptured) {
/*  608 */               String SPVersion = "";
/*  609 */               if (monitorType.equalsIgnoreCase("OfficeSharePointServer"))
/*      */               {
/*  611 */                 line = line + ",true";
/*  612 */                 tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
/*      */               }
/*  614 */               ArrayList indexAlreadyUsed = new ArrayList();
/*  615 */               for (int x = 0; x < header.length; x++)
/*      */               {
/*  617 */                 if (header[x].trim().equalsIgnoreCase("DisplayName")) {
/*  618 */                   singlemonitor.put("displayname", readfromArray(tokens, x));
/*      */ 
/*      */                 }
/*  621 */                 else if ((header[x].equalsIgnoreCase("Run on server")) || (header[x].equalsIgnoreCase("Run on agent"))) {
/*  622 */                   if (isAgentEnabled.equals("YES"))
/*      */                   {
/*  624 */                     boolean isAgentsSelected = false;
/*  625 */                     String runOnAgent = "";
/*  626 */                     if (header[x].equalsIgnoreCase("Run on agent")) {
/*  627 */                       runOnAgent = readfromArray(tokens, x);
/*  628 */                       if ((runOnAgent != null) && (!runOnAgent.trim().equals("")) && (!runOnAgent.equalsIgnoreCase("No"))) {
/*  629 */                         isAgentsSelected = true;
/*  630 */                         ArrayList selectedAgentsList = getSelectedAgent(runOnAgent);
/*  631 */                         singlemonitor.put(header[x], "True");
/*  632 */                         singlemonitor.put("SelectedAgents", selectedAgentsList);
/*      */                       }
/*      */                       else {
/*  635 */                         singlemonitor.put(header[x], "False");
/*      */                       }
/*      */                     }
/*      */                     
/*  639 */                     if (header[x].equalsIgnoreCase("Run on server")) {
/*  640 */                       String runOnServer = readfromArray(tokens, x);
/*  641 */                       if ((runOnServer == null) || (runOnServer.trim().equals("")) || (runOnServer.equalsIgnoreCase("No"))) {
/*  642 */                         singlemonitor.put(header[x], "False");
/*      */                       }
/*  644 */                       else if (runOnServer.equalsIgnoreCase("yes")) {
/*  645 */                         singlemonitor.put(header[x], "True");
/*      */                       }
/*      */                       
/*      */                     }
/*      */                   }
/*      */                 }
/*  651 */                 else if (header[x].trim().equalsIgnoreCase("MonitorGroup")) {
/*  652 */                   singlemonitor.put("MonitorGroup", readfromArray(tokens, x));
/*      */ 
/*      */                 }
/*  655 */                 else if (header[x].trim().equalsIgnoreCase("MonitorGroupID")) {
/*  656 */                   singlemonitor.put("MonitorGroupID", readfromArray(tokens, x));
/*      */                 }
/*  658 */                 else if (header[x].trim().equalsIgnoreCase("PollInterval")) {
/*  659 */                   singlemonitor.put("PollInterval", readfromArray(tokens, x));
/*      */                 }
/*      */                 else
/*      */                 {
/*  663 */                   Iterator it = argsDisplayNames.iterator();
/*  664 */                   int i = 0;
/*  665 */                   while (it.hasNext()) {
/*  666 */                     String argdisName = FormatUtil.getStringEn((String)it.next());
/*  667 */                     String argname = (String)args_name.get(i);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*  672 */                     if ((header[x].equalsIgnoreCase(argdisName)) && (!indexAlreadyUsed.contains(new Integer(i))))
/*      */                     {
/*  674 */                       if ((tokens[x].startsWith("\"")) && (tokens[x].endsWith("\""))) {
/*  675 */                         tokens[x] = tokens[x].substring(1, tokens[x].length() - 1);
/*      */                       }
/*  677 */                       indexAlreadyUsed.add(new Integer(i));
/*      */                       
/*  679 */                       singlemonitor.put(argname, readfromArray(tokens, x));
/*  680 */                       if ((monitorType.equalsIgnoreCase("OfficeSharePointServer")) && (argname.equalsIgnoreCase("Version")))
/*      */                       {
/*  682 */                         SPVersion = readfromArray(tokens, x);
/*      */                       }
/*  684 */                       if ((!monitorType.equalsIgnoreCase("OfficeSharePointServer")) || (!argdisName.equalsIgnoreCase("Features")))
/*      */                         break;
/*  686 */                       singlemonitor.remove("Services");
/*  687 */                       if (SPVersion.equals("2007"))
/*      */                       {
/*  689 */                         singlemonitor.put("Service", readfromArray(tokens, x)); break;
/*      */                       }
/*  691 */                       if ((!SPVersion.equals("2010")) && (!SPVersion.equals("2013")))
/*      */                         break;
/*  693 */                       singlemonitor.put("Services", readfromArray(tokens, x)); break;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*  698 */                     i++;
/*      */                   }
/*      */                 }
/*  701 */                 if ("CredentialID".equalsIgnoreCase(header[x].trim())) {
/*      */                   try {
/*  703 */                     String credentialID = readfromArray(tokens, x);
/*  704 */                     if ((credentialID != null) && (!credentialID.equals(""))) {
/*  705 */                       Properties credProps = credUtil.rowNameVsValue(new Long(credentialID).longValue());
/*  706 */                       singlemonitor.putAll(credProps);
/*      */                     }
/*      */                   } catch (Exception e) {
/*  709 */                     e.printStackTrace();
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  717 */             if (!singlemonitor.isEmpty()) {
/*  718 */               toreturn.add(singlemonitor);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  732 */       return toreturn;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  724 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  728 */         wrapperreader.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scheduleDiscoveryforURL(ArrayList allcsvlist, HashMap dc_extra_props)
/*      */   {
/*  740 */     String monitortype = (String)dc_extra_props.get("monitortype");
/*      */     
/*  742 */     String logfile = (String)dc_extra_props.get("logfile");
/*  743 */     for (int i = 0; i < allcsvlist.size(); i++)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  748 */         HashMap singlemonitor = (HashMap)allcsvlist.get(i);
/*  749 */         String displayname = singlemonitor.get("displayname") != null ? (String)singlemonitor.get("displayname") : "";
/*  750 */         String urladdress = singlemonitor.get("URLAddress") != null ? (String)singlemonitor.get("URLAddress") : "";
/*  751 */         String checkforcontent = singlemonitor.get("Checkforcontent") != null ? (String)singlemonitor.get("Checkforcontent") : "";
/*  752 */         String error_if_match = singlemonitor.get("Error_If_Match") != null ? (String)singlemonitor.get("Error_If_Match") : "";
/*  753 */         String timeout = singlemonitor.get("Timeout") != null ? (String)singlemonitor.get("Timeout") : "";
/*      */         
/*  755 */         String username = singlemonitor.get("username") != null ? (String)singlemonitor.get("username") : "";
/*  756 */         String password = singlemonitor.get("password") != null ? (String)singlemonitor.get("password") : "";
/*  757 */         String method = singlemonitor.get("method") != null ? (String)singlemonitor.get("method") : "";
/*  758 */         String wsbfrequestparams = singlemonitor.get("request_parameters") != null ? (String)singlemonitor.get("request_parameters") : "";
/*  759 */         String monitorgroup = singlemonitor.get("MonitorGroup") != null ? (String)singlemonitor.get("MonitorGroup") : "";
/*  760 */         String monitorgroupid = singlemonitor.get("MonitorGroupID") != null ? (String)singlemonitor.get("MonitorGroupID") : "";
/*  761 */         String pollinterval = singlemonitor.get("PollInterval") != null ? (String)singlemonitor.get("PollInterval") : "600";
/*  762 */         if (method.equalsIgnoreCase("POST")) {
/*  763 */           method = "P";
/*      */         } else
/*  765 */           method = "G";
/*  766 */         if ((urladdress == null) || (urladdress.trim().equals("")))
/*      */         {
/*  768 */           System.out.println("Invalid URL Address");
/*      */         }
/*      */         else {
/*  771 */           HashMap additionalProps = new HashMap();
/*  772 */           additionalProps.put("displayname", displayname);
/*  773 */           additionalProps.put("monitortype", monitortype);
/*  774 */           additionalProps.put("logfile", logfile);
/*  775 */           additionalProps.put("URLAddress", urladdress);
/*  776 */           additionalProps.put("Checkforcontent", checkforcontent);
/*  777 */           additionalProps.put("Error_If_Match", error_if_match);
/*  778 */           additionalProps.put("pollinterval", pollinterval);
/*  779 */           additionalProps.put("username", username);
/*  780 */           additionalProps.put("password", password);
/*  781 */           additionalProps.put("Timeout", timeout);
/*  782 */           additionalProps.put("method", method);
/*  783 */           additionalProps.put("wsbfrequestparams", wsbfrequestparams);
/*  784 */           additionalProps.put("monitorgroup", monitorgroup);
/*  785 */           additionalProps.put("monitorgroupid", monitorgroupid);
/*  786 */           if (singlemonitor.get("httpcondition") != null) {
/*  787 */             additionalProps.put("httpcondition", (String)singlemonitor.get("httpcondition"));
/*      */           }
/*  789 */           if (singlemonitor.get("httpvalue") != null) {
/*  790 */             additionalProps.put("httpvalue", (String)singlemonitor.get("httpvalue"));
/*      */           }
/*  792 */           Scheduler bulkaddSchedular = Scheduler.getScheduler("BulkAddMonitors");
/*  793 */           BulkDiscoveryJob singlejob = new BulkDiscoveryJob();
/*  794 */           singlejob.dcprop = new Properties();
/*  795 */           singlejob.additionalProps = additionalProps;
/*  796 */           bulkaddSchedular.scheduleTask(singlejob, System.currentTimeMillis() + 1000L);
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/*  800 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void scheduleDiscoveryforMonitors(ArrayList allcsvlist, HashMap dc_extra_props)
/*      */   {
/*  808 */     String monitortype = (String)dc_extra_props.get("monitortype");
/*      */     
/*  810 */     String logfile = (String)dc_extra_props.get("logfile");
/*  811 */     for (int i = 0; i < allcsvlist.size(); i++)
/*      */     {
/*      */ 
/*  814 */       HashMap additionalProps = new HashMap();
/*      */       try
/*      */       {
/*  817 */         HashMap singlemonitor = (HashMap)allcsvlist.get(i);
/*  818 */         Properties dcprop = new Properties();
/*  819 */         Hashtable confmonitors = Constants.getCustomMonitors();
/*  820 */         if (confmonitors.containsKey(monitortype))
/*      */         {
/*  822 */           Iterator it = singlemonitor.keySet().iterator();
/*  823 */           while (it.hasNext()) {
/*  824 */             String key = (String)it.next();
/*  825 */             if (key.equalsIgnoreCase("DisplayName")) {
/*  826 */               additionalProps.put("DisplayName", (String)singlemonitor.get(key));
/*      */             }
/*  828 */             else if (key.equalsIgnoreCase("MonitorGroup")) {
/*  829 */               additionalProps.put("MonitorGroup", (String)singlemonitor.get(key));
/*      */             }
/*  831 */             else if (key.equalsIgnoreCase("MonitorGroupID")) {
/*  832 */               additionalProps.put("monitorgroupId", (String)singlemonitor.get(key));
/*      */             }
/*  834 */             else if (key.equalsIgnoreCase("PollInterval")) {
/*  835 */               additionalProps.put("PollInterval", (String)singlemonitor.get(key));
/*      */             }
/*  837 */             else if (key.equalsIgnoreCase("Run on server")) {
/*  838 */               additionalProps.put("runOnServer", (String)singlemonitor.get(key));
/*      */             }
/*  840 */             else if (key.equalsIgnoreCase("Run on agent")) {
/*  841 */               additionalProps.put("runOnAgent", (String)singlemonitor.get(key));
/*      */             }
/*  843 */             else if (key.equalsIgnoreCase("SelectedAgents")) {
/*  844 */               additionalProps.put("SelectedAgents", (ArrayList)singlemonitor.get(key));
/*      */             }
/*  846 */             else if (key.equalsIgnoreCase("Version")) {
/*  847 */               String version = sanitizeVersion(monitortype, (String)singlemonitor.get(key));
/*  848 */               dcprop.setProperty(key, version);
/*  849 */             } else if ((key.equalsIgnoreCase("LaunchType")) && (monitortype.equals("JBOSS-server"))) {
/*  850 */               String launchType = (String)singlemonitor.get(key);
/*  851 */               if (("domain".equalsIgnoreCase(launchType)) || ("true".equalsIgnoreCase(launchType))) {
/*  852 */                 dcprop.setProperty("LaunchType", "DOMAIN");
/*      */               } else {
/*  854 */                 dcprop.setProperty(key, "False");
/*      */               }
/*      */             }
/*      */             else {
/*  858 */               dcprop.setProperty(key, (String)singlemonitor.get(key));
/*      */             }
/*      */           }
/*  861 */           if (monitortype.equals("WebSphere-server")) {
/*  862 */             if (singlemonitor.get("mode").toString().equalsIgnoreCase("ND")) {
/*  863 */               dcprop.setProperty("isDmgr", "true");
/*      */             } else {
/*  865 */               dcprop.setProperty("isDmgr", "false");
/*      */             }
/*  867 */             dcprop.setProperty("allowEdit", "true");
/*  868 */             dcprop.setProperty("sslenabled", dcprop.getProperty("sslenabled").toLowerCase());
/*      */           }
/*  870 */           additionalProps.put("logfile", logfile);
/*  871 */           additionalProps.put("monitortype", monitortype);
/*      */         }
/*      */         else
/*      */         {
/*  875 */           Hashtable configuredList = new Hashtable();
/*  876 */           Hashtable hostInformation = new Hashtable();
/*      */           
/*  878 */           String displayname = singlemonitor.get("displayname") != null ? (String)singlemonitor.get("displayname") : "";
/*  879 */           String givenhost = singlemonitor.get("givenhost") != null ? (String)singlemonitor.get("givenhost") : "";
/*  880 */           String serverType = singlemonitor.get("os") != null ? (String)singlemonitor.get("os") : "";
/*  881 */           String subnet = singlemonitor.get("SUBNET_MASK") != null ? (String)singlemonitor.get("SUBNET_MASK") : "";
/*  882 */           String mode = singlemonitor.get("MODE") != null ? (String)singlemonitor.get("MODE") : "";
/*  883 */           String username = singlemonitor.get("username") != null ? (String)singlemonitor.get("username") : "";
/*  884 */           String password = singlemonitor.get("password") != null ? (String)singlemonitor.get("password") : "";
/*      */           
/*  886 */           String snmpcommunity = singlemonitor.get("SNMPCOMMUNITY") != null ? (String)singlemonitor.get("SNMPCOMMUNITY") : "";
/*  887 */           String commandprompt = singlemonitor.get("prompt") != null ? (String)singlemonitor.get("prompt") : "";
/*  888 */           String sshpublickey = singlemonitor.get("privateKey") != null ? (String)singlemonitor.get("privateKey") : "";
/*  889 */           String sshpublickeyAlgo = singlemonitor.get("privateKeyAlgorithm") != null ? (String)singlemonitor.get("privateKeyAlgorithm") : "";
/*      */           
/*      */ 
/*      */ 
/*  893 */           if ((sshpublickey != null) && (!"".equals(sshpublickey))) {
/*  894 */             StringBuffer sbuf = new StringBuffer();
/*  895 */             sbuf.append("-----BEGIN " + sshpublickeyAlgo + " PRIVATE KEY-----");
/*  896 */             sbuf.append("\n");
/*  897 */             sbuf.append(sshpublickey);
/*  898 */             sbuf.append("\n");
/*  899 */             sbuf.append("-----END " + sshpublickeyAlgo + " PRIVATE KEY-----");
/*  900 */             sshpublickey = sbuf.toString();
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  906 */           String port = singlemonitor.get("PORT") != null ? (String)singlemonitor.get("PORT") : "";
/*  907 */           String version = singlemonitor.get("version") != null ? (String)singlemonitor.get("version") : "";
/*      */           
/*  909 */           String instance = singlemonitor.get("instance") != null ? (String)singlemonitor.get("instance") : "";
/*  910 */           String jndiname = singlemonitor.get("JNDIName") != null ? (String)singlemonitor.get("JNDIName") : "";
/*  911 */           String jmxurl = singlemonitor.get("JMXUrl") != null ? (String)singlemonitor.get("JMXUrl") : "";
/*  912 */           String ndhost = singlemonitor.get("ndhost") != null ? (String)singlemonitor.get("ndhost") : "";
/*  913 */           String ndport = singlemonitor.get("ndport") != null ? (String)singlemonitor.get("ndport") : "";
/*  914 */           String soapport = singlemonitor.get("soapport") != null ? (String)singlemonitor.get("soapport") : "";
/*  915 */           String timeout = singlemonitor.get("Timeout") != null ? (String)singlemonitor.get("Timeout") : "5";
/*  916 */           String monitorgroup = singlemonitor.get("MonitorGroup") != null ? (String)singlemonitor.get("MonitorGroup") : "";
/*  917 */           String monitorgroupId = singlemonitor.get("MonitorGroupID") != null ? (String)singlemonitor.get("MonitorGroupID") : null;
/*  918 */           String pollinterval = singlemonitor.get("PollInterval") != null ? (String)singlemonitor.get("PollInterval") : "600";
/*  919 */           String sslenabled = "false";
/*  920 */           String tempsslenabled = singlemonitor.get("sslenabled") != null ? (String)singlemonitor.get("sslenabled") : "";
/*  921 */           sslenabled = tempsslenabled.equals("true") ? "true" : "false";
/*  922 */           String command = singlemonitor.get("commandtoExecute") != null ? (String)singlemonitor.get("commandtoExecute") : "";
/*  923 */           String search = singlemonitor.get("matchContent") != null ? (String)singlemonitor.get("matchContent") : "";
/*  924 */           String eventLogMon = singlemonitor.get("eventlog_status") != null ? (String)singlemonitor.get("eventlog_status") : "";
/*  925 */           if ((eventLogMon == null) || (eventLogMon.trim().length() == 0) || (eventLogMon.equalsIgnoreCase("no"))) {
/*  926 */             eventLogMon = "false";
/*      */           }
/*  928 */           if (eventLogMon.equalsIgnoreCase("yes")) {
/*  929 */             eventLogMon = "true";
/*      */           }
/*  931 */           String credentialID = singlemonitor.get("credentialID") == null ? "-1" : (String)singlemonitor.get("credentialID");
/*  932 */           dcprop.setProperty("credentialID", credentialID);
/*      */           
/*  934 */           if ((ndport == null) || (ndport.equals("")))
/*      */           {
/*  936 */             ndport = soapport;
/*      */           }
/*  938 */           mode = sanitizeMode(mode);
/*  939 */           serverType = sanitizeServerType(serverType);
/*  940 */           version = sanitizeVersion(monitortype, version);
/*  941 */           dcprop.setProperty("PORT", port);
/*  942 */           dcprop.setProperty("version", version);
/*  943 */           dcprop.setProperty("pollinterval", pollinterval);
/*      */           
/*  945 */           dcprop.setProperty("instance", instance);
/*  946 */           dcprop.setProperty("jndiurl", jndiname);
/*  947 */           dcprop.setProperty("jmxurl", jmxurl);
/*  948 */           dcprop.setProperty("WTEnabled", "false");
/*      */           
/*  950 */           if ((mode.equalsIgnoreCase("SNMP")) && (monitortype.equals("SYSTEM")))
/*      */           {
/*  952 */             port = "161";
/*      */           }
/*  954 */           dcprop.setProperty("TELNETPORT", "23");
/*  955 */           if (mode.equals("SSH"))
/*      */           {
/*  957 */             dcprop.setProperty("TELNETPORT", "22");
/*      */             
/*  959 */             if ((sshpublickey != null) && (!"".equals(sshpublickey))) {
/*  960 */               dcprop.setProperty("sshPKAuthChecked", "checked");
/*      */               
/*  962 */               password = sshpublickey.trim();
/*      */             }
/*      */           }
/*      */           
/*  966 */           dcprop.setProperty("eventlog_status", eventLogMon);
/*  967 */           dcprop.setProperty("MODE", mode);
/*  968 */           dcprop.setProperty("SNMPCOMMUNITY", snmpcommunity);
/*      */           
/*  970 */           dcprop.setProperty("SNMPPORT", "161");
/*  971 */           dcprop.setProperty("givenhost", givenhost);
/*  972 */           dcprop.setProperty("os", serverType);
/*  973 */           dcprop.setProperty("username", username);
/*      */           
/*  975 */           dcprop.setProperty("password", password);
/*  976 */           dcprop.setProperty("prompt", commandprompt);
/*  977 */           dcprop.setProperty("privateKey", sshpublickey.trim());
/*      */           
/*  979 */           String hostIp = givenhost;
/*      */           try {
/*  981 */             hostIp = InetAddress.getByName(givenhost).getHostAddress();
/*  982 */             if (InetAddress.getByName(hostIp).isLoopbackAddress())
/*      */             {
/*  984 */               hostIp = AMServerStartup.ipaddress;
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  989 */             System.out.println("unknown host :" + givenhost);
/*      */           }
/*  991 */           if ((ndhost == null) || (ndhost.equals("")))
/*      */           {
/*  993 */             ndhost = hostIp;
/*      */           }
/*  995 */           dcprop.setProperty("ndhost", ndhost);
/*  996 */           dcprop.setProperty("ndport", ndport);
/*  997 */           dcprop.setProperty("soapport", soapport);
/*      */           
/*  999 */           dcprop.setProperty("sslenabled", sslenabled);
/* 1000 */           dcprop.setProperty("command", command);
/* 1001 */           dcprop.setProperty("search", search);
/* 1002 */           Properties tempProps = new Properties();
/* 1003 */           tempProps.setProperty("HOST_IP", hostIp);
/* 1004 */           tempProps.setProperty("SUBNET_MASK", subnet);
/* 1005 */           tempProps.setProperty("SERVER_TYPE", monitortype);
/* 1006 */           tempProps.setProperty("PORT", port);
/* 1007 */           tempProps.setProperty("sslenabled", sslenabled);
/*      */           
/* 1009 */           hostInformation.put(hostIp + ":" + port, tempProps);
/* 1010 */           configuredList.put("HOST_CONFIG", hostInformation);
/* 1011 */           Properties discoveryProperties = new Properties();
/* 1012 */           discoveryProperties.setProperty("REDISCOVER_INTERVAL", "24");
/* 1013 */           discoveryProperties.setProperty("HOUR", "-1");
/* 1014 */           configuredList.put("DISC_DATA_PROP", discoveryProperties);
/*      */           
/* 1016 */           additionalProps.put("configuredList", configuredList);
/* 1017 */           if (monitortype.equals("Ping Monitor"))
/*      */           {
/* 1019 */             additionalProps.put("givenhost", givenhost);
/* 1020 */             additionalProps.put("Timeout", timeout);
/* 1021 */             additionalProps.put("pollinterval", pollinterval);
/*      */           }
/* 1023 */           additionalProps.put("logfile", logfile);
/* 1024 */           additionalProps.put("monitortype", monitortype);
/* 1025 */           if (monitorgroupId != null) {
/* 1026 */             additionalProps.put("monitorgroupId", monitorgroupId);
/*      */           } else {
/* 1028 */             additionalProps.put("monitorgroup", monitorgroup);
/*      */           }
/* 1030 */           additionalProps.put("displayname", displayname);
/*      */           
/* 1032 */           dcprop.setProperty("DISPLAYNAME", displayname);
/* 1033 */           if (singlemonitor.containsKey("credentialID")) {
/* 1034 */             dcprop.setProperty("credentialID", singlemonitor.get("credentialID") != null ? (String)singlemonitor.get("credentialID") : "");
/*      */           }
/*      */         }
/*      */         
/* 1038 */         Scheduler bulkaddSchedular = Scheduler.getScheduler("BulkAddMonitors");
/* 1039 */         BulkDiscoveryJob singlejob = new BulkDiscoveryJob();
/* 1040 */         singlejob.dcprop = dcprop;
/* 1041 */         singlejob.additionalProps = additionalProps;
/* 1042 */         bulkaddSchedular.scheduleTask(singlejob, System.currentTimeMillis() + 1000L);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1046 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList readfromCSV(String filename)
/*      */   {
/* 1058 */     int displayname_index = -1;
/* 1059 */     int hostname_index = -1;
/* 1060 */     int operating_system_index = -1;
/* 1061 */     int subnetmask_index = -1;
/* 1062 */     int mode_of_monitoring_index = -1;
/* 1063 */     int username_index = -1;
/* 1064 */     int password_index = -1;
/* 1065 */     int snmp_community_string_index = -1;
/* 1066 */     int telnet_ssh_command_prompt_index = -1;
/* 1067 */     int ssh_publickey_index = -1;
/* 1068 */     int ssh_publickey_Algorithm_index = -1;
/*      */     
/* 1070 */     int port_index = -1;
/* 1071 */     int version_index = -1;
/*      */     
/* 1073 */     int database_name_index = -1;
/*      */     
/* 1075 */     int isslenabled_index = -1;
/* 1076 */     int networkdeployer_host_index = -1;
/* 1077 */     int networkdeployer_soap_port_index = -1;
/* 1078 */     int soap_port_index = -1;
/*      */     
/* 1080 */     int urladdress_index = -1;
/* 1081 */     int check_for_specific_html_content_index = -1;
/* 1082 */     int error_if_match_index = -1;
/* 1083 */     int timeout_index = -1;
/* 1084 */     int form_submission_method_index = -1;
/* 1085 */     int request_parameters_index = -1;
/* 1086 */     int httpcondition_index = -1;
/* 1087 */     int httpvalue_index = -1;
/* 1088 */     int monitorgroup_index = -1;
/* 1089 */     int monitorgroupid_index = -1;
/* 1090 */     int pollinterval_index = -1;
/* 1091 */     int command_to_execute_index = -1;
/* 1092 */     int match_content_index = -1;
/* 1093 */     int eventlogStatusIdx = -1;
/* 1094 */     int jndiNameIdx = -1;
/* 1095 */     int jmxurlIdx = -1;
/* 1096 */     int credentialID = -1;
/*      */     
/* 1098 */     toreturn = new ArrayList();
/* 1099 */     File BulkMonitor_CSV = null;
/* 1100 */     BufferedReader wrapperreader = null;
/*      */     try {
/* 1102 */       BulkMonitor_CSV = new File(filename);
/* 1103 */       if (BulkMonitor_CSV.exists())
/*      */       {
/* 1105 */         wrapperreader = new BufferedReader(new FileReader(BulkMonitor_CSV));
/* 1106 */         String line = null;
/* 1107 */         boolean headerCaptured = false;
/* 1108 */         while ((line = wrapperreader.readLine()) != null)
/*      */         {
/* 1110 */           String[] tokens = tokens = line.split(",");
/* 1111 */           line = line.trim();
/* 1112 */           HashMap singlemonitor = new HashMap();
/* 1113 */           if (!line.equals(""))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1118 */             if (line.startsWith("#"))
/*      */             {
/*      */ 
/* 1121 */               line = line.substring(1);
/* 1122 */               line = line.trim();
/* 1123 */               if ((!headerCaptured) && ((line.startsWith("Header:")) || (line.startsWith("header:"))))
/*      */               {
/*      */                 try
/*      */                 {
/* 1127 */                   line = line.substring(7);
/* 1128 */                   line = line.trim();
/*      */                   
/* 1130 */                   tokens = line.split(",");
/*      */                   
/* 1132 */                   headerCaptured = true;
/*      */                 } catch (Exception ex) {
/* 1134 */                   ex.printStackTrace(); }
/* 1135 */                 for (int x = 0; x < tokens.length; x++)
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1155 */                   if (tokens[x].trim().equalsIgnoreCase("DisplayName")) {
/* 1156 */                     displayname_index = x;
/*      */                   }
/* 1158 */                   else if (tokens[x].trim().equalsIgnoreCase("HostName")) {
/* 1159 */                     hostname_index = x;
/*      */                   }
/* 1161 */                   else if (tokens[x].trim().equalsIgnoreCase("OperatingSystem")) {
/* 1162 */                     operating_system_index = x;
/*      */                   }
/* 1164 */                   else if (tokens[x].trim().equalsIgnoreCase("SubNetMask")) {
/* 1165 */                     subnetmask_index = x;
/*      */                   }
/* 1167 */                   else if ((tokens[x].trim().equalsIgnoreCase("ModeOfMonitoring")) || (tokens[x].trim().equalsIgnoreCase("DeploymentMode"))) {
/* 1168 */                     mode_of_monitoring_index = x;
/*      */                   }
/* 1170 */                   else if (tokens[x].trim().equalsIgnoreCase("UserName")) {
/* 1171 */                     username_index = x;
/*      */                   }
/* 1173 */                   else if (tokens[x].trim().equalsIgnoreCase("Password")) {
/* 1174 */                     password_index = x;
/*      */                   }
/* 1176 */                   else if (tokens[x].trim().equalsIgnoreCase("SNMPCommunityString")) {
/* 1177 */                     snmp_community_string_index = x;
/*      */ 
/*      */                   }
/* 1180 */                   else if (tokens[x].trim().equalsIgnoreCase("SSHPublicKey")) {
/* 1181 */                     ssh_publickey_index = x;
/*      */ 
/*      */                   }
/* 1184 */                   else if (tokens[x].trim().equalsIgnoreCase("SSHKeyAlgorithm")) {
/* 1185 */                     ssh_publickey_Algorithm_index = x;
/*      */ 
/*      */ 
/*      */                   }
/* 1189 */                   else if (tokens[x].trim().equalsIgnoreCase("TelnetSSHCommandPrompt")) {
/* 1190 */                     telnet_ssh_command_prompt_index = x;
/*      */ 
/*      */                   }
/* 1193 */                   else if (tokens[x].trim().equalsIgnoreCase("Port")) {
/* 1194 */                     port_index = x;
/*      */                   }
/* 1196 */                   else if ((tokens[x].trim().equalsIgnoreCase("WeblogicVersion")) || (tokens[x].trim().equalsIgnoreCase("JBOSSVersion")) || (tokens[x].trim().equalsIgnoreCase("TomcatVersion")) || (tokens[x].trim().equalsIgnoreCase("WebsphereVersion")) || (tokens[x].trim().equalsIgnoreCase("WeblogicIntegrationVersion")) || (tokens[x].trim().equalsIgnoreCase("OracleASVersion")))
/*      */                   {
/* 1198 */                     version_index = x;
/* 1199 */                     System.out.println("version_index" + version_index);
/*      */ 
/*      */                   }
/* 1202 */                   else if ((tokens[x].trim().equalsIgnoreCase("DatabaseName")) || (tokens[x].trim().equalsIgnoreCase("InstanceName"))) {
/* 1203 */                     database_name_index = x;
/*      */ 
/*      */                   }
/* 1206 */                   else if (tokens[x].trim().equalsIgnoreCase("isSSLEnabled")) {
/* 1207 */                     isslenabled_index = x;
/*      */ 
/*      */                   }
/* 1210 */                   else if (tokens[x].trim().equalsIgnoreCase("NetworkDeployerHost")) {
/* 1211 */                     networkdeployer_host_index = x;
/*      */                   }
/* 1213 */                   else if (tokens[x].trim().equalsIgnoreCase("NetworkDeployerSOAPPort")) {
/* 1214 */                     networkdeployer_soap_port_index = x;
/*      */                   }
/* 1216 */                   else if (tokens[x].trim().equalsIgnoreCase("SOAPPort")) {
/* 1217 */                     soap_port_index = x;
/*      */                   }
/* 1219 */                   else if (tokens[x].trim().equalsIgnoreCase("URLAddress")) {
/* 1220 */                     urladdress_index = x;
/*      */                   }
/* 1222 */                   else if (tokens[x].trim().equalsIgnoreCase("CheckForContent")) {
/* 1223 */                     check_for_specific_html_content_index = x;
/*      */                   }
/* 1225 */                   else if (tokens[x].trim().equalsIgnoreCase("ErrorIfMatch")) {
/* 1226 */                     error_if_match_index = x;
/*      */                   }
/* 1228 */                   else if (tokens[x].trim().equalsIgnoreCase("TimeoutInSeconds")) {
/* 1229 */                     timeout_index = x;
/*      */                   }
/* 1231 */                   else if (tokens[x].trim().equalsIgnoreCase("FormSubmissionMethod")) {
/* 1232 */                     form_submission_method_index = x;
/*      */                   }
/* 1234 */                   else if (tokens[x].trim().equalsIgnoreCase("httpcondition")) {
/* 1235 */                     httpcondition_index = x;
/* 1236 */                   } else if (tokens[x].trim().equalsIgnoreCase("httpvalue")) {
/* 1237 */                     httpvalue_index = x;
/*      */                   }
/* 1239 */                   else if (tokens[x].trim().equalsIgnoreCase("RequestParameters")) {
/* 1240 */                     request_parameters_index = x;
/* 1241 */                   } else if (tokens[x].trim().equalsIgnoreCase("CommandtoExecute"))
/*      */                   {
/* 1243 */                     command_to_execute_index = x;
/*      */ 
/*      */                   }
/* 1246 */                   else if (tokens[x].trim().equalsIgnoreCase("MatchContent"))
/*      */                   {
/* 1248 */                     match_content_index = x;
/*      */ 
/*      */                   }
/* 1251 */                   else if (tokens[x].trim().equalsIgnoreCase("MonitorGroup")) {
/* 1252 */                     monitorgroup_index = x;
/*      */                   }
/* 1254 */                   else if (tokens[x].trim().equalsIgnoreCase("MonitorGroupID")) {
/* 1255 */                     monitorgroupid_index = x;
/*      */                   }
/* 1257 */                   else if (tokens[x].trim().equalsIgnoreCase("PollInterval")) {
/* 1258 */                     pollinterval_index = x;
/*      */                   }
/* 1260 */                   else if (tokens[x].trim().equalsIgnoreCase("EnableEventLogMonitoring")) {
/* 1261 */                     eventlogStatusIdx = x;
/*      */                   }
/* 1263 */                   else if (tokens[x].trim().equalsIgnoreCase("JNDIName")) {
/* 1264 */                     jndiNameIdx = x;
/*      */                   }
/* 1266 */                   else if (tokens[x].trim().equalsIgnoreCase("JMXUrl")) {
/* 1267 */                     jmxurlIdx = x;
/*      */                   }
/* 1269 */                   else if (tokens[x].trim().equalsIgnoreCase("CredentialID"))
/*      */                   {
/* 1271 */                     credentialID = x;
/*      */ 
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/* 1283 */             else if (headerCaptured)
/*      */             {
/*      */ 
/* 1286 */               singlemonitor.put("displayname", readfromArray(tokens, displayname_index));
/* 1287 */               singlemonitor.put("givenhost", readfromArray(tokens, hostname_index));
/* 1288 */               singlemonitor.put("os", readfromArray(tokens, operating_system_index));
/* 1289 */               singlemonitor.put("SUBNET_MASK", readfromArray(tokens, subnetmask_index));
/* 1290 */               singlemonitor.put("MODE", readfromArray(tokens, mode_of_monitoring_index));
/* 1291 */               singlemonitor.put("username", readfromArray(tokens, username_index));
/* 1292 */               singlemonitor.put("password", readfromArray(tokens, password_index));
/* 1293 */               singlemonitor.put("SNMPCOMMUNITY", readfromArray(tokens, snmp_community_string_index));
/* 1294 */               singlemonitor.put("prompt", readfromArray(tokens, telnet_ssh_command_prompt_index));
/*      */               
/* 1296 */               singlemonitor.put("privateKey", readfromArray(tokens, ssh_publickey_index));
/* 1297 */               singlemonitor.put("privateKeyAlgorithm", readfromArray(tokens, ssh_publickey_Algorithm_index));
/*      */               
/*      */ 
/*      */ 
/* 1301 */               singlemonitor.put("PORT", readfromArray(tokens, port_index));
/* 1302 */               singlemonitor.put("version", readfromArray(tokens, version_index));
/*      */               
/* 1304 */               singlemonitor.put("instance", readfromArray(tokens, database_name_index));
/*      */               
/* 1306 */               singlemonitor.put("sslenabled", readfromArray(tokens, isslenabled_index));
/* 1307 */               singlemonitor.put("ndhost", readfromArray(tokens, networkdeployer_host_index));
/* 1308 */               singlemonitor.put("ndport", readfromArray(tokens, networkdeployer_soap_port_index));
/* 1309 */               singlemonitor.put("soapport", readfromArray(tokens, soap_port_index));
/*      */               
/* 1311 */               singlemonitor.put("URLAddress", readfromArray(tokens, urladdress_index));
/* 1312 */               singlemonitor.put("Checkforcontent", readfromArray(tokens, check_for_specific_html_content_index));
/* 1313 */               singlemonitor.put("Error_If_Match", readfromArray(tokens, error_if_match_index));
/* 1314 */               singlemonitor.put("Timeout", readfromArray(tokens, timeout_index));
/* 1315 */               singlemonitor.put("method", readfromArray(tokens, form_submission_method_index));
/* 1316 */               singlemonitor.put("commandtoExecute", readfromArray(tokens, command_to_execute_index));
/* 1317 */               singlemonitor.put("matchContent", readfromArray(tokens, match_content_index));
/* 1318 */               singlemonitor.put("request_parameters", readfromArray(tokens, request_parameters_index));
/* 1319 */               singlemonitor.put("httpcondition", readfromArray(tokens, httpcondition_index));
/* 1320 */               singlemonitor.put("httpvalue", readfromArray(tokens, httpvalue_index));
/* 1321 */               singlemonitor.put("MonitorGroup", readfromArray(tokens, monitorgroup_index));
/* 1322 */               singlemonitor.put("MonitorGroupID", readfromArray(tokens, monitorgroupid_index));
/* 1323 */               singlemonitor.put("eventlog_status", readfromArray(tokens, eventlogStatusIdx));
/* 1324 */               singlemonitor.put("JNDIName", readfromArray(tokens, jndiNameIdx));
/* 1325 */               singlemonitor.put("JMXUrl", readfromArray(tokens, jmxurlIdx));
/*      */               
/*      */               try
/*      */               {
/* 1329 */                 String credentialId = readfromArray(tokens, credentialID);
/* 1330 */                 if ((credentialId != null) && (!credentialId.equals("")))
/*      */                 {
/* 1332 */                   CredentialManagerUtil credentialmanagerutil = new CredentialManagerUtil();
/* 1333 */                   HttpServletRequest httpservletrequest = null;
/* 1334 */                   Properties properties = credentialmanagerutil.getSnmpProps(httpservletrequest, credentialId);
/*      */                   
/* 1336 */                   singlemonitor.put("credentialID", credentialId);
/* 1337 */                   singlemonitor.putAll(properties);
/*      */                 }
/*      */               }
/*      */               catch (Exception exception4)
/*      */               {
/* 1342 */                 exception4.printStackTrace();
/*      */               }
/*      */               
/* 1345 */               int pollintervalinint = 600;
/*      */               try
/*      */               {
/* 1348 */                 String temppollinterval = readfromArray(tokens, pollinterval_index);
/* 1349 */                 if ((temppollinterval != null) && (!temppollinterval.equals(""))) {
/* 1350 */                   pollintervalinint = Integer.parseInt(temppollinterval) * 60;
/*      */                 }
/*      */               }
/*      */               catch (Exception ex2) {
/* 1354 */                 pollintervalinint = 600;
/*      */               }
/* 1356 */               singlemonitor.put("PollInterval", String.valueOf(pollintervalinint));
/* 1357 */               toreturn.add(singlemonitor);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1377 */       return toreturn;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1369 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 1373 */         wrapperreader.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String readfromArray(String[] tokens, int index)
/*      */   {
/* 1383 */     String toreturn = "";
/*      */     try
/*      */     {
/* 1386 */       if ((tokens[index].startsWith("\"")) && (tokens[index].endsWith("\""))) {
/* 1387 */         toreturn = tokens[index].substring(1, tokens[index].length() - 1);
/*      */       }
/* 1389 */       toreturn = tokens[index].trim();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1394 */       return null;
/*      */     }
/* 1396 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isSchedulerIdle()
/*      */   {
/* 1402 */     int threadsused = 3;
/*      */     
/*      */ 
/*      */ 
/* 1406 */     Vector allschedulers = Scheduler.getAllSchedulerNames();
/* 1407 */     if (!allschedulers.contains("BulkAddMonitors"))
/*      */     {
/* 1409 */       System.out.println("Scheduler not yet started");
/* 1410 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1414 */     Scheduler bulkaddSchedular = Scheduler.getScheduler("BulkAddMonitors");
/*      */     
/*      */ 
/*      */ 
/* 1418 */     if (bulkaddSchedular.getIdleThreads() < 3) {
/* 1419 */       return false;
/*      */     }
/* 1421 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void deletefile(String filename_loc)
/*      */   {
/*      */     try
/*      */     {
/* 1429 */       File BulkMonitor_CSV = new File(filename_loc);
/* 1430 */       if (BulkMonitor_CSV.exists())
/*      */       {
/* 1432 */         System.out.println("Deleting the csv file :" + filename_loc);
/* 1433 */         BulkMonitor_CSV.delete();
/*      */       }
/*      */     }
/*      */     catch (Exception ex1)
/*      */     {
/* 1438 */       System.out.println("problem in deleting the csv file :" + filename_loc);
/* 1439 */       ex1.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public String sanitizeServerType(String serverType)
/*      */   {
/*      */     try {
/* 1446 */       if (serverType != null)
/*      */       {
/*      */ 
/* 1449 */         if (serverType.indexOf("2003") != -1) {
/* 1450 */           serverType = "Windows 2003";
/* 1451 */         } else if (serverType.indexOf("2000") != -1) {
/* 1452 */           serverType = "Windows 2000";
/* 1453 */         } else if (serverType.indexOf("2008") != -1)
/*      */         {
/* 1455 */           serverType = "Windows 2008";
/*      */         }
/* 1457 */         else if (serverType.indexOf("Windows 7") != -1)
/*      */         {
/* 1459 */           serverType = "Windows 7";
/*      */         }
/* 1461 */         else if (serverType.indexOf("Windows 8") != -1)
/*      */         {
/* 1463 */           serverType = "Windows 8";
/*      */         }
/* 1465 */         else if (serverType.indexOf("Windows 10") != -1)
/*      */         {
/* 1467 */           serverType = "Windows 10";
/*      */         }
/* 1469 */         else if (serverType.indexOf("Windows 2012") != -1)
/*      */         {
/* 1471 */           serverType = "Windows 2012";
/*      */         }
/* 1473 */         else if ((serverType.indexOf("vista") != -1) || (serverType.indexOf("Vista") != -1))
/*      */         {
/* 1475 */           serverType = "Windows Vista";
/*      */         }
/* 1477 */         else if ((serverType.indexOf("XP") != -1) || (serverType.indexOf("xp") != -1)) {
/* 1478 */           serverType = "Windows XP";
/* 1479 */         } else if ((serverType.indexOf("NT") != -1) || (serverType.indexOf("nt") != -1)) {
/* 1480 */           serverType = "WindowsNT";
/*      */         }
/* 1482 */         if (serverType.equalsIgnoreCase("Linux")) {
/* 1483 */           serverType = "Linux";
/* 1484 */         } else if (serverType.equalsIgnoreCase("AIX")) {
/* 1485 */           serverType = "AIX";
/* 1486 */         } else if (serverType.equalsIgnoreCase("FreeBSD")) {
/* 1487 */           serverType = "FreeBSD";
/* 1488 */         } else if (serverType.equalsIgnoreCase("HP-UX")) {
/* 1489 */           serverType = "HP-UX";
/* 1490 */         } else if ((serverType.equalsIgnoreCase("Solaris")) || (serverType.equalsIgnoreCase("Sun Solaris"))) {
/* 1491 */           serverType = "SUN";
/* 1492 */         } else if (serverType.equalsIgnoreCase("HP-TRU64")) {
/* 1493 */           serverType = "HP-TRU64";
/* 1494 */         } else if ((serverType.equalsIgnoreCase("AS400/iSeries")) || (serverType.equalsIgnoreCase("AS400")) || (serverType.equalsIgnoreCase("iSeries")))
/*      */         {
/* 1496 */           serverType = "AS400/iSeries";
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 1502 */         serverType = "Windows 2003";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1507 */       ex.printStackTrace();
/* 1508 */       serverType = "Windows 2003";
/*      */     }
/* 1510 */     return serverType;
/*      */   }
/*      */   
/*      */   public String sanitizeMode(String mode) {
/*      */     try {
/* 1515 */       if ((mode != null) && (mode.equalsIgnoreCase("TELNET")))
/*      */       {
/* 1517 */         mode = "TELNET";
/*      */       }
/* 1519 */       if ((mode != null) && (mode.equalsIgnoreCase("SNMP")))
/*      */       {
/* 1521 */         mode = "SNMP";
/*      */       }
/* 1523 */       if ((mode != null) && (mode.equalsIgnoreCase("SSH")))
/*      */       {
/* 1525 */         mode = "SSH";
/*      */       }
/* 1527 */       if ((mode != null) && (mode.equalsIgnoreCase("WMI")))
/*      */       {
/* 1529 */         mode = "WMI";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1534 */       ex.printStackTrace();
/*      */     }
/* 1536 */     return mode;
/*      */   }
/*      */   
/*      */ 
/*      */   public String sanitizeVersion(String monitortype, String version)
/*      */   {
/*      */     try
/*      */     {
/* 1544 */       if (monitortype.equalsIgnoreCase("WebLogic"))
/*      */       {
/* 1546 */         if (version.equals("8.1")) {
/* 1547 */           version = "WLS_8_1";
/* 1548 */         } else if (version.equals("9.x")) {
/* 1549 */           version = "WLS_9_0";
/* 1550 */         } else if (version.equals("10.x"))
/*      */         {
/* 1552 */           version = "WLS_10_0";
/*      */         }
/* 1554 */         else if (version.equals("7.0")) {
/* 1555 */           version = "WLS_7_0";
/* 1556 */         } else if (version.equals("6.1")) {
/* 1557 */           version = "WLS_6_1";
/*      */         } else
/* 1559 */           version = "WLS_8_1";
/*      */       }
/* 1561 */       if ((monitortype.equalsIgnoreCase("JBoss")) || (monitortype.equalsIgnoreCase("JBOSS-server")))
/*      */       {
/* 1563 */         if (version.equals("3.2.x")) {
/* 1564 */           version = "JBOSS_HTTP";
/* 1565 */         } else if (version.equals("4.x")) {
/* 1566 */           version = "JBOSS_HTTP40";
/* 1567 */         } else if (version.equals("4.0.1")) {
/* 1568 */           version = "JBOSS_HTTP401";
/* 1569 */         } else if (version.equals("4.0.2")) {
/* 1570 */           version = "JBOSS_HTTP402";
/* 1571 */         } else if (version.equals("6.x")) {
/* 1572 */           version = "JBOSS_HTTP60";
/* 1573 */         } else if (version.equals("7.x")) {
/* 1574 */           version = "JBOSS_HTTP7";
/*      */         }
/* 1576 */         else if (version.equals("Wildfly_8.x")) {
/* 1577 */           version = "JBOSS_HTTP8";
/*      */         }
/*      */         else
/* 1580 */           version = "JBOSS_HTTP402";
/*      */       }
/* 1582 */       if (monitortype.equalsIgnoreCase("Tomcat-server"))
/*      */       {
/* 1584 */         if (version.equals("3.x")) {
/* 1585 */           version = "3";
/* 1586 */         } else if (version.equals("4.x")) {
/* 1587 */           version = "4";
/* 1588 */         } else if (version.equals("5.x")) {
/* 1589 */           version = "5";
/* 1590 */         } else if (version.equals("6.x")) {
/* 1591 */           version = "6";
/* 1592 */         } else if (version.equals("7.x")) {
/* 1593 */           version = "7";
/*      */         } else
/* 1595 */           version = "5";
/*      */       }
/* 1597 */       if (monitortype.equalsIgnoreCase("WebSphere"))
/*      */       {
/* 1599 */         if (version.equals("5.x")) {
/* 1600 */           version = "5.0";
/* 1601 */         } else if (version.equals("6.x")) {
/* 1602 */           version = "6";
/*      */         } else
/* 1604 */           version = "6";
/*      */       }
/* 1606 */       if (monitortype.equalsIgnoreCase("WLI"))
/*      */       {
/* 1608 */         if (version.equals("8.x"))
/* 1609 */           version = "WLS_8_1";
/*      */       }
/* 1611 */       if (monitortype.equals("ORACLEAS"))
/*      */       {
/* 1613 */         if (version.equals("10.1.2")) {
/* 1614 */           version = "2";
/* 1615 */         } else if (version.equals("10.1.3"))
/*      */         {
/* 1617 */           version = "3";
/*      */         }
/*      */         else
/*      */         {
/* 1621 */           version = "2";
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1628 */       ex.printStackTrace();
/*      */     }
/* 1630 */     return version;
/*      */   }
/*      */   
/*      */   public boolean checkHeader(String monitortype, ArrayList headerlist)
/*      */   {
/* 1635 */     boolean isheadercorrect = false;
/*      */     try {
/* 1637 */       ArrayList templist = new ArrayList();
/* 1638 */       for (int i = 0; i < headerlist.size(); i++)
/*      */       {
/* 1640 */         String tempstr = (String)headerlist.get(i);
/* 1641 */         templist.add(tempstr.toLowerCase());
/*      */       }
/* 1643 */       if (monitortype.equals("SYSTEM"))
/*      */       {
/* 1645 */         if (templist.contains("operatingsystem")) {
/* 1646 */           isheadercorrect = true;
/*      */         }
/* 1648 */       } else if (monitortype.equals("JBoss"))
/*      */       {
/* 1650 */         if (templist.contains("jbossversion")) {
/* 1651 */           isheadercorrect = true;
/*      */         }
/* 1653 */       } else if (monitortype.equals(".Net"))
/*      */       {
/* 1655 */         if (templist.contains("hostname")) {
/* 1656 */           isheadercorrect = true;
/*      */         }
/* 1658 */       } else if (monitortype.equals("ORACLEAS"))
/*      */       {
/* 1660 */         if ((templist.contains("hostname")) && (templist.contains("port"))) {
/* 1661 */           isheadercorrect = true;
/*      */         }
/* 1663 */       } else if (monitortype.equals("Tomcat"))
/*      */       {
/* 1665 */         if (templist.contains("tomcatversion")) {
/* 1666 */           isheadercorrect = true;
/*      */         }
/* 1668 */       } else if (monitortype.equals("WLI"))
/*      */       {
/* 1670 */         if (templist.contains("weblogicintegrationversion")) {
/* 1671 */           isheadercorrect = true;
/*      */         }
/* 1673 */       } else if (monitortype.equals("WebLogic"))
/*      */       {
/* 1675 */         if (templist.contains("weblogicversion")) {
/* 1676 */           isheadercorrect = true;
/*      */         }
/* 1678 */       } else if (monitortype.equals("WebSphere"))
/*      */       {
/* 1680 */         if (templist.contains("deploymentmode")) {
/* 1681 */           isheadercorrect = true;
/*      */         }
/* 1683 */       } else if (monitortype.equals("DB2"))
/*      */       {
/* 1685 */         if ((templist.contains("databasename")) && (templist.contains("port"))) {
/* 1686 */           isheadercorrect = true;
/*      */         }
/* 1688 */       } else if (monitortype.equals("MSSQLDB"))
/*      */       {
/* 1690 */         if (templist.contains("port")) {
/* 1691 */           isheadercorrect = true;
/*      */         }
/* 1693 */       } else if (monitortype.equals("MYSQLDB"))
/*      */       {
/* 1695 */         if ((templist.contains("databasename")) && (templist.contains("port"))) {
/* 1696 */           isheadercorrect = true;
/*      */         }
/* 1698 */       } else if (monitortype.equals("ORACLEDB"))
/*      */       {
/* 1700 */         if ((templist.contains("instancename")) && (templist.contains("port"))) {
/* 1701 */           isheadercorrect = true;
/*      */         }
/* 1703 */       } else if (monitortype.equals("SYBASEDB"))
/*      */       {
/* 1705 */         if ((templist.contains("databasename")) && (templist.contains("port"))) {
/* 1706 */           isheadercorrect = true;
/*      */         }
/* 1708 */       } else if (monitortype.equals("UrlMonitor"))
/*      */       {
/* 1710 */         if (templist.contains("urladdress")) {
/* 1711 */           isheadercorrect = true;
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 1716 */         isheadercorrect = true;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1721 */       isheadercorrect = true;
/* 1722 */       ex.printStackTrace();
/*      */     }
/* 1724 */     return isheadercorrect;
/*      */   }
/*      */   
/*      */   public void getConfMonitors(HttpServletRequest request)
/*      */   {
/* 1729 */     ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 1730 */     HashMap confMonitorsList = conf.getConfMonitorsByCategory();
/* 1731 */     ArrayList servers = (ArrayList)confMonitorsList.get("SYS");
/* 1732 */     request.setAttribute("servers", servers);
/* 1733 */     ArrayList appservers = (ArrayList)confMonitorsList.get("APP");
/* 1734 */     request.setAttribute("appservers", appservers);
/* 1735 */     ArrayList dbservers = (ArrayList)confMonitorsList.get("DBS");
/* 1736 */     dbservers = removeNoSQLMonitors(dbservers);
/* 1737 */     request.setAttribute("dbservers", dbservers);
/* 1738 */     ArrayList services = (ArrayList)confMonitorsList.get("SER");
/* 1739 */     request.setAttribute("services", services);
/* 1740 */     ArrayList virservers = (ArrayList)confMonitorsList.get("VIR");
/* 1741 */     request.setAttribute("virservers", virservers);
/* 1742 */     ArrayList cloudapps = (ArrayList)confMonitorsList.get("CLD");
/* 1743 */     request.setAttribute("cloudapps", cloudapps);
/* 1744 */     ArrayList momserver = (ArrayList)confMonitorsList.get("MOM");
/* 1745 */     request.setAttribute("momserver", momserver);
/* 1746 */     ArrayList mailservers = (ArrayList)confMonitorsList.get("MS");
/* 1747 */     request.setAttribute("mailservers", mailservers);
/* 1748 */     ArrayList erp = (ArrayList)confMonitorsList.get("ERP");
/* 1749 */     request.setAttribute("erp", erp);
/* 1750 */     ArrayList webservers = (ArrayList)confMonitorsList.get("URL");
/* 1751 */     request.setAttribute("webservers", webservers);
/*      */   }
/*      */   
/*      */   private ArrayList removeNoSQLMonitors(ArrayList dbsrvs)
/*      */   {
/* 1756 */     ArrayList toRet = new ArrayList();
/* 1757 */     Iterator it = dbsrvs.iterator();
/* 1758 */     while (it.hasNext())
/*      */     {
/* 1760 */       Properties pp = (Properties)it.next();
/* 1761 */       Enumeration en = pp.keys();
/* 1762 */       while (en.hasMoreElements())
/*      */       {
/* 1764 */         String key = (String)en.nextElement();
/* 1765 */         if (!Constants.noSqlList.contains(key))
/*      */         {
/* 1767 */           toRet.add(pp);
/* 1768 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1772 */     return toRet;
/*      */   }
/*      */   
/*      */   public ActionForward getConfMonitorHeader(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1776 */     String monitorType = request.getParameter("resType");
/*      */     
/* 1778 */     String header = getConfMonitorHeader(monitorType, false, null);
/* 1779 */     response.setContentType("text/plain; charset=UTF-8");
/* 1780 */     PrintWriter pw = response.getWriter();
/* 1781 */     if ((header != null) && (header.trim().equals(""))) {
/* 1782 */       pw.print("Failed");
/*      */     }
/*      */     else {
/* 1785 */       monitorType = FormatUtil.findReplace(monitorType, " ", ".");
/* 1786 */       String toreturn = FormatUtil.getString("am.webclient.admin.bulkimport." + monitorType + ".help.text", new String[] { header });
/* 1787 */       pw.print(toreturn);
/*      */     }
/* 1789 */     return null;
/*      */   }
/*      */   
/*      */   public String getConfMonitorHeader(String monitorType, boolean checkHeader, ArrayList passWordColumns)
/*      */   {
/* 1794 */     StringBuilder header = new StringBuilder();
/*      */     try {
/* 1796 */       ArrayList args = NewMonitorUtil.getArgsforConfMon(monitorType);
/* 1797 */       ArrayList argsDisplayNames = (ArrayList)args.get(5);
/* 1798 */       ArrayList argsTypes = (ArrayList)args.get(1);
/* 1799 */       if (checkHeader) {
/* 1800 */         header.append("DisplayName");
/*      */       }
/*      */       else {
/* 1803 */         header.append("#Header:DisplayName");
/*      */       }
/* 1805 */       Iterator it = argsDisplayNames.iterator();
/*      */       
/* 1807 */       String isAgentEnabled = "NO";
/* 1808 */       Properties discoverPageUIDetails = ConfMonitorConfiguration.getInstance().getTypeDescription(monitorType);
/* 1809 */       if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null)) {
/* 1810 */         isAgentEnabled = discoverPageUIDetails.getProperty("IS-AGENT-ENABLED");
/*      */       }
/* 1812 */       int i = 0;
/* 1813 */       while (it.hasNext()) {
/* 1814 */         String argName = FormatUtil.getStringEn((String)it.next());
/* 1815 */         if ((passWordColumns != null) && (((String)argsTypes.get(i)).equals("7"))) {
/* 1816 */           passWordColumns.add(argName);
/*      */         }
/* 1818 */         i++;
/* 1819 */         if (((!isAgentEnabled.equals("YES")) || ((!argName.equals("isParent")) && (!argName.equals("UT")))) && 
/*      */         
/*      */ 
/* 1822 */           (!"Credential Details".equalsIgnoreCase(argName)))
/*      */         {
/* 1824 */           if ("Credential Manager".equalsIgnoreCase(argName)) {
/* 1825 */             header.append(",credentialID");
/*      */           } else
/* 1827 */             header.append(",").append(argName);
/*      */         }
/*      */       }
/* 1830 */       header.append(",MonitorGroupID,PollInterval");
/*      */       
/* 1832 */       if (isAgentEnabled.equals("YES")) {
/* 1833 */         header.append(",Run on server,Run on agent");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1837 */       e.printStackTrace();
/*      */     }
/* 1839 */     return header.toString();
/*      */   }
/*      */   
/*      */   public ArrayList getSelectedAgent(String runOnAgent) {
/*      */     try {
/* 1844 */       String[] agent = runOnAgent.split("\\|");
/* 1845 */       return new ArrayList(Arrays.asList(agent));
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1850 */       e.printStackTrace();
/*      */     }
/* 1852 */     return null;
/*      */   }
/*      */   
/*      */   public boolean checkConfHeader(String monitorType, ArrayList headerList, ArrayList passWordColumns)
/*      */   {
/* 1857 */     boolean toreturn = true;
/*      */     try {
/* 1859 */       String header = getConfMonitorHeader(monitorType, true, passWordColumns);
/* 1860 */       String[] headers = header.split(",");
/* 1861 */       List getheaderList = Arrays.asList(headers);
/* 1862 */       if (EnterpriseUtil.isAdminServer) {
/* 1863 */         if (headerList.contains("ManagedServerGroupName")) {
/* 1864 */           getheaderList.add("ManagedServerGroupName");
/*      */         }
/* 1866 */         if (headerList.contains("ManagedServerID")) {
/* 1867 */           getheaderList.add("ManagedServerID");
/*      */         }
/*      */       }
/* 1870 */       if ((!monitorType.equalsIgnoreCase("OfficeSharePointServer")) && (!monitorType.equalsIgnoreCase("Kafka")))
/*      */       {
/* 1872 */         if (getheaderList.size() != headerList.size()) {
/* 1873 */           toreturn = false;
/*      */         }
/*      */       }
/* 1876 */       Iterator it = getheaderList.iterator();
/* 1877 */       while (it.hasNext())
/*      */       {
/* 1879 */         String headername = ((String)it.next()).trim();
/* 1880 */         if ((!headerList.contains(headername)) && (!monitorType.equalsIgnoreCase("Kafka")) && (headerList.contains("controller_KafkaController_ActiveControllerCount"))) {
/* 1881 */           toreturn = false;
/* 1882 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1887 */       e.printStackTrace();
/*      */     }
/* 1889 */     return toreturn;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\BulkAddMonitors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */