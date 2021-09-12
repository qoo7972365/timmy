/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.agent.utilities.xml.XMLDataReader;
/*      */ import com.adventnet.agent.utilities.xml.XMLNode;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.personalize.AMPersonalize;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.beans.GroupComponent;
/*      */ import com.adventnet.appmanager.struts.beans.ResourceNames;
/*      */ import com.adventnet.appmanager.struts.form.MyPageForm;
/*      */ import com.adventnet.appmanager.util.AMFlyingSaucerUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DashboardUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtConnectorUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletOutputStream;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.util.LabelValueBean;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MyPageAction
/*      */   extends DispatchAction
/*      */ {
/*  107 */   private static int nooftabs = 8;
/*  108 */   private static ArrayList<String> complexTypes = new ArrayList();
/*  109 */   public static String forpage = "1";
/*      */   
/*      */   private static final String OPMANAGER = "OPMANAGER";
/*      */   
/*      */   private static final String EXTPROD = "EXTPROD";
/*      */   
/*  115 */   private static final String OPMAN_DASHBOARD_NAME = FormatUtil.getString("it360.dashboard.networkdevicesdashboard");
/*  116 */   private static Hashtable<String, String> extProdDashboards = new Hashtable();
/*      */   
/*      */   static
/*      */   {
/*  120 */     complexTypes.add("$ComplexType_Windows");
/*  121 */     complexTypes.add("$ComplexType_Servers");
/*  122 */     complexTypes.add("$ComplexType_All");
/*  123 */     complexTypes.add("$ComplexType_StorageDevices");
/*  124 */     complexTypes.add("$ComplexType_NetworkDevices");
/*  125 */     complexTypes.add("$ComplexType_AllApps");
/*  126 */     complexTypes.add("$ComplexType_HAI");
/*      */     
/*      */ 
/*  129 */     if (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) {
/*  130 */       extProdDashboards.put(OPMAN_DASHBOARD_NAME, "-1");
/*  131 */       extProdDashboards = fillExtProductDashboardIds(extProdDashboards);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  136 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public ActionForward viewPublishedPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  139 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*  141 */     int pageid = -1;
/*  142 */     String userid = null;
/*      */     try {
/*  144 */       String pagekey = request.getParameter("pagekey");
/*  145 */       ResultSet rs; ResultSet rs; if (DBQueryUtil.getDBType().equals("mssql")) {
/*  146 */         rs = AMConnectionPool.executeQueryStmt("select PAGEID,USERID from AM_MYPAGE_PUBLISHKEYS where PUBLISHID=0x" + pagekey); } else { ResultSet rs;
/*  147 */         if (DBQueryUtil.getDBType().equals("mysql")) {
/*  148 */           rs = AMConnectionPool.executeQueryStmt("select PAGEID,USERID from AM_MYPAGE_PUBLISHKEYS where PUBLISHID='" + pagekey + "'");
/*      */         } else
/*  150 */           rs = AMConnectionPool.executeQueryStmt("select PAGEID,USERID from AM_MYPAGE_PUBLISHKEYS where PUBLISHID='" + pagekey + "'");
/*      */       }
/*  152 */       if (rs.next()) {
/*  153 */         pageid = rs.getInt("PAGEID");
/*  154 */         userid = rs.getString("USERID");
/*      */       }
/*  156 */       AMConnectionPool.closeResultSet(rs);
/*  157 */       if (pageid != -1) {
/*  158 */         request.setAttribute("pageid", String.valueOf(pageid));
/*  159 */         request.setAttribute("userid", userid);
/*  160 */         request.setAttribute("publishpage", "true");
/*  161 */         request.setAttribute("pagekey", pagekey);
/*  162 */         populatePageProps(mapping, form, request, response);
/*  163 */         return new ActionForward("/jsp/MyPage.jsp");
/*      */       }
/*      */     } catch (Exception ex) {
/*  166 */       ex.printStackTrace();
/*      */     }
/*  168 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getPublishedKey(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  173 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  178 */       Integer.parseInt(request.getParameter("pageid"));
/*      */     } catch (Exception e) {
/*  180 */       AMLog.debug("MyPageAction : getPublishedKey. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/*  181 */       return new ActionForward("/applications.do", true);
/*      */     }
/*  183 */     String pageid = request.getParameter("pageid");
/*  184 */     String templateResid = request.getParameter("template_resid");
/*      */     try {
/*  186 */       String pagekey = pageid;
/*  187 */       ManagedApplication mo = new ManagedApplication();
/*  188 */       String userid = mo.getUserID(request);
/*  189 */       if ((userid == null) || (userid.equals("-1"))) {
/*  190 */         userid = DashboardUtil.getAdminUserId();
/*      */       }
/*  192 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select PUBLISHID from AM_MYPAGE_PUBLISHKEYS where pageid=" + pageid + " and  USERID=" + userid);
/*  193 */       if (rs.next()) {
/*  194 */         pagekey = rs.getString("PUBLISHID");
/*      */       } else {
/*  196 */         long publishid = System.currentTimeMillis();
/*  197 */         pagekey = String.valueOf(publishid);
/*  198 */         AMConnectionPool.executeUpdateStmt("insert into AM_MYPAGE_PUBLISHKEYS(PAGEID,PUBLISHID,USERID,UTILCOLUMN) values(" + pageid + "," + DBQueryUtil.MD5(pagekey) + "," + userid + ",0)");
/*  199 */         ResultSet rs2 = AMConnectionPool.executeQueryStmt("select PUBLISHID from AM_MYPAGE_PUBLISHKEYS where pageid=" + pageid + " and  USERID=" + userid);
/*  200 */         if (rs2.next()) {
/*  201 */           pagekey = rs2.getString("PUBLISHID");
/*      */         }
/*  203 */         AMConnectionPool.closeResultSet(rs2);
/*      */       }
/*  205 */       AMConnectionPool.closeResultSet(rs);
/*  206 */       request.setAttribute("pagekey", pagekey);
/*      */     } catch (Exception ex) {
/*  208 */       ex.printStackTrace();
/*      */     }
/*  210 */     if (templateResid != null) {
/*  211 */       request.setAttribute("template_resid", templateResid);
/*      */     }
/*  213 */     request.setAttribute("related_action", "publishviewkey");
/*  214 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward saveTabOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  218 */     AMConnectionPool pool = new AMConnectionPool();
/*  219 */     String pageid = "-1";
/*  220 */     String userid = "-1";
/*  221 */     String resourceid = "0";
/*      */     try
/*      */     {
/*  224 */       if (request.isUserInRole("DEMO")) {
/*  225 */         ActionMessages messages = new ActionMessages();
/*  226 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text")));
/*  227 */         saveMessages(request, messages);
/*  228 */         return new ActionForward("/MyPage.do?method=viewDashBoard");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  233 */       String dashboardtype = "1";
/*  234 */       userid = this.mo.getUserID(request);
/*  235 */       AMConnectionPool.executeUpdateStmt("delete from AM_DASHBOARDS where FORPAGE=" + forpage + " and RESOURCEID=" + resourceid + " and OWNERID=" + userid);
/*  236 */       for (int orderid = 0; orderid < nooftabs; orderid++) {
/*  237 */         pageid = request.getParameter("selectedPage" + orderid);
/*      */         
/*  239 */         dashboardtype = "1";
/*      */         try {
/*  241 */           String insertQuery = "insert into AM_DASHBOARDS(FORPAGE,RESOURCEID,DASHBOARDTYPE,PAGEID,TABORDERID,OWNERID,UTILCOLUMN1) values(" + forpage + "," + resourceid + "," + dashboardtype + "," + pageid + "," + orderid + "," + userid + ",-1)";
/*  242 */           AMConnectionPool.executeUpdateStmt(insertQuery);
/*      */         } catch (Exception ex) {
/*  244 */           ex.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  252 */       ex.printStackTrace();
/*      */     }
/*  254 */     return new ActionForward("/MyPage.do?method=editTabs&savedstaus=true");
/*      */   }
/*      */   
/*      */   public ActionForward editTabs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  258 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  265 */     HashMap selectedorder = new HashMap();
/*  266 */     HashMap dashboardNames = new HashMap();
/*  267 */     ArrayList globalpages = new ArrayList();
/*  268 */     ArrayList businesspages = new ArrayList();
/*  269 */     ArrayList mgtemplatepages = new ArrayList();
/*      */     try {
/*  271 */       ArrayList notselected = new ArrayList();
/*  272 */       notselected.add("-100");
/*  273 */       notselected.add(FormatUtil.getString("am.mypage.notselected.text"));
/*  274 */       globalpages.add(notselected);
/*  275 */       String condition = "";
/*  276 */       if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */       {
/*  278 */         condition = " where USERID=" + DBUtil.getUserID(request.getRemoteUser()) + " or (PAGEID%" + EnterpriseUtil.RANGE + ")<5";
/*  279 */       } else if ((DBUtil.isDelegatedAdminEnabled()) && (!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  281 */         condition = DBUtil.getCondition(" where USERID ", DelegatedUserRoleUtil.getNonDelegtedAdminIDs());
/*  282 */         condition = request.isUserInRole("OPERATOR") ? condition + " and PAGENAME<>'Business View'" : condition;
/*      */       }
/*      */       
/*  285 */       String availDashboardsQuery = "select PAGEID,PAGENAME,PAGETYPE from AM_MYPAGES " + condition + " order by PAGENAME";
/*  286 */       ResultSet rs2 = AMConnectionPool.executeQueryStmt(availDashboardsQuery);
/*      */       
/*  288 */       while (rs2.next()) {
/*  289 */         ArrayList eachPage = new ArrayList();
/*  290 */         if (rs2.getString("PAGETYPE").equals("globalpage")) {
/*  291 */           eachPage.add(rs2.getString("PAGEID"));
/*  292 */           eachPage.add(rs2.getString("PAGENAME"));
/*  293 */           globalpages.add(eachPage);
/*  294 */         } else if (rs2.getString("PAGETYPE").equals("businesspage")) {
/*  295 */           eachPage.add(rs2.getString("PAGEID"));
/*  296 */           eachPage.add(rs2.getString("PAGENAME"));
/*  297 */           businesspages.add(eachPage);
/*  298 */         } else if (rs2.getString("PAGETYPE").equals("mgtemplate")) {
/*  299 */           eachPage.add(rs2.getString("PAGEID"));
/*  300 */           eachPage.add(rs2.getString("PAGENAME"));
/*  301 */           mgtemplatepages.add(eachPage);
/*      */         }
/*      */       }
/*  304 */       AMConnectionPool.closeStatement(rs2);
/*      */       
/*      */ 
/*  307 */       request.setAttribute("globalpages", globalpages);
/*      */       
/*  309 */       if (businesspages.size() > 0) {
/*  310 */         request.setAttribute("businesspages", businesspages);
/*      */       }
/*  312 */       if (mgtemplatepages.size() > 0) {
/*  313 */         request.setAttribute("mgtemplatepages", mgtemplatepages);
/*      */       }
/*      */       
/*  316 */       ManagedApplication mo = new ManagedApplication();
/*  317 */       String userid = mo.getUserID(request);
/*  318 */       String taborderQuery = "select PAGEID,TABORDERID,OWNERID from AM_DASHBOARDS where OWNERID=" + userid + " and FORPAGE=" + forpage;
/*  319 */       ResultSet rs = AMConnectionPool.executeQueryStmt(taborderQuery);
/*      */       
/*  321 */       request.setAttribute("homepagetype", "1");
/*  322 */       while (rs.next()) {
/*  323 */         String pageid = rs.getString("PAGEID");
/*  324 */         String orderid = rs.getString("TABORDERID");
/*  325 */         selectedorder.put(orderid, pageid);
/*      */       }
/*  327 */       AMConnectionPool.closeStatement(rs);
/*  328 */       if (selectedorder.size() > 0) {
/*  329 */         for (int i = 1; i < nooftabs; i++) {
/*  330 */           if (selectedorder.get(String.valueOf(i)) == null) {
/*  331 */             selectedorder.put(String.valueOf(i), "-100");
/*      */           }
/*      */         }
/*      */       }
/*  335 */       String defaulttabSelectquery = DBQueryUtil.getTopNValues("select PAGEID,PAGENAME,'URL',1 from AM_MYPAGES order by PAGEID asc", 4);
/*  336 */       if (request.isUserInRole("OPERATOR")) {
/*  337 */         defaulttabSelectquery = DBQueryUtil.getTopNValues("select PAGEID,PAGENAME,'URL',1 from AM_MYPAGES where PAGENAME<>'Business View' order by PAGEID asc", 4);
/*      */       }
/*  339 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(defaulttabSelectquery);
/*  340 */       int tabcount = 0;
/*  341 */       while ((rs1.next()) && (tabcount < 4)) {
/*  342 */         if (selectedorder.get(String.valueOf(tabcount)) == null) {
/*  343 */           selectedorder.put(String.valueOf(tabcount++), rs1.getString("PAGEID"));
/*      */         }
/*      */       }
/*  346 */       AMConnectionPool.closeStatement(rs1);
/*  347 */       for (int i = 4; i < nooftabs; i++) {
/*  348 */         if (selectedorder.get(String.valueOf(i)) == null) {
/*  349 */           selectedorder.put(String.valueOf(i), "-100");
/*      */         }
/*      */       }
/*  352 */       request.setAttribute("selectedorder", selectedorder);
/*      */     }
/*      */     catch (Exception ex) {
/*  355 */       ex.printStackTrace();
/*      */     }
/*  357 */     return new ActionForward("/jsp/MyPage_TabEdit.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward setAsDefault(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  361 */     AMConnectionPool pool = new AMConnectionPool();
/*  362 */     String resourceid = "0";
/*      */     try
/*      */     {
/*  365 */       String dashboardtype = request.getParameter("dashboardtype");
/*  366 */       String pageid = "-1";
/*  367 */       String templateResid = request.getParameter("template_resid");
/*  368 */       MyPageForm fm = (MyPageForm)form;
/*  369 */       ManagedApplication mo = new ManagedApplication();
/*  370 */       String userid = mo.getUserID(request);
/*  371 */       String dashboardname = "";
/*  372 */       String url = "";
/*      */       try
/*      */       {
/*  375 */         if (request.getParameter("resourceid") != null) {
/*  376 */           Integer.parseInt(request.getParameter("resourceid"));
/*      */         }
/*  378 */         if (request.getParameter("pageid") != null) {
/*  379 */           Integer.parseInt(request.getParameter("pageid"));
/*      */         }
/*  381 */         if (request.getParameter("forpage") != null) {
/*  382 */           Integer.parseInt(request.getParameter("forpage"));
/*      */         }
/*      */       } catch (Exception e) {
/*  385 */         AMLog.debug("MyPageAction : setAsDefault. Incorrect values obtained for resourceid : " + request.getParameter("resourceid") + ", pageid : " + request.getParameter("pageid") + ", forpage : " + request.getParameter("forpage") + ". Error : " + e.getMessage());
/*  386 */         return new ActionForward("/applications.do", true);
/*      */       }
/*  388 */       if (request.getParameter("resourceid") != null)
/*      */       {
/*  390 */         resourceid = request.getParameter("resourceid");
/*      */       }
/*  392 */       if (dashboardtype.equals("1"))
/*      */       {
/*  394 */         pageid = request.getParameter("pageid");
/*  395 */       } else if (dashboardtype.equals("2"))
/*      */       {
/*  397 */         url = fm.getUrl();
/*  398 */         url = URLEncoder.encode(url, "UTF-8");
/*  399 */         dashboardname = request.getParameter("dashboardname");
/*  400 */         if (dashboardname != null) {
/*  401 */           int index = dashboardname.indexOf(OEMUtil.getOEMString("product.name"));
/*  402 */           int namelength = OEMUtil.getOEMString("product.name").length();
/*      */           
/*      */ 
/*      */           try
/*      */           {
/*  407 */             if (index >= 0) {
/*  408 */               dashboardname = dashboardname.substring(namelength + 3);
/*      */             }
/*      */           } catch (Exception ex) {
/*  411 */             dashboardname = FormatUtil.getString("am.mypage.defaulthome.text");
/*      */           }
/*      */         } else {
/*  414 */           dashboardname = FormatUtil.getString("am.mypage.defaulthome.text");
/*      */         }
/*      */       }
/*  417 */       String forpage = "1";
/*  418 */       if (request.getParameter("forpage") != null) {
/*  419 */         forpage = request.getParameter("forpage");
/*      */       }
/*      */       
/*  422 */       String selectquery = "select PAGEID,FORPAGE,RESOURCEID from AM_DASHBOARDS where FORPAGE=" + forpage + " and RESOURCEID=" + resourceid + " and OWNERID=" + userid + " and TABORDERID=0";
/*  423 */       ResultSet rs = AMConnectionPool.executeQueryStmt(selectquery);
/*  424 */       if (rs.next())
/*      */       {
/*  426 */         String oldTab = rs.getString(1);
/*  427 */         if (!pageid.equals(oldTab)) {
/*  428 */           ResultSet rs1 = null;
/*      */           try {
/*  430 */             String selQuery = "select TABORDERID from AM_DASHBOARDS where PAGEID=" + pageid + " and FORPAGE=" + forpage + " and OWNERID=" + userid + " and RESOURCEID=" + resourceid;
/*  431 */             rs1 = AMConnectionPool.executeQueryStmt(selQuery);
/*      */             
/*  433 */             String updatequery = "update AM_DASHBOARDS set PAGEID=" + pageid + ",DASHBOARDTYPE=" + dashboardtype + ",URL='" + url + "',DASHBOARDNAME='" + dashboardname + "'  where FORPAGE=" + forpage + " and RESOURCEID=" + resourceid + " and OWNERID=" + userid + " and TABORDERID=0";
/*  434 */             AMConnectionPool.executeUpdateStmt(updatequery);
/*      */             
/*  436 */             if (rs1.next()) {
/*  437 */               AMConnectionPool.executeUpdateStmt("update AM_DASHBOARDS set PAGEID=" + oldTab + ",DASHBOARDTYPE=" + dashboardtype + ",URL='" + url + "',DASHBOARDNAME='" + dashboardname + "'  where FORPAGE=" + forpage + " and RESOURCEID=" + resourceid + " and OWNERID=" + userid + " and TABORDERID=" + rs1.getString(1));
/*      */             }
/*      */           } catch (Exception e) {
/*  440 */             e.printStackTrace();
/*      */           } finally {
/*  442 */             AMConnectionPool.closeStatement(rs1);
/*      */           }
/*      */         }
/*      */       } else {
/*  446 */         String insertquery = "insert into AM_DASHBOARDS(PAGEID,RESOURCEID,FORPAGE,DASHBOARDTYPE,OWNERID,URL,DASHBOARDNAME,TABORDERID,UTILCOLUMN1) values(" + pageid + "," + resourceid + "," + forpage + "," + dashboardtype + "," + userid + ",'" + url + "','" + dashboardname + "',0,-1)";
/*  447 */         AMConnectionPool.executeUpdateStmt(insertquery);
/*      */       }
/*  449 */       rs.close();
/*      */     } catch (Exception ex) {
/*  451 */       ex.printStackTrace();
/*      */     }
/*  453 */     if (!resourceid.equals("0")) {
/*  454 */       return new ActionForward("/showapplication.do?&method=showApplication&haid=" + resourceid);
/*      */     }
/*  456 */     return new ActionForward("/MyPage.do?method=viewDashBoard&forpage=" + forpage);
/*      */   }
/*      */   
/*      */   public ActionForward listDashBoards(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  460 */     AMConnectionPool pool = new AMConnectionPool();
/*  461 */     ResultSet rs = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  466 */     ArrayList globalpages = new ArrayList();
/*  467 */     ArrayList templatepages = new ArrayList();
/*  468 */     ArrayList businesspages = new ArrayList();
/*      */     try
/*      */     {
/*  471 */       String condition = "";
/*  472 */       if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */       {
/*  474 */         condition = " where USERID=" + DBUtil.getUserID(request.getRemoteUser()) + " or (PAGEID%" + EnterpriseUtil.RANGE + ")<5";
/*  475 */       } else if ((DBUtil.isDelegatedAdminEnabled()) && (!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  477 */         condition = DBUtil.getCondition(" where USERID ", DelegatedUserRoleUtil.getNonDelegtedAdminIDs());
/*  478 */         condition = request.isUserInRole("OPERATOR") ? condition + " and PAGENAME<>'Business View'" : condition;
/*      */       }
/*      */       
/*  481 */       String selectquery = "select PAGEID,PAGETYPE,PAGENAME from AM_MYPAGES " + condition + " order by PAGENAME";
/*      */       
/*  483 */       rs = AMConnectionPool.executeQueryStmt(selectquery);
/*  484 */       while (rs.next()) {
/*  485 */         ArrayList singleDashboard = new ArrayList();
/*  486 */         String pageid = rs.getString("PAGEID");
/*  487 */         String pagename = rs.getString("PAGENAME");
/*  488 */         String pagetype = rs.getString("PAGETYPE");
/*  489 */         singleDashboard.add(pageid);
/*  490 */         singleDashboard.add(pagename);
/*  491 */         if (pagetype.equals("mgtemplate"))
/*      */         {
/*  493 */           templatepages.add(singleDashboard);
/*  494 */         } else if (pagetype.equals("businesspage"))
/*      */         {
/*  496 */           businesspages.add(singleDashboard);
/*      */         } else {
/*  498 */           globalpages.add(singleDashboard);
/*      */         }
/*      */       }
/*  501 */       request.setAttribute("globalpages", globalpages);
/*  502 */       request.setAttribute("templatepages", templatepages);
/*  503 */       request.setAttribute("businesspages", businesspages);
/*      */     } catch (Exception ex) {
/*  505 */       ex.printStackTrace();
/*      */     } finally {
/*  507 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  509 */     return new ActionForward("/jsp/MyPage_DashboardList.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward popOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  513 */     response.setContentType("text/xml; charset=UTF-8");
/*  514 */     populatePageProps(mapping, form, request, response);
/*  515 */     request.setAttribute("isPopup", "true");
/*  516 */     return new ActionForward("/jsp/MyPage.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward viewDashBoard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  522 */       FreeEditionDetails free = FreeEditionDetails.getFreeEditionDetails();
/*  523 */       String usrtype = free.getUserType();
/*  524 */       if ((request.getParameter("fromtab") == null) && (request.getParameter("toredirect") != null)) {
/*  525 */         Properties tabprops = AMPersonalize.getHeaderTabToFocus(request.getRemoteUser());
/*  526 */         String url = tabprops.getProperty("URL") != null ? tabprops.getProperty("URL") : "/MyPage.do?method=viewDashBoard";
/*      */         
/*  528 */         String tabIdType = tabprops.getProperty("ID") != null ? tabprops.getProperty("ID") : "1_1";
/*      */         
/*  530 */         AMPersonalize.tabtoFocusOnRedirect = tabIdType;
/*  531 */         if (!url.equals("/MyPage.do?method=viewDashBoard")) {
/*  532 */           Cookie objCookie = new Cookie("selectedtab", tabIdType);
/*  533 */           objCookie.setMaxAge(31536000);
/*  534 */           response.addCookie(objCookie);
/*  535 */           return new ActionForward(url);
/*      */         }
/*  537 */         Cookie objCookie = new Cookie("selectedtab", tabIdType);
/*  538 */         objCookie.setMaxAge(31536000);
/*  539 */         response.addCookie(objCookie);
/*      */       }
/*  541 */       if ((usrtype != null) && (usrtype.equals("F")) && (!com.adventnet.appmanager.util.Constants.sqlManager)) {
/*  542 */         return new ActionForward("/showapplication.do?method=showApplications");
/*      */       }
/*  544 */       if (request.isUserInRole("MANAGER")) {
/*  545 */         return new ActionForward("/showBussiness.do?method=generateApplicationAvailablity&selectTabName=SLA");
/*      */       }
/*  547 */       MyPageForm fm = (MyPageForm)form;
/*  548 */       if (request.getParameter("fromtab") == null) {
/*  549 */         request.setAttribute("fromWhere", "dashboardpage");
/*      */       }
/*  551 */       String dashboardtype_id = "-1";
/*  552 */       if (request.getParameter("forpage") != null) {
/*  553 */         forpage = request.getParameter("forpage");
/*      */       } else {
/*  555 */         forpage = "1";
/*      */       }
/*  557 */       ManagedApplication mo = new ManagedApplication();
/*  558 */       String userid = mo.getUserID(request);
/*  559 */       String selectedTab = "0";
/*      */       
/*  561 */       setDashBoards(mapping, form, request, response);
/*  562 */       ArrayList dashboardsinOrder = (ArrayList)request.getAttribute("dashboardsinOrder");
/*      */       
/*  564 */       if ((dashboardsinOrder.size() <= 0) && 
/*  565 */         (!userid.equals("-1")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  571 */         DashboardUtil.createDefaultdashboards();
/*  572 */         setDashBoards(mapping, form, request, response);
/*  573 */         dashboardsinOrder = (ArrayList)request.getAttribute("dashboardsinOrder");
/*      */       }
/*      */       
/*  576 */       String addNewTab = request.getParameter("addNewTab");
/*  577 */       if (request.getAttribute("dashboardsnotsaved") != null) {
/*  578 */         addNewTab = null;
/*      */       }
/*  580 */       dashboardtype_id = (String)request.getAttribute("pageid");
/*      */       
/*      */ 
/*  583 */       if (request.getParameter("selectedTab") != null)
/*      */       {
/*      */ 
/*      */ 
/*  587 */         populatePageProps(mapping, form, request, response);
/*  588 */         selectedTab = fm.getSelectedTab();
/*  589 */         if (selectedTab != null)
/*      */         {
/*      */           try
/*      */           {
/*  593 */             if (Integer.parseInt(selectedTab) > dashboardsinOrder.size() - 1) {
/*  594 */               String name = fm.getDisplayName();
/*  595 */               ArrayList extratab = new ArrayList();
/*  596 */               extratab.add(dashboardtype_id);
/*  597 */               extratab.add(name);
/*      */               
/*      */ 
/*  600 */               selectedTab = String.valueOf(dashboardsinOrder.size());
/*  601 */               dashboardsinOrder.add(extratab);
/*      */             }
/*      */           } catch (Exception ex) {
/*  604 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*  608 */       else if (addNewTab != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  614 */         populatePageProps(mapping, form, request, response);
/*  615 */         if (addNewTab.equals("true"))
/*      */         {
/*  617 */           String name = fm.getDisplayName();
/*  618 */           ArrayList extratab = new ArrayList();
/*  619 */           extratab.add(dashboardtype_id);
/*  620 */           extratab.add(name);
/*      */           
/*      */ 
/*  623 */           selectedTab = getTabForPageid(dashboardtype_id, dashboardsinOrder);
/*  624 */           if (Integer.parseInt(selectedTab) > dashboardsinOrder.size() - 1) {
/*  625 */             selectedTab = String.valueOf(dashboardsinOrder.size());
/*  626 */             dashboardsinOrder.add(extratab);
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  631 */         request.setAttribute("pageid", dashboardtype_id);
/*  632 */         populatePageProps(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  637 */       request.setAttribute("selectedTab", selectedTab);
/*      */       
/*      */ 
/*  640 */       return new ActionForward("/jsp/MyPage_Container.jsp");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  644 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  648 */     return new ActionForward("/jsp/MyPage_Container.jsp");
/*      */   }
/*      */   
/*      */   private void setDashBoards(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  652 */     ArrayList dashboardsinOrder = new ArrayList();
/*      */     try {
/*  654 */       MyPageForm fm = (MyPageForm)form;
/*  655 */       ManagedApplication mo = new ManagedApplication();
/*  656 */       String userid = mo.getUserID(request);
/*  657 */       String dashboardtype_id = "-1";
/*      */       
/*      */ 
/*  660 */       String url = "/applications.do";
/*      */       
/*  662 */       String dashboardorder = "select AM_DASHBOARDS.PAGEID,PAGENAME from AM_DASHBOARDS left outer join AM_MYPAGES on AM_MYPAGES.PAGEID=AM_DASHBOARDS.PAGEID where FORPAGE=" + forpage + " and OWNERID=" + userid + " and AM_DASHBOARDS.PAGEID>0 order by TABORDERID asc";
/*  663 */       dashboardsinOrder = mo.getRows(dashboardorder);
/*      */       
/*  665 */       if (dashboardsinOrder.size() > 0) {
/*  666 */         ArrayList firstrow = (ArrayList)dashboardsinOrder.get(0);
/*  667 */         dashboardtype_id = (String)firstrow.get(0);
/*  668 */         request.setAttribute("pageid", dashboardtype_id);
/*  669 */         request.setAttribute("url", url);
/*  670 */         request.setAttribute("dashboardsinOrder", dashboardsinOrder);
/*  671 */         if (request.getParameter("selectedpageid") != null)
/*      */         {
/*  673 */           dashboardtype_id = request.getParameter("selectedpageid");
/*  674 */           request.setAttribute("pageid", dashboardtype_id);
/*      */         }
/*  676 */         if (request.getParameter("selectedTab") == null)
/*      */         {
/*  678 */           String selectedtab = getTabForPageid(dashboardtype_id, dashboardsinOrder);
/*  679 */           fm.setSelectedTab(selectedtab);
/*      */         }
/*  681 */         return;
/*      */       }
/*      */       
/*      */ 
/*  685 */       dashboardorder = "select AM_DASHBOARDS.PAGEID,PAGENAME from AM_DASHBOARDS left outer join AM_MYPAGES on AM_MYPAGES.PAGEID=AM_DASHBOARDS.PAGEID where FORPAGE=" + forpage + " and  OWNERID=1 and AM_DASHBOARDS.PAGEID>0  order by TABORDERID asc";
/*  686 */       dashboardsinOrder = mo.getRows(dashboardorder);
/*  687 */       if (dashboardsinOrder.size() > 0) {
/*  688 */         ArrayList firstrow = (ArrayList)dashboardsinOrder.get(0);
/*  689 */         dashboardtype_id = (String)firstrow.get(0);
/*  690 */         request.setAttribute("pageid", dashboardtype_id);
/*  691 */         if (request.getParameter("selectedpageid") != null)
/*      */         {
/*  693 */           dashboardtype_id = request.getParameter("selectedpageid");
/*  694 */           request.setAttribute("pageid", dashboardtype_id);
/*      */         }
/*  696 */         if (request.getParameter("selectedTab") == null)
/*      */         {
/*  698 */           String selectedtab = getTabForPageid(dashboardtype_id, dashboardsinOrder);
/*  699 */           fm.setSelectedTab(selectedtab);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  704 */       if (dashboardsinOrder.size() <= 0)
/*      */       {
/*  706 */         dashboardorder = DBQueryUtil.getTopNValues("select PAGEID,PAGENAME,'URL',1 from AM_MYPAGES order by PAGEID asc", 4);
/*      */         
/*  708 */         dashboardsinOrder = mo.getRows(dashboardorder);
/*  709 */         if (dashboardsinOrder.size() > 0) {
/*  710 */           if (request.getParameter("selectedpageid") != null) {
/*  711 */             if (request.getParameter("selectedTab") == null)
/*      */             {
/*  713 */               String selectedtab = getTabForPageid(request.getParameter("selectedpageid"), dashboardsinOrder);
/*  714 */               fm.setSelectedTab(selectedtab);
/*      */             }
/*  716 */             dashboardtype_id = request.getParameter("selectedpageid");
/*  717 */             request.setAttribute("pageid", dashboardtype_id);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  722 */             ArrayList firstrow = (ArrayList)dashboardsinOrder.get(0);
/*  723 */             dashboardtype_id = (String)firstrow.get(0);
/*  724 */             request.setAttribute("pageid", dashboardtype_id);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  731 */       ex.printStackTrace();
/*      */     }
/*  733 */     request.setAttribute("dashboardsinOrder", dashboardsinOrder);
/*      */   }
/*      */   
/*      */   private String getTabForPageid(String pageid, ArrayList dashboardsinOrder)
/*      */   {
/*      */     try
/*      */     {
/*  740 */       for (int i = 0; i < dashboardsinOrder.size(); i++) {
/*  741 */         ArrayList firstrow = (ArrayList)dashboardsinOrder.get(i);
/*  742 */         String dashboard_pageid = (String)firstrow.get(0);
/*  743 */         if (pageid.equals(dashboard_pageid)) {
/*  744 */           return String.valueOf(i);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  749 */       ex.printStackTrace();
/*      */     }
/*  751 */     return "10";
/*      */   }
/*      */   
/*      */   public ActionForward newMyPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  756 */     MyPageForm fm = (MyPageForm)form;
/*      */     try {
/*  758 */       if ((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) {
/*  759 */         ActionMessages messages = new ActionMessages();
/*  760 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.mypage.adminactions.noauthorization.text")));
/*  761 */         saveMessages(request, messages);
/*  762 */         return new ActionForward("/MyPage.do?method=viewDashBoard");
/*      */       }
/*  764 */       if (request.isUserInRole("DEMO")) {
/*  765 */         ActionMessages messages = new ActionMessages();
/*  766 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text")));
/*  767 */         saveMessages(request, messages);
/*  768 */         return new ActionForward("/MyPage.do?method=viewDashBoard");
/*      */       }
/*  770 */       String pagetype = request.getParameter("pagetype");
/*      */       
/*  772 */       if ((pagetype != null) && (pagetype.equals("mgtemplate")))
/*      */       {
/*  774 */         fm.setMgtemplatepage(true);
/*  775 */         request.setAttribute("fromMg", "true");
/*  776 */         request.setAttribute("widgetsFile", "Widgets_mgtemplate_" + OEMUtil.getOEMString("product.build.number") + ".xml");
/*  777 */         checkAndCreateWidgetsFile("mgtemplate");
/*  778 */         ArrayList applications1 = null;
/*  779 */         if (EnterpriseUtil.isIt360MSPEdition()) {
/*  780 */           applications1 = AlarmUtil.getConfiguredGroups(request);
/*      */         } else {
/*  782 */           applications1 = AlarmUtil.getConfiguredGroups();
/*      */         }
/*  784 */         if (applications1 != null) {
/*  785 */           request.setAttribute("applications1", applications1);
/*      */         }
/*  787 */         if (request.getParameter("template_resid") != null) {
/*  788 */           String template_resid = request.getParameter("template_resid");
/*  789 */           fm.setAllMGsSelectedForTemplatePage("0");
/*  790 */           ArrayList<String> selectedMultiMgs_list = new ArrayList();
/*  791 */           selectedMultiMgs_list.add(template_resid);
/*  792 */           request.setAttribute("selectedMultiMgs_list", selectedMultiMgs_list);
/*  793 */           request.setAttribute("templateResid", template_resid);
/*      */         }
/*  795 */       } else if ((pagetype != null) && (pagetype.equals("businesspage")))
/*      */       {
/*  797 */         fm.setDisplayName(FormatUtil.getString("am.mypage.mybusinesspage.text"));
/*  798 */         fm.setNumberOfColumns(4);
/*  799 */         fm.setColumnWidth1(25);
/*  800 */         fm.setColumnWidth2(25);
/*  801 */         fm.setColumnWidth3(25);
/*  802 */         fm.setColumnWidth4(25);
/*      */       } else {
/*  804 */         pagetype = "globalpage";
/*  805 */         request.setAttribute("widgetsFile", "Widgets_" + OEMUtil.getOEMString("product.build.number") + ".xml");
/*  806 */         checkAndCreateWidgetsFile("globalpage");
/*      */       }
/*  808 */       fm.setPageType(pagetype);
/*      */     }
/*      */     catch (Exception ex) {
/*  811 */       ex.printStackTrace();
/*      */     }
/*  813 */     return new ActionForward("/jsp/MyPage_New.jsp?action=newMyPage");
/*      */   }
/*      */   
/*      */   public ActionForward getMgsForPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  818 */     MyPageForm fm = (MyPageForm)form;
/*      */     
/*  820 */     ArrayList applications1 = null;
/*  821 */     if (EnterpriseUtil.isIt360MSPEdition()) {
/*  822 */       applications1 = AlarmUtil.getConfiguredGroups(request);
/*      */     } else {
/*  824 */       applications1 = AlarmUtil.getConfiguredGroups();
/*      */     }
/*  826 */     if (applications1 != null) {
/*  827 */       request.setAttribute("applications1", applications1);
/*      */     }
/*  829 */     request.setAttribute("related_action", "availMonitorGroupsforPage");
/*      */     
/*  831 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward editMyPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  835 */     MyPageForm fm = (MyPageForm)form;
/*  836 */     ResultSet rs = null;
/*  837 */     ResultSet rs1 = null;
/*  838 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try
/*      */     {
/*  841 */       Integer.parseInt(request.getParameter("pageid"));
/*      */     } catch (Exception e) {
/*  843 */       AMLog.debug("MyPageAction : editMyPage. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/*  844 */       return new ActionForward("/applications.do", true);
/*      */     }
/*  846 */     String pageid = request.getParameter("pageid");
/*  847 */     String pagetypefromrequest = request.getParameter("pagetype");
/*      */     try {
/*  849 */       if ((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) {
/*  850 */         ActionMessages messages = new ActionMessages();
/*  851 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.mypage.adminactions.noauthorization.text")));
/*  852 */         saveMessages(request, messages);
/*  853 */         return new ActionForward("/MyPage.do?method=viewDashBoard");
/*      */       }
/*  855 */       String mypagequery = "select USERID,PAGENAME,DESCRIPTION,PAGETYPE,NOOFCOLUMNS,COLWIDTH_ONE,COLWIDTH_TWO,COLWIDTH_THREE,COLWIDTH_FOUR,ALLMGSELECTED from AM_MYPAGES where PAGEID=" + pageid;
/*  856 */       rs = AMConnectionPool.executeQueryStmt(mypagequery);
/*  857 */       if (rs.next()) {
/*  858 */         fm.setDisplayName(rs.getString("PAGENAME"));
/*  859 */         fm.setDescription(rs.getString("DESCRIPTION"));
/*  860 */         fm.setNumberOfColumns(rs.getInt("NOOFCOLUMNS"));
/*  861 */         fm.setColumnWidth1(rs.getInt("COLWIDTH_ONE"));
/*  862 */         fm.setColumnWidth2(rs.getInt("COLWIDTH_TWO"));
/*  863 */         fm.setColumnWidth3(rs.getInt("COLWIDTH_THREE"));
/*  864 */         fm.setColumnWidth4(rs.getInt("COLWIDTH_FOUR"));
/*  865 */         fm.setPageType(rs.getString("PAGETYPE"));
/*  866 */         fm.setAllMGsSelectedForTemplatePage(rs.getString("ALLMGSELECTED"));
/*  867 */         if (rs.getString("PAGETYPE").equals("mgtemplate")) {
/*  868 */           fm.setMgtemplatepage(true);
/*      */         } else {
/*  870 */           fm.setMgtemplatepage(false);
/*      */         }
/*      */       }
/*  873 */       AMConnectionPool.closeResultSet(rs);
/*      */       
/*  875 */       if ((pagetypefromrequest != null) && (pagetypefromrequest.equals("globalpage"))) {
/*  876 */         fm.setMgtemplatepage(false);
/*  877 */         fm.setPageType("globalpage");
/*  878 */       } else if ((pagetypefromrequest != null) && (pagetypefromrequest.equals("mgtemplate"))) {
/*  879 */         fm.setMgtemplatepage(true);
/*  880 */         fm.setPageType("mgtemplate");
/*      */       }
/*  882 */       ArrayList applications1 = null;
/*  883 */       if (EnterpriseUtil.isIt360MSPEdition()) {
/*  884 */         applications1 = AlarmUtil.getConfiguredGroups(request);
/*      */       } else {
/*  886 */         applications1 = AlarmUtil.getConfiguredGroups();
/*      */       }
/*  888 */       if (applications1 != null) {
/*  889 */         request.setAttribute("applications1", applications1);
/*      */       }
/*  891 */       request.setAttribute("related_action", "availMonitorGroupsforPage");
/*      */       
/*  893 */       ArrayList selectedMultiMgs_list = new ArrayList();
/*      */       
/*  895 */       if (request.getParameter("template_resid") != null) {
/*  896 */         request.setAttribute("fromMg", "true");
/*  897 */         request.setAttribute("templateResid", request.getParameter("template_resid"));
/*      */       }
/*  899 */       rs1 = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_MYPAGE_TEMPLATE_MGS where PAGEID=" + pageid);
/*  900 */       while (rs1.next()) {
/*  901 */         selectedMultiMgs_list.add(rs1.getString("RESOURCEID"));
/*      */       }
/*  903 */       AMConnectionPool.closeResultSet(rs1);
/*  904 */       request.setAttribute("selectedMultiMgs_list", selectedMultiMgs_list);
/*      */     }
/*      */     catch (Exception ex) {
/*  907 */       ex.printStackTrace();
/*      */     }
/*  909 */     request.setAttribute("editMyPage", "true");
/*  910 */     return new ActionForward("/jsp/MyPage_New.jsp?action=editMyPage");
/*      */   }
/*      */   
/*      */   public ActionForward saveMyPageLayout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  914 */     if (request.isUserInRole("DEMO")) {
/*  915 */       return null;
/*      */     }
/*  917 */     AMConnectionPool pool = new AMConnectionPool();
/*  918 */     Statement stmt = AMConnectionPool.getConnection().createStatement();
/*      */     try {
/*  920 */       ManagedApplication mo = new ManagedApplication();
/*  921 */       String userid = mo.getUserID(request);
/*      */       try
/*      */       {
/*  924 */         Integer.parseInt(request.getParameter("pageid"));
/*      */       } catch (Exception e) {
/*  926 */         AMLog.debug("MyPageAction : saveMyPageLayout. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/*  927 */         return new ActionForward("/applications.do", true);
/*      */       }
/*  929 */       String pageid = request.getParameter("pageid");
/*  930 */       String locationlist = request.getParameter("positionArray");
/*  931 */       String[] locations = locationlist.split(",");
/*  932 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_LAYOUT where PAGEID=" + pageid + " and USERID=" + userid);
/*  933 */       for (int i = 0; i < locations.length; i++) {
/*  934 */         String widgetid_location = locations[i];
/*  935 */         String[] splitStringArray = widgetid_location.split("_");
/*  936 */         String widgetid = splitStringArray[0];
/*  937 */         String location = splitStringArray[1];
/*  938 */         String updayeQuery = "insert into AM_MYPAGE_LAYOUT(PAGEID,WIDGETID,ORDERID,USERID) values (" + pageid + "," + widgetid + "," + location + "," + userid + ")";
/*      */         
/*  940 */         stmt.addBatch(updayeQuery);
/*      */       }
/*  942 */       stmt.executeBatch();
/*  943 */       stmt.clearBatch();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  953 */       return null;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  945 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  948 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward saveMyPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  957 */     String pageid = "-1";
/*  958 */     String choosetab = "&addNewTab=true";
/*  959 */     String fromMg = request.getParameter("fromMg");
/*  960 */     String templateResid = request.getParameter("templateResid");
/*      */     try {
/*  962 */       if (request.isUserInRole("DEMO")) {
/*  963 */         return null;
/*      */       }
/*  965 */       ResultSet rs1 = null;
/*  966 */       ResultSet rs = null;
/*  967 */       String action = request.getParameter("action");
/*  968 */       MyPageForm fm = (MyPageForm)form;
/*  969 */       AMConnectionPool pool = new AMConnectionPool();
/*  970 */       ManagedApplication mo = new ManagedApplication();
/*  971 */       String userid = mo.getUserID(request);
/*  972 */       String displayName = fm.getDisplayName();
/*  973 */       String description = fm.getDescription();
/*  974 */       String pageType = fm.getPageType();
/*      */       
/*  976 */       int noofcolumns = fm.getNumberOfColumns();
/*  977 */       int colwidth_one = fm.getColumnWidth1();
/*  978 */       int colwidth_two = fm.getColumnWidth2();
/*  979 */       int colwidth_three = fm.getColumnWidth3();
/*  980 */       int colwidth_four = fm.getColumnWidth4();
/*  981 */       String allMGsSelectedForTemplatePage = fm.getAllMGsSelectedForTemplatePage();
/*  982 */       if (noofcolumns == 1) {
/*  983 */         colwidth_one = colwidth_two = colwidth_three = colwidth_four = 100;
/*      */       }
/*  985 */       if ((System.getProperty("am.dbserver.type") != null) && (System.getProperty("am.dbserver.type").equals("mssql"))) {
/*  986 */         displayName = displayName.replaceAll("'", "''");
/*  987 */         description = description.replaceAll("'", "''");
/*      */       } else {
/*  989 */         displayName = StrUtil.findReplace(displayName, "\\", "\\\\");
/*  990 */         description = StrUtil.findReplace(description, "'", "\\'");
/*      */       }
/*  992 */       if (action.equals("newMyPage"))
/*      */       {
/*      */ 
/*      */ 
/*  996 */         int pageid_int = -1;
/*      */         try {
/*  998 */           rs = AMConnectionPool.executeQueryStmt("select max(PAGEID) as maxid from AM_MYPAGES");
/*  999 */           if (rs.next()) {
/* 1000 */             if (rs.getString("maxid") == null)
/*      */             {
/* 1002 */               pageid_int = 10000001;
/*      */             } else {
/* 1004 */               pageid_int = rs.getInt("maxid") + 1;
/*      */             }
/*      */           }
/*      */           
/* 1008 */           rs.close();
/*      */         }
/*      */         catch (Exception ex) {
/* 1011 */           ex.printStackTrace();
/*      */         }
/* 1013 */         pageid = String.valueOf(pageid_int);
/*      */         
/* 1015 */         String myPageQuery = "insert into AM_MYPAGES(PAGEID,USERID,PAGENAME,DESCRIPTION,PAGETYPE,NOOFCOLUMNS,COLWIDTH_ONE,COLWIDTH_TWO,COLWIDTH_THREE,COLWIDTH_FOUR,ALLMGSELECTED) values(" + pageid + ",'" + userid + "','" + displayName + "','" + description + "','" + pageType + "'," + noofcolumns + "," + colwidth_one + "," + colwidth_two + "," + colwidth_three + "," + colwidth_four + "," + allMGsSelectedForTemplatePage + ")";
/* 1016 */         AMConnectionPool.executeUpdateStmt(myPageQuery);
/*      */         
/* 1018 */         request.setAttribute("pageid", pageid);
/*      */         
/* 1020 */         addWidgets(mapping, form, request, response);
/* 1021 */         DashboardUtil.createandMapWidgets("300", pageid, userid);
/*      */       }
/* 1023 */       else if (action.equals("editMyPage"))
/*      */       {
/* 1025 */         pageid = request.getParameter("pageid");
/* 1026 */         String myPageUpdateQuery = "update AM_MYPAGES set PAGENAME='" + displayName + "',PAGETYPE='" + pageType + "' ,DESCRIPTION='" + description + "',NOOFCOLUMNS=" + noofcolumns + ",COLWIDTH_ONE=" + colwidth_one + ",COLWIDTH_TWO=" + colwidth_two + ",COLWIDTH_THREE=" + colwidth_three + ",COLWIDTH_FOUR=" + colwidth_four + ",ALLMGSELECTED=" + allMGsSelectedForTemplatePage + " where PAGEID=" + pageid;
/*      */         
/* 1028 */         AMConnectionPool.executeUpdateStmt(myPageUpdateQuery);
/* 1029 */         if (pageType.equals("mgtemplate"))
/*      */         {
/* 1031 */           AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_TEMPLATE_MGS where PAGEID=" + pageid);
/*      */         }
/* 1033 */         choosetab = "&selectedTab=" + fm.getSelectedTab();
/*      */       }
/*      */       
/*      */ 
/* 1037 */       if ((pageType.equals("mgtemplate")) && (allMGsSelectedForTemplatePage.equals("0")))
/*      */       {
/* 1039 */         String[] selectedMultiMonitors = fm.getSelectedMultiMonitors();
/* 1040 */         Statement stmt2 = AMConnectionPool.getConnection().createStatement();
/* 1041 */         for (int i = 0; i < selectedMultiMonitors.length; i++) {
/* 1042 */           stmt2.addBatch("insert into AM_MYPAGE_TEMPLATE_MGS(PAGEID,RESOURCEID) values (" + pageid + "," + selectedMultiMonitors[i] + ")");
/*      */         }
/* 1044 */         stmt2.executeBatch();
/* 1045 */         stmt2.clearBatch();
/* 1046 */         stmt2.close();
/*      */       }
/*      */     } catch (Exception ex) {
/* 1049 */       ex.printStackTrace();
/*      */     }
/* 1051 */     if ((fromMg != null) && (fromMg.equals("true"))) {
/* 1052 */       if (templateResid.equals("")) {
/* 1053 */         String templateResidForMG = request.getParameter("template_resid");
/* 1054 */         return new ActionForward("/showapplication.do?&method=showApplication&haid=" + templateResidForMG);
/*      */       }
/* 1056 */       return new ActionForward("/showapplication.do?&method=showApplication&haid=" + templateResid + "&pageid=" + pageid, true);
/*      */     }
/*      */     
/* 1059 */     return new ActionForward("/MyPage.do?method=viewDashBoard&forpage=" + forpage + "&selectedpageid=" + pageid + choosetab, true);
/*      */   }
/*      */   
/*      */   public ActionForward deleteMyPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1063 */     AMConnectionPool pool = new AMConnectionPool();
/* 1064 */     ResultSet rs = null;
/* 1065 */     String pageidtoshow = "";
/* 1066 */     Statement stmt = AMConnectionPool.getConnection().createStatement();
/* 1067 */     String template_resid = request.getParameter("template_resid");
/*      */     try { ActionForward localActionForward;
/* 1069 */       if (request.isUserInRole("DEMO")) {
/* 1070 */         ActionMessages messages = new ActionMessages();
/* 1071 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text")));
/* 1072 */         saveMessages(request, messages);
/* 1073 */         return new ActionForward("/MyPage.do?method=viewDashBoard&forpage=1");
/*      */       }
/*      */       try
/*      */       {
/* 1077 */         Integer.parseInt(request.getParameter("pageid"));
/*      */       } catch (Exception e) {
/* 1079 */         AMLog.debug("MyPageAction : editMyPage. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/* 1080 */         return new ActionForward("/applications.do", true);
/*      */       }
/* 1082 */       String pageid = request.getParameter("pageid");
/* 1083 */       request.setAttribute("pageid", pageid);
/* 1084 */       if (com.adventnet.appmanager.util.Constants.defaultDashboardIds.contains(Integer.valueOf(Integer.parseInt(pageid)))) {
/* 1085 */         ActionMessages messages = new ActionMessages();
/* 1086 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.mypage.cannotdelete.dashboards.text")));
/* 1087 */         saveMessages(request, messages);
/* 1088 */         return new ActionForward("/MyPage.do?method=viewDashBoard&forpage=1");
/*      */       }
/* 1090 */       String associatedResTypeDelQuery = "DELETE FROM AM_WIDGETS_ASSOCIATEDTYPES WHERE WIDGETID IN (SELECT AM_MYPAGE_WIDGETS.WIDGETID FROM AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGETS WHERE AM_MYPAGE_WIDGET_MAPPING.PAGEID='" + pageid + "' AND AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID)";
/* 1091 */       String widgetDeleteQuery = "delete AM_MYPAGE_WIDGETS from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGETS where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1092 */       String widgetAttributesQuery = "delete AM_MYPAGE_WIDGET_ATTRIBUTES from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGET_ATTRIBUTES where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_ATTRIBUTES.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1093 */       String widgetMonitorsQuery = "delete AM_MYPAGE_WIDGET_MONITORS from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGET_MONITORS where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_MONITORS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/*      */       
/* 1095 */       String widgetViewsQuery = "delete from AM_MYPAGE_WIDGET_FLASHVIEWS where WIDGETID in (select widgetview.WIDGETID from AM_MYPAGE_WIDGET_FLASHVIEWS widgetview join AM_MYPAGE_WIDGET_MAPPING pagewidget on pagewidget.WIDGETID=widgetview.WIDGETID where pagewidget.PAGEID=" + pageid + ")";
/* 1096 */       String deletefromwidgetmapping = "delete from AM_MYPAGE_WIDGET_MAPPING where PAGEID=" + pageid;
/* 1097 */       String layoutDeleteQuery = "delete from AM_MYPAGE_LAYOUT where PAGEID=" + pageid;
/* 1098 */       String deletefromDashBoards = "delete from AM_DASHBOARDS where PAGEID=" + pageid;
/* 1099 */       String pagekeysdeleteQuery = "delete from AM_MYPAGE_PUBLISHKEYS where PAGEID=" + pageid;
/* 1100 */       String templatemgDeletQuery = "delete from  AM_MYPAGE_TEMPLATE_MGS where PAGEID=" + pageid;
/* 1101 */       String pageDeleteQuery = "delete from AM_MYPAGES where PAGEID=" + pageid;
/* 1102 */       if (DBQueryUtil.isPgsql()) {
/* 1103 */         widgetDeleteQuery = "delete from AM_MYPAGE_WIDGETS using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1104 */         widgetAttributesQuery = "delete from AM_MYPAGE_WIDGET_ATTRIBUTES using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_ATTRIBUTES.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1105 */         widgetMonitorsQuery = "delete from AM_MYPAGE_WIDGET_MONITORS using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_MONITORS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/*      */       }
/* 1107 */       stmt.addBatch(widgetViewsQuery);
/* 1108 */       stmt.addBatch(associatedResTypeDelQuery);
/* 1109 */       stmt.addBatch(widgetDeleteQuery);
/* 1110 */       stmt.addBatch(widgetAttributesQuery);
/* 1111 */       stmt.addBatch(widgetMonitorsQuery);
/* 1112 */       stmt.addBatch(layoutDeleteQuery);
/* 1113 */       stmt.addBatch(deletefromDashBoards);
/* 1114 */       stmt.addBatch(deletefromwidgetmapping);
/* 1115 */       stmt.addBatch(pagekeysdeleteQuery);
/* 1116 */       stmt.addBatch(templatemgDeletQuery);
/* 1117 */       stmt.addBatch(pageDeleteQuery);
/* 1118 */       stmt.executeBatch();
/* 1119 */       stmt.clearBatch();
/* 1120 */       stmt.close();
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1125 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1134 */       reloadTabs(request, response);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1122 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1125 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1135 */     if (template_resid != null) {
/* 1136 */       return new ActionForward("/showapplication.do?method=showApplication&haid=" + template_resid, true);
/*      */     }
/* 1138 */     return new ActionForward("/MyPage.do?method=viewDashBoard&forpage=1", true);
/*      */   }
/*      */   
/*      */   private void reloadTabs(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1143 */     ArrayList orderedTabList = new ArrayList();
/* 1144 */     ResultSet rs = null;ResultSet rs_orderedTab = null;
/* 1145 */     String userid = null;
/*      */     try {
/* 1147 */       HttpSession session = request.getSession();
/*      */       try
/*      */       {
/* 1150 */         Integer.parseInt(request.getParameter("pageid"));
/*      */       } catch (Exception e) {
/* 1152 */         AMLog.debug("MyPageAction : reloadTabs. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage()); return;
/*      */       }
/*      */       
/* 1155 */       String pageID = request.getAttribute("pageid").toString();
/* 1156 */       ManagedApplication mo = new ManagedApplication();
/* 1157 */       userid = mo.getUserID(request);
/*      */       
/* 1159 */       rs = AMConnectionPool.executeQueryStmt("select TABID from AM_TABUSERMAPPING where TABID=" + pageID + " and userid=" + userid);
/* 1160 */       if (rs.next()) {
/* 1161 */         String consoletype = request.getParameter("consoletype");
/* 1162 */         String viewtype = "1";
/* 1163 */         String deleteOldOrder = "delete from AM_TABUSERMAPPING where TABID=" + pageID + " and userid=" + userid;
/* 1164 */         if ((consoletype != null) && (consoletype.trim().equals("sla"))) {
/* 1165 */           viewtype = "2";
/*      */         }
/* 1167 */         deleteOldOrder = deleteOldOrder + " and VIEWTYPE=" + viewtype;
/* 1168 */         AMConnectionPool.executeUpdateStmt(deleteOldOrder);
/*      */         
/* 1170 */         rs_orderedTab = AMConnectionPool.executeQueryStmt("select TABID from AM_TABUSERMAPPING where userid=" + userid + " and VIEWTYPE=" + viewtype);
/* 1171 */         while (rs.next()) {
/* 1172 */           orderedTabList.add(rs.getString("TABID"));
/*      */         }
/* 1174 */         AMConnectionPool.closeStatement(rs_orderedTab);
/* 1175 */         session.setAttribute("orderedtablist", orderedTabList);
/*      */       }
/* 1177 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1182 */         if (rs_orderedTab != null) {
/* 1183 */           rs_orderedTab.close();
/*      */         }
/* 1185 */         if (rs != null) {
/* 1186 */           rs.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 1189 */         e.printStackTrace();
/*      */       }
/*      */       
/* 1192 */       DBUtil.reloadOrderedTabIdList(request.getRemoteUser(), userid);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1179 */       exc.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1182 */         if (rs_orderedTab != null) {
/* 1183 */           rs_orderedTab.close();
/*      */         }
/* 1185 */         if (rs != null) {
/* 1186 */           rs.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 1189 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward viewMyPageForMG(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1197 */     response.setContentType("text/xml; charset=UTF-8");
/* 1198 */     String template_resid = request.getParameter("template_resid");
/*      */     try
/*      */     {
/* 1201 */       if (request.getParameter("pageid") != null) {
/* 1202 */         Integer.parseInt(request.getParameter("pageid"));
/*      */       }
/*      */     } catch (Exception e) {
/* 1205 */       AMLog.debug("MyPageAction : reloadTabs. Incorrect values obtained for pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/* 1206 */       return new ActionForward("/applications.do", true);
/*      */     }
/* 1208 */     String pageid = request.getParameter("pageid");
/*      */     
/* 1210 */     String gpType = GroupComponent.getGroupType(template_resid);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1215 */     AMConnectionPool pool = new AMConnectionPool();
/* 1216 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1219 */       if (pageid == null) {
/* 1220 */         ManagedApplication mo = new ManagedApplication();
/* 1221 */         String userid = mo.getUserID(request);
/* 1222 */         String mypageQuery = "select AM_MYPAGES.PAGEID,AM_MYPAGES.PAGENAME from AM_DASHBOARDS inner join AM_MYPAGES on AM_MYPAGES.PAGEID=AM_DASHBOARDS.PAGEID where FORPAGE=2 and OWNERID=" + userid + " and RESOURCEID=" + template_resid;
/* 1223 */         rs = AMConnectionPool.executeQueryStmt(mypageQuery);
/* 1224 */         if (rs.next()) {
/* 1225 */           pageid = rs.getString("PAGEID");
/*      */         }
/* 1227 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       
/* 1230 */       if (pageid == null) {
/* 1231 */         String mypageQuery = DBQueryUtil.getTopNValues("select AM_MYPAGES.PAGEID,AM_MYPAGES.PAGENAME from AM_MYPAGE_TEMPLATE_MGS inner join AM_MYPAGES on AM_MYPAGE_TEMPLATE_MGS.PAGEID=AM_MYPAGES.PAGEID  where RESOURCEID=" + template_resid, 1);
/* 1232 */         rs = AMConnectionPool.executeQueryStmt(mypageQuery);
/* 1233 */         if (rs.next()) {
/* 1234 */           pageid = rs.getString("PAGEID");
/*      */         }
/* 1236 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       
/* 1239 */       if (pageid == null) {
/* 1240 */         String mypageQuery = DBQueryUtil.getTopNValues("select AM_MYPAGES.PAGEID,AM_MYPAGES.PAGENAME from  AM_MYPAGES   where PAGETYPE='mgtemplate' and ALLMGSELECTED=1", 1);
/* 1241 */         rs = AMConnectionPool.executeQueryStmt(mypageQuery);
/* 1242 */         if (rs.next()) {
/* 1243 */           pageid = rs.getString("PAGEID");
/*      */         }
/* 1245 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1251 */       request.setAttribute("fromMyPage", "true");
/* 1252 */       request.setAttribute("pageid", pageid);
/*      */       
/*      */ 
/* 1255 */       request.setAttribute("templateType", "mgtemplate");
/*      */       
/*      */ 
/* 1258 */       if ((pageid == null) && ((!com.adventnet.appmanager.util.Constants.isIt360) || ((com.adventnet.appmanager.util.Constants.isIt360) && (!"3".equals(gpType))))) {
/* 1259 */         ActionMessages messages = new ActionMessages();
/* 1260 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.mypage.mg.notemplate.dashboards.text")));
/* 1261 */         saveMessages(request, messages);
/* 1262 */         request.setAttribute("helpkey", "noDashboards");
/* 1263 */         return new ActionForward("/jsp/helpmessages.jsp?template_resid=" + template_resid);
/*      */       }
/* 1265 */       populatePageProps(mapping, form, request, response);
/*      */     } catch (Exception ex) {
/* 1267 */       ex.printStackTrace();
/*      */     }
/* 1269 */     request.setAttribute("fromWhere", "mgtemplate");
/*      */     
/*      */ 
/* 1272 */     return new ActionForward("/jsp/MyPage.jsp");
/*      */   }
/*      */   
/*      */   private void populatePageProps(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1276 */     response.setContentType("text/html;charset=UTF-8");
/* 1277 */     String pageid = request.getParameter("pageid");
/* 1278 */     if (request.getAttribute("pageid") != null)
/*      */     {
/* 1280 */       pageid = (String)request.getAttribute("pageid");
/*      */     }
/* 1282 */     String userid = null;
/* 1283 */     if (request.getAttribute("userid") != null)
/*      */     {
/* 1285 */       userid = (String)request.getAttribute("userid");
/*      */     }
/* 1287 */     AMConnectionPool pool = new AMConnectionPool();
/* 1288 */     MyPageForm fm = (MyPageForm)form;
/* 1289 */     ResultSet rs1 = null;
/* 1290 */     ArrayList widgetsinorder = new ArrayList();
/*      */     try {
/* 1292 */       ManagedApplication mo = new ManagedApplication();
/* 1293 */       if (userid == null) {
/* 1294 */         userid = mo.getUserID(request);
/*      */       }
/* 1296 */       if ((userid == null) || (userid.equals("-1"))) {
/* 1297 */         userid = DashboardUtil.getAdminUserId();
/*      */       }
/* 1299 */       int noofcolumns = 2;
/* 1300 */       int[] colwidths = { 50, 50, 30, 30 };
/*      */       try {
/* 1302 */         String columnsquery = "select PAGEID,PAGENAME,NOOFCOLUMNS,COLWIDTH_ONE,COLWIDTH_TWO,COLWIDTH_THREE,COLWIDTH_FOUR from AM_MYPAGES where PAGEID=" + pageid;
/* 1303 */         if (pageid == null) {
/* 1304 */           String templateType = (String)request.getAttribute("templateType");
/* 1305 */           if ((templateType != null) && (templateType.equals("mgtemplate")))
/*      */           {
/* 1307 */             String template_resid = request.getParameter("template_resid");
/* 1308 */             columnsquery = "select PAGEID,PAGENAME,NOOFCOLUMNS,COLWIDTH_ONE,COLWIDTH_TWO,COLWIDTH_THREE,COLWIDTH_FOUR from AM_MYPAGES where PAGETYPE ='mgtemplate' order by PAGENAME";
/* 1309 */             columnsquery = DBQueryUtil.getTopNValues(columnsquery, 1);
/*      */           } else {
/* 1311 */             columnsquery = "select PAGEID,PAGENAME,NOOFCOLUMNS,COLWIDTH_ONE,COLWIDTH_TWO,COLWIDTH_THREE,COLWIDTH_FOUR from AM_MYPAGES order by PAGEID";
/* 1312 */             columnsquery = DBQueryUtil.getTopNValues(columnsquery, 1);
/*      */           }
/*      */         }
/*      */         
/* 1316 */         rs1 = AMConnectionPool.executeQueryStmt(columnsquery);
/* 1317 */         if (rs1.next())
/*      */         {
/*      */ 
/* 1320 */           noofcolumns = rs1.getInt("NOOFCOLUMNS");
/* 1321 */           colwidths[0] = rs1.getInt("COLWIDTH_ONE");
/* 1322 */           colwidths[1] = rs1.getInt("COLWIDTH_TWO");
/* 1323 */           colwidths[2] = rs1.getInt("COLWIDTH_THREE");
/* 1324 */           colwidths[3] = rs1.getInt("COLWIDTH_FOUR");
/* 1325 */           pageid = rs1.getString("PAGEID");
/* 1326 */           fm.setDisplayName(rs1.getString("PAGENAME"));
/* 1327 */           fm.setNumberOfColumns(noofcolumns);
/* 1328 */           request.setAttribute("pageid", pageid);
/* 1329 */           request.setAttribute("numberOfColumns", Integer.valueOf(noofcolumns));
/*      */         }
/* 1331 */         rs1.close();
/* 1332 */         String widgetQuery = "";
/* 1333 */         if (DBQueryUtil.isMssql())
/*      */         {
/* 1335 */           widgetQuery = "select AM_MYPAGE_WIDGET_MAPPING.WIDGETID,case when AM_BUSINESSHOURSDETAILS.NAME is null THEN DISPLAYNAME ELSE DISPLAYNAME+'   ['+AM_BUSINESSHOURSDETAILS.NAME+']' END DISPLAYNAME, AM_MYPAGE_LAYOUT.ORDERID  from  AM_MYPAGE_WIDGET_MAPPING inner join AM_MYPAGE_WIDGETS on AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID left outer join AM_BUSINESSHOURSDETAILS on AM_BUSINESSHOURSDETAILS.ID=AM_MYPAGE_WIDGETS.BUSINESSRULE  left outer join AM_MYPAGE_LAYOUT on AM_MYPAGE_WIDGET_MAPPING.WIDGETID=AM_MYPAGE_LAYOUT.WIDGETID and AM_MYPAGE_LAYOUT.PAGEID=" + pageid + " and USERID=" + userid + " where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " order by ORDERID asc,AM_MYPAGE_LAYOUT.WIDGETID desc";
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           widgetQuery = "select AM_MYPAGE_WIDGET_MAPPING.WIDGETID,case when AM_BUSINESSHOURSDETAILS.NAME is null THEN DISPLAYNAME ELSE concat(DISPLAYNAME,'   [',AM_BUSINESSHOURSDETAILS.NAME,']') END DISPLAYNAME, AM_MYPAGE_LAYOUT.ORDERID  from  AM_MYPAGE_WIDGET_MAPPING inner join AM_MYPAGE_WIDGETS on AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID left outer join AM_BUSINESSHOURSDETAILS on AM_BUSINESSHOURSDETAILS.ID=AM_MYPAGE_WIDGETS.BUSINESSRULE  left outer join AM_MYPAGE_LAYOUT on AM_MYPAGE_WIDGET_MAPPING.WIDGETID=AM_MYPAGE_LAYOUT.WIDGETID and AM_MYPAGE_LAYOUT.PAGEID=" + pageid + " and USERID=" + userid + " where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " order by ORDERID asc,AM_MYPAGE_LAYOUT.WIDGETID desc";
/*      */         }
/*      */         
/* 1342 */         if (request.isUserInRole("OPERATOR")) {
/* 1343 */           String checkOrderExistsQuery = "select ORDERID from AM_MYPAGE_LAYOUT where PAGEID=" + pageid + " and USERID=" + userid;
/* 1344 */           checkOrderExistsQuery = DBQueryUtil.getTopNValues(checkOrderExistsQuery, 1);
/* 1345 */           ArrayList orderList = mo.getRowsForSingleColumn(checkOrderExistsQuery);
/* 1346 */           if (orderList.size() == 0) {
/* 1347 */             String adminid = DashboardUtil.getAdminUserId();
/* 1348 */             widgetQuery = "select AM_MYPAGE_WIDGET_MAPPING.WIDGETID,DISPLAYNAME, AM_MYPAGE_LAYOUT.ORDERID  from  AM_MYPAGE_WIDGET_MAPPING inner join AM_MYPAGE_WIDGETS on AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID  left outer join AM_MYPAGE_LAYOUT on AM_MYPAGE_WIDGET_MAPPING.WIDGETID=AM_MYPAGE_LAYOUT.WIDGETID and AM_MYPAGE_LAYOUT.PAGEID=" + pageid + " and USERID=" + adminid + " where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " order by ORDERID asc,AM_MYPAGE_LAYOUT.WIDGETID desc";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1353 */         ArrayList widgets = new ArrayList();
/*      */         try {
/* 1355 */           widgets = mo.getRowsFromDB(widgetQuery);
/*      */         } catch (Exception ex) {
/* 1357 */           ex.printStackTrace();
/* 1358 */           request.setAttribute("widgets", widgets);
/*      */           
/* 1360 */           ArrayList widgetcolumns = new ArrayList();
/* 1361 */           for (int i = 0; i < noofcolumns; i++) {
/* 1362 */             widgetcolumns.add(Integer.valueOf(colwidths[i]));
/*      */           }
/* 1364 */           request.setAttribute("widgetcolumns", widgetcolumns);
/* 1365 */           request.setAttribute("reloadperiod", "300");
/* 1366 */           return;
/*      */         }
/* 1368 */         long maxWidgetid = 0L;
/* 1369 */         for (int j = 0; j < widgets.size(); j++) {
/* 1370 */           ArrayList singlerow = (ArrayList)widgets.get(j);
/* 1371 */           String order = (String)singlerow.get(2);
/*      */           
/* 1373 */           long widgetid = Long.valueOf((String)singlerow.get(0)).longValue();
/* 1374 */           if (widgetid > maxWidgetid) {
/* 1375 */             maxWidgetid = widgetid;
/*      */           }
/* 1377 */           if (order == null) {
/* 1378 */             order = j + "";
/*      */           }
/* 1380 */           int orderid = Integer.parseInt(order);
/* 1381 */           int colid = orderid % noofcolumns;
/* 1382 */           singlerow.set(2, Integer.valueOf(colid));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1388 */         if (widgets.size() == 0) {
/* 1389 */           String selectedWidgets = "300";
/* 1390 */           DashboardUtil.createandMapWidgets(selectedWidgets, pageid, userid);
/*      */           
/* 1392 */           widgetQuery = "select AM_MYPAGE_WIDGET_MAPPING.WIDGETID,DISPLAYNAME,COALESCE(AM_MYPAGE_LAYOUT.ORDERID,'0') from  AM_MYPAGE_WIDGET_MAPPING inner join AM_MYPAGE_WIDGETS on AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID  left outer join AM_MYPAGE_LAYOUT on AM_MYPAGE_WIDGET_MAPPING.WIDGETID=AM_MYPAGE_LAYOUT.WIDGETID and AM_MYPAGE_LAYOUT.PAGEID=" + pageid + " and USERID=" + userid + " where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " order by ORDERID asc,AM_MYPAGE_LAYOUT.WIDGETID desc";
/* 1393 */           widgets = mo.getRows(widgetQuery);
/*      */         }
/* 1395 */         request.setAttribute("maxWidget", Long.valueOf(maxWidgetid));
/* 1396 */         request.setAttribute("widgets", widgets);
/* 1397 */         ArrayList widgetcolumns = new ArrayList();
/* 1398 */         for (int i = 0; i < noofcolumns; i++) {
/* 1399 */           widgetcolumns.add(Integer.valueOf(colwidths[i]));
/*      */         }
/* 1401 */         request.setAttribute("widgetcolumns", widgetcolumns);
/*      */       } catch (Exception ex) {
/* 1403 */         ex.printStackTrace();
/*      */       }
/*      */     } catch (Exception ex) {
/* 1406 */       ex.printStackTrace();
/*      */     }
/* 1408 */     request.setAttribute("reloadperiod", "300");
/*      */   }
/*      */   
/*      */   public ActionForward viewMyPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1412 */     String pageid = request.getParameter("pageid");
/* 1413 */     if (request.getAttribute("pageid") != null)
/*      */     {
/* 1415 */       pageid = (String)request.getAttribute("pageid");
/*      */     }
/* 1417 */     AMConnectionPool pool = new AMConnectionPool();
/* 1418 */     MyPageForm fm = (MyPageForm)form;
/* 1419 */     ResultSet rs1 = null;
/*      */     try {
/* 1421 */       populatePageProps(mapping, form, request, response);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1428 */       String pageQuery = "select PAGEID,PAGENAME from  AM_MYPAGES  order by PAGENAME";
/* 1429 */       ArrayList pages = this.mo.getRows(pageQuery);
/* 1430 */       request.setAttribute("pages", pages);
/*      */     }
/*      */     catch (Exception ex) {
/* 1433 */       ex.printStackTrace();
/*      */     }
/* 1435 */     request.setAttribute("pageid", pageid);
/* 1436 */     return new ActionForward("/jsp/MyPage.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward newWidgets(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1440 */     if (request.isUserInRole("OPERATOR")) {
/* 1441 */       return mapping.findForward("accessRestricted");
/*      */     }
/* 1443 */     ResultSet rs = null;
/* 1444 */     String pagetype = "globalpage";
/* 1445 */     String pagename = "";
/* 1446 */     AMConnectionPool pool = new AMConnectionPool();
/* 1447 */     MyPageForm fm = (MyPageForm)form;
/* 1448 */     String pageid = request.getParameter("pageid");
/*      */     try {
/* 1450 */       request.setAttribute("pageid", pageid);
/* 1451 */       String pageQuery = "select PAGETYPE,PAGENAME  from  AM_MYPAGES  where PAGEID=" + pageid;
/* 1452 */       rs = AMConnectionPool.executeQueryStmt(pageQuery);
/* 1453 */       if (rs.next()) {
/* 1454 */         pagetype = rs.getString("PAGETYPE");
/* 1455 */         pagename = rs.getString("PAGENAME");
/*      */       }
/* 1457 */       rs.close();
/*      */     } catch (Exception ex) {
/* 1459 */       ex.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/* 1463 */       fm.setPageType(pagetype);
/* 1464 */       if ((pagetype != null) && (pagetype.equals("mgtemplate")))
/*      */       {
/* 1466 */         request.setAttribute("widgetsFile", "Widgets_mgtemplate_" + OEMUtil.getOEMString("product.build.number") + ".xml");
/* 1467 */         checkAndCreateWidgetsFile("mgtemplate");
/* 1468 */       } else if ((pagetype == null) || (!pagetype.equals("businesspage")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1473 */         if (pageid.equalsIgnoreCase((String)extProdDashboards.get(OPMAN_DASHBOARD_NAME))) {
/* 1474 */           request.setAttribute("widgetsFile", "Widgets_OPMANAGER_" + OEMUtil.getOEMString("product.build.number") + ".xml");
/* 1475 */           checkAndCreateWidgetsFile("OPMANAGER");
/*      */         }
/*      */         else
/*      */         {
/* 1479 */           request.setAttribute("widgetsFile", "Widgets_" + OEMUtil.getOEMString("product.build.number") + ".xml");
/* 1480 */           checkAndCreateWidgetsFile("globalpage");
/*      */         }
/*      */       }
/* 1483 */       request.setAttribute("pagename", pagename);
/*      */     } catch (Exception ex) {
/* 1485 */       ex.printStackTrace();
/*      */     }
/* 1487 */     return new ActionForward("/jsp/MyPage_AddWidgets.jsp");
/*      */   }
/*      */   
/*      */   private void createWidgetsFile(File widgetsfile, boolean isMgTemplate, String pagetype)
/*      */   {
/* 1492 */     ResultSet rs1 = null;
/* 1493 */     ResultSet rs2 = null;
/* 1494 */     ArrayList categorylist = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1501 */     FileWriter fw = null;
/*      */     try {
/* 1503 */       ArrayList skipWidgetsForMGTemplatePages = new ArrayList();
/* 1504 */       ArrayList skipCategoryForMGTemplatePages = new ArrayList();
/* 1505 */       skipWidgetsForMGTemplatePages.add("2");
/* 1506 */       skipWidgetsForMGTemplatePages.add("104");
/* 1507 */       skipWidgetsForMGTemplatePages.add("105");
/* 1508 */       skipWidgetsForMGTemplatePages.add("106");
/* 1509 */       skipWidgetsForMGTemplatePages.add("21");
/* 1510 */       skipWidgetsForMGTemplatePages.add("51");
/* 1511 */       skipWidgetsForMGTemplatePages.add("201");
/* 1512 */       skipCategoryForMGTemplatePages.add("1003");
/* 1513 */       skipCategoryForMGTemplatePages.add("1004");
/* 1514 */       skipCategoryForMGTemplatePages.add("1005");
/* 1515 */       HashMap categorymap = new HashMap();
/*      */       
/* 1517 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 1518 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 1519 */       Document doc = docBuilder.newDocument();
/* 1520 */       Element rootnode = doc.createElement("tree");
/* 1521 */       rootnode.setAttribute("id", "0");
/* 1522 */       doc.appendChild(rootnode);
/* 1523 */       String query = "select CATEGORYID,NAME,DESCRIPTION from AM_MYPAGE_WIDGET_CATEGORY";
/*      */       
/* 1525 */       if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (pagetype.equalsIgnoreCase("OPMANAGER"))) {
/* 1526 */         query = query + " where NAME in ('am.mypage.widgettypes.opman.categ.name')";
/*      */       }
/*      */       
/*      */ 
/* 1530 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 1532 */       while (rs1.next()) {
/* 1533 */         String categoryid = rs1.getString("CATEGORYID");
/* 1534 */         if ((!isMgTemplate) || (!skipCategoryForMGTemplatePages.contains(categoryid)))
/*      */         {
/*      */ 
/* 1537 */           String cateogoryname = rs1.getString("NAME");
/* 1538 */           String cateogorydesc = rs1.getString("DESCRIPTION");
/* 1539 */           cateogoryname = FormatUtil.getString(cateogoryname);
/* 1540 */           Element node = doc.createElement("item");
/* 1541 */           node.setAttribute("id", categoryid);
/* 1542 */           node.setAttribute("open", "1");
/* 1543 */           node.setAttribute("text", cateogoryname);
/* 1544 */           rootnode.appendChild(node);
/* 1545 */           categorymap.put(categoryid, node);
/*      */         }
/*      */       }
/* 1548 */       rs2 = AMConnectionPool.executeQueryStmt("select TYPEID,NAME,DESCRIPTION,CATEGORYID from AM_MYPAGE_WIDGET_TYPES");
/* 1549 */       while (rs2.next()) {
/* 1550 */         String widgettypeid = rs2.getString("TYPEID");
/* 1551 */         if (((!isMgTemplate) || (!skipWidgetsForMGTemplatePages.contains(widgettypeid))) && (
/*      */         
/*      */ 
/* 1554 */           (!EnterpriseUtil.isAdminServer) || (!widgettypeid.equals("104"))))
/*      */         {
/*      */ 
/* 1557 */           String widgettypename = rs2.getString("NAME");
/* 1558 */           String widgettyprdesc = rs2.getString("DESCRIPTION");
/* 1559 */           String categoryid = rs2.getString("CATEGORYID");
/* 1560 */           Element node = doc.createElement("item");
/* 1561 */           node.setAttribute("id", widgettypeid);
/* 1562 */           node.setAttribute("text", FormatUtil.getString(widgettypename));
/* 1563 */           node.setAttribute("tooltip", FormatUtil.getString(widgettyprdesc));
/* 1564 */           Element parentnode = (Element)categorymap.get(categoryid);
/* 1565 */           if (parentnode != null) {
/* 1566 */             parentnode.appendChild(node);
/*      */           }
/*      */         }
/*      */       }
/* 1570 */       widgetsfile.createNewFile();
/* 1571 */       fw = new FileWriter(widgetsfile, true);
/* 1572 */       Source source = new DOMSource(doc);
/* 1573 */       Result result = new StreamResult(fw);
/* 1574 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 1575 */       transformer.setOutputProperty("method", "xml");
/* 1576 */       transformer.setOutputProperty("encoding", "UTF-8");
/* 1577 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "20");
/* 1578 */       transformer.setOutputProperty("indent", "yes");
/* 1579 */       transformer.transform(source, result);
/*      */     } catch (Exception ex) {
/* 1581 */       ex.printStackTrace();
/*      */     } finally {
/* 1583 */       if (rs1 != null) {
/* 1584 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/* 1586 */       if (rs2 != null) {
/* 1587 */         AMConnectionPool.closeStatement(rs2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void checkAndCreateWidgetsFile(String pagetype)
/*      */   {
/* 1595 */     String filename = "Widgets_" + OEMUtil.getOEMString("product.build.number") + ".xml";
/* 1596 */     boolean isMgTemplate = false;
/* 1597 */     if ((pagetype != null) && (pagetype.equals("mgtemplate")))
/*      */     {
/* 1599 */       isMgTemplate = true;
/* 1600 */       filename = "Widgets_mgtemplate_" + OEMUtil.getOEMString("product.build.number") + ".xml";
/*      */     }
/*      */     
/* 1603 */     if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (pagetype.equalsIgnoreCase("OPMANAGER"))) {
/* 1604 */       filename = "Widgets_OPMANAGER_" + OEMUtil.getOEMString("product.build.number") + ".xml";
/*      */     }
/*      */     try
/*      */     {
/* 1608 */       File widgetsfile = new File("." + File.separator + "webclient" + File.separator + filename);
/* 1609 */       if ((!widgetsfile.exists()) || (checkWidgetFileCorrupted(widgetsfile)))
/*      */       {
/* 1611 */         createWidgetsFile(widgetsfile, isMgTemplate, pagetype);
/*      */       } else {
/* 1613 */         ArrayList checkids = new ArrayList();
/*      */         
/* 1615 */         if ((pagetype != null) && (pagetype.equals("mgtemplate"))) {
/* 1616 */           checkids.add("1001");
/* 1617 */           checkids.add("1002");
/* 1618 */           checkids.add("1006");
/*      */         }
/*      */         else {
/* 1621 */           checkids.add("1001");
/* 1622 */           checkids.add("1002");
/* 1623 */           checkids.add("1004");
/* 1624 */           checkids.add("1005");
/* 1625 */           checkids.add("1006");
/*      */         }
/*      */         try
/*      */         {
/* 1629 */           DocumentBuilderFactory document = DocumentBuilderFactory.newInstance();
/* 1630 */           DocumentBuilder builder = document.newDocumentBuilder();
/* 1631 */           Document doc = builder.parse(widgetsfile);
/* 1632 */           NodeList mainList = doc.getElementsByTagName("tree");
/* 1633 */           for (int i = 0; i < mainList.getLength(); i++)
/*      */           {
/* 1635 */             Node node = mainList.item(i);
/* 1636 */             NodeList childNodeList = node.getChildNodes();
/* 1637 */             for (int j = 0; j < childNodeList.getLength(); j++) {
/* 1638 */               Node childnode = childNodeList.item(j);
/* 1639 */               if (childnode.getNodeType() == 1) {
/* 1640 */                 NamedNodeMap attr = childnode.getAttributes();
/* 1641 */                 for (int k = 0; k < attr.getLength(); k++) {
/* 1642 */                   Node attrname = attr.item(k);
/* 1643 */                   if (attrname.getNodeName().equals("id")) {
/* 1644 */                     checkids.remove(attrname.getNodeValue());
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 1651 */           if (!checkids.isEmpty()) {
/* 1652 */             widgetsfile.delete();
/* 1653 */             createWidgetsFile(widgetsfile, isMgTemplate, pagetype);
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 1657 */           ex.printStackTrace();
/* 1658 */           widgetsfile.delete();
/* 1659 */           createWidgetsFile(widgetsfile, isMgTemplate, pagetype);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1664 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward addWidgets(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1670 */     String pageid = request.getParameter("pageid");
/* 1671 */     if (request.getAttribute("pageid") != null)
/*      */     {
/* 1673 */       pageid = (String)request.getAttribute("pageid");
/*      */     }
/*      */     try {
/* 1676 */       if (request.isUserInRole("DEMO")) {
/* 1677 */         return null;
/*      */       }
/* 1679 */       ManagedApplication mo = new ManagedApplication();
/* 1680 */       String userid = mo.getUserID(request);
/*      */       
/* 1682 */       String widgetlist = request.getParameter("selectedWidgets");
/* 1683 */       if (request.getAttribute("selectedWidgets") != null)
/*      */       {
/* 1685 */         widgetlist = (String)request.getAttribute("selectedWidgets");
/*      */       }
/* 1687 */       DashboardUtil.createandMapWidgets(widgetlist, pageid, userid);
/*      */     } catch (Exception ex) {
/* 1689 */       ex.printStackTrace();
/*      */     }
/* 1691 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getBussinessViews(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1696 */     ResultSet rs = null;
/* 1697 */     MyPageForm fm = (MyPageForm)form;
/* 1698 */     String pageid = request.getParameter("pageid");
/* 1699 */     String widgetid = request.getParameter("widgetid");
/* 1700 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try {
/* 1702 */       request.setAttribute("related_action", "businessviews");
/* 1703 */       ArrayList availMonitors = new ArrayList();
/*      */       
/* 1705 */       String monitorsQuery = "select VIEWID,DISPLAYNAME from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=0 order by UPPER(DISPLAYNAME)";
/* 1706 */       fm.setAvailBusinessViews(this.mo.getRows(monitorsQuery));
/* 1707 */       String selectedViewQuery = "select VIEWID from AM_MYPAGE_WIDGET_FLASHVIEWS where WIDGETID=" + widgetid;
/* 1708 */       rs = AMConnectionPool.executeQueryStmt(selectedViewQuery);
/* 1709 */       if (rs.next()) {
/* 1710 */         fm.setSelectedBusinessView(rs.getString("VIEWID"));
/*      */       }
/* 1712 */       rs.close();
/*      */     } catch (Exception ex) {
/* 1714 */       ex.printStackTrace();
/*      */     }
/* 1716 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward showTopologyMapViewsInEditWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1720 */     ResultSet rs = null;
/* 1721 */     MyPageForm fm = (MyPageForm)form;
/* 1722 */     String pageid = request.getParameter("pageid");
/* 1723 */     String widgetid = request.getParameter("widgetid");
/* 1724 */     AMConnectionPool pool = new AMConnectionPool();
/* 1725 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*      */     try {
/* 1727 */       request.setAttribute("related_action", "businessviews");
/* 1728 */       ArrayList availMonitors = new ArrayList();
/* 1729 */       String listOfMapNames = MapViewUtil.getListOfMapViews(username, request);
/* 1730 */       StringTokenizer st = new StringTokenizer(listOfMapNames, ",");
/* 1731 */       ArrayList tokArray = new ArrayList();
/* 1732 */       while (st.hasMoreTokens()) {
/* 1733 */         tokArray.add(st.nextToken());
/*      */       }
/*      */       
/* 1736 */       Collections.sort(tokArray);
/*      */       
/* 1738 */       fm.setAvailTopologyMapViews(tokArray);
/* 1739 */       rs = AMConnectionPool.executeQueryStmt("select WIDGETURL from AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid);
/* 1740 */       if (rs.next()) {
/* 1741 */         String url = rs.getString("WIDGETURL");
/* 1742 */         if ((url != null) && (!url.equals(""))) {
/* 1743 */           String[] arr = url.split("&");
/* 1744 */           String mapName = arr[1];
/* 1745 */           int equalIndex = mapName.indexOf("=");
/* 1746 */           mapName = mapName.substring(equalIndex + 1);
/* 1747 */           fm.setSelectedTopologyMapView(mapName);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1751 */       ex.printStackTrace();
/*      */     } finally {
/* 1753 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1755 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward getAlertDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1759 */     MyPageForm fm = (MyPageForm)form;
/* 1760 */     String selectiontype = fm.getSelecttionType();
/* 1761 */     String pageid = request.getParameter("pageid");
/* 1762 */     String widgetid = request.getParameter("widgetid");
/*      */     
/*      */ 
/*      */ 
/* 1766 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward editWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1771 */     ResultSet rs = null;
/* 1772 */     ResultSet rs1 = null;
/* 1773 */     AMConnectionPool pool = new AMConnectionPool();
/* 1774 */     String pageid = request.getParameter("pageid");
/* 1775 */     String widgetid = request.getParameter("widgetid");
/* 1776 */     boolean isMgTemplate = false;
/* 1777 */     MyPageForm fm = (MyPageForm)form;
/*      */     try
/*      */     {
/* 1780 */       if ((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) {
/* 1781 */         return null;
/*      */       }
/* 1783 */       if ((pageid != null) && (!pageid.equals("-1"))) {
/*      */         try
/*      */         {
/* 1786 */           rs1 = AMConnectionPool.executeQueryStmt("select PAGENAME,PAGETYPE from AM_MYPAGES where PAGEID=" + pageid);
/* 1787 */           if (rs1.next()) {
/* 1788 */             String pagetype = rs1.getString("PAGETYPE");
/* 1789 */             String pagename = rs1.getString("PAGENAME");
/* 1790 */             request.setAttribute("pagename", pagename);
/* 1791 */             if (pagetype.equals("mgtemplate"))
/*      */             {
/* 1793 */               isMgTemplate = true;
/*      */             }
/* 1795 */             fm.setPageType(pagetype);
/*      */           }
/*      */           
/* 1798 */           rs1.close();
/*      */         } catch (Exception ex) {
/* 1800 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1805 */       String restypefromclient = fm.getSelectedMonitorType();
/* 1806 */       String displaynamefromclient = fm.getDisplayName();
/* 1807 */       int widgetType = -1;
/* 1808 */       String restype = "";
/* 1809 */       String selecttionType = "All";
/* 1810 */       String widgetpropsQuery = "select DISPLAYNAME,DESCRIPTION,RESOURCETYPE,WIDGETTYPE,LIMITTYPE,LIMIT_N,REPORTPERIOD,SELECTMONITORTYPE,WIDGETURL,HEIGHT,UTILCOLUMN1,UTILCOLUMN2,UTILCOLUMN3,MAXSCALE,MINSCALE,SUBGROUP,FILTEROPTION,BUSINESSRULE  from  AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid;
/*      */       
/* 1812 */       String businessperiod = null;
/* 1813 */       rs = AMConnectionPool.executeQueryStmt(widgetpropsQuery);
/* 1814 */       if (rs.next()) {
/* 1815 */         boolean isGraphforAdditionalMetric = false;
/* 1816 */         boolean monitorsStatus = false;
/* 1817 */         restype = rs.getString("RESOURCETYPE");
/* 1818 */         widgetType = rs.getInt("WIDGETTYPE");
/* 1819 */         int limitn = rs.getInt("LIMIT_N");
/* 1820 */         selecttionType = rs.getString("SELECTMONITORTYPE");
/* 1821 */         businessperiod = rs.getString("BUSINESSRULE");
/* 1822 */         fm.setTopnOrder(rs.getString("LIMITTYPE"));
/* 1823 */         fm.setWidgetType(widgetType);
/* 1824 */         fm.setWidgetTypeName(DashboardUtil.getWidgetTypeName(String.valueOf(widgetType)));
/* 1825 */         fm.setLimitn(limitn);
/* 1826 */         fm.setPeriod(rs.getString("REPORTPERIOD"));
/* 1827 */         fm.setSelecttionType(selecttionType);
/* 1828 */         fm.setDescription(rs.getString("DESCRIPTION"));
/* 1829 */         fm.setUrl(rs.getString("WIDGETURL"));
/* 1830 */         String filteroption = rs.getString("FILTEROPTION");
/* 1831 */         fm.setFilterByName(filteroption != null ? filteroption : "");
/* 1832 */         if (widgetType != 201) {
/* 1833 */           fm.setCustomScaleOption(rs.getString("UTILCOLUMN3"));
/* 1834 */           if ("1".equalsIgnoreCase(rs.getString("UTILCOLUMN2")))
/*      */           {
/*      */ 
/* 1837 */             isGraphforAdditionalMetric = true;
/*      */           }
/*      */           
/* 1840 */           fm.setGraphforAdditionalMetric(isGraphforAdditionalMetric);
/*      */         }
/*      */         else {
/* 1843 */           if ((rs.getString("UTILCOLUMN3") != null) && (!rs.getString("UTILCOLUMN3").trim().equalsIgnoreCase("null"))) {
/* 1844 */             fm.setTodaysavailability(Boolean.parseBoolean(rs.getString("UTILCOLUMN3")));
/*      */           } else {
/* 1846 */             fm.setTodaysavailability(false);
/*      */           }
/* 1848 */           if ("1".equalsIgnoreCase(rs.getString("UTILCOLUMN2")))
/*      */           {
/* 1850 */             monitorsStatus = true;
/*      */           }
/*      */         }
/*      */         
/* 1854 */         fm.setMonitorsStatus(monitorsStatus);
/* 1855 */         fm.setMaximumScale(rs.getString("MAXSCALE"));
/* 1856 */         fm.setMinimumScale(rs.getString("MINSCALE"));
/* 1857 */         if (rs.getString("HEIGHT") != null)
/*      */         {
/* 1859 */           fm.setWidgetHeight(rs.getInt("HEIGHT"));
/*      */         }
/* 1861 */         boolean isAlertStatusForMetrics = false;
/* 1862 */         if (rs.getString("UTILCOLUMN1") != null)
/*      */         {
/* 1864 */           if (rs.getString("UTILCOLUMN1").equals("1")) {
/* 1865 */             isAlertStatusForMetrics = true;
/*      */           }
/*      */         }
/* 1868 */         fm.setAlertStatusForMetrics(isAlertStatusForMetrics);
/*      */         
/* 1870 */         boolean isSubGroupOption = false;
/* 1871 */         if ((rs.getString("SUBGROUP") != null) && (rs.getString("SUBGROUP").equals("1")))
/*      */         {
/*      */ 
/* 1874 */           isSubGroupOption = true;
/*      */         }
/*      */         
/* 1877 */         fm.setDisplaySubGroups(isSubGroupOption);
/*      */         
/*      */ 
/* 1880 */         if (restypefromclient.equals(""))
/*      */         {
/* 1882 */           displaynamefromclient = rs.getString("DISPLAYNAME");
/* 1883 */           fm.setSelectedMonitorType(rs.getString("RESOURCETYPE"));
/* 1884 */           fm.setDisplayName(displaynamefromclient);
/*      */         }
/*      */       }
/*      */       
/* 1888 */       rs.close();
/*      */       
/* 1890 */       int PERFORMANCE_WIDGET = 1001;
/*      */       
/* 1892 */       String brQuery = "select ID,NAME from AM_BUSINESSHOURSDETAILS where STATUS='enable'";
/* 1893 */       ResultSet rs_br = null;
/* 1894 */       ArrayList<Properties> brules = new ArrayList();
/* 1895 */       ResultSet widgetCatSet = null;
/*      */       try {
/* 1897 */         int widgetCategoryI = -1;
/* 1898 */         String widgetCatQry = "select * from AM_MYPAGE_WIDGET_TYPES where TYPEID=" + widgetType;
/*      */         try {
/* 1900 */           widgetCatSet = AMConnectionPool.executeQueryStmt(widgetCatQry);
/*      */           
/* 1902 */           if (widgetCatSet.next()) {
/* 1903 */             widgetCategoryI = widgetCatSet.getInt("CATEGORYID");
/*      */           }
/*      */         } catch (Exception exc) {
/* 1906 */           exc.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */         
/*      */ 
/* 1911 */         if (widgetCategoryI == 1001)
/*      */         {
/* 1913 */           rs_br = AMConnectionPool.executeQueryStmt(brQuery);
/* 1914 */           while (rs_br.next()) {
/* 1915 */             Properties dataProps = new Properties();
/* 1916 */             dataProps.setProperty("label", rs_br.getString("NAME"));
/* 1917 */             dataProps.setProperty("value", rs_br.getString("ID"));
/* 1918 */             brules.add(dataProps);
/*      */           }
/* 1920 */           if (businessperiod != null) {
/* 1921 */             fm.setBusinessPeriod(businessperiod);
/*      */           }
/* 1923 */           fm.setBusinessrules(brules);
/*      */         }
/*      */       } catch (Exception ee) {
/* 1926 */         ee.printStackTrace();
/*      */       } finally {
/* 1928 */         AMConnectionPool.closeStatement(rs_br);
/*      */       }
/* 1930 */       AMLog.info("HistoryDataAction : getData : setBusinessrules ======>" + brules);
/*      */       
/* 1932 */       if (widgetType == 51) {
/* 1933 */         com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.put("Trap", "Trap");
/* 1934 */         com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.put("SAN", FormatUtil.getString("Storage Devices"));
/* 1935 */         if ((com.adventnet.appmanager.util.Constants.resourceGroupsandDesc != null) && (com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.get("NET") != null)) {
/* 1936 */           com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.remove("NET");
/*      */         }
/* 1938 */         com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.put("NWD", FormatUtil.getString("Network Devices"));
/*      */ 
/*      */       }
/* 1941 */       else if (com.adventnet.appmanager.util.Constants.resourceGroupsandDesc != null) {
/* 1942 */         com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.remove("Trap");
/*      */       } else {
/* 1944 */         com.adventnet.appmanager.util.Constants.checkAndUpdateResourceGroup();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1949 */       Collection resourceGroupsandDescLabelValueBeanCollection = new ArrayList();
/* 1950 */       String widgType = Integer.toString(widgetType);
/*      */       
/* 1952 */       if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (DashboardUtil.externalProdWidgets.contains(widgType)))
/*      */       {
/* 1954 */         request.setAttribute("isExtProd", "true");
/* 1955 */         request.setAttribute("showSelectCategories", "true");
/* 1956 */         ArrayList<String> associatedTypes = new ArrayList();
/* 1957 */         String[] typeVals = new String[0];
/* 1958 */         String query = "select ASSOCIATEDTYPES from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/* 1959 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1960 */         while (rs.next()) {
/* 1961 */           associatedTypes.add(rs.getString(1));
/*      */         }
/* 1963 */         rs.close();
/*      */         
/* 1965 */         Hashtable<String, String> categDescHash = new Hashtable();
/* 1966 */         if (DashboardUtil.opmanWidgets.contains(widgType))
/*      */         {
/* 1968 */           request.setAttribute("extProdName", "OPMANAGER");
/* 1969 */           categDescHash = getTypeDescrForExternalProduct("OPMANAGER");
/*      */         }
/*      */         
/* 1972 */         if (widgType.equals("503"))
/*      */         {
/* 1974 */           request.setAttribute("showLimit", "true");
/*      */         }
/*      */         
/* 1977 */         if ((associatedTypes.size() == 0) || (associatedTypes.size() == categDescHash.size())) {
/* 1978 */           typeVals = (String[])categDescHash.keySet().toArray(typeVals);
/* 1979 */           fm.setSelectAllresGrp("on");
/*      */         } else {
/* 1981 */           typeVals = (String[])associatedTypes.toArray(typeVals);
/*      */         }
/* 1983 */         fm.setSelectedItems(typeVals);
/* 1984 */         resourceGroupsandDescLabelValueBeanCollection = getResourceGroupsandDescLabelValueBeanArray(categDescHash);
/*      */       }
/*      */       else {
/* 1987 */         resourceGroupsandDescLabelValueBeanCollection = getResourceGroupsandDescLabelValueBeanArray(com.adventnet.appmanager.util.Constants.resourceGroupsandDesc);
/*      */       }
/* 1989 */       request.setAttribute("resourceGroupsandDesc", resourceGroupsandDescLabelValueBeanCollection);
/*      */       
/* 1991 */       if ((widgetType == 51) || (widgetType == 104)) {
/* 1992 */         ArrayList<String> associatedTypes = new ArrayList();
/* 1993 */         String[] typeVals = new String[0];
/* 1994 */         String query = "select ASSOCIATEDTYPES from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/* 1995 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1996 */         while (rs.next()) {
/* 1997 */           associatedTypes.add(rs.getString(1));
/*      */         }
/* 1999 */         rs.close();
/* 2000 */         if ((associatedTypes.size() == 0) || (associatedTypes.size() == com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.size())) {
/* 2001 */           typeVals = (String[])com.adventnet.appmanager.util.Constants.resourceGroupsandDesc.keySet().toArray(typeVals);
/* 2002 */           fm.setSelectAllresGrp("on");
/*      */         } else {
/* 2004 */           typeVals = (String[])associatedTypes.toArray(typeVals);
/*      */         }
/* 2006 */         fm.setSelectedItems(typeVals);
/*      */       }
/* 2008 */       if (widgetType == 21)
/* 2009 */         return getBussinessViews(mapping, form, request, response);
/* 2010 */       if (widgetType == 204)
/* 2011 */         return showTopologyMapViewsInEditWidget(mapping, form, request, response);
/* 2012 */       if ((widgetType == 51) || (Integer.toString(widgetType).equals("503"))) {
/* 2013 */         return getAlertDetails(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/* 2017 */       String defaultPrimaryAttribute = "";
/* 2018 */       ArrayList availAttributes = new ArrayList();
/* 2019 */       List attList = null;
/* 2020 */       String graphtype = "";
/* 2021 */       String primaryAttribute = null;
/*      */       
/* 2023 */       setUserConfiguredMonitorTypes(fm, request);
/* 2024 */       restype = fm.getSelectedMonitorType();
/* 2025 */       int monitorSelectionType = fm.getMonitorSelectionType();
/*      */       
/* 2027 */       if ((widgetType == 2) && (monitorSelectionType == 2)) {
/* 2028 */         String selecteddMon = request.getParameter("selectedMonitors");
/* 2029 */         String[] selectedmoitorsForAllResType = new String[0];
/* 2030 */         getResourceForSelectionType(mapping, form, request, response);
/* 2031 */         if (selecteddMon == null) {
/* 2032 */           selectedmoitorsForAllResType = getPreexistingMultiMonitor(widgetid);
/* 2033 */           fm.setMultiMonitor(selectedmoitorsForAllResType);
/*      */         }
/* 2035 */         else if (!selecteddMon.equals("")) {
/* 2036 */           selectedmoitorsForAllResType = selecteddMon.split(",");
/* 2037 */           fm.setMultiMonitor(selectedmoitorsForAllResType);
/*      */         }
/*      */         
/* 2040 */         fm.setMonitorSelectionType(monitorSelectionType);
/* 2041 */         String[] attribs = getPreexistingMultiAttributes(widgetid);
/* 2042 */         fm.setMultiAttributes(attribs);
/* 2043 */         String resid = null;
/* 2044 */         String resType = null;
/* 2045 */         String resourceName = null;
/* 2046 */         Map<String, Object> resAttrMap = new TreeMap();
/* 2047 */         String period = fm.getPeriod();
/* 2048 */         if (request.getParameter("period") != null)
/*      */         {
/* 2050 */           period = request.getParameter("period");
/*      */         }
/* 2052 */         for (int i = 0; i < selectedmoitorsForAllResType.length; i++) {
/* 2053 */           Map<String, String> attrMap = null;
/* 2054 */           resid = selectedmoitorsForAllResType[i].trim();
/* 2055 */           String resGroup = "";
/* 2056 */           HashMap<String, Object> resDetailsMap = ReportUtil.getResourceDetails(resid);
/* 2057 */           if (!resDetailsMap.isEmpty()) {
/* 2058 */             resType = (String)resDetailsMap.get("TYPE");
/* 2059 */             resGroup = (String)resDetailsMap.get("SUBGROUP");
/*      */           }
/* 2061 */           if ((resGroup != null) && (!resAttrMap.containsKey(resGroup))) {
/* 2062 */             if (period.equals("20")) {
/* 2063 */               attrMap = getAttributesForResTypeForPolledData(resType);
/*      */             } else {
/* 2065 */               attrMap = getAttributesForResTypeWithReportsEnabled(resType);
/*      */             }
/* 2067 */             resAttrMap.put(resGroup, attrMap);
/*      */           }
/*      */         }
/* 2070 */         request.setAttribute("resAttrMap", resAttrMap);
/* 2071 */         fm.setPeriod(period);
/* 2072 */         fm.setSelectedGraphType("line");
/*      */         
/* 2074 */         return new ActionForward("/jsp/MyPage_EditWidget.jsp?pageid=" + pageid + "&widgetid=" + widgetid + "&period=" + period);
/*      */       }
/*      */       
/*      */ 
/* 2078 */       if (restypefromclient.equals(""))
/*      */       {
/* 2080 */         ArrayList selectedlist = new ArrayList();
/*      */         
/* 2082 */         String attribQuery = "select ATTRIBUTEID,ISPRIMARYATTRIBUTE,GRAPHTYPE  from  AM_MYPAGE_WIDGET_ATTRIBUTES where WIDGETID=" + widgetid;
/* 2083 */         rs = AMConnectionPool.executeQueryStmt(attribQuery);
/* 2084 */         while (rs.next()) {
/* 2085 */           int isprimary = rs.getInt("ISPRIMARYATTRIBUTE");
/* 2086 */           if (isprimary == 0) {
/* 2087 */             graphtype = rs.getString("GRAPHTYPE");
/* 2088 */             primaryAttribute = rs.getString("ATTRIBUTEID");
/* 2089 */             fm.setSelectedAttribute(primaryAttribute);
/* 2090 */             fm.setSelectedGraphType(graphtype);
/*      */           } else {
/* 2092 */             selectedlist.add(rs.getString("ATTRIBUTEID"));
/*      */           }
/* 2094 */           request.setAttribute("selectedlist", selectedlist);
/*      */         }
/*      */       }
/*      */       
/* 2098 */       String selectedResourceid = null;
/*      */       
/*      */ 
/* 2101 */       if (widgetType == 2) {
/* 2102 */         getResourceForSelectionType(mapping, form, request, response);
/*      */         try {
/* 2104 */           String selectedMonitorsQuery = "select RESOURCEID from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid;
/* 2105 */           rs = AMConnectionPool.executeQueryStmt(selectedMonitorsQuery);
/* 2106 */           if (rs.next()) {
/* 2107 */             fm.setSelectedMonitor(rs.getString("RESOURCEID"));
/*      */           }
/* 2109 */           rs.close();
/*      */           
/* 2111 */           if ("true".equalsIgnoreCase(request.getParameter("clearScriptMonitor"))) {
/* 2112 */             fm.setSelectedMonitor(null);
/* 2113 */             fm.setSelectedAttribute(null);
/*      */           }
/* 2115 */           if ("true".equalsIgnoreCase(request.getParameter("editscriptwidget"))) {
/* 2116 */             fm.setSelectedMonitor(request.getParameter("selectedMonitor"));
/*      */           }
/* 2118 */           selectedResourceid = fm.getSelectedMonitor();
/* 2119 */           if (selectedResourceid == null) {
/* 2120 */             selectedResourceid = (String)((ArrayList)fm.getAvailMonitors().get(0)).get(0);
/*      */           }
/* 2122 */           request.setAttribute("selectedResourceid", selectedResourceid);
/* 2123 */           if (request.getAttribute("secondlevelattribute") != null) {
/* 2124 */             boolean ischildResourceid = ((Boolean)request.getAttribute("secondlevelattribute")).booleanValue();
/* 2125 */             if (ischildResourceid) {
/* 2126 */               String childID = fm.getSelectedMonitor();
/* 2127 */               HashMap<String, String> details = getParentID(childID);
/* 2128 */               request.setAttribute("childParentid", details.get("parentid"));
/* 2129 */               request.setAttribute("childParentName", details.get("parentname"));
/* 2130 */               selectedResourceid = selectedResourceid + "#" + (String)details.get("parentid");
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 2135 */           ex.printStackTrace();
/*      */         }
/* 2137 */       } else if ((widgetType == 1) || (widgetType == 3) || (widgetType == 5) || (widgetType == 4) || (widgetType == 102) || (widgetType == 103) || (widgetType == 107))
/*      */       {
/* 2139 */         if (isMgTemplate) {
/* 2140 */           fm.setSelecttionType("GROUP");
/*      */         }
/* 2142 */         setAvailableMonitors(fm, restype, widgetid, selecttionType, mapping, request, response);
/* 2143 */       } else if (((widgetType == 201) || (widgetType == 202) || (widgetType == 203)) && (!selecttionType.equals("ALL")) && (!selecttionType.equals("-1")))
/*      */       {
/* 2145 */         HashMap customField = getCustomValues(widgetid);
/* 2146 */         String aliasname = (String)customField.get("aliasname");
/* 2147 */         String fieldid = (String)customField.get("fieldid");
/* 2148 */         String value = (String)customField.get("value");
/* 2149 */         fm.setSelecttionType(aliasname + "$" + fieldid);
/* 2150 */         request.setAttribute("fromedit", "true");
/* 2151 */         request.setAttribute("widgetid", widgetid);
/* 2152 */         request.setAttribute("aliasname", aliasname);
/* 2153 */         request.setAttribute("value", value);
/* 2154 */         request.setAttribute("subgroup", String.valueOf(fm.getDisplaySubGroups()));
/*      */         
/* 2156 */         listMonitorGroups(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/* 2160 */       if (widgetType != 2) {
/* 2161 */         attList = checkAndGetAttribsForComplexType(restype);
/*      */       } else {
/* 2163 */         attList = checkAndGetAttribsForComplexType(restype, selectedResourceid);
/*      */       }
/*      */       
/* 2166 */       LinkedHashMap<String, String> attrNameVsId = new LinkedHashMap();
/* 2167 */       for (int i = 0; i < attList.size(); i++) {
/* 2168 */         String attribid_name = (String)attList.get(i);
/* 2169 */         String[] temp = attribid_name.split("#");
/* 2170 */         String attributeid = temp[0];
/* 2171 */         String displayname = temp[1];
/*      */         
/* 2173 */         if (!attrNameVsId.containsKey(displayname)) {
/* 2174 */           attrNameVsId.put(displayname, attributeid);
/*      */         }
/*      */       }
/*      */       
/* 2178 */       Iterator<String> grpIdItr = attrNameVsId.keySet().iterator();
/* 2179 */       while (grpIdItr.hasNext()) {
/* 2180 */         String displayname = (String)grpIdItr.next();
/* 2181 */         String attributeid = (String)attrNameVsId.get(displayname);
/* 2182 */         Properties dataProps = new Properties();
/* 2183 */         dataProps.setProperty("label", FormatUtil.getString(displayname));
/* 2184 */         dataProps.setProperty("value", attributeid);
/* 2185 */         availAttributes.add(dataProps);
/* 2186 */         if (defaultPrimaryAttribute.equals(""))
/*      */         {
/* 2188 */           defaultPrimaryAttribute = attributeid;
/*      */         }
/*      */       }
/*      */       
/* 2192 */       fm.setAvailAttributes(availAttributes);
/*      */       
/*      */ 
/*      */ 
/* 2196 */       if (primaryAttribute == null) {
/* 2197 */         primaryAttribute = defaultPrimaryAttribute;
/* 2198 */         fm.setSelectedAttribute(primaryAttribute);
/*      */       }
/* 2200 */       getRelatedAttributes(mapping, form, request, response);
/*      */       
/*      */ 
/* 2203 */       String typedisplayname = DashboardUtil.getWidgetTypeName(String.valueOf(widgetType));
/* 2204 */       if (displaynamefromclient.equals(typedisplayname)) {
/* 2205 */         request.setAttribute("defaultnameforType", typedisplayname);
/*      */       }
/*      */     } catch (Exception ex) {
/* 2208 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2211 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   private HashMap<String, String> getParentID(String childID) {
/* 2215 */     ResultSet parentset = null;
/* 2216 */     HashMap<String, String> parentdetails = new HashMap();
/*      */     try {
/* 2218 */       parentset = AMConnectionPool.executeQueryStmt("select RESOURCEID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID and AM_PARENTCHILDMAPPER.CHILDID=" + childID);
/* 2219 */       if (parentset.next()) {
/* 2220 */         parentdetails.put("parentid", parentset.getString("RESOURCEID"));
/* 2221 */         parentdetails.put("parentname", EnterpriseUtil.decodeString(parentset.getString("DISPLAYNAME")));
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2225 */       ex.printStackTrace();
/*      */     } finally {
/* 2227 */       if (parentset != null) {
/* 2228 */         AMConnectionPool.closeStatement(parentset);
/*      */       }
/*      */     }
/* 2231 */     return parentdetails;
/*      */   }
/*      */   
/*      */   public ActionForward getSelectedCustomField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2235 */     String widgetid = request.getParameter("widgetid");
/* 2236 */     HashMap customField = getCustomValues(widgetid);
/* 2237 */     String aliasname = (String)customField.get("aliasname");
/* 2238 */     String optionSel = (String)customField.get("value");
/*      */     
/* 2240 */     return new ActionForward("/myFields.do?method=getFieldValues&aliasName=" + aliasname + "&forBulkAssign=false&optionSel=" + optionSel, false);
/*      */   }
/*      */   
/*      */   private HashMap getCustomValues(String widgetid)
/*      */   {
/* 2245 */     customVal = new HashMap();
/* 2246 */     ResultSet res = null;
/*      */     try
/*      */     {
/* 2249 */       res = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_WIDGET_CUSTOMFIELD.VALUE,AM_MYFIELDS_METADATA.FIELDTYPE,AM_MYFIELDS_METADATA.ALIASNAME, AM_MYFIELDS_METADATA.FIELDID from AM_MYPAGE_WIDGETS, AM_MYPAGE_WIDGET_CUSTOMFIELD, AM_MYFIELDS_METADATA where AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_CUSTOMFIELD.WIDGETID and AM_MYPAGE_WIDGET_CUSTOMFIELD.FIELDID=AM_MYFIELDS_METADATA.FIELDID and AM_MYPAGE_WIDGET_CUSTOMFIELD.WIDGETID=" + widgetid);
/* 2250 */       if (res.next()) {
/* 2251 */         customVal.put("aliasname", res.getString("ALIASNAME"));
/* 2252 */         customVal.put("value", res.getString("VALUE"));
/* 2253 */         customVal.put("fieldid", res.getString("FIELDID"));
/* 2254 */         customVal.put("fieldtype", res.getString("FIELDTYPE"));
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
/* 2268 */       return customVal;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2258 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 2261 */         if (res != null) {
/* 2262 */           AMConnectionPool.closeStatement(res);
/*      */         }
/*      */       } catch (Exception ex) {
/* 2265 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward listMonitorGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2272 */     MyPageForm fm = (MyPageForm)form;
/* 2273 */     ArrayList availMonitors = new ArrayList();
/* 2274 */     String customColumn = "";
/* 2275 */     String customValue = "";
/* 2276 */     String subgroups = "";
/* 2277 */     String includeSubGroups = " and AM_HOLISTICAPPLICATION.TYPE=0";
/*      */     
/* 2279 */     if ((request.getAttribute("fromedit") != null) && (request.getAttribute("fromedit").equals("true")))
/*      */     {
/* 2281 */       customColumn = (String)request.getAttribute("aliasname");
/* 2282 */       customValue = (String)request.getAttribute("value");
/* 2283 */       subgroups = (String)request.getAttribute("subgroup");
/*      */     } else {
/* 2285 */       customColumn = request.getParameter("customfield");
/* 2286 */       customValue = request.getParameter("searchtext");
/* 2287 */       subgroups = request.getParameter("subgroup");
/*      */     }
/*      */     
/* 2290 */     HashMap custom = MyFields.customCondition(customColumn, customValue, null, false);
/* 2291 */     String datatable = (String)custom.get("groupTable");
/* 2292 */     String cond = (String)custom.get("groupQuery");
/* 2293 */     String query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from " + datatable + " , AM_ManagedObject, AM_HOLISTICAPPLICATION where AM_ManagedObject.TYPE='HAI' and AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID " + includeSubGroups + "  " + cond + "";
/* 2294 */     if ("true".equals(subgroups)) {
/* 2295 */       includeSubGroups = "";
/* 2296 */       query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from " + datatable + " , AM_ManagedObject, AM_HOLISTICAPPLICATION where AM_ManagedObject.TYPE='HAI' and AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID " + includeSubGroups + "  " + cond + "";
/* 2297 */       Vector<String> getsubgroups = MyFields.listMgSubGroups(query, datatable, "RESOURCEID", cond);
/* 2298 */       query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", getsubgroups);
/*      */     }
/* 2300 */     availMonitors = this.mo.getRows(query);
/* 2301 */     fm.setAvailMultiMonitors(availMonitors);
/* 2302 */     request.setAttribute("related_action", "availMonitorGroupforCustomFields");
/* 2303 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward getCustomfieldMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2308 */     MyPageForm fm = (MyPageForm)form;
/* 2309 */     String selectiontype = fm.getSelecttionType();
/* 2310 */     String monitortypes = fm.getSelectedMonitorType();
/* 2311 */     String widgetid = "";
/* 2312 */     int widgettype = -1;
/*      */     
/* 2314 */     String monitortype = "";
/*      */     
/* 2316 */     String customField = "";
/* 2317 */     String searchText = "";
/* 2318 */     String selectedattributeid = "";
/* 2319 */     String fieldtype = "";
/*      */     
/* 2321 */     ArrayList availMonitors = new ArrayList();
/*      */     
/* 2323 */     if ((request.getAttribute("fromedit") != null) && (request.getAttribute("fromedit").equals("true"))) {
/* 2324 */       selectedattributeid = fm.getSelectedAttribute();
/* 2325 */       selectiontype = fm.getSelecttionType();
/* 2326 */       monitortype = fm.getSelectedMonitorType();
/* 2327 */       widgettype = fm.getWidgetType();
/* 2328 */       widgetid = (String)request.getAttribute("widgetid");
/* 2329 */       HashMap custom = getCustomValues(widgetid);
/* 2330 */       fieldtype = (String)custom.get("fieldtype");
/* 2331 */       customField = (String)custom.get("aliasname");
/* 2332 */       searchText = (String)custom.get("value");
/*      */     } else {
/* 2334 */       selectedattributeid = request.getParameter("attributeid");
/* 2335 */       monitortype = request.getParameter("monitorType");
/* 2336 */       widgettype = Integer.parseInt(request.getParameter("widgetType"));
/* 2337 */       customField = request.getParameter("customfield");
/* 2338 */       searchText = request.getParameter("searchtext");
/*      */     }
/*      */     
/* 2341 */     String resourcetypecondition = getResourceTypesForComplexTypeCondition(monitortype, "TYPE");
/* 2342 */     ResultSet rs = null;
/*      */     try {
/* 2344 */       HashMap<String, String> queryCon = MyFields.customCondition(customField, searchText, null, false);
/*      */       
/* 2346 */       String dataTable = (String)queryCon.get("groupTable");
/* 2347 */       String qryCon = (String)queryCon.get("groupQuery");
/* 2348 */       String groupmonitors = (String)queryCon.get("groupmonitors");
/* 2349 */       fieldtype = (String)queryCon.get("fieldtype");
/*      */       
/* 2351 */       request.setAttribute("related_action", "availCustomFieldMonitors");
/* 2352 */       request.setAttribute("fieldtype", fieldtype);
/* 2353 */       String attribute_level = "1";
/* 2354 */       String secondleveltype = monitortype;
/*      */       
/* 2356 */       String monitorsQuery = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.Type, AM_ManagedObject.RESOURCENAME from AM_ManagedObject," + dataTable + " where " + resourcetypecondition + " " + qryCon + " order by DISPLAYNAME";
/*      */       
/* 2358 */       if (ClientDBUtil.isPrivilegedUser(request))
/*      */       {
/* 2360 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2361 */           String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 2362 */           monitorsQuery = "select AM_ManagedObject.RESOURCEID, DISPLAYNAME from AM_ManagedObject," + dataTable + ",AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and " + resourcetypecondition + " " + qryCon + " order by DISPLAYNAME";
/*      */         } else {
/* 2364 */           Vector resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 2365 */           monitorsQuery = "select AM_ManagedObject.RESOURCEID, DISPLAYNAME from AM_ManagedObject," + dataTable + " where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " and " + resourcetypecondition + " " + qryCon + " order by DISPLAYNAME";
/*      */         }
/*      */       }
/*      */       
/* 2369 */       if ((widgettype == 1) || (widgettype == 4) || (widgettype == 3))
/*      */       {
/* 2371 */         if ((selectedattributeid != null) && (!selectedattributeid.equals("")))
/*      */         {
/* 2373 */           ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(selectedattributeid);
/*      */           
/* 2375 */           attribute_level = (String)attrExtTableDetails.get(10);
/* 2376 */           secondleveltype = (String)attrExtTableDetails.get(6);
/*      */         }
/* 2378 */         if (!attribute_level.equals("2"))
/*      */         {
/*      */ 
/* 2381 */           availMonitors = this.mo.getRows(monitorsQuery);
/*      */         }
/*      */         else {
/* 2384 */           ArrayList firstlevelresids = new ArrayList();
/* 2385 */           rs = AMConnectionPool.executeQueryStmt(monitorsQuery);
/* 2386 */           while (rs.next()) {
/* 2387 */             firstlevelresids.add(rs.getString("RESOURCEID"));
/*      */           }
/* 2389 */           AMConnectionPool.closeStatement(rs);
/* 2390 */           String secondlevelquery = "select RESOURCEID,DISPLAYNAME from AM_PARENTCHILDMAPPER inner join   AM_ManagedObject  on RESOURCEID=CHILDID and TYPE='" + secondleveltype + "' where " + ManagedApplication.getCondition("PARENTID", firstlevelresids);
/*      */           
/* 2392 */           availMonitors = this.mo.getRows(secondlevelquery);
/*      */         }
/*      */       }
/*      */       else {
/* 2396 */         availMonitors = this.mo.getRows(monitorsQuery);
/*      */       }
/*      */       
/*      */ 
/* 2400 */       monitorsQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_ManagedResourceType, " + dataTable + " where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE='HAI' " + qryCon + "";
/*      */       
/* 2402 */       rs = AMConnectionPool.executeQueryStmt(monitorsQuery);
/* 2403 */       Vector<String> resids = new Vector();
/* 2404 */       ManagedApplication mo = new ManagedApplication();
/* 2405 */       while (rs.next())
/*      */       {
/* 2407 */         mo.getAllChildsinTree(resids, rs.getString("RESOURCEID"));
/*      */       }
/*      */       
/* 2410 */       monitorsQuery = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.Type, AM_ManagedObject.RESOURCENAME from AM_ManagedResourceType,AM_ManagedObject left join " + dataTable + " on AM_ManagedObject.RESOURCEID=" + dataTable + ".RESOURCEID where " + resourcetypecondition + " and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE not in ('Node','Network','HAI')  and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resids) + "  " + groupmonitors + " order by DISPLAYNAME";
/* 2411 */       ArrayList monitorsFromMg = mo.getRows(monitorsQuery);
/* 2412 */       for (int i = 0; i < monitorsFromMg.size(); i++) {
/* 2413 */         availMonitors.add(monitorsFromMg.get(i));
/*      */       }
/* 2415 */       fm.setAvailMultiMonitors(availMonitors);
/*      */     } catch (Exception ex) {
/* 2417 */       ex.printStackTrace();
/*      */     } finally {
/* 2419 */       if (rs != null) {
/* 2420 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 2424 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   private List checkAndGetAttribsForComplexType(String resourcetype, String selectedresourceid)
/*      */   {
/* 2429 */     AMConnectionPool con = new AMConnectionPool();
/* 2430 */     ResultSet rs = null;
/*      */     
/*      */ 
/* 2433 */     List toreturn = new ArrayList();
/*      */     try {
/* 2435 */       if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2436 */         toreturn = ReportUtil.getAttributesForResourcetype("MSSQL-DB-server','Windows 2003','QueryMonitor");
/* 2437 */       } else if (resourcetype.equals("$ComplexType_Servers"))
/*      */       {
/* 2439 */         String query = "select max(ATTRIBUTEID) as ATTRIBID,DISPLAYNAME from AM_ATTRIBUTES where RESOURCETYPE in" + com.adventnet.appmanager.util.Constants.serverTypes + "  and ATTRIBUTE in('CPU Utilization','Response Time','Physical Memory Utilization','Jobs in Minute','Jobs in 5 Minutes','Jobs in 15 Minutes','Swap Memory Utilization') group by DISPLAYNAME order by  DISPLAYNAME ";
/* 2440 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2441 */         while (rs.next()) {
/* 2442 */           String attribid = rs.getString("ATTRIBID");
/* 2443 */           String attribname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2444 */           toreturn.add(attribid + "#" + attribname);
/*      */         }
/* 2446 */         rs.close();
/* 2447 */         toreturn.add("711#" + FormatUtil.getString("Disk Utilization") + "(%)(" + FormatUtil.getString("Disk") + ")");
/* 2448 */         toreturn.add("712#" + FormatUtil.getString("Disk Utilization") + "(" + FormatUtil.getString("MB") + ")(" + FormatUtil.getString("Disk") + ")");
/* 2449 */       } else if ("$ComplexType_All".equalsIgnoreCase(resourcetype))
/*      */       {
/* 2451 */         toreturn.add("1208#" + FormatUtil.getString("Response Time") + "(" + FormatUtil.getString("ms") + ")");
/*      */       }
/* 2453 */       else if ("$ComplexType_Windows".equalsIgnoreCase(resourcetype))
/*      */       {
/*      */ 
/* 2456 */         toreturn = ReportUtil.getAttributesForResourcetype("Windows XP");
/* 2457 */       } else if (("JMX1.2-MX4J-RMI".equalsIgnoreCase(resourcetype)) || ("SNMP".equalsIgnoreCase(resourcetype)))
/*      */       {
/* 2459 */         String jmxquery = "select max(AM_ATTRIBUTES.ATTRIBUTEID ) as ATTRIBID,max(AM_CAM_DC_GROUPS.GROUPID),AM_ATTRIBUTES.DISPLAYNAME,GROUPNAME   from AM_ATTRIBUTES  inner join  AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID =AM_ATTRIBUTES.ATTRIBUTEID  inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID where AM_ATTRIBUTES.RESOURCETYPE='" + resourcetype + "' group by AM_ATTRIBUTES.DISPLAYNAME, GROUPNAME  order by GROUPNAME,DISPLAYNAME ";
/* 2460 */         rs = AMConnectionPool.executeQueryStmt(jmxquery);
/* 2461 */         while (rs.next()) {
/* 2462 */           String attribid = rs.getString("ATTRIBID");
/* 2463 */           String attribname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2464 */           String groupname = rs.getString("GROUPNAME");
/* 2465 */           if ((groupname == null) || (isNumeric(groupname)) || (groupname.equals(""))) {
/* 2466 */             groupname = "";
/*      */           } else {
/* 2468 */             groupname = " (" + groupname + ")";
/*      */           }
/* 2470 */           toreturn.add(attribid + "#" + attribname + groupname);
/*      */         }
/* 2472 */         rs.close();
/*      */       }
/* 2474 */       else if ("$ComplexType_VC_HAI".equalsIgnoreCase(resourcetype)) {
/* 2475 */         String query = "select ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES where (ATTRIBUTEID > 7910 and ATTRIBUTEID < 7920) or (ATTRIBUTEID > 7929 and ATTRIBUTEID < 7950)";
/* 2476 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2477 */         while (rs.next()) {
/* 2478 */           String attribid = rs.getString("ATTRIBUTEID");
/* 2479 */           String attribname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2480 */           toreturn.add(attribid + "#" + attribname);
/*      */         }
/* 2482 */         rs.close();
/* 2483 */       } else if ("RBM".equalsIgnoreCase(resourcetype)) {
/* 2484 */         String query = "select ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES where RESOURCETYPE='RBM' and type=0";
/* 2485 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2486 */         while (rs.next()) {
/* 2487 */           String attribid = rs.getString("ATTRIBUTEID");
/* 2488 */           String attribname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2489 */           toreturn.add(attribid + "#" + attribname);
/*      */         }
/* 2491 */         AMConnectionPool.closeStatement(rs);
/* 2492 */       } else if (selectedresourceid != null)
/*      */       {
/* 2494 */         if (("QueryMonitor".equalsIgnoreCase(resourcetype)) || ("Script Monitor".equalsIgnoreCase(resourcetype))) {
/* 2495 */           toreturn = getAttribsforScriptType(resourcetype, selectedresourceid);
/* 2496 */         } else if ("VirtualMachine".equals(resourcetype)) {
/* 2497 */           resourcetype = resourcetype + "', 'XenServerVM";
/* 2498 */           toreturn = ReportUtil.getAttributesForResourcetype(resourcetype);
/*      */         } else {
/* 2500 */           toreturn = ReportUtil.getAttributesForResourcetype(resourcetype);
/*      */         }
/* 2502 */       } else if ("VirtualMachine".equals(resourcetype)) {
/* 2503 */         resourcetype = resourcetype + "', 'XenServerVM";
/* 2504 */         toreturn = ReportUtil.getAttributesForResourcetype(resourcetype);
/*      */       } else {
/* 2506 */         toreturn = ReportUtil.getAttributesForResourcetype(resourcetype);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2510 */       ex.printStackTrace();
/*      */     }
/* 2512 */     return toreturn;
/*      */   }
/*      */   
/*      */   private List checkAndGetAttribsForComplexType(String resourcetype)
/*      */   {
/* 2517 */     return checkAndGetAttribsForComplexType(resourcetype, null);
/*      */   }
/*      */   
/*      */   private List getAttribsforScriptType(String resourcetype, String resourceid)
/*      */   {
/* 2522 */     List toreturn = new ArrayList();
/*      */     try
/*      */     {
/* 2525 */       if (resourceid.indexOf("#") != -1) {
/* 2526 */         String[] temp = resourceid.split("#");
/* 2527 */         String attributeid = temp[0];
/* 2528 */         resourceid = temp[1];
/*      */       }
/* 2530 */       String healthid = AMAttributesCache.getHealthId(resourcetype);
/* 2531 */       StringBuilder childhealthids = new StringBuilder();
/* 2532 */       ResultSet restimeset = null;
/*      */       try
/*      */       {
/* 2535 */         String responseQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES_EXT.REPORTS_ENABLED,AM_ATTRIBUTES_EXT.ISARCHIVEING from AM_ATTRIBUTESDEPENDENCY,AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where PARENTID=" + healthid + " and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTESDEPENDENCY.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_ATTRIBUTES.TYPE < 3 AND AM_ATTRIBUTES_EXT.REPORTS_ENABLED < 2 and ATTRIBUTE in ('ResponseTime','Health') and AM_ATTRIBUTES.ATTRIBUTEID NOT IN (select ATTRIBUTEID from AM_CAM_DC_ATTRIBUTES,AM_SCRIPT_TABLES,AM_ManagedObject where AM_SCRIPT_TABLES.TABLEID=AM_CAM_DC_ATTRIBUTES.GROUPID and AM_ManagedObject.RESOURCEID=AM_SCRIPT_TABLES.SCRIPTID and AM_ManagedObject.TYPE='" + resourcetype + "')";
/* 2536 */         restimeset = AMConnectionPool.executeQueryStmt(responseQuery);
/* 2537 */         while (restimeset.next()) {
/* 2538 */           if ("Health".equals(restimeset.getString("DISPLAYNAME"))) {
/* 2539 */             childhealthids.append(restimeset.getString("ATTRIBUTEID")).append(",");
/*      */           }
/*      */           else
/*      */           {
/* 2543 */             String aid = restimeset.getString("ATTRIBUTEID");
/* 2544 */             String aname = FormatUtil.getString(restimeset.getString("DISPLAYNAME").trim());
/* 2545 */             String unit = FormatUtil.getString(restimeset.getString("UNITS"));
/*      */             
/* 2547 */             if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*      */             {
/* 2549 */               toreturn.add(aid + "#" + aname + " " + FormatUtil.getString("in") + " " + unit);
/*      */             } else
/* 2551 */               toreturn.add(aid + "#" + aname);
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/* 2555 */         ex.printStackTrace();
/*      */       } finally {
/* 2557 */         if (restimeset != null) {
/* 2558 */           AMConnectionPool.closeStatement(restimeset);
/*      */         }
/*      */       }
/*      */       
/* 2562 */       if (childhealthids.length() > 0) {
/* 2563 */         ResultSet childset = null;
/*      */         try {
/* 2565 */           String childQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,UNITS from AM_ATTRIBUTES,AM_ATTRIBUTESDEPENDENCY,AM_ATTRIBUTES_EXT where AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTESDEPENDENCY.childid and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and  AM_ATTRIBUTESDEPENDENCY.parentid in (" + childhealthids.substring(0, childhealthids.length() - 1) + ") and AM_ATTRIBUTES.TYPE < 3 AND AM_ATTRIBUTES_EXT.REPORTS_ENABLED < 2 and AM_ATTRIBUTESDEPENDENCY.childid not in (214,215,415,4838,4839,4842,4848,4858,4867,6242,6243,2734,2735,2736,2737,2738,2739,2746,2747,2748,2755,2756) and ATTRIBUTE not in ('Availability','Health') ";
/* 2566 */           childset = AMConnectionPool.executeQueryStmt(childQuery);
/*      */           
/* 2568 */           while (childset.next()) {
/* 2569 */             String attid = childset.getString("ATTRIBUTEID");
/* 2570 */             String restype = FormatUtil.getString(childset.getString("RESOURCETYPE"));
/* 2571 */             String disname = FormatUtil.getString(childset.getString("DISPLAYNAME").trim());
/* 2572 */             String unit = FormatUtil.getString(childset.getString("UNITS"));
/* 2573 */             String aname = disname + " (" + restype + ")";
/* 2574 */             if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit))) {
/* 2575 */               aname = disname + " " + FormatUtil.getString("in") + " " + unit + " (" + restype + ")";
/*      */             }
/* 2577 */             toreturn.add(attid + "#" + aname);
/*      */           }
/*      */         } catch (Exception ex) {
/* 2580 */           ex.printStackTrace();
/*      */         } finally {
/* 2582 */           if (childset != null) {
/* 2583 */             AMConnectionPool.closeStatement(childset);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2588 */       ResultSet rs = null;
/*      */       try {
/* 2590 */         String scriptQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES_EXT.REPORTS_ENABLED,AM_ATTRIBUTES_EXT.ISARCHIVEING from AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_ATTRIBUTES.TYPE < 3 AND AM_ATTRIBUTES_EXT.REPORTS_ENABLED < 2 and AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid;
/* 2591 */         rs = AMConnectionPool.executeQueryStmt(scriptQuery);
/* 2592 */         while (rs.next())
/*      */         {
/* 2594 */           String aid = rs.getString("ATTRIBUTEID");
/* 2595 */           String aname = FormatUtil.getString(rs.getString("DISPLAYNAME").trim());
/* 2596 */           String unit = FormatUtil.getString(rs.getString("UNITS"));
/*      */           
/* 2598 */           if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*      */           {
/* 2600 */             toreturn.add(aid + "#" + aname + " " + FormatUtil.getString("in") + " " + unit);
/*      */           } else {
/* 2602 */             toreturn.add(aid + "#" + aname);
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/* 2606 */         ex.printStackTrace();
/*      */       } finally {
/* 2608 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/* 2611 */       ResultSet attrset = null;
/*      */       try {
/* 2613 */         String secondAttributeQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES_EXT.REPORTS_ENABLED,AM_ATTRIBUTES_EXT.ISARCHIVEING from AM_SCRIPT_TABLES,AM_CAM_DC_ATTRIBUTES,AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_CAM_DC_ATTRIBUTES.GROUPID=AM_SCRIPT_TABLES.TABLEID and AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_ATTRIBUTES.TYPE < 3 AND AM_ATTRIBUTES_EXT.REPORTS_ENABLED < 2 and ATTRIBUTE not in ('Availability','Health')  and AM_SCRIPT_TABLES.SCRIPTID=" + resourceid;
/* 2614 */         attrset = AMConnectionPool.executeQueryStmt(secondAttributeQuery);
/* 2615 */         while (attrset.next()) {
/* 2616 */           String attid = attrset.getString("ATTRIBUTEID");
/* 2617 */           String restype = FormatUtil.getString(attrset.getString("RESOURCETYPE"));
/* 2618 */           String disname = FormatUtil.getString(attrset.getString("DISPLAYNAME").trim());
/* 2619 */           String unit = FormatUtil.getString(attrset.getString("UNITS"));
/* 2620 */           String aname = disname + " (" + restype + ")";
/* 2621 */           if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit))) {
/* 2622 */             aname = disname + " " + FormatUtil.getString("in") + " " + unit + " (" + restype + ")";
/*      */           }
/* 2624 */           toreturn.add(attid + "#" + aname);
/*      */         }
/*      */       } catch (Exception ex) {
/* 2627 */         ex.printStackTrace();
/*      */       } finally {
/* 2629 */         if (attrset != null) {
/* 2630 */           AMConnectionPool.closeStatement(attrset);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2635 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2638 */     return toreturn;
/*      */   }
/*      */   
/*      */   private void setUserConfiguredMonitorTypes(ActionForm form, HttpServletRequest request) {
/* 2642 */     MyPageForm fm = (MyPageForm)form;
/* 2643 */     ArrayList availMonitorTypes = new ArrayList();
/* 2644 */     AMConnectionPool pool = new AMConnectionPool();
/* 2645 */     Vector resIds_vector = null;
/* 2646 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/* 2650 */       int widgetType = fm.getWidgetType();
/* 2651 */       String nwdcondition = "";
/* 2652 */       String businessDashboardTypesCondition = "";
/*      */       
/*      */ 
/*      */ 
/* 2656 */       nwdcondition = " and AM_ManagedResourceType.RESOURCEGROUP  not in ('NWD','EMO','SAN') ";
/*      */       
/* 2658 */       String excludeAPMInsight = " and TYPE not in ('APM-Insight-Instance')";
/* 2659 */       if (fm.getPageType().equals("businesspage")) {
/* 2660 */         businessDashboardTypesCondition = " and AM_ManagedResourceType.RESOURCETYPE in ('SAP','SAP-CCMS','Script Monitor','QueryMonitor','Generic WMI','Custom-Application','SNMP','JMX1.2-MX4J-RMI','Ping Monitor','LDAP Server','Port-Test','DNSMonitor','UrlMonitor','UrlSeq','Web Service','RBM') ";
/*      */       }
/* 2662 */       String monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + excludeAPMInsight + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME order by AM_ManagedResourceType.DISPLAYNAME";
/*      */       
/* 2664 */       if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 2666 */         if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2667 */           resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser(), request, null);
/* 2668 */           monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where   AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " and " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME  order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         }
/* 2670 */         else if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2671 */           String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 2672 */           monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE inner join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_USERRESOURCESTABLE.USERID=" + userid + " and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME  order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         } else {
/* 2674 */           resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 2675 */           monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where   AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " and " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME  order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2681 */       String defaultMonitorType = "";
/*      */       
/* 2683 */       rs = AMConnectionPool.executeQueryStmt(monitortypeQuery);
/* 2684 */       boolean isWindowsAdded = false;
/* 2685 */       boolean isVMAdded = false;
/* 2686 */       while (rs.next()) {
/* 2687 */         if (defaultMonitorType.equals(""))
/*      */         {
/* 2689 */           defaultMonitorType = rs.getString("TYPE");
/*      */         }
/* 2691 */         String resourcetype = rs.getString("TYPE");
/* 2692 */         String displayname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2693 */         if ((resourcetype.indexOf("Windows") != -1) && (!resourcetype.equals("WindowsAzure")) && (!resourcetype.equals("Windows Cluster")))
/*      */         {
/* 2695 */           if (isWindowsAdded) {
/*      */             continue;
/*      */           }
/* 2698 */           isWindowsAdded = true;
/* 2699 */           resourcetype = "$ComplexType_Windows";
/* 2700 */           displayname = FormatUtil.getString("Windows");
/* 2701 */         } else if (resourcetype.equals("file"))
/*      */         {
/* 2703 */           displayname = FormatUtil.getString("am.monitortype.file.text");
/* 2704 */         } else if (resourcetype.equals("directory"))
/*      */         {
/* 2706 */           displayname = FormatUtil.getString("am.monitortype.directory.text");
/* 2707 */         } else if ((resourcetype.equals("VirtualMachine")) || (resourcetype.equals("XenServerVM"))) {
/* 2708 */           if (isVMAdded) {
/*      */             continue;
/*      */           }
/* 2711 */           isVMAdded = true;
/*      */         }
/* 2713 */         Properties dataProps = new Properties();
/* 2714 */         dataProps.setProperty("label", displayname);
/* 2715 */         dataProps.setProperty("value", resourcetype);
/* 2716 */         availMonitorTypes.add(dataProps);
/*      */       }
/* 2718 */       rs.close();
/* 2719 */       if (fm.getPageType().equals("businesspage")) {
/* 2720 */         String customtypes = "select AM_ManagedResourceType.RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_MONITOR_TYPES on TYPENAME=AM_ManagedObject.TYPE inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_MONITOR_TYPES.TYPENAME  where AMCREATED='NO' " + nwdcondition + "  group by AM_ManagedResourceType.RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME order by AM_ManagedResourceType.DISPLAYNAME";
/* 2721 */         if (ClientDBUtil.isPrivilegedUser(request))
/*      */         {
/* 2723 */           customtypes = "select AM_ManagedResourceType.RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_MONITOR_TYPES on TYPENAME=AM_ManagedObject.TYPE inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_MONITOR_TYPES.TYPENAME  where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " AMCREATED='NO' " + nwdcondition + "  group by AM_ManagedResourceType.RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         }
/* 2725 */         rs = AMConnectionPool.executeQueryStmt(customtypes);
/* 2726 */         while (rs.next()) {
/* 2727 */           String displayname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/* 2728 */           String resourcetype = rs.getString("RESOURCETYPE");
/* 2729 */           Properties dataProps = new Properties();
/* 2730 */           dataProps.setProperty("label", displayname);
/* 2731 */           dataProps.setProperty("value", resourcetype);
/* 2732 */           availMonitorTypes.add(dataProps);
/*      */         }
/* 2734 */         rs.close();
/*      */       }
/* 2736 */       if (widgetType == 3) {
/* 2737 */         Properties dataProps = new Properties();
/* 2738 */         dataProps.setProperty("label", FormatUtil.getString("VMWare Clusters"));
/* 2739 */         dataProps.setProperty("value", "$ComplexType_VC_HAI");
/* 2740 */         availMonitorTypes.add(dataProps);
/*      */       }
/* 2742 */       if (widgetType != 2) {
/* 2743 */         Properties dataProps = new Properties();
/* 2744 */         dataProps.setProperty("label", FormatUtil.getString("am.mypage.complextype.servers.text"));
/* 2745 */         dataProps.setProperty("value", "$ComplexType_Servers");
/* 2746 */         availMonitorTypes.add(dataProps);
/* 2747 */         if ((widgetType != 105) && (widgetType != 106) && (widgetType != 3))
/*      */         {
/* 2749 */           dataProps = new Properties();
/* 2750 */           dataProps.setProperty("label", FormatUtil.getString("am.mypage.complextype.all.text"));
/* 2751 */           dataProps.setProperty("value", "$ComplexType_All");
/* 2752 */           availMonitorTypes.add(0, dataProps);
/*      */         }
/*      */       }
/*      */       
/* 2756 */       if ((widgetType == 107) || (widgetType == 102) || (widgetType == 104) || (widgetType == 105) || (widgetType == 106)) {
/* 2757 */         Properties dataProps = new Properties();
/* 2758 */         dataProps.setProperty("label", FormatUtil.getString("Network Devices"));
/* 2759 */         dataProps.setProperty("value", "$ComplexType_NetworkDevices");
/* 2760 */         availMonitorTypes.add(dataProps);
/*      */         
/* 2762 */         dataProps = new Properties();
/* 2763 */         dataProps.setProperty("label", FormatUtil.getString("Storage Devices"));
/* 2764 */         dataProps.setProperty("value", "$ComplexType_StorageDevices");
/* 2765 */         availMonitorTypes.add(dataProps);
/*      */         
/* 2767 */         Properties dataProps1 = new Properties();
/* 2768 */         dataProps1.setProperty("label", FormatUtil.getString("am.mypage.availAndHealthstatus.all.text"));
/* 2769 */         dataProps1.setProperty("value", "$ComplexType_AllApps");
/* 2770 */         availMonitorTypes.add(dataProps1);
/*      */         
/* 2772 */         Properties dataProps2 = new Properties();
/* 2773 */         dataProps2.setProperty("label", FormatUtil.getString("am.mypage.availAndHealthstatus.all.servers"));
/* 2774 */         dataProps2.setProperty("value", "$ComplexType_AllSers");
/* 2775 */         availMonitorTypes.add(dataProps2);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2784 */       HashMap<String, ArrayList<String>> selectedResidattribsMap = new HashMap();
/* 2785 */       int monitorSelectionType = fm.getMonitorSelectionType();
/*      */       
/* 2787 */       if (widgetType == 2) {
/* 2788 */         String monitorSelectionTypeFromClient = request.getParameter("monitorSelectionType");
/* 2789 */         if (monitorSelectionTypeFromClient != null) {
/* 2790 */           monitorSelectionType = Integer.parseInt(monitorSelectionTypeFromClient);
/*      */         }
/*      */         else {
/* 2793 */           String widgetid = request.getParameter("widgetid");
/* 2794 */           selectedResidattribsMap = getSelResAttrForAllTypes(widgetid);
/* 2795 */           if (!selectedResidattribsMap.isEmpty())
/*      */           {
/* 2797 */             monitorSelectionType = 2;
/*      */           }
/*      */         }
/* 2800 */         fm.setMonitorSelectionType(monitorSelectionType);
/* 2801 */         request.setAttribute("monitorSelectionType", "" + monitorSelectionType);
/*      */       }
/*      */       
/* 2804 */       if ((widgetType == 2) && (monitorSelectionType == 2))
/*      */       {
/* 2806 */         Properties dataProps = new Properties();
/* 2807 */         dataProps.setProperty("label", FormatUtil.getString("am.mypage.complextype.all.text"));
/* 2808 */         dataProps.setProperty("value", "$ComplexType_All");
/* 2809 */         availMonitorTypes.add(dataProps);
/*      */       }
/* 2811 */       fm.setAvailMonitorTypes(availMonitorTypes);
/* 2812 */       if (fm.getSelectedMonitorType().equals(""))
/*      */       {
/*      */ 
/* 2815 */         fm.setSelectedMonitorType(defaultMonitorType);
/*      */       }
/*      */       
/* 2818 */       if ((widgetType == 2) && (monitorSelectionType == 1) && (fm.getSelectedMonitorType().equals("$ComplexType_All")))
/*      */       {
/* 2820 */         fm.setSelectedMonitorType(defaultMonitorType);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2825 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void setAvailableMonitors(ActionForm form, String restype, String widgetid, String selecttionType, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2831 */     MyPageForm fm = (MyPageForm)form;
/* 2832 */     AMConnectionPool pool = new AMConnectionPool();
/* 2833 */     ResultSet rs = null;
/* 2834 */     selecttionType = fm.getSelecttionType();
/*      */     try
/*      */     {
/* 2837 */       if (selecttionType.equals("CUSTOM"))
/*      */       {
/* 2839 */         getResourceForSelectionType(mapping, form, request, response);
/*      */         
/* 2841 */         String selectedMultiMonitorsQuery = "select RESOURCEID from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid;
/*      */         
/* 2843 */         ArrayList selMonList = this.mo.getRows(selectedMultiMonitorsQuery);
/* 2844 */         if (selMonList.size() > 0) {
/* 2845 */           ArrayList selectedMultiMonitors_list = new ArrayList();
/*      */           
/* 2847 */           for (int i = 0; i < selMonList.size(); i++) {
/* 2848 */             selectedMultiMonitors_list.add((String)((ArrayList)selMonList.get(i)).get(0));
/*      */           }
/* 2850 */           request.setAttribute("selectedMultiMonitors_list", selectedMultiMonitors_list);
/*      */         }
/* 2852 */       } else if (selecttionType.equals("GROUP"))
/*      */       {
/* 2854 */         getResourceForSelectionType(mapping, form, request, response);
/*      */ 
/*      */ 
/*      */       }
/* 2858 */       else if (selecttionType.equals("CUSTOM FIELDS")) {
/* 2859 */         String aliasname = "";
/* 2860 */         String fieldid = "";
/* 2861 */         String fieldtype = "";
/* 2862 */         HashMap customField = getCustomValues(widgetid);
/* 2863 */         aliasname = (String)customField.get("aliasname");
/* 2864 */         fieldid = (String)customField.get("fieldid");
/* 2865 */         fieldtype = (String)customField.get("fieldtype");
/* 2866 */         fm.setSelecttionType(aliasname + "$" + fieldid);
/*      */         
/* 2868 */         if ((fieldtype.equals("1")) || (fieldtype.equals("3"))) {
/* 2869 */           String selectedMultiMonitorsQuery = "select RESOURCEID from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid;
/*      */           
/* 2871 */           ArrayList selMonList = this.mo.getRows(selectedMultiMonitorsQuery);
/* 2872 */           if (selMonList.size() > 0) {
/* 2873 */             ArrayList selectedMultiMonitors_list = new ArrayList();
/*      */             
/* 2875 */             for (int i = 0; i < selMonList.size(); i++) {
/* 2876 */               selectedMultiMonitors_list.add((String)((ArrayList)selMonList.get(i)).get(0));
/*      */             }
/* 2878 */             request.setAttribute("selectedMultiMonitors_list", selectedMultiMonitors_list);
/*      */           }
/*      */         }
/* 2881 */         request.setAttribute("fromedit", "true");
/* 2882 */         request.setAttribute("widgetid", widgetid);
/* 2883 */         getCustomfieldMonitors(mapping, form, request, response);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2887 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward saveBusinessViewWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2893 */     AMConnectionPool pool = new AMConnectionPool();
/* 2894 */     MyPageForm fm = (MyPageForm)form;
/* 2895 */     String pageid = request.getParameter("pageid");
/* 2896 */     String widgetid = request.getParameter("widgetid");
/*      */     try {
/* 2898 */       String widgetname = fm.getDisplayName();
/* 2899 */       String query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "' where WIDGETID=" + widgetid;
/* 2900 */       AMConnectionPool.executeUpdateStmt(query1);
/* 2901 */       String selectedView = fm.getSelectedBusinessView();
/* 2902 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_FLASHVIEWS where WIDGETID=" + widgetid);
/* 2903 */       AMConnectionPool.executeUpdateStmt("insert into AM_MYPAGE_WIDGET_FLASHVIEWS(WIDGETID,VIEWID) values (" + widgetid + "," + selectedView + ")");
/*      */     } catch (Exception ex) {
/* 2905 */       ex.printStackTrace();
/*      */     }
/* 2907 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?savedstaus=true&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward saveTopologyMapWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2912 */     AMConnectionPool pool = new AMConnectionPool();
/* 2913 */     MyPageForm fm = (MyPageForm)form;
/* 2914 */     String pageid = request.getParameter("pageid");
/* 2915 */     String widgetId = request.getParameter("widgetid");
/* 2916 */     String mapViewName = fm.getSelectedTopologyMapView();
/* 2917 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*      */     try
/*      */     {
/* 2920 */       String listOfMapNames = MapViewUtil.getListOfMapViews(username, request);
/* 2921 */       StringTokenizer st = new StringTokenizer(listOfMapNames, ",");
/* 2922 */       ArrayList tokArray = new ArrayList();
/* 2923 */       while (st.hasMoreTokens()) {
/* 2924 */         tokArray.add(st.nextToken());
/*      */       }
/*      */       
/* 2927 */       Collections.sort(tokArray);
/*      */       
/* 2929 */       fm.setAvailTopologyMapViews(tokArray);
/* 2930 */       String widgetname = fm.getDisplayName();
/* 2931 */       if ((widgetId != null) && (!widgetId.trim().equals(""))) {
/* 2932 */         String url = "/mapViewAction.do?method=showMap&mapViewName=" + mapViewName + "&admin=true&oldtab=0&type=view";
/* 2933 */         String query1 = "update AM_MYPAGE_WIDGETS set WIDGETURL='" + url + "', DISPLAYNAME='" + widgetname + "'  where WIDGETID=" + widgetId;
/* 2934 */         AMConnectionPool.executeUpdateStmt(query1);
/*      */       }
/*      */     } catch (Exception ex) {
/* 2937 */       ex.printStackTrace();
/*      */     }
/* 2939 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?savedstaus=true&widgetid=" + widgetId);
/*      */   }
/*      */   
/*      */   public ActionForward saveWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2943 */     String pageid = request.getParameter("pageid");
/* 2944 */     String widgetid = request.getParameter("widgetid");
/* 2945 */     String customFields = request.getParameter("customFields");
/* 2946 */     String dropdownfield = request.getParameter("selectFieldVal");
/* 2947 */     String customsearchtext = request.getParameter("customfieldsearchtext");
/* 2948 */     AMConnectionPool pool = new AMConnectionPool();
/* 2949 */     Statement stmt = AMConnectionPool.getConnection().createStatement();
/*      */     try { ActionForward localActionForward;
/* 2951 */       if ((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) {
/* 2952 */         return null;
/*      */       }
/*      */       
/* 2955 */       if (request.isUserInRole("DEMO")) {
/* 2956 */         return null;
/*      */       }
/* 2958 */       ResultSet rs = null;
/* 2959 */       fm = (MyPageForm)form;
/*      */       
/* 2961 */       widgetType = fm.getWidgetType();
/* 2962 */       resourcetype = fm.getSelectedMonitorType();
/* 2963 */       attributeid = fm.getSelectedAttribute();
/* 2964 */       graphtype = fm.getSelectedGraphType();
/* 2965 */       widgetname = fm.getDisplayName();
/* 2966 */       period = fm.getPeriod();
/* 2967 */       description = fm.getDescription();
/* 2968 */       selecttionType = fm.getSelecttionType();
/* 2969 */       url = fm.getUrl();
/* 2970 */       scaleOption = fm.getCustomScaleOption();
/* 2971 */       String maxScale = fm.getMaximumScale();
/* 2972 */       String minScale = fm.getMinimumScale();
/* 2973 */       selectedBusinessR = fm.getBusinessPeriod();
/* 2974 */       maximumScale = 0;
/* 2975 */       minimumScale = 0;
/* 2976 */       if (com.adventnet.appmanager.util.Constants.isIntegerNumber(maxScale)) {
/* 2977 */         maximumScale = Integer.parseInt(maxScale);
/*      */       }
/* 2979 */       if (com.adventnet.appmanager.util.Constants.isIntegerNumber(minScale)) {
/* 2980 */         minimumScale = Integer.parseInt(minScale);
/*      */       }
/* 2982 */       todayavail = fm.getTodaysavailability();
/* 2983 */       monitorsStatus = fm.getMonitorsStatus() ? 1 : 0;
/* 2984 */       limitn = fm.getLimitn();
/* 2985 */       if (3 == widgetType) {
/* 2986 */         limitn = 5000;
/*      */       }
/* 2988 */       toporbottom = fm.getTopnOrder();
/* 2989 */       filterbyName = fm.getFilterByName();
/* 2990 */       height = fm.getWidgetHeight();
/* 2991 */       isalertStatusForMetrics = 0;
/* 2992 */       isgraphForAdditionalMetric = 0;
/* 2993 */       isSubGroup = 0;
/* 2994 */       relatedAttributes = fm.getRelatedAttributes();
/* 2995 */       isalertStatusForMetrics = fm.isalertStatusForMetrics() ? 1 : 0;
/* 2996 */       isgraphForAdditionalMetric = fm.isGraphforAdditionalMetric() ? 1 : 0;
/* 2997 */       isSubGroup = fm.getDisplaySubGroups() ? 1 : 0;
/* 2998 */       if (DBQueryUtil.isPgsql()) {
/* 2999 */         widgetname = ManagedApplication.findAndReplaceAll(widgetname, "'", "''");
/* 3000 */         description = ManagedApplication.findAndReplaceAll(description, "'", "''");
/* 3001 */       } else if (DBQueryUtil.isMysql()) {
/* 3002 */         widgetname = ManagedApplication.findAndReplaceAll(widgetname, "\\", "\\\\");
/* 3003 */         widgetname = ManagedApplication.findAndReplaceAll(widgetname, "'", "\\'");
/* 3004 */         description = ManagedApplication.findAndReplaceAll(description, "\\", "\\\\");
/* 3005 */         description = ManagedApplication.findAndReplaceAll(description, "'", "\\'");
/*      */       }
/*      */       
/*      */ 
/* 3009 */       if ((widgetType == 51) || (widgetType == 104)) {
/* 3010 */         resGrpStmt = AMConnectionPool.getConnection().createStatement();
/*      */         try {
/* 3012 */           String[] selectedResourceGroups = fm.getSelectedItems();
/* 3013 */           String selQuery = "select WIDGETID from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/* 3014 */           ResultSet selQueryRs = AMConnectionPool.executeQueryStmt(selQuery);
/* 3015 */           if (selQueryRs.next()) {
/* 3016 */             resGrpStmt.addBatch("delete from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid);
/*      */           }
/* 3018 */           selQueryRs.close();
/* 3019 */           for (int i = 0; i < selectedResourceGroups.length; i++) {
/* 3020 */             resGrpStmt.addBatch("insert into AM_WIDGETS_ASSOCIATEDTYPES(WIDGETID,ASSOCIATEDTYPES) values (" + widgetid + ",'" + selectedResourceGroups[i] + "')");
/*      */           }
/* 3022 */           resGrpStmt.executeBatch();
/*      */           
/*      */ 
/*      */           try
/*      */           {
/* 3027 */             resGrpStmt.clearBatch();
/* 3028 */             resGrpStmt.close();
/*      */           }
/*      */           catch (Exception e) {}
/*      */           
/*      */ 
/*      */ 
/* 3034 */           if (widgetType != 21) {
/*      */             break label784;
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3024 */           e.printStackTrace();
/*      */         } finally {
/*      */           try {
/* 3027 */             resGrpStmt.clearBatch();
/* 3028 */             resGrpStmt.close();
/*      */           }
/*      */           catch (Exception e) {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3035 */       return saveBusinessViewWidget(mapping, form, request, response);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */       MyPageForm fm;
/*      */       
/*      */ 
/*      */ 
/*      */       int widgetType;
/*      */       
/*      */ 
/*      */ 
/*      */       String resourcetype;
/*      */       
/*      */ 
/*      */ 
/*      */       String attributeid;
/*      */       
/*      */ 
/*      */ 
/*      */       String graphtype;
/*      */       
/*      */ 
/*      */ 
/*      */       String widgetname;
/*      */       
/*      */ 
/*      */ 
/*      */       String period;
/*      */       
/*      */ 
/*      */ 
/*      */       String description;
/*      */       
/*      */ 
/*      */ 
/*      */       String selecttionType;
/*      */       
/*      */ 
/*      */ 
/*      */       String url;
/*      */       
/*      */ 
/*      */ 
/*      */       String scaleOption;
/*      */       
/*      */ 
/*      */ 
/*      */       String selectedBusinessR;
/*      */       
/*      */ 
/*      */ 
/*      */       int maximumScale;
/*      */       
/*      */ 
/*      */ 
/*      */       int minimumScale;
/*      */       
/*      */ 
/*      */       boolean todayavail;
/*      */       
/*      */ 
/*      */       int monitorsStatus;
/*      */       
/*      */ 
/*      */       int limitn;
/*      */       
/*      */ 
/*      */       String toporbottom;
/*      */       
/*      */ 
/*      */       String filterbyName;
/*      */       
/*      */ 
/*      */       int height;
/*      */       
/*      */ 
/*      */       int isalertStatusForMetrics;
/*      */       
/*      */ 
/*      */       int isgraphForAdditionalMetric;
/*      */       
/*      */ 
/*      */       int isSubGroup;
/*      */       
/*      */ 
/*      */       String[] relatedAttributes;
/*      */       
/*      */ 
/*      */       Statement resGrpStmt;
/*      */       
/*      */ 
/*      */       label784:
/*      */       
/*      */ 
/*      */       String query1;
/*      */       
/*      */ 
/*      */       String widgType;
/*      */       
/*      */ 
/*      */       Statement resGrpStmt;
/*      */       
/*      */ 
/*      */       ResultSet selQueryRs;
/*      */       
/*      */ 
/*      */       String customColumn;
/*      */       
/*      */ 
/*      */       String fieldId;
/*      */       
/*      */ 
/*      */       int fieldType;
/*      */       
/*      */ 
/*      */       String customVal;
/*      */       
/*      */ 
/*      */       int monitorSelectionType;
/*      */       
/*      */ 
/*      */       String[] multiResAttribs;
/*      */       
/*      */ 
/*      */       List attIdList;
/*      */       
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */       String query3;
/*      */       
/*      */ 
/*      */       String query2;
/*      */       
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */       String typeDisplayname;
/*      */       
/*      */ 
/*      */       String selectedMonitor;
/*      */       
/*      */ 
/*      */       int monitorSelectionType;
/*      */       
/*      */ 
/*      */       String[] selectedMonitors;
/*      */       
/*      */ 
/*      */       String[] multiResAttribs;
/*      */       
/*      */ 
/*      */       int m;
/*      */       
/*      */ 
/*      */       String selectedMG;
/*      */       
/*      */ 
/*      */       String mgquery;
/*      */       
/*      */ 
/*      */       String[] selectedMultiMonitors;
/*      */       
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */       String customColumn;
/*      */       
/*      */ 
/*      */       String fieldId;
/*      */       
/*      */ 
/*      */       int fieldType;
/*      */       
/*      */ 
/*      */       String customVal;
/*      */       
/*      */ 
/* 3217 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 3220 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/* 3037 */     if (widgetType == 204) {
/* 3038 */       resGrpStmt = saveTopologyMapWidget(mapping, form, request, response);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3220 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/* 3223 */       return resGrpStmt;
/*      */     }
/* 3040 */     query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "',DESCRIPTION='" + description + "', RESOURCETYPE='" + resourcetype + "',LIMIT_N=" + limitn + ",REPORTPERIOD='" + period + "',SELECTMONITORTYPE='" + selecttionType + "',LIMITTYPE='" + toporbottom + "',WIDGETURL='" + url + "',HEIGHT=" + height + ",UTILCOLUMN1=" + isalertStatusForMetrics + ",UTILCOLUMN2=" + isgraphForAdditionalMetric + ",UTILCOLUMN3='" + scaleOption + "',MAXSCALE=" + maximumScale + ",MINSCALE=" + minimumScale + ",SUBGROUP=" + isSubGroup + ",FILTEROPTION='" + filterbyName + "'";
/*      */     
/* 3042 */     if ((selectedBusinessR != null) && (!selectedBusinessR.equalsIgnoreCase("oni"))) {
/* 3043 */       query1 = query1 + " ,BUSINESSRULE=" + selectedBusinessR;
/*      */     } else {
/* 3045 */       query1 = query1 + " ,BUSINESSRULE=null";
/*      */     }
/* 3047 */     query1 = query1 + " where WIDGETID=" + widgetid;
/*      */     
/*      */ 
/* 3050 */     widgType = Integer.toString(widgetType);
/* 3051 */     if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (DashboardUtil.externalProdWidgets.contains(widgType))) {
/* 3052 */       resGrpStmt = AMConnectionPool.getConnection().createStatement();
/* 3053 */       selQueryRs = null;
/*      */       try {
/* 3055 */         String[] selectedResourceGroups = fm.getSelectedItems();
/* 3056 */         String selQuery = "select WIDGETID from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/* 3057 */         selQueryRs = AMConnectionPool.executeQueryStmt(selQuery);
/* 3058 */         if (selQueryRs.next()) {
/* 3059 */           resGrpStmt.addBatch("delete from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid);
/*      */         }
/* 3061 */         selQueryRs.close();
/* 3062 */         for (int i = 0; i < selectedResourceGroups.length; i++) {
/* 3063 */           resGrpStmt.addBatch("insert into AM_WIDGETS_ASSOCIATEDTYPES(WIDGETID,ASSOCIATEDTYPES) values (" + widgetid + ",'" + selectedResourceGroups[i] + "')");
/*      */         }
/* 3065 */         resGrpStmt.executeBatch();
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 3070 */           resGrpStmt.clearBatch();
/* 3071 */           resGrpStmt.close();
/* 3072 */           if (selQueryRs != null) {
/* 3073 */             selQueryRs.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 3076 */           e.printStackTrace();
/*      */         }
/*      */         
/* 3079 */         if (!widgType.equals("503")) {
/*      */           break label1502;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3067 */         e.printStackTrace();
/*      */       } finally {
/*      */         try {
/* 3070 */           resGrpStmt.clearBatch();
/* 3071 */           resGrpStmt.close();
/* 3072 */           if (selQueryRs != null) {
/* 3073 */             selQueryRs.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 3076 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 3080 */       query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "',DESCRIPTION='" + description + "',LIMIT_N=" + limitn + " where WIDGETID=" + widgetid;
/*      */     }
/*      */     
/*      */     label1502:
/*      */     
/* 3085 */     if (widgetType == 51) {
/* 3086 */       query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "',DESCRIPTION='" + description + "',LIMIT_N=" + limitn + " where WIDGETID=" + widgetid;
/* 3087 */     } else if ((widgetType == 202) || (widgetType == 203) || (widgetType == 105) || (widgetType == 106)) {
/* 3088 */       query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "',DESCRIPTION='" + description + "',LIMIT_N=" + limitn + ",REPORTPERIOD='" + period + "', RESOURCETYPE='" + resourcetype + "' where WIDGETID=" + widgetid;
/*      */     }
/* 3090 */     if ((widgetType == 201) || (widgetType == 202) || (widgetType == 203))
/*      */     {
/* 3092 */       query1 = "update AM_MYPAGE_WIDGETS set DISPLAYNAME='" + widgetname + "',DESCRIPTION='" + description + "',LIMIT_N=" + limitn + ",REPORTPERIOD='" + period + "',SELECTMONITORTYPE='" + selecttionType + "', RESOURCETYPE='" + resourcetype + "',SUBGROUP=" + isSubGroup + " where WIDGETID=" + widgetid;
/*      */       
/* 3094 */       if (!selecttionType.equals("ALL"))
/*      */       {
/* 3096 */         AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_CUSTOMFIELD where WIDGETID=" + widgetid);
/* 3097 */         customColumn = selecttionType.substring(0, selecttionType.indexOf("$"));
/* 3098 */         fieldId = selecttionType.substring(selecttionType.indexOf("$") + 1);
/* 3099 */         fieldType = MyFields.getCustomFieldType(fieldId);
/* 3100 */         customVal = "";
/* 3101 */         if ((fieldType == 1) || (fieldType == 3)) {
/* 3102 */           customVal = request.getParameter("customfieldsearchtext");
/*      */         } else {
/* 3104 */           customVal = request.getParameter("selectFieldVal");
/* 3105 */           customVal = customVal.substring(customVal.indexOf("$") + 1);
/*      */         }
/*      */         
/* 3108 */         stmt.addBatch("insert into AM_MYPAGE_WIDGET_CUSTOMFIELD values (" + widgetid + "," + fieldId + ",'" + customVal + "')");
/* 3109 */         stmt.addBatch("update AM_MYPAGE_WIDGETS set SELECTMONITORTYPE='CUSTOM FIELDS' where WIDGETID=" + widgetid);
/*      */       }
/*      */       
/* 3112 */       if (widgetType == 201) {
/* 3113 */         stmt.addBatch("update AM_MYPAGE_WIDGETS set UTILCOLUMN3='" + todayavail + "' where WIDGETID=" + widgetid);
/* 3114 */         stmt.addBatch("update AM_MYPAGE_WIDGETS set UTILCOLUMN2='" + monitorsStatus + "' where WIDGETID=" + widgetid);
/*      */       }
/*      */     }
/* 3117 */     AMConnectionPool.executeUpdateStmt(query1);
/*      */     
/* 3119 */     if ((widgetType == 1) || (widgetType == 2) || (widgetType == 3) || (widgetType == 4)) {
/* 3120 */       monitorSelectionType = fm.getMonitorSelectionType();
/*      */       
/* 3122 */       if ((widgetType == 2) && (monitorSelectionType == 2)) {
/* 3123 */         AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_ATTRIBUTES where WIDGETID=" + widgetid);
/* 3124 */         multiResAttribs = fm.getMultiAttributes();
/* 3125 */         attIdList = new ArrayList();
/* 3126 */         for (i = 0; i < multiResAttribs.length; i++)
/*      */         {
/* 3128 */           String attId = multiResAttribs[i].substring(multiResAttribs[i].indexOf("#") + 1).trim();
/* 3129 */           if (!attIdList.contains(attId))
/*      */           {
/* 3131 */             stmt.addBatch("insert into AM_MYPAGE_WIDGET_ATTRIBUTES(WIDGETID,ATTRIBUTEID,GRAPHTYPE,ISPRIMARYATTRIBUTE) values (" + widgetid + "," + attId + ",'" + graphtype + "',0)");
/*      */           }
/* 3133 */           attIdList.add(attId);
/*      */         }
/*      */       } else {
/* 3136 */         query3 = "insert into AM_MYPAGE_WIDGET_ATTRIBUTES(WIDGETID,ATTRIBUTEID,GRAPHTYPE,ISPRIMARYATTRIBUTE) values (" + widgetid + "," + attributeid + ",'" + graphtype + "',0)";
/* 3137 */         stmt.addBatch(query3);
/* 3138 */         query2 = "delete from AM_MYPAGE_WIDGET_ATTRIBUTES where WIDGETID=" + widgetid;
/* 3139 */         AMConnectionPool.executeUpdateStmt(query2);
/* 3140 */         for (i = 0; i < relatedAttributes.length; i++) {
/* 3141 */           stmt.addBatch("insert into AM_MYPAGE_WIDGET_ATTRIBUTES(WIDGETID,ATTRIBUTEID,GRAPHTYPE,ISPRIMARYATTRIBUTE) values (" + widgetid + "," + relatedAttributes[i] + ",'" + graphtype + "',1)");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3146 */       typeDisplayname = DashboardUtil.getWidgetTypeName(String.valueOf(widgetType));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3151 */     if (widgetType == 2) {
/* 3152 */       selectedMonitor = fm.getSelectedMonitor();
/* 3153 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid);
/* 3154 */       AMConnectionPool.executeUpdateStmt("delete from AM_WIDGET_ALLTYPES where WIDGETID=" + widgetid);
/*      */       
/* 3156 */       monitorSelectionType = fm.getMonitorSelectionType();
/* 3157 */       if ((widgetType == 2) && (monitorSelectionType == 2)) {
/* 3158 */         selectedMonitors = fm.getMultiMonitor();
/* 3159 */         multiResAttribs = fm.getMultiAttributes();
/* 3160 */         for (m = 0; m < selectedMonitors.length; m++) {
/* 3161 */           for (int n = 0; n < multiResAttribs.length; n++) {
/* 3162 */             String typeForSelected = multiResAttribs[n].substring(0, multiResAttribs[n].indexOf("#")).trim();
/* 3163 */             String resType = "";
/* 3164 */             String resGroup = "";
/* 3165 */             HashMap<String, Object> resDetailsMap = ReportUtil.getResourceDetails(selectedMonitors[m].trim());
/* 3166 */             if (!resDetailsMap.isEmpty()) {
/* 3167 */               resType = (String)resDetailsMap.get("TYPE");
/* 3168 */               resGroup = (String)resDetailsMap.get("SUBGROUP");
/*      */             }
/* 3170 */             if ((resType.equalsIgnoreCase(typeForSelected)) || (resGroup.equalsIgnoreCase(typeForSelected)))
/*      */             {
/* 3172 */               String attId = multiResAttribs[n].substring(multiResAttribs[n].indexOf("#") + 1).trim();
/* 3173 */               stmt.addBatch("insert into AM_WIDGET_ALLTYPES(WIDGETID,RESOURCEID,ATTRIBUTEID) values (" + widgetid + ",'" + selectedMonitors[m] + "','" + attId + "')");
/*      */             }
/*      */           }
/* 3176 */           stmt.addBatch("insert into AM_MYPAGE_WIDGET_MONITORS(WIDGETID,RESOURCEID) values (" + widgetid + ",'" + selectedMonitors[m] + "')");
/*      */         }
/*      */       } else {
/* 3179 */         stmt.addBatch("insert into AM_MYPAGE_WIDGET_MONITORS(WIDGETID,RESOURCEID) values (" + widgetid + "," + selectedMonitor + ")");
/*      */       }
/* 3181 */     } else if ((widgetType == 1) || (widgetType == 3) || (widgetType == 5) || (widgetType == 4) || (widgetType == 102) || (widgetType == 103) || (widgetType == 107)) {
/* 3182 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid);
/* 3183 */       if (selecttionType.equals("GROUP"))
/*      */       {
/* 3185 */         selectedMG = fm.getSelectedMG();
/* 3186 */         mgquery = "insert into AM_MYPAGE_WIDGET_MONITORS(WIDGETID,RESOURCEID) values (" + widgetid + "," + selectedMG + ")";
/* 3187 */         stmt.addBatch(mgquery);
/* 3188 */       } else if (selecttionType.equals("CUSTOM"))
/*      */       {
/* 3190 */         selectedMultiMonitors = fm.getSelectedMultiMonitors();
/* 3191 */         for (i = 0; i < selectedMultiMonitors.length; i++) {
/* 3192 */           stmt.addBatch("insert into AM_MYPAGE_WIDGET_MONITORS(WIDGETID,RESOURCEID) values (" + widgetid + "," + selectedMultiMonitors[i] + ")");
/*      */         }
/* 3194 */       } else if ((selecttionType.indexOf("SYSTEMDATA") != -1) || (selecttionType.indexOf("USERDATA") != -1) || (selecttionType.indexOf("LOCATION_NAME") != -1) || (selecttionType.indexOf("USERNAME") != -1) || (selecttionType.indexOf("VALUEID") != -1))
/*      */       {
/* 3196 */         AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_CUSTOMFIELD where WIDGETID=" + widgetid);
/* 3197 */         customColumn = selecttionType.substring(0, selecttionType.indexOf("$"));
/* 3198 */         fieldId = selecttionType.substring(selecttionType.indexOf("$") + 1);
/*      */         
/* 3200 */         fieldType = MyFields.getCustomFieldType(fieldId);
/* 3201 */         customVal = "";
/* 3202 */         if ((fieldType == 1) || (fieldType == 3)) {
/* 3203 */           customVal = request.getParameter("customfieldsearchtext");
/*      */         } else {
/* 3205 */           customVal = request.getParameter("selectFieldVal");
/* 3206 */           customVal = customVal.substring(customVal.indexOf("$") + 1);
/*      */         }
/*      */         
/* 3209 */         stmt.addBatch("insert into AM_MYPAGE_WIDGET_CUSTOMFIELD values (" + widgetid + "," + fieldId + ",'" + customVal + "')");
/* 3210 */         stmt.addBatch("update AM_MYPAGE_WIDGETS set SELECTMONITORTYPE='CUSTOM FIELDS' where WIDGETID=" + widgetid);
/*      */       }
/*      */     }
/*      */     
/* 3214 */     stmt.executeBatch();
/* 3215 */     stmt.clearBatch();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3220 */       stmt.close();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/* 3225 */     return new ActionForward("/jsp/MyPage_EditWidget.jsp?savedstaus=true&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward deleteWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 3229 */     String widgetid = request.getParameter("widgetid");
/* 3230 */     String pageid = request.getParameter("pageid");
/*      */     try
/*      */     {
/* 3233 */       if (request.isUserInRole("DEMO")) {
/* 3234 */         return null;
/*      */       }
/* 3236 */       AMConnectionPool pool = new AMConnectionPool();
/* 3237 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid);
/* 3238 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_ATTRIBUTES where WIDGETID=" + widgetid);
/* 3239 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid);
/* 3240 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_MAPPING where WIDGETID=" + widgetid);
/* 3241 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_LAYOUT where WIDGETID=" + widgetid);
/* 3242 */       AMConnectionPool.executeUpdateStmt("delete from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid);
/* 3243 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_CUSTOMFIELD where WIDGETID=" + widgetid);
/* 3244 */       AMConnectionPool.executeUpdateStmt("delete from AM_WIDGET_ALLTYPES where WIDGETID=" + widgetid);
/*      */       
/* 3246 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_WIDGET_FLASHVIEWS where WIDGETID=" + widgetid);
/*      */     } catch (Exception ex) {
/* 3248 */       ex.printStackTrace();
/*      */     }
/* 3250 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getRelatedAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 3254 */     MyPageForm fm = (MyPageForm)form;
/*      */     
/* 3256 */     ResultSet rs = null;
/* 3257 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try {
/* 3259 */       String widgetid = request.getParameter("widgetid");
/* 3260 */       String attributeid = fm.getSelectedAttribute();
/* 3261 */       String monitortype = fm.getSelectedMonitorType();
/* 3262 */       int widgettype = fm.getWidgetType();
/* 3263 */       fm.setWidgetType(widgettype);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3268 */       if (monitortype.equals("$ComplexType_Servers"))
/*      */       {
/*      */ 
/* 3271 */         ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(attributeid);
/* 3272 */         String level = (String)attrExtTableDetails.get(10);
/* 3273 */         String displayname = (String)attrExtTableDetails.get(5);
/* 3274 */         ArrayList relatedattributes = new ArrayList();
/* 3275 */         if (level.equals("1"))
/*      */         {
/* 3277 */           String relatedattribsforComplextypeQuery = "select max(ATTRIBUTEID) as ATTRIBID,DISPLAYNAME from AM_ATTRIBUTES where RESOURCETYPE in" + com.adventnet.appmanager.util.Constants.serverTypes + "  and ATTRIBUTE in('CPU Utilization','Response Time','Physical Memory Utilization','Jobs in Minute','Jobs in 5 Minutes','Jobs in 15 Minutes','Swap Memory Utilization') and DISPLAYNAME not in ('" + displayname + "') group by DISPLAYNAME order by  DISPLAYNAME ";
/* 3278 */           rs = AMConnectionPool.executeQueryStmt(relatedattribsforComplextypeQuery);
/* 3279 */           while (rs.next()) {
/* 3280 */             ArrayList singlerow = new ArrayList();
/* 3281 */             singlerow.add(rs.getString("ATTRIBID"));
/* 3282 */             singlerow.add(FormatUtil.getString(rs.getString("DISPLAYNAME")));
/* 3283 */             singlerow.add("");
/* 3284 */             relatedattributes.add(singlerow);
/*      */           }
/* 3286 */           rs.close();
/* 3287 */         } else if (level.equals("2"))
/*      */         {
/* 3289 */           relatedattributes = getRealatedattribs(attributeid);
/*      */         }
/*      */         
/* 3292 */         request.setAttribute("relatedattributes", relatedattributes);
/*      */       }
/* 3294 */       else if (monitortype.equals("$ComplexType_All"))
/*      */       {
/* 3296 */         ArrayList relatedattributes = new ArrayList();
/* 3297 */         request.setAttribute("relatedattributes", relatedattributes);
/* 3298 */       } else if ((monitortype.equals("JMX1.2-MX4J-RMI")) || (monitortype.equals("SNMP")))
/*      */       {
/*      */ 
/*      */ 
/* 3302 */         String action = "getRelatedAttributesQuery";
/* 3303 */         String relatedattribsforComplextypeQuery = getSimilarAttributeForCAM_Actions(monitortype, attributeid, null, action);
/*      */         
/* 3305 */         rs = AMConnectionPool.executeQueryStmt(relatedattribsforComplextypeQuery);
/* 3306 */         ArrayList relatedattributes = new ArrayList();
/* 3307 */         while (rs.next()) {
/* 3308 */           ArrayList singlerow = new ArrayList();
/* 3309 */           singlerow.add(rs.getString("ATTRIBID"));
/* 3310 */           singlerow.add(rs.getString("DISPLAYNAME"));
/* 3311 */           singlerow.add("");
/* 3312 */           relatedattributes.add(singlerow);
/*      */         }
/* 3314 */         rs.close();
/* 3315 */         request.setAttribute("relatedattributes", relatedattributes);
/*      */       } else {
/* 3317 */         ArrayList relatedattributes = new ArrayList();
/* 3318 */         if (widgettype == 2) {
/* 3319 */           if (("QueryMonitor".equalsIgnoreCase(monitortype)) || ("Script Monitor".equalsIgnoreCase(monitortype)))
/*      */           {
/* 3321 */             String selectedResourceid = (String)request.getAttribute("selectedResourceid");
/* 3322 */             if ("true".equalsIgnoreCase(request.getParameter("editscriptwidget"))) {
/* 3323 */               selectedResourceid = request.getParameter("selectedMonitor");
/*      */             }
/* 3325 */             relatedattributes = getRealatedattribs(attributeid, selectedResourceid);
/*      */           } else {
/* 3327 */             relatedattributes = getRealatedattribs(attributeid);
/*      */           }
/*      */         }
/*      */         else {
/* 3331 */           relatedattributes = getRealatedattribs(attributeid);
/*      */         }
/*      */         
/* 3334 */         request.setAttribute("relatedattributes", relatedattributes);
/*      */       }
/*      */       
/* 3337 */       request.setAttribute("related_action", "relatedattributes");
/*      */     }
/*      */     catch (Exception ex) {
/* 3340 */       ex.printStackTrace();
/*      */     }
/* 3342 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward getResourceForSelectionType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 3346 */     ResultSet rs = null;
/* 3347 */     MyPageForm fm = (MyPageForm)form;
/* 3348 */     int widgettype = fm.getWidgetType();
/* 3349 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3354 */       HashMap widgetProps = (HashMap)request.getAttribute("widgetProps");
/*      */       
/* 3356 */       String selectiontype = fm.getSelecttionType();
/* 3357 */       String monitortype = fm.getSelectedMonitorType();
/* 3358 */       String selectedattributeid = fm.getSelectedAttribute();
/* 3359 */       String widgetid = request.getParameter("widgetid");
/* 3360 */       ArrayList applications = null;
/* 3361 */       String resourcetypecondition = getResourceTypesForComplexTypeCondition(monitortype, "TYPE");
/*      */       
/*      */ 
/*      */ 
/* 3365 */       Vector resIds_vector = new Vector();
/*      */       
/* 3367 */       String eumConditionStr = com.adventnet.appmanager.util.Constants.getEUMWidgetCondition(widgettype, "AM_ManagedObject.RESOURCEID", "AND");
/* 3368 */       if (widgettype == 2) {
/* 3369 */         String attribute_level = "1";
/* 3370 */         String resourcetype = "";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3375 */         ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(selectedattributeid);
/* 3376 */         if (attrExtTableDetails != null) {
/* 3377 */           attribute_level = (String)attrExtTableDetails.get(10);
/* 3378 */           resourcetype = (String)attrExtTableDetails.get(6);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3383 */         String monitorsQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */         
/* 3385 */         if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */         {
/* 3387 */           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 3388 */             resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser(), request, widgetProps);
/* 3389 */             monitorsQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " and  " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           }
/* 3391 */           else if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 3392 */             String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 3393 */             monitorsQuery = "select AM_ManagedObject.RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and  " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           } else {
/* 3395 */             resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 3396 */             monitorsQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " and  " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3403 */         if (attribute_level.equals("1"))
/*      */         {
/* 3405 */           ArrayList availMonitors = this.mo.getRows(monitorsQuery);
/* 3406 */           fm.setAvailMonitors(availMonitors);
/*      */         }
/* 3408 */         else if (attribute_level.equals("2"))
/*      */         {
/* 3410 */           ArrayList firstlevelresids = new ArrayList();
/* 3411 */           rs = AMConnectionPool.executeQueryStmt(monitorsQuery);
/* 3412 */           HashMap<String, String> map = new HashMap();
/* 3413 */           while (rs.next()) {
/* 3414 */             String resId = rs.getString("RESOURCEID");
/* 3415 */             firstlevelresids.add(resId);
/* 3416 */             map.put(resId, rs.getString("DISPLAYNAME"));
/*      */           }
/* 3418 */           rs.close();
/* 3419 */           String secondlevelquery = "select RESOURCEID,DISPLAYNAME,PARENTID from AM_PARENTCHILDMAPPER inner join  AM_ManagedObject  on RESOURCEID=CHILDID and TYPE in ('" + resourcetype + "') where " + ManagedApplication.getCondition("PARENTID", firstlevelresids);
/* 3420 */           ArrayList availMonitors = this.mo.getRows(secondlevelquery);
/*      */           
/*      */ 
/* 3423 */           Iterator<ArrayList> iterator = availMonitors.iterator();
/* 3424 */           while (iterator.hasNext()) {
/* 3425 */             ArrayList valList = (ArrayList)iterator.next();
/* 3426 */             String name = "" + valList.get(1);
/* 3427 */             valList.set(1, name.concat("_").concat((String)map.get(valList.get(2))));
/*      */           }
/*      */           
/* 3430 */           fm.setAvailMonitors(availMonitors);
/* 3431 */           request.setAttribute("secondlevelattribute", Boolean.valueOf(true));
/*      */           
/* 3433 */           AMLog.info("MyPageAction.getResourceForSelectionType() secondlevelquery : " + secondlevelquery + " availMonitors : " + availMonitors);
/*      */         }
/*      */         
/* 3436 */         if ("true".equalsIgnoreCase(request.getParameter("editscriptwidget"))) {
/* 3437 */           request.setAttribute("childParentid", request.getParameter("selectedMonitor"));
/*      */         }
/* 3439 */         request.setAttribute("related_action", "selectSingleMonitor");
/* 3440 */       } else if (selectiontype.equals("GROUP"))
/*      */       {
/*      */ 
/*      */ 
/* 3444 */         if ((ClientDBUtil.isPrivilegedUser(request)) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */         {
/* 3446 */           applications = AlarmUtil.getApplicationsForOwner(request.getRemoteUser(), request);
/*      */         }
/* 3448 */         else if (EnterpriseUtil.isIt360MSPEdition()) {
/* 3449 */           applications = AlarmUtil.getConfiguredGroups(request);
/*      */         } else {
/* 3451 */           applications = AlarmUtil.getConfiguredGroups();
/*      */         }
/*      */         
/*      */ 
/* 3455 */         if (applications != null) {
/* 3456 */           request.setAttribute("applications1", applications);
/*      */         }
/* 3458 */         String selectedMultiMonitorsQuery = "select RESOURCEID from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid;
/* 3459 */         rs = AMConnectionPool.executeQueryStmt(selectedMultiMonitorsQuery);
/* 3460 */         if (rs.next()) {
/* 3461 */           fm.setSelectedMG(rs.getString("RESOURCEID"));
/*      */         }
/* 3463 */         rs.close();
/* 3464 */         request.setAttribute("related_action", "monitorgroups");
/* 3465 */       } else if (selectiontype.equals("CUSTOM"))
/*      */       {
/* 3467 */         request.setAttribute("related_action", "availMutliMonitors");
/* 3468 */         String attribute_level = "1";
/* 3469 */         String secondleveltype = monitortype;
/*      */         
/* 3471 */         if ((selectedattributeid != null) && (!selectedattributeid.equals("")))
/*      */         {
/* 3473 */           ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(selectedattributeid);
/*      */           
/* 3475 */           attribute_level = (String)attrExtTableDetails.get(10);
/* 3476 */           secondleveltype = (String)attrExtTableDetails.get(6);
/*      */         }
/*      */         
/* 3479 */         String monitorsQuery = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */         
/* 3481 */         if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */         {
/* 3483 */           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 3484 */             resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser(), request, widgetProps);
/* 3485 */             monitorsQuery = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " and " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           }
/* 3487 */           else if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 3488 */             String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 3489 */             monitorsQuery = "select AM_ManagedObject.RESOURCEID, DISPLAYNAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           } else {
/* 3491 */             resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 3492 */             monitorsQuery = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " and " + resourcetypecondition + eumConditionStr + " order by DISPLAYNAME";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3499 */         if (attribute_level.equals("2"))
/*      */         {
/* 3501 */           ArrayList firstlevelresids = new ArrayList();
/* 3502 */           rs = AMConnectionPool.executeQueryStmt(monitorsQuery);
/* 3503 */           while (rs.next()) {
/* 3504 */             firstlevelresids.add(rs.getString("RESOURCEID"));
/*      */           }
/* 3506 */           rs.close();
/* 3507 */           String secondlevelquery = "select RESOURCEID,DISPLAYNAME from AM_PARENTCHILDMAPPER inner join   AM_ManagedObject  on RESOURCEID=CHILDID and TYPE='" + secondleveltype + "' where " + ManagedApplication.getCondition("PARENTID", firstlevelresids);
/* 3508 */           ArrayList availMonitors = this.mo.getRows(secondlevelquery);
/* 3509 */           fm.setAvailMultiMonitors(availMonitors);
/*      */         }
/*      */         else
/*      */         {
/* 3513 */           ArrayList availMonitors = this.mo.getRows(monitorsQuery);
/* 3514 */           fm.setAvailMultiMonitors(availMonitors);
/*      */         }
/*      */       }
/*      */       else {
/* 3518 */         return new ActionForward("/myFields.do?method=getFieldValues&aliasName=" + selectiontype.substring(0, selectiontype.indexOf("$")) + "&forBulkAssign=false");
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3522 */       ex.printStackTrace();
/*      */     }
/* 3524 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList getRealatedattribs(String attributeid, String selectedResourceid)
/*      */   {
/* 3532 */     ResultSet rs = null;
/* 3533 */     AMConnectionPool pool = new AMConnectionPool();
/* 3534 */     ArrayList relatedattributes = new ArrayList();
/* 3535 */     String healthid = null;
/* 3536 */     String availabilityid = null;
/*      */     try
/*      */     {
/* 3539 */       ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(attributeid);
/* 3540 */       if ((attrExtTableDetails != null) && (!attrExtTableDetails.isEmpty()))
/*      */       {
/* 3542 */         String secondleveltype = (String)attrExtTableDetails.get(6);
/* 3543 */         String level = (String)attrExtTableDetails.get(10);
/* 3544 */         String rcaTable = "";
/* 3545 */         if ((level.equals("1")) && (selectedResourceid != null)) {
/* 3546 */           rcaTable = " and AttDependency.CHILDID not in (select ATTRIBUTEID from AM_Script_Resource_Attributes_Mapper,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_Script_Resource_Attributes_Mapper.RESOURCEID and AM_ManagedObject.type='" + secondleveltype + "' and AM_ManagedObject.RESOURCEID <>" + selectedResourceid + ")";
/*      */         }
/* 3548 */         String query1 = "select AttDependency.PARENTID,AM_ATTRIBUTES.TYPE,AttDependency.CHILDID as attribid,AM_ATTRIBUTES.DISPLAYNAME,COALESCE(AM_ATTRIBUTES.UNITS,'') as unit from AM_ATTRIBUTESDEPENDENCY  left outer join  AM_ATTRIBUTESDEPENDENCY as AttDependency on AttDependency.PARENTID=AM_ATTRIBUTESDEPENDENCY .PARENTID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=AttDependency.CHILDID and AM_ATTRIBUTES.TYPE in (0,1,3,8) inner join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AttDependency.CHILDID where AM_ATTRIBUTESDEPENDENCY.CHILDID=" + attributeid + "  and AttDependency.CHILDID <>" + attributeid + " and AttDependency.CHILDID not in (3507) " + rcaTable + " and AM_ATTRIBUTES.ATTRIBUTE <> 'EventLogRuleMatched' and AM_ATTRIBUTES_EXT.REPORTS_ENABLED < 2 and RESOURCETYPE='" + secondleveltype + "'";
/* 3549 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 3550 */         while (rs.next()) {
/* 3551 */           healthid = rs.getString("PARENTID");
/* 3552 */           int type = rs.getInt("TYPE");
/* 3553 */           if (type == 1) {
/* 3554 */             availabilityid = rs.getString("attribid");
/*      */ 
/*      */           }
/* 3557 */           else if ((!"OpenStack".equalsIgnoreCase(secondleveltype)) || (!"ID".equalsIgnoreCase(rs.getString("DISPLAYNAME"))))
/*      */           {
/*      */ 
/* 3560 */             String units = rs.getString("unit");
/* 3561 */             if (!units.equals(""))
/*      */             {
/* 3563 */               units = "(" + FormatUtil.getString(units) + ")";
/*      */             }
/* 3565 */             ArrayList singlerow = new ArrayList();
/* 3566 */             singlerow.add(rs.getString("attribid"));
/* 3567 */             singlerow.add(FormatUtil.getString(rs.getString("DISPLAYNAME")));
/* 3568 */             singlerow.add(units);
/* 3569 */             relatedattributes.add(singlerow);
/*      */           } }
/* 3571 */         if (healthid != null) {
/* 3572 */           ArrayList singlerow = new ArrayList();
/* 3573 */           singlerow.add(healthid);
/* 3574 */           singlerow.add(FormatUtil.getString("Health"));
/* 3575 */           singlerow.add("");
/* 3576 */           relatedattributes.add(singlerow);
/*      */         }
/* 3578 */         if (availabilityid != null) {
/* 3579 */           ArrayList singlerow = new ArrayList();
/* 3580 */           singlerow.add(availabilityid);
/* 3581 */           singlerow.add(FormatUtil.getString("Availability"));
/* 3582 */           singlerow.add("");
/* 3583 */           relatedattributes.add(singlerow);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 3587 */       ex.printStackTrace();
/*      */     }
/* 3589 */     return relatedattributes;
/*      */   }
/*      */   
/*      */   private ArrayList getRealatedattribs(String attributeid)
/*      */   {
/* 3594 */     return getRealatedattribs(attributeid, null);
/*      */   }
/*      */   
/*      */   public ActionForward getRecentAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3599 */     String widgetid = request.getParameter("widgetid");
/* 3600 */     String pageid = request.getParameter("pageid");
/*      */     try {
/* 3602 */       HashMap widgetProps = (HashMap)request.getAttribute("widgetProps");
/* 3603 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 3604 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 3605 */       String topn = (String)widgetProps.get("toporBottom_N");
/* 3606 */       int topn_int = Integer.parseInt(topn);
/* 3607 */       ResourceNames resourcenames = new ResourceNames();
/* 3608 */       ArrayList<ArrayList> alarmOutputData = new ArrayList();
/* 3609 */       ArrayList<String> alarmHeader = new ArrayList();
/* 3610 */       ArrayList recentAlarmsList = null;
/* 3611 */       ArrayList<String> associatedTypes = (ArrayList)widgetProps.get("associatedTypes");
/*      */       
/* 3613 */       String externalProduct = (String)widgetProps.get("EXTPROD");
/* 3614 */       String associatedTypeVals = "";
/* 3615 */       if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (externalProduct != null) && (!externalProduct.trim().equals("")))
/*      */       {
/* 3617 */         if (associatedTypes.size() != 0) {
/* 3618 */           associatedTypeVals = "IN (";
/* 3619 */           for (int i = 0; i < associatedTypes.size(); i++) {
/* 3620 */             if (i != associatedTypes.size() - 1) {
/* 3621 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "',";
/*      */             } else {
/* 3623 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "')";
/*      */             }
/*      */           }
/*      */         }
/* 3627 */         if (associatedTypeVals.trim().equalsIgnoreCase("")) {
/* 3628 */           associatedTypeVals = "ALL";
/*      */         }
/*      */         
/* 3631 */         if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition))
/*      */         {
/* 3633 */           recentAlarmsList = AlarmUtil.getRecentExtProdAlertsForOwner(request, topn_int, "desc", widgetProps, associatedTypeVals, externalProduct);
/*      */         } else {
/* 3635 */           recentAlarmsList = AlarmUtil.getRecentExtProdAlerts(topn_int, associatedTypeVals, externalProduct);
/*      */         }
/*      */       } else {
/* 3638 */         associatedTypeVals = "ALL";
/*      */         
/*      */ 
/* 3641 */         if (associatedTypes.size() != 0) {
/* 3642 */           associatedTypeVals = "IN (";
/* 3643 */           for (int i = 0; i < associatedTypes.size(); i++) {
/* 3644 */             if (i != associatedTypes.size() - 1) {
/* 3645 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "',";
/*      */             } else {
/* 3647 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "')";
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 3652 */         if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition))
/*      */         {
/* 3654 */           recentAlarmsList = AlarmUtil.getRecentAlertsForOwner(request, topn_int, "desc", widgetProps, associatedTypeVals);
/*      */         } else {
/* 3656 */           recentAlarmsList = AlarmUtil.getRecentAlerts(topn_int, associatedTypeVals);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3663 */       alarmHeader.add(FormatUtil.getString("am.mypage.healthstatus.text"));
/* 3664 */       alarmHeader.add(FormatUtil.getString(""));
/* 3665 */       alarmHeader.add(FormatUtil.getString("webclient.fault.event.text"));
/* 3666 */       alarmHeader.add(FormatUtil.getString("am.mypage.monitorname.text"));
/* 3667 */       alarmHeader.add(FormatUtil.getString("am.mypage.monitortype.text"));
/* 3668 */       alarmHeader.add(FormatUtil.getString("am.mypage.alerttime.text"));
/* 3669 */       alarmHeader.add(FormatUtil.getString("webclient.fault.alarm.who"));
/* 3670 */       controlHeader.add("healthicon");
/* 3671 */       controlHeader.add("alarmannotation");
/* 3672 */       controlHeader.add("healthshortmessage");
/* 3673 */       controlHeader.add("monitorLink");
/* 3674 */       controlHeader.add("value");
/* 3675 */       controlHeader.add("formatdate");
/* 3676 */       controlHeader.add("alarmtechnician");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3682 */       for (int i = 0; i < recentAlarmsList.size(); i++) {
/* 3683 */         ArrayList singlealert = (ArrayList)recentAlarmsList.get(i);
/* 3684 */         ArrayList singlerow = new ArrayList();
/* 3685 */         String resid = (String)singlealert.get(6);
/* 3686 */         String alertmessage = (String)singlealert.get(3);
/* 3687 */         String resname = resourcenames.getResourceName(resid);
/* 3688 */         String resourcetype1 = (String)singlealert.get(9);
/* 3689 */         String annotation = (String)singlealert.get(10);
/* 3690 */         String username = (String)singlealert.get(11);
/* 3691 */         String alaramid = (String)singlealert.get(2);
/* 3692 */         HashMap rowInfo = new HashMap();
/* 3693 */         if (resourcetype1.equals("Monitor Group")) {
/* 3694 */           resourcetype1 = resourcenames.getGroupType(resid);
/* 3695 */           rowInfo.put("rowbelongsto", "HAI");
/*      */         }
/* 3697 */         if (resourcetype1.equals("Windows")) {
/* 3698 */           resourcetype1 = (String)singlealert.get(7);
/*      */         }
/* 3700 */         if ((username == null) || (username.equalsIgnoreCase("null"))) {
/* 3701 */           username = "";
/*      */         }
/*      */         
/* 3704 */         String attributeid = (String)singlealert.get(0);
/* 3705 */         String severity = (String)singlealert.get(1);
/* 3706 */         String date = (String)singlealert.get(5);
/* 3707 */         String formattedDate = date;
/*      */         try {
/* 3709 */           formattedDate = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(formattedDate)));
/*      */         } catch (Exception ex) {
/* 3711 */           formattedDate = date;
/*      */         }
/* 3713 */         rowInfo.put("resourceid", resid);
/* 3714 */         rowInfo.put("rowResourceid", resid);
/* 3715 */         rowInfo.put("resourceidforalerts", resid);
/* 3716 */         rowInfo.put("alertmessage", alertmessage);
/* 3717 */         rowInfo.put("healthid", attributeid);
/* 3718 */         rowInfo.put("displayname", resname);
/* 3719 */         rowInfo.put("alarmid", alaramid);
/* 3720 */         rowInfo.put("alarmtime", formattedDate);
/* 3721 */         metaInfoInOrder.add(rowInfo);
/* 3722 */         singlerow.add(severity);
/* 3723 */         singlerow.add(annotation);
/* 3724 */         singlerow.add(alertmessage);
/* 3725 */         singlerow.add(resname);
/* 3726 */         singlerow.add(FormatUtil.getString(resourcetype1));
/* 3727 */         singlerow.add(date);
/* 3728 */         singlerow.add(username);
/* 3729 */         alarmOutputData.add(singlerow);
/*      */       }
/* 3731 */       request.setAttribute("metricdata", alarmOutputData);
/* 3732 */       request.setAttribute("outputheader", alarmHeader);
/* 3733 */       request.setAttribute("controlHeader", controlHeader);
/* 3734 */       request.setAttribute("metaInfoInOrder", metaInfoInOrder);
/*      */       
/* 3736 */       if ((alarmOutputData == null) || (alarmOutputData.size() == 0)) {
/* 3737 */         request.setAttribute("custommessage", FormatUtil.getString("am.mypage.noalerts.message.text"));
/*      */       }
/*      */     } catch (Exception ex) {
/* 3740 */       ex.printStackTrace();
/*      */     }
/* 3742 */     return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */   }
/*      */   
/*      */   public ActionForward getBusinessViewWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3747 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try
/*      */     {
/* 3750 */       Integer.parseInt(request.getParameter("widgetid"));
/*      */     } catch (Exception e) {
/* 3752 */       AMLog.debug("MyPageAction : getBusinessViewWidget. Incorrect values obtained for widgetid : " + request.getParameter("widgetid") + ". Error : " + e.getMessage());
/* 3753 */       return new ActionForward("/applications.do", true);
/*      */     }
/* 3755 */     String widgetid = request.getParameter("widgetid");
/* 3756 */     String haid = "0";
/* 3757 */     String viewid = "1";
/* 3758 */     ResultSet rs2 = null;
/*      */     try {
/* 3760 */       if (request.isUserInRole("OPERATOR"))
/*      */       {
/* 3762 */         request.setAttribute("errormessage", FormatUtil.getString("am.mypage.businessview.operator.err.txt"));
/* 3763 */         return new ActionForward("/jsp/MyPage_Widget.jsp");
/*      */       }
/* 3765 */       String monitorsquery = "select AM_MYPAGE_WIDGET_FLASHVIEWS.VIEWID,HAID from AM_MYPAGE_WIDGET_FLASHVIEWS  inner join AM_MONITORGROUP_FLASHVIEWCONFIG on  AM_MONITORGROUP_FLASHVIEWCONFIG.VIEWID=AM_MYPAGE_WIDGET_FLASHVIEWS.VIEWID where  WIDGETID=" + widgetid;
/* 3766 */       rs2 = AMConnectionPool.executeQueryStmt(monitorsquery);
/* 3767 */       if (rs2.next()) {
/* 3768 */         haid = rs2.getString("HAID");
/* 3769 */         viewid = rs2.getString("VIEWID");
/* 3770 */         if ((!rs2.wasNull()) && 
/* 3771 */           (!viewid.equals("-1"))) {
/* 3772 */           request.setAttribute("haid", haid);
/* 3773 */           request.setAttribute("viewid", viewid);
/*      */         }
/*      */         
/* 3776 */         rs2.close();
/*      */       } else {
/* 3778 */         rs2.close();
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3782 */       ex.printStackTrace();
/*      */     }
/* 3784 */     request.setAttribute("related_action", "showbusinessview");
/* 3785 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward getWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 3793 */       Integer.parseInt(request.getParameter("widgetid"));
/* 3794 */       Integer.parseInt(request.getParameter("pageid"));
/*      */     } catch (Exception e) {
/* 3796 */       AMLog.debug("MyPageAction : getWidget. Incorrect values obtained for widgetid : " + request.getParameter("widgetid") + " or pageid : " + request.getParameter("pageid") + ". Error : " + e.getMessage());
/* 3797 */       return new ActionForward("/applications.do", true);
/*      */     }
/* 3799 */     String widgetid = request.getParameter("widgetid");
/*      */     
/* 3801 */     String pageid = request.getParameter("pageid");
/*      */     
/* 3803 */     if ((pageid != null) && (pageid.indexOf("?") != -1)) {
/* 3804 */       pageid = pageid.substring(0, pageid.indexOf("?"));
/*      */     }
/*      */     
/* 3807 */     String template_resid = request.getParameter("template_resid");
/*      */     try {
/* 3809 */       MyPageForm fm = (MyPageForm)form;
/* 3810 */       HashMap widgetProps = new HashMap();
/* 3811 */       ResultSet rs = null;
/* 3812 */       ResultSet rs1 = null;
/* 3813 */       ResultSet rs2 = null;
/* 3814 */       String period = "20";
/* 3815 */       String baseAttributeid = "";
/* 3816 */       String widgettype = "1";
/* 3817 */       String resourcetype = "";
/* 3818 */       String selectmonitortype = "";
/* 3819 */       String widgeturl = "";
/* 3820 */       String widgetHeight = "500";
/* 3821 */       String toporbottomn = "1000";
/* 3822 */       String widgetName = "Widget Name";
/* 3823 */       String alertstatusForMetrics = "0";
/* 3824 */       String includeSubGroups = "0";
/*      */       
/*      */ 
/* 3827 */       String baseattributeUnits = "";
/*      */       
/* 3829 */       if ((pageid != null) && (!pageid.equals("null")))
/*      */       {
/* 3831 */         widgetProps.put("pageid", pageid);
/*      */       }
/* 3833 */       if ((template_resid != null) && (!template_resid.equals("null")))
/*      */       {
/* 3835 */         widgetProps.put("template_resid", template_resid);
/*      */       }
/*      */       
/* 3838 */       if ((request.getParameter("columns") != null) && (com.adventnet.appmanager.util.Constants.isIntegerNumber(request.getParameter("columns"))))
/*      */       {
/* 3840 */         fm.setNumberOfColumns(Integer.parseInt(request.getParameter("columns")));
/*      */       }
/*      */       
/* 3843 */       if (ClientDBUtil.isPrivilegedUser(request))
/*      */       {
/* 3845 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 3846 */           widgetProps.put("loginUserid", com.adventnet.appmanager.util.Constants.getLoginUserid(request));
/*      */         }
/* 3848 */         widgetProps.put("isoperator", "true");
/* 3849 */         widgetProps.put("user", request.getRemoteUser());
/*      */       }
/* 3851 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 3853 */         widgetProps.put("user", request.getRemoteUser());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3858 */       if (EnterpriseUtil.isIt360MSPEdition()) {
/* 3859 */         Properties custProp = CustomerManagementAPI.getCustProp(request);
/* 3860 */         if ((custProp != null) && (custProp.size() > 0)) {
/* 3861 */           String custName = custProp.keys().nextElement().toString();
/* 3862 */           String custId = custProp.getProperty(custName);
/* 3863 */           widgetProps.put("custName", custName);
/* 3864 */           widgetProps.put("custId", custId);
/*      */         }
/* 3866 */         Properties siteProp = CustomerManagementAPI.getSiteProp(request);
/* 3867 */         if ((siteProp != null) && (siteProp.size() > 0)) {
/* 3868 */           String siteName = siteProp.keys().nextElement().toString();
/* 3869 */           String siteId = siteProp.getProperty(siteName);
/* 3870 */           widgetProps.put("siteName", siteName);
/* 3871 */           widgetProps.put("siteId", siteId);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3876 */       AMConnectionPool pool = new AMConnectionPool();
/*      */       
/* 3878 */       ArrayList relatedAttributeList = new ArrayList();
/* 3879 */       ArrayList selectedMonitors = new ArrayList();
/* 3880 */       ArrayList<String> allAttributes = new ArrayList();
/* 3881 */       HashMap<String, String> residDisplayNames = new HashMap();
/* 3882 */       HashMap<String, String> sqlhostresidMapping = new HashMap();
/* 3883 */       HashMap<String, String> residTypeMapping = new HashMap();
/* 3884 */       ArrayList<String> controlHeader = new ArrayList();
/* 3885 */       ArrayList<HashMap> metaInfoInOrder = new ArrayList();
/*      */       
/* 3887 */       ArrayList<String> headerAttributeIds = new ArrayList();
/*      */       
/* 3889 */       widgetProps.put("selectedMonitors", selectedMonitors);
/* 3890 */       widgetProps.put("residDisplayNames", residDisplayNames);
/* 3891 */       widgetProps.put("controlHeader", controlHeader);
/* 3892 */       widgetProps.put("metaInfoInOrder", metaInfoInOrder);
/* 3893 */       widgetProps.put("residTypeMapping", residTypeMapping);
/* 3894 */       widgetProps.put("sqlhostresidMapping", sqlhostresidMapping);
/*      */       
/* 3896 */       String wigdetInfoQuery = "select LIMIT_N,REPORTPERIOD,WIDGETTYPE,RESOURCETYPE,LIMITTYPE,SELECTMONITORTYPE,WIDGETURL,HEIGHT,DISPLAYNAME,UTILCOLUMN1,DESCRIPTION,UTILCOLUMN2,UTILCOLUMN3,MAXSCALE,MINSCALE,SUBGROUP,FILTEROPTION,BUSINESSRULE  from  AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid;
/*      */       
/* 3898 */       rs = AMConnectionPool.executeQueryStmt(wigdetInfoQuery);
/* 3899 */       if (rs.next()) {
/* 3900 */         toporbottomn = rs.getString("LIMIT_N");
/* 3901 */         widgetProps.put("toporBottom_N", toporbottomn);
/* 3902 */         widgetProps.put("toporBottom", rs.getString("LIMITTYPE"));
/* 3903 */         period = rs.getString("REPORTPERIOD");
/* 3904 */         widgettype = rs.getString("WIDGETTYPE");
/* 3905 */         resourcetype = rs.getString("RESOURCETYPE");
/* 3906 */         selectmonitortype = rs.getString("SELECTMONITORTYPE");
/* 3907 */         widgeturl = rs.getString("WIDGETURL");
/* 3908 */         widgetHeight = rs.getString("HEIGHT");
/* 3909 */         widgetName = rs.getString("DISPLAYNAME");
/* 3910 */         alertstatusForMetrics = rs.getString("UTILCOLUMN1");
/* 3911 */         includeSubGroups = rs.getString("SUBGROUP");
/* 3912 */         widgetProps.put("filterbyname", rs.getString("FILTEROPTION"));
/* 3913 */         widgetProps.put("period", period);
/* 3914 */         widgetProps.put("widgetid", widgetid);
/* 3915 */         widgetProps.put("resourcetype", resourcetype);
/* 3916 */         widgetProps.put("widgettype", widgettype);
/* 3917 */         widgetProps.put("selectmonitortype", selectmonitortype);
/* 3918 */         widgetProps.put("widgeturl", widgeturl);
/* 3919 */         widgetProps.put("widgetname", widgetName);
/* 3920 */         widgetProps.put("alertstatusForMetrics", alertstatusForMetrics);
/* 3921 */         widgetProps.put("widgetdescription", rs.getString("DESCRIPTION"));
/* 3922 */         widgetProps.put("subgroupoption", includeSubGroups);
/* 3923 */         request.setAttribute("widgettype", widgettype);
/* 3924 */         request.setAttribute("widgettypename", DashboardUtil.getWidgetTypeName(widgettype));
/* 3925 */         request.setAttribute("resourceType", resourcetype);
/* 3926 */         request.setAttribute("widgetdescription", rs.getString("DESCRIPTION"));
/* 3927 */         String businessperiod = rs.getString("BUSINESSRULE");
/* 3928 */         if (businessperiod != null) {
/* 3929 */           widgetProps.put("businessperiod", businessperiod);
/* 3930 */           request.setAttribute("businessperiod", businessperiod);
/*      */         }
/*      */         
/* 3933 */         if (!widgettype.trim().equals("201")) {
/* 3934 */           request.setAttribute("widgetgraphscale", rs.getString("UTILCOLUMN3"));
/* 3935 */           request.setAttribute("additionalmetricgraph", rs.getString("UTILCOLUMN2"));
/*      */         } else {
/* 3937 */           widgetProps.put("todaysavailability", rs.getString("UTILCOLUMN3"));
/* 3938 */           if (EnterpriseUtil.isAdminServer()) {
/* 3939 */             widgetProps.put("monitorsStatus", rs.getString("UTILCOLUMN2"));
/*      */           } else {
/* 3941 */             widgetProps.put("monitorsStatus", "1");
/*      */           }
/*      */         }
/*      */         
/* 3945 */         request.setAttribute("minimumscale", rs.getString("MINSCALE"));
/* 3946 */         request.setAttribute("maximumscale", rs.getString("MAXSCALE"));
/*      */       }
/*      */       
/* 3949 */       rs.close();
/*      */       
/*      */ 
/* 3952 */       if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (DashboardUtil.externalProdWidgets != null) && (DashboardUtil.externalProdWidgets.contains(widgettype)))
/*      */       {
/* 3954 */         if ((DashboardUtil.opmanWidgets != null) && (DashboardUtil.opmanWidgets.contains(widgettype))) {
/* 3955 */           widgetProps.put("EXTPROD", "OPMANAGER");
/*      */         }
/* 3957 */         widgetProps.put("associatedTypes", getSelectedTypesForExtProductWidget(widgetid));
/*      */       }
/*      */       
/* 3960 */       if ((widgettype.equals("51")) || (widgettype.equals("104"))) {
/* 3961 */         ArrayList<String> associatedTypes = new ArrayList();
/* 3962 */         String query = "select ASSOCIATEDTYPES from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/* 3963 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3964 */         while (rs.next()) {
/* 3965 */           associatedTypes.add(rs.getString(1));
/*      */         }
/* 3967 */         rs.close();
/* 3968 */         widgetProps.put("associatedTypes", associatedTypes);
/*      */       }
/*      */       
/* 3971 */       if (widgettype.equals("21"))
/*      */       {
/* 3973 */         return getBusinessViewWidget(mapping, form, request, response); }
/* 3974 */       if ((widgettype.equals("51")) || (widgettype.equals("503")))
/*      */       {
/* 3976 */         request.setAttribute("widgetProps", widgetProps);
/* 3977 */         return getRecentAlerts(mapping, form, request, response); }
/* 3978 */       if ((widgettype.equals("105")) || (widgettype.equals("202")))
/*      */       {
/* 3980 */         request.setAttribute("widgetProps", widgetProps);
/* 3981 */         if (widgettype.equals("202")) {
/* 3982 */           resourcetype = "HAI";
/*      */         }
/* 3984 */         if ((resourcetype == null) || (resourcetype.equalsIgnoreCase("NULL")) || (resourcetype.equals("")))
/*      */         {
/* 3986 */           return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */         }
/* 3988 */         if ((resourcetype.indexOf("Windows") != -1) && (!resourcetype.equals("Windows Cluster")))
/*      */         {
/* 3990 */           resourcetype = "Windows";
/* 3991 */         } else if (resourcetype.indexOf("$ComplexType_Servers") != -1)
/*      */         {
/* 3993 */           resourcetype = "SYS";
/*      */         }
/* 3995 */         if (period.equals("-30"))
/*      */         {
/* 3997 */           period = "2";
/*      */         } else {
/* 3999 */           period = "1";
/*      */         }
/* 4001 */         if (request.getParameter("period") != null)
/*      */         {
/* 4003 */           period = request.getParameter("period");
/*      */         }
/* 4005 */         return new ActionForward("/dashboard.do?method=generateAvailabilityHistory&includeInWidget=true&type=" + resourcetype + "&period=" + period + "&widgetid=" + widgetid + "&selecttionType=" + selectmonitortype + "&subgroup=" + includeSubGroups); }
/* 4006 */       if ((widgettype.equals("106")) || (widgettype.equals("203")))
/*      */       {
/* 4008 */         request.setAttribute("widgetProps", widgetProps);
/* 4009 */         if (widgettype.equals("203")) {
/* 4010 */           resourcetype = "HAI";
/*      */         }
/* 4012 */         if ((resourcetype == null) || (resourcetype.equalsIgnoreCase("NULL")) || (resourcetype.equals("")))
/*      */         {
/* 4014 */           return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */         }
/* 4016 */         if ((resourcetype.indexOf("Windows") != -1) && (!resourcetype.equals("Windows Cluster")))
/*      */         {
/* 4018 */           resourcetype = "Windows";
/* 4019 */         } else if (resourcetype.indexOf("$ComplexType_Servers") != -1)
/*      */         {
/* 4021 */           resourcetype = "SYS";
/*      */         }
/* 4023 */         if (period.equals("-30"))
/*      */         {
/* 4025 */           period = "2";
/*      */         } else {
/* 4027 */           period = "1";
/*      */         }
/* 4029 */         if (request.getParameter("period") != null)
/*      */         {
/* 4031 */           period = request.getParameter("period");
/*      */         }
/*      */         
/* 4034 */         return new ActionForward("/dashboard.do?method=generateHealthHistory&includeInWidget=true&type=" + resourcetype + "&period=" + period + "&widgetid=" + widgetid + "&selecttionType=" + selectmonitortype + "&subgroup=" + includeSubGroups); }
/* 4035 */       if (widgettype.equals("300"))
/*      */       {
/* 4037 */         String help = FormatUtil.getString("am.mypage.widgets.helpmessage.text");
/* 4038 */         request.setAttribute("helpmessage", widgetProps.get("widgetdescription"));
/*      */       }
/*      */       else {
/* 4041 */         if ((widgettype.equals("301")) || (DashboardUtil.externalProdurlWidgets.contains(widgettype)))
/*      */         {
/*      */ 
/*      */ 
/* 4045 */           request.setAttribute("url", widgeturl);
/* 4046 */           request.setAttribute("related_action", "includePage");
/* 4047 */           request.setAttribute("widgetHeight", widgetHeight);
/* 4048 */           return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp"); }
/* 4049 */         if (widgettype.equals("302"))
/*      */         {
/* 4051 */           request.setAttribute("widgetProps", widgetProps);
/* 4052 */           widgetProps.put("headerAttributeIds", headerAttributeIds);
/* 4053 */           return getBookmarksWidget(mapping, form, request, response); }
/* 4054 */         if (widgettype.equals("303"))
/*      */         {
/* 4056 */           return getCustomHtmlText(mapping, form, request, response);
/*      */         }
/*      */         
/* 4059 */         if (widgettype.equals("401"))
/* 4060 */           return getSiteInfo(mapping, form, request, response);
/* 4061 */         if (widgettype.equals("204")) {
/* 4062 */           return getTopologyMapWidget(mapping, form, request, response);
/*      */         }
/*      */       }
/* 4065 */       String query1 = "select ATTRIBUTEID,GRAPHTYPE,ISPRIMARYATTRIBUTE from  AM_MYPAGE_WIDGET_ATTRIBUTES where WIDGETID=" + widgetid;
/* 4066 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4067 */       while (rs1.next()) {
/* 4068 */         String attributeid = rs1.getString("ATTRIBUTEID");
/* 4069 */         String graphtype = rs1.getString("GRAPHTYPE");
/* 4070 */         int isprimary = rs1.getInt("ISPRIMARYATTRIBUTE");
/* 4071 */         ArrayList templist = new ArrayList();
/* 4072 */         if (isprimary == 0) {
/* 4073 */           baseAttributeid = attributeid;
/* 4074 */           allAttributes.add(baseAttributeid);
/* 4075 */           widgetProps.put("baseAttribute", attributeid);
/* 4076 */           request.setAttribute("graphtype", graphtype);
/*      */         } else {
/* 4078 */           relatedAttributeList.add(attributeid);
/*      */         }
/*      */       }
/* 4081 */       widgetProps.put("relatedAttributeList", relatedAttributeList);
/* 4082 */       request.setAttribute("allAttributeIDs", relatedAttributeList);
/* 4083 */       allAttributes.addAll(relatedAttributeList);
/* 4084 */       widgetProps.put("allAttributes", allAttributes);
/* 4085 */       rs1.close();
/*      */       
/* 4087 */       if ((!widgettype.equals("201")) && (!widgettype.equals("104")) && (!widgettype.equals("504")) && (!widgettype.equals("102")) && (!widgettype.equals("103")) && (!widgettype.equals("107")) && (!widgettype.equals("505")) && (widgetProps.get("baseAttribute") == null))
/*      */       {
/* 4089 */         return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */       }
/* 4091 */       boolean gotTableDetails = this.mo.getAttrbuteExtEntries(allAttributes);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4100 */       String monitorSelectionType = "1";
/* 4101 */       HashMap<String, ArrayList<String>> selectedResidattribsMap = new HashMap();
/* 4102 */       if (widgettype.equals("2")) {
/* 4103 */         selectedResidattribsMap = getSelResAttrForAllTypes(widgetid);
/* 4104 */         if (!selectedResidattribsMap.isEmpty())
/*      */         {
/* 4106 */           monitorSelectionType = "2";
/*      */         }
/* 4108 */         widgetProps.put("monitorSelectionType", monitorSelectionType);
/* 4109 */         fm.setMonitorSelectionType(Integer.parseInt(monitorSelectionType));
/* 4110 */         request.setAttribute("monitorSelectionType", monitorSelectionType);
/*      */       }
/*      */       
/* 4113 */       if ((!widgettype.equals("201")) || (!widgettype.equals("202")) || (!widgettype.equals("203"))) {
/* 4114 */         getSelectedMonitors(widgetProps);
/*      */       }
/*      */       
/* 4117 */       if ((!widgettype.equals("104")) && (!widgettype.equals("504")) && (!widgettype.equals("201")) && (selectedMonitors.size() <= 0))
/*      */       {
/* 4119 */         return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4125 */       Hashtable availabilitykeys = (Hashtable)request.getSession().getServletContext().getAttribute("availabilitykeys");
/* 4126 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*      */       
/* 4128 */       widgetProps.put("availabilitykeys", availabilitykeys);
/* 4129 */       widgetProps.put("healthkeys", healthkeys);
/*      */       HashMap widgetData;
/* 4131 */       if ((widgettype.equals("1")) || (widgettype.equals("2")) || (widgettype.equals("3"))) {
/*      */         HashMap widgetData;
/* 4133 */         if (period.equals("20"))
/*      */         {
/* 4135 */           widgetData = gatherMetrics(widgetProps);
/*      */         }
/*      */         else {
/* 4138 */           HashMap widgetData = getReportData(widgetProps);
/* 4139 */           if (widgetData.get("attribslistCheckedforComplextype") != null)
/*      */           {
/* 4141 */             request.setAttribute("attribslistCheckedforComplextype", widgetData.get("attribslistCheckedforComplextype")); }
/*      */         }
/*      */       } else { HashMap widgetData;
/* 4144 */         if (widgettype.equals("102"))
/*      */         {
/* 4146 */           ArrayList<ArrayList> outputData = new ArrayList();
/* 4147 */           widgetProps.put("outputData", outputData);
/* 4148 */           widgetProps.put("appendWhere", "random");
/* 4149 */           widgetProps.put("noLabels", "false");
/* 4150 */           widgetProps.put("headerAttributeIds", headerAttributeIds);
/* 4151 */           headerAttributeIds.add("Monitor Name");
/* 4152 */           controlHeader.add("monitorLink");
/*      */           
/* 4154 */           widgetData = getAvailHealth(widgetProps); } else { HashMap widgetData;
/* 4155 */           if (widgettype.equals("103"))
/*      */           {
/* 4157 */             widgetData = getAvailHealthHistory(widgetProps);
/* 4158 */           } else if (widgettype.equals("4"))
/*      */           {
/* 4160 */             HashMap widgetData = getThresholdBreakers(widgetProps);
/* 4161 */             if (EnterpriseUtil.isAdminServer())
/* 4162 */               request.setAttribute("graphtype", "nograph");
/*      */           } else { HashMap widgetData;
/* 4164 */             if ((widgettype.equals("104")) || (widgettype.equals("504")))
/*      */             {
/* 4166 */               widgetData = getInfrastructureSnapshot(widgetProps);
/*      */             } else {
/* 4168 */               if ((widgettype.equals("107")) || (widgettype.equals("505"))) {
/* 4169 */                 HashMap widgetData = getAvailHealthRCASummary(widgetProps);
/* 4170 */                 request.setAttribute("widgetData", widgetData);
/* 4171 */                 return new ActionForward("/jsp/MyPage_Widget2.jsp?widgetid=" + widgetid + "&pageid=" + pageid); }
/* 4172 */               HashMap widgetData; if (widgettype.equals("201"))
/*      */               {
/* 4174 */                 HttpSession session = request.getSession();
/* 4175 */                 ServletContext ctx = session.getServletContext();
/* 4176 */                 if ((ctx.getAttribute("mgdrilldown") != null) && (ctx.getAttribute("mgdrilldown").equals("true"))) {
/* 4177 */                   widgetProps.put("mgdrilldown", "true");
/*      */                 }
/* 4179 */                 String owner = request.getRemoteUser();
/* 4180 */                 widgetData = getMonitorGroupStatus(widgetProps, owner, request);
/*      */               } else {
/* 4182 */                 widgetData = gatherMetrics(widgetProps);
/* 4183 */                 Properties alert = (Properties)widgetData.get("alert");
/* 4184 */                 request.setAttribute("alert", alert);
/*      */               }
/*      */             } } } }
/* 4187 */       ArrayList<ArrayList> metricdata = (ArrayList)widgetData.get("metricdata");
/* 4188 */       ArrayList<String> outputheader = (ArrayList)widgetData.get("header");
/* 4189 */       ArrayList resourceIds = (ArrayList)widgetData.get("resourceIds");
/*      */       
/* 4191 */       ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(baseAttributeid);
/* 4192 */       String attributelevel = "1";
/* 4193 */       if (attrExtTableDetails != null) {
/* 4194 */         attributelevel = (String)attrExtTableDetails.get(10);
/* 4195 */         baseattributeUnits = (String)attrExtTableDetails.get(8);
/*      */       }
/* 4197 */       if (attributelevel.equals("2"))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 4202 */         String secondlevelrestype = (String)attrExtTableDetails.get(6);
/* 4203 */         if ((widgettype.equals("1")) || (widgettype.equals("2")) || (widgettype.equals("3")) || (widgettype.equals("4")) || ((allAttributes != null) && (allAttributes.size() > 1))) {
/* 4204 */           addMonitorNameForSecondLevelMos(metricdata, resourceIds, outputheader, controlHeader, metaInfoInOrder, residDisplayNames, secondlevelrestype);
/*      */         }
/*      */       }
/*      */       
/* 4208 */       headerAttributeIds = (ArrayList)outputheader.clone();
/* 4209 */       addDisplayNames(metricdata, outputheader, residDisplayNames, metaInfoInOrder, controlHeader);
/*      */       
/* 4211 */       request.setAttribute("metricdata", metricdata);
/* 4212 */       request.setAttribute("outputheader", outputheader);
/* 4213 */       request.setAttribute("headerAttributeIds", headerAttributeIds);
/* 4214 */       request.setAttribute("baseAttributeid", baseAttributeid);
/* 4215 */       request.setAttribute("resourceIds", resourceIds);
/*      */       
/* 4217 */       request.setAttribute("residDisplayNames", residDisplayNames);
/* 4218 */       request.setAttribute("sqlhostresidMapping", sqlhostresidMapping);
/* 4219 */       request.setAttribute("period", period);
/* 4220 */       request.setAttribute("controlHeader", controlHeader);
/*      */       
/* 4222 */       request.setAttribute("metaInfoInOrder", metaInfoInOrder);
/*      */       
/* 4224 */       request.setAttribute("baseattributeUnits", baseattributeUnits);
/*      */       
/*      */ 
/* 4227 */       if ((widgettype.equals("2")) && (monitorSelectionType.equals("2")))
/*      */       {
/*      */ 
/* 4230 */         AMLog.debug(":::selectedResidattribsMap for all types of 2 widget::::" + selectedResidattribsMap);
/* 4231 */         request.setAttribute("selectedResidattribsMap", selectedResidattribsMap);
/* 4232 */         if (widgetData.get("widgetDataForAll") != null) {
/* 4233 */           request.setAttribute("widgetDataForAll", widgetData.get("widgetDataForAll"));
/*      */         }
/*      */       }
/* 4236 */       if (widgetData.get("custommessage") != null)
/*      */       {
/* 4238 */         request.setAttribute("custommessage", widgetData.get("custommessage"));
/*      */       }
/* 4240 */       String grpType = (String)request.getAttribute("graphtype");
/* 4241 */       if ((grpType != null) && (grpType.equals("dial"))) {
/* 4242 */         String attrDisplayName = null;
/* 4243 */         String query = "SELECT DISPLAYNAME FROM AM_ATTRIBUTES WHERE ATTRIBUTEID='" + baseAttributeid + "'";
/* 4244 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 4245 */         if (rs1.next()) {
/* 4246 */           attrDisplayName = rs1.getString(1);
/* 4247 */           request.setAttribute("attributeDisplayName", attrDisplayName);
/*      */         }
/* 4249 */         request.setAttribute("dialGraphAttribute", baseAttributeid);
/* 4250 */         if (("$ComplexType_Windows".equalsIgnoreCase((String)widgetProps.get("resourcetype"))) && (!"2".equals((String)widgetProps.get("attributelevel")))) {
/* 4251 */           String attrQuery = "select ATTRIBUTEID from AM_ATTRIBUTES where DISPLAYNAME='" + attrDisplayName + "' and RESOURCETYPE='" + ((HashMap)widgetProps.get("residTypeMapping")).get(resourceIds.get(0)) + "'";
/* 4252 */           rs1 = AMConnectionPool.executeQueryStmt(attrQuery);
/* 4253 */           if (rs1.next()) {
/* 4254 */             request.setAttribute("dialGraphAttribute", rs1.getString("ATTRIBUTEID"));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4261 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 4265 */     return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */   }
/*      */   
/*      */   private ActionForward getSiteInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4270 */     String pageid = request.getParameter("pageid");
/* 4271 */     String widgetid = request.getParameter("widgetid");
/* 4272 */     if (request.getSession().getAttribute("custProp") != null) {
/* 4273 */       Properties cutomerProps = (Properties)request.getSession().getAttribute("custProp");
/* 4274 */       AMLog.debug("CUTOMERPROPS ==" + cutomerProps);
/* 4275 */       String custName = cutomerProps.keys().nextElement().toString();
/* 4276 */       String custId = cutomerProps.getProperty(custName);
/* 4277 */       ArrayList<ArrayList> alarmOutputData = new ArrayList();
/* 4278 */       ArrayList<HashMap> metaInfoInOrder = new ArrayList();
/*      */       
/* 4280 */       ResultSet rs = null;
/*      */       try {
/* 4282 */         String condn = "";
/* 4283 */         String siteQuery = "select SITEID,SITENAME from SITEINFO where CUSTOMERID=" + custId;
/* 4284 */         condn = CustomerManagementAPI.getSiteCondnForUser(custId, CustomerManagementAPI.getLoggedInUserName(request));
/* 4285 */         Properties siteProp = CustomerManagementAPI.getSiteProp(request);
/* 4286 */         if ((siteProp != null) && (siteProp.size() > 0)) {
/* 4287 */           String siteName = siteProp.keys().nextElement().toString();
/* 4288 */           String siteId = siteProp.getProperty(siteName);
/* 4289 */           siteQuery = "select SITEID,SITENAME from SITEINFO where SITEID=" + siteId;
/* 4290 */           condn = "";
/*      */         }
/*      */         
/* 4293 */         if (!condn.equals("")) {
/* 4294 */           siteQuery = siteQuery + " AND " + condn;
/*      */         }
/* 4296 */         AMLog.debug("siteQuery" + siteQuery);
/* 4297 */         rs = AMConnectionPool.executeQueryStmt(siteQuery);
/* 4298 */         while (rs.next()) {
/* 4299 */           ArrayList<String> list = new ArrayList();
/* 4300 */           HashMap<String, String> siteMap = new HashMap();
/* 4301 */           siteMap.put("resourceid", rs.getString(1));
/* 4302 */           siteMap.put("displayname", rs.getString(2));
/* 4303 */           list.add(rs.getString(2));
/* 4304 */           metaInfoInOrder.add(siteMap);
/* 4305 */           alarmOutputData.add(list);
/*      */         }
/* 4307 */         ArrayList<String> outputheader = new ArrayList();
/* 4308 */         outputheader.add(FormatUtil.getString("it360.sp.customermgmt.sites"));
/* 4309 */         request.setAttribute("outputheader", outputheader);
/* 4310 */         request.setAttribute("metaInfoInOrder", metaInfoInOrder);
/* 4311 */         request.setAttribute("metricdata", alarmOutputData);
/*      */       } catch (Exception ex) {
/* 4313 */         ex.printStackTrace();
/*      */       } finally {
/* 4315 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 4318 */     return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */   }
/*      */   
/*      */   public void getSelectedMonitors(HashMap widgetProps)
/*      */   {
/* 4323 */     String selectmonitortype = (String)widgetProps.get("selectmonitortype");
/* 4324 */     ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 4325 */     HashMap residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/* 4326 */     HashMap sqlhostresidMapping = (HashMap)widgetProps.get("sqlhostresidMapping");
/* 4327 */     HashMap residTypeMapping = (HashMap)widgetProps.get("residTypeMapping");
/* 4328 */     String widgetid = (String)widgetProps.get("widgetid");
/* 4329 */     String resourcetype = (String)widgetProps.get("resourcetype");
/* 4330 */     String baseattributeid = (String)widgetProps.get("baseAttribute");
/* 4331 */     String pageid = (String)widgetProps.get("pageid");
/* 4332 */     String template_resid = (String)widgetProps.get("template_resid");
/* 4333 */     String filterbyname = (String)widgetProps.get("filterbyname");
/* 4334 */     boolean isoperator = false;
/* 4335 */     String isoperatorfromProps = "false";
/* 4336 */     String username = null;
/*      */     
/* 4338 */     ResultSet rs = null;
/* 4339 */     ResultSet rs1 = null;
/* 4340 */     ResultSet rs2 = null;
/* 4341 */     ResultSet rs3 = null;
/* 4342 */     ResultSet rs4 = null;
/* 4343 */     ResultSet rs5 = null;
/* 4344 */     Vector resIds_vector = null;
/* 4345 */     boolean isUserResourceEnabled = false;
/* 4346 */     AMConnectionPool con = new AMConnectionPool();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4351 */     int widgettype = Integer.parseInt((String)widgetProps.get("widgettype"));
/* 4352 */     String eumCondition = com.adventnet.appmanager.util.Constants.getEUMWidgetCondition(widgettype, "RESOURCEID", "AND");
/* 4353 */     if (pageid != null) {
/*      */       try {
/* 4355 */         if (!selectmonitortype.equals("CUSTOM"))
/*      */         {
/* 4357 */           rs = AMConnectionPool.executeQueryStmt("select PAGETYPE from AM_MYPAGES where PAGEID=" + pageid);
/* 4358 */           if (rs.next()) {
/* 4359 */             String pagetype = rs.getString("PAGETYPE");
/* 4360 */             if (pagetype.equals("mgtemplate"))
/*      */             {
/* 4362 */               if (template_resid == null) {
/* 4363 */                 selectmonitortype = "ALL";
/* 4364 */                 widgetProps.put("selectmonitortype", "ALL");
/*      */               } else {
/* 4366 */                 selectmonitortype = "GROUP";
/*      */               }
/*      */             }
/*      */           }
/* 4370 */           rs.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 4373 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 4377 */     String resourcetypecondition = getResourceTypesForComplexTypeCondition(resourcetype, "TYPE");
/*      */     
/*      */     try
/*      */     {
/* 4381 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 4382 */         isUserResourceEnabled = true;
/*      */       }
/* 4384 */       isoperatorfromProps = (String)widgetProps.get("isoperator");
/* 4385 */       if ((isoperatorfromProps != null) && (isoperatorfromProps.equals("true")))
/*      */       {
/* 4387 */         isoperator = true;
/* 4388 */         if (!isUserResourceEnabled) {
/* 4389 */           username = (String)widgetProps.get("user");
/* 4390 */           resIds_vector = ClientDBUtil.getResourceIdentity(username);
/*      */         }
/*      */       }
/*      */       
/* 4394 */       if (EnterpriseUtil.isIt360MSPEdition()) {
/* 4395 */         resIds_vector = ClientDBUtil.getResourceIdentity(username, null, widgetProps);
/*      */       }
/*      */       
/* 4398 */       String attribute_level = "1";
/* 4399 */       String secondleveltype = selectmonitortype;
/* 4400 */       if (baseattributeid != null) {
/* 4401 */         ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(baseattributeid);
/* 4402 */         attribute_level = (String)attrExtTableDetails.get(10);
/* 4403 */         secondleveltype = (String)attrExtTableDetails.get(6);
/* 4404 */         if (attribute_level.equals("2"))
/*      */         {
/* 4406 */           widgetProps.put("attributelevel", "2");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4412 */       if (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) {
/* 4413 */         String widgetTyp = widgetProps.get("widgettype").toString();
/* 4414 */         if (widgetTyp.equals("505")) {
/* 4415 */           String query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ";
/*      */           
/* 4417 */           String extProd = widgetProps.get("EXTPROD").toString();
/* 4418 */           String associatedCategs = getAssociatedTypeValsForExternalProduct(extProd, (ArrayList)widgetProps.get("associatedTypes"));
/*      */           
/* 4420 */           if (associatedCategs.equalsIgnoreCase("ALL"))
/*      */           {
/*      */ 
/* 4423 */             if (extProd.equalsIgnoreCase("OpManager")) {
/* 4424 */               query = query + " where ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */             }
/*      */           }
/* 4427 */           else if ((associatedCategs != null) && (!associatedCategs.trim().equalsIgnoreCase(""))) {
/* 4428 */             query = query + " where ExternalDeviceDetails.CATEGORY " + associatedCategs + " ";
/*      */           }
/* 4430 */           if (query.trim().equals("")) {
/* 4431 */             query = query + " where AM_ManagedResourceType.RESOURCEGROUP in ('NWD','EMO','SAN') and AM_ManagedResourceType.SUBGROUP not like 'OpManager-Interface%' ";
/*      */           }
/*      */           
/* 4434 */           if ((isoperator) || (EnterpriseUtil.isIt360MSPEdition())) {
/* 4435 */             if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 4436 */               String userid = (String)widgetProps.get("loginUserid");
/* 4437 */               query = query + " and  AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + userid + ")";
/*      */             } else {
/* 4439 */               query = query + " and  " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 4444 */           rs2 = AMConnectionPool.executeQueryStmt(query);
/* 4445 */           while (rs2.next()) {
/* 4446 */             selectedMonitors.add(rs2.getString("RESOURCEID"));
/* 4447 */             residDisplayNames.put(rs2.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs2.getString("DISPLAYNAME")));
/* 4448 */             residTypeMapping.put(rs2.getString("RESOURCEID"), rs2.getString("TYPE"));
/*      */           }
/* 4450 */           rs2.close();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4455 */       if (selectmonitortype.equals("GROUP"))
/*      */       {
/* 4457 */         String parentid = "";
/* 4458 */         if (template_resid == null) {
/* 4459 */           String monitorsquery = "select AM_MYPAGE_WIDGET_MONITORS.RESOURCEID from AM_MYPAGE_WIDGET_MONITORS  where WIDGETID=" + widgetid;
/* 4460 */           rs1 = AMConnectionPool.executeQueryStmt(monitorsquery);
/* 4461 */           if (rs1.next()) {
/* 4462 */             parentid = rs1.getString("RESOURCEID");
/*      */           }
/* 4464 */           rs1.close();
/*      */         } else {
/* 4466 */           parentid = template_resid;
/*      */         }
/*      */         
/* 4469 */         Hashtable fullMgTree = getFullMgTree();
/* 4470 */         ArrayList subGroups = new ArrayList();
/* 4471 */         getAllSubGroups(fullMgTree, parentid, subGroups);
/* 4472 */         String EUMmonitorwidgetcondition = ManagedApplication.getCondition("PARENTID", subGroups);
/*      */         
/* 4474 */         if (com.adventnet.appmanager.util.Constants.resourceTypesEUM.contains(resourcetype)) {
/* 4475 */           Vector mgparentid = new Vector();
/* 4476 */           mgparentid.add(parentid);
/* 4477 */           Vector allChildMOs = DBUtil.getChildMonitors(mgparentid);
/* 4478 */           allChildMOs = DBUtil.getChildMonitors(allChildMOs);
/* 4479 */           EUMmonitorwidgetcondition = ManagedApplication.getCondition("RESOURCEID", allChildMOs);
/*      */         }
/*      */         
/* 4482 */         String selectmonitorquery = "select RESOURCEID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER inner join AM_ManagedObject on RESOURCEID=CHILDID and " + resourcetypecondition + eumCondition + "  where " + EUMmonitorwidgetcondition + " order by DISPLAYNAME";
/*      */         
/*      */ 
/* 4485 */         String mgtypequery = "select GROUPTYPE from AM_HOLISTICAPPLICATION where HAID=" + parentid;
/* 4486 */         rs5 = AMConnectionPool.executeQueryStmt(mgtypequery);
/* 4487 */         if (rs5.next()) {
/* 4488 */           String widgetType = (String)widgetProps.get("widgettype");
/* 4489 */           if ((rs5.getInt("GROUPTYPE") == 1010) && ("3".equals(widgetType))) {
/* 4490 */             selectmonitorquery = "select RESOURCEID,DISPLAYNAME,TYPE from AM_ManagedObject where RESOURCEID=" + parentid + eumCondition;
/*      */           }
/*      */         }
/*      */         
/* 4494 */         if (attribute_level.equals("2"))
/*      */         {
/* 4496 */           if (!resourcetype.equals("Generic WMI")) {
/* 4497 */             widgetProps.put("resourcetype", secondleveltype);
/*      */           }
/* 4499 */           ArrayList firstlevelresids = new ArrayList();
/* 4500 */           rs3 = AMConnectionPool.executeQueryStmt(selectmonitorquery);
/* 4501 */           while (rs3.next()) {
/* 4502 */             firstlevelresids.add(rs3.getString("RESOURCEID"));
/*      */           }
/* 4504 */           rs3.close();
/* 4505 */           String secondlevelquery = "select RESOURCEID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER inner join   AM_ManagedObject  on RESOURCEID=CHILDID and TYPE='" + secondleveltype + "'" + eumCondition + "where " + ManagedApplication.getCondition("PARENTID", firstlevelresids) + " order by DISPLAYNAME";
/*      */           
/* 4507 */           rs2 = AMConnectionPool.executeQueryStmt(secondlevelquery);
/* 4508 */           while (rs2.next()) {
/* 4509 */             selectedMonitors.add(rs2.getString("RESOURCEID"));
/* 4510 */             residDisplayNames.put(rs2.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs2.getString("DISPLAYNAME")));
/* 4511 */             residTypeMapping.put(rs2.getString("RESOURCEID"), rs2.getString("TYPE"));
/*      */           }
/* 4513 */           rs2.close();
/*      */         }
/*      */         else {
/* 4516 */           rs4 = AMConnectionPool.executeQueryStmt(selectmonitorquery);
/*      */           
/* 4518 */           while (rs4.next()) {
/* 4519 */             selectedMonitors.add(rs4.getString("RESOURCEID"));
/* 4520 */             residDisplayNames.put(rs4.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs4.getString("DISPLAYNAME")));
/* 4521 */             residTypeMapping.put(rs4.getString("RESOURCEID"), rs4.getString("TYPE"));
/*      */           }
/* 4523 */           rs4.close();
/*      */         }
/* 4525 */       } else if (selectmonitortype.equals("CUSTOM"))
/*      */       {
/* 4527 */         eumCondition = eumCondition.replace("RESOURCEID", "AM_ManagedObject.RESOURCEID");
/* 4528 */         String monitorsquery = "select AM_MYPAGE_WIDGET_MONITORS.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_MYPAGE_WIDGET_MONITORS left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_MYPAGE_WIDGET_MONITORS.RESOURCEID where WIDGETID=" + widgetid + eumCondition + " order by DISPLAYNAME";
/*      */         
/* 4530 */         if ((isoperator) || (EnterpriseUtil.isIt360MSPEdition())) {
/* 4531 */           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 4532 */             resIds_vector = CustomerManagementAPI.filterResourceIds(widgetProps);
/*      */           }
/* 4534 */           if ((widgettype == 1) || (widgettype == 2) || (widgettype == 3) || (widgettype == 4)) {
/* 4535 */             ParentChildRelationalUtil.getChildwithParentMonitors(resIds_vector);
/*      */           }
/* 4537 */           if (isUserResourceEnabled) {
/* 4538 */             String userid = (String)widgetProps.get("loginUserid");
/* 4539 */             monitorsquery = "select AM_MYPAGE_WIDGET_MONITORS.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_MYPAGE_WIDGET_MONITORS left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_MYPAGE_WIDGET_MONITORS.RESOURCEID join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " where  WIDGETID=" + widgetid + eumCondition + " order by DISPLAYNAME";
/*      */           } else {
/* 4541 */             monitorsquery = "select AM_MYPAGE_WIDGET_MONITORS.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_MYPAGE_WIDGET_MONITORS left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_MYPAGE_WIDGET_MONITORS.RESOURCEID where " + ManagedApplication.getCondition("AM_MYPAGE_WIDGET_MONITORS.RESOURCEID", resIds_vector) + " and  WIDGETID=" + widgetid + eumCondition + " order by DISPLAYNAME";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 4547 */         rs2 = AMConnectionPool.executeQueryStmt(monitorsquery);
/* 4548 */         while (rs2.next()) {
/* 4549 */           selectedMonitors.add(rs2.getString("RESOURCEID"));
/* 4550 */           residDisplayNames.put(rs2.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs2.getString("DISPLAYNAME")));
/* 4551 */           residTypeMapping.put(rs2.getString("RESOURCEID"), rs2.getString("TYPE"));
/*      */         }
/* 4553 */         rs2.close();
/* 4554 */       } else if (selectmonitortype.equals("ALL"))
/*      */       {
/* 4556 */         eumCondition = eumCondition.replace("RESOURCEID", "AM_ManagedObject.RESOURCEID");
/* 4557 */         String filterOption = "";
/* 4558 */         if ((filterbyname != null) && (filterbyname.trim().length() > 0)) {
/* 4559 */           filterOption = " and AM_ManagedObject.DISPLAYNAME like '%" + filterbyname + "%' ";
/*      */         }
/*      */         
/* 4562 */         String monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_ManagedObject  where " + resourcetypecondition + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */         
/* 4564 */         if (("$ComplexType_Windows".equals(resourcetype)) && (com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 4565 */           monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE,SQLDBM_SQL_HOST_MAPPING.SQLID from AM_ManagedObject  join SQLDBM_SQL_HOST_MAPPING on AM_ManagedObject.RESOURCEID=SQLDBM_SQL_HOST_MAPPING.HOSTID  where " + resourcetypecondition + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */         }
/*      */         
/* 4568 */         if ((isoperator) || (EnterpriseUtil.isIt360MSPEdition())) {
/* 4569 */           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 4570 */             resIds_vector = CustomerManagementAPI.filterResourceIds(widgetProps);
/*      */           }
/* 4572 */           if (isUserResourceEnabled) {
/* 4573 */             String userid = (String)widgetProps.get("loginUserid");
/* 4574 */             monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_ManagedObject,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and " + resourcetypecondition + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */           } else {
/* 4576 */             monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_ManagedObject  where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " and " + resourcetypecondition + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */           }
/*      */           
/* 4579 */           if (("$ComplexType_Windows".equals(resourcetype)) && (com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 4580 */             monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE,SQLDBM_SQL_HOST_MAPPING.SQLID from AM_ManagedObject  join SQLDBM_SQL_HOST_MAPPING on AM_ManagedObject.RESOURCEID=SQLDBM_SQL_HOST_MAPPING.HOSTID where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " and " + resourcetypecondition + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 4585 */         if (attribute_level.equals("2"))
/*      */         {
/* 4587 */           monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_ManagedObject  where " + resourcetypecondition + eumCondition + " order by DISPLAYNAME";
/* 4588 */           if (isoperator) {
/* 4589 */             monitorsquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,TYPE from AM_ManagedObject  where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " and " + resourcetypecondition + eumCondition + " order by DISPLAYNAME";
/*      */           }
/* 4591 */           eumCondition = eumCondition.replace("AM_ManagedObject.RESOURCEID", "RESOURCEID");
/* 4592 */           widgetProps.put("resourcetype", secondleveltype);
/* 4593 */           ArrayList firstlevelresids = new ArrayList();
/* 4594 */           rs3 = AMConnectionPool.executeQueryStmt(monitorsquery);
/* 4595 */           while (rs3.next()) {
/* 4596 */             firstlevelresids.add(rs3.getString("RESOURCEID"));
/*      */           }
/* 4598 */           rs3.close();
/* 4599 */           String secondlevelquery = "select RESOURCEID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER inner join   AM_ManagedObject  on RESOURCEID=CHILDID and TYPE='" + secondleveltype + "' where " + ManagedApplication.getCondition("PARENTID", firstlevelresids) + eumCondition + filterOption + " order by DISPLAYNAME";
/*      */           
/* 4601 */           rs2 = AMConnectionPool.executeQueryStmt(secondlevelquery);
/* 4602 */           while (rs2.next()) {
/* 4603 */             selectedMonitors.add(rs2.getString("RESOURCEID"));
/* 4604 */             residDisplayNames.put(rs2.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs2.getString("DISPLAYNAME")));
/* 4605 */             residTypeMapping.put(rs2.getString("RESOURCEID"), rs2.getString("TYPE"));
/*      */           }
/* 4607 */           rs2.close();
/*      */         }
/*      */         else {
/* 4610 */           rs4 = AMConnectionPool.executeQueryStmt(monitorsquery);
/* 4611 */           while (rs4.next()) {
/* 4612 */             selectedMonitors.add(rs4.getString("RESOURCEID"));
/* 4613 */             residDisplayNames.put(rs4.getString("RESOURCEID"), EnterpriseUtil.decodeString(rs4.getString("DISPLAYNAME")));
/* 4614 */             residTypeMapping.put(rs4.getString("RESOURCEID"), rs4.getString("TYPE"));
/* 4615 */             if (("$ComplexType_Windows".equals(resourcetype)) && (com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 4616 */               sqlhostresidMapping.put(rs4.getString("RESOURCEID"), rs4.getString("SQLID"));
/*      */             }
/*      */           }
/* 4619 */           rs4.close();
/*      */         }
/*      */         
/* 4622 */         if (("$ComplexType_Windows".equals(resourcetype)) && (com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 4623 */           ResultSet rslt = null;
/*      */           try {
/* 4625 */             resourcetypecondition = resourcetypecondition.replace("TYPE", "AM_ManagedObject.TYPE");
/* 4626 */             rslt = AMConnectionPool.executeQueryStmt("select SQLDBM_SQL_HOST_MAPPING.HOSTID, mo2.DISPLAYNAME from AM_ManagedObject join SQLDBM_SQL_HOST_MAPPING on AM_ManagedObject.RESOURCEID=SQLDBM_SQL_HOST_MAPPING.HOSTID join AM_ManagedObject mo2 on SQLDBM_SQL_HOST_MAPPING.SQLID=mo2.RESOURCEID where " + resourcetypecondition + eumCondition + filterOption);
/* 4627 */             while (rslt.next()) {
/* 4628 */               residDisplayNames.put(rslt.getString("HOSTID"), rslt.getString("DISPLAYNAME"));
/*      */             }
/*      */           } catch (Exception e) {
/* 4631 */             e.printStackTrace();
/*      */           } finally {
/* 4633 */             if (rslt != null) {
/* 4634 */               AMConnectionPool.closeStatement(rslt);
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 4641 */       else if (selectmonitortype.equals("CUSTOM FIELDS"))
/*      */       {
/* 4643 */         eumCondition = eumCondition.replace("RESOURCEID", "AM_ManagedObject.RESOURCEID");
/* 4644 */         int fieldtype = -1;
/* 4645 */         String datatable = "";
/* 4646 */         String aliasname = "";
/* 4647 */         String value = "";
/* 4648 */         ResultSet res = null;
/* 4649 */         ResultSet cfresult = null;
/*      */         try {
/* 4651 */           res = AMConnectionPool.executeQueryStmt("select VALUE,ALIASNAME,FIELDTYPE,DATATABLE from AM_MYFIELDS_METADATA as me, AM_MYPAGE_WIDGET_CUSTOMFIELD as cs where me.fieldid=cs.fieldid and cs.widgetid=" + widgetid);
/* 4652 */           if (res.next()) {
/* 4653 */             fieldtype = res.getInt("FIELDTYPE");
/* 4654 */             datatable = res.getString("DATATABLE");
/* 4655 */             aliasname = res.getString("ALIASNAME");
/* 4656 */             value = res.getString("VALUE");
/*      */           }
/* 4658 */           HashMap<String, String> condition = MyFields.customCondition(aliasname, value, null, false);
/*      */           
/* 4660 */           datatable = (String)condition.get("groupTable");
/* 4661 */           String qryCon = (String)condition.get("groupQuery");
/* 4662 */           String groupmonitors = (String)condition.get("groupmonitors");
/*      */           
/* 4664 */           String queryN = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from " + datatable + ", AM_ManagedObject where " + resourcetypecondition + eumCondition + " " + qryCon + "";
/* 4665 */           if (resourcetype.equals("Generic WMI")) {
/* 4666 */             queryN = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject where resourceid in (select GROUPID from AM_CAM_DC_GROUPS where RESOURCEID in (select RESOURCEID from " + datatable + " where " + aliasname + "=" + value + "))";
/*      */           }
/* 4668 */           cfresult = AMConnectionPool.executeQueryStmt(queryN);
/* 4669 */           while (cfresult.next()) {
/* 4670 */             selectedMonitors.add(cfresult.getString("RESOURCEID"));
/* 4671 */             residDisplayNames.put(cfresult.getString("RESOURCEID"), EnterpriseUtil.decodeString(cfresult.getString("DISPLAYNAME")));
/* 4672 */             residTypeMapping.put(cfresult.getString("RESOURCEID"), cfresult.getString("TYPE"));
/*      */           }
/*      */           
/* 4675 */           String mgQuery = "select AM_ManagedObject.RESOURCEID from " + datatable + ", AM_ManagedObject where AM_ManagedObject.type='HAI' " + qryCon;
/* 4676 */           Vector<String> resids = new Vector();
/* 4677 */           res = AMConnectionPool.executeQueryStmt(mgQuery);
/* 4678 */           List<String> mgResIdList = new ArrayList();
/* 4679 */           while (res.next())
/*      */           {
/* 4681 */             mgResIdList.add(res.getString("RESOURCEID"));
/*      */           }
/*      */           
/* 4684 */           for (String mgId : mgResIdList) {
/* 4685 */             this.mo.getAllChildsinTree(resids, mgId);
/*      */           }
/* 4687 */           cfresult = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedResourceType, AM_ManagedObject left join " + datatable + " on AM_ManagedObject.RESOURCEID=" + datatable + ".RESOURCEID where " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resids) + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE NOT IN ('Node','Network','HAI') and " + resourcetypecondition + eumCondition + " " + groupmonitors + "");
/* 4688 */           while (cfresult.next()) {
/* 4689 */             selectedMonitors.add(cfresult.getString("RESOURCEID"));
/* 4690 */             residDisplayNames.put(cfresult.getString("RESOURCEID"), EnterpriseUtil.decodeString(cfresult.getString("DISPLAYNAME")));
/* 4691 */             residTypeMapping.put(cfresult.getString("RESOURCEID"), cfresult.getString("TYPE"));
/*      */           }
/*      */         } catch (Exception ex) {
/* 4694 */           ex.printStackTrace();
/*      */         } finally {
/*      */           try {
/* 4697 */             if (res != null) {
/* 4698 */               AMConnectionPool.closeStatement(res);
/*      */             }
/* 4700 */             if (cfresult != null) {
/* 4701 */               AMConnectionPool.closeStatement(cfresult);
/*      */             }
/* 4703 */             if (rs5 != null) {
/* 4704 */               rs5.close();
/*      */             }
/*      */           } catch (Exception ex) {
/* 4707 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       return;
/*      */     } catch (Exception ex) {
/* 4713 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void getAllSubGroups(Hashtable fullMgTree, String parentid, ArrayList subGroups)
/*      */   {
/* 4722 */     subGroups.add(parentid);
/* 4723 */     if (fullMgTree.containsKey(parentid)) {
/* 4724 */       ArrayList subgrouplist = (ArrayList)fullMgTree.get(parentid);
/* 4725 */       for (int i = 0; i < subgrouplist.size(); i++) {
/* 4726 */         String subgroupid = (String)subgrouplist.get(i);
/* 4727 */         getAllSubGroups(fullMgTree, subgroupid, subGroups);
/*      */       }
/*      */     }
/*      */     else {}
/*      */   }
/*      */   
/*      */ 
/*      */   private Hashtable getFullMgTree()
/*      */   {
/* 4736 */     ResultSet rs = null;
/* 4737 */     AMConnectionPool con = new AMConnectionPool();
/* 4738 */     Hashtable fullMgTree = new Hashtable();
/*      */     try
/*      */     {
/* 4741 */       String allhaids = " select HAID,PARENTID,AM_HOLISTICAPPLICATION.TYPE  from  AM_HOLISTICAPPLICATION left outer  join AM_PARENTCHILDMAPPER on HAID=CHILDID ";
/* 4742 */       rs = AMConnectionPool.executeQueryStmt(allhaids);
/* 4743 */       while (rs.next()) {
/* 4744 */         String haid = rs.getString("HAID");
/* 4745 */         String parentid = rs.getString("PARENTID");
/* 4746 */         int haidtype = rs.getInt("TYPE");
/* 4747 */         if (parentid != null)
/*      */         {
/*      */ 
/* 4750 */           if (fullMgTree.containsKey(parentid)) {
/* 4751 */             ArrayList subgroups = (ArrayList)fullMgTree.get(parentid);
/* 4752 */             subgroups.add(haid);
/*      */           } else {
/* 4754 */             ArrayList subgroups = new ArrayList();
/* 4755 */             subgroups.add(haid);
/* 4756 */             fullMgTree.put(parentid, subgroups);
/*      */           } }
/*      */       }
/* 4759 */       rs.close();
/*      */     } catch (Exception ex) {
/* 4761 */       ex.printStackTrace();
/*      */     }
/* 4763 */     return fullMgTree;
/*      */   }
/*      */   
/*      */   public HashMap gatherMetrics(HashMap widgetProps) {
/* 4767 */     HashMap widgetData = new HashMap();
/*      */     try
/*      */     {
/* 4770 */       String baseAttribute = (String)widgetProps.get("baseAttribute");
/* 4771 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 4772 */       String alertstatusForMetrics = (String)widgetProps.get("alertstatusForMetrics");
/* 4773 */       ArrayList relatedAttributeList = (ArrayList)widgetProps.get("relatedAttributeList");
/* 4774 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 4775 */       ArrayList allAttributes = (ArrayList)widgetProps.get("allAttributes");
/* 4776 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 4777 */       String businessperiod = (String)widgetProps.get("businessperiod");
/*      */       
/* 4779 */       ArrayList<String> maxResidList = new ArrayList();
/* 4780 */       ArrayList<String> maxCollecTimeList = new ArrayList();
/* 4781 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 4782 */       ArrayList<String> headerAttributeIds = new ArrayList();
/* 4783 */       ArrayList<String> output_Resids = new ArrayList();
/*      */       
/* 4785 */       headerAttributeIds.add("Monitor Name");
/* 4786 */       controlHeader.add("monitorLink");
/*      */       
/*      */ 
/*      */ 
/* 4790 */       String widgetid = (String)widgetProps.get("widgetid");
/* 4791 */       int widgettype = Integer.parseInt((String)widgetProps.get("widgettype"));
/* 4792 */       int monitorSelectionType = -1;
/* 4793 */       if (widgettype == 2) {
/* 4794 */         monitorSelectionType = Integer.parseInt((String)widgetProps.get("monitorSelectionType"));
/*      */       }
/*      */       
/* 4797 */       if ((widgettype == 2) && (monitorSelectionType == 2))
/*      */       {
/* 4799 */         HashMap<String, ArrayList<String>> selectedResidattribsMap = new HashMap();
/* 4800 */         selectedResidattribsMap = getSelResAttrForAllTypes(widgetid);
/* 4801 */         HashMap widgetDataForAll = new HashMap();
/* 4802 */         ArrayList<String> maxResidList1 = new ArrayList();
/* 4803 */         ArrayList<String> headerAttributeIds1 = new ArrayList();
/* 4804 */         String limitn = "";
/* 4805 */         String toporbottom = "";
/* 4806 */         limitn = (String)widgetProps.get("toporBottom_N");
/* 4807 */         toporbottom = " " + (String)widgetProps.get("toporBottom");
/* 4808 */         for (Map.Entry<String, ArrayList<String>> entry : selectedResidattribsMap.entrySet()) {
/* 4809 */           String resId = (String)entry.getKey();
/* 4810 */           String resDispName = DBUtil.getDisplaynameforResourceID(resId);
/* 4811 */           ArrayList<String> attList = (ArrayList)entry.getValue();
/* 4812 */           for (int k = 0; k < attList.size(); k++) {
/* 4813 */             String attrId = (String)attList.get(k);
/* 4814 */             ArrayList<String> resIdList = new ArrayList();
/* 4815 */             ArrayList<String> maxCollecTimeList1 = new ArrayList();
/* 4816 */             ArrayList monList = new ArrayList();
/* 4817 */             monList.add(resId);
/* 4818 */             getMaxCollectionTimes(attrId, resIdList, maxCollecTimeList1, monList, widgetProps);
/* 4819 */             ArrayList<ArrayList> attrValue = processBatchForAttribute(attrId, resIdList, maxCollecTimeList1, limitn, toporbottom);
/* 4820 */             resIdList.clear();
/* 4821 */             maxCollecTimeList1.clear();
/* 4822 */             monList.clear();
/* 4823 */             ArrayList<String> attrExtTableDetailsForUnits = this.mo.getCachedAttributeDetails(attrId);
/* 4824 */             String attrDispName = FormatUtil.getString((String)attrExtTableDetailsForUnits.get(5));
/* 4825 */             String units = (String)attrExtTableDetailsForUnits.get(8);
/* 4826 */             if (!units.equals(""))
/*      */             {
/* 4828 */               units = "(" + FormatUtil.getString(units) + ")";
/*      */             }
/* 4830 */             String dispValue = "";
/* 4831 */             String tempStr = resId + "-" + attrId;
/* 4832 */             if ((resDispName != null) && (!resDispName.equals("")) && (attrDispName != null)) {
/* 4833 */               dispValue = resDispName + "#" + attrDispName;
/*      */             }
/* 4835 */             if ((attrValue.isEmpty()) || (attrValue.size() == 0) || (attrValue.get(0) == null) || (((ArrayList)attrValue.get(0)).toString().equals("null"))) {
/* 4836 */               widgetDataForAll.put(dispValue, "-#" + tempStr);
/*      */             }
/*      */             else {
/* 4839 */               ArrayList resMaxValueList = (ArrayList)attrValue.get(0);
/* 4840 */               if ((resMaxValueList.get(1) != null) && (!resMaxValueList.get(1).toString().equals("null")))
/*      */               {
/*      */ 
/* 4843 */                 widgetDataForAll.put(dispValue, resMaxValueList.get(1).toString() + units + "#" + tempStr);
/* 4844 */                 resMaxValueList.clear();
/*      */               }
/*      */             } } }
/* 4847 */         widgetData.put("widgetDataForAll", widgetDataForAll);
/*      */       }
/* 4849 */       getMaxCollectionTimes(baseAttribute, maxResidList, maxCollecTimeList, selectedMonitors, widgetProps);
/* 4850 */       if ((maxCollecTimeList.size() == 0) && (maxResidList.size() == 0)) {
/* 4851 */         for (int i = 0; i < relatedAttributeList.size(); i++) {
/* 4852 */           baseAttribute = relatedAttributeList.get(i).toString();
/* 4853 */           getMaxCollectionTimes(baseAttribute, maxResidList, maxCollecTimeList, selectedMonitors, widgetProps);
/* 4854 */           if ((maxCollecTimeList.size() > 0) || (maxResidList.size() > 0)) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/* 4859 */       HashMap batch = groupSimilarAttributes(baseAttribute, allAttributes);
/*      */       
/* 4861 */       processBatchforOuput(batch, maxResidList, maxCollecTimeList, outputData, widgetProps, headerAttributeIds);
/*      */       
/* 4863 */       if ((!resourcetype.equals("JMX1.2-MX4J-RMI")) && (!resourcetype.equals("SNMP"))) {
/* 4864 */         filterResidsFromMaxids(outputData, maxResidList, controlHeader);
/*      */       }
/* 4866 */       if ((alertstatusForMetrics != null) && (alertstatusForMetrics.equals("1"))) {
/* 4867 */         addAlertStatusToMetrics(widgetProps, outputData, headerAttributeIds);
/*      */       }
/* 4869 */       if (complexTypes.contains(resourcetype)) {
/* 4870 */         addResourceidAttributeidMapping(widgetProps, outputData, headerAttributeIds);
/*      */       }
/* 4872 */       widgetData.put("metricdata", outputData);
/* 4873 */       widgetData.put("header", headerAttributeIds);
/* 4874 */       widgetData.put("baseAttribute", baseAttribute);
/* 4875 */       widgetData.put("resourceIds", maxResidList);
/*      */       
/*      */ 
/* 4878 */       return widgetData;
/*      */     } catch (Exception ex) {
/* 4880 */       ex.printStackTrace();
/*      */     }
/* 4882 */     return widgetData;
/*      */   }
/*      */   
/*      */   private void addAlertStatusToMetrics(HashMap widgetProps, ArrayList<ArrayList> outputData, ArrayList<String> headerAttributeIds) {
/* 4886 */     ResultSet rs = null;
/* 4887 */     AMConnectionPool conn = new AMConnectionPool();
/*      */     try
/*      */     {
/* 4890 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 4891 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 4892 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 4893 */       boolean isMetaInfoAlreadyConstructed = outputData.size() == metaInfoInOrder.size();
/* 4894 */       int resid_index = controlHeader.indexOf("monitorLink");
/* 4895 */       StringBuffer residstoappend = new StringBuffer();
/* 4896 */       String alertQuery = null;
/* 4897 */       String eventQuery = null;
/* 4898 */       String comma = ",";
/* 4899 */       String singleqoute = "'";
/* 4900 */       String resids = null;
/* 4901 */       String underscore = "_";
/* 4902 */       String alertstatus = "alertstatus";
/* 4903 */       residstoappend.append("(");
/* 4904 */       for (int i = 0; i < outputData.size(); i++)
/*      */       {
/*      */ 
/* 4907 */         if (!isMetaInfoAlreadyConstructed) {
/* 4908 */           HashMap singleMetaInfoRow = new HashMap();
/* 4909 */           metaInfoInOrder.add(singleMetaInfoRow);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4914 */       for (int i = 0; i < headerAttributeIds.size(); i++) {
/* 4915 */         String attribType = (String)controlHeader.get(i);
/* 4916 */         String attribid = (String)headerAttributeIds.get(i);
/* 4917 */         if (attribType.equals("wholenumber"))
/*      */         {
/*      */ 
/*      */ 
/* 4921 */           ArrayList attribids = new ArrayList();
/* 4922 */           HashMap restypeAttribidMapping = new HashMap();
/* 4923 */           getAttribIdsforResourceType(resourcetype, attribid, attribids, restypeAttribidMapping);
/* 4924 */           for (int j = 0; j < outputData.size(); j++) {
/* 4925 */             ArrayList singleRow = (ArrayList)outputData.get(j);
/* 4926 */             String resourceid = (String)singleRow.get(resid_index);
/* 4927 */             HashMap<String, String> singleMetaInfoRow = (HashMap)metaInfoInOrder.get(j);
/* 4928 */             String attribCheckedForComplexType = getAttribIdForResourceType(resourceid, attribid, resourcetype, restypeAttribidMapping, widgetProps);
/* 4929 */             String entity = resourceid + underscore + attribCheckedForComplexType;
/* 4930 */             if (FaultUtil.getLatestEvent(entity) != null) {
/* 4931 */               HashMap<String, String> entitydetails = FaultUtil.getLatestEvent(entity);
/* 4932 */               String entitystatus = (String)entitydetails.get("SEVERITY");
/* 4933 */               if (entitystatus.equals("5")) {
/* 4934 */                 singleMetaInfoRow.put(resourceid + underscore + attribid + underscore + alertstatus, "#a4e72f");
/* 4935 */               } else if (entitystatus.equals("1")) {
/* 4936 */                 singleMetaInfoRow.put(resourceid + underscore + attribid + underscore + alertstatus, "#ff7672");
/* 4937 */               } else if (entitystatus.equals("4")) {
/* 4938 */                 singleMetaInfoRow.put(resourceid + underscore + attribid + underscore + alertstatus, "#ffcc6a");
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 4945 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void addResourceidAttributeidMapping(HashMap widgetProps, ArrayList<ArrayList> outputData, ArrayList<String> headerAttributeIds) {
/* 4950 */     ResultSet rs = null;
/* 4951 */     AMConnectionPool conn = new AMConnectionPool();
/*      */     try {
/* 4953 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 4954 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 4955 */       HashMap residTypeMapping = (HashMap)widgetProps.get("residTypeMapping");
/* 4956 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 4957 */       int resid_index = controlHeader.indexOf("monitorLink");
/* 4958 */       boolean isMetaInfoAlreadyConstructed = outputData.size() == metaInfoInOrder.size();
/* 4959 */       for (int i = 0; i < outputData.size(); i++) {
/* 4960 */         if (!isMetaInfoAlreadyConstructed) {
/* 4961 */           HashMap singleMetaInfoRow = new HashMap();
/* 4962 */           metaInfoInOrder.add(singleMetaInfoRow);
/*      */         }
/*      */       }
/* 4965 */       for (int i = 0; i < controlHeader.size(); i++) {
/* 4966 */         String columntype = (String)controlHeader.get(i);
/* 4967 */         if (columntype.equals("wholenumber")) {
/* 4968 */           HashMap restypeAttribidMapping = new HashMap();
/* 4969 */           ArrayList attribids = new ArrayList();
/* 4970 */           String attribid = (String)headerAttributeIds.get(i);
/* 4971 */           getAttribIdsforResourceType(resourcetype, attribid, attribids, restypeAttribidMapping);
/* 4972 */           for (int j = 0; j < outputData.size(); j++) {
/* 4973 */             HashMap metaInfoForRow = (HashMap)metaInfoInOrder.get(j);
/* 4974 */             ArrayList singlerow = (ArrayList)outputData.get(j);
/* 4975 */             String resourceid = (String)singlerow.get(resid_index);
/* 4976 */             String attribCheckedForComplexType = getAttribIdForResourceType(resourceid, attribid, resourcetype, restypeAttribidMapping, widgetProps);
/* 4977 */             metaInfoForRow.put(resourceid + "_" + attribid + "_forComplexType", attribCheckedForComplexType);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 4983 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void addDisplayNames(ArrayList<ArrayList> outputData, ArrayList<String> headerAttributeIds, HashMap<String, String> residDisplayName, ArrayList<HashMap> metaInfoInOrder, ArrayList<String> controlHeader) {
/*      */     try {
/* 4989 */       String resourcetype = "";
/* 4990 */       int resid_index = controlHeader.indexOf("monitorLink");
/* 4991 */       boolean isThisForMonitorGroupNames = false;
/* 4992 */       if (resid_index < 0) {
/* 4993 */         resid_index = controlHeader.indexOf("monitorgroupLink");
/* 4994 */         isThisForMonitorGroupNames = true;
/*      */       }
/* 4996 */       boolean isMetaInfoAlreadyConstructed = outputData.size() == metaInfoInOrder.size();
/*      */       
/*      */ 
/*      */ 
/* 5000 */       if (resid_index < 0) {
/* 5001 */         return;
/*      */       }
/* 5003 */       for (int k = 0; k < outputData.size(); k++) {
/* 5004 */         ArrayList<String> rowMarker = (ArrayList)outputData.get(k);
/* 5005 */         String resid = (String)rowMarker.get(resid_index);
/* 5006 */         String res_displayName = (String)residDisplayName.get(resid);
/* 5007 */         HashMap singlerow_metadata; HashMap singlerow_metadata; if (isMetaInfoAlreadyConstructed) {
/* 5008 */           singlerow_metadata = (HashMap)metaInfoInOrder.get(k);
/*      */         } else {
/* 5010 */           singlerow_metadata = new HashMap();
/* 5011 */           metaInfoInOrder.add(singlerow_metadata);
/*      */         }
/* 5013 */         singlerow_metadata.put("resourceid", resid);
/* 5014 */         if (singlerow_metadata.get("rowResourceid") == null) {
/* 5015 */           singlerow_metadata.put("rowResourceid", resid);
/*      */         }
/* 5017 */         int residinInt = -1;
/*      */         try {
/* 5019 */           residinInt = Integer.parseInt(resid);
/*      */         }
/*      */         catch (Exception ex1) {}
/* 5022 */         if ((EnterpriseUtil.isAdminServer()) && (isThisForMonitorGroupNames) && (residinInt > com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 5023 */           res_displayName = res_displayName + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */         }
/* 5025 */         singlerow_metadata.put("displayname", res_displayName);
/*      */       }
/*      */       
/*      */ 
/* 5029 */       for (int i = 0; i < headerAttributeIds.size(); i++) {
/* 5030 */         String attribid = (String)headerAttributeIds.get(i);
/* 5031 */         String displayName = attribid;
/* 5032 */         String units = "";
/* 5033 */         if ((attribid.equals("Monitor Name")) || (attribid.equals("am.mypage.monitorname.text")))
/*      */         {
/* 5035 */           displayName = FormatUtil.getString("am.mypage.monitorname.text");
/* 5036 */         } else if ((attribid.equals("ThresholdName")) || (attribid.equals("am.mypage.thresholdname.text")))
/*      */         {
/* 5038 */           displayName = FormatUtil.getString("am.mypage.thresholdname.text");
/* 5039 */         } else if (attribid.equals("AttribHealthStatus"))
/*      */         {
/* 5041 */           displayName = "";
/* 5042 */         } else if (attribid.equals("availabilitybar"))
/*      */         {
/* 5044 */           displayName = FormatUtil.getString("am.webclient.common.todaysavailability.text");
/* 5045 */         } else if (!isNumeric(attribid)) {
/* 5046 */           displayName = attribid;
/*      */         } else {
/* 5048 */           ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(attribid);
/* 5049 */           if (attrExtTableDetails != null) {
/* 5050 */             displayName = FormatUtil.getString((String)attrExtTableDetails.get(5));
/* 5051 */             units = (String)attrExtTableDetails.get(8);
/* 5052 */             resourcetype = (String)attrExtTableDetails.get(6);
/* 5053 */             if (!units.equals(""))
/*      */             {
/* 5055 */               units = "(" + FormatUtil.getString(units) + ")";
/*      */             }
/*      */           }
/*      */         }
/* 5059 */         headerAttributeIds.set(i, displayName + units);
/*      */       }
/*      */       
/* 5062 */       if ((resourcetype != null) && ((resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP")))) {
/* 5063 */         boolean isTabularAttributes = false;
/* 5064 */         ArrayList listofTabularCAMAttributes = new ArrayList();
/* 5065 */         for (int k = 0; k < outputData.size(); k++) {
/* 5066 */           ArrayList<String> rowMarker = (ArrayList)outputData.get(k);
/* 5067 */           String resid = (String)rowMarker.get(resid_index);
/* 5068 */           if (resid.indexOf("_") != -1) {
/* 5069 */             String tableattribute = resid.substring(0, resid.indexOf("_"));
/* 5070 */             rowMarker.set(resid_index, tableattribute);
/* 5071 */             isTabularAttributes = true;
/* 5072 */             listofTabularCAMAttributes.add(tableattribute);
/*      */           }
/*      */         }
/*      */         
/* 5076 */         if (isTabularAttributes) {
/* 5077 */           String action = "getDisplayNamesQuery";
/* 5078 */           HashMap extraprops = new HashMap();
/* 5079 */           HashMap attribToResidMapping = new HashMap();
/* 5080 */           extraprops.put("listofTabularCAMAttributes", listofTabularCAMAttributes);
/* 5081 */           String displaynameQuery = getSimilarAttributeForCAM_Actions(resourcetype, null, extraprops, action);
/* 5082 */           ResultSet rs = null;
/* 5083 */           AMConnectionPool con = new AMConnectionPool();
/* 5084 */           rs = AMConnectionPool.executeQueryStmt(displaynameQuery);
/* 5085 */           while (rs.next()) {
/* 5086 */             attribToResidMapping.put(rs.getString("ATTRIBUTEID"), rs.getString("RESOURCEID"));
/*      */           }
/* 5088 */           rs.close();
/* 5089 */           for (int k = 0; k < outputData.size(); k++) {
/* 5090 */             ArrayList<String> rowMarker = (ArrayList)outputData.get(k);
/* 5091 */             String attribid = (String)rowMarker.get(resid_index);
/* 5092 */             String resid = (String)attribToResidMapping.get(attribid);
/* 5093 */             rowMarker.set(resid_index, resid);
/* 5094 */             HashMap singlerow_metadata = (HashMap)metaInfoInOrder.get(k);
/* 5095 */             String res_displayName = (String)residDisplayName.get(resid);
/* 5096 */             singlerow_metadata.put("resourceid", resid);
/* 5097 */             if (singlerow_metadata.get("rowResourceid") == null) {
/* 5098 */               singlerow_metadata.put("rowResourceid", resid);
/*      */             }
/* 5100 */             singlerow_metadata.put("displayname", res_displayName);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 5106 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void addMonitorNameForSecondLevelMos(ArrayList<ArrayList> outputdata, ArrayList resourceIds, ArrayList<String> headerAttributeIds, ArrayList<String> controlHeader, ArrayList<HashMap> metaInfoInOrder, HashMap<String, String> residDisplayNames, String secondlevelrestype) {
/* 5111 */     ResultSet rs = null;
/* 5112 */     HashMap parentChildMapping = new HashMap();
/*      */     
/* 5114 */     AMConnectionPool con = new AMConnectionPool();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 5125 */       boolean isMetaInfoAlreadyConstructed = outputdata.size() == metaInfoInOrder.size();
/* 5126 */       int monitornameIndex = controlHeader.indexOf("monitorLink");
/* 5127 */       if (monitornameIndex < 0) {
/* 5128 */         monitornameIndex = controlHeader.indexOf("monitorgroupLink");
/*      */       }
/* 5130 */       headerAttributeIds.set(monitornameIndex, secondlevelrestype);
/* 5131 */       headerAttributeIds.add(monitornameIndex + 1, "Monitor Name");
/* 5132 */       controlHeader.set(monitornameIndex, "SecondLevelMOName");
/* 5133 */       controlHeader.add(monitornameIndex + 1, "monitorLink");
/*      */       
/* 5135 */       String query = "select PARENTID,CHILDID,DISPLAYNAME from AM_PARENTCHILDMAPPER inner join AM_ManagedObject on RESOURCEID=PARENTID where " + ManagedApplication.getCondition("CHILDID", resourceIds);
/*      */       
/* 5137 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 5138 */       while (rs.next()) {
/* 5139 */         String parentid = rs.getString("PARENTID");
/* 5140 */         String childid = rs.getString("CHILDID");
/* 5141 */         String displayName = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 5142 */         parentChildMapping.put(childid, parentid);
/* 5143 */         residDisplayNames.put(parentid, displayName);
/*      */       }
/* 5145 */       rs.close();
/* 5146 */       for (int i = 0; i < outputdata.size(); i++) {
/* 5147 */         ArrayList singlerow = (ArrayList)outputdata.get(i);
/* 5148 */         HashMap metaInfoForEachRow; HashMap metaInfoForEachRow; if (isMetaInfoAlreadyConstructed) {
/* 5149 */           metaInfoForEachRow = (HashMap)metaInfoInOrder.get(i);
/*      */         } else {
/* 5151 */           metaInfoForEachRow = new HashMap();
/* 5152 */           metaInfoInOrder.add(metaInfoForEachRow);
/*      */         }
/* 5154 */         String childid = (String)singlerow.get(monitornameIndex);
/* 5155 */         String parentid = (String)parentChildMapping.get(childid);
/* 5156 */         String displayName = (String)residDisplayNames.get(parentid);
/* 5157 */         displayName = EnterpriseUtil.decodeString(displayName);
/*      */         
/* 5159 */         String secondLevelDisplayname = (String)residDisplayNames.get(childid);
/* 5160 */         metaInfoForEachRow.put("secondLevelResourceid", childid);
/* 5161 */         metaInfoForEachRow.put("rowResourceid", childid);
/* 5162 */         metaInfoForEachRow.put("secondLevelDisplayname", secondLevelDisplayname);
/* 5163 */         metaInfoForEachRow.put("resourceid", parentid);
/* 5164 */         metaInfoForEachRow.put("displayname", displayName);
/* 5165 */         singlerow.set(monitornameIndex, secondLevelDisplayname);
/* 5166 */         singlerow.add(monitornameIndex + 1, parentid);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 5170 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isNumeric(String attribid)
/*      */   {
/*      */     try {
/* 5177 */       attribid_int = Integer.parseInt(attribid);
/*      */     } catch (Exception ex) {
/*      */       int attribid_int;
/* 5180 */       return false;
/*      */     }
/* 5182 */     return true;
/*      */   }
/*      */   
/*      */   private void getMaxCollectionTimes(String baseAttribute, ArrayList<String> maxResidList, ArrayList<String> maxCollecTimeList, ArrayList<String> selectedMonitors, HashMap widgetProps)
/*      */   {
/* 5187 */     ResultSet set1 = null;
/* 5188 */     AMConnectionPool cp = new AMConnectionPool();
/* 5189 */     String resourceType = "";
/*      */     try
/*      */     {
/* 5192 */       ArrayList<String> attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(baseAttribute);
/* 5193 */       String datatable = (String)attrExtTableDetails.get(0);
/* 5194 */       String resid_col = (String)attrExtTableDetails.get(1);
/* 5195 */       String attid_col = (String)attrExtTableDetails.get(2);
/* 5196 */       String value_col = (String)attrExtTableDetails.get(3);
/* 5197 */       String coltime_col = (String)attrExtTableDetails.get(4);
/* 5198 */       resourceType = (String)attrExtTableDetails.get(6);
/* 5199 */       String selectedCondition = "";
/* 5200 */       String temptype = resourceType.toLowerCase();
/* 5201 */       if (selectedMonitors.size() > 0) {
/* 5202 */         selectedCondition = ManagedApplication.getCondition(datatable + "." + resid_col, selectedMonitors);
/*      */       }
/* 5204 */       String selectedConditionwithAnd = "";
/* 5205 */       if (selectedMonitors.size() > 0) {
/* 5206 */         selectedConditionwithAnd = selectedCondition + " and ";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 5212 */       String maxTimeQuery = "select max(" + coltime_col + ")as MaxColTime," + datatable + "." + resid_col + "  from   " + datatable + "  where " + selectedCondition + " group by " + resid_col;
/* 5213 */       if (!attid_col.equals("-1"))
/*      */       {
/* 5215 */         maxTimeQuery = "select max(" + coltime_col + ") as MaxColTime," + datatable + "." + resid_col + "   from   " + datatable + " where " + selectedConditionwithAnd + " " + attid_col + "=" + baseAttribute + " group by " + resid_col;
/* 5216 */       } else if (datatable.equalsIgnoreCase("TableSpaceStatus")) {
/* 5217 */         maxTimeQuery = "select max(" + coltime_col + ") as MaxColTime,AM_ManagedObject.RESOURCEID   from   " + datatable + " inner join AM_ManagedObject   on  " + DBQueryUtil.concat(new String[] { "TableSpaceStatus.RESOURCENAME", "':'", "TableSpaceStatus.TABLESPACENAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", selectedMonitors) + " group by AM_ManagedObject.RESOURCEID";
/* 5218 */         resid_col = "RESOURCEID";
/* 5219 */       } else if (datatable.equalsIgnoreCase("DataFiles")) {
/* 5220 */         maxTimeQuery = "select max(" + coltime_col + ") as MaxColTime,AM_ManagedObject.RESOURCEID   from   " + datatable + " inner join AM_ManagedObject   on  " + DBQueryUtil.concat(new String[] { "DataFiles.RESOURCENAME", "':'", "DataFiles.FILE_NAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", selectedMonitors) + " group by AM_ManagedObject.RESOURCEID";
/* 5221 */         resid_col = "RESOURCEID";
/* 5222 */       } else if (datatable.equalsIgnoreCase("RollbackData")) {
/* 5223 */         maxTimeQuery = "select max(" + coltime_col + ") as MaxColTime,AM_ManagedObject.RESOURCEID   from   " + datatable + " inner join AM_ManagedObject   on    " + DBQueryUtil.concat(new String[] { "RollbackData.RESOURCENAME", "':'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", selectedMonitors) + " group by AM_ManagedObject.RESOURCEID";
/* 5224 */         resid_col = "RESOURCEID";
/* 5225 */       } else if (resid_col.equalsIgnoreCase("RESOURCENAME")) {
/* 5226 */         maxTimeQuery = "select max(" + coltime_col + ") as MaxColTime,AM_ManagedObject.RESOURCEID   from   " + datatable + " inner join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + "  where " + ManagedApplication.getCondition("RESOURCEID", selectedMonitors) + " group by AM_ManagedObject.RESOURCEID";
/* 5227 */         resid_col = "RESOURCEID";
/* 5228 */       } else if ((resourceType.equals("JMX1.2-MX4J-RMI")) || (resourceType.equals("SNMP")) || (temptype.indexOf("win32_") != -1) || (resourceType.equals("Web_Service_Operation"))) {
/* 5229 */         String action = "getMaxTimeQuery";
/* 5230 */         HashMap extraprops = new HashMap();
/* 5231 */         extraprops.put("selectedMonitors", selectedMonitors);
/* 5232 */         maxTimeQuery = getSimilarAttributeForCAM_Actions(resourceType, baseAttribute, extraprops, action);
/* 5233 */         resid_col = "RESOURCEID";
/*      */       }
/*      */       
/* 5236 */       if (maxTimeQuery == null) {
/*      */         return;
/*      */       }
/*      */       
/* 5240 */       set1 = AMConnectionPool.executeQueryStmt(maxTimeQuery);
/* 5241 */       String businessperiod = (String)widgetProps.get("businessperiod");
/* 5242 */       Hashtable bhDetails = SegmentReportUtil.getBusinessRule(businessperiod);
/*      */       
/* 5244 */       while (set1.next()) {
/* 5245 */         String resourceId = set1.getString(resid_col);
/* 5246 */         long maximumtime = set1.getLong("MaxColTime");
/* 5247 */         if ((businessperiod != null) && (!DashboardUtil.isBHour(maximumtime, bhDetails, true)))
/*      */         {
/*      */           try {
/* 5250 */             String period = (String)widgetProps.get("period");
/* 5251 */             boolean isPolledData = "20".equals(period);
/* 5252 */             long latestBHTime = getLatestBHCollectionTIme(baseAttribute, resourceId, bhDetails, isPolledData);
/* 5253 */             if (latestBHTime != -1L) {
/* 5254 */               maxResidList.add(resourceId);
/* 5255 */               maxCollecTimeList.add(String.valueOf(latestBHTime));
/*      */             }
/*      */           } catch (Exception exc) {
/* 5258 */             maxResidList.add(resourceId);
/* 5259 */             maxCollecTimeList.add(set1.getString("MaxColTime"));
/* 5260 */             exc.printStackTrace();
/* 5261 */             AMLog.debug("Unable to get the latest collection time for Business Hour " + bhDetails);
/*      */           }
/*      */         } else {
/* 5264 */           maxResidList.add(resourceId);
/* 5265 */           maxCollecTimeList.add(set1.getString("MaxColTime"));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 5271 */       ex.printStackTrace();
/*      */     } finally {
/* 5273 */       closeResultSet(set1);
/*      */     }
/*      */   }
/*      */   
/*      */   private long getLatestBHCollectionTIme(String baseAttribute, String resourceId, Hashtable bhDetails, boolean isPolledData) throws Exception
/*      */   {
/* 5279 */     long latestBHCollectionTime = -1L;
/* 5280 */     ArrayList<String> attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(baseAttribute);
/* 5281 */     ArrayList resourceIds = new ArrayList();
/* 5282 */     resourceIds.add(resourceId);
/* 5283 */     String datatable = (String)attrExtTableDetails.get(0);
/* 5284 */     String resid_col = (String)attrExtTableDetails.get(1);
/* 5285 */     String attid_col = (String)attrExtTableDetails.get(2);
/* 5286 */     String value_col = (String)attrExtTableDetails.get(3);
/* 5287 */     String coltime_col = (String)attrExtTableDetails.get(4);
/* 5288 */     String resourceType = (String)attrExtTableDetails.get(6);
/* 5289 */     String selectedCondition = "";
/* 5290 */     String temptype = resourceType.toLowerCase();
/* 5291 */     String orderByCondition = "order by " + coltime_col + " desc ";
/* 5292 */     String collectionTimeQuery = "select " + resid_col + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, resourceIds);
/* 5293 */     ResultSet colTimeSet = null;
/*      */     try
/*      */     {
/* 5296 */       if (!attid_col.equals("-1"))
/*      */       {
/* 5298 */         collectionTimeQuery = "select " + resid_col + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, resourceIds) + " and   " + attid_col + "=" + baseAttribute;
/* 5299 */       } else if (datatable.equalsIgnoreCase("TableSpaceStatus")) {
/* 5300 */         collectionTimeQuery = "select RESOURCEID," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "TableSpaceStatus.RESOURCENAME", "':'", "TableSpaceStatus.TABLESPACENAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", resourceIds);
/* 5301 */         resid_col = "RESOURCEID";
/* 5302 */       } else if (datatable.equalsIgnoreCase("DataFiles")) {
/* 5303 */         collectionTimeQuery = "select RESOURCEID," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "DataFiles.RESOURCENAME", "':'", "DataFiles.FILE_NAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", resourceIds);
/* 5304 */         resid_col = "RESOURCEID";
/* 5305 */       } else if (datatable.equalsIgnoreCase("RollbackData")) {
/* 5306 */         collectionTimeQuery = "select RESOURCEID," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on    " + DBQueryUtil.concat(new String[] { "RollbackData.RESOURCENAME", "':'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", resourceIds);
/* 5307 */         resid_col = "RESOURCEID";
/* 5308 */       } else if (resid_col.equalsIgnoreCase("RESOURCENAME")) {
/* 5309 */         collectionTimeQuery = "select RESOURCEID," + coltime_col + " from " + datatable + " inner join AM_ManagedObject on  AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + " where " + ManagedApplication.getCondition("RESOURCEID", resourceIds);
/* 5310 */         resid_col = "RESOURCEID";
/* 5311 */       } else if ((resourceType.equals("JMX1.2-MX4J-RMI")) || (resourceType.equals("SNMP")) || (temptype.indexOf("win32_") != -1) || (resourceType.equals("Web_Service_Operation"))) {
/* 5312 */         collectionTimeQuery = getSimilarAttributeForCAM_Actions(resourceType, baseAttribute, null, "getGraphQuery");
/* 5313 */         value_col = "VALUE";
/* 5314 */         resid_col = "RESOURCEID";
/*      */       }
/* 5316 */       collectionTimeQuery = collectionTimeQuery + orderByCondition;
/*      */       
/* 5318 */       colTimeSet = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/* 5319 */       while (colTimeSet.next()) {
/* 5320 */         long tmplatestBHCollectionTime = colTimeSet.getLong(coltime_col);
/* 5321 */         if (DashboardUtil.isBHour(tmplatestBHCollectionTime, bhDetails, isPolledData)) {
/* 5322 */           return tmplatestBHCollectionTime;
/*      */         }
/*      */       }
/*      */     } catch (Exception exc) {
/* 5326 */       exc.printStackTrace();
/* 5327 */       throw exc;
/*      */     } finally {
/* 5329 */       AMConnectionPool.closeResultSet(colTimeSet);
/*      */     }
/* 5331 */     return latestBHCollectionTime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private HashMap groupSimilarAttributes(String baseAttribute, ArrayList<String> allAttributes)
/*      */   {
/* 5338 */     HashMap<String, ArrayList> batchCommands = new HashMap();
/* 5339 */     HashMap toreturn = new HashMap();
/*      */     
/*      */ 
/* 5342 */     String resourcetype = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5350 */     ArrayList<String> columns = this.mo.getCachedAttributeDetails(baseAttribute);
/* 5351 */     boolean isTypeGenericWMI = false;
/* 5352 */     if ((columns != null) && (((String)columns.get(6)).toLowerCase().indexOf("win32") != -1))
/*      */     {
/* 5354 */       isTypeGenericWMI = true;
/*      */     }
/*      */     
/* 5357 */     if ((columns != null) && ((((String)columns.get(6)).equals("JMX1.2-MX4J-RMI")) || (((String)columns.get(6)).equals("SNMP")) || (isTypeGenericWMI) || (((String)columns.get(6)).equals("Web_Service_Operation"))))
/*      */     {
/* 5359 */       toreturn.put("isbaseTableScalar", "true");
/* 5360 */       toreturn.put("baseTable", "AM_CAM_DC_ATTRIBUTES");
/* 5361 */       for (int i = 0; i < allAttributes.size(); i++) {
/* 5362 */         ArrayList<String> batch_command = new ArrayList();
/* 5363 */         batch_command.add(allAttributes.get(i));
/* 5364 */         if (((String)allAttributes.get(i)).equals(baseAttribute)) {
/* 5365 */           batchCommands.put("AM_CAM_DC_ATTRIBUTES", batch_command);
/*      */         } else {
/* 5367 */           batchCommands.put("AM_CAM_DC_ATTRIBUTES_" + i, batch_command);
/*      */         }
/*      */       }
/* 5370 */       toreturn.put("batchCommands", batchCommands);
/* 5371 */       return toreturn;
/*      */     }
/*      */     
/* 5374 */     for (int i = 0; i < allAttributes.size(); i++) {
/* 5375 */       String batch_attbid = (String)allAttributes.get(i);
/* 5376 */       columns = (ArrayList)ManagedApplication.attributesExt.get(batch_attbid);
/* 5377 */       if (columns != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 5382 */         String batch_tableName = (String)columns.get(0);
/* 5383 */         String attribute_type = (String)columns.get(9);
/*      */         
/*      */ 
/*      */ 
/* 5387 */         if ((attribute_type.equals("1")) || (attribute_type.equals("2")))
/*      */         {
/* 5389 */           batch_tableName = batch_tableName + "_healthorAvailability";
/*      */         }
/* 5391 */         if (batchCommands.containsKey(batch_tableName)) {
/* 5392 */           ArrayList<String> batch_command = (ArrayList)batchCommands.get(batch_tableName);
/* 5393 */           batch_command.add(batch_attbid);
/* 5394 */           if (batch_attbid.equals(baseAttribute)) {
/* 5395 */             toreturn.put("baseTable", batch_tableName);
/*      */             
/*      */ 
/* 5398 */             if (((String)columns.get(2)).equals("-1"))
/*      */             {
/* 5400 */               toreturn.put("isbaseTableScalar", "false");
/*      */             } else {
/* 5402 */               toreturn.put("isbaseTableScalar", "true");
/*      */             }
/*      */           }
/*      */         } else {
/* 5406 */           ArrayList<String> batch_command = new ArrayList();
/* 5407 */           batch_command.add(batch_attbid);
/* 5408 */           if (batch_attbid.equals(baseAttribute)) {
/* 5409 */             toreturn.put("baseTable", batch_tableName);
/* 5410 */             if (((String)columns.get(2)).equals("-1"))
/*      */             {
/* 5412 */               toreturn.put("isbaseTableScalar", "false");
/*      */             } else {
/* 5414 */               toreturn.put("isbaseTableScalar", "true");
/*      */             }
/*      */           }
/* 5417 */           batchCommands.put(batch_tableName, batch_command);
/*      */         }
/*      */       } }
/* 5420 */     toreturn.put("batchCommands", batchCommands);
/* 5421 */     return toreturn;
/*      */   }
/*      */   
/*      */   private boolean appendDataToOutput(ArrayList<ArrayList> processData, ArrayList<ArrayList> outputData, ArrayList<String> headerAttributeIds, ArrayList<String> columnsforProcessing, ArrayList<String> controlHeader, int whereToAppend)
/*      */   {
/* 5426 */     return appendDataToOutput(processData, outputData, headerAttributeIds, columnsforProcessing, controlHeader, whereToAppend, null, new ArrayList());
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
/*      */   private boolean appendDataToOutput(ArrayList<ArrayList> processData, ArrayList<ArrayList> outputData, ArrayList<String> headerAttributeIds, ArrayList<String> columnsforProcessing, ArrayList<String> controlHeader, int whereToAppend, ArrayList<HashMap> processMetaInfoInOrder, ArrayList<HashMap> metaInfoInOrder)
/*      */   {
/*      */     try
/*      */     {
/* 5441 */       boolean isAppendMetaData = false;
/* 5442 */       boolean isMetaInfoAlreadyConstructed = (metaInfoInOrder != null) && (outputData.size() == metaInfoInOrder.size());
/* 5443 */       if (processMetaInfoInOrder != null) {
/* 5444 */         isAppendMetaData = true;
/*      */       }
/* 5446 */       int resid_index = 0;
/* 5447 */       if (controlHeader.indexOf("monitorLink") != -1)
/*      */       {
/* 5449 */         resid_index = controlHeader.indexOf("monitorLink");
/*      */       }
/*      */       
/* 5452 */       headerAttributeIds.addAll(whereToAppend, columnsforProcessing);
/* 5453 */       int tempWhereToAppend = whereToAppend;
/* 5454 */       for (int i = 0; i < columnsforProcessing.size(); i++) {
/* 5455 */         ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails((String)columnsforProcessing.get(i));
/* 5456 */         String attribute_type = (String)attrExtTableDetails.get(9);
/* 5457 */         if (attribute_type.equals("1"))
/*      */         {
/* 5459 */           controlHeader.add(tempWhereToAppend, "availicon");
/* 5460 */         } else if (attribute_type.equals("2"))
/*      */         {
/* 5462 */           controlHeader.add(tempWhereToAppend, "healthicon");
/* 5463 */         } else if (attribute_type.equals("0"))
/*      */         {
/* 5465 */           controlHeader.add(tempWhereToAppend, "wholenumber");
/* 5466 */         } else if (attribute_type.equals("3"))
/*      */         {
/* 5468 */           controlHeader.add(tempWhereToAppend, "valueasis");
/*      */         } else {
/* 5470 */           controlHeader.add(tempWhereToAppend, "valueasis");
/*      */         }
/* 5472 */         tempWhereToAppend++;
/*      */       }
/*      */       
/* 5475 */       if (outputData.size() <= 0)
/*      */       {
/* 5477 */         outputData.addAll(processData);
/* 5478 */         if ((metaInfoInOrder != null) && (processMetaInfoInOrder != null)) {
/* 5479 */           metaInfoInOrder.addAll(processMetaInfoInOrder);
/*      */         }
/* 5481 */         return true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5486 */       HashMap<String, ArrayList> resids_data_map = new HashMap();
/* 5487 */       HashMap<String, HashMap> resids_metadata_map = new HashMap();
/* 5488 */       if (processData != null) {
/* 5489 */         for (int i = 0; i < processData.size(); i++) {
/* 5490 */           ArrayList<String> rowMarker = (ArrayList)processData.get(i);
/* 5491 */           resids_data_map.put(rowMarker.get(0), rowMarker);
/* 5492 */           if (isAppendMetaData) {
/* 5493 */             resids_metadata_map.put(rowMarker.get(0), processMetaInfoInOrder.get(i));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5500 */       for (int l = 0; l < outputData.size(); l++) {
/* 5501 */         ArrayList<String> rowMarker = (ArrayList)outputData.get(l);
/* 5502 */         String resid = (String)rowMarker.get(resid_index);
/*      */         
/*      */ 
/* 5505 */         ArrayList<String> dataToAppend = (ArrayList)resids_data_map.get(resid);
/* 5506 */         HashMap metaDataToAppend = (HashMap)resids_metadata_map.get(resid);
/*      */         
/*      */ 
/*      */ 
/* 5510 */         if (dataToAppend == null) {
/* 5511 */           addDummyData(rowMarker, whereToAppend, columnsforProcessing.size());
/*      */         } else {
/* 5513 */           dataToAppend.remove(0);
/*      */           
/* 5515 */           rowMarker.addAll(whereToAppend, dataToAppend);
/*      */         }
/*      */         HashMap rowInfo;
/*      */         HashMap rowInfo;
/* 5519 */         if (isMetaInfoAlreadyConstructed) {
/* 5520 */           rowInfo = (HashMap)metaInfoInOrder.get(l);
/*      */         } else {
/* 5522 */           rowInfo = new HashMap();
/* 5523 */           metaInfoInOrder.add(rowInfo);
/*      */         }
/*      */         
/* 5526 */         if (metaDataToAppend != null) {
/* 5527 */           rowInfo.putAll(metaDataToAppend);
/*      */         }
/*      */       }
/* 5530 */       return true;
/*      */     } catch (Exception ex) {
/* 5532 */       ex.printStackTrace(); }
/* 5533 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void addDummyData(ArrayList metricDataRow, int whereToAppend, int sizetoAppend)
/*      */   {
/* 5540 */     ArrayList<String> dummyData = new ArrayList();
/* 5541 */     for (int i = 0; i < sizetoAppend; i++) {
/* 5542 */       dummyData.add("-");
/*      */     }
/* 5544 */     metricDataRow.addAll(whereToAppend, dummyData);
/*      */   }
/*      */   
/*      */ 
/*      */   private void processBatchforOuput(HashMap batch, ArrayList<String> maxResidList, ArrayList<String> maxCollecTimeList, ArrayList<ArrayList> outputData, HashMap widgetProps, ArrayList<String> headerAttributeIds)
/*      */   {
/*      */     try
/*      */     {
/* 5552 */       baseAttribute = (String)widgetProps.get("baseAttribute");
/* 5553 */       controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 5554 */       limitn = "";
/* 5555 */       toporbottom = "";
/* 5556 */       String widgettype = (String)widgetProps.get("widgettype");
/* 5557 */       String businessperiod = (String)widgetProps.get("businessperiod");
/* 5558 */       limitn = (String)widgetProps.get("toporBottom_N");
/* 5559 */       toporbottom = " " + (String)widgetProps.get("toporBottom");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 5564 */       batchCommands = (HashMap)batch.get("batchCommands");
/* 5565 */       String baseTable = (String)batch.get("baseTable");
/* 5566 */       String isbaseTableScalar = (String)batch.get("isbaseTableScalar");
/* 5567 */       ArrayList<String> columnsWithBaseAttrib = (ArrayList)batchCommands.get(baseTable);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5574 */       ArrayList<ArrayList> processData = null;
/* 5575 */       if (isbaseTableScalar.equals("true"))
/*      */       {
/* 5577 */         ArrayList<String> dummy_columnsWithBaseAttrib = new ArrayList();
/* 5578 */         dummy_columnsWithBaseAttrib.add(baseAttribute);
/* 5579 */         processData = processBatch(dummy_columnsWithBaseAttrib, maxResidList, maxCollecTimeList, baseAttribute, limitn, toporbottom);
/* 5580 */         int whereToAppend = headerAttributeIds.size();
/* 5581 */         boolean isAppend = appendDataToOutput(processData, outputData, headerAttributeIds, dummy_columnsWithBaseAttrib, controlHeader, whereToAppend);
/* 5582 */         if (columnsWithBaseAttrib.indexOf(baseAttribute) >= 0) {
/* 5583 */           columnsWithBaseAttrib.remove(columnsWithBaseAttrib.indexOf(baseAttribute));
/*      */         }
/*      */       }
/*      */       else {
/* 5587 */         processData = processBatch(columnsWithBaseAttrib, maxResidList, maxCollecTimeList, baseAttribute, limitn, toporbottom);
/* 5588 */         int whereToAppend = headerAttributeIds.size();
/* 5589 */         boolean isAppend = appendDataToOutput(processData, outputData, headerAttributeIds, columnsWithBaseAttrib, controlHeader, whereToAppend);
/* 5590 */         batchCommands.remove(baseTable);
/*      */       }
/*      */       
/* 5593 */       if ((outputData == null) || (outputData.size() <= 0))
/*      */       {
/* 5595 */         return;
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
/*      */ 
/*      */ 
/* 5614 */       AMLog.info("MyPageAction.processBatchforOuput() limitn : " + limitn);
/* 5615 */       if (((widgettype.equals("1")) || (widgettype.equals("2")) || (widgettype.equals("3"))) && (maxResidList.size() > Integer.parseInt(limitn)))
/*      */       {
/* 5617 */         ArrayList newMaxResidList = new ArrayList();
/* 5618 */         ArrayList newMaxCollecTimeList = new ArrayList();
/*      */         
/* 5620 */         for (ArrayList innerList : processData) {
/* 5621 */           Object val = innerList.get(0);
/* 5622 */           int positionTogetCollTime = maxResidList.indexOf(val);
/* 5623 */           AMLog.info("insid newMaxResidList building : val is : " + val + " and positionTogetCollTime is : " + positionTogetCollTime);
/* 5624 */           newMaxResidList.add(val);
/* 5625 */           newMaxCollecTimeList.add(maxCollecTimeList.get(positionTogetCollTime));
/*      */         }
/*      */         
/* 5628 */         AMLog.info("going to replace the old values with new vals : newMaxResidList " + newMaxResidList + " newMaxCollecTimeList : " + newMaxCollecTimeList);
/* 5629 */         maxResidList = newMaxResidList;
/* 5630 */         maxCollecTimeList = newMaxCollecTimeList;
/*      */       }
/* 5632 */       mo = new ManagedApplication();
/* 5633 */       for (it = batchCommands.entrySet().iterator(); it.hasNext();) {
/* 5634 */         Map.Entry entry = (Map.Entry)it.next();
/* 5635 */         String strKey = (String)entry.getKey();
/* 5636 */         ArrayList<String> columnsforProcessing = (ArrayList)batchCommands.get(strKey);
/*      */         
/*      */ 
/*      */ 
/* 5640 */         boolean isHealthorAvailSelected = false;
/* 5641 */         for (int i = 0; i < columnsforProcessing.size(); i++) {
/* 5642 */           String sinlgeAttribid = (String)columnsforProcessing.get(i);
/* 5643 */           ArrayList<String> attrExtTableDetails = mo.getCachedAttributeDetails(sinlgeAttribid);
/* 5644 */           String attribute_type = (String)attrExtTableDetails.get(9);
/*      */           
/* 5646 */           if (attribute_type.equals("1"))
/*      */           {
/*      */ 
/* 5649 */             widgetProps.put("availabilityid", sinlgeAttribid);
/* 5650 */             isHealthorAvailSelected = true;
/*      */           }
/* 5652 */           if (attribute_type.equals("2"))
/*      */           {
/*      */ 
/* 5655 */             widgetProps.put("healthid", sinlgeAttribid);
/* 5656 */             isHealthorAvailSelected = true;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 5661 */         if (isHealthorAvailSelected)
/*      */         {
/* 5663 */           widgetProps.put("appendWhere", "afterMonitorNameColumn");
/* 5664 */           widgetProps.put("noLabels", "true");
/* 5665 */           widgetProps.put("outputData", outputData);
/* 5666 */           widgetProps.put("headerAttributeIds", headerAttributeIds);
/* 5667 */           getAvailHealth(widgetProps);
/*      */         }
/* 5669 */         else if (columnsforProcessing.size() > 0) {
/* 5670 */           ArrayList<ArrayList> processDataSecondaryAttr = processBatch(columnsforProcessing, maxResidList, maxCollecTimeList, baseAttribute, limitn, toporbottom);
/*      */           
/* 5672 */           int whereToAppend = headerAttributeIds.size();
/* 5673 */           isAppend = appendDataToOutput(processDataSecondaryAttr, outputData, headerAttributeIds, columnsforProcessing, controlHeader, whereToAppend);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*      */       String baseAttribute;
/*      */       ArrayList<String> controlHeader;
/*      */       String limitn;
/*      */       String toporbottom;
/*      */       HashMap<String, ArrayList> batchCommands;
/*      */       ManagedApplication mo;
/*      */       Iterator it;
/*      */       boolean isAppend;
/* 5685 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void filterResidsFromMaxids(ArrayList<ArrayList> outputData, ArrayList<String> maxResidList, ArrayList controlHeader) {
/*      */     try {
/* 5691 */       int indexOfResourceIDColumn = controlHeader.indexOf("monitorLink");
/* 5692 */       ArrayList<String> output_Resids = new ArrayList();
/* 5693 */       for (int i = 0; i < outputData.size(); i++) {
/* 5694 */         ArrayList singlerow = (ArrayList)outputData.get(i);
/* 5695 */         String resid = (String)singlerow.get(indexOfResourceIDColumn);
/* 5696 */         output_Resids.add(resid);
/*      */       }
/* 5698 */       maxResidList.clear();
/* 5699 */       maxResidList.addAll(output_Resids);
/*      */     } catch (Exception ex) {
/* 5701 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ArrayList<ArrayList> processBatch(ArrayList<String> columnsForQuery, ArrayList<String> maxResidList, ArrayList<String> maxCollecTimeList, String baseAttribute, String limitn, String toporbottom)
/*      */   {
/*      */     try
/*      */     {
/* 5711 */       ManagedApplication mo = new ManagedApplication();
/* 5712 */       HashMap<String, ArrayList> headerAndData = new HashMap();
/* 5713 */       ResultSet rs = null;
/* 5714 */       AMConnectionPool cp = new AMConnectionPool();
/* 5715 */       if (columnsForQuery.size() <= 0) {
/* 5716 */         return null;
/*      */       }
/* 5718 */       String columnsToQuery = "";
/* 5719 */       String datatable = null;
/* 5720 */       String resid_col = null;
/* 5721 */       String attid_col = null;
/* 5722 */       String value_col = null;
/* 5723 */       String coltime_col = null;
/* 5724 */       String expression = "";
/* 5725 */       ArrayList<String> attrExtTableDetails = null;
/* 5726 */       String process_Attribute = "-1";
/* 5727 */       String resourcetype = "";
/* 5728 */       for (int i = 0; i < columnsForQuery.size(); i++) {
/* 5729 */         process_Attribute = (String)columnsForQuery.get(i);
/* 5730 */         attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(columnsForQuery.get(i));
/* 5731 */         datatable = (String)attrExtTableDetails.get(0);
/* 5732 */         resid_col = (String)attrExtTableDetails.get(1);
/* 5733 */         attid_col = (String)attrExtTableDetails.get(2);
/* 5734 */         value_col = (String)attrExtTableDetails.get(3);
/* 5735 */         coltime_col = (String)attrExtTableDetails.get(4);
/* 5736 */         resourcetype = (String)attrExtTableDetails.get(6);
/* 5737 */         expression = (String)attrExtTableDetails.get(12);
/*      */         
/*      */ 
/* 5740 */         value_col = datatable.equalsIgnoreCase("AM_CONFIGURATION_INFO") ? value_col : DBQueryUtil.escapeColumn(value_col, process_Attribute);
/* 5741 */         if (columnsToQuery.equals(""))
/*      */         {
/* 5743 */           columnsToQuery = value_col + expression;
/*      */         } else {
/* 5745 */           columnsToQuery = columnsToQuery + "," + value_col + expression;
/*      */         }
/*      */       }
/*      */       
/* 5749 */       if (attid_col.equals("-1"))
/*      */       {
/*      */ 
/* 5752 */         String metricQuery = "select " + resid_col + "," + columnsToQuery + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/*      */         
/* 5754 */         if (resid_col.equalsIgnoreCase("RESOURCENAME")) {
/* 5755 */           metricQuery = "select RESOURCEID," + columnsToQuery + " from " + datatable + " inner join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + " where " + ManagedApplication.getCondition("RESOURCEID", maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/* 5756 */         } else if (datatable.equalsIgnoreCase("AM_CAM_DC_ATTRIBUTES")) {
/* 5757 */           String action = "getMetricQuery";
/* 5758 */           HashMap extraprops = new HashMap();
/* 5759 */           extraprops.put("maxCollecTimeList", maxCollecTimeList);
/* 5760 */           extraprops.put("maxResidList", maxResidList);
/* 5761 */           metricQuery = getSimilarAttributeForCAM_Actions(resourcetype, (String)columnsForQuery.get(0), extraprops, action);
/*      */         }
/* 5763 */         ArrayList attListinConfDataTables = ConfMonitorConfiguration.getInstance().getAttListInDataTables();
/* 5764 */         if ((columnsForQuery.contains(baseAttribute)) && (!attListinConfDataTables.contains(process_Attribute))) {
/* 5765 */           attrExtTableDetails = mo.getCachedAttributeDetails(baseAttribute);
/* 5766 */           if (datatable.equalsIgnoreCase("AM_CAM_DC_ATTRIBUTES")) {
/* 5767 */             value_col = "VALUE";
/* 5768 */             metricQuery = DBQueryUtil.orderResultsetValues(metricQuery, value_col, toporbottom);
/*      */           } else {
/* 5770 */             value_col = (String)attrExtTableDetails.get(3);
/* 5771 */             metricQuery = DBQueryUtil.orderResultsetValues(metricQuery, datatable + "." + value_col, toporbottom);
/*      */           }
/* 5773 */           metricQuery = DBQueryUtil.getTopNValues(metricQuery, limitn);
/*      */         } else {
/* 5775 */           if (datatable.equalsIgnoreCase("AM_CAM_DC_ATTRIBUTES")) {
/* 5776 */             value_col = "VALUE";
/* 5777 */             metricQuery = DBQueryUtil.orderResultsetValues(metricQuery, value_col, toporbottom);
/*      */           } else {
/* 5779 */             metricQuery = DBQueryUtil.orderResultsetValues(metricQuery, datatable + "." + value_col, toporbottom);
/*      */           }
/* 5781 */           metricQuery = DBQueryUtil.getTopNValues(metricQuery, limitn);
/*      */         }
/* 5783 */         System.out.println("processBatch:metricQuery : vector-------->" + metricQuery);
/* 5784 */         if (metricQuery != null) {
/* 5785 */           return mo.getRows(metricQuery);
/*      */         }
/*      */         
/* 5788 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5793 */       String metricQuery = "select " + resid_col + "," + attid_col + "," + value_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList) + " and " + ManagedApplication.getCondition(attid_col, columnsForQuery);
/* 5794 */       if (datatable.toUpperCase().equals("AM_CONFIGURATION_INFO"))
/*      */       {
/* 5796 */         metricQuery = "SELECT AM_CONFIGURATION_INFO." + resid_col + ",AM_CONFIGURATION_INFO." + attid_col + ",AM_CONFIGURATION_INFO.CONFVALUE from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and LATEST=1 and  " + ManagedApplication.getCondition(attid_col, columnsForQuery);
/*      */       }
/* 5798 */       if ((columnsForQuery.contains(baseAttribute)) && (!datatable.toLowerCase().contains("am_script_"))) {
/* 5799 */         metricQuery = "select " + resid_col + "," + value_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList) + " and " + ManagedApplication.getCondition(attid_col, columnsForQuery);
/* 5800 */         metricQuery = DBQueryUtil.orderResultsetValues(metricQuery, value_col, toporbottom);
/* 5801 */         metricQuery = DBQueryUtil.getTopNValues(metricQuery, limitn);
/* 5802 */         System.out.println("processBatch:metricQuery : scalar:base-------->" + metricQuery);
/* 5803 */         return mo.getRows(metricQuery);
/*      */       }
/*      */       
/* 5806 */       ArrayList<ArrayList> scalarData = mo.getRows(metricQuery);
/* 5807 */       System.out.println("processBatch:metricQuery into else: scalar-------->" + metricQuery);
/*      */       
/* 5809 */       return formatScalarAttributes(scalarData, maxResidList, columnsForQuery);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 5814 */       ex.printStackTrace(); }
/* 5815 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSimilarAttributeForCAM_Actions(String resourcetype, String camattribute, HashMap extraprops, String action)
/*      */   {
/* 5821 */     ArrayList camAttributeList = new ArrayList();
/* 5822 */     AMConnectionPool cp = new AMConnectionPool();
/* 5823 */     ResultSet rs = null;
/* 5824 */     ResultSet rs1 = null;
/* 5825 */     toreturn = null;
/* 5826 */     String dataTable = "AM_CAM_NUMERIC_DATA";
/*      */     
/* 5828 */     boolean isTypeGenericWMI = false;
/*      */     try {
/* 5830 */       String camAttribName = null;
/* 5831 */       String groupName = null;
/* 5832 */       int attribType = -1;
/* 5833 */       String temptype = resourcetype.toLowerCase();
/* 5834 */       if (temptype.indexOf("win32") != -1)
/* 5835 */         isTypeGenericWMI = true;
/*      */       String resourceid;
/* 5837 */       if ((resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP"))) {
/* 5838 */         if (action.equals("getDisplayNamesQuery")) {
/* 5839 */           ArrayList listofTabularCAMAttributes = (ArrayList)extraprops.get("listofTabularCAMAttributes");
/* 5840 */           toreturn = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID ,AM_ManagedObject.RESOURCEID from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID inner join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_CAM_DC_GROUPS.RESOURCEID where " + ManagedApplication.getCondition("AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID", listofTabularCAMAttributes);
/* 5841 */           return toreturn;
/*      */         }
/*      */         
/* 5844 */         String camattribDetailsQuery = "select ATTRIBUTENAME ,TYPE ,GROUPNAME,AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID left outer join AM_CAM_TABLE_COLUMN_MAPPER on AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID =AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + camattribute;
/* 5845 */         rs = AMConnectionPool.executeQueryStmt(camattribDetailsQuery);
/* 5846 */         if (rs.next()) {
/* 5847 */           camAttribName = rs.getString("ATTRIBUTENAME");
/* 5848 */           groupName = rs.getString("GROUPNAME");
/* 5849 */           attribType = rs.getInt("TYPE");
/* 5850 */           if (rs.getString("COLUMNATTRIBUTEID") != null) {
/* 5851 */             dataTable = "AM_CAM_COLUMNAR_DATA";
/* 5852 */           } else if (attribType == 0) {
/* 5853 */             dataTable = "AM_CAM_NUMERIC_DATA";
/* 5854 */           } else if (attribType == 1) {
/* 5855 */             dataTable = "AM_CAM_STRING_DATA";
/*      */           }
/* 5857 */           String allatribsquery = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID and AM_CAM_DC_GROUPS.GROUPNAME='" + groupName + "'  where AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME='" + camAttribName + "'";
/* 5858 */           if (resourcetype.equals("SNMP")) {
/* 5859 */             allatribsquery = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID where AM_CAM_DC_ATTRIBUTES.DISPLAYNAME in (select DISPLAYNAME from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + camattribute + ")";
/*      */           }
/* 5861 */           rs1 = AMConnectionPool.executeQueryStmt(allatribsquery);
/* 5862 */           while (rs1.next()) {
/* 5863 */             camAttributeList.add(rs1.getString("ATTRIBUTEID"));
/*      */           }
/* 5865 */           if (action.equals("getMaxTimeQuery")) {
/* 5866 */             ArrayList selectedMonitors = (ArrayList)extraprops.get("selectedMonitors");
/* 5867 */             toreturn = "select max(COLLECTIONTIME) as MaxColTime,RESOURCEID from " + dataTable + " inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + dataTable + ".ATTRIBUTEID inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID  where " + ManagedApplication.getCondition(new StringBuilder().append(dataTable).append(".ATTRIBUTEID").toString(), camAttributeList) + " and " + ManagedApplication.getCondition("RESOURCEID", selectedMonitors) + " group by " + dataTable + ".ATTRIBUTEID,RESOURCEID";
/* 5868 */           } else if (action.equals("getMetricQuery")) {
/* 5869 */             ArrayList maxCollecTimeList = (ArrayList)extraprops.get("maxCollecTimeList");
/* 5870 */             ArrayList maxResidList = (ArrayList)extraprops.get("maxResidList");
/* 5871 */             toreturn = "select  RESOURCEID,VALUE from " + dataTable + " inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + dataTable + ".ATTRIBUTEID inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID  where " + ManagedApplication.getCondition(new StringBuilder().append(dataTable).append(".ATTRIBUTEID").toString(), camAttributeList) + " and " + ManagedApplication.getCondition(new StringBuilder().append(dataTable).append(".COLLECTIONTIME ").toString(), maxCollecTimeList) + " and " + ManagedApplication.getCondition("RESOURCEID", maxResidList);
/* 5872 */             if (dataTable.equals("AM_CAM_COLUMNAR_DATA")) {
/* 5873 */               toreturn = "select  " + DBQueryUtil.concat(new String[] { "AM_CAM_TABLE_COLUMN_MAPPER.TABLEATTRIBUTEID", "'_'", "ROWID" }) + "  as RESOURCEID,VALUE from  AM_CAM_COLUMNAR_DATA inner join AM_CAM_TABLE_COLUMN_MAPPER on AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID =AM_CAM_COLUMNAR_DATA.ATTRIBUTEID where " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.ATTRIBUTEID", camAttributeList) + " and " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.COLLECTIONTIME ", maxCollecTimeList);
/*      */             }
/* 5875 */           } else if (action.equals("getRelatedAttributesQuery")) {
/* 5876 */             toreturn = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBID,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME  from   AM_CAM_DC_GROUPS  inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID where  AM_CAM_DC_GROUPS.GROUPNAME='" + groupName + "' and AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME not in ('" + camAttribName + "') group by AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME";
/* 5877 */           } else if (action.equals("getAllGroupedAttribsQuery")) {
/* 5878 */             toreturn = "select ATTRIBUTEID,RESOURCETYPE from AM_ATTRIBUTES where " + ManagedApplication.getCondition("AM_ATTRIBUTES.ATTRIBUTEID", camAttributeList);
/* 5879 */           } else if (action.equals("getAttribidforResidQuery")) {
/* 5880 */             String resourceid = (String)extraprops.get("resourceid");
/* 5881 */             toreturn = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID and RESOURCEID=" + resourceid + " where " + ManagedApplication.getCondition("AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID", camAttributeList);
/*      */           }
/*      */         }
/* 5884 */       } else if ((isTypeGenericWMI) || (resourcetype.equals("Web_Service_Operation")) || (resourcetype.equals("Web Service")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5899 */         camAttributeList.add(camattribute);
/*      */         
/* 5901 */         if (action.equals("getMaxTimeQuery")) {
/* 5902 */           ArrayList selectedMonitors = (ArrayList)extraprops.get("selectedMonitors");
/* 5903 */           toreturn = "select max(COLLECTIONTIME) as MaxColTime,ROWID as RESOURCEID from AM_CAM_COLUMNAR_DATA    where " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.ATTRIBUTEID", camAttributeList) + " and " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.ROWID", selectedMonitors) + " group by AM_CAM_COLUMNAR_DATA.ATTRIBUTEID,AM_CAM_COLUMNAR_DATA.ROWID";
/* 5904 */         } else if (action.equals("getMetricQuery")) {
/* 5905 */           ArrayList maxCollecTimeList = (ArrayList)extraprops.get("maxCollecTimeList");
/* 5906 */           ArrayList maxResidList = (ArrayList)extraprops.get("maxResidList");
/* 5907 */           toreturn = "select  ROWID  as RESOURCEID,cast(VALUE as decimal(15,2)) as VALUE from AM_CAM_COLUMNAR_DATA  where " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.ATTRIBUTEID", camAttributeList) + " and " + ManagedApplication.getCondition("AM_CAM_COLUMNAR_DATA.COLLECTIONTIME ", maxCollecTimeList) + " and " + ManagedApplication.getCondition("ROWID", maxResidList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 5913 */         else if (action.equals("getAttribidforResidQuery")) {
/* 5914 */           resourceid = (String)extraprops.get("resourceid"); } }
/* 5915 */       return "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID and RESOURCEID=" + resourceid + " where " + ManagedApplication.getCondition("AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID", camAttributeList);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 5920 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 5923 */         if (rs != null) {
/* 5924 */           rs.close();
/*      */         }
/* 5926 */         if (rs1 != null) {
/* 5927 */           rs1.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 5930 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ArrayList<ArrayList> formatScalarAttributes(ArrayList<ArrayList> processData, ArrayList<String> maxResidList, ArrayList<String> columnsForQuery)
/*      */   {
/*      */     try
/*      */     {
/* 5941 */       ArrayList<ArrayList> toreturn = new ArrayList();
/* 5942 */       HashMap<String, ArrayList> resids_data_map = new HashMap();
/* 5943 */       for (int i = 0; i < processData.size(); i++) {
/* 5944 */         ArrayList<String> rowMarker = (ArrayList)processData.get(i);
/* 5945 */         resids_data_map.put((String)rowMarker.get(0) + "_" + (String)rowMarker.get(1), rowMarker);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5950 */       for (int k = 0; k < maxResidList.size(); k++) {
/* 5951 */         String resid = (String)maxResidList.get(k);
/* 5952 */         ArrayList<String> dataToAppend = new ArrayList();
/* 5953 */         dataToAppend.add(resid);
/* 5954 */         for (int j = 0; j < columnsForQuery.size(); j++) {
/* 5955 */           String attributeId = (String)columnsForQuery.get(j);
/*      */           
/* 5957 */           ArrayList<String> singleRow = (ArrayList)resids_data_map.get(resid + "_" + attributeId);
/* 5958 */           if (singleRow != null) {
/* 5959 */             dataToAppend.add(singleRow.get(2));
/*      */           } else {
/* 5961 */             dataToAppend.add("-");
/*      */           }
/*      */         }
/* 5964 */         toreturn.add(dataToAppend);
/*      */       }
/* 5966 */       return toreturn;
/*      */     } catch (Exception ex) {
/* 5968 */       ex.printStackTrace(); }
/* 5969 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private Map<String, Float> getArchivedDataForBH(Map widgetProps, String archivedTableName, ArrayList attribslistCheckedforComplextype)
/*      */   {
/* 5975 */     ArrayList<String> selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 5976 */     return getArchivedDataForBH(widgetProps, archivedTableName, attribslistCheckedforComplextype, selectedMonitors);
/*      */   }
/*      */   
/*      */   private Map<String, Float> getArchivedDataForBH(Map widgetProps, String archivedTableName, ArrayList attribslistCheckedforComplextype, ArrayList selectedMonitors)
/*      */   {
/* 5981 */     String period = (String)widgetProps.get("period");
/* 5982 */     long[] timeStamps = ReportUtilities.getTimeStamp(period);
/* 5983 */     Map<String, List<Long>> residAverageList = new HashMap();
/* 5984 */     String reportquery = "select " + archivedTableName + ".RESID, TOTAL, TOTALCOUNT,ARCHIVEDTIME  from " + archivedTableName + "   where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".RESID").toString(), selectedMonitors) + " and " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attribslistCheckedforComplextype) + "  and MINVALUE>=0 and MAXVALUE>=0 ";
/* 5985 */     String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " order by RESID";
/* 5986 */     reportquery = reportquery + dailyRptCondition;
/* 5987 */     ResultSet bhdataSet = null;
/* 5988 */     Map<String, Float> residAvgMap = new HashMap();
/*      */     try {
/* 5990 */       bhdataSet = AMConnectionPool.executeQueryStmt(reportquery);
/* 5991 */       String businessperiod = (String)widgetProps.get("businessperiod");
/* 5992 */       Hashtable bhrDetails = SegmentReportUtil.getBusinessRule(businessperiod);
/* 5993 */       while (bhdataSet.next()) {
/* 5994 */         long collectiontime = bhdataSet.getLong("ARCHIVEDTIME");
/* 5995 */         String resid = bhdataSet.getString("RESID");
/* 5996 */         Long sum = Long.valueOf(bhdataSet.getLong("TOTAL"));
/* 5997 */         Long count = Long.valueOf(bhdataSet.getLong("TOTALCOUNT"));
/* 5998 */         if (DashboardUtil.isBHour(collectiontime, bhrDetails)) {
/* 5999 */           if (!residAverageList.containsKey(resid)) {
/* 6000 */             List<Long> sumandcountList = new ArrayList();
/* 6001 */             sumandcountList.add(sum);
/* 6002 */             sumandcountList.add(count);
/* 6003 */             residAverageList.put(resid, sumandcountList);
/*      */           } else {
/* 6005 */             List<Long> sumandcountList = (List)residAverageList.get(resid);
/* 6006 */             Long tsum = Long.valueOf(((Long)sumandcountList.get(0)).longValue() + sum.longValue());
/* 6007 */             Long tcount = Long.valueOf(((Long)sumandcountList.get(1)).longValue() + count.longValue());
/* 6008 */             sumandcountList.set(0, tsum);
/* 6009 */             sumandcountList.set(1, tcount);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 6015 */       for (Map.Entry<String, List<Long>> residRow : residAverageList.entrySet()) {
/* 6016 */         String resid = (String)residRow.getKey();
/* 6017 */         List<Long> sumandcount = (List)residRow.getValue();
/* 6018 */         float average = (float)((Long)sumandcount.get(0)).longValue() / (float)((Long)sumandcount.get(1)).longValue();
/* 6019 */         residAvgMap.put(resid, Float.valueOf(average));
/*      */       }
/*      */     }
/*      */     catch (Exception exc) {
/* 6023 */       exc.printStackTrace();
/*      */     } finally {
/* 6025 */       AMConnectionPool.closeStatement(bhdataSet);
/*      */     }
/* 6027 */     return residAvgMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap getReportData(HashMap widgetProps)
/*      */   {
/* 6037 */     HashMap widgetData = new HashMap();
/* 6038 */     ResultSet rs = null;
/* 6039 */     AMConnectionPool cp = new AMConnectionPool();
/* 6040 */     boolean isHealthorAvailSelected = false;
/*      */     try
/*      */     {
/* 6043 */       ArrayList<String> selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 6044 */       String toporBottom_N = (String)widgetProps.get("toporBottom_N");
/* 6045 */       String topOrBottom = (String)widgetProps.get("toporBottom");
/* 6046 */       if ((toporBottom_N == null) || (toporBottom_N.isEmpty())) {
/* 6047 */         toporBottom_N = "-1";
/*      */       }
/* 6049 */       String baseAttribute = (String)widgetProps.get("baseAttribute");
/* 6050 */       ArrayList relatedAttributeList = (ArrayList)widgetProps.get("relatedAttributeList");
/* 6051 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 6052 */       ArrayList baseList = new ArrayList();
/* 6053 */       baseList.add(baseAttribute);
/* 6054 */       ArrayList allAttributes = (ArrayList)widgetProps.get("allAttributes");
/* 6055 */       String resourcetype = (String)widgetProps.get("resourcetype");
/*      */       
/* 6057 */       String period = (String)widgetProps.get("period");
/* 6058 */       String businessperiod = (String)widgetProps.get("businessperiod");
/* 6059 */       ReportUtilities rep = new ReportUtilities();
/* 6060 */       ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(baseAttribute);
/*      */       
/*      */ 
/* 6063 */       HashMap residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/* 6064 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 6065 */       ArrayList<String> headerAttributeIds = new ArrayList();
/* 6066 */       ArrayList<String> output_Resids = new ArrayList();
/* 6067 */       headerAttributeIds.add("am.mypage.monitorname.text");
/* 6068 */       controlHeader.add("monitorLink");
/* 6069 */       int widgettype = Integer.parseInt((String)widgetProps.get("widgettype"));
/* 6070 */       String widgetid = (String)widgetProps.get("widgetid");
/* 6071 */       int monitorSelectionType = -1;
/* 6072 */       if (widgettype == 2) {
/* 6073 */         monitorSelectionType = Integer.parseInt((String)widgetProps.get("monitorSelectionType"));
/*      */       }
/*      */       
/* 6076 */       if ((widgettype == 2) && (monitorSelectionType == 2)) {
/* 6077 */         HashMap<String, ArrayList<String>> selectedResidattribsMap = new HashMap();
/* 6078 */         selectedResidattribsMap = getSelResAttrForAllTypes(widgetid);
/* 6079 */         HashMap widgetDataForAll = new HashMap();
/* 6080 */         for (Map.Entry<String, ArrayList<String>> entry : selectedResidattribsMap.entrySet()) {
/* 6081 */           String resId = (String)entry.getKey();
/* 6082 */           String resDispName = DBUtil.getDisplaynameforResourceID(resId);
/* 6083 */           ArrayList<String> attList = (ArrayList)entry.getValue();
/* 6084 */           for (int k = 0; k < attList.size(); k++) {
/* 6085 */             String attrId = (String)attList.get(k);
/* 6086 */             String opData = null;
/* 6087 */             attrExtTableDetails = this.mo.getCachedAttributeDetails(attrId);
/* 6088 */             ArrayList attribslistCheckedforComplextype = new ArrayList();
/* 6089 */             HashMap restypeAttribidMapping = new HashMap();
/* 6090 */             getAttribIdsforResourceType(resourcetype, attrId, attribslistCheckedforComplextype, restypeAttribidMapping);
/* 6091 */             String archivedTableName = (String)attrExtTableDetails.get(7);
/* 6092 */             if (businessperiod != null) {
/* 6093 */               ArrayList residList = new ArrayList();
/* 6094 */               residList.add(resId);
/* 6095 */               Map<String, Float> bhData = getArchivedDataForBH(widgetProps, archivedTableName, attribslistCheckedforComplextype, residList);
/* 6096 */               if ((bhData != null) && (!bhData.isEmpty())) {
/* 6097 */                 opData = String.valueOf(bhData.get(resId));
/*      */               }
/*      */             } else {
/* 6100 */               opData = getOutPutAverageData(attrId, resId, period, toporBottom_N);
/*      */             }
/*      */             
/* 6103 */             attrExtTableDetails = this.mo.getCachedAttributeDetails(attrId);
/* 6104 */             String attrDispName = FormatUtil.getString((String)attrExtTableDetails.get(5));
/* 6105 */             String units = (String)attrExtTableDetails.get(8);
/* 6106 */             if (!units.equals(""))
/*      */             {
/* 6108 */               units = "(" + FormatUtil.getString(units) + ")";
/*      */             }
/* 6110 */             if ((opData == null) || (opData.equals(""))) {
/* 6111 */               opData = "-";
/*      */             } else {
/* 6113 */               opData = opData + units;
/*      */             }
/*      */             
/* 6116 */             String dispValue = "";
/* 6117 */             String tempStr = resId + "-" + attrId;
/* 6118 */             if ((resDispName != null) && (!resDispName.equals("")) && (attrDispName != null)) {
/* 6119 */               dispValue = resDispName + "#" + attrDispName;
/*      */             }
/* 6121 */             widgetDataForAll.put(dispValue, opData + "#" + tempStr);
/*      */           }
/*      */         }
/* 6124 */         widgetData.put("widgetDataForAll", widgetDataForAll);
/* 6125 */         widgetData.put("widgetDataForAll", widgetDataForAll);
/* 6126 */         widgetData.put("metricdata", outputData);
/* 6127 */         widgetData.put("header", headerAttributeIds);
/* 6128 */         widgetData.put("baseAttribute", baseAttribute);
/* 6129 */         widgetData.put("resourceIds", output_Resids);
/* 6130 */         widgetData.put("residDisplayNames", residDisplayNames);
/*      */       } else {
/* 6132 */         String archivedTableName = (String)attrExtTableDetails.get(7);
/* 6133 */         long[] timeStamps = null;
/* 6134 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*      */         
/* 6136 */         ArrayList attribslistCheckedforComplextype = new ArrayList();
/* 6137 */         HashMap restypeAttribidMapping = new HashMap();
/* 6138 */         getAttribIdsforResourceType(resourcetype, baseAttribute, attribslistCheckedforComplextype, restypeAttribidMapping);
/* 6139 */         widgetData.put("attribslistCheckedforComplextype", attribslistCheckedforComplextype);
/*      */         
/* 6141 */         String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/* 6142 */         String reportquery = "select " + archivedTableName + ".RESID,CAST(sum(TOTAL)/sum(TOTALCOUNT) as decimal (15,2)) as Average from " + archivedTableName + "   where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".RESID").toString(), selectedMonitors) + " and " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attribslistCheckedforComplextype) + "  and MINVALUE>=0 and MAXVALUE>=0 ";
/* 6143 */         String groupbyStr = "  group by RESID," + archivedTableName + ".ATTRIBUTEID ";
/*      */         long limit;
/* 6145 */         if (businessperiod != null)
/*      */         {
/* 6147 */           Map<String, Float> bhData = getArchivedDataForBH(widgetProps, archivedTableName, attribslistCheckedforComplextype);
/*      */           
/* 6149 */           String widgetType = (String)widgetProps.get("widgettype");
/* 6150 */           if ((widgetType.equals("1")) || (widgetType.equals("2")) || (widgetType.equals("3"))) {
/* 6151 */             TreeMap sortedMap = new TreeMap(new ValueComparator(bhData, topOrBottom));
/* 6152 */             sortedMap.putAll(bhData);
/* 6153 */             bhData = sortedMap;
/*      */           }
/*      */           
/* 6156 */           limit = Long.parseLong(toporBottom_N);
/* 6157 */           for (Map.Entry<String, Float> residRow : bhData.entrySet()) {
/* 6158 */             if ((limit != -1L) && (outputData.size() >= limit)) {
/*      */               break;
/*      */             }
/* 6161 */             String resid = (String)residRow.getKey();
/* 6162 */             Float average = (Float)residRow.getValue();
/* 6163 */             ArrayList residAvgList = new ArrayList();
/* 6164 */             residAvgList.add(resid);
/* 6165 */             residAvgList.add(String.valueOf(average));
/* 6166 */             outputData.add(residAvgList);
/* 6167 */             output_Resids.add(resid);
/*      */           }
/*      */         }
/*      */         else {
/* 6171 */           reportquery = reportquery + dailyRptCondition + groupbyStr;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6177 */           controlHeader.add("wholenumber");
/*      */           
/*      */ 
/* 6180 */           reportquery = getReportQueryForComplexAttribute(reportquery, attribslistCheckedforComplextype, resourcetype, topOrBottom);
/* 6181 */           reportquery = DBQueryUtil.getTopNValues(reportquery, toporBottom_N);
/* 6182 */           System.out.println("getReportData:baseattribute:reportquery---->" + reportquery);
/* 6183 */           rs = AMConnectionPool.executeQueryStmt(reportquery);
/* 6184 */           while (rs.next())
/*      */           {
/*      */ 
/* 6187 */             ArrayList templist = new ArrayList();
/* 6188 */             templist.add(rs.getString("RESID"));
/* 6189 */             templist.add(rs.getString("Average"));
/* 6190 */             outputData.add(templist);
/* 6191 */             output_Resids.add(rs.getString("RESID"));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 6196 */         headerAttributeIds.add(baseAttribute);
/* 6197 */         controlHeader.add("wholenumber");
/*      */         
/* 6199 */         for (int i = 0; i < relatedAttributeList.size(); i++) {
/*      */           try {
/* 6201 */             String realtedattributeid = (String)relatedAttributeList.get(i);
/* 6202 */             attribslistCheckedforComplextype = new ArrayList();
/* 6203 */             restypeAttribidMapping = new HashMap();
/* 6204 */             getAttribIdsforResourceType(resourcetype, realtedattributeid, attribslistCheckedforComplextype, restypeAttribidMapping);
/*      */             
/* 6206 */             attrExtTableDetails = this.mo.getCachedAttributeDetails(realtedattributeid);
/* 6207 */             archivedTableName = (String)attrExtTableDetails.get(7);
/*      */             
/*      */ 
/* 6210 */             String attribute_type = (String)attrExtTableDetails.get(9);
/* 6211 */             if (attribute_type.equals("1"))
/*      */             {
/* 6213 */               widgetProps.put("availabilityid", realtedattributeid);
/* 6214 */               isHealthorAvailSelected = true;
/*      */ 
/*      */             }
/* 6217 */             else if (attribute_type.equals("2"))
/*      */             {
/* 6219 */               widgetProps.put("healthid", realtedattributeid);
/* 6220 */               isHealthorAvailSelected = true;
/*      */             }
/*      */             else {
/* 6223 */               ArrayList<String> columnsforProcessing = new ArrayList();
/* 6224 */               columnsforProcessing.add(realtedattributeid);
/*      */               
/*      */ 
/*      */ 
/* 6228 */               ArrayList<ArrayList> processData = new ArrayList();
/* 6229 */               String expression = attrExtTableDetails.get(12) != null ? (String)attrExtTableDetails.get(12) : "";
/* 6230 */               String residconditoin = " and " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".RESID").toString(), output_Resids);
/* 6231 */               dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/* 6232 */               reportquery = "select " + archivedTableName + ".RESID,(CAST(sum(TOTAL)/sum(TOTALCOUNT) as decimal (15,2)))" + expression + " as Average from " + archivedTableName + "  where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".RESID").toString(), output_Resids) + "and " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attribslistCheckedforComplextype) + " and MINVALUE>=0 and MAXVALUE>=0 ";
/* 6233 */               groupbyStr = "  group by RESID," + archivedTableName + ".ATTRIBUTEID order by Average desc ";
/* 6234 */               reportquery = reportquery + dailyRptCondition + groupbyStr;
/* 6235 */               System.out.println("getReportData:relatedattributes:reportquery---->" + reportquery);
/* 6236 */               if (businessperiod != null) {
/* 6237 */                 Map<String, Float> bhData = getArchivedDataForBH(widgetProps, archivedTableName, attribslistCheckedforComplextype, output_Resids);
/* 6238 */                 for (Map.Entry<String, Float> residRow : bhData.entrySet()) {
/* 6239 */                   String resid = (String)residRow.getKey();
/* 6240 */                   float average = ((Float)residRow.getValue()).floatValue();
/* 6241 */                   ArrayList residAvgList = new ArrayList();
/* 6242 */                   residAvgList.add(resid);
/* 6243 */                   residAvgList.add(String.format("%.02f", new Object[] { Float.valueOf(average) }));
/* 6244 */                   processData.add(residAvgList);
/*      */                 }
/*      */               } else {
/* 6247 */                 AMLog.debug("getReportData:relatedattributes:reportquery---->" + reportquery);
/* 6248 */                 processData = this.mo.getRows(reportquery);
/*      */               }
/*      */               
/*      */ 
/* 6252 */               int whereToAppend = headerAttributeIds.size();
/*      */               
/* 6254 */               appendDataToOutput(processData, outputData, headerAttributeIds, columnsforProcessing, controlHeader, whereToAppend);
/*      */             }
/* 6256 */           } catch (Exception ex) { ex.printStackTrace();
/*      */           }
/*      */         }
/* 6259 */         if ((isHealthorAvailSelected) && (outputData != null) && (outputData.size() > 0))
/*      */         {
/*      */ 
/* 6262 */           widgetProps.put("appendWhere", "first");
/* 6263 */           widgetProps.put("noLabels", "true");
/* 6264 */           widgetProps.put("outputData", outputData);
/* 6265 */           widgetProps.put("headerAttributeIds", headerAttributeIds);
/* 6266 */           getAvailHealth(widgetProps);
/*      */         }
/*      */         
/* 6269 */         widgetData.put("metricdata", outputData);
/* 6270 */         widgetData.put("header", headerAttributeIds);
/* 6271 */         widgetData.put("baseAttribute", baseAttribute);
/* 6272 */         widgetData.put("resourceIds", output_Resids);
/* 6273 */         widgetData.put("residDisplayNames", residDisplayNames);
/* 6274 */         filterResidsFromMaxids(outputData, output_Resids, controlHeader);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 6278 */       ex.printStackTrace();
/*      */     }
/* 6280 */     return widgetData;
/*      */   }
/*      */   
/*      */   private String getReportQueryForComplexAttribute(String reportquery, ArrayList attribslistCheckedforComplextype, String resourcetype)
/*      */   {
/* 6285 */     return getReportQueryForComplexAttribute(reportquery, attribslistCheckedforComplextype, resourcetype, null);
/*      */   }
/*      */   
/*      */   private String getReportQueryForComplexAttribute(String reportquery, ArrayList attribslistCheckedforComplextype, String resourcetype, String topOrBottom) {
/* 6289 */     String modifiedReportQuery = "";
/*      */     
/*      */ 
/* 6292 */     ArrayList listofReportDataTables = new ArrayList();
/*      */     try {
/* 6294 */       if ((resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All"))) {
/* 6295 */         for (int i = 0; i < attribslistCheckedforComplextype.size(); i++) {
/* 6296 */           String attributeid = (String)attribslistCheckedforComplextype.get(i);
/* 6297 */           ArrayList attrExtTableDetails = this.mo.getCachedAttributeDetails(attributeid);
/* 6298 */           if (attrExtTableDetails != null) {
/* 6299 */             String reportDataTable = (String)attrExtTableDetails.get(7);
/* 6300 */             if (!listofReportDataTables.contains(reportDataTable))
/*      */             {
/*      */ 
/* 6303 */               listofReportDataTables.add(reportDataTable);
/* 6304 */               if (modifiedReportQuery.equals("")) {
/* 6305 */                 modifiedReportQuery = reportquery.replaceAll("AM_RESPONSETIME_MinMaxAvgData", reportDataTable);
/*      */               } else
/* 6307 */                 modifiedReportQuery = modifiedReportQuery + " union (" + reportquery.replaceAll("AM_RESPONSETIME_MinMaxAvgData", reportDataTable) + ")";
/*      */             }
/*      */           }
/*      */         }
/* 6311 */         modifiedReportQuery = modifiedReportQuery + " ORDER BY Average DSEC";
/*      */       } else {
/* 6313 */         modifiedReportQuery = reportquery + " ORDER BY Average DESC";
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6319 */       ex.printStackTrace();
/*      */     }
/* 6321 */     return modifiedReportQuery;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap getAvailHealth(HashMap widgetProps)
/*      */   {
/* 6331 */     HashMap widgetData = new HashMap();
/*      */     try {
/* 6333 */       if (widgetProps.get("metaInfoInOrder") == null) {
/* 6334 */         widgetProps.put("metaInfoInOrder", new ArrayList());
/*      */       }
/* 6336 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 6337 */       ArrayList<String> headerAttributeIds = (ArrayList)widgetProps.get("headerAttributeIds");
/* 6338 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 6339 */       ArrayList<ArrayList> outputData = (ArrayList)widgetProps.get("outputData");
/* 6340 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 6341 */       ArrayList allAttributes = new ArrayList();
/* 6342 */       ArrayList availandhealthAttribs = new ArrayList();
/* 6343 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 6344 */       String availabilityid = (String)widgetProps.get("availabilityid");
/* 6345 */       String healthid = (String)widgetProps.get("healthid");
/*      */       
/* 6347 */       boolean isHealthIdsinWidgetProps = false;
/* 6348 */       boolean isAvailIdsinWidgetProps = false;
/* 6349 */       ArrayList health_attribids = new ArrayList();
/* 6350 */       ArrayList avail_attribids = new ArrayList();
/* 6351 */       boolean isSecondlevelAttribute = false;
/* 6352 */       String attributelevel = (String)widgetProps.get("attributelevel");
/* 6353 */       if ((attributelevel != null) && (attributelevel.equals("2")))
/*      */       {
/*      */ 
/*      */ 
/* 6357 */         isSecondlevelAttribute = true;
/*      */       }
/*      */       
/* 6360 */       if (healthid != null) {
/* 6361 */         isHealthIdsinWidgetProps = true;
/* 6362 */         health_attribids = new ArrayList();
/* 6363 */         health_attribids.add(healthid);
/*      */         
/* 6365 */         if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")))
/*      */         {
/* 6367 */           HashMap attibProps = getAvailandHealthAttribs(resourcetype);
/* 6368 */           health_attribids = (ArrayList)attibProps.get("health_attribids");
/* 6369 */           allAttributes.addAll(health_attribids);
/*      */         }
/* 6371 */         allAttributes.add(healthid);
/*      */       }
/* 6373 */       if (availabilityid != null) {
/* 6374 */         isAvailIdsinWidgetProps = true;
/* 6375 */         avail_attribids = new ArrayList();
/* 6376 */         avail_attribids.add(availabilityid);
/* 6377 */         allAttributes.add(availabilityid);
/* 6378 */         if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("$ComplexType_NetworkDevices")) || (resourcetype.equals("$ComplexType_StorageDevices")) || (resourcetype.equals("$ComplexType_AllApps")) || (resourcetype.equals("$ComplexType_VC_HAI")))
/*      */         {
/* 6380 */           HashMap attibProps = getAvailandHealthAttribs(resourcetype);
/* 6381 */           avail_attribids = (ArrayList)attibProps.get("avail_attribids");
/* 6382 */           allAttributes.addAll(avail_attribids);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 6388 */       if ((!isHealthIdsinWidgetProps) && (!isAvailIdsinWidgetProps)) {
/* 6389 */         HashMap attibProps = getAvailandHealthAttribs(resourcetype);
/* 6390 */         health_attribids = (ArrayList)attibProps.get("health_attribids");
/* 6391 */         avail_attribids = (ArrayList)attibProps.get("avail_attribids");
/* 6392 */         allAttributes.addAll(avail_attribids);
/* 6393 */         allAttributes.addAll(health_attribids);
/*      */         
/*      */ 
/* 6396 */         isHealthIdsinWidgetProps = true;
/* 6397 */         isAvailIdsinWidgetProps = true;
/*      */       }
/*      */       
/* 6400 */       ArrayList<HashMap> processMetaInfoInOrder = new ArrayList();
/* 6401 */       ArrayList<String> columnsforProcessing = new ArrayList();
/* 6402 */       ArrayList<ArrayList> tempOutputData = new ArrayList();
/*      */       
/*      */ 
/*      */ 
/* 6406 */       HashMap residTypeMapping = (HashMap)widgetProps.get("residTypeMapping");
/*      */       
/*      */ 
/* 6409 */       if ((isAvailIdsinWidgetProps) && 
/* 6410 */         (avail_attribids.size() > 0)) {
/* 6411 */         columnsforProcessing.add((String)avail_attribids.get(0));
/*      */       }
/*      */       
/* 6414 */       if ((isHealthIdsinWidgetProps) && 
/* 6415 */         (health_attribids.size() > 0)) {
/* 6416 */         columnsforProcessing.add((String)health_attribids.get(0));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 6422 */       HashMap residDisplayNames = null;
/* 6423 */       if ((selectedMonitors != null) && (selectedMonitors.size() > 0)) {
/* 6424 */         residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/*      */       }
/* 6426 */       Properties alert = null;
/* 6427 */       if ((resourcetype != null) && (resourcetype.equalsIgnoreCase("HAI"))) {
/* 6428 */         alert = FaultUtil.getStatus((ArrayList)widgetProps.get("entitylist"), false);
/*      */       }
/*      */       else {
/* 6431 */         alert = FaultUtil.getStatus(selectedMonitors, allAttributes);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6441 */       for (int j = 0; j < selectedMonitors.size(); j++) {
/* 6442 */         String resid = (String)selectedMonitors.get(j);
/* 6443 */         ArrayList<String> singlerow = new ArrayList(3);
/* 6444 */         singlerow.add(resid);
/*      */         
/*      */ 
/*      */ 
/* 6448 */         HashMap rowInfo = new HashMap();
/* 6449 */         processMetaInfoInOrder.add(rowInfo);
/* 6450 */         rowInfo.put("resourceidforalerts", resid);
/* 6451 */         if (isAvailIdsinWidgetProps) {
/* 6452 */           if (!isSecondlevelAttribute) {
/* 6453 */             availabilityid = getAvailIdForResourceType(resid, resourcetype, residTypeMapping, widgetProps);
/*      */           }
/*      */           
/* 6456 */           String availstatus = alert.getProperty(resid + "#" + availabilityid);
/* 6457 */           singlerow.add(availstatus);
/* 6458 */           String availmessage = alert.getProperty(resid + "#" + availabilityid + "#" + "MESSAGE");
/* 6459 */           rowInfo.put("availstatus", availstatus);
/* 6460 */           rowInfo.put("availmessage", availmessage);
/* 6461 */           rowInfo.put("availabilityid", availabilityid);
/*      */         }
/*      */         
/*      */ 
/* 6465 */         if (isHealthIdsinWidgetProps) {
/* 6466 */           if (!isSecondlevelAttribute) {
/* 6467 */             healthid = getHealthIdForResourceType(resid, resourcetype, residTypeMapping, widgetProps);
/*      */           }
/* 6469 */           String healthstatus = alert.getProperty(resid + "#" + healthid);
/* 6470 */           singlerow.add(healthstatus);
/*      */           
/* 6472 */           String alertmessage = alert.getProperty(resid + "#" + healthid + "#" + "MESSAGE");
/* 6473 */           rowInfo.put("healthstatus", healthstatus);
/* 6474 */           rowInfo.put("alertmessage", alertmessage);
/* 6475 */           rowInfo.put("healthid", healthid);
/*      */         }
/*      */         
/* 6478 */         tempOutputData.add(singlerow);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 6483 */       String appendWhere = (String)widgetProps.get("appendWhere");
/* 6484 */       int whereToAppend = headerAttributeIds.size();
/* 6485 */       if (appendWhere.equals("first"))
/*      */       {
/* 6487 */         whereToAppend = 0;
/* 6488 */       } else if ((appendWhere.equals("afterMonitorNameColumn")) && (((String)headerAttributeIds.get(0)).equals("Monitor Name"))) {
/* 6489 */         whereToAppend = 1;
/*      */       }
/*      */       
/* 6492 */       appendDataToOutput(tempOutputData, outputData, headerAttributeIds, columnsforProcessing, controlHeader, whereToAppend, processMetaInfoInOrder, metaInfoInOrder);
/* 6493 */       widgetData.put("metricdata", outputData);
/*      */       
/* 6495 */       widgetData.put("header", headerAttributeIds);
/*      */       
/* 6497 */       widgetData.put("resourceIds", selectedMonitors);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6501 */       ex.printStackTrace();
/*      */     }
/* 6503 */     return widgetData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getAvailIdForResourceType(String resid, String resourcetype, HashMap residRestypeMapping, HashMap widgetProps)
/*      */   {
/* 6513 */     String availid = "-1";
/* 6514 */     Hashtable availabilitykeys = (Hashtable)widgetProps.get("availabilitykeys");
/*      */     try {
/* 6516 */       if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("$ComplexType_NetworkDevices")) || (resourcetype.equals("$ComplexType_StorageDevices")) || (resourcetype.equals("$ComplexType_AllApps")) || (resourcetype.equals("$ComplexType_VC_HAI")) || (resourcetype.equals("$ComplexType_AllSers")))
/*      */       {
/* 6518 */         resourcetype = (String)residRestypeMapping.get(resid);
/* 6519 */         return (String)availabilitykeys.get(resourcetype);
/*      */       }
/*      */       
/*      */ 
/* 6523 */       return (String)availabilitykeys.get(resourcetype);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6527 */       ex.printStackTrace();
/*      */     }
/* 6529 */     return availid;
/*      */   }
/*      */   
/*      */   private String getHealthIdForResourceType(String resid, String resourcetype, HashMap residRestypeMapping, HashMap widgetProps) {
/* 6533 */     String healthid = "-1";
/* 6534 */     Hashtable healthkeys = (Hashtable)widgetProps.get("healthkeys");
/*      */     try {
/* 6536 */       if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("$ComplexType_NetworkDevices")) || (resourcetype.equals("$ComplexType_StorageDevices")) || (resourcetype.equals("$ComplexType_AllApps")) || (resourcetype.equals("$ComplexType_VC_HAI")) || (resourcetype.equals("$ComplexType_AllSers")))
/*      */       {
/* 6538 */         resourcetype = (String)residRestypeMapping.get(resid);
/* 6539 */         return (String)healthkeys.get(resourcetype);
/*      */       }
/*      */       
/* 6542 */       return (String)healthkeys.get(resourcetype);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6546 */       ex.printStackTrace();
/*      */     }
/* 6548 */     return healthid;
/*      */   }
/*      */   
/*      */   public String getResourceTypesForComplexType(String resourceType) {
/* 6552 */     String toReturn = resourceType;
/*      */     try {
/* 6554 */       toReturn = getResourceTypesForComplexTypeCondition(resourceType, "").trim();
/* 6555 */       toReturn = toReturn.replaceAll("in (", "").replaceAll(")", "").replaceAll("'", "").trim();
/* 6556 */       if (toReturn.length() == 0) {
/* 6557 */         toReturn = "-";
/*      */       }
/*      */     } catch (Exception e) {
/* 6560 */       e.printStackTrace();
/*      */     }
/* 6562 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getResourceTypesForComplexTypeCondition(String resourceType, String resourcetypecolumn)
/*      */   {
/* 6569 */     String condition = " " + resourcetypecolumn + " in ('" + resourceType + "')";
/*      */     try {
/* 6571 */       if (resourceType.equals("$ComplexType_Windows"))
/*      */       {
/*      */ 
/* 6574 */         condition = resourcetypecolumn + " in ('Windows 2000','Windows XP','Windows 2003','Windows Vista','Windows 2008','Windows 7','Windows 8','Windows 10','Windows 2012')";
/* 6575 */       } else if (resourceType.equals("$ComplexType_Servers"))
/*      */       {
/* 6577 */         condition = resourcetypecolumn + " in " + com.adventnet.appmanager.util.Constants.serverTypes;
/* 6578 */       } else if (resourceType.equals("$ComplexType_All"))
/*      */       {
/* 6580 */         condition = resourcetypecolumn + " in " + com.adventnet.appmanager.util.Constants.resourceTypes;
/* 6581 */       } else if (resourceType.equals("$ComplexType_NetworkDevices"))
/*      */       {
/* 6583 */         condition = resourcetypecolumn + getNWDSubGroups();
/* 6584 */       } else if (resourceType.equals("$ComplexType_StorageDevices"))
/*      */       {
/* 6586 */         condition = resourcetypecolumn + getSANSubGroups();
/* 6587 */       } else if (resourceType.equals("$ComplexType_AllApps"))
/*      */       {
/* 6589 */         if (com.adventnet.appmanager.util.Constants.getAppsSubGroups() == null) {
/* 6590 */           condition = resourcetypecolumn + " in ('')";
/*      */         } else {
/* 6592 */           condition = resourcetypecolumn + com.adventnet.appmanager.util.Constants.getAppsSubGroups();
/*      */         }
/* 6594 */       } else if (resourceType.equals("$ComplexType_VC_HAI")) {
/* 6595 */         condition = resourcetypecolumn + " in ('HAI')";
/* 6596 */       } else if (resourceType.equals("$ComplexType_AllSers")) {
/* 6597 */         condition = resourcetypecolumn + " in " + com.adventnet.appmanager.util.Constants.allServerTypes;
/* 6598 */       } else if (resourceType.equals("VirtualMachine")) {
/* 6599 */         condition = resourcetypecolumn + " in ('VirtualMachine', 'XenServerVM')";
/*      */       }
/*      */     } catch (Exception ex) {
/* 6602 */       ex.printStackTrace();
/*      */     }
/* 6604 */     return condition;
/*      */   }
/*      */   
/*      */   private HashMap getAvailandHealthAttribs(String resourcetype)
/*      */   {
/* 6609 */     ArrayList avail_attribids = new ArrayList();
/* 6610 */     ArrayList health_attribids = new ArrayList();
/* 6611 */     HashMap toreturn = new HashMap();
/* 6612 */     ResultSet rs = null;
/*      */     
/* 6614 */     AMConnectionPool con = new AMConnectionPool();
/*      */     try {
/* 6616 */       String resourcetypecondition = getResourceTypesForComplexTypeCondition(resourcetype, "RESOURCETYPE");
/* 6617 */       String query = "select ATTRIBUTEID,TYPE from AM_ATTRIBUTES where (TYPE=1 or TYPE=2) and " + resourcetypecondition;
/*      */       
/* 6619 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6620 */       while (rs.next()) {
/* 6621 */         String attid = rs.getString("ATTRIBUTEID");
/* 6622 */         int type = rs.getInt("TYPE");
/* 6623 */         if (type == 1) {
/* 6624 */           avail_attribids.add(attid);
/* 6625 */         } else if (type == 2) {
/* 6626 */           health_attribids.add(attid);
/*      */         }
/*      */       }
/* 6629 */       rs.close();
/*      */     } catch (Exception ex) {
/* 6631 */       ex.printStackTrace();
/*      */     }
/* 6633 */     toreturn.put("avail_attribids", avail_attribids);
/* 6634 */     toreturn.put("health_attribids", health_attribids);
/* 6635 */     return toreturn;
/*      */   }
/*      */   
/*      */   private HashMap getAvailHealthHistory(HashMap widgetProps) {
/* 6639 */     HashMap widgetData = new HashMap();
/*      */     try {
/* 6641 */       ArrayList allAttributes = new ArrayList();
/*      */       
/* 6643 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 6644 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 6645 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/*      */       
/* 6647 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 6648 */       String period = (String)widgetProps.get("period");
/*      */       
/* 6650 */       controlHeader.add("monitorLink");
/* 6651 */       controlHeader.add("availabilitybar");
/*      */       
/* 6653 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 6654 */       ArrayList<String> headerAttributeIds = new ArrayList();
/*      */       
/* 6656 */       headerAttributeIds.add("am.mypage.monitorname.text");
/* 6657 */       if (chkForAvailability(widgetProps)) {
/* 6658 */         headerAttributeIds.add("availabilitybar");
/*      */       }
/* 6660 */       if (chkForAvailability(widgetProps)) {
/* 6661 */         for (int i = 0; i < selectedMonitors.size(); i++) {
/* 6662 */           String resid = (String)selectedMonitors.get(i);
/* 6663 */           Properties moPropRep = moPropRep = ReportUtilities.getMonitorGroupAvailability(resid, period);
/* 6664 */           ArrayList<String> singlerow = new ArrayList(3);
/* 6665 */           singlerow.add(resid);
/* 6666 */           singlerow.add("avail");
/* 6667 */           outputData.add(singlerow);
/* 6668 */           HashMap rowInfo = new HashMap();
/* 6669 */           rowInfo.put("overAllAvailability", moPropRep);
/* 6670 */           metaInfoInOrder.add(rowInfo);
/*      */         }
/*      */       }
/* 6673 */       widgetData.put("metricdata", outputData);
/* 6674 */       widgetData.put("header", headerAttributeIds);
/* 6675 */       widgetData.put("resourceIds", selectedMonitors);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 6681 */       ex.printStackTrace();
/*      */     }
/* 6683 */     return widgetData;
/*      */   }
/*      */   
/*      */   private HashMap getThresholdBreakers(HashMap widgetProps) {
/* 6687 */     HashMap widgetData = new HashMap();
/*      */     try {
/* 6689 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 6690 */       ArrayList allAttributes = new ArrayList();
/* 6691 */       ArrayList attributesForProcessing = new ArrayList();
/* 6692 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 6693 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 6694 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/*      */       
/* 6696 */       String baseAttribute = (String)widgetProps.get("baseAttribute");
/* 6697 */       controlHeader.add("attributehealth");
/* 6698 */       controlHeader.add("monitorLink");
/* 6699 */       if (!EnterpriseUtil.isAdminServer()) {
/* 6700 */         controlHeader.add("thresholdLink");
/*      */       }
/*      */       
/*      */ 
/* 6704 */       ArrayList<String> headerAttributeIds = new ArrayList();
/* 6705 */       headerAttributeIds.add("AttribHealthStatus");
/* 6706 */       headerAttributeIds.add("Monitor Name");
/*      */       
/* 6708 */       if (!EnterpriseUtil.isAdminServer()) {
/* 6709 */         headerAttributeIds.add("ThresholdName");
/*      */       }
/*      */       
/* 6712 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/*      */       
/* 6714 */       HashMap residDisplayNames = null;
/*      */       
/* 6716 */       if ((selectedMonitors != null) && (selectedMonitors.size() > 0)) {
/* 6717 */         residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 6723 */       ArrayList attribslistCheckedforComplextype = new ArrayList();
/* 6724 */       HashMap restypeAttribidMapping = new HashMap();
/* 6725 */       getAttribIdsforResourceType(resourcetype, baseAttribute, attribslistCheckedforComplextype, restypeAttribidMapping);
/* 6726 */       allAttributes.addAll(attribslistCheckedforComplextype);
/* 6727 */       attributesForProcessing.add(baseAttribute);
/* 6728 */       HashMap thresholdMapping = getThresholdsForResources(selectedMonitors, attribslistCheckedforComplextype);
/* 6729 */       Properties alert = FaultUtil.getStatus(selectedMonitors, allAttributes);
/*      */       
/*      */ 
/* 6732 */       for (int i = 0; i < selectedMonitors.size(); i++) {
/* 6733 */         String resid = (String)selectedMonitors.get(i);
/* 6734 */         String attributeForRow = getAttribIdForResourceType(resid, baseAttribute, resourcetype, restypeAttribidMapping, widgetProps);
/* 6735 */         String healthstatus = alert.getProperty(resid + "#" + attributeForRow);
/*      */         
/*      */ 
/*      */ 
/* 6739 */         if ((healthstatus != null) && ((healthstatus.equals("1")) || (healthstatus.equals("4"))))
/*      */         {
/* 6741 */           HashMap thresholdforResid = (HashMap)thresholdMapping.get(resid);
/* 6742 */           String thresholdid; String thesholdname; String thresholdid; if (thresholdforResid != null) {
/* 6743 */             String thesholdname = (String)thresholdforResid.get("thresholdname");
/* 6744 */             thresholdid = (String)thresholdforResid.get("thresholdid");
/*      */           }
/*      */           else {
/* 6747 */             thesholdname = "-";
/* 6748 */             thresholdid = "-";
/*      */           }
/*      */           
/* 6751 */           ArrayList<String> singlerow = new ArrayList(3);
/* 6752 */           singlerow.add(healthstatus);
/* 6753 */           singlerow.add(resid);
/* 6754 */           if (!EnterpriseUtil.isAdminServer()) {
/* 6755 */             singlerow.add(thesholdname);
/*      */           }
/*      */           
/* 6758 */           outputData.add(singlerow);
/* 6759 */           HashMap rowInfo = new HashMap();
/* 6760 */           String alertmessage = alert.getProperty(resid + "#" + attributeForRow + "#" + "MESSAGE");
/* 6761 */           rowInfo.put("resourceid", resid);
/* 6762 */           rowInfo.put("rowResourceid", resid);
/* 6763 */           rowInfo.put("attributeid", attributeForRow);
/* 6764 */           rowInfo.put("alertmessage", alertmessage);
/* 6765 */           rowInfo.put("healthstatus", healthstatus);
/* 6766 */           rowInfo.put("thresholdid", thresholdid);
/* 6767 */           metaInfoInOrder.add(rowInfo);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       boolean isAppend;
/*      */       
/*      */ 
/* 6775 */       if ((outputData.size() > 0) && (!EnterpriseUtil.isAdminServer())) {
/* 6776 */         ArrayList<String> maxResidList = new ArrayList();
/* 6777 */         ArrayList<String> maxCollecTimeList = new ArrayList();
/* 6778 */         String limitn = "1000";
/* 6779 */         String toporbottom = "";
/* 6780 */         getMaxCollectionTimes(baseAttribute, maxResidList, maxCollecTimeList, selectedMonitors, widgetProps);
/* 6781 */         ArrayList<ArrayList> processData = processBatch(attributesForProcessing, maxResidList, maxCollecTimeList, baseAttribute, limitn, toporbottom);
/*      */         
/*      */ 
/* 6784 */         int whereToAppend = headerAttributeIds.size() - 1;
/* 6785 */         isAppend = appendDataToOutput(processData, outputData, headerAttributeIds, attributesForProcessing, controlHeader, whereToAppend, null, metaInfoInOrder);
/*      */       } else {
/* 6787 */         widgetData.put("custommessage", FormatUtil.getString("am.mypage.thresholdbreakers.clearmessage.text"));
/*      */       }
/*      */       
/* 6790 */       filterResidsFromMaxids(outputData, selectedMonitors, controlHeader);
/*      */       
/* 6792 */       widgetData.put("metricdata", outputData);
/* 6793 */       widgetData.put("header", headerAttributeIds);
/* 6794 */       widgetData.put("resourceIds", selectedMonitors);
/* 6795 */       widgetData.put("residDisplayNames", residDisplayNames);
/*      */     } catch (Exception ex) {
/* 6797 */       ex.printStackTrace();
/*      */     }
/* 6799 */     return widgetData;
/*      */   }
/*      */   
/*      */   private void getAttribIdsforResourceType(String resourcetype, String baseAttributeid, ArrayList attribids, HashMap restypeAttribidMapping)
/*      */   {
/* 6804 */     ResultSet rs = null;
/* 6805 */     AMConnectionPool con = new AMConnectionPool();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 6813 */       String[] complextype_attribids = { baseAttributeid };
/* 6814 */       if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP")) || (resourcetype.equals("Generic WMI")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("$ComplexType_NetworkDevices")) || (resourcetype.equals("$ComplexType_AllApps")))
/*      */       {
/* 6816 */         ArrayList attrExtTableDetails = this.mo.getCachedAttributeDetails(baseAttributeid);
/*      */         
/* 6818 */         if (attrExtTableDetails != null) {
/* 6819 */           String attribute = (String)attrExtTableDetails.get(11);
/* 6820 */           String resourcetypecondition = getResourceTypesForComplexTypeCondition(resourcetype, "RESOURCETYPE");
/* 6821 */           String complexTypeAttribidsQuery = "select ATTRIBUTEID,RESOURCETYPE from AM_ATTRIBUTES where ATTRIBUTE='" + attribute + "' and " + resourcetypecondition;
/* 6822 */           if (resourcetype.equals("$ComplexType_All"))
/*      */           {
/* 6824 */             complexTypeAttribidsQuery = "select ATTRIBUTEID,RESOURCETYPE from AM_ATTRIBUTES where ATTRIBUTE in ('Response Time','Connection Time','Average Response Time','ResponseTime') and " + resourcetypecondition;
/* 6825 */           } else if ((resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP")))
/*      */           {
/*      */ 
/* 6828 */             String action = "getAllGroupedAttribsQuery";
/* 6829 */             complexTypeAttribidsQuery = getSimilarAttributeForCAM_Actions(resourcetype, baseAttributeid, null, action);
/* 6830 */           } else if (resourcetype.equals("Generic WMI")) {
/* 6831 */             complexTypeAttribidsQuery = "select ATTRIBUTEID,RESOURCETYPE from AM_ATTRIBUTES where ATTRIBUTEID=" + baseAttributeid;
/*      */           }
/*      */           
/* 6834 */           rs = AMConnectionPool.executeQueryStmt(complexTypeAttribidsQuery);
/* 6835 */           while (rs.next()) {
/* 6836 */             String attributeid = rs.getString("ATTRIBUTEID");
/* 6837 */             resourcetype = rs.getString("RESOURCETYPE");
/* 6838 */             attribids.add(attributeid);
/* 6839 */             restypeAttribidMapping.put(resourcetype, attributeid);
/*      */           }
/* 6841 */           rs.close();
/*      */         }
/*      */       } else {
/* 6844 */         attribids.add(baseAttributeid);
/* 6845 */         restypeAttribidMapping.put(resourcetype, baseAttributeid);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 6849 */       ex.printStackTrace();
/* 6850 */       attribids.add(baseAttributeid);
/* 6851 */       restypeAttribidMapping.put(resourcetype, baseAttributeid);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private String getAttribIdForResourceType(String resid, String attributeid, String resourcetype, HashMap restypeAttribidMapping, HashMap widgetProps)
/*      */   {
/* 6858 */     ResultSet rs = null;
/* 6859 */     AMConnectionPool con = new AMConnectionPool();
/* 6860 */     String attribid = "-1";
/* 6861 */     HashMap residTypeMapping = (HashMap)widgetProps.get("residTypeMapping");
/*      */     try
/*      */     {
/* 6864 */       if ((resourcetype.equals("$ComplexType_Windows")) || (resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")))
/*      */       {
/* 6866 */         resourcetype = (String)residTypeMapping.get(resid);
/* 6867 */         return (String)restypeAttribidMapping.get(resourcetype);
/*      */       }
/* 6869 */       if ((resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP")))
/*      */       {
/* 6871 */         String action = "getAttribidforResidQuery";
/* 6872 */         HashMap extraprops = new HashMap();
/* 6873 */         extraprops.put("resourceid", resid);
/* 6874 */         String complexTypeAttribidsQuery = getSimilarAttributeForCAM_Actions(resourcetype, attributeid, extraprops, action);
/* 6875 */         rs = AMConnectionPool.executeQueryStmt(complexTypeAttribidsQuery);
/* 6876 */         if (rs.next()) {
/* 6877 */           return rs.getString("ATTRIBUTEID");
/*      */         }
/* 6879 */         rs.close();
/*      */       } else {
/* 6881 */         return attributeid;
/*      */       }
/*      */     } catch (Exception ex) {
/* 6884 */       ex.printStackTrace();
/*      */     }
/* 6886 */     return attribid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap getThresholdsForResources(ArrayList resids, ArrayList attribslistCheckedforComplextype)
/*      */   {
/* 6894 */     ResultSet rs = null;
/* 6895 */     HashMap thresholdMapping = new HashMap();
/* 6896 */     AMConnectionPool con = new AMConnectionPool();
/*      */     try {
/* 6898 */       String query = "select AM_ATTRIBUTETHRESHOLDMAPPER.ID as RESOURCEID,AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID ,AM_THRESHOLDCONFIG.NAME,AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION,AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE from AM_ATTRIBUTETHRESHOLDMAPPER left outer join  AM_THRESHOLDCONFIG on  AM_THRESHOLDCONFIG.Id=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID  where " + ManagedApplication.getCondition("AM_ATTRIBUTETHRESHOLDMAPPER.ID", resids) + " and " + ManagedApplication.getCondition("AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE", attribslistCheckedforComplextype);
/* 6899 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6900 */       while (rs.next()) {
/* 6901 */         String resid = rs.getString("RESOURCEID");
/* 6902 */         String thresholdname = rs.getString("NAME");
/* 6903 */         String criticalcondtion = rs.getString("CRITICALTHRESHOLDCONDITION");
/* 6904 */         String criticalcondition_value = rs.getString("CRITICALTHRESHOLDVALUE");
/* 6905 */         String thresholdid = rs.getString("THRESHOLDCONFIGURATIONID");
/* 6906 */         HashMap singlethreshold = new HashMap();
/* 6907 */         singlethreshold.put("resourceid", resid);
/* 6908 */         singlethreshold.put("thresholdname", thresholdname);
/* 6909 */         singlethreshold.put("criticalcondtion", criticalcondtion);
/* 6910 */         singlethreshold.put("criticalcondition_value", criticalcondition_value);
/* 6911 */         singlethreshold.put("thresholdid", thresholdid);
/* 6912 */         thresholdMapping.put(resid, singlethreshold);
/*      */       }
/* 6914 */       rs.close();
/*      */     } catch (Exception ex) {
/* 6916 */       ex.printStackTrace();
/*      */     }
/* 6918 */     return thresholdMapping;
/*      */   }
/*      */   
/*      */   public HashMap getAllAddedMonitorTypes(String owner, String role)
/*      */   {
/* 6923 */     return getAllAddedMonitorTypes(owner, role, new ArrayList(), false, "-1", null);
/*      */   }
/*      */   
/*      */   public HashMap getAllAddedMonitorTypes(String owner, String role, ArrayList<String> associatedTypes, boolean isRestAPI, String category, HttpServletRequest request)
/*      */   {
/* 6928 */     HashMap getaddedMonitors = new HashMap();
/* 6929 */     HashMap map = new HashMap();
/* 6930 */     HashMap retmap = new HashMap();
/* 6931 */     Hashtable mhash = new Hashtable();
/* 6932 */     ArrayList selectedMonitors = new ArrayList();
/* 6933 */     ArrayList<String> controlHeader = new ArrayList();
/* 6934 */     ArrayList<HashMap> metaInfoInOrder = new ArrayList();
/*      */     
/* 6936 */     getaddedMonitors.put("selectedMonitors", selectedMonitors);
/* 6937 */     getaddedMonitors.put("controlHeader", controlHeader);
/* 6938 */     getaddedMonitors.put("metaInfoInOrder", metaInfoInOrder);
/* 6939 */     getaddedMonitors.put("associatedTypes", associatedTypes);
/*      */     
/* 6941 */     if (role.equals("operator"))
/*      */     {
/* 6943 */       getaddedMonitors.put("isoperator", "true");
/* 6944 */       getaddedMonitors.put("user", owner);
/* 6945 */       if ((request != null) && (com.adventnet.appmanager.util.Constants.isUserResourceEnabled())) {
/* 6946 */         getaddedMonitors.put("loginUserid", com.adventnet.appmanager.util.Constants.getLoginUserid(request));
/*      */       }
/*      */     }
/* 6949 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 6951 */       getaddedMonitors.put("user", owner);
/*      */     }
/*      */     
/* 6954 */     if (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) {
/* 6955 */       if ((request == null) || ((request != null) && (request.getParameter("monitor_viewtype") != null) && (request.getParameter("monitor_viewtype").equals("categoryview"))) || (isRestAPI)) {
/* 6956 */         getaddedMonitors.put("EXTPROD", "");
/*      */       } else {
/* 6958 */         getaddedMonitors.put("EXTPROD", "OPMANAGER");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 6963 */     getaddedMonitors.put("isRestAPI", Boolean.valueOf(isRestAPI));
/* 6964 */     getaddedMonitors.put("infraCategory", category);
/*      */     
/* 6966 */     HashMap addedMonitors = getInfrastructureSnapshot(getaddedMonitors, true);
/* 6967 */     HashMap ostypemap = com.adventnet.appmanager.util.Constants.getVMs_EC2CountForAllOS(role, owner, request);
/*      */     
/* 6969 */     HashMap VMmap = new HashMap();
/* 6970 */     HashMap EC2map = new HashMap();
/* 6971 */     Set ostypeKey = ostypemap.keySet();
/* 6972 */     Iterator Itr = ostypeKey.iterator();
/* 6973 */     while (Itr.hasNext()) {
/* 6974 */       String keys = (String)Itr.next();
/* 6975 */       if (keys.equals("EC2Instance")) {
/* 6976 */         EC2map = (HashMap)ostypemap.get(keys);
/*      */       }
/* 6978 */       else if (keys.equals("VirtualMachine")) {
/* 6979 */         VMmap = (HashMap)ostypemap.get(keys);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 6985 */     ArrayList list = new ArrayList();
/* 6986 */     String s = null;String resourceTypeUrl = null;String healthmsg = null;
/* 6987 */     String availmsg = null;String subgroup = null;String outages = null;
/* 6988 */     String clearCount = null;String warningCount = null;String criticalCount = null;
/* 6989 */     String healthUnknownCount = null;String availUnknownCount = null;
/* 6990 */     String upCount = null;String downCount = null;
/* 6991 */     String[] sarray = null;
/* 6992 */     String count = null;String disname = null;String availstatus = null;
/* 6993 */     String healthstatus = null;String img = null;String resCategory = null;
/* 6994 */     int totalMonitorCount = 0;int tempcnt = 0;
/* 6995 */     for (Iterator i = addedMonitors.keySet().iterator(); i.hasNext();)
/*      */     {
/* 6997 */       String key2 = (String)i.next();
/* 6998 */       if (key2.equals("metricdata")) {
/* 6999 */         list = (ArrayList)addedMonitors.get(key2);
/* 7000 */         for (int l = 0; l < list.size(); l++) {
/* 7001 */           mhash = new Hashtable();
/* 7002 */           ArrayList temp = (ArrayList)list.get(l);
/*      */           
/* 7004 */           disname = (String)temp.get(0);
/* 7005 */           availstatus = (String)temp.get(1);
/* 7006 */           outages = (String)temp.get(3);
/* 7007 */           img = (String)temp.get(4);
/* 7008 */           resCategory = (String)temp.get(5);
/* 7009 */           sarray = outages.split("/");
/* 7010 */           count = sarray[1];
/*      */           
/* 7012 */           for (int x = 0; x < metaInfoInOrder.size(); x++) {
/* 7013 */             if (((HashMap)metaInfoInOrder.get(x)).get("displayname").equals(disname)) {
/* 7014 */               availmsg = (String)((HashMap)metaInfoInOrder.get(x)).get("availmessage");
/* 7015 */               healthmsg = (String)((HashMap)metaInfoInOrder.get(x)).get("alertmessage");
/* 7016 */               healthstatus = (String)((HashMap)metaInfoInOrder.get(x)).get("healthstatus");
/* 7017 */               subgroup = (String)((HashMap)metaInfoInOrder.get(x)).get("resourcetype");
/* 7018 */               availUnknownCount = (String)((HashMap)metaInfoInOrder.get(x)).get("availunknowncount");
/* 7019 */               upCount = (String)((HashMap)metaInfoInOrder.get(x)).get("upcount");
/* 7020 */               downCount = (String)((HashMap)metaInfoInOrder.get(x)).get("downcount");
/* 7021 */               healthUnknownCount = (String)((HashMap)metaInfoInOrder.get(x)).get("healthunknowncount");
/* 7022 */               clearCount = (String)((HashMap)metaInfoInOrder.get(x)).get("clearcount");
/* 7023 */               warningCount = (String)((HashMap)metaInfoInOrder.get(x)).get("warningcount");
/* 7024 */               criticalCount = (String)((HashMap)metaInfoInOrder.get(x)).get("criticalcount");
/* 7025 */               resourceTypeUrl = (String)((HashMap)metaInfoInOrder.get(x)).get("resourceTypeUrl");
/*      */             }
/*      */           }
/* 7028 */           tempcnt = Integer.parseInt(count);
/* 7029 */           totalMonitorCount += tempcnt;
/*      */           
/* 7031 */           mhash.put("displayname", disname);
/* 7032 */           mhash.put("subgroup", subgroup);
/* 7033 */           mhash.put("severity", availstatus);
/* 7034 */           mhash.put("count", count);
/* 7035 */           mhash.put("img", img);
/* 7036 */           mhash.put("resCategory", resCategory);
/* 7037 */           mhash.put("alertmsg", healthmsg);
/* 7038 */           mhash.put("outages", outages);
/*      */           
/*      */ 
/* 7041 */           mhash.put("availmsg", availmsg);
/* 7042 */           mhash.put("healthSeverity", healthstatus);
/* 7043 */           mhash.put("resourceTypeUrl", resourceTypeUrl);
/* 7044 */           mhash.put("availUnknownCount", availUnknownCount);
/* 7045 */           mhash.put("upCount", upCount);
/* 7046 */           mhash.put("downCount", downCount);
/* 7047 */           mhash.put("healthUnknownCount", healthUnknownCount);
/* 7048 */           mhash.put("clearCount", clearCount);
/* 7049 */           mhash.put("warningCount", warningCount);
/* 7050 */           mhash.put("criticalCount", criticalCount);
/*      */           
/* 7052 */           map.put(disname, mhash);
/*      */         }
/*      */       }
/*      */     }
/* 7056 */     Set HostKeys = VMmap.keySet();
/* 7057 */     Iterator It = HostKeys.iterator();
/* 7058 */     while (It.hasNext()) {
/* 7059 */       String keys = (String)It.next();
/* 7060 */       if (map.containsKey(keys)) {
/* 7061 */         Hashtable h1 = (Hashtable)map.get(keys);
/* 7062 */         h1.put("VMoscount", ((Hashtable)VMmap.get(keys)).get("oscount") + "");
/* 7063 */         map.put(keys, h1);
/*      */       } else {
/* 7065 */         Hashtable h1 = new Hashtable();
/* 7066 */         h1.put("VMoscount", ((Hashtable)VMmap.get(keys)).get("oscount") + "");
/* 7067 */         h1.put("img", ((Hashtable)VMmap.get(keys)).get("osimg"));
/* 7068 */         h1.put("displayname", keys);
/* 7069 */         map.put(keys, h1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 7074 */     HostKeys = EC2map.keySet();
/* 7075 */     It = HostKeys.iterator();
/* 7076 */     while (It.hasNext()) {
/* 7077 */       String keys = (String)It.next();
/* 7078 */       if (map.containsKey(keys)) {
/* 7079 */         Hashtable h2 = (Hashtable)map.get(keys);
/* 7080 */         if (EC2map.containsKey(keys)) {
/* 7081 */           h2.put("EC2oscount", ((Hashtable)EC2map.get(keys)).get("oscount") + "");
/*      */         }
/* 7083 */         map.put(keys, h2);
/*      */       } else {
/* 7085 */         Hashtable h2 = new Hashtable();
/* 7086 */         if (EC2map.containsKey(keys)) {
/* 7087 */           h2.put("EC2oscount", ((Hashtable)EC2map.get(keys)).get("oscount") + "");
/* 7088 */           h2.put("img", ((Hashtable)EC2map.get(keys)).get("osimg"));
/*      */         }
/* 7090 */         h2.put("displayname", keys);
/* 7091 */         map.put(keys, h2);
/*      */       }
/*      */     }
/* 7094 */     map.put("totalMonCnt", totalMonitorCount + "");
/* 7095 */     return map;
/*      */   }
/*      */   
/*      */   private static void updateResourceTypeStatus(HashMap restypeDetails, String availstatus, String healthstatus, String displayname) {
/* 7099 */     boolean iscrtical = ((Boolean)restypeDetails.get("iscrtical")).booleanValue();
/* 7100 */     boolean isdown = ((Boolean)restypeDetails.get("isdown")).booleanValue();
/* 7101 */     boolean isup = ((Boolean)restypeDetails.get("isup")).booleanValue();
/* 7102 */     boolean iswarning = ((Boolean)restypeDetails.get("iswarning")).booleanValue();
/* 7103 */     boolean isclear = ((Boolean)restypeDetails.get("isclear")).booleanValue();
/* 7104 */     boolean ishealthunknown = ((Boolean)restypeDetails.get("ishealthunknown")).booleanValue();
/* 7105 */     boolean isavailunknown = ((Boolean)restypeDetails.get("isavailunknown")).booleanValue();
/*      */     
/* 7107 */     int availunknowncount = ((Integer)restypeDetails.get("availunknowncount")).intValue();
/* 7108 */     int healthunknowncount = ((Integer)restypeDetails.get("healthunknowncount")).intValue();
/* 7109 */     int warningcount = ((Integer)restypeDetails.get("warningcount")).intValue();
/* 7110 */     int clearcount = ((Integer)restypeDetails.get("clearcount")).intValue();
/* 7111 */     int criticalcount = ((Integer)restypeDetails.get("criticalcount")).intValue();
/* 7112 */     int upcount = ((Integer)restypeDetails.get("upcount")).intValue();
/* 7113 */     int downcount = ((Integer)restypeDetails.get("downcount")).intValue();
/* 7114 */     StringBuilder warningmessage = (StringBuilder)restypeDetails.get("warningmessage");
/* 7115 */     StringBuilder clearmessage = (StringBuilder)restypeDetails.get("clearmessage");
/* 7116 */     StringBuilder criticalmessage = (StringBuilder)restypeDetails.get("criticalmessage");
/* 7117 */     StringBuilder upmessage = (StringBuilder)restypeDetails.get("upmessage");
/* 7118 */     StringBuilder downmessage = (StringBuilder)restypeDetails.get("downmessage");
/*      */     
/* 7120 */     if (healthstatus == null) {
/* 7121 */       ishealthunknown = true;
/* 7122 */       healthunknowncount++;
/* 7123 */     } else if (healthstatus.equals("4"))
/*      */     {
/* 7125 */       iswarning = true;
/* 7126 */       warningcount++;
/* 7127 */       warningmessage.append(warningcount + ". " + FormatUtil.getString("am.mypage.health.monitor.warning.text", new String[] { displayname }) + " <br>");
/* 7128 */     } else if (healthstatus.equals("5"))
/*      */     {
/* 7130 */       isclear = true;
/* 7131 */       clearcount++;
/* 7132 */       clearmessage.append(clearcount + ". " + FormatUtil.getString("am.mypage.health.monitor.clear.text", new String[] { displayname }) + " <br>");
/* 7133 */     } else if (healthstatus.equals("1"))
/*      */     {
/* 7135 */       iscrtical = true;
/* 7136 */       criticalcount++;
/* 7137 */       criticalmessage.append(criticalcount + ". " + FormatUtil.getString("am.mypage.health.monitor.critical.text", new String[] { displayname }) + " <br>");
/*      */     } else {
/* 7139 */       ishealthunknown = true;
/* 7140 */       healthunknowncount++;
/*      */     }
/*      */     
/* 7143 */     if (availstatus == null) {
/* 7144 */       isavailunknown = true;
/* 7145 */       availunknowncount++;
/* 7146 */     } else if (availstatus.equals("5"))
/*      */     {
/* 7148 */       isup = true;
/* 7149 */       upcount++;
/* 7150 */       upmessage.append(upcount + ". " + FormatUtil.getString("am.mypage.avail.monitor.up.text", new String[] { displayname }));
/* 7151 */     } else if (availstatus.equals("1"))
/*      */     {
/* 7153 */       isdown = true;
/* 7154 */       downcount++;
/* 7155 */       downmessage.append(downcount + ". " + FormatUtil.getString("am.mypage.avail.monitor.down.text", new String[] { displayname }));
/*      */     } else {
/* 7157 */       isavailunknown = true;
/* 7158 */       availunknowncount++;
/*      */     }
/*      */     
/* 7161 */     restypeDetails.put("warningcount", Integer.valueOf(warningcount));
/* 7162 */     restypeDetails.put("clearcount", Integer.valueOf(clearcount));
/* 7163 */     restypeDetails.put("criticalcount", Integer.valueOf(criticalcount));
/* 7164 */     restypeDetails.put("upcount", Integer.valueOf(upcount));
/* 7165 */     restypeDetails.put("downcount", Integer.valueOf(downcount));
/* 7166 */     restypeDetails.put("ishealthunknown", Boolean.valueOf(ishealthunknown));
/* 7167 */     restypeDetails.put("healthunknowncount", Integer.valueOf(healthunknowncount));
/* 7168 */     restypeDetails.put("isavailunknown", Boolean.valueOf(isavailunknown));
/* 7169 */     restypeDetails.put("availunknowncount", Integer.valueOf(availunknowncount));
/* 7170 */     restypeDetails.put("iscrtical", Boolean.valueOf(iscrtical));
/* 7171 */     restypeDetails.put("isdown", Boolean.valueOf(isdown));
/* 7172 */     restypeDetails.put("isup", Boolean.valueOf(isup));
/* 7173 */     restypeDetails.put("iswarning", Boolean.valueOf(iswarning));
/* 7174 */     restypeDetails.put("isclear", Boolean.valueOf(isclear));
/* 7175 */     restypeDetails.put("warningmessage", warningmessage);
/* 7176 */     restypeDetails.put("clearmessage", clearmessage);
/* 7177 */     restypeDetails.put("criticalmessage", criticalmessage);
/* 7178 */     restypeDetails.put("upmessage", upmessage);
/* 7179 */     restypeDetails.put("downmessage", downmessage);
/*      */   }
/*      */   
/*      */   public HashMap getInfrastructureSnapshot(HashMap widgetProps) {
/* 7183 */     return getInfrastructureSnapshot(widgetProps, false);
/*      */   }
/*      */   
/*      */   public HashMap getInfrastructureSnapshot(HashMap widgetProps, boolean flag) {
/* 7187 */     ResultSet rs = null;
/*      */     
/*      */ 
/* 7190 */     String restype = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7199 */     boolean iscrtical = false;
/* 7200 */     boolean iswarning = false;
/* 7201 */     boolean isdown = false;
/*      */     
/*      */ 
/* 7204 */     ArrayList<String> singlerow = new ArrayList();
/* 7205 */     HashMap restype_resids_mapping = new HashMap();
/* 7206 */     HashMap restype_attribids_mapping = new HashMap();
/* 7207 */     HashMap residDisplayName_mapping = new HashMap();
/* 7208 */     HashMap restype_subgroup_mapping = new HashMap();
/* 7209 */     HashMap restype_img_mapping = new HashMap();
/* 7210 */     HashMap residcategorty_mapping = new HashMap();
/* 7211 */     HashMap<String, String> residResourceType_mapping = new HashMap();
/* 7212 */     ArrayList<String> allResTypes = new ArrayList();
/* 7213 */     ArrayList<String> allResTypesinOrder = new ArrayList();
/* 7214 */     ArrayList<String> allResids = new ArrayList();
/* 7215 */     ArrayList<String> restypes_critical_index = new ArrayList();
/* 7216 */     ArrayList<String> restypes_down_index = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7234 */     HashMap widgetData = new HashMap();
/* 7235 */     Vector resIds_vector = new Vector();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7243 */     String unknownhealthmsg = FormatUtil.getString("am.mypage.health.unknown.text");
/* 7244 */     String unknownavailmsg = FormatUtil.getString("am.mypage.avail.up.text");
/*      */     
/* 7246 */     String img = null;
/* 7247 */     String infraCategory = "-1";
/* 7248 */     boolean isRestAPI = false;
/*      */     try
/*      */     {
/* 7251 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 7252 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 7253 */       ArrayList<String> headerAttributeIds = new ArrayList();
/* 7254 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 7255 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 7256 */       ArrayList<String> associatedTypes = (ArrayList)widgetProps.get("associatedTypes");
/*      */       
/*      */ 
/* 7259 */       if (widgetProps.containsKey("isRestAPI")) {
/* 7260 */         infraCategory = (String)widgetProps.get("infraCategory");
/* 7261 */         isRestAPI = ((Boolean)widgetProps.get("isRestAPI")).booleanValue();
/*      */       }
/*      */       
/*      */ 
/* 7265 */       boolean isNWD = false;
/* 7266 */       if ((associatedTypes.size() != 0) && (associatedTypes.contains("NWD"))) {
/* 7267 */         isNWD = true;
/*      */       }
/*      */       
/* 7270 */       String externalProduct = (String)widgetProps.get("EXTPROD");
/* 7271 */       String associatedTypeVals = "";
/* 7272 */       String criteria = " ";
/*      */       
/* 7274 */       String resourceidsQuery = "";
/* 7275 */       String eumChildIDString = com.adventnet.appmanager.util.Constants.getEUMChildString();
/*      */       
/* 7277 */       if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (externalProduct != null) && (!externalProduct.trim().equals(""))) {
/* 7278 */         resourceidsQuery = "select RESOURCEID,AM_ManagedObject.TYPE,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as RES_DISPLAYNAME,AM_ManagedResourceType.RESOURCEGROUP,ExternalDeviceDetails.CATEGORY as SUBGROUP,IMAGEPATH  as img from AM_ManagedObject inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE";
/*      */         
/* 7280 */         if (associatedTypes.size() != 0)
/*      */         {
/*      */ 
/*      */ 
/* 7284 */           if (isRestAPI) {
/* 7285 */             associatedTypeVals = infraCategory.equals("application") ? " NOT IN ( " : " IN ( ";
/*      */           } else {
/* 7287 */             associatedTypeVals = " IN ( ";
/*      */           }
/*      */           
/*      */ 
/* 7291 */           for (int i = 0; i < associatedTypes.size(); i++) {
/* 7292 */             if (i != associatedTypes.size() - 1) {
/* 7293 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "',";
/*      */             } else {
/* 7295 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "')";
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 7301 */         if ((isRestAPI) && ((infraCategory.equals("network")) || (infraCategory.equals("all")))) {
/* 7302 */           associatedTypeVals = associatedTypeVals + " and AM_ManagedResourceType.SUBGROUP NOT IN ('OpManager-Interface')";
/*      */         }
/*      */         
/* 7305 */         if (associatedTypeVals.equalsIgnoreCase("")) {
/* 7306 */           if (externalProduct.equalsIgnoreCase("OPMANAGER")) {
/* 7307 */             criteria = " where ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */           }
/*      */         } else {
/* 7310 */           criteria = " where ExternalDeviceDetails.CATEGORY " + associatedTypeVals + " ";
/*      */         }
/* 7312 */         resourceidsQuery = resourceidsQuery + criteria;
/*      */       }
/*      */       else
/*      */       {
/* 7316 */         associatedTypeVals = " NOT IN ('HAI')";
/*      */         
/* 7318 */         if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (!EnterpriseUtil.isIt360MSPEdition()) && (associatedTypes.contains("NET"))) {
/* 7319 */           associatedTypes.remove("NET");
/* 7320 */           if (associatedTypes.size() == 0) {
/* 7321 */             associatedTypeVals = " IN ('')";
/*      */           }
/*      */         }
/* 7324 */         if (associatedTypes.size() != 0) {
/* 7325 */           associatedTypeVals = " IN (";
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7331 */           associatedTypeVals = infraCategory.equals("application") ? " NOT IN ( " : " IN ( ";
/*      */           
/*      */ 
/* 7334 */           for (int i = 0; i < associatedTypes.size(); i++) {
/* 7335 */             if (i != associatedTypes.size() - 1) {
/* 7336 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "',";
/*      */             } else {
/* 7338 */               associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "')";
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 7344 */         resourceidsQuery = "select RESOURCEID,TYPE,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as RES_DISPLAYNAME,AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.SUBGROUP as SUBGROUP,AM_ManagedResourceType.IMAGEPATH  as img from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  and  AM_ManagedObject.TYPE not in ('HAI','Network') and  AM_ManagedObject.RESOURCEID NOT IN (" + eumChildIDString + ") and AM_ManagedObject.RESOURCEID NOT IN(select RESOURCEID from AM_RESOURCE_AGENT_MAPPING) and AM_ManagedResourceType.RESOURCEGROUP " + associatedTypeVals;
/* 7345 */         String category = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 7346 */         if (EnterpriseUtil.isCloudEdition()) {
/* 7347 */           resourceidsQuery = resourceidsQuery + " and AM_ManagedObject.TYPE not in " + com.adventnet.appmanager.util.Constants.getNotSupported();
/*      */         }
/* 7349 */         resourceidsQuery = resourceidsQuery + " and AM_ManagedResourceType.SUBGROUP NOT IN ('OpManager-Interface')";
/*      */       }
/*      */       
/*      */ 
/* 7353 */       HashMap residsTypeMapping = new HashMap();
/* 7354 */       String isoperatorfromProps = (String)widgetProps.get("isoperator");
/* 7355 */       if (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets"))
/*      */       {
/* 7357 */         if (EnterpriseUtil.isIt360MSPEdition()) {
/* 7358 */           resIds_vector = ClientDBUtil.getResourceIdentity((String)widgetProps.get("user"), null, widgetProps);
/* 7359 */           resourceidsQuery = resourceidsQuery + " AND " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector);
/*      */ 
/*      */         }
/* 7362 */         else if ((isoperatorfromProps != null) && (isoperatorfromProps.equals("true")))
/*      */         {
/* 7364 */           boolean isoperator = true;
/* 7365 */           String username = (String)widgetProps.get("user");
/* 7366 */           resIds_vector = ClientDBUtil.getResourceIdentity(username);
/* 7367 */           resourceidsQuery = resourceidsQuery + " AND " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes;
/*      */         }
/*      */       }
/* 7370 */       else if ((isoperatorfromProps != null) && (isoperatorfromProps.equals("true")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 7372 */         boolean isoperator = true;
/* 7373 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 7374 */           String userid = (String)widgetProps.get("loginUserid");
/* 7375 */           resourceidsQuery = "select AM_ManagedObject.RESOURCEID,TYPE,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as RES_DISPLAYNAME,AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.SUBGROUP as SUBGROUP,IMAGEPATH  as img  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE inner join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_USERRESOURCESTABLE.USERID=" + userid + " and  AM_ManagedObject.RESOURCEID NOT IN (" + eumChildIDString + ")  and AM_ManagedResourceType.RESOURCEGROUP " + associatedTypeVals + " and  AM_ManagedObject.TYPE not in ('HAI','Network') and AM_ManagedObject.RESOURCEID NOT IN(select RESOURCEID from AM_RESOURCE_AGENT_MAPPING) and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + " " + com.adventnet.appmanager.util.Constants.excludeIntf;
/*      */         } else {
/* 7377 */           String username = (String)widgetProps.get("user");
/* 7378 */           resIds_vector = ClientDBUtil.getResourceIdentity(username);
/* 7379 */           resourceidsQuery = "select AM_ManagedObject.RESOURCEID,TYPE,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as RES_DISPLAYNAME,AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.SUBGROUP as SUBGROUP,IMAGEPATH  as img  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " and  AM_ManagedObject.RESOURCEID NOT IN (" + eumChildIDString + ")  and AM_ManagedResourceType.RESOURCEGROUP " + associatedTypeVals + " and  AM_ManagedObject.TYPE not in ('HAI','Network') and AM_ManagedObject.RESOURCEID NOT IN(select RESOURCEID from AM_RESOURCE_AGENT_MAPPING) and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + " " + com.adventnet.appmanager.util.Constants.excludeIntf;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 7386 */         AMLog.debug("resourceidsQuery" + resourceidsQuery);
/* 7387 */         rs = AMConnectionPool.executeQueryStmt(resourceidsQuery);
/* 7388 */         while (rs.next()) {
/* 7389 */           String resourceid = rs.getString("RESOURCEID");
/* 7390 */           restype = rs.getString("SUBGROUP");
/* 7391 */           String actualrestype = rs.getString("TYPE");
/* 7392 */           String displayname = rs.getString("DISPLAYNAME");
/* 7393 */           displayname = EnterpriseUtil.decodeString(displayname);
/* 7394 */           img = rs.getString("img");
/* 7395 */           String restype_displayname = rs.getString("RES_DISPLAYNAME");
/*      */           
/* 7397 */           if (rs.getString("RESOURCEGROUP").equals("NWD"))
/*      */           {
/*      */ 
/*      */ 
/* 7401 */             restype_displayname = (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (isRestAPI) ? restype : "Network Devices";
/* 7402 */           } else if (rs.getString("RESOURCEGROUP").equals("SAN")) {
/* 7403 */             restype_displayname = (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (isRestAPI) ? restype : "Storage Devices";
/* 7404 */           } else if (rs.getString("RESOURCEGROUP").equals("EMO"))
/*      */           {
/* 7406 */             restype_displayname = (com.adventnet.appmanager.util.Constants.isIt360) && (isRestAPI) ? restype : "Site24x7 Monitors";
/*      */           }
/*      */           
/* 7409 */           if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (widgetProps.get("widgettype") != null)) {
/* 7410 */             String widgType = (String)widgetProps.get("widgettype");
/* 7411 */             if (widgType.equals("504")) {
/* 7412 */               restype_displayname = restype;
/*      */             }
/*      */           }
/*      */           
/* 7416 */           residDisplayName_mapping.put(resourceid, displayname);
/* 7417 */           restype_subgroup_mapping.put(actualrestype, restype);
/* 7418 */           String resourcegroup = rs.getString("RESOURCEGROUP");
/*      */           
/* 7420 */           allResids.add(resourceid);
/*      */           
/* 7422 */           residsTypeMapping.put(resourceid, actualrestype);
/* 7423 */           restype_displayname = restype.equals("VirtualMachine") ? "Virtual Machine" : restype.equals("Windows") ? "Windows" : restype_displayname;
/* 7424 */           restype_img_mapping.put(restype_displayname, img);
/* 7425 */           residcategorty_mapping.put(restype_displayname, resourcegroup);
/* 7426 */           residResourceType_mapping.put(resourceid, restype_displayname);
/*      */           
/* 7428 */           if (!allResTypesinOrder.contains(restype_displayname)) {
/* 7429 */             allResTypesinOrder.add(restype_displayname);
/*      */           }
/*      */           
/* 7432 */           if (restype_resids_mapping.get(restype_displayname) != null) {
/* 7433 */             HashMap resTypeDetails = (HashMap)restype_resids_mapping.get(restype_displayname);
/* 7434 */             ArrayList<String> resids = (ArrayList)resTypeDetails.get("resids");
/* 7435 */             resids.add(resourceid);
/*      */           } else {
/* 7437 */             ArrayList<String> resids = new ArrayList();
/* 7438 */             resids.add(resourceid);
/* 7439 */             HashMap resTypeDetails = new HashMap();
/* 7440 */             resTypeDetails.put("warningcount", Integer.valueOf(0));
/* 7441 */             resTypeDetails.put("clearcount", Integer.valueOf(0));
/* 7442 */             resTypeDetails.put("criticalcount", Integer.valueOf(0));
/* 7443 */             resTypeDetails.put("healthunknowncount", Integer.valueOf(0));
/* 7444 */             resTypeDetails.put("upcount", Integer.valueOf(0));
/* 7445 */             resTypeDetails.put("downcount", Integer.valueOf(0));
/* 7446 */             resTypeDetails.put("availunknowncount", Integer.valueOf(0));
/* 7447 */             resTypeDetails.put("ishealthunknown", Boolean.valueOf(false));
/* 7448 */             resTypeDetails.put("isavailunknown", Boolean.valueOf(false));
/* 7449 */             resTypeDetails.put("iscrtical", Boolean.valueOf(false));
/* 7450 */             resTypeDetails.put("isdown", Boolean.valueOf(false));
/* 7451 */             resTypeDetails.put("isup", Boolean.valueOf(false));
/* 7452 */             resTypeDetails.put("iswarning", Boolean.valueOf(false));
/* 7453 */             resTypeDetails.put("isclear", Boolean.valueOf(false));
/* 7454 */             resTypeDetails.put("resids", resids);
/* 7455 */             resTypeDetails.put("clearmessage", new StringBuilder(FormatUtil.getString("am.mypage.health.clear.text")));
/* 7456 */             resTypeDetails.put("criticalmessage", new StringBuilder(FormatUtil.getString("am.mypage.health.critical.text")));
/* 7457 */             resTypeDetails.put("warningmessage", new StringBuilder(FormatUtil.getString("am.mypage.health.warning.text")));
/* 7458 */             resTypeDetails.put("downmessage", new StringBuilder(FormatUtil.getString("am.mypage.avail.down.text")));
/* 7459 */             resTypeDetails.put("upmessage", new StringBuilder(FormatUtil.getString("am.mypage.avail.up.text")));
/* 7460 */             restype_resids_mapping.put(restype_displayname, resTypeDetails);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/* 7465 */         ex.printStackTrace();
/*      */       } finally {
/* 7467 */         if (rs != null) {
/* 7468 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       
/* 7472 */       for (int i = 0; i < allResids.size(); i++) {
/* 7473 */         String resid_single = (String)allResids.get(i);
/* 7474 */         String typeForResid = (String)residsTypeMapping.get(resid_single);
/* 7475 */         String attribHealth = AMAttributesCache.getHealthId(typeForResid);
/* 7476 */         String attribAvail = AMAttributesCache.getAvailabilityId(typeForResid);
/* 7477 */         HashMap<String, String> availdetails = FaultUtil.getLatestEvent(resid_single + "_" + attribAvail);
/* 7478 */         HashMap<String, String> healthdetails = FaultUtil.getLatestEvent(resid_single + "_" + attribHealth);
/* 7479 */         String displayname = (String)residDisplayName_mapping.get(resid_single);
/* 7480 */         String availstatus = null;
/* 7481 */         String healthstatus = null;
/* 7482 */         if (availdetails != null) {
/* 7483 */           availstatus = (String)availdetails.get("SEVERITY");
/*      */         }
/* 7485 */         if (healthdetails != null) {
/* 7486 */           healthstatus = (String)healthdetails.get("SEVERITY");
/*      */         }
/* 7488 */         String restype_displayname = (String)residResourceType_mapping.get(resid_single);
/* 7489 */         HashMap restypeDetails = (HashMap)restype_resids_mapping.get(restype_displayname);
/* 7490 */         updateResourceTypeStatus(restypeDetails, availstatus, healthstatus, displayname);
/*      */       }
/*      */       
/* 7493 */       controlHeader.add("monitortypelink");
/* 7494 */       controlHeader.add("restype_availicon");
/* 7495 */       controlHeader.add("restype_healthicon");
/* 7496 */       controlHeader.add("valueasis");
/*      */       
/* 7498 */       headerAttributeIds.add(FormatUtil.getString("am.mypage.widget.momnitorstype.text"));
/* 7499 */       headerAttributeIds.add(FormatUtil.getString("Availability"));
/* 7500 */       headerAttributeIds.add(FormatUtil.getString("Health"));
/* 7501 */       headerAttributeIds.add(FormatUtil.getString("am.mypage.widget.momnitors.critical.text"));
/*      */       
/* 7503 */       Collections.sort(allResTypesinOrder);
/*      */       
/* 7505 */       for (int j = 0; j < allResTypesinOrder.size(); j++) {
/* 7506 */         String restype_displayname = (String)allResTypesinOrder.get(j);
/* 7507 */         HashMap resTypeDetails = (HashMap)restype_resids_mapping.get(restype_displayname);
/* 7508 */         ArrayList<String> resids = (ArrayList)resTypeDetails.get("resids");
/* 7509 */         int monitorcount = resids.size();
/*      */         
/* 7511 */         iscrtical = ((Boolean)resTypeDetails.get("iscrtical")).booleanValue();
/* 7512 */         isdown = ((Boolean)resTypeDetails.get("isdown")).booleanValue();
/* 7513 */         boolean isup = ((Boolean)resTypeDetails.get("isup")).booleanValue();
/* 7514 */         iswarning = ((Boolean)resTypeDetails.get("iswarning")).booleanValue();
/* 7515 */         boolean isclear = ((Boolean)resTypeDetails.get("isclear")).booleanValue();
/* 7516 */         int warningcount = ((Integer)resTypeDetails.get("warningcount")).intValue();
/* 7517 */         int clearcount = ((Integer)resTypeDetails.get("clearcount")).intValue();
/* 7518 */         int criticalcount = ((Integer)resTypeDetails.get("criticalcount")).intValue();
/* 7519 */         int healthunknowncount = ((Integer)resTypeDetails.get("healthunknowncount")).intValue();
/* 7520 */         int availunknowncount = ((Integer)resTypeDetails.get("availunknowncount")).intValue();
/* 7521 */         int upcount = ((Integer)resTypeDetails.get("upcount")).intValue();
/* 7522 */         int downcount = ((Integer)resTypeDetails.get("downcount")).intValue();
/* 7523 */         boolean ishealthunknown = ((Boolean)resTypeDetails.get("ishealthunknown")).booleanValue();
/* 7524 */         boolean isavailunknown = ((Boolean)resTypeDetails.get("isavailunknown")).booleanValue();
/* 7525 */         StringBuilder criticalmessage = (StringBuilder)resTypeDetails.get("criticalmessage");
/* 7526 */         StringBuilder clearmessage = (StringBuilder)resTypeDetails.get("clearmessage");
/* 7527 */         StringBuilder warningmessage = (StringBuilder)resTypeDetails.get("warningmessage");
/* 7528 */         StringBuilder downmessage = (StringBuilder)resTypeDetails.get("downmessage");
/* 7529 */         StringBuilder upmessage = (StringBuilder)resTypeDetails.get("upmessage");
/*      */         
/* 7531 */         HashMap rowInfo = new HashMap();
/* 7532 */         singlerow = new ArrayList();
/*      */         
/* 7534 */         String resTypeDN = FormatUtil.getString(restype_displayname);
/* 7535 */         if ((ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) && (resTypeDN.startsWith("OpManager-"))) {
/* 7536 */           resTypeDN = resTypeDN.substring(10);
/*      */         }
/* 7538 */         singlerow.add(resTypeDN);
/*      */         
/*      */ 
/* 7541 */         restype = (String)restype_subgroup_mapping.get(residsTypeMapping.get(resids.get(0)));
/* 7542 */         if (restype_displayname.equals("Network Devices")) {
/* 7543 */           rowInfo.put("queryGroup", "&group=NWD");
/* 7544 */         } else if (restype_displayname.equals("Storage Devices")) {
/* 7545 */           rowInfo.put("queryGroup", "&group=SAN");
/* 7546 */         } else if (restype_displayname.equals("Site24x7 Monitors")) {
/* 7547 */           rowInfo.put("queryGroup", "&group=EMO");
/* 7548 */         } else if (restype_displayname.equals("Unknown")) {
/* 7549 */           restype = "Unknown";
/* 7550 */         } else if (restype_displayname.equals("File / Directory Monitor")) {
/* 7551 */           restype = "File System Monitor";
/* 7552 */         } else if (restype_displayname.equals("Windows")) {
/* 7553 */           restype = "Windows";
/*      */         }
/* 7555 */         if ((externalProduct != null) && (!externalProduct.trim().equals(""))) {
/* 7556 */           restype = restype_displayname;
/*      */         }
/* 7558 */         String resourceTypeUrl = "/showresource.do?method=showResourceTypes&direct=true&network=" + restype + "&detailspage=true&PRINTER_FRIENDLY=true";
/* 7559 */         rowInfo.put("resourceTypeUrl", resourceTypeUrl);
/* 7560 */         rowInfo.put("resourcetype", restype);
/* 7561 */         if (isdown) {
/* 7562 */           restypes_down_index.add(String.valueOf(j));
/* 7563 */           singlerow.add("1");
/* 7564 */           rowInfo.put("availstatus", "1");
/* 7565 */           rowInfo.put("availmessage", downmessage.toString());
/* 7566 */         } else if (isup) {
/* 7567 */           singlerow.add("5");
/* 7568 */           rowInfo.put("availstatus", "5");
/* 7569 */           rowInfo.put("availmessage", upmessage.toString());
/*      */         }
/*      */         else {
/* 7572 */           singlerow.add("7");
/* 7573 */           rowInfo.put("availstatus", "7");
/* 7574 */           rowInfo.put("availmessage", unknownavailmsg);
/*      */         }
/* 7576 */         if ((iswarning) && (!iscrtical)) {
/* 7577 */           restypes_critical_index.add(String.valueOf(j));
/* 7578 */           singlerow.add("4");
/* 7579 */           rowInfo.put("healthstatus", "4");
/* 7580 */           rowInfo.put("alertmessage", warningmessage.toString());
/* 7581 */         } else if (iscrtical) {
/* 7582 */           restypes_critical_index.add(String.valueOf(j));
/* 7583 */           singlerow.add("1");
/* 7584 */           rowInfo.put("healthstatus", "1");
/* 7585 */           rowInfo.put("alertmessage", criticalmessage.toString());
/* 7586 */         } else if (isclear) {
/* 7587 */           singlerow.add("5");
/* 7588 */           rowInfo.put("healthstatus", "5");
/* 7589 */           rowInfo.put("alertmessage", clearmessage.toString());
/*      */         }
/*      */         else {
/* 7592 */           singlerow.add("7");
/* 7593 */           rowInfo.put("healthstatus", "7");
/* 7594 */           rowInfo.put("alertmessage", unknownhealthmsg);
/*      */         }
/*      */         
/* 7597 */         rowInfo.put("warningcount", warningcount + "");
/* 7598 */         rowInfo.put("clearcount", clearcount + "");
/* 7599 */         rowInfo.put("criticalcount", criticalcount + "");
/* 7600 */         rowInfo.put("healthunknowncount", healthunknowncount + "");
/* 7601 */         rowInfo.put("availunknowncount", availunknowncount + "");
/* 7602 */         rowInfo.put("upcount", upcount + "");
/* 7603 */         rowInfo.put("downcount", downcount + "");
/*      */         
/* 7605 */         singlerow.add(criticalcount + "/" + monitorcount);
/* 7606 */         String image = (String)restype_img_mapping.get(restype_displayname);
/* 7607 */         String resCategory = (String)residcategorty_mapping.get(restype_displayname);
/* 7608 */         if (flag == true) {
/* 7609 */           singlerow.add(image);
/* 7610 */           singlerow.add(resCategory);
/* 7611 */           rowInfo.put("displayname", resTypeDN);
/*      */         }
/* 7613 */         outputData.add(singlerow);
/* 7614 */         metaInfoInOrder.add(rowInfo);
/*      */       }
/*      */       
/*      */ 
/* 7618 */       for (int i = restypes_critical_index.size() - 1; i >= 0; i--) {
/* 7619 */         int index = Integer.parseInt((String)restypes_critical_index.get(i));
/* 7620 */         index += restypes_critical_index.size() - i - 1;
/*      */         
/* 7622 */         singlerow = (ArrayList)outputData.remove(index);
/* 7623 */         outputData.add(0, singlerow);
/* 7624 */         HashMap rowInfo = (HashMap)metaInfoInOrder.remove(index);
/* 7625 */         metaInfoInOrder.add(0, rowInfo);
/*      */       }
/*      */       
/*      */ 
/* 7629 */       widgetData.put("metricdata", outputData);
/* 7630 */       widgetData.put("header", headerAttributeIds);
/*      */     } catch (Exception ex) {
/* 7632 */       ex.printStackTrace();
/*      */     }
/* 7634 */     return widgetData;
/*      */   }
/*      */   
/*      */   private HashMap getAvailHealthRCASummary(HashMap widgetProps)
/*      */   {
/* 7639 */     HashMap widgetData = new HashMap();
/*      */     try {
/* 7641 */       int availability_up_status = 0;
/* 7642 */       int availability_down_status = 0;
/* 7643 */       int health_up_status_count = 0;
/* 7644 */       int health_down_status_count = 0;
/* 7645 */       int health_warning_status_count = 0;
/* 7646 */       int health_unknown_status_count = 0;
/* 7647 */       int health_status = 5;
/* 7648 */       int availability_status = 5;
/* 7649 */       List health_critical_rca = new ArrayList();
/* 7650 */       List health_warning_rca = new ArrayList();
/* 7651 */       List health_clear_rca = new ArrayList();
/* 7652 */       List health_unknown_rca = new ArrayList();
/* 7653 */       String resourcetype = (String)widgetProps.get("resourcetype");
/* 7654 */       ArrayList allEntities = new ArrayList();
/* 7655 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 7656 */       HashMap residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/* 7657 */       HashMap residTypeMapping = (HashMap)widgetProps.get("residTypeMapping");
/*      */       
/* 7659 */       for (int j = 0; j < selectedMonitors.size(); j++) {
/* 7660 */         allEntities.add(selectedMonitors.get(j) + "_" + AMAttributesCache.getAvailabilityId((String)residTypeMapping.get((String)selectedMonitors.get(j))));
/* 7661 */         allEntities.add(selectedMonitors.get(j) + "_" + AMAttributesCache.getHealthId((String)residTypeMapping.get((String)selectedMonitors.get(j))));
/*      */       }
/*      */       
/* 7664 */       if (allEntities.size() != 0) {
/* 7665 */         Properties alert = FaultUtil.getStatus(allEntities, false);
/* 7666 */         for (int j = 0; j < selectedMonitors.size(); j++) {
/* 7667 */           String resid = (String)selectedMonitors.get(j);
/*      */           try
/*      */           {
/* 7670 */             String availStatusStr = alert.getProperty(resid + "#" + AMAttributesCache.getAvailabilityId((String)residTypeMapping.get((String)selectedMonitors.get(j))));
/* 7671 */             if (availStatusStr == null) {
/* 7672 */               availability_status = -1;
/* 7673 */             } else if (Integer.parseInt(availStatusStr) == 1) {
/* 7674 */               availability_status = 1;
/* 7675 */               availability_down_status += 1;
/*      */             } else {
/* 7677 */               availability_up_status += 1;
/*      */             }
/*      */             
/*      */ 
/* 7681 */             String healthStatusStr = alert.getProperty(resid + "#" + AMAttributesCache.getHealthId((String)residTypeMapping.get((String)selectedMonitors.get(j))));
/* 7682 */             if (healthStatusStr == null) {
/* 7683 */               health_status = -1;
/* 7684 */               health_unknown_status_count += 1;
/* 7685 */               health_unknown_rca.add(FormatUtil.getString("am.mypage.health.monitor.unknown.text", new String[] { (String)residDisplayNames.get(resid) }));
/* 7686 */             } else if (Integer.parseInt(healthStatusStr) == 1) {
/* 7687 */               health_status = 1;
/* 7688 */               health_down_status_count += 1;
/* 7689 */               health_critical_rca.add(FormatUtil.getString("am.mypage.health.monitor.critical.text", new String[] { (String)residDisplayNames.get(resid) }));
/*      */ 
/*      */             }
/* 7692 */             else if ((Integer.parseInt(healthStatusStr) == 4) && (health_status != 1)) {
/* 7693 */               health_status = 4;
/* 7694 */               health_warning_status_count += 1;
/* 7695 */               health_warning_rca.add(FormatUtil.getString("am.mypage.health.monitor.warning.text", new String[] { (String)residDisplayNames.get(resid) }));
/*      */             } else {
/* 7697 */               health_status = 5;
/* 7698 */               health_up_status_count += 1;
/* 7699 */               health_clear_rca.add(FormatUtil.getString("am.mypage.health.monitor.clear.text", new String[] { (String)residDisplayNames.get(resid) }));
/*      */             }
/*      */           } catch (Exception e) {
/* 7702 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 7707 */       if (availability_status == -1) {
/* 7708 */         if (availability_down_status > 0) {
/* 7709 */           availability_status = 1;
/* 7710 */         } else if (availability_up_status > 0) {
/* 7711 */           availability_status = 5;
/*      */         }
/*      */       }
/*      */       
/* 7715 */       if (health_down_status_count > 0) {
/* 7716 */         health_status = 1;
/* 7717 */       } else if (health_warning_status_count > 0) {
/* 7718 */         health_status = 4;
/* 7719 */       } else if (health_up_status_count > 0) {
/* 7720 */         health_status = 5;
/*      */       } else {
/* 7722 */         health_status = -1;
/*      */       }
/*      */       
/* 7725 */       widgetData.put("availability_up_status", String.valueOf(availability_up_status));
/* 7726 */       widgetData.put("health_status", String.valueOf(health_status));
/* 7727 */       widgetData.put("availability_status", String.valueOf(availability_status));
/* 7728 */       Hashtable h = DBUtil.getAlertCountDetails(selectedMonitors);
/*      */       
/* 7730 */       if (health_status == 1) {
/* 7731 */         widgetData.put("health_rca", health_critical_rca);
/*      */       }
/* 7733 */       else if (health_status == 4) {
/* 7734 */         widgetData.put("health_rca", health_warning_rca);
/*      */       }
/* 7736 */       else if (health_status == 5) {
/* 7737 */         widgetData.put("health_rca", health_clear_rca);
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/* 7745 */         widgetData.put("health_rca", health_unknown_rca);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 7751 */       String clearCount = "0";
/* 7752 */       if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 7753 */         widgetData.put("isIt360", "true");
/* 7754 */         widgetData.put("totalAlarms", String.valueOf(Integer.parseInt((String)h.get("Critical")) + Integer.parseInt((String)h.get("Warning")) + Integer.parseInt((String)h.get("Clear"))));
/* 7755 */         clearCount = String.valueOf(selectedMonitors.size() - Integer.parseInt((String)h.get("Critical")) - Integer.parseInt((String)h.get("Warning")) - health_unknown_status_count);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 7760 */         clearCount = String.valueOf(selectedMonitors.size() - Integer.parseInt((String)h.get("Critical")) - Integer.parseInt((String)h.get("Warning")) - health_unknown_status_count);
/*      */       }
/*      */       
/* 7763 */       widgetData.put("monitor_critical", (String)h.get("Critical"));
/* 7764 */       widgetData.put("monitor_warning", (String)h.get("Warning"));
/* 7765 */       widgetData.put("monitor_clear", clearCount);
/* 7766 */       widgetData.put("monitor_unknown", Integer.valueOf(health_unknown_status_count));
/* 7767 */       widgetData.put("monitor_total", String.valueOf(selectedMonitors.size()));
/* 7768 */       widgetData.put("resourcetype", resourcetype);
/* 7769 */       AMLog.info("widgetdata in getAvailHealthRCASummary is : " + widgetData);
/*      */     } catch (Exception ex) {
/* 7771 */       ex.printStackTrace();
/*      */     }
/* 7773 */     return widgetData;
/*      */   }
/*      */   
/*      */   public static Properties getStatus(List allEntity, boolean extDevice) {
/*      */     try {
/* 7778 */       StringBuffer alertQuery = new StringBuffer("select MODTIME,SOURCE source,CATEGORY category,SEVERITY severity,entity  from Alert where Entity ");
/* 7779 */       StringBuffer eventQuery = new StringBuffer("select TTIME,SOURCE source,CATEGORY category,SEVERITY severity  from Event where entity ");
/* 7780 */       if (allEntity.size() == 1) {
/* 7781 */         if (extDevice) {
/* 7782 */           alertQuery.append("IN ('").append(allEntity.get(0)).append("'");
/*      */         } else {
/* 7784 */           alertQuery.append("='").append(allEntity.get(0)).append("'");
/*      */         }
/*      */       } else {
/* 7787 */         alertQuery = alertQuery.append(" IN (");
/* 7788 */         if (allEntity.size() > 0) {
/* 7789 */           for (int i = 0; i < allEntity.size(); i++) {
/* 7790 */             alertQuery.append("'").append(allEntity.get(i)).append("'");
/* 7791 */             if (i + 1 != allEntity.size()) {
/* 7792 */               alertQuery.append(",");
/*      */             }
/*      */             
/*      */           }
/*      */         } else {
/* 7797 */           alertQuery.append("''");
/*      */         }
/* 7799 */         alertQuery.append(")");
/*      */       }
/* 7801 */       Properties toReturn = new Properties();
/* 7802 */       ResultSet alertRes = AMConnectionPool.executeQueryStmt(alertQuery.toString());
/* 7803 */       while (alertRes.next()) {
/* 7804 */         toReturn.setProperty(alertRes.getString("source") + "#" + alertRes.getString("category"), "" + alertRes.getInt("severity"));
/* 7805 */         allEntity.remove(alertRes.getString("entity"));
/*      */       }
/*      */       
/* 7808 */       AMConnectionPool.closeStatement(alertRes);
/* 7809 */       if (allEntity.size() > 0) {
/* 7810 */         if (allEntity.size() == 1) {
/* 7811 */           if (extDevice) {
/* 7812 */             eventQuery.append("IN ('").append(allEntity.get(0)).append("')");
/*      */           } else {
/* 7814 */             eventQuery.append("='").append(allEntity.get(0)).append("'");
/*      */           }
/*      */         } else {
/* 7817 */           eventQuery = eventQuery.append(" IN (");
/* 7818 */           for (int i = 0; i < allEntity.size(); i++) {
/* 7819 */             eventQuery.append("'").append(allEntity.get(i)).append("'");
/* 7820 */             if (i + 1 != allEntity.size()) {
/* 7821 */               eventQuery.append(",");
/*      */             }
/*      */           }
/* 7824 */           eventQuery.append(")");
/*      */         }
/* 7826 */         eventQuery.append(" ORDER BY ID DESC,TTIME DESC");
/*      */         
/* 7828 */         ResultSet eventRes = AMConnectionPool.executeQueryStmt(eventQuery.toString());
/*      */         
/* 7830 */         while (eventRes.next()) {
/* 7831 */           String key = eventRes.getString("source") + "#" + eventRes.getString("category");
/* 7832 */           if (toReturn.getProperty(key) == null) {
/* 7833 */             toReturn.setProperty(key, "" + eventRes.getInt("severity"));
/*      */           }
/*      */         }
/* 7836 */         AMConnectionPool.closeStatement(eventRes);
/*      */       }
/* 7838 */       toReturn.setProperty("alertquery", alertQuery.toString());
/* 7839 */       toReturn.setProperty("eventquery", eventQuery.toString());
/* 7840 */       return toReturn;
/*      */     } catch (Exception e) {
/* 7842 */       e.printStackTrace();
/*      */     }
/* 7844 */     return new Properties();
/*      */   }
/*      */   
/*      */   private HashMap getMonitorGroupStatus(HashMap widgetProps) {
/* 7848 */     return getMonitorGroupStatus(widgetProps, null, null);
/*      */   }
/*      */   
/*      */   private HashMap getMonitorGroupStatus(HashMap widgetProps, String owner, HttpServletRequest request) {
/* 7852 */     AMConnectionPool con = new AMConnectionPool();
/*      */     
/*      */ 
/* 7855 */     HashMap widgetData = new HashMap();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 7860 */     ArrayList<String> headerAttributeIds = new ArrayList();
/* 7861 */     ArrayList<String> entitylist = new ArrayList();
/* 7862 */     ArrayList<ArrayList> outputData = new ArrayList();
/*      */     try {
/* 7864 */       String isoperatorfromProps = (String)widgetProps.get("isoperator");
/* 7865 */       String selectMonitorType = (String)widgetProps.get("selectmonitortype");
/* 7866 */       String widgetid = (String)widgetProps.get("widgetid");
/* 7867 */       String mgdrilldown = (String)widgetProps.get("mgdrilldown");
/* 7868 */       ArrayList selectedMonitors = (ArrayList)widgetProps.get("selectedMonitors");
/* 7869 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 7870 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 7871 */       HashMap residDisplayNames = (HashMap)widgetProps.get("residDisplayNames");
/* 7872 */       String mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by DISPLAYNAME";
/*      */       
/* 7874 */       if ((isoperatorfromProps != null) && (isoperatorfromProps.equals("true")) && (mgdrilldown != null) && (mgdrilldown.equals("true")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 7876 */         ArrayList subgrouplist = new ArrayList();
/* 7877 */         String userName = (String)widgetProps.get("user");
/* 7878 */         mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID,AM_HOLISTICAPPLICATION.TYPE from AM_HOLISTICAPPLICATION inner join AM_ManagedObject on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID   inner join AM_HOLISTICAPPLICATION_OWNERS on AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID inner join  AM_UserPasswordTable on  USERID=OWNERID  and AM_UserPasswordTable.USERNAME='" + userName + "' ORDER BY HAID";
/* 7879 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 7880 */           String loginUserid = (String)widgetProps.get("loginUserid");
/* 7881 */           mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID,AM_HOLISTICAPPLICATION.TYPE from AM_HOLISTICAPPLICATION inner join AM_ManagedObject on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID   inner join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " ORDER BY HAID";
/*      */         }
/* 7883 */         ResultSet rs = AMConnectionPool.executeQueryStmt(mgselectquery);
/* 7884 */         while (rs.next()) {
/* 7885 */           int mgtype = rs.getInt("TYPE");
/* 7886 */           String haid = rs.getString("HAID");
/* 7887 */           if (mgtype == 0) {
/* 7888 */             String mgname = rs.getString("DISPLAYNAME");
/* 7889 */             selectedMonitors.add(haid);
/* 7890 */             entitylist.add(haid + "_17");
/* 7891 */             entitylist.add(haid + "_18");
/* 7892 */             residDisplayNames.put(haid, mgname);
/*      */           } else {
/* 7894 */             subgrouplist.add(haid);
/*      */           }
/*      */         }
/* 7897 */         rs.close();
/* 7898 */         if (subgrouplist.size() > 0) {
/* 7899 */           ArrayList parentLevelHaids = new ArrayList();
/* 7900 */           Hashtable parentlist = DBUtil.getParentMGsforChildMGs(subgrouplist);
/* 7901 */           List rootlist = new ArrayList();
/* 7902 */           Set ks = parentlist.keySet();
/* 7903 */           Iterator it = ks.iterator();
/* 7904 */           while (it.hasNext()) {
/* 7905 */             String key = (String)it.next();
/* 7906 */             parentLevelHaids.add(((ArrayList)parentlist.get(key)).get(0));
/*      */           }
/* 7908 */           String parentlevelquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + ManagedApplication.getCondition("AM_HOLISTICAPPLICATION.HAID", parentLevelHaids) + " order by DISPLAYNAME ";
/* 7909 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(parentlevelquery);
/* 7910 */           while (rs1.next()) {
/* 7911 */             String haid = rs1.getString("HAID");
/* 7912 */             String mgname = rs1.getString("DISPLAYNAME");
/* 7913 */             entitylist.add(haid + "_17");
/* 7914 */             entitylist.add(haid + "_18");
/* 7915 */             selectedMonitors.add(haid);
/* 7916 */             residDisplayNames.put(haid, mgname);
/*      */           }
/* 7918 */           rs1.close();
/*      */         }
/*      */       }
/* 7921 */       else if ((isoperatorfromProps != null) && (isoperatorfromProps.equals("true")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 7923 */         String userName = (String)widgetProps.get("user");
/* 7924 */         String qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_UserPasswordTable,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID AND  AM_UserPasswordTable.USERNAME='" + userName + "' AND USERID=OWNERID ORDER BY HAID";
/* 7925 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 7926 */           String loginUserid = (String)widgetProps.get("loginUserid");
/* 7927 */           qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_HOLISTICAPPLICATION.HAID AND  USERID=" + loginUserid + " ORDER BY HAID";
/*      */         }
/* 7929 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 7930 */         String sg = "(";
/* 7931 */         List list1 = new ArrayList();
/* 7932 */         while (rs1.next()) {
/* 7933 */           list1.add(rs1.getString(1));
/* 7934 */           if (rs1.getInt(2) == 1) {
/* 7935 */             sg = sg + "'" + rs1.getString(1) + "',";
/*      */           }
/*      */         }
/* 7938 */         rs1.close();
/* 7939 */         if (sg.length() > 2) {
/* 7940 */           sg = sg.substring(0, sg.length() - 1) + ")";
/* 7941 */           Hashtable sglist = DBUtil.getParentMGsforChildMGs(sg);
/* 7942 */           Set s1 = sglist.keySet();
/* 7943 */           Iterator it = s1.iterator();
/* 7944 */           String key; while (it.hasNext()) {
/* 7945 */             key = (String)it.next();
/* 7946 */             List l1 = (ArrayList)sglist.get(key);
/* 7947 */             for (Object temp : l1) {
/* 7948 */               if (list1.contains(temp)) {
/* 7949 */                 list1.remove(key);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 7954 */         String haids = "(";
/* 7955 */         if ((list1 != null) && (list1.size() > 0)) {
/* 7956 */           for (int i = 0; i < list1.size(); i++) {
/* 7957 */             haids = haids + "'" + list1.get(i) + "',";
/*      */           }
/* 7959 */           if (haids.length() > 2) {
/* 7960 */             haids = haids.substring(0, haids.length() - 1) + ")";
/* 7961 */             mgselectquery = "select  DISPLAYNAME, AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  AND HAID in " + haids + " order by RESOURCENAME";
/* 7962 */             ResultSet rs = AMConnectionPool.executeQueryStmt(mgselectquery);
/* 7963 */             while (rs.next()) {
/* 7964 */               String mgname = rs.getString("DISPLAYNAME");
/* 7965 */               String haid = rs.getString("HAID");
/* 7966 */               selectedMonitors.add(haid);
/* 7967 */               entitylist.add(haid + "_17");
/* 7968 */               entitylist.add(haid + "_18");
/* 7969 */               residDisplayNames.put(haid, mgname);
/*      */             }
/* 7971 */             rs.close();
/*      */           }
/*      */         }
/* 7974 */       } else { CustomerManagementAPI.getInstance(); if (CustomerManagementAPI.isUserPermittedToSeeAllBSG((String)widgetProps.get("user"), (String)widgetProps.get("custId")))
/*      */         {
/* 7976 */           if (EnterpriseUtil.isIt360MSPEdition) {
/* 7977 */             Vector resIdVec = new Vector();
/* 7978 */             if (widgetProps.get("custId") != null) {
/* 7979 */               if (request != null) {
/* 7980 */                 resIdVec = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 7981 */                 if (request.isUserInRole("OPERATOR"))
/*      */                 {
/* 7983 */                   String condition = EnterpriseUtil.getCondition("hao.HAID", resIdVec);
/* 7984 */                   resIdVec = DBUtil.assignedBsg(owner, condition);
/*      */                 }
/*      */                 
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 7991 */               resIdVec = new Vector();
/* 7992 */               String usrName = (String)widgetProps.get("user");
/* 7993 */               String usrType = new It360SPUserManagementUtil().getUserType(usrName);
/* 7994 */               if ((usrType != null) && (usrType.equals("bsguser"))) {
/* 7995 */                 Hashtable haidsHash = new It360SPUserManagementUtil().getPrivilegedBSGsForUser(usrName);
/* 7996 */                 if ((haidsHash != null) && (haidsHash.size() > 0)) {
/* 7997 */                   Enumeration enkeys = haidsHash.keys();
/* 7998 */                   while (enkeys.hasMoreElements()) {
/* 7999 */                     resIdVec.add(enkeys.nextElement().toString());
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 8004 */             String condition = ReportUtilities.getCondition("AM_HOLISTICAPPLICATION.HAID", resIdVec);
/* 8005 */             mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.GROUPTYPE in (1,3)  and " + condition + " order by DISPLAYNAME";
/*      */           } else {
/* 8007 */             mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by DISPLAYNAME";
/* 8008 */             if ((!selectMonitorType.equals("ALL")) && (!selectMonitorType.equals("-1"))) {
/* 8009 */               int fieldtype = -1;
/* 8010 */               String datatable = "";
/* 8011 */               String aliasname = "";
/* 8012 */               String value = "";
/* 8013 */               ResultSet rs = AMConnectionPool.executeQueryStmt("select VALUE,ALIASNAME,FIELDTYPE,DATATABLE from AM_MYFIELDS_METADATA as me, AM_MYPAGE_WIDGET_CUSTOMFIELD as cs where me.fieldid=cs.fieldid and cs.widgetid=" + widgetid);
/* 8014 */               if (rs.next()) {
/* 8015 */                 fieldtype = rs.getInt("FIELDTYPE");
/* 8016 */                 aliasname = rs.getString("ALIASNAME");
/* 8017 */                 value = rs.getString("VALUE");
/*      */               }
/* 8019 */               HashMap<String, String> conditon = MyFields.customCondition(aliasname, value, null, false);
/* 8020 */               String qryCon = (String)conditon.get("groupQuery");
/* 8021 */               datatable = (String)conditon.get("groupTable");
/* 8022 */               AMConnectionPool.closeStatement(rs);
/*      */               
/* 8024 */               String isSubGroup = (String)widgetProps.get("subgroupoption");
/* 8025 */               String enableSubGroup = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/*      */               
/* 8027 */               mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by DISPLAYNAME";
/*      */               
/* 8029 */               if ("1".equalsIgnoreCase(isSubGroup)) {
/* 8030 */                 enableSubGroup = "";
/* 8031 */                 mgselectquery = "select DISPLAYNAME,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by DISPLAYNAME";
/* 8032 */                 Vector<String> getsubgroups = MyFields.listMgSubGroups(mgselectquery, datatable, "HAID", qryCon);
/* 8033 */                 mgselectquery = "select DISPLAYNAME, AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + ManagedApplication.getCondition("RESOURCEID", getsubgroups);
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 8038 */           ResultSet rs = AMConnectionPool.executeQueryStmt(mgselectquery);
/* 8039 */           while (rs.next()) {
/* 8040 */             String mgname = rs.getString("DISPLAYNAME");
/* 8041 */             String haid = rs.getString("HAID");
/* 8042 */             selectedMonitors.add(haid);
/* 8043 */             entitylist.add(haid + "_17");
/* 8044 */             entitylist.add(haid + "_18");
/* 8045 */             residDisplayNames.put(haid, mgname);
/*      */           }
/* 8047 */           rs.close();
/*      */         } }
/* 8049 */       controlHeader.add("monitorgroupLink");
/* 8050 */       headerAttributeIds.add(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/*      */       
/* 8052 */       widgetProps.put("appendWhere", "random");
/* 8053 */       widgetProps.put("noLabels", "false");
/* 8054 */       widgetProps.put("outputData", outputData);
/* 8055 */       widgetProps.put("headerAttributeIds", headerAttributeIds);
/* 8056 */       widgetProps.put("availability", "true");
/* 8057 */       widgetProps.put("health", "true");
/* 8058 */       widgetProps.put("resourcetype", "HAI");
/* 8059 */       widgetProps.put("entitylist", entitylist);
/* 8060 */       getAvailHealth(widgetProps);
/* 8061 */       boolean monitorStatus = checkForMonitorStatus(widgetProps);
/* 8062 */       Map monitorsNosAndErrors = null;
/* 8063 */       if (monitorStatus) {
/* 8064 */         monitorsNosAndErrors = DataCollectionDBUtil.getMonitorGroupsInfo();
/* 8065 */         controlHeader.add("valueasis");
/* 8066 */         headerAttributeIds.add(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.monitorstatus"));
/*      */       }
/* 8068 */       Map mgAvail = new HashMap();
/* 8069 */       if (chkForAvailability(widgetProps)) {
/* 8070 */         controlHeader.add("availabilitybar");
/* 8071 */         headerAttributeIds.add("availabilitybar");
/* 8072 */         mgAvail = ReportUtilities.getTodaysAvailabilityForAllMonitorGroups(selectedMonitors);
/*      */       }
/*      */       
/* 8075 */       for (int i = 0; i < outputData.size(); i++) {
/* 8076 */         ArrayList singlerow = (ArrayList)outputData.get(i);
/* 8077 */         String haid = (String)singlerow.get(0);
/* 8078 */         if (monitorStatus) {
/* 8079 */           Map currMGInfo = (Map)monitorsNosAndErrors.get(haid);
/* 8080 */           String noOfMons = "";
/* 8081 */           String monsInErr = "";
/* 8082 */           if (currMGInfo != null) {
/* 8083 */             noOfMons = (String)currMGInfo.get("TOTALCHILDCOUNT");
/* 8084 */             if ((noOfMons != null) && (noOfMons.equals("0")))
/*      */             {
/* 8086 */               monsInErr = "0";
/*      */             } else {
/* 8088 */               monsInErr = " " + (String)currMGInfo.get("CHILDRENINERROR") + "/";
/* 8089 */               if (monsInErr.equals(" None/"))
/*      */               {
/* 8091 */                 monsInErr = "0/";
/*      */               }
/* 8093 */               monsInErr = monsInErr + noOfMons + " " + FormatUtil.getString("am.webclient.hometab.inerror.text");
/*      */             }
/* 8095 */             singlerow.add(monsInErr);
/*      */           } else {
/* 8097 */             singlerow.add("-");
/*      */           }
/*      */         }
/* 8100 */         if (chkForAvailability(widgetProps)) {
/* 8101 */           singlerow.add("-");
/*      */         }
/* 8103 */         if (chkForAvailability(widgetProps)) {
/* 8104 */           Properties moPropRep = (Properties)mgAvail.get(haid);
/* 8105 */           HashMap rowInfo = (HashMap)metaInfoInOrder.get(i);
/* 8106 */           rowInfo.put("overAllAvailability", moPropRep);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 8111 */       ex.printStackTrace();
/*      */     }
/* 8113 */     widgetData.put("metricdata", outputData);
/* 8114 */     widgetData.put("header", headerAttributeIds);
/* 8115 */     return widgetData;
/*      */   }
/*      */   
/*      */   private boolean chkForAvailability(HashMap widgetprops)
/*      */   {
/*      */     try {
/* 8121 */       String widgettype = (String)widgetprops.get("widgettype");
/* 8122 */       if (widgettype.equals("201")) {
/* 8123 */         String todavail = (String)widgetprops.get("todaysavailability");
/* 8124 */         if ((todavail == null) || (todavail.trim().equalsIgnoreCase("null"))) {
/* 8125 */           return false;
/*      */         }
/* 8127 */         return Boolean.parseBoolean(todavail);
/*      */       }
/*      */       
/* 8130 */       return true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 8134 */       e.printStackTrace(); }
/* 8135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean checkForMonitorStatus(HashMap widgetprops)
/*      */   {
/*      */     try {
/* 8141 */       String widgettype = (String)widgetprops.get("widgettype");
/* 8142 */       if (widgettype.equals("201")) {
/* 8143 */         String val = (String)widgetprops.get("monitorsStatus");
/* 8144 */         if ((val == null) || (val.equalsIgnoreCase("0"))) {
/* 8145 */           return false;
/*      */         }
/* 8147 */         return true;
/*      */       }
/*      */       
/* 8150 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 8153 */       e.printStackTrace();
/*      */     }
/* 8155 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward saveBookMark(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 8166 */     String widgetid = request.getParameter("widgetid");
/* 8167 */     String pageid = request.getParameter("pageid");
/* 8168 */     String bookmarkidfromclient = request.getParameter("bookmarkid");
/* 8169 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try {
/* 8171 */       String displayname = request.getParameter("displayname");
/* 8172 */       String url = request.getParameter("url");
/* 8173 */       String description = request.getParameter("description");
/* 8174 */       if ((bookmarkidfromclient == null) || (bookmarkidfromclient.equals("-1"))) {
/* 8175 */         int bookmarkid = DBQueryUtil.getIncrementedID("BOOKMARKID", "AM_MYPAGE_BOOKMARKS");
/* 8176 */         AMConnectionPool.executeUpdateStmt("insert into AM_MYPAGE_BOOKMARKS (BOOKMARKID,WIDGETID,URL,BOOKMARKNAME,DESCRIPTION) values (" + bookmarkid + "," + widgetid + ",'" + url + "','" + displayname + "','" + description + "')");
/*      */       } else {
/* 8178 */         AMConnectionPool.executeUpdateStmt("update AM_MYPAGE_BOOKMARKS set URL='" + url + "',BOOKMARKNAME='" + displayname + "',DESCRIPTION='" + description + "' where BOOKMARKID=" + bookmarkidfromclient);
/*      */       }
/*      */     } catch (Exception ex) {
/* 8181 */       ex.printStackTrace();
/*      */     }
/* 8183 */     return new ActionForward("/MyPage.do?method=getWidget&pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward deleteBookMark(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 8188 */     String widgetid = request.getParameter("widgetid");
/* 8189 */     String pageid = request.getParameter("pageid");
/* 8190 */     String bookmarkidfromclient = request.getParameter("bookmarkid");
/* 8191 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try {
/* 8193 */       AMConnectionPool.executeUpdateStmt("delete from AM_MYPAGE_BOOKMARKS  where BOOKMARKID=" + bookmarkidfromclient);
/*      */     } catch (Exception ex) {
/* 8195 */       ex.printStackTrace();
/*      */     }
/* 8197 */     return new ActionForward("/MyPage.do?method=getWidget&pageid=" + pageid + "&widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   private ActionForward getBookmarksWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 8202 */     String widgetid = request.getParameter("widgetid");
/* 8203 */     String pageid = request.getParameter("pageid");
/* 8204 */     AMConnectionPool pool = new AMConnectionPool();
/* 8205 */     ResultSet rs1 = null;
/*      */     try {
/* 8207 */       HashMap widgetProps = (HashMap)request.getAttribute("widgetProps");
/* 8208 */       ArrayList<String> controlHeader = (ArrayList)widgetProps.get("controlHeader");
/* 8209 */       ArrayList<String> headerAttributeIds = (ArrayList)widgetProps.get("headerAttributeIds");
/* 8210 */       ArrayList<HashMap> metaInfoInOrder = (ArrayList)widgetProps.get("metaInfoInOrder");
/* 8211 */       ArrayList<ArrayList> outputData = new ArrayList();
/* 8212 */       ArrayList<String> outputheader = new ArrayList();
/* 8213 */       controlHeader.add("bookmarklink");
/* 8214 */       controlHeader.add("bookmarksettings");
/* 8215 */       controlHeader.add("bookmarkdelete");
/* 8216 */       headerAttributeIds.add(FormatUtil.getString("am.webclient.mypage.bookmark.text"));
/* 8217 */       headerAttributeIds.add(null);
/* 8218 */       headerAttributeIds.add(null);
/* 8219 */       outputheader.add(FormatUtil.getString("am.webclient.mypage.bookmark.text"));
/* 8220 */       outputheader.add(null);
/* 8221 */       outputheader.add(null);
/* 8222 */       rs1 = AMConnectionPool.executeQueryStmt("select BOOKMARKID,URL,WIDGETID,BOOKMARKNAME,DESCRIPTION from AM_MYPAGE_BOOKMARKS where WIDGETID=" + widgetid);
/* 8223 */       while (rs1.next()) {
/* 8224 */         HashMap rowInfo = new HashMap();
/* 8225 */         ArrayList<String> singlerow = new ArrayList();
/* 8226 */         singlerow.add(rs1.getString("BOOKMARKNAME"));
/* 8227 */         singlerow.add("");
/* 8228 */         singlerow.add("");
/* 8229 */         rowInfo.put("url", rs1.getString("URL"));
/* 8230 */         rowInfo.put("bookmarkid", rs1.getString("BOOKMARKID"));
/* 8231 */         outputData.add(singlerow);
/* 8232 */         metaInfoInOrder.add(rowInfo);
/*      */       }
/* 8234 */       rs1.close();
/* 8235 */       if ((outputData == null) || (outputData.size() <= 0)) {
/* 8236 */         request.setAttribute("nobookmarksmsg", "nobokmarks");
/*      */       }
/* 8238 */       request.setAttribute("metricdata", outputData);
/* 8239 */       request.setAttribute("outputheader", outputheader);
/* 8240 */       request.setAttribute("controlHeader", controlHeader);
/* 8241 */       request.setAttribute("metaInfoInOrder", metaInfoInOrder);
/*      */     } catch (Exception ex) {
/* 8243 */       ex.printStackTrace();
/*      */     }
/* 8245 */     return new ActionForward("/jsp/MyPage_Widget.jsp?widgetid=" + widgetid + "&pageid=" + pageid);
/*      */   }
/*      */   
/*      */   public ActionForward getBookMarkForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8249 */     String widgetid = request.getParameter("widgetid");
/* 8250 */     String bookmarkid = request.getParameter("bookmarkid");
/* 8251 */     AMConnectionPool pool = new AMConnectionPool();
/* 8252 */     String url = "http://";
/* 8253 */     String displayName = "";
/* 8254 */     String description = "";
/* 8255 */     ResultSet rs1 = null;
/*      */     try {
/* 8257 */       if ((bookmarkid != null) && (!bookmarkid.equals("-1"))) {
/* 8258 */         request.setAttribute("editBookmark", "true");
/* 8259 */         rs1 = AMConnectionPool.executeQueryStmt("select URL,BOOKMARKNAME,DESCRIPTION from AM_MYPAGE_BOOKMARKS where BOOKMARKID=" + bookmarkid);
/* 8260 */         if (rs1.next()) {
/* 8261 */           url = rs1.getString("URL");
/* 8262 */           displayName = rs1.getString("BOOKMARKNAME");
/* 8263 */           description = rs1.getString("DESCRIPTION");
/*      */         }
/* 8265 */         rs1.close();
/*      */       }
/*      */     } catch (Exception ex) {
/* 8268 */       ex.printStackTrace();
/*      */     }
/* 8270 */     request.setAttribute("url", url);
/* 8271 */     request.setAttribute("displayName", displayName);
/* 8272 */     request.setAttribute("description", description);
/* 8273 */     request.setAttribute("related_action", "bookmarkform");
/* 8274 */     request.setAttribute("bookmarkid", bookmarkid);
/* 8275 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp?widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   private ActionForward getCustomHtmlText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8279 */     response.setContentType("text/xml; charset=UTF-8");
/* 8280 */     String widgetid = request.getParameter("widgetid");
/*      */     try {
/* 8282 */       String widgetdescription = (String)request.getAttribute("widgetdescription");
/* 8283 */       if ((widgetdescription == null) || (widgetdescription.trim().equals(""))) {
/* 8284 */         widgetdescription = FormatUtil.getString("am.mypage.customhtml.emptymsg.text");
/* 8285 */         if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))) {
/* 8286 */           widgetdescription = widgetdescription + " " + FormatUtil.getString("am.mypage.customhtml.addtext.value", new String[] { widgetid });
/*      */         }
/*      */       }
/* 8289 */       String tempDesc = widgetdescription.toLowerCase();
/* 8290 */       if (tempDesc.indexOf("<table") != -1) {
/* 8291 */         widgetdescription = "<div style='padding:0px;margin:0px' wrap='true' class='txtGlobal errorMsg'>" + widgetdescription + "</div>";
/*      */       } else {
/* 8293 */         widgetdescription = "<table cellpadding='0' cellspacing='10' border='0' align='center' width='100%'><tr><td style='padding:4px;margin:0px' wrap='true' class='txtGlobal errorMsg' align='center'><span>" + widgetdescription + "</span></td></tr></table>";
/*      */       }
/* 8295 */       PrintWriter out = response.getWriter();
/* 8296 */       out.print(widgetdescription);
/*      */     } catch (Exception ex) {
/* 8298 */       ex.printStackTrace();
/*      */     }
/* 8300 */     return null;
/*      */   }
/*      */   
/*      */   private ActionForward getTopologyMapWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8304 */     String widgetid = request.getParameter("widgetid");
/* 8305 */     String url = "";
/* 8306 */     String description = "";
/* 8307 */     AMConnectionPool pool = new AMConnectionPool();
/* 8308 */     int widgettype = -1;
/* 8309 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*      */     try {
/* 8311 */       String widgetdescription = (String)request.getAttribute("widgetdescription");
/* 8312 */       request.setAttribute("widgettypename", DashboardUtil.getWidgetTypeName(String.valueOf(widgettype)));
/* 8313 */       String tempDesc; if ((widgetdescription == null) || (widgetdescription.trim().equals(""))) {
/* 8314 */         widgetdescription = FormatUtil.getString("am.mypage.customhtml.emptymsg.text");
/* 8315 */         if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))) {
/* 8316 */           widgetdescription = widgetdescription + " " + FormatUtil.getString("am.mypage.topologymap.addtext.value1", new String[] { widgetid });
/*      */         }
/* 8318 */         tempDesc = widgetdescription.toLowerCase();
/*      */       }
/* 8320 */       ResultSet rs1 = null;
/*      */       try {
/* 8322 */         rs1 = AMConnectionPool.executeQueryStmt("select WIDGETURL,DESCRIPTION,WIDGETTYPE from AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid);
/* 8323 */         if (rs1.next()) {
/* 8324 */           url = rs1.getString("WIDGETURL");
/* 8325 */           if ((url != null) && (!url.equals(""))) {
/* 8326 */             url = url + "&PRINTER_FRIENDLY=true";
/* 8327 */             url = url + "&isPopUp=true";
/* 8328 */             url = url + "&widgetid=" + widgetid;
/*      */           }
/* 8330 */           description = rs1.getString("DESCRIPTION");
/* 8331 */           widgettype = rs1.getInt("WIDGETTYPE");
/* 8332 */           request.setAttribute("url", url);
/*      */         }
/* 8334 */         boolean showMapForThisUser = false;
/* 8335 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/*      */ 
/* 8338 */           String[] arr = url.split("&");
/* 8339 */           if ((url != null) && (!url.equals(""))) {
/* 8340 */             String mapName = arr[1];
/* 8341 */             int equalIndex = mapName.indexOf("=");
/* 8342 */             mapName = mapName.substring(equalIndex + 1);
/* 8343 */             String listOfMapNames = MapViewUtil.getListOfMapViews(username, request);
/* 8344 */             String[] mapList = listOfMapNames.split(",");
/* 8345 */             for (int i = 0; i < mapList.length; i++) {
/* 8346 */               if (mapList[i].equals(mapName)) {
/* 8347 */                 showMapForThisUser = true;
/* 8348 */                 break;
/*      */               }
/*      */             }
/*      */           }
/* 8352 */           if (!showMapForThisUser) {
/* 8353 */             request.setAttribute("url", "");
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex1) {
/* 8358 */         ex1.printStackTrace();
/*      */       } finally {
/* 8360 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/*      */     } catch (Exception ex) {
/* 8363 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 8366 */     request.setAttribute("widgetid", widgetid);
/* 8367 */     request.setAttribute("widgetHeight", "900");
/* 8368 */     request.setAttribute("related_action", "includeTopologyMap");
/* 8369 */     request.setAttribute("description", description);
/* 8370 */     request.setAttribute("widgettype", Integer.valueOf(widgettype));
/* 8371 */     String fromDeleteMap = request.getParameter("fromDeleteMap");
/* 8372 */     if ((fromDeleteMap != null) && (!fromDeleteMap.equals(""))) {
/* 8373 */       request.setAttribute("url", "");
/*      */     }
/* 8375 */     request.setAttribute("topologyerrormessage", FormatUtil.getString("No Topology Map is available "));
/* 8376 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp?widgetid=" + widgetid);
/*      */   }
/*      */   
/*      */   public ActionForward getWidgetProperties(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 8381 */     AMConnectionPool pool = new AMConnectionPool();
/* 8382 */     ResultSet rs1 = null;
/*      */     try {
/* 8384 */       String pageid = request.getParameter("pageid");
/* 8385 */       String widgetid = request.getParameter("widgetid");
/* 8386 */       int widgettype = -1;
/* 8387 */       String resTypeforAllTypes = "";
/* 8388 */       String widgetquery = "select DESCRIPTION,WIDGETTYPE,RESOURCETYPE from AM_MYPAGE_WIDGETS where WIDGETID=" + widgetid;
/* 8389 */       rs1 = AMConnectionPool.executeQueryStmt(widgetquery);
/* 8390 */       if (rs1.next()) {
/* 8391 */         widgettype = rs1.getInt("WIDGETTYPE");
/* 8392 */         if ((rs1.getString("DESCRIPTION") != null) && (!rs1.getString("DESCRIPTION").equals(""))) {
/* 8393 */           request.setAttribute("widgetdescription", rs1.getString("DESCRIPTION"));
/*      */         }
/* 8395 */         request.setAttribute("widgettypename", DashboardUtil.getWidgetTypeName(rs1.getString("WIDGETTYPE")));
/* 8396 */         resTypeforAllTypes = rs1.getString("RESOURCETYPE");
/*      */       }
/* 8398 */       rs1.close();
/* 8399 */       if (widgettype == 2) {
/* 8400 */         if ((resTypeforAllTypes != null) && (resTypeforAllTypes.equals("$ComplexType_All")))
/*      */         {
/*      */ 
/*      */ 
/* 8404 */           AMLog.debug(":::::No need to set attid and resid for widget2 of all resourcetypes:::");
/*      */         } else {
/* 8406 */           boolean isSeconLevelResource = false;
/* 8407 */           rs1 = AMConnectionPool.executeQueryStmt("select DISPLAYNAME,ATTRIBUTE_LEVEL from  AM_MYPAGE_WIDGET_ATTRIBUTES inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=AM_MYPAGE_WIDGET_ATTRIBUTES.ATTRIBUTEID inner join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_MYPAGE_WIDGET_ATTRIBUTES.ATTRIBUTEID where WIDGETID=" + widgetid);
/*      */           
/* 8409 */           if (rs1.next()) {
/* 8410 */             request.setAttribute("attributename", rs1.getString("DISPLAYNAME"));
/* 8411 */             if (rs1.getInt("ATTRIBUTE_LEVEL") == 2) {
/* 8412 */               isSeconLevelResource = true;
/*      */             }
/*      */           }
/*      */           
/* 8416 */           rs1.close();
/* 8417 */           String resourceQuery = "select AM_MYPAGE_WIDGET_MONITORS.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_MYPAGE_WIDGET_MONITORS inner join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_MYPAGE_WIDGET_MONITORS.RESOURCEID  where WIDGETID=" + widgetid;
/* 8418 */           if (isSeconLevelResource) {
/* 8419 */             resourceQuery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_MYPAGE_WIDGET_MONITORS inner join AM_PARENTCHILDMAPPER on CHILDID= AM_MYPAGE_WIDGET_MONITORS.RESOURCEID inner join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID  where WIDGETID=" + widgetid;
/*      */           }
/* 8421 */           rs1 = AMConnectionPool.executeQueryStmt(resourceQuery);
/* 8422 */           if (rs1.next()) {
/* 8423 */             request.setAttribute("resourceid", rs1.getString("RESOURCEID"));
/* 8424 */             request.setAttribute("monitorname", EnterpriseUtil.decodeString(rs1.getString("DISPLAYNAME")));
/*      */           }
/* 8426 */           rs1.close();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 8431 */       ex.printStackTrace();
/*      */     }
/* 8433 */     request.setAttribute("related_action", "widgetdescription");
/* 8434 */     return new ActionForward("/jsp/MyPage_RelatedAttribs.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward listMGDashBoards(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 8439 */     String haid = request.getParameter("haid");
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 8444 */       request.setAttribute("dashboardlistType", "mgtemplateonly");
/* 8445 */       request.setAttribute("haid", haid);
/* 8446 */       String selectquery = "select PAGEID,PAGENAME from AM_MYPAGES where PAGETYPE='mgtemplate' and ALLMGSELECTED=1 order by PAGENAME";
/* 8447 */       ArrayList templatepagesAllMgs = this.mo.getRows(selectquery);
/* 8448 */       selectquery = "select AM_MYPAGES.PAGEID,PAGENAME from AM_MYPAGE_TEMPLATE_MGS inner join AM_MYPAGES on AM_MYPAGES.PAGEID=AM_MYPAGE_TEMPLATE_MGS.PAGEID where RESOURCEID=" + haid + " and PAGETYPE='mgtemplate' and ALLMGSELECTED=0 order by PAGENAME";
/* 8449 */       ArrayList templatepages = this.mo.getRows(selectquery);
/* 8450 */       templatepages.addAll(templatepagesAllMgs);
/* 8451 */       request.setAttribute("templatepages", templatepages);
/*      */     } catch (Exception ex) {
/* 8453 */       ex.printStackTrace();
/*      */     }
/* 8455 */     return new ActionForward("/jsp/MyPage_DashboardList.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward deleteembedurl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8459 */     String widgetid = request.getParameter("widgetid");
/*      */     
/* 8461 */     String query = "update AM_MYPAGE_WIDGETS set WIDGETURL='' where WIDGETID=" + widgetid;
/* 8462 */     AMConnectionPool.executeUpdateStmt(query);
/* 8463 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward generatePdfForWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 8468 */       String content = "";
/* 8469 */       if (request.getParameter("divToConvert") != null) {
/* 8470 */         content = request.getParameter("divToConvert");
/*      */       }
/* 8472 */       String testparam = "";
/*      */       
/* 8474 */       testparam = request.getParameter("opfile");
/* 8475 */       String opfile = "";
/* 8476 */       if (request.getParameter("opfile") != null) {
/* 8477 */         opfile = request.getParameter("opfile");
/*      */       }
/* 8479 */       String Root_Dir = System.getProperty("webnms.rootdir");
/* 8480 */       String absolutepath = Root_Dir + File.separator + "Widgets";
/* 8481 */       boolean success; if (new File(absolutepath).exists()) {
/* 8482 */         AMLog.debug("*******the dir exists in mentioned path **********" + absolutepath);
/*      */       } else {
/* 8484 */         success = new File(absolutepath).mkdir();
/*      */       }
/* 8486 */       absolutepath = absolutepath + File.separator + opfile + ".html";
/* 8487 */       File fileName = new File(absolutepath);
/*      */       
/* 8489 */       if (!fileName.exists()) {
/* 8490 */         fileName.createNewFile();
/*      */       }
/*      */       
/*      */ 
/* 8494 */       String scheme = request.getScheme();
/* 8495 */       String serverName = request.getServerName();
/* 8496 */       int serverPort = request.getServerPort();
/* 8497 */       StringBuffer baseurl = new StringBuffer();
/* 8498 */       baseurl.append(scheme).append("://").append(serverName);
/*      */       
/* 8500 */       if ((serverPort != 80) && (serverPort != 443)) {
/* 8501 */         baseurl.append(":").append(serverPort);
/*      */       }
/*      */       
/* 8504 */       String replceImages = baseurl.toString();
/* 8505 */       String widgetWebURL = baseurl + "/Widgets/" + URLEncoder.encode(opfile, "UTF-8").replace("+", "%20") + ".html?isInternalRequest=true";
/*      */       
/* 8507 */       String repeatedDomain = replceImages + replceImages;
/* 8508 */       if (content.indexOf("/images/") != -1) {
/* 8509 */         if (content.indexOf(replceImages) != -1) {
/* 8510 */           content = content.replaceAll("/images", replceImages + "/images");
/* 8511 */           if (content.indexOf(repeatedDomain) != -1) {
/* 8512 */             content = content.replaceAll(repeatedDomain, replceImages);
/*      */           }
/*      */         } else {
/* 8515 */           content = content.replaceAll("/images", replceImages + "/images");
/*      */         }
/*      */       }
/* 8518 */       if (content.indexOf("temp/") != -1) {
/* 8519 */         if (content.indexOf(replceImages) != -1) {
/* 8520 */           content = content.replaceAll("/webclient/temp", replceImages + "/webclient/temp");
/* 8521 */           if (content.indexOf(repeatedDomain) != -1) {
/* 8522 */             content = content.replaceAll(repeatedDomain, replceImages);
/*      */           }
/*      */         }
/*      */         else {
/* 8526 */           content = content.replaceAll("/webclient/temp", replceImages + "/webclient/temp");
/*      */         }
/*      */       }
/* 8529 */       if (content.indexOf("<?xml:namespace prefix = bean />") != -1) {
/* 8530 */         content = content.replaceAll("<\\?xml:namespace prefix = bean />", "");
/*      */       }
/* 8532 */       if (content.indexOf("&") != -1) {
/* 8533 */         content = content.replaceAll("&", "&amp;");
/*      */       }
/* 8535 */       FileWriter fw = new FileWriter(fileName.getAbsoluteFile());
/* 8536 */       BufferedWriter bw = new BufferedWriter(fw);
/* 8537 */       String locale = System.getProperty("locale");
/* 8538 */       Locale currentLocale = new Locale("en", "US");
/*      */       try {
/* 8540 */         if ((locale != null) && (locale.indexOf("_") != -1)) {
/* 8541 */           String[] lan_Loc = locale.trim().split("_");
/* 8542 */           currentLocale = new Locale(lan_Loc[0], lan_Loc[1]);
/*      */         }
/*      */       } catch (Exception ee) {
/* 8545 */         ee.printStackTrace();
/*      */       }
/* 8547 */       SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aaa", currentLocale);
/* 8548 */       Date time = new Date(System.currentTimeMillis());
/*      */       
/* 8550 */       String style = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../images/commonstyle.css\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../images/Blue/style.css\"/>";
/*      */       
/* 8552 */       String reportHeader = "<table width=\"100%\"><tr><td align=\"left\"><img src=\"../images/am_logo.gif\"/></td></tr><tr><td align=\"center\" style=\"font-size:20px;font-weight:bold;\">" + opfile + " Report</td></tr><tr><td align=\"right\" style=\"font-size:15px;\">" + FormatUtil.getString("am.webclient.pdfreport.greatertime.text", new String[] { sdf.format(time).toString() }) + "</td></tr></table>";
/* 8553 */       content = "<!DOCTYPE html><html><head>" + style + "</head><body>" + reportHeader + content + "</body></html>";
/* 8554 */       bw.write(content);
/* 8555 */       bw.close();
/* 8556 */       String outputFile = AMFlyingSaucerUtil.createPDFForSavedHTMLFile(widgetWebURL, opfile);
/* 8557 */       response.setContentType("application/pdf");
/* 8558 */       SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
/* 8559 */       response.setCharacterEncoding("UTF-8");
/* 8560 */       String reportName = opfile + "_" + sdf1.format(time) + ".pdf";
/* 8561 */       response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
/* 8562 */       ServletOutputStream os = null;
/* 8563 */       InputStream in = null;
/*      */       try {
/* 8565 */         os = response.getOutputStream();
/* 8566 */         in = new BufferedInputStream(new FileInputStream(new File(outputFile)));
/*      */         int ch;
/* 8568 */         while ((ch = in.read()) != -1) {
/* 8569 */           os.print((char)ch);
/*      */         }
/*      */       } catch (IOException e) {
/* 8572 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*      */         try {
/* 8576 */           if (in != null) {
/* 8577 */             in.close();
/*      */           }
/* 8579 */           if (os != null) {
/* 8580 */             os.flush();
/* 8581 */             os.close();
/*      */           }
/*      */         }
/*      */         catch (Exception e) {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 8589 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 8587 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void closeResultSet(ResultSet set)
/*      */   {
/*      */     try {
/* 8594 */       if (set != null) {
/* 8595 */         set.close();
/*      */       }
/*      */     } catch (Exception ex) {
/* 8598 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private String getNWDSubGroups() {
/* 8603 */     ResultSet subGrpRs = null;
/* 8604 */     ArrayList<String> grpList = new ArrayList();
/* 8605 */     String subGroup = new String();
/*      */     try {
/* 8607 */       String excludeInterface = "";
/* 8608 */       if (ExtConnectorUtil.getConnectorPropertyAsBoolean("opmConnector.show.nwd.widgets")) {
/* 8609 */         excludeInterface = " and SUBGROUP <> 'OpManager-Interface' and SUBGROUP like 'OpManager-%'";
/*      */       }
/* 8611 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT RESOURCETYPE from AM_ManagedResourceType where RESOURCEGROUP='NWD' " + excludeInterface);
/* 8612 */       while (subGrpRs.next()) {
/* 8613 */         grpList.add(subGrpRs.getString(1));
/*      */       }
/* 8615 */       if (grpList.size() > 0) {
/* 8616 */         subGroup = " IN (";
/* 8617 */         for (int i = 0; i < grpList.size(); i++) {
/* 8618 */           if (i != grpList.size() - 1) {
/* 8619 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "',";
/*      */           } else {
/* 8621 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "')";
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 8626 */       e.printStackTrace();
/*      */     } finally {
/* 8628 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */     
/* 8631 */     return subGroup;
/*      */   }
/*      */   
/*      */   private String getSANSubGroups() {
/* 8635 */     ResultSet subGrpRs = null;
/* 8636 */     ArrayList<String> grpList = new ArrayList();
/* 8637 */     String subGroup = new String();
/*      */     try {
/* 8639 */       String excludeInterface = "";
/* 8640 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT RESOURCETYPE from AM_ManagedResourceType where RESOURCEGROUP='SAN' " + excludeInterface);
/* 8641 */       while (subGrpRs.next()) {
/* 8642 */         grpList.add(subGrpRs.getString(1));
/*      */       }
/* 8644 */       if (grpList.size() > 0) {
/* 8645 */         subGroup = " IN (";
/* 8646 */         for (int i = 0; i < grpList.size(); i++) {
/* 8647 */           if (i != grpList.size() - 1) {
/* 8648 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "',";
/*      */           } else {
/* 8650 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "')";
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 8655 */       e.printStackTrace();
/*      */     } finally {
/* 8657 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */     
/* 8660 */     return subGroup;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Hashtable<String, String> fillExtProductDashboardIds(Hashtable<String, String> extProdDashboardHash)
/*      */   {
/* 8667 */     if ((extProdDashboardHash != null) && (!extProdDashboardHash.isEmpty())) {
/* 8668 */       AMConnectionPool pool = new AMConnectionPool();
/* 8669 */       ResultSet rs = null;
/* 8670 */       StringBuffer query = new StringBuffer("");
/* 8671 */       query.append("select NAME,VALUE from am_globalconfig where NAME in (");
/* 8672 */       Enumeration enu = extProdDashboardHash.keys();
/* 8673 */       for (int i = 1; enu.hasMoreElements(); i++) {
/* 8674 */         query.append("'");
/* 8675 */         query.append(enu.nextElement());
/* 8676 */         query.append("'");
/* 8677 */         if (i == extProdDashboardHash.size()) {
/* 8678 */           query.append(")");
/*      */         } else {
/* 8680 */           query.append(",");
/*      */         }
/*      */       }
/*      */       try {
/* 8684 */         rs = AMConnectionPool.executeQueryStmt(query.toString());
/*      */         
/* 8686 */         while (rs.next()) {
/* 8687 */           extProdDashboardHash.put(rs.getString(1), rs.getString(2));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 8700 */         return extProdDashboardHash;
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 8690 */         e.printStackTrace();
/*      */       } finally {
/*      */         try {
/* 8693 */           rs.close();
/*      */         } catch (SQLException e) {
/* 8695 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private Collection getResourceGroupsandDescLabelValueBeanArray(Hashtable<String, String> hash)
/*      */   {
/* 8704 */     Collection resourceGroupsandDesc = new ArrayList();
/*      */     
/* 8706 */     for (Enumeration e = hash.keys(); e.hasMoreElements();) {
/* 8707 */       String resGrp = (String)e.nextElement();
/* 8708 */       String desc = (String)hash.get(resGrp);
/* 8709 */       resourceGroupsandDesc.add(new LabelValueBean(desc, resGrp));
/*      */     }
/* 8711 */     return resourceGroupsandDesc;
/*      */   }
/*      */   
/*      */   private Hashtable<String, String> getTypeDescrForExternalProduct(String extProd) {
/* 8715 */     toReturn = new Hashtable();
/* 8716 */     if ((extProd != null) && (!extProd.trim().equalsIgnoreCase(""))) {
/* 8717 */       ResultSet rs = null;
/* 8718 */       ArrayList<String> associatedTypes = new ArrayList();
/* 8719 */       String query = "";
/* 8720 */       if (extProd.equals("OPMANAGER")) {
/* 8721 */         query = "select distinct(CATEGORY) from ExternalDeviceDetails where CATEGORY like 'OpManager-%' and CATEGORY not like 'OpManager-Interface%'";
/*      */       }
/* 8723 */       if ((query != null) && (!query.trim().equals(""))) {
/*      */         try {
/* 8725 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 8726 */           while (rs.next()) {
/* 8727 */             String categ = rs.getString(1);
/* 8728 */             String dispCateg = categ.substring(categ.indexOf("-") + 1);
/* 8729 */             toReturn.put(categ, dispCateg);
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
/*      */ 
/*      */ 
/* 8742 */           return toReturn;
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/* 8732 */           e.printStackTrace();
/*      */         } finally {
/*      */           try {
/* 8735 */             rs.close();
/*      */           } catch (SQLException e) {
/* 8737 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String getAssociatedTypeValsForExternalProduct(String externalProduct, ArrayList<String> associatedTypes)
/*      */   {
/* 8746 */     String toReturn = "";
/*      */     
/* 8748 */     if (associatedTypes.size() != 0) {
/* 8749 */       toReturn = "IN (";
/* 8750 */       for (int i = 0; i < associatedTypes.size(); i++) {
/* 8751 */         if (i != associatedTypes.size() - 1) {
/* 8752 */           toReturn = toReturn + "'" + (String)associatedTypes.get(i) + "',";
/*      */         } else {
/* 8754 */           toReturn = toReturn + "'" + (String)associatedTypes.get(i) + "')";
/*      */         }
/*      */       }
/*      */     }
/* 8758 */     if (toReturn.trim().equalsIgnoreCase("")) {
/* 8759 */       toReturn = "ALL";
/*      */     }
/* 8761 */     return toReturn;
/*      */   }
/*      */   
/*      */   private boolean checkWidgetFileCorrupted(File widgetsfile) {
/*      */     try {
/* 8766 */       XMLDataReader x = new XMLDataReader(widgetsfile.toString());
/* 8767 */       if ((x.getRootNode().equals("tree")) && (x.getRootNode().getChildNodes().size() > 0)) {
/* 8768 */         widgetsfile.delete();
/* 8769 */         return true;
/*      */       }
/* 8771 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 8774 */       widgetsfile.delete();
/* 8775 */       e.printStackTrace();
/*      */     }
/* 8777 */     return true;
/*      */   }
/*      */   
/*      */   private ArrayList<String> getSelectedTypesForExtProductWidget(String widgetid) {
/* 8781 */     AMConnectionPool pool = new AMConnectionPool();
/* 8782 */     ResultSet rs = null;
/* 8783 */     associatedTypes = new ArrayList();
/* 8784 */     String query = "select ASSOCIATEDTYPES from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/*      */     try {
/* 8786 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 8788 */       while (rs.next()) {
/* 8789 */         associatedTypes.add(rs.getString(1));
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
/* 8800 */       return associatedTypes;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 8792 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 8795 */         rs.close();
/*      */       } catch (SQLException e) {
/* 8797 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Map<String, String> getAttributesForResTypeWithReportsEnabled(String type)
/*      */   {
/* 8808 */     Map<String, String> attrbIdsMap = new TreeMap();
/* 8809 */     ArrayList attList = ReportUtilities.getReportEnabledAttListForResourcetype(type);
/* 8810 */     AMLog.debug(":::::Attrlist with reports enabled for all resourcetypes:::" + attList);
/*      */     
/* 8812 */     for (int j = 0; j < attList.size(); j++) {
/* 8813 */       Properties attrProps = (Properties)attList.get(j);
/* 8814 */       String attId = attrProps.getProperty("value");
/*      */       
/* 8816 */       attrbIdsMap.put(FormatUtil.getString(attrProps.getProperty("label")), attId);
/*      */     }
/* 8818 */     return attrbIdsMap;
/*      */   }
/*      */   
/*      */   private Map<String, String> getAttributesForResTypeForPolledData(String type) {
/* 8822 */     Map<String, String> attrbIdsMap = new TreeMap();
/*      */     
/* 8824 */     List attrList = null;
/* 8825 */     if ("vmcluster".equalsIgnoreCase(type)) {
/* 8826 */       attrList = ReportUtil.getAttributesForVMClusters();
/* 8827 */     } else if ("vmrpool".equalsIgnoreCase(type)) {
/* 8828 */       attrList = ReportUtil.getAttributesForVMRPools();
/*      */     } else {
/* 8830 */       attrList = ReportUtil.getAttributesForResourcetype(type);
/*      */     }
/* 8832 */     for (int j = 0; j < attrList.size(); j++) {
/* 8833 */       String attrWithId = attrList.get(j).toString();
/* 8834 */       String attrId = attrWithId.substring(0, attrWithId.indexOf("#"));
/* 8835 */       String attrName = attrWithId.substring(attrWithId.indexOf("#") + 1, attrWithId.length());
/* 8836 */       String resGroup = ReportUtil.getSubgroupForResType(type);
/* 8837 */       if (!type.equalsIgnoreCase(resGroup))
/*      */       {
/* 8839 */         attrbIdsMap.put(attrName, resGroup + "#" + attrId);
/*      */       } else {
/* 8841 */         attrbIdsMap.put(attrName, type + "#" + attrId);
/*      */       }
/*      */     }
/* 8844 */     return attrbIdsMap;
/*      */   }
/*      */   
/*      */   private HashMap<String, ArrayList<String>> getSelResAttrForAllTypes(String widgetid) {
/* 8848 */     String attribsQuery = "SELECT RESOURCEID,ATTRIBUTEID FROM AM_WIDGET_ALLTYPES WHERE WIDGETID=" + widgetid;
/* 8849 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 8850 */     ResultSet rs = null;
/* 8851 */     selectedResidattribsMap = new HashMap();
/*      */     try {
/* 8853 */       rs = AMConnectionPool.executeQueryStmt(attribsQuery);
/* 8854 */       while (rs.next()) {
/* 8855 */         String resId = rs.getString("RESOURCEID");
/* 8856 */         String attrId = rs.getString("ATTRIBUTEID");
/* 8857 */         if (!selectedResidattribsMap.containsKey(resId)) {
/* 8858 */           ArrayList<String> attrList = new ArrayList();
/* 8859 */           attrList.add(attrId);
/* 8860 */           selectedResidattribsMap.put(resId, attrList);
/*      */         } else {
/* 8862 */           ArrayList<String> existingList = new ArrayList();
/* 8863 */           existingList = (ArrayList)selectedResidattribsMap.get(resId);
/* 8864 */           existingList.add(attrId);
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
/* 8877 */       return selectedResidattribsMap;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 8869 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 8872 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception exc) {
/* 8874 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String[] getPreexistingMultiMonitor(String widgetid)
/*      */   {
/* 8881 */     String monsQuery = "SELECT RESOURCEID FROM AM_WIDGET_ALLTYPES WHERE WIDGETID=" + widgetid;
/* 8882 */     ResultSet rs5 = null;
/* 8883 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 8884 */     ArrayList<String> monList = new ArrayList();
/*      */     try {
/* 8886 */       rs5 = AMConnectionPool.executeQueryStmt(monsQuery);
/* 8887 */       while (rs5.next()) {
/* 8888 */         if (!monList.contains(rs5.getString("RESOURCEID"))) {
/* 8889 */           monList.add(rs5.getString("RESOURCEID"));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 8896 */         AMConnectionPool.closeStatement(rs5);
/*      */       } catch (Exception exc) {
/* 8898 */         exc.printStackTrace();
/*      */       }
/*      */       
/* 8901 */       multiMon = new String[monList.size()];
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 8893 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 8896 */         AMConnectionPool.closeStatement(rs5);
/*      */       } catch (Exception exc) {
/* 8898 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 8902 */     String[] multiMon = (String[])monList.toArray(multiMon);
/* 8903 */     return multiMon;
/*      */   }
/*      */   
/*      */   private String[] getPreexistingMultiAttributes(String widgetid) {
/* 8907 */     String attribsQuery = "SELECT RESOURCEID,ATTRIBUTEID FROM AM_WIDGET_ALLTYPES WHERE WIDGETID=" + widgetid;
/* 8908 */     ResultSet rs5 = null;
/* 8909 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 8910 */     ArrayList<String> attrList = new ArrayList();
/* 8911 */     String resId = null;
/* 8912 */     String attrId = null;
/* 8913 */     String resType = null;
/* 8914 */     String attrDisplay = null;
/*      */     try {
/* 8916 */       rs5 = AMConnectionPool.executeQueryStmt(attribsQuery);
/* 8917 */       while (rs5.next()) {
/* 8918 */         resId = rs5.getString("RESOURCEID");
/* 8919 */         attrId = rs5.getString("ATTRIBUTEID");
/* 8920 */         String resGroup = "";
/* 8921 */         HashMap<String, Object> resDetailsMap = ReportUtil.getResourceDetails(resId);
/* 8922 */         if (!resDetailsMap.isEmpty()) {
/* 8923 */           resType = (String)resDetailsMap.get("TYPE");
/* 8924 */           resGroup = (String)resDetailsMap.get("SUBGROUP");
/*      */         }
/* 8926 */         if (!resType.equalsIgnoreCase(resGroup))
/*      */         {
/* 8928 */           attrDisplay = resGroup + "#" + attrId;
/*      */         } else {
/* 8930 */           attrDisplay = resType + "#" + attrId;
/*      */         }
/* 8932 */         if (!attrList.contains(attrDisplay)) {
/* 8933 */           attrList.add(attrDisplay);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 8940 */         AMConnectionPool.closeStatement(rs5);
/*      */       } catch (Exception exc) {
/* 8942 */         exc.printStackTrace();
/*      */       }
/*      */       
/* 8945 */       multiAttribute = new String[attrList.size()];
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 8937 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 8940 */         AMConnectionPool.closeStatement(rs5);
/*      */       } catch (Exception exc) {
/* 8942 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 8946 */     String[] multiAttribute = (String[])attrList.toArray(multiAttribute);
/* 8947 */     return multiAttribute;
/*      */   }
/*      */   
/*      */   private ArrayList<ArrayList> processBatchForAttribute(String columnsForQuery, ArrayList<String> maxResidList, ArrayList<String> maxCollecTimeList, String limitn, String toporbottom) {
/*      */     try {
/* 8952 */       ManagedApplication mo = new ManagedApplication();
/* 8953 */       HashMap<String, ArrayList> headerAndData = new HashMap();
/* 8954 */       String columnsToQuery = "";
/* 8955 */       String datatable = null;
/* 8956 */       String resid_col = null;
/* 8957 */       String attid_col = null;
/* 8958 */       String value_col = null;
/* 8959 */       String coltime_col = null;
/* 8960 */       String expression = "";
/* 8961 */       ArrayList<String> attrExtTableDetails = null;
/* 8962 */       String process_Attribute = "-1";
/* 8963 */       String resourcetype = "";
/* 8964 */       process_Attribute = columnsForQuery;
/* 8965 */       attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(columnsForQuery);
/* 8966 */       datatable = (String)attrExtTableDetails.get(0);
/* 8967 */       resid_col = (String)attrExtTableDetails.get(1);
/* 8968 */       attid_col = (String)attrExtTableDetails.get(2);
/* 8969 */       value_col = (String)attrExtTableDetails.get(3);
/* 8970 */       coltime_col = (String)attrExtTableDetails.get(4);
/* 8971 */       resourcetype = (String)attrExtTableDetails.get(6);
/* 8972 */       expression = (String)attrExtTableDetails.get(12);
/*      */       
/*      */ 
/* 8975 */       value_col = DBQueryUtil.escapeColumn(value_col, process_Attribute);
/* 8976 */       if (columnsToQuery.equals(""))
/*      */       {
/* 8978 */         columnsToQuery = value_col + expression;
/*      */       } else {
/* 8980 */         columnsToQuery = columnsToQuery + "," + value_col + expression;
/*      */       }
/*      */       
/* 8983 */       if (attid_col.equals("-1"))
/*      */       {
/*      */ 
/* 8986 */         String metricQuery = "select " + resid_col + "," + columnsToQuery + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/*      */         
/* 8988 */         if (datatable.equalsIgnoreCase("TableSpaceStatus")) {
/* 8989 */           metricQuery = "select RESOURCEID," + columnsToQuery + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "TableSpaceStatus.RESOURCENAME", "':'", "TableSpaceStatus.TABLESPACENAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/* 8990 */         } else if (datatable.equalsIgnoreCase("DataFiles")) {
/* 8991 */           metricQuery = "select RESOURCEID," + columnsToQuery + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "DataFiles.RESOURCENAME", "':'", "DataFiles.FILE_NAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/* 8992 */         } else if (datatable.equalsIgnoreCase("RollbackData")) {
/* 8993 */           metricQuery = "select RESOURCEID," + columnsToQuery + " from " + datatable + " inner join AM_ManagedObject  on   " + DBQueryUtil.concat(new String[] { "RollbackData.RESOURCENAME", "':'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" }) + "=AM_ManagedObject.RESOURCENAME where " + ManagedApplication.getCondition("RESOURCEID", maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/* 8994 */         } else if (resid_col.equalsIgnoreCase("RESOURCENAME")) {
/* 8995 */           metricQuery = "select RESOURCEID," + columnsToQuery + " from " + datatable + " inner join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + " where " + ManagedApplication.getCondition("RESOURCEID", maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList);
/* 8996 */         } else if (datatable.equalsIgnoreCase("AM_CAM_DC_ATTRIBUTES")) {
/* 8997 */           String action = "getMetricQuery";
/* 8998 */           HashMap extraprops = new HashMap();
/* 8999 */           extraprops.put("maxCollecTimeList", maxCollecTimeList);
/* 9000 */           extraprops.put("maxResidList", maxResidList);
/* 9001 */           metricQuery = getSimilarAttributeForCAM_Actions(resourcetype, columnsForQuery, extraprops, action);
/*      */         }
/* 9003 */         ArrayList attListinConfDataTables = ConfMonitorConfiguration.getInstance().getAttListInDataTables();
/* 9004 */         AMLog.debug("processBatch:metricQuery for all types:-------->" + metricQuery);
/* 9005 */         if (metricQuery != null) {
/* 9006 */           return mo.getRows(metricQuery);
/*      */         }
/*      */         
/* 9009 */         return null;
/*      */       }
/*      */       
/* 9012 */       ArrayList<String> colForQuery = new ArrayList();
/* 9013 */       colForQuery.clear();
/* 9014 */       colForQuery.add(columnsForQuery);
/* 9015 */       String metricQuery = "select " + resid_col + "," + attid_col + "," + value_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and  " + ManagedApplication.getCondition(coltime_col, maxCollecTimeList) + " and " + ManagedApplication.getCondition(attid_col, colForQuery);
/* 9016 */       if (datatable.toUpperCase().equals("AM_CONFIGURATION_INFO"))
/*      */       {
/* 9018 */         metricQuery = "SELECT AM_CONFIGURATION_INFO." + resid_col + ",AM_CONFIGURATION_INFO." + attid_col + ",AM_CONFIGURATION_INFO.CONFVALUE from " + datatable + " where " + ManagedApplication.getCondition(resid_col, maxResidList) + " and LATEST=1 and  " + ManagedApplication.getCondition(attid_col, colForQuery);
/*      */       }
/* 9020 */       ArrayList<ArrayList> scalarData = mo.getRows(metricQuery);
/* 9021 */       return formatScalarAttributes(scalarData, maxResidList, colForQuery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 9025 */       ex.printStackTrace(); }
/* 9026 */     return null;
/*      */   }
/*      */   
/*      */   public String getOutPutAverageData(String attrId, String resId, String period, String toporBottom_N)
/*      */   {
/* 9031 */     ResultSet rs = null;
/* 9032 */     AMConnectionPool cp = new AMConnectionPool();
/* 9033 */     ArrayList<String> attrExtTableDetails = this.mo.getCachedAttributeDetails(attrId);
/* 9034 */     String archivedTableName = (String)attrExtTableDetails.get(7);
/* 9035 */     long[] timeStamps = null;
/* 9036 */     timeStamps = ReportUtilities.getTimeStamp(period);
/* 9037 */     ArrayList<String> selectedMonitors = new ArrayList();
/*      */     
/* 9039 */     String resourcetype = DBUtil.getTypeName(Integer.parseInt(resId));
/* 9040 */     selectedMonitors.add(resId);
/* 9041 */     ArrayList attribslistCheckedforComplextype = new ArrayList();
/* 9042 */     HashMap restypeAttribidMapping = new HashMap();
/* 9043 */     getAttribIdsforResourceType(resourcetype, attrId, attribslistCheckedforComplextype, restypeAttribidMapping);
/* 9044 */     String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/* 9045 */     String reportquery = "select " + archivedTableName + ".RESID,CAST(sum(TOTAL)/sum(TOTALCOUNT) as decimal (15,2)) as Average from " + archivedTableName + "   where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".RESID").toString(), selectedMonitors) + " and " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attribslistCheckedforComplextype) + "  and MINVALUE>=0 and MAXVALUE>=0 ";
/* 9046 */     String groupbyStr = "  group by RESID," + archivedTableName + ".ATTRIBUTEID ";
/*      */     
/* 9048 */     reportquery = reportquery + dailyRptCondition + groupbyStr;
/* 9049 */     reportquery = getReportQueryForComplexAttribute(reportquery, attribslistCheckedforComplextype, resourcetype);
/* 9050 */     reportquery = DBQueryUtil.getTopNValues(reportquery, toporBottom_N);
/* 9051 */     AMLog.debug("getReportData:attribute in case of all types:reportquery---->" + reportquery);
/* 9052 */     avgValue = "";
/*      */     try {
/* 9054 */       rs = AMConnectionPool.executeQueryStmt(reportquery);
/* 9055 */       while (rs.next()) {
/* 9056 */         avgValue = rs.getString("Average");
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
/* 9068 */       return avgValue;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 9059 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 9062 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception exc) {
/* 9064 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MyPageAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */