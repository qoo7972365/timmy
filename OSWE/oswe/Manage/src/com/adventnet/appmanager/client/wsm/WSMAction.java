/*      */ package com.adventnet.appmanager.client.wsm;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMBatchStmtExecutor;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.wsm.datacollection.AddWSMMonitor;
/*      */ import com.adventnet.appmanager.server.wsm.datacollection.DeleteWSMMonitor;
/*      */ import com.adventnet.appmanager.server.wsm.datacollection.WSMDataCollector;
/*      */ import com.adventnet.appmanager.server.wsm.datacollection.WSMProcess;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSMAction
/*      */   extends DispatchAction
/*      */ {
/*   56 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   80 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*   82 */       ActionErrors errors = new ActionErrors();
/*   83 */       ActionMessages messages = new ActionMessages();
/*   84 */       String haid = request.getParameter("haid");
/*   85 */       String resourcetype = ((AMActionForm)form).getType();
/*   86 */       Properties argsprops = new Properties();
/*   87 */       argsprops.setProperty("monitorType", resourcetype);
/*   88 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*   90 */         String param = (String)e.nextElement();
/*   91 */         if (!argsprops.containsKey(param))
/*      */         {
/*   93 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*   95 */         if (param.equals("haid"))
/*      */         {
/*   97 */           String[] multiVal = request.getParameterValues(param);
/*   98 */           if ((multiVal != null) && (multiVal.length > 0)) {
/*   99 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/*  100 */             val = val.substring(1, val.length() - 1);
/*  101 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  107 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/*  108 */         ArrayList<String> al1 = new ArrayList();
/*  109 */         String displayname = request.getParameter("displayname");
/*  110 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/*  111 */           displayname = request.getParameter("displayName");
/*      */         }
/*  113 */         String status = "Success";
/*  114 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/*  115 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/*  116 */         al1.add(displayname);
/*  117 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/*  118 */           status = "Failed";
/*  119 */           message = (String)responseMap.get("message");
/*      */         }
/*  121 */         al1.add(status);
/*  122 */         al1.add(message);
/*  123 */         al1.add(masDisplayName);
/*  124 */         request.setAttribute("discoverystatus", al1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  143 */         e.printStackTrace();
/*      */       }
/*  145 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid);
/*      */     }
/*      */     
/*  148 */     int resid = 0;
/*  149 */     if (!DataCollectionControllerUtil.isallowed()) {
/*  150 */       return new ActionForward("/showresource.do?method=showResourceTypes&fromwhere=unabletocreate");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  155 */       String addtoapplication = request.getParameter("addtoha");
/*  156 */       String haidname = request.getParameter("haid");
/*  157 */       ArrayList addStatus = new ArrayList();
/*  158 */       String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  159 */       request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*  160 */       AMActionForm actionForm = (AMActionForm)form;
/*  161 */       String wsdlurl = actionForm.getWSDLUrl();
/*  162 */       String type = actionForm.getType();
/*  163 */       int pollinterval = actionForm.getPollInterval();
/*  164 */       String haid = actionForm.getHaid();
/*  165 */       int timeout = actionForm.getTimeout();
/*  166 */       String username = actionForm.getUsername().trim();
/*  167 */       String password = actionForm.getPassword().trim();
/*  168 */       String displayname = actionForm.getDisplayname().trim();
/*  169 */       boolean isRestFul = actionForm.isResFulWebservice();
/*  170 */       String customHeaders = actionForm.getCustomHeaders();
/*  171 */       String tokenAndOperation = actionForm.getTokenAndOperation();
/*  172 */       String endPointUrl = actionForm.getEndPointUrl();
/*  173 */       String[] selMonitorGroups = request.getParameterValues("haid");
/*      */       
/*  175 */       AMLog.debug("isRestFul  is : " + isRestFul);
/*      */       
/*  177 */       Properties confInfo = new Properties();
/*  178 */       confInfo.setProperty("wsdlurl", wsdlurl);
/*  179 */       confInfo.setProperty("pollinterval", String.valueOf(pollinterval));
/*  180 */       confInfo.setProperty("timeout", timeout + "");
/*  181 */       confInfo.setProperty("username", username);
/*  182 */       confInfo.setProperty("password", password);
/*  183 */       confInfo.setProperty("displayname", displayname);
/*  184 */       confInfo.setProperty("isRestFul", "" + isRestFul);
/*  185 */       confInfo.setProperty("customHeaders", customHeaders);
/*  186 */       confInfo.setProperty("tokenAndOperation", tokenAndOperation);
/*  187 */       confInfo.setProperty("endPointUrl", endPointUrl);
/*  188 */       AMLog.debug("WSMAction.addMonitor : confInfo : " + confInfo);
/*  189 */       AddWSMMonitor addwsmObj = new AddWSMMonitor(confInfo);
/*      */       
/*      */ 
/*  192 */       Properties pr = addwsmObj.checkURL();
/*  193 */       int value = Integer.parseInt(pr.getProperty("errorcode"));
/*  194 */       if (value == 0)
/*      */       {
/*      */ 
/*      */ 
/*  198 */         Properties props = addwsmObj.addMO();
/*  199 */         if (props.getProperty("resourceid") != null)
/*      */         {
/*  201 */           resid = Integer.parseInt(props.getProperty("resourceid"));
/*      */         }
/*      */         
/*  204 */         if ((addtoapplication != null) && (addtoapplication.equals("true")))
/*      */         {
/*  206 */           String[] resources = { props.getProperty("resourceid") };
/*  207 */           this.mo.updateManagedApplicationResources(selMonitorGroups, displayname, resources, null, null);
/*      */         }
/*      */         
/*  210 */         WSMDataCollector dc = new WSMDataCollector(resid);
/*  211 */         dc.start();
/*      */         
/*  213 */         WSMProcess.datacollectorMap.put(Integer.valueOf(resid), dc);
/*  214 */         addStatus.add(wsdlurl);
/*  215 */         addStatus.add("Success");
/*  216 */         addStatus.add("/wsm.do?resourceid=" + resid + "&frompage=addmonitor&method=showdetails");
/*  217 */         request.setAttribute("discoverystatus", addStatus);
/*  218 */         request.setAttribute("resourceid", Integer.valueOf(resid));
/*  219 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + actionForm.getType() + "&haid=" + haid);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  224 */       addStatus.add(wsdlurl);
/*  225 */       addStatus.add("Failed");
/*  226 */       addStatus.add(pr.getProperty("error"));
/*  227 */       request.setAttribute("discoverystatus", addStatus);
/*  228 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Web Service&restype=Web Service");
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  233 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  238 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void populateResProps(String resid, HttpServletRequest request)
/*      */   {
/*  248 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  251 */       String query = "select resourcename,displayname from AM_ManagedObject where resourceid=" + resid;
/*  252 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  253 */       if (rs.next())
/*      */       {
/*  255 */         request.setAttribute("name", rs.getString("resourcename"));
/*  256 */         request.setAttribute("displayname", rs.getString("displayname"));
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  261 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  265 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try {
/*  272 */       ActionMessages messages = new ActionMessages();
/*  273 */       String frompage = request.getParameter("frompage");
/*  274 */       if (frompage != null)
/*      */       {
/*  276 */         if (frompage.equals("add"))
/*      */         {
/*  278 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.addoperation.text"));
/*      */         }
/*  280 */         else if (frompage.equals("addmonitor"))
/*      */         {
/*  282 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.addmonitor.text"));
/*      */         }
/*  284 */         saveMessages(request, messages);
/*      */       }
/*  286 */       String resid = request.getParameter("resourceid");
/*      */       
/*      */ 
/*      */ 
/*  290 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resid);
/*  291 */       if (rs.next())
/*      */       {
/*  293 */         String errmessage = rs.getString("ERRORMSG");
/*  294 */         if (!errmessage.equals("WSDL URL invoked successfully"))
/*      */         {
/*  296 */           if (errmessage.equals("Proxy updated")) {
/*  297 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wsm.proxymessage.text", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("company.troubleshoot.link") })));
/*      */           }
/*      */           else {
/*  300 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errmessage));
/*      */           }
/*  302 */           saveMessages(request, messages);
/*      */         }
/*      */       }
/*  305 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  307 */       populateResProps(resid, request);
/*      */       
/*  309 */       String query = "select WSDLPATH,HOST,PORT,ENDPOINT,SERVICENAME,POLLINTERVAL,TIMEOUT,USERNAME," + DBQueryUtil.decode(Constants.ENCODEKEY, "password") + " as PASSWORD,HEADERS,TOKENS from AM_WSM_Config where resourceid=" + resid;
/*  310 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  311 */       HashMap configProps = new HashMap();
/*  312 */       while (rs.next())
/*      */       {
/*  314 */         configProps.put("wsdlpath", rs.getString("WSDLPATH"));
/*  315 */         configProps.put("host", rs.getString("HOST"));
/*  316 */         configProps.put("port", rs.getString("PORT"));
/*  317 */         configProps.put("endpoint", rs.getString("ENDPOINT"));
/*  318 */         configProps.put("serviceName", rs.getString("SERVICENAME"));
/*  319 */         configProps.put("pollinterval", rs.getString("POLLINTERVAL"));
/*  320 */         configProps.put("timeout", rs.getString("TIMEOUT"));
/*  321 */         configProps.put("username", rs.getString("USERNAME"));
/*  322 */         configProps.put("password", rs.getString("PASSWORD"));
/*  323 */         configProps.put("customHeaders", rs.getString("HEADERS"));
/*  324 */         configProps.put("tokenAndOperation", rs.getString("TOKENS"));
/*      */       }
/*  326 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  328 */       query = "select max(collectiontime) as coltime from AM_ManagedObjectData mod1 where mod1.resid=" + resid;
/*  329 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  330 */       if (rs.next()) {
/*  331 */         String coltimestr = rs.getString("coltime");
/*  332 */         if ((coltimestr != null) && (!coltimestr.equalsIgnoreCase("null")))
/*      */         {
/*  334 */           long lastpoll = Long.parseLong(coltimestr);
/*  335 */           long nextpoll = lastpoll + Integer.parseInt((String)configProps.get("pollinterval")) * 60 * 1000;
/*  336 */           configProps.put("lastpoll", lastpoll + "");
/*  337 */           configProps.put("nextpoll", nextpoll + "");
/*      */         }
/*      */         else {
/*  340 */           configProps.put("lastpoll", "0");
/*  341 */           configProps.put("nextpoll", "0");
/*      */         }
/*      */       }
/*      */       else {
/*  345 */         configProps.put("lastpoll", "0");
/*  346 */         configProps.put("nextpoll", "0");
/*      */       }
/*  348 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  350 */       query = "select executiontime from AM_WSM_WebServiceData where resourceid=" + resid + " order by collectiontime desc";
/*  351 */       query = DBQueryUtil.getTopNValues(query, 1);
/*  352 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  353 */       if (rs.next()) {
/*  354 */         configProps.put("urlexecutiontime", rs.getString("executiontime"));
/*      */       }
/*  356 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  361 */       ArrayList<Properties> operations = null;
/*  362 */       String maxCollectionTime = (String)configProps.get("lastpoll");
/*  363 */       ArrayList resids = null;
/*      */       try
/*      */       {
/*  366 */         query = "select AM_WSM_Instance.ID, AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.DCSTARTED , AM_WSM_OperationData.EXECUTIONTIME from AM_WSM_Instance inner join AM_PARENTCHILDMAPPER on AM_WSM_Instance.ID=AM_PARENTCHILDMAPPER.CHILDID and  AM_PARENTCHILDMAPPER.PARENTID=" + resid + " left join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left join AM_WSM_OperationData on  AM_WSM_OperationData.INSTANCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_WSM_OperationData.COLLECTIONTIME=" + maxCollectionTime;
/*  367 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  368 */         operations = new ArrayList();
/*  369 */         resids = new ArrayList();
/*  370 */         resids.add(resid);
/*      */         
/*  372 */         while (rs.next())
/*      */         {
/*  374 */           Properties operationInfo = new Properties();
/*      */           
/*  376 */           operationInfo.setProperty("name", rs.getString("DISPLAYNAME"));
/*  377 */           operationInfo.setProperty("operationid", rs.getString("ID"));
/*  378 */           operationInfo.setProperty("status", rs.getString("DCSTARTED"));
/*  379 */           String execute = rs.getString("EXECUTIONTIME");
/*  380 */           if ((execute != null) && (execute != ""))
/*      */           {
/*  382 */             operationInfo.setProperty("executiontime", execute);
/*      */           }
/*      */           else
/*      */           {
/*  386 */             operationInfo.setProperty("executiontime", "-");
/*      */           }
/*  388 */           operations.add(operationInfo);
/*  389 */           resids.add(operationInfo.getProperty("operationid"));
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  394 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  398 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*  400 */       request.setAttribute("WSProps", configProps);
/*  401 */       request.setAttribute("resids", resids);
/*  402 */       request.setAttribute("WSOpData", operations);
/*  403 */       request.setAttribute("HelpKey", "Web Service Details");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  407 */       e.printStackTrace();
/*      */     }
/*  409 */     return mapping.findForward("details");
/*      */   }
/*      */   
/*      */ 
/*      */   private Properties getMyOperation(ArrayList ops, String oid, String insid)
/*      */   {
/*  415 */     int i = 0; for (int size = ops.size(); i < size; i++)
/*      */     {
/*  417 */       Properties p = (Properties)ops.get(i);
/*  418 */       String ooid = p.getProperty("oid");
/*  419 */       String iinsid = p.getProperty("insid");
/*  420 */       if ((ooid.equals(oid)) && (iinsid.equals(insid))) {
/*  421 */         return p;
/*      */       }
/*      */     }
/*  424 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showoperations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  429 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/*  433 */       ActionMessages messages = new ActionMessages();
/*  434 */       String resid = request.getParameter("resourceid");
/*  435 */       populateResProps(resid, request);
/*  436 */       int opcount = -1;
/*      */       
/*      */ 
/*  439 */       String query = "select AM_WSM_Operation.ID as OPERATIONID,AM_WSM_Operation.NAME as OPERATIONNAME from AM_WSM_Operation,AM_WSM_OperationWSDLMap where AM_WSM_OperationWSDLMap.RESOURCEID=" + resid + " and AM_WSM_OperationWSDLMap.OPERATIONID= AM_WSM_Operation.ID";
/*      */       
/*  441 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  442 */       ArrayList<Properties> ops = new ArrayList();
/*      */       
/*  444 */       while (rs.next())
/*      */       {
/*  446 */         Properties operationInfo = new Properties();
/*  447 */         operationInfo.setProperty("name", rs.getString("OPERATIONNAME"));
/*  448 */         operationInfo.setProperty("oid", rs.getString("OPERATIONID"));
/*  449 */         ops.add(operationInfo);
/*      */       }
/*  451 */       request.setAttribute("operations", ops);
/*      */       
/*  453 */       query = "select count(*) as count from AM_WSM_Instance , AM_PARENTCHILDMAPPER where AM_WSM_Instance.ID = AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resid;
/*  454 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  455 */       if (rs.next()) {
/*  456 */         opcount = rs.getInt("count");
/*      */       }
/*  458 */       request.setAttribute("count", Integer.valueOf(opcount));
/*  459 */       String value = request.getParameter("value");
/*      */       
/*  461 */       if ((value != null) && (request.getParameter("value").equals("two")))
/*      */       {
/*  463 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.saveandconfigure.text"));
/*  464 */         saveMessages(request, messages);
/*      */       }
/*  466 */       else if ((value != null) && (value.equals("refreshoperation")))
/*      */       {
/*  468 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.refreshoperation.success.text"));
/*  469 */         saveMessages(request, messages);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  475 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  478 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  480 */     request.setAttribute("HelpKey", "Adding Web Service Operation");
/*  481 */     return mapping.findForward("operations");
/*      */   }
/*      */   
/*      */   public ActionForward refreshoperations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  486 */     ResultSet res = null;
/*  487 */     int resid = Integer.parseInt(request.getParameter("resourceid"));
/*      */     
/*      */     try
/*      */     {
/*  491 */       String wsdlpath = "";
/*  492 */       String username = "";
/*  493 */       String password = "";
/*  494 */       HashMap<String, HashMap> operationsMap = new HashMap();
/*  495 */       HashMap<String, HashMap> refreshOperations = new HashMap();
/*  496 */       String query = "select WSDLPATH,USERNAME,PASSWORD from AM_WSM_Config where RESOURCEID=" + resid;
/*  497 */       res = AMConnectionPool.executeQueryStmt(query);
/*  498 */       if (res.next())
/*      */       {
/*  500 */         wsdlpath = res.getString("WSDLPATH");
/*  501 */         username = res.getString("USERNAME");
/*  502 */         password = res.getString("PASSWORD");
/*      */       }
/*  504 */       query = "select awo.ID, awo.NAME, awo.SOAPREQUEST,awo.SOAPACTION from AM_WSM_Operation awo,AM_WSM_OperationWSDLMap opmap where awo.ID=opmap.OPERATIONID and opmap.RESOURCEID=" + resid;
/*  505 */       res = AMConnectionPool.executeQueryStmt(query);
/*  506 */       while (res.next())
/*      */       {
/*  508 */         HashMap opt = new HashMap();
/*  509 */         opt.put("id", res.getString("ID"));
/*  510 */         opt.put("soaprequest", res.getString("SOAPREQUEST"));
/*  511 */         String soapaction = res.getString("SOAPACTION");
/*  512 */         if (soapaction != null)
/*      */         {
/*  514 */           opt.put("soapaction", soapaction);
/*      */         }
/*      */         else {
/*  517 */           opt.put("soapaction", "");
/*      */         }
/*  519 */         operationsMap.put(res.getString("NAME"), opt);
/*      */       }
/*      */       
/*  522 */       AddWSMMonitor addwsm = new AddWSMMonitor(wsdlpath, username, password);
/*  523 */       Properties prop = addwsm.checkURL();
/*  524 */       int value = Integer.parseInt(prop.getProperty("errorcode"));
/*  525 */       if (value == 0)
/*      */       {
/*  527 */         refreshOperations = addwsm.refreshWSDL();
/*  528 */         addwsm.updateOperations(operationsMap, refreshOperations, resid);
/*      */       }
/*      */       else
/*      */       {
/*  532 */         request.setAttribute("error", prop.getProperty("error"));
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  538 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  541 */       AMConnectionPool.closeStatement(res);
/*      */     }
/*      */     
/*  544 */     return new ActionForward("/wsm.do?method=showoperations&value=refreshoperation&resourceid=" + resid, true);
/*      */   }
/*      */   
/*      */   private Properties getMyOperation(ArrayList ops, String oid)
/*      */   {
/*  549 */     int i = 0; for (int size = ops.size(); i < size; i++)
/*      */     {
/*  551 */       Properties p = (Properties)ops.get(i);
/*  552 */       String ooid = p.getProperty("oid");
/*  553 */       if (ooid.equals(oid)) {
/*  554 */         return p;
/*      */       }
/*      */     }
/*  557 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward saveoperation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  563 */     String resid = request.getParameter("resourceid");
/*  564 */     String operationID = request.getParameter("operation");
/*  565 */     String soapAction = request.getParameter("soapAction");
/*  566 */     String soapRequest = request.getParameter("soapRequest");
/*  567 */     String value = request.getParameter("value");
/*  568 */     String attrdisname = request.getParameter("operationName");
/*  569 */     String xsltInput = request.getParameter("XSLTInput").trim();
/*      */     
/*      */ 
/*  572 */     boolean isCustomMon = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  579 */       if (Integer.parseInt(operationID) == 0)
/*      */       {
/*  581 */         operationID = "" + DBQueryUtil.getIncrementedID("ID", "AM_WSM_Operation");
/*  582 */         isCustomMon = true;
/*      */         
/*      */         try
/*      */         {
/*  586 */           String qry = "insert into AM_WSM_Operation values(" + operationID + ",'" + attrdisname + "'" + ",'" + soapRequest + "'" + ",'" + soapAction + "')";
/*  587 */           AMConnectionPool.executeUpdateStmt(qry);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  591 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*  594 */       AMManagedObject mo = new AMManagedObject();
/*  595 */       mo.setRESOURCENAME(getDisplayName(operationID));
/*  596 */       mo.setDISPLAYNAME(attrdisname);
/*  597 */       mo.setType("Web_Service_Operation");
/*  598 */       mo.setDESCRIPTION("Web Service Operation");
/*  599 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  600 */       dao.create(mo);
/*  601 */       DBUtil.insertParentChildMapper(Integer.parseInt(resid), mo.getRESOURCEID());
/*  602 */       AMAttributesDependencyAdder add = new AMAttributesDependencyAdder();
/*  603 */       add.addDependentAttributes(Integer.parseInt(resid), mo.getRESOURCEID());
/*  604 */       add.addInterDependentAttributes(mo.getRESOURCEID());
/*      */       
/*  606 */       int insid = mo.getRESOURCEID();
/*  607 */       PreparedStatement saveop = AMConnectionPool.getConnection().prepareStatement("insert into AM_WSM_Instance (ID,OPERATIONID,SOAPACTION,SOAPREQUEST,XSLTINPUT,HASREPORT) values (?,?,?,?,?,?)");
/*  608 */       saveop.setLong(1, insid);
/*  609 */       saveop.setLong(2, Long.valueOf(operationID).longValue());
/*  610 */       saveop.setString(3, soapAction);
/*  611 */       saveop.setString(4, soapRequest);
/*  612 */       saveop.setString(5, xsltInput);
/*  613 */       saveop.setInt(6, 0);
/*  614 */       saveop.execute();
/*      */       
/*  616 */       if (isCustomMon)
/*      */       {
/*  618 */         AMLog.debug("customer monitor operation id is : " + operationID);
/*      */         try
/*      */         {
/*  621 */           String qry = "insert into AM_WSM_OperationWSDLMap values(" + operationID + "," + resid + ")";
/*  622 */           AMConnectionPool.executeUpdateStmt(qry);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  626 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*  629 */       String classInstaceResourceMappingQuery = "insert into AM_CAM_DC_GROUPS values('" + mo.getRESOURCEID() + "','Web_Service_Operation','" + resid + "','null')";
/*  630 */       AMConnectionPool.executeUpdateStmt(classInstaceResourceMappingQuery);
/*      */     }
/*      */     catch (Exception e) {
/*  633 */       e.printStackTrace();
/*      */     }
/*  635 */     if ((value.equals("two")) && (value != ""))
/*      */     {
/*  637 */       return new ActionForward("/wsm.do?method=showoperations&value=" + request.getParameter("value") + "&resourceid=" + resid, true);
/*      */     }
/*      */     
/*      */ 
/*  641 */     return new ActionForward("/wsm.do?method=showdetails&resourceid=" + resid + "&frompage=add", true);
/*      */   }
/*      */   
/*      */ 
/*      */   private int getAttributeID()
/*      */   {
/*  647 */     id = 20000;
/*  648 */     String query = "select max(attributeid) as maxid from AM_ATTRIBUTES where attributeid>=20000";
/*  649 */     ResultSet rs = null;
/*      */     try {
/*  651 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  652 */       if (rs.next())
/*      */       {
/*  654 */         id = rs.getInt("maxid") + 1;
/*      */       }
/*      */       else
/*      */       {
/*  658 */         id++;
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
/*  672 */       return id;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  662 */       e.printStackTrace();
/*  663 */       id++;
/*      */     }
/*      */     finally {
/*  666 */       if (rs != null) {
/*      */         try {
/*  668 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         catch (Exception e) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String getDisplayName(String oid)
/*      */   {
/*  677 */     ResultSet rs = null;
/*  678 */     String toret = "";
/*      */     try {
/*  680 */       String query = "select name from AM_WSM_Operation where id=" + oid;
/*  681 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  682 */       if (rs.next())
/*      */       {
/*  684 */         toret = rs.getString("name");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  688 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  691 */       if (rs != null)
/*      */       {
/*  693 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  696 */     return toret;
/*      */   }
/*      */   
/*      */   private void addBeanValue(int insid, String argid, String oid, String cid) throws Exception
/*      */   {
/*  701 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  704 */       String query = "select bp.id,bp.isarray,c.id as classid,c.name,c.type from AM_WSM_BeanProperty bp,AM_WSM_Class c where classid=" + cid + " and bp.type=c.id order by id";
/*  705 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  706 */       while (rs.next())
/*      */       {
/*  708 */         String bpid = rs.getString("id");
/*  709 */         String bpcid = rs.getString("classid");
/*  710 */         String classname = rs.getString("name");
/*  711 */         int type = rs.getInt("type");
/*  712 */         int isarray = rs.getInt("isarray");
/*      */         
/*  714 */         if (type == 1) {
/*  715 */           int arrayindex = -1;
/*  716 */           if (isarray == 1) {
/*  717 */             arrayindex = 1;
/*      */           }
/*      */           
/*  720 */           query = "insert into AM_WSM_BeanPropertyValue (INSTANCEID,ARGUMENTID,PROPERTYID,OPERATIONID,ARRAYINDEX,VALUE) values (" + insid + "," + argid + "," + bpid + "," + oid + "," + arrayindex + ",'')";
/*  721 */           AMConnectionPool.executeUpdateStmt(query);
/*      */         }
/*  723 */         else if (type == 2) {
/*  724 */           addBeanValue(insid, argid, oid, bpcid);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  730 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  733 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward manageoperations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  739 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  742 */       String resid = request.getParameter("resourceid");
/*  743 */       populateResProps(resid, request);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  748 */       String query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject , AM_PARENTCHILDMAPPER , AM_WSM_Instance where AM_WSM_Instance.ID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resid;
/*  749 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  750 */       ArrayList ops = new ArrayList();
/*      */       
/*  752 */       while (rs.next())
/*      */       {
/*  754 */         Properties prop = new Properties();
/*  755 */         prop.setProperty("displayname", rs.getString("DISPLAYNAME"));
/*  756 */         prop.setProperty("insid", rs.getString("RESOURCEID"));
/*  757 */         ops.add(prop);
/*      */       }
/*      */       
/*      */ 
/*  761 */       request.setAttribute("WSOpData", ops);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  765 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  769 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  771 */     return mapping.findForward("manageoperations");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showargs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  779 */       String oid = request.getParameter("oid");
/*  780 */       String insid = request.getParameter("insid");
/*      */       
/*  782 */       request.setAttribute("oid", oid);
/*  783 */       request.setAttribute("insid", insid);
/*      */       
/*  785 */       String query = "select displayname from AM_ManagedObject where resourceid=" + insid;
/*  786 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  787 */       if (rs.next())
/*      */       {
/*  789 */         request.setAttribute("displayname", rs.getString("displayname"));
/*      */       }
/*  791 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  793 */       query = "SELECT o.name as opname, a.*, c.name as argclass, c.type, av.arrayindex, av.value FROM  AM_WSM_Argument a JOIN AM_WSM_Operation o on o.id  = " + oid + "  and a.operationid  = o.id JOIN AM_WSM_Class c on a.classid  = c.id LEFT OUTER JOIN AM_WSM_ArgumentValue av ON av.argumentid  = a.id AND av.instanceid = " + insid + " ORDER BY ARGINDEX, arrayindex ";
/*  794 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  796 */       ArrayList args = new ArrayList();
/*      */       
/*  798 */       while (rs.next())
/*      */       {
/*  800 */         int argindex = rs.getInt("ARGINDEX");
/*  801 */         String name = rs.getString("NAME");
/*      */         
/*      */ 
/*  804 */         HashMap map = getArgMap(args, name);
/*  805 */         int type = rs.getInt("type");
/*  806 */         int isarray = rs.getInt("ISARRAY");
/*  807 */         int argid = rs.getInt("ID");
/*      */         
/*  809 */         if (map == null) {
/*  810 */           map = new HashMap();
/*  811 */           map.put("argid", String.valueOf(argid));
/*  812 */           map.put("argindex", String.valueOf(argindex));
/*  813 */           map.put("name", name);
/*  814 */           map.put("argclass", rs.getString("argclass"));
/*  815 */           map.put("type", String.valueOf(type));
/*  816 */           map.put("isarray", isarray + "");
/*      */           
/*  818 */           args.add(map);
/*      */         }
/*  820 */         if (type == 1) {
/*  821 */           ArrayList value = (ArrayList)map.get("value");
/*  822 */           if (value == null) {
/*  823 */             value = new ArrayList();
/*      */           }
/*  825 */           value.add(rs.getString("value"));
/*  826 */           map.put("value", value);
/*      */         }
/*  828 */         else if (type == 2) {
/*  829 */           int cid = rs.getInt("CLASSID");
/*  830 */           ArrayList beanprops = new ArrayList();
/*  831 */           ArrayList beanprop = handleBeanArg(insid, cid, argid, beanprops);
/*  832 */           map.put("value", beanprop);
/*      */         }
/*      */       }
/*  835 */       AMConnectionPool.closeStatement(rs);
/*  836 */       request.setAttribute("argumentdetails", args);
/*      */     } catch (Exception e) {
/*  838 */       e.printStackTrace();
/*      */     }
/*  840 */     return new ActionForward("/jsp/wsmarguments.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward saveargs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  845 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  848 */       Map map = request.getParameterMap();
/*  849 */       String oid = ((String[])(String[])map.get("oid"))[0];
/*  850 */       String insid = ((String[])(String[])map.get("insid"))[0];
/*  851 */       String displayname = ((String[])(String[])map.get("displayname"))[0];
/*      */       
/*  853 */       String query = "update AM_ManagedObject set displayname='" + displayname + "' where resourceid=" + insid;
/*  854 */       AMConnectionPool.executeUpdateStmt(query);
/*      */       
/*  856 */       query = "select o.name as opname,a.id,a.classid,c.name,c.type,a.isarray from AM_WSM_Argument a,AM_WSM_Class c,AM_WSM_Operation o where operationid=" + oid + " and a.classid=c.id and a.operationid=o.id";
/*  857 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  858 */       while (rs.next())
/*      */       {
/*  860 */         String argid = rs.getString("id");
/*  861 */         int isarray = rs.getInt("isarray");
/*  862 */         int type = rs.getInt("type");
/*  863 */         int classid = rs.getInt("classid");
/*      */         
/*  865 */         if (type == 1) {
/*  866 */           String value = ((String[])(String[])map.get("simple-" + argid))[0];
/*  867 */           if (value != null) {
/*  868 */             if (isarray == 0) {
/*  869 */               query = "update AM_WSM_ArgumentValue set VALUE='" + value + "' where instanceid=" + insid + " and argumentid=" + argid;
/*  870 */               AMConnectionPool.executeUpdateStmt(query);
/*      */             }
/*      */             else {
/*  873 */               query = "delete from AM_WSM_ArgumentValue where instanceid=" + insid + " and argumentid=" + argid;
/*  874 */               AMConnectionPool.executeUpdateStmt(query);
/*  875 */               StringTokenizer tok = new StringTokenizer(value, ",");
/*  876 */               int index = 1;
/*  877 */               while (tok.hasMoreTokens())
/*      */               {
/*  879 */                 query = "insert into AM_WSM_ArgumentValue (INSTANCEID,ARGUMENTID,OPERATIONID,ARRAYINDEX,VALUE) values (" + insid + "," + argid + "," + oid + "," + index + ",'" + tok.nextToken() + "')";
/*  880 */                 AMConnectionPool.executeUpdateStmt(query);
/*  881 */                 index++;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  886 */         else if (type == 2) {
/*  887 */           handleBeanValue(insid, argid, classid, oid, isarray, map);
/*      */         }
/*      */       }
/*      */       
/*  891 */       PrintWriter pw = response.getWriter();
/*  892 */       pw.print("<script>window.opener.location.reload();window.close();</script>");
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  897 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  900 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*  903 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getSOAPRequestInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  908 */     String operationID = request.getParameter("operationID");
/*  909 */     ResultSet result = null;
/*      */     try
/*      */     {
/*  912 */       PrintWriter out = response.getWriter();
/*  913 */       StringBuffer soapInfo = new StringBuffer();
/*      */       
/*  915 */       result = AMConnectionPool.executeQueryStmt("select SOAPREQUEST,SOAPACTION from AM_WSM_Operation where ID=" + operationID);
/*  916 */       if (result.next())
/*      */       {
/*  918 */         soapInfo.append(result.getString("SOAPACTION")).append("#SOAP_SEPERATOR#").append(result.getString("SOAPREQUEST"));
/*      */       }
/*  920 */       out.println(soapInfo);
/*  921 */       out.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  925 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  929 */       AMConnectionPool.closeStatement(result);
/*      */     }
/*      */     
/*  932 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getSOAPInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  937 */     String operationID = request.getParameter("operationId");
/*  938 */     ResultSet result = null;
/*  939 */     HashMap toReturn = null;
/*  940 */     boolean emptyResponse = false;
/*  941 */     HashMap configProps = new HashMap();
/*      */     
/*      */     try
/*      */     {
/*  945 */       result = AMConnectionPool.executeQueryStmt("select SOAPREQUEST,SOAPACTION,XSLTINPUT from AM_WSM_Instance where ID=" + operationID);
/*  946 */       if (result.next())
/*      */       {
/*  948 */         toReturn = new HashMap();
/*  949 */         toReturn.put("Request", result.getString("SOAPREQUEST"));
/*  950 */         String action = result.getString("SOAPACTION");
/*  951 */         if (action != null)
/*      */         {
/*  953 */           toReturn.put("Action", action);
/*      */         } else {
/*  955 */           toReturn.put("Action", "");
/*      */         }
/*  957 */         String xslt = result.getString("XSLTINPUT") != null ? result.getString("XSLTINPUT") : "";
/*  958 */         toReturn.put("xsltInput", result.getString("XSLTINPUT"));
/*      */       }
/*  960 */       long colltime = -1L;
/*  961 */       result = AMConnectionPool.executeQueryStmt("select SOAPRESPONSE,COLLECTIONTIME from AM_WSM_OperationData where INSTANCEID=" + operationID + " order by COLLECTIONTIME desc");
/*  962 */       if (result.next())
/*      */       {
/*  964 */         toReturn.put("Response", result.getString("SOAPRESPONSE"));
/*  965 */         colltime = result.getLong("COLLECTIONTIME");
/*      */       }
/*      */       else
/*      */       {
/*  969 */         toReturn.put("Response", FormatUtil.getString("am.webclient.wsm.nosoapresponse.text"));
/*  970 */         emptyResponse = true;
/*      */       }
/*  972 */       if (!emptyResponse)
/*      */       {
/*      */ 
/*      */ 
/*  976 */         configProps = getOperationConfigDetails(request.getParameter("resId"), operationID);
/*      */         
/*  978 */         String resid = request.getParameter("resId");
/*  979 */         HashMap xslt = new HashMap();
/*  980 */         int attributeId = -1;
/*  981 */         ArrayList<Integer> graphIds = new ArrayList();
/*  982 */         AMConnectionPool.closeStatement(result);
/*  983 */         result = AMConnectionPool.executeQueryStmt("select AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,ATTRIBUTE,TYPE,REPORTS_ENABLED from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and RESOURCEID=" + operationID);
/*  984 */         while (result.next())
/*      */         {
/*  986 */           Properties prop = new Properties();
/*  987 */           prop.setProperty("Name", result.getString("ATTRIBUTE"));
/*  988 */           prop.setProperty("Value", "-");
/*  989 */           prop.setProperty("Reports", String.valueOf(result.getInt("REPORTS_ENABLED")));
/*  990 */           xslt.put(Integer.valueOf(result.getInt("ATTRIBUTEID")), prop);
/*  991 */           if (result.getInt("TYPE") == 0)
/*      */           {
/*  993 */             prop.setProperty("Type", "0");
/*  994 */             graphIds.add(Integer.valueOf(result.getInt("ATTRIBUTEID")));
/*      */           }
/*      */           else
/*      */           {
/*  998 */             prop.setProperty("Type", "1");
/*      */           }
/*      */         }
/* 1001 */         AMConnectionPool.closeResultSet(result);
/* 1002 */         result = AMConnectionPool.executeQueryStmt("select ATTRIBUTEID,VALUE from AM_CAM_COLUMNAR_DATA where ROWID=" + operationID + " and COLLECTIONTIME=" + colltime);
/* 1003 */         while (result.next())
/*      */         {
/* 1005 */           attributeId = result.getInt("ATTRIBUTEID");
/* 1006 */           Properties prop = (Properties)xslt.get(Integer.valueOf(attributeId));
/* 1007 */           String[] attr_details = DBUtil.getAttributeDetails(attributeId);
/* 1008 */           prop.setProperty("Name", attr_details[1]);
/* 1009 */           prop.setProperty("Value", result.getString("VALUE"));
/* 1010 */           xslt.put(Integer.valueOf(attributeId), prop);
/*      */         }
/* 1012 */         toReturn.put("xsltAttributes", xslt);
/* 1013 */         toReturn.put("graphIds", graphIds);
/* 1014 */         toReturn.put("resourceId", resid);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1019 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1023 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1025 */     if (toReturn != null)
/*      */     {
/* 1027 */       toReturn.put("name", DBUtil.getDisplaynameforResourceID(operationID));
/* 1028 */       request.setAttribute("SOAPInfo", toReturn);
/* 1029 */       request.setAttribute("configProps", configProps);
/*      */     }
/* 1031 */     request.setAttribute("displayname", DBUtil.getDisplaynameforResourceID(request.getParameter("resId")));
/* 1032 */     return mapping.findForward("operationDetails");
/*      */   }
/*      */   
/*      */   private HashMap getOperationConfigDetails(String resid, String operationID)
/*      */   {
/* 1037 */     HashMap configProps = new HashMap();
/* 1038 */     ResultSet rs = null;
/* 1039 */     String query = "select WSDLPATH,HOST,PORT,ENDPOINT,SERVICENAME,POLLINTERVAL,TIMEOUT,USERNAME," + DBQueryUtil.decode(Constants.ENCODEKEY, "password") + " as PASSWORD,HEADERS,TOKENS from AM_WSM_Config where resourceid=" + resid;
/*      */     try
/*      */     {
/* 1042 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1043 */       while (rs.next())
/*      */       {
/* 1045 */         configProps.put("wsdlpath", rs.getString("WSDLPATH"));
/* 1046 */         configProps.put("host", rs.getString("HOST"));
/* 1047 */         configProps.put("port", rs.getString("PORT"));
/* 1048 */         configProps.put("endpoint", rs.getString("ENDPOINT"));
/* 1049 */         configProps.put("serviceName", rs.getString("SERVICENAME"));
/* 1050 */         configProps.put("pollinterval", rs.getString("POLLINTERVAL"));
/* 1051 */         configProps.put("timeout", rs.getString("TIMEOUT"));
/* 1052 */         configProps.put("username", rs.getString("USERNAME"));
/* 1053 */         configProps.put("password", rs.getString("PASSWORD"));
/* 1054 */         configProps.put("customHeaders", rs.getString("HEADERS"));
/* 1055 */         configProps.put("tokenAndOperation", rs.getString("TOKENS"));
/*      */       }
/* 1057 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1059 */       query = "select max(collectiontime) as coltime from AM_ManagedObjectData mod1 where mod1.resid=" + resid;
/* 1060 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1061 */       if (rs.next()) {
/* 1062 */         String coltimestr = rs.getString("coltime");
/* 1063 */         if ((coltimestr != null) && (!coltimestr.equalsIgnoreCase("null")))
/*      */         {
/* 1065 */           long lastpoll = Long.parseLong(coltimestr);
/* 1066 */           long nextpoll = lastpoll + Integer.parseInt((String)configProps.get("pollinterval")) * 60 * 1000;
/* 1067 */           configProps.put("lastpoll", lastpoll + "");
/* 1068 */           configProps.put("nextpoll", nextpoll + "");
/*      */         }
/*      */         else {
/* 1071 */           configProps.put("lastpoll", "0");
/* 1072 */           configProps.put("nextpoll", "0");
/*      */         }
/*      */       }
/*      */       else {
/* 1076 */         configProps.put("lastpoll", "0");
/* 1077 */         configProps.put("nextpoll", "0");
/*      */       }
/* 1079 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1081 */       String maxCollectionTime = (String)configProps.get("lastpoll");
/* 1082 */       query = "select EXECUTIONTIME from AM_WSM_OperationData where INSTANCEID=" + operationID + " and COLLECTIONTIME=" + maxCollectionTime;
/* 1083 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1084 */       if (rs.next()) {
/* 1085 */         configProps.put("executiontime", rs.getString("EXECUTIONTIME"));
/*      */       }
/* 1087 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1091 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1095 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1097 */     return configProps;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward updateRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1103 */     ActionMessages messages = new ActionMessages();
/* 1104 */     PreparedStatement ps = null;
/* 1105 */     StringBuffer result = null;
/* 1106 */     String operationId = "";String resourceId = "";
/*      */     
/*      */     try
/*      */     {
/* 1110 */       resourceId = request.getParameter("resourceId");
/* 1111 */       operationId = request.getParameter("operationId");
/* 1112 */       String updatedRequest = request.getParameter("soapRequest");
/* 1113 */       String soapAction = request.getParameter("soapAction");
/* 1114 */       String xsltInput = request.getParameter("XSLTInput").trim();
/* 1115 */       String operationName = request.getParameter("operationName");
/* 1116 */       ps = AMConnectionPool.getConnection().prepareStatement("update AM_WSM_Instance set SOAPREQUEST=?,SOAPACTION=?,XSLTINPUT=? where ID=?");
/* 1117 */       ps.setString(1, updatedRequest);
/* 1118 */       ps.setString(2, soapAction);
/* 1119 */       ps.setString(3, xsltInput);
/* 1120 */       ps.setLong(4, Long.parseLong(operationId));
/* 1121 */       ps.executeUpdate();
/* 1122 */       AMConnectionPool.executeUpdateStmt("update AM_ManagedObject set DISPLAYNAME='" + operationName + "' where RESOURCEID=" + operationId);
/* 1123 */       WSMDataCollector.modifiedOperations.put(operationId, Long.valueOf(System.currentTimeMillis()));
/* 1124 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.soaprequest.update.success"));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1128 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.soaprequest.update.failure"));
/*      */     }
/* 1130 */     saveMessages(request, messages);
/* 1131 */     return new ActionForward("/wsm.do?method=getSOAPInfo&operationId=" + operationId + "&resId=" + resourceId + "&type=request");
/*      */   }
/*      */   
/*      */   private void handleBeanValue(String insid, String argid, int classid, String oid, int isarray, Map map) throws Exception {
/* 1135 */     String query = "select bp.*,c.type as proptype from AM_WSM_BeanProperty bp,AM_WSM_Class c where classid=" + classid + " and bp.type=c.id";
/* 1136 */     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1137 */     while (rs.next())
/*      */     {
/* 1139 */       String propid = rs.getString("ID");
/* 1140 */       int propclassid = rs.getInt("TYPE");
/* 1141 */       int proptype = rs.getInt("proptype");
/* 1142 */       int isproparray = rs.getInt("ISARRAY");
/*      */       
/* 1144 */       if (proptype == 1)
/*      */       {
/* 1146 */         String value = ((String[])(String[])map.get("bean-" + argid + "-" + propid))[0];
/* 1147 */         if (value != null) {
/* 1148 */           if (isproparray == 0) {
/* 1149 */             query = "update AM_WSM_BeanPropertyValue set VALUE='" + value + "' where instanceid=" + insid + " and argumentid=" + argid + " and propertyid=" + propid;
/* 1150 */             AMConnectionPool.executeUpdateStmt(query);
/*      */           }
/*      */           else {
/* 1153 */             query = "delete from AM_WSM_BeanPropertyValue where instanceid=" + insid + " and argumentid=" + argid + " and propertyid=" + propid;
/* 1154 */             AMConnectionPool.executeUpdateStmt(query);
/* 1155 */             StringTokenizer tok = new StringTokenizer(value, ",");
/* 1156 */             int index = 1;
/* 1157 */             while (tok.hasMoreTokens())
/*      */             {
/* 1159 */               query = "insert into AM_WSM_BeanPropertyValue (INSTANCEID,ARGUMENTID,PROPERTYID,OPERATIONID,ARRAYINDEX,VALUE) values (" + insid + "," + argid + "," + propid + "," + oid + "," + index + ",'" + tok.nextToken() + "')";
/* 1160 */               AMConnectionPool.executeUpdateStmt(query);
/* 1161 */               index++;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1166 */       else if (proptype == 2)
/*      */       {
/* 1168 */         handleBeanValue(insid, argid, propclassid, oid, isproparray, map);
/*      */       }
/*      */     }
/* 1171 */     AMConnectionPool.closeStatement(rs);
/*      */   }
/*      */   
/*      */   private HashMap getArgMap(ArrayList args, String name)
/*      */   {
/* 1176 */     int i = 0; for (int size = args.size(); i < size; i++)
/*      */     {
/* 1178 */       HashMap arg = (HashMap)args.get(i);
/* 1179 */       if (arg.get("name").equals(name)) {
/* 1180 */         return arg;
/*      */       }
/*      */     }
/* 1183 */     return null;
/*      */   }
/*      */   
/*      */   private ArrayList handleBeanArg(String insid, int classid, int argid, ArrayList beanprops)
/*      */   {
/* 1188 */     String query = "select bp.id,bp.propertyname,bp.subtype,bp.isarray,bpv.arrayindex,bpv.value,c1.name as beanclass,c2.name as propertyclass,c2.type as propertytype,c2.id as propertyclassid from AM_WSM_BeanProperty bp,AM_WSM_BeanPropertyValue bpv,AM_WSM_Class c1,AM_WSM_Class c2 where bp.classid=" + classid + " and bp.id=bpv.propertyid and bpv.instanceid=" + insid + " and c1.id=bp.classid and c2.id=bp.type and bpv.argumentid=" + argid;
/* 1189 */     ResultSet rs = null;
/*      */     try {
/* 1191 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1192 */       while (rs.next())
/*      */       {
/* 1194 */         String propname = rs.getString("propertyname");
/* 1195 */         HashMap props = getPropMap(beanprops, propname);
/* 1196 */         int type = rs.getInt("propertytype");
/* 1197 */         int isarray = rs.getInt("isarray");
/*      */         
/* 1199 */         if (props == null) {
/* 1200 */           props = new HashMap();
/* 1201 */           props.put("propid", rs.getString("id"));
/* 1202 */           props.put("propname", propname);
/* 1203 */           props.put("propclass", rs.getString("propertyclass"));
/* 1204 */           props.put("proptype", String.valueOf(type));
/* 1205 */           props.put("isarray", String.valueOf(isarray));
/*      */           
/* 1207 */           beanprops.add(props);
/*      */         }
/* 1209 */         if (type == 1) {
/* 1210 */           ArrayList value = (ArrayList)props.get("value");
/* 1211 */           if (value == null) {
/* 1212 */             value = new ArrayList();
/*      */           }
/* 1214 */           value.add(rs.getString("value"));
/*      */           
/* 1216 */           props.put("value", value);
/*      */         }
/*      */         else {
/* 1219 */           int cid = rs.getInt("propertyclassid");
/* 1220 */           ArrayList beanprop = handleBeanArg(insid, cid, argid, new ArrayList());
/* 1221 */           props.put("value", beanprop);
/*      */         }
/*      */       }
/* 1224 */       AMConnectionPool.closeStatement(rs);
/* 1225 */       return beanprops;
/*      */     } catch (Exception e) {
/* 1227 */       e.printStackTrace();
/*      */     }
/* 1229 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward deleteoperations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1234 */     String resourceid = request.getParameter("resourceid");
/* 1235 */     String[] instances = request.getParameterValues("instance");
/*      */     try
/*      */     {
/* 1238 */       ArrayList instance = new ArrayList();
/*      */       
/*      */ 
/* 1241 */       int i = 0; for (int si = instances.length; i < si; i++)
/*      */       {
/* 1243 */         instance.add(instances[i]);
/*      */       }
/*      */       
/* 1246 */       DeleteWSMMonitor.deleteInstanceOperations(instance);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1251 */       ex.printStackTrace();
/*      */     }
/* 1253 */     return new ActionForward("/wsm.do?method=manageoperations&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */   public ActionForward editmonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1258 */     String resid = request.getParameter("resourceid");
/* 1259 */     String poll = request.getParameter("pollinterval");
/* 1260 */     String timeout = request.getParameter("timeout");
/* 1261 */     String username = request.getParameter("username");
/* 1262 */     String password = request.getParameter("password");
/* 1263 */     String displayname = request.getParameter("displayname");
/* 1264 */     String endpoint = request.getParameter("endpoint");
/* 1265 */     String customHeaders = request.getParameter("customHeaders");
/* 1266 */     String tokenAndOperation = request.getParameter("tokenAndOperation");
/*      */     
/* 1268 */     String query = "update AM_ManagedObject set displayname='" + displayname + "' where resourceid=" + resid;
/*      */     
/*      */     try
/*      */     {
/* 1272 */       AMConnectionPool.executeUpdateStmt(query);
/* 1273 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*      */       
/* 1275 */       query = "update AM_WSM_Config set ENDPOINT='" + endpoint + "', pollinterval=" + poll + ",timeout=" + timeout + ",username='" + username + "',password=" + DBQueryUtil.encode(password, Constants.ENCODEKEY) + ",HEADERS='" + customHeaders + "', TOKENS='" + tokenAndOperation + "' where resourceid=" + resid;
/*      */       
/* 1277 */       AMConnectionPool.executeUpdateStmt(query);
/* 1278 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1282 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1286 */     WSMDataCollector dc = (WSMDataCollector)WSMProcess.datacollectorMap.get(Integer.valueOf(Integer.parseInt(resid)));
/* 1287 */     if (dc != null)
/*      */     {
/* 1289 */       dc.run();
/*      */     }
/* 1291 */     return new ActionForward("/wsm.do?method=showdetails&resourceid=" + resid, true);
/*      */   }
/*      */   
/*      */   private HashMap getPropMap(ArrayList beanprops, String propName)
/*      */   {
/* 1296 */     int i = 0; for (int size = beanprops.size(); i < size; i++)
/*      */     {
/* 1298 */       HashMap props = (HashMap)beanprops.get(i);
/* 1299 */       if (props.get("propname").equals(propName)) {
/* 1300 */         return props;
/*      */       }
/*      */     }
/* 1303 */     return null;
/*      */   }
/*      */   
/*      */   private boolean checkIfAlreadyExists(String wsdlurl)
/*      */   {
/* 1308 */     String query = "select RESOURCEID from AM_ManagedObject where type='Web Service' and RESOURCENAME='" + wsdlurl + "'";
/*      */     try {
/* 1310 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1311 */       if (rs.next())
/*      */       {
/* 1313 */         return true;
/*      */       }
/* 1315 */       AMConnectionPool.closeStatement(rs);
/*      */     } catch (Exception e) {
/* 1317 */       e.printStackTrace();
/*      */     }
/* 1319 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward pollNow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1326 */     String resourceID = request.getParameter("resourceid");
/*      */     try {
/* 1328 */       WSMDataCollector dc = (WSMDataCollector)WSMProcess.datacollectorMap.get(Integer.valueOf(Integer.parseInt(resourceID)));
/* 1329 */       if (dc != null)
/*      */       {
/* 1331 */         dc.setEnabled(false);
/*      */       }
/*      */       else {
/* 1334 */         AMLog.debug("Polling cannot be done for resourceID " + resourceID);
/*      */       }
/* 1336 */       int resid = Integer.parseInt(resourceID);
/* 1337 */       WSMProcess.datacollectorMap.remove(Integer.valueOf(resid));
/* 1338 */       WSMDataCollector dc1 = new WSMDataCollector(Integer.parseInt(resourceID));
/* 1339 */       dc1.start();
/* 1340 */       WSMProcess.datacollectorMap.put(Integer.valueOf(resid), dc1);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1344 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 1347 */     return new ActionForward("/showresource.do?resourceid=" + resourceID + "&method=showResourceForResourceID&pollnow=true", true);
/*      */   }
/*      */   
/*      */   public ActionForward testOperation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try {
/* 1353 */       Properties prop = new Properties();
/* 1354 */       JSONObject json = new JSONObject();
/*      */       
/* 1356 */       int operationID = -1;
/* 1357 */       String opid = request.getParameter("operation");
/* 1358 */       if (opid != null) {
/* 1359 */         operationID = Integer.parseInt(opid);
/*      */       }
/*      */       
/* 1362 */       String req = request.getParameter("soapRequest");
/* 1363 */       String operationName = request.getParameter("operationName");
/* 1364 */       String soapAction = request.getParameter("soapAction");
/* 1365 */       String xsltInput = request.getParameter("XSLTInput").trim();
/*      */       
/* 1367 */       if (req != null) {
/* 1368 */         prop.setProperty("soapRequest", req);
/*      */       } else {
/* 1370 */         prop.setProperty("soapRequest", "");
/*      */       }
/*      */       
/* 1373 */       if (soapAction != null) {
/* 1374 */         prop.setProperty("soapAction", soapAction);
/*      */       } else {
/* 1376 */         prop.setProperty("soapAction", "");
/*      */       }
/* 1378 */       if (xsltInput != null) {
/* 1379 */         prop.setProperty("xsltInput", xsltInput);
/*      */       } else {
/* 1381 */         prop.setProperty("xsltInput", "");
/*      */       }
/*      */       
/*      */ 
/* 1385 */       WSMDataCollector wsm = new WSMDataCollector(Integer.parseInt(request.getParameter("resourceid")));
/* 1386 */       HashMap responseInfo = wsm.invoke(prop);
/*      */       
/* 1388 */       String soapResponse = "";
/* 1389 */       String xsltOutput = "";
/* 1390 */       soapResponse = (String)responseInfo.get("SOAPRequest");
/*      */       
/* 1392 */       if (soapResponse.equals("none"))
/*      */       {
/*      */ 
/* 1395 */         soapResponse = FormatUtil.getString("am.webclient.wsm.nosoapresponse.text");
/*      */ 
/*      */ 
/*      */       }
/* 1399 */       else if ((xsltInput != null) && (xsltInput.contains("?xml")))
/*      */       {
/* 1401 */         xsltOutput = WSMDataCollector.applyXSLT(soapResponse, xsltInput);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1406 */       if ((operationID == 0) && (operationName != null))
/*      */       {
/*      */ 
/* 1409 */         json.put("operationName", operationName);
/*      */       }
/*      */       else
/*      */       {
/* 1413 */         ResultSet rs = null;
/*      */         try
/*      */         {
/* 1416 */           rs = AMConnectionPool.executeQueryStmt("select name from AM_WSM_Operation where id=" + operationID);
/* 1417 */           if (rs.next()) {
/* 1418 */             json.put("operationName", rs.getString("name"));
/*      */           }
/*      */         } catch (Exception ex) {
/* 1421 */           ex.printStackTrace();
/*      */         } finally {
/* 1423 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       
/* 1427 */       json.put("soapresponse", soapResponse);
/* 1428 */       json.put("xsltOutput", xsltOutput);
/* 1429 */       PrintWriter out = response.getWriter();
/* 1430 */       response.setContentType("text/plain;charset=UTF-8");
/* 1431 */       out.println(json.toString());
/* 1432 */       out.flush();
/*      */     } catch (Exception ex) {
/* 1434 */       ex.printStackTrace();
/*      */     }
/* 1436 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getSOAPRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1442 */     String operationID = request.getParameter("operationID");
/*      */     
/* 1444 */     ResultSet result = null;
/* 1445 */     PrintWriter out = null;
/*      */     try {
/* 1447 */       out = response.getWriter();
/*      */     }
/*      */     catch (IOException e1) {
/* 1450 */       e1.printStackTrace();
/*      */     }
/* 1452 */     StringBuffer soapInfo = new StringBuffer();
/*      */     try
/*      */     {
/* 1455 */       result = AMConnectionPool.executeQueryStmt("select SOAPREQUEST from AM_WSM_Instance where ID=" + operationID);
/* 1456 */       if (result.next())
/*      */       {
/* 1458 */         soapInfo.append(result.getString("SOAPREQUEST"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1463 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1467 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1469 */     out.println(soapInfo);
/* 1470 */     out.flush();
/* 1471 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getSOAPResponse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1476 */     String operationID = request.getParameter("operationID");
/*      */     
/* 1478 */     ResultSet result = null;
/* 1479 */     PrintWriter out = null;
/*      */     try {
/* 1481 */       out = response.getWriter();
/*      */     }
/*      */     catch (IOException e1) {
/* 1484 */       e1.printStackTrace();
/*      */     }
/*      */     
/* 1487 */     StringBuffer soapInfo = new StringBuffer();
/*      */     try
/*      */     {
/* 1490 */       result = AMConnectionPool.executeQueryStmt("select SOAPRESPONSE from AM_WSM_OperationData where INSTANCEID=" + operationID);
/* 1491 */       if (result.next())
/*      */       {
/* 1493 */         soapInfo.append(result.getString("SOAPRESPONSE"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1498 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1502 */       AMConnectionPool.closeStatement(result);
/*      */     }
/*      */     
/* 1505 */     out.println(soapInfo);
/* 1506 */     out.flush();
/* 1507 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward enabledisablereports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1512 */     ActionMessages messages = new ActionMessages();
/* 1513 */     String operationId = request.getParameter("operationId");
/* 1514 */     String[] xsltAttrs = request.getParameterValues("xslt");
/* 1515 */     String enable = request.getParameter("enable");
/* 1516 */     String resourceId = request.getParameter("resourceId");
/* 1517 */     ArrayList<String> reportQueries = new ArrayList();
/* 1518 */     int hasreport = 0;
/* 1519 */     if (enable.equals("true")) {
/* 1520 */       hasreport = 1;
/*      */     }
/* 1522 */     if (xsltAttrs != null)
/*      */     {
/* 1524 */       updateReportSettings(xsltAttrs, hasreport, enable);
/* 1525 */       if (hasreport == 1) {
/* 1526 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.reportenabled.text"));
/*      */       }
/*      */       else
/*      */       {
/* 1530 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.reportdisabled.text"));
/*      */       }
/* 1532 */       saveMessages(request, messages);
/*      */     }
/* 1534 */     return new ActionForward("/wsm.do?method=getSOAPInfo&operationId=" + operationId + "&resId=" + resourceId + "&type=response");
/*      */   }
/*      */   
/*      */   public static boolean updateReportSettings(String[] attrs, int hasreport, String enable)
/*      */   {
/* 1539 */     ArrayList<String> reportQueries = new ArrayList();
/*      */     try
/*      */     {
/* 1542 */       String wsmReportQuery = "";String xsltAttr = "";String CAMarchiverQuery = "";
/* 1543 */       int i = 0; for (int len = attrs.length; i < len; i++)
/*      */       {
/* 1545 */         xsltAttr = attrs[i];
/* 1546 */         wsmReportQuery = "update AM_ATTRIBUTES_EXT set REPORTS_ENABLED=" + hasreport + ",ISARCHIVEING=" + hasreport + " where ATTRIBUTEID=" + xsltAttr;
/* 1547 */         if (enable.equals("true"))
/*      */         {
/* 1549 */           CAMarchiverQuery = "insert into AM_ArchiverCAMConfig values (" + xsltAttr + ")";
/*      */         }
/*      */         else
/*      */         {
/* 1553 */           CAMarchiverQuery = "delete from AM_ArchiverCAMConfig where ATTRIBUTEID = " + xsltAttr;
/*      */         }
/* 1555 */         reportQueries.add(wsmReportQuery);
/* 1556 */         reportQueries.add(CAMarchiverQuery);
/* 1557 */         EnterpriseUtil.addUpdateQueryToFile(wsmReportQuery);
/*      */       }
/* 1559 */       AMBatchStmtExecutor.executeBatch(reportQueries);
/* 1560 */       return true;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1564 */       ex.printStackTrace();
/*      */     }
/* 1566 */     return false;
/*      */   }
/*      */   
/*      */   public ActionForward variableMethods(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1571 */     ArrayList<Properties> varMethods = AddWSMMonitor.getVariableMethods();
/* 1572 */     request.setAttribute("varmethods", varMethods);
/* 1573 */     return new ActionForward("/jsp/WSMMethods.jsp");
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\wsm\WSMAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */