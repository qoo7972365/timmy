/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.customfields.MyFields;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
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
/*     */ import java.util.Vector;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
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
/*     */ public final class ScheduleReportsMGTree_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  47 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*     */   public static final String STATUS_SEPARATOR = "#";
/*     */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*  50 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*  51 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*     */   public static final String MISTR = "Monitor";
/*     */   public static final String MISTRs = "Monitors";
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*     */   {
/*  57 */     return getOptions(value, text, tableName, distinct, "");
/*     */   }
/*     */   
/*     */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*     */   {
/*  62 */     ArrayList list = null;
/*  63 */     StringBuffer sbf = new StringBuffer();
/*  64 */     ManagedApplication mo = new ManagedApplication();
/*  65 */     if (distinct)
/*     */     {
/*  67 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*     */     }
/*     */     else
/*     */     {
/*  71 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*     */     }
/*     */     
/*  74 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/*  76 */       ArrayList row = (ArrayList)list.get(i);
/*  77 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  78 */       if (distinct) {
/*  79 */         sbf.append(row.get(0));
/*     */       } else
/*  81 */         sbf.append(row.get(1));
/*  82 */       sbf.append("</option>");
/*     */     }
/*     */     
/*  85 */     return sbf.toString();
/*     */   }
/*     */   
/*     */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*     */   {
/*  90 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*     */   }
/*     */   
/*     */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*     */   {
/*  95 */     return FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*     */   {
/* 101 */     Vector resourceIds = new Vector();
/*     */     
/* 103 */     if (EnterpriseUtil.isIt360MSPEdition())
/*     */     {
/*     */ 
/* 106 */       ManagedApplication.getChildIDs(resourceIds, request.getParameter("cust"));
/*     */     }
/*     */     
/*     */ 
/* 110 */     int i = 0;
/* 111 */     String uri = request.getRequestURI();
/* 112 */     String uri1 = "/jsp/MaintenanceTask.jsp";
/* 113 */     if (uri.equals(uri1)) {
/* 114 */       i = 1;
/*     */     }
/*     */     else {
/* 117 */       i = 0;
/*     */     }
/*     */     
/* 120 */     StringBuffer toreturn = new StringBuffer();
/*     */     
/* 122 */     for (int j = 0; j < singlechilmos.size(); j++)
/*     */     {
/* 124 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 125 */       String childresid = (String)singlerow.get(0);
/*     */       
/* 127 */       String childresname = (String)singlerow.get(1);
/* 128 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/* 130 */         childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*     */       }
/* 132 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*     */       
/* 134 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 135 */       String checkedvalue = "";
/* 136 */       ArrayList grouplist = new ArrayList();
/* 137 */       if (i == 1) {
/* 138 */         grouplist = ((AMActionForm)request.getAttribute("AMActionForm")).getPresentg();
/*     */       }
/*     */       else {
/* 141 */         grouplist = (ArrayList)request.getAttribute("selectedMonitor");
/*     */       }
/*     */       
/* 144 */       if ((grouplist != null) && (grouplist.size() != 0))
/*     */       {
/* 146 */         for (int z = 0; z < grouplist.size(); z++)
/*     */         {
/* 148 */           if (childresid.equals(((Properties)grouplist.get(z)).getProperty("value")))
/*     */           {
/* 150 */             checkedvalue = "checked";
/*     */           }
/*     */         }
/*     */       }
/* 154 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 155 */       int spacing = 0;
/* 156 */       if (level >= 1)
/*     */       {
/* 158 */         spacing = 20 * level;
/*     */       }
/*     */       
/* 161 */       if ((childtype.equals("HAI")) && (!CustomerManagementAPI.isSiteId(childresid)))
/*     */       {
/* 163 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 164 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 165 */         String checkbox = "<input type=\"checkbox\" name=\"resourcestypes\" " + checkedvalue + " id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"javascript:selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\" >";
/*     */         
/*     */ 
/* 168 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*     */         
/* 170 */         System.out.println("request.isUserInRole\"ADMIN\")" + request.isUserInRole("ADMIN"));
/* 171 */         String resourcelink = "";
/*     */         
/* 173 */         if ((resourceIds.contains(childresid)) || (!EnterpriseUtil.isIt360MSPEdition()))
/*     */         {
/* 175 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + " " + getTrimmedText(childresname, 45) + " ";
/*     */           
/* 177 */           toreturn.append("<tr " + tempbgcolor + " width=\"80%\" id=\"#monitor" + currentresourceidtree + "\" style=\"background: rgb(255, 255, 255) none repeat scroll 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; display: none;\">");
/* 178 */           toreturn.append("<td " + tempbgcolor + " width=\"2%\" >&nbsp;&nbsp;</td> ");
/* 179 */           toreturn.append("<td " + tempbgcolor + " width=\"68%\"  style=\"padding-left: " + spacing + "px !important;\">" + resourcelink + "</td>");
/* 180 */           toreturn.append("");
/* 181 */           toreturn.append("</tr>");
/*     */         }
/* 183 */         if (childmos.get(childresid + "") != null)
/*     */         {
/* 185 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 186 */           toreturn.append(toappend);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 192 */     return toreturn.toString();
/*     */   }
/*     */   
/*     */ 
/* 196 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/* 208 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/* 212 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 213 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 214 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 215 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/* 219 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/* 227 */     HttpSession session = null;
/*     */     
/*     */ 
/* 230 */     JspWriter out = null;
/* 231 */     Object page = this;
/* 232 */     JspWriter _jspx_out = null;
/* 233 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 237 */       response.setContentType("text/html;charset=UTF-8");
/* 238 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 240 */       _jspx_page_context = pageContext;
/* 241 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 242 */       ServletConfig config = pageContext.getServletConfig();
/* 243 */       session = pageContext.getSession();
/* 244 */       out = pageContext.getOut();
/* 245 */       _jspx_out = out;
/*     */       
/* 247 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 248 */       out.write(10);
/*     */       
/*     */ 
/* 251 */       HashMap map = new HashMap();
/* 252 */       String customfieldfilter = request.getParameter("customfieldfilter");
/* 253 */       boolean filterbycustomfield = false;
/* 254 */       HashMap<String, String> querycondition = new HashMap();
/* 255 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/* 256 */         if (Constants.isUserResourceEnabled()) {
/* 257 */           String loginUserid = Constants.getLoginUserid(request);
/* 258 */           querycondition.put("privelagedCondition", " and AM_HOLISTICAPPLICATION.HAID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ") ");
/*     */         } else {
/* 260 */           querycondition.put("privelagedCondition", " and " + DependantMOUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", ClientDBUtil.getAssociatedMonitorGroupsForOwner(request.getRemoteUser())) + " ");
/*     */         }
/*     */       }
/*     */       
/* 264 */       String querycon = "";
/* 265 */       String fieldsDataTable = "";
/* 266 */       String attributemgcondition = "";
/* 267 */       if ("true".equals(customfieldfilter)) {
/*     */         try
/*     */         {
/* 270 */           filterbycustomfield = true;
/* 271 */           String aliasname = request.getParameter("aliasname");
/* 272 */           String customValue = request.getParameter("customValue");
/* 273 */           querycondition = MyFields.customCondition(aliasname, customValue, null, false);
/* 274 */           querycon = (String)querycondition.get("qryCon");
/* 275 */           fieldsDataTable = (String)querycondition.get("groupTable");
/* 276 */           attributemgcondition = " and " + fieldsDataTable + ".RESOURCEID=AM_HOLISTICAPPLICATION.HAID ";
/* 277 */           fieldsDataTable = "," + fieldsDataTable;
/*     */         } catch (Exception ex) {
/* 279 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 286 */         map = ReportUtil.getMonitorGroups(filterbycustomfield, querycondition);
/* 287 */         ArrayList tempList = (ArrayList)map.get("grouplist");
/* 288 */         request.setAttribute("applications", tempList);
/*     */       } catch (Exception ex) {
/* 290 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 293 */       ArrayList resIDs = new ArrayList();
/* 294 */       Hashtable chilmos = (Hashtable)map.get("childlist");
/* 295 */       int totalmonitorcount = 0;
/* 296 */       ArrayList tempresourelist = new ArrayList();
/* 297 */       String mgResType = request.getParameter("mgResType");
/* 298 */       String resGroup = request.getParameter("ResGroup");
/* 299 */       String mgquery = null;
/* 300 */       ArrayList mgList = new ArrayList();
/* 301 */       boolean isLimit = false;
/* 302 */       HashMap extDeviceMap = null;
/* 303 */       HashMap site24x7List = null;
/* 304 */       if (Constants.isExtDeviceConfigured())
/*     */       {
/* 306 */         extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink();
/* 307 */         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 312 */       out.write(10);
/* 313 */       out.write(10);
/*     */       
/* 315 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 316 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 317 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */       
/* 319 */       _jspx_th_logic_005fiterate_005f0.setName("applications");
/*     */       
/* 321 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */       
/* 323 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*     */       
/* 325 */       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 326 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 327 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 328 */         ArrayList row = null;
/* 329 */         Integer i = null;
/* 330 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 331 */           out = _jspx_page_context.pushBody();
/* 332 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 333 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */         }
/* 335 */         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 336 */         i = (Integer)_jspx_page_context.findAttribute("i");
/*     */         for (;;) {
/* 338 */           out.write(10);
/*     */           
/* 340 */           if (!((String)row.get(6)).equals("orphaned"))
/*     */           {
/* 342 */             resIDs.add((String)row.get(6));
/*     */           }
/*     */           try
/*     */           {
/* 346 */             ArrayList srow = (ArrayList)chilmos.get((String)row.get(6) + "");
/* 347 */             if (srow != null)
/*     */             {
/* 349 */               for (int k = 0; k < srow.size(); k++)
/*     */               {
/* 351 */                 ArrayList mo = (ArrayList)srow.get(k);
/* 352 */                 if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*     */                 {
/* 354 */                   String cresid = ((String)mo.get(0) + "").trim();
/* 355 */                   resIDs.add(cresid);
/* 356 */                   String resourceType = ((String)mo.get(2) + "").trim();
/*     */                   
/* 358 */                   if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*     */                   {
/* 360 */                     tempresourelist.add(cresid);
/* 361 */                     totalmonitorcount++;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 369 */             ex.printStackTrace();
/*     */           }
/*     */           
/* 372 */           out.write(10);
/* 373 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 374 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 375 */           i = (Integer)_jspx_page_context.findAttribute("i");
/* 376 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 379 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 380 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 383 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 384 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */       }
/*     */       else {
/* 387 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 388 */         out.write("\n\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\">\n\tvar GlobalMGs=new Array();\n\t");
/*     */         
/* 390 */         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 391 */         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 392 */         _jspx_th_logic_005fiterate_005f1.setParent(null);
/*     */         
/* 394 */         _jspx_th_logic_005fiterate_005f1.setName("applications");
/*     */         
/* 396 */         _jspx_th_logic_005fiterate_005f1.setId("row");
/*     */         
/* 398 */         _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*     */         
/* 400 */         _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 401 */         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 402 */         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 403 */           ArrayList row = null;
/* 404 */           Integer i = null;
/* 405 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 406 */             out = _jspx_page_context.pushBody();
/* 407 */             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 408 */             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*     */           }
/* 410 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 411 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*     */           for (;;) {
/* 413 */             out.write("\n\t\tGlobalMGs[");
/* 414 */             out.print(i);
/* 415 */             out.write(93);
/* 416 */             out.write(61);
/* 417 */             out.write(39);
/* 418 */             out.print((String)row.get(6));
/* 419 */             out.write("';\n\t");
/* 420 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 421 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 422 */             i = (Integer)_jspx_page_context.findAttribute("i");
/* 423 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 426 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 427 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 430 */         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 431 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*     */         }
/*     */         else {
/* 434 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 435 */           out.write("\n\n</SCRIPT>\n\n\n\n\t");
/*     */           
/* 437 */           String sid = request.getParameter("sid");
/* 438 */           ArrayList grouplist = new ArrayList();
/* 439 */           if ((request.getParameter("sid") != null) && (!sid.equals("null")) && (!request.getParameter("sid").equals("undefined")))
/*     */           {
/*     */ 
/*     */             try
/*     */             {
/*     */ 
/* 445 */               ManagedApplication mo = new ManagedApplication();
/* 446 */               String query = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID='" + sid + "'";
/* 447 */               if (query != null)
/*     */               {
/* 449 */                 ArrayList selectedIDs = mo.getRows(query);
/* 450 */                 for (int i = 0; i < selectedIDs.size(); i++)
/*     */                 {
/*     */ 
/* 453 */                   String temp_res = (String)((ArrayList)selectedIDs.get(i)).get(0);
/* 454 */                   if (temp_res.contains(","))
/*     */                   {
/* 456 */                     String[] temp_resids = temp_res.split(",");
/* 457 */                     for (int n = 0; n < temp_resids.length; n++)
/*     */                     {
/* 459 */                       Properties props = new Properties();
/* 460 */                       props.setProperty("value", temp_resids[n]);
/* 461 */                       grouplist.add(props);
/*     */                     }
/*     */                   }
/*     */                   else
/*     */                   {
/* 466 */                     Properties props = new Properties();
/* 467 */                     props.setProperty("value", temp_res);
/* 468 */                     grouplist.add(props);
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 473 */               request.setAttribute("selectedMonitor", grouplist);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 477 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 484 */           String filterBSV = "";
/* 485 */           if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 487 */             filterBSV = " and HAID = " + Integer.parseInt(request.getParameter("cust"));
/*     */           }
/*     */           
/* 490 */           if ((mgResType != null) && (mgResType.equals("APP,MOM")))
/*     */           {
/* 492 */             isLimit = true;
/* 493 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP IN ('APP','MOM') " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 495 */           else if ((mgResType != null) && (mgResType.equals("URL")))
/*     */           {
/* 497 */             isLimit = true;
/* 498 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='URL' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 500 */           else if ((mgResType != null) && (mgResType.equals("DBS")))
/*     */           {
/* 502 */             isLimit = true;
/* 503 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='DBS' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 505 */           else if ((mgResType != null) && (mgResType.equals("SYS")))
/*     */           {
/* 507 */             isLimit = true;
/* 508 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='SYS' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 510 */           else if ((mgResType != null) && (mgResType.equals("TM")))
/*     */           {
/* 512 */             isLimit = true;
/* 513 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='TM' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 515 */           else if ((mgResType != null) && (mgResType.equals("Custom")))
/*     */           {
/* 517 */             isLimit = true;
/* 518 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='" + resGroup + "' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 520 */           else if ((mgResType != null) && (mgResType.equals("SAP")))
/*     */           {
/* 522 */             isLimit = true;
/* 523 */             mgquery = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + fieldsDataTable + "  where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='ERP' " + attributemgcondition + " " + querycon + filterBSV + " group BY HAID";
/*     */           }
/* 525 */           if (mgResType != null)
/*     */           {
/* 527 */             ManagedApplication mo = new ManagedApplication();
/* 528 */             ArrayList mgrows = mo.getRows(mgquery);
/* 529 */             if (mgrows.size() > 0)
/*     */             {
/*     */               try
/*     */               {
/* 533 */                 for (int l = 0; l < mgrows.size(); l++)
/*     */                 {
/* 535 */                   ArrayList mgrow = (ArrayList)mgrows.get(l);
/* 536 */                   String temphaid = mo.getTOPLevelMG((String)mgrow.get(0));
/* 537 */                   if (!mgList.contains(temphaid))
/*     */                   {
/* 539 */                     mgList.add(temphaid);
/*     */                   }
/*     */                 }
/*     */               } catch (Exception ex) {
/* 543 */                 ex.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 550 */           boolean nothing = true;
/*     */           
/* 552 */           out.write("\n\n\n\t<table width=\"70%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n\t\t<tr height=\"22\">\n\t\t\t<td class=\"light-blue-bg\" colspan=\"7\" width=\"60%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>");
/* 553 */           out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 554 */           out.write("</b></td>\n\n\t\t\t<td  align=\"center\"  class=\"light-blue-bg\" ><a href=\"javascript:void(0);\" class=\"staticlinks\" onClick=\"javascript:toggleallDivs('show');\"><div id=\"showall\" style=\"display: inline;\" >[");
/* 555 */           out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 556 */           out.write("]</div><div id=\"hideall\" style=\"display: none;\">[");
/* 557 */           out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 558 */           out.write("]</div></a>&nbsp; &nbsp; </td>\n\t\t</tr>\n\t</table>\n\n\t<table width=\"70%\" id=\"allMonitorGroups\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\" >\n\n\t");
/*     */           
/* 560 */           IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 561 */           _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 562 */           _jspx_th_logic_005fiterate_005f2.setParent(null);
/*     */           
/* 564 */           _jspx_th_logic_005fiterate_005f2.setName("applications");
/*     */           
/* 566 */           _jspx_th_logic_005fiterate_005f2.setId("row");
/*     */           
/* 568 */           _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/*     */           
/* 570 */           _jspx_th_logic_005fiterate_005f2.setIndexId("i");
/* 571 */           int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 572 */           if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 573 */             ArrayList row = null;
/* 574 */             Integer i = null;
/* 575 */             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 576 */               out = _jspx_page_context.pushBody();
/* 577 */               _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 578 */               _jspx_th_logic_005fiterate_005f2.doInitBody();
/*     */             }
/* 580 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 581 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*     */             for (;;) {
/* 583 */               out.write(10);
/* 584 */               out.write(9);
/*     */               
/* 586 */               String MGType = row.get(2) != null ? (String)row.get(2) : "";
/*     */               
/* 588 */               if ((MGType.equals("0")) || ((EnterpriseUtil.isIt360MSPEdition()) && (MGType.equals("1"))))
/*     */               {
/*     */ 
/* 591 */                 String restitle = row.get(8) != null ? (String)row.get(0) + "-UnManaged" : (String)row.get(0);
/* 592 */                 String status = row.get(8) != null ? "disabledtext" : "staticlinks";
/* 593 */                 String resIdTOCheck = "-1";
/* 594 */                 String checkedvalue = "";
/*     */                 
/*     */                 try
/*     */                 {
/* 598 */                   resIdTOCheck = (String)row.get(6);
/* 599 */                   for (int z = 0; z < grouplist.size(); z++)
/*     */                   {
/* 601 */                     String targetID = ((Properties)grouplist.get(z)).getProperty("value");
/*     */                     
/* 603 */                     if (resIdTOCheck.equals(targetID))
/*     */                     {
/* 605 */                       checkedvalue = "checked";
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 610 */                   e.printStackTrace();
/*     */                 }
/*     */                 
/*     */ 
/* 614 */                 Vector resourceIds = new Vector();
/* 615 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*     */                 {
/* 617 */                   resourceIds = CustomerManagementAPI.filterSiteBasedResourceIds(request.getParameter("cust"), resourceIds);
/*     */                 }
/*     */                 
/* 620 */                 if ((!resIdTOCheck.equals("orphaned")) && ((!isLimit) || ((isLimit) && (mgList.contains(resIdTOCheck)))))
/*     */                 {
/* 622 */                   nothing = false;
/* 623 */                   if (((request.getParameter("cust") != null) && (request.getParameter("cust").equals(resIdTOCheck))) || (!EnterpriseUtil.isIt360MSPEdition()) || ((EnterpriseUtil.isIt360MSPEdition()) && ("true".equals(customfieldfilter))))
/*     */                   {
/* 625 */                     String resDisplay = (String)row.get(1);
/* 626 */                     if ((EnterpriseUtil.isAdminServer()) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */                     {
/* 628 */                       resDisplay = resDisplay + "_" + CommDBUtil.getManagedServerNameWithPort(resIdTOCheck);
/*     */                     }
/*     */                     
/* 631 */                     out.write("\n\n\t\t\t<tr width=\"80%\" class=\"whitegrayrightalign\" height=\"25\">\n\t\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"3%\">\n\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor");
/* 632 */                     out.print(resIdTOCheck);
/* 633 */                     out.write("'),toggleTreeImage('");
/* 634 */                     out.print(resIdTOCheck);
/* 635 */                     out.write("');\">\n\t\t\t\t\t\t<div id=\"monitorShow");
/* 636 */                     out.print(resIdTOCheck);
/* 637 */                     out.write("\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div>\n\t\t\t\t\t\t<div id=\"monitorHide");
/* 638 */                     out.print(resIdTOCheck);
/* 639 */                     out.write("\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div>\n\t\t\t\t\t</a>\n\t\t\t\t</td>\n\t\t\t\t<td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"67%\" title=\"");
/* 640 */                     out.print(restitle);
/* 641 */                     out.write("\"  style=\"padding-left: 0px;\">\n\t\t\t\t\t<input  type=\"checkbox\" name=\"resourcestypes\" value=\"");
/* 642 */                     out.print(resIdTOCheck);
/* 643 */                     out.write(34);
/* 644 */                     out.write(32);
/* 645 */                     out.print(checkedvalue);
/* 646 */                     out.write(" id=\"");
/* 647 */                     out.print(resIdTOCheck);
/* 648 */                     out.write("\"  onclick=\"selectAllChildCKbs('");
/* 649 */                     out.print(resIdTOCheck);
/* 650 */                     out.write("',this,this.form),deselectParentCKbs('");
/* 651 */                     out.print(resIdTOCheck);
/* 652 */                     out.write("',this,this.form)\">");
/* 653 */                     out.print(resDisplay);
/* 654 */                     out.write("\n\t\t\t\t</td>\n\n\t\t\t\t<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t\t\t\t");
/*     */                     
/* 656 */                     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 657 */                     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 658 */                     _jspx_th_c_005fset_005f0.setParent(_jspx_th_logic_005fiterate_005f2);
/*     */                     
/* 660 */                     _jspx_th_c_005fset_005f0.setVar("key");
/* 661 */                     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 662 */                     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 663 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 664 */                         out = _jspx_page_context.pushBody();
/* 665 */                         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 666 */                         _jspx_th_c_005fset_005f0.doInitBody();
/*     */                       }
/*     */                       for (;;) {
/* 669 */                         out.write(32);
/* 670 */                         out.print(row.get(6) + "#" + "17" + "#" + "MESSAGE");
/* 671 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 672 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/* 675 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 676 */                         out = _jspx_page_context.popBody();
/*     */                       }
/*     */                     }
/* 679 */                     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 680 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*     */                     }
/*     */                     
/* 683 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 684 */                     out.write("\n\t\t\t</tr>\n\t\t");
/*     */                   }
/*     */                 }
/*     */                 
/* 688 */                 if ((chilmos.get(resIdTOCheck + "") != null) && ((!isLimit) || ((isLimit) && (mgList.contains(resIdTOCheck)))))
/*     */                 {
/*     */ 
/* 691 */                   ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 692 */                   Hashtable availhealth = new Hashtable();
/* 693 */                   boolean displayChildNodes = true;
/* 694 */                   if ((EnterpriseUtil.isIt360MSPEdition()) && (!resIdTOCheck.equals(request.getParameter("cust"))))
/*     */                   {
/* 696 */                     displayChildNodes = false;
/*     */                   }
/* 698 */                   if (displayChildNodes)
/*     */                   {
/* 700 */                     String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", resIdTOCheck + "", chilmos, availhealth, 1, request, extDeviceMap, site24x7List);
/* 701 */                     out.println(toappend);
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 706 */               out.write(10);
/* 707 */               out.write(9);
/* 708 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 709 */               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 710 */               i = (Integer)_jspx_page_context.findAttribute("i");
/* 711 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 714 */             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 715 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 718 */           if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 719 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/*     */           }
/*     */           else {
/* 722 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 723 */             out.write(10);
/* 724 */             out.write(9);
/*     */             
/* 726 */             if (nothing)
/*     */             {
/*     */ 
/* 729 */               out.write("\n        <tr>\n         <td>\n          <span class='bodytext'>&nbsp;");
/* 730 */               out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitorgroups.text"));
/* 731 */               out.write("</span>\t             \n           </td>\n        </tr>\t\t\t\n\t  ");
/*     */             }
/*     */             
/*     */ 
/* 735 */             out.write("\n</table>\n");
/*     */           }
/* 737 */         } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 738 */         out = _jspx_out;
/* 739 */         if ((out != null) && (out.getBufferSize() != 0))
/* 740 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 741 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 744 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScheduleReportsMGTree_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */