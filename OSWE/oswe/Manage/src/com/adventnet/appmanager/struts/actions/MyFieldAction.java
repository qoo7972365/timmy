/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.customfields.SingleMyField;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.me.apm.cmdb.APMHDSettingsUtil;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil;
/*      */ import com.me.helpdesk.object.CISettings;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.htmlparser.util.Translate;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MyFieldAction
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward getMyFields(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   59 */     String resourceid = request.getParameter("resourceid");
/*   60 */     String entity = request.getParameter("entity");
/*   61 */     String mgview = request.getParameter("mgview");
/*   62 */     boolean isConfMonitor = false;
/*      */     
/*   64 */     if (request.getParameter("isConfMonitor") != null) {
/*   65 */       isConfMonitor = request.getParameter("isConfMonitor").equals("true");
/*      */     }
/*   67 */     boolean fromParent = false;
/*   68 */     boolean fromAlarm = false;
/*   69 */     boolean mgviewinclude = false;
/*      */     try {
/*   71 */       if ((entity != null) && (!entity.equals("noalarms")) && (!entity.equals("undefined")))
/*      */       {
/*   73 */         fromAlarm = true;
/*      */       }
/*   75 */       if ((mgview != null) && (mgview.equals("true"))) {
/*   76 */         mgviewinclude = true;
/*      */       }
/*      */       
/*      */ 
/*   80 */       String[] dataTables = MyFields.myfieldDataTables;
/*      */       
/*   82 */       if ((request.getAttribute("userassociation") != null) && (request.getAttribute("userassociation").equals(Boolean.valueOf(true)))) {
/*   83 */         dataTables = MyFields.userDataTables;
/*      */       }
/*      */       
/*   86 */       ArrayList<MyFields> fieldGroups = new ArrayList();
/*   87 */       Vector<String> moList = new Vector();
/*   88 */       for (int i = 0; i < dataTables.length; i++) {
/*   89 */         MyFields eachFieldGroup = new MyFields();
/*   90 */         eachFieldGroup.setResourceid(resourceid);
/*   91 */         eachFieldGroup.setDataTable(dataTables[i]);
/*   92 */         fromParent = eachFieldGroup.getFieldsFromParentIfEmpty(moList);
/*   93 */         eachFieldGroup.setFromParent(fromParent);
/*   94 */         fieldGroups.add(eachFieldGroup);
/*      */       }
/*   96 */       request.setAttribute("fieldGroups", fieldGroups);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  100 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  108 */     if ((request.getAttribute("userassociation") != null) && (request.getAttribute("userassociation").equals(Boolean.valueOf(true)))) {
/*  109 */       return new ActionForward("/jsp/MyField_Users.jsp?resourceid=" + resourceid + "&mgview=" + mgviewinclude + "&fromAlarm=" + fromAlarm + "&fromParent=" + fromParent);
/*      */     }
/*  111 */     return new ActionForward("/jsp/MyFields.jsp?resourceid=" + resourceid + "&mgview=" + mgviewinclude + "&fromAlarm=" + fromAlarm + "&fromParent=" + fromParent + "&isConfMonitor=" + isConfMonitor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getUserAssociation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  122 */     String resourceid = request.getParameter("resourceid");
/*  123 */     String fromAlarm = null;
/*  124 */     String entity = request.getParameter("entity");
/*  125 */     if (entity.equals("true")) {
/*  126 */       fromAlarm = "fromAlarm";
/*      */     } else {
/*  128 */       fromAlarm = "noalarms";
/*      */     }
/*  130 */     request.setAttribute("userassociation", Boolean.valueOf(true));
/*  131 */     return new ActionForward("/myFields.do?method=getMyFields&resourceid=" + resourceid + "&entity=" + fromAlarm);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getAdminUserAssociation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  141 */     String fromAlarm = null;
/*  142 */     String resourceid = request.getParameter("resourceid");
/*  143 */     String entity = request.getParameter("entity");
/*  144 */     if (entity.equals("true")) {
/*  145 */       fromAlarm = "fromAlarm";
/*      */     } else {
/*  147 */       fromAlarm = "noalarms";
/*      */     }
/*  149 */     request.setAttribute("userassociation", Boolean.valueOf(true));
/*  150 */     request.setAttribute("appendAdminUsers", Boolean.valueOf(true));
/*  151 */     return new ActionForward("/myFields.do?method=getMyFields&resourceid=" + resourceid + "&entity=" + fromAlarm);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getSelectedEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  161 */     response.setContentType("text/html;charset=UTF-8");
/*  162 */     String resourceid = request.getParameter("resourceid");
/*  163 */     String dataTable = request.getParameter("dataTable");
/*  164 */     String selectedEntityId = request.getParameter("selectedEntityId");
/*      */     try {
/*  166 */       Vector<String> moList = new Vector();
/*  167 */       ArrayList<MyFields> fieldGroups = new ArrayList();
/*  168 */       MyFields eachFieldGroup = new MyFields();
/*  169 */       eachFieldGroup.setResourceid(resourceid);
/*  170 */       eachFieldGroup.setDataTable(dataTable);
/*  171 */       eachFieldGroup.setEntityValueId(selectedEntityId);
/*  172 */       eachFieldGroup.getFieldsFromParentIfEmpty(moList);
/*  173 */       fieldGroups.add(eachFieldGroup);
/*  174 */       request.setAttribute("fieldGroups", fieldGroups);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  178 */       ex.printStackTrace();
/*      */     }
/*  180 */     request.setAttribute("editEntity", Boolean.valueOf(true));
/*  181 */     return new ActionForward("/jsp/MyField_Entities.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward saveMyFields(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  192 */     ResultSet rs1 = null;
/*  193 */     String resourceid = request.getParameter("resourceid");
/*      */     try {
/*  195 */       Enumeration<String> params = request.getParameterNames();
/*  196 */       ArrayList<String> fieldids = new ArrayList();
/*  197 */       while (params.hasMoreElements()) {
/*  198 */         String eachParam = (String)params.nextElement();
/*  199 */         if (eachParam.indexOf("myfield_") != -1) {
/*  200 */           eachParam = eachParam.substring(8);
/*  201 */           fieldids.add(eachParam);
/*      */         }
/*      */       }
/*      */       
/*  205 */       HashMap<String, MyFields> dataTables = new HashMap();
/*  206 */       String columnsQuery = "select FIELDID,ALIASNAME,DATATABLE,FIELDTYPE from AM_MYFIELDS_METADATA where " + ManagedApplication.getCondition("FIELDID", fieldids);
/*  207 */       rs1 = AMConnectionPool.executeQueryStmt(columnsQuery);
/*  208 */       while (rs1.next()) {
/*  209 */         String aliasName = rs1.getString("ALIASNAME");
/*  210 */         String dataTable = rs1.getString("DATATABLE");
/*  211 */         int fieldType = rs1.getInt("FIELDTYPE");
/*  212 */         String value = request.getParameter("myfield_" + rs1.getString("FIELDID")).trim();
/*  213 */         if (value.equals("-")) {
/*  214 */           value = "";
/*      */         }
/*  216 */         SingleMyField eachField = new SingleMyField();
/*  217 */         eachField.setAliasName(aliasName);
/*  218 */         eachField.setValue(value);
/*      */         MyFields myFieldGroup;
/*  220 */         MyFields myFieldGroup; if (dataTables.get(dataTable) != null) {
/*  221 */           myFieldGroup = (MyFields)dataTables.get(dataTable);
/*      */         }
/*      */         else
/*      */         {
/*  225 */           myFieldGroup = new MyFields();
/*  226 */           myFieldGroup.setResourceid(resourceid);
/*  227 */           myFieldGroup.setDataTable(dataTable);
/*  228 */           dataTables.put(dataTable, myFieldGroup);
/*      */         }
/*  230 */         myFieldGroup.addField(eachField);
/*  231 */         if (fieldType == MyFields.entitySelect) {
/*  232 */           myFieldGroup.setEntity(true);
/*  233 */           myFieldGroup.setEntityValueId(value);
/*      */         }
/*      */       }
/*  236 */       List<MyFields> myList = new ArrayList(dataTables.values());
/*  237 */       for (int index = 0; index < myList.size(); index++) {
/*  238 */         MyFields eachGroup = (MyFields)myList.get(index);
/*  239 */         eachGroup.setFieldListToDB();
/*      */       }
/*      */       try
/*      */       {
/*  243 */         if (APMHDSettingsUtil.getCiSettingsCache().isCiAttributesToBeSynced())
/*      */         {
/*  245 */           APMHelpDeskUtil.updateCI(Integer.parseInt(resourceid));
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  250 */         e.printStackTrace();
/*      */       }
/*  252 */       String formName = request.getParameter("formname");
/*  253 */       if ((formName != null) && (formName.equals("firstLevelform"))) {
/*  254 */         request.setAttribute("flatFieldTables", Boolean.valueOf(true));
/*      */       } else {
/*  256 */         request.setAttribute("editEntity", Boolean.valueOf(true));
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  261 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  264 */       if (rs1 != null) {
/*  265 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/*      */     }
/*  268 */     boolean isConfMonitor = false;
/*  269 */     if (request.getParameter("isConfMonitor") != null) {
/*  270 */       isConfMonitor = request.getParameter("isConfMonitor").equals("true");
/*      */     }
/*  272 */     return new ActionForward("/myFields.do?method=getMyFields&resourceid=" + resourceid + "&isConfMonitor=" + isConfMonitor);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward saveEntityTemplateValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  279 */     String resourceid = request.getParameter("resourceid");
/*  280 */     String entityValueId = request.getParameter("entityValueId");
/*  281 */     String dataTable = request.getParameter("dataTable");
/*  282 */     String isNewEntity = request.getParameter("isNewEntity");
/*  283 */     String fieldSearchStr = "entityfield_";
/*  284 */     if ("true".equals(isNewEntity)) {
/*  285 */       fieldSearchStr = "entitynewfield_";
/*  286 */       entityValueId = "-100";
/*      */     }
/*  288 */     boolean isConfMonitor = false;
/*  289 */     if (request.getParameter("isConfMonitor") != null) {
/*  290 */       isConfMonitor = request.getParameter("isConfMonitor").equals("true");
/*      */     }
/*  292 */     int searchStrLength = fieldSearchStr.length();
/*      */     try {
/*  294 */       MyFields myFieldGroup = new MyFields();
/*  295 */       myFieldGroup.setResourceid(resourceid);
/*  296 */       myFieldGroup.setDataTable(dataTable);
/*  297 */       myFieldGroup.setEntity(true);
/*  298 */       myFieldGroup.setEntityValueId(entityValueId);
/*  299 */       Enumeration<String> params = request.getParameterNames();
/*  300 */       ArrayList<String> fieldids = new ArrayList();
/*  301 */       while (params.hasMoreElements()) {
/*  302 */         String eachParam = (String)params.nextElement();
/*  303 */         if (eachParam.indexOf(fieldSearchStr) != -1) {
/*  304 */           eachParam = eachParam.substring(searchStrLength);
/*  305 */           fieldids.add(eachParam);
/*      */         }
/*      */       }
/*  308 */       getDBTableColumns(fieldids, myFieldGroup, request, false, null, fieldSearchStr, null);
/*      */       
/*  310 */       if (entityValueId.equals("-100")) {
/*  311 */         AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  312 */         int locationId = dao.getAutoLocationId();
/*  313 */         myFieldGroup.setEntityValueId(String.valueOf(locationId));
/*      */       }
/*      */       
/*  316 */       myFieldGroup.getEntityRelatedDetails();
/*  317 */       myFieldGroup.setEntityRowToDB();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  321 */       ex.printStackTrace();
/*      */     }
/*  323 */     request.setAttribute("editEntity", Boolean.valueOf(true));
/*  324 */     return new ActionForward("/myFields.do?method=getMyFields&resourceid=" + resourceid + "&isConfMonitor=" + isConfMonitor);
/*      */   }
/*      */   
/*  327 */   public static void getDBTableColumns(ArrayList<String> fieldids, MyFields myFieldGroup, HttpServletRequest request, boolean fromAdmin, Properties fieldProps, String fieldSearchStr, String dataTable) { String columnsQuery = "select FIELDID,ALIASNAME,DATATABLE,FIELDTYPE from AM_MYFIELDS_METADATA";
/*  328 */     if ((fieldids != null) && (!fromAdmin)) {
/*  329 */       columnsQuery = columnsQuery + " where " + ManagedApplication.getCondition("FIELDID", fieldids);
/*      */     }
/*  331 */     else if ((fromAdmin) && (dataTable != null) && (dataTable.equals("AM_MYFIELDS_LOCATION"))) {
/*  332 */       columnsQuery = columnsQuery + " where DATATABLE='AM_MYFIELDS_LOCATION'";
/*      */     }
/*  334 */     ResultSet rs1 = null;
/*      */     try {
/*  336 */       rs1 = AMConnectionPool.executeQueryStmt(columnsQuery);
/*  337 */       while (rs1.next()) {
/*  338 */         String aliasName = rs1.getString("ALIASNAME");
/*  339 */         String value = "";
/*  340 */         if (fromAdmin) {
/*  341 */           value = fieldProps.getProperty(aliasName);
/*      */         }
/*      */         else {
/*  344 */           value = request.getParameter(fieldSearchStr + rs1.getString("FIELDID"));
/*      */         }
/*  346 */         SingleMyField eachField = new SingleMyField();
/*  347 */         eachField.setAliasName(aliasName);
/*  348 */         eachField.setValue(value);
/*  349 */         myFieldGroup.addField(eachField);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  353 */       ex.printStackTrace();
/*      */     } finally {
/*  355 */       if (rs1 != null) {
/*  356 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward saveMySingleField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  363 */     String resourceid = request.getParameter("resourceid");
/*  364 */     String fieldid = request.getParameter("fieldid");
/*  365 */     String fieldValue = URLDecoder.decode(request.getParameter("fieldvalue"));
/*  366 */     fieldValue = MyFields.checkForSpecialChar(fieldValue);
/*      */     
/*  368 */     ResultSet tableNameRS = null;
/*  369 */     ResultSet rs = null;
/*  370 */     String aliasname = null;
/*  371 */     String datatable = null;
/*      */     try {
/*  373 */       String query = "select ALIASNAME,DATATABLE from AM_MYFIELDS_METADATA where FIELDID=" + fieldid;
/*      */       
/*  375 */       tableNameRS = AMConnectionPool.executeQueryStmt(query);
/*  376 */       if (tableNameRS.next()) {
/*  377 */         aliasname = tableNameRS.getString("ALIASNAME");
/*  378 */         datatable = tableNameRS.getString("DATATABLE");
/*      */       }
/*      */       
/*  381 */       query = "select RESOURCEID from " + datatable + " where RESOURCEID=" + resourceid;
/*      */       
/*  383 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  384 */       if (rs.next()) {
/*  385 */         AMConnectionPool.executeUpdateStmt("update " + datatable + " set " + aliasname + "='" + fieldValue + "' where RESOURCEID=" + resourceid);
/*  386 */         EnterpriseUtil.addUpdateQueryToFile("update " + datatable + " set " + aliasname + "='" + fieldValue + "' where RESOURCEID=" + resourceid);
/*      */       } else {
/*  388 */         String insertVal = MyFields.getCustomFieldFromParent(resourceid, datatable);
/*  389 */         if (insertVal.length() > 0) {
/*  390 */           AMConnectionPool.executeUpdateStmt("insert into " + datatable + " values (" + insertVal + ")");
/*  391 */           AMConnectionPool.executeUpdateStmt("update " + datatable + " set " + aliasname + "='" + fieldValue + "' where RESOURCEID=" + resourceid);
/*  392 */           EnterpriseUtil.addUpdateQueryToFile("insert into " + datatable + " values (" + insertVal + ")");
/*  393 */           EnterpriseUtil.addUpdateQueryToFile("update " + datatable + " set " + aliasname + "='" + fieldValue + "' where RESOURCEID=" + resourceid);
/*      */         } else {
/*  395 */           AMConnectionPool.executeUpdateStmt("insert into " + datatable + " (RESOURCEID," + aliasname + ") values(" + resourceid + ",'" + fieldValue + "')");
/*  396 */           EnterpriseUtil.addUpdateQueryToFile("insert into " + datatable + " (RESOURCEID," + aliasname + ") values(" + resourceid + ",'" + fieldValue + "')");
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  400 */       ex.printStackTrace();
/*      */     } finally {
/*  402 */       if (rs != null) {
/*  403 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*  405 */       if (tableNameRS != null) {
/*  406 */         AMConnectionPool.closeStatement(tableNameRS);
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  411 */       if (APMHDSettingsUtil.getCiSettingsCache().isCiAttributesToBeSynced())
/*      */       {
/*  413 */         APMHelpDeskUtil.updateCI(Integer.parseInt(resourceid));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  418 */       e.printStackTrace();
/*      */     }
/*  420 */     request.setAttribute("flatFieldTables", Boolean.valueOf(true));
/*      */     
/*  422 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward EditFieldsMetaData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  432 */     String messageid = null;
/*  433 */     String messageValue = null;
/*  434 */     ResultSet rs1 = null;
/*      */     try
/*      */     {
/*  437 */       messageid = request.getParameter("messagefieldid");
/*  438 */       String fieldMetaDataQuery = "select FIELDID,DISPLAYNAME,DESCRIPTION,FIELDTYPE,DATATABLE,ENABLED from AM_MYFIELDS_METADATA where DATATABLE in ('AM_MYFIELDS_LABELDATA','AM_MYFIELDS_SYSTEMDATA','AM_MYFIELDS_USERDATA') order by FIELDID desc";
/*  439 */       rs1 = AMConnectionPool.executeQueryStmt(fieldMetaDataQuery);
/*  440 */       MyFields myFieldGroup = new MyFields();
/*  441 */       while (rs1.next()) {
/*  442 */         SingleMyField eachField = new SingleMyField();
/*  443 */         boolean isenabled = true;
/*  444 */         String fieldid = rs1.getString("FIELDID");
/*  445 */         int fieldtype = rs1.getInt("FIELDTYPE");
/*  446 */         String dataTable = rs1.getString("DATATABLE");
/*  447 */         String displayName = rs1.getString("DISPLAYNAME");
/*  448 */         String description = rs1.getString("DESCRIPTION");
/*  449 */         int enabled = rs1.getInt("ENABLED");
/*  450 */         if (enabled == 0) {
/*  451 */           isenabled = false;
/*      */         }
/*  453 */         if ((messageid != null) && (messageid.equals(fieldid))) {
/*  454 */           messageValue = FormatUtil.getString(displayName);
/*      */         }
/*  456 */         eachField.setEnabled(isenabled);
/*  457 */         eachField.setFieldid(fieldid);
/*  458 */         request.setAttribute("templatedata_" + fieldid, MyFields.getEntryForEnumeration(fieldid));
/*  459 */         eachField.setDataTable(dataTable);
/*  460 */         eachField.setDisplayName(displayName);
/*  461 */         eachField.setFieldType(fieldtype);
/*  462 */         eachField.setDescription(description);
/*  463 */         myFieldGroup.addField(eachField);
/*      */       }
/*  465 */       request.setAttribute("fieldGroup", myFieldGroup);
/*  466 */       request.setAttribute("showmessage", Boolean.valueOf(false));
/*  467 */       request.setAttribute("newenummessage", Boolean.valueOf(false));
/*  468 */       String displaymessage = request.getParameter("displaymessage");
/*  469 */       if ((displaymessage != null) && (displaymessage.equals("newfield"))) {
/*  470 */         request.setAttribute("showmessage", Boolean.valueOf(true));
/*  471 */         request.setAttribute("messagevalue", FormatUtil.getString("am.myfield.newfieldadded.success.text"));
/*  472 */       } else if ((displaymessage != null) && (displaymessage.equals("newenum")) && (messageValue != null)) {
/*  473 */         request.setAttribute("newenummessage", Boolean.valueOf(true));
/*  474 */         request.setAttribute("enummessagevalue", FormatUtil.getString("am.myfield.valueadded.message.text", new String[] { "\"" + messageValue + "\"" }));
/*  475 */       } else if ((displaymessage != null) && (displaymessage.equals("deletefield"))) {
/*  476 */         request.setAttribute("showmessage", Boolean.valueOf(true));
/*  477 */         request.setAttribute("messagevalue", FormatUtil.getString("am.myfield.deletefield.success.text"));
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
/*  490 */       return new ActionForward("/jsp/MyField_Edit.jsp");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  481 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  485 */         if (rs1 != null) { AMConnectionPool.closeStatement(rs1);
/*      */         }
/*      */       }
/*      */       catch (Exception e2) {}
/*      */     }
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addUsersEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  606 */     String resourceid = request.getParameter("resourceid");
/*  607 */     String[] user = request.getParameter("usercheckbox").split(",");
/*  608 */     Statement st = null;
/*      */     try
/*      */     {
/*  611 */       ArrayList<String> users = new ArrayList();
/*      */       
/*  613 */       for (int i = 0; i < user.length; i++) {
/*  614 */         if (user[i].trim().length() > 0) {
/*  615 */           users.add(user[i]);
/*      */         }
/*      */       }
/*  618 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_ENTITYDATA where RESOURCEID=" + resourceid + " and DATATABLE='AM_UserPasswordTable'");
/*  619 */       int relationid = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA");
/*  620 */       st = AMConnectionPool.getConnection().createStatement();
/*  621 */       for (int i = 0; i < users.size(); i++) {
/*  622 */         int id = relationid + i;
/*  623 */         String query = "insert into AM_MYFIELDS_ENTITYDATA (RELATIONID,RESOURCEID,DATATABLE,VALUEID) values(" + id + "," + resourceid + ",'AM_UserPasswordTable'," + (String)users.get(i) + ")";
/*  624 */         st.addBatch(query);
/*      */       }
/*  626 */       st.executeBatch();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  635 */         st.clearBatch();
/*  636 */         st.close();
/*      */       } catch (Exception ex) {
/*  638 */         ex.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*  642 */       request.setAttribute("userAdded", Boolean.valueOf(true));
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  632 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  635 */         st.clearBatch();
/*  636 */         st.close();
/*      */       } catch (Exception ex) {
/*  638 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  643 */     return new ActionForward("/jsp/showUsers.jsp?savedstaus=true&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */   public ActionForward addLabelField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  648 */     resourceid = request.getParameter("resourceid");
/*  649 */     String[] label = request.getParameterValues("labelcheckbox");
/*  650 */     Statement st = null;
/*  651 */     ResultSet rs = null;
/*  652 */     ArrayList<String> valueIdList = new ArrayList();
/*      */     try
/*      */     {
/*  655 */       rs = AMConnectionPool.executeQueryStmt("select VALUEID from AM_MYFIELDS_LABELDATA where RESOURCEID=" + resourceid);
/*  656 */       while (rs.next()) {
/*  657 */         valueIdList.add(rs.getString("VALUEID"));
/*      */       }
/*      */       
/*      */ 
/*  661 */       int relationid = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_LABELDATA");
/*  662 */       st = AMConnectionPool.getConnection().createStatement();
/*  663 */       if (label != null) {
/*  664 */         for (int i = 0; i < label.length; i++) {
/*  665 */           if (label[i].trim().length() > 0)
/*      */           {
/*  667 */             if (valueIdList.contains(label[i])) {
/*  668 */               valueIdList.remove(label[i]);
/*      */             } else {
/*  670 */               int id = relationid + i;
/*  671 */               String query = "insert into AM_MYFIELDS_LABELDATA (RELATIONID,RESOURCEID,VALUEID) values(" + id + "," + resourceid + "," + label[i] + ")";
/*  672 */               st.addBatch(query);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  678 */       for (int j = 0; j < valueIdList.size(); j++)
/*      */       {
/*  680 */         st.addBatch("delete from AM_MYFIELDS_LABELDATA where RESOURCEID=" + resourceid + " and VALUEID=" + (String)valueIdList.get(j));
/*  681 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_MYFIELDS_LABELDATA where RESOURCEID=" + resourceid + " and VALUEID=" + (String)valueIdList.get(j));
/*      */       }
/*      */       
/*  684 */       st.executeBatch();
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
/*  702 */       return new ActionForward("/jsp/MyField_Dialog.jsp?savedstaus=true&resourceid=" + resourceid);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  689 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  692 */         st.clearBatch();
/*  693 */         st.close();
/*  694 */         if (rs != null) {
/*  695 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/*  698 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
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
/*      */   public ActionForward checkDuplicateField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  751 */     JSONObject json = new JSONObject();
/*  752 */     String displayname = request.getParameter("fieldname");
/*  753 */     String columnType = request.getParameter("fieldType");
/*      */     
/*  755 */     boolean duplicateField = false;
/*  756 */     boolean duplicateValue = false;
/*  757 */     ResultSet rs = null;
/*  758 */     PrintWriter out = response.getWriter();
/*      */     try {
/*  760 */       rs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_MYFIELDS_METADATA where FIELDTYPE in (0,1,2,3)");
/*  761 */       while (rs.next()) {
/*  762 */         String fieldName = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/*  763 */         if (fieldName.equalsIgnoreCase(displayname)) {
/*  764 */           duplicateField = true;
/*  765 */           break;
/*      */         }
/*      */       }
/*  768 */       json.put("fieldname", duplicateField);
/*      */       
/*  770 */       if ((columnType != null) && (columnType.equals("combobox")) && (!duplicateField))
/*      */       {
/*  772 */         String[] columnValue = request.getParameter("listvalues").split(",");
/*  773 */         ArrayList<String> dropDownValues = new ArrayList();
/*  774 */         for (int i = 0; i < columnValue.length; i++) {
/*  775 */           String value = columnValue[i].trim();
/*  776 */           if (dropDownValues.contains(value)) {
/*  777 */             duplicateValue = true;
/*  778 */             break;
/*      */           }
/*  780 */           dropDownValues.add(value);
/*      */         }
/*      */       }
/*      */       
/*  784 */       json.put("fieldValues", duplicateValue);
/*  785 */       out.println(json);
/*  786 */       out.flush();
/*      */     } catch (Exception ex) {
/*  788 */       ex.printStackTrace();
/*      */     } finally {
/*  790 */       if (rs != null) {
/*  791 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*  795 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward addNewField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  805 */       String formname = request.getParameter("formname");
/*  806 */       String displayname = request.getParameter(formname + "_displayname");
/*  807 */       String columnType = request.getParameter(formname + "_columntype");
/*  808 */       String description = request.getParameter(formname + "_description");
/*      */       
/*  810 */       ArrayList<String> listValues = new ArrayList();
/*  811 */       int fieldType = MyFields.textbox;
/*  812 */       if (columnType.equals("combobox")) {
/*  813 */         columnType = "INTEGER";
/*  814 */         fieldType = MyFields.listbox;
/*  815 */         String[] columnValue = request.getParameter(formname + "_comboValuebottom").split(",");
/*  816 */         String enumval = null;
/*  817 */         for (int i = 0; i < columnValue.length; i++) {
/*  818 */           enumval = columnValue[i].trim();
/*  819 */           if ((enumval != null) && (enumval.length() > 0)) {
/*  820 */             listValues.add(enumval);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  825 */       if ("text".equals(columnType)) {
/*  826 */         fieldType = MyFields.textarea;
/*      */       }
/*      */       
/*  829 */       SingleMyField singlefield = new SingleMyField();
/*  830 */       singlefield.setDisplayName(displayname);
/*  831 */       singlefield.setFieldType(fieldType);
/*  832 */       singlefield.setDataType(columnType);
/*  833 */       singlefield.setDescription(description);
/*  834 */       singlefield.setEnumerationList(listValues);
/*  835 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  836 */       singlefield.setFieldid(String.valueOf(dao.getAutoGenFieldId()));
/*      */       
/*  838 */       MyFields fields = new MyFields();
/*  839 */       fields.addField(singlefield);
/*      */       
/*  841 */       MyFields.addNewField(fields);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  845 */       ex.printStackTrace();
/*      */     }
/*  847 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  856 */     String fieldid = request.getParameter("fieldid");
/*  857 */     deleteField(fieldid);
/*  858 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/*  861 */         MyFields.handleMASSYNCHDETAILS(Integer.parseInt(fieldid), "DELETE");
/*      */       }
/*      */       catch (Exception e) {
/*  864 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  868 */     return null;
/*      */   }
/*      */   
/*  871 */   public static boolean deleteField(String fieldid) { String aliasname = "";
/*  872 */     ResultSet rs = null;
/*  873 */     boolean status = true;
/*      */     try {
/*  875 */       rs = AMConnectionPool.executeQueryStmt("select ALIASNAME from AM_MYFIELDS_METADATA where FIELDID=" + fieldid);
/*  876 */       if (rs.next()) {
/*  877 */         aliasname = rs.getString("ALIASNAME");
/*      */       }
/*  879 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_METADATA  where FIELDID=" + fieldid);
/*  880 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_TEMPLATEDATA where FIELDID=" + fieldid);
/*  881 */       AMConnectionPool.executeUpdateStmt("ALTER TABLE AM_MYFIELDS_USERDATA drop column " + aliasname);
/*      */     }
/*      */     catch (Exception ex) {
/*  884 */       ex.printStackTrace();
/*  885 */       status = false;
/*      */     }
/*      */     finally {
/*  888 */       if (rs != null) AMConnectionPool.closeStatement(rs);
/*      */     }
/*  890 */     return status;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  939 */     String resourceid = request.getParameter("resourceid");
/*  940 */     String entityValueId = request.getParameter("entityValueId");
/*  941 */     String dataTable = request.getParameter("dataTable");
/*  942 */     MyFields myFieldGroup = new MyFields();
/*  943 */     myFieldGroup.setResourceid(resourceid);
/*  944 */     myFieldGroup.setDataTable(dataTable);
/*  945 */     myFieldGroup.setEntity(true);
/*  946 */     myFieldGroup.setEntityValueId(entityValueId);
/*  947 */     myFieldGroup.getEntityRelatedDetails();
/*  948 */     myFieldGroup.deleteEntity();
/*  949 */     request.setAttribute("editEntity", Boolean.valueOf(true));
/*  950 */     return new ActionForward("/myFields.do?method=getMyFields&resourceid=" + resourceid + "&entity=noalarms");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward duplicateEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  958 */     ResultSet rs = null;
/*  959 */     JSONObject json = new JSONObject();
/*  960 */     PrintWriter out = response.getWriter();
/*  961 */     boolean duplicateEnum = false;
/*      */     try {
/*  963 */       String[] value = URLDecoder.decode(request.getParameter("value")).split(",");
/*      */       
/*      */ 
/*  966 */       String fieldid = request.getParameter("fieldid");
/*  967 */       rs = AMConnectionPool.executeQueryStmt("select AM_MYFIELDS_TEMPLATEDATA.VALUE,AM_MYFIELDS_TEMPLATEDATA.VALUEID from AM_MYFIELDS_TEMPLATEDATA,AM_MYFIELDS_METADATA where  AM_MYFIELDS_METADATA .FIELDID=AM_MYFIELDS_TEMPLATEDATA.FIELDID and AM_MYFIELDS_TEMPLATEDATA.FIELDID=" + fieldid);
/*  968 */       while (rs.next()) {
/*  969 */         int valueid = rs.getInt("VALUEID");
/*  970 */         String templateValue = null;
/*  971 */         if (valueid <= 8) {
/*  972 */           templateValue = FormatUtil.getString(rs.getString("VALUE"));
/*      */         } else {
/*  974 */           templateValue = rs.getString("VALUE");
/*      */         }
/*  976 */         for (int i = 0; i < value.length; i++)
/*      */         {
/*  978 */           String enumval = value[i].trim();
/*  979 */           if (templateValue.equalsIgnoreCase(enumval)) {
/*  980 */             duplicateEnum = true;
/*  981 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  986 */       ex.printStackTrace();
/*      */     } finally {
/*  988 */       if (rs != null) {
/*  989 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  994 */     json.put("enumResponse", duplicateEnum);
/*  995 */     out.print(json);
/*  996 */     out.flush();
/*  997 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addEntryToEnumeration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1008 */     String fieldid = request.getParameter("fieldid");
/* 1009 */     String[] value = URLDecoder.decode(request.getParameter("value")).split(",");
/* 1010 */     SingleMyField singlefield = new SingleMyField();
/* 1011 */     singlefield.setFieldid(fieldid);
/* 1012 */     ArrayList<String> enumvalues = new ArrayList();
/* 1013 */     String enumval = null;
/* 1014 */     for (int i = 0; i < value.length; i++) {
/* 1015 */       enumval = value[i].trim();
/* 1016 */       if (enumval.length() > 0) {
/* 1017 */         enumvalues.add(enumval);
/*      */       }
/*      */     }
/* 1020 */     singlefield.setEnumerationList(enumvalues);
/* 1021 */     MyFields.addToEnumeration(singlefield, false, "EDIT");
/* 1022 */     return null;
/*      */   }
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
/*      */   public ActionForward updateEntryToEnumeration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1060 */     String valueid = request.getParameter("valueid");
/* 1061 */     String value = URLDecoder.decode(request.getParameter("value"));
/* 1062 */     String fieldid = request.getParameter("fieldid");
/* 1063 */     String message = updateEntryToEnumeration(fieldid, valueid, value);
/* 1064 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try {
/* 1066 */         MyFields.handleMASSYNCHDETAILS(Integer.parseInt(fieldid), "EDIT");
/*      */       }
/*      */       catch (Exception e) {
/* 1069 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1072 */     return new ActionForward("/myFields.do?method=getEnumeration&fieldid=" + fieldid + "&message=" + message);
/*      */   }
/*      */   
/*      */   public static String updateEntryToEnumeration(String fieldid, String valueid, String value)
/*      */   {
/* 1077 */     String message = FormatUtil.getString("am.myfield.updatesuccess.text");
/*      */     try {
/* 1079 */       value = MyFields.checkForSpecialChar(value);
/* 1080 */       String updateQry = "update AM_MYFIELDS_TEMPLATEDATA set VALUE='" + value + "' where VALUEID=" + valueid;
/*      */       
/* 1082 */       AMConnectionPool.executeUpdateStmt(updateQry);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1086 */       ex.printStackTrace();
/* 1087 */       message = FormatUtil.getString("am.myfield.updatefailure.text");
/*      */     }
/*      */     
/* 1090 */     return message;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteEnumeration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1100 */     String valueid = request.getParameter("valueid");
/* 1101 */     String fieldid = request.getParameter("fieldid");
/* 1102 */     response.setContentType("text/html;charset=UTF-8");
/* 1103 */     PrintWriter out = response.getWriter();
/* 1104 */     deleteEnumeration(valueid);
/* 1105 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/* 1108 */         MyFields.handleMASSYNCHDETAILS(Integer.parseInt(fieldid), "DELETEENUM");
/*      */       }
/*      */       catch (Exception e) {
/* 1111 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1116 */     out.println("<td class='error-text'>" + FormatUtil.getString("am.myfield.enumdeletesuccess.text") + "</td>");
/* 1117 */     out.flush();
/* 1118 */     return null;
/*      */   }
/*      */   
/* 1121 */   public static boolean deleteEnumeration(String valueid) { boolean isDeleteSucces = true;
/*      */     try {
/* 1123 */       String delQry = "delete from AM_MYFIELDS_TEMPLATEDATA where VALUEID=" + valueid;
/* 1124 */       AMConnectionPool.executeUpdateStmt(delQry);
/* 1125 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_LABELDATA WHERE VALUEID=" + valueid);
/*      */     } catch (Exception ex) {
/* 1127 */       ex.printStackTrace();
/* 1128 */       isDeleteSucces = false;
/*      */     }
/* 1130 */     return isDeleteSucces;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward getEnumeration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1138 */     String fieldid = null;
/* 1139 */     String valueid = null;
/* 1140 */     ResultSet rs = null;
/* 1141 */     String message = null;
/* 1142 */     String value = null;
/*      */     try
/*      */     {
/* 1145 */       fieldid = request.getParameter("fieldid");
/* 1146 */       valueid = request.getParameter("valueid");
/* 1147 */       message = request.getParameter("message");
/*      */       
/* 1149 */       if ((valueid != null) && (!valueid.trim().equals("")))
/*      */       {
/* 1151 */         String valQry = "select VALUE from AM_MYFIELDS_TEMPLATEDATA where VALUEID=" + valueid;
/* 1152 */         rs = AMConnectionPool.executeQueryStmt(valQry);
/* 1153 */         if (rs.next())
/*      */         {
/* 1155 */           value = rs.getString("VALUE");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1161 */       ex.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1166 */       if (rs != null) {
/* 1167 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1171 */     request.setAttribute("updatemessage", message);
/* 1172 */     request.setAttribute("updateEnum", Boolean.valueOf(true));
/*      */     
/* 1174 */     return new ActionForward("/jsp/MyField_AddToEnum.jsp?fieldid=" + fieldid + "&value=" + value + "&valueid=" + valueid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getFieldValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1184 */     response.setContentType("text/plain;charset=UTF-8");
/* 1185 */     String columnName = request.getParameter("aliasName");
/* 1186 */     String aliasName = columnName;
/* 1187 */     int fieldId = -1;
/* 1188 */     if (columnName.contains("$")) {
/*      */       try
/*      */       {
/* 1191 */         aliasName = columnName.substring(0, columnName.indexOf("$"));
/* 1192 */         fieldId = Integer.parseInt(columnName.substring(columnName.indexOf("$") + 1));
/*      */       } catch (Exception ex) {
/* 1194 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1197 */     String query = null;
/* 1198 */     PrintWriter out = response.getWriter();
/*      */     
/* 1200 */     ResultSet rs = null;
/* 1201 */     ResultSet rs1 = null;
/* 1202 */     ResultSet rs2 = null;
/* 1203 */     int fieldType = -1;
/*      */     
/* 1205 */     String dropDownName = "selectFieldVal";
/* 1206 */     String forBulkAssign = request.getParameter("forBulkAssign");
/* 1207 */     String monitorType = request.getParameter("monitortype");
/* 1208 */     if ((monitorType != null) && (monitorType.contains("&#39;"))) {
/* 1209 */       monitorType = monitorType.replace("&#39;", "'");
/*      */     }
/* 1211 */     String optionSel = request.getParameter("optionSel");
/* 1212 */     String reportspage = request.getParameter("reportspage");
/* 1213 */     String filterCondition = "";
/* 1214 */     String selectBoxName = "selectFieldVal";
/* 1215 */     String onChange = "loadUrl(this.options[this.selectedIndex].value)";
/* 1216 */     if ((reportspage != null) && (reportspage.equals("true"))) {
/* 1217 */       selectBoxName = "selectFieldVal_" + request.getParameter("selectboxname");
/* 1218 */       dropDownName = selectBoxName;
/* 1219 */       onChange = "";
/*      */     }
/*      */     
/* 1222 */     if ((monitorType != null) && (!monitorType.equals("All"))) {
/* 1223 */       filterCondition = "and AM_ManagedResourceType.SUBGROUP in ('" + monitorType + "')";
/*      */     }
/* 1225 */     boolean isAttributeReport = false;
/* 1226 */     String attributeResource = null;
/* 1227 */     if ("schedulereport".equals(reportspage)) {
/* 1228 */       filterCondition = "and AM_ManagedResourceType.RESOURCETYPE in ('" + monitorType + "')";
/* 1229 */       String reportname = request.getParameter("reportname");
/* 1230 */       String attribute = request.getParameter("attribute");
/* 1231 */       String iscustom = request.getParameter("iscustom");
/* 1232 */       if ("attribute".equalsIgnoreCase(reportname))
/*      */       {
/* 1234 */         if ("true".equals(iscustom)) {
/* 1235 */           String resgroup = request.getParameter("resgroup");
/* 1236 */           ScheduleReportAction sch = new ScheduleReportAction();
/* 1237 */           Properties prop = sch.getResourceGroup(resgroup);
/* 1238 */           attributeResource = prop.getProperty("ResourceGroup");
/*      */         } else {
/* 1240 */           attributeResource = MyFields.getAttributeResource(attribute);
/*      */         }
/* 1242 */         if (!"All".equalsIgnoreCase(attributeResource)) {
/* 1243 */           isAttributeReport = true;
/* 1244 */           filterCondition = "and AM_ManagedResourceType.RESOURCEGROUP in ('" + attributeResource + "')";
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1250 */     if ((forBulkAssign != null) && (forBulkAssign.equals("true")))
/*      */     {
/* 1252 */       onChange = "";
/* 1253 */       String resid = request.getParameter("resourceid");
/* 1254 */       dropDownName = "fieldVal" + resid;
/* 1255 */       if (!resid.equals("bulkassign")) {
/* 1256 */         HashMap<String, String> allQuery = MyFields.customCondition(aliasName, "", resid, false);
/* 1257 */         query = (String)allQuery.get("customvalue");
/*      */         
/* 1259 */         rs2 = AMConnectionPool.executeQueryStmt(query);
/* 1260 */         if (rs2.next()) {
/* 1261 */           optionSel = rs2.getString(1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1266 */     String mgQuery = null;
/* 1267 */     String subqueryCondition = null;
/* 1268 */     StringBuilder temp = new StringBuilder();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1273 */       temp.append("<select name='" + dropDownName + "' id='" + selectBoxName + "' data-placeholder='" + FormatUtil.getString("am.myfield.choosevalue.text") + "' class='chzn-select' style='width:190px' onchange='" + onChange + "'>");
/*      */       
/* 1275 */       if ((!"true".equals(reportspage)) && (!"schedulereport".equals(reportspage))) {
/* 1276 */         temp.append("<option value='-' > </option>");
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
/* 1293 */       HashMap<String, String> fieldDetails = MyFields.customCondition(aliasName, null, null, false);
/* 1294 */       String dataTable = (String)fieldDetails.get("groupTable");
/* 1295 */       if ((aliasName.indexOf("SYSTEMDATA") != -1) || (aliasName.indexOf("USERDATA") != -1) || (aliasName.indexOf("VALUEID") != -1))
/*      */       {
/* 1297 */         query = "select distinct AM_MYFIELDS_TEMPLATEDATA.VALUEID,AM_MYFIELDS_TEMPLATEDATA.VALUE from AM_MYFIELDS_TEMPLATEDATA,AM_MYFIELDS_METADATA,AM_ManagedObject,AM_ManagedResourceType," + dataTable + " where AM_MYFIELDS_TEMPLATEDATA.FIELDID=AM_MYFIELDS_METADATA.FIELDID and " + dataTable + "." + aliasName + "=AM_MYFIELDS_TEMPLATEDATA.VALUEID and AM_MYFIELDS_METADATA.ALIASNAME='" + aliasName + "' and AM_ManagedObject.RESOURCEID=" + dataTable + ".RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/* 1298 */         mgQuery = " select distinct AM_MYFIELDS_TEMPLATEDATA.VALUEID,AM_MYFIELDS_TEMPLATEDATA.VALUE," + dataTable + ".RESOURCEID from AM_MYFIELDS_TEMPLATEDATA,AM_MYFIELDS_METADATA,AM_ManagedObject," + dataTable + " where AM_MYFIELDS_TEMPLATEDATA.FIELDID=AM_MYFIELDS_METADATA.FIELDID and " + dataTable + "." + aliasName + "=AM_MYFIELDS_TEMPLATEDATA.VALUEID and AM_MYFIELDS_METADATA.ALIASNAME='" + aliasName + "' and AM_ManagedObject.RESOURCEID=" + dataTable + ".RESOURCEID and AM_ManagedObject.TYPE='HAI'";
/* 1299 */         subqueryCondition = "select " + dataTable + ".RESOURCEID from AM_ManagedObject," + dataTable + " where AM_ManagedObject.RESOURCEID=" + dataTable + ".RESOURCEID and AM_ManagedObject.TYPE not in ('HAI')";
/* 1300 */         if (isAttributeReport) {
/* 1301 */           query = "select distinct AM_MYFIELDS_TEMPLATEDATA.VALUEID,AM_MYFIELDS_TEMPLATEDATA.VALUE from AM_MYFIELDS_TEMPLATEDATA,AM_MYFIELDS_METADATA,AM_ManagedObject,AM_ManagedResourceType," + dataTable + ",AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER where AM_MYFIELDS_TEMPLATEDATA.FIELDID=AM_MYFIELDS_METADATA.FIELDID and " + dataTable + "." + aliasName + "=AM_MYFIELDS_TEMPLATEDATA.VALUEID and AM_MYFIELDS_METADATA.ALIASNAME='" + aliasName + "' and AM_HOLISTICAPPLICATION.HAID=" + dataTable + ".RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/*      */         }
/* 1303 */         if ("true".equalsIgnoreCase(forBulkAssign)) {
/* 1304 */           query = "select valueid,value from AM_MYFIELDS_TEMPLATEDATA,AM_MYFIELDS_METADATA where AM_MYFIELDS_TEMPLATEDATA.FIELDID=AM_MYFIELDS_METADATA.FIELDID and AM_MYFIELDS_METADATA.ALIASNAME='" + aliasName + "'";
/*      */         }
/* 1306 */       } else if (aliasName.equals("LOCATION_NAME"))
/*      */       {
/* 1308 */         query = "select LOCATIONID,LOCATION_NAME from AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject,AM_ManagedResourceType where AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/* 1309 */         mgQuery = "select LOCATIONID,LOCATION_NAME,AM_MYFIELDS_ENTITYDATA.RESOURCEID from AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject where AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI'";
/* 1310 */         subqueryCondition = "select AM_MYFIELDS_ENTITYDATA.RESOURCEID from AM_ManagedObject,AM_MYFIELDS_ENTITYDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_ManagedObject.TYPE not in ('HAI') and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION'";
/* 1311 */         if (isAttributeReport) {
/* 1312 */           query = "select LOCATIONID,LOCATION_NAME from AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject,AM_ManagedResourceType,AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER where AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/*      */         }
/* 1314 */         if ("true".equalsIgnoreCase(forBulkAssign)) {
/* 1315 */           query = "select LOCATIONID,LOCATION_NAME from AM_MYFIELDS_LOCATION";
/*      */         }
/*      */       } else {
/* 1318 */         String notAllowedUserNames = "'reportadmin', 'systemadmin_enterprise', '@dminuser', 'loginuser'";
/* 1319 */         query = "select distinct USERID,USERNAME from AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject,AM_ManagedResourceType where USERNAME NOT IN (" + notAllowedUserNames + ") and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/* 1320 */         mgQuery = "select distinct USERID,USERNAME,AM_MYFIELDS_ENTITYDATA.RESOURCEID from AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject where USERNAME NOT IN (" + notAllowedUserNames + ") and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI' ";
/* 1321 */         subqueryCondition = "select AM_MYFIELDS_ENTITYDATA.RESOURCEID from AM_ManagedObject,AM_MYFIELDS_ENTITYDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_ManagedObject.TYPE not in ('HAI') and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable'";
/* 1322 */         if (isAttributeReport) {
/* 1323 */           query = "select distinct USERID,USERNAME from AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,AM_ManagedObject,AM_ManagedResourceType,AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER where USERNAME NOT IN (" + notAllowedUserNames + ") and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE " + filterCondition;
/*      */         }
/* 1325 */         if ("true".equalsIgnoreCase(forBulkAssign)) {
/* 1326 */           query = "select USERID,USERNAME from AM_UserPasswordTable where USERNAME NOT IN (" + notAllowedUserNames + ")";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1331 */       String selected = "";
/* 1332 */       HashMap<String, String> optionsList = new HashMap();
/* 1333 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 1335 */       while (rs.next()) {
/* 1336 */         optionsList.put(rs.getString(1), rs.getString(2));
/*      */       }
/* 1338 */       ArrayList<HashMap<String, String>> monitorGroupIds = new ArrayList();
/* 1339 */       if ((monitorType != null) && (monitorType != "All") && (monitorType != "HAI") && (!"true".equalsIgnoreCase(forBulkAssign))) {
/* 1340 */         rs = AMConnectionPool.executeQueryStmt(mgQuery);
/* 1341 */         while (rs.next()) {
/* 1342 */           HashMap<String, String> map = new HashMap();
/* 1343 */           map.put("resourceid", rs.getString("RESOURCEID"));
/* 1344 */           map.put("valueid", rs.getString(1));
/* 1345 */           map.put("value", rs.getString(2));
/* 1346 */           monitorGroupIds.add(map);
/*      */         }
/*      */       }
/*      */       
/* 1350 */       for (HashMap<String, String> mgids : monitorGroupIds) {
/* 1351 */         String valueid = (String)mgids.get("valueid");
/* 1352 */         String resourceid = (String)mgids.get("resourceid");
/* 1353 */         String value = (String)mgids.get("value");
/* 1354 */         if (!optionsList.containsKey(valueid)) {
/* 1355 */           ResultSet mgChildSet = null;
/*      */           try {
/* 1357 */             Vector<String> mgchildIds = new Vector();
/* 1358 */             ParentChildRelationalUtil.getAllChildMapper(mgchildIds, resourceid);
/* 1359 */             mgChildSet = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in ('" + monitorType + "') and " + DBUtil.getCondition("AM_ManagedObject.RESOURCEID", mgchildIds) + " and AM_ManagedObject.RESOURCEID not in (" + subqueryCondition + ")");
/* 1360 */             if (mgChildSet.next()) {
/* 1361 */               optionsList.put(valueid, value);
/*      */             }
/*      */           }
/*      */           catch (Exception ex) {
/* 1365 */             ex.printStackTrace();
/*      */           } finally {
/* 1367 */             if (mgChildSet != null) {
/* 1368 */               AMConnectionPool.closeStatement(mgChildSet);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1377 */       Iterator<String> it = optionsList.keySet().iterator();
/* 1378 */       while (it.hasNext()) {
/* 1379 */         String key = (String)it.next();
/* 1380 */         if (((optionSel != null) && (optionSel.equals(key))) || ((request.getParameter("customFieldValue") != null) && (request.getParameter("customFieldValue").equals(aliasName + "$" + key))))
/*      */         {
/* 1382 */           selected = "selected";
/*      */         }
/*      */         else
/*      */         {
/* 1386 */           selected = "";
/*      */         }
/*      */         
/* 1389 */         temp.append("<option " + selected + " class='chzn-select' value='" + aliasName + "$" + key + "'>" + FormatUtil.getString((String)optionsList.get(key)) + "</option>");
/*      */       }
/* 1391 */       temp.append("</select>");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1416 */         if (rs != null) AMConnectionPool.closeStatement(rs);
/* 1417 */         if (rs1 != null) AMConnectionPool.closeStatement(rs1);
/* 1418 */         if (rs2 != null) AMConnectionPool.closeStatement(rs2);
/*      */       } catch (Exception ex1) {
/* 1420 */         ex1.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 1424 */       value = temp.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1412 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 1416 */         if (rs != null) AMConnectionPool.closeStatement(rs);
/* 1417 */         if (rs1 != null) AMConnectionPool.closeStatement(rs1);
/* 1418 */         if (rs2 != null) AMConnectionPool.closeStatement(rs2);
/*      */       } catch (Exception ex1) {
/* 1420 */         ex1.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */     String value;
/* 1425 */     out.println(value);
/* 1426 */     out.flush();
/* 1427 */     request.setAttribute("dropDownBox", value);
/*      */     
/* 1429 */     if ((request.getAttribute("fromedit") != null) && (request.getAttribute("fromedit").equals("true"))) {
/* 1430 */       request.setAttribute("related_action", "customfields");
/*      */       
/* 1432 */       return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */     }
/* 1434 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward bulkAssignSeparateMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1447 */     Statement updateSt = AMConnectionPool.getConnection().createStatement();
/* 1448 */     Statement deleteSt = AMConnectionPool.getConnection().createStatement();
/* 1449 */     ResultSet rs = null;
/* 1450 */     ActionMessages messages = new ActionMessages();
/* 1451 */     ActionErrors errors = new ActionErrors();
/* 1452 */     String customFieldValue = null;
/* 1453 */     String customColumn = null;
/* 1454 */     String columnValue = null;
/* 1455 */     String dataTable = null;
/* 1456 */     String entityTable = null;
/* 1457 */     String resid = "";
/*      */     try
/*      */     {
/* 1460 */       resid = request.getParameter("resids");
/* 1461 */       String resourceid = null;
/* 1462 */       StringTokenizer st = new StringTokenizer(resid, ",");
/*      */       
/* 1464 */       String query = null;
/* 1465 */       String delQry = null;
/* 1466 */       String selQry = null;
/*      */       
/* 1468 */       int labelId = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_LABELDATA") - 1;
/* 1469 */       int entityId = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA") - 1;
/*      */       
/*      */ 
/* 1472 */       while (st.hasMoreTokens()) {
/* 1473 */         delQry = null;
/* 1474 */         resourceid = st.nextToken();
/* 1475 */         customFieldValue = request.getParameter("fieldVal" + resourceid);
/* 1476 */         if ((customFieldValue != null) && (customFieldValue.contains("$"))) {
/* 1477 */           customColumn = customFieldValue.substring(0, customFieldValue.indexOf("$"));
/* 1478 */           columnValue = customFieldValue.substring(customFieldValue.indexOf("$") + 1);
/*      */           
/*      */ 
/*      */ 
/* 1482 */           HashMap<String, String> customFieldCondition = MyFields.customCondition(customColumn, columnValue, null, false);
/*      */           
/* 1484 */           dataTable = (String)customFieldCondition.get("groupTable");
/* 1485 */           entityTable = (String)customFieldCondition.get("fieldsDataTable");
/*      */           
/* 1487 */           if ((dataTable.equals("AM_MYFIELDS_SYSTEMDATA")) || (dataTable.equals("AM_MYFIELDS_USERDATA")))
/*      */           {
/* 1489 */             selQry = "select RESOURCEID from " + dataTable + " where RESOURCEID=" + resourceid;
/* 1490 */             rs = AMConnectionPool.executeQueryStmt(selQry);
/*      */             
/* 1492 */             if (rs.next())
/*      */             {
/* 1494 */               query = "update " + dataTable + " set " + customColumn + "='" + columnValue + "' where RESOURCEID=" + resourceid;
/*      */             }
/*      */             else
/*      */             {
/* 1498 */               query = "insert into " + dataTable + "(RESOURCEID," + customColumn + ") values(" + resourceid + ",'" + columnValue + "')";
/*      */             }
/* 1500 */             EnterpriseUtil.addUpdateQueryToFile(query);
/*      */           }
/* 1502 */           else if (dataTable.equals("AM_MYFIELDS_LABELDATA"))
/*      */           {
/* 1504 */             labelId += 1;
/* 1505 */             delQry = "delete from " + dataTable + " where RESOURCEID=" + resourceid;
/* 1506 */             EnterpriseUtil.addUpdateQueryToFile(delQry);
/* 1507 */             query = "insert into " + dataTable + " values(" + labelId + "," + resourceid + "," + columnValue + ")";
/*      */           }
/*      */           else
/*      */           {
/* 1511 */             entityId += 1;
/* 1512 */             delQry = "delete from " + dataTable + " where RESOURCEID=" + resourceid + " and DATATABLE='" + entityTable + "'";
/* 1513 */             EnterpriseUtil.addUpdateQueryToFile(delQry);
/* 1514 */             query = "insert into " + dataTable + " values(" + entityId + "," + resourceid + ",'" + entityTable + "'" + "," + columnValue + ")";
/*      */           }
/*      */           
/*      */ 
/* 1518 */           if (delQry != null) {
/* 1519 */             deleteSt.addBatch(delQry);
/*      */           }
/* 1521 */           updateSt.addBatch(query);
/*      */         } }
/* 1523 */       deleteSt.executeBatch();
/* 1524 */       updateSt.executeBatch();
/*      */       
/* 1526 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.myfield.bulkassign.allmonitors.success.text")));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1534 */         deleteSt.clearBatch();
/* 1535 */         updateSt.clearBatch();
/* 1536 */         deleteSt.close();
/* 1537 */         updateSt.close();
/* 1538 */         if (rs != null) {
/* 1539 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1542 */         ex.printStackTrace();
/*      */       }
/*      */       
/* 1545 */       saveMessages(request, messages);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1529 */       ex.printStackTrace();
/* 1530 */       errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.myfield.bulkassign.allmonitors.failure.text")));
/*      */     }
/*      */     finally {
/*      */       try {
/* 1534 */         deleteSt.clearBatch();
/* 1535 */         updateSt.clearBatch();
/* 1536 */         deleteSt.close();
/* 1537 */         updateSt.close();
/* 1538 */         if (rs != null) {
/* 1539 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1542 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1546 */     saveErrors(request, errors);
/* 1547 */     request.setAttribute("reloads", "2");
/*      */     
/*      */ 
/*      */ 
/* 1551 */     return new ActionForward("/myFields.do?method=assignCustomFields&resids" + resid + "&reloads=inmonitors");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward bulkAssign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1562 */     Statement stmt = AMConnectionPool.getConnection().createStatement();
/* 1563 */     ResultSet rs = null;
/* 1564 */     ActionMessages messages = new ActionMessages();
/* 1565 */     ActionErrors errors = new ActionErrors();
/* 1566 */     String resid = request.getParameter("resids");
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1572 */       StringTokenizer st = new StringTokenizer(resid, ",");
/* 1573 */       if (resid.length() > 0) {
/* 1574 */         resid = resid.substring(0, resid.length() - 1);
/*      */       }
/* 1576 */       String customFieldValue = request.getParameter("fieldValbulkassign");
/* 1577 */       String customCol = null;
/* 1578 */       String columnValue = null;
/*      */       
/* 1580 */       if (customFieldValue.contains("$")) {
/* 1581 */         customCol = customFieldValue.substring(0, customFieldValue.indexOf("$"));
/* 1582 */         columnValue = customFieldValue.substring(customFieldValue.indexOf("$") + 1);
/*      */       }
/* 1584 */       HashMap<String, String> customCondition = MyFields.customCondition(customCol, columnValue, null, false);
/* 1585 */       String dataTable = (String)customCondition.get("groupTable");
/* 1586 */       String entityDataTable = (String)customCondition.get("fieldsDataTable");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1591 */       int labelId = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_LABELDATA") - 1;
/* 1592 */       int entityId = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA") - 1;
/* 1593 */       String selQry = "";
/*      */       
/*      */ 
/*      */ 
/* 1597 */       String resourceid = null;
/* 1598 */       String query = null;
/*      */       
/*      */ 
/* 1601 */       while (st.hasMoreTokens())
/*      */       {
/*      */ 
/* 1604 */         if ((dataTable.equals("AM_MYFIELDS_SYSTEMDATA")) || (dataTable.equals("AM_MYFIELDS_USERDATA")))
/*      */         {
/* 1606 */           while (st.hasMoreTokens())
/*      */           {
/* 1608 */             resourceid = st.nextToken();
/* 1609 */             selQry = "select RESOURCEID from " + dataTable + " where RESOURCEID=" + resourceid;
/* 1610 */             rs = AMConnectionPool.executeQueryStmt(selQry);
/* 1611 */             if (rs.next()) {
/* 1612 */               query = "update " + dataTable + " set " + customCol + "='" + columnValue + "' where RESOURCEID=" + resourceid;
/*      */             } else {
/* 1614 */               query = "insert into " + dataTable + "(RESOURCEID," + customCol + ") values(" + resourceid + ",'" + columnValue + "')";
/*      */             }
/* 1616 */             stmt.addBatch(query);
/* 1617 */             EnterpriseUtil.addUpdateQueryToFile(query);
/*      */           }
/*      */           
/*      */         }
/* 1621 */         else if (dataTable.equals("AM_MYFIELDS_LABELDATA"))
/*      */         {
/* 1623 */           AMConnectionPool.executeUpdateStmt("delete from " + dataTable + " where RESOURCEID in (" + resid + ")");
/* 1624 */           EnterpriseUtil.addUpdateQueryToFile("delete from " + dataTable + " where RESOURCEID in (" + resid + ")");
/* 1625 */           while (st.hasMoreTokens()) {
/* 1626 */             labelId += 1;
/* 1627 */             resourceid = st.nextToken();
/* 1628 */             query = "insert into " + dataTable + " values(" + labelId + "," + resourceid + "," + columnValue + ")";
/* 1629 */             stmt.addBatch(query);
/*      */           }
/*      */         }
/*      */         else {
/* 1633 */           AMConnectionPool.executeUpdateStmt("delete from " + dataTable + " where RESOURCEID in (" + resid + ") and DATATABLE='" + entityDataTable + "'");
/* 1634 */           if (!entityDataTable.equals("AM_UserPasswordTable")) {
/* 1635 */             EnterpriseUtil.addUpdateQueryToFile("delete from " + dataTable + " where RESOURCEID in (" + resid + ") and DATATABLE='" + entityDataTable + "'");
/*      */           }
/* 1637 */           while (st.hasMoreTokens()) {
/* 1638 */             resourceid = st.nextToken();
/* 1639 */             entityId += 1;
/* 1640 */             query = "insert into " + dataTable + " values(" + entityId + "," + resourceid + ",'" + entityDataTable + "'" + "," + columnValue + ")";
/* 1641 */             stmt.addBatch(query);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1646 */       stmt.executeBatch();
/* 1647 */       stmt.clearBatch();
/*      */       
/* 1649 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.myfield.bulkassign.allmonitors.success.text"));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1657 */         stmt.close();
/* 1658 */         if (rs != null) {
/* 1659 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1662 */         ex.printStackTrace();
/*      */       }
/*      */       
/* 1665 */       saveMessages(request, messages);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1652 */       ex.printStackTrace();
/* 1653 */       errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.myfield.bulkassign.allmonitors.failure.text"));
/*      */     }
/*      */     finally {
/*      */       try {
/* 1657 */         stmt.close();
/* 1658 */         if (rs != null) {
/* 1659 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1662 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1666 */     saveErrors(request, errors);
/* 1667 */     request.setAttribute("reloads", "1");
/*      */     
/*      */ 
/*      */ 
/* 1671 */     return new ActionForward("/myFields.do?method=assignCustomFields&resids" + resid);
/*      */   }
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
/*      */   public ActionForward getLabelValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1686 */     JSONObject json = new JSONObject();
/* 1687 */     String resourceid = request.getParameter("resourceid");
/* 1688 */     Vector<String> parentids = new Vector();
/* 1689 */     parentids.add(resourceid);
/* 1690 */     ParentChildRelationalUtil.getParentMGs(parentids, resourceid);
/* 1691 */     ResultSet res = null;
/* 1692 */     boolean hasLabel = false;
/* 1693 */     ArrayList<HashMap<String, String>> labelValues = new ArrayList();
/*      */     try {
/* 1695 */       ArrayList<String> labelids = new ArrayList();
/* 1696 */       String query = "select AM_MYFIELDS_TEMPLATEDATA.VALUEID, AM_MYFIELDS_TEMPLATEDATA.VALUE,AM_MYFIELDS_LABELDATA.RESOURCEID from AM_MYFIELDS_LABELDATA, AM_MYFIELDS_TEMPLATEDATA where AM_MYFIELDS_TEMPLATEDATA.VALUEID=AM_MYFIELDS_LABELDATA.VALUEID and " + DependantMOUtil.getCondition("AM_MYFIELDS_LABELDATA.RESOURCEID", parentids) + " order by case RESOURCEID when " + resourceid + " then 1 end desc";
/* 1697 */       res = AMConnectionPool.executeQueryStmt(query);
/* 1698 */       while (res.next()) {
/* 1699 */         HashMap<String, String> label = new HashMap();
/* 1700 */         String valueid = res.getString("VALUEID");
/* 1701 */         if (!labelids.contains(valueid))
/*      */         {
/*      */ 
/* 1704 */           String value = res.getString("VALUE");
/* 1705 */           String reid = res.getString("RESOURCEID");
/* 1706 */           label.put("id", valueid);
/* 1707 */           if (!reid.equals(resourceid)) {
/* 1708 */             value = value + " *";
/*      */           }
/* 1710 */           label.put("displayname", value);
/* 1711 */           labelids.add(valueid);
/* 1712 */           labelValues.add(label);
/*      */         }
/*      */       }
/* 1715 */       ArrayList<HashMap<String, String>> optionslist = new ArrayList();
/*      */       
/* 1717 */       query = "select AM_MYFIELDS_TEMPLATEDATA.VALUEID, AM_MYFIELDS_TEMPLATEDATA.VALUE from AM_MYFIELDS_TEMPLATEDATA where AM_MYFIELDS_TEMPLATEDATA.VALUEID NOT IN (select valueid from AM_MYFIELDS_LABELDATA where " + DependantMOUtil.getCondition("RESOURCEID", parentids) + ") and AM_MYFIELDS_TEMPLATEDATA.FIELDID=1";
/* 1718 */       res = AMConnectionPool.executeQueryStmt(query);
/* 1719 */       while (res.next()) {
/* 1720 */         HashMap<String, String> label = new HashMap();
/* 1721 */         String valueid = res.getString("VALUEID");
/* 1722 */         String value = res.getString("VALUE");
/* 1723 */         label.put("id", valueid);
/* 1724 */         label.put("displayname", value);
/* 1725 */         optionslist.add(label);
/*      */       }
/*      */       
/* 1728 */       String add = "<img src=\"/images/icon_custom_add_label.gif\" border=\"0\" >";
/* 1729 */       String remove = "<img src=\"/images/deleteWidget.gif\" border=\"0\" >";
/* 1730 */       boolean toShowImage = true;
/* 1731 */       String removeValues = constructTable(labelValues, remove, "remove", toShowImage);
/* 1732 */       if (resourceid != null) {
/* 1733 */         long resId = Long.valueOf(resourceid).longValue();
/* 1734 */         if ((resId > 10000000L) && (EnterpriseUtil.isAdminServer())) {
/* 1735 */           toShowImage = false;
/*      */         }
/*      */       }
/* 1738 */       String addValues = constructTable(optionslist, add, "add", toShowImage);
/* 1739 */       json.put("addValues", addValues);
/* 1740 */       json.put("removeValues", removeValues);
/* 1741 */       if ((optionslist.size() > 0) || (labelValues.size() > 0)) {
/* 1742 */         hasLabel = true;
/*      */       }
/* 1744 */       response.setContentType("text/html;charset=UTF-8");
/* 1745 */       PrintWriter out = response.getWriter();
/* 1746 */       json.put("hasLabel", hasLabel);
/* 1747 */       out.println(json.toString());
/* 1748 */       out.flush();
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
/* 1761 */       return null;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1751 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1754 */         if (res != null) {
/* 1755 */           AMConnectionPool.closeStatement(res);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1758 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String constructTable(ArrayList<HashMap<String, String>> value, String image, String addOrRemove) {
/* 1764 */     return constructTable(value, image, addOrRemove, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public String constructTable(ArrayList<HashMap<String, String>> value, String image, String addOrRemove, boolean toShowImage)
/*      */   {
/* 1770 */     String clickfun = "deletelabel";
/*      */     
/* 1772 */     if (addOrRemove.equals("add")) {
/* 1773 */       clickfun = "addlabel";
/*      */     }
/* 1775 */     StringBuilder str = new StringBuilder();
/* 1776 */     str.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> ");
/*      */     
/* 1778 */     if (value.size() > 0) {
/* 1779 */       for (int i = 0; i < value.size(); i++) {
/* 1780 */         HashMap<String, String> map = (HashMap)value.get(i);
/* 1781 */         if ((toShowImage) && (!((String)map.get("displayname")).contains("*"))) {
/* 1782 */           str.append("<tr class='bodytext' height='25'><td valign='top' align='left' class='bodytext'>" + (String)map.get("displayname") + "</td><td align='right' class='bodytext'><a href='javascript:void(0)'  onclick='" + clickfun + "(" + (String)map.get("id") + ")'><span class='staticlinks'>" + image + "</span></a></td></tr>");
/*      */         }
/*      */         else {
/* 1785 */           str.append("<tr class='bodytext' height='25'><td valign='top' align='left' class='bodytext'>" + (String)map.get("displayname") + "</td></tr>");
/*      */         }
/*      */       }
/*      */     } else {
/* 1789 */       str.append("<tr class='bodytext' height='25'><td>" + FormatUtil.getString("am.myfield.dialog.nolabel.text") + "</td></tr>");
/*      */     }
/* 1791 */     str.append("</table>");
/* 1792 */     return str.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward associateLabels(ActionMapping mapping, ActionForm from, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1798 */     String resourceid = request.getParameter("resourceid");
/* 1799 */     ResultSet rs = null;
/* 1800 */     ArrayList<HashMap<String, String>> labelList = new ArrayList();
/* 1801 */     ArrayList<String> addedLabels = new ArrayList();
/*      */     try
/*      */     {
/* 1804 */       rs = AMConnectionPool.executeQueryStmt("select VALUEID ,VALUE from AM_MYFIELDS_TEMPLATEDATA where FIELDID=1");
/* 1805 */       while (rs.next()) {
/* 1806 */         HashMap<String, String> map = new HashMap();
/* 1807 */         map.put("id", rs.getString("VALUEID"));
/* 1808 */         map.put("displayValue", rs.getString("VALUE"));
/* 1809 */         labelList.add(map);
/*      */       }
/* 1811 */       request.setAttribute("labelList", labelList);
/*      */       
/* 1813 */       rs = AMConnectionPool.executeQueryStmt("select VALUEID from  AM_MYFIELDS_LABELDATA where RESOURCEID=" + resourceid);
/* 1814 */       while (rs.next()) {
/* 1815 */         addedLabels.add(rs.getString("VALUEID"));
/*      */       }
/* 1817 */       request.setAttribute("addedLabels", addedLabels);
/*      */     } catch (Exception ex) {
/* 1819 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 1822 */       if (rs != null) {
/* 1823 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1827 */     return new ActionForward("/jsp/MyField_Dialog.jsp");
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addLabel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1892 */     String resourceid = request.getParameter("resourceid");
/* 1893 */     String valueid = request.getParameter("valueid");
/*      */     try
/*      */     {
/* 1896 */       int id = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_LABELDATA");
/* 1897 */       AMConnectionPool.executeUpdateStmt("insert into AM_MYFIELDS_LABELDATA (RELATIONID,VALUEID,RESOURCEID)values(" + id + "," + valueid + "," + resourceid + ")");
/*      */     } catch (Exception exe) {
/* 1899 */       exe.printStackTrace();
/*      */     }
/* 1901 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward deleteLabel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1908 */     String resourceid = request.getParameter("resourceid");
/* 1909 */     String valueid = request.getParameter("valueid");
/*      */     try
/*      */     {
/* 1912 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_LABELDATA where VALUEID=" + valueid + " and resourceid=" + resourceid + "");
/* 1913 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_MYFIELDS_LABELDATA where VALUEID=" + valueid + " and resourceid=" + resourceid + "");
/*      */     } catch (Exception exe) {
/* 1915 */       exe.printStackTrace();
/*      */     }
/* 1917 */     return null;
/*      */   }
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
/*      */   public ActionForward deleteUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1939 */     String resourceid = request.getParameter("resourceid");
/* 1940 */     String userid = request.getParameter("userid");
/*      */     try {
/* 1942 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYFIELDS_ENTITYDATA where RESOURCEID=" + resourceid + " and DATATABLE='AM_UserPasswordTable' and VALUEID=" + userid + "");
/*      */     } catch (Exception exe) {
/* 1944 */       exe.printStackTrace();
/*      */     }
/* 1946 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward displaylabel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1955 */     String resourceid = request.getParameter("resourceid");
/* 1956 */     Vector<String> parentids = new Vector();
/* 1957 */     parentids.add(resourceid);
/* 1958 */     ParentChildRelationalUtil.getParentMGs(parentids, resourceid);
/* 1959 */     ResultSet rs = null;
/* 1960 */     StringBuilder label = new StringBuilder();
/* 1961 */     String query = "select VALUE from AM_MYFIELDS_TEMPLATEDATA, AM_MYFIELDS_LABELDATA where AM_MYFIELDS_TEMPLATEDATA.VALUEID=AM_MYFIELDS_LABELDATA.VALUEID and " + DependantMOUtil.getCondition("AM_MYFIELDS_LABELDATA.RESOURCEID", parentids);
/*      */     
/*      */     try
/*      */     {
/* 1965 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1966 */       while (rs.next()) {
/* 1967 */         label.append(" " + rs.getString("VALUE") + ",");
/*      */       }
/* 1969 */       String val = label.toString();
/*      */       
/* 1971 */       if (val.length() > 1) {
/* 1972 */         val = val.substring(1, val.length() - 1);
/*      */       }
/* 1974 */       response.setContentType("text/html;charset=UTF-8");
/* 1975 */       PrintWriter out = response.getWriter();
/* 1976 */       out.println(val);
/* 1977 */       out.flush();
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
/* 1991 */       return null;
/*      */     }
/*      */     catch (Exception exe)
/*      */     {
/* 1981 */       exe.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1984 */         if (rs != null) {
/* 1985 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1988 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward getScheduledReportFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1997 */     String resourcetype = request.getParameter("resourcetype");
/* 1998 */     resourcetype = Translate.decode(resourcetype);
/* 1999 */     AMLog.debug("MyFieldAction.getScheduledReportFilter() resourcetype : " + resourcetype);
/* 2000 */     String isedit = request.getParameter("edit");
/* 2001 */     String isCustom = request.getParameter("isCustom");
/* 2002 */     String attribute = request.getParameter("attribute");
/* 2003 */     String resgroup = request.getParameter("resourcegroup");
/* 2004 */     String reportname = request.getParameter("reportname");
/* 2005 */     String parameters = "";
/* 2006 */     boolean isAttributeReport = false;
/*      */     
/* 2008 */     if ("attribute".equalsIgnoreCase(reportname)) {
/* 2009 */       isAttributeReport = true;
/* 2010 */       if ("true".equals(isCustom)) {
/* 2011 */         ScheduleReportAction sch = new ScheduleReportAction();
/* 2012 */         Properties prop = sch.getResourceGroup(resgroup);
/* 2013 */         resgroup = prop.getProperty("ResourceGroup");
/*      */       } else {
/* 2015 */         resgroup = MyFields.getAttributeResource(attribute);
/*      */       }
/*      */     }
/*      */     
/* 2019 */     if ((isedit != null) && (isedit != "undefined")) {
/* 2020 */       parameters = "&edit=true&aliasname=" + request.getParameter("aliasname") + "&valueid=" + request.getParameter("valueid");
/*      */     }
/* 2022 */     parameters = request.getParameter("customFieldId") != null ? parameters + "&customFieldId=" + request.getParameter("customFieldId") : parameters;
/* 2023 */     parameters = request.getParameter("customFieldValue") != null ? parameters + "&customFieldValue=" + request.getParameter("customFieldValue") : parameters;
/*      */     
/* 2025 */     ArrayList<Properties> filterValue = MyFields.getDollarTags(false, resourcetype, null, true, isAttributeReport, resgroup);
/* 2026 */     request.setAttribute("dollartags", filterValue);
/* 2027 */     return new ActionForward("/jsp/ScheduleReportsResources.jsp?customfieldfilter=true" + parameters);
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward assignCustomFields(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2107 */     String resids = request.getParameter("resids");
/*      */     
/* 2109 */     StringBuilder assignResourceIds = new StringBuilder();
/* 2110 */     StringTokenizer resoureTokens = new StringTokenizer(resids, ",");
/* 2111 */     while (resoureTokens.hasMoreTokens())
/*      */     {
/* 2113 */       assignResourceIds.append(resoureTokens.nextToken() + ",");
/*      */     }
/* 2115 */     String unopengroups = request.getParameter("unopengroups");
/* 2116 */     if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/* 2117 */       Vector<String> vresid = new Vector();
/* 2118 */       StringTokenizer st = new StringTokenizer(unopengroups, "|");
/* 2119 */       String str = "";
/* 2120 */       while (st.hasMoreElements()) {
/* 2121 */         str = st.nextToken();
/* 2122 */         if ("-1".equals(str)) {
/* 2123 */           ParentChildRelationalUtil.getUncategorizedMonitors(vresid);
/*      */         }
/*      */         else {
/* 2126 */           ParentChildRelationalUtil.getAllChildMapper(vresid, str, true);
/*      */         }
/*      */       }
/* 2129 */       StringBuffer sb = new StringBuffer();
/* 2130 */       if (vresid.size() > 0) {
/* 2131 */         Iterator<String> it = vresid.iterator();
/* 2132 */         while (it.hasNext()) {
/* 2133 */           sb.append((String)it.next() + ",");
/*      */         }
/* 2135 */         assignResourceIds.append(sb);
/*      */       }
/*      */     }
/*      */     
/* 2139 */     assignResourceIds.deleteCharAt(assignResourceIds.length() - 1);
/*      */     
/* 2141 */     String query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where RESOURCEID in (" + assignResourceIds.toString() + ")";
/* 2142 */     ArrayList<String> resourceids = new ArrayList();
/* 2143 */     Hashtable<String, String> name_ids = new Hashtable();
/* 2144 */     ArrayList<String> monitor_types = new ArrayList();
/* 2145 */     ResultSet results = null;
/*      */     try {
/* 2147 */       results = AMConnectionPool.executeQueryStmt(query);
/* 2148 */       while (results.next()) {
/* 2149 */         String dispname = results.getString("DISPLAYNAME");
/* 2150 */         dispname = EnterpriseUtil.decodeString(dispname);
/* 2151 */         String id = results.getString("RESOURCEID");
/* 2152 */         resourceids.add(id);
/* 2153 */         name_ids.put(id, dispname);
/*      */       }
/* 2155 */       request.setAttribute("resourceids", resourceids);
/* 2156 */       request.setAttribute("mondisplayNames", name_ids);
/*      */     } catch (Exception exception) {
/* 2158 */       exception.printStackTrace();
/*      */     } finally {
/* 2160 */       AMConnectionPool.closeStatement(results);
/*      */     }
/*      */     try
/*      */     {
/* 2164 */       query = "select distinct AM_ManagedResourceType.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.resourceid in (" + assignResourceIds.toString() + ")";
/* 2165 */       results = AMConnectionPool.executeQueryStmt(query);
/* 2166 */       while (results.next()) {
/* 2167 */         monitor_types.add(EnterpriseUtil.decodeString(results.getString("DISPLAYNAME")));
/*      */       }
/* 2169 */       request.setAttribute("monitorTypes", monitor_types);
/*      */     } catch (Exception ex) {
/* 2171 */       ex.printStackTrace();
/*      */     } finally {
/* 2173 */       AMConnectionPool.closeStatement(results);
/*      */     }
/* 2175 */     String loadingValue = "1";
/*      */     
/* 2177 */     if (request.getAttribute("reloads") != null) {
/* 2178 */       loadingValue = (String)request.getAttribute("reloads");
/*      */     }
/*      */     
/* 2181 */     ((DynaActionForm)form).set("customgroupType", loadingValue);
/* 2182 */     request.setAttribute("loadvalue", loadingValue);
/*      */     
/* 2184 */     return new ActionForward("/jsp/AssignCustomFields.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward enableDisableField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2192 */     String enable = request.getParameter("enablevalue");
/* 2193 */     String fieldid = request.getParameter("fieldid");
/*      */     try {
/* 2195 */       AMConnectionPool.executeUpdateStmt("update AM_MYFIELDS_METADATA set ENABLED=" + enable + " where FIELDID=" + fieldid);
/*      */     } catch (Exception ex) {
/* 2197 */       ex.printStackTrace();
/*      */     }
/* 2199 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/* 2202 */         MyFields.handleMASSYNCHDETAILS(Integer.parseInt(fieldid), "EDIT");
/*      */       }
/*      */       catch (Exception e) {
/* 2205 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 2208 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editSaveFieldsMetaData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2217 */     String columnName = request.getParameter("columnname");
/* 2218 */     String fieldid = request.getParameter("fieldid");
/* 2219 */     String value = URLDecoder.decode(request.getParameter("value"));
/* 2220 */     editSaveFieldsMetaData(columnName, fieldid, value);
/*      */     
/* 2222 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*      */       try
/*      */       {
/* 2226 */         MyFields.handleMASSYNCHDETAILS(Integer.parseInt(fieldid), "EDIT");
/*      */       }
/*      */       catch (Exception e) {
/* 2229 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 2232 */     return null;
/*      */   }
/*      */   
/*      */   public static void editSaveFieldsMetaData(String columnName, String fieldid, String value) {
/* 2236 */     try { value = MyFields.checkForSpecialChar(value);
/* 2237 */       AMConnectionPool.executeUpdateStmt("update AM_MYFIELDS_METADATA set " + columnName + "='" + value + "' where FIELDID=" + fieldid);
/*      */     } catch (Exception ex) {
/* 2239 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MyFieldAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */