/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import com.manageengine.it360.util.SLAUtil;
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
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.tomcat.InstanceManager;
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
/*     */ public final class AlertRes_005fMtrgrp_jsp
/*     */   extends HttpJspBase
/*     */   implements JspSourceDependent
/*     */ {
/*  61 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*     */   public static final String STATUS_SEPARATOR = "#";
/*     */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*  64 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*  65 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*     */   public static final String MISTR = "Monitor";
/*     */   public static final String MISTRs = "Monitors";
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*     */   {
/*  71 */     return getOptions(value, text, tableName, distinct, "");
/*     */   }
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*     */   {
/*  76 */     ArrayList list = null;
/*  77 */     StringBuffer sbf = new StringBuffer();
/*  78 */     ManagedApplication mo = new ManagedApplication();
/*  79 */     if (distinct)
/*     */     {
/*  81 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*     */     }
/*     */     else
/*     */     {
/*  85 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*     */     }
/*     */     
/*  88 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/*  90 */       ArrayList row = (ArrayList)list.get(i);
/*  91 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  92 */       if (distinct) {
/*  93 */         sbf.append(row.get(0));
/*     */       } else
/*  95 */         sbf.append(row.get(1));
/*  96 */       sbf.append("</option>");
/*     */     }
/*     */     
/*  99 */     return sbf.toString();
/*     */   }
/*     */   
/*     */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*     */   {
/* 104 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*     */   }
/*     */   
/*     */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*     */   {
/* 109 */     return FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*     */   }
/*     */   
/*     */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*     */   {
/* 114 */     int i = 0;
/* 115 */     String uri = request.getRequestURI();
/* 116 */     String fromAlarmEscalation = "/jsp/AlertRes_Mtrgrp.jsp";
/* 117 */     String uri1 = "/jsp/MaintenanceTask.jsp";
/* 118 */     if (uri.equals(uri1)) {
/* 119 */       i = 1;
/*     */     }
/*     */     else {
/* 122 */       i = 0;
/*     */     }
/* 124 */     String addColspan = "";
/* 125 */     if (fromAlarmEscalation.equals(uri)) {
/* 126 */       addColspan = "colspan=\"2\"";
/*     */     }
/* 128 */     String type = request.getParameter("selectionType");
/*     */     
/* 130 */     StringBuffer toreturn = new StringBuffer();
/*     */     
/* 132 */     String disableSelAllChilds = request.getParameter("disableSelAllChilds");
/* 133 */     String selectedDependentMGroupsStr = (String)request.getAttribute("selectedDepMonGroups");
/* 134 */     ArrayList<String> mgroupsToDisable = (ArrayList)request.getAttribute("mgroupsToDisable");
/*     */     
/* 136 */     for (int j = 0; j < singlechilmos.size(); j++)
/*     */     {
/* 138 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 139 */       String childresid = (String)singlerow.get(0);
/* 140 */       String childresname = (String)singlerow.get(1);
/* 141 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*     */       
/* 143 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 144 */       String checkedvalue = "";
/* 145 */       ArrayList grouplist = new ArrayList();
/* 146 */       if (i == 1) {
/* 147 */         grouplist = ((AMActionForm)request.getAttribute("AMActionForm")).getPresentg();
/*     */       }
/* 149 */       else if ("usergroup".equalsIgnoreCase(type)) {
/* 150 */         grouplist = (ArrayList)request.getAttribute("selectedUserGroupMg");
/*     */       } else {
/* 152 */         grouplist = (ArrayList)request.getAttribute("selectedMonitor");
/*     */       }
/*     */       
/* 155 */       if ((grouplist != null) && (grouplist.size() != 0))
/*     */       {
/* 157 */         for (int z = 0; z < grouplist.size(); z++)
/*     */         {
/* 159 */           if (childresid.equals(((Properties)grouplist.get(z)).getProperty("value")))
/*     */           {
/* 161 */             checkedvalue = "checked";
/*     */           }
/*     */         }
/*     */       }
/* 165 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 166 */       int spacing = 0;
/* 167 */       if (level >= 1)
/*     */       {
/* 169 */         spacing = 20 * level;
/*     */       }
/* 171 */       if (EnterpriseUtil.isAdminServer)
/*     */       {
/*     */ 
/* 174 */         int childresidInt = Integer.parseInt(childresid);
/* 175 */         if (childresidInt > com.adventnet.appmanager.server.framework.comm.Constants.RANGE)
/*     */         {
/* 177 */           String parent = CommDBUtil.getManagedServerNameWithPort(childresid);
/* 178 */           childresname = childresname + "_" + parent;
/*     */         }
/*     */       }
/* 181 */       if ((childtype.equals("HAI")) && (!CustomerManagementAPI.isSiteId(childresid + "")))
/*     */       {
/* 183 */         String checkedStr = "";
/* 184 */         String disableStr = "";
/* 185 */         if ((selectedDependentMGroupsStr != null) && (selectedDependentMGroupsStr.indexOf(childresid) != -1)) {
/* 186 */           checkedStr = "checked";
/*     */         }
/* 188 */         if ((mgroupsToDisable != null) && (mgroupsToDisable.contains(childresid))) {
/* 189 */           disableStr = "disabled";
/*     */         }
/* 191 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 192 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 193 */         String checkbox = "<input type=\"checkbox\" " + disableStr + " " + checkedStr + " name=\"select\" " + checkedvalue + " id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"";
/* 194 */         if ((disableSelAllChilds == null) || (disableSelAllChilds.equalsIgnoreCase("false"))) {
/* 195 */           checkbox = checkbox + " onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"";
/*     */         }
/* 197 */         checkbox = checkbox + ">";
/* 198 */         String mgNameHidInput = "<input type=\"hidden\" name=\"mgName\" id=\"mgName\" value=\"" + childresname + "\">";
/* 199 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*     */         
/* 201 */         System.out.println("request.isUserInRole\"ADMIN\")" + request.isUserInRole("ADMIN"));
/* 202 */         String resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + " " + getTrimmedText(childresname, 45) + " ";
/*     */         
/* 204 */         toreturn.append("<tr " + tempbgcolor + " width=\"80%\" id=\"#monitor" + currentresourceidtree + "\" style=\"background: rgb(255, 255, 255) none repeat scroll 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; display: none;\">");
/* 205 */         toreturn.append("<td " + tempbgcolor + " width=\"2%\" >&nbsp;&nbsp;</td> ");
/* 206 */         toreturn.append(mgNameHidInput);
/* 207 */         toreturn.append("<td " + tempbgcolor + " " + addColspan + " width=\"68%\"  style=\"padding-left: " + spacing + "px !important;\">" + resourcelink + "</td>");
/* 208 */         toreturn.append("");
/* 209 */         toreturn.append("</tr>");
/* 210 */         if (childmos.get(childresid + "") != null)
/*     */         {
/* 212 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 213 */           toreturn.append(toappend);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 219 */     return toreturn.toString();
/*     */   }
/*     */   
/*     */ 
/* 223 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 229 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 230 */   static { _jspx_dependants.put("/jsp/mgtreeview.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/* 240 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/* 244 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 245 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 246 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 247 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/* 251 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 252 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 259 */     HttpSession session = null;
/*     */     
/*     */ 
/* 262 */     JspWriter out = null;
/* 263 */     Object page = this;
/* 264 */     JspWriter _jspx_out = null;
/* 265 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 269 */       response.setContentType("text/html;charset=UTF-8");
/* 270 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*     */       
/* 272 */       _jspx_page_context = pageContext;
/* 273 */       ServletContext application = pageContext.getServletContext();
/* 274 */       ServletConfig config = pageContext.getServletConfig();
/* 275 */       session = pageContext.getSession();
/* 276 */       out = pageContext.getOut();
/* 277 */       _jspx_out = out;
/*     */       
/* 279 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 280 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 281 */       out.write(10);
/* 282 */       out.write(10);
/*     */       
/*     */ 
/* 285 */       HashMap map = new HashMap();
/*     */       try
/*     */       {
/* 288 */         map = ReportUtil.getMonitorGroups(false, null, request);
/* 289 */         ArrayList tempList = (ArrayList)map.get("grouplist");
/* 290 */         request.setAttribute("applications", tempList);
/*     */       } catch (Exception ex) {
/* 292 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 295 */       ArrayList resIDs = new ArrayList();
/* 296 */       Hashtable chilmos = (Hashtable)map.get("childlist");
/*     */       
/* 298 */       int totalmonitorcount = 0;
/* 299 */       ArrayList tempresourelist = new ArrayList();
/*     */       
/* 301 */       HashMap extDeviceMap = null;
/* 302 */       HashMap site24x7List = null;
/* 303 */       if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*     */       {
/* 305 */         extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink();
/* 306 */         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 311 */       out.write(10);
/* 312 */       out.write(10);
/*     */       
/* 314 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 315 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 316 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */       
/* 318 */       _jspx_th_logic_005fiterate_005f0.setName("applications");
/*     */       
/* 320 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */       
/* 322 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*     */       
/* 324 */       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 325 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 326 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 327 */         ArrayList row = null;
/* 328 */         Integer i = null;
/* 329 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 330 */           out = _jspx_page_context.pushBody();
/* 331 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 332 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */         }
/* 334 */         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 335 */         i = (Integer)_jspx_page_context.findAttribute("i");
/*     */         for (;;) {
/* 337 */           out.write(10);
/*     */           
/* 339 */           if (!((String)row.get(6)).equals("orphaned"))
/*     */           {
/* 341 */             resIDs.add((String)row.get(6));
/*     */           }
/*     */           try
/*     */           {
/* 345 */             ArrayList srow = (ArrayList)chilmos.get((String)row.get(6) + "");
/* 346 */             if (srow != null)
/*     */             {
/* 348 */               for (int k = 0; k < srow.size(); k++)
/*     */               {
/* 350 */                 ArrayList mo = (ArrayList)srow.get(k);
/* 351 */                 if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*     */                 {
/* 353 */                   String cresid = ((String)mo.get(0) + "").trim();
/* 354 */                   resIDs.add(cresid);
/* 355 */                   String resourceType = ((String)mo.get(2) + "").trim();
/*     */                   
/* 357 */                   if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*     */                   {
/* 359 */                     tempresourelist.add(cresid);
/* 360 */                     totalmonitorcount++;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 368 */             ex.printStackTrace();
/*     */           }
/*     */           
/* 371 */           out.write(10);
/* 372 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 373 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 374 */           i = (Integer)_jspx_page_context.findAttribute("i");
/* 375 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 378 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 379 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 382 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 383 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */       }
/*     */       else {
/* 386 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 387 */         out.write("\n\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\">\n\tvar GlobalMGs=new Array();\n\t");
/*     */         
/* 389 */         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 390 */         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 391 */         _jspx_th_logic_005fiterate_005f1.setParent(null);
/*     */         
/* 393 */         _jspx_th_logic_005fiterate_005f1.setName("applications");
/*     */         
/* 395 */         _jspx_th_logic_005fiterate_005f1.setId("row");
/*     */         
/* 397 */         _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*     */         
/* 399 */         _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 400 */         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 401 */         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 402 */           ArrayList row = null;
/* 403 */           Integer i = null;
/* 404 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 405 */             out = _jspx_page_context.pushBody();
/* 406 */             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 407 */             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*     */           }
/* 409 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 410 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*     */           for (;;) {
/* 412 */             out.write("\n\t\tGlobalMGs[");
/* 413 */             out.print(i);
/* 414 */             out.write(93);
/* 415 */             out.write(61);
/* 416 */             out.write(39);
/* 417 */             out.print((String)row.get(6));
/* 418 */             out.write("';\n\t");
/* 419 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 420 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 421 */             i = (Integer)_jspx_page_context.findAttribute("i");
/* 422 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 425 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 426 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 429 */         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 430 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*     */         }
/*     */         else {
/* 433 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 434 */           out.write("\n\n</SCRIPT>\n\n\n\n\t");
/*     */           
/* 436 */           String ruleid = request.getParameter("sid");
/* 437 */           ArrayList grouplist = new ArrayList();
/* 438 */           if (!ruleid.equals("null"))
/*     */           {
/*     */ 
/*     */             try
/*     */             {
/*     */ 
/* 444 */               ManagedApplication mo = new ManagedApplication();
/* 445 */               String query = "Select MONITOR_ID from AM_ALERTTYPEMAPPER where RULEID=" + ruleid;
/* 446 */               if (query != null)
/*     */               {
/* 448 */                 ArrayList rules = mo.getRows(query);
/* 449 */                 for (int i = 0; i < rules.size(); i++)
/*     */                 {
/* 451 */                   Properties props = new Properties();
/* 452 */                   props.setProperty("value", (String)((ArrayList)rules.get(i)).get(0));
/* 453 */                   grouplist.add(props);
/*     */                 }
/*     */               }
/*     */               
/* 457 */               request.setAttribute("selectedMonitor", grouplist);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 461 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */           
/* 465 */           out.write("\n\n\t<table width=\"90%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\" border=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"62%\" height=\"31\" class=\"tableheading\">");
/* 466 */           out.print(FormatUtil.getString("am.webclient.monitorgroup.details"));
/* 467 */           out.write("</td>\n\t\t</tr>\n\t</table>\n\n\t<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t\t<tr height=\"28\">\n\t\t<td width=\"100%\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>\n\t\t\t<td class=\"columnheading\" width=\"6%\"></td>\n            <td class=\"columnheading\" colspan=\"7\" height=\"28\" width=\"67%\">");
/* 468 */           out.print(FormatUtil.getString("Name"));
/* 469 */           out.write("</td>\n\n\t\t\t<td  align=\"left\" height=\"28\" class=\"columnheading\" width=\"27%\" ><a href=\"javascript:void(0);\" class=\"staticlinks\" onClick=\"javascript:toggleallDivs('show');\"><div id=\"showall\" style=\"display: inline;\" >[");
/* 470 */           out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 471 */           out.write("]</div><div id=\"hideall\" style=\"display: none;\">[");
/* 472 */           out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 473 */           out.write("]</div></a>&nbsp; &nbsp; </td>\n\t\t</tr></table>\n\t</td></tr>\n\t<tr><td width=\"100%\">\n\t<table width=\"100%\" id=\"allMonitorGroups\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t");
/*     */           
/* 475 */           IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 476 */           _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 477 */           _jspx_th_logic_005fiterate_005f2.setParent(null);
/*     */           
/* 479 */           _jspx_th_logic_005fiterate_005f2.setName("applications");
/*     */           
/* 481 */           _jspx_th_logic_005fiterate_005f2.setId("row");
/*     */           
/* 483 */           _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/*     */           
/* 485 */           _jspx_th_logic_005fiterate_005f2.setIndexId("i");
/* 486 */           int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 487 */           if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 488 */             ArrayList row = null;
/* 489 */             Integer i = null;
/* 490 */             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 491 */               out = _jspx_page_context.pushBody();
/* 492 */               _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 493 */               _jspx_th_logic_005fiterate_005f2.doInitBody();
/*     */             }
/* 495 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 496 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*     */             for (;;) {
/* 498 */               out.write(10);
/* 499 */               out.write(9);
/*     */               
/*     */ 
/* 502 */               String MGType = row.get(2) != null ? (String)row.get(2) : "";
/*     */               
/* 504 */               if (MGType.equals("0"))
/*     */               {
/*     */ 
/* 507 */                 String restitle = row.get(8) != null ? (String)row.get(0) + "-UnManaged" : (String)row.get(0);
/* 508 */                 String status = row.get(8) != null ? "disabledtext" : "staticlinks";
/* 509 */                 String resIdTOCheck = "-1";
/* 510 */                 String checkedvalue = "";
/*     */                 
/*     */                 try
/*     */                 {
/* 514 */                   resIdTOCheck = (String)row.get(6);
/* 515 */                   for (int z = 0; z < grouplist.size(); z++)
/*     */                   {
/* 517 */                     String targetID = ((Properties)grouplist.get(z)).getProperty("value");
/*     */                     
/* 519 */                     if (resIdTOCheck.equals(targetID))
/*     */                     {
/* 521 */                       checkedvalue = "checked";
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 526 */                   e.printStackTrace();
/*     */                 }
/* 528 */                 boolean toShow = false;
/* 529 */                 if (EnterpriseUtil.isIt360MSPEdition)
/*     */                 {
/*     */ 
/* 532 */                   Properties bsgProps = CustomerManagementAPI.getAllBSGProps(resIdTOCheck);
/* 533 */                   if ((bsgProps != null) && (!bsgProps.isEmpty()) && (!SLAUtil.isSLA(resIdTOCheck)))
/*     */                   {
/* 535 */                     toShow = true;
/*     */                   }
/*     */                 }
/*     */                 else
/*     */                 {
/* 540 */                   toShow = true;
/*     */                 }
/* 542 */                 if (toShow)
/*     */                 {
/* 544 */                   if (!resIdTOCheck.equals("orphaned"))
/*     */                   {
/*     */ 
/* 547 */                     out.write("\n\n\t\t\t<tr width=\"80%\" class=\"whitegrayrightalign\" height=\"25\">\n\t\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"3%\">\n\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor");
/* 548 */                     out.print(resIdTOCheck);
/* 549 */                     out.write("'),toggleTreeImage('");
/* 550 */                     out.print(resIdTOCheck);
/* 551 */                     out.write("');\">\n\t\t\t\t\t\t<div id=\"monitorShow");
/* 552 */                     out.print(resIdTOCheck);
/* 553 */                     out.write("\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div>\n\t\t\t\t\t\t<div id=\"monitorHide");
/* 554 */                     out.print(resIdTOCheck);
/* 555 */                     out.write("\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div>\n\t\t\t\t\t</a>\n\t\t\t\t</td>\n\t\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"67%\" title=\"");
/* 556 */                     out.print(restitle);
/* 557 */                     out.write("\"  style=\"padding-left: 0px;\">\n\t\t\t\t\t<input type=\"checkbox\" name=\"select\" value=\"");
/* 558 */                     out.print(resIdTOCheck);
/* 559 */                     out.write(34);
/* 560 */                     out.write(32);
/* 561 */                     out.print(checkedvalue);
/* 562 */                     out.write(" id=\"");
/* 563 */                     out.print(resIdTOCheck);
/* 564 */                     out.write("\"  onclick=\"selectAllChildCKbs('");
/* 565 */                     out.print(resIdTOCheck);
/* 566 */                     out.write("',this,this.form)\">");
/* 567 */                     out.print(row.get(1));
/* 568 */                     out.write("\n\t\t\t\t</td>\n\t\t\t\t<td  class=\"whitegrayrightalign\" witdh=\"30%\" >");
/* 569 */                     out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 570 */                     out.write(" </td>\n\t\t\t\t<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t\t\t\t");
/*     */                     
/* 572 */                     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 573 */                     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 574 */                     _jspx_th_c_005fset_005f0.setParent(_jspx_th_logic_005fiterate_005f2);
/*     */                     
/* 576 */                     _jspx_th_c_005fset_005f0.setVar("key");
/* 577 */                     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 578 */                     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 579 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 580 */                         out = _jspx_page_context.pushBody();
/* 581 */                         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 582 */                         _jspx_th_c_005fset_005f0.doInitBody();
/*     */                       }
/*     */                       for (;;) {
/* 585 */                         out.write(32);
/* 586 */                         out.print(row.get(6) + "#" + "17" + "#" + "MESSAGE");
/* 587 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 588 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/* 591 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 592 */                         out = _jspx_page_context.popBody();
/*     */                       }
/*     */                     }
/* 595 */                     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 596 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*     */                     }
/*     */                     
/* 599 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 600 */                     out.write("\n\t\t\t</tr>\n\t\t");
/*     */                   }
/*     */                   
/* 603 */                   if (chilmos.get(resIdTOCheck + "") != null)
/*     */                   {
/*     */ 
/* 606 */                     ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 607 */                     Hashtable availhealth = new Hashtable();
/*     */                     
/* 609 */                     String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", resIdTOCheck + "", chilmos, availhealth, 1, request, extDeviceMap, site24x7List);
/* 610 */                     out.println(toappend);
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 615 */               out.write(10);
/* 616 */               out.write(9);
/* 617 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 618 */               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 619 */               i = (Integer)_jspx_page_context.findAttribute("i");
/* 620 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 623 */             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 624 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 627 */           if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 628 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/*     */           }
/*     */           else {
/* 631 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 632 */             out.write("\n</table>\n</td></tr>\n</table>\n\n");
/*     */           }
/* 634 */         } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 635 */         out = _jspx_out;
/* 636 */         if ((out != null) && (out.getBufferSize() != 0))
/* 637 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 638 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 641 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AlertRes_005fMtrgrp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */