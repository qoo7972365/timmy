/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.struts.actions.AdminActions;
/*      */ import com.adventnet.appmanager.struts.actions.CreateScriptMonitor;
/*      */ import com.adventnet.appmanager.struts.actions.HAIDManagerAction;
/*      */ import com.adventnet.appmanager.struts.actions.NewMonitorConf;
/*      */ import com.adventnet.appmanager.struts.actions.WMIPerfAction;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.struts.rbmmonitor.CreateRBMMonitor;
/*      */ import com.adventnet.appmanager.struts.urlmonitor.CreateUrlMonitor;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import java.io.File;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONObject;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ public class AddMonitorAPIUtil
/*      */ {
/*      */   public static String addNewMonitor(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*   53 */     response.setContentType("text/xml; charset=UTF-8");
/*   54 */     AMLog.debug("REST API : inside AddMonitor");
/*   55 */     String xmlString = "";
/*   56 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*   57 */     MSreq.setContentType("text/xml; charset=UTF-8");
/*   58 */     String postQueryStr = "";
/*   59 */     String ParameterNames = "";
/*   60 */     String montype = "";
/*   61 */     String monType = null;
/*   62 */     int pollInterval = 5;
/*   63 */     int timeOut = 5;
/*   64 */     boolean isConfMonitor = false;
/*   65 */     AdminActions am = new AdminActions();
/*   66 */     AMActionForm amform = new AMActionForm();
/*   67 */     MSreq.addParameter("fromRESTAPI", "true");
/*   68 */     for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */     {
/*   70 */       ParameterNames = (String)e.nextElement();
/*      */       
/*   72 */       if ((ParameterNames.equals("type")) || (ParameterNames.equals("runOnServer")) || (ParameterNames.equals("version")) || (ParameterNames.equals("logonClient")) || (ParameterNames.equals("language")) || (ParameterNames.equals("method")) || (ParameterNames.equals("exchangeservice")) || (ParameterNames.equals("addtoha")) || (ParameterNames.equals("haid")))
/*      */       {
/*   74 */         AMLog.debug("REST API : DO NOTHING for parameter name " + ParameterNames);
/*      */       }
/*      */       else
/*      */       {
/*   78 */         MSreq.addParameter(ParameterNames, request.getParameter(ParameterNames));
/*      */       }
/*      */     }
/*   81 */     if ((request.getParameter("pollInterval") == null) || (request.getParameter("pollInterval").equals("")))
/*      */     {
/*   83 */       MSreq.addParameter("pollinterval", "5");
/*   84 */       MSreq.addParameter("pollInterval", "5");
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*   90 */         pollInterval = Integer.parseInt(request.getParameter("pollInterval"));
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*   94 */         AMLog.debug("REST API : The pollInterval should be a valid whole number.");
/*   95 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pollinter.notvalidmessage"), "4201");
/*      */       }
/*      */       
/*   98 */       MSreq.addParameter("pollinterval", request.getParameter("pollInterval"));
/*      */     }
/*  100 */     if ((request.getParameter("type") != null) && (!request.getParameter("type").equals("")))
/*      */     {
/*  102 */       montype = request.getParameter("type");
/*      */     }
/*      */     else
/*      */     {
/*  106 */       AMLog.debug("REST API : The type should not be empty.");
/*  107 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.emptymessage"), "4202");
/*  108 */       return xmlString;
/*      */     }
/*  110 */     if ((Constants.isPrivilegedUserFromAPIKey(request.getParameter("apikey"))) && (request.getParameter("groupID") == null) && (request.getParameter("haid") == null))
/*      */     {
/*  112 */       AMLog.debug("REST API : For Delegated ADMINs groupID is mandatory");
/*  113 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.addmon.delegatedAdmin.mgmandatory.check"), "4580");
/*  114 */       return xmlString;
/*      */     }
/*  116 */     String credentialid = null;
/*  117 */     if ((request.getParameter("credentialID") != null) && (!"".equals(request.getParameter("credentialID").trim())) && (!"No Credentials".equals(request.getParameter("credentialID").trim()))) {
/*      */       try {
/*  119 */         credentialid = request.getParameter("credentialID");
/*  120 */         MSreq.addParameter("credentialID", credentialid);
/*  121 */         MSreq.addParameter("CredentialDetails", "cm");
/*  122 */         List credIDList = CredentialManagerUtil.getInstance().getCredentialIDs();
/*  123 */         if (!credIDList.contains(Integer.valueOf(Integer.parseInt(credentialid)))) {
/*  124 */           AMLog.debug("REST API : credentialID : " + credentialid + " does not exist");
/*  125 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.idnotexist"), "5004");
/*      */         }
/*      */       } catch (Exception e) {
/*  128 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  132 */     if (credentialid != null)
/*      */     {
/*  134 */       String modeSelected = request.getParameter("mode");
/*  135 */       if ((modeSelected != null) && (!modeSelected.equals("")))
/*      */       {
/*  137 */         if (modeSelected.equalsIgnoreCase("telnet"))
/*      */         {
/*  139 */           MSreq.addParameter("TelnetCredentialDetails", "cmtelnet");
/*  140 */           MSreq.addParameter("cmTelnetValue", credentialid);
/*      */         }
/*  142 */         if (modeSelected.equalsIgnoreCase("ssh"))
/*      */         {
/*  144 */           MSreq.addParameter("SSHCredentialDetails", "cmssh");
/*  145 */           MSreq.addParameter("cmSSHValue", credentialid);
/*      */         }
/*  147 */         if (modeSelected.equalsIgnoreCase("snmp"))
/*      */         {
/*  149 */           MSreq.addParameter("SNMPCredentialDetails", "cmsnmp");
/*  150 */           MSreq.addParameter("cmSNMPValue", credentialid);
/*      */         }
/*  152 */         if (modeSelected.equalsIgnoreCase("wmi"))
/*      */         {
/*  154 */           MSreq.addParameter("WMICredentialDetails", "cmwmi");
/*  155 */           MSreq.addParameter("cmWMIValue", credentialid);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  160 */         MSreq.addParameter("cmValue", credentialid);
/*      */       }
/*      */     }
/*  163 */     if ((request.getParameter("prompt") == null) || (request.getParameter("prompt").equals("")))
/*      */     {
/*  165 */       MSreq.addParameter("prompt", "$");
/*      */     }
/*  167 */     if (((request.getParameter("groupID") != null) && (!request.getParameter("groupID").trim().equals(""))) || ((request.getParameter("haid") == null) || (request.getParameter("haid").trim().equals("")) || ("false".equalsIgnoreCase(request.getParameter("addToGroup")))))
/*      */     {
/*  169 */       MSreq.addParameter("addtoha", "false");
/*  170 */       MSreq.addParameter("haid", "-");
/*      */     }
/*      */     else
/*      */     {
/*  174 */       boolean flag = false;
/*  175 */       String monitorGroups = request.getParameter("groupID") != null ? request.getParameter("groupID") : request.getParameter("haid");
/*  176 */       StringTokenizer strToken = new StringTokenizer(monitorGroups, ",");
/*  177 */       while (strToken.hasMoreTokens()) {
/*  178 */         int mgID = 0;
/*      */         try
/*      */         {
/*  181 */           mgID = Integer.parseInt(strToken.nextToken());
/*  182 */           if (CommonAPIUtil.checkResourceidforDelegatedAdmin(request, String.valueOf(mgID)))
/*      */           {
/*  184 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4579");
/*      */           }
/*      */           
/*  187 */           if ((mgID < Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId())) || (mgID > EnterpriseUtil.getDistributedEndResourceId())) {
/*      */             continue;
/*      */           }
/*      */         } catch (Exception ex) {
/*  191 */           AMLog.debug("REST API : The groupID should be a valid one.");
/*  192 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.groupid.notvalidmessage"), "4203");
/*      */         }
/*      */         
/*  195 */         if (checkMGID(mgID))
/*      */         {
/*  197 */           flag = true;
/*      */           
/*  199 */           MSreq.addParameter("haid", String.valueOf(mgID));
/*  200 */           amform.setHaid(String.valueOf(mgID));
/*      */         }
/*      */         else
/*      */         {
/*  204 */           AMLog.debug("REST API : The groupID should be a valid one.");
/*  205 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.groupid.notvalidmessage"), "4203");
/*  206 */           return xmlString;
/*      */         }
/*      */       }
/*  209 */       MSreq.addParameter("addtoha", String.valueOf(flag));
/*  210 */       if (!flag) {
/*  211 */         MSreq.addParameter("haid", "-");
/*      */       }
/*      */     }
/*      */     
/*  215 */     if ((request.getParameter("subnet") == null) || (request.getParameter("subnet").equals("")))
/*      */     {
/*  217 */       amform.setnetmask("255.255.255.0");
/*  218 */       MSreq.addParameter("netmask", "255.255.255.0");
/*      */     }
/*      */     else
/*      */     {
/*  222 */       amform.setnetmask(request.getParameter("subnet"));
/*  223 */       MSreq.addParameter("netmask", request.getParameter("subnet"));
/*      */     }
/*  225 */     if ((request.getParameter("resolvedns") != null) && (request.getParameter("resolvedns").equalsIgnoreCase("false"))) {
/*  226 */       amform.setResolveDNS(true);
/*      */     }
/*      */     
/*  229 */     if (!com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isallowed()) {
/*  230 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("registered.monitor.restriction2"), "4444");
/*  231 */       AMLog.debug("REST API : Error in adding " + montype + " monitor by API as number of monitors exceed license");
/*  232 */       return xmlString;
/*      */     }
/*      */     
/*  235 */     if (montype.equalsIgnoreCase("MS SQL"))
/*      */     {
/*  237 */       monType = "MSSQLDB:1433";
/*      */     }
/*  239 */     else if (montype.equalsIgnoreCase("DB2"))
/*      */     {
/*  241 */       monType = "DB2:50000";
/*      */     }
/*  243 */     else if (montype.equalsIgnoreCase("MySQL"))
/*      */     {
/*  245 */       monType = "MYSQLDB:3306";
/*      */     }
/*  247 */     else if (montype.equalsIgnoreCase("Sybase"))
/*      */     {
/*  249 */       monType = "SYBASEDB:5000";
/*  250 */       if ((!"yes".equalsIgnoreCase(request.getParameter("jconnect"))) && (!"no".equalsIgnoreCase(request.getParameter("jconnect"))))
/*      */       {
/*  252 */         AMLog.debug("REST API : The JConnect mentioned in the request URL should be yes or no.");
/*  253 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jconnect.notemptymessage"), "4209");
/*  254 */         return xmlString;
/*      */       }
/*      */     }
/*  257 */     else if (montype.equalsIgnoreCase("Apache Server"))
/*      */     {
/*  259 */       monType = "APACHE:80";
/*      */     }
/*  261 */     else if (montype.equalsIgnoreCase("IIS Server"))
/*      */     {
/*  263 */       monType = "IIS:80";
/*      */     }
/*  265 */     else if (montype.equalsIgnoreCase("PHP"))
/*      */     {
/*  267 */       monType = "PHP:80";
/*      */     }
/*  269 */     else if (montype.equalsIgnoreCase("Web Server"))
/*      */     {
/*  271 */       monType = "WEB:80";
/*      */     }
/*  273 */     else if (montype.equalsIgnoreCase("Ingres"))
/*      */     {
/*  275 */       monType = "Ingres";
/*      */     }
/*  277 */     else if (montype.equalsIgnoreCase("SNMP/Network Device"))
/*      */     {
/*  279 */       monType = "SNMP:161";
/*      */     }
/*  281 */     else if (montype.equalsIgnoreCase("JMX Applications"))
/*      */     {
/*  283 */       monType = "JMX1.2-MX4J-RMI:1099";
/*      */     }
/*  285 */     else if (montype.equalsIgnoreCase("WEBLOGIC SERVER"))
/*      */     {
/*  287 */       monType = "WEBLOGIC:7001";
/*      */     }
/*  289 */     else if (montype.equalsIgnoreCase("Tomcat server"))
/*      */     {
/*  291 */       monType = "Tomcat-server";
/*  292 */       isConfMonitor = true;
/*      */     }
/*  294 */     else if (montype.equalsIgnoreCase("Oracle Application Server"))
/*      */     {
/*  296 */       monType = "ORACLEAS:7200";
/*      */     }
/*  298 */     else if (montype.equalsIgnoreCase("Web Service"))
/*      */     {
/*  300 */       monType = "Web Service";
/*  301 */       if ((request.getParameter("WSDLUrl") == null) || (request.getParameter("WSDLUrl").equals("")))
/*      */       {
/*  303 */         AMLog.debug("REST API : The WSDLUrl should not be empty.");
/*  304 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.wsdlurl.notemptymessage"), "4204");
/*  305 */         return xmlString;
/*      */       }
/*      */     }
/*  308 */     else if (montype.equalsIgnoreCase("Java Runtime"))
/*      */     {
/*  310 */       monType = "JDK1.5:1099";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  319 */     else if (montype.equalsIgnoreCase("WebLogic Integration"))
/*      */     {
/*  321 */       monType = "WLI:7001";
/*      */     }
/*  323 */     else if (montype.equalsIgnoreCase("SAP Server"))
/*      */     {
/*  325 */       monType = "SAP:00";
/*      */     }
/*  327 */     else if (montype.equalsIgnoreCase("SAP CCMS"))
/*      */     {
/*  329 */       monType = "SAP-CCMS";
/*      */     }
/*  331 */     else if (montype.equalsIgnoreCase(".net"))
/*      */     {
/*  333 */       monType = ".Net:9080";
/*      */     }
/*  335 */     else if (montype.equalsIgnoreCase("MSOfficeSharePointServer"))
/*      */     {
/*  337 */       monType = "OfficeSharePointServer";
/*  338 */       isConfMonitor = true;
/*      */     }
/*  340 */     else if (montype.equalsIgnoreCase("Hyper-V Server"))
/*      */     {
/*  342 */       monType = "Hyper-V-Server";
/*  343 */       isConfMonitor = true;
/*      */     }
/*  345 */     else if (montype.equalsIgnoreCase("Windows Performance Counters"))
/*      */     {
/*  347 */       monType = "Generic WMI";
/*      */     }
/*  349 */     else if ((montype.equalsIgnoreCase("Exchange Server")) || (montype.equalsIgnoreCase("Exchange-server")))
/*      */     {
/*  351 */       montype = "Exchange-server";
/*  352 */       monType = "Exchange-server";
/*  353 */       isConfMonitor = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  358 */     else if (montype.equalsIgnoreCase("rbm"))
/*      */     {
/*  360 */       monType = "RBM";
/*  361 */       if ((request.getParameter("rbmagentID") != null) && (request.getParameter("rbmagentID").equals("")))
/*      */       {
/*  363 */         AMLog.debug("REST API : The rbmagentID mentioned in the request URL should not be empty.");
/*  364 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.rbmagentid.notemptymessage"), "4208");
/*  365 */         return xmlString;
/*      */       }
/*      */       
/*  368 */       String res = checkEUMAgent(request, response, MSreq, amform, monType);
/*  369 */       if (!res.equalsIgnoreCase("success"))
/*      */       {
/*  371 */         return res;
/*      */       }
/*      */       
/*  374 */       if ((request.getParameter("scriptname") == null) || (request.getParameter("scriptname").equals("")))
/*      */       {
/*  376 */         AMLog.debug("REST API : The scriptname mentioned in the request URL should not be empty.");
/*  377 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.script.notemptymessage"), "4210");
/*  378 */         return xmlString;
/*      */       }
/*      */     }
/*  381 */     else if (montype.equalsIgnoreCase("Servers"))
/*      */     {
/*      */ 
/*  384 */       if ((request.getParameter("os") != null) && ((request.getParameter("os").equals("AIX")) || (request.getParameter("os").equals("AS400/iSeries")) || (request.getParameter("os").equals("FreeBSD")) || (request.getParameter("os").equals("HP-UX")) || (request.getParameter("os").equals("Linux")) || (request.getParameter("os").equals("Mac OS")) || (request.getParameter("os").equals("Novell")) || (request.getParameter("os").equals("SUN")) || (request.getParameter("os").toLowerCase().indexOf("windows") != -1) || (request.getParameter("os").equals("ToSelectValue"))))
/*      */       {
/*  386 */         monType = "SYSTEM:9999";
/*      */       }
/*      */       else
/*      */       {
/*  390 */         AMLog.debug("REST API : Invalid OS type.");
/*  391 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.ostype.notvalidmessage"), "4212");
/*  392 */         return xmlString;
/*      */       }
/*  394 */       if ((request.getParameter("os").equals("AIX")) || (request.getParameter("os").equals("FreeBSD")) || (request.getParameter("os").equals("HP-UX")) || (request.getParameter("os").equals("Linux")) || (request.getParameter("os").equals("Mac OS")) || (request.getParameter("os").equals("Novell")) || (request.getParameter("os").equals("SUN")) || (request.getParameter("os").equals("Windows 2000")) || (request.getParameter("os").equals("Windows 2003")) || (request.getParameter("os").equals("Windows XP")) || (request.getParameter("os").equals("WindowsNT")) || (request.getParameter("os").equals("Windows Vista")) || (request.getParameter("os").equals("Windows 2008")) || (request.getParameter("os").equals("Windows 7")) || (request.getParameter("os").equals("Windows 8")) || (request.getParameter("os").equals("Windows 10")) || (request.getParameter("os").equals("Windows 2012")))
/*      */       {
/*  396 */         if ((request.getParameter("mode") != null) && ((request.getParameter("mode").equals("SNMP")) || (request.getParameter("mode").equals("TELNET")) || (request.getParameter("mode").equals("SSH")) || (request.getParameter("mode").equals("WMI"))))
/*      */         {
/*  398 */           AMLog.debug("REST API : ");
/*      */         }
/*      */         else
/*      */         {
/*  402 */           AMLog.debug("REST API : The mode should be any one among SSH/TELNET/SNMP/WMI");
/*  403 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mode.notvalidmessage"), "4213");
/*  404 */           return xmlString;
/*      */         }
/*      */         try
/*      */         {
/*  408 */           if ((request.getParameter("mode").equals("WMI")) || (request.getParameter("mode").equals("SNMP"))) {
/*  409 */             MSreq.addParameter("snmptelnetport", "23");
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*      */ 
/*  418 */           AMLog.debug("REST API : The snmptelnetport should be a valid one.");
/*  419 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.snmpport.notvalidmessage"), "4214");
/*      */         }
/*      */         
/*  422 */         if ((request.getParameter("mode").equals("SSH")) && (credentialid == null))
/*      */         {
/*  424 */           if ((request.getParameter("sshPKAuth") != null) && (request.getParameter("sshPKAuth").equals("on")) && (request.getParameter("username") == null))
/*      */           {
/*  426 */             AMLog.debug("REST API : The username mentioned in the request URL should not be empty.");
/*  427 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usr.notemptymessage"), "4219");
/*  428 */             return xmlString;
/*      */           }
/*  430 */           if ((request.getParameter("description") != null) && (!request.getParameter("description").equals("")))
/*      */           {
/*  432 */             MSreq.addParameter("sshPKAuth", request.getParameter("Yes"));
/*  433 */             MSreq.addParameter("description", request.getParameter("description"));
/*  434 */             MSreq.addParameter("passphrase", request.getParameter("passphrase"));
/*      */           }
/*  436 */           else if ((request.getParameter("username") == null) && (request.getParameter("password") == null))
/*      */           {
/*  438 */             AMLog.debug("REST API : The username and password mentioned in the request URL should not be empty.");
/*  439 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usrpwd.notemptymessage"), "4206");
/*  440 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/*  444 */       if (request.getParameter("os").equals("AS400/iSeries"))
/*      */       {
/*  446 */         MSreq.addParameter("snmptelnetport", "23");
/*  447 */         MSreq.addParameter("mode", "TELNET");
/*  448 */         MSreq.addParameter("prompt", "$");
/*      */       } else {
/*  450 */         if (((request.getParameter("mode").equals("SSH")) || (request.getParameter("mode").equals("TELNET"))) && (credentialid == null) && (request.getParameter("sshPKAuth") == null) && ((request.getParameter("username") == null) || (request.getParameter("username").equals("")) || (request.getParameter("password") == null) || (request.getParameter("password").equals(""))))
/*      */         {
/*  452 */           AMLog.debug("REST API : The username and password mentioned in the request URL should not be empty.");
/*  453 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usrpwd.notemptymessage"), "4206");
/*  454 */           return xmlString;
/*      */         }
/*      */         
/*      */ 
/*  458 */         MSreq.addParameter("username", "");
/*  459 */         MSreq.addParameter("password", "");
/*      */       }
/*  461 */       if ((request.getParameter("snmpCommunityString") == null) || (request.getParameter("snmpCommunityString").equals("")))
/*      */       {
/*  463 */         MSreq.addParameter("snmpCommunityString", "public");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  468 */         MSreq.addParameter("snmpCommunityString", request.getParameter("snmpCommunityString"));
/*      */       }
/*      */       
/*      */ 
/*  472 */       if ((request.getParameter("mode") != null) && (request.getParameter("mode").equalsIgnoreCase("WMI"))) {
/*  473 */         String eventlog_status = request.getParameter("eventlog_status");
/*  474 */         if ((eventlog_status != null) && (eventlog_status.equalsIgnoreCase("true"))) {
/*  475 */           amform.setEventlog_status(true);
/*      */         } else {
/*  477 */           amform.setEventlog_status(false);
/*      */         }
/*      */       }
/*      */     }
/*  481 */     else if (montype.equalsIgnoreCase("UrlMonitor"))
/*      */     {
/*  483 */       monType = "UrlMonitor";
/*      */     }
/*  485 */     else if (montype.equalsIgnoreCase("UrlMonitor"))
/*      */     {
/*  487 */       monType = "UrlMonitor";
/*      */     }
/*  489 */     else if ((montype.equalsIgnoreCase("File Monitor")) || (montype.equalsIgnoreCase("Directory Monitor")) || (montype.equalsIgnoreCase("File System Monitor"))) {
/*  490 */       monType = "File System Monitor";
/*  491 */     } else if (montype.equalsIgnoreCase("Script Monitor")) {
/*  492 */       monType = "Script Monitor";
/*  493 */     } else if (montype.equalsIgnoreCase("vCenter")) {
/*  494 */       monType = "vCenter";
/*      */     }
/*  496 */     else if (montype.equalsIgnoreCase("VMwareView")) {
/*  497 */       monType = "VMwareView";
/*      */     }
/*      */     else
/*      */     {
/*  501 */       String tempMonType = com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil.getConfMonitorTypeName(montype);
/*      */       
/*  503 */       AMLog.debug("REST API : type from request: " + request.getParameter("type") + "   Conf type: " + tempMonType);
/*  504 */       if ((tempMonType != null) && (!tempMonType.trim().equals(""))) {
/*  505 */         monType = tempMonType;
/*  506 */         isConfMonitor = true;
/*      */       }
/*      */       else {
/*  509 */         AMLog.debug("REST API : The type mentioned in the request URL is not supported." + request.getParameter("type"));
/*  510 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.notsupportedmessage"), "4215");
/*  511 */         return xmlString;
/*      */       }
/*      */     }
/*  514 */     MSreq.addParameter("type", monType);
/*  515 */     MSreq.addParameter("montype", monType);
/*  516 */     amform.setType(monType);
/*  517 */     if ((request.getParameter("displayname") == null) || (request.getParameter("displayname").equals("")))
/*      */     {
/*      */ 
/*  520 */       AMLog.debug("REST API : The displayname mentioned in the request URL should not be empty.");
/*  521 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.displayname.notemptymessage"), "4211");
/*  522 */       return xmlString;
/*      */     }
/*      */     
/*      */ 
/*  526 */     amform.setDisplayname(request.getParameter("displayname"));
/*      */     try
/*      */     {
/*  529 */       String resid = null;
/*  530 */       String dspname = null;
/*  531 */       ArrayList<String> chkOut = new ArrayList();
/*  532 */       HashMap<String, String> map = new HashMap();
/*  533 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*  535 */         String param = (String)e.nextElement();
/*  536 */         map.put(param, request.getParameter(param));
/*      */       }
/*      */       
/*  539 */       Properties tempArgs = new Properties();
/*  540 */       tempArgs.putAll(map);
/*  541 */       boolean isMonitorPresent = NewMonitorUtil.checkIfMonitorAlreadyExists(tempArgs, monType);
/*  542 */       if (isMonitorPresent)
/*      */       {
/*  544 */         chkOut = com.adventnet.appmanager.util.DBUtil.getResourceidanddspNameForIP(map);
/*  545 */         if (!chkOut.isEmpty()) {
/*  546 */           resid = tempArgs.getProperty("EXISTING-RESID");
/*  547 */           if (resid == null)
/*      */           {
/*  549 */             resid = (String)chkOut.get(0);
/*      */           }
/*  551 */           dspname = (String)chkOut.get(1);
/*  552 */           if ((resid != null) && (dspname != null))
/*      */           {
/*      */             try {
/*  555 */               if (!dspname.equalsIgnoreCase(request.getParameter("displayname"))) {
/*  556 */                 String query = "update AM_ManagedObject set DISPLAYNAME='" + request.getParameter("displayname") + "' where RESOURCEID=" + resid;
/*  557 */                 AMConnectionPool.executeUpdateStmt(query);
/*  558 */                 EnterpriseUtil.addUpdateQueryToFile(query);
/*      */               }
/*      */             } catch (Exception ep) {
/*  561 */               ep.printStackTrace();
/*      */             }
/*  563 */             request.setAttribute("resourceid", resid);
/*  564 */             if ((request.getParameter("label") != null) && (!request.getParameter("label").equals(""))) {
/*  565 */               insertLabel(request.getParameter("label"), resid);
/*      */             }
/*  567 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.monitor.alreadypresent"), "4509", true);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  573 */       e.printStackTrace();
/*      */     }
/*  575 */     if ((montype.equalsIgnoreCase("MS SQL")) || (montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("Sybase")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("Oracle")) || (montype.equalsIgnoreCase("oracleEBS")) || (montype.equalsIgnoreCase("Servers")) || (montype.equalsIgnoreCase("Apache Server")) || (montype.equalsIgnoreCase("IIS Server")) || (montype.equalsIgnoreCase("PHP")) || (montype.equalsIgnoreCase("Web Server")) || (montype.equalsIgnoreCase("Java Runtime")) || (montype.equalsIgnoreCase("J2EE Web Transactions")) || (montype.equalsIgnoreCase("Mail Server")) || (montype.equalsIgnoreCase("PostgreSQL")) || (montype.equalsIgnoreCase("Ingres")) || (montype.equalsIgnoreCase("Telnet")) || (montype.equalsIgnoreCase("SNMP/Network Device")) || (montype.equalsIgnoreCase("Ping Monitor")) || (montype.equalsIgnoreCase("Service Monitoring")) || (montype.equalsIgnoreCase("Memcached")) || (montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("ActiveDirectory")) || (montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("SilverStream")) || (montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("GlassFish")) || (montype.equalsIgnoreCase("JBoss server")) || (montype.equalsIgnoreCase("Tomcat server")) || (montype.equalsIgnoreCase("WebSphere Server")) || (montype.equalsIgnoreCase("Oracle Application Server")) || (montype.equalsIgnoreCase(".net")) || (montype.equalsIgnoreCase("OracleEBS")) || (montype.equalsIgnoreCase("MSOfficeSharePointServer")) || (montype.equalsIgnoreCase("WebLogic Integration")) || (montype.equalsIgnoreCase("SAP Server")) || (montype.equalsIgnoreCase("QueryMonitor")) || (montype.equalsIgnoreCase("Exchange-server")) || (montype.equalsIgnoreCase("Windows Performance Counters")) || (montype.equalsIgnoreCase("Hyper-V Server")) || (montype.equalsIgnoreCase("IBM Websphere MQ")) || (montype.equalsIgnoreCase("Microsoft MQ")) || (montype.equalsIgnoreCase("VMware vFabric tc Server")) || (montype.equalsIgnoreCase("RabbitMQ")) || (montype.equalsIgnoreCase("Nginx")) || (montype.equalsIgnoreCase("SAP CCMS")) || (montype.equalsIgnoreCase("vCenter")) || (montype.equalsIgnoreCase("VMwareView")))
/*      */     {
/*  577 */       if ((request.getParameter("host") != null) && (!request.getParameter("host").equals("")))
/*      */       {
/*  579 */         amform.setHost(request.getParameter("host"));
/*      */       }
/*      */       else
/*      */       {
/*  583 */         AMLog.debug("REST API : The host should not be empty.");
/*  584 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.host.notemptymessage"), "4217");
/*  585 */         return xmlString;
/*      */       }
/*  587 */       if ((montype.equalsIgnoreCase("Ingres")) || (montype.equalsIgnoreCase("Hyper-V Server")) || (montype.equalsIgnoreCase("Exchange-server")))
/*      */       {
/*  589 */         MSreq.addParameter("HostName", request.getParameter("host"));
/*      */       }
/*      */     }
/*      */     
/*  593 */     if ((montype.equalsIgnoreCase("MS SQL")) || (montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("Sybase")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("Oracle")) || (montype.equalsIgnoreCase("oracleEBS")) || (montype.equalsIgnoreCase("Apache Server")) || (montype.equalsIgnoreCase("IIS Server")) || (montype.equalsIgnoreCase("PHP")) || (montype.equalsIgnoreCase("Web Server")) || (montype.equalsIgnoreCase("Java Runtime")) || (montype.equalsIgnoreCase("J2EE Web Transactions")) || (montype.equalsIgnoreCase("Mail Server")) || (montype.equalsIgnoreCase("PostgreSQL")) || (montype.equalsIgnoreCase("Ingres")) || (montype.equalsIgnoreCase("Telnet")) || (montype.equalsIgnoreCase("SNMP/Network Device")) || (montype.equalsIgnoreCase("Service Monitoring")) || (montype.equalsIgnoreCase("Memcached")) || (montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("FTPMonitor")) || (montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("SilverStream")) || (montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("GlassFish")) || (montype.equalsIgnoreCase("JBoss server")) || (montype.equalsIgnoreCase("Tomcat server")) || (montype.equalsIgnoreCase("WebSphere Server")) || (montype.equalsIgnoreCase("Oracle Application Server")) || (montype.equalsIgnoreCase("OracleEBS")) || (montype.equalsIgnoreCase("WebLogic Integration")) || (montype.equalsIgnoreCase("QueryMonitor")) || (montype.equalsIgnoreCase("VMware vFabric tc Server")) || (montype.equalsIgnoreCase("SSLCertificateMonitor")) || (montype.equalsIgnoreCase("RabbitMQ")) || (montype.equalsIgnoreCase("Nginx")) || (montype.equalsIgnoreCase("vCenter")))
/*      */     {
/*  595 */       if (!montype.equalsIgnoreCase("Ingres")) {
/*      */         try
/*      */         {
/*  598 */           portInt = Integer.parseInt(request.getParameter("port"));
/*      */         }
/*      */         catch (Exception ex) {
/*      */           int portInt;
/*  602 */           AMLog.debug("REST API : The port should be a valid one.");
/*  603 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*      */         }
/*      */       }
/*      */       
/*  607 */       amform.setPort(request.getParameter("port"));
/*      */     }
/*  609 */     if ((montype.equalsIgnoreCase("MS SQL")) || (montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("Sybase")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("Oracle")) || (montype.equalsIgnoreCase("PostgreSQL")) || (montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("GlassFish")) || (montype.equalsIgnoreCase("VMware vFabric tc Server")) || (montype.equalsIgnoreCase("RabbitMQ")) || (montype.equalsIgnoreCase("MSOfficeSharePointServer")) || (montype.equalsIgnoreCase("Exchange-server")) || (montype.equalsIgnoreCase("Microsoft MQ")) || (montype.equalsIgnoreCase(".net")))
/*      */     {
/*  611 */       if (((request.getParameter("username") == null) || (request.getParameter("username").equals(""))) && (credentialid == null))
/*      */       {
/*  613 */         AMLog.debug("REST API : The username mentioned in the request URL should not be empty.");
/*  614 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usr.notemptymessage"), "4219");
/*  615 */         return xmlString;
/*      */       }
/*      */     }
/*  618 */     if ((montype.equalsIgnoreCase("MS SQL")) || (montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("Oracle")) || (montype.equalsIgnoreCase("PostgreSQL")) || (montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("GlassFish")) || (montype.equalsIgnoreCase("Tomcat server")) || (montype.equalsIgnoreCase("VMware vFabric tc Server")) || (montype.equalsIgnoreCase("RabbitMQ")))
/*      */     {
/*  620 */       String tomcatVersion = request.getParameter("version");
/*  621 */       boolean needPwdChk = (tomcatVersion != null) && (!tomcatVersion.equals("3")) && (!tomcatVersion.equals("4"));
/*  622 */       if ((needPwdChk) && ((request.getParameter("password") == null) || (request.getParameter("password").equals(""))) && (credentialid == null))
/*      */       {
/*  624 */         AMLog.debug("REST API : The password mentioned in the request URL should not be empty.");
/*  625 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pwd.notemptymessage"), "4220");
/*  626 */         return xmlString;
/*      */       }
/*      */     }
/*  629 */     if (((montype.equalsIgnoreCase("Ingres")) || (montype.equalsIgnoreCase("LDAP Server")) || (montype.equalsIgnoreCase("ActiveDirectory")) || (montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("WebLogic Integration")) || (montype.equalsIgnoreCase("SAP Server")) || (montype.equalsIgnoreCase("QueryMonitor")) || (montype.equalsIgnoreCase("Windows Performance Counters")) || (montype.equalsIgnoreCase("Hyper-V Server")) || (montype.equalsIgnoreCase("SAP CCMS")) || (montype.equalsIgnoreCase("vCenter")) || (montype.equalsIgnoreCase("VMwareView"))) && ((request.getParameter("username") == null) || (request.getParameter("username").equals(""))))
/*      */     {
/*  631 */       AMLog.debug("REST API : The username mentioned in the request URL should not be empty.");
/*  632 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usr.notemptymessage"), "4219");
/*  633 */       return xmlString;
/*      */     }
/*      */     
/*  636 */     if (((montype.equalsIgnoreCase("Ingres")) || (montype.equalsIgnoreCase("ActiveDirectory")) || (montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("WebLogic Integration")) || (montype.equalsIgnoreCase("SAP Server")) || (montype.equalsIgnoreCase("QueryMonitor")) || (montype.equalsIgnoreCase("Windows Performance Counters")) || (montype.equalsIgnoreCase("Hyper-V Server")) || (montype.equalsIgnoreCase("SAP CCMS")) || (montype.equalsIgnoreCase("vCenter")) || (montype.equalsIgnoreCase("VMwareView")) || (montype.equalsIgnoreCase("Exchange-server"))) && (request.getParameter("password") == null))
/*      */     {
/*  638 */       AMLog.debug("REST API : The password mentioned in the request URL should not be empty.");
/*  639 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pwd.notemptymessage"), "4220");
/*  640 */       return xmlString;
/*      */     }
/*  642 */     if ((montype.equalsIgnoreCase("ActiveDirectory")) || (montype.equalsIgnoreCase("Exchange-server")) || (montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("GlassFish")) || (montype.equalsIgnoreCase("VMware vFabric tc Server")) || (montype.equalsIgnoreCase("RabbitMQ")))
/*      */     {
/*  644 */       MSreq.addParameter("Password", request.getParameter("password"));
/*  645 */       MSreq.addParameter("UserName", request.getParameter("username"));
/*      */     }
/*  647 */     if (montype.equalsIgnoreCase("Amazon"))
/*      */     {
/*  649 */       if ((request.getParameter("SecretAccessKey") == null) || (request.getParameter("SecretAccessKey").equals("")))
/*      */       {
/*  651 */         AMLog.debug("REST API : The SecretAccessKey should not be empty.");
/*  652 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.amazonsecretacckey.notemptymessage"), "4271");
/*  653 */         return xmlString;
/*      */       }
/*  655 */       MSreq.addParameter("Password", request.getParameter("SecretAccessKey"));
/*  656 */       amform.setPassword(request.getParameter("password"));
/*      */     }
/*  658 */     if ((montype.equalsIgnoreCase("VMWare ESX/ESXi")) || (montype.equalsIgnoreCase("XenServerHost")))
/*      */     {
/*  660 */       if ((request.getParameter("monitorvms") != null) && ("no".equalsIgnoreCase(request.getParameter("monitorvms"))))
/*      */       {
/*  662 */         MSreq.addParameter("AddVMS", "0");
/*      */       }
/*  664 */       else if ((request.getParameter("monitorvms") != null) && ("onlyavailability".equalsIgnoreCase(request.getParameter("monitorvms"))))
/*      */       {
/*  666 */         MSreq.addParameter("AddVMS", "1");
/*      */       }
/*      */       else
/*      */       {
/*  670 */         MSreq.addParameter("AddVMS", "2");
/*      */       }
/*      */     }
/*  673 */     if (montype.equalsIgnoreCase("FTPMonitor"))
/*      */     {
/*  675 */       if ((request.getParameter("TargetAddress") == null) || (request.getParameter("TargetAddress").equals("")))
/*      */       {
/*  677 */         AMLog.debug("REST API : The TargetAddress mentioned in the request URL should not be empty.");
/*  678 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.targetaddress.notemptymessage"), "4231");
/*  679 */         return xmlString;
/*      */       }
/*  681 */       if ((!request.getParameter("DownloadFile").equals("yes")) && (!request.getParameter("DownloadFile").equals("no")))
/*      */       {
/*  683 */         AMLog.debug("REST API : The DownloadFile mentioned in the request URL should not be empty.");
/*  684 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.downloadfile.notemptymessage"), "4232");
/*  685 */         return xmlString;
/*      */       }
/*  687 */       if ((!request.getParameter("IsSecured").equals("yes")) && (!request.getParameter("IsSecured").equals("no")))
/*      */       {
/*  689 */         AMLog.debug("REST API : The IsSecured should be either yes or no.");
/*  690 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.issecure.message"), "4230");
/*  691 */         return xmlString;
/*      */       }
/*  693 */       if ((!request.getParameter("UploadFile").equals("yes")) && (!request.getParameter("UploadFile").equals("no")))
/*      */       {
/*  695 */         AMLog.debug("REST API : The UploadFile should be either yes or no.");
/*  696 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.uploadfile.message"), "4233");
/*  697 */         return xmlString;
/*      */       }
/*  699 */       if (request.getParameter("UploadFile").equals("no"))
/*      */       {
/*  701 */         MSreq.addParameter("Local Source File Name", "");
/*  702 */         MSreq.addParameter("Remote Destination File Name", "");
/*      */       }
/*      */       else
/*      */       {
/*  706 */         MSreq.addParameter("Local Source File Name", request.getParameter("LocalSourceFileName"));
/*  707 */         MSreq.addParameter("Remote Destination File Name", request.getParameter("RemoteDestinationFileName"));
/*      */       }
/*      */       
/*  710 */       if (request.getParameter("DownloadFile").equals("no"))
/*      */       {
/*  712 */         MSreq.addParameter("Remote Source File Name", "");
/*  713 */         MSreq.addParameter("Local Destination File Name", "");
/*      */       }
/*      */       else
/*      */       {
/*  717 */         MSreq.addParameter("Remote Source File Name", request.getParameter("RemoteSourceFileName"));
/*  718 */         MSreq.addParameter("Local Destination File Name", request.getParameter("LocalDestinationFileName"));
/*      */       }
/*      */       
/*  721 */       MSreq.addParameter("Target Address", request.getParameter("TargetAddress"));
/*  722 */       MSreq.addParameter("Download File", request.getParameter("DownloadFile"));
/*  723 */       MSreq.addParameter("Is Secured", request.getParameter("IsSecured"));
/*  724 */       MSreq.addParameter("Upload File", request.getParameter("UploadFile"));
/*  725 */       MSreq.addParameter("Port No", request.getParameter("port"));
/*  726 */       MSreq.addParameter("Password", request.getParameter("password"));
/*  727 */       MSreq.addParameter("User Name", request.getParameter("username"));
/*      */     }
/*  729 */     if ((montype.equalsIgnoreCase("RBM")) || (montype.equalsIgnoreCase("Mail Server")) || (montype.equalsIgnoreCase("Web Service")) || (montype.equalsIgnoreCase("SNMP/Network Device")) || (montype.equalsIgnoreCase("Ping Monitor")) || (montype.equalsIgnoreCase("FTPMonitor")) || (montype.equalsIgnoreCase("DNSMonitor")) || (montype.equalsIgnoreCase("SSLCertificateMonitor")) || (montype.equalsIgnoreCase("UrlMonitor")) || (montype.equalsIgnoreCase("Service Monitoring")) || (montype.equalsIgnoreCase("Telnet")))
/*      */     {
/*      */       try
/*      */       {
/*  733 */         timeOut = Integer.parseInt(request.getParameter("timeout"));
/*  734 */         amform.setTimeout(timeOut);
/*  735 */         MSreq.addParameter("Timeout", request.getParameter("timeout"));
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  739 */         AMLog.debug("REST API : The timeout should be a valid one.");
/*  740 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.timeout.notvalidmessage"), "4216");
/*      */       }
/*      */       
/*  743 */       if (montype.equalsIgnoreCase("Ping Monitor"))
/*      */       {
/*  745 */         amform.setPtimeout(timeOut);
/*  746 */         amform.setPollInterval(pollInterval);
/*  747 */         amform.setHostname(request.getParameter("host"));
/*      */       }
/*      */     }
/*  750 */     if ((montype.equalsIgnoreCase("SNMP/Network Device")) && ((request.getParameter("snmpCommunityString") == null) || (request.getParameter("snmpCommunityString").equals(""))))
/*      */     {
/*  752 */       MSreq.addParameter("snmpCommunityString", "public");
/*      */     }
/*  754 */     if ((montype.equalsIgnoreCase("MS SQL")) && (request.getParameter("authentication") != null) && (!request.getParameter("authentication").equals("")))
/*      */     {
/*  756 */       if ((request.getParameter("authentication") != null) && ((request.getParameter("authentication").equals("SQL")) || (request.getParameter("authentication").equals("Windows"))))
/*      */       {
/*  758 */         MSreq.addParameter("authType", request.getParameter("authentication"));
/*      */       }
/*      */       else
/*      */       {
/*  762 */         AMLog.debug("REST API : The authentication should SQL or Windows.");
/*  763 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.auth.message"), "4221");
/*  764 */         return xmlString;
/*      */       }
/*      */     }
/*  767 */     if (montype.equalsIgnoreCase("Hyper-V Server"))
/*      */     {
/*  769 */       MSreq.addParameter("Password", request.getParameter("password"));
/*  770 */       MSreq.addParameter("UserName", request.getParameter("username"));
/*  771 */       if ((request.getParameter("addvms") != null) && (request.getParameter("addvms").equalsIgnoreCase("yes")))
/*      */       {
/*  773 */         MSreq.addParameter("AddVMS", "Yes");
/*      */       }
/*      */       else
/*      */       {
/*  777 */         MSreq.addParameter("AddVMS", "False");
/*      */       }
/*      */     }
/*  780 */     if (montype.equalsIgnoreCase("IBM Websphere MQ"))
/*      */     {
/*  782 */       if ((request.getParameter("listenerport") == null) || (request.getParameter("listenerport").equals("")))
/*      */       {
/*      */ 
/*  785 */         AMLog.debug("REST API : The listenerport should not be a empty.");
/*  786 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.listenerport.notemptymessage"), "4274");
/*  787 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  792 */       MSreq.addParameter("Listener Port", request.getParameter("listenerport"));
/*      */       
/*  794 */       if ((request.getParameter("serverconnectionchannel") == null) || (request.getParameter("serverconnectionchannel").equals("")))
/*      */       {
/*      */ 
/*  797 */         AMLog.debug("REST API : The serverconnectionchannel should not be a empty.");
/*  798 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.serverconnectionchannel.notemptymessage"), "4275");
/*  799 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  804 */       MSreq.addParameter("Server Connection Channel", request.getParameter("serverconnectionchannel"));
/*      */       
/*  806 */       if ((request.getParameter("ccsid") != null) && (!request.getParameter("ccsid").equals("")))
/*      */       {
/*  808 */         MSreq.addParameter("CCSID", request.getParameter("ccsid"));
/*      */       }
/*  810 */       MSreq.addParameter("Host Name", request.getParameter("host"));
/*      */     }
/*  812 */     if (montype.equalsIgnoreCase("Apache Server"))
/*      */     {
/*  814 */       if ((request.getParameter("serverstatusurl") != null) && (request.getParameter("serverstatusurl").equals("true")))
/*      */       {
/*  816 */         if ((request.getParameter("apacheurl") == null) || (request.getParameter("apacheurl").equals("")))
/*      */         {
/*  818 */           AMLog.debug("REST API : The apacheurl should not be a empty.");
/*  819 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.apacheurl.notemptymessage"), "4272");
/*  820 */           return xmlString;
/*      */         }
/*      */       }
/*  823 */       else if ((request.getParameter("serverstatusurl") == null) || (request.getParameter("serverstatusurl").equals("")))
/*      */       {
/*  825 */         AMLog.debug("REST API : The serverstatusurl should not be true or false.");
/*  826 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.serverstatusurl.notemptymessage"), "4273");
/*  827 */         return xmlString;
/*      */       }
/*      */     }
/*  830 */     if (montype.equalsIgnoreCase("Nginx"))
/*      */     {
/*  832 */       MSreq.addParameter("hostname", request.getParameter("host"));
/*  833 */       MSreq.addParameter("port", request.getParameter("port"));
/*  834 */       MSreq.addParameter("dc", "yes");
/*  835 */       if ((request.getParameter("sslenable") != null) && (request.getParameter("sslenable").equalsIgnoreCase("true")))
/*      */       {
/*  837 */         MSreq.addParameter("ssl", "Yes");
/*      */       }
/*      */       else
/*      */       {
/*  841 */         MSreq.addParameter("ssl", "false");
/*      */       }
/*  843 */       if ((request.getParameter("nginxauth") != null) && (request.getParameter("nginxauth").equalsIgnoreCase("true")))
/*      */       {
/*  845 */         MSreq.addParameter("authreq", "Yes");
/*  846 */         if ((request.getParameter("username") != null) && (!request.getParameter("username").equals("")) && (request.getParameter("password") != null) && (!request.getParameter("password").equals("")))
/*      */         {
/*  848 */           MSreq.addParameter("username", request.getParameter("username"));
/*  849 */           MSreq.addParameter("password", request.getParameter("password"));
/*      */         }
/*      */         else
/*      */         {
/*  853 */           AMLog.debug("REST API : The username and password should not be empty, when authetication is enabled.");
/*  854 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.addmonitor.nginx.userpassempty.txt"), "4550");
/*  855 */           return xmlString;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  860 */         MSreq.addParameter("authreq", "false");
/*  861 */         MSreq.addParameter("username", "");
/*  862 */         MSreq.addParameter("password", "");
/*      */       }
/*  864 */       if (request.getParameter("nginxstatusurl") != null)
/*      */       {
/*  866 */         MSreq.addParameter("nginxstatusurl", "");
/*      */       }
/*      */     }
/*  869 */     if (montype.equalsIgnoreCase("PHP"))
/*      */     {
/*  871 */       if ((request.getParameter("serverpath") == null) || (request.getParameter("serverpath").equals("")))
/*      */       {
/*  873 */         AMLog.debug("REST API : The serverpath should not be a empty.");
/*  874 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.serverpath.notemptymessage"), "4222");
/*  875 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/*  879 */       amform.setServerpath(request.getParameter("serverpath"));
/*      */     }
/*      */     
/*  882 */     if (montype.equalsIgnoreCase("JMX Applications"))
/*      */     {
/*  884 */       if ((credentialid == null) && ((request.getParameter("jndiurl") == null) || (request.getParameter("jndiurl").equals(""))))
/*      */       {
/*  886 */         AMLog.debug("REST API : The jndiurl should not be a empty.");
/*  887 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jndiurl.notemptymessage"), "4223");
/*  888 */         return xmlString;
/*      */       }
/*  890 */       if ((request.getParameter("jmxEnabled") != null) && (request.getParameter("jmxEnabled").equalsIgnoreCase("on")) && ((request.getParameter("jmxurl") == null) || (request.getParameter("jmxurl").equals(""))))
/*      */       {
/*  892 */         AMLog.debug("REST API : The jmxurl should not be a empty.");
/*  893 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jmxurl.notemptymessage"), "4276");
/*  894 */         return xmlString;
/*      */       }
/*      */     }
/*  897 */     if (montype.equalsIgnoreCase("Java Runtime"))
/*      */     {
/*  899 */       if ((credentialid == null) && ((request.getParameter("jndiurl") == null) || (request.getParameter("jndiurl").equals(""))))
/*      */       {
/*  901 */         AMLog.debug("REST API : The jndiurl should not be a empty.");
/*  902 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jndiurl.notemptymessage"), "4223");
/*  903 */         return xmlString;
/*      */       }
/*  905 */       if ((request.getParameter("jmxEnabled") != null) && (request.getParameter("jmxEnabled").equalsIgnoreCase("on")) && ((request.getParameter("jmxurl") == null) || (request.getParameter("jmxurl").equals(""))))
/*      */       {
/*  907 */         AMLog.debug("REST API : The jmxurl should not be a empty.");
/*  908 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jmxurl.notemptymessage"), "4276");
/*  909 */         return xmlString;
/*      */       }
/*      */     }
/*  912 */     if ((request.getParameter("authEnabled") != null) && ((montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("JBoss server")) || (montype.equalsIgnoreCase("Java Runtime"))) && (request.getParameter("authEnabled").equals("on")))
/*      */     {
/*  914 */       if ((request.getParameter("username") == null) || (request.getParameter("username").equals("")))
/*      */       {
/*  916 */         AMLog.debug("REST API : The username mentioned in the request URL should not be empty.");
/*  917 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usr.notemptymessage"), "4219");
/*  918 */         return xmlString;
/*      */       }
/*  920 */       if ((request.getParameter("password") == null) || (request.getParameter("password").equals("")))
/*      */       {
/*  922 */         MSreq.addParameter("password", "");
/*      */       }
/*      */     }
/*  925 */     else if (((request.getParameter("authEnabled") != null) && (!request.getParameter("authEnabled").equals("")) && (request.getParameter("authEnabled").equals("on"))) || ((montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("JBoss server")) || (montype.equalsIgnoreCase("Java Runtime"))))
/*      */     {
/*  927 */       MSreq.addParameter("username", "");
/*  928 */       MSreq.addParameter("password", "");
/*      */     }
/*  930 */     if ((request.getParameter("LaunchType") != null) && (montype.equalsIgnoreCase("JBoss server")))
/*      */     {
/*      */ 
/*  933 */       if (request.getParameter("LaunchType").equalsIgnoreCase("STANDALONE"))
/*      */       {
/*  935 */         MSreq.addParameter("LaunchType", "False");
/*  936 */         MSreq.addParameter("HostController", "");
/*  937 */         MSreq.addParameter("ServerName", "");
/*  938 */       } else if (request.getParameter("LaunchType").equalsIgnoreCase("DOMAIN")) {
/*  939 */         MSreq.addParameter("LaunchType", "DOMAIN");
/*  940 */         if ((request.getParameter("HostController") != null) && (request.getParameter("ServerName") != null) && (!request.getParameter("HostController").equals("")) && (!request.getParameter("ServerName").equals("")))
/*      */         {
/*  942 */           MSreq.addParameter("HostController", request.getParameter("HostController"));
/*  943 */           MSreq.addParameter("ServerName", request.getParameter("ServerName"));
/*      */         } else {
/*  945 */           AMLog.debug("REST API : The HostController/ServerName should not be a empty if Launch type is domain.");
/*  946 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jboss.domain.notemptymessage"), "4551");
/*  947 */           return xmlString;
/*      */         }
/*      */       }
/*      */     }
/*  951 */     else if ((request.getParameter("LaunchType") == null) && (montype.equalsIgnoreCase("JBoss server")))
/*      */     {
/*  953 */       MSreq.addParameter("LaunchType", "False");
/*  954 */       MSreq.addParameter("HostController", "");
/*  955 */       MSreq.addParameter("ServerName", "");
/*      */     }
/*  957 */     if ((request.getParameter("sslenabled") != null) && (montype.equalsIgnoreCase("JBoss server")) && ((request.getParameter("sslenabled").equalsIgnoreCase("on")) || (request.getParameter("sslenabled").equalsIgnoreCase("true"))))
/*      */     {
/*  959 */       MSreq.addParameter("sslenabled", "True");
/*      */     } else {
/*  961 */       MSreq.addParameter("sslenabled", "False");
/*      */     }
/*  963 */     if (((request.getParameter("instance") == null) || (request.getParameter("instance").equals(""))) && ((montype.equalsIgnoreCase("Oracle")) || (montype.equalsIgnoreCase("Ingres"))))
/*      */     {
/*  965 */       AMLog.debug("REST API : The instance should not be a empty.");
/*  966 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.instance.notemptymessage"), "4224");
/*  967 */       return xmlString;
/*      */     }
/*  969 */     if ((credentialid == null) && ((request.getParameter("instance") == null) || (request.getParameter("instance").equals(""))) && ((montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("Sybase")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("PostgreSQL"))))
/*      */     {
/*  971 */       AMLog.debug("REST API : The instance should not be a empty.");
/*  972 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.instance.notemptymessage"), "4224");
/*  973 */       return xmlString;
/*      */     }
/*  975 */     if ((montype.equalsIgnoreCase("PostgreSQL")) || (montype.equalsIgnoreCase("Ingres")))
/*      */     {
/*  977 */       MSreq.addParameter("UserName", request.getParameter("username"));
/*  978 */       MSreq.addParameter("Password", request.getParameter("password"));
/*      */       
/*  980 */       MSreq.addParameter("DBname", request.getParameter("instance"));
/*      */     }
/*      */     
/*  983 */     if ((montype.equalsIgnoreCase("MongoDB")) || (montype.equalsIgnoreCase("Redis")) || (montype.equalsIgnoreCase("Cassandra")))
/*      */     {
/*  985 */       MSreq.addParameter("hostname", request.getParameter("host"));
/*  986 */       MSreq.addParameter("port", request.getParameter("port"));
/*  987 */       MSreq.addParameter("DisplayName", request.getParameter("displayname"));
/*      */       
/*  989 */       if ((montype.equalsIgnoreCase("Redis")) || (montype.equalsIgnoreCase("Cassandra"))) {
/*  990 */         MSreq.addParameter("discoverclusternodes", request.getParameter("discoverclusternodes"));
/*      */       } else {
/*  992 */         MSreq.addParameter("discoverchildren", request.getParameter("discoverchildren"));
/*      */       }
/*  994 */       if ((request.getParameter("authRequired") != null) && (request.getParameter("authRequired").equalsIgnoreCase("true")))
/*      */       {
/*  996 */         MSreq.addParameter("authreq", "Yes");
/*  997 */         if ((montype.equalsIgnoreCase("MongoDB")) || (montype.equalsIgnoreCase("Cassandra"))) {
/*  998 */           if (((request.getParameter("username") == null) || (request.getParameter("username").equals(""))) && (credentialid == null)) {
/*  999 */             AMLog.debug("REST API : The username mentioned in the request URL should not be empty.");
/* 1000 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usr.notemptymessage"), "4219");
/* 1001 */             return xmlString;
/*      */           }
/* 1003 */           MSreq.addParameter("username", request.getParameter("username"));
/*      */         }
/* 1005 */         MSreq.addParameter("password", request.getParameter("password"));
/*      */       } else {
/* 1007 */         MSreq.addParameter("authreq", "False");
/*      */       }
/*      */     }
/* 1010 */     if ((montype.equalsIgnoreCase("MSOfficeSharePointServer")) || (montype.equalsIgnoreCase("Microsoft MQ")) || (montype.equalsIgnoreCase("Microsoft Dynamics CRM")))
/*      */     {
/* 1012 */       MSreq.addParameter("UserName", request.getParameter("username"));
/* 1013 */       MSreq.addParameter("Password", request.getParameter("password"));
/*      */     }
/* 1015 */     if (montype.equalsIgnoreCase("DNSMonitor"))
/*      */     {
/* 1017 */       if ((request.getParameter("SearchField") == null) || (request.getParameter("SearchField").equals("")))
/*      */       {
/* 1019 */         AMLog.debug("REST API : The SearchField should not be empty.");
/* 1020 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.serachfield.notemptymessage"), "4234");
/* 1021 */         return xmlString;
/*      */       }
/* 1023 */       if ((request.getParameter("TargetAddress") == null) || (request.getParameter("TargetAddress").equals("")))
/*      */       {
/* 1025 */         AMLog.debug("REST API : The TargetAddress mentioned in the request URL should not be empty.");
/* 1026 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.targetaddress.notemptymessage"), "4231");
/* 1027 */         return xmlString;
/*      */       }
/* 1029 */       if ((request.getParameter("LookupAddress") == null) || (request.getParameter("LookupAddress").equals("")))
/*      */       {
/* 1031 */         AMLog.debug("REST API : The LookupAddress should not be empty.");
/* 1032 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.lookupaddress.notemptymessage"), "4236");
/* 1033 */         return xmlString;
/*      */       }
/* 1035 */       if ((request.getParameter("RecordType") == null) || (request.getParameter("RecordType").equals("")))
/*      */       {
/* 1037 */         AMLog.debug("REST API : The RecordType should not be empty.");
/* 1038 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.recordtype.notemptymessage"), "4237");
/* 1039 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/* 1043 */       MSreq.addParameter("Search Field", request.getParameter("SearchField"));
/* 1044 */       MSreq.addParameter("Target Address", request.getParameter("TargetAddress"));
/* 1045 */       MSreq.addParameter("Lookup Address", request.getParameter("LookupAddress"));
/* 1046 */       MSreq.addParameter("Record Type", request.getParameter("RecordType"));
/*      */       
/*      */ 
/* 1049 */       if ((request.getParameter("SearchValue") == null) || (request.getParameter("SearchValue").equals("")))
/*      */       {
/* 1051 */         MSreq.addParameter("Search Value", "");
/*      */       }
/*      */       else
/*      */       {
/* 1055 */         MSreq.addParameter("Search Value", request.getParameter("SearchValue"));
/*      */       }
/*      */     }
/* 1058 */     if (montype.equalsIgnoreCase("LDAP Server"))
/*      */     {
/* 1060 */       if ((request.getParameter("LDAPServer") == null) || (request.getParameter("LDAPServer").equals("")))
/*      */       {
/* 1062 */         AMLog.debug("REST API : The LDAPServer should not be empty.");
/* 1063 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.ldapserver.notemptymessage"), "4226");
/* 1064 */         return xmlString;
/*      */       }
/* 1066 */       if ((request.getParameter("LDAPServerPort") == null) || (request.getParameter("LDAPServerPort").equals("")))
/*      */       {
/* 1068 */         AMLog.debug("REST API : The LDAPServerPort should not be empty.");
/* 1069 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.ldapserverport.notemptymessage"), "4227");
/* 1070 */         return xmlString;
/*      */       }
/* 1072 */       if ((request.getParameter("MatchingAttribute") == null) || (request.getParameter("MatchingAttribute").equals("")))
/*      */       {
/* 1074 */         AMLog.debug("REST API : The MatchingAttribute should be anyone of cn, uid, sn, displayname, givenname, objectclass, dc, ou.");
/* 1075 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.ldapmatchingatt.message"), "4228");
/* 1076 */         return xmlString;
/*      */       }
/* 1078 */       if ((request.getParameter("FilterCondition") == null) || (request.getParameter("FilterCondition").equals("")))
/*      */       {
/* 1080 */         AMLog.debug("REST API : The FilterCondition should be anyone of equals, contains, notequals.");
/* 1081 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.ldapfilterconition.message"), "4229");
/* 1082 */         return xmlString;
/*      */       }
/* 1084 */       if ((request.getParameter("IsSecured") == null) || (request.getParameter("IsSecured").equals("")))
/*      */       {
/* 1086 */         AMLog.debug("REST API : The IsSecured should be either yes or no.");
/* 1087 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.issecure.message"), "4230");
/* 1088 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/* 1092 */       MSreq.addParameter("LDAP Server", request.getParameter("LDAPServer"));
/* 1093 */       MSreq.addParameter("LDAP Server Port", request.getParameter("LDAPServerPort"));
/* 1094 */       MSreq.addParameter("Password", request.getParameter("password"));
/* 1095 */       MSreq.addParameter("UserName", request.getParameter("username"));
/* 1096 */       MSreq.addParameter("Matching Attribute", request.getParameter("MatchingAttribute"));
/* 1097 */       MSreq.addParameter("Filter Condition", request.getParameter("FilterCondition"));
/* 1098 */       MSreq.addParameter("Is Secured", request.getParameter("IsSecured"));
/* 1099 */       MSreq.addParameter("Search Result", request.getParameter("SearchResult"));
/* 1100 */       MSreq.addParameter("Search Filter", request.getParameter("SearchFilter"));
/* 1101 */       MSreq.addParameter("Search Base", request.getParameter("SearchBase"));
/*      */     }
/*      */     
/* 1104 */     if ((montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("WebLogic Integration")))
/*      */     {
/* 1106 */       if (credentialid == null)
/*      */       {
/* 1108 */         if ((montype.equalsIgnoreCase("WEBLOGIC SERVER")) && (request.getParameter("sslenabled") != null)) {
/* 1109 */           amform.setSslenabled(Boolean.valueOf(request.getParameter("sslenabled")).booleanValue());
/*      */         }
/* 1111 */         if (request.getParameter("version") != null)
/*      */         {
/* 1113 */           String add = null;
/*      */           try
/*      */           {
/* 1116 */             add = InetAddress.getLocalHost().getHostName();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1120 */             e.printStackTrace();
/*      */           }
/* 1122 */           String workingdir = null;
/* 1123 */           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/*      */           {
/* 1125 */             workingdir = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*      */           }
/*      */           else
/*      */           {
/* 1129 */             workingdir = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*      */           }
/* 1131 */           if (request.getParameter("version").equalsIgnoreCase("6.1"))
/*      */           {
/* 1133 */             if ((!new File("./classes/weblogicclient6_1.jar").exists()) && (!new File("./classes/weblogic/version6/weblogic.jar").exists()))
/*      */             {
/* 1135 */               AMLog.debug("REST API : The weblogic.jar is missing and is required for monitoring Weblogic  server Version 6.");
/* 1136 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.api.weblogicclient6jar.missing"), "4265");
/* 1137 */               return xmlString;
/*      */             }
/* 1139 */             MSreq.addParameter("version", "WLS_6_1");
/* 1140 */             amform.setVersion("WLS_6_1");
/*      */           }
/* 1142 */           else if (request.getParameter("version").equalsIgnoreCase("7.0"))
/*      */           {
/* 1144 */             if ((!new File("./classes/weblogicclient7_0_jmx.jar").exists()) && (!new File("./classes/weblogic/version7/weblogic.jar").exists()))
/*      */             {
/* 1146 */               AMLog.debug("REST API : The weblogic.jar is missing and is required for monitoring Weblogic  server Version 7.");
/* 1147 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.api.weblogicclient7jar.missing"), "4266");
/* 1148 */               return xmlString;
/*      */             }
/* 1150 */             MSreq.addParameter("version", "WLS_7_0");
/* 1151 */             amform.setVersion("WLS_7_0");
/*      */           }
/* 1153 */           else if (request.getParameter("version").equalsIgnoreCase("8.1"))
/*      */           {
/* 1155 */             if ((!new File("./classes/weblogicclient8_1_jmx.jar").exists()) && (!new File("./classes/weblogic/version8/weblogic.jar").exists()))
/*      */             {
/* 1157 */               AMLog.debug("REST API : The weblogic.jar is missing and is required for monitoring Weblogic  server Version 8.");
/* 1158 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.api.weblogicclient8jar.missing"), "4267");
/* 1159 */               return xmlString;
/*      */             }
/* 1161 */             MSreq.addParameter("version", "WLS_8_1");
/* 1162 */             amform.setVersion("WLS_8_1");
/*      */           }
/* 1164 */           else if (request.getParameter("version").equalsIgnoreCase("9.x"))
/*      */           {
/* 1166 */             if ((!new File("./classes/weblogicclient9.jar").exists()) && (!new File("./classes/weblogic/version9/weblogic.jar").exists()))
/*      */             {
/* 1168 */               AMLog.debug("REST API : The weblogic.jar is missing and is required for monitoring Weblogic  server Version 9.");
/* 1169 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.api.weblogicclient9jar.missing"), "4268");
/* 1170 */               return xmlString;
/*      */             }
/* 1172 */             MSreq.addParameter("version", "WLS_9_1");
/* 1173 */             amform.setVersion("WLS_9_1");
/*      */           }
/* 1175 */           else if (request.getParameter("version").equalsIgnoreCase("10.x"))
/*      */           {
/*      */ 
/* 1178 */             if ((!new File("./classes/weblogicclient9.jar").exists()) && (!new File("./classes/weblogic/version9/weblogic.jar").exists()) && ((!new File("./classes/weblogic/version10/weblogic.jar").exists()) || (!new File("./classes/weblogic/version10/wlclient.jar").exists()) || (!new File("./classes/weblogic/version10/wljmsclient.jar").exists()) || (!new File("./classes/weblogic/version10/wlthint3client.jar").exists())))
/*      */             {
/* 1180 */               AMLog.debug("REST API : The weblogic.jar is missing and is required for monitoring Weblogic  server Version 10.x (11g),12c.");
/* 1181 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.api.weblogicclient10jar.missing"), "4269");
/* 1182 */               return xmlString;
/*      */             }
/* 1184 */             MSreq.addParameter("version", "WLS_10_0");
/* 1185 */             amform.setVersion("WLS_10_0");
/*      */           }
/*      */           else
/*      */           {
/* 1189 */             AMLog.debug("REST API : The version of WEBLOGIC should be anyone of 6.1,7.0,8.1,9.x,10.x (11g),12c.");
/* 1190 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.weblogicversion.message"), "4238");
/* 1191 */             return xmlString;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1196 */           AMLog.debug("REST API : The version of WEBLOGIC should be anyone of 6.1,7.0,8.1,9.x,10.x (11g),12c.");
/* 1197 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.weblogicversion.message"), "4238");
/* 1198 */           return xmlString;
/*      */         }
/*      */       }
/*      */     }
/* 1202 */     if (montype.equalsIgnoreCase("JBoss server"))
/*      */     {
/* 1204 */       if (request.getParameter("version") != null)
/*      */       {
/* 1206 */         if (request.getParameter("version").equalsIgnoreCase("3.2.x"))
/*      */         {
/* 1208 */           MSreq.addParameter("version", "JBOSS_HTTP");
/*      */         }
/* 1210 */         else if (request.getParameter("version").equalsIgnoreCase("4.x"))
/*      */         {
/* 1212 */           MSreq.addParameter("version", "JBOSS_HTTP40");
/*      */         }
/* 1214 */         else if (request.getParameter("version").equalsIgnoreCase("4.0.1"))
/*      */         {
/* 1216 */           MSreq.addParameter("version", "JBOSS_HTTP401");
/*      */         }
/* 1218 */         else if (request.getParameter("version").equalsIgnoreCase("4.0.2"))
/*      */         {
/* 1220 */           MSreq.addParameter("version", "JBOSS_HTTP402");
/*      */         }
/* 1222 */         else if (request.getParameter("version").equalsIgnoreCase("5.x"))
/*      */         {
/* 1224 */           MSreq.addParameter("version", "JBOSS_HTTP402");
/*      */         }
/* 1226 */         else if (request.getParameter("version").equalsIgnoreCase("6.x"))
/*      */         {
/* 1228 */           MSreq.addParameter("version", "JBOSS_HTTP60");
/*      */         }
/* 1230 */         else if (request.getParameter("version").equalsIgnoreCase("7.x"))
/*      */         {
/* 1232 */           MSreq.addParameter("version", "JBOSS_HTTP7");
/*      */         }
/* 1234 */         else if (request.getParameter("version").equalsIgnoreCase("Wildfly_8.x"))
/*      */         {
/* 1236 */           MSreq.addParameter("version", "JBOSS_HTTP8");
/*      */         }
/*      */         else
/*      */         {
/* 1240 */           AMLog.debug("REST API : The version of JBoss server should be anyone of 3.2.x,4.x,4.0.1,4.0.2 & above,5.x,6.x,7.x,Wildfly_8.x.");
/* 1241 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jbossversion.message"), "4239");
/* 1242 */           return xmlString;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1247 */         AMLog.debug("REST API : The version of JBoss server should be anyone of 3.2.x,4.x,4.0.1,4.0.2 & above,5.x,6.x,7.x,Wildfly_8.x.");
/* 1248 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.jbossversion.message"), "4239");
/* 1249 */         return xmlString;
/*      */       }
/*      */     }
/* 1252 */     if ((montype.equalsIgnoreCase("Tomcat server")) && (credentialid == null))
/*      */     {
/* 1254 */       String version = null;
/* 1255 */       if (request.getParameter("version") != null) {
/* 1256 */         version = request.getParameter("version").trim();
/*      */       }
/*      */       
/* 1259 */       if ("3.x".equalsIgnoreCase(version)) {
/* 1260 */         version = "3";
/* 1261 */       } else if ("4.x".equalsIgnoreCase(version)) {
/* 1262 */         version = "4";
/* 1263 */       } else if ("5.x".equalsIgnoreCase(version)) {
/* 1264 */         version = "5";
/* 1265 */       } else if ("6.x".equalsIgnoreCase(version)) {
/* 1266 */         version = "6";
/* 1267 */       } else if ("7.x".equalsIgnoreCase(version)) {
/* 1268 */         version = "7";
/* 1269 */       } else if ("8.x".equalsIgnoreCase(version)) {
/* 1270 */         version = "8";
/*      */       }
/*      */       
/* 1273 */       if ((version != null) && ((version.equalsIgnoreCase("3")) || (version.equalsIgnoreCase("4")) || (version.equalsIgnoreCase("5")) || (version.equalsIgnoreCase("6")) || (version.equalsIgnoreCase("7")) || (version.equalsIgnoreCase("8"))))
/*      */       {
/* 1275 */         MSreq.addParameter("version", version);
/*      */       }
/*      */       else
/*      */       {
/* 1279 */         AMLog.debug("REST API : The version of Tomcat Server should be anyone of 3,4,5,6,7.");
/* 1280 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.tomcatversion.message"), "4240");
/* 1281 */         return xmlString;
/*      */       }
/* 1283 */       if ((version.equalsIgnoreCase("5")) || (version.equalsIgnoreCase("6")) || (version.equalsIgnoreCase("7")) || (version.equalsIgnoreCase("8"))) {
/* 1284 */         String tomcatmanagerurl = "/manager";
/* 1285 */         if ((request.getParameter("tomcatmanagerurl") == null) || (request.getParameter("tomcatmanagerurl").equals("")))
/*      */         {
/*      */ 
/*      */ 
/* 1289 */           tomcatmanagerurl = "/manager";
/*      */         }
/*      */         else {
/* 1292 */           tomcatmanagerurl = request.getParameter("tomcatmanagerurl");
/* 1293 */           if (!tomcatmanagerurl.startsWith("/")) {
/* 1294 */             tomcatmanagerurl = "/" + tomcatmanagerurl;
/*      */           }
/*      */         }
/* 1297 */         amform.setTomcatmanagerurl(tomcatmanagerurl);
/* 1298 */         MSreq.addParameter("tomcatmanagerurl", tomcatmanagerurl);
/*      */       }
/*      */     }
/* 1301 */     if ((montype.equalsIgnoreCase("OracleEBS")) && (request.getParameter("SSL") != null))
/*      */     {
/* 1303 */       if ((!request.getParameter("SSL").equals("no")) && (!request.getParameter("SSL").equals("yes")))
/*      */       {
/* 1305 */         AMLog.debug("REST API : The SSL of OracleEBS should be yes or no.");
/* 1306 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.oraclessl.message"), "4245");
/* 1307 */         return xmlString;
/*      */       }
/* 1309 */       MSreq.addParameter("SSL Enabled", request.getParameter("SSL"));
/*      */     }
/* 1311 */     if (montype.equalsIgnoreCase("WebSphere Server"))
/*      */     {
/* 1313 */       if (credentialid == null)
/*      */       {
/* 1315 */         if (request.getParameter("version") != null)
/*      */         {
/* 1317 */           if ((request.getParameter("version").equalsIgnoreCase("5.x")) || (request.getParameter("version").equalsIgnoreCase("5")))
/*      */           {
/* 1319 */             MSreq.addParameter("version", "5.0");
/*      */           }
/* 1321 */           else if ((request.getParameter("version").equalsIgnoreCase("6.x")) || (request.getParameter("version").equalsIgnoreCase("6")))
/*      */           {
/* 1323 */             MSreq.addParameter("version", "6");
/*      */           }
/* 1325 */           else if ((request.getParameter("version").equalsIgnoreCase("7.x")) || (request.getParameter("version").equalsIgnoreCase("7")))
/*      */           {
/* 1327 */             MSreq.addParameter("version", "7");
/*      */           }
/* 1329 */           else if ((request.getParameter("version").equalsIgnoreCase("8.x")) || (request.getParameter("version").equalsIgnoreCase("8")))
/*      */           {
/* 1331 */             MSreq.addParameter("version", "8");
/*      */           }
/*      */           else
/*      */           {
/* 1335 */             AMLog.debug("REST API : The version of Websphere Server should be anyone of 5.x,6.x,7.x,8.x.");
/* 1336 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.websphereversion.message"), "4241");
/* 1337 */             return xmlString;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1342 */           AMLog.debug("REST API : The version of Websphere Server should be anyone of 5.x,6.x,7.x,8.x.");
/* 1343 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.websphereversion.message"), "4241");
/* 1344 */           return xmlString;
/*      */         }
/*      */         
/* 1347 */         if ((request.getParameter("soapport") == null) || (request.getParameter("soapport").equalsIgnoreCase("")))
/*      */         {
/* 1349 */           AMLog.debug("REST API : The soapport should be a valid whole number.");
/* 1350 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.soapport.notvalidmessage"), "4243");
/* 1351 */           return xmlString;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1358 */           int soapport = Integer.parseInt(request.getParameter("soapport"));
/* 1359 */           MSreq.addParameter("soapPort", request.getParameter("soapport"));
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1363 */           AMLog.debug("REST API : The soapport should be a valid whole number.");
/* 1364 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.soapport.notvalidmessage"), "4243");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1369 */       if ((request.getParameter("mode") != null) && ((request.getParameter("mode").equals("BASE")) || (request.getParameter("mode").equals("ND"))))
/*      */       {
/* 1371 */         if (request.getParameter("mode").equals("ND"))
/*      */         {
/* 1373 */           MSreq.addParameter("ndhost", request.getParameter("ndhost"));
/* 1374 */           MSreq.addParameter("ndport", request.getParameter("ndport"));
/* 1375 */           MSreq.addParameter("ndHost", request.getParameter("ndhost"));
/* 1376 */           MSreq.addParameter("ndPort", request.getParameter("ndport"));
/* 1377 */           MSreq.addParameter("isDmgr", "true");
/*      */         } else {
/* 1379 */           MSreq.addParameter("isDmgr", "false");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1384 */         AMLog.debug("REST API : The mode of Websphere Server should be BASE or ND.");
/* 1385 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.webspheremode.message"), "4242");
/* 1386 */         return xmlString;
/*      */       }
/*      */       
/* 1389 */       if ((request.getParameter("authEnabled") != null) && (request.getParameter("authEnabled").equalsIgnoreCase("true")))
/*      */       {
/* 1391 */         MSreq.addParameter("username", request.getParameter("username"));
/* 1392 */         MSreq.addParameter("password", request.getParameter("password"));
/*      */       }
/* 1394 */       MSreq.addParameter("allowEdit", "true");
/*      */     }
/* 1396 */     if (montype.equalsIgnoreCase("Oracle Application Server"))
/*      */     {
/* 1398 */       if (request.getParameter("version") != null)
/*      */       {
/* 1400 */         if (request.getParameter("version").equals("10.1.2"))
/*      */         {
/* 1402 */           MSreq.addParameter("version", "2");
/*      */         }
/* 1404 */         else if (request.getParameter("version").equals("10.1.3"))
/*      */         {
/* 1406 */           MSreq.addParameter("version", "3");
/*      */         }
/*      */         else
/*      */         {
/* 1410 */           AMLog.debug("REST API : The version of Oracle Application Server should be anyone of 10.1.2 or 10.1.3.");
/* 1411 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.oracleversion.message"), "4241");
/* 1412 */           return xmlString;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1417 */         AMLog.debug("REST API : The version of Oracle Application Server should be anyone of 10.1.2 or 10.1.3.");
/* 1418 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.oracleversion.message"), "4241");
/* 1419 */         return xmlString;
/*      */       }
/*      */     }
/* 1422 */     if ((montype.equalsIgnoreCase("SAP Server")) || (montype.equalsIgnoreCase("SAP CCMS")))
/*      */     {
/* 1424 */       if (request.getParameter("systemnumber") != null)
/*      */       {
/* 1426 */         amform.setSystemNumber(request.getParameter("systemnumber"));
/* 1427 */         MSreq.addParameter("port", request.getParameter("systemnumber"));
/*      */       }
/*      */       else
/*      */       {
/* 1431 */         AMLog.debug("REST API : The systemnumber of SAP Server should not be empty.");
/* 1432 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sapsys.message"), "4246");
/* 1433 */         return xmlString;
/*      */       }
/* 1435 */       if (request.getParameter("logonClient") != null)
/*      */       {
/* 1437 */         amform.setLogonClient(request.getParameter("logonClient"));
/* 1438 */         MSreq.addParameter("logonClient", request.getParameter("logonClient"));
/*      */       }
/*      */       else
/*      */       {
/* 1442 */         AMLog.debug("REST API : The logonClient of SAP Server should not be empty.");
/* 1443 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.saplogon.message"), "4247");
/* 1444 */         return xmlString;
/*      */       }
/* 1446 */       if (request.getParameter("language") != null)
/*      */       {
/* 1448 */         amform.setLanguage(request.getParameter("language"));
/* 1449 */         MSreq.addParameter("language", request.getParameter("language"));
/*      */       }
/*      */       else
/*      */       {
/* 1453 */         AMLog.debug("REST API : The language of SAP Server should not be empty.");
/* 1454 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.saplang.message"), "4248");
/* 1455 */         return xmlString;
/*      */       }
/*      */       
/* 1458 */       if (request.getParameter("username") != null) {
/* 1459 */         amform.setUsername(request.getParameter("username"));
/*      */       }
/* 1461 */       if (request.getParameter("password") != null) {
/* 1462 */         amform.setPassword(request.getParameter("password"));
/*      */       }
/* 1464 */       if (request.getParameter("routerString") != null) {
/* 1465 */         amform.setUsedRouterString(true);
/* 1466 */         amform.setRouterString(request.getParameter("routerString"));
/*      */       }
/*      */       
/* 1469 */       if (montype.equalsIgnoreCase("SAP CCMS")) {
/* 1470 */         String monitorset = request.getParameter("monitorset");
/* 1471 */         if (monitorset != null) {
/* 1472 */           amform.setVersion(monitorset);
/* 1473 */           MSreq.addParameter("version", monitorset);
/*      */         } else {
/* 1475 */           AMLog.debug("REST API : Value for \"CCMS Monitor Sets\" of SAP CCMS Server should not be empty.");
/* 1476 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sapccms.monitor.set.message"), "4552");
/* 1477 */           return xmlString;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1482 */     if (montype.equalsIgnoreCase("Exchange-server"))
/*      */     {
/* 1484 */       if ((request.getParameter("version") != null) && (!request.getParameter("version").equals("")) && ((request.getParameter("version").equals("2007")) || (request.getParameter("version").equals("2003")) || (request.getParameter("version").equals("2000")) || (request.getParameter("version").equals("5")) || (request.getParameter("version").equals("2010")) || (request.getParameter("version").equals("2013"))))
/*      */       {
/* 1486 */         MSreq.addParameter("version", request.getParameter("version"));
/* 1487 */         MSreq.addParameter("exchangeservice", request.getParameter("exchangeservice"));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1492 */         AMLog.debug("REST API : The version of Exchange Server should be any one of 2013,2010,2007, 2003, 2000, 5.");
/* 1493 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.exserverversiong.message"), "4256");
/* 1494 */         return xmlString;
/*      */       }
/*      */     }
/* 1497 */     if (montype.equalsIgnoreCase("QueryMonitor"))
/*      */     {
/* 1499 */       MSreq.addParameter("Host Name", request.getParameter("host"));
/* 1500 */       MSreq.addParameter("Port", request.getParameter("port"));
/* 1501 */       MSreq.addParameter("Username", request.getParameter("username"));
/* 1502 */       MSreq.addParameter("Password", request.getParameter("password"));
/* 1503 */       if ((request.getParameter("databasetype") != null) && ((request.getParameter("databasetype").equals("MySQL")) || (request.getParameter("databasetype").equals("Oracle")) || (request.getParameter("databasetype").equals("DB2")) || (request.getParameter("databasetype").equals("MsSQL")) || (request.getParameter("databasetype").equals("Sybase")) || (request.getParameter("databasetype").equals("Postgres"))))
/*      */       {
/* 1505 */         MSreq.addParameter("Database", request.getParameter("databasetype"));
/*      */       }
/*      */       else
/*      */       {
/* 1509 */         AMLog.debug("REST API : The databasetype of QueryMonitor should be any one of MySQL, Oracle, DB2, MsSQL, Sybase, Postgres.");
/* 1510 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.querymondbtype.message"), "4257");
/* 1511 */         return xmlString;
/*      */       }
/* 1513 */       if (request.getParameter("JDBCUrlOptional") != null)
/*      */       {
/* 1515 */         MSreq.addParameter("JDBC Url (Optional)", request.getParameter("JDBCUrlOptional"));
/*      */       }
/*      */       else
/*      */       {
/* 1519 */         MSreq.addParameter("JDBC Url (Optional)", "");
/*      */       }
/* 1521 */       if (request.getParameter("databasename") != null)
/*      */       {
/* 1523 */         MSreq.addParameter("Database Name", request.getParameter("databasename"));
/*      */       }
/*      */       else
/*      */       {
/* 1527 */         AMLog.debug("REST API : The databasetypename of QueryMonitor should not be empty.");
/* 1528 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.querymondbname.message"), "4258");
/* 1529 */         return xmlString;
/*      */       }
/* 1531 */       if (request.getParameter("showqueryoutput") != null)
/*      */       {
/* 1533 */         MSreq.addParameter("Show Query Output", request.getParameter("showqueryoutput"));
/*      */       }
/*      */       else
/*      */       {
/* 1537 */         AMLog.debug("REST API : The showqueryoutput of QueryMonitor should be any one of yes or no.");
/* 1538 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.querymonshowqry.message"), "4259");
/* 1539 */         return xmlString;
/*      */       }
/* 1541 */       if (request.getParameter("queries") != null)
/*      */       {
/* 1543 */         MSreq.addParameter("Queries", request.getParameter("queries"));
/*      */       }
/*      */       else
/*      */       {
/* 1547 */         AMLog.debug("REST API : The queries of QueryMonitor should not be empty.");
/* 1548 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.querymonqryempty.message"), "4259");
/* 1549 */         return xmlString;
/*      */       }
/*      */     }
/* 1552 */     if (montype.equalsIgnoreCase("Service Monitoring"))
/*      */     {
/* 1554 */       if (request.getParameter("command") == null)
/*      */       {
/* 1556 */         MSreq.addParameter("command", "");
/*      */       }
/* 1558 */       if (request.getParameter("search") == null)
/*      */       {
/* 1560 */         MSreq.addParameter("search", "");
/*      */       }
/*      */     }
/* 1563 */     AMLog.debug("REST API : montype=" + montype);
/* 1564 */     if (montype.equalsIgnoreCase("Mail Server"))
/*      */     {
/* 1566 */       if ((request.getParameter("emailid") == null) || (request.getParameter("emailid").equals("")))
/*      */       {
/* 1568 */         AMLog.debug("REST API : The emailid should not be empty.");
/* 1569 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailid.notemptymessage"), "4205");
/* 1570 */         return xmlString;
/*      */       }
/*      */       
/*      */ 
/* 1574 */       MSreq.addParameter("emailId", request.getParameter("emailid"));
/*      */       
/*      */ 
/* 1577 */       if ((request.getParameter("mailMsg") == null) || (request.getParameter("mailMsg").equals("")))
/*      */       {
/* 1579 */         amform.setMailMsg(FormatUtil.getString("am.mail.server.msg"));
/* 1580 */         MSreq.addParameter("mailMsg", FormatUtil.getString("am.mail.server.msg"));
/* 1581 */         MSreq.addParameter("mailSubject", FormatUtil.getString("am.mail.server.msg"));
/*      */       }
/*      */       else
/*      */       {
/* 1585 */         amform.setMailMsg(request.getParameter("mailMsg"));
/* 1586 */         MSreq.addParameter("mailSubject", request.getParameter("mailMsg"));
/*      */       }
/*      */       
/* 1589 */       if ((request.getParameter("mailSubject") == null) || (request.getParameter("mailSubject").equals("")))
/*      */       {
/* 1591 */         amform.setMailMsg(FormatUtil.getString("am.mail.server.msg"));
/* 1592 */         MSreq.addParameter("mailSubject", FormatUtil.getString("am.mail.server.msg"));
/*      */       }
/*      */       
/*      */ 
/* 1596 */       if ((request.getParameter("smtpauth") == null) || (request.getParameter("smtpauth").equals("")) || (request.getParameter("smtpauth").equals("false")))
/*      */       {
/* 1598 */         MSreq.addParameter("authRequired", "false");
/*      */ 
/*      */ 
/*      */       }
/* 1602 */       else if (request.getParameter("smtpauth").equals("true"))
/*      */       {
/* 1604 */         MSreq.addParameter("authRequired", "Yes");
/* 1605 */         if ((request.getParameter("username") != null) && (request.getParameter("password") != null))
/*      */         {
/* 1607 */           MSreq.addParameter("userName", request.getParameter("username"));
/* 1608 */           AMLog.debug("REST API : ");
/*      */         }
/*      */         else
/*      */         {
/* 1612 */           AMLog.debug("REST API : The username and password mentioned in the request URL should not be empty.");
/* 1613 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usrpwd.notemptymessage"), "4206");
/* 1614 */           return xmlString;
/*      */         }
/*      */       }
/*      */       
/* 1618 */       if ((request.getParameter("authRequired") == null) || (request.getParameter("authRequired").equals("")))
/*      */       {
/* 1620 */         MSreq.addParameter("authRequired", "false");
/* 1621 */         MSreq.addParameter("userName", "");
/* 1622 */         MSreq.addParameter("password", "");
/*      */       }
/* 1624 */       else if (request.getParameter("authRequired").equals("Yes"))
/*      */       {
/* 1626 */         if ((request.getParameter("username") != null) && (!request.getParameter("username").equals("")) && (request.getParameter("password") != null) && (!request.getParameter("password").equals("")))
/*      */         {
/* 1628 */           MSreq.addParameter("userName", request.getParameter("username"));
/*      */         }
/*      */         else
/*      */         {
/* 1632 */           AMLog.debug("REST API : The username and password mentioned in the request URL should not be empty.");
/* 1633 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.usrpwd.notemptymessage"), "4206");
/* 1634 */           return xmlString;
/*      */         }
/*      */       }
/*      */       
/* 1638 */       if (request.getParameter("sslEnabled") == null)
/*      */       {
/*      */ 
/* 1641 */         MSreq.addParameter("sslEnabled", "false");
/*      */       } else {
/* 1643 */         if (request.getParameter("sslEnabled").equals(""))
/*      */         {
/* 1645 */           AMLog.debug("REST API : The sslEnabled mentioned in the request URL should not be empty.");
/* 1646 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailsmtp.sslnotempty"), "4341");
/* 1647 */           return xmlString;
/*      */         }
/* 1649 */         if ((request.getParameter("sslEnabled").equals("Yes")) && ((request.getParameter("tlsEnabled") == null) || (request.getParameter("tlsEnabled").equals(""))))
/*      */         {
/* 1651 */           AMLog.debug("REST API : The tlsEnabled mentioned in the request URL should not be empty.");
/* 1652 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailsmtp.tlsnotempty"), "4308");
/* 1653 */           return xmlString;
/*      */         }
/*      */       }
/* 1656 */       if ((request.getParameter("popenabled") == null) || (request.getParameter("popenabled").equals("")) || (!request.getParameter("popenabled").equals("on")))
/*      */       {
/* 1658 */         MSreq.addParameter("fetchEnabled", "false");
/*      */       }
/* 1660 */       else if (request.getParameter("popenabled").equals("on"))
/*      */       {
/*      */ 
/* 1663 */         MSreq.addParameter("fetchEnabled", "Yes");
/* 1664 */         MSreq.addParameter("fetchType", "1");
/* 1665 */         if ((request.getParameter("popPort") == null) || (request.getParameter("popPort").equals("")))
/*      */         {
/* 1667 */           MSreq.addParameter("fsport", "110");
/*      */         }
/*      */         
/* 1670 */         if ((request.getParameter("popHost") != null) && (request.getParameter("popUserName") != null) && (request.getParameter("popPassword") != null))
/*      */         {
/*      */ 
/* 1673 */           MSreq.addParameter("fsHost", "" + request.getParameter("popHost"));
/* 1674 */           MSreq.addParameter("fsUserName", "" + request.getParameter("popUserName"));
/* 1675 */           MSreq.addParameter("fsPassword", "" + request.getParameter("popPassword"));
/*      */         }
/*      */         else
/*      */         {
/* 1679 */           AMLog.debug("REST API : The popHost, popUserName and popPassword mentioned in the request URL should not be empty.");
/* 1680 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pophost.notemptymessage"), "4207");
/* 1681 */           return xmlString;
/*      */         }
/*      */         
/* 1684 */         if ((request.getParameter("popSSLEnabled") == null) || (request.getParameter("popSSLEnabled").equals("")))
/*      */         {
/*      */ 
/* 1687 */           MSreq.addParameter("fsSSLEnabled", "false");
/*      */         }
/* 1689 */         else if ((request.getParameter("popSSLEnabled") != null) && ((request.getParameter("popSSLEnabled").equals("on")) || (request.getParameter("popSSLEnabled").equals("true"))))
/*      */         {
/* 1691 */           MSreq.addParameter("fsSSLEnabled", "Yes");
/*      */         }
/*      */         
/* 1694 */         if ((request.getParameter("popTLSEnabled") == null) || (request.getParameter("popTLSEnabled").equals("")))
/*      */         {
/*      */ 
/* 1697 */           MSreq.addParameter("fsTLSEnabled", "false");
/*      */         }
/* 1699 */         else if ((request.getParameter("popTLSEnabled") != null) && ((request.getParameter("popTLSEnabled").equals("on")) || (request.getParameter("popTLSEnabled").equals("true"))))
/*      */         {
/* 1701 */           MSreq.addParameter("fsTLSEnabled", "Yes");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1708 */       if (request.getParameter("fetchEnabled") == null)
/*      */       {
/* 1710 */         MSreq.addParameter("fetchEnabled", "false");
/*      */       } else {
/* 1712 */         if (request.getParameter("fetchEnabled").equals(""))
/*      */         {
/* 1714 */           AMLog.debug("REST API : The fetchEnabled mentioned in the request URL should not be empty.");
/* 1715 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchenabled.notempty"), "4309");
/* 1716 */           return xmlString;
/*      */         }
/* 1718 */         if (request.getParameter("fetchEnabled").equalsIgnoreCase("Yes"))
/*      */         {
/* 1720 */           if ((request.getParameter("fetchType") == null) || (request.getParameter("fetchType").equals("")))
/*      */           {
/* 1722 */             AMLog.debug("REST API : The fetchType in request URL should not be empty.");
/* 1723 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchtype.notempty"), "4307");
/* 1724 */             return xmlString;
/*      */           }
/* 1726 */           if ((request.getParameter("fetchType").equals("1")) || (request.getParameter("fetchType").equals("2")))
/*      */           {
/* 1728 */             if ((request.getParameter("fsport") == null) || (request.getParameter("fsport").equals("")))
/*      */             {
/* 1730 */               AMLog.debug("REST API : The fsPort mentioned in the request URL should not be empty.");
/* 1731 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchport.notempty"), "4306");
/* 1732 */               return xmlString;
/*      */             }
/* 1734 */             if ((request.getParameter("fsHost") == null) || (request.getParameter("fsHost").equals("")) || (request.getParameter("fsUserName") == null) || (request.getParameter("fsUserName").equals("")) || (request.getParameter("fsPassword") == null) || (request.getParameter("fsPassword").equals("")))
/*      */             {
/*      */ 
/* 1737 */               AMLog.debug("REST API : The fsHost, fsUserName and fsPassword mentioned in the request URL should not be empty.");
/* 1738 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchhost.notempty"), "4305");
/* 1739 */               return xmlString;
/*      */             }
/*      */             
/* 1742 */             if (request.getParameter("fsSSLEnabled") == null)
/*      */             {
/*      */ 
/* 1745 */               MSreq.addParameter("fsSSLEnabled", "false");
/*      */             } else {
/* 1747 */               if (request.getParameter("fsSSLEnabled").equals(""))
/*      */               {
/* 1749 */                 AMLog.debug("REST API : The fsSSLEnabled mentioned in the request URL should not be empty.");
/* 1750 */                 xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfsslenabled.notempty"), "4340");
/* 1751 */                 return xmlString;
/*      */               }
/* 1753 */               if ((request.getParameter("fsSSLEnabled").equals("Yes")) && ((request.getParameter("fsTLSEnabled") == null) || (request.getParameter("fsTLSEnabled").equals(""))))
/*      */               {
/* 1755 */                 AMLog.debug("REST API : The fsTLSEnabled mentioned in the request URL should not be empty.");
/* 1756 */                 xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchtls.notempty"), "4304");
/* 1757 */                 return xmlString;
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1763 */             AMLog.debug("REST API : The fetchType  in request URL should  be either 1 or 2 - 1 for POP / 2 for IMAP.");
/* 1764 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mailfetchtype.invalid"), "4303");
/* 1765 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1771 */     if (montype.equalsIgnoreCase("UrlMonitor"))
/*      */     {
/* 1773 */       MSreq.addParameter("monitorname", request.getParameter("displayname"));
/* 1774 */       MSreq.addParameter("actionmethod", "createUrlMonitor");
/* 1775 */       MSreq.addParameter("isAPI", "true");
/*      */       
/* 1777 */       if ((request.getParameter("url") == null) || (request.getParameter("url").equals("")))
/*      */       {
/* 1779 */         AMLog.debug("REST API : The url should not be empty.");
/* 1780 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.urlempty.msg"), "4310");
/* 1781 */         return xmlString;
/*      */       }
/* 1783 */       if ((request.getParameter("urlMethod") != null) && ((request.getParameter("urlMethod").equals("P")) || (request.getParameter("urlMethod").equals("G"))))
/*      */       {
/* 1785 */         MSreq.addParameter("method", request.getParameter("urlMethod"));
/*      */       }
/*      */       else
/*      */       {
/* 1789 */         AMLog.debug("REST API : The urlMethod should be P or G.");
/* 1790 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.urlmethodempty.msg"), "4311");
/* 1791 */         return xmlString;
/*      */       }
/*      */       
/* 1794 */       if (request.getParameter("httpCondition") != null)
/*      */       {
/* 1796 */         if ((request.getParameter("httpCondition").equals("LT")) || (request.getParameter("httpCondition").equals("GT")) || (request.getParameter("httpCondition").equals("EQ")) || (request.getParameter("httpCondition").equals("NE")) || (request.getParameter("httpCondition").equals("LE")) || (request.getParameter("httpCondition").equals("GE")))
/*      */         {
/* 1798 */           MSreq.addParameter("httpcondition", request.getParameter("httpCondition"));
/*      */         }
/*      */         else
/*      */         {
/* 1802 */           AMLog.debug("REST API : The httpCondition should be LT or GT or EQ or NE or LE or GE.");
/* 1803 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpcondition.msg"), "4313");
/* 1804 */           return xmlString;
/*      */         }
/*      */         
/* 1807 */         if ((request.getParameter("httpValue") != null) && (!request.getParameter("httpValue").equals("")))
/*      */         {
/*      */           try
/*      */           {
/* 1811 */             int httpvalueInt = Integer.parseInt(request.getParameter("httpValue"));
/* 1812 */             MSreq.addParameter("httpvalue", "" + httpvalueInt);
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1816 */             AMLog.debug("REST API : The httpValue should be a valid Integer.");
/* 1817 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpvalue.integermessage"), "4312");
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1823 */           AMLog.debug("REST API : The httpValue should be a valid Integer.");
/* 1824 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpvalue.integermessage"), "4312");
/* 1825 */           return xmlString;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 1831 */         AMLog.debug("REST API : The httpCondition should be LT or GT or EQ or NE or LE or GE.");
/* 1832 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpcondition.msg"), "4313");
/* 1833 */         return xmlString;
/*      */       }
/* 1835 */       if (request.getParameter("checkForContent") != null)
/*      */       {
/* 1837 */         MSreq.addParameter("checkfor", request.getParameter("checkForContent"));
/*      */       }
/*      */       else {
/* 1840 */         MSreq.addParameter("checkfor", "");
/*      */       }
/* 1842 */       if (request.getParameter("userName") != null)
/*      */       {
/* 1844 */         MSreq.addParameter("userid", request.getParameter("userName"));
/*      */       }
/* 1846 */       if (request.getParameter("errorIfMatch") != null)
/*      */       {
/* 1848 */         MSreq.addParameter("errorcontent", request.getParameter("errorIfMatch"));
/*      */       }
/*      */       else
/*      */       {
/* 1852 */         MSreq.addParameter("errorcontent", "");
/*      */       }
/* 1854 */       if ((request.getParameter("verifyError") != null) && (request.getParameter("verifyError").equals("true")))
/*      */       {
/* 1856 */         MSreq.addParameter("verifyerror", "true");
/*      */       }
/*      */       else
/*      */       {
/* 1860 */         MSreq.addParameter("verifyerror", "");
/*      */       }
/* 1862 */       if (request.getParameter("requestParams") != null)
/*      */       {
/* 1864 */         MSreq.addParameter("requestparams", request.getParameter("requestParams"));
/*      */       }
/*      */       else
/*      */       {
/* 1868 */         MSreq.addParameter("requestparams", "");
/*      */       }
/*      */     }
/* 1871 */     if (montype.equalsIgnoreCase("SSLCertificateMonitor"))
/*      */     {
/*      */       try
/*      */       {
/* 1875 */         if ((request.getParameter("domain") == null) || (request.getParameter("domain").equals("")))
/*      */         {
/* 1877 */           AMLog.debug("REST API : The domain in request URL should not be empty.");
/* 1878 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.domain.notempty"), "4325");
/*      */         }
/*      */         
/*      */ 
/* 1882 */         if ((request.getParameter("isProxyNeeded") != null) && ((request.getParameter("isProxyNeeded").equals("Yes")) || (request.getParameter("isProxyNeeded").equals("true"))))
/*      */         {
/* 1884 */           MSreq.addParameter("isProxyNeeded", "Yes");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1889 */           MSreq.addParameter("isProxyNeeded", "No");
/*      */         }
/*      */         
/* 1892 */         if (("yes".equalsIgnoreCase(request.getParameter("hostNameError"))) || ("true".equalsIgnoreCase(request.getParameter("hostNameError")))) {
/* 1893 */           MSreq.addParameter("ignoreHostNameError", "Yes");
/*      */         } else {
/* 1895 */           MSreq.addParameter("ignoreHostNameError", "False");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1900 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1903 */     if ((montype.equalsIgnoreCase("File Monitor")) || (montype.equalsIgnoreCase("Directory Monitor")) || (montype.equalsIgnoreCase("File System Monitor")))
/*      */     {
/*      */ 
/* 1906 */       if ((request.getParameter("filepath") == null) || (request.getParameter("filepath").trim().equals(""))) {
/* 1907 */         AMLog.debug("REST API : File/Directory Name should be specified with Absolute Path.");
/* 1908 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.exact.path.text"), "4553");
/* 1909 */         return xmlString;
/*      */       }
/* 1911 */       String serverSite = request.getParameter("serversite");
/* 1912 */       String chooseHost = request.getParameter("choosehost");
/* 1913 */       serverSite = serverSite != null ? serverSite : "";
/* 1914 */       chooseHost = chooseHost != null ? chooseHost : "";
/* 1915 */       if ((serverSite.equalsIgnoreCase("remote")) && (chooseHost.equalsIgnoreCase("-1"))) {
/* 1916 */         String host = request.getParameter("host");
/* 1917 */         if ((host == null) || (host.trim().length() == 0)) {
/* 1918 */           AMLog.debug("REST API : Hostname cannot be empty.");
/* 1919 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.hostnameemp.text"), "4555");
/* 1920 */           return xmlString;
/*      */         }
/* 1922 */         if (credentialid == null) {
/* 1923 */           String userName = request.getParameter("username");
/* 1924 */           if ((userName == null) || (userName.trim().length() == 0)) {
/* 1925 */             AMLog.debug("REST API : Username cannot be empty.");
/* 1926 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.usernameemp.text"), "4556");
/* 1927 */             return xmlString;
/*      */           }
/*      */         }
/* 1930 */         if ((request.getParameter("sshkey") != null) && (request.getParameter("sshkey").equalsIgnoreCase("on"))) {
/* 1931 */           String privateKey = request.getParameter("description");
/* 1932 */           if ((privateKey == null) || (privateKey.trim().length() == 0)) {
/* 1933 */             AMLog.debug("REST API : Private Key cannot be empty if Key Based Authentication is choosen");
/* 1934 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.sshkeyemp.text"), "4557");
/* 1935 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/* 1939 */       String contentChk = request.getParameter("contentChk");
/* 1940 */       if ((contentChk != null) && ((contentChk.equalsIgnoreCase("on")) || (contentChk.equalsIgnoreCase("yes")))) {
/* 1941 */         String content = request.getParameter("ccontent");
/* 1942 */         if ((content == null) || (content.trim().length() == 0)) {
/* 1943 */           AMLog.debug("REST API : content cannot be empty.");
/* 1944 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.alert.content.text"), "4554");
/* 1945 */           return xmlString;
/*      */         }
/* 1947 */         String clearCondition = request.getParameter("clearCondition");
/* 1948 */         if ((clearCondition != null) && ("1".equals(clearCondition))) {
/* 1949 */           String clearConditionRuleType = request.getParameter("clearConditionRuleType");
/* 1950 */           if ((clearConditionRuleType == null) || ((!clearConditionRuleType.equals("1")) && (!clearConditionRuleType.equals("2")) && (!clearConditionRuleType.equals("3")) && (!clearConditionRuleType.equals("4")) && (!clearConditionRuleType.equalsIgnoreCase("All")))) {
/* 1951 */             AMLog.debug("REST API : The value for clearConditionRuleType should be one of these values. 1 (or) 2 (or) 3 (or) 4 (or) All.");
/* 1952 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newfile.alert.clear.ruletype.invaid.text"), "4590");
/* 1953 */             return xmlString;
/*      */           }
/* 1955 */           String clearConditionContent = request.getParameter("clearConditionContent");
/* 1956 */           if ((clearConditionContent == null) || (clearConditionContent.trim().length() == 0)) {
/* 1957 */             AMLog.debug("REST API : content for clearing condition cannot be empty.");
/* 1958 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.alert.content.text"), "4554");
/* 1959 */             return xmlString;
/*      */           }
/* 1961 */           String clearConditionRegEx = request.getParameter("clearConditionContent");
/* 1962 */           if ((clearConditionRegEx != null) && (clearConditionRegEx.equalsIgnoreCase("on"))) {
/* 1963 */             clearConditionRegEx = "on";
/*      */           } else {
/* 1965 */             clearConditionRegEx = "off";
/*      */           }
/* 1967 */           amform.setClearCondition(1);
/* 1968 */           amform.setClearConditionContent(clearConditionContent);
/* 1969 */           amform.setClearConditionRegEx(clearConditionRegEx);
/* 1970 */           if (clearConditionRuleType.equalsIgnoreCase("All")) {
/* 1971 */             amform.setClearConditionRuleType("All");
/*      */           } else {
/* 1973 */             amform.setClearConditionRuleType("0");
/* 1974 */             amform.setClearConditionCountVal(clearConditionRuleType);
/*      */           }
/*      */         }
/*      */         else {
/* 1978 */           amform.setClearCondition(0);
/* 1979 */           amform.setClearConditionRuleType("0");
/* 1980 */           amform.setClearConditionCountVal("1");
/* 1981 */           amform.setClearConditionContent("");
/* 1982 */           amform.setClearConditionRegEx("off");
/*      */         }
/*      */       }
/*      */     }
/* 1986 */     String spParse = "";
/* 1987 */     if (montype.equalsIgnoreCase("Script Monitor"))
/*      */     {
/*      */       try {
/* 1990 */         String reqQryStr = request.getQueryString();
/* 1991 */         if ((reqQryStr != null) && (!reqQryStr.trim().equals("")) && (reqQryStr.indexOf("serverpath=") != -1)) {
/* 1992 */           String tstr1 = reqQryStr.substring(reqQryStr.indexOf("serverpath=")).trim();
/* 1993 */           spParse = tstr1.substring(tstr1.indexOf("serverpath=") + 11, tstr1.indexOf("&")).replaceAll("%20", " ");
/* 1994 */           spParse = org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(spParse);
/*      */         }
/* 1996 */         else if (request.getParameter("serverpath") != null) {
/* 1997 */           spParse = request.getParameter("serverpath");
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/* 2001 */         ex.printStackTrace();
/*      */       }
/* 2003 */       if ((spParse == null) || (spParse.trim().equals(""))) {
/* 2004 */         AMLog.debug("REST API : Script to be Monitored should not be empty.");
/* 2005 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.alert.scriptempty.text"), "4558");
/* 2006 */         return xmlString;
/*      */       }
/* 2008 */       if ((request.getParameter("workingdirectory") == null) || (request.getParameter("workingdirectory").trim().equals(""))) {
/* 2009 */         AMLog.debug("REST API : Working directory(directory from which the script is executed) should not be empty.");
/* 2010 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.alert.workingdirectoryempty.text"), "4559");
/* 2011 */         return xmlString;
/*      */       }
/* 2013 */       String serverSite = request.getParameter("serversite");
/* 2014 */       String chooseHost = request.getParameter("choosehost");
/* 2015 */       serverSite = serverSite != null ? serverSite : "";
/* 2016 */       chooseHost = chooseHost != null ? chooseHost : "";
/* 2017 */       if ((serverSite.equalsIgnoreCase("remote")) && (chooseHost.equalsIgnoreCase("-1"))) {
/* 2018 */         String host = request.getParameter("host");
/* 2019 */         if ((host == null) || (host.trim().length() == 0)) {
/* 2020 */           AMLog.debug("REST API : Hostname cannot be empty.");
/* 2021 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.hostnameemp.text"), "4555");
/* 2022 */           return xmlString;
/*      */         }
/* 2024 */         if (credentialid == null) {
/* 2025 */           String userName = request.getParameter("username");
/* 2026 */           if ((userName == null) || (userName.trim().length() == 0)) {
/* 2027 */             AMLog.debug("REST API : Username cannot be empty.");
/* 2028 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.usernameemp.text"), "4556");
/* 2029 */             return xmlString;
/*      */           }
/* 2031 */           if ((request.getParameter("sshkey") != null) && (request.getParameter("sshkey").equalsIgnoreCase("on"))) {
/* 2032 */             String privateKey = request.getParameter("description");
/* 2033 */             if ((privateKey == null) || (privateKey.trim().length() == 0)) {
/* 2034 */               AMLog.debug("REST API : Private Key cannot be empty if Key Based Authentication is choosen");
/* 2035 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.sshkeyemp.text"), "4557");
/* 2036 */               return xmlString;
/*      */             }
/*      */           }
/* 2039 */           String prompt = request.getParameter("prompt");
/* 2040 */           if ((prompt == null) || (prompt.trim().length() == 0)) {
/* 2041 */             AMLog.debug("REST API : Prompt cannot be empty if the new host is selected.");
/* 2042 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newmonitor.alert.prompt.empty.text"), "4564");
/* 2043 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/* 2047 */       String IsTablesInOPFile = request.getParameter("tablespresent");
/* 2048 */       if ((IsTablesInOPFile != null) && ((IsTablesInOPFile.equalsIgnoreCase("on")) || (IsTablesInOPFile.equalsIgnoreCase("yes")) || (IsTablesInOPFile.equalsIgnoreCase("true"))))
/*      */       {
/* 2050 */         String noOfTableStr = request.getParameter("table_row");
/* 2051 */         int noOfTables = 1;
/*      */         try {
/* 2053 */           noOfTables = Integer.parseInt(noOfTableStr);
/*      */         } catch (Exception ex) {
/* 2055 */           AMLog.debug("REST API : Value for parameter table_row (Number of tables) is invalid.");
/* 2056 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.alert.number.of.tables.invalid.text"), "4560");
/*      */         }
/*      */         
/* 2059 */         for (int i = 1; i <= noOfTables; i++) {
/* 2060 */           String tableName = request.getParameter("table" + i);
/* 2061 */           if ((tableName == null) || (tableName.trim().length() == 0)) {
/* 2062 */             AMLog.debug("REST API : Tablename cannot be empty.");
/* 2063 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.tablename.empty"), "4561");
/* 2064 */             return xmlString;
/*      */           }
/*      */           
/* 2067 */           String numericAttrbName = request.getParameter("numericatt" + i);
/* 2068 */           String stringAttrbName = request.getParameter("stringatt" + i);
/* 2069 */           if (((numericAttrbName == null) || (numericAttrbName.trim().length() == 0)) && ((stringAttrbName == null) || (stringAttrbName.trim().length() == 0))) {
/* 2070 */             AMLog.debug("REST API : Either String attributes or Numeric Attributes that are present in that table should be given for monitoring.");
/* 2071 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.enterattributes.text"), "4562");
/* 2072 */             return xmlString;
/*      */           }
/*      */           
/* 2075 */           String uniqueColumn = request.getParameter("pcatt" + i);
/* 2076 */           if ((uniqueColumn == null) || (uniqueColumn.trim().length() == 0)) {
/* 2077 */             AMLog.debug("REST API : Unique column for the table should  not be empty.");
/* 2078 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.newscript.uniquecolumn.empty"), "4563");
/* 2079 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2085 */     ArrayList error_array = new ArrayList();
/* 2086 */     ArrayList error_msg = new ArrayList();
/*      */     try
/*      */     {
/* 2089 */       if ((montype.equalsIgnoreCase("DB2")) || (montype.equalsIgnoreCase("Sybase")) || (montype.equalsIgnoreCase("MySQL")) || (montype.equalsIgnoreCase("MS SQL")) || (montype.equalsIgnoreCase("Servers")) || (montype.equalsIgnoreCase("Apache Server")) || (montype.equalsIgnoreCase("IIS Server")) || (montype.equalsIgnoreCase("PHP")) || (montype.equalsIgnoreCase("Web Server")) || (montype.equalsIgnoreCase("Java Runtime")) || (montype.equalsIgnoreCase("J2EE Web Transactions")) || (montype.equalsIgnoreCase("SNMP/Network Device")) || (montype.equalsIgnoreCase("JMX Applications")) || (montype.equalsIgnoreCase("WEBLOGIC SERVER")) || (montype.equalsIgnoreCase("Oracle Application Server")) || (montype.equalsIgnoreCase(".net")) || (montype.equalsIgnoreCase("WebLogic Integration")) || (montype.equalsIgnoreCase("SAP Server")))
/*      */       {
/* 2091 */         if ((request.getParameter("forceadd") != null) && (request.getParameter("forceadd").equals("true"))) {
/* 2092 */           MSreq.addParameter("forceadd", "true");
/*      */         }
/* 2094 */         if ((request.getParameter("addgivenname") != null) && (request.getParameter("addgivenname").equals("true"))) {
/* 2095 */           MSreq.addParameter("addgivenname", "true");
/*      */         }
/* 2097 */         if ((montype.equalsIgnoreCase("MS SQL")) && (("Yes".equalsIgnoreCase(request.getParameter("namedInstance"))) || ("true".equalsIgnoreCase(request.getParameter("namedInstance")))))
/*      */         {
/* 2099 */           MSreq.addParameter("instance", request.getParameter("instance"));
/*      */         }
/* 2101 */         am.configureHostDiscovery(null, amform, MSreq, response);
/* 2102 */         if (MSreq.getAttribute("resourceid") != null) {
/* 2103 */           request.setAttribute("resourceid", (String)MSreq.getAttribute("resourceid"));
/*      */         }
/*      */       } else {
/* 2106 */         if (montype.equalsIgnoreCase("rbm"))
/*      */         {
/* 2108 */           CreateRBMMonitor crbm = new CreateRBMMonitor();
/* 2109 */           crbm.createrbm(null, amform, MSreq, response);
/* 2110 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2111 */           if (error_array.get(1).equals("Success"))
/*      */           {
/* 2113 */             AMLog.debug("REST API : RBM SUCCESSS");
/* 2114 */             return MessageXML(request, response, "addmonitor", FormatUtil.getString("am.webclient.api.addmonitor.succmessage"));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2119 */           AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2120 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2121 */           AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2122 */           return xmlString;
/*      */         }
/*      */         
/* 2125 */         if (montype.equalsIgnoreCase("Web Service"))
/*      */         {
/* 2127 */           amform.setWSDLUrl(request.getParameter("WSDLUrl"));
/* 2128 */           amform.setPollInterval(pollInterval);
/* 2129 */           amform.setHaid(request.getParameter("haid"));
/* 2130 */           amform.setTimeout(timeOut);
/* 2131 */           amform.setUsername(request.getParameter("username"));
/* 2132 */           amform.setPassword(request.getParameter("password"));
/* 2133 */           amform.setEndPointUrl(request.getParameter("endPointUrl"));
/* 2134 */           amform.setCustomHeaders(request.getParameter("customHeaders"));
/* 2135 */           if ((request.getParameter("resFulWebservice") != null) && (request.getParameter("resFulWebservice").equals("true"))) {
/* 2136 */             amform.setResFulWebservice(true);
/*      */           }
/* 2138 */           if ((request.getParameter("proxy") != null) && (request.getParameter("proxy").equalsIgnoreCase("true")))
/*      */           {
/* 2140 */             amform.setProxyRequired(true);
/*      */           }
/*      */           try {
/* 2143 */             Class wsmActionClass = Class.forName("com.adventnet.appmanager.client.wsm.WSMAction");
/* 2144 */             Class[] argTypes = { ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class };
/* 2145 */             Method method = wsmActionClass.getMethod("addMonitor", argTypes);
/* 2146 */             Object[] argObjs = { null, amform, MSreq, response };
/* 2147 */             method.invoke(wsmActionClass.newInstance(), new Object[] { null, amform, MSreq, response });
/*      */           } catch (ClassNotFoundException cnf) {
/* 2149 */             cnf.printStackTrace();
/*      */           } catch (NoSuchMethodException nsm) {
/* 2151 */             nsm.printStackTrace();
/*      */           } catch (Throwable t) {
/* 2153 */             t.printStackTrace();
/*      */           }
/* 2155 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2156 */           AMLog.debug("REST API : TRY Error in adding monitor by API" + MSreq.getAttribute("discoverystatus"));
/* 2157 */           if (error_array.get(1).equals("Failed"))
/*      */           {
/* 2159 */             AMLog.debug("REST API : The emailid should not be empty.");
/* 2160 */             String errorMessage = (String)error_array.get(2);
/* 2161 */             if ((errorMessage != null) && (errorMessage.length() > 0)) {
/* 2162 */               errorMessage = addManagerServerUrlInProxyLink(errorMessage);
/*      */             }
/* 2164 */             return URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + errorMessage, "4444");
/*      */           }
/*      */           
/*      */         }
/* 2168 */         else if ((montype.equalsIgnoreCase("Ingres")) || (isConfMonitor))
/*      */         {
/* 2170 */           String isAgentEnabled = ConfMonitorConfiguration.getInstance().getTypeDescription(monType).getProperty("IS-AGENT-ENABLED");
/*      */           try {
/* 2172 */             if (!montype.equalsIgnoreCase("QueryMonitor")) {
/* 2173 */               String xmlStringtoreturn = validateRequestForConfMonitors(monType, request, response, MSreq);
/* 2174 */               if ((xmlStringtoreturn != null) && (!xmlStringtoreturn.trim().equals(""))) {
/* 2175 */                 return xmlStringtoreturn;
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 2180 */             e.printStackTrace();
/* 2181 */             return URITree.generateXML(request, response, FormatUtil.getString("Error in processing the URL"), "4445");
/*      */           }
/*      */           
/* 2184 */           if ((isAgentEnabled != null) && (isAgentEnabled.equalsIgnoreCase("YES")))
/*      */           {
/*      */ 
/*      */ 
/* 2188 */             String reason = checkEUMAgent(request, response, MSreq, amform, monType);
/* 2189 */             if (!reason.equalsIgnoreCase("success"))
/*      */             {
/* 2191 */               xmlString = reason;
/* 2192 */               AMLog.debug("REST API : Error in adding monitor by API- CHECKEUMAGENT FAILED " + reason);
/* 2193 */               return xmlString;
/*      */             }
/* 2195 */             MSreq.addParameter("UT", "" + System.currentTimeMillis());
/* 2196 */             MSreq.addParameter("isParent", "false");
/*      */           }
/* 2198 */           if ((request.getParameter("forceadd") != null) && (request.getParameter("forceadd").equals("true"))) {
/* 2199 */             MSreq.addParameter("forceadd", "true");
/*      */           }
/* 2201 */           NewMonitorConf nmc = new NewMonitorConf();
/* 2202 */           nmc.createMonitor(null, amform, MSreq, response);
/* 2203 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2204 */           if ((error_array.get(1).equals("Failed")) && (!error_array.get(1).equals("Success")))
/*      */           {
/* 2206 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2207 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2208 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2209 */             return xmlString;
/*      */           }
/*      */         }
/* 2212 */         else if (montype.equalsIgnoreCase("Windows Performance Counters"))
/*      */         {
/* 2214 */           MSreq.addParameter("choosehost", "-1");
/* 2215 */           amform.setChoosehost("-1");
/* 2216 */           if (request.getParameter("description") != null)
/*      */           {
/* 2218 */             MSreq.addParameter("description", request.getParameter("description"));
/* 2219 */             amform.setDescription(request.getParameter("description"));
/*      */           }
/*      */           else
/*      */           {
/* 2223 */             MSreq.addParameter("description", "");
/* 2224 */             amform.setDescription("");
/*      */           }
/*      */           
/* 2227 */           if ((request.getParameter("username") != null) && (request.getParameter("password") != null)) {
/* 2228 */             amform.setUsername(request.getParameter("username"));
/* 2229 */             amform.setPassword(request.getParameter("password"));
/*      */           }
/* 2231 */           amform.setPollInterval(pollInterval);
/*      */           
/* 2233 */           WMIPerfAction wmipa = new WMIPerfAction();
/* 2234 */           wmipa.createWMIPerfmonitor(null, amform, MSreq, response);
/* 2235 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2236 */           if (error_array.get(1).equals("Failed")) {
/* 2237 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2238 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2239 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2240 */             return xmlString;
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 2250 */         else if (montype.equalsIgnoreCase("SAP CCMS")) {
/*      */           try {
/* 2252 */             Class sapActionClass = Class.forName("com.adventnet.appmanager.client.sap.SAPAction");
/* 2253 */             Class[] argTypes = { ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class };
/* 2254 */             Method method = sapActionClass.getMethod("createCCMSMonitors", argTypes);
/* 2255 */             Object[] argObjs = { null, amform, MSreq, response };
/* 2256 */             method.invoke(sapActionClass.newInstance(), new Object[] { null, amform, MSreq, response });
/*      */           } catch (ClassNotFoundException cnf) {
/* 2258 */             cnf.printStackTrace();
/*      */           } catch (NoSuchMethodException nsm) {
/* 2260 */             nsm.printStackTrace();
/*      */           } catch (Throwable t) {
/* 2262 */             t.printStackTrace();
/*      */           }
/* 2264 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2265 */           if (!error_array.get(1).equals("Success"))
/*      */           {
/* 2267 */             AMLog.debug("REST API : SAP CCMS Error: " + error_array.get(2));
/* 2268 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2269 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2270 */             return xmlString;
/*      */           }
/* 2272 */         } else if ((montype.equalsIgnoreCase("File Monitor")) || (montype.equalsIgnoreCase("Directory Monitor")) || (montype.equalsIgnoreCase("File System Monitor")))
/*      */         {
/* 2274 */           int timeval = 10;
/* 2275 */           int timeout = 60;
/*      */           try {
/* 2277 */             timeval = Integer.parseInt(request.getParameter("timeval"));
/*      */           } catch (Exception ex) {}
/*      */           try {
/* 2280 */             timeout = Integer.parseInt(request.getParameter("timeout"));
/*      */           }
/*      */           catch (Exception ex) {}
/*      */           
/* 2284 */           if (montype.equalsIgnoreCase("File Monitor")) {
/* 2285 */             amform.setMtype("file");
/* 2286 */           } else if (montype.equalsIgnoreCase("Directory Monitor")) {
/* 2287 */             amform.setMtype("directory");
/*      */           } else {
/* 2289 */             amform.setMtype(request.getParameter("mtype"));
/*      */           }
/* 2291 */           amform.setServersite(request.getParameter("serversite"));
/* 2292 */           amform.setFilepath(request.getParameter("filepath"));
/*      */           
/*      */ 
/* 2295 */           amform.setChoosehost(request.getParameter("choosehost"));
/* 2296 */           String monitoringmode = request.getParameter("monitoringmode");
/* 2297 */           amform.setMonitoringmode(monitoringmode);
/* 2298 */           amform.setHost(request.getParameter("host"));
/* 2299 */           Long credentialID = Long.valueOf(0L);
/* 2300 */           boolean isCredManager = false;
/* 2301 */           if (credentialid != null) {
/* 2302 */             credentialID = new Long(credentialid);
/* 2303 */             isCredManager = true;
/*      */           }
/*      */           
/* 2306 */           if (isCredManager) {
/* 2307 */             CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2308 */             Properties credentialProps = null;
/* 2309 */             credentialProps = credUtil.rowNameVsValue(credentialID.longValue());
/* 2310 */             amform.setUsername(credentialProps.getProperty("username"));
/* 2311 */             if (credentialProps.getProperty("password") != null)
/*      */             {
/* 2313 */               amform.setPassword(credentialProps.getProperty("password"));
/*      */             }
/* 2315 */             if (credentialProps.getProperty("prompt") != null)
/*      */             {
/* 2317 */               amform.setPrompt(credentialProps.getProperty("prompt"));
/*      */             }
/* 2319 */             if (("ssh".equalsIgnoreCase(monitoringmode)) && (credentialProps.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */             {
/* 2321 */               amform.setPassphrase(credentialProps.getProperty("passPhrase"));
/*      */             }
/* 2323 */             amform.setIsCredManager(isCredManager);
/* 2324 */             amform.setCredentialID(credentialid);
/*      */           }
/*      */           else {
/* 2327 */             amform.setUsername(request.getParameter("username"));
/* 2328 */             if (request.getParameter("password") != null) {
/* 2329 */               amform.setPassword(request.getParameter("password"));
/*      */             }
/* 2331 */             String sshkey = request.getParameter("sshkey");
/* 2332 */             sshkey = sshkey == null ? request.getParameter("keybasedauth") : sshkey;
/* 2333 */             if ((sshkey != null) && ((sshkey.equalsIgnoreCase("on")) || (sshkey.equalsIgnoreCase("yes")))) {
/* 2334 */               amform.setSshkey(true);
/*      */             }
/* 2336 */             String privateKey = request.getParameter("description");
/* 2337 */             privateKey = privateKey == null ? request.getParameter("privatekey") : privateKey;
/* 2338 */             if ((privateKey != null) && (privateKey.trim().length() > 0)) {
/* 2339 */               amform.setDescription(privateKey);
/*      */             }
/* 2341 */             if (request.getParameter("passphrase") != null) {
/* 2342 */               amform.setPassphrase(request.getParameter("passphrase"));
/*      */             }
/* 2344 */             if (request.getParameter("prompt") != null) {
/* 2345 */               amform.setPrompt(request.getParameter("prompt"));
/*      */             }
/*      */           }
/* 2348 */           String port = request.getParameter("port");
/* 2349 */           if ((port != null) && (port.trim().length() > 0)) {
/* 2350 */             amform.setPort(port);
/*      */           } else {
/* 2352 */             amform.setPort("23");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2357 */           String contentChk = request.getParameter("contentChk");
/* 2358 */           if ((contentChk != null) && ((contentChk.equalsIgnoreCase("on")) || (contentChk.equalsIgnoreCase("yes")))) {
/* 2359 */             amform.setContentChk("on");
/*      */           }
/* 2361 */           amform.setCcontent(request.getParameter("ccontent"));
/*      */           
/* 2363 */           String fileCheckType = request.getParameter("fileCheckType");
/* 2364 */           if ((fileCheckType != null) && ((fileCheckType.equalsIgnoreCase("whole")) || (fileCheckType.equalsIgnoreCase("1")))) {
/* 2365 */             amform.setFileCheckType(1);
/*      */           } else {
/* 2367 */             amform.setFileCheckType(0);
/*      */           }
/*      */           
/* 2370 */           String selectStatusType = request.getParameter("selectStatusType");
/* 2371 */           if ((selectStatusType != null) && ((selectStatusType.equalsIgnoreCase("up")) || (selectStatusType.equalsIgnoreCase("1")))) {
/* 2372 */             amform.setSelectStatusType(1);
/*      */           } else {
/* 2374 */             amform.setSelectStatusType(0);
/*      */           }
/*      */           
/* 2377 */           String regexChk = request.getParameter("regexChk");
/* 2378 */           if (("on".equalsIgnoreCase(regexChk)) || ("yes".equalsIgnoreCase(regexChk))) {
/* 2379 */             amform.setRegexChk("on");
/*      */           }
/*      */           else {
/* 2382 */             String selectRuleType = request.getParameter("selectRuleType");
/* 2383 */             if ((selectRuleType != null) && ((selectRuleType.equalsIgnoreCase("all")) || (selectRuleType.equalsIgnoreCase("1")))) {
/* 2384 */               amform.setSelectRuleType("1");
/*      */             } else {
/* 2386 */               amform.setSelectRuleType("0");
/*      */             }
/*      */             
/* 2389 */             amform.setCountval(request.getParameter("countval"));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2394 */           String fileDirAge = request.getParameter("fileDirAge");
/* 2395 */           if ((fileDirAge != null) && ((fileDirAge.equalsIgnoreCase("on")) || (fileDirAge.equalsIgnoreCase("yes")))) {
/* 2396 */             amform.setFileDirAge("on");
/*      */           }
/*      */           
/* 2399 */           String selectMonStatus = request.getParameter("selectMonStatus");
/* 2400 */           if ((selectMonStatus != null) && ((selectMonStatus.equalsIgnoreCase("up")) || (selectMonStatus.equalsIgnoreCase("1")))) {
/* 2401 */             amform.setSelectMonStatus(1);
/*      */           } else {
/* 2403 */             amform.setSelectMonStatus(0);
/*      */           }
/*      */           
/* 2406 */           String selectChangeType = request.getParameter("selectChangeType");
/* 2407 */           if ((selectChangeType != null) && ((selectChangeType.equalsIgnoreCase("modified")) || (selectChangeType.equalsIgnoreCase("1")))) {
/* 2408 */             amform.setSelectChangeType(1);
/*      */           } else {
/* 2410 */             amform.setSelectChangeType(0);
/*      */           }
/* 2412 */           amform.setTimeval(timeval);
/* 2413 */           amform.setTimeUnit(request.getParameter("timeUnit"));
/*      */           
/*      */ 
/* 2416 */           amform.setSubDirCntChk(request.getParameter("subDirCntChk"));
/* 2417 */           amform.setTimeout(timeout);
/* 2418 */           amform.setPollInterval(pollInterval);
/*      */           
/* 2420 */           CreateScriptMonitor fileDir = new CreateScriptMonitor();
/* 2421 */           fileDir.filemon(null, amform, MSreq, response);
/* 2422 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2423 */           if ((error_array.get(1).equals("Failed")) && (!error_array.get(1).equals("Success")))
/*      */           {
/* 2425 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2426 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2427 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2428 */             return xmlString;
/*      */           }
/*      */         }
/* 2431 */         else if (montype.equalsIgnoreCase("Script Monitor"))
/*      */         {
/* 2433 */           int timeout = 30;
/*      */           try {
/* 2435 */             timeout = Integer.parseInt(request.getParameter("timeout"));
/*      */           }
/*      */           catch (Exception ex) {}
/*      */           
/* 2439 */           amform.setType("Script Monitor");
/* 2440 */           String serverPath = spParse;
/* 2441 */           serverPath = serverPath != null ? serverPath : "";
/* 2442 */           if ((serverPath != null) && (serverPath.trim().length() > 0)) {
/* 2443 */             amform.setServerpath(serverPath);
/*      */           }
/* 2445 */           String workingDirectory = request.getParameter("workingdirectory");
/* 2446 */           workingDirectory = workingDirectory != null ? workingDirectory : "";
/* 2447 */           if ((workingDirectory != null) && (workingDirectory.trim().length() > 0)) {
/* 2448 */             amform.setWorkingdirectory(workingDirectory);
/*      */           }
/*      */           
/* 2451 */           String isCommand = request.getParameter("isCommand");
/* 2452 */           if (isCommand != null) {
/* 2453 */             amform.setisCommand(Boolean.valueOf(isCommand).booleanValue());
/*      */           }
/*      */           
/*      */ 
/* 2457 */           String serverSite = request.getParameter("serversite");
/* 2458 */           serverSite = serverSite != null ? serverSite : "";
/* 2459 */           if ((serverSite != null) && (serverSite.trim().length() > 0)) {
/* 2460 */             amform.setServersite(serverSite);
/*      */           }
/* 2462 */           String chooseHost = request.getParameter("choosehost");
/* 2463 */           chooseHost = chooseHost != null ? chooseHost : "";
/* 2464 */           if ((chooseHost != null) && (chooseHost.trim().length() > 0)) {
/* 2465 */             amform.setChoosehost(chooseHost);
/*      */           }
/* 2467 */           amform.setHost(request.getParameter("host"));
/* 2468 */           String monitoringMode = request.getParameter("monitoringmode");
/* 2469 */           monitoringMode = monitoringMode != null ? monitoringMode : "";
/* 2470 */           if ((monitoringMode != null) && (monitoringMode.trim().length() > 0)) {
/* 2471 */             amform.setMonitoringmode(monitoringMode);
/*      */           }
/* 2473 */           Long credentialID = Long.valueOf(0L);
/* 2474 */           boolean isCredManager = false;
/* 2475 */           if (credentialid != null) {
/* 2476 */             credentialID = new Long(credentialid);
/* 2477 */             isCredManager = true;
/*      */           }
/* 2479 */           if (isCredManager)
/*      */           {
/* 2481 */             CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2482 */             Properties credentialProps = null;
/* 2483 */             credentialProps = credUtil.rowNameVsValue(credentialID.longValue());
/* 2484 */             amform.setUsername(credentialProps.getProperty("username"));
/* 2485 */             if (credentialProps.getProperty("password") != null)
/*      */             {
/* 2487 */               amform.setPassword(credentialProps.getProperty("password"));
/*      */             }
/* 2489 */             if (credentialProps.getProperty("prompt") != null)
/*      */             {
/* 2491 */               amform.setPrompt(credentialProps.getProperty("prompt"));
/*      */             }
/* 2493 */             if (("ssh".equalsIgnoreCase(monitoringMode)) && (credentialProps.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */             {
/* 2495 */               amform.setPassphrase(credentialProps.getProperty("passPhrase"));
/*      */             }
/* 2497 */             amform.setIsCredManager(true);
/* 2498 */             amform.setCredentialID(credentialid);
/*      */           }
/*      */           else {
/* 2501 */             amform.setUsername(request.getParameter("username"));
/* 2502 */             if (request.getParameter("password") != null) {
/* 2503 */               amform.setPassword(request.getParameter("password"));
/*      */             }
/* 2505 */             String sshkey = request.getParameter("sshkey");
/* 2506 */             sshkey = sshkey == null ? request.getParameter("keybasedauth") : sshkey;
/* 2507 */             if ((sshkey != null) && ((sshkey.equalsIgnoreCase("on")) || (sshkey.equalsIgnoreCase("yes")))) {
/* 2508 */               amform.setSshkey(true);
/*      */             }
/* 2510 */             String privateKey = request.getParameter("description");
/* 2511 */             privateKey = privateKey != null ? privateKey : request.getParameter("privatekey");
/* 2512 */             if ((privateKey != null) && (privateKey.trim().length() > 0)) {
/* 2513 */               amform.setDescription(privateKey);
/*      */             }
/* 2515 */             if (request.getParameter("passphrase") != null) {
/* 2516 */               amform.setPassphrase(request.getParameter("passphrase"));
/*      */             }
/* 2518 */             if (request.getParameter("prompt") != null) {
/* 2519 */               amform.setPrompt(request.getParameter("prompt"));
/*      */             }
/*      */           }
/* 2522 */           String port = request.getParameter("port");
/* 2523 */           if ((port != null) && (port.trim().length() > 0)) {
/* 2524 */             amform.setPort(port);
/*      */           } else {
/* 2526 */             amform.setPort("23");
/*      */           }
/* 2528 */           String execMode = request.getParameter("mode");
/* 2529 */           execMode = execMode != null ? execMode : "";
/* 2530 */           if ((execMode != null) && (execMode.trim().length() > 0)) {
/* 2531 */             amform.setMode(execMode);
/*      */           } else {
/* 2533 */             amform.setMode("sh");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2538 */           String outputSettings = request.getParameter("opfile");
/* 2539 */           if ((outputSettings != null) && ((outputSettings.equalsIgnoreCase("on")) || (outputSettings.equalsIgnoreCase("yes")) || (outputSettings.equalsIgnoreCase("true")))) {
/* 2540 */             amform.setOpfile(true);
/* 2541 */             MSreq.addParameter("isFile", request.getParameter("opfile"));
/*      */           }
/* 2543 */           String outputFile = request.getParameter("outputfile");
/* 2544 */           outputFile = outputFile != null ? outputFile : "";
/* 2545 */           if ((outputFile.length() > 0) && (outputFile.trim().length() > 0)) {
/* 2546 */             amform.setOutputfile(outputFile);
/*      */           } else {
/* 2548 */             amform.setOutputfile("");
/*      */           }
/* 2550 */           String stringAttbs = request.getParameter("string_att");
/* 2551 */           stringAttbs = stringAttbs != null ? stringAttbs : "";
/* 2552 */           if ((stringAttbs.length() > 0) && (stringAttbs.trim().length() > 0)) {
/* 2553 */             stringAttbs = stringAttbs.replaceAll(",", "\n");
/* 2554 */             amform.setString_att(stringAttbs);
/*      */           }
/* 2556 */           String numericAttrbs = request.getParameter("numeric_att");
/* 2557 */           numericAttrbs = numericAttrbs != null ? numericAttrbs : "";
/* 2558 */           if ((numericAttrbs.length() > 0) && (numericAttrbs.trim().length() > 0)) {
/* 2559 */             numericAttrbs = numericAttrbs.replaceAll(",", "\n");
/* 2560 */             amform.setNumeric_att(numericAttrbs);
/*      */           }
/* 2562 */           String delimiter = request.getParameter("delimiter");
/* 2563 */           delimiter = delimiter != null ? delimiter : "";
/* 2564 */           if ((delimiter.length() > 0) && (delimiter.trim().length() > 0)) {
/* 2565 */             amform.setDelimiter(delimiter);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2576 */           String message = request.getParameter("message");
/* 2577 */           message = message != null ? message : "";
/* 2578 */           amform.setMessage(message);
/* 2579 */           amform.setTimeout(timeout);
/* 2580 */           amform.setPollInterval(pollInterval);
/*      */           
/* 2582 */           CreateScriptMonitor scriptMon = new CreateScriptMonitor();
/* 2583 */           scriptMon.createscript(null, amform, MSreq, response);
/* 2584 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2585 */           if ((error_array.get(1).equals("Failed")) && (!error_array.get(1).equals("Success")))
/*      */           {
/* 2587 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2588 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2589 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2590 */             return xmlString;
/*      */           }
/* 2592 */         } else if (montype.equalsIgnoreCase("vCenter"))
/*      */         {
/* 2594 */           HAIDManagerAction ha = new HAIDManagerAction();
/* 2595 */           ha.configureVCenterDiscovery(null, null, MSreq, response);
/* 2596 */           if (MSreq.getAttribute("resourceid") != null) {
/* 2597 */             request.setAttribute("resourceid", (String)MSreq.getAttribute("resourceid"));
/*      */           }
/* 2599 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2600 */           if (error_array.get(1).equals("Failed")) {
/* 2601 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2602 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2603 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2604 */             return xmlString;
/*      */           }
/* 2606 */         } else if (montype.equalsIgnoreCase("VMwareView"))
/*      */         {
/* 2608 */           HAIDManagerAction ha = new HAIDManagerAction();
/* 2609 */           ha.configureHorizonDiscovery(null, null, MSreq, response);
/* 2610 */           if (MSreq.getAttribute("resourceid") != null) {
/* 2611 */             request.setAttribute("resourceid", (String)MSreq.getAttribute("resourceid"));
/*      */           }
/* 2613 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2614 */           if (error_array.get(1).equals("Failed")) {
/* 2615 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2616 */             if (FormatUtil.getString("am.webclient.newresourcetypes.vmware.view.broker.text").equals(error_array.get(2)))
/*      */             {
/* 2618 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4574");
/*      */             }
/*      */             else
/*      */             {
/* 2622 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2623 */               AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/*      */             }
/* 2625 */             return xmlString;
/*      */           }
/* 2627 */         } else if (montype.equalsIgnoreCase("UrlMonitor"))
/*      */         {
/* 2629 */           CreateUrlMonitor creUrlMon = new CreateUrlMonitor();
/* 2630 */           creUrlMon.createUrlMonitor(null, null, MSreq, response);
/* 2631 */           if (MSreq.getAttribute("resourceid") != null) {
/* 2632 */             request.setAttribute("resourceid", (String)MSreq.getAttribute("resourceid"));
/*      */           }
/* 2634 */           error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2635 */           if (error_array.get(1).equals("Failed")) {
/* 2636 */             AMLog.debug("REST API : Error: " + error_array.get(2));
/* 2637 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/* 2638 */             AMLog.debug("REST API : Error in adding monitor by API" + error_array.get(2));
/* 2639 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2645 */       ex.printStackTrace();
/* 2646 */       AMLog.debug("REST API : Error in adding monitor by API" + error_array);
/* 2647 */       if (montype.equalsIgnoreCase("Web Service"))
/*      */       {
/* 2649 */         if (error_array.get(1).equals("Failed"))
/*      */         {
/* 2651 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + error_array.get(2), "4444");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2656 */         error_array = (ArrayList)MSreq.getAttribute("discoverystatus");
/* 2657 */         error_msg = (ArrayList)error_array.get(0);
/* 2658 */         String errStr = (String)error_msg.get(3);
/* 2659 */         if ("Tomcat server".equalsIgnoreCase(montype)) {
/* 2660 */           errStr = removeHtmlTagFromErrMsg(montype, errStr);
/*      */         }
/* 2662 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + errStr, "4444");
/* 2663 */         AMLog.debug("REST API : Error in adding monitor by API" + errStr);
/*      */       }
/*      */       
/* 2666 */       return xmlString;
/*      */     }
/* 2668 */     if (MSreq.getAttribute("resourceid") != null) {
/* 2669 */       request.setAttribute("resourceid", MSreq.getAttribute("resourceid") + "");
/* 2670 */       if ((request.getParameter("label") != null) && (!request.getParameter("label").equals(""))) {
/* 2671 */         insertLabel(request.getParameter("label"), (String)MSreq.getAttribute("resourceid"));
/*      */       }
/*      */     }
/*      */     try {
/* 2675 */       Object obj = MSreq.getAttribute("discoverystatus");
/* 2676 */       if ((obj instanceof ArrayList)) {
/* 2677 */         error_array = (ArrayList)obj;
/* 2678 */         obj = error_array.get(0);
/* 2679 */         if ((obj instanceof ArrayList)) {
/* 2680 */           ArrayList returnMessageList = (ArrayList)obj;
/* 2681 */           if (returnMessageList.get(2).equals("Success"))
/*      */           {
/* 2683 */             AMLog.debug("REST API : " + montype + " SUCCESSS");
/* 2684 */             return MessageXML(request, response, "addmonitor", FormatUtil.getString("am.webclient.api.addmonitor.succmessage"));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2689 */           StringBuilder errorMessage = new StringBuilder();
/* 2690 */           errorMessage.append(returnMessageList.get(3));
/* 2691 */           if (returnMessageList.size() > 4) {
/* 2692 */             errorMessage.append("<BR>").append(returnMessageList.get(4));
/*      */           }
/* 2694 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + errorMessage.toString(), "4444");
/* 2695 */           AMLog.debug("REST API : Error in adding monitor by API" + errorMessage.toString());
/* 2696 */           return xmlString;
/*      */         }
/* 2698 */         if (((obj instanceof String)) && (((String)error_array.get(1)).equals("Exist"))) {
/* 2699 */           AMLog.debug("REST API : Exist: " + error_array.get(2));
/* 2700 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.monitor.alreadypresent"), "4509", true);
/*      */         }
/*      */         
/* 2703 */         xmlString = MessageXML(request, response, "addmonitor", FormatUtil.getString("am.webclient.api.addmonitor.succmessage"));
/*      */       }
/*      */       else {
/* 2706 */         xmlString = MessageXML(request, response, "addmonitor", FormatUtil.getString("am.webclient.api.addmonitor.succmessage"));
/*      */       }
/*      */     } catch (Throwable th) {
/* 2709 */       th.printStackTrace();
/*      */     }
/* 2711 */     return xmlString;
/*      */   }
/*      */   
/*      */   public static void insertLabel(String labelName, String resourceID) {
/* 2715 */     String label = null;
/* 2716 */     ResultSet rs = null;
/*      */     try {
/* 2718 */       String query = "select VALUEID from AM_MYFIELDS_TEMPLATEDATA where FIELDID=1 and VALUE='" + labelName + "'";
/* 2719 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2720 */       if (rs.next()) {
/* 2721 */         label = rs.getString("VALUEID");
/*      */       }
/* 2723 */       AMConnectionPool.closeStatement(rs);
/* 2724 */       if (label != null) {
/* 2725 */         query = "select RESOURCEID from AM_MYFIELDS_LABELDATA where RESOURCEID='" + resourceID + "'";
/* 2726 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2727 */         if (rs.next()) {
/* 2728 */           query = "update AM_MYFIELDS_LABELDATA set VALUEID=" + label + " where RESOURCEID='" + resourceID + "'";
/* 2729 */           AMConnectionPool.executeUpdateStmt(query);
/* 2730 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*      */         } else {
/* 2732 */           query = "insert into AM_MYFIELDS_LABELDATA values(" + com.adventnet.appmanager.db.DBQueryUtil.getIncrementedID("RELATIONID", "AM_MYFIELDS_LABELDATA") + "," + resourceID + "," + label + ")";
/* 2733 */           AMConnectionPool.executeUpdateStmt(query);
/* 2734 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*      */         }
/*      */       }
/*      */       return;
/* 2738 */     } catch (Exception exp) { exp.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 2741 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ef) {
/* 2743 */         ef.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String removeHtmlTagFromErrMsg(String montype, String errStr) throws Exception {
/*      */     try {
/* 2750 */       String finalErrStr = errStr;
/* 2751 */       if ("Tomcat server".equalsIgnoreCase(montype)) {
/* 2752 */         if ((errStr.indexOf("<a ") != -1) && (errStr.indexOf("</a>") != -1))
/*      */         {
/* 2754 */           String errStr1 = errStr.substring(errStr.indexOf("<a "), errStr.indexOf("</a>"));
/* 2755 */           String errStrMid = errStr1.lastIndexOf(">") != -1 ? errStr1.substring(errStr1.lastIndexOf(">") + 1) : "";
/* 2756 */           String errStrStart = errStr.substring(0, errStr.indexOf("<a "));
/* 2757 */           String errStrEnd = errStr.substring(errStr.indexOf("</a>") + 4);
/* 2758 */           finalErrStr = errStrStart + errStrMid + errStrEnd;
/*      */         }
/* 2760 */         if (errStr.indexOf("<br>") == -1) {} }
/* 2761 */       return errStr.replaceAll("<br>", " ");
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2766 */       ex.printStackTrace();
/*      */     }
/* 2768 */     return errStr;
/*      */   }
/*      */   
/*      */   public static String MessageXML(HttpServletRequest request, HttpServletResponse response, String method, String message) throws Exception
/*      */   {
/* 2773 */     String uri = request.getRequestURI();
/* 2774 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 2775 */     if (isJsonFormat) {
/* 2776 */       response.setContentType("text/plain; charset=UTF-8");
/* 2777 */       String respCode = "4000";
/* 2778 */       JSONObject jsonOutput = new JSONObject();
/* 2779 */       JSONObject jsonResponse = new JSONObject();
/* 2780 */       JSONArray jsonResultArray = new JSONArray();
/* 2781 */       if (method.equalsIgnoreCase("addcredential")) {
/* 2782 */         respCode = "5000";
/*      */       }
/*      */       try {
/* 2785 */         jsonResultArray.put(new JSONObject().put("message", message));
/*      */       } catch (Exception e) {
/* 2787 */         AMLog.debug("REST API : Error in generating JSON Feed!");
/* 2788 */         respCode = "40xx";
/* 2789 */         jsonResultArray = new JSONArray();
/* 2790 */         jsonResultArray.put(new JSONObject().put("message", FormatUtil.getString("Error in generating JSON Feed!")));
/* 2791 */         e.printStackTrace();
/*      */       }
/* 2793 */       jsonOutput.put("response-code", respCode);
/* 2794 */       jsonResponse.put("uri", uri);
/* 2795 */       jsonResponse.put("result", jsonResultArray);
/* 2796 */       jsonOutput.put("response", jsonResponse);
/* 2797 */       if (request.getAttribute("resourceid") != null) {
/* 2798 */         jsonOutput.put("resourceid", (String)request.getAttribute("resourceid"));
/*      */       }
/* 2800 */       String toReturn = jsonOutput.toString();
/* 2801 */       return toReturn;
/*      */     }
/*      */     
/*      */ 
/* 2805 */     String xmlString = "";
/* 2806 */     response.setContentType("text/xml; charset=UTF-8");
/* 2807 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 2808 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 2809 */     Document doc = docBuilder.newDocument();
/* 2810 */     Element rootNode = doc.createElement("AppManager-response");
/* 2811 */     rootNode.setAttribute("uri", uri);
/* 2812 */     doc.appendChild(rootNode);
/* 2813 */     Element resultNode = doc.createElement("result");
/* 2814 */     rootNode.appendChild(resultNode);
/*      */     
/* 2816 */     Element responseNode = doc.createElement("response");
/* 2817 */     if (request.getAttribute("resourceid") != null) {
/* 2818 */       responseNode.setAttribute("resourceid", (String)request.getAttribute("resourceid"));
/*      */     }
/* 2820 */     responseNode.setAttribute("response-code", "4000");
/* 2821 */     if (method.equalsIgnoreCase("addcredential"))
/*      */     {
/* 2823 */       responseNode.setAttribute("response-code", "5000");
/*      */     }
/* 2825 */     resultNode.appendChild(responseNode);
/*      */     
/* 2827 */     Element nodeMonitor = doc.createElement("message");
/* 2828 */     org.w3c.dom.Text textMSS = doc.createTextNode(message);
/* 2829 */     nodeMonitor.appendChild(textMSS);
/* 2830 */     responseNode.appendChild(nodeMonitor);
/*      */     
/* 2832 */     TransformerFactory factory = TransformerFactory.newInstance();
/* 2833 */     Transformer transformer = factory.newTransformer();
/* 2834 */     transformer.setOutputProperty("indent", "yes");
/* 2835 */     StringWriter sw = new StringWriter();
/* 2836 */     StreamResult result = new StreamResult(sw);
/* 2837 */     DOMSource source = new DOMSource(doc);
/* 2838 */     transformer.transform(source, result);
/* 2839 */     xmlString = sw.toString();
/* 2840 */     return xmlString;
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
/*      */   public static String checkEUMAgent(HttpServletRequest request, HttpServletResponse response, MockHttpServletRequest mReq, AMActionForm amfm, String monType)
/*      */     throws Exception
/*      */   {
/* 2883 */     AMLog.debug("REST API : inside checkEUMAgent");
/* 2884 */     String eumAgentIDs = request.getParameter("eumAgentsID");
/* 2885 */     eumAgentIDs = eumAgentIDs == null ? request.getParameter("selectedAgents") : eumAgentIDs;
/* 2886 */     String eumAgents = request.getParameter("eumAgents");
/* 2887 */     String rbmAgentIds = request.getParameter("rbmagentID");
/* 2888 */     String runOnServer = request.getParameter("runOnServer");
/* 2889 */     String res = "";
/* 2890 */     if ((runOnServer != null) && (runOnServer.equals("true")))
/*      */     {
/* 2892 */       mReq.addParameter("runOnServer", "true");
/*      */     }
/* 2894 */     if ((monType.equalsIgnoreCase("rbm")) && (eumAgentIDs == null))
/*      */     {
/*      */ 
/* 2897 */       eumAgentIDs = rbmAgentIds;
/*      */     }
/* 2899 */     if ((eumAgentIDs == null) && (eumAgents == null))
/*      */     {
/*      */ 
/*      */ 
/* 2903 */       if (monType.equalsIgnoreCase("RBM"))
/*      */       {
/* 2905 */         res = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eum.restapi.agentinvalid"), "4300");
/* 2906 */         AMLog.debug("REST API : inside checkEUMAgent AgentStr - " + res);
/* 2907 */         return res;
/*      */       }
/*      */       
/*      */ 
/* 2911 */       mReq.addParameter("runOnServer", "true");
/* 2912 */       AMLog.debug("REST API : inside checkEUMAgent - RUN On Server");
/* 2913 */       return "success";
/*      */     }
/*      */     
/*      */ 
/* 2917 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2918 */     ResultSet rs = null;
/*      */     
/* 2920 */     String checkquery = "";
/* 2921 */     String[] agentid = null;
/* 2922 */     String[] agentsStr = null;
/* 2923 */     if ((eumAgentIDs != null) && (!eumAgentIDs.equals("")))
/*      */     {
/* 2925 */       agentsStr = eumAgentIDs.split(",");
/* 2926 */       AMLog.debug("REST API : inside checkEUMAgent AgentStr - " + eumAgentIDs);
/* 2927 */       checkquery = "select DISPLAYNAME,AGENTID,DESCRIPTION from AM_RBMAGENTDATA where AGENTID in (" + eumAgentIDs + ") and AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ")";
/*      */     }
/* 2929 */     else if ((eumAgents != null) && (!eumAgents.equals("")))
/*      */     {
/* 2931 */       AMLog.debug("REST API : inside checkEUMAgent AgentStr - " + eumAgents);
/* 2932 */       agentsStr = eumAgents.split(",");
/* 2933 */       String temp = "'-1'";
/* 2934 */       for (int i = 0; i < agentsStr.length; i++)
/*      */       {
/* 2936 */         temp = temp + "," + "'" + agentsStr[i] + "'";
/*      */       }
/* 2938 */       checkquery = "select DISPLAYNAME,AGENTID,DESCRIPTION from AM_RBMAGENTDATA where DISPLAYNAME in (" + temp + ") and AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ")";
/*      */     }
/*      */     else
/*      */     {
/* 2942 */       res = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eum.restapi.agentinvalid"), "4300");
/*      */       
/* 2944 */       return res;
/*      */     }
/*      */     
/* 2947 */     int count = 0;
/* 2948 */     agentid = new String[agentsStr.length];
/*      */     
/*      */     try
/*      */     {
/* 2952 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 2953 */       String desc = "";
/* 2954 */       while (rs.next())
/*      */       {
/* 2956 */         desc = rs.getString(3);
/* 2957 */         if ((monType.equalsIgnoreCase("rbm")) && (desc.toLowerCase().indexOf("win") < 0))
/*      */         {
/* 2959 */           String agtName = rs.getString(1) + "/" + rs.getInt(2);
/* 2960 */           AMLog.debug("REST API : Agent mentioned in the URL Request not supported for RBM Monitor. RBM Moniotrs supports only Agents running in Windows.");
/* 2961 */           res = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eum.restapi.rbm.linuagentnotsupport", new String[] { agtName }), "4342");
/* 2962 */           return res;
/*      */         }
/* 2964 */         agentid[count] = ("" + rs.getInt(2));
/*      */         
/* 2966 */         count++;
/*      */       }
/* 2968 */       if ((agentsStr.length == 0) || (agentid.length != count))
/*      */       {
/* 2970 */         res = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eum.restapi.agentinvalidnames"), "4301");
/*      */ 
/*      */       }
/* 2973 */       else if (agentid.length == count)
/*      */       {
/* 2975 */         amfm.setSelectedAgents(agentid);
/* 2976 */         if (monType.equalsIgnoreCase("RBM"))
/*      */         {
/* 2978 */           for (int i = 0; i < agentid.length; i++)
/*      */           {
/* 2980 */             mReq.addParameter("selectedAgents", agentid[i]);
/*      */           }
/*      */         }
/* 2983 */         res = "success";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2988 */       ex.printStackTrace();
/* 2989 */       AMLog.debug("REST API : inside checkEUMAgent AgentStr - error : ");
/* 2990 */       res = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eum.restapi.agenterror", new String[] { ex.getMessage() }), "4302");
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2995 */       if (rs != null)
/*      */       {
/* 2997 */         rs.close();
/*      */       }
/*      */     }
/*      */     
/* 3001 */     return res;
/*      */   }
/*      */   
/*      */   public static boolean checkMGID(int mgID)
/*      */     throws Exception
/*      */   {
/* 3007 */     ResultSet rs = null;
/* 3008 */     boolean xmlString = false;
/*      */     try
/*      */     {
/* 3011 */       int haiID = 0;
/* 3012 */       String checkquery = "select RESOURCEID from AM_ManagedObject where TYPE='HAI' and RESOURCEID=" + mgID + "";
/* 3013 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3014 */       if (rs.next()) {
/* 3015 */         haiID = Integer.parseInt(rs.getString("RESOURCEID"));
/*      */       }
/* 3017 */       AMLog.debug("REST API : debug print:haiID: " + haiID + " agentID: " + mgID);
/* 3018 */       if ((haiID == mgID) && (mgID != 0))
/*      */       {
/* 3020 */         xmlString = true;
/*      */       }
/*      */       else
/*      */       {
/* 3024 */         xmlString = false;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3029 */       ex.printStackTrace();
/* 3030 */       xmlString = false;
/*      */     }
/*      */     finally
/*      */     {
/* 3034 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 3036 */     return xmlString;
/*      */   }
/*      */   
/*      */   public static String validateRequestForConfMonitors(String monType, HttpServletRequest request, HttpServletResponse response, MockHttpServletRequest mreq)
/*      */     throws Exception
/*      */   {
/* 3042 */     String xmlString = "";
/* 3043 */     ArrayList argumentsList = NewMonitorUtil.getArgsforConfMon(monType);
/* 3044 */     if ((argumentsList != null) && (!argumentsList.isEmpty())) {
/* 3045 */       ArrayList argsNames = (ArrayList)argumentsList.get(0);
/* 3046 */       ArrayList mandatoryArgs = (ArrayList)argumentsList.get(3);
/* 3047 */       ArrayList argsType = (ArrayList)argumentsList.get(1);
/* 3048 */       HashMap keyValuePairs = (HashMap)argumentsList.get(4);
/* 3049 */       ArrayList argsDefault = (ArrayList)argumentsList.get(6);
/* 3050 */       ArrayList args_ShowOnCondition = (ArrayList)argumentsList.get(8);
/* 3051 */       HashMap showArgsOnClick = (HashMap)argumentsList.get(9);
/* 3052 */       HashMap args_onChangeMap = (HashMap)argumentsList.get(15);
/* 3053 */       Properties hostProperties = ConfMonitorConfiguration.getInstance().getHostDetails(monType);
/* 3054 */       String hostColumn = (hostProperties != null) && (hostProperties.getProperty("Value").equals("YES")) ? hostProperties.getProperty("Name") : "";
/* 3055 */       boolean isCredialManagerused = (mreq.getParameter("CredentialDetails") != null) && (mreq.getParameter("CredentialDetails").equals("cm"));
/* 3056 */       JSONArray argsInCredentialManager = CredentialManagerUtil.getInstance().getCredentialAsJSONArrayByType(monType);
/* 3057 */       ArrayList checkBeforeProceeding = new ArrayList();
/* 3058 */       ArrayList dependentArguments = new ArrayList();
/* 3059 */       for (int i = 0; i < argsNames.size(); i++) {
/* 3060 */         String argName = (String)argsNames.get(i);
/* 3061 */         String argType = (String)argsType.get(i);
/* 3062 */         boolean mandatory = ((String)mandatoryArgs.get(i)).equals("1");
/* 3063 */         if ((isCredialManagerused) && (argsInCredentialManager != null))
/*      */         {
/* 3065 */           int len = argsInCredentialManager.length();
/* 3066 */           for (int j = 0; j < len; j++)
/*      */           {
/* 3068 */             JSONObject jsObj = argsInCredentialManager.getJSONObject(j);
/* 3069 */             String argNameFromCredentialMgr = jsObj.getString("rowID");
/* 3070 */             if (argName.equals(argNameFromCredentialMgr))
/*      */             {
/* 3072 */               mandatory = false;
/* 3073 */               break;
/*      */             }
/*      */           }
/* 3076 */         } else if ((isCredialManagerused) && (argsInCredentialManager == null)) {
/* 3077 */           AMLog.debug("REST API : credentialID : does not exist");
/* 3078 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.idnotexist"), "5004");
/*      */         }
/* 3080 */         String defaultValue = (String)argsDefault.get(i);
/* 3081 */         if (mreq.getParameter(argName) != null) {
/* 3082 */           String value = mreq.getParameter(argName);
/* 3083 */           if ((argType.equals("2")) || ((argType.equals("4")) && (value != null) && (value != ""))) {
/* 3084 */             LinkedHashMap key_val = (LinkedHashMap)keyValuePairs.get(argName);
/* 3085 */             boolean alreadyHandled = checkIfValueAlreadyHandled(value, argName, key_val);
/* 3086 */             if (!alreadyHandled) {
/* 3087 */               AMLog.debug("REST API : The mentioned " + argName + " should contain any one of this values " + key_val.values());
/* 3088 */               xmlString = URITree.generateXML(request, response, FormatUtil.getString("REST API : The mentioned " + argName + " in the URL should contain any one of this values " + key_val.values()), "4209");
/* 3089 */               return xmlString;
/*      */             }
/* 3091 */             if ((args_onChangeMap != null) && (args_onChangeMap.get(argName) != null)) {
/* 3092 */               LinkedHashMap args_onChange = (LinkedHashMap)args_onChangeMap.get(argName);
/* 3093 */               String onChangeShowArgs = (String)args_onChange.get(value + "_OnChangeShowArgs");
/* 3094 */               if ((onChangeShowArgs != null) && (!onChangeShowArgs.trim().equals(""))) {
/* 3095 */                 parseStringAndAdd(dependentArguments, onChangeShowArgs);
/*      */               }
/* 3097 */               String onClickShowArgs = (String)args_onChange.get(value + "_OnClickShowArgs");
/* 3098 */               if ((onClickShowArgs != null) && (!onClickShowArgs.trim().equals(""))) {
/* 3099 */                 parseStringAndAdd(dependentArguments, onClickShowArgs);
/*      */               }
/*      */             }
/*      */           }
/* 3103 */           if ((argType.equals("5")) && (!value.equalsIgnoreCase("false")) && (!value.equalsIgnoreCase("no")) && (showArgsOnClick.get(argName) != null)) {
/* 3104 */             dependentArguments.addAll((ArrayList)showArgsOnClick.get(argName));
/*      */           }
/*      */         }
/*      */         
/* 3108 */         if ((hostColumn.equals(argName)) && (request.getParameter("host") != null) && (mreq.getParameter(argName) == null)) {
/* 3109 */           mreq.addParameter(argName, request.getParameter("host"));
/*      */ 
/*      */         }
/* 3112 */         else if (("Port".equals(argName)) && (request.getParameter("port") != null) && (mreq.getParameter(argName) == null)) {
/* 3113 */           mreq.addParameter(argName, request.getParameter("port"));
/*      */ 
/*      */         }
/* 3116 */         else if ((defaultValue != null) && (mreq.getParameter(argName) == null)) {
/* 3117 */           if ((argType.equals("2")) || (argType.equals("4")) || (argType.equals("5"))) {
/* 3118 */             LinkedHashMap key_val = (LinkedHashMap)keyValuePairs.get(argName);
/* 3119 */             defaultValue = key_val.get(defaultValue) != null ? (String)key_val.get(defaultValue) : defaultValue;
/*      */           }
/* 3121 */           mreq.addParameter(argName, defaultValue);
/*      */ 
/*      */         }
/* 3124 */         else if ((mreq.getParameter(argName) == null) && (mandatory) && (!"CredentialDetails".equals(argName)) && (!"cmValue".equals(argName)) && (!"UT".equals(argName)) && (!"isParent".equals(argName))) {
/* 3125 */           if (((String)args_ShowOnCondition.get(i)).equals("YES")) {
/* 3126 */             checkBeforeProceeding.add(argName);
/*      */           }
/* 3128 */           else if (!argType.equals("6")) {
/* 3129 */             AMLog.debug("REST API : The mentioned " + argName + " should not be empty.");
/* 3130 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.argument.notempty", new String[] { argName }), "4206");
/* 3131 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/* 3135 */       if (!checkBeforeProceeding.isEmpty()) {
/* 3136 */         Iterator emptyArgs = checkBeforeProceeding.iterator();
/* 3137 */         while (emptyArgs.hasNext()) {
/* 3138 */           String emptyArg = (String)emptyArgs.next();
/*      */           
/* 3140 */           if (dependentArguments.contains(emptyArg)) {
/* 3141 */             AMLog.debug("REST API : The mentioned " + emptyArg + " should not be empty.");
/* 3142 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.argument.notempty", new String[] { emptyArg }), "4206");
/* 3143 */             return xmlString;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3148 */     return xmlString;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void parseStringAndAdd(ArrayList dependentArgs, String commaSeparatedArgs)
/*      */   {
/* 3154 */     StringTokenizer args = new StringTokenizer(commaSeparatedArgs, ",");
/* 3155 */     while (args.hasMoreTokens()) {
/* 3156 */       dependentArgs.add(args.nextToken());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean checkIfValueAlreadyHandled(String value, String argName, LinkedHashMap key_val)
/*      */   {
/* 3163 */     Iterator it = key_val.keySet().iterator();
/* 3164 */     boolean alreadyHandled = false;
/* 3165 */     while (it.hasNext()) {
/* 3166 */       String key = (String)it.next();
/* 3167 */       String kVal = (String)key_val.get(key);
/* 3168 */       if (kVal.equals(value)) {
/* 3169 */         alreadyHandled = true;
/* 3170 */         break;
/*      */       }
/*      */     }
/* 3173 */     return alreadyHandled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String addManagerServerUrlInProxyLink(String errorMessage)
/*      */   {
/* 3181 */     if (errorMessage.indexOf("/jsp/ProxyConfiguration.jsp") != 0) {
/* 3182 */       String managedServerHost = Constants.getAppHostName();
/* 3183 */       String managedServerSslPort = com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport();
/* 3184 */       String proxyUrl = "<a href=\"#\" onclick='javascript:fnOpenNewWindowWithHeightWidth(\"" + "https://" + managedServerHost + ":" + managedServerSslPort + "/jsp/ProxyConfiguration.jsp?" + "\",1100,500)' class=\"staticlinks\">";
/* 3185 */       errorMessage = errorMessage.replaceAll("<a href=\"/jsp/ProxyConfiguration.jsp\" class=\"staticlinks-red\">", proxyUrl);
/*      */     }
/* 3187 */     return errorMessage;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\AddMonitorAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */