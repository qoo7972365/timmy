/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import HTTPClient.HttpURLConnection;
/*     */ import com.adventnet.appmanager.customfields.MyFields;
/*     */ import com.adventnet.appmanager.customfields.SingleMyField;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.actions.MyFieldAction;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.utils.client.URITree;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class MyFieldServlet extends javax.servlet.http.HttpServlet
/*     */ {
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  33 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  37 */     PrintWriter out = response.getWriter();
/*     */     
/*  39 */     String customFiledObject = request.getParameter("customFieldObject");
/*     */     
/*     */ 
/*  42 */     ObjectInputStream ois = null;
/*  43 */     String xmlResp = "";
/*  44 */     boolean issuccess = true;
/*  45 */     HashMap fieldSynchstatus = new HashMap();
/*     */     
/*     */     try
/*     */     {
/*  49 */       String url = "https://" + EnterpriseUtil.getAdminServerHost() + ":" + EnterpriseUtil.getAdminServerPort() + "/servlet/CustomFieldsFeedServlet?customFieldObject=" + customFiledObject;
/*  50 */       URL server = new URL(url);
/*  51 */       HttpURLConnection con = new HttpURLConnection(server);
/*  52 */       con.setRequestProperty("Content-Type", "text/xml");
/*  53 */       ois = new ObjectInputStream(con.getInputStream());
/*  54 */       HashMap synchDetails = (HashMap)ois.readObject();
/*     */       
/*     */ 
/*     */ 
/*  58 */       AMLog.debug("MyFieldServlet ############--------------->" + synchDetails);
/*  59 */       boolean newlyadded = false;
/*  60 */       if ((synchDetails.get("Newlyadded") != null) && (synchDetails.get("Newlyadded").equals("true"))) {
/*  61 */         newlyadded = true;
/*     */       }
/*  63 */       if (!newlyadded) {
/*  64 */         HashMap fieldData = (HashMap)synchDetails.get("FIELDDATA");
/*  65 */         HashMap locationData = (HashMap)synchDetails.get("LOCATIONDATA");
/*  66 */         if ((fieldData != null) && (!fieldData.isEmpty())) {
/*  67 */           Iterator it = fieldData.keySet().iterator();
/*  68 */           while (it.hasNext()) {
/*  69 */             String fieldId = (String)it.next();
/*  70 */             Properties statusProp = new Properties();
/*  71 */             if (fieldData.get(fieldId) != null) {
/*  72 */               HashMap individualFieldData = (HashMap)fieldData.get(fieldId);
/*  73 */               String fieldAction = (String)individualFieldData.get("TASKACTION");
/*  74 */               statusProp.setProperty("ACTION", fieldAction);
/*  75 */               boolean status = false;
/*  76 */               if (fieldAction.equals("DELETE")) {
/*  77 */                 status = deleteField(fieldId);
/*     */               }
/*  79 */               else if (fieldAction.equals("DELETEENUM")) {
/*  80 */                 status = deleteENUM(fieldId, fieldAction, individualFieldData);
/*     */               }
/*     */               else {
/*  83 */                 status = createOrEditField(fieldId, fieldAction, individualFieldData);
/*     */               }
/*  85 */               if (status) {
/*  86 */                 statusProp.setProperty("STATUS", "SUCCESS");
/*     */               }
/*     */               else {
/*  89 */                 statusProp.setProperty("STATUS", "FAILED");
/*     */               }
/*  91 */               fieldSynchstatus.put(fieldId, statusProp);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  96 */         if ((locationData != null) && (!locationData.isEmpty())) {
/*  97 */           Iterator it = locationData.keySet().iterator();
/*  98 */           while (it.hasNext()) {
/*  99 */             String locId = (String)it.next();
/* 100 */             Properties statusProp = new Properties();
/* 101 */             if (locationData.get(locId) != null) {
/* 102 */               HashMap individualLocData = (HashMap)locationData.get(locId);
/* 103 */               String locAction = (String)individualLocData.get("TASKACTION");
/* 104 */               statusProp.setProperty("ACTION", locAction);
/* 105 */               boolean status = false;
/* 106 */               if (locAction.equals("DELETE_LOCATION")) {
/* 107 */                 status = deleteLocation(locId);
/*     */               }
/*     */               else {
/* 110 */                 status = createOrEditLocation(locId, locAction, individualLocData);
/*     */               }
/* 112 */               if (status) {
/* 113 */                 statusProp.setProperty("STATUS", "SUCCESS");
/*     */               }
/*     */               else {
/* 116 */                 statusProp.setProperty("STATUS", "FAILED");
/*     */               }
/* 118 */               fieldSynchstatus.put(locId, statusProp);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         try {
/* 126 */           insertMetaData(synchDetails);
/* 127 */           insertLocationData(synchDetails);
/* 128 */           insertTemplateData(synchDetails);
/*     */         }
/*     */         catch (Exception e) {
/* 131 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 143 */         if (ois != null) {
/* 144 */           ois.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 149 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 156 */         if (issuccess) {
/* 157 */           xmlResp = URITree.generateXMLForCustomFields(request, response, fieldSynchstatus);
/*     */         }
/*     */         else {
/* 160 */           xmlResp = URITree.generateXML(request, response, "Failed", "3333");
/*     */         }
/* 162 */         AMLog.debug("MyFieldServlet : xmlResp : ========================> " + xmlResp);
/* 163 */         out.println(xmlResp);
/* 164 */         out.close();
/*     */       }
/*     */       catch (Exception e) {
/* 167 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 137 */       e.printStackTrace();
/* 138 */       issuccess = false;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 143 */         if (ois != null) {
/* 144 */           ois.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 149 */         e.printStackTrace();
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
/*     */ 
/*     */   public boolean deleteField(String fieldId)
/*     */   {
/*     */     try
/*     */     {
/* 174 */       if (isFieldAlreadyExists(fieldId)) {
/* 175 */         return MyFieldAction.deleteField(fieldId);
/*     */       }
/*     */       
/* 178 */       return true;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 182 */       e.printStackTrace();
/*     */     }
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   public boolean deleteLocation(String locId) {
/* 188 */     try { if (isLocAlreadyExists(locId)) {
/* 189 */         MyFields myfield = new MyFields();
/* 190 */         myfield.setDataTable("AM_MYFIELDS_LOCATION");
/* 191 */         myfield.setEntityPrimaryKey("LOCATIONID");
/* 192 */         myfield.setEntityValueId(locId);
/* 193 */         return myfield.deleteEntity();
/*     */       }
/*     */       
/* 196 */       return true;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 200 */       e.printStackTrace();
/*     */     }
/* 202 */     return false;
/*     */   }
/*     */   
/* 205 */   public boolean deleteENUM(String fieldId, String fieldAction, HashMap individualFieldData) { boolean status = true;
/*     */     try {
/* 207 */       if (individualFieldData.get("FIELDVALUE") != null) {
/* 208 */         Properties fieldValues = (Properties)individualFieldData.get("FIELDVALUE");
/* 209 */         status = editOrDeleteEnumeration(fieldId, fieldValues, fieldAction);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 214 */       e.printStackTrace();
/* 215 */       status = false;
/*     */     }
/* 217 */     return status;
/*     */   }
/*     */   
/*     */   public boolean createOrEditField(String fieldId, String fieldAction, HashMap individualFieldData) {
/* 221 */     Properties fieldMetaData = null;
/* 222 */     boolean status = true;
/*     */     try {
/* 224 */       if ((fieldAction.equals("CREATE")) && (individualFieldData.get("METADATA") != null) && (!isFieldAlreadyExists(fieldId))) {
/* 225 */         fieldMetaData = (Properties)individualFieldData.get("METADATA");
/* 226 */         status = addField(fieldId, fieldMetaData);
/* 227 */         if (individualFieldData.get("FIELDVALUE") != null) {
/* 228 */           Properties fieldValues = (Properties)individualFieldData.get("FIELDVALUE");
/* 229 */           status = editOrDeleteEnumeration(fieldId, fieldValues, fieldAction);
/*     */         }
/*     */       }
/*     */       
/* 233 */       if (fieldAction.equals("EDIT")) {
/* 234 */         if (individualFieldData.get("METADATA") != null) {
/* 235 */           fieldMetaData = (Properties)individualFieldData.get("METADATA");
/*     */           try {
/* 237 */             AMConnectionPool.executeUpdateStmt("update AM_MYFIELDS_METADATA set DISPLAYNAME='" + fieldMetaData.getProperty("DISPLAYNAME") + "',DESCRIPTION='" + fieldMetaData.getProperty("DESCRIPTION") + "',ENABLED='" + fieldMetaData.getProperty("ENABLED") + "' where FIELDID=" + fieldId);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 241 */             e.printStackTrace();
/* 242 */             status = false;
/*     */           }
/*     */         }
/* 245 */         if (individualFieldData.get("FIELDVALUE") != null) {
/* 246 */           Properties fieldValues = (Properties)individualFieldData.get("FIELDVALUE");
/* 247 */           status = editOrDeleteEnumeration(fieldId, fieldValues, fieldAction);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 252 */       e.printStackTrace();
/* 253 */       status = false;
/*     */     }
/*     */     
/* 256 */     return status;
/*     */   }
/*     */   
/*     */   public boolean addField(String fieldId, Properties fieldMetaData) {
/* 260 */     try { SingleMyField singlefield = new SingleMyField();
/* 261 */       singlefield.setDisplayName(fieldMetaData.getProperty("DISPLAYNAME"));
/* 262 */       singlefield.setFieldType(Integer.valueOf(fieldMetaData.getProperty("FIELDTYPE")).intValue());
/* 263 */       singlefield.setDataType(fieldMetaData.getProperty("DATATYPE"));
/* 264 */       singlefield.setDescription(fieldMetaData.getProperty("DESCRIPTION"));
/* 265 */       singlefield.setDataTable(fieldMetaData.getProperty("DATATABLE"));
/* 266 */       singlefield.setFieldid(fieldId);
/* 267 */       singlefield.setAliasName(fieldMetaData.getProperty("ALIASNAME"));
/* 268 */       if (fieldMetaData.getProperty("ENABLED").equals("1")) {
/* 269 */         singlefield.setEnabled(true);
/*     */       }
/*     */       else {
/* 272 */         singlefield.setEnabled(false);
/*     */       }
/* 274 */       ArrayList<String> listValues = new ArrayList();
/* 275 */       singlefield.setEnumerationList(listValues);
/* 276 */       MyFields fields = new MyFields();
/* 277 */       fields.addField(singlefield);
/* 278 */       return MyFields.addNewField(fields);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 282 */       e.printStackTrace();
/*     */     }
/* 284 */     return false;
/*     */   }
/*     */   
/* 287 */   public boolean isFieldAlreadyExists(String fieldId) { String qry = "select FieldId from AM_MYFIELDS_METADATA where FIELDID=" + fieldId;
/* 288 */     ResultSet rs = null;
/*     */     try {
/* 290 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 291 */       if (rs.next()) {
/* 292 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 296 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 299 */       if (rs != null) {
/* 300 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 303 */     return false;
/*     */   }
/*     */   
/* 306 */   public boolean createOrEditLocation(String locId, String locAction, HashMap individualLocData) { boolean status = true;
/*     */     try {
/* 308 */       if (individualLocData.get("LOCATIONDATA") != null) {
/* 309 */         Properties locMetaData = (Properties)individualLocData.get("LOCATIONDATA");
/* 310 */         status = addLocation(locMetaData, locId);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 314 */       e.printStackTrace();
/* 315 */       status = false;
/*     */     }
/* 317 */     return status;
/*     */   }
/*     */   
/*     */   public boolean addLocation(Properties locMetaData, String locId)
/*     */   {
/*     */     try
/*     */     {
/* 324 */       MyFields myFieldGroup = new MyFields();
/* 325 */       myFieldGroup.setEntityPrimaryKey("LOCATIONID");
/* 326 */       myFieldGroup.setDataTable("AM_MYFIELDS_LOCATION");
/* 327 */       myFieldGroup.setEntityValueId(locId);
/* 328 */       MyFieldAction.getDBTableColumns(null, myFieldGroup, null, true, locMetaData, null, "AM_MYFIELDS_LOCATION");
/* 329 */       return myFieldGroup.setEntityRowToDB();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 333 */       e.printStackTrace();
/*     */     }
/* 335 */     return false;
/*     */   }
/*     */   
/* 338 */   public String updateFieldListQuery(Properties locMetaData) { Iterator it = locMetaData.keySet().iterator();
/* 339 */     String toreturn = "";
/* 340 */     while (it.hasNext()) {
/* 341 */       String key = (String)it.next();
/* 342 */       String value = locMetaData.getProperty(key);
/* 343 */       if (!toreturn.equals("")) {
/* 344 */         toreturn = toreturn + "," + key + "='" + value + "'";
/*     */       }
/*     */       else {
/* 347 */         toreturn = key + "='" + value + "'";
/*     */       }
/*     */     }
/* 350 */     return toreturn;
/*     */   }
/*     */   
/* 353 */   public boolean isLocAlreadyExists(String locId) { String qry = "select LOCATIONID from AM_MYFIELDS_LOCATION where LOCATIONID=" + locId;
/* 354 */     ResultSet rs = null;
/*     */     try {
/* 356 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 357 */       if (rs.next()) {
/* 358 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 362 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 365 */       if (rs != null) {
/* 366 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 369 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean editOrDeleteEnumeration(String fieldId, Properties fieldValues, String fieldAction)
/*     */   {
/* 375 */     Set existingFieldValues = new HashSet();
/* 376 */     boolean status = true;
/*     */     try {
/* 378 */       if (!fieldAction.equals("CREATE")) {
/* 379 */         status = deleteExistingENUMList(fieldId, existingFieldValues);
/*     */       }
/* 381 */       status = addNewENUMList(fieldId, fieldValues, existingFieldValues);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 385 */       e.printStackTrace();
/* 386 */       status = false;
/*     */     }
/* 388 */     return status;
/*     */   }
/*     */   
/* 391 */   public boolean addNewENUMList(String fieldId, Properties fieldValues, Set existingFieldValues) { PreparedStatement ps = null;
/* 392 */     Iterator it = fieldValues.keySet().iterator();
/* 393 */     boolean status = true;
/*     */     try
/*     */     {
/* 396 */       ArrayList listValues = new ArrayList();
/* 397 */       ArrayList listValueIds = new ArrayList();
/* 398 */       while (it.hasNext()) {
/* 399 */         String valueid = (String)it.next();
/* 400 */         existingFieldValues.remove(valueid);
/* 401 */         listValues.add(fieldValues.getProperty(valueid));
/* 402 */         listValueIds.add(valueid);
/*     */       }
/*     */       
/* 405 */       SingleMyField singlefield = new SingleMyField();
/* 406 */       singlefield.setEnumerationList(listValues);
/* 407 */       singlefield.setFieldid(fieldId);
/* 408 */       singlefield.setEnumerationValueIdList(listValueIds);
/* 409 */       status = MyFields.addToEnumeration(singlefield, true, "CREATE");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 413 */       e.printStackTrace();
/* 414 */       status = false;
/*     */     }
/* 416 */     status = deleteENUMresourceMapping(existingFieldValues, fieldId);
/* 417 */     return status;
/*     */   }
/*     */   
/*     */   public boolean deleteExistingENUMList(String fieldId, Set existingFieldValues) {
/* 421 */     String selectQry = "select VALUEID from AM_MYFIELDS_TEMPLATEDATA where FIELDID=" + fieldId;
/* 422 */     boolean status = true;
/* 423 */     ResultSet rs = null;
/*     */     try {
/* 425 */       rs = AMConnectionPool.executeQueryStmt(selectQry);
/* 426 */       while (rs.next()) {
/* 427 */         existingFieldValues.add(rs.getString("VALUEID"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 432 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 435 */       if (rs != null) {
/* 436 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 439 */     String deleteQry = "delete from AM_MYFIELDS_TEMPLATEDATA where FIELDID=" + fieldId;
/*     */     try {
/* 441 */       AMConnectionPool.executeUpdateStmt(deleteQry);
/*     */     }
/*     */     catch (Exception e) {
/* 444 */       e.printStackTrace();
/* 445 */       status = false;
/*     */     }
/* 447 */     return status;
/*     */   }
/*     */   
/*     */   public boolean deleteENUMresourceMapping(Set existingFieldValues, String fieldId) {
/* 451 */     try { Iterator it = existingFieldValues.iterator();
/* 452 */       String deleteIds = "";
/* 453 */       while (it.hasNext()) {
/* 454 */         String valueid = (String)it.next();
/* 455 */         if (deleteIds.equals("")) {
/* 456 */           deleteIds = valueid;
/*     */         }
/*     */         else {
/* 459 */           deleteIds = deleteIds + "," + valueid;
/*     */         }
/*     */       }
/* 462 */       if (!deleteIds.trim().equals("")) {
/* 463 */         String deleteQry = "delete from AM_MYFIELDS_LABELDATA where VALUEID in(" + deleteIds + ")";
/* 464 */         AMConnectionPool.executeUpdateStmt(deleteQry);
/*     */       }
/* 466 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/* 469 */       e.printStackTrace();
/*     */     }
/* 471 */     return false;
/*     */   }
/*     */   
/* 474 */   public void insertMetaData(HashMap synchDetails) { if (synchDetails.get("FIELD") != null) {
/* 475 */       HashMap fieldData = (HashMap)synchDetails.get("FIELD");
/* 476 */       Iterator it = fieldData.keySet().iterator();
/* 477 */       ArrayList filedList = new ArrayList();
/* 478 */       getFieldIdList(filedList);
/* 479 */       String qry = "";
/* 480 */       Statement toinsert = null;
/*     */       try {
/* 482 */         AMConnectionPool.getInstance();toinsert = AMConnectionPool.getConnection().createStatement();
/* 483 */         while (it.hasNext()) {
/* 484 */           String fieldId = (String)it.next();
/* 485 */           Properties prop = (Properties)fieldData.get(fieldId);
/* 486 */           if (filedList.contains(fieldId)) {
/* 487 */             String updatequery = updateFieldListQuery(prop);
/*     */             
/* 489 */             qry = "update AM_MYFIELDS_METADATA set " + updatequery + " where FIELDID=" + fieldId;
/*     */           }
/*     */           else {
/* 492 */             qry = "insert into AM_MYFIELDS_METADATA values(" + fieldId + ",'" + prop.getProperty("ALIASNAME") + "','" + prop.getProperty("DISPLAYNAME") + "','" + prop.getProperty("DESCRIPTION") + "','" + prop.getProperty("DATATYPE") + "'," + prop.getProperty("FIELDTYPE") + ",'" + prop.getProperty("DATATABLE") + "'," + prop.getProperty("ENABLED") + ")";
/*     */           }
/* 494 */           toinsert.addBatch(qry);
/*     */         }
/*     */         try {
/* 497 */           toinsert.executeBatch();
/*     */         }
/*     */         catch (Exception e) {
/* 500 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 504 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         try {
/* 508 */           if (toinsert != null) {
/* 509 */             toinsert.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void insertLocationData(HashMap synchDetails)
/*     */   {
/* 519 */     if (synchDetails.get("LOCATION") != null) {
/* 520 */       HashMap locData = (HashMap)synchDetails.get("LOCATION");
/* 521 */       Iterator it = locData.keySet().iterator();
/* 522 */       ArrayList locList = new ArrayList();
/* 523 */       getlocIdList(locList);
/* 524 */       String qry = "";
/* 525 */       Statement toinsert = null;
/*     */       try {
/* 527 */         AMConnectionPool.getInstance();toinsert = AMConnectionPool.getConnection().createStatement();
/* 528 */         while (it.hasNext()) {
/* 529 */           String locId = (String)it.next();
/* 530 */           Properties prop = (Properties)locData.get(locId);
/* 531 */           if (locList.contains(locId)) {
/* 532 */             String updatequery = updateFieldListQuery(prop);
/* 533 */             qry = "update AM_MYFIELDS_LOCATION set " + updatequery + " where LOCATIONID=" + locId;
/*     */           }
/*     */           else {
/* 536 */             qry = "insert into AM_MYFIELDS_LOCATION values (" + locId + ",'" + prop.getProperty("LOCATION_NAME") + "','" + prop.getProperty("LOCATION_FLOOR") + "','" + prop.getProperty("LOCATION_BUILDING") + "','" + prop.getProperty("LOCATION_CITY") + "','" + prop.getProperty("LOCATION_STATE") + "','" + prop.getProperty("LOCATION_COUNTRY") + "','" + prop.getProperty("LOCATION_POSTALCODE") + "','" + prop.getProperty("LOCATION_ZIPCODE") + "')";
/*     */           }
/*     */           
/* 539 */           toinsert.addBatch(qry);
/*     */         }
/*     */         try
/*     */         {
/* 543 */           toinsert.executeBatch();
/*     */         }
/*     */         catch (Exception e) {
/* 546 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 550 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         try {
/* 554 */           if (toinsert != null) {
/* 555 */             toinsert.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void insertTemplateData(HashMap synchDetails)
/*     */   {
/* 565 */     if (synchDetails.get("ENUM") != null) {
/* 566 */       HashMap enumData = (HashMap)synchDetails.get("ENUM");
/* 567 */       Iterator it = enumData.keySet().iterator();
/* 568 */       ArrayList enumList = new ArrayList();
/* 569 */       getenumIdList(enumList);
/* 570 */       String qry = "";
/* 571 */       Statement toinsert = null;
/*     */       try {
/* 573 */         AMConnectionPool.getInstance();toinsert = AMConnectionPool.getConnection().createStatement();
/* 574 */         while (it.hasNext()) {
/* 575 */           String valueId = (String)it.next();
/* 576 */           Properties prop = (Properties)enumData.get(valueId);
/* 577 */           if (enumList.contains(valueId)) {
/* 578 */             String updatequery = updateFieldListQuery(prop);
/* 579 */             qry = "update AM_MYFIELDS_TEMPLATEDATA set " + updatequery + " where VALUEID=" + valueId;
/*     */           }
/*     */           else {
/* 582 */             qry = "insert into AM_MYFIELDS_TEMPLATEDATA values (" + valueId + ",'" + prop.getProperty("FIELDID") + "','" + prop.getProperty("VALUE") + "')";
/*     */           }
/*     */           
/* 585 */           toinsert.addBatch(qry);
/*     */         }
/*     */         try
/*     */         {
/* 589 */           toinsert.executeBatch();
/*     */         }
/*     */         catch (Exception e) {
/* 592 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 596 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         try {
/* 600 */           if (toinsert != null) {
/* 601 */             toinsert.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getenumIdList(ArrayList enumList)
/*     */   {
/* 613 */     String selectQry = "select VALUEID from AM_MYFIELDS_TEMPLATEDATA";
/* 614 */     ResultSet rs = null;
/*     */     try {
/* 616 */       rs = AMConnectionPool.executeQueryStmt(selectQry);
/* 617 */       while (rs.next()) {
/* 618 */         enumList.add(rs.getString("VALUEID"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 623 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 626 */       if (rs != null)
/* 627 */         AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/*     */   public void getlocIdList(ArrayList locList) {
/* 632 */     String selectQry = "select LOCATIONID from AM_MYFIELDS_LOCATION";
/* 633 */     ResultSet rs = null;
/*     */     try {
/* 635 */       rs = AMConnectionPool.executeQueryStmt(selectQry);
/* 636 */       while (rs.next()) {
/* 637 */         locList.add(rs.getString("LOCATIONID"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 642 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 645 */       if (rs != null) {
/* 646 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void getFieldIdList(ArrayList filedList) {
/* 652 */     String selectQry = "select FIELDID from AM_MYFIELDS_METADATA";
/* 653 */     ResultSet rs = null;
/*     */     try {
/* 655 */       rs = AMConnectionPool.executeQueryStmt(selectQry);
/* 656 */       while (rs.next()) {
/* 657 */         filedList.add(rs.getString("FIELDID"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 662 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 665 */       if (rs != null) {
/* 666 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\MyFieldServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */