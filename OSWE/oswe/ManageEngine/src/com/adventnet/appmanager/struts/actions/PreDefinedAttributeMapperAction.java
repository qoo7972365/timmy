/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.fault.predefinedthreshold.PDTTemplate;
/*     */ import com.adventnet.appmanager.fault.predefinedthreshold.PDTUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ public class PreDefinedAttributeMapperAction
/*     */   extends DispatchAction
/*     */ {
/*  52 */   private static Object obj = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward getPDTTemplates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  59 */     List<Properties> dependencyChain = new ArrayList();
/*     */     
/*  61 */     for (int i = 0;; i++)
/*     */     {
/*  63 */       String dependentEntry = request.getParameter("depChain" + i);
/*  64 */       if (!PDTUtil.stringValidate(dependentEntry))
/*     */         break;
/*  66 */       String[] dependentDetails = dependentEntry.split("=");
/*  67 */       if (dependentDetails.length == 2)
/*     */       {
/*  69 */         Properties props = new Properties();
/*  70 */         props.setProperty("MON_TYPE", dependentDetails[0]);
/*  71 */         props.setProperty("MON_TYPE_DISPLAY", dependentDetails[1]);
/*  72 */         dependencyChain.add(props);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  79 */     request.setAttribute("DEP_CHAIN", dependencyChain);
/*     */     
/*  81 */     String parMonType = request.getParameter("parMonType");
/*  82 */     String childMonType = request.getParameter("childMonType");
/*  83 */     Map<String, Map<String, String>> resTypes = PDTUtil.getResourceTypes();
/*  84 */     request.setAttribute("MON_TYPES", resTypes);
/*  85 */     String selectedMontype = request.getParameter("SELECTED_MON_TYPE");
/*  86 */     if (!PDTUtil.stringValidate(selectedMontype))
/*     */     {
/*  88 */       selectedMontype = "";
/*     */     }
/*     */     
/*  91 */     if (PDTUtil.stringValidate(childMonType))
/*     */     {
/*  93 */       PDTTemplate template = PDTUtil.getPDTTemplate(parMonType, childMonType);
/*  94 */       request.setAttribute("TEMPLATE", template);
/*     */       
/*  96 */       String lastDepenEntryMonType = "";
/*  97 */       if (dependencyChain.size() > 0)
/*     */       {
/*  99 */         Properties lastEntryProps = (Properties)dependencyChain.get(dependencyChain.size() - 1);
/* 100 */         lastDepenEntryMonType = lastEntryProps.getProperty("MON_TYPE");
/*     */       }
/*     */       
/* 103 */       if (!lastDepenEntryMonType.equals(template.getMonType()))
/*     */       {
/* 105 */         Properties props = new Properties();
/* 106 */         props.setProperty("MON_TYPE", template.getMonType());
/* 107 */         props.setProperty("MON_TYPE_DISPLAY", template.getMonTypeDisplayName());
/* 108 */         dependencyChain.add(props);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 113 */     request.setAttribute("SELECTED_MON_TYPE", selectedMontype);
/* 114 */     boolean showExportImport = !EnterpriseUtil.isAdminServer();
/* 115 */     request.setAttribute("showExportImport", Boolean.valueOf(showExportImport));
/*     */     
/*     */ 
/* 118 */     return mapping.findForward("PDTTEMPLATE");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward exportMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 125 */     synchronized (obj)
/*     */     {
/* 127 */       String filePath = generateAttrMapXml();
/* 128 */       if ((filePath != null) && (!filePath.trim().equals("")))
/*     */       {
/* 130 */         ActionMessages messages = new ActionMessages();
/* 131 */         ActionMessage message = new ActionMessage(FormatUtil.getString("am.webclient.admin.pdam.export.threshold.success", new String[] { "<a href='" + filePath + "' target=\"_blank\" class=\"staticlinks\">" + FormatUtil.getString("am.webclient.admin.pdam.export.threshold.download") + "</a>" }));
/* 132 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", message);
/* 133 */         saveMessages(request, messages);
/*     */       }
/*     */       else
/*     */       {
/* 137 */         ActionErrors errors = new ActionErrors();
/* 138 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.admin.pdam.export.threshold.failure")));
/* 139 */         saveErrors(request, errors);
/*     */       }
/*     */     }
/* 142 */     return getPDTTemplates(mapping, form, request, response);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward importMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 151 */     synchronized (obj)
/*     */     {
/* 153 */       String filePath = uploadTheFile(mapping, form, request, response);
/* 154 */       if ((filePath != null) && (!filePath.trim().equals("")))
/*     */       {
/* 156 */         importMapping(filePath);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 161 */         request.setAttribute("MESSAGE", FormatUtil.getString("am.webclient.admin.pdam.import.threshold.success"));
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 168 */         request.setAttribute("MESSAGE", FormatUtil.getString("am.webclient.admin.pdam.import.threshold.failure"));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 174 */     return mapping.findForward("IMPORTTHRESHOLDTEMPLATES");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String uploadTheFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 184 */     String toReturn = "";
/* 185 */     AMActionForm theForm = (AMActionForm)form;
/* 186 */     FormFile file = theForm.getTheFile();
/* 187 */     String fileName = file.getFileName();
/* 188 */     String uploadFileName = "";
/* 189 */     String contentType = file.getContentType();
/*     */     
/*     */ 
/*     */ 
/* 193 */     String size = file.getFileSize() + " bytes";
/* 194 */     String data = null;
/* 195 */     AMLog.debug(new StringBuilder("PreDefinedAttributeMapperAction => Import fileName:").append(fileName).append("; contentType:").append(contentType).append("; size:").append(size));
/* 196 */     if ((!isValid(fileName)) || (file.getFileSize() <= 0))
/*     */     {
/* 198 */       return "";
/*     */     }
/*     */     try
/*     */     {
/* 202 */       InputStream stream = file.getInputStream();
/*     */       
/* 204 */       String uploadDir = "./pdam/";
/* 205 */       File bulk_dir = new File("." + File.separator + "pdam");
/* 206 */       if (!bulk_dir.exists())
/*     */       {
/* 208 */         bulk_dir.mkdir();
/*     */       }
/* 210 */       uploadFileName = "PDAM_" + System.currentTimeMillis() + ".xml";
/* 211 */       OutputStream bos = new FileOutputStream(uploadDir + uploadFileName);
/* 212 */       int bytesRead = 0;
/* 213 */       byte[] buffer = new byte[' '];
/* 214 */       while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/* 215 */         bos.write(buffer, 0, bytesRead);
/*     */       }
/* 217 */       bos.close();
/* 218 */       data = "The file has been written to \"" + uploadDir + uploadFileName + "\"";
/* 219 */       AMLog.debug(data);
/*     */       
/* 221 */       stream.close();
/* 222 */       toReturn = uploadDir + uploadFileName;
/*     */     }
/*     */     catch (FileNotFoundException fnfe) {
/* 225 */       fnfe.printStackTrace();
/*     */     }
/*     */     catch (IOException ioe) {
/* 228 */       ioe.printStackTrace();
/*     */     }
/* 230 */     file.destroy();
/*     */     
/*     */ 
/* 233 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void importMapping(String fileName)
/*     */   {
/*     */     try
/*     */     {
/* 242 */       Document doc = getDocument(fileName);
/* 243 */       saveMapping(doc);
/*     */     }
/*     */     catch (Exception e) {
/* 246 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveMapping(Document doc)
/*     */   {
/* 252 */     if (doc != null)
/*     */     {
/* 254 */       NodeList tcsList = doc.getElementsByTagName("AM_THRESHOLDCONFIGS");
/* 255 */       if ((tcsList != null) && (tcsList.getLength() > 0))
/*     */       {
/* 257 */         Element tcs = (Element)tcsList.item(0);
/* 258 */         NodeList list = tcs.getElementsByTagName("AM_THRESHOLDCONFIG");
/* 259 */         if (list != null)
/*     */         {
/* 261 */           for (int i = 0; i < list.getLength(); i++)
/*     */           {
/* 263 */             Element tc = (Element)list.item(i);
/* 264 */             saveThresholdConfig(tc);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 269 */       NodeList preDefinedAttrThresholdsList = doc.getElementsByTagName("AM_PredefinedThresholds");
/* 270 */       if ((preDefinedAttrThresholdsList != null) && (preDefinedAttrThresholdsList.getLength() > 0))
/*     */       {
/*     */ 
/* 273 */         Element preDefinedAttrThresholds = (Element)preDefinedAttrThresholdsList.item(0);
/* 274 */         NodeList list = preDefinedAttrThresholds.getElementsByTagName("AM_PredefinedThreshold");
/* 275 */         if (list != null)
/*     */         {
/* 277 */           for (int i = 0; i < list.getLength(); i++)
/*     */           {
/* 279 */             Element preDefinedAttrThreshold = (Element)list.item(i);
/* 280 */             String resType = preDefinedAttrThreshold.getAttribute("RESOURCETYPE");
/* 281 */             String attr = preDefinedAttrThreshold.getAttribute("ATTRIBUTE");
/* 282 */             String thrName = preDefinedAttrThreshold.getAttribute("NAME");
/*     */             
/* 284 */             Map<String, String> thDetails = getThresholdDetails(thrName);
/* 285 */             Map<String, String> attrDetails = getAttributeDetails(resType, attr);
/* 286 */             if ((thDetails != null) && (!thDetails.isEmpty()) && (attrDetails != null) && (!attrDetails.isEmpty()))
/*     */             {
/* 288 */               int attrId = Integer.parseInt((String)attrDetails.get("ATTRIBUTEID"));
/* 289 */               int thId = Integer.parseInt((String)thDetails.get("ID"));
/* 290 */               int attrType = Integer.parseInt((String)attrDetails.get("TYPE"));
/* 291 */               int thType = Integer.parseInt((String)thDetails.get("TYPE"));
/*     */               
/* 293 */               if (isthrAttrTypeCompatible(thType, attrType))
/*     */               {
/* 295 */                 if ((attrId > 0) && (thId > 0) && (!isPDTMapperPresent(attrId, thId)))
/*     */                 {
/* 297 */                   saveAttrThresholdMapper(attrId, thId);
/*     */                 }
/*     */                 
/*     */               }
/*     */               else {
/* 302 */                 AMLog.debug("PreDefinedAttributeMapperAction => As attribute type and threshold type are incompatible ,Predefined threshold is not populated for threshold " + thDetails + " and attribute " + attrDetails);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
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
/*     */   private static boolean isthrAttrTypeCompatible(int thType, int attrType)
/*     */   {
/* 328 */     return ((thType != 2) && (thType != 1)) || ((attrType == 0) || ((thType == 3) && (attrType == 3)) || ((thType == 4) && (attrType == 7)));
/*     */   }
/*     */   
/*     */   private static void saveAttrThresholdMapper(int attrId, int thId) {
/* 332 */     if ((attrId > 0) && (thId > 0))
/*     */     {
/* 334 */       String deleteQuery = "delete from AM_PredefinedThreshold where  ATTRIBUTEID=" + attrId;
/* 335 */       String query = "insert into AM_PredefinedThreshold values(" + attrId + "," + thId + ")";
/*     */       try {
/* 337 */         AMConnectionPool.executeUpdateStmt(deleteQuery);
/* 338 */         AMConnectionPool.executeUpdateStmt(query);
/*     */       } catch (SQLException e) {
/* 340 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 345 */   private static boolean isPDTMapperPresent(int attrId, int thId) { toReturn = true;
/* 346 */     if ((attrId > 0) && (thId > 0))
/*     */     {
/* 348 */       String query = "select * from AM_PredefinedThreshold where ATTRIBUTEID=" + attrId + " and THRESHOLDCONFIGURATIONID=" + thId;
/* 349 */       ResultSet rs = null;
/*     */       try
/*     */       {
/* 352 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 353 */         if (!rs.next()) {}
/*     */         
/* 355 */         return false;
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 360 */         e.printStackTrace();
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 365 */         if (rs != null) {
/*     */           try
/*     */           {
/* 368 */             AMConnectionPool.closeStatement(rs);
/* 369 */             rs.close();
/*     */           } catch (SQLException e) {
/* 371 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static Map<String, String> getAttributeDetails(String resType, String attr) {
/* 379 */     toReturn = new HashMap();
/* 380 */     if ((isValid(resType)) && (isValid(attr)))
/*     */     {
/* 382 */       String query = "select ATTRIBUTEID,TYPE from AM_ATTRIBUTES where RESOURCETYPE='" + resType + "' and ATTRIBUTE='" + attr + "'";
/* 383 */       ResultSet rs = null;
/*     */       try
/*     */       {
/* 386 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 387 */         if (rs.next())
/*     */         {
/* 389 */           toReturn.put("ATTRIBUTEID", rs.getString("ATTRIBUTEID"));
/* 390 */           toReturn.put("TYPE", rs.getString("TYPE"));
/*     */         }
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
/* 410 */         return toReturn;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 395 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 399 */         if (rs != null) {
/*     */           try
/*     */           {
/* 402 */             AMConnectionPool.closeStatement(rs);
/* 403 */             rs.close();
/*     */           } catch (SQLException e) {
/* 405 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void saveThresholdConfig(Element tc, int thId)
/*     */   {
/* 415 */     if (tc != null)
/*     */     {
/* 417 */       String thName = tc.getAttribute("NAME");
/* 418 */       String thType = tc.getAttribute("TYPE");
/* 419 */       String thDescr = tc.getAttribute("DESCRIPTION");
/* 420 */       String crThresholdCondn = tc.getAttribute("CRITICALTHRESHOLDCONDITION");
/* 421 */       String crThresholdValue = tc.getAttribute("CRITICALTHRESHOLDVALUE");
/* 422 */       String crThresholdMessage = tc.getAttribute("CRITICALTHRESHOLDMESSAGE");
/* 423 */       String warnThresholdCondn = tc.getAttribute("WARNINGTHRESHOLDCONDITION");
/* 424 */       String warnThresholdValue = tc.getAttribute("WARNINGTHRESHOLDVALUE");
/* 425 */       String warnThresholdMessage = tc.getAttribute("WARNINGTHRESHOLDMESSAGE");
/* 426 */       String infoThresholdCondn = tc.getAttribute("INFOTHRESHOLDCONDITION");
/* 427 */       String infoThresholdValue = tc.getAttribute("INFOTHRESHOLDVALUE");
/* 428 */       String infoThresholdMessage = tc.getAttribute("INFOTHRESHOLDMESSAGE");
/* 429 */       String criticalPollsToTry = tc.getAttribute("CRITICAL_POLLSTOTRY");
/* 430 */       String warnPollsToTry = tc.getAttribute("WARNING_POLLSTOTRY");
/* 431 */       String clearPollsToTry = tc.getAttribute("CLEAR_POLLSTOTRY");
/*     */       
/* 433 */       if (thId <= 0) {
/* 434 */         StringBuilder query = new StringBuilder("insert into AM_THRESHOLDCONFIG select ").append(" max(ID)+1  ").append(",'").append(thName).append("','").append(thType).append("','").append(thDescr).append("','").append(crThresholdCondn).append("','").append(crThresholdValue).append("','").append(crThresholdMessage).append("','").append(warnThresholdCondn).append("','").append(warnThresholdValue).append("','").append(warnThresholdMessage).append("','").append(infoThresholdCondn).append("','").append(infoThresholdValue).append("','").append(infoThresholdMessage).append("','").append(criticalPollsToTry).append("','").append(warnPollsToTry).append("','").append(clearPollsToTry).append("' from AM_THRESHOLDCONFIG ;");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 444 */           AMConnectionPool.executeUpdateStmt(query.toString());
/* 445 */           thId = getthresholdId(thName);
/*     */           
/* 447 */           if (thId > 0)
/*     */           {
/* 449 */             NodeList list = tc.getElementsByTagName("AM_PATTERNMATCHERCONFIG");
/* 450 */             if ((list != null) && (list.getLength() > 0))
/*     */             {
/* 452 */               Element pmc = (Element)list.item(0);
/* 453 */               String pmccv = pmc.getAttribute("CRITICALTHRESHOLDVALUE");
/* 454 */               String pmcwv = pmc.getAttribute("WARNINGTHRESHOLDVALUE");
/* 455 */               String pmciv = pmc.getAttribute("INFOTHRESHOLDVALUE");
/* 456 */               query = new StringBuilder("insert into AM_PATTERNMATCHERCONFIG values('").append(thId).append("','").append(pmccv).append("','").append(pmcwv).append("','").append(pmciv).append("');");
/*     */               try
/*     */               {
/* 459 */                 AMConnectionPool.executeUpdateStmt(query.toString());
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 463 */                 e.printStackTrace();
/*     */               }
/*     */             }
/* 466 */             list = tc.getElementsByTagName("AM_FLOAT_THRESHOLDCONFIG");
/* 467 */             if ((list != null) && (list.getLength() > 0))
/*     */             {
/* 469 */               Element pmc = (Element)list.item(0);
/* 470 */               String ftccv = pmc.getAttribute("CRITICALTHRESHOLDVALUE");
/* 471 */               String ftcwv = pmc.getAttribute("WARNINGTHRESHOLDVALUE");
/* 472 */               String ftciv = pmc.getAttribute("INFOTHRESHOLDVALUE");
/* 473 */               query = new StringBuilder("insert into AM_FLOAT_THRESHOLDCONFIG values('").append(thId).append("','").append(ftccv).append("','").append(ftcwv).append("','").append(ftciv).append("');");
/*     */               try
/*     */               {
/* 476 */                 AMConnectionPool.executeUpdateStmt(query.toString());
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 480 */                 e.printStackTrace();
/*     */               }
/*     */               
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 489 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 494 */         StringBuilder query = new StringBuilder("update AM_THRESHOLDCONFIG ").append("set NAME ='").append(thName).append("',").append(" TYPE ='").append(thType).append("',").append(" DESCRIPTION ='").append(thDescr).append("',").append(" CRITICALTHRESHOLDCONDITION ='").append(crThresholdCondn).append("',").append(" CRITICALTHRESHOLDVALUE ='").append(crThresholdValue).append("',").append("  CRITICALTHRESHOLDMESSAGE ='").append(crThresholdMessage).append("',").append("  WARNINGTHRESHOLDCONDITION ='").append(warnThresholdCondn).append("',").append("  WARNINGTHRESHOLDVALUE ='").append(warnThresholdValue).append("',").append("  WARNINGTHRESHOLDMESSAGE ='").append(warnThresholdMessage).append("',").append("  INFOTHRESHOLDCONDITION ='").append(infoThresholdCondn).append("',").append("  INFOTHRESHOLDVALUE ='").append(infoThresholdValue).append("',").append("  INFOTHRESHOLDMESSAGE ='").append(infoThresholdMessage).append("',").append("  CRITICAL_POLLSTOTRY ='").append(criticalPollsToTry).append("',").append("  WARNING_POLLSTOTRY ='").append(warnPollsToTry).append("',").append("  CLEAR_POLLSTOTRY ='").append(clearPollsToTry).append("'").append(" where ID='").append(thId).append("'");
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
/*     */         try
/*     */         {
/* 512 */           AMConnectionPool.executeUpdateStmt(query.toString());
/* 513 */           NodeList list = tc.getElementsByTagName("AM_PATTERNMATCHERCONFIG");
/* 514 */           if ((list != null) && (list.getLength() > 0))
/*     */           {
/* 516 */             Element pmc = (Element)list.item(0);
/* 517 */             String pmccv = pmc.getAttribute("CRITICALTHRESHOLDVALUE");
/* 518 */             String pmcwv = pmc.getAttribute("WARNINGTHRESHOLDVALUE");
/* 519 */             String pmciv = pmc.getAttribute("INFOTHRESHOLDVALUE");
/* 520 */             query = new StringBuilder("update AM_PATTERNMATCHERCONFIG ").append(" set CRITICALTHRESHOLDVALUE ='").append(pmccv).append("',").append(" WARNINGTHRESHOLDVALUE ='").append(pmcwv).append("',").append(" INFOTHRESHOLDVALUE ='").append(pmciv).append("' ").append(" where ID='").append(thId).append("'");
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/* 527 */               AMConnectionPool.executeUpdateStmt(query.toString());
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 531 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 534 */           list = tc.getElementsByTagName("AM_FLOAT_THRESHOLDCONFIG");
/* 535 */           if ((list != null) && (list.getLength() > 0))
/*     */           {
/* 537 */             Element pmc = (Element)list.item(0);
/* 538 */             String ftccv = pmc.getAttribute("CRITICALTHRESHOLDVALUE");
/* 539 */             String ftcwv = pmc.getAttribute("WARNINGTHRESHOLDVALUE");
/* 540 */             String ftciv = pmc.getAttribute("INFOTHRESHOLDVALUE");
/* 541 */             query = new StringBuilder("update AM_FLOAT_THRESHOLDCONFIG ").append(" set CRITICALTHRESHOLDVALUE ='").append(ftccv).append("',").append(" WARNINGTHRESHOLDVALUE ='").append(ftcwv).append("',").append(" INFOTHRESHOLDVALUE ='").append(ftciv).append("' ").append(" where ID='").append(thId).append("'");
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/* 548 */               AMConnectionPool.executeUpdateStmt(query.toString());
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 552 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/* 559 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 562 */       AMCacheHandler.setThresholdProfileinCache(thId);
/*     */     }
/*     */   }
/*     */   
/* 566 */   private static void saveThresholdConfig(Element tc) { if (tc != null)
/*     */     {
/* 568 */       String thName = tc.getAttribute("NAME");
/* 569 */       int thType = Integer.parseInt(tc.getAttribute("TYPE"));
/*     */       
/* 571 */       if (isValid(thName))
/*     */       {
/* 573 */         int thId = getthresholdId(thName);
/*     */         
/* 575 */         if (thId <= 0)
/*     */         {
/* 577 */           AMLog.debug("PreDefinedAttributeMapperAction => Import => THRESHOLD not present hence adding as new. IMPORTED THRESHOLD DETAILS:" + tc);
/* 578 */           saveThresholdConfig(tc, thId);
/*     */         }
/*     */         else
/*     */         {
/* 582 */           Map<String, String> thDetails = getThresholdDetails(thName);
/* 583 */           if ((thDetails != null) && (!thDetails.isEmpty()))
/*     */           {
/* 585 */             int thresholdType = Integer.parseInt((String)thDetails.get("TYPE"));
/* 586 */             if (thType == thresholdType)
/*     */             {
/* 588 */               AMLog.debug("PreDefinedAttributeMapperAction => Import => THRESHOLD OVERWRITTEN AS THERE IS AN EXISTING THRESHOLD WITH SAME NAME AND SAME DATA TYPE . OVERWRITTEN THRESHOLD DETAILS:" + tc);
/* 589 */               saveThresholdConfig(tc, thId);
/*     */             }
/*     */             else
/*     */             {
/* 593 */               AMLog.debug("PreDefinedAttributeMapperAction => Import => THRESHOLD NOT IMPORTED AS THERE IS AN EXISTING THRESHOLD WITH SAME NAME BUT DIFFERENT DATA TYPE .UNIMPORTED THRESHOLD DETAILS:" + tc);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static Map<String, String> getThresholdDetails(String thresholdname)
/*     */   {
/* 603 */     toReturn = new HashMap();
/* 604 */     String query = "select * from AM_THRESHOLDCONFIG where NAME='" + thresholdname + "'";
/* 605 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 608 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 609 */       ResultSetMetaData metaData = rs.getMetaData();
/* 610 */       List<String> columnNames = new ArrayList();
/* 611 */       for (int i = 1; i <= metaData.getColumnCount(); i++)
/*     */       {
/* 613 */         String columnName = metaData.getColumnName(i);
/* 614 */         columnNames.add(columnName);
/*     */       }
/*     */       
/* 617 */       while (rs.next())
/*     */       {
/* 619 */         for (String columnName : columnNames)
/*     */         {
/* 621 */           String columnValue = validate(rs.getString(columnName));
/* 622 */           toReturn.put(columnName, validate(columnValue));
/*     */         }
/*     */       }
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
/* 645 */       return toReturn;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 628 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 632 */       if (rs != null) {
/*     */         try
/*     */         {
/* 635 */           AMConnectionPool.closeStatement(rs);
/* 636 */           rs.close();
/*     */         } catch (SQLException e) {
/* 638 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int getthresholdId(String thresholdname)
/*     */   {
/* 650 */     thId = -1;
/* 651 */     if (isValid(thresholdname))
/*     */     {
/* 653 */       String query = "select ID from AM_THRESHOLDCONFIG where NAME='" + thresholdname + "'";
/*     */       
/* 655 */       ResultSet rs = null;
/*     */       try
/*     */       {
/* 658 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 659 */         if (rs.next()) {}
/*     */         
/* 661 */         return rs.getInt("ID");
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 666 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 670 */         if (rs != null) {
/*     */           try
/*     */           {
/* 673 */             AMConnectionPool.closeStatement(rs);
/* 674 */             rs.close();
/*     */           } catch (SQLException e) {
/* 676 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static Document getDocument(String filePath) throws Exception
/*     */   {
/* 685 */     Document doc = null;
/* 686 */     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/* 687 */     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
/* 688 */     doc = dBuilder.parse(filePath);
/* 689 */     doc.getDocumentElement().normalize();
/* 690 */     return doc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String generateAttrMapXml()
/*     */   {
/* 700 */     String toReturn = "";
/* 701 */     Document doc = createDocument("ATTRIBUTE_MAPPER");
/* 702 */     doc = addTCs(doc);
/* 703 */     doc = addPreDefinedTcs(doc);
/* 704 */     String fileName = "Thresholds.xml";
/* 705 */     String filePath = "." + File.separator + fileName;
/* 706 */     if (doc != null)
/*     */     {
/*     */       try
/*     */       {
/* 710 */         writeXmlFile(doc, filePath);
/* 711 */         toReturn = fileName;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 715 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 718 */     return toReturn;
/*     */   }
/*     */   
/*     */   private Document addPreDefinedTcs(Document doc) {
/* 722 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 725 */       Element pdts = null;
/* 726 */       rs = AMConnectionPool.executeQueryStmt(getPreDefinedTcsQuery());
/* 727 */       if (rs == null)
/*     */       {
/* 729 */         return doc;
/*     */       }
/*     */       
/*     */ 
/* 733 */       Element root = doc.getDocumentElement();
/* 734 */       pdts = doc.createElement("AM_PredefinedThresholds");
/* 735 */       root.appendChild(pdts);
/*     */       
/* 737 */       while (rs.next())
/*     */       {
/* 739 */         Element pdt = doc.createElement("AM_PredefinedThreshold");
/* 740 */         pdts.appendChild(pdt);
/* 741 */         ResultSetMetaData metaData = rs.getMetaData();
/* 742 */         for (int i = 1; i <= metaData.getColumnCount(); i++)
/*     */         {
/* 744 */           String columnName = metaData.getColumnName(i);
/* 745 */           if (isValid(columnName))
/*     */           {
/* 747 */             String columnValue = validate(rs.getString(columnName));
/* 748 */             pdt.setAttribute(columnName, validate(columnValue));
/*     */           }
/*     */         }
/*     */       }
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
/* 769 */       return doc;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 755 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 759 */       if (rs != null) {
/*     */         try
/*     */         {
/* 762 */           AMConnectionPool.closeStatement(rs);
/* 763 */           rs.close();
/*     */         } catch (SQLException e) {
/* 765 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Document addTCs(Document doc)
/*     */   {
/* 773 */     ResultSet rs = null;
/*     */     try {
/* 775 */       Element tcs = null;
/* 776 */       rs = AMConnectionPool.executeQueryStmt(getTCsQuery());
/* 777 */       if (rs == null)
/*     */       {
/* 779 */         return doc;
/*     */       }
/*     */       
/*     */ 
/* 783 */       Element root = doc.getDocumentElement();
/* 784 */       tcs = doc.createElement("AM_THRESHOLDCONFIGS");
/* 785 */       root.appendChild(tcs);
/*     */       for (;;) {
/* 787 */         if (rs.next())
/*     */         {
/* 789 */           String tcId = rs.getString("ID");
/*     */           
/* 791 */           if (isValid(tcId))
/*     */           {
/* 793 */             int tcID = Integer.parseInt(tcId);
/* 794 */             int tcType = rs.getInt("TYPE");
/* 795 */             Element tc = doc.createElement("AM_THRESHOLDCONFIG");
/* 796 */             tcs.appendChild(tc);
/* 797 */             ResultSetMetaData metaData = rs.getMetaData();
/* 798 */             for (int i = 1; i <= metaData.getColumnCount(); i++)
/*     */             {
/* 800 */               String columnName = metaData.getColumnName(i);
/* 801 */               if (isValid(columnName))
/*     */               {
/* 803 */                 String columnValue = validate(rs.getString(columnName));
/* 804 */                 tc.setAttribute(columnName, validate(columnValue));
/*     */               }
/*     */             }
/* 807 */             if (tcType == 3)
/*     */             {
/* 809 */               ResultSet rs1 = null;
/*     */               try
/*     */               {
/* 812 */                 rs1 = AMConnectionPool.executeQueryStmt(getPatternMatcherQuery(tcID));
/* 813 */                 if ((rs1 != null) && (rs1.next()))
/*     */                 {
/* 815 */                   Element pmc = doc.createElement("AM_PATTERNMATCHERCONFIG");
/* 816 */                   tc.appendChild(pmc);
/* 817 */                   ResultSetMetaData metaData1 = rs1.getMetaData();
/* 818 */                   for (int i = 1; i <= metaData1.getColumnCount(); i++)
/*     */                   {
/* 820 */                     String columnName = metaData1.getColumnName(i);
/* 821 */                     if (isValid(columnName))
/*     */                     {
/* 823 */                       String columnValue = validate(rs1.getString(columnName));
/* 824 */                       pmc.setAttribute(columnName, validate(columnValue));
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */               catch (SQLException e)
/*     */               {
/* 831 */                 e.printStackTrace();
/*     */               }
/*     */               finally {}
/*     */             }
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
/* 846 */             if (tcType == 4)
/*     */             {
/* 848 */               ResultSet rs1 = null;
/*     */               try
/*     */               {
/* 851 */                 rs1 = AMConnectionPool.executeQueryStmt(getFloatTCQuery(tcID));
/* 852 */                 if ((rs1 != null) && (rs1.next()))
/*     */                 {
/* 854 */                   Element ftc = doc.createElement("AM_FLOAT_THRESHOLDCONFIG");
/* 855 */                   tc.appendChild(ftc);
/* 856 */                   ResultSetMetaData metaData1 = rs1.getMetaData();
/* 857 */                   for (int i = 1; i <= metaData1.getColumnCount(); i++)
/*     */                   {
/* 859 */                     String columnName = metaData1.getColumnName(i);
/* 860 */                     if (isValid(columnName))
/*     */                     {
/* 862 */                       String columnValue = validate(rs1.getString(columnName));
/* 863 */                       ftc.setAttribute(columnName, validate(columnValue));
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 874 */                 if (rs1 != null) {
/*     */                   try
/*     */                   {
/* 877 */                     AMConnectionPool.closeStatement(rs1);
/* 878 */                     rs1.close();
/*     */                   } catch (SQLException e) {
/* 880 */                     e.printStackTrace();
/*     */                   }
/*     */                 }
/*     */               }
/*     */               catch (SQLException e)
/*     */               {
/* 870 */                 e.printStackTrace();
/*     */               }
/*     */               finally {}
/*     */             }
/*     */           }
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 902 */       return doc;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 888 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 892 */       if (rs != null) {
/*     */         try
/*     */         {
/* 895 */           AMConnectionPool.closeStatement(rs);
/* 896 */           rs.close();
/*     */         } catch (SQLException e) {
/* 898 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String getPreDefinedTcsQuery()
/*     */   {
/* 906 */     return "select a.RESOURCETYPE,a.ATTRIBUTE,tc.NAME from AM_PredefinedThreshold  pt inner join AM_ATTRIBUTES a on a.ATTRIBUTEID=pt.ATTRIBUTEID inner join AM_THRESHOLDCONFIG tc on tc.ID=pt.THRESHOLDCONFIGURATIONID";
/*     */   }
/*     */   
/*     */   private String getPatternMatcherQuery(int tcId) {
/* 910 */     StringBuilder toReturn = new StringBuilder("");
/* 911 */     if (tcId > 0)
/*     */     {
/* 913 */       toReturn.append("select * from AM_PATTERNMATCHERCONFIG where ID=").append(tcId);
/*     */     }
/* 915 */     return toReturn.toString();
/*     */   }
/*     */   
/*     */   private String getFloatTCQuery(int tcId) {
/* 919 */     StringBuilder toReturn = new StringBuilder("");
/* 920 */     if (tcId > 0)
/*     */     {
/* 922 */       toReturn.append("select * from AM_FLOAT_THRESHOLDCONFIG where ID=").append(tcId);
/*     */     }
/* 924 */     return toReturn.toString();
/*     */   }
/*     */   
/*     */   private String getTCsQuery() {
/* 928 */     return "select * FROM  AM_THRESHOLDCONFIG WHERE AM_THRESHOLDCONFIG.NAME NOT IN ( 'Marker THRESHOLD','Availability Threshold','Health Threshold','WTA Exception') AND AM_THRESHOLDCONFIG.ID  > 2 AND    DESCRIPTION  <> '##Threshod for URL##' ";
/*     */   }
/*     */   
/*     */   private static Document createDocument(String rootName)
/*     */   {
/* 933 */     Document doc = null;
/*     */     try
/*     */     {
/* 936 */       DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
/* 937 */       dbfactory.setValidating(false);
/* 938 */       DocumentBuilder builder = dbfactory.newDocumentBuilder();
/*     */       
/* 940 */       DOMImplementation impl = builder.getDOMImplementation();
/* 941 */       doc = impl.createDocument(null, rootName, null);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 945 */       ex.printStackTrace();
/*     */     }
/* 947 */     return doc;
/*     */   }
/*     */   
/* 950 */   public static void writeXmlFile(Document doc, String filename) throws Exception { TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */     
/* 952 */     Transformer transformer = transformerFactory.newTransformer();
/* 953 */     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/* 954 */     transformer.setOutputProperty("indent", "yes");
/* 955 */     DOMSource source = new DOMSource(doc);
/* 956 */     File f = new File(filename);
/* 957 */     boolean b; if (!f.exists())
/*     */     {
/* 959 */       b = f.createNewFile();
/*     */     }
/* 961 */     StreamResult result = new StreamResult(f.toURI().getPath());
/* 962 */     transformer.transform(source, result);
/* 963 */     AMLog.debug("PreDefinedAttributeMapperAction => Export =>Exported successfully to " + filename);
/*     */   }
/*     */   
/*     */   private static boolean isValid(String string) {
/* 967 */     return (string != null) && (!string.trim().equals(""));
/*     */   }
/*     */   
/*     */   private static String validate(String string) {
/* 971 */     return string != null ? string : "";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\PreDefinedAttributeMapperAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */