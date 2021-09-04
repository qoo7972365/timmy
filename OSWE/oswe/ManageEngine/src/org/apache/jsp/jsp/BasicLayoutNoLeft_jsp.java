/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.IOException;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class BasicLayoutNoLeft_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public String getHelpLink(String key)
/*      */   {
/*   40 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   41 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   44 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   45 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   46 */       set = AMConnectionPool.executeQueryStmt(query);
/*   47 */       if (set.next())
/*      */       {
/*   49 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   52 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   58 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*   75 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   68 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   77 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   83 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(6);
/*   84 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   85 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*   86 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   87 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   88 */     _jspx_dependants.put("/jsp/includes/IT360Includes.jspf", Long.valueOf(1473429417000L));
/*   89 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  112 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  116 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  130 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  131 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  132 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  138 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  139 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  140 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  141 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  142 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  143 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  144 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  145 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  146 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  147 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  148 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  149 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  150 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  157 */     HttpSession session = null;
/*      */     
/*      */ 
/*  160 */     JspWriter out = null;
/*  161 */     Object page = this;
/*  162 */     JspWriter _jspx_out = null;
/*  163 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  167 */       response.setContentType("text/html;charset=UTF-8");
/*  168 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  170 */       _jspx_page_context = pageContext;
/*  171 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  172 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  173 */       session = pageContext.getSession();
/*  174 */       out = pageContext.getOut();
/*  175 */       _jspx_out = out;
/*      */       
/*  177 */       out.write("<!DOCTYPE html>\n");
/*  178 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  179 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  180 */       Properties applications = null;
/*  181 */       synchronized (application) {
/*  182 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  183 */         if (applications == null) {
/*  184 */           applications = new Properties();
/*  185 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  188 */       out.write("    \n\n");
/*      */       
/*  190 */       String addtotitle = "";
/*  191 */       if (Constants.isMinimizedversion())
/*      */       {
/*  193 */         addtotitle = " " + Constants.getCategorytype() + " Edition";
/*      */       }
/*      */       
/*  196 */       boolean Printer_Check = false;
/*      */       
/*  198 */       String method = request.getParameter("method");
/*  199 */       String uri = (String)request.getAttribute("uri");
/*      */       
/*  201 */       out.write("\n<html>\n<head>\n");
/*  202 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  203 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  205 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  206 */       out.write(10);
/*  207 */       out.write(10);
/*  208 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  209 */       out.write(10);
/*  210 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  211 */       out.print(request.getContextPath());
/*  212 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  213 */       out.print(request.getContextPath());
/*  214 */       out.write("'); //No I18N\n</script>\n");
/*  215 */       if (Constants.isIt360) {
/*  216 */         out.write("<script src='");
/*  217 */         out.print(request.getContextPath());
/*  218 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  220 */       out.write("\n<SCRIPT src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n");
/*      */       
/*  222 */       if (Constants.isIt360)
/*      */       {
/*  224 */         out.write("\n<title>");
/*  225 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  226 */         out.write("</title>\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  231 */         out.write("\n<title>");
/*  232 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  233 */         out.write(32);
/*  234 */         out.write(45);
/*  235 */         out.write(32);
/*  236 */         if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */           return;
/*  238 */         out.write("</title>\n");
/*      */       }
/*  240 */       out.write(10);
/*  241 */       out.write(10);
/*  242 */       out.write(10);
/*  243 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  245 */       out.write("\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n\n</head>\n");
/*  246 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */         return;
/*  248 */       out.write(10);
/*  249 */       out.write(10);
/*      */       
/*  251 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  252 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  253 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  255 */       out.write(10);
/*      */       
/*  257 */       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  258 */       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  259 */       _jspx_th_c_005fif_005f1.setParent(null);
/*      */       
/*  261 */       _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  262 */       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  263 */       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */         for (;;) {
/*  265 */           out.write(10);
/*  266 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*  267 */           out.write(10);
/*  268 */           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  269 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  273 */       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  274 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */       }
/*      */       else {
/*  277 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  278 */         out.write(10);
/*      */         
/*      */ 
/*  281 */         String searchQuery = request.getParameter("query");
/*  282 */         if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  283 */           searchQuery = "";
/*      */         }
/*      */         
/*  286 */         out.write(10);
/*  287 */         out.write(10);
/*      */         
/*  289 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  290 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  291 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  292 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  293 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  295 */             out.write("\n    ");
/*      */             
/*  297 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  298 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  299 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  301 */             _jspx_th_c_005fwhen_005f2.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  302 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  303 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  305 */                 out.write("\n\t\t<div id=\"userAreaContainerDiv\">\n    \t<div id=\"dhtmltooltip\"></div>\n\t<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr> \n        \t<td>\n              \t\t");
/*  306 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  307 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  308 */                 out.write(10);
/*  309 */                 out.write(10);
/*  310 */                 out.write(10);
/*  311 */                 out.write(32);
/*      */                 
/*  313 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  314 */                 if (helpKey == null)
/*      */                 {
/*  316 */                   String tileName = request.getParameter("TileName");
/*  317 */                   if (tileName != null)
/*      */                   {
/*  319 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  321 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  323 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  325 */                       helpKey = "Send E-mail";
/*      */                     }
/*  327 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  329 */                       helpKey = "Send SMS";
/*      */                     }
/*  331 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  333 */                       helpKey = "Execute Program";
/*      */                     }
/*  335 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  337 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  342 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  343 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  345 */                 Enumeration en = request.getParameterNames();
/*  346 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  347 */                 while (en.hasMoreElements()) {
/*  348 */                   String reqKey = (String)en.nextElement();
/*  349 */                   String reqValue = request.getParameter(reqKey);
/*  350 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  354 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  356 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*  358 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  360 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  364 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  368 */                 out.println("\";");
/*      */                 
/*  370 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  372 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  373 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  374 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  376 */                 _jspx_th_c_005fif_005f2.setTest("${selectedscheme == 'slim'}");
/*  377 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  378 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  380 */                     out.write(10);
/*  381 */                     out.write(10);
/*      */                     
/*  383 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  385 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  387 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  388 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  389 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  390 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  392 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  393 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  394 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  395 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  396 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  397 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  398 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  399 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  400 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  401 */                       out.print(getHelpLink(helpKey));
/*  402 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  403 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  404 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  405 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  406 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  408 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  409 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  410 */                       out.write("</a>");
/*  411 */                       if (request.getRemoteUser() != null)
/*  412 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  413 */                         out.println("&nbsp;");
/*  414 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  416 */                     out.write(32);
/*  417 */                     out.write(32);
/*  418 */                     out.write(10);
/*      */                     
/*  420 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  422 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  424 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  425 */                       out.write(10);
/*  426 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  428 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  429 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  430 */                       out.write("</a></td>\n  ");
/*      */                       
/*  432 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  433 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  434 */                       _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  436 */                       _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  437 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  438 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/*  440 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  441 */                           if (_jspx_meth_c_005fchoose_005f5(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                             return;
/*  443 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  444 */                           out.write("\n  </a></td>\n  ");
/*  445 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  446 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  450 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  451 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/*  454 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  455 */                       out.write(10);
/*  456 */                       out.write(32);
/*  457 */                       out.write(32);
/*      */                       
/*  459 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  460 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  461 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  463 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  464 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  465 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  467 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  469 */                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  470 */                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  471 */                           _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  472 */                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  473 */                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                             for (;;) {
/*  475 */                               out.write("\n   \t\t ");
/*      */                               
/*  477 */                               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  478 */                               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/*  479 */                               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                               
/*  481 */                               _jspx_th_c_005fwhen_005f10.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  482 */                               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/*  483 */                               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                 for (;;) {
/*  485 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  486 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                                     return;
/*  488 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  489 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  490 */                                   out.write("</a>\n   \t\t ");
/*  491 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/*  492 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  496 */                               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/*  497 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                               }
/*      */                               
/*  500 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/*  501 */                               out.write("\n   \t \t ");
/*      */                               
/*  503 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  504 */                               _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  505 */                               _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/*  506 */                               int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  507 */                               if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                 for (;;) {
/*  509 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  510 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                                     return;
/*  512 */                                   out.write("\" class=\"staticlinks\">");
/*  513 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  514 */                                   out.write("</a>\n   \t\t ");
/*  515 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  516 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  520 */                               if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  521 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                               }
/*      */                               
/*  524 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  525 */                               out.write("\n   \t");
/*  526 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  527 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  531 */                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  532 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                           }
/*      */                           
/*  535 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  536 */                           out.write("\n\t</td> \n  ");
/*  537 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  538 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  542 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  543 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  546 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  547 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  548 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  549 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  550 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  551 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  552 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  553 */                       out.write("</a></td>\n\t");
/*      */                       
/*  555 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  556 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  557 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  559 */                       _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  560 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  561 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  563 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  564 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  565 */                           out.write("</a></td>\n\t");
/*  566 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  567 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  571 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  572 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  575 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  576 */                       out.write(10);
/*  577 */                       out.write(9);
/*      */                       
/*  579 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  580 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  581 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  583 */                       _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  584 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  585 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  587 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  588 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  589 */                           out.write("</a></td>\n\t");
/*  590 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  591 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  595 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  596 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  599 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  600 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  601 */                       out.print(getHelpLink(helpKey));
/*  602 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  603 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  604 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  605 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  606 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  608 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  609 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  610 */                       out.write("</a>");
/*  611 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  613 */                         out.write("&nbsp;");
/*  614 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  615 */                         out.write("\n    \t\t\t\t\t");
/*  616 */                       } else { out.println("&nbsp;"); }
/*  617 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  619 */                     out.write(10);
/*  620 */                     out.write(32);
/*  621 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  622 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  626 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  627 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  630 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  631 */                 out.write(" \n\n\t");
/*  632 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()))
/*      */                 {
/*  634 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  636 */                 out.write("\n\n</table>\n");
/*  637 */                 out.write("\n              \t</td>\n        </tr>\n    <tr>\n    <td valign=\"top\"> \n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        \n        ");
/*      */                 
/*  639 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  640 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  641 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  643 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/*  645 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  646 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  647 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  648 */                   String msg = null;
/*  649 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  650 */                     out = _jspx_page_context.pushBody();
/*  651 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  652 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/*  654 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  656 */                     out.write(" \n        <tr> \n\t          <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:5px 5px 5px 5px;\" width=\"99%\">\n\t            <tr>\n\t            <td width=\"98%\">\n\t            \n\t            \n\t            <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t    \t<tr>\n\t\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t    \t</tr>\n\t\t    <tr>\n\t\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"96%\" >\n\t\t    \t\t<tr>\n\t\t    \t\t      <td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t                    <td width=\"98%\" class=\"msg-table-width\"> &nbsp; ");
/*  657 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/*  659 */                     out.write("</td>\n\t\t    \t\t</tr>\n\t\t    \t\t</table>\n\t\t    \t</td>\n\t\t    \t<td class=\"msg-status-right-bg\"></td>\n\t\t    </tr>\n\t\t    <tr>\n\t\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t    </tr>\n\t</table>\n\t\t\t\t            \t<br>\n\t\t\t\t        </td>\n        </tr>\n        ");
/*  660 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  661 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  662 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  665 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  666 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  669 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  670 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/*  673 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  674 */                 out.write("\n         ");
/*      */                 
/*  676 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  677 */                 _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  678 */                 _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  680 */                 _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  681 */                 int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  682 */                 if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                   for (;;) {
/*  684 */                     out.write(" \n\n         <tr> \n\t\t\t          <td height=\"46\" valign=\"top\" class=\"tdindent\"> \n\t\t\t          \t<table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:5px 5px 5px 5px;\" width=\"99%\">\n\t<tr>\n\t<td width=\"98%\">\n\t\n\t\n\t\n\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t<tr>\n\t\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t</tr>\n\t<tr>\n\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\" >\n\t\t\t\t<tr>\n\t\t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t                \t<td width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t\t\t\t\t\t                \t\t");
/*      */                     
/*  686 */                     MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  687 */                     _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  688 */                     _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                     
/*  690 */                     _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                     
/*  692 */                     _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  693 */                     int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  694 */                     if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  695 */                       String msg = null;
/*  696 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  697 */                         out = _jspx_page_context.pushBody();
/*  698 */                         _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  699 */                         _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                       }
/*  701 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  703 */                         out.write(" \n\t                  ");
/*  704 */                         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                           return;
/*  706 */                         out.write("\n\t\t\t\t\t\t\t\t\t                  \t\t");
/*  707 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  708 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  709 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  712 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  713 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  716 */                     if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  717 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                     }
/*      */                     
/*  720 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  721 */                     out.write("\n\t\t\t\t\t\t\t\t\t                  \t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t\t<td class=\"msg-status-right-bg\"></td>\n\t</tr>\n\t<tr>\n\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t</tr>\n\t</table>\n\t</td>\n        </tr>\n        ");
/*  722 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  723 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  727 */                 if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  728 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                 }
/*      */                 
/*  731 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  732 */                 out.write(" \n        <tr> \n            <td height=\"100%\" valign=\"top\" >\n\t            \n\t            <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" height=\"100%\">\n\t  \t  <tr>\n\t  \t  <td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t  \t  <img src=\"/images/spacer.gif\"/>\n\t  \t  </td>\n\t  \t  <td valign=\"top\">\n\t  \t  ");
/*  733 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  735 */                 out.write("\n\t            </td>\n\t  \t  <td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t  \t  <img src=\"/images/spacer.gif\"/>\n\t  \t  </td>\n\t\t\t\t\t\t\t</tr>\n\t  \t  </table>\n\t  </td>   \n        </tr>\n\t\t      </table>\n\t\t  </td>          \n        </tr>\n\t    </div><!-- userAreaContainerDiv ends -->\n\t    ");
/*  736 */                 if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  738 */                 out.write("\n    ");
/*  739 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  740 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  744 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  745 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  748 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  749 */             out.write(10);
/*  750 */             out.write(32);
/*  751 */             out.write(32);
/*      */             
/*  753 */             OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  754 */             _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  755 */             _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*  756 */             int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  757 */             if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */               for (;;) {
/*  759 */                 out.write(10);
/*  760 */                 out.write(10);
/*  761 */                 if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/*  763 */                 out.write(" \n<div id=\"userAreaContainerDiv\">  \n\t<div style=\"padding:5px;\">\n\t</div>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n        \n    <td valign=\"top\"> \n\t  \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n       \n        ");
/*      */                 
/*  765 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  766 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/*  767 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                 
/*  769 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/*  771 */                 _jspx_th_html_005fmessages_005f2.setMessage("false");
/*  772 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/*  773 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/*  774 */                   String msg = null;
/*  775 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  776 */                     out = _jspx_page_context.pushBody();
/*  777 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/*  778 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/*  780 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  782 */                     out.write(" \n        ");
/*  783 */                     if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/*  785 */                     out.write("\n\t\n\t              \t");
/*  786 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/*  788 */                     out.write("\n        ");
/*  789 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/*  790 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  791 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  794 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  795 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  798 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/*  799 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/*  802 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/*  803 */                 out.write(" \n         ");
/*  804 */                 if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/*  806 */                 out.write("\n        ");
/*      */                 
/*  808 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  809 */                 _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  810 */                 _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                 
/*  812 */                 _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  813 */                 int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  814 */                 if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                   for (;;) {
/*  816 */                     out.write(" \n        <tr> \n\t          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:5px 0px 5px 15px;\" width=\"98%\">\n\t<tr>\n\t<td width=\"84%\">\n\t\n\t\n\t\n\t\n\t\n\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" align=\"center\">\n\t\t<tr>\n\t\t\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t<td  class=\"msg-status-tp-right-corn\"></td>\n\t\t</tr>\n\t     <tr>\n\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"88%\" border=\"0\">\n\t\t\t<tr>\n\t\t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"25\" width=\"25\"></td>\n\t                <td width=\"88%\" class=\"msg-table-width\">&nbsp;");
/*      */                     
/*  818 */                     MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  819 */                     _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/*  820 */                     _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                     
/*  822 */                     _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                     
/*  824 */                     _jspx_th_html_005fmessages_005f3.setMessage("true");
/*  825 */                     int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/*  826 */                     if (_jspx_eval_html_005fmessages_005f3 != 0) {
/*  827 */                       String msg = null;
/*  828 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  829 */                         out = _jspx_page_context.pushBody();
/*  830 */                         _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/*  831 */                         _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                       }
/*  833 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  835 */                         out.write(32);
/*  836 */                         if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                           return;
/*  838 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/*  839 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  840 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  843 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  844 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  847 */                     if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/*  848 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                     }
/*      */                     
/*  851 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/*  852 */                     out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t\t<td class=\"msg-status-right-bg\"></td>\n\t     </tr>\n\t\n\t<tr>\n\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n\t</tr>\n\t</table>\n\t\n\t\n\t</td>\n\t</tr>\n\t</table></td>\n        </tr>\n        ");
/*  853 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/*  854 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  858 */                 if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/*  859 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                 }
/*      */                 
/*  862 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  863 */                 out.write(" \n        <tr> \n           <td height=\"100%\" valign=\"top\" >\n\t           \n\t           <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t \t  <tr>\n\t \t  <td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t \t  <img src=\"/images/spacer.gif\"/>\n\t \t  </td>\n\t \t  <td valign=\"top\">\n\t \t  ");
/*  864 */                 if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/*  866 */                 out.write("\n\t       </td>\n\t \t  <td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t \t  <img src=\"/images/spacer.gif\"/>\n\t \t  </td>\n\t\t \t  \t\t\t</tr>\n\t \t  </table>\n\t  </td>   \n        </tr>\n        <tr> \n          <td height=\"20%\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n      \t</table>\n     </td>\n  </tr>\n</table>\n</div><!-- userAreaContainerDiv ends -->\n");
/*  867 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/*  869 */                 out.write("        \n");
/*      */                 
/*  871 */                 Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                 
/*  873 */                 out.write(10);
/*  874 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  875 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  879 */             if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  880 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */             }
/*      */             
/*  883 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  884 */             out.write(10);
/*  885 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  886 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  890 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  891 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */         }
/*      */         else {
/*  894 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  895 */           out.write("\n\n<script type=\"text/javascript\">\n\ttry\n\t{\n\t  form = document.forms[0];\n\t  if(form && form.action.indexOf(\"Search.do\")<0)  //NO I18N\n\t  {\n\t    $('body').prepend('<form id=\"mySearch\" action=\"/Search.do\" style=\"display:none;\"></form>');   //No I18N\n\t  }\n\t}\n\tcatch(err){\n\n\t}\n</script>\n");
/*  896 */           out.write(10);
/*  897 */           out.write(10);
/*  898 */           if (Constants.isIt360) {
/*  899 */             out.write("\n<script type=\"text/javascript\">\n\tvar iframe= parent.window.document.getElementById('_iframe_view');\n\tif(iframe != null)\n\t{\n\t\tvar iframeName = iframe.name;\n\t\tif(iframeName != null && iframeName == '_iframe_view')\n\t\t{\n\t\t\tvar frameDoc = iframe.contentDocument || iframe.contentWindow.document;\n\t\t\tvar e = frameDoc.getElementById(\"userAreaContainerDiv\");\n\t\t\tif(e!=null)\n\t\t\t{\n\t\t\t\te.id = \"userAreaContainerDiv_admin\";\n\t\t\t}\n\t\t\n\t\t\tvar footer = frameDoc.getElementById(\"footer-container\");\n\t\t\tif(footer!=null)\n\t\t\t{\n\t\t\t\tfooter.innerHTML=\"\";\n\t\t\t}\n\t\t}\n\t}\n</script>\n");
/*      */           }
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
/*  922 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  923 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  926 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  932 */     PageContext pageContext = _jspx_page_context;
/*  933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  935 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  936 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  937 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  939 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  941 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  942 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  943 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  945 */       return true;
/*      */     }
/*  947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  953 */     PageContext pageContext = _jspx_page_context;
/*  954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  956 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/*  957 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/*  958 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/*  960 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/*  961 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/*  962 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/*  963 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/*  964 */       return true;
/*      */     }
/*  966 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/*  967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  972 */     PageContext pageContext = _jspx_page_context;
/*  973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  975 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  976 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  977 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/*  979 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod && !empty customreloadperiod}");
/*  980 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  981 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/*  983 */         out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/*  984 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*  985 */           return true;
/*  986 */         out.write(34);
/*  987 */         out.write(62);
/*  988 */         out.write(10);
/*  989 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  990 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  994 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  995 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  996 */       return true;
/*      */     }
/*  998 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1004 */     PageContext pageContext = _jspx_page_context;
/* 1005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1007 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1008 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1009 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1011 */     _jspx_th_c_005fout_005f1.setValue("${customreloadperiod}");
/* 1012 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1013 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1014 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1015 */       return true;
/*      */     }
/* 1017 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1023 */     PageContext pageContext = _jspx_page_context;
/* 1024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1026 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1027 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1028 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 1029 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1030 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1032 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1033 */           return true;
/* 1034 */         out.write(10);
/* 1035 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1036 */           return true;
/* 1037 */         out.write(10);
/* 1038 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1039 */           return true;
/* 1040 */         out.write(10);
/* 1041 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1046 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1047 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1048 */       return true;
/*      */     }
/* 1050 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1056 */     PageContext pageContext = _jspx_page_context;
/* 1057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1059 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1060 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1061 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1063 */     _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showApplications'}");
/* 1064 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1065 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1067 */         out.write("\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad(),setPref('home')\" onUnload=\"savePref('home');\">\n");
/* 1068 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1069 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1073 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1074 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1075 */       return true;
/*      */     }
/* 1077 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1083 */     PageContext pageContext = _jspx_page_context;
/* 1084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1086 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1087 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1088 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1090 */     _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showResourceTypes'}");
/* 1091 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1092 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1094 */         out.write("\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad(),setPref('monitor')\" onUnload=\"savePref('monitor');\">\n\n");
/* 1095 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1096 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1100 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1101 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1102 */       return true;
/*      */     }
/* 1104 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1110 */     PageContext pageContext = _jspx_page_context;
/* 1111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1113 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1114 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1115 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1116 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1117 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1119 */         out.write("\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad()\" >\n\n");
/* 1120 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1121 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1125 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1126 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1127 */       return true;
/*      */     }
/* 1129 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1130 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1135 */     PageContext pageContext = _jspx_page_context;
/* 1136 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1138 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1139 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1140 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1142 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1144 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1145 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1147 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1148 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1150 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1151 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1152 */             return true;
/* 1153 */           out.write("\"\" class=\"staticlinks\">");
/* 1154 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1155 */             return true;
/* 1156 */           out.write("</a></td>\n");
/* 1157 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1158 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1162 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1163 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1166 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1167 */         out = _jspx_page_context.popBody(); }
/* 1168 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1170 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1171 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1178 */     PageContext pageContext = _jspx_page_context;
/* 1179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1181 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1182 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1183 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1185 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABLINK}");
/* 1186 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1187 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1188 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1189 */       return true;
/*      */     }
/* 1191 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1197 */     PageContext pageContext = _jspx_page_context;
/* 1198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1200 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1201 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1202 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1204 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 1205 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1206 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1208 */       return true;
/*      */     }
/* 1210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1216 */     PageContext pageContext = _jspx_page_context;
/* 1217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1219 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1220 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1221 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1223 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1225 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1226 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1228 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1229 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1231 */           out.write(10);
/* 1232 */           out.write(10);
/* 1233 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1234 */             return true;
/* 1235 */           out.write(10);
/* 1236 */           out.write(10);
/* 1237 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1238 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1242 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1243 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1246 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1247 */         out = _jspx_page_context.popBody(); }
/* 1248 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1250 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1251 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1258 */     PageContext pageContext = _jspx_page_context;
/* 1259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1261 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1262 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1263 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1264 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1265 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1267 */         out.write(10);
/* 1268 */         out.write(10);
/* 1269 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1270 */           return true;
/* 1271 */         out.write(10);
/* 1272 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1273 */           return true;
/* 1274 */         out.write(10);
/* 1275 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1276 */           return true;
/* 1277 */         out.write("\n\n\n\n\n");
/* 1278 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1279 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1283 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1284 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1285 */       return true;
/*      */     }
/* 1287 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1293 */     PageContext pageContext = _jspx_page_context;
/* 1294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1296 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1297 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1298 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1300 */     _jspx_th_c_005fwhen_005f3.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1301 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1302 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1304 */         out.write(10);
/* 1305 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1306 */           return true;
/* 1307 */         out.write(10);
/* 1308 */         out.write(32);
/* 1309 */         out.write(32);
/* 1310 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1311 */           return true;
/* 1312 */         out.write(10);
/* 1313 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1314 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1318 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1319 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1320 */       return true;
/*      */     }
/* 1322 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1328 */     PageContext pageContext = _jspx_page_context;
/* 1329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1331 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1332 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1333 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1335 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1336 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1337 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1339 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1340 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1341 */           return true;
/* 1342 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1343 */           return true;
/* 1344 */         out.write("\n  </a></td>\n  ");
/* 1345 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1346 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1350 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1351 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1352 */       return true;
/*      */     }
/* 1354 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1360 */     PageContext pageContext = _jspx_page_context;
/* 1361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1363 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1364 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1365 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1366 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1367 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1369 */         out.write("\n  \t\t");
/* 1370 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1371 */           return true;
/* 1372 */         out.write("\n  \t\t");
/* 1373 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1374 */           return true;
/* 1375 */         out.write("\n  \t\t");
/* 1376 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1377 */           return true;
/* 1378 */         out.write("\n  \t");
/* 1379 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1380 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1384 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1385 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1386 */       return true;
/*      */     }
/* 1388 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1389 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1394 */     PageContext pageContext = _jspx_page_context;
/* 1395 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1397 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1398 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1399 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1401 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1402 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1403 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1405 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1406 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1407 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1411 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1412 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1413 */       return true;
/*      */     }
/* 1415 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1421 */     PageContext pageContext = _jspx_page_context;
/* 1422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1424 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1425 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1426 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1428 */     _jspx_th_c_005fwhen_005f5.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1429 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1430 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1432 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1433 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1434 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1438 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1439 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1440 */       return true;
/*      */     }
/* 1442 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1443 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1448 */     PageContext pageContext = _jspx_page_context;
/* 1449 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1451 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1452 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1453 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1454 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1455 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1457 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1458 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1459 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1463 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1464 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1465 */       return true;
/*      */     }
/* 1467 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1468 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1473 */     PageContext pageContext = _jspx_page_context;
/* 1474 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1476 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1477 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1478 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 1480 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1481 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1482 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1483 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1484 */       return true;
/*      */     }
/* 1486 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1487 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1492 */     PageContext pageContext = _jspx_page_context;
/* 1493 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1495 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1496 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1497 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1499 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1500 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1501 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1503 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1504 */         if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1505 */           return true;
/* 1506 */         out.write("\n\t</td> \n  ");
/* 1507 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1508 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1512 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1513 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1514 */       return true;
/*      */     }
/* 1516 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1522 */     PageContext pageContext = _jspx_page_context;
/* 1523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1525 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1526 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1527 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 1528 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1529 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 1531 */         out.write("\n   \t\t ");
/* 1532 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1533 */           return true;
/* 1534 */         out.write("\n   \t \t ");
/* 1535 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1536 */           return true;
/* 1537 */         out.write("\n   \t");
/* 1538 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1539 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1543 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1544 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1545 */       return true;
/*      */     }
/* 1547 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1553 */     PageContext pageContext = _jspx_page_context;
/* 1554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1556 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1557 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1558 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 1560 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1561 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1562 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 1564 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1565 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1566 */           return true;
/* 1567 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1568 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1569 */           return true;
/* 1570 */         out.write("</a>\n   \t\t ");
/* 1571 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1572 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1576 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1577 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1578 */       return true;
/*      */     }
/* 1580 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1586 */     PageContext pageContext = _jspx_page_context;
/* 1587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1589 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1590 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1591 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 1593 */     _jspx_th_c_005fout_005f5.setValue("${globalconfig['defaultmonitorsview']}");
/* 1594 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1595 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1596 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1597 */       return true;
/*      */     }
/* 1599 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1605 */     PageContext pageContext = _jspx_page_context;
/* 1606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1608 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1609 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1610 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 1612 */     _jspx_th_c_005fout_005f6.setValue("${tab.TABNAME}");
/* 1613 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1614 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1615 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1616 */       return true;
/*      */     }
/* 1618 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1624 */     PageContext pageContext = _jspx_page_context;
/* 1625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1627 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1628 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1629 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 1630 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1631 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 1633 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1634 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1635 */           return true;
/* 1636 */         out.write("\" class=\"staticlinks\">");
/* 1637 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1638 */           return true;
/* 1639 */         out.write("</a>\n   \t\t ");
/* 1640 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1641 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1645 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1646 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1647 */       return true;
/*      */     }
/* 1649 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1655 */     PageContext pageContext = _jspx_page_context;
/* 1656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1658 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1659 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1660 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1662 */     _jspx_th_c_005fout_005f7.setValue("${globalconfig['defaultmonitorsview']}");
/* 1663 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1664 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1665 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1666 */       return true;
/*      */     }
/* 1668 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1669 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1674 */     PageContext pageContext = _jspx_page_context;
/* 1675 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1677 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1678 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1679 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1681 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 1682 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1683 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1684 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1685 */       return true;
/*      */     }
/* 1687 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1693 */     PageContext pageContext = _jspx_page_context;
/* 1694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1696 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1697 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1698 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1700 */     _jspx_th_c_005fwhen_005f7.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 1701 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1702 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 1704 */         out.write(10);
/* 1705 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1706 */           return true;
/* 1707 */         out.write(10);
/* 1708 */         out.write(9);
/* 1709 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1710 */           return true;
/* 1711 */         out.write(10);
/* 1712 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1713 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1717 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1718 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1719 */       return true;
/*      */     }
/* 1721 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1727 */     PageContext pageContext = _jspx_page_context;
/* 1728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1730 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1731 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1732 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 1734 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1735 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1736 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1738 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1739 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1740 */           return true;
/* 1741 */         out.write("</a></td>\n\t");
/* 1742 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1743 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1747 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1748 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1749 */       return true;
/*      */     }
/* 1751 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1757 */     PageContext pageContext = _jspx_page_context;
/* 1758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1760 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1761 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1762 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1764 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 1765 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1766 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1767 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1768 */       return true;
/*      */     }
/* 1770 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1776 */     PageContext pageContext = _jspx_page_context;
/* 1777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1779 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1780 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1781 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 1783 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 1784 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1785 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1787 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1788 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1789 */           return true;
/* 1790 */         out.write("</a></td>\n\t");
/* 1791 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1792 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1796 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1797 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1798 */       return true;
/*      */     }
/* 1800 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1806 */     PageContext pageContext = _jspx_page_context;
/* 1807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1809 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1810 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1811 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 1813 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 1814 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1815 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1816 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1817 */       return true;
/*      */     }
/* 1819 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1825 */     PageContext pageContext = _jspx_page_context;
/* 1826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1828 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1829 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1830 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1831 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1832 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 1834 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 1835 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1836 */           return true;
/* 1837 */         out.write("\" class=\"staticlinks\">\t");
/* 1838 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1839 */           return true;
/* 1840 */         out.write("</a></td>\n");
/* 1841 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1842 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1846 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1847 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1848 */       return true;
/*      */     }
/* 1850 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1856 */     PageContext pageContext = _jspx_page_context;
/* 1857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1859 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1860 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1861 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 1863 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABLINK}");
/* 1864 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1865 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1866 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1867 */       return true;
/*      */     }
/* 1869 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1875 */     PageContext pageContext = _jspx_page_context;
/* 1876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1878 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1879 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1880 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 1882 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABNAME}");
/* 1883 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1884 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1885 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1886 */       return true;
/*      */     }
/* 1888 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1889 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1894 */     PageContext pageContext = _jspx_page_context;
/* 1895 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1897 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1898 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1899 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 1900 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1901 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 1903 */         out.write("\n  \t\t");
/* 1904 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 1905 */           return true;
/* 1906 */         out.write("\n  \t\t");
/* 1907 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 1908 */           return true;
/* 1909 */         out.write("\n  \t\t");
/* 1910 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 1911 */           return true;
/* 1912 */         out.write("\n  \t");
/* 1913 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1914 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1918 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1919 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1920 */       return true;
/*      */     }
/* 1922 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1928 */     PageContext pageContext = _jspx_page_context;
/* 1929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1931 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1932 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1933 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 1935 */     _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1936 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1937 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 1939 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1940 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1941 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1945 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1946 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1947 */       return true;
/*      */     }
/* 1949 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1955 */     PageContext pageContext = _jspx_page_context;
/* 1956 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1958 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1959 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1960 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 1962 */     _jspx_th_c_005fwhen_005f9.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1963 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1964 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 1966 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1967 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1968 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1972 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1973 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1974 */       return true;
/*      */     }
/* 1976 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1982 */     PageContext pageContext = _jspx_page_context;
/* 1983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1985 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1986 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1987 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 1988 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1989 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 1991 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1992 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1997 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1998 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1999 */       return true;
/*      */     }
/* 2001 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2007 */     PageContext pageContext = _jspx_page_context;
/* 2008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2010 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2011 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2012 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 2014 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 2015 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2016 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2018 */       return true;
/*      */     }
/* 2020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2026 */     PageContext pageContext = _jspx_page_context;
/* 2027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2029 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2030 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2031 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2033 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2034 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2035 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2037 */       return true;
/*      */     }
/* 2039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2045 */     PageContext pageContext = _jspx_page_context;
/* 2046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2048 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2049 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2050 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2052 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2054 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2055 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2056 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2057 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2058 */       return true;
/*      */     }
/* 2060 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2066 */     PageContext pageContext = _jspx_page_context;
/* 2067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2069 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2070 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2071 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2073 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2075 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2076 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2077 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2078 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2079 */       return true;
/*      */     }
/* 2081 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2087 */     PageContext pageContext = _jspx_page_context;
/* 2088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2090 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2091 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2092 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2094 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 2095 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2096 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2097 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2098 */       return true;
/*      */     }
/* 2100 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2106 */     PageContext pageContext = _jspx_page_context;
/* 2107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2109 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2110 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2111 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2113 */     _jspx_th_tiles_005finsert_005f1.setAttribute("footer");
/* 2114 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2115 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2116 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2117 */       return true;
/*      */     }
/* 2119 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2125 */     PageContext pageContext = _jspx_page_context;
/* 2126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2128 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2129 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2130 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2132 */     _jspx_th_tiles_005finsert_005f2.setAttribute("Header");
/* 2133 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2134 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2135 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2136 */       return true;
/*      */     }
/* 2138 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2144 */     PageContext pageContext = _jspx_page_context;
/* 2145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2147 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2148 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2149 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2151 */     _jspx_th_c_005fif_005f3.setTest("${empty firstrow}");
/* 2152 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2153 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2155 */         out.write("\n\t        \n\t           <tr> \n\t\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t          \t<table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:5px 0px 5px 15px;\" width=\"98%\">\n\t            <tr>\n\t            <td width=\"98%\">\n\t            \n\t            \n\t            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t    \t<tr>\n\t\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t    \t</tr>\n\t\t    <tr>\n\t\t    \t<td class=\"msg-status-left-bg\" >&nbsp;</td>\n\t\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t    \t\t\t<tr>\n\t\t    \t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t                    <td width=\"98%\" class=\"msg-table-width\">");
/* 2156 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 2157 */           return true;
/* 2158 */         out.write("\n\t        \t\t");
/* 2159 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2160 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2164 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2165 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2166 */       return true;
/*      */     }
/* 2168 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2174 */     PageContext pageContext = _jspx_page_context;
/* 2175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2177 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2178 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2179 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2181 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 2183 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 2184 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2185 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2186 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2187 */       return true;
/*      */     }
/* 2189 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2195 */     PageContext pageContext = _jspx_page_context;
/* 2196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2198 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2199 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2200 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2202 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2204 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2205 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2206 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2207 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2208 */       return true;
/*      */     }
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2216 */     PageContext pageContext = _jspx_page_context;
/* 2217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2219 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2220 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2221 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2223 */     _jspx_th_c_005fif_005f4.setTest("${!empty firstrow}");
/* 2224 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2225 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2227 */         out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n\t    </table>            \n            </td>\n            </tr>\n            </table></td>\n        </tr>\n        ");
/* 2228 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2229 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2233 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2234 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2235 */       return true;
/*      */     }
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2243 */     PageContext pageContext = _jspx_page_context;
/* 2244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2246 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2247 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2248 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2250 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2252 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2253 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2254 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2255 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2256 */       return true;
/*      */     }
/* 2258 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2264 */     PageContext pageContext = _jspx_page_context;
/* 2265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2267 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2268 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2269 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2271 */     _jspx_th_tiles_005finsert_005f3.setAttribute("UserArea");
/* 2272 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2273 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2274 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2275 */       return true;
/*      */     }
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2283 */     PageContext pageContext = _jspx_page_context;
/* 2284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2286 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2287 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2288 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2290 */     _jspx_th_tiles_005finsert_005f4.setAttribute("footer");
/* 2291 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2292 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2293 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2294 */       return true;
/*      */     }
/* 2296 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2297 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\BasicLayoutNoLeft_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */