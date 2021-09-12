/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.archiver.AMAvailabilityEventListener;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.extprod.OPMEventQueue;
/*      */ import com.adventnet.appmanager.server.framework.extprod.OPMEventQueueCopyTask;
/*      */ import com.adventnet.appmanager.server.framework.extprod.OPMEventUpdateTask;
/*      */ import com.adventnet.appmanager.server.framework.extprod.ProdIntegThread;
/*      */ import com.adventnet.appmanager.server.framework.extprod.ServiceNowIntegConfig;
/*      */ import com.adventnet.appmanager.server.framework.extprod.Site24x7IntegUpdate;
/*      */ import com.adventnet.appmanager.struts.beans.APMHDClientUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.ConcurrentHttpClient;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtConnectorUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.ProxyUtil;
/*      */ import com.adventnet.appmanager.util.Site24X7Util;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.CMDBResyncObject;
/*      */ import com.me.apm.cmdb.CMDBResyncThread;
/*      */ import com.me.apm.cmdb.CMDBResyncThread.SyncState;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URL;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.httpclient.HttpClient;
/*      */ import org.apache.commons.httpclient.methods.GetMethod;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ExtDeviceActions
/*      */   extends DispatchAction
/*      */ {
/*   66 */   private String types = Constants.resourceTypes;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward fetchDataNowForExtDevice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   74 */     AMActionForm amform = (AMActionForm)form;
/*   75 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*   76 */     ActionMessages messages = new ActionMessages();
/*   77 */     String prodName = request.getParameter("prodName");
/*   78 */     if (prodName.equals("ServiceDesk"))
/*      */     {
/*   80 */       CMDBResyncThread thread = CMDBResyncThread.getInstance();
/*   81 */       if (thread.getSyncState() != CMDBResyncThread.SyncState.INPROGRESS)
/*      */       {
/*      */         try
/*      */         {
/*   85 */           CMDBResyncObject reSyncObj = new CMDBResyncObject(request);
/*   86 */           thread.setResyncObject(reSyncObj);
/*   87 */           thread.start();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*   91 */           e.printStackTrace();
/*      */         }
/*   93 */         request.setAttribute("SuccessMessage", FormatUtil.getString("am.webclient.addon.data.fetch.cmdb.initiated"));
/*   94 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*      */       
/*      */ 
/*   98 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.addon.feth.cmdb.inprogress")));
/*   99 */       saveMessages(request, messages);
/*  100 */       return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */     }
/*      */     
/*  103 */     FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*      */     
/*  105 */     if (prodName.equals("OpManager"))
/*      */     {
/*  107 */       boolean opmanagerConnectorPresent = fed.isOpmanagerConnectorPresent();
/*  108 */       String usrtype = fed.getUserType();
/*  109 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opmanagerConnectorPresent))))
/*      */       {
/*  111 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpManager is not supported in this edition.")));
/*  112 */         saveMessages(request, messages);
/*  113 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*      */     }
/*  116 */     else if (prodName.equals("OpStor"))
/*      */     {
/*  118 */       boolean opstorConnectorPresent = fed.isOpstorConnectorPresent();
/*  119 */       String usrtype = fed.getUserType();
/*  120 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opstorConnectorPresent))))
/*      */       {
/*  122 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpStor is not supported in this edition.")));
/*  123 */         saveMessages(request, messages);
/*  124 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*      */     }
/*  127 */     String haid = request.getParameter("haid");
/*      */     
/*  129 */     if (prodName.equals("Site24x7")) {
/*  130 */       ResultSet rs = null;
/*      */       try {
/*  132 */         String apiKey = null;
/*  133 */         String autoStatus = null;
/*  134 */         String query = "select URL,BYPASSAUTH from AM_INTEGRATEDPRODUCTS where ID='" + request.getParameter("id") + "'";
/*  135 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  136 */         if (rs.next()) {
/*  137 */           apiKey = rs.getString("URL");
/*  138 */           autoStatus = rs.getString("BYPASSAUTH");
/*      */         }
/*      */         
/*  141 */         boolean autoSync = (autoStatus != null) && (autoStatus.equals("true"));
/*  142 */         boolean fetchStatus = Site24X7Util.fetchSite24x7data(true, apiKey, Integer.parseInt(request.getParameter("id")), autoSync, false);
/*  143 */         if (fetchStatus) {
/*  144 */           request.setAttribute("SuccessMessage", FormatUtil.getString("am.webclient.addon.data.fetch.success"));
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/*  153 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  156 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  161 */       String host = null;
/*  162 */       String port = null;
/*  163 */       String protocol = null;
/*  164 */       String urlToContact = null;
/*  165 */       String userName = null;
/*  166 */       String password = null;
/*  167 */       String checkBox = "true";
/*  168 */       String byPassAuth = "false";
/*  169 */       int prodId = -1;
/*  170 */       String pollingInterval = null;
/*  171 */       String query = "select PROTOCOL,HOST,PORT,ID,URL,USERNAME," + DBQueryUtil.decode(Constants.EXTPRODENCODEKEY, "PASSWORD") + " as PASSWORD,POLLINGINTERVAL,STATUS,BYPASSAUTH from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + prodName + "'";
/*  172 */       ResultSet set1 = null;
/*      */       try
/*      */       {
/*  175 */         set1 = AMConnectionPool.executeQueryStmt(query);
/*  176 */         if (set1.next())
/*      */         {
/*  178 */           prodId = set1.getInt("ID");
/*  179 */           host = set1.getString("HOST");
/*  180 */           port = set1.getString("PORT");
/*  181 */           protocol = set1.getString("PROTOCOL");
/*  182 */           urlToContact = set1.getString("URL");
/*  183 */           userName = set1.getString("USERNAME");
/*  184 */           password = set1.getString("PASSWORD");
/*  185 */           pollingInterval = set1.getString("POLLINGINTERVAL");
/*  186 */           checkBox = set1.getString("STATUS");
/*  187 */           byPassAuth = set1.getString("BYPASSAUTH");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  192 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  196 */         AMConnectionPool.closeStatement(set1);
/*      */       }
/*      */       try
/*      */       {
/*  200 */         String errorMessage = ProdIntegThread.getXMLFromRemoteProductAndInsert(protocol, host, port, prodName, urlToContact, checkBox, userName, password, byPassAuth, pollingInterval, prodId, false);
/*  201 */         if (errorMessage == null)
/*      */         {
/*  203 */           request.setAttribute("SuccessMessage", FormatUtil.getString("am.webclient.addon.data.fetch.success"));
/*      */         }
/*      */         else
/*      */         {
/*  207 */           errorMessage = "Could not Fetch Data. " + errorMessage + "<a class='bodytext' href='/extDeviceAction.do?method=editExtDevices&prodName=" + prodName + "'>Edit</a>";
/*  208 */           request.setAttribute("ErrorMessage", errorMessage);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  213 */         request.setAttribute("ErrorMessage", "Could not Fetch Data. " + e.getMessage());
/*  214 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Could not Fetch Data. " + e.getMessage()));
/*  215 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  218 */     saveMessages(request, messages);
/*  219 */     if (haid != null)
/*      */     {
/*  221 */       return new ActionForward("/showresource.do?type=All&method=getMonitorForm&haid=" + haid);
/*      */     }
/*      */     
/*      */ 
/*  225 */     return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteDeviceConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  233 */     AMActionForm amform = (AMActionForm)form;
/*  234 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  235 */     ActionMessages messages = new ActionMessages();
/*  236 */     String errorMessage = null;
/*  237 */     String prodID = request.getParameter("id");
/*      */     try {
/*  239 */       Site24X7Util.deleteExtDevice(prodID);
/*      */     } catch (Exception e) {
/*  241 */       request.setAttribute("ErrorMessage", "Could not Delete Data. " + e.getMessage());
/*  242 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Could not Delete Data. " + e.getMessage()));
/*  243 */       e.printStackTrace();
/*      */     }
/*  245 */     request.setAttribute("SuccessMessage", FormatUtil.getString("am.webclient.rbm.message.delete"));
/*  246 */     return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showExtDeviceConfigurations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  256 */     if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */     {
/*  258 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/*      */     
/*  261 */     AMActionForm amform = (AMActionForm)form;
/*  262 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  263 */     ActionErrors errors = new ActionErrors();
/*  264 */     ActionMessages messages = new ActionMessages();
/*  265 */     String errorMessage = null;
/*  266 */     String edition = Constants.getCategorytype();
/*  267 */     FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*  268 */     if (request.getParameter("updateStatus") != null)
/*      */     {
/*  270 */       String prodName = request.getParameter("prodName");
/*  271 */       if (prodName.equals("OpManager"))
/*      */       {
/*  273 */         boolean opmanagerConnectorPresent = fed.isOpmanagerConnectorPresent();
/*  274 */         String usrtype = fed.getUserType();
/*  275 */         if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opmanagerConnectorPresent))))
/*      */         {
/*  277 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpManager is not supported in this edition.")));
/*  278 */           saveMessages(request, messages);
/*      */         }
/*  280 */         else if ("CLOUD".equalsIgnoreCase(edition))
/*      */         {
/*  282 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.cloud.opstor.unsupported.edition")));
/*  283 */           saveErrors(request, errors);
/*      */         }
/*      */       }
/*  286 */       else if (prodName.equals("OpStor"))
/*      */       {
/*  288 */         boolean opstorConnectorPresent = fed.isOpstorConnectorPresent();
/*  289 */         String usrtype = fed.getUserType();
/*  290 */         if ("CLOUD".equalsIgnoreCase(edition))
/*      */         {
/*  292 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.cloud.opstor.unsupported.edition")));
/*  293 */           saveErrors(request, errors);
/*      */         }
/*  295 */         else if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opstorConnectorPresent))))
/*      */         {
/*  297 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpStor is not supported in this edition.")));
/*  298 */           saveMessages(request, messages);
/*      */         }
/*      */       }
/*      */       
/*  302 */       String status = request.getParameter("updateStatus");
/*  303 */       String updateQuery = null;
/*      */       
/*  305 */       if (prodName.equals("Site24x7")) {
/*  306 */         updateQuery = "update AM_INTEGRATEDPRODUCTS set STATUS='" + status + "' where ID='" + request.getParameter("id") + "'";
/*      */         try {
/*  308 */           HashMap prodInfo = (HashMap)DBUtil.site24x7Accounts.get(request.getParameter("id"));
/*  309 */           prodInfo.put("STATUS", status);
/*  310 */           DBUtil.site24x7Accounts.put(request.getParameter("id"), prodInfo);
/*      */           
/*  312 */           if (status.equals("true")) {
/*  313 */             Site24x7IntegUpdate.startDataCollection(request.getParameter("id"), true, true, false);
/*      */           }
/*      */         } catch (Exception e) {
/*  316 */           e.printStackTrace();
/*      */         }
/*      */       } else {
/*  319 */         updateQuery = "update AM_INTEGRATEDPRODUCTS set STATUS='" + status + "' where PRODUCT_NAME='" + prodName + "'";
/*      */       }
/*  321 */       AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */     }
/*  323 */     if ("CLOUD".equalsIgnoreCase(edition))
/*      */     {
/*  325 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.cloud.opstor.unsupported.edition")));
/*  326 */       saveErrors(request, errors);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  331 */       String url = null;
/*  332 */       String SDeskQuery = "select HOST, PORT, PROTOCOL from ADDONPRODUCTS_URL where PRODUCT_NAME='ServiceDesk'";
/*  333 */       ResultSet rs = AMConnectionPool.executeQueryStmt(SDeskQuery);
/*  334 */       if (rs.next())
/*      */       {
/*  336 */         url = rs.getString("PROTOCOL") + "://" + rs.getString("HOST") + ":" + rs.getString("PORT");
/*  337 */         request.setAttribute("SDeskHost", rs.getString("HOST"));
/*  338 */         request.setAttribute("SDeskPort", rs.getString("PORT"));
/*      */       }
/*  340 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  342 */       HashMap<String, ArrayList> Site24x7List = new HashMap();
/*  343 */       String query = "select PRODUCT_NAME,ID,HOST,PORT,STATUS,LASTDC,ERRORMESSAGE,USERNAME,POLLINGINTERVAL,MASID,PROTOCOL,URL from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME in ('OpManager','OpStor','Site24x7','Facilities','ServiceNow')";
/*  344 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/*  345 */       while (rs1.next())
/*      */       {
/*  347 */         url = rs1.getString("PROTOCOL") + "://" + rs1.getString("HOST") + ":" + rs1.getString("PORT");
/*  348 */         if (rs1.getString("PRODUCT_NAME").equals("OpManager"))
/*      */         {
/*  350 */           String prodStatus = (String)ExtConnectorUtil.prodNameVsStatus.get("OpManager");
/*  351 */           request.setAttribute("OpManId", rs1.getString("ID"));
/*  352 */           request.setAttribute("OpManHost", rs1.getString("HOST"));
/*  353 */           request.setAttribute("OpManPort", rs1.getString("PORT"));
/*  354 */           request.setAttribute("OpManLastDC", rs1.getString("LASTDC"));
/*      */           
/*  356 */           String enableStatus = rs1.getString("STATUS");
/*  357 */           ExtConnectorUtil.enableOPMSync = (enableStatus != null) && (enableStatus.equals("true"));
/*  358 */           request.setAttribute("OpManEnabled", rs1.getString("STATUS"));
/*  359 */           request.setAttribute("OpManStatus", prodStatus);
/*  360 */           if ((prodStatus == null) || ((prodStatus != null) && (prodStatus.equals("down"))))
/*      */           {
/*  362 */             if ((!rs1.getString("ERRORMESSAGE").equals("NONE")) && (errorMessage == null))
/*      */             {
/*  364 */               errorMessage = rs1.getString("ERRORMESSAGE");
/*      */             }
/*  366 */             else if ((errorMessage != null) && (!rs1.getString("ERRORMESSAGE").equals("NONE")))
/*      */             {
/*  368 */               errorMessage = errorMessage + "<br>" + rs1.getString("ERRORMESSAGE");
/*      */             }
/*      */           }
/*      */         }
/*  372 */         else if (rs1.getString("PRODUCT_NAME").equals("ServiceNow"))
/*      */         {
/*  374 */           request.setAttribute("SDeskHost", rs1.getString("URL"));
/*  375 */           request.setAttribute("SDeskPort", "N/A");
/*      */         }
/*  377 */         else if (rs1.getString("PRODUCT_NAME").equals("OpStor"))
/*      */         {
/*  379 */           String prodStatus = (String)ExtConnectorUtil.prodNameVsStatus.get("OpStor");
/*  380 */           request.setAttribute("OpStorId", rs1.getString("ID"));
/*  381 */           request.setAttribute("OpStorHost", rs1.getString("HOST"));
/*  382 */           request.setAttribute("OpStorPort", rs1.getString("PORT"));
/*  383 */           request.setAttribute("OpStorLastDC", rs1.getString("LASTDC"));
/*  384 */           request.setAttribute("OpStorEnabled", rs1.getString("STATUS"));
/*  385 */           request.setAttribute("OpStorStatus", prodStatus);
/*  386 */           if ((prodStatus == null) || ((prodStatus != null) && (prodStatus.equals("down"))))
/*      */           {
/*  388 */             if ((!rs1.getString("ERRORMESSAGE").equals("NONE")) && (errorMessage == null))
/*      */             {
/*  390 */               errorMessage = rs1.getString("ERRORMESSAGE");
/*      */             }
/*  392 */             else if ((errorMessage != null) && (!rs1.getString("ERRORMESSAGE").equals("NONE")))
/*      */             {
/*  394 */               errorMessage = errorMessage + "<br>" + rs1.getString("ERRORMESSAGE");
/*      */             }
/*      */           }
/*  397 */           request.setAttribute("OpStorProtocol", rs1.getString("PROTOCOL"));
/*      */         }
/*  399 */         else if (rs1.getString("PRODUCT_NAME").equals("Facilities"))
/*      */         {
/*  401 */           request.setAttribute("DCIMId", rs1.getString("ID"));
/*  402 */           request.setAttribute("DCIMHost", rs1.getString("HOST"));
/*  403 */           request.setAttribute("DCIMPort", rs1.getString("PORT"));
/*  404 */           request.setAttribute("DCIMLastDC", rs1.getString("LASTDC"));
/*  405 */           request.setAttribute("DCIMEnabled", rs1.getString("STATUS"));
/*  406 */           if ((!rs1.getString("ERRORMESSAGE").equals("NONE")) && (errorMessage == null))
/*      */           {
/*  408 */             errorMessage = rs1.getString("ERRORMESSAGE");
/*      */           }
/*  410 */           else if ((errorMessage != null) && (!rs1.getString("ERRORMESSAGE").equals("NONE")))
/*      */           {
/*  412 */             errorMessage = errorMessage + "<br>" + rs1.getString("ERRORMESSAGE");
/*      */           }
/*  414 */           request.setAttribute("DCIMProtocol", rs1.getString("PROTOCOL"));
/*      */         }
/*  416 */         else if (rs1.getString("PRODUCT_NAME").equals("Site24x7"))
/*      */         {
/*  418 */           String errorMsg = null;
/*  419 */           String site24x7DC = "-";
/*  420 */           ArrayList<String> lst = new ArrayList();
/*  421 */           lst.add(rs1.getString("USERNAME"));
/*  422 */           lst.add(rs1.getString("STATUS"));
/*  423 */           lst.add(rs1.getString("LASTDC"));
/*  424 */           lst.add(rs1.getString("POLLINGINTERVAL"));
/*  425 */           if (!rs1.getString("ERRORMESSAGE").equals("NONE")) {
/*  426 */             errorMsg = rs1.getString("ERRORMESSAGE");
/*      */           } else {
/*  428 */             errorMsg = FormatUtil.getString("am.webclient.site24x7.dc.success");
/*      */           }
/*  430 */           lst.add(errorMsg);
/*      */           
/*  432 */           if ((rs1.getString("LASTDC") != null) && (!rs1.getString("LASTDC").equals("-1"))) {
/*  433 */             site24x7DC = FormatUtil.formatDT(rs1.getString("LASTDC"));
/*      */           }
/*  435 */           lst.add(site24x7DC);
/*  436 */           Site24x7List.put(rs1.getString("ID"), lst);
/*      */         }
/*      */       }
/*      */       
/*  440 */       request.setAttribute("Site24x4Details", Site24x7List);
/*  441 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/*  443 */       String selectQuery = "select VALUE from AM_GLOBALCONFIG where NAME='EXTPROD.SYNC.POLLINTERVAL'";
/*  444 */       ResultSet rs2 = AMConnectionPool.executeQueryStmt(selectQuery);
/*  445 */       if (rs2.next())
/*      */       {
/*  447 */         amform.setPort(rs2.getString("VALUE"));
/*      */       }
/*  449 */       AMConnectionPool.closeStatement(rs2);
/*  450 */       long syncInter = ExtProdUtil.opmConnectorFullUpdateInterval;
/*  451 */       int syncHr = (int)(syncInter / 3600000L);
/*  452 */       int syncMin = (int)(syncInter / 60000L % 60L);
/*  453 */       amform.setSynchHr(syncHr);
/*  454 */       amform.setSynchMin(syncMin);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  459 */       e.printStackTrace();
/*      */     }
/*  461 */     if (errorMessage != null)
/*      */     {
/*  463 */       request.setAttribute("ErrorMessage", errorMessage);
/*      */     }
/*  465 */     return mapping.findForward("showExtDeviceDetails");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editExtDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  474 */     AMActionForm amform = (AMActionForm)form;
/*  475 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  476 */     ActionMessages messages = new ActionMessages();
/*      */     
/*  478 */     String prodName = request.getParameter("prodName");
/*      */     
/*  480 */     if ((prodName != null) && (prodName.equals("ServiceDesk")))
/*      */     {
/*  482 */       String methodName = "showSdeskConfiguration";
/*  483 */       if (!ServiceNowIntegConfig.getInstance().isServiceNowConfigured())
/*      */       {
/*  485 */         amform.setHelpDeskProduct("SERVICEDESK");
/*      */       }
/*      */       else
/*      */       {
/*  489 */         methodName = "showServiceNowConfiguration";
/*  490 */         amform.setHelpDeskProduct("SERVICENOW");
/*      */       }
/*  492 */       return new ActionForward("/adminAction.do?method=" + methodName);
/*      */     }
/*      */     
/*      */ 
/*  496 */     FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*  497 */     if (prodName.equals("OpManager"))
/*      */     {
/*  499 */       boolean opmanagerConnectorPresent = fed.isOpmanagerConnectorPresent();
/*  500 */       String usrtype = fed.getUserType();
/*  501 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opmanagerConnectorPresent))))
/*      */       {
/*  503 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("OpManager is not supported in this edition.")));
/*  504 */         saveMessages(request, messages);
/*  505 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  507 */       if ((!opmanagerConnectorPresent) && (FreeEditionDetails.opmanagerMessage != null))
/*      */       {
/*  509 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FreeEditionDetails.opmanagerMessage));
/*  510 */         saveMessages(request, messages);
/*      */       }
/*      */     }
/*  513 */     else if (prodName.equals("OpStor"))
/*      */     {
/*  515 */       boolean opstorConnectorPresent = fed.isOpstorConnectorPresent();
/*  516 */       String usrtype = fed.getUserType();
/*  517 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opstorConnectorPresent))))
/*      */       {
/*      */ 
/*  520 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("OpStor is not supported in this edition.")));
/*  521 */         saveMessages(request, messages);
/*  522 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  524 */       if ((!opstorConnectorPresent) && (FreeEditionDetails.opstorMessage != null))
/*      */       {
/*  526 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FreeEditionDetails.opstorMessage));
/*  527 */         saveMessages(request, messages);
/*      */       }
/*  529 */     } else if (prodName.equals("Site24x7")) {
/*  530 */       ResultSet rs = null;
/*      */       try {
/*  532 */         String quey = "select ID,URL,USERNAME,POLLINGINTERVAL,STATUS,BYPASSAUTH,MASID from AM_INTEGRATEDPRODUCTS where ID='" + request.getParameter("id") + "'";
/*  533 */         rs = AMConnectionPool.executeQueryStmt(quey);
/*  534 */         String importAll; if (rs.next())
/*      */         {
/*  536 */           amform.setApikey(rs.getString("URL"));
/*  537 */           amform.setEmail(rs.getString("USERNAME"));
/*  538 */           amform.setPollInter(rs.getInt("POLLINGINTERVAL"));
/*  539 */           amform.setGettingstarted(rs.getString("STATUS"));
/*  540 */           amform.setGettingsync(rs.getString("BYPASSAUTH"));
/*  541 */           importAll = "false";
/*  542 */           if (rs.getInt("MASID") == 1) {
/*  543 */             importAll = "true";
/*      */           }
/*  545 */           amform.setImportall(importAll);
/*  546 */           request.setAttribute("productId", rs.getString("ID"));
/*      */         }
/*  548 */         AMConnectionPool.closeStatement(rs);
/*  549 */         return mapping.findForward("AddNewExtDevice");
/*      */       } catch (Exception gd) {
/*  551 */         gd.printStackTrace();
/*      */       }
/*      */       finally {
/*  554 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*  558 */     String selectQuery = "select ID,HOST,PORT,LASTDC,STATUS,USERNAME,PROTOCOL,BYPASSAUTH,IS_REMOTE from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + prodName + "'";
/*  559 */     ResultSet set = AMConnectionPool.executeQueryStmt(selectQuery);
/*  560 */     if (set.next())
/*      */     {
/*  562 */       amform.setHost(set.getString("HOST"));
/*  563 */       amform.setPort(set.getString("PORT"));
/*  564 */       if (!set.getString("USERNAME").equalsIgnoreCase("null"))
/*      */       {
/*  566 */         amform.setUsername(set.getString("USERNAME"));
/*      */       }
/*  568 */       amform.setProtocol(set.getString("PROTOCOL"));
/*  569 */       amform.setGettingstarted(set.getString("STATUS"));
/*  570 */       amform.setActionsEnabled(set.getString("BYPASSAUTH"));
/*      */       
/*  572 */       request.setAttribute("BYPASSAUTH", set.getString("BYPASSAUTH"));
/*  573 */       request.setAttribute("productId", set.getString("ID"));
/*      */     }
/*  575 */     if (prodName.equals("OpManager"))
/*      */     {
/*  577 */       boolean addDeviceDefault = ExtProdUtil.opmImportAllDevices;
/*  578 */       String addDevDefault = addDeviceDefault ? "true" : "false";
/*  579 */       amform.setAddDeviceDefault(addDevDefault);
/*      */     }
/*  581 */     AMConnectionPool.closeStatement(set);
/*  582 */     return mapping.findForward("AddNewExtDevice");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addNewExtDevice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  592 */     AMActionForm amform = (AMActionForm)form;
/*  593 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  594 */     ActionMessages messages = new ActionMessages();
/*  595 */     ActionErrors errors = new ActionErrors();
/*  596 */     String edition = Constants.getCategorytype();
/*  597 */     String prodName = request.getParameter("prodName");
/*  598 */     if ((prodName != null) && (prodName.equals("ServiceDesk")))
/*      */     {
/*  600 */       APMHDClientUtil.setAdvancedAPISettingsInTheForm(amform);
/*  601 */       amform.setHelpDeskProduct("SERVICEDESK");
/*  602 */       request.setAttribute("showIncludeSecondLevelCITypes", Boolean.valueOf(DBUtil.getGlobalConfigValueasBoolean("am.cmdb.settings.showAll")));
/*  603 */       request.setAttribute("showAllSettings", Boolean.valueOf(true));
/*  604 */       return mapping.findForward("SdeskConfig");
/*      */     }
/*      */     
/*      */ 
/*  608 */     FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*  609 */     if (prodName.equals("OpManager"))
/*      */     {
/*  611 */       boolean opmanagerConnectorPresent = fed.isOpmanagerConnectorPresent();
/*  612 */       String usrtype = fed.getUserType();
/*  613 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opmanagerConnectorPresent)) || ("CLOUD".equalsIgnoreCase(edition))))
/*      */       {
/*  615 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.opmnager.unsupported.edition")));
/*  616 */         saveMessages(request, messages);
/*  617 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  619 */       if ((!opmanagerConnectorPresent) && (FreeEditionDetails.opmanagerMessage != null))
/*      */       {
/*  621 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FreeEditionDetails.opmanagerMessage));
/*  622 */         saveMessages(request, messages);
/*      */       }
/*      */     }
/*  625 */     else if (prodName.equals("OpStor"))
/*      */     {
/*  627 */       boolean opstorConnectorPresent = fed.isOpstorConnectorPresent();
/*  628 */       String usrtype = fed.getUserType();
/*  629 */       if (((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opstorConnectorPresent)))) || ("CLOUD".equalsIgnoreCase(edition)))
/*      */       {
/*      */ 
/*  632 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.opstor.unsupported.edition")));
/*  633 */         saveMessages(request, messages);
/*  634 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  636 */       if ((!opstorConnectorPresent) && (FreeEditionDetails.opstorMessage != null))
/*      */       {
/*  638 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FreeEditionDetails.opstorMessage));
/*  639 */         saveMessages(request, messages);
/*      */       }
/*      */     }
/*  642 */     return mapping.findForward("AddNewExtDevice");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward APMExtProductConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  652 */     ActionMessages messages = new ActionMessages();
/*  653 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     
/*  655 */     String protocol = request.getParameter("protocol");
/*  656 */     String host = request.getParameter("host");
/*  657 */     String port = request.getParameter("port");
/*  658 */     String userName = request.getParameter("username");
/*  659 */     String password = request.getParameter("password");
/*  660 */     String productName = request.getParameter("productName");
/*  661 */     String checkBox = request.getParameter("gettingstarted");
/*      */     
/*  663 */     String isRemote = ExtProdUtil.checkTheHostIsRemote(host);
/*  664 */     String pollingInterval = "5";
/*  665 */     String byPassAuth = "false";
/*      */     
/*      */ 
/*  668 */     String apikey = request.getParameter("apikey");
/*  669 */     String email = request.getParameter("email");
/*  670 */     String pollInter = request.getParameter("pollInter");
/*  671 */     String gettingsync = request.getParameter("gettingsync");
/*  672 */     gettingsync = gettingsync != null ? "true" : "false";
/*      */     
/*  674 */     String addDeviceDefault = request.getParameter("addDeviceDefault");
/*      */     
/*  676 */     if (productName.equals("OpManager"))
/*      */     {
/*  678 */       addDeviceDefault = addDeviceDefault != null ? "true" : "false";
/*      */       
/*  680 */       AMLog.debug("ExtDeviceActions addDeviceDefault:" + addDeviceDefault);
/*      */     }
/*      */     
/*  683 */     int prodId = -1;
/*      */     
/*  685 */     checkBox = checkBox != null ? "true" : "false";
/*      */     
/*  687 */     if (request.getParameter("timeout") != null)
/*      */     {
/*  689 */       pollingInterval = request.getParameter("timeout");
/*      */     }
/*      */     
/*  692 */     if (request.getParameter("productId") != null)
/*      */     {
/*  694 */       prodId = Integer.parseInt(request.getParameter("productId"));
/*      */     }
/*  696 */     request.setAttribute("productId", prodId + "");
/*      */     
/*      */ 
/*  699 */     FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*  700 */     if (productName.equals("OpManager"))
/*      */     {
/*  702 */       boolean opmanagerConnectorPresent = fed.isOpmanagerConnectorPresent();
/*  703 */       String usrtype = fed.getUserType();
/*  704 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opmanagerConnectorPresent))))
/*      */       {
/*  706 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpManager is not supported in this edition.")));
/*  707 */         saveMessages(request, messages);
/*  708 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*      */     }
/*  711 */     else if (productName.equals("OpStor"))
/*      */     {
/*  713 */       boolean opstorConnectorPresent = fed.isOpstorConnectorPresent();
/*  714 */       String usrtype = fed.getUserType();
/*  715 */       if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!opstorConnectorPresent))))
/*      */       {
/*  717 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("Could not perform the Action.OpStor is not supported in this edition.")));
/*  718 */         saveMessages(request, messages);
/*  719 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  721 */     } else if (productName.equals("Site24x7")) {
/*  722 */       int impall = 0;
/*  723 */       boolean impAll = false;
/*      */       try {
/*  725 */         if (request.getParameter("importall") != null) {
/*  726 */           impall = 1;
/*  727 */           impAll = true;
/*      */         }
/*      */       } catch (Exception er) {
/*  730 */         er.printStackTrace();
/*      */       }
/*      */       
/*  733 */       if (prodId == -1) {
/*  734 */         prodId = DBQueryUtil.getIncrementedID("ID", "AM_INTEGRATEDPRODUCTS");
/*      */       }
/*      */       
/*  737 */       HashMap statusMap = Site24X7Util.validateSite24x7APIKey("https://www.site24x7.com/api/json2/currentstatus?apikey=" + apikey.trim());
/*  738 */       String errMsg = "Could not Save Details.";
/*  739 */       boolean status = false;
/*  740 */       if (statusMap.containsKey("status")) {
/*  741 */         status = ((Boolean)statusMap.get("status")).booleanValue();
/*  742 */         if (status) {
/*  743 */           status = true;
/*      */         } else {
/*  745 */           errMsg = (String)statusMap.get("message");
/*      */         }
/*      */       }
/*      */       
/*  749 */       if (status) {
/*  750 */         boolean present = false;
/*  751 */         String selectQuery = "select ID,URL from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + productName + "' and URL='" + apikey.trim() + "'";
/*  752 */         ResultSet set = AMConnectionPool.executeQueryStmt(selectQuery);
/*  753 */         String apiKey = null;
/*  754 */         int id = -1;
/*  755 */         if (set.next()) {
/*  756 */           apiKey = set.getString("URL");
/*  757 */           id = set.getInt("ID");
/*  758 */           present = true;
/*      */         }
/*      */         
/*  761 */         if (present) {
/*  762 */           int masId = EnterpriseUtil.getManagedServerIndex();
/*  763 */           String updateAddOnProducts = "update AM_INTEGRATEDPRODUCTS set PRODUCT_NAME='" + productName + "', URL ='" + apikey.trim() + "', STATUS='" + checkBox + "', USERNAME='" + email + "',POLLINGINTERVAL='" + pollInter + "',BYPASSAUTH='" + gettingsync + "',MASID=" + impall + " where ID=" + id;
/*  764 */           AMConnectionPool.executeUpdateStmt(updateAddOnProducts);
/*      */           try {
/*  766 */             HashMap<String, String> prodList = new HashMap();
/*  767 */             prodList.put("URLTOCONTACT", apikey);
/*  768 */             prodList.put("USERNAME", email);
/*  769 */             prodList.put("STATUS", checkBox);
/*  770 */             prodList.put("POLLINTERVAL", pollInter);
/*  771 */             prodList.put("AUTOSYNC", gettingsync);
/*  772 */             prodList.put("IMPALL", impall + "");
/*  773 */             DBUtil.site24x7Accounts.put(id + "", prodList);
/*      */           }
/*      */           catch (Exception e) {
/*  776 */             e.printStackTrace();
/*      */           }
/*      */         } else {
/*  779 */           int masId = EnterpriseUtil.getManagedServerIndex();
/*  780 */           int pid = DBQueryUtil.getIncrementedID("ID", "AM_INTEGRATEDPRODUCTS");
/*  781 */           String insertAddOnProductQuery = "INSERT INTO  AM_INTEGRATEDPRODUCTS ( ID ,PRODUCT_NAME,STATUS ,URL ,USERNAME ,POLLINGINTERVAL ,BYPASSAUTH ,MASID ) values(" + pid + ",'" + productName + "','" + checkBox + "','" + apikey.trim() + "','" + email + "','" + pollInter + "','" + gettingsync + "'," + impall + ") ";
/*  782 */           AMConnectionPool.executeUpdateStmt(insertAddOnProductQuery);
/*      */           try {
/*  784 */             HashMap<String, String> prodList = new HashMap();
/*  785 */             prodList.put("URLTOCONTACT", apikey);
/*  786 */             prodList.put("USERNAME", email);
/*  787 */             prodList.put("STATUS", checkBox);
/*  788 */             prodList.put("POLLINTERVAL", pollInter);
/*  789 */             prodList.put("AUTOSYNC", gettingsync);
/*  790 */             prodList.put("IMPALL", impall + "");
/*  791 */             DBUtil.site24x7Accounts.put(pid + "", prodList);
/*      */             
/*      */ 
/*  794 */             Site24x7IntegUpdate.startDataCollection(pid + "", true, true, impAll);
/*      */           }
/*      */           catch (Exception e) {
/*  797 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*  800 */             AMConnectionPool.closeStatement(set);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  805 */         Constants.setExtDeviceConfigured(true);
/*  806 */         request.setAttribute("SuccessMessage", FormatUtil.getString("am.opstor.saved.success"));
/*  807 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.opstor.saved.success"));
/*  808 */         return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */       }
/*  810 */       request.setAttribute("ErrorMessage", errMsg);
/*  811 */       request.setAttribute("productName", productName);
/*  812 */       return mapping.findForward("AddNewExtDevice");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  819 */       if ((Constants.isIt360) && (productName.equals("OpStor")))
/*      */       {
/*      */         try
/*      */         {
/*  823 */           if ((host != null) && (!host.trim().equals("")) && (host.equalsIgnoreCase("localhost")))
/*      */           {
/*  825 */             host = InetAddress.getLocalHost().getHostName();
/*  826 */             AMLog.debug("Replacing localhost to hostname even if the customer enters as localhost in the Storage Configuration");
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  831 */           AMLog.debug("Not able to replace the localhost to hostname " + host);
/*      */         }
/*      */       }
/*      */       
/*  835 */       if (DBUtil.getGlobalConfigValueasBoolean("useproxy"))
/*      */       {
/*  837 */         String previousbypasshosts = DBUtil.hasGlobalConfigValue("bypassproxyaddress") ? DBUtil.getGlobalConfigValue("bypassproxyaddress") : "";
/*      */         
/*  839 */         StringBuilder sb = new StringBuilder(previousbypasshosts);
/*  840 */         if (!previousbypasshosts.isEmpty())
/*      */         {
/*  842 */           sb.append(";");
/*      */         }
/*  844 */         String dontProxyAddress = sb.toString();
/*  845 */         if (!previousbypasshosts.contains(host))
/*      */         {
/*  847 */           dontProxyAddress = host;
/*  848 */           DBUtil.insertOrUpdateGlobalConfigValue("bypassproxyaddress", dontProxyAddress);
/*      */         }
/*  850 */         AMLog.debug("ExtDeviceActions dontProxyAddress:" + dontProxyAddress);
/*  851 */         ProxyUtil.doByPassProxy(dontProxyAddress);
/*  852 */         AMLog.debug("DoByPassProxy Successfully");
/*      */       }
/*      */       
/*  855 */       String urlToContact = protocol + "://" + host + ":" + port + "/servlet/APMExtAlertsInteg";
/*  856 */       String nmsJSessionId = null;
/*  857 */       if (productName.equals("Facilities"))
/*      */       {
/*  859 */         urlToContact = protocol + "://" + host + ":" + port + "/servlets/GetChallengeServlet";
/*  860 */         nmsJSessionId = ExtProdUtil.authenticateNMS(userName, password, host, protocol + "://" + host + ":" + port);
/*  861 */         AMLog.debug("nmsJSessionId ==>  : " + nmsJSessionId);
/*  862 */         if (nmsJSessionId != null)
/*      */         {
/*  864 */           request.getSession().setAttribute("nmssessionid", nmsJSessionId);
/*  865 */           URL urlTemp = new URL(protocol + "://" + host + ":" + port + "/mainLayout.do;jsessionid=" + nmsJSessionId);
/*  866 */           HttpURLConnection connection = (HttpURLConnection)urlTemp.openConnection();
/*  867 */           connection.connect();
/*  868 */           InputStreamReader isr = new InputStreamReader(connection.getInputStream());
/*  869 */           BufferedReader br = new BufferedReader(isr);
/*  870 */           String htmlText = "";
/*  871 */           String nextLine = "";
/*  872 */           while ((nextLine = br.readLine()) != null)
/*      */           {
/*  874 */             htmlText = htmlText + nextLine;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  879 */       if (Constants.isIt360)
/*      */       {
/*  881 */         ProdIntegThread.setRequest(true);
/*      */       }
/*  883 */       String errorMessage = null;
/*  884 */       if (!productName.equals("Facilities"))
/*      */       {
/*  886 */         if (productName.equals("OpManager"))
/*      */         {
/*  888 */           ExtProdUtil.setImportAllDevicesFromOPM(addDeviceDefault);
/*      */           
/*  890 */           AMActionForm amform = new AMActionForm();
/*  891 */           amform.setAddDeviceDefault(addDeviceDefault);
/*      */         }
/*  893 */         errorMessage = ProdIntegThread.getXMLFromRemoteProductAndInsert(protocol, host, port, productName, urlToContact, checkBox, userName, password, byPassAuth, pollingInterval, prodId, true);
/*      */       }
/*  895 */       System.out.println("url to contact for opmanager==>" + urlToContact);
/*  896 */       if (errorMessage != null)
/*      */       {
/*  898 */         request.setAttribute("ErrorMessage", errorMessage);
/*  899 */         request.setAttribute("productName", productName);
/*  900 */         return mapping.findForward("AddNewExtDevice");
/*      */       }
/*  902 */       if ((productName.equals("Facilities")) && (nmsJSessionId == null))
/*      */       {
/*  904 */         errorMessage = FormatUtil.getString("am.facilities.unauthorised.message");
/*  905 */         request.setAttribute("ErrorMessage", errorMessage);
/*  906 */         request.setAttribute("productName", productName);
/*  907 */         return mapping.findForward("AddNewExtDevice");
/*      */       }
/*      */       
/*      */ 
/*  911 */       String selectQuery = "select ID from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + productName + "'";
/*  912 */       ResultSet set = AMConnectionPool.executeQueryStmt(selectQuery);
/*  913 */       if (set.next())
/*      */       {
/*  915 */         int masId = EnterpriseUtil.getManagedServerIndex();
/*  916 */         String updateAddOnProducts = "update AM_INTEGRATEDPRODUCTS set PRODUCT_NAME='" + productName + "', PROTOCOL='" + protocol + "', HOST ='" + host + "', PORT ='" + port + "' , URL ='" + urlToContact + "', STATUS='" + checkBox + "', USERNAME='" + userName + "',PASSWORD=" + DBQueryUtil.encode(password, Constants.EXTPRODENCODEKEY) + ",POLLINGINTERVAL='" + pollingInterval + "',BYPASSAUTH='" + byPassAuth + "',IS_REMOTE='" + isRemote + "' where  MASID =" + masId + " and ID=" + set.getInt("ID");
/*  917 */         AMConnectionPool.executeUpdateStmt(updateAddOnProducts);
/*      */       }
/*      */       else
/*      */       {
/*  921 */         int masId = EnterpriseUtil.getManagedServerIndex();
/*  922 */         String insertAddOnProductQuery = "INSERT INTO  AM_INTEGRATEDPRODUCTS ( ID ,PRODUCT_NAME ,PROTOCOL ,HOST ,PORT ,STATUS ,URL ,USERNAME ,PASSWORD ,POLLINGINTERVAL ,BYPASSAUTH ,MASID, IS_REMOTE ) values(" + DBQueryUtil.getIncrementedID("ID", "AM_INTEGRATEDPRODUCTS") + ",'" + productName + "','" + protocol + "','" + host + "','" + port + "','" + checkBox + "','" + urlToContact + "','" + userName + "'," + DBQueryUtil.encode(password, Constants.EXTPRODENCODEKEY) + ",'" + pollingInterval + "','" + byPassAuth + "','" + masId + "','" + isRemote + "') ";
/*  923 */         AMConnectionPool.executeUpdateStmt(insertAddOnProductQuery);
/*      */       }
/*  925 */       AMConnectionPool.closeStatement(set);
/*  926 */       Constants.setExtDeviceConfigured(true);
/*  927 */       request.setAttribute("SuccessMessage", FormatUtil.getString("am.opstor.saved.success"));
/*  928 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.opstor.saved.success"));
/*  929 */       String statusMsg; if ((Constants.isIt360) && (productName.equals("OpStor")))
/*      */       {
/*      */ 
/*  932 */         statusMsg = updateOpStorSettingInConsole(protocol, host, port, isRemote);
/*      */       }
/*  934 */       if (productName.equals("OpManager"))
/*      */       {
/*  936 */         if (ExtConnectorUtil.isPushEnabled)
/*      */         {
/*  938 */           AMLog.debug("OPMEventQueue initialized on saving add-on config");
/*  939 */           AMLog.debug("ExtDeviceActions opmConnFullUpdateInterval:" + ExtProdUtil.opmConnectorFullUpdateInterval);
/*  940 */           AMLog.debug("ExtDeviceActions opmConnAddDev:" + ExtProdUtil.opmImportAllDevices);
/*  941 */           ExtConnectorUtil.enableOPMSync = (checkBox != null) && (checkBox.equals("true"));
/*  942 */           OPMEventQueue.initQueueAndTasks();
/*  943 */           OPMEventUpdateTask.proceedProcessingQ = true;
/*  944 */           OPMEventQueueCopyTask.scheduleTask();
/*  945 */           OPMEventUpdateTask.scheduleTask(100);
/*      */         }
/*      */         
/*  948 */         ExtProdUtil.updateOPMAPIKey();
/*      */       }
/*      */       
/*  951 */       return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  956 */       request.setAttribute("ErrorMessage", FormatUtil.getString("Could not Save Details."));
/*  957 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Could not Save Details."));
/*  958 */       e.printStackTrace();
/*      */       
/*  960 */       saveMessages(request, messages); }
/*  961 */     return mapping.findForward("AddNewExtDevice");
/*      */   }
/*      */   
/*      */   private static String updateOpStorSettingInConsole(String protocol, String host, String port, String isRemote)
/*      */   {
/*  966 */     String iamAgentUrl = ("true".equals(System.getProperty("server.secure")) ? "https://" : "http://") + System.getProperty("iam.server") + System.getProperty("context.path");
/*      */     
/*      */     try
/*      */     {
/*  970 */       String url = iamAgentUrl + "/servlet/AddOnProductInteg";
/*      */       
/*  972 */       GetMethod get = new GetMethod(url);
/*  973 */       get.setQueryString("PROTOCOL=" + protocol + "&HOSTNAME=" + host + "&PORT_NUMBER=" + port + "&IS_REMOTE=" + isRemote);
/*  974 */       int rspCode = ConcurrentHttpClient.getHttpClient().executeMethod(get);
/*  975 */       if (rspCode == 200)
/*      */       {
/*      */ 
/*  978 */         return "SUCCESS";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  983 */       ex.printStackTrace();
/*      */     }
/*  985 */     return "FAILED";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward editExtDevicePollInterval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  993 */     AMActionForm amform = (AMActionForm)form;
/*  994 */     ActionMessages messages = new ActionMessages();
/*  995 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     
/*      */     try
/*      */     {
/*  999 */       String port = request.getParameter("port");
/* 1000 */       Constants.setExtProdPollInt(Integer.parseInt(port));
/* 1001 */       String updateQuery = "update AM_GLOBALCONFIG set VALUE='" + port + "' where NAME='EXTPROD.SYNC.POLLINTERVAL'";
/* 1002 */       AMConnectionPool.executeUpdateStmt(updateQuery);
/* 1003 */       amform.setPort(port);
/* 1004 */       request.setAttribute("SuccessMessage", FormatUtil.getString("am.ext.pollinterval.updated.text"));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1008 */       e.printStackTrace();
/* 1009 */       request.setAttribute("MessageToDisplay", FormatUtil.getString("Could not update Poll Interval."));
/*      */     }
/*      */     
/* 1012 */     int synchHr = 12;
/* 1013 */     int synchMin = 0;
/*      */     
/* 1015 */     String incomingSyncHr = request.getParameter("synchHr");
/* 1016 */     if (incomingSyncHr.equals(""))
/*      */     {
/* 1018 */       incomingSyncHr = "0";
/*      */     }
/* 1020 */     synchHr = Integer.parseInt(incomingSyncHr);
/* 1021 */     synchMin = Integer.parseInt(request.getParameter("synchMin"));
/*      */     
/* 1023 */     amform.setSynchHr(synchHr);
/* 1024 */     amform.setSynchMin(synchMin);
/*      */     
/* 1026 */     long synchMillis = (synchHr * 60 + synchMin) * 60 * 1000;
/* 1027 */     ExtProdUtil.setOpmConnFullUpdateInterval(synchMillis);
/*      */     
/* 1029 */     return new ActionForward("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward addExtIntegResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1037 */     boolean goBackToMG = false;
/*      */     try {
/* 1039 */       String haid = request.getParameter("haid");
/* 1040 */       ManagedApplication mo = new ManagedApplication();
/* 1041 */       Hashtable toNotifier = new Hashtable();
/* 1042 */       toNotifier.put("MGID", mo.getTOPLevelMG(haid));
/* 1043 */       toNotifier.put("subGrpMGID", haid);
/* 1044 */       if (!isTokenValid(request))
/*      */       {
/* 1046 */         return mapping.findForward("showapplication");
/*      */       }
/*      */       
/*      */ 
/* 1050 */       resetToken(request);
/*      */       
/* 1052 */       if ((request.getParameter("goback") != null) && (request.getParameter("goback").equals("true")))
/*      */       {
/* 1054 */         AMLog.debug("MonitorGroup Association: Going back to MG details: " + request.getParameter("goback"));
/* 1055 */         goBackToMG = true;
/*      */       }
/* 1057 */       String applicationName = request.getParameter("applicationname");
/* 1058 */       String[] resources = request.getParameterValues("selectedExternalresource");
/* 1059 */       if (resources == null)
/*      */       {
/* 1061 */         resources = request.getParameterValues("selectedStorageresource");
/*      */       }
/* 1063 */       if (resources != null)
/*      */       {
/* 1065 */         for (String resName : resources)
/*      */         {
/* 1067 */           String[] arr = resName.split("\\$");
/* 1068 */           resName = arr[0];
/* 1069 */           String prodId = arr[1];
/* 1070 */           String category = arr[2];
/* 1071 */           if (!EnterpriseUtil.isAdminServer()) {
/*      */             try
/*      */             {
/* 1074 */               ExtProdUtil.addAndAssociateDevices(resName, haid, request, prodId, null, category);
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 1078 */               ex.printStackTrace();
/*      */             }
/*      */             
/*      */           } else {
/*      */             try
/*      */             {
/* 1084 */               ExtProdUtil.insertParentChild(haid, String.valueOf(resName));
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 1088 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1094 */         String mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/* 1095 */         String deviceId = "-1";
/* 1096 */         if ((mapViewId != null) && (resources != null) && (resources.length > 0))
/*      */         {
/* 1098 */           ArrayList devNameArray = new ArrayList();
/* 1099 */           for (String deviceName : resources)
/*      */           {
/* 1101 */             String[] arr = deviceName.split("\\$");
/* 1102 */             deviceName = arr[0];
/* 1103 */             if (!deviceName.startsWith("IF-"))
/*      */             {
/* 1105 */               deviceId = DBUtil.getResourceIdForResourceName(deviceName);
/*      */               
/* 1107 */               MapViewUtil.addDevice(mapViewId, deviceId);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1115 */           toNotifier.put("EventType", "Updated");
/* 1116 */           MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/* 1117 */           notifyConsole.setProperties(toNotifier);
/* 1118 */           Thread t = new Thread(notifyConsole);
/* 1119 */           t.start();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1123 */           e.printStackTrace();
/*      */         }
/* 1125 */         ExtProdUtil.callApplyRCAForThisMG(haid);
/* 1126 */         AMAvailabilityEventListener.load_attributes();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1131 */       ex.printStackTrace();
/*      */     }
/* 1133 */     if (goBackToMG)
/*      */     {
/* 1135 */       return new ActionForward("/showapplication.do?haid=" + request.getParameter("haid") + "&method=showApplication");
/*      */     }
/* 1137 */     return new ActionForward("/showresource.do?type=All&method=getMonitorForm&haid=" + request.getParameter("haid"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward saveOpStorDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1146 */     String devicesString = request.getParameter("devices");
/* 1147 */     String host = request.getParameter("host");
/* 1148 */     String port = request.getParameter("port");
/* 1149 */     String protocol = request.getParameter("protocol");
/* 1150 */     JSONObject jsonObject = new JSONObject(devicesString);
/* 1151 */     HashMap<String, Properties> devicesMap = new HashMap();
/* 1152 */     JSONArray deviceArray = jsonObject.getJSONArray("deviceArray");
/* 1153 */     for (int i = 0; i < deviceArray.length(); i++)
/*      */     {
/* 1155 */       Properties deviceInfoProps = new Properties();
/* 1156 */       JSONObject devObject = (JSONObject)deviceArray.get(i);
/* 1157 */       String deviceName = devObject.getString("name");
/* 1158 */       String custName = devObject.getString("custName");
/* 1159 */       String siteName = devObject.getString("siteName");
/* 1160 */       deviceInfoProps.put("custName", custName);
/* 1161 */       deviceInfoProps.put("siteName", siteName);
/* 1162 */       devicesMap.put(deviceName, deviceInfoProps);
/*      */     }
/* 1164 */     CustomerManagementAPI.saveDeviceMapping(devicesMap, host, port, protocol);
/* 1165 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addSite24x7Resource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1174 */     boolean goBackToMG = false;
/*      */     try {
/* 1176 */       String haid = request.getParameter("haid");
/* 1177 */       ManagedApplication mo = new ManagedApplication();
/* 1178 */       Hashtable toNotifier = new Hashtable();
/* 1179 */       toNotifier.put("MGID", mo.getTOPLevelMG(haid));
/* 1180 */       toNotifier.put("subGrpMGID", haid);
/* 1181 */       if (!isTokenValid(request)) {
/* 1182 */         return mapping.findForward("showapplication");
/*      */       }
/* 1184 */       resetToken(request);
/*      */       
/* 1186 */       if ((request.getParameter("goback") != null) && (request.getParameter("goback").equals("true"))) {
/* 1187 */         AMLog.debug("MonitorGroup Association: Going back to MG details: " + request.getParameter("goback"));
/* 1188 */         goBackToMG = true;
/*      */       }
/*      */       
/* 1191 */       String[] resources = request.getParameterValues("selectedSite24x7resource");
/* 1192 */       if (resources != null) {
/* 1193 */         for (String resName : resources)
/*      */         {
/* 1195 */           ExtProdUtil.addAndAssociateSite24x7Monitors(resName, haid);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1200 */         String mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/* 1201 */         String deviceId = "-1";
/* 1202 */         if ((mapViewId != null) && (resources != null) && (resources.length > 0))
/*      */         {
/* 1204 */           ArrayList devNameArray = new ArrayList();
/*      */           
/* 1206 */           for (String deviceName : resources)
/*      */           {
/*      */ 
/*      */ 
/* 1210 */             if (!deviceName.startsWith("IF-"))
/*      */             {
/* 1212 */               deviceId = DBUtil.getResourceIdForResourceName(deviceName);
/* 1213 */               MapViewUtil.addDevice(mapViewId, deviceId);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 1220 */           toNotifier.put("EventType", "Updated");
/* 1221 */           MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/* 1222 */           notifyConsole.setProperties(toNotifier);
/* 1223 */           Thread t = new Thread(notifyConsole);
/* 1224 */           t.start();
/*      */         }
/*      */         catch (Exception e) {
/* 1227 */           e.printStackTrace();
/*      */         }
/* 1229 */         ExtProdUtil.callApplyRCAForThisMG(haid);
/* 1230 */         AMAvailabilityEventListener.load_attributes();
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1234 */       ex.printStackTrace();
/*      */     }
/* 1236 */     if (goBackToMG) {
/* 1237 */       return new ActionForward("/showapplication.do?haid=" + request.getParameter("haid") + "&method=showApplication");
/*      */     }
/* 1239 */     return new ActionForward("/showresource.do?type=All&method=getMonitorForm&haid=" + request.getParameter("haid"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward site24x7Reports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1248 */     response.setContentType("text/html; charset=UTF-8");
/* 1249 */     String resourceid = request.getParameter("resourceid");
/*      */     try {
/* 1251 */       request.setAttribute("resourceid", resourceid);
/*      */     } catch (Exception e) {
/* 1253 */       e.printStackTrace();
/*      */     }
/* 1255 */     return mapping.findForward("site24x7Reports");
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ExtDeviceActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */