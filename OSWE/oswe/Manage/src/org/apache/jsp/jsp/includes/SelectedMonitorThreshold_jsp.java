/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class SelectedMonitorThreshold_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   private static void closeResultSet(ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     catch (Exception exc) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  46 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  57 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  63 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  67 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  74 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  77 */     JspWriter out = null;
/*  78 */     Object page = this;
/*  79 */     JspWriter _jspx_out = null;
/*  80 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  84 */       response.setContentType("text/html;charset=UTF-8");
/*  85 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  87 */       _jspx_page_context = pageContext;
/*  88 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  89 */       ServletConfig config = pageContext.getServletConfig();
/*  90 */       session = pageContext.getSession();
/*  91 */       out = pageContext.getOut();
/*  92 */       _jspx_out = out;
/*     */       
/*  94 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  95 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  97 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n");
/*     */       
/*  99 */       ArrayList commonThresholdMonitorsList = new ArrayList();
/* 100 */       String commonThresholdID = request.getParameter("thresholdid");
/* 101 */       if (commonThresholdID.equals("noids"))
/*     */       {
/* 103 */         commonThresholdID = "-99";
/*     */       }
/* 105 */       String monitorid = request.getParameter("resourceid");
/* 106 */       String attributeID = request.getParameter("attributeid");
/* 107 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 108 */       String resourceType = request.getParameter("resourcetype");
/* 109 */       String anomalyid = request.getParameter("anomalyid");
/*     */       
/* 111 */       String resourceDispName = "";
/* 112 */       boolean isSelectedMonAvailable = false;
/* 113 */       ArrayList similarMonitorsList = new ArrayList();
/*     */       
/* 115 */       String condition = "";
/* 116 */       String condtion_temp = "";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 121 */       String eumCondition = " ";
/* 122 */       if (Constants.resourceTypesEUM.contains(resourceType))
/*     */       {
/* 124 */         boolean addParents = (AMAttributesCache.getAvailabilityId(resourceType).equals(attributeID)) || (AMAttributesCache.getHealthId(resourceType).equals(attributeID));
/* 125 */         ArrayList<String> resIdsList = Constants.getEumMonitors(resourceType, addParents);
/* 126 */         eumCondition = !resIdsList.isEmpty() ? " AND " + EnterpriseUtil.getCondition("t1.RESOURCEID", new Vector(resIdsList)) : "";
/*     */       }
/*     */       
/* 129 */       List<Integer> resIdsList = new ArrayList();
/* 130 */       resIdsList = com.adventnet.appmanager.util.MonitorFilterUtil.filterResourceIdsForAnAttribute(resourceType, Integer.parseInt(attributeID), Integer.parseInt(monitorid), request);
/* 131 */       if (resIdsList != null)
/*     */       {
/* 133 */         if (resIdsList.size() > 0)
/*     */         {
/* 135 */           condition = " AND t1.RESOURCEID in (" + resIdsList.toString().substring(1, resIdsList.toString().length() - 1) + ")  ";
/*     */         }
/*     */         else
/*     */         {
/* 139 */           condition = " AND t1.RESOURCEID in (-1) ";
/*     */         }
/*     */       }
/*     */       
/* 143 */       String similarMonitorTypeQuery = "select t1.RESOURCEID,t1.TYPE,mrs.DISPLAYNAME from AM_ManagedObject t1 join AM_ManagedObject t2 on t1.TYPE = t2.TYPE LEFT OUTER JOIN AM_ManagedResourceType mrs on t1.TYPE=mrs.RESOURCETYPE  where t2.RESOURCEID= " + monitorid + condition + eumCondition;
/*     */       
/* 145 */       int resourceid = 0;
/*     */       try
/*     */       {
/* 148 */         resourceid = Integer.parseInt(monitorid);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 153 */       String groupTemplate = request.getParameter("groupTemplate");
/* 154 */       if ("true".equals(groupTemplate)) {
/* 155 */         String haid = request.getParameter("haid");
/* 156 */         Vector<String> haids = new Vector();
/* 157 */         haids.add(haid);
/* 158 */         ParentChildRelationalUtil.getAllChildMapper(haids, haid, true, true);
/* 159 */         similarMonitorTypeQuery = "select t1.RESOURCEID,t1.TYPE,AM_ManagedResourceType.DISPLAYNAME from AM_PARENTCHILDMAPPER, AM_ManagedObject as t1,AM_ManagedResourceType where AM_PARENTCHILDMAPPER.CHILDID=t1.RESOURCEID and AM_ManagedResourceType.RESOURCETYPE= t1.TYPE and t1.TYPE = '" + resourceType + "' and " + EnterpriseUtil.getCondition("t1.RESOURCEID", haids) + " " + condition + eumCondition;
/*     */       }
/* 161 */       else if ((resourceid <= 0) && (resourceType != null) && (!resourceType.trim().equals("")))
/*     */       {
/* 163 */         similarMonitorTypeQuery = "select t1.RESOURCEID,t1.TYPE,mrs.DISPLAYNAME from AM_ManagedObject t1 LEFT OUTER JOIN AM_ManagedResourceType mrs on t1.TYPE=mrs.RESOURCETYPE  where t1.TYPE= '" + resourceType + "' " + condition + eumCondition;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 169 */       if (resourceType.equals("SCRIPT_ROW"))
/*     */       {
/*     */         try
/*     */         {
/* 173 */           String temp_type = "";
/* 174 */           String temp_resid = "";
/* 175 */           String temp_name = "";
/* 176 */           String tablename = "";
/*     */           try
/*     */           {
/* 179 */             ResultSet rs = AMConnectionPool.executeQueryStmt("select parentid from AM_PARENTCHILDMAPPER WHERE CHILDID=" + monitorid);
/* 180 */             if (rs.next())
/*     */             {
/* 182 */               ResultSet rs1 = AMConnectionPool.executeQueryStmt("select TYPE,RESOURCEID from AM_ManagedObject where resourceid=" + rs.getString(1));
/* 183 */               if (rs1.next())
/*     */               {
/* 185 */                 temp_type = rs1.getString(1);
/* 186 */                 temp_resid = rs1.getString(2);
/*     */               }
/* 188 */               closeResultSet(rs1);
/*     */             }
/* 190 */             closeResultSet(rs);
/*     */             
/* 192 */             ResultSet rs2 = AMConnectionPool.executeQueryStmt("select resourcename from AM_ManagedObject where resourceid=" + monitorid);
/* 193 */             if (rs2.next())
/*     */             {
/* 195 */               temp_name = rs2.getString(1);
/*     */             }
/* 197 */             closeResultSet(rs2);
/* 198 */             if ((temp_name != null) && (!temp_name.equals("")))
/*     */             {
/* 200 */               StringTokenizer sttok = new StringTokenizer(temp_name, "_");
/* 201 */               if (sttok.hasMoreTokens())
/*     */               {
/* 203 */                 tablename = sttok.nextToken();
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception exc)
/*     */           {
/* 209 */             exc.printStackTrace();
/*     */           }
/* 211 */           if ((!temp_type.equals("Script Monitor")) && (!temp_type.equals("QueryMonitor")))
/*     */           {
/*     */ 
/* 214 */             String q6 = "select resourceid from AM_ManagedObject where type='" + temp_type + "'";
/* 215 */             ResultSet rs5 = AMConnectionPool.executeQueryStmt(q6);
/* 216 */             ArrayList resids = new ArrayList();
/* 217 */             while (rs5.next())
/*     */             {
/* 219 */               resids.add(rs5.getString(1));
/*     */             }
/* 221 */             closeResultSet(rs5);
/*     */             
/* 223 */             String q7 = "select typeid from AM_MONITOR_TYPES WHERE TYPENAME LIKE '" + temp_type + "'";
/* 224 */             ResultSet rs6 = AMConnectionPool.executeQueryStmt(q7);
/* 225 */             String tname = "";
/* 226 */             if (rs6.next())
/*     */             {
/* 228 */               temp_resid = rs6.getString(1);
/* 229 */               String q1 = "select tablename from AM_SCRIPT_TABLES WHERE SCRIPTID=" + temp_resid + " and tablename like '" + tablename + "%'";
/* 230 */               ResultSet rs7 = AMConnectionPool.executeQueryStmt(q1);
/* 231 */               if (rs7.next())
/*     */               {
/* 233 */                 tname = rs7.getString(1);
/*     */               }
/* 235 */               closeResultSet(rs7);
/*     */             }
/* 237 */             closeResultSet(rs6);
/*     */             
/* 239 */             for (int i = 0; i < resids.size(); i++)
/*     */             {
/* 241 */               String q2 = "select * from AM_ManagedObject where type like 'SCRIPT_ROW' and resourcename like '" + tname + "%' and resourcename like '%" + resids.get(i) + "'";
/* 242 */               ResultSet rs4 = AMConnectionPool.executeQueryStmt(q2);
/* 243 */               while (rs4.next())
/*     */               {
/* 245 */                 resourceDispName = rs4.getString("DISPLAYNAME");
/* 246 */                 String monitorResourceId = rs4.getString("RESOURCEID");
/* 247 */                 if (!monitorResourceId.equals(monitorid))
/* 248 */                   similarMonitorsList.add(monitorResourceId);
/*     */               }
/* 250 */               closeResultSet(rs4);
/*     */             }
/*     */             
/*     */           }
/*     */           else
/*     */           {
/* 256 */             String q1 = "select tablename from AM_SCRIPT_TABLES WHERE SCRIPTID=" + temp_resid + " and tablename like '" + tablename + "%'";
/* 257 */             ResultSet rs3 = AMConnectionPool.executeQueryStmt(q1);
/* 258 */             if (rs3.next())
/*     */             {
/* 260 */               String tname = rs3.getString(1);
/* 261 */               String q2 = "select * from AM_ManagedObject where type like 'SCRIPT_ROW' and resourcename like '" + tname + "%' and resourcename like '%" + temp_resid + "'";
/* 262 */               ResultSet rs4 = AMConnectionPool.executeQueryStmt(q2);
/* 263 */               while (rs4.next())
/*     */               {
/* 265 */                 resourceDispName = rs4.getString("DISPLAYNAME");
/* 266 */                 String monitorResourceId = rs4.getString("RESOURCEID");
/* 267 */                 if (!monitorResourceId.equals(monitorid))
/* 268 */                   similarMonitorsList.add(monitorResourceId);
/*     */               }
/* 270 */               closeResultSet(rs4);
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */         catch (Exception exc1)
/*     */         {
/* 277 */           exc1.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 284 */         ResultSet similarMonitorSet = AMConnectionPool.executeQueryStmt(similarMonitorTypeQuery);
/* 285 */         while (similarMonitorSet.next())
/*     */         {
/*     */ 
/* 288 */           String monitorResourceId = String.valueOf(similarMonitorSet.getInt("RESOURCEID"));
/* 289 */           if (monitorResourceId.equals(monitorid))
/*     */           {
/* 291 */             resourceDispName = similarMonitorSet.getString("DISPLAYNAME");
/* 292 */             if (resourceDispName == null)
/*     */             {
/* 294 */               resourceDispName = similarMonitorSet.getString("TYPE");
/*     */             }
/*     */             else
/*     */             {
/* 298 */               resourceDispName = EnterpriseUtil.decodeString(resourceDispName);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 303 */             similarMonitorsList.add(monitorResourceId);
/*     */           }
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 309 */           if (similarMonitorSet != null) {
/* 310 */             similarMonitorSet.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 318 */       if (((similarMonitorsList != null) && (!similarMonitorsList.isEmpty()) && (commonThresholdID != null) && (!commonThresholdID.equals("-1")) && (!commonThresholdID.equals("Newfalse")) && (!commonThresholdID.equals("Reset")) && (!commonThresholdID.equals("Newtrue")) && (!commonThresholdID.equals("nothingselected"))) || (anomalyid != null))
/*     */       {
/* 320 */         String commonThresholdMonitorsQuery = "select t1.* from AM_ManagedObject t1 join AM_ManagedObject t2 on t1.TYPE = t2.TYPE left outer join AM_ATTRIBUTETHRESHOLDMAPPER atm on t1.RESOURCEID=atm.ID  where t2.RESOURCEID= " + monitorid + " and t1.RESOURCEID !=" + monitorid + " and atm.ATTRIBUTE=" + attributeID + " and atm.THRESHOLDCONFIGURATIONID=" + commonThresholdID;
/*     */         
/* 322 */         if ((resourceid <= 0) && (resourceType != null) && (!resourceType.trim().equals("")))
/*     */         {
/* 324 */           commonThresholdMonitorsQuery = "select * from  AM_ATTRIBUTETHRESHOLDMAPPER atm join  AM_ManagedObject mo  on mo.RESOURCEID=atm.ID join AM_ATTRIBUTES aa on aa.ATTRIBUTEID=atm.ATTRIBUTE and aa.RESOURCETYPE=mo.TYPE where mo.TYPE='" + resourceType + "' and aa.ATTRIBUTEID=" + attributeID + " and atm.THRESHOLDCONFIGURATIONID=" + commonThresholdID;
/*     */         }
/* 326 */         if ((!"Reset".equals(anomalyid)) && ("-99".equals(commonThresholdID)))
/*     */         {
/* 328 */           commonThresholdMonitorsQuery = "select t1.* from AM_ManagedObject t1 join AM_ManagedObject t2 on t1.TYPE = t2.TYPE left outer join AM_ANOMALYTHRESHOLDMAPPER atm on t1.RESOURCEID=atm.ID  where t2.RESOURCEID= " + monitorid + " and t1.RESOURCEID !=" + monitorid + " and atm.ATTRIBUTEID=" + attributeID + " and atm.ANOMALYID=" + anomalyid;
/*     */         }
/*     */         
/* 331 */         ResultSet commonThresholdMonitorResultSet = null;
/*     */         try
/*     */         {
/* 334 */           commonThresholdMonitorResultSet = AMConnectionPool.executeQueryStmt(commonThresholdMonitorsQuery);
/* 335 */           while (commonThresholdMonitorResultSet.next())
/*     */           {
/* 337 */             String monitorDisplayName = commonThresholdMonitorResultSet.getString("DISPLAYNAME");
/* 338 */             String monitorResourceId = String.valueOf(commonThresholdMonitorResultSet.getInt("RESOURCEID"));
/* 339 */             if (similarMonitorsList.contains(monitorResourceId))
/*     */             {
/*     */ 
/* 342 */               similarMonitorsList.remove(monitorResourceId);
/* 343 */               commonThresholdMonitorsList.add(monitorResourceId);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 348 */           isSelectedMonAvailable = !commonThresholdMonitorsList.isEmpty();
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 352 */           exception.printStackTrace();
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 357 */           if (commonThresholdMonitorResultSet != null) {
/* 358 */             commonThresholdMonitorResultSet.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 362 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 367 */       out.write(10);
/*     */       
/* 369 */       out.println("<div style=\"width:55px\" id=\"loading\"><span class=\"bodytextboldwhite\">&nbsp;Loading...&nbsp;</span></div>");
/* 370 */       out.println("<div id=\"dhtmltooltip\"></div>");
/* 371 */       out.println("<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>");
/*     */       
/* 373 */       out.write("\n<div id=\"similarmonitors\" style=\"display:block\" class=\"bodytext\"> <!--%=(isSelectedMonAvailable ?\"block\":\"none\")%>\"-->\n\t     ");
/* 374 */       if (((similarMonitorsList != null) && (!similarMonitorsList.isEmpty())) || ((commonThresholdMonitorsList != null) && (!commonThresholdMonitorsList.isEmpty())))
/*     */       {
/* 376 */         int selectedMonSize = 8;
/* 377 */         int availableMonSize = 8;
/* 378 */         if ((similarMonitorsList != null) && (similarMonitorsList.size() > 8))
/* 379 */           availableMonSize = similarMonitorsList.size();
/* 380 */         if ((commonThresholdMonitorsList != null) && (commonThresholdMonitorsList.size() > 0)) {
/* 381 */           selectedMonSize = commonThresholdMonitorsList.size();
/*     */         }
/*     */         
/* 384 */         if ((commonThresholdID != null) && (commonThresholdID.equals("-1")))
/*     */         {
/*     */ 
/* 387 */           out.write("\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff; padding:5px; line-height:20px;\">\n\t\t  \t\t<tr>\n\t\t     \t\t<td class=\"columnheading\"><b>");
/* 388 */           out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 389 */           out.write("</b></td>\n\t\t  \t\t</tr>\n\t\t  \t\t<tr>\n\t\t     \t\t<td class=\"bodytext\"> ");
/* 390 */           out.print(FormatUtil.getString("am.webclient.configurealert.health.note1"));
/* 391 */           out.write(".\n\t\t\t\t<br></td>\n\t\t  \t\t</tr>\n\t\t  \t\t<tr>\n\t\t     \t\t<td class=\"bodytext\"> ");
/* 392 */           out.print(FormatUtil.getString("am.webclient.configurealert.health.note2"));
/* 393 */           out.write(". </td>\n\t\t  \t\t</tr>\n\t\t  \t\t<tr>\n\t\t     \t\t<td class=\"bodytext\">");
/* 394 */           out.print(FormatUtil.getString("am.webclient.configurealert.health.note3"));
/* 395 */           out.write(".\n\t\t\t\t<br></td>\n\t\t  \t\t</tr>\n   \t\t</table>\n\t\t");
/*     */         }
/*     */         
/*     */ 
/* 399 */         out.write("\n        \t<table>\n                <tr>\n\n        \t        <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                \t<td align=\"center\" class=\"bodytextbold\">");
/* 400 */         out.print(FormatUtil.getString("am.webclient.configurealert.availablemonitors"));
/* 401 */         out.write("</td>\n\t                <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n        \t        <td align=\"center\" class=\"bodytextbold\">");
/* 402 */         out.print(FormatUtil.getString("am.webclient.configurealert.selectedmonitors"));
/* 403 */         out.write("</td>\n                \t<td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                </tr>\n                <tr>\n\n        \t        <td colspan=\"2\" width=\"31%\" align=\"right\" class=\"bodytext\">\n\t\t<div id=\"divAvailableMonitors\" class=\"formtextarea\" width=\"480px\" style=\"OVERFLOW: auto;width:480px;height: 130px\" onscroll=\"OnDivScroll(this);\">\n\t\t\t<select name=\"similarmonitors_available\" STYLE=\"width:750px;\" size=\"");
/* 404 */         out.print(availableMonSize);
/* 405 */         out.write("\" multiple id=\"monitorsavailable\" onfocus=\"OnSelectFocus(this);\" class=\"bodytext\">\n                \t");
/*     */         
/* 407 */         if ((similarMonitorsList != null) && (!similarMonitorsList.isEmpty()))
/*     */         {
/* 409 */           Properties monitorNameId = FaultUtil.getMonitorName(similarMonitorsList, false, false);
/* 410 */           LinkedHashMap sortedSimilarMonitors = FaultUtil.sortPropertiesByValue(monitorNameId);
/* 411 */           Set monitorSet = sortedSimilarMonitors.keySet();
/* 412 */           Iterator monitorItr = monitorSet.iterator();
/* 413 */           while (monitorItr.hasNext())
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 421 */             String monitorId = (String)monitorItr.next();
/*     */             
/* 423 */             String monitorName = (String)sortedSimilarMonitors.get(monitorId);
/* 424 */             monitorName = EnterpriseUtil.decodeString(monitorName);
/* 425 */             String monitorNamettip = monitorName;
/*     */             try {
/* 427 */               if (monitorName.contains("<"))
/* 428 */                 monitorName = monitorName.replace("<", "&#060;");
/* 429 */               if (monitorName.contains(">")) {
/* 430 */                 monitorName = monitorName.replace(">", "&#062;");
/*     */               }
/* 432 */               if (monitorNamettip.contains("<"))
/* 433 */                 monitorNamettip = monitorNamettip.replace("<", "&lt;&#16;");
/* 434 */               if (monitorNamettip.contains(">")) {
/* 435 */                 monitorNamettip = monitorNamettip.replace(">", "&gt;");
/*     */               }
/*     */             } catch (Exception ex) {
/* 438 */               ex.printStackTrace();
/*     */             }
/*     */             
/* 441 */             out.write("\n\t\t                <option value='");
/* 442 */             out.print(monitorId);
/* 443 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 444 */             out.print(monitorNamettip);
/* 445 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 446 */             out.print(monitorName);
/* 447 */             out.write(" </option>\n                \t");
/*     */           } }
/* 449 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n\t\t\t</td>\n\t\t        <td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\" ><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(document.form3.similarmonitors_available,document.form3.similarmonitors_selected);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(document.form3.similarmonitors_available,document.form3.similarmonitors_selected),fnRemoveAllFrmCombo(document.form3.similarmonitors_available);\" value=\"&gt;&gt;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n");
/* 450 */         out.write("\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(document.form3.similarmonitors_selected,document.form3.similarmonitors_available)\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t                </tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(document.form3.similarmonitors_selected,document.form3.similarmonitors_available),fnRemoveAllFrmCombo(document.form3.similarmonitors_selected);\" value=\"&lt;&lt;\"></td>\n\t\t                </tr>\n\t\t\t      </table>\n\t\t\t</td>\n\t\t         <td colspan=\"2\" width=\"31%\" align=\"left\" class=\"bodytext\">\n\t\t\t<div id=\"divSelectedMonitors\" class=\"formtextarea\" width=\"480px\" style=\"OVERFLOW: auto;width:480px;height: 130px\" onscroll=\"OnDivScroll(this);\">\n\t\t\t<select name=\"similarmonitors_selected\" STYLE=\"width:750px;\" size=\"");
/* 451 */         out.print(selectedMonSize);
/* 452 */         out.write("\" multiple id=\"monitorsselected\" onfocus=\"OnSelectFocus(this);\" class=\"bodytext\">\n\t\t\t");
/*     */         
/* 454 */         if ((commonThresholdMonitorsList != null) && (commonThresholdMonitorsList.size() > 0))
/*     */         {
/* 456 */           Properties commonThresholdMonitorNameId = FaultUtil.getMonitorName(commonThresholdMonitorsList, false, false);
/* 457 */           LinkedHashMap sortedSelectedMonitors = FaultUtil.sortPropertiesByValue(commonThresholdMonitorNameId);
/* 458 */           Set monitorSet = sortedSelectedMonitors.keySet();
/* 459 */           Iterator thresholdMonitorItr = monitorSet.iterator();
/*     */           
/*     */ 
/* 462 */           while (thresholdMonitorItr.hasNext())
/*     */           {
/* 464 */             String monitorId = (String)thresholdMonitorItr.next();
/* 465 */             String monitorName = (String)sortedSelectedMonitors.get(monitorId);
/* 466 */             monitorName = EnterpriseUtil.decodeString(monitorName);
/* 467 */             String monitorNamettip = monitorName;
/*     */             try {
/* 469 */               if (monitorName.contains("<"))
/* 470 */                 monitorName = monitorName.replace("<", "&#060;");
/* 471 */               if (monitorName.contains(">")) {
/* 472 */                 monitorName = monitorName.replace(">", "&#062;");
/*     */               }
/* 474 */               if (monitorNamettip.contains("<"))
/* 475 */                 monitorNamettip = monitorNamettip.replace("<", "&lt;&#16;");
/* 476 */               if (monitorNamettip.contains(">")) {
/* 477 */                 monitorNamettip = monitorNamettip.replace(">", "&gt;");
/*     */               }
/*     */             } catch (Exception ex) {
/* 480 */               ex.printStackTrace();
/*     */             }
/*     */             
/* 483 */             out.write("\n\t\t\t\t <option value='");
/* 484 */             out.print(monitorId);
/* 485 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 486 */             out.print(monitorNamettip);
/* 487 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 488 */             out.print(monitorName);
/* 489 */             out.write(" </option>\n\t\t\t");
/*     */           } }
/* 491 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n</td>\n\n\t\t</tr>\n\t        </table>\n                ");
/*     */       } else {
/* 493 */         out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"messagebox\" align=\"center\">\n                        <tr>\n                         <td class=\"bodytext\">\n                        ");
/* 494 */         out.write(" \n                        ");
/* 495 */         if ((resourceid <= 0) && (resourceType != null))
/*     */         {
/* 497 */           out.print(FormatUtil.getString("am.webclient.configurealert.nosimilarmonitors", new String[] { resourceType }));
/* 498 */           out.write("\n                        ");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 503 */           out.write("\n                        \t");
/* 504 */           out.print(FormatUtil.getString("am.webclient.configurealert.nosimilarmonitors", new String[] { resourceDispName }));
/* 505 */           out.write("\n                        ");
/*     */         }
/* 507 */         if ((resourceType.equals("Web_Service_Operation")) || (resourceDispName.equals("Web_Service_Operation")))
/*     */         {
/*     */ 
/* 510 */           out.write("\n                        ");
/* 511 */           out.print(FormatUtil.getString("am.webclient.configurealert.wsm.nosimilarmonitors"));
/* 512 */           out.write("\n                        ");
/*     */         }
/* 514 */         out.write("\n                        </td>\n                        ");
/* 515 */         out.write(" </tr>\n                 </table>\n\t\t ");
/*     */       }
/* 517 */       out.write("\n</div>\n");
/* 518 */       out.write("\n\n\n\n");
/*     */     } catch (Throwable t) {
/* 520 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 521 */         out = _jspx_out;
/* 522 */         if ((out != null) && (out.getBufferSize() != 0))
/* 523 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 524 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 527 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 533 */     PageContext pageContext = _jspx_page_context;
/* 534 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 536 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 537 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 538 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 540 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 542 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 543 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 544 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 546 */       return true;
/*     */     }
/* 548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 549 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\SelectedMonitorThreshold_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */