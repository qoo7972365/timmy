/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.LoadTime;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.GetAttributeTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ManagerLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public String getHelpLink(String key)
/*      */   {
/*   36 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   37 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   40 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   41 */       com.adventnet.appmanager.db.AMConnectionPool cp = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/*   42 */       set = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(query);
/*   43 */       if (set.next())
/*      */       {
/*   45 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   48 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   54 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*   71 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   64 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   73 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   79 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(5);
/*   80 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   81 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*   82 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   83 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   84 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  109 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  113 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  130 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  131 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  135 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  138 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  139 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  140 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  141 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  142 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  143 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  144 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  145 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  146 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  147 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  148 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  149 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.release();
/*  150 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  151 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  158 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  161 */     JspWriter out = null;
/*  162 */     Object page = this;
/*  163 */     JspWriter _jspx_out = null;
/*  164 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  168 */       response.setContentType("text/html");
/*  169 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  171 */       _jspx_page_context = pageContext;
/*  172 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  173 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  174 */       session = pageContext.getSession();
/*  175 */       out = pageContext.getOut();
/*  176 */       _jspx_out = out;
/*      */       
/*  178 */       out.write("<!DOCTYPE html>\n");
/*  179 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  180 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/*  181 */       java.util.Properties applications = null;
/*  182 */       synchronized (application) {
/*  183 */         applications = (java.util.Properties)_jspx_page_context.getAttribute("applications", 4);
/*  184 */         if (applications == null) {
/*  185 */           applications = new java.util.Properties();
/*  186 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  189 */       out.write("    \n<html>\n<head>\n<title>");
/*  190 */       out.print(FormatUtil.getString("am.webclient.MGAM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  191 */       out.write(32);
/*  192 */       out.write(45);
/*  193 */       out.write(32);
/*  194 */       if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */         return;
/*  196 */       out.write("</title>\n");
/*  197 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  198 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  200 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  201 */       out.write(10);
/*  202 */       out.write(10);
/*  203 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  204 */       out.write(10);
/*  205 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  206 */       out.print(request.getContextPath());
/*  207 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  208 */       out.print(request.getContextPath());
/*  209 */       out.write("'); //No I18N\n</script>\n");
/*  210 */       if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  211 */         out.write("<script src='");
/*  212 */         out.print(request.getContextPath());
/*  213 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  215 */       out.write(10);
/*  216 */       response.setContentType("text/html;charset=UTF-8");
/*  217 */       out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/*  218 */       out.print(session.getAttribute("customreloadperiod"));
/*  219 */       out.write("\">\n</head>\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad();\">\n");
/*      */       
/*  221 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  222 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  223 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*  224 */       String uri = (String)request.getAttribute("uri");
/*  225 */       String searchQuery = request.getParameter("query");
/*  226 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  227 */         searchQuery = "";
/*      */       }
/*  229 */       String responseTimeMessage = FormatUtil.getString("webclient.server.responsetime");
/*  230 */       String systemTimeMessage = FormatUtil.getString("webclient.server.systemtime");
/*  231 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  233 */         responseTimeMessage = FormatUtil.getString("webclient.server.responsetime.managedserver");
/*  234 */         systemTimeMessage = FormatUtil.getString("webclient.server.systemtime.managedserver");
/*      */       }
/*      */       
/*  237 */       out.write(10);
/*      */       
/*  239 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  240 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  241 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  243 */       _jspx_th_c_005fif_005f0.setTest("${selectedscheme == 'slim'}");
/*  244 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  245 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  247 */           out.write(10);
/*      */           
/*  249 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*  250 */           request.setAttribute("Layout", "slim");
/*      */           
/*  252 */           out.write(10);
/*  253 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  254 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  258 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  259 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  262 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  263 */         out.write(10);
/*      */         
/*  265 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  266 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  267 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  268 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  269 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  271 */             out.write("\n    ");
/*      */             
/*  273 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  274 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  275 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  277 */             _jspx_th_c_005fwhen_005f0.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  278 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  279 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  281 */                 out.write("\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr> \n              ");
/*  282 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  283 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  284 */                 out.write(10);
/*  285 */                 out.write(10);
/*  286 */                 out.write(10);
/*  287 */                 out.write(32);
/*      */                 
/*  289 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  290 */                 if (helpKey == null)
/*      */                 {
/*  292 */                   String tileName = request.getParameter("TileName");
/*  293 */                   if (tileName != null)
/*      */                   {
/*  295 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  297 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  299 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  301 */                       helpKey = "Send E-mail";
/*      */                     }
/*  303 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  305 */                       helpKey = "Send SMS";
/*      */                     }
/*  307 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  309 */                       helpKey = "Execute Program";
/*      */                     }
/*  311 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  313 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  318 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  319 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  321 */                 java.util.Enumeration en = request.getParameterNames();
/*  322 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  323 */                 while (en.hasMoreElements()) {
/*  324 */                   String reqKey = (String)en.nextElement();
/*  325 */                   String reqValue = request.getParameter(reqKey);
/*  326 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  330 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  332 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*  334 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  336 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  340 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  344 */                 out.println("\";");
/*      */                 
/*  346 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  348 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  349 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  350 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  352 */                 _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  353 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  354 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/*  356 */                     out.write(10);
/*  357 */                     out.write(10);
/*      */                     
/*  359 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  361 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  363 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  364 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  365 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  366 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                         return;
/*  368 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  369 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  370 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  371 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  372 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  373 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  374 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  375 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  376 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  377 */                       out.print(getHelpLink(helpKey));
/*  378 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  379 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  380 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  381 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  382 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  384 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  385 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  386 */                       out.write("</a>");
/*  387 */                       if (request.getRemoteUser() != null)
/*  388 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  389 */                         out.println("&nbsp;");
/*  390 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  392 */                     out.write(32);
/*  393 */                     out.write(32);
/*  394 */                     out.write(10);
/*      */                     
/*  396 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  398 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  400 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  401 */                       out.write(10);
/*  402 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                         return;
/*  404 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  405 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  406 */                       out.write("</a></td>\n  ");
/*      */                       
/*  408 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  409 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  410 */                       _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  412 */                       _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  413 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  414 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/*  416 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  417 */                           if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                             return;
/*  419 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  420 */                           out.write("\n  </a></td>\n  ");
/*  421 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  422 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  426 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  427 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/*  430 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  431 */                       out.write(10);
/*  432 */                       out.write(32);
/*  433 */                       out.write(32);
/*      */                       
/*  435 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  436 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  437 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  439 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  440 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  441 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  443 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  445 */                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  446 */                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  447 */                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  448 */                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  449 */                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                             for (;;) {
/*  451 */                               out.write("\n   \t\t ");
/*      */                               
/*  453 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  454 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  455 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*  457 */                               _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  458 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  459 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/*  461 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  462 */                                   if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                                     return;
/*  464 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  465 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  466 */                                   out.write("</a>\n   \t\t ");
/*  467 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  468 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  472 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  473 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/*  476 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  477 */                               out.write("\n   \t \t ");
/*      */                               
/*  479 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  480 */                               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  481 */                               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/*  482 */                               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  483 */                               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                 for (;;) {
/*  485 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  486 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/*  488 */                                   out.write("\" class=\"staticlinks\">");
/*  489 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  490 */                                   out.write("</a>\n   \t\t ");
/*  491 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  492 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  496 */                               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  497 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                               }
/*      */                               
/*  500 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  501 */                               out.write("\n   \t");
/*  502 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  503 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  507 */                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  508 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                           }
/*      */                           
/*  511 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  512 */                           out.write("\n\t</td> \n  ");
/*  513 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  514 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  518 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  519 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  522 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  523 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  524 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  525 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  526 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  527 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  528 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  529 */                       out.write("</a></td>\n\t");
/*      */                       
/*  531 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  532 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  533 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  535 */                       _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  536 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  537 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  539 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  540 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  541 */                           out.write("</a></td>\n\t");
/*  542 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  543 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  547 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  548 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  551 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  552 */                       out.write(10);
/*  553 */                       out.write(9);
/*      */                       
/*  555 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  556 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  557 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  559 */                       _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  560 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  561 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  563 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  564 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  565 */                           out.write("</a></td>\n\t");
/*  566 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  567 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  571 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  572 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  575 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  576 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  577 */                       out.print(getHelpLink(helpKey));
/*  578 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  579 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  580 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  581 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  582 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  584 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  585 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  586 */                       out.write("</a>");
/*  587 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  589 */                         out.write("&nbsp;");
/*  590 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  591 */                         out.write("\n    \t\t\t\t\t");
/*  592 */                       } else { out.println("&nbsp;"); }
/*  593 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  595 */                     out.write(10);
/*  596 */                     out.write(32);
/*  597 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  598 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  602 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  603 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/*  606 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  607 */                 out.write(" \n\n\t");
/*  608 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()))
/*      */                 {
/*  610 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  612 */                 out.write("\n\n</table>\n");
/*  613 */                 out.write("\n        </tr>\n    <tr>\n    <td valign=\"top\"> \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      \n        ");
/*      */                 
/*  615 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  616 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  617 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  619 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/*  621 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  622 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  623 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  624 */                   String msg = null;
/*  625 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  626 */                     out = _jspx_page_context.pushBody();
/*  627 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  628 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/*  630 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  632 */                     out.write(" \n        <tr> \n          <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" height=\"28\" class=\"message\">");
/*  633 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/*  635 */                     out.write("</td>\n              </tr>\n            </table>\n            <br></td>\n        </tr>\n        ");
/*  636 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  637 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  638 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  641 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  642 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  645 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  646 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/*  649 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  650 */                 out.write(32);
/*      */                 
/*  652 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  653 */                 _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  654 */                 _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  656 */                 _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  657 */                 int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  658 */                 if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                   for (;;) {
/*  660 */                     out.write(" \n        <tr> \n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                     
/*  662 */                     MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  663 */                     _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  664 */                     _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                     
/*  666 */                     _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                     
/*  668 */                     _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  669 */                     int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  670 */                     if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  671 */                       String msg = null;
/*  672 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  673 */                         out = _jspx_page_context.pushBody();
/*  674 */                         _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  675 */                         _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                       }
/*  677 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  679 */                         out.write(" \n                  ");
/*  680 */                         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                           return;
/*  682 */                         out.write("<br>\n                  ");
/*  683 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  684 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  685 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  688 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  689 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  692 */                     if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  693 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                     }
/*      */                     
/*  696 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  697 */                     out.write(" </td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*  698 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  699 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  703 */                 if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  704 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                 }
/*      */                 
/*  707 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  708 */                 out.write(" \n        <tr> \n          <td  valign=\"top\" class=\"tdindent\" height=\"5\"> ");
/*  709 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  711 */                 out.write("</td>\n        </tr>\n\n        <tr> \n          <td height=\"20%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n        <tr> \n          ");
/*      */                 
/*      */ 
/*  714 */                 request.setAttribute("systime", new java.util.Date());
/*      */                 try
/*      */                 {
/*  717 */                   long starttime = ((Long)request.getAttribute("starttime")).longValue();
/*  718 */                   long pageloadtime = System.currentTimeMillis() - starttime;
/*  719 */                   if (pageloadtime > 60000L)
/*      */                   {
/*  721 */                     String logmsg = "PAGELOADTIME for " + uri + " " + request.getQueryString() + " is " + pageloadtime;
/*  722 */                     com.adventnet.appmanager.util.Constants.logSlowPage(logmsg);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception exc) {}
/*  726 */                 out.write("\n          <td height=\"20%\" class=\"tdindent\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> \n                <td><span class=\"footer\">");
/*  727 */                 out.print(responseTimeMessage);
/*  728 */                 out.write(" <span class=\"bodytextbold\">");
/*  729 */                 if (_jspx_meth_am_005ftimestamp_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  731 */                 out.write("</span> \n                 ");
/*  732 */                 if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  734 */                 out.write(".</span></td>\n                <td align=\"right\"><span class=\"footer\">");
/*  735 */                 out.print(systemTimeMessage);
/*  736 */                 out.write(32);
/*  737 */                 out.write(58);
/*  738 */                 out.write(32);
/*  739 */                 if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  741 */                 out.write("</span>&nbsp;&nbsp;</td>\n              </tr>\n            </table></td>\n        </tr>\n      </table></td>          \n      </tr>\n      </table>\n    ");
/*  742 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  743 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  747 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  748 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  751 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  752 */             out.write(10);
/*  753 */             out.write(32);
/*  754 */             out.write(32);
/*      */             
/*  756 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  757 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  758 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f0);
/*  759 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  760 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  762 */                 out.write(10);
/*  763 */                 out.write(10);
/*  764 */                 if (!OEMUtil.isRemove("hide.header")) {
/*  765 */                   out.write(10);
/*  766 */                   if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/*  768 */                   out.write(32);
/*  769 */                   out.write(10);
/*      */                 } else {
/*  771 */                   out.write("<form></form>");
/*      */                 }
/*  773 */                 out.write("\n\n\n\n\n<div id=\"userAreaContainerDiv\" style=\"margin:0px;width:100%;padding-top:10px\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    \n\n\n\n    <td valign=\"top\"> \n\t  \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <!--%@ include file=\"/jsp/includes/CreateApplicationWizard.jspf\" %-->\n        <!--html:messages id=\"msg\" message=\"false\"> \n        <//c:if test=\"${empty firstrow}\">\n        \n           <tr> \n\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t              <tr> \n                <td width=\"5%\" align=\"center\">\n                <img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n                </td>\n                <td width=\"95%\" class=\"message\">        \n        \n          <//c:set var=\"firstrow\" value=\"true\"/>\n        <///c:if>\n\n              <//bean:write name=\"msg\"  filter=\"false\"  /><br>\n           \n        <///html:messages> \n");
/*  774 */                 out.write("        <//c:if test=\"${!empty firstrow}\">\n        </td>\n\t              </tr>\n\t            </table></td>\n        </tr-->\n        <///c:if> <//logic:messagesPresent message=\"true\"> \n        <!--tr> \n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\"> <//html:messages id=\"msg\" message=\"true\"> \n                  <//bean:write name=\"msg\" filter=\"false\" /><br>\n                  <///html:messages> </td>\n              </tr>\n            </table></td>\n        </tr-->\n        <///logic:messagesPresent> \n        <tr> \n\n\n\n\n\n ");
/*      */                 
/*  776 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  777 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/*  778 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/*  780 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/*  782 */                 _jspx_th_html_005fmessages_005f2.setMessage("false");
/*  783 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/*  784 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/*  785 */                   String msg = null;
/*  786 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  787 */                     out = _jspx_page_context.pushBody();
/*  788 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/*  789 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/*  791 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  793 */                     out.write("\n        <tr>\n          <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" height=\"28\" class=\"message\">");
/*  794 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/*  796 */                     out.write("</td>\n              </tr>\n            </table>\n            <br></td>\n        </tr>\n        ");
/*  797 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/*  798 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  799 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  802 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  803 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  806 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/*  807 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/*  810 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/*  811 */                 out.write(32);
/*      */                 
/*  813 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  814 */                 _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  815 */                 _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/*  817 */                 _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  818 */                 int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  819 */                 if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                   for (;;) {
/*  821 */                     out.write("\n        <tr>\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                     
/*  823 */                     MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  824 */                     _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/*  825 */                     _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                     
/*  827 */                     _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                     
/*  829 */                     _jspx_th_html_005fmessages_005f3.setMessage("true");
/*  830 */                     int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/*  831 */                     if (_jspx_eval_html_005fmessages_005f3 != 0) {
/*  832 */                       String msg = null;
/*  833 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  834 */                         out = _jspx_page_context.pushBody();
/*  835 */                         _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/*  836 */                         _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                       }
/*  838 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  840 */                         out.write("\n                  ");
/*  841 */                         if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                           return;
/*  843 */                         out.write("<br>\n                  ");
/*  844 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/*  845 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  846 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  849 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  850 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  853 */                     if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/*  854 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                     }
/*      */                     
/*  857 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/*  858 */                     out.write(" </td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*  859 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/*  860 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  864 */                 if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/*  865 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                 }
/*      */                 
/*  868 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  869 */                 out.write("\n\n\n\n\n          <td height=\"90%\" valign=\"top\" class=\"tdindent\"> ");
/*  870 */                 if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/*  872 */                 out.write("</td>\n        </tr>\n        <tr> \n          <td height=\"20%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n          ");
/*      */                 
/*  874 */                 request.setAttribute("systime", new java.util.Date());
/*      */                 
/*  876 */                 out.write("\n      </table></td>\n  </tr>\n</table>\n</div><!-- userAreaContainerDiv ends -->\n");
/*  877 */                 if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/*  879 */                 out.write(10);
/*  880 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  881 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  885 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  886 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/*  889 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  890 */             out.write(10);
/*  891 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  892 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  896 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  897 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/*  900 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  901 */           out.write(10);
/*  902 */           out.write(10);
/*  903 */           out.write(10);
/*  904 */           out.write(10);
/*      */           
/*  906 */           if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  911 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/*  912 */             out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/*  913 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/*  917 */           out.write("\n</body>\n</html>\n\n");
/*      */         }
/*  919 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  920 */         out = _jspx_out;
/*  921 */         if ((out != null) && (out.getBufferSize() != 0))
/*  922 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  923 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  926 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  932 */     PageContext pageContext = _jspx_page_context;
/*  933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  935 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/*  936 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/*  937 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/*  939 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/*  940 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/*  941 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/*  942 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/*  943 */       return true;
/*      */     }
/*  945 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/*  946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  951 */     PageContext pageContext = _jspx_page_context;
/*  952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  954 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  955 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  956 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  958 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  960 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  961 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  962 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  963 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  964 */       return true;
/*      */     }
/*  966 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  972 */     PageContext pageContext = _jspx_page_context;
/*  973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  975 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/*  976 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  977 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  979 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/*  981 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/*  982 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/*  984 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  985 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/*  987 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/*  988 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  989 */             return true;
/*  990 */           out.write("\"\" class=\"staticlinks\">");
/*  991 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  992 */             return true;
/*  993 */           out.write("</a></td>\n");
/*  994 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  995 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  999 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1000 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1003 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1004 */         out = _jspx_page_context.popBody(); }
/* 1005 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1007 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1008 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1015 */     PageContext pageContext = _jspx_page_context;
/* 1016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1018 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1019 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1020 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1022 */     _jspx_th_c_005fout_005f1.setValue("${tab.TABLINK}");
/* 1023 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1024 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1025 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1026 */       return true;
/*      */     }
/* 1028 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1034 */     PageContext pageContext = _jspx_page_context;
/* 1035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1037 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1038 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1039 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1041 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABNAME}");
/* 1042 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1043 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1044 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1045 */       return true;
/*      */     }
/* 1047 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1053 */     PageContext pageContext = _jspx_page_context;
/* 1054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1056 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1057 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1058 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1060 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1062 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1063 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1065 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1066 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1068 */           out.write(10);
/* 1069 */           out.write(10);
/* 1070 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1071 */             return true;
/* 1072 */           out.write(10);
/* 1073 */           out.write(10);
/* 1074 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1075 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1079 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1080 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1083 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1084 */         out = _jspx_page_context.popBody(); }
/* 1085 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1087 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1088 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1095 */     PageContext pageContext = _jspx_page_context;
/* 1096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1098 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1099 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1100 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1101 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1102 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1104 */         out.write(10);
/* 1105 */         out.write(10);
/* 1106 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1107 */           return true;
/* 1108 */         out.write(10);
/* 1109 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1110 */           return true;
/* 1111 */         out.write(10);
/* 1112 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1113 */           return true;
/* 1114 */         out.write("\n\n\n\n\n");
/* 1115 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1116 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1120 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1121 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1122 */       return true;
/*      */     }
/* 1124 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1130 */     PageContext pageContext = _jspx_page_context;
/* 1131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1133 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1134 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1135 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1137 */     _jspx_th_c_005fwhen_005f1.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1138 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1139 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1141 */         out.write(10);
/* 1142 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1143 */           return true;
/* 1144 */         out.write(10);
/* 1145 */         out.write(32);
/* 1146 */         out.write(32);
/* 1147 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1148 */           return true;
/* 1149 */         out.write(10);
/* 1150 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1155 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1156 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1157 */       return true;
/*      */     }
/* 1159 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1165 */     PageContext pageContext = _jspx_page_context;
/* 1166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1168 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1169 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1170 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1172 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1173 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1174 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1176 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1177 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1178 */           return true;
/* 1179 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1180 */           return true;
/* 1181 */         out.write("\n  </a></td>\n  ");
/* 1182 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1183 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1187 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1188 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1189 */       return true;
/*      */     }
/* 1191 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1197 */     PageContext pageContext = _jspx_page_context;
/* 1198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1200 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1201 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1202 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1203 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1204 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1206 */         out.write("\n  \t\t");
/* 1207 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1208 */           return true;
/* 1209 */         out.write("\n  \t\t");
/* 1210 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1211 */           return true;
/* 1212 */         out.write("\n  \t\t");
/* 1213 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1214 */           return true;
/* 1215 */         out.write("\n  \t");
/* 1216 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1221 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1222 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1223 */       return true;
/*      */     }
/* 1225 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1231 */     PageContext pageContext = _jspx_page_context;
/* 1232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1234 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1235 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1236 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1238 */     _jspx_th_c_005fwhen_005f2.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1239 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1240 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1242 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1243 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1248 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1249 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1250 */       return true;
/*      */     }
/* 1252 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1258 */     PageContext pageContext = _jspx_page_context;
/* 1259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1261 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1262 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1263 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1265 */     _jspx_th_c_005fwhen_005f3.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1266 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1267 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1269 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1270 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1271 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1275 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1276 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1277 */       return true;
/*      */     }
/* 1279 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1280 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1285 */     PageContext pageContext = _jspx_page_context;
/* 1286 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1288 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1289 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1290 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1291 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1292 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1294 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1295 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1296 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1300 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1301 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1302 */       return true;
/*      */     }
/* 1304 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1310 */     PageContext pageContext = _jspx_page_context;
/* 1311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1313 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1314 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1315 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 1317 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 1318 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1319 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1320 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1321 */       return true;
/*      */     }
/* 1323 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1324 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1329 */     PageContext pageContext = _jspx_page_context;
/* 1330 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1332 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1333 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1334 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1336 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1337 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1338 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1340 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1341 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1342 */           return true;
/* 1343 */         out.write("\n\t</td> \n  ");
/* 1344 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1349 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1350 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1351 */       return true;
/*      */     }
/* 1353 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1359 */     PageContext pageContext = _jspx_page_context;
/* 1360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1362 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1363 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1364 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 1365 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1366 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1368 */         out.write("\n   \t\t ");
/* 1369 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1370 */           return true;
/* 1371 */         out.write("\n   \t \t ");
/* 1372 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1373 */           return true;
/* 1374 */         out.write("\n   \t");
/* 1375 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1376 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1380 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1381 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1382 */       return true;
/*      */     }
/* 1384 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1390 */     PageContext pageContext = _jspx_page_context;
/* 1391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1393 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1394 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1395 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1397 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1398 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1399 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1401 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1402 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1403 */           return true;
/* 1404 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1405 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1406 */           return true;
/* 1407 */         out.write("</a>\n   \t\t ");
/* 1408 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1413 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1414 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1415 */       return true;
/*      */     }
/* 1417 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1423 */     PageContext pageContext = _jspx_page_context;
/* 1424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1426 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1427 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1428 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1430 */     _jspx_th_c_005fout_005f4.setValue("${globalconfig['defaultmonitorsview']}");
/* 1431 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1432 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1433 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1434 */       return true;
/*      */     }
/* 1436 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1442 */     PageContext pageContext = _jspx_page_context;
/* 1443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1445 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1446 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1447 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1449 */     _jspx_th_c_005fout_005f5.setValue("${tab.TABNAME}");
/* 1450 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1451 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1452 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1453 */       return true;
/*      */     }
/* 1455 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1461 */     PageContext pageContext = _jspx_page_context;
/* 1462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1464 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1465 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1466 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1467 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1468 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1470 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1471 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1472 */           return true;
/* 1473 */         out.write("\" class=\"staticlinks\">");
/* 1474 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1475 */           return true;
/* 1476 */         out.write("</a>\n   \t\t ");
/* 1477 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1478 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1482 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1483 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1484 */       return true;
/*      */     }
/* 1486 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1487 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1492 */     PageContext pageContext = _jspx_page_context;
/* 1493 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1495 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1496 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1497 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1499 */     _jspx_th_c_005fout_005f6.setValue("${globalconfig['defaultmonitorsview']}");
/* 1500 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1501 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1502 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1503 */       return true;
/*      */     }
/* 1505 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1511 */     PageContext pageContext = _jspx_page_context;
/* 1512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1514 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1515 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1516 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1518 */     _jspx_th_c_005fout_005f7.setValue("${tab.TABNAME}");
/* 1519 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1520 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1521 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1522 */       return true;
/*      */     }
/* 1524 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1530 */     PageContext pageContext = _jspx_page_context;
/* 1531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1533 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1534 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1535 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1537 */     _jspx_th_c_005fwhen_005f5.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 1538 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1539 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1541 */         out.write(10);
/* 1542 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1543 */           return true;
/* 1544 */         out.write(10);
/* 1545 */         out.write(9);
/* 1546 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1547 */           return true;
/* 1548 */         out.write(10);
/* 1549 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1550 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1554 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1555 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1556 */       return true;
/*      */     }
/* 1558 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1564 */     PageContext pageContext = _jspx_page_context;
/* 1565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1567 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1568 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1569 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1571 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1572 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1573 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1575 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1576 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1577 */           return true;
/* 1578 */         out.write("</a></td>\n\t");
/* 1579 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1580 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1584 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1585 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1586 */       return true;
/*      */     }
/* 1588 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1594 */     PageContext pageContext = _jspx_page_context;
/* 1595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1597 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1598 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1599 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1601 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 1602 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1603 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1604 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1605 */       return true;
/*      */     }
/* 1607 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1613 */     PageContext pageContext = _jspx_page_context;
/* 1614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1616 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1617 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1618 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1620 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 1621 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1622 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1624 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1625 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1626 */           return true;
/* 1627 */         out.write("</a></td>\n\t");
/* 1628 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1629 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1633 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1634 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1635 */       return true;
/*      */     }
/* 1637 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1643 */     PageContext pageContext = _jspx_page_context;
/* 1644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1646 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1647 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1648 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 1650 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 1651 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1652 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1653 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1654 */       return true;
/*      */     }
/* 1656 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1657 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1662 */     PageContext pageContext = _jspx_page_context;
/* 1663 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1665 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1666 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1667 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1668 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1669 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 1671 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 1672 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1673 */           return true;
/* 1674 */         out.write("\" class=\"staticlinks\">\t");
/* 1675 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1676 */           return true;
/* 1677 */         out.write("</a></td>\n");
/* 1678 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1683 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1684 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1685 */       return true;
/*      */     }
/* 1687 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1693 */     PageContext pageContext = _jspx_page_context;
/* 1694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1696 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1697 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1698 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1700 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABLINK}");
/* 1701 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1702 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1704 */       return true;
/*      */     }
/* 1706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1712 */     PageContext pageContext = _jspx_page_context;
/* 1713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1715 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1716 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1717 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1719 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABNAME}");
/* 1720 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1721 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1722 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1723 */       return true;
/*      */     }
/* 1725 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1731 */     PageContext pageContext = _jspx_page_context;
/* 1732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1734 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1735 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1736 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 1737 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1738 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 1740 */         out.write("\n  \t\t");
/* 1741 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 1742 */           return true;
/* 1743 */         out.write("\n  \t\t");
/* 1744 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 1745 */           return true;
/* 1746 */         out.write("\n  \t\t");
/* 1747 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 1748 */           return true;
/* 1749 */         out.write("\n  \t");
/* 1750 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1751 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1755 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1756 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1757 */       return true;
/*      */     }
/* 1759 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1765 */     PageContext pageContext = _jspx_page_context;
/* 1766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1768 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1769 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1770 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 1772 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1773 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1774 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 1776 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1777 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1778 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1782 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1783 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1784 */       return true;
/*      */     }
/* 1786 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1792 */     PageContext pageContext = _jspx_page_context;
/* 1793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1795 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1796 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1797 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 1799 */     _jspx_th_c_005fwhen_005f7.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1800 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1801 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 1803 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1804 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1805 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1809 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1810 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1811 */       return true;
/*      */     }
/* 1813 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1819 */     PageContext pageContext = _jspx_page_context;
/* 1820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1822 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1823 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1824 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 1825 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1826 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 1828 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1829 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1830 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1834 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1835 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1836 */       return true;
/*      */     }
/* 1838 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1839 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1844 */     PageContext pageContext = _jspx_page_context;
/* 1845 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1847 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1848 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1849 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 1851 */     _jspx_th_c_005fout_005f12.setValue("${globalconfig['defaultmonitorsview']}");
/* 1852 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1853 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1854 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1855 */       return true;
/*      */     }
/* 1857 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1863 */     PageContext pageContext = _jspx_page_context;
/* 1864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1866 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1867 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 1868 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 1870 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 1871 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 1872 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 1873 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1874 */       return true;
/*      */     }
/* 1876 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1882 */     PageContext pageContext = _jspx_page_context;
/* 1883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1885 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 1886 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 1887 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 1889 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 1891 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 1892 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 1893 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 1894 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 1895 */       return true;
/*      */     }
/* 1897 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 1898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1903 */     PageContext pageContext = _jspx_page_context;
/* 1904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1906 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 1907 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 1908 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 1910 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 1912 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 1913 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 1914 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 1915 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 1916 */       return true;
/*      */     }
/* 1918 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 1919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1924 */     PageContext pageContext = _jspx_page_context;
/* 1925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1927 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 1928 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 1929 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1931 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 1932 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 1933 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 1934 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1935 */       return true;
/*      */     }
/* 1937 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1943 */     PageContext pageContext = _jspx_page_context;
/* 1944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1946 */     LoadTime _jspx_th_am_005ftimestamp_005f0 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 1947 */     _jspx_th_am_005ftimestamp_005f0.setPageContext(_jspx_page_context);
/* 1948 */     _jspx_th_am_005ftimestamp_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 1949 */     int _jspx_eval_am_005ftimestamp_005f0 = _jspx_th_am_005ftimestamp_005f0.doStartTag();
/* 1950 */     if (_jspx_th_am_005ftimestamp_005f0.doEndTag() == 5) {
/* 1951 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 1952 */       return true;
/*      */     }
/* 1954 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 1955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1960 */     PageContext pageContext = _jspx_page_context;
/* 1961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1963 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 1964 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1965 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 1966 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1967 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 1968 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1969 */         out = _jspx_page_context.pushBody();
/* 1970 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 1971 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1974 */         out.write("milliseconds");
/* 1975 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 1976 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1979 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1980 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1983 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1984 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1985 */       return true;
/*      */     }
/* 1987 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1993 */     PageContext pageContext = _jspx_page_context;
/* 1994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1996 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1997 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 1998 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2000 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${systime}");
/*      */     
/* 2002 */     _jspx_th_fmt_005fformatDate_005f0.setType("BOTH");
/* 2003 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 2004 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 2005 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2006 */       return true;
/*      */     }
/* 2008 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2014 */     PageContext pageContext = _jspx_page_context;
/* 2015 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2017 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2018 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2019 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2021 */     _jspx_th_tiles_005finsert_005f1.setAttribute("Header");
/* 2022 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2023 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2037 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2040 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2042 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2043 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2044 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2045 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2046 */       return true;
/*      */     }
/* 2048 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2054 */     PageContext pageContext = _jspx_page_context;
/* 2055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2057 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2058 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2059 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2061 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2063 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2064 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2065 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2066 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2067 */       return true;
/*      */     }
/* 2069 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2075 */     PageContext pageContext = _jspx_page_context;
/* 2076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2078 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2079 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2080 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2082 */     _jspx_th_tiles_005finsert_005f2.setAttribute("UserArea");
/* 2083 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2084 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2085 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2086 */       return true;
/*      */     }
/* 2088 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2094 */     PageContext pageContext = _jspx_page_context;
/* 2095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2097 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2098 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2099 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2101 */     _jspx_th_tiles_005finsert_005f3.setAttribute("footer");
/* 2102 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2103 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2104 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2105 */       return true;
/*      */     }
/* 2107 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2108 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ManagerLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */