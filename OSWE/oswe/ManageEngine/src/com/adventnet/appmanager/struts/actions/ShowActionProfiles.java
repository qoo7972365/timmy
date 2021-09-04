/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.RetreiveFeedHandler;
/*      */ import com.adventnet.appmanager.util.RssConnector;
/*      */ import com.adventnet.appmanager.util.SDPIntegUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.me.apm.fault.actions.util.ActionsUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ShowActionProfiles
/*      */   extends DispatchAction
/*      */ {
/*   46 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public ActionForward generateActionUsedbyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/*   50 */       int actionId = -1;
/*   51 */       if (request.getParameter("actionid") != null) {
/*   52 */         actionId = Integer.parseInt(request.getParameter("actionid"));
/*      */       }
/*   54 */       String actionName = request.getParameter("actionname");
/*   55 */       ArrayList detailsList = new ArrayList();
/*   56 */       if (actionId != -1) {
/*   57 */         int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/*   58 */         boolean isPrivilegedUser = com.adventnet.appmanager.util.Constants.isPrivilegedUser(request);
/*   59 */         detailsList = ActionsUtil.getAllActions(userID, isPrivilegedUser, actionId);
/*   60 */         if (detailsList != null) {
/*   61 */           request.setAttribute("actionConfig", detailsList);
/*      */         }
/*   63 */         request.setAttribute("actionName", actionName);
/*      */       }
/*      */     } catch (Exception e) {
/*   66 */       e.printStackTrace();
/*      */     }
/*   68 */     return mapping.findForward("report.action.csv");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getHAProfiles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   77 */     String first = getHolisticApps(mapping, form, request, response);
/*   78 */     String haid = request.getParameter("haid");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*   83 */     if (request.getParameter("admin") != null)
/*      */     {
/*   85 */       getMonitors(mapping, form, request, response, null);
/*   86 */       if ((haid == null) || ("0".equals(haid)))
/*      */       {
/*   88 */         if (first != null)
/*      */         {
/*   90 */           haid = first;
/*   91 */           ((AMActionForm)form).setHaid(haid);
/*      */         }
/*      */         else
/*      */         {
/*   95 */           return new ActionForward("/jsp/AssociatedThresholds.jsp?monitor=true&none=true");
/*      */         }
/*      */       }
/*      */     }
/*   99 */     String baname = "";
/*  100 */     String query = null;
/*  101 */     Map hadetails = new LinkedHashMap();
/*      */     
/*      */ 
/*  104 */     Vector haidlist = new Vector();
/*  105 */     Vector treelist = new Vector();
/*  106 */     haidlist.add(haid);
/*  107 */     treelist.add("");
/*  108 */     this.mo.getMGtree(haidlist, treelist, haid, "");
/*  109 */     String processAndServiceQuery = "(SELECT IMAGEPATH, DISPLAYNAME, RESOURCETYPE FROM AM_ManagedResourceType UNION SELECT '" + ChildMOHandler.getImageForChildType("Service") + "','Service','Service' UNION SELECT '" + ChildMOHandler.getImageForChildType("Process") + "','Process','Process') AM_ManagedResourceType";
/*  110 */     for (int k = 0; k < haidlist.size(); k++)
/*      */     {
/*  112 */       String treeStructure = "";
/*  113 */       String temphaid = (String)haidlist.get(k);
/*      */       
/*  115 */       query = "select DISTINCT AM_ManagedObject.RESOURCENAME , AM_ManagedObject.DISPLAYNAME , AM_ATTRIBUTES.ATTRIBUTE ,  AM_THRESHOLDCONFIG.NAME , AM_ATTRIBUTEACTIONMAPPER.ACTIONID,   AM_ATTRIBUTEACTIONMAPPER.SEVERITY, AM_ACTIONPROFILE.NAME ,AM_ManagedResourceType.IMAGEPATH , AM_ATTRIBUTES.ATTRIBUTEID , AM_ManagedObject.RESOURCEID ,AM_THRESHOLDCONFIG.ID ,AM_ACTIONPROFILE.ID , AM_ACTIONPROFILE.TYPE,AM_ManagedResourceType.DISPLAYNAME,AM_ATTRIBUTES.TYPE,AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION,AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE,AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION,AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE,AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION,AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE,COALESCE(AM_ATTRIBUTES.UNITS,''),AM_ManagedObject.RESOURCEID, AM_ATTRIBUTES.DISPLAYNAME,AM_THRESHOLDCONFIG.TYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME from " + processAndServiceQuery + " join AM_ManagedObject on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID or AM_ManagedObject.RESOURCEID=" + temphaid + "  LEFT OUTER JOIN AM_ATTRIBUTEACTIONMAPPER  ON AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE AND AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID LEFT OUTER JOIN AM_ACTIONPROFILE ON AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID  and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID where AM_PARENTCHILDMAPPER.PARENTID=" + temphaid + " and AM_ATTRIBUTES.ATTRIBUTEID <> 403 and AM_ATTRIBUTES.ATTRIBUTEID <> 411 and AM_ATTRIBUTES.TYPE not in (8) and ENABLE_THRESHOLD=1 order by AM_ManagedObject.DISPLAYNAME, AM_ATTRIBUTES.TYPE desc, AM_ATTRIBUTES.DISPLAYNAME";
/*      */       
/*  117 */       ArrayList haprofiles = this.mo.getRows(query);
/*  118 */       for (int i = 0; i < haprofiles.size(); i++)
/*      */       {
/*  120 */         ArrayList row = (ArrayList)haprofiles.get(i);
/*      */         try
/*      */         {
/*  123 */           treeStructure = "";
/*  124 */           if (haid.equals((String)row.get(22)))
/*      */           {
/*  126 */             baname = (String)row.get(0);
/*      */           }
/*  128 */           if (!temphaid.equals((String)row.get(22)))
/*      */           {
/*  130 */             treeStructure = k < treelist.size() ? (String)treelist.get(k) : "";
/*      */           }
/*  132 */           if ((hadetails.containsKey((String)row.get(0))) || ((ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(13))) && (hadetails.containsKey((String)row.get(0) + "_" + (String)row.get(9)))) || (("Script Monitor".equals((String)row.get(13))) && (hadetails.containsKey((String)row.get(0) + "_" + (String)row.get(9)))))
/*      */           {
/*  134 */             LinkedHashMap previous = (LinkedHashMap)hadetails.get((String)row.get(0));
/*  135 */             if ("Script Monitor".equals((String)row.get(13))) {
/*  136 */               previous = (LinkedHashMap)hadetails.get((String)row.get(0) + "_" + (String)row.get(9));
/*      */             }
/*  138 */             previous.put("displayname", treeStructure + (String)row.get(1));
/*  139 */             previous.put("icon", (String)row.get(7));
/*  140 */             previous.put("resourceid", (String)row.get(9));
/*  141 */             previous.put("type", (String)row.get(13));
/*  142 */             if (previous.containsKey((String)row.get(2)))
/*      */             {
/*  144 */               Properties p = (Properties)previous.get((String)row.get(2));
/*  145 */               p.setProperty("attributeid", (String)row.get(8));
/*  146 */               p.setProperty("attributetype", (String)row.get(14));
/*  147 */               p.setProperty("displayname", (String)row.get(23));
/*  148 */               if (row.get(3) != null)
/*      */               {
/*  150 */                 p.setProperty("name", (String)row.get(3));
/*  151 */                 p.setProperty("thresholdid", (String)row.get(10));
/*  152 */                 p.setProperty("criticalcondition", (String)row.get(15));
/*  153 */                 p.setProperty("criticalvalue", (String)row.get(16));
/*  154 */                 p.setProperty("warningcondition", (String)row.get(17));
/*  155 */                 p.setProperty("warningvalue", (String)row.get(18));
/*  156 */                 p.setProperty("clearcondition", (String)row.get(19));
/*  157 */                 p.setProperty("clearvalue", (String)row.get(20));
/*  158 */                 String threshType = (String)row.get(24);
/*  159 */                 if (threshType.equals("3"))
/*      */                 {
/*  161 */                   updatePatternValues(p, (String)row.get(10));
/*      */                 }
/*      */               }
/*  164 */               if (row.get(21) != null)
/*      */               {
/*  166 */                 p.setProperty("attributeunit", (String)row.get(21));
/*      */               }
/*  168 */               if (row.get(5) != null)
/*      */               {
/*  170 */                 if (p.getProperty((String)row.get(5)) == null)
/*      */                 {
/*  172 */                   p.setProperty((String)row.get(5), (String)row.get(6) + ":" + (String)row.get(4));
/*      */                 }
/*      */                 else
/*      */                 {
/*  176 */                   p.setProperty((String)row.get(5), p.getProperty((String)row.get(5)) + "," + (String)row.get(6) + ":" + (String)row.get(4));
/*      */                 }
/*      */               }
/*  179 */               previous.put((String)row.get(2), p);
/*  180 */               if (row.get(25) != null) {
/*  181 */                 p.setProperty("anomalyid", (String)row.get(25));
/*  182 */                 p.setProperty("anomalyname", (String)row.get(26));
/*      */               }
/*      */               
/*      */             }
/*      */             else
/*      */             {
/*  188 */               Properties p = new Properties();
/*  189 */               p.setProperty("attributeid", (String)row.get(8));
/*  190 */               p.setProperty("attributetype", (String)row.get(14));
/*  191 */               p.setProperty("displayname", (String)row.get(23));
/*  192 */               if (row.get(3) != null)
/*      */               {
/*  194 */                 p.setProperty("name", (String)row.get(3));
/*  195 */                 p.setProperty("thresholdid", (String)row.get(10));
/*  196 */                 p.setProperty("criticalcondition", (String)row.get(15));
/*  197 */                 p.setProperty("criticalvalue", (String)row.get(16));
/*  198 */                 p.setProperty("warningcondition", (String)row.get(17));
/*  199 */                 p.setProperty("warningvalue", (String)row.get(18));
/*  200 */                 p.setProperty("clearcondition", (String)row.get(19));
/*  201 */                 p.setProperty("clearvalue", (String)row.get(20));
/*  202 */                 String threshType = (String)row.get(24);
/*  203 */                 if (threshType.equals("3"))
/*      */                 {
/*  205 */                   updatePatternValues(p, (String)row.get(10));
/*      */                 }
/*      */               }
/*  208 */               if (row.get(21) != null)
/*      */               {
/*  210 */                 p.setProperty("attributeunit", (String)row.get(21));
/*      */               }
/*  212 */               if (row.get(5) != null)
/*      */               {
/*  214 */                 if (p.getProperty((String)row.get(5)) == null)
/*      */                 {
/*  216 */                   p.setProperty((String)row.get(5), (String)row.get(6) + ":" + (String)row.get(4));
/*      */                 }
/*      */                 else
/*      */                 {
/*  220 */                   p.setProperty((String)row.get(5), p.getProperty((String)row.get(5)) + "," + (String)row.get(6) + ":" + (String)row.get(4));
/*      */                 }
/*      */               }
/*  223 */               previous.put((String)row.get(2), p);
/*  224 */               if (row.get(25) != null) {
/*  225 */                 p.setProperty("anomalyid", (String)row.get(25));
/*  226 */                 p.setProperty("anomalyname", (String)row.get(26));
/*      */               }
/*      */               
/*      */             }
/*      */             
/*      */           }
/*      */           else
/*      */           {
/*  234 */             LinkedHashMap h = new LinkedHashMap();
/*  235 */             h.put("displayname", treeStructure + (String)row.get(1));
/*  236 */             h.put("icon", (String)row.get(7));
/*  237 */             h.put("resourceid", (String)row.get(9));
/*  238 */             h.put("type", (String)row.get(13));
/*  239 */             Properties p = new Properties();
/*  240 */             p.setProperty("attributeid", (String)row.get(8));
/*  241 */             p.setProperty("attributetype", (String)row.get(14));
/*  242 */             p.setProperty("displayname", (String)row.get(23));
/*  243 */             if (row.get(3) != null)
/*      */             {
/*  245 */               p.setProperty("name", (String)row.get(3));
/*  246 */               p.setProperty("thresholdid", (String)row.get(10));
/*  247 */               p.setProperty("criticalcondition", (String)row.get(15));
/*  248 */               p.setProperty("criticalvalue", (String)row.get(16));
/*  249 */               p.setProperty("warningcondition", (String)row.get(17));
/*  250 */               p.setProperty("warningvalue", (String)row.get(18));
/*  251 */               p.setProperty("clearcondition", (String)row.get(19));
/*  252 */               p.setProperty("clearvalue", (String)row.get(20));
/*  253 */               String threshType = (String)row.get(24);
/*  254 */               if (threshType.equals("3"))
/*      */               {
/*  256 */                 updatePatternValues(p, (String)row.get(10));
/*      */               }
/*      */             }
/*  259 */             if (row.get(21) != null)
/*      */             {
/*  261 */               p.setProperty("attributeunit", (String)row.get(21));
/*      */             }
/*  263 */             if (row.get(5) != null)
/*      */             {
/*  265 */               if (p.getProperty((String)row.get(5)) == null)
/*      */               {
/*  267 */                 p.setProperty((String)row.get(5), (String)row.get(6) + ":" + (String)row.get(4));
/*      */               }
/*      */               else
/*      */               {
/*  271 */                 p.setProperty((String)row.get(5), p.getProperty((String)row.get(5)) + "," + (String)row.get(6) + ":" + (String)row.get(4));
/*      */               }
/*      */             }
/*  274 */             h.put((String)row.get(2), p);
/*  275 */             if ("Script Monitor".equals((String)row.get(13))) {
/*  276 */               hadetails.put((String)row.get(0) + "_" + (String)row.get(9), h);
/*      */             }
/*  278 */             else if ((ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(13))) && (hadetails.containsKey((String)row.get(0) + "_" + (String)row.get(9)))) {
/*  279 */               hadetails.put((String)row.get(0) + "_" + (String)row.get(9), h);
/*      */             }
/*      */             else {
/*  282 */               hadetails.put((String)row.get(0), h);
/*      */             }
/*  284 */             hadetails.put("baname", baname);
/*  285 */             if (row.get(25) != null) {
/*  286 */               p.setProperty("anomalyid", (String)row.get(25));
/*  287 */               p.setProperty("anomalyname", (String)row.get(26));
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ee) {
/*  292 */           ee.printStackTrace();
/*  293 */           AMLog.debug("ShowActionProfiles : showActionProfiles : Exception " + ee.getMessage() + " for temphaid===>" + temphaid + " Query ==>\n" + query + " \n row" + row + " \n");
/*      */         }
/*      */       }
/*      */     }
/*  297 */     request.setAttribute("hadetails", hadetails);
/*  298 */     ArrayList actions = new ArrayList();
/*  299 */     ResultSet rs = null;
/*  300 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  301 */     query = "select ID,NAME from AM_ACTIONPROFILE where AM_ACTIONPROFILE.NAME NOT IN('Marker','Restart The Service') order by TYPE,NAME";
/*      */     try
/*      */     {
/*  304 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  308 */       rs = null;
/*  309 */       e.printStackTrace();
/*      */     }
/*  311 */     while (rs.next())
/*      */     {
/*  313 */       HashMap temp = new HashMap();
/*  314 */       temp.put("actionid", rs.getString("ID"));
/*  315 */       temp.put("actionname", rs.getString("NAME"));
/*  316 */       actions.add(temp);
/*      */     }
/*  318 */     boolean actionsAvailable = rs.first();
/*  319 */     rs.close();
/*  320 */     request.setAttribute("actions", actions);
/*  321 */     request.setAttribute("actionsAvailable", Boolean.valueOf(actionsAvailable));
/*  322 */     return new ActionForward("/jsp/AssociatedThresholds.jsp?haid=" + haid + "");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getTypeforId(String resourceid)
/*      */   {
/*  331 */     String type = "";
/*  332 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/*  335 */       String qry = "select type from AM_ManagedObject where resourceid=" + resourceid;
/*  336 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  337 */       if (rs.next())
/*      */       {
/*  339 */         type = rs.getString(1);
/*      */       }
/*  341 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  345 */       exc.printStackTrace();
/*      */     }
/*  347 */     return type;
/*      */   }
/*      */   
/*      */   public ActionForward getResourceProfiles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  353 */     response.setContentType("text/html;charset=UTF-8");
/*      */     
/*  355 */     String reqID = request.getParameter("reqID");
/*  356 */     String viewBy = request.getParameter("viewBy");
/*  357 */     if (EnterpriseUtil.isAdminServer()) {
/*  358 */       request.setAttribute("isAdminServer", Boolean.valueOf(true));
/*  359 */       if (viewBy == null) {
/*  360 */         viewBy = "monitorGroups";
/*      */       }
/*      */     }
/*      */     
/*  364 */     if ((reqID != null) && (reqID.equals("getFilteredMonitors")))
/*      */     {
/*  366 */       return getFilteredMonitors(mapping, form, request, response);
/*      */     }
/*      */     
/*  369 */     if ((reqID != null) && (reqID.equals("getFilteredTypes")))
/*      */     {
/*  371 */       getHolisticApps(mapping, form, request, response);
/*  372 */       return getFilteredMonitorTypes(mapping, form, request, response);
/*      */     }
/*      */     
/*      */ 
/*  376 */     ArrayList configurationAttList = new ArrayList();
/*      */     
/*  378 */     ((AMActionForm)form).setViewBy(viewBy);
/*      */     
/*  380 */     String resourceType = request.getParameter("montype");
/*  381 */     String first = getResourceTypes(mapping, form, request, response);
/*  382 */     if ((resourceType == null) && (first != null))
/*      */     {
/*  384 */       resourceType = first;
/*      */     }
/*      */     
/*      */ 
/*  388 */     ((AMActionForm)form).setMontype(resourceType);
/*  389 */     ((AMActionForm)form).setMgTemplateType(resourceType);
/*      */     
/*  391 */     if ((viewBy != null) && (viewBy.equals("monitorGroups")))
/*      */     {
/*  393 */       return getHAProfiles(mapping, form, request, response);
/*      */     }
/*      */     
/*  396 */     int id = 0;
/*  397 */     if ("true".equals(request.getParameter("groupTemplate"))) {
/*  398 */       request.setAttribute("groupTemplate", Boolean.valueOf(true));
/*  399 */       String haid = request.getParameter("haidValue");
/*  400 */       request.setAttribute("haid", haid);
/*  401 */       id = Integer.parseInt(haid);
/*      */     }
/*      */     
/*      */ 
/*  405 */     String resourceid = request.getParameter("resourceid");
/*  406 */     if ((resourceid == null) && (resourceType != null) && ("monitorType".equals(viewBy))) {
/*  407 */       resourceid = "0";
/*      */     }
/*  409 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  410 */     ResultSet rs = null;
/*      */     
/*  412 */     String orgName = request.getParameter("organization");
/*  413 */     String siteName = request.getParameter("siteName");
/*  414 */     if (("monitorType".equalsIgnoreCase(viewBy)) && (request.getParameter("admin") != null))
/*      */     {
/*  416 */       getHolisticApps(mapping, form, request, response);
/*  417 */       getMonitors(mapping, form, request, response, null);
/*  418 */       ((AMActionForm)form).setResourceid("-1");
/*      */     }
/*  420 */     else if (request.getParameter("admin") != null)
/*      */     {
/*  422 */       getHolisticApps(mapping, form, request, response);
/*      */       
/*  424 */       String firstMon = getMonitors(mapping, form, request, response, resourceType);
/*  425 */       if (resourceid == null)
/*      */       {
/*  427 */         if (firstMon != null)
/*      */         {
/*  429 */           resourceid = firstMon;
/*      */ 
/*      */ 
/*      */         }
/*  433 */         else if (viewBy == null)
/*      */         {
/*  435 */           ((AMActionForm)form).setViewBy("monitor");
/*      */         }
/*      */         else
/*      */         {
/*  439 */           return new ActionForward("/showActionProfiles.do?method=getHAProfiles&admin=true", true);
/*      */         }
/*      */       }
/*      */       
/*  443 */       ((AMActionForm)form).setResourceid(resourceid);
/*      */     }
/*  445 */     String resourcetype = "";
/*  446 */     String query = "";
/*  447 */     String attTypesCondition = "AM_ATTRIBUTES.TYPE in ('0','1','2','3','7','5','6','8')";
/*  448 */     if (("monitors".equalsIgnoreCase(viewBy)) || (viewBy == null))
/*      */     {
/*  450 */       resourcetype = getTypeforId(resourceid);
/*      */       
/*      */ 
/*  453 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (request.getParameter("resourceid") != null) && ((resourcetype.contains("Windows")) || (resourcetype.equals("Node")) || (resourcetype.equals("Novell")) || (resourcetype.equals("Linux")) || (resourcetype.equals("snmp-node")) || (resourcetype.equals("SUN")) || (resourcetype.equals("SUN PC")) || (resourcetype.equals("AIX")) || (resourcetype.equals("HP-UX")) || (resourcetype.equals("HP-TRU64")) || (resourcetype.equals("FreeBSD / OpenBSD")) || (resourcetype.equals("OpenBSD")) || (resourcetype.equals("FreeBSD")) || (resourcetype.equals("Mac OS"))))
/*      */       {
/*  455 */         request.setAttribute("oldtab", "2");
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
/*  471 */       String errAttTypeCondn = "";
/*  472 */       if ((resourcetype != null) && (com.adventnet.appmanager.util.Constants.serverTypes.contains(resourcetype))) {
/*  473 */         errAttTypeCondn = "  or AM_ATTRIBUTES.RESOURCETYPE in ('Error') ";
/*      */       }
/*  475 */       String camAttCondn1 = "";
/*  476 */       String camAttCondn2 = "";
/*  477 */       if ((resourcetype != null) && (resourcetype.equals("JBOSS-server"))) {
/*  478 */         camAttCondn1 = " left outer join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID is not null ";
/*  479 */         camAttCondn2 = " and (AM_ATTRIBUTES.ATTRIBUTEID<10000000 or AM_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID) ";
/*      */       }
/*  481 */       String ThresholdCondition = "";
/*  482 */       if ((resourcetype != null) && (com.adventnet.appmanager.util.Constants.server_windows.contains(resourcetype))) {
/*  483 */         ThresholdCondition = "(Enable_Threshold=1 or AM_ATTRIBUTES.ATTRIBUTE in ('EventLogRuleMatched'))";
/*      */       }
/*  485 */       else if (resourcetype != null) {
/*  486 */         ThresholdCondition = "Enable_Threshold=1";
/*      */       }
/*  488 */       query = new StringBuilder().append("select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE ").append(errAttTypeCondn).append(camAttCondn1).append(" left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where ").append(attTypesCondition).append(camAttCondn2).append(" and ").append(ThresholdCondition).append(" and AM_ManagedObject.RESOURCEID=").toString() + resourceid;
/*      */       
/*  490 */       if ((resourceid != null) && (!resourceid.trim().equals("")) && (com.adventnet.appmanager.util.Constants.resourceTypesEUM.contains(resourcetype)) && (isEUMParent(resourceid)))
/*      */       {
/*  492 */         query = query + " and AM_ATTRIBUTES.TYPE in ('1','2') ";
/*      */       }
/*  494 */       query = query + " order by AM_ATTRIBUTES.TYPE  ,AM_ATTRIBUTES.DISPLAYNAME,AM_ACTIONPROFILE.TYPE,AM_ACTIONPROFILE.NAME";
/*  495 */       if ((resourcetype != null) && (resourcetype.equals("Script Monitor")))
/*      */       {
/*  497 */         String query1 = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED  from AM_ManagedObject join AM_Script_Resource_Attributes_Mapper on AM_Script_Resource_Attributes_Mapper.resourceid=AM_ManagedObject.resourceid join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_Script_Resource_Attributes_Mapper.resourceid=AM_ManagedObject.resourceid  and AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ManagedObject.RESOURCEID=AM_Script_Resource_Attributes_Mapper.resourceid and AM_ATTRIBUTES.TYPE in ('0','1','2','3') and AM_ManagedObject.RESOURCEID=" + resourceid;
/*  498 */         String query2 = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED  from AM_ManagedObject join  AM_ATTRIBUTES on  (AM_ATTRIBUTES.ATTRIBUTEID IN(2200,2201,2202) and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE) left outer join AM_ATTRIBUTETHRESHOLDMAPPER on (AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID) left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ATTRIBUTES.TYPE in ('0','1','2','3') and AM_ManagedObject.RESOURCEID=" + resourceid + " order by AM_ATTRIBUTES.TYPE desc,AM_ATTRIBUTES.DISPLAYNAME,AM_ACTIONPROFILE.TYPE,AM_ACTIONPROFILE.NAME";
/*  499 */         query = "( " + query1 + " ) " + " UNION " + " ( " + query2 + " ) ";
/*      */       }
/*  501 */       if ((resourcetype != null) && (resourcetype.equals("QueryMonitor")))
/*      */       {
/*  503 */         query = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME, AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED  from AM_ManagedObject  join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ATTRIBUTES.TYPE in ('0','1','2','3') and  AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_ATTRIBUTES.ATTRIBUTEID in (5000,5001) order by AM_ATTRIBUTES.TYPE  ,AM_ATTRIBUTES.DISPLAYNAME,AM_ACTIONPROFILE.TYPE,AM_ACTIONPROFILE.NAME";
/*      */       }
/*  505 */       if ((resourcetype != null) && (resourcetype.equals("SAP-CCMS")))
/*      */       {
/*  507 */         query = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ATTRIBUTES.TYPE in ('0','1','2','3') join AM_RCAMAPPER on AM_RCAMAPPER.PARENTRESOURCEID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTES.ATTRIBUTEID=AM_RCAMAPPER.CHILD_RESOURCEATTRIBUTEMAPPERID left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ManagedObject.RESOURCEID=" + resourceid + " union select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ATTRIBUTES.TYPE in ('0','1','2','3') and AM_ATTRIBUTES.ATTRIBUTEID=4001 left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID  left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ManagedObject.RESOURCEID=" + resourceid + " order by ATTRIBUTESTYPE desc,ATTRIBUTEDISPLAYNAME,TYPE,ACTIONNAME";
/*      */       }
/*  509 */       if ((resourcetype != null) && (resourcetype.equals("SNMP")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  514 */         query = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID  and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_ManagedObject.RESOURCEID left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ATTRIBUTES.TYPE in ('0','1','2','3','7') and AM_ManagedObject.RESOURCEID=" + resourceid + "  UNION select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME,AM_ATTRIBUTES_EXT.ISARCHIVEING,AM_ATTRIBUTES_EXT.REPORTS_ENABLED from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where AM_ATTRIBUTES.TYPE in ('0','1','2','3','7') and AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID is null and AM_ManagedObject.RESOURCEID=" + resourceid + " order by ATTRIBUTESTYPE  ,ATTRIBUTEDISPLAYNAME,TYPE,ACTIONNAME ";
/*      */       }
/*  516 */       if ((resourcetype != null) && ((resourcetype.indexOf("OpManager") != -1) || (resourcetype.indexOf("OpStor") != -1)))
/*      */       {
/*  518 */         query = new StringBuilder().append("select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID where ").append(attTypesCondition).append(" and AM_ManagedObject.RESOURCEID=").toString() + resourceid;
/*      */       }
/*      */     }
/*  521 */     else if ("monitorType".equalsIgnoreCase(viewBy))
/*      */     {
/*  523 */       String joinAMAttrExt = "";
/*  524 */       String thresholdEnabled = "') ";
/*      */       
/*  526 */       if ((resourceType != null) && (!resourceType.contains("OpManager")) && (!resourceType.contains("OpStor")))
/*      */       {
/*  528 */         if ((resourceType != null) && (resourceType.equals("Script Monitor")))
/*      */         {
/*  530 */           attTypesCondition = " AM_ATTRIBUTES.ATTRIBUTEID IN (2200,2201,2202) AND " + attTypesCondition;
/*      */         }
/*  532 */         joinAMAttrExt = " left join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID ";
/*  533 */         if (com.adventnet.appmanager.util.Constants.serverTypesArray.contains(resourceType.toLowerCase())) {
/*  534 */           thresholdEnabled = "') and (ENABLE_THRESHOLD=1 OR AM_ATTRIBUTES.ATTRIBUTEID=721 OR AM_ATTRIBUTES.ATTRIBUTE='EventLogRuleMatched')";
/*      */         }
/*      */         else {
/*  537 */           thresholdEnabled = "') and (ENABLE_THRESHOLD=1 OR AM_ATTRIBUTES.ATTRIBUTE='EventLogRuleMatched')";
/*      */         }
/*      */       }
/*  540 */       query = new StringBuilder().append("select AM_ATTRIBUTES.RESOURCETYPE as DISPLAYNAME,AM_ATTRIBUTES.RESOURCETYPE , AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_PredefinedAction.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE  from AM_ATTRIBUTES ").append(joinAMAttrExt).append(" left join AM_PredefinedThreshold  on AM_ATTRIBUTES.ATTRIBUTEID=AM_PredefinedThreshold.ATTRIBUTEID and AM_PredefinedThreshold.RESOURCETYPE ='").toString() + resourceType + "' and AM_PredefinedThreshold.ID =" + id + " left join AM_THRESHOLDCONFIG  on AM_THRESHOLDCONFIG.ID=AM_PredefinedThreshold.THRESHOLDCONFIGURATIONID left join  AM_PredefinedAction on AM_ATTRIBUTES.ATTRIBUTEID=AM_PredefinedAction.ATTRIBUTEID and AM_PredefinedAction.RESOURCETYPE = '" + resourceType + "' and AM_PredefinedAction.ID = " + id + new StringBuilder().append(" left join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_PredefinedAction.ACTIONID where ").append(attTypesCondition).append("  and AM_ATTRIBUTES.RESOURCETYPE in (select DISTINCT(S1.RESOURCETYPE) from AM_ATTRIBUTES AS S1, AM_ATTRIBUTES AS S2, AM_ATTRIBUTESDEPENDENCY WHERE S1.ATTRIBUTEID=AM_ATTRIBUTESDEPENDENCY.CHILDID AND S2.ATTRIBUTEID=AM_ATTRIBUTESDEPENDENCY.PARENTID AND S2.RESOURCETYPE='").toString() + resourceType + thresholdEnabled + "  order by AM_ATTRIBUTES.RESOURCETYPE ,AM_ATTRIBUTES.TYPE ,AM_ATTRIBUTES.DISPLAYNAME";
/*      */     }
/*      */     
/*  543 */     Map m = new LinkedHashMap();
/*      */     try
/*      */     {
/*  546 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  547 */       if (("monitors".equalsIgnoreCase(viewBy)) || (viewBy == null))
/*      */       {
/*  549 */         if ((resourcetype != null) && (resourcetype.equals("Script Monitor")) && (rs.next()))
/*      */         {
/*  551 */           rs.beforeFirst();
/*      */         }
/*  553 */         else if ((resourcetype != null) && (resourcetype.equals("Script Monitor")))
/*      */         {
/*  555 */           query = "select AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE as RESOURCETYPE, AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_ATTRIBUTEACTIONMAPPER.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE,AM_ANOMALYDETECTIONDETAILS.ID as ANOMALYID,AM_ANOMALYDETECTIONDETAILS.NAME as ANOMALYNAME from AM_ManagedObject  join AM_ATTRIBUTES on AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE left outer join AM_ATTRIBUTETHRESHOLDMAPPER on AM_ATTRIBUTETHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_THRESHOLDCONFIG on AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID left outer join AM_ANOMALYTHRESHOLDMAPPER on AM_ANOMALYTHRESHOLDMAPPER.ID=AM_ManagedObject.RESOURCEID and AM_ANOMALYTHRESHOLDMAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ANOMALYDETECTIONDETAILS on AM_ANOMALYDETECTIONDETAILS.ID=AM_ANOMALYTHRESHOLDMAPPER.ANOMALYID where AM_ATTRIBUTES.ATTRIBUTEID IN(2200,2201,2202) and AM_ATTRIBUTES.TYPE in ('0','1','2','3') and AM_ManagedObject.RESOURCEID=" + resourceid + " order by AM_ATTRIBUTES.TYPE ,AM_ATTRIBUTES.DISPLAYNAME,AM_ACTIONPROFILE.TYPE,AM_ACTIONPROFILE.NAME";
/*  556 */           rs = AMConnectionPool.executeQueryStmt(query);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  562 */       rs = null;
/*  563 */       e.printStackTrace();
/*      */     }
/*      */     
/*  566 */     StringBuffer attributeIdBuf = new StringBuffer();
/*  567 */     ArrayList patternMatcherThresholdList = new ArrayList();
/*  568 */     ArrayList stringAttributesList = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  574 */     HashMap healthMap = new HashMap();
/*  575 */     HashMap availabilityMap = new HashMap();
/*  576 */     LinkedHashMap secondaryAttributesMap = new LinkedHashMap();
/*      */     try {
/*  578 */       ArrayList scalarAttrbList = null;
/*  579 */       boolean isEnabled = NewMonitorUtil.isListingOnlySupportedAttrbEnabled(resourcetype);
/*  580 */       if (isEnabled)
/*      */       {
/*  582 */         Properties argsProps = new Properties();
/*  583 */         argsProps.setProperty("montype", resourcetype);
/*  584 */         argsProps.setProperty("resourceid", resourceid);
/*  585 */         HashMap notSupportedAttrbMap = NewMonitorUtil.getNotSupportedAttributesList(argsProps, false);
/*  586 */         if (notSupportedAttrbMap != null) {
/*  587 */           scalarAttrbList = (ArrayList)notSupportedAttrbMap.get("SCALAR");
/*      */         }
/*  589 */         AMLog.debug("ShowActionProfiles.getResourceProfiles():resourceType::::" + resourcetype + "   scalarAttrbList::::" + scalarAttrbList);
/*      */       }
/*  591 */       while (rs.next())
/*      */       {
/*  593 */         String attributeId = rs.getString("ATTRIBUTEID");
/*  594 */         if ((scalarAttrbList == null) || (!scalarAttrbList.contains(attributeId)))
/*      */         {
/*      */ 
/*  597 */           String resType = rs.getString("RESOURCETYPE");
/*  598 */           if (((!com.adventnet.appmanager.util.Constants.serverTypesArray.contains(resourceType.toLowerCase())) && (!com.adventnet.appmanager.util.Constants.databaseServersList.contains(resourceType))) || ((!"Script Monitor".equals(resType)) && (!"UrlMonitor".equals(resType)) && (!"UrlSeq".equals(resType))))
/*      */           {
/*      */ 
/*  601 */             Boolean isSecondaryLevelAttr = Boolean.valueOf((!resType.equals(resourceType)) && (viewBy != null) && (viewBy.equalsIgnoreCase("monitorType")));
/*  602 */             String secondaryResourceType = "";
/*  603 */             if (isSecondaryLevelAttr.booleanValue())
/*      */             {
/*  605 */               secondaryResourceType = (ConfMonitorConfiguration.getInstance().isConfMonitor(resourceType)) && (resType.indexOf("_") != -1) ? resType.split("_")[1] : resType;
/*      */             }
/*  607 */             if ((!m.containsKey(attributeId)) && (!healthMap.containsKey(attributeId)) && (!availabilityMap.containsKey(attributeId)) && (!secondaryAttributesMap.containsKey(attributeId)))
/*      */             {
/*  609 */               HashMap h = new HashMap();
/*  610 */               h.put("displayname", rs.getString("DISPLAYNAME"));
/*  611 */               h.put("resourcetype", resType);
/*  612 */               h.put("attributeid", attributeId);
/*  613 */               if (attributeIdBuf.length() != 0)
/*  614 */                 attributeIdBuf.append(",");
/*  615 */               attributeIdBuf.append(attributeId);
/*      */               
/*  617 */               int attributeType = rs.getInt("ATTRIBUTESTYPE");
/*      */               
/*  619 */               h.put("attributedisplayname", rs.getString("ATTRIBUTEDISPLAYNAME"));
/*  620 */               h.put("attributestype", attributeType + "");
/*  621 */               h.put("attributesunits", rs.getString("UNITS"));
/*  622 */               h.put("thresholdid", rs.getString("ID"));
/*  623 */               h.put("thresholdname", rs.getString("THRESHOLDNAME"));
/*  624 */               h.put("criticalthresholdcondition", rs.getString("CRITICALTHRESHOLDCONDITION"));
/*  625 */               h.put("criticalthresholdvalue", rs.getString("CRITICALTHRESHOLDVALUE"));
/*  626 */               h.put("criticalthresholdmessage", rs.getString("CRITICALTHRESHOLDMESSAGE"));
/*  627 */               h.put("attributedisplaytype", rs.getString("ATTRIBUTEDISPLAYTYPE"));
/*      */               
/*  629 */               h.put("criticalActions", new ArrayList());
/*  630 */               h.put("warningActions", new ArrayList());
/*  631 */               h.put("isSecondaryLevelAttribute", isSecondaryLevelAttr.toString());
/*  632 */               h.put("secondaryResourceType", secondaryResourceType);
/*  633 */               h.put("clearActions", new ArrayList());
/*  634 */               if (!"monitorType".equalsIgnoreCase(viewBy))
/*      */               {
/*  636 */                 h.put("anomalyid", rs.getString("ANOMALYID"));
/*  637 */                 h.put("anomalyname", rs.getString("ANOMALYNAME"));
/*      */               }
/*  639 */               if ((rs.getString("ACTIONID") != null) && (rs.getString("ACTIONNAME") != null) && (rs.getString("TYPE") != null) && (rs.getString("SEVERITY") != null))
/*      */               {
/*  641 */                 ArrayList actions = new ArrayList();
/*  642 */                 HashMap temp = new HashMap();
/*  643 */                 temp.put("actionid", rs.getString("ACTIONID"));
/*  644 */                 temp.put("isBusinessHourAssociatedAction", DBUtil.isBusinessHourAssociatedAction(rs.getString("ACTIONID")) + "");
/*  645 */                 temp.put("actionname", rs.getString("ACTIONNAME"));
/*  646 */                 temp.put("actiontype", rs.getString("TYPE"));
/*  647 */                 temp.put("severity", rs.getString("SEVERITY"));
/*  648 */                 actions.add(temp);
/*  649 */                 if (rs.getString("SEVERITY").equals("1"))
/*      */                 {
/*  651 */                   h.put("criticalActions", actions);
/*      */                 }
/*  653 */                 else if (rs.getString("SEVERITY").equals("4"))
/*      */                 {
/*  655 */                   h.put("warningActions", actions);
/*      */                 }
/*  657 */                 else if (rs.getString("SEVERITY").equals("5"))
/*      */                 {
/*  659 */                   h.put("clearActions", actions);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  666 */               if ((attributeType == 7) && (rs.getString("ID") != null))
/*      */               {
/*  668 */                 updateFloatThresholdValues(h, rs.getString("ID"));
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  674 */               if ((attributeType == 3) || (attributeType == 5)) {
/*  675 */                 patternMatcherThresholdList.add(rs.getString("ID"));
/*  676 */                 stringAttributesList.add(rs.getString("ATTRIBUTEID"));
/*      */               }
/*  678 */               if ((attributeType == 5) || (attributeType == 6)) {
/*  679 */                 configurationAttList.add(rs.getString("ATTRIBUTEID"));
/*      */               }
/*  681 */               if (!isSecondaryLevelAttr.booleanValue()) {
/*  682 */                 if (attributeType == 1) {
/*  683 */                   availabilityMap.put(rs.getString("ATTRIBUTEID"), h);
/*  684 */                 } else if (attributeType == 2) {
/*  685 */                   healthMap.put(rs.getString("ATTRIBUTEID"), h);
/*      */                 }
/*      */                 else {
/*  688 */                   m.put(rs.getString("ATTRIBUTEID"), h);
/*      */                 }
/*      */               }
/*      */               else {
/*  692 */                 secondaryAttributesMap.put(rs.getString("ATTRIBUTEID"), h);
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*  698 */             else if ((rs.getString("SEVERITY") != null) && (rs.getString("ACTIONID") != null))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  703 */               HashMap temp = new HashMap();
/*  704 */               temp.put("actionid", rs.getString("ACTIONID"));
/*  705 */               temp.put("isBusinessHourAssociatedAction", DBUtil.isBusinessHourAssociatedAction(rs.getString("ACTIONID")) + "");
/*  706 */               temp.put("actionname", rs.getString("ACTIONNAME"));
/*  707 */               temp.put("actiontype", rs.getString("TYPE"));
/*  708 */               temp.put("severity", rs.getString("SEVERITY"));
/*      */               
/*      */ 
/*  711 */               HashMap h = new HashMap();
/*  712 */               int attributeType = rs.getInt("ATTRIBUTESTYPE");
/*  713 */               if (!isSecondaryLevelAttr.booleanValue()) {
/*  714 */                 if (attributeType == 1) {
/*  715 */                   h = (HashMap)availabilityMap.get(rs.getString("ATTRIBUTEID"));
/*      */                 }
/*  717 */                 else if (attributeType == 2) {
/*  718 */                   h = (HashMap)healthMap.get(rs.getString("ATTRIBUTEID"));
/*      */                 }
/*      */                 else {
/*  721 */                   h = (HashMap)m.get(rs.getString("ATTRIBUTEID"));
/*      */                 }
/*      */               }
/*      */               else {
/*  725 */                 h = (HashMap)secondaryAttributesMap.get(rs.getString("ATTRIBUTEID"));
/*      */               }
/*      */               
/*  728 */               if (rs.getString("SEVERITY").equals("1"))
/*      */               {
/*  730 */                 ArrayList actions = (ArrayList)h.get("criticalActions");
/*  731 */                 actions.add(temp);
/*  732 */                 h.put("criticalActions", actions);
/*      */               }
/*  734 */               else if (rs.getString("SEVERITY").equals("4"))
/*      */               {
/*  736 */                 ArrayList actions = (ArrayList)h.get("warningActions");
/*  737 */                 actions.add(temp);
/*  738 */                 h.put("warningActions", actions);
/*      */               }
/*  740 */               else if (rs.getString("SEVERITY").equals("5"))
/*      */               {
/*  742 */                 ArrayList actions = (ArrayList)h.get("clearActions");
/*  743 */                 actions.add(temp);
/*  744 */                 h.put("clearActions", actions);
/*      */               }
/*  746 */               if ((attributeType == 5) || (attributeType == 6)) {
/*  747 */                 configurationAttList.add(rs.getString("ATTRIBUTEID"));
/*      */               }
/*  749 */               if (!isSecondaryLevelAttr.booleanValue()) {
/*  750 */                 if (attributeType == 1) {
/*  751 */                   availabilityMap.put(rs.getString("ATTRIBUTEID"), h);
/*  752 */                 } else if (attributeType == 2) {
/*  753 */                   healthMap.put(rs.getString("ATTRIBUTEID"), h);
/*      */                 } else {
/*  755 */                   m.put(rs.getString("ATTRIBUTEID"), h);
/*      */                 }
/*      */               }
/*      */               else {
/*  759 */                 secondaryAttributesMap.put(rs.getString("ATTRIBUTEID"), h);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  770 */         if (rs != null) rs.close();
/*      */       }
/*      */       catch (Exception e) {
/*  773 */         e.printStackTrace();
/*      */       }
/*      */       
/*  776 */       if (attributeIdBuf.toString().equals("")) {
/*      */         break label3583;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  766 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  770 */         if (rs != null) rs.close();
/*      */       }
/*      */       catch (Exception e) {
/*  773 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  778 */     HashMap attributeIdGroupNameMapper = getGroupName(attributeIdBuf.toString());
/*  779 */     request.setAttribute("groupnamemap", attributeIdGroupNameMapper);
/*      */     label3583:
/*  781 */     m.putAll(secondaryAttributesMap);
/*  782 */     m.putAll(healthMap);
/*  783 */     m.putAll(availabilityMap);
/*  784 */     if (!patternMatcherThresholdList.isEmpty()) {
/*  785 */       updateStringThresholdValues(m, patternMatcherThresholdList, stringAttributesList);
/*      */     }
/*  787 */     request.setAttribute("resourcedetails", m);
/*  788 */     request.setAttribute("viewBy", viewBy);
/*  789 */     ArrayList actions = new ArrayList();
/*  790 */     query = "select ID,NAME from AM_ACTIONPROFILE where AM_ACTIONPROFILE.NAME !='Marker' AND AM_ACTIONPROFILE.NAME !='Restart The Service' order by TYPE,NAME";
/*      */     try
/*      */     {
/*  793 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  797 */       rs = null;
/*  798 */       e.printStackTrace();
/*      */     }
/*  800 */     while (rs.next())
/*      */     {
/*  802 */       HashMap temp = new HashMap();
/*  803 */       temp.put("actionid", rs.getString("ID"));
/*  804 */       temp.put("actionname", rs.getString("NAME"));
/*  805 */       actions.add(temp);
/*      */     }
/*  807 */     boolean actionsAvailable = rs.first();
/*  808 */     rs.close();
/*  809 */     request.setAttribute("actions", actions);
/*  810 */     request.setAttribute("actionsAvailable", Boolean.valueOf(actionsAvailable));
/*  811 */     request.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/*      */     
/*  813 */     if (request.getParameter("include") == null)
/*      */     {
/*  815 */       return new ActionForward("/jsp/AssociatedThresholds.jsp?monitor=true&resourceid=" + resourceid);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  820 */     return new ActionForward("/jsp/MonitorThresholdConfiguration.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   private HashMap getGroupName(String attributeIds)
/*      */   {
/*  826 */     HashMap attributeGroupNameMapper = new HashMap();
/*  827 */     if (attributeIds.trim().equals(""))
/*      */     {
/*  829 */       return attributeGroupNameMapper;
/*      */     }
/*  831 */     ResultSet groupNameRS = null;
/*  832 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  833 */     String grpNameQuery = "select AM_ATTRIBUTES.ATTRIBUTEID, GROUPNAME from AM_ATTRIBUTES left outer join AM_CAM_DC_ATTRIBUTES ON AM_ATTRIBUTES.ATTRIBUTEID = AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID left outer join AM_CAM_DC_GROUPS ON AM_CAM_DC_ATTRIBUTES.GROUPID = AM_CAM_DC_GROUPS.GROUPID where AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeIds + ")";
/*  834 */     AMLog.debug("The grpNameQuery===>" + grpNameQuery);
/*      */     try
/*      */     {
/*  837 */       groupNameRS = AMConnectionPool.executeQueryStmt(grpNameQuery);
/*  838 */       while (groupNameRS.next())
/*      */       {
/*  840 */         String attributeId = groupNameRS.getString("ATTRIBUTEID");
/*  841 */         String groupName = groupNameRS.getString("GROUPNAME");
/*  842 */         attributeGroupNameMapper.put(attributeId, groupName);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  847 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  851 */       if (groupNameRS != null)
/*      */       {
/*      */         try
/*      */         {
/*  855 */           groupNameRS.close();
/*      */         } catch (Exception rsCloseException) {
/*  857 */           rsCloseException.printStackTrace();
/*      */         } }
/*  859 */       groupNameRS = null;
/*      */     }
/*      */     
/*  862 */     return attributeGroupNameMapper;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward getThresholdDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  870 */     String thresholdid = request.getParameter("thresholdid");
/*      */     
/*  872 */     String query = "select NAME , DESCRIPTION , CRITICALTHRESHOLDCONDITION , CRITICALTHRESHOLDVALUE , WARNINGTHRESHOLDCONDITION , WARNINGTHRESHOLDVALUE ,INFOTHRESHOLDCONDITION , INFOTHRESHOLDVALUE,CRITICAL_POLLSTOTRY,WARNING_POLLSTOTRY,CLEAR_POLLSTOTRY  from AM_THRESHOLDCONFIG where AM_THRESHOLDCONFIG.ID=" + thresholdid + "";
/*  873 */     request.setAttribute("thresholddetails", this.mo.getRows(query));
/*  874 */     if (request.getParameter("popupNewThreshold") == null)
/*      */     {
/*  876 */       return new ActionForward("/jsp/Iframe_ThresholdDetails.jsp");
/*      */     }
/*  878 */     if (request.getParameter("popupNewThreshold").equals("true"))
/*      */     {
/*  880 */       return new ActionForward("/adminAction.do?method=addThresholdFromPopup");
/*      */     }
/*  882 */     return new ActionForward("/jsp/Popup_NewThreshold.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getPatternDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  889 */     String thresholdid = request.getParameter("thresholdid");
/*  890 */     String query = "select NAME, DESCRIPTION, CRITICALTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE,WARNINGTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE,INFOTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE,IF (CRITICAL_POLLSTOTRY !=-1,CRITICAL_POLLSTOTRY,''),IF (WARNING_POLLSTOTRY !=-1,WARNING_POLLSTOTRY,''),IF (CLEAR_POLLSTOTRY !=-1,CLEAR_POLLSTOTRY,'')  from AM_THRESHOLDCONFIG left outer join AM_PATTERNMATCHERCONFIG on AM_THRESHOLDCONFIG.ID=AM_PATTERNMATCHERCONFIG.ID where AM_THRESHOLDCONFIG.ID=" + thresholdid + "";
/*  891 */     request.setAttribute("thresholddetails", this.mo.getRows(query));
/*  892 */     if (request.getParameter("popupNewThreshold") == null)
/*      */     {
/*  894 */       return new ActionForward("/jsp/Iframe_PatternDetails.jsp");
/*      */     }
/*  896 */     if (request.getParameter("popupNewThreshold").equals("true"))
/*      */     {
/*  898 */       return new ActionForward("/adminAction.do?method=addPatternFromPopup");
/*      */     }
/*  900 */     return new ActionForward("/jsp/Popup_NewPattern.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getActionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  910 */     String actionid = request.getParameter("actionid");
/*  911 */     String monitorType = request.getParameter("monitorType");
/*  912 */     String typequery = "select TYPE  from AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=" + actionid + "";
/*  913 */     ArrayList rows = this.mo.getRows(typequery);
/*  914 */     ArrayList row = (ArrayList)rows.get(0);
/*  915 */     String type = (String)row.get(0);
/*  916 */     boolean isContainerAction = (type.equals("850")) || (type.equals("851")) || (type.equals("852"));
/*  917 */     if (type.equals("1"))
/*      */     {
/*  919 */       String emailquery = "select AM_ACTIONPROFILE.NAME ,  FROMADDRESS , TOADDRESS , SUBJECT , MESSAGE  from AM_EMAILACTION , AM_ACTIONPROFILE where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*      */       
/*  921 */       request.setAttribute("actiondetails", this.mo.getRows(emailquery));
/*  922 */       return new ActionForward("/jsp/Popup_EmailActions.jsp");
/*      */     }
/*  924 */     if (type.equals("2"))
/*      */     {
/*  926 */       String smsquery = "select AM_ACTIONPROFILE.NAME , AM_SMSACTION.FROMADDRESS , AM_SMSACTION.TOADDRESS , AM_SMSACTION.MESSAGE from AM_SMSACTION , AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_SMSACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*      */       
/*  928 */       request.setAttribute("actiondetails", this.mo.getRows(smsquery));
/*  929 */       return new ActionForward("/jsp/Popup_SmsActions.jsp");
/*      */     }
/*  931 */     if (type.equals("5"))
/*      */     {
/*      */ 
/*  934 */       String smsquery = "select AM_ACTIONPROFILE.NAME , AM_SMSMODEMACTION.MOBILENO ,AM_SMSMODEMACTION.MESSAGE from AM_SMSMODEMACTION , AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_SMSMODEMACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*      */       
/*  936 */       request.setAttribute("actiondetails", this.mo.getRows(smsquery));
/*  937 */       return new ActionForward("/jsp/Popup_SmsModemActions.jsp");
/*      */     }
/*      */     
/*      */ 
/*  941 */     if (type.equals("3"))
/*      */     {
/*  943 */       String cmdquery = "select AM_ACTIONPROFILE.NAME , AM_SERVERCMDACTION.COMMAND , AM_SERVERCMDACTION.APPENDOUT , AM_SERVERCMDACTION.APPENDERROR , AM_SERVERCMDACTION.ABORTAFTER, AM_SERVERCMDACTION.DIRTOEXECUTE from AM_ACTIONPROFILE , AM_SERVERCMDACTION where AM_ACTIONPROFILE.ID=AM_SERVERCMDACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*      */       
/*  945 */       request.setAttribute("actiondetails", this.mo.getRows(cmdquery));
/*  946 */       return new ActionForward("/jsp/Popup_ProgramActions.jsp");
/*      */     }
/*  948 */     if (type.equals("6"))
/*      */     {
/*  950 */       AMActionForm amform = (AMActionForm)form;
/*  951 */       RetreiveFeedHandler RFH = new RetreiveFeedHandler();
/*      */       try
/*      */       {
/*  954 */         RssConnector rssConnector = RssConnector.getInstance();
/*  955 */         rssConnector.fetchRss(RFH);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  959 */         e.printStackTrace();
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/*  964 */         amform.setToAdd(makeArrayList(RFH.category));
/*  965 */         amform.setPresent(makeArrayList(RFH.priority));
/*  966 */         amform.setApplications(makeArrayList(RFH.technician));
/*      */       }
/*      */       
/*  969 */       String emailquery = "select AM_ACTIONPROFILE.NAME , AM_SDESK_TICKET_DETAILS.CATEGORY,AM_SDESK_TICKET_DETAILS.PRIORITY,AM_SDESK_TICKET_DETAILS.TECHNICIAN, SUBJECT , MESSAGE  from AM_EMAILACTION , AM_ACTIONPROFILE , AM_SDESK_TICKET_DETAILS where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID and AM_SDESK_TICKET_DETAILS.ID=AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*      */       
/*  971 */       request.setAttribute("actiondetails", this.mo.getRows(emailquery));
/*  972 */       return new ActionForward("/jsp/Popup_TicketActions.jsp");
/*      */     }
/*      */     
/*  975 */     if (type.equals("11"))
/*      */     {
/*  977 */       String cmdquery = "select NAME,case TYPE when '11' then 'v1' END, DESTINATIONHOST,DESTINATIONPORT from AM_V1_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V1_TRAPACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*  978 */       request.setAttribute("actiondetails", this.mo.getRows(cmdquery));
/*  979 */       return new ActionForward("/jsp/Popup_SendTrapActions.jsp");
/*      */     }
/*  981 */     if (type.equals("12"))
/*      */     {
/*  983 */       String cmdquery = "select NAME,case TYPE when '12' then 'v2C' END,DESTINATIONHOST,DESTINATIONPORT from AM_V2_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V2_TRAPACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*  984 */       request.setAttribute("actiondetails", this.mo.getRows(cmdquery));
/*  985 */       return new ActionForward("/jsp/Popup_SendTrapActions.jsp");
/*      */     }
/*  987 */     if (type.equals("13"))
/*      */     {
/*  989 */       String cmdquery = "select NAME,case TYPE when '13' then 'v3' END,DESTINATIONHOST,DESTINATIONPORT from AM_V3_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V3_TRAPACTION.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/*  990 */       request.setAttribute("actiondetails", this.mo.getRows(cmdquery));
/*  991 */       return new ActionForward("/jsp/Popup_SendTrapActions.jsp");
/*      */     }
/*      */     
/*      */ 
/*  995 */     if ((type.equals("850")) || (type.equals("851")) || (type.equals("852")) || (type.equals("101")) || (type.equals("102")) || (type.equals("103")) || (type.equals("201")) || (type.equals("202")) || (type.equals("203")))
/*      */     {
/*  997 */       AMLog.debug("Inside type = 101 | 102 | 103");
/*  998 */       AMActionForm amform = (AMActionForm)form;
/*  999 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1000 */       rows = new ArrayList();
/* 1001 */       ResultSet set = null;
/*      */       try {
/* 1003 */         String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 1004 */         if (EnterpriseUtil.isAdminServer()) {
/* 1005 */           query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */         }
/*      */         try
/*      */         {
/* 1009 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1010 */           AMLog.debug("VM Action : " + query);
/* 1011 */           while (set.next()) {
/* 1012 */             String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 1013 */             Properties dataProps = new Properties();
/* 1014 */             dataProps.setProperty("label", labelvalue);
/* 1015 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1016 */             rows.add(dataProps);
/*      */           }
/* 1018 */           AMConnectionPool.closeStatement(set);
/*      */           
/* 1020 */           ((AMActionForm)form).setMaillist(rows);
/* 1021 */           ((AMActionForm)form).setTdcount(2);
/* 1022 */           ((AMActionForm)form).setTddelay(30);
/*      */         } catch (Exception exp) {
/* 1024 */           AMLog.fatal("VM Action :  Exception ", exp);
/* 1025 */           exp.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 1029 */         ArrayList applications = new ArrayList();
/* 1030 */         if ((request.isUserInRole("OPERATOR")) && (!EnterpriseUtil.isIt360MSPEdition())) {
/* 1031 */           applications = AlarmUtil.getApplicationsForOwner(request.getRemoteUser(), request);
/*      */         }
/* 1033 */         else if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1035 */           applications = AlarmUtil.getConfiguredGroups(request);
/*      */         }
/*      */         else
/*      */         {
/* 1039 */           applications = AlarmUtil.getConfiguredGroups();
/*      */         }
/*      */         
/*      */ 
/* 1043 */         if (applications != null)
/*      */         {
/* 1045 */           request.setAttribute("applications", applications);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1050 */         String resourceType = isContainerAction ? "Docker Container" : "VirtualMachine";
/*      */         
/* 1052 */         query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='" + resourceType + "'  ORDER BY RESOURCENAME";
/*      */         
/* 1054 */         set = AMConnectionPool.executeQueryStmt(query);
/* 1055 */         rows = new ArrayList();
/* 1056 */         while (set.next())
/*      */         {
/* 1058 */           Properties dataProps = new Properties();
/* 1059 */           dataProps.setProperty("label", set.getString(2));
/*      */           
/* 1061 */           dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1062 */           rows.add(dataProps);
/*      */         }
/* 1064 */         set.close();
/* 1065 */         ((AMActionForm)form).setJrelist(rows);
/* 1066 */         request.setAttribute("jrelist", rows);
/*      */         
/*      */ 
/*      */ 
/* 1070 */         String hostresourceType = isContainerAction ? "Docker" : "VMWare ESX/ESXi";
/* 1071 */         query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='" + hostresourceType + "' ORDER BY DISPLAYNAME";
/*      */         
/* 1073 */         set = AMConnectionPool.executeQueryStmt(query);
/* 1074 */         rows = new ArrayList();
/* 1075 */         while (set.next())
/*      */         {
/* 1077 */           Properties dataProps = new Properties();
/* 1078 */           dataProps.setProperty("label", set.getString(2));
/* 1079 */           dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1080 */           rows.add(dataProps);
/*      */         }
/* 1082 */         set.close();
/* 1083 */         ((AMActionForm)form).setHostlist(rows);
/* 1084 */         request.setAttribute("hostlist", rows);
/*      */         
/* 1086 */         if (!isContainerAction) {
/* 1087 */           query = "select * from AM_ManagedObject where TYPE='HyperVVirtualMachine'";
/* 1088 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1089 */           rows = new ArrayList();
/* 1090 */           while (set.next())
/*      */           {
/* 1092 */             Properties dataProps = new Properties();
/* 1093 */             dataProps.setProperty("label", set.getString(4));
/* 1094 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1095 */             rows.add(dataProps);
/*      */           }
/*      */           
/* 1098 */           set.close();
/* 1099 */           ((AMActionForm)form).setHypervVMList(rows);
/* 1100 */           AMLog.debug("(*) setHypervVMList is." + rows);
/* 1101 */           request.setAttribute("hypervVMList", rows);
/* 1102 */           query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='Hyper-V-Server' ORDER BY DISPLAYNAME";
/*      */           
/* 1104 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1105 */           rows = new ArrayList();
/* 1106 */           while (set.next())
/*      */           {
/* 1108 */             Properties dataProps = new Properties();
/* 1109 */             dataProps.setProperty("label", set.getString(2));
/* 1110 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1111 */             rows.add(dataProps);
/*      */           }
/* 1113 */           set.close();
/* 1114 */           ((AMActionForm)form).setHypervhostlist(rows);
/* 1115 */           request.setAttribute("hypervhostlist", rows);
/*      */         }
/*      */         
/*      */ 
/* 1119 */         if (actionid != null)
/*      */         {
/* 1121 */           query = "select AM_ACTIONPROFILE.NAME,AM_JREACTIONS.DELAY,AM_JREACTIONS.COUNT,AM_JREACTIONS.EMAIL_ACTION_ID,AM_JREACTIONS.TYPE,AM_JREACTIONS.TARGET_RESID,AM_ACTIONPROFILE.TYPE from AM_JREACTIONS,AM_ACTIONPROFILE where AM_JREACTIONS.ID=AM_ACTIONPROFILE.ID  and AM_JREACTIONS.ID=" + actionid;
/* 1122 */           rows = this.mo.getRows(query);
/* 1123 */           if (rows != null)
/*      */           {
/* 1125 */             row = (ArrayList)rows.get(0);
/* 1126 */             ((AMActionForm)form).setTdcount(Integer.parseInt((String)row.get(2)));
/* 1127 */             ((AMActionForm)form).setTddelay(Integer.parseInt((String)row.get(1)));
/* 1128 */             ((AMActionForm)form).setDisplayname((String)row.get(0));
/* 1129 */             ((AMActionForm)form).setLogConfig((String)row.get(4));
/* 1130 */             ((AMActionForm)form).setSendmail((String)row.get(3));
/* 1131 */             ((AMActionForm)form).setSelectedMG((String)row.get(5));
/* 1132 */             ((AMActionForm)form).setSelectedhost((String)row.get(5));
/* 1133 */             ((AMActionForm)form).setSelectedjre((String)row.get(5));
/* 1134 */             ((AMActionForm)form).setId(Integer.parseInt(actionid));
/* 1135 */             ((AMActionForm)form).setSelectedhypervhost((String)row.get(5));
/* 1136 */             ((AMActionForm)form).setSelectedHyperVVM((String)row.get(5));
/* 1137 */             type = (String)row.get(6);
/*      */             
/* 1139 */             Integer typeInt = Integer.valueOf(type);
/* 1140 */             if ((850 == typeInt.intValue()) || (851 == typeInt.intValue()) || (852 == typeInt.intValue())) {
/* 1141 */               isContainerAction = true;
/* 1142 */               ((AMActionForm)form).setHostType("850");
/*      */             }
/* 1144 */             else if (typeInt.intValue() - 100 > 100)
/*      */             {
/* 1146 */               ((AMActionForm)form).setHostType("100");
/*      */             }
/*      */             
/*      */ 
/* 1150 */             if ((type != null) && ((type.equals("102")) || (type.equals("202")) || (type.equals("851"))))
/*      */             {
/* 1152 */               type = "StopVM";
/* 1153 */             } else if ((type != null) && ((type.equals("103")) || (type.equals("203")) || (type.equals("852"))))
/*      */             {
/* 1155 */               type = "RestartVM";
/*      */             }
/*      */             else
/*      */             {
/* 1159 */               type = "StartVM";
/*      */             }
/*      */             
/* 1162 */             ((AMActionForm)form).setJtaskMethod(type);
/*      */           }
/*      */         }
/*      */         
/* 1166 */         request.setAttribute("isContainerAction", Boolean.valueOf(isContainerAction));
/*      */       }
/*      */       catch (Exception e) {
/* 1169 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 1172 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       
/* 1175 */       return new ActionForward("/jsp/Popup_VMActions.jsp");
/*      */     }
/*      */     
/*      */ 
/* 1179 */     if ((type.equals("7")) || (type.equals("8")) || (type.equals("9")) || (type.equals("14")) || (type.equals("15")) || (type.equals("16")))
/*      */     {
/* 1181 */       AMActionForm amform = (AMActionForm)form;
/* 1182 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1183 */       rows = new ArrayList();
/* 1184 */       ResultSet set = null;
/*      */       try {
/* 1186 */         String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 1187 */         if (EnterpriseUtil.isAdminServer()) {
/* 1188 */           query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */         }
/*      */         try
/*      */         {
/* 1192 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1193 */           AMLog.debug("Java Action : " + query);
/* 1194 */           while (set.next()) {
/* 1195 */             String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 1196 */             Properties dataProps = new Properties();
/* 1197 */             dataProps.setProperty("label", labelvalue);
/* 1198 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1199 */             rows.add(dataProps);
/*      */           }
/* 1201 */           AMConnectionPool.closeStatement(set);
/*      */           
/* 1203 */           ((AMActionForm)form).setMaillist(rows);
/* 1204 */           ((AMActionForm)form).setTdcount(2);
/* 1205 */           ((AMActionForm)form).setTddelay(30);
/*      */         } catch (Exception exp) {
/* 1207 */           AMLog.fatal("Java Thread Dump Action :  Exception ", exp);
/* 1208 */           exp.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 1212 */         ArrayList applications = new ArrayList();
/* 1213 */         if ((request.isUserInRole("OPERATOR")) && (!EnterpriseUtil.isIt360MSPEdition())) {
/* 1214 */           applications = AlarmUtil.getApplicationsForOwner(request.getRemoteUser(), request);
/*      */         }
/* 1216 */         else if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1218 */           applications = AlarmUtil.getConfiguredGroups(request);
/*      */         }
/*      */         else
/*      */         {
/* 1222 */           applications = AlarmUtil.getConfiguredGroups();
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
/*      */ 
/* 1236 */         if (applications != null)
/*      */         {
/* 1238 */           request.setAttribute("applications", applications);
/*      */         }
/* 1240 */         if (monitorType.equalsIgnoreCase("jre"))
/*      */         {
/* 1242 */           query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE='JDK1.5' ORDER BY RESOURCENAME";
/* 1243 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1244 */           rows = new ArrayList();
/* 1245 */           while (set.next())
/*      */           {
/* 1247 */             Properties dataProps = new Properties();
/* 1248 */             dataProps.setProperty("label", set.getString(2));
/* 1249 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1250 */             rows.add(dataProps);
/*      */           }
/* 1252 */           set.close();
/* 1253 */           ((AMActionForm)form).setJrelist(rows);
/* 1254 */           request.setAttribute("jrelist", rows);
/*      */           
/*      */ 
/*      */ 
/* 1258 */           query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE IN " + com.adventnet.appmanager.util.Constants.serverTypes + " ORDER BY RESOURCENAME";
/* 1259 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1260 */           rows = new ArrayList();
/* 1261 */           while (set.next())
/*      */           {
/* 1263 */             Properties dataProps = new Properties();
/* 1264 */             dataProps.setProperty("label", set.getString(2));
/* 1265 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1266 */             rows.add(dataProps);
/*      */           }
/* 1268 */           set.close();
/* 1269 */           ((AMActionForm)form).setHostlist(rows);
/* 1270 */           request.setAttribute("hostlist", rows);
/*      */         }
/*      */         else {
/* 1273 */           query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE='EC2Instance' ORDER BY RESOURCENAME";
/* 1274 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1275 */           ResultSet rst = null;
/* 1276 */           rows = new ArrayList();
/* 1277 */           String typeId = "";
/* 1278 */           ResultSet rsForTypeId = null;
/*      */           try
/*      */           {
/* 1281 */             String typeIdQuery = "select TYPEID from AM_MONITOR_TYPES where TYPENAME ='EC2Instance'";
/* 1282 */             rsForTypeId = AMConnectionPool.executeQueryStmt(typeIdQuery);
/* 1283 */             if (rsForTypeId.next())
/*      */             {
/* 1285 */               typeId = rsForTypeId.getString("TYPEID");
/*      */             }
/*      */           }
/*      */           catch (SQLException e)
/*      */           {
/* 1290 */             e.printStackTrace();
/*      */           } finally {
/* 1292 */             rsForTypeId.close();
/*      */           }
/*      */           try {
/* 1295 */             while (set.next())
/*      */             {
/*      */ 
/*      */ 
/* 1299 */               String queryName = "select CONFVALUE from AM_CONFIGURATION_INFO  where RESOURCEID='" + set.getInt(1) + "'  and  ATTRIBUTEID = 9852 and LATEST=1";
/* 1300 */               rst = AMConnectionPool.executeQueryStmt(queryName);
/* 1301 */               String label = null;
/* 1302 */               Properties dataProps = new Properties();
/* 1303 */               if (rst != null) {
/* 1304 */                 while (rst.next()) {
/* 1305 */                   label = rst.getString(1) + "(" + set.getString(2) + ")";
/* 1306 */                   dataProps.setProperty("label", label);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1312 */               label = set.getString(2) + "(" + set.getString(2) + ")";
/* 1313 */               dataProps.setProperty("label", set.getString(2));
/*      */               
/*      */ 
/* 1316 */               dataProps.setProperty("value", set.getString(1));
/* 1317 */               rows.add(dataProps);
/*      */             }
/*      */           } catch (Exception err) {
/* 1320 */             err.printStackTrace();
/*      */           } finally {
/* 1322 */             AMConnectionPool.closeStatement(rst);
/*      */           }
/*      */           
/* 1325 */           ((AMActionForm)form).setEc2Instance(rows);
/* 1326 */           request.setAttribute("ec2Instance", rows);
/*      */         }
/*      */         
/* 1329 */         if (actionid != null)
/*      */         {
/* 1331 */           query = "select AM_ACTIONPROFILE.NAME,AM_JREACTIONS.DELAY,AM_JREACTIONS.COUNT,AM_JREACTIONS.EMAIL_ACTION_ID,AM_JREACTIONS.TYPE,AM_JREACTIONS.TARGET_RESID,AM_ACTIONPROFILE.TYPE from AM_JREACTIONS,AM_ACTIONPROFILE where AM_JREACTIONS.ID=AM_ACTIONPROFILE.ID  and AM_JREACTIONS.ID=" + actionid;
/* 1332 */           rows = this.mo.getRows(query);
/* 1333 */           if (rows != null)
/*      */           {
/* 1335 */             row = (ArrayList)rows.get(0);
/* 1336 */             ((AMActionForm)form).setTdcount(Integer.parseInt((String)row.get(2)));
/* 1337 */             ((AMActionForm)form).setTddelay(Integer.parseInt((String)row.get(1)));
/* 1338 */             ((AMActionForm)form).setDisplayname((String)row.get(0));
/* 1339 */             ((AMActionForm)form).setLogConfig((String)row.get(4));
/* 1340 */             ((AMActionForm)form).setSendmail((String)row.get(3));
/* 1341 */             ((AMActionForm)form).setSelectedMG((String)row.get(5));
/* 1342 */             ((AMActionForm)form).setSelectedhost((String)row.get(5));
/* 1343 */             ((AMActionForm)form).setSelectedjre((String)row.get(5));
/* 1344 */             ((AMActionForm)form).setId(Integer.parseInt(actionid));
/*      */             
/* 1346 */             type = (String)row.get(6);
/* 1347 */             if ((type != null) && (type.equals("8"))) {
/* 1348 */               type = "HeapDump";
/* 1349 */             } else if ((type != null) && (type.equals("9"))) {
/* 1350 */               type = "PerformGC";
/* 1351 */             } else if ((type != null) && (type.equals("14"))) {
/* 1352 */               type = "Start";
/* 1353 */             } else if ((type != null) && (type.equals("15"))) {
/* 1354 */               type = "Stop";
/*      */             }
/* 1356 */             else if ((type != null) && (type.equals("16"))) {
/* 1357 */               type = "Restart";
/*      */             }
/*      */             else {
/* 1360 */               type = "ThreadDump";
/*      */             }
/* 1362 */             ((AMActionForm)form).setJtaskMethod(type);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1369 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 1372 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       
/* 1375 */       return new ActionForward("/jsp/Popup_TnreadDumpActions.jsp?monitorType=" + monitorType);
/*      */     }
/* 1377 */     if (type.equals("10"))
/*      */     {
/*      */ 
/* 1380 */       AMActionForm amform = (AMActionForm)form;
/* 1381 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1382 */       rows = new ArrayList();
/* 1383 */       ResultSet set = null;
/*      */       
/* 1385 */       String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/*      */       
/*      */       try
/*      */       {
/* 1389 */         set = AMConnectionPool.executeQueryStmt(query);
/* 1390 */         AMLog.debug("Execute query Action : " + query);
/* 1391 */         while (set.next()) {
/* 1392 */           String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 1393 */           Properties dataProps = new Properties();
/* 1394 */           dataProps.setProperty("label", labelvalue);
/* 1395 */           dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 1396 */           rows.add(dataProps);
/*      */         }
/*      */         
/*      */ 
/* 1400 */         ((AMActionForm)form).setMaillist(rows);
/*      */       } catch (Exception exp) {
/* 1402 */         AMLog.fatal("Execute query :  Exception ", exp);
/* 1403 */         exp.printStackTrace();
/*      */       }
/*      */       finally {
/* 1406 */         AMConnectionPool.closeStatement(set);
/*      */       }
/* 1408 */       String queryexecquery = "select AM_ACTIONPROFILE.NAME ,AM_QUERYACTIONS.EMAIL_ACTION_ID , AM_QUERYACTIONS.QUERY  from AM_QUERYACTIONS , AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_QUERYACTIONS.ID and AM_ACTIONPROFILE.ID=" + actionid + "";
/* 1409 */       rows = new ArrayList();
/* 1410 */       rows = this.mo.getRows(queryexecquery);
/* 1411 */       if (rows != null)
/*      */       {
/* 1413 */         ArrayList row1 = (ArrayList)rows.get(0);
/* 1414 */         amform.setSendmail((String)row1.get(1));
/*      */       }
/* 1416 */       request.setAttribute("actiondetails", this.mo.getRows(queryexecquery));
/* 1417 */       return new ActionForward("/jsp/Popup_ExecQueryActions.jsp");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1422 */     String cmdquery = "select AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,AM_ManagedObject.DISPLAYNAME,AM_CAM_DC_GROUPS.GROUPNAME,AM_MBEANOPERATIONACTION.OPERATIONNAME,AM_MBEANOPERATIONACTION.ARGSCOUNT,action.NAME from AM_ACTIONPROFILE,AM_CAM_DC_GROUPS,AM_ManagedObject,AM_MBEANOPERATIONACTION left join AM_EMAILACTION on AM_MBEANOPERATIONACTION.EMAIL_ACTION_ID=AM_EMAILACTION.ID left join AM_ACTIONPROFILE action on action.ID=AM_EMAILACTION.ID where AM_ACTIONPROFILE.ID=AM_MBEANOPERATIONACTION.ID and AM_MBEANOPERATIONACTION.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_CAM_DC_GROUPS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ACTIONPROFILE.ID=" + actionid + "";
/* 1423 */     request.setAttribute("actiondetails", this.mo.getRows(cmdquery));
/* 1424 */     return new ActionForward("/jsp/Popup_MopActions.jsp");
/*      */   }
/*      */   
/*      */   private ArrayList makeArrayList(String values)
/*      */   {
/* 1429 */     ArrayList list = new ArrayList();
/* 1430 */     Properties p = new Properties();
/* 1431 */     p.setProperty("label", "Choose a Value");
/* 1432 */     p.setProperty("value", "Choose a Value");
/* 1433 */     list.add(p);
/* 1434 */     StringTokenizer sToken = new StringTokenizer(values, ",");
/* 1435 */     while (sToken.hasMoreTokens())
/*      */     {
/* 1437 */       Properties p1 = new Properties();
/* 1438 */       String next = sToken.nextToken();
/* 1439 */       p1.setProperty("label", next);
/* 1440 */       p1.setProperty("value", next);
/* 1441 */       list.add(p1);
/*      */     }
/* 1443 */     return list;
/*      */   }
/*      */   
/*      */   public ActionForward monitorGroupAlarmTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1448 */     ResultSet rs = null;
/*      */     try {
/* 1450 */       String haid = request.getParameter("haid");
/* 1451 */       HashMap resourceDetails = new LinkedHashMap();
/* 1452 */       Hashtable thresholdCondition = (Hashtable)request.getSession().getServletContext().getAttribute("thresholdconditions");
/* 1453 */       String query = "select AM_ATTRIBUTES.RESOURCETYPE as DISPLAYNAME,AM_ATTRIBUTES.RESOURCETYPE , AM_ATTRIBUTES.ATTRIBUTEID, AM_ATTRIBUTES.DISPLAYNAME as ATTRIBUTEDISPLAYNAME, COALESCE(AM_ATTRIBUTES.UNITS,'')as UNITS, AM_ATTRIBUTES.TYPE as ATTRIBUTESTYPE, AM_THRESHOLDCONFIG.ID, AM_THRESHOLDCONFIG.NAME as THRESHOLDNAME, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, AM_THRESHOLDCONFIG.CRITICALTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, AM_THRESHOLDCONFIG.WARNINGTHRESHOLDMESSAGE, AM_THRESHOLDCONFIG.INFOTHRESHOLDCONDITION, AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, AM_THRESHOLDCONFIG.INFOTHRESHOLDMESSAGE, AM_ACTIONPROFILE.ID as ACTIONID, AM_ACTIONPROFILE.NAME as ACTIONNAME, AM_ACTIONPROFILE.TYPE, AM_PredefinedAction.SEVERITY,AM_ATTRIBUTES.ATTRIBUTE as ATTRIBUTEDISPLAYTYPE  from AM_ATTRIBUTES   join AM_PredefinedThreshold  on AM_ATTRIBUTES.ATTRIBUTEID=AM_PredefinedThreshold.ATTRIBUTEID and AM_PredefinedThreshold.ID =" + haid + " left join AM_THRESHOLDCONFIG  on AM_THRESHOLDCONFIG.ID=AM_PredefinedThreshold.THRESHOLDCONFIGURATIONID left join  AM_PredefinedAction on AM_ATTRIBUTES.ATTRIBUTEID=AM_PredefinedAction.ATTRIBUTEID and AM_PredefinedAction.ID = " + haid + " left join AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_PredefinedAction.ACTIONID where AM_ATTRIBUTES.TYPE in ('0','1','2','3','7')  order by AM_ATTRIBUTES.TYPE ,AM_ATTRIBUTES.RESOURCETYPE ,AM_ATTRIBUTES.DISPLAYNAME";
/* 1454 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1455 */       while (rs.next()) {
/* 1456 */         String attributeid = rs.getString("ATTRIBUTEID");
/* 1457 */         if (resourceDetails.get(attributeid) != null) {
/* 1458 */           HashMap details = (HashMap)resourceDetails.get(attributeid);
/* 1459 */           String severity = rs.getString("SEVERITY");
/* 1460 */           String actionName = rs.getString("ACTIONNAME");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1465 */           if (severity.equals("1"))
/*      */           {
/* 1467 */             if (details.get("criticalActions") != null) {
/* 1468 */               String criticalActions = (String)details.get("criticalActions");
/* 1469 */               criticalActions = criticalActions + ", " + actionName;
/* 1470 */               details.put("criticalActions", criticalActions);
/*      */             } else {
/* 1472 */               details.put("criticalActions", actionName);
/*      */             }
/*      */           }
/* 1475 */           else if (severity.equals("4"))
/*      */           {
/* 1477 */             if (details.get("warningActions") != null) {
/* 1478 */               String criticalActions = (String)details.get("warningActions");
/* 1479 */               criticalActions = criticalActions + ", " + actionName;
/* 1480 */               details.put("warningActions", criticalActions);
/*      */             } else {
/* 1482 */               details.put("warningActions", actionName);
/*      */             }
/*      */           }
/* 1485 */           else if (severity.equals("5"))
/*      */           {
/* 1487 */             if (details.get("clearActions") != null) {
/* 1488 */               String criticalActions = (String)details.get("clearActions");
/* 1489 */               criticalActions = criticalActions + ", " + actionName;
/* 1490 */               details.put("clearActions", criticalActions);
/*      */             } else {
/* 1492 */               details.put("clearActions", actionName);
/*      */             }
/*      */           }
/*      */         } else {
/* 1496 */           HashMap<String, String> attrDetails = new HashMap();
/* 1497 */           attrDetails.put("attrDisplayname", rs.getString("ATTRIBUTEDISPLAYNAME"));
/* 1498 */           attrDetails.put("resourcetype", rs.getString("RESOURCETYPE"));
/* 1499 */           String thresholdName = rs.getString("THRESHOLDNAME");
/* 1500 */           StringBuilder criticalThreshold = new StringBuilder(FormatUtil.getString("am.webclient.threshold.criticalalert")).append(" ").append(thresholdCondition.get(rs.getString("CRITICALTHRESHOLDCONDITION"))).append(" ").append(rs.getString("CRITICALTHRESHOLDVALUE")).append(" ").append(rs.getString("UNITS"));
/* 1501 */           StringBuilder warningThreshold = new StringBuilder(FormatUtil.getString("am.webclient.threshold.warningalert")).append(" ").append(thresholdCondition.get(rs.getString("WARNINGTHRESHOLDCONDITION"))).append(" ").append(rs.getString("WARNINGTHRESHOLDVALUE")).append(" ").append(rs.getString("UNITS"));
/* 1502 */           StringBuilder clearThreshold = new StringBuilder(FormatUtil.getString("am.webclient.threshold.clearalert")).append(" ").append(thresholdCondition.get(rs.getString("INFOTHRESHOLDCONDITION"))).append(" ").append(rs.getString("INFOTHRESHOLDVALUE")).append(" ").append(rs.getString("UNITS"));
/* 1503 */           attrDetails.put("thresholdName", thresholdName);
/* 1504 */           attrDetails.put("criticalThreshold", criticalThreshold.toString());
/* 1505 */           attrDetails.put("warningThreshold", warningThreshold.toString());
/* 1506 */           attrDetails.put("clearThreshold", clearThreshold.toString());
/*      */           
/* 1508 */           if (rs.getString("SEVERITY") != null) {
/* 1509 */             String severity = rs.getString("SEVERITY");
/*      */             
/* 1511 */             String actionName = rs.getString("ACTIONNAME");
/*      */             
/*      */ 
/*      */ 
/* 1515 */             if (severity.equals("1"))
/*      */             {
/* 1517 */               attrDetails.put("criticalActions", actionName);
/*      */             }
/* 1519 */             else if (severity.equals("4"))
/*      */             {
/* 1521 */               attrDetails.put("warningActions", actionName);
/*      */             }
/* 1523 */             else if (severity.equals("5"))
/*      */             {
/* 1525 */               attrDetails.put("clearActions", actionName);
/*      */             }
/*      */           }
/* 1528 */           resourceDetails.put(attributeid, attrDetails);
/*      */         }
/*      */       }
/* 1531 */       request.setAttribute("resourceDetails", resourceDetails);
/* 1532 */       request.setAttribute("groupName", com.adventnet.appmanager.util.Constants.getMGName(haid));
/*      */     } catch (Exception ex) {
/* 1534 */       ex.printStackTrace();
/*      */     } finally {
/* 1536 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1538 */     return new ActionForward("/jsp/MonitorGroupAlarmTemplate.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   private ActionForward returnForward(ActionForward forward, HttpServletRequest request)
/*      */   {
/* 1544 */     if (request.getParameter("include") != null)
/*      */     {
/* 1546 */       AMLog.debug("redirecting the page");
/* 1547 */       return new ActionForward("/applications.do", true);
/*      */     }
/*      */     
/*      */ 
/* 1551 */     return forward;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getType(String id)
/*      */   {
/* 1557 */     String type = "";
/*      */     try {
/* 1559 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1560 */       String query = "select AM_ManagedObject.TYPE from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + id;
/* 1561 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1562 */       if (rs.next())
/*      */       {
/* 1564 */         type = rs.getString("TYPE");
/*      */       }
/* 1566 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/* 1569 */       e.printStackTrace();
/*      */     }
/* 1571 */     return type;
/*      */   }
/*      */   
/*      */   private String getHolisticApps(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1576 */     String query = null;
/*      */     
/* 1578 */     String bsgFilterCondn = "";
/* 1579 */     String bsgType = "0";
/* 1580 */     String custFilterCond = "";
/*      */     
/*      */ 
/* 1583 */     String orgName = request.getParameter("organization");
/* 1584 */     String siteName = request.getParameter("siteName");
/* 1585 */     String resourceType = request.getParameter("montype");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1677 */     ArrayList applications = new ArrayList();
/* 1678 */     String owner = request.getRemoteUser();
/* 1679 */     boolean privalegedUser = false;
/*      */     
/* 1681 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/* 1682 */       privalegedUser = true;
/*      */     }
/* 1684 */     if ((privalegedUser) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 1686 */       applications = AlarmUtil.getApplicationsForOwner(owner, request);
/*      */ 
/*      */ 
/*      */     }
/* 1690 */     else if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 1692 */       applications = AlarmUtil.getApplicationsForAdmin(request);
/*      */     }
/*      */     else
/*      */     {
/* 1696 */       applications = AlarmUtil.getApplicationsForAdmin();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1701 */     String first = null;
/* 1702 */     ArrayList rows = new ArrayList();
/*      */     
/*      */ 
/*      */ 
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
/* 1716 */       Properties dataProps = new Properties();
/* 1717 */       dataProps.setProperty("label", FormatUtil.getString("am.webclient.configurealert.selectmonitorgroup"));
/* 1718 */       dataProps.setProperty("value", "0");
/* 1719 */       rows.add(dataProps);
/*      */       
/* 1721 */       for (int i = 0; i < applications.size(); i++)
/*      */       {
/* 1723 */         ArrayList InsideRows = (ArrayList)applications.get(i);
/* 1724 */         if (i == 0)
/*      */         {
/* 1726 */           first = (String)InsideRows.get(1);
/*      */         }
/* 1728 */         Properties dataProps1 = new Properties();
/* 1729 */         String bsgName = (String)InsideRows.get(0);
/* 1730 */         String bsgIDStr = (String)InsideRows.get(1);
/* 1731 */         Long bsgID = Long.valueOf(Long.parseLong(bsgIDStr));
/* 1732 */         if ((EnterpriseUtil.isAdminServer()) && (bsgID.longValue() > com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */         {
/* 1734 */           bsgName = bsgName + "_" + CommDBUtil.getManagedServerNameWithPort(bsgIDStr);
/*      */         }
/* 1736 */         dataProps1.setProperty("label", bsgName);
/* 1737 */         dataProps1.setProperty("value", bsgIDStr);
/* 1738 */         rows.add(dataProps1);
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1743 */       exp.printStackTrace();
/*      */     }
/* 1745 */     ((AMActionForm)form).setApplications(rows);
/* 1746 */     return first;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ActionForward getFilteredMonitorTypes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1754 */     PrintWriter out = response.getWriter();
/* 1755 */     response.setContentType("text/xml");
/* 1756 */     out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
/* 1757 */     out.println("<response>");
/*      */     
/*      */ 
/* 1760 */     ResultSet set = null;
/* 1761 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/* 1763 */     String orgName = request.getParameter("organization");
/* 1764 */     String siteName = request.getParameter("siteName");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1769 */     Vector filteredID = new Vector();
/*      */     
/* 1771 */     if ((orgName != null) && (siteName != null) && (!orgName.equals("-")) && (!siteName.equals("-")))
/*      */     {
/* 1773 */       filteredID = EnterpriseUtil.filterSiteBasedResourceIds(Integer.parseInt(siteName), filteredID);
/*      */     }
/* 1775 */     else if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 1777 */       filteredID = EnterpriseUtil.filterCustBasedResourceIds(Integer.parseInt(orgName));
/*      */     }
/*      */     
/*      */ 
/* 1781 */     String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 1782 */     types = types.replace("'Node',", "");
/* 1783 */     types = types.replace("'snmp-node',", "");
/*      */     
/* 1785 */     String orderby = " order by TYPE";
/*      */     
/* 1787 */     String query = "select distinct(TYPE) from AM_ManagedObject where TYPE in " + types;
/* 1788 */     if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */     {
/* 1790 */       query = "select distinct(TYPE) from AM_ManagedObject where TYPE in " + com.adventnet.appmanager.util.Constants.sqlManagerresourceTypes;
/*      */     }
/*      */     
/* 1793 */     if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 1795 */       query = query + " AND " + EnterpriseUtil.getCondition("RESOURCEID", filteredID);
/*      */     }
/*      */     
/* 1798 */     query = query + orderby;
/*      */     
/* 1800 */     AMLog.debug(" GET [getFilteredMonitorType] query " + query);
/*      */     
/* 1802 */     String first = null;
/*      */     try
/*      */     {
/* 1805 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1806 */       ArrayList rows = new ArrayList();
/* 1807 */       String monType = "";
/* 1808 */       while (set.next())
/*      */       {
/* 1810 */         if (monType == "")
/*      */         {
/* 1812 */           monType = set.getString(1);
/*      */         }
/*      */         else
/*      */         {
/* 1816 */           monType = monType + "," + set.getString(1);
/*      */         }
/*      */       }
/* 1819 */       out.println("<monTypeLabel>" + monType + "</monTypeLabel>");
/* 1820 */       out.println("<monTypeValue>" + monType + "</monTypeValue>");
/* 1821 */       out.println("</response>");
/* 1822 */       out.flush();
/*      */     }
/*      */     catch (Exception exp) {
/* 1825 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1829 */       set.close();
/* 1830 */       set = null;
/*      */     }
/* 1832 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ActionForward getFilteredMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1841 */     PrintWriter out = response.getWriter();
/* 1842 */     response.setContentType("text/xml");
/* 1843 */     out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
/* 1844 */     out.println("<response>");
/* 1845 */     String selectSiteString = FormatUtil.getString("am.webclient.configurealert.selectmonitor");
/*      */     
/*      */ 
/* 1848 */     ResultSet set = null;
/* 1849 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/* 1851 */     String orgName = request.getParameter("organization");
/* 1852 */     String siteName = request.getParameter("siteName");
/* 1853 */     String resourceType = request.getParameter("montype");
/*      */     
/*      */ 
/*      */ 
/* 1857 */     Vector filteredID = new Vector();
/*      */     
/* 1859 */     if ((orgName != null) && (siteName != null) && (!orgName.equals("-")) && (!siteName.equals("-")))
/*      */     {
/* 1861 */       filteredID = EnterpriseUtil.filterSiteBasedResourceIds(Integer.parseInt(siteName), filteredID);
/*      */     }
/* 1863 */     else if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 1865 */       filteredID = EnterpriseUtil.filterCustBasedResourceIds(Integer.parseInt(orgName));
/*      */     }
/*      */     
/* 1868 */     String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 1869 */     types = types.replace("'Node',", "");
/* 1870 */     types = types.replace("'snmp-node',", "");
/*      */     
/* 1872 */     String allType = types;
/* 1873 */     String sqlTypes = com.adventnet.appmanager.util.Constants.sqlManagerresourceTypes;
/*      */     
/* 1875 */     if ((resourceType != null) && (!resourceType.equals("0")))
/*      */     {
/* 1877 */       allType = "('" + resourceType + "')";
/* 1878 */       sqlTypes = "('" + resourceType + "')";
/*      */     }
/*      */     
/*      */ 
/* 1882 */     String orderby = " order by  DISPLAYNAME";
/*      */     
/* 1884 */     String query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE in " + allType;
/* 1885 */     if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */     {
/* 1887 */       query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE in " + sqlTypes;
/*      */     }
/* 1889 */     if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 1891 */       query = query + " AND " + EnterpriseUtil.getCondition("RESOURCEID", filteredID);
/*      */     }
/* 1893 */     else if (ClientDBUtil.isPrivilegedUser(request)) {
/* 1894 */       query = query + " AND " + EnterpriseUtil.getCondition("RESOURCEID", ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/*      */     }
/*      */     
/* 1897 */     query = query + orderby;
/*      */     
/* 1899 */     AMLog.debug(" GET [getFilteredMonitors] query " + query);
/*      */     
/* 1901 */     String first = null;
/*      */     try
/*      */     {
/* 1904 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1905 */       ArrayList rows = new ArrayList();
/* 1906 */       String monLabel = "";
/* 1907 */       String monValue = "";
/* 1908 */       while (set.next())
/*      */       {
/*      */ 
/* 1911 */         if (monLabel == "")
/*      */         {
/* 1913 */           monLabel = FormatUtil.getString(set.getString(2));
/*      */         }
/*      */         else
/*      */         {
/* 1917 */           monLabel = monLabel + "," + FormatUtil.getString(set.getString(2));
/*      */         }
/*      */         
/*      */ 
/* 1921 */         if (monValue == "")
/*      */         {
/* 1923 */           monValue = set.getString(1);
/*      */         }
/*      */         else
/*      */         {
/* 1927 */           monValue = monValue + "," + set.getString(1);
/*      */         }
/*      */       }
/*      */       
/* 1931 */       monLabel = EnterpriseUtil.decodeString(monLabel);
/* 1932 */       monLabel = SDPIntegUtil.replaceXMLSpecialChars(monLabel);
/* 1933 */       out.println("<monLabel>" + monLabel + "</monLabel>");
/* 1934 */       out.println("<monValue>" + monValue + "</monValue>");
/* 1935 */       out.println("</response>");
/* 1936 */       out.flush();
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1940 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1944 */       set.close();
/* 1945 */       set = null;
/*      */     }
/* 1947 */     return null;
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public String escapeforXML(String string)
/*      */   {
/* 1968 */     if (string.contains("&")) {
/* 1969 */       string = string.replaceAll("&", "&amp;");
/*      */     }
/* 1971 */     if (string.contains("<")) {
/* 1972 */       string = string.replaceAll("<", "&lt;");
/*      */     }
/* 1974 */     if (string.contains(">")) {
/* 1975 */       string = string.replaceAll(">", "&gt;");
/*      */     }
/* 1977 */     if (string.contains("\"")) {
/* 1978 */       string = string.replaceAll("\"", "&quot;");
/*      */     }
/* 1980 */     if (string.contains("'")) {
/* 1981 */       string = string.replaceAll("'", "&apos;");
/*      */     }
/* 1983 */     return string;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String resType)
/*      */     throws Exception
/*      */   {
/* 1990 */     ResultSet set = null;
/* 1991 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1992 */     String orgName = request.getParameter("organization");
/* 1993 */     String siteName = request.getParameter("siteName");
/* 1994 */     String resourceType = resType;
/* 1995 */     if (resourceType == null)
/*      */     {
/* 1997 */       resourceType = request.getParameter("montype");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2002 */     Vector filteredID = new Vector();
/*      */     
/* 2004 */     if ((orgName != null) && (siteName != null) && (!orgName.equals("-")) && (!siteName.equals("-")))
/*      */     {
/* 2006 */       filteredID = EnterpriseUtil.filterSiteBasedResourceIds(Integer.parseInt(siteName), filteredID);
/*      */     }
/* 2008 */     else if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 2010 */       filteredID = EnterpriseUtil.filterCustBasedResourceIds(Integer.parseInt(orgName));
/*      */     }
/* 2012 */     boolean isUserResourceEnabled = false;
/* 2013 */     String loginUserid = null;
/* 2014 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/* 2015 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2016 */         isUserResourceEnabled = true;
/* 2017 */         loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*      */       } else {
/* 2019 */         filteredID = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2025 */     String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 2026 */     types = types.replace("'Node',", "");
/* 2027 */     types = types.replace("'snmp-node',", "");
/*      */     
/* 2029 */     String allType = types;
/* 2030 */     String sqlTypes = com.adventnet.appmanager.util.Constants.sqlManagerresourceTypes;
/*      */     
/* 2032 */     if ((resourceType != null) && (!resourceType.equals("0")))
/*      */     {
/* 2034 */       allType = "('" + resourceType + "')";
/* 2035 */       sqlTypes = "('" + resourceType + "')";
/*      */     }
/*      */     
/* 2038 */     String orderby = " order by  DISPLAYNAME";
/* 2039 */     String query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE in " + allType;
/* 2040 */     if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */     {
/* 2042 */       query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE in " + sqlTypes;
/*      */     }
/*      */     
/* 2045 */     if (isUserResourceEnabled)
/*      */     {
/* 2047 */       query = "select AM_ManagedObject.RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and TYPE in " + allType;
/* 2048 */     } else if (!filteredID.isEmpty())
/*      */     {
/* 2050 */       query = query + " AND " + EnterpriseUtil.getCondition("RESOURCEID", filteredID);
/*      */     }
/*      */     
/* 2053 */     query = query + orderby;
/*      */     
/* 2055 */     AMLog.debug(" GET [MONITORS] query " + query);
/*      */     
/* 2057 */     String first = null;
/*      */     try
/*      */     {
/* 2060 */       set = AMConnectionPool.executeQueryStmt(query);
/* 2061 */       ArrayList rows = new ArrayList();
/* 2062 */       Properties dataProps = new Properties();
/* 2063 */       dataProps.setProperty("label", FormatUtil.getString("am.webclient.configurealert.selectmonitor"));
/* 2064 */       dataProps.setProperty("value", "0");
/* 2065 */       rows.add(dataProps);
/* 2066 */       while (set.next())
/*      */       {
/* 2068 */         if (set.isFirst())
/*      */         {
/* 2070 */           first = String.valueOf(set.getInt(1));
/*      */         }
/* 2072 */         dataProps = new Properties();
/* 2073 */         dataProps.setProperty("label", EnterpriseUtil.decodeString(set.getString(2)));
/* 2074 */         dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 2075 */         rows.add(dataProps);
/*      */       }
/* 2077 */       ((AMActionForm)form).setAvailableResources(rows);
/*      */     }
/*      */     catch (Exception exp) {
/* 2080 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2084 */       set.close();
/* 2085 */       set = null;
/*      */     }
/* 2087 */     return first;
/*      */   }
/*      */   
/*      */   private String getResourceTypes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2092 */     ResultSet set = null;
/* 2093 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/* 2096 */     String orgName = request.getParameter("organization");
/* 2097 */     String siteName = request.getParameter("siteName");
/* 2098 */     String resourceType = request.getParameter("montype");
/*      */     
/*      */ 
/*      */ 
/* 2102 */     ArrayList monitorLevelResTypeList = com.adventnet.appmanager.util.Constants.getAllMonitorLevelResourceTypes();
/* 2103 */     monitorLevelResTypeList.remove("HAI");
/*      */     
/* 2105 */     String selectMonitor = FormatUtil.getString("am.webclient.configurealert.selectmonitor");
/*      */     
/* 2107 */     Vector filteredID = new Vector();
/*      */     
/* 2109 */     if ((orgName != null) && (siteName != null) && (!orgName.equals("-")) && (!siteName.equals("-")))
/*      */     {
/* 2111 */       filteredID = EnterpriseUtil.filterSiteBasedResourceIds(Integer.parseInt(siteName), filteredID);
/*      */     }
/* 2113 */     else if ((orgName != null) && (!orgName.equals("-")))
/*      */     {
/* 2115 */       filteredID = EnterpriseUtil.filterCustBasedResourceIds(Integer.parseInt(orgName));
/*      */     }
/* 2117 */     boolean isUserResourceEnabled = false;
/* 2118 */     String loginUserid = null;
/* 2119 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/* 2120 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2121 */         isUserResourceEnabled = true;
/* 2122 */         loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*      */       } else {
/* 2124 */         filteredID = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2129 */     String orderby = " order by DISPLAYNAME";
/* 2130 */     String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 2131 */     types = types.replace("'Node',", "");
/* 2132 */     types = types.replace("'snmp-node',", "");
/*      */     
/* 2134 */     String query = "select distinct(TYPE) from AM_ManagedObject where TYPE in " + types;
/* 2135 */     if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */     {
/* 2137 */       query = "select distinct(TYPE) from AM_ManagedObject where TYPE in " + com.adventnet.appmanager.util.Constants.sqlManagerresourceTypes;
/*      */     }
/*      */     
/* 2140 */     if (isUserResourceEnabled) {
/* 2141 */       query = "select distinct(TYPE) from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and TYPE in " + types;
/* 2142 */     } else if (!filteredID.isEmpty())
/*      */     {
/* 2144 */       query = query + " AND " + EnterpriseUtil.getCondition("RESOURCEID", filteredID);
/*      */     }
/* 2146 */     query = "select RESOURCETYPE,DISPLAYNAME from AM_ManagedResourceType where RESOURCETYPE in (" + query + ")";
/* 2147 */     String viewBy = request.getParameter("viewBy");
/* 2148 */     String defaultView = ((AMActionForm)form).getViewBy();
/* 2149 */     if (("monitorType".equalsIgnoreCase(viewBy)) || ("monitorGroups".equalsIgnoreCase(defaultView)))
/*      */     {
/* 2151 */       selectMonitor = FormatUtil.getString("am.webclient.selectmonitor.alert.text");
/* 2152 */       query = "select RESOURCETYPE,DISPLAYNAME from AM_ManagedResourceType where RESOURCETYPE in " + types;
/* 2153 */       if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */       {
/* 2155 */         query = "select RESOURCETYPE,DISPLAYNAME from AM_ManagedResourceType where RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.sqlManagerresourceTypes;
/*      */       }
/*      */     }
/*      */     
/* 2159 */     query = query + orderby;
/*      */     
/* 2161 */     String first = null;
/*      */     try
/*      */     {
/* 2164 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 2166 */       ArrayList rows = new ArrayList();
/* 2167 */       Properties dataProps = new Properties();
/* 2168 */       dataProps.setProperty("label", FormatUtil.getString("am.webclient.selectmonitor.alert.text"));
/* 2169 */       dataProps.setProperty("value", selectMonitor);
/* 2170 */       rows.add(dataProps);
/* 2171 */       while (set.next())
/*      */       {
/* 2173 */         if (set.isFirst())
/*      */         {
/* 2175 */           first = set.getString(1);
/*      */         }
/* 2177 */         dataProps = new Properties();
/* 2178 */         String devType = set.getString(1);
/* 2179 */         String devTypeLabel = set.getString(2);
/*      */         
/* 2181 */         if ((devType != null) && (devType.startsWith("OpManager-")))
/*      */         {
/* 2183 */           devTypeLabel = "OpManager-" + devTypeLabel;
/*      */         }
/* 2185 */         if ((devType != null) && (devType.startsWith("OpStor-")))
/*      */         {
/* 2187 */           devTypeLabel = "OpStor-" + devTypeLabel;
/*      */         }
/* 2189 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 2191 */           devTypeLabel = devType;
/*      */         }
/* 2193 */         if (devType.equals("directory")) {
/* 2194 */           devTypeLabel = "Directory Monitor";
/*      */         }
/* 2196 */         if (devType.equals("file")) {
/* 2197 */           devTypeLabel = "File Monitor";
/*      */         }
/* 2199 */         dataProps.setProperty("label", FormatUtil.getString(devTypeLabel));
/* 2200 */         dataProps.setProperty("value", devType);
/* 2201 */         rows.add(dataProps);
/*      */       }
/*      */       
/*      */ 
/* 2205 */       ((AMActionForm)form).setAvailableResourceTypes(rows);
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 2209 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2213 */       set.close();
/* 2214 */       set = null;
/*      */     }
/* 2216 */     return first;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void updatePatternValues(Properties pr, String thresholdid)
/*      */   {
/* 2223 */     String query = "select CRITICALTHRESHOLDVALUE, WARNINGTHRESHOLDVALUE, INFOTHRESHOLDVALUE from AM_PATTERNMATCHERCONFIG where ID=" + thresholdid;
/* 2224 */     ArrayList rows = this.mo.getRows(query);
/* 2225 */     ArrayList row = (ArrayList)rows.get(0);
/* 2226 */     pr.setProperty("criticalvalue", (String)row.get(0));
/* 2227 */     pr.setProperty("warningvalue", (String)row.get(1));
/* 2228 */     pr.setProperty("clearvalue", (String)row.get(2));
/*      */   }
/*      */   
/*      */   private void updateFloatThresholdValues(HashMap h, String thresholdId)
/*      */   {
/* 2233 */     String query = "select CRITICALTHRESHOLDVALUE, WARNINGTHRESHOLDVALUE, INFOTHRESHOLDVALUE from AM_FLOAT_THRESHOLDCONFIG where ID=" + thresholdId;
/* 2234 */     ArrayList rows = this.mo.getRows(query);
/* 2235 */     ArrayList row = (ArrayList)rows.get(0);
/* 2236 */     h.put("criticalthresholdvalue", (String)row.get(0));
/* 2237 */     h.put("warningthresholdvalue", (String)row.get(1));
/* 2238 */     h.put("clearthresholdvalue", (String)row.get(2));
/*      */   }
/*      */   
/* 2241 */   private void updateStringThresholdValues(Map resourceDetails, ArrayList patternMatcherThresholdIds, ArrayList<String> stringAttributeIds) { String condition = DBUtil.getCondition("ID", new Vector(patternMatcherThresholdIds));
/* 2242 */     String query = "select ID,CRITICALTHRESHOLDVALUE,WARNINGTHRESHOLDVALUE,INFOTHRESHOLDVALUE from AM_PATTERNMATCHERCONFIG where " + condition;
/* 2243 */     ResultSet rs = null;
/* 2244 */     HashMap thresholdValuesMap = new HashMap();
/*      */     try
/*      */     {
/* 2247 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2248 */       while (rs.next()) {
/* 2249 */         Properties valueProps = new Properties();
/* 2250 */         thresholdValuesMap.put(rs.getString("ID"), valueProps);
/* 2251 */         valueProps.setProperty("criticalthresholdvalue", rs.getString("CRITICALTHRESHOLDVALUE"));
/* 2252 */         valueProps.setProperty("warningthresholdvalue", rs.getString("WARNINGTHRESHOLDVALUE"));
/* 2253 */         valueProps.setProperty("clearthresholdvalue", rs.getString("INFOTHRESHOLDVALUE"));
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2257 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2260 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2262 */     for (String attributeId : stringAttributeIds) {
/*      */       try {
/* 2264 */         HashMap thresholdDetails = (HashMap)resourceDetails.get(attributeId);
/* 2265 */         String thresholdid = (String)thresholdDetails.get("thresholdid");
/* 2266 */         Properties valueProps = (Properties)thresholdValuesMap.get(thresholdid);
/* 2267 */         if ((thresholdDetails != null) && (valueProps != null)) {
/* 2268 */           thresholdDetails.put("criticalthresholdvalue", valueProps.getProperty("criticalthresholdvalue"));
/* 2269 */           thresholdDetails.put("warningthresholdvalue", valueProps.getProperty("warningthresholdvalue"));
/* 2270 */           thresholdDetails.put("clearthresholdvalue", valueProps.getProperty("clearthresholdvalue"));
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 2274 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isEUMParent(String resid) {
/* 2280 */     return DBUtil.isEUMParent(resid);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowActionProfiles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */