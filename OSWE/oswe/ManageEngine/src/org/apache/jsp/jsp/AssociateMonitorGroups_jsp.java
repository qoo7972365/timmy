/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class AssociateMonitorGroups_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  35 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*     */   public static final String STATUS_SEPARATOR = "#";
/*     */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*  38 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*  39 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*     */   public static final String MISTR = "Monitor";
/*     */   public static final String MISTRs = "Monitors";
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*     */   {
/*  45 */     return getOptions(value, text, tableName, distinct, "");
/*     */   }
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*     */   {
/*  50 */     ArrayList list = null;
/*  51 */     StringBuffer sbf = new StringBuffer();
/*  52 */     ManagedApplication mo = new ManagedApplication();
/*  53 */     if (distinct)
/*     */     {
/*  55 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*     */     }
/*     */     else
/*     */     {
/*  59 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*     */     }
/*     */     
/*  62 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/*  64 */       ArrayList row = (ArrayList)list.get(i);
/*  65 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  66 */       if (distinct) {
/*  67 */         sbf.append(row.get(0));
/*     */       } else
/*  69 */         sbf.append(row.get(1));
/*  70 */       sbf.append("</option>");
/*     */     }
/*     */     
/*  73 */     return sbf.toString();
/*     */   }
/*     */   
/*     */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*     */   {
/*  78 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*     */   }
/*     */   
/*     */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*     */   {
/*  83 */     return com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*     */   }
/*     */   
/*     */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*     */   {
/*  88 */     int i = 0;
/*  89 */     String uri = request.getRequestURI();
/*  90 */     String fromAlarmEscalation = "/jsp/AlertRes_Mtrgrp.jsp";
/*  91 */     String uri1 = "/jsp/MaintenanceTask.jsp";
/*  92 */     if (uri.equals(uri1)) {
/*  93 */       i = 1;
/*     */     }
/*     */     else {
/*  96 */       i = 0;
/*     */     }
/*  98 */     String addColspan = "";
/*  99 */     if (fromAlarmEscalation.equals(uri)) {
/* 100 */       addColspan = "colspan=\"2\"";
/*     */     }
/* 102 */     String type = request.getParameter("selectionType");
/*     */     
/* 104 */     StringBuffer toreturn = new StringBuffer();
/*     */     
/* 106 */     String disableSelAllChilds = request.getParameter("disableSelAllChilds");
/* 107 */     String selectedDependentMGroupsStr = (String)request.getAttribute("selectedDepMonGroups");
/* 108 */     ArrayList<String> mgroupsToDisable = (ArrayList)request.getAttribute("mgroupsToDisable");
/*     */     
/* 110 */     for (int j = 0; j < singlechilmos.size(); j++)
/*     */     {
/* 112 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 113 */       String childresid = (String)singlerow.get(0);
/* 114 */       String childresname = (String)singlerow.get(1);
/* 115 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*     */       
/* 117 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 118 */       String checkedvalue = "";
/* 119 */       ArrayList grouplist = new ArrayList();
/* 120 */       if (i == 1) {
/* 121 */         grouplist = ((AMActionForm)request.getAttribute("AMActionForm")).getPresentg();
/*     */       }
/* 123 */       else if ("usergroup".equalsIgnoreCase(type)) {
/* 124 */         grouplist = (ArrayList)request.getAttribute("selectedUserGroupMg");
/*     */       } else {
/* 126 */         grouplist = (ArrayList)request.getAttribute("selectedMonitor");
/*     */       }
/*     */       
/* 129 */       if ((grouplist != null) && (grouplist.size() != 0))
/*     */       {
/* 131 */         for (int z = 0; z < grouplist.size(); z++)
/*     */         {
/* 133 */           if (childresid.equals(((Properties)grouplist.get(z)).getProperty("value")))
/*     */           {
/* 135 */             checkedvalue = "checked";
/*     */           }
/*     */         }
/*     */       }
/* 139 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 140 */       int spacing = 0;
/* 141 */       if (level >= 1)
/*     */       {
/* 143 */         spacing = 20 * level;
/*     */       }
/* 145 */       if (EnterpriseUtil.isAdminServer)
/*     */       {
/*     */ 
/* 148 */         int childresidInt = Integer.parseInt(childresid);
/* 149 */         if (childresidInt > com.adventnet.appmanager.server.framework.comm.Constants.RANGE)
/*     */         {
/* 151 */           String parent = CommDBUtil.getManagedServerNameWithPort(childresid);
/* 152 */           childresname = childresname + "_" + parent;
/*     */         }
/*     */       }
/* 155 */       if ((childtype.equals("HAI")) && (!CustomerManagementAPI.isSiteId(childresid + "")))
/*     */       {
/* 157 */         String checkedStr = "";
/* 158 */         String disableStr = "";
/* 159 */         if ((selectedDependentMGroupsStr != null) && (selectedDependentMGroupsStr.indexOf(childresid) != -1)) {
/* 160 */           checkedStr = "checked";
/*     */         }
/* 162 */         if ((mgroupsToDisable != null) && (mgroupsToDisable.contains(childresid))) {
/* 163 */           disableStr = "disabled";
/*     */         }
/* 165 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 166 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 167 */         String checkbox = "<input type=\"checkbox\" " + disableStr + " " + checkedStr + " name=\"select\" " + checkedvalue + " id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"";
/* 168 */         if ((disableSelAllChilds == null) || (disableSelAllChilds.equalsIgnoreCase("false"))) {
/* 169 */           checkbox = checkbox + " onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"";
/*     */         }
/* 171 */         checkbox = checkbox + ">";
/* 172 */         String mgNameHidInput = "<input type=\"hidden\" name=\"mgName\" id=\"mgName\" value=\"" + childresname + "\">";
/* 173 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*     */         
/* 175 */         System.out.println("request.isUserInRole\"ADMIN\")" + request.isUserInRole("ADMIN"));
/* 176 */         String resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + " " + getTrimmedText(childresname, 45) + " ";
/*     */         
/* 178 */         toreturn.append("<tr " + tempbgcolor + " width=\"80%\" id=\"#monitor" + currentresourceidtree + "\" style=\"background: rgb(255, 255, 255) none repeat scroll 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; display: none;\">");
/* 179 */         toreturn.append("<td " + tempbgcolor + " width=\"2%\" >&nbsp;&nbsp;</td> ");
/* 180 */         toreturn.append(mgNameHidInput);
/* 181 */         toreturn.append("<td " + tempbgcolor + " " + addColspan + " width=\"68%\"  style=\"padding-left: " + spacing + "px !important;\">" + resourcelink + "</td>");
/* 182 */         toreturn.append("");
/* 183 */         toreturn.append("</tr>");
/* 184 */         if (childmos.get(childresid + "") != null)
/*     */         {
/* 186 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 187 */           toreturn.append(toappend);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 193 */     return toreturn.toString();
/*     */   }
/*     */   
/*     */ 
/* 197 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 203 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 204 */   static { _jspx_dependants.put("/jsp/mgtreeview.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/* 214 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/* 218 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 219 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 221 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/* 225 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 226 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/* 233 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/* 236 */     JspWriter out = null;
/* 237 */     Object page = this;
/* 238 */     JspWriter _jspx_out = null;
/* 239 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 243 */       response.setContentType("text/html;charset=UTF-8");
/* 244 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*     */       
/* 246 */       _jspx_page_context = pageContext;
/* 247 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 248 */       ServletConfig config = pageContext.getServletConfig();
/* 249 */       session = pageContext.getSession();
/* 250 */       out = pageContext.getOut();
/* 251 */       _jspx_out = out;
/*     */       
/* 253 */       out.write("\n\n\n\n\n");
/* 254 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 255 */       out.write(10);
/*     */       
/* 257 */       HashMap map = new HashMap();
/* 258 */       map = ReportUtil.getMonitorGroups();
/* 259 */       Hashtable chilmos = (Hashtable)map.get("childlist");
/* 260 */       HashMap extDeviceMap = null;
/* 261 */       HashMap site24x7List = null;
/* 262 */       if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*     */       {
/* 264 */         extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/* 265 */         site24x7List = com.adventnet.appmanager.util.DBUtil.getAllsite24x7MonitorsLink();
/*     */       }
/*     */       
/* 268 */       out.write("\n\n<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n\t\t<tr height=\"22\">\n\t\t<td class=\"light-blue-bg\" width=\"40%\" align=\"center\">&nbsp;<b>");
/* 269 */       out.print(FormatUtil.getString("Name"));
/* 270 */       out.write("</b> &nbsp;</td>\n\n\t\t<td class=\"light-blue-bg\" align=\"right\"><a href=\"javascript:void(0);\" class=\"staticlinks\" onClick=\"javascript:toggleallDivs('show');\">\n\t\t<div id=\"showall\" style=\"display: inline;\" >[");
/* 271 */       out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 272 */       out.write("]</div>\n\t\t<div id=\"hideall\" style=\"display: none;\">[");
/* 273 */       out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 274 */       out.write("]</div></a>&nbsp; &nbsp;</td>\n\t\t</tr>\n\t\t</table>\n\n\t\t");
/*     */       
/* 276 */       ArrayList grouplist = new ArrayList();
/* 277 */       String type = request.getParameter("selectionType");
/*     */       try {
/* 279 */         if ("usergroup".equalsIgnoreCase(type)) {
/* 280 */           grouplist = (ArrayList)request.getAttribute("selectedUserGroupMg");
/*     */         } else {
/* 282 */           grouplist = (ArrayList)request.getAttribute("selectedMonitor");
/*     */         }
/*     */       } catch (Exception e) {
/* 285 */         e.printStackTrace();
/*     */       }
/*     */       
/* 288 */       out.write("\n\n\t\t<table width=\"90%\" id=\"allMonitorGroups\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\" >\n\n\t\t");
/*     */       
/* 290 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 291 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 292 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */       
/* 294 */       _jspx_th_logic_005fiterate_005f0.setName("applications");
/*     */       
/* 296 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */       
/* 298 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*     */       
/* 300 */       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 301 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 302 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 303 */         ArrayList row = null;
/* 304 */         Integer i = null;
/* 305 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 306 */           out = _jspx_page_context.pushBody();
/* 307 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 308 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */         }
/* 310 */         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 311 */         i = (Integer)_jspx_page_context.findAttribute("i");
/*     */         for (;;) {
/* 313 */           out.write("\n\t\t\t");
/*     */           
/* 315 */           String MGType = row.get(2) != null ? (String)row.get(2) : "";
/*     */           
/* 317 */           if (MGType.equals("0"))
/*     */           {
/*     */ 
/* 320 */             String restitle = null;
/* 321 */             if (EnterpriseUtil.isAdminServer())
/*     */             {
/* 323 */               restitle = (String)row.get(0) + "_" + CommDBUtil.getManagedServerNameWithPort((String)row.get(6));
/*     */             }
/*     */             else
/*     */             {
/* 327 */               restitle = row.get(8) != null ? (String)row.get(0) + "-UnManaged" : (String)row.get(0);
/*     */             }
/* 329 */             String status = row.get(8) != null ? "disabledtext" : "staticlinks";
/* 330 */             String resIdTOCheck = "-1";
/* 331 */             String checkedvalue = "";
/*     */             
/*     */             try
/*     */             {
/* 335 */               resIdTOCheck = (String)row.get(6);
/* 336 */               if (grouplist != null) {
/* 337 */                 for (int z = 0; z < grouplist.size(); z++)
/*     */                 {
/* 339 */                   if (resIdTOCheck.equals(((Properties)grouplist.get(z)).getProperty("value")))
/*     */                   {
/* 341 */                     checkedvalue = "checked";
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 347 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */ 
/* 351 */             if (!resIdTOCheck.equals("orphaned"))
/*     */             {
/*     */ 
/* 354 */               out.write("\n\t\t\t<tr width=\"40%\" class=\"whitegrayrightalign\" height=\"25\">\n\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"3%\"><a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor");
/* 355 */               out.print(resIdTOCheck);
/* 356 */               out.write("'),toggleTreeImage('");
/* 357 */               out.print(resIdTOCheck);
/* 358 */               out.write("');\"><div id=\"monitorShow");
/* 359 */               out.print(resIdTOCheck);
/* 360 */               out.write("\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide");
/* 361 */               out.print(resIdTOCheck);
/* 362 */               out.write("\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a></td>\n\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"27%\" title=\"");
/* 363 */               out.print(restitle);
/* 364 */               out.write("\"  style=\"padding-left: 0px;\"><input type=\"checkbox\" name=\"select\" value=\"");
/* 365 */               out.print(resIdTOCheck);
/* 366 */               out.write(34);
/* 367 */               out.write(32);
/* 368 */               out.print(checkedvalue);
/* 369 */               out.write(" id=\"");
/* 370 */               out.print(resIdTOCheck);
/* 371 */               out.write("\"  onclick=\"selectAllChildCKbs('");
/* 372 */               out.print(resIdTOCheck);
/* 373 */               out.write("',this,this.form)\">");
/* 374 */               out.print(row.get(1));
/* 375 */               out.write("\n\t\t\t");
/* 376 */               if (EnterpriseUtil.isAdminServer())
/*     */               {
/* 378 */                 out.write("\n\t\t\t_");
/* 379 */                 out.print(CommDBUtil.getManagedServerNameWithPort((String)row.get(6)));
/* 380 */                 out.write("\n\t\t\t");
/*     */               }
/* 382 */               out.write("\n\t\t\t</td>\n\n\t\t\t<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t\t\t");
/*     */               
/* 384 */               SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 385 */               _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 386 */               _jspx_th_c_005fset_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*     */               
/* 388 */               _jspx_th_c_005fset_005f0.setVar("key");
/* 389 */               int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 390 */               if (_jspx_eval_c_005fset_005f0 != 0) {
/* 391 */                 if (_jspx_eval_c_005fset_005f0 != 1) {
/* 392 */                   out = _jspx_page_context.pushBody();
/* 393 */                   _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 394 */                   _jspx_th_c_005fset_005f0.doInitBody();
/*     */                 }
/*     */                 for (;;) {
/* 397 */                   out.write(32);
/* 398 */                   out.print(row.get(6) + "#" + "17" + "#" + "MESSAGE");
/* 399 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 400 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 403 */                 if (_jspx_eval_c_005fset_005f0 != 1) {
/* 404 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 407 */               if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 408 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*     */               }
/*     */               
/* 411 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 412 */               out.write("\n\t\t\t</tr>\n\t\t\t");
/*     */             }
/*     */             
/* 415 */             if (chilmos.get(resIdTOCheck + "") != null)
/*     */             {
/* 417 */               System.out.println("resIdTOCheck22222" + resIdTOCheck);
/* 418 */               ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 419 */               Hashtable availhealth = new Hashtable();
/*     */               
/* 421 */               String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", resIdTOCheck + "", chilmos, availhealth, 1, request, extDeviceMap, site24x7List);
/* 422 */               out.println(toappend);
/*     */             }
/*     */           }
/*     */           
/* 426 */           out.write(10);
/* 427 */           out.write(9);
/* 428 */           out.write(9);
/* 429 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 430 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 431 */           i = (Integer)_jspx_page_context.findAttribute("i");
/* 432 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 435 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 436 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 439 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 440 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */       }
/*     */       else {
/* 443 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 444 */         out.write("\n\t</table>\n");
/*     */       }
/* 446 */     } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 447 */         out = _jspx_out;
/* 448 */         if ((out != null) && (out.getBufferSize() != 0))
/* 449 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 450 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 453 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AssociateMonitorGroups_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */