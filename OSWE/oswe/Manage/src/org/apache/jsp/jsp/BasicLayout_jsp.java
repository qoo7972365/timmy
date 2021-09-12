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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.tiles.ImportAttributeTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.tiles.ComponentContext;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class BasicLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public String getHelpLink(String key)
/*      */   {
/*   43 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   44 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   47 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   48 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   49 */       set = AMConnectionPool.executeQueryStmt(query);
/*   50 */       if (set.next())
/*      */       {
/*   52 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   55 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   61 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*   78 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   71 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   80 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   86 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(6);
/*   87 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   88 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*   89 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   90 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   91 */     _jspx_dependants.put("/jsp/includes/IT360Includes.jspf", Long.valueOf(1473429417000L));
/*   92 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody;
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
/*  116 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  120 */     this._005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  130 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  131 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  132 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  133 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  134 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  135 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  136 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  137 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  141 */     this._005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody.release();
/*  142 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  143 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  144 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  145 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  146 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  147 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  148 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  149 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  150 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  151 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  152 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  153 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  154 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  155 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  156 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  163 */     HttpSession session = null;
/*      */     
/*      */ 
/*  166 */     JspWriter out = null;
/*  167 */     Object page = this;
/*  168 */     JspWriter _jspx_out = null;
/*  169 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  173 */       response.setContentType("text/html;charset=UTF-8");
/*  174 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  176 */       _jspx_page_context = pageContext;
/*  177 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  178 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  179 */       session = pageContext.getSession();
/*  180 */       out = pageContext.getOut();
/*  181 */       _jspx_out = out;
/*      */       
/*  183 */       out.write("<!DOCTYPE html>\n");
/*  184 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  185 */       out.write(10);
/*  186 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  187 */       Properties applications = null;
/*  188 */       synchronized (application) {
/*  189 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  190 */         if (applications == null) {
/*  191 */           applications = new Properties();
/*  192 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  195 */       out.write(10);
/*      */       
/*  197 */       boolean isSearchPage = false;
/*  198 */       ComponentContext compContext1 = (ComponentContext)pageContext.getAttribute("org.apache.struts.taglib.tiles.CompContext", 2);
/*  199 */       if ((Constants.isIt360) && (compContext1.getAttribute("LeftArea") != null))
/*      */       {
/*  201 */         if (compContext1.getAttribute("LeftArea").toString().contains("searchLeft.jsp"))
/*      */         {
/*  203 */           isSearchPage = true;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  208 */       boolean Printer_Check = false;
/*  209 */       boolean isAdmin = false;
/*      */       
/*  211 */       String method = request.getParameter("method");
/*  212 */       String uri = (String)request.getAttribute("uri");
/*      */       
/*      */ 
/*  215 */       out.write(10);
/*  216 */       out.write(32);
/*  217 */       out.write(32);
/*  218 */       if (_jspx_meth_tiles_005fimportAttribute_005f0(_jspx_page_context))
/*      */         return;
/*  220 */       out.write(10);
/*  221 */       out.write(32);
/*      */       
/*  223 */       String pageTitle = (String)pageContext.getAttribute("title");
/*      */       
/*  225 */       out.write("\n<html>\n<head>\n");
/*  226 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  227 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  229 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  230 */       out.write(10);
/*  231 */       out.write(10);
/*  232 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  233 */       out.write(10);
/*  234 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  235 */       out.print(request.getContextPath());
/*  236 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  237 */       out.print(request.getContextPath());
/*  238 */       out.write("'); //No I18N\n</script>\n");
/*  239 */       if (Constants.isIt360) {
/*  240 */         out.write("<script src='");
/*  241 */         out.print(request.getContextPath());
/*  242 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  244 */       out.write(10);
/*      */       
/*  246 */       if (Constants.isIt360)
/*      */       {
/*  248 */         out.write("\n<title>");
/*  249 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  250 */         out.write("</title>\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  255 */         out.write("\n<title>");
/*  256 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  257 */         out.write(32);
/*  258 */         out.write(45);
/*  259 */         out.write(32);
/*  260 */         if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */           return;
/*  262 */         out.write("</title>\n");
/*      */       }
/*  264 */       out.write(10);
/*  265 */       out.write(10);
/*  266 */       out.write(10);
/*  267 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  269 */       out.write("\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n");
/*  270 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */         return;
/*  272 */       out.write(10);
/*  273 */       out.write(10);
/*      */       
/*  275 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  276 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  277 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  279 */       out.write(10);
/*      */       
/*  281 */       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  282 */       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  283 */       _jspx_th_c_005fif_005f1.setParent(null);
/*      */       
/*  285 */       _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  286 */       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  287 */       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */         for (;;) {
/*  289 */           out.write(10);
/*  290 */           Printer_Check = true;
/*  291 */           out.write(10);
/*  292 */           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  293 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  297 */       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  298 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */       }
/*      */       else {
/*  301 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  302 */         out.write(10);
/*  303 */         out.write(10);
/*  304 */         if ((Printer_Check) || (isAdmin)) {
/*  305 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */         }
/*  307 */         out.write(10);
/*      */         
/*      */ 
/*  310 */         String searchQuery = request.getParameter("query");
/*  311 */         if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  312 */           searchQuery = "";
/*      */         }
/*      */         
/*  315 */         out.write(10);
/*  316 */         out.write(10);
/*      */         
/*  318 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  319 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  320 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  321 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  322 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  324 */             out.write("\n    ");
/*      */             
/*  326 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  327 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  328 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  330 */             _jspx_th_c_005fwhen_005f2.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  331 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  332 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  334 */                 out.write("\n\t<div id=\"userAreaContainerDiv\">\n    \t<div id=\"dhtmltooltip\"></div>\n\t<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n        \t<td>\n              \t\t");
/*  335 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  336 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  337 */                 out.write(10);
/*  338 */                 out.write(10);
/*  339 */                 out.write(10);
/*  340 */                 out.write(32);
/*      */                 
/*  342 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  343 */                 if (helpKey == null)
/*      */                 {
/*  345 */                   String tileName = request.getParameter("TileName");
/*  346 */                   if (tileName != null)
/*      */                   {
/*  348 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  350 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  352 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  354 */                       helpKey = "Send E-mail";
/*      */                     }
/*  356 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  358 */                       helpKey = "Send SMS";
/*      */                     }
/*  360 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  362 */                       helpKey = "Execute Program";
/*      */                     }
/*  364 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  366 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  371 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  372 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  374 */                 Enumeration en = request.getParameterNames();
/*  375 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  376 */                 while (en.hasMoreElements()) {
/*  377 */                   String reqKey = (String)en.nextElement();
/*  378 */                   String reqValue = request.getParameter(reqKey);
/*  379 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  383 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  385 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*  387 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  389 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  393 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  397 */                 out.println("\";");
/*      */                 
/*  399 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  401 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  402 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  403 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  405 */                 _jspx_th_c_005fif_005f2.setTest("${selectedscheme == 'slim'}");
/*  406 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  407 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  409 */                     out.write(10);
/*  410 */                     out.write(10);
/*      */                     
/*  412 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  414 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  416 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  417 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  418 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  419 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  421 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  422 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  423 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  424 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  425 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  426 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  427 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  428 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  429 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  430 */                       out.print(getHelpLink(helpKey));
/*  431 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  432 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  433 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  434 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  435 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  437 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  438 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  439 */                       out.write("</a>");
/*  440 */                       if (request.getRemoteUser() != null)
/*  441 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  442 */                         out.println("&nbsp;");
/*  443 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  445 */                     out.write(32);
/*  446 */                     out.write(32);
/*  447 */                     out.write(10);
/*      */                     
/*  449 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  451 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  453 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  454 */                       out.write(10);
/*  455 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  457 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  458 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  459 */                       out.write("</a></td>\n  ");
/*      */                       
/*  461 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  462 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  463 */                       _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  465 */                       _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  466 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  467 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/*  469 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  470 */                           if (_jspx_meth_c_005fchoose_005f5(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                             return;
/*  472 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  473 */                           out.write("\n  </a></td>\n  ");
/*  474 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  475 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  479 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  480 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/*  483 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  484 */                       out.write(10);
/*  485 */                       out.write(32);
/*  486 */                       out.write(32);
/*      */                       
/*  488 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  489 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  490 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  492 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  493 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  494 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  496 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  498 */                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  499 */                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  500 */                           _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  501 */                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  502 */                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                             for (;;) {
/*  504 */                               out.write("\n   \t\t ");
/*      */                               
/*  506 */                               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  507 */                               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/*  508 */                               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                               
/*  510 */                               _jspx_th_c_005fwhen_005f10.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  511 */                               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/*  512 */                               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                 for (;;) {
/*  514 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  515 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                                     return;
/*  517 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  518 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  519 */                                   out.write("</a>\n   \t\t ");
/*  520 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/*  521 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  525 */                               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/*  526 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                               }
/*      */                               
/*  529 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/*  530 */                               out.write("\n   \t \t ");
/*      */                               
/*  532 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  533 */                               _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  534 */                               _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/*  535 */                               int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  536 */                               if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                 for (;;) {
/*  538 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  539 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                                     return;
/*  541 */                                   out.write("\" class=\"staticlinks\">");
/*  542 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  543 */                                   out.write("</a>\n   \t\t ");
/*  544 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  545 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  549 */                               if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  550 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                               }
/*      */                               
/*  553 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  554 */                               out.write("\n   \t");
/*  555 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  556 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  560 */                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  561 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                           }
/*      */                           
/*  564 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  565 */                           out.write("\n\t</td> \n  ");
/*  566 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  567 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  571 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  572 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  575 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  576 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  577 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  578 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  579 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  580 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  581 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  582 */                       out.write("</a></td>\n\t");
/*      */                       
/*  584 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  585 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  586 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  588 */                       _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  589 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  590 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  592 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  593 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  594 */                           out.write("</a></td>\n\t");
/*  595 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  596 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  600 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  601 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  604 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  605 */                       out.write(10);
/*  606 */                       out.write(9);
/*      */                       
/*  608 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  609 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  610 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  612 */                       _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  613 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  614 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  616 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  617 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  618 */                           out.write("</a></td>\n\t");
/*  619 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  620 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  624 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  625 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  628 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  629 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  630 */                       out.print(getHelpLink(helpKey));
/*  631 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  632 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  633 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  634 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  635 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  637 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  638 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  639 */                       out.write("</a>");
/*  640 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  642 */                         out.write("&nbsp;");
/*  643 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  644 */                         out.write("\n    \t\t\t\t\t");
/*  645 */                       } else { out.println("&nbsp;"); }
/*  646 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  648 */                     out.write(10);
/*  649 */                     out.write(32);
/*  650 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  651 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  655 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  656 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  659 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  660 */                 out.write(" \n\n\t");
/*  661 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()))
/*      */                 {
/*  663 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  665 */                 out.write("\n\n</table>\n");
/*  666 */                 out.write("\n              \t</td>\n        </tr>\n    <tr>\n    <td valign=\"top\">\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n        ");
/*      */                 
/*  668 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  669 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  670 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  672 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/*  674 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  675 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  676 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  677 */                   String msg = null;
/*  678 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  679 */                     out = _jspx_page_context.pushBody();
/*  680 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  681 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/*  683 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  685 */                     out.write("\n        <tr>\n          <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n\n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"96%\" >\n\t    \t\t<tr>\n\t    \t\t      <td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                    <td width=\"98%\" class=\"msg-table-width\"> &nbsp; ");
/*  686 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/*  688 */                     out.write("</td>\n\t    \t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n\n\n\n           </td>\n        </tr>\n        ");
/*  689 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  690 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  691 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  694 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  695 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  698 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  699 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/*  702 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  703 */                 out.write(32);
/*      */                 
/*  705 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  706 */                 _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  707 */                 _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  709 */                 _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  710 */                 int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  711 */                 if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                   for (;;) {
/*  713 */                     out.write("\n\n        <tr>\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\" >\n\t\t\t<tr>\n\t\t<td width=\"4%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"98%\" class=\"msg-table-width\">");
/*      */                     
/*  715 */                     MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  716 */                     _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  717 */                     _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                     
/*  719 */                     _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                     
/*  721 */                     _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  722 */                     int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  723 */                     if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  724 */                       String msg = null;
/*  725 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  726 */                         out = _jspx_page_context.pushBody();
/*  727 */                         _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  728 */                         _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                       }
/*  730 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  732 */                         out.write("\n                  ");
/*  733 */                         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                           return;
/*  735 */                         out.write("\n                  ");
/*  736 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  737 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  738 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  741 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  742 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  745 */                     if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  746 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                     }
/*      */                     
/*  749 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  750 */                     out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n</td>\n        </tr>\n        ");
/*  751 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  752 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  756 */                 if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  757 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                 }
/*      */                 
/*  760 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  761 */                 out.write("\n        <tr>\n          <td height=\"90%\" valign=\"top\" class=\"tdindent\">");
/*  762 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  764 */                 out.write("&nbsp;</td>\n        </tr>        \n\t<tr>\n\t</tr>\n      </table>\n       </div><!-- userAreaContainerDiv ends -->      \n        ");
/*  765 */                 if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  767 */                 out.write("       \n    ");
/*  768 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  769 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  773 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  774 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  777 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  778 */             out.write(10);
/*  779 */             out.write(32);
/*  780 */             out.write(32);
/*      */             
/*  782 */             OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  783 */             _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  784 */             _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*  785 */             int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  786 */             if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */               for (;;) {
/*  788 */                 out.write(10);
/*      */                 
/*  790 */                 boolean showIT360left = false;
/*  791 */                 ComponentContext compContext = (ComponentContext)pageContext.getAttribute("org.apache.struts.taglib.tiles.CompContext", 2);
/*  792 */                 if ((Constants.isIt360) && (compContext.getAttribute("LeftArea") != null))
/*      */                 {
/*  794 */                   if ((compContext.getAttribute("LeftArea").toString().contains("AdminLeftLinks")) || (compContext.getAttribute("LeftArea").toString().contains("AlarmDetailsLeftPage")) || (compContext.getAttribute("LeftArea").toString().contains("DiscoveryLeftLinks")) || (compContext.getAttribute("LeftArea").toString().contains("ActionLeftLinks")) || (compContext.getAttribute("LeftArea").toString().contains("searchLeft.jsp")))
/*      */                   {
/*  796 */                     showIT360left = true;
/*      */                   }
/*      */                 }
/*      */                 
/*  800 */                 boolean sqlleft = false;
/*  801 */                 if ((Constants.sqlManager) && (compContext.getAttribute("LeftArea") != null) && (compContext.getAttribute("LeftArea").toString().contains("AlarmLeftPage")))
/*      */                 {
/*  803 */                   sqlleft = true;
/*      */                 }
/*      */                 
/*  806 */                 boolean leftarea = true;
/*  807 */                 if (compContext.getAttribute("LeftArea") != null)
/*      */                 {
/*  809 */                   String component = compContext.getAttribute("LeftArea").toString() != null ? compContext.getAttribute("LeftArea").toString() : "";
/*  810 */                   if ((!showIT360left) && (!sqlleft) && (component.contains("/jsp/AdminLeftLinks.jsp")))
/*      */                   {
/*  812 */                     leftarea = false;
/*      */                   }
/*      */                 }
/*  815 */                 if ((OEMUtil.getOEMString("remove.header") != null) && (OEMUtil.getOEMString("remove.header").equals("true")))
/*      */                 {
/*      */ 
/*  818 */                   leftarea = false;
/*      */                 }
/*      */                 
/*      */ 
/*  822 */                 if ((request.isUserInRole("OPERATOR")) && (uri.contains("/downTimeScheduler.do")))
/*      */                 {
/*  824 */                   leftarea = false;
/*      */                 }
/*  826 */                 if (uri.contains("/serverinfo.do"))
/*      */                 {
/*  828 */                   leftarea = false;
/*      */                 }
/*  830 */                 String hideFields = request.getParameter("hideFieldsForIT360");
/*  831 */                 boolean hideFieldsForIT360 = (hideFields != null) && (hideFields.equals("true"));
/*  832 */                 if ((compContext.getAttribute("Header") != null) && (!hideFieldsForIT360)) {
/*  833 */                   out.write(10);
/*  834 */                   if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                     return;
/*  836 */                   out.write(10);
/*      */                 }
/*  838 */                 out.write("\n<div id=\"userAreaContainerDiv\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n  <tr>\n        ");
/*      */                 
/*  840 */                 if (((!OEMUtil.isRemove("hide.leftArea")) || (sqlleft) || (showIT360left)) && (!hideFieldsForIT360) && (leftarea)) {
/*  841 */                   out.write("\n\t      ");
/*      */                   
/*      */ 
/*  844 */                   if (!isSearchPage)
/*      */                   {
/*      */ 
/*  847 */                     out.write("\n<td width=\"16%\" height=\"8\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"150\"></td>\n    <td  width=\"84%\"  height=\"8\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\t\n    ");
/*      */                   }
/*      */                   
/*      */ 
/*  851 */                   out.write("\n\n  </tr>\n  <tr>\n\n  ");
/*      */                   
/*  853 */                   if (isSearchPage)
/*      */                   {
/*      */ 
/*  856 */                     out.write("\n\t  <td height=\"505\"  align=\"center\" valign=\"top\" class=\"leftcells\" width=\"14%\">\n   ");
/*      */                   }
/*      */                   else
/*      */                   {
/*  860 */                     out.write("\n      <td height=\"505\"  align=\"center\" valign=\"top\" class=\"leftcells\" >\n");
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*  865 */                   out.write("\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td colspan=\"2\" valign=\"top\">\n\n\n          ");
/*  866 */                   if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                     return;
/*  868 */                   out.write("</td>\n        </tr>\n      </table></td>");
/*      */                 }
/*  870 */                 else if (Constants.sqlManager)
/*      */                 {
/*      */ 
/*  873 */                   out.write("\n\n\t       ");
/*      */                   
/*  875 */                   if ((Constants.isIt360) && (compContext1.getAttribute("LeftArea").toString().contains("searchLeft.jsp")))
/*      */                   {
/*      */ 
/*  878 */                     out.write("\n\t  <td width=\"86%\"  height=\"8\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\n  \t ");
/*      */                   }
/*      */                   else
/*      */                   {
/*  882 */                     out.write("\n      \t<td   height=\"8\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t");
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*  887 */                   out.write("\n\n\n\t  </tr>\n\t  <tr>\n\t  ");
/*      */                 }
/*  889 */                 out.write("\n    <td valign=\"top\">\n\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n        ");
/*      */                 
/*  891 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  892 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/*  893 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                 
/*  895 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/*  897 */                 _jspx_th_html_005fmessages_005f2.setMessage("false");
/*  898 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/*  899 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/*  900 */                   String msg = null;
/*  901 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  902 */                     out = _jspx_page_context.pushBody();
/*  903 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/*  904 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/*  906 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/*  908 */                     out.write("\n        ");
/*  909 */                     if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/*  911 */                     out.write("\n\n              ");
/*  912 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/*  914 */                     out.write("<br>\n\n        ");
/*  915 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/*  916 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*  917 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  920 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  921 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  924 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/*  925 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/*  928 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/*  929 */                 out.write("\n        ");
/*  930 */                 if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/*  932 */                 out.write(32);
/*      */                 
/*  934 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  935 */                 _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  936 */                 _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                 
/*  938 */                 _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  939 */                 int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  940 */                 if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                   for (;;) {
/*  942 */                     out.write("\n        <tr id=\"it360statusHide\">\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n\t<tr>\n\t\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td  class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n     <tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n\t\t<tr>\n\t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"25\" width=\"25\"></td>\n                <td width=\"98%\" class=\"msg-table-width\">&nbsp;");
/*      */                     
/*  944 */                     MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  945 */                     _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/*  946 */                     _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                     
/*  948 */                     _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                     
/*  950 */                     _jspx_th_html_005fmessages_005f3.setMessage("true");
/*  951 */                     int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/*  952 */                     if (_jspx_eval_html_005fmessages_005f3 != 0) {
/*  953 */                       String msg = null;
/*  954 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  955 */                         out = _jspx_page_context.pushBody();
/*  956 */                         _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/*  957 */                         _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                       }
/*  959 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/*  961 */                         out.write(32);
/*  962 */                         if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                           return;
/*  964 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/*  965 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*  966 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  969 */                       if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  970 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  973 */                     if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/*  974 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                     }
/*      */                     
/*  977 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/*  978 */                     out.write("</td>\n\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n     </tr>\n\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n</tr>\n</table>\n\n\n</td>\n</tr>\n</table></td>\n        </tr>\n        ");
/*  979 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/*  980 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  984 */                 if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/*  985 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                 }
/*      */                 
/*  988 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  989 */                 out.write("\n        <tr>\n\n         ");
/*      */                 
/*  991 */                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  992 */                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/*  993 */                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                 
/*  995 */                 _jspx_th_logic_005fpresent_005f6.setRole("OPERATOR");
/*  996 */                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/*  997 */                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                   for (;;) {
/*  999 */                     out.write("\n\n\t\t");
/*      */                     
/* 1001 */                     String mes = request.getParameter("message");
/* 1002 */                     if ((mes != null) && (mes.equals("false")))
/*      */                     {
/* 1004 */                       out.write("\n\t\t<tr>\n\t\t<td height=\"46\" valign=\"top\" class=\"tdindent\">\n\t\t<table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n\t\t<tr>\n\t\t<td width=\"99%\">\n\n\n\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t<tr>\n\t\t\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t\t\t\t<tr>\n\t\t\t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t                <td width=\"98%\" class=\"bodytext\">");
/* 1005 */                       out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 1006 */                       out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t</tr>\n\t\t</table>\n\n\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t</tr>\n\t\t");
/*      */                     }
/* 1008 */                     out.write(10);
/* 1009 */                     out.write(10);
/* 1010 */                     out.write(9);
/* 1011 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 1012 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1016 */                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 1017 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                 }
/*      */                 
/* 1020 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 1021 */                 out.write("\n\n          <td height=\"90%\" valign=\"top\" class=\"tdindent\" id=\"BL_UserArea\"> ");
/* 1022 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/* 1024 */                 out.write("</td>\n        </tr>\n        <tr>\n          <td height=\"20%\" colspan=\"3\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t<tr>\n\t</tr>        \n      </table></td>\n  </tr>\n</table>\n</div><!-- userAreaContainerDiv ends -->\n");
/* 1025 */                 if (_jspx_meth_tiles_005finsert_005f5(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                   return;
/* 1027 */                 out.write(10);
/*      */                 
/* 1029 */                 Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                 
/* 1031 */                 out.write(10);
/* 1032 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1033 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1037 */             if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1038 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */             }
/*      */             
/* 1041 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1042 */             out.write(10);
/* 1043 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1044 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1048 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1049 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */         }
/*      */         else {
/* 1052 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1053 */           out.write("\n<script type=\"text/javascript\">\n\ttry\n\t{\n\t  form = document.forms[0];\n\t  if(form && form.action.indexOf(\"Search.do\")<0)  //NO I18N\n\t  {\n\t    $('body').prepend('<form id=\"mySearch\" action=\"/Search.do\" style=\"display:none;\"></form>');   //No I18N\n\t  }\n\t}\n\tcatch(err){\n\t}\n</script>\n");
/* 1054 */           out.write(10);
/* 1055 */           out.write(10);
/* 1056 */           if (Constants.isIt360) {
/* 1057 */             out.write("\n<script type=\"text/javascript\">\n\tvar iframe= parent.window.document.getElementById('_iframe_view');\n\tif(iframe != null)\n\t{\n\t\tvar iframeName = iframe.name;\n\t\tif(iframeName != null && iframeName == '_iframe_view')\n\t\t{\n\t\t\tvar frameDoc = iframe.contentDocument || iframe.contentWindow.document;\n\t\t\tvar e = frameDoc.getElementById(\"userAreaContainerDiv\");\n\t\t\tif(e!=null)\n\t\t\t{\n\t\t\t\te.id = \"userAreaContainerDiv_admin\";\n\t\t\t}\n\t\t\n\t\t\tvar footer = frameDoc.getElementById(\"footer-container\");\n\t\t\tif(footer!=null)\n\t\t\t{\n\t\t\t\tfooter.innerHTML=\"\";\n\t\t\t}\n\t\t}\n\t}\n</script>\n");
/*      */           }
/* 1059 */           out.write(10);
/* 1060 */           out.write(10);
/* 1061 */           out.write(10);
/* 1062 */           out.write(10);
/*      */           
/* 1064 */           if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1069 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 1070 */             out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/* 1071 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 1075 */           out.write("\n</body>\n</html>\n\n");
/*      */         }
/* 1077 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1078 */         out = _jspx_out;
/* 1079 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1080 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1081 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1084 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fimportAttribute_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1090 */     PageContext pageContext = _jspx_page_context;
/* 1091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1093 */     ImportAttributeTag _jspx_th_tiles_005fimportAttribute_005f0 = (ImportAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody.get(ImportAttributeTag.class);
/* 1094 */     _jspx_th_tiles_005fimportAttribute_005f0.setPageContext(_jspx_page_context);
/* 1095 */     _jspx_th_tiles_005fimportAttribute_005f0.setParent(null);
/*      */     
/* 1097 */     _jspx_th_tiles_005fimportAttribute_005f0.setName("title");
/* 1098 */     int _jspx_eval_tiles_005fimportAttribute_005f0 = _jspx_th_tiles_005fimportAttribute_005f0.doStartTag();
/* 1099 */     if (_jspx_th_tiles_005fimportAttribute_005f0.doEndTag() == 5) {
/* 1100 */       this._005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fimportAttribute_005f0);
/* 1101 */       return true;
/*      */     }
/* 1103 */     this._005fjspx_005ftagPool_005ftiles_005fimportAttribute_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fimportAttribute_005f0);
/* 1104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1109 */     PageContext pageContext = _jspx_page_context;
/* 1110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1112 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1113 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1114 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1116 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1118 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1119 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1120 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1122 */       return true;
/*      */     }
/* 1124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1130 */     PageContext pageContext = _jspx_page_context;
/* 1131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1133 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 1134 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 1135 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/* 1137 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 1138 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 1139 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 1140 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1141 */       return true;
/*      */     }
/* 1143 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1149 */     PageContext pageContext = _jspx_page_context;
/* 1150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1152 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1153 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1154 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 1156 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod && !empty customreloadperiod}");
/* 1157 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1158 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1160 */         out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1161 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1162 */           return true;
/* 1163 */         out.write(34);
/* 1164 */         out.write(62);
/* 1165 */         out.write(10);
/* 1166 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1167 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1171 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1172 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1173 */       return true;
/*      */     }
/* 1175 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1181 */     PageContext pageContext = _jspx_page_context;
/* 1182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1184 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1185 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1186 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1188 */     _jspx_th_c_005fout_005f1.setValue("${customreloadperiod}");
/* 1189 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1190 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1191 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1192 */       return true;
/*      */     }
/* 1194 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1200 */     PageContext pageContext = _jspx_page_context;
/* 1201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1203 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1204 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1205 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 1206 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1207 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1209 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1210 */           return true;
/* 1211 */         out.write(10);
/* 1212 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1213 */           return true;
/* 1214 */         out.write(10);
/* 1215 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1216 */           return true;
/* 1217 */         out.write(10);
/* 1218 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1219 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1223 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1224 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1225 */       return true;
/*      */     }
/* 1227 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1228 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1233 */     PageContext pageContext = _jspx_page_context;
/* 1234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1236 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1237 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1238 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1240 */     _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showApplications'}");
/* 1241 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1242 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1244 */         out.write("\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad(),setPref('home')\" onUnload=\"savePref('home');\">\n");
/* 1245 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1246 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1250 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1251 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1252 */       return true;
/*      */     }
/* 1254 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1260 */     PageContext pageContext = _jspx_page_context;
/* 1261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1263 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1264 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1265 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1267 */     _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showResourceTypes'}");
/* 1268 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1269 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1271 */         out.write("\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad(),setPref('monitor')\" onUnload=\"savePref('monitor');\">\n\n");
/* 1272 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1273 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1277 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1278 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1279 */       return true;
/*      */     }
/* 1281 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1287 */     PageContext pageContext = _jspx_page_context;
/* 1288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1290 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1291 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1292 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1293 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1294 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1296 */         out.write("\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad()\" >\n\n");
/* 1297 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1298 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1302 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1303 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1304 */       return true;
/*      */     }
/* 1306 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1312 */     PageContext pageContext = _jspx_page_context;
/* 1313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1315 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1316 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1317 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1319 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1321 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1322 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1324 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1325 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1327 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1328 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1329 */             return true;
/* 1330 */           out.write("\"\" class=\"staticlinks\">");
/* 1331 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1332 */             return true;
/* 1333 */           out.write("</a></td>\n");
/* 1334 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1335 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1339 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1340 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1343 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1344 */         out = _jspx_page_context.popBody(); }
/* 1345 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1347 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1348 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1355 */     PageContext pageContext = _jspx_page_context;
/* 1356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1358 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1359 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1360 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1362 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABLINK}");
/* 1363 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1364 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1365 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1366 */       return true;
/*      */     }
/* 1368 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1374 */     PageContext pageContext = _jspx_page_context;
/* 1375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1377 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1378 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1379 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1381 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 1382 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1383 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1384 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1385 */       return true;
/*      */     }
/* 1387 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1393 */     PageContext pageContext = _jspx_page_context;
/* 1394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1396 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1397 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1398 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1400 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1402 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1403 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1405 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1406 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1408 */           out.write(10);
/* 1409 */           out.write(10);
/* 1410 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1411 */             return true;
/* 1412 */           out.write(10);
/* 1413 */           out.write(10);
/* 1414 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1415 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1419 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1420 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1423 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1424 */         out = _jspx_page_context.popBody(); }
/* 1425 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1427 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1428 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1435 */     PageContext pageContext = _jspx_page_context;
/* 1436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1438 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1439 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1440 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1441 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1442 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1444 */         out.write(10);
/* 1445 */         out.write(10);
/* 1446 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1447 */           return true;
/* 1448 */         out.write(10);
/* 1449 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1450 */           return true;
/* 1451 */         out.write(10);
/* 1452 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1453 */           return true;
/* 1454 */         out.write("\n\n\n\n\n");
/* 1455 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1456 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1460 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1461 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1462 */       return true;
/*      */     }
/* 1464 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1470 */     PageContext pageContext = _jspx_page_context;
/* 1471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1473 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1474 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1475 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1477 */     _jspx_th_c_005fwhen_005f3.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1478 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1479 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1481 */         out.write(10);
/* 1482 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1483 */           return true;
/* 1484 */         out.write(10);
/* 1485 */         out.write(32);
/* 1486 */         out.write(32);
/* 1487 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1488 */           return true;
/* 1489 */         out.write(10);
/* 1490 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1491 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1495 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1496 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1497 */       return true;
/*      */     }
/* 1499 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1505 */     PageContext pageContext = _jspx_page_context;
/* 1506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1508 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1509 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1510 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1512 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1513 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1514 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1516 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1517 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1518 */           return true;
/* 1519 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1520 */           return true;
/* 1521 */         out.write("\n  </a></td>\n  ");
/* 1522 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1523 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1527 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1528 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1529 */       return true;
/*      */     }
/* 1531 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1532 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1537 */     PageContext pageContext = _jspx_page_context;
/* 1538 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1540 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1541 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1542 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1543 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1544 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1546 */         out.write("\n  \t\t");
/* 1547 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1548 */           return true;
/* 1549 */         out.write("\n  \t\t");
/* 1550 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1551 */           return true;
/* 1552 */         out.write("\n  \t\t");
/* 1553 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1554 */           return true;
/* 1555 */         out.write("\n  \t");
/* 1556 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1557 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1561 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1562 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1563 */       return true;
/*      */     }
/* 1565 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1571 */     PageContext pageContext = _jspx_page_context;
/* 1572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1574 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1575 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1576 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1578 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1579 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1580 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1582 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1583 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1584 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1588 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1589 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1590 */       return true;
/*      */     }
/* 1592 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1598 */     PageContext pageContext = _jspx_page_context;
/* 1599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1601 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1602 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1603 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1605 */     _jspx_th_c_005fwhen_005f5.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1606 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1607 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1609 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1610 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1611 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1615 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1616 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1617 */       return true;
/*      */     }
/* 1619 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1625 */     PageContext pageContext = _jspx_page_context;
/* 1626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1628 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1629 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1630 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1631 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1632 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1634 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1635 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1640 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1641 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1642 */       return true;
/*      */     }
/* 1644 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1650 */     PageContext pageContext = _jspx_page_context;
/* 1651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1653 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1654 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1655 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 1657 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1658 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1659 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1660 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1661 */       return true;
/*      */     }
/* 1663 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1669 */     PageContext pageContext = _jspx_page_context;
/* 1670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1672 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1673 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1674 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1676 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1677 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1678 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1680 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1681 */         if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1682 */           return true;
/* 1683 */         out.write("\n\t</td> \n  ");
/* 1684 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1689 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1690 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1691 */       return true;
/*      */     }
/* 1693 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1699 */     PageContext pageContext = _jspx_page_context;
/* 1700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1702 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1703 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1704 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 1705 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1706 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 1708 */         out.write("\n   \t\t ");
/* 1709 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1710 */           return true;
/* 1711 */         out.write("\n   \t \t ");
/* 1712 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1713 */           return true;
/* 1714 */         out.write("\n   \t");
/* 1715 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1716 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1720 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1721 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1722 */       return true;
/*      */     }
/* 1724 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1730 */     PageContext pageContext = _jspx_page_context;
/* 1731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1733 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1734 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1735 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 1737 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1738 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1739 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 1741 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1742 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1743 */           return true;
/* 1744 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1745 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1746 */           return true;
/* 1747 */         out.write("</a>\n   \t\t ");
/* 1748 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1749 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1753 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1754 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1755 */       return true;
/*      */     }
/* 1757 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1758 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1763 */     PageContext pageContext = _jspx_page_context;
/* 1764 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1766 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1767 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1768 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 1770 */     _jspx_th_c_005fout_005f5.setValue("${globalconfig['defaultmonitorsview']}");
/* 1771 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1772 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1773 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1774 */       return true;
/*      */     }
/* 1776 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1782 */     PageContext pageContext = _jspx_page_context;
/* 1783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1785 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1786 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1787 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 1789 */     _jspx_th_c_005fout_005f6.setValue("${tab.TABNAME}");
/* 1790 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1791 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1792 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1793 */       return true;
/*      */     }
/* 1795 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1801 */     PageContext pageContext = _jspx_page_context;
/* 1802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1804 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1805 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1806 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 1807 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1808 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 1810 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1811 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1812 */           return true;
/* 1813 */         out.write("\" class=\"staticlinks\">");
/* 1814 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1815 */           return true;
/* 1816 */         out.write("</a>\n   \t\t ");
/* 1817 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1818 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1822 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1823 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1824 */       return true;
/*      */     }
/* 1826 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1832 */     PageContext pageContext = _jspx_page_context;
/* 1833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1835 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1836 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1837 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1839 */     _jspx_th_c_005fout_005f7.setValue("${globalconfig['defaultmonitorsview']}");
/* 1840 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1841 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1842 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1843 */       return true;
/*      */     }
/* 1845 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1851 */     PageContext pageContext = _jspx_page_context;
/* 1852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1854 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1855 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1856 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1858 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 1859 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1860 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1861 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1862 */       return true;
/*      */     }
/* 1864 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1870 */     PageContext pageContext = _jspx_page_context;
/* 1871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1873 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1874 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1875 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1877 */     _jspx_th_c_005fwhen_005f7.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 1878 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1879 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 1881 */         out.write(10);
/* 1882 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1883 */           return true;
/* 1884 */         out.write(10);
/* 1885 */         out.write(9);
/* 1886 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1887 */           return true;
/* 1888 */         out.write(10);
/* 1889 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1890 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1894 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1895 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1896 */       return true;
/*      */     }
/* 1898 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1904 */     PageContext pageContext = _jspx_page_context;
/* 1905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1907 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1908 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1909 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 1911 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1912 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1913 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1915 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1916 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1917 */           return true;
/* 1918 */         out.write("</a></td>\n\t");
/* 1919 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1920 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1924 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1925 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1926 */       return true;
/*      */     }
/* 1928 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1934 */     PageContext pageContext = _jspx_page_context;
/* 1935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1937 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1938 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1939 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1941 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 1942 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1943 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1945 */       return true;
/*      */     }
/* 1947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1953 */     PageContext pageContext = _jspx_page_context;
/* 1954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1956 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1957 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1958 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 1960 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 1961 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1962 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1964 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1965 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1966 */           return true;
/* 1967 */         out.write("</a></td>\n\t");
/* 1968 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1973 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1974 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1975 */       return true;
/*      */     }
/* 1977 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1983 */     PageContext pageContext = _jspx_page_context;
/* 1984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1986 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1987 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1988 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 1990 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 1991 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1992 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1993 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1994 */       return true;
/*      */     }
/* 1996 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2002 */     PageContext pageContext = _jspx_page_context;
/* 2003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2005 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2006 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2007 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2008 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2009 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2011 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 2012 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2013 */           return true;
/* 2014 */         out.write("\" class=\"staticlinks\">\t");
/* 2015 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2016 */           return true;
/* 2017 */         out.write("</a></td>\n");
/* 2018 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2019 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2023 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2037 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2040 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABLINK}");
/* 2041 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2042 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2043 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2044 */       return true;
/*      */     }
/* 2046 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2052 */     PageContext pageContext = _jspx_page_context;
/* 2053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2055 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2056 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2057 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2059 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABNAME}");
/* 2060 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2061 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2062 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2063 */       return true;
/*      */     }
/* 2065 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2071 */     PageContext pageContext = _jspx_page_context;
/* 2072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2074 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2075 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2076 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 2077 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2078 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 2080 */         out.write("\n  \t\t");
/* 2081 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2082 */           return true;
/* 2083 */         out.write("\n  \t\t");
/* 2084 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2085 */           return true;
/* 2086 */         out.write("\n  \t\t");
/* 2087 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2088 */           return true;
/* 2089 */         out.write("\n  \t");
/* 2090 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2091 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2095 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2096 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2097 */       return true;
/*      */     }
/* 2099 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2105 */     PageContext pageContext = _jspx_page_context;
/* 2106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2108 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2109 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2110 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2112 */     _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2113 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2114 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 2116 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 2117 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2118 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2122 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2123 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2124 */       return true;
/*      */     }
/* 2126 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2132 */     PageContext pageContext = _jspx_page_context;
/* 2133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2135 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2136 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 2137 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2139 */     _jspx_th_c_005fwhen_005f9.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2140 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 2141 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 2143 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2144 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 2145 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2149 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 2150 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 2151 */       return true;
/*      */     }
/* 2153 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 2154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2159 */     PageContext pageContext = _jspx_page_context;
/* 2160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2162 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2163 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2164 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 2165 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2166 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2168 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 2169 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2170 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2174 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2175 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2176 */       return true;
/*      */     }
/* 2178 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2184 */     PageContext pageContext = _jspx_page_context;
/* 2185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2187 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2188 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2189 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 2191 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 2192 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2193 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2194 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2195 */       return true;
/*      */     }
/* 2197 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2203 */     PageContext pageContext = _jspx_page_context;
/* 2204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2206 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2207 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2208 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2210 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2211 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2212 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2213 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2214 */       return true;
/*      */     }
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2222 */     PageContext pageContext = _jspx_page_context;
/* 2223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2225 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2226 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2227 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2229 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2231 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2232 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2233 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2234 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2235 */       return true;
/*      */     }
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2243 */     PageContext pageContext = _jspx_page_context;
/* 2244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2246 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2247 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2248 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2250 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2252 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2253 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2254 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2255 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2256 */       return true;
/*      */     }
/* 2258 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2264 */     PageContext pageContext = _jspx_page_context;
/* 2265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2267 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2268 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2269 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2271 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 2272 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2273 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2274 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2275 */       return true;
/*      */     }
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2283 */     PageContext pageContext = _jspx_page_context;
/* 2284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2286 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2287 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2288 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2290 */     _jspx_th_tiles_005finsert_005f1.setAttribute("footer");
/* 2291 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2292 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2293 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2294 */       return true;
/*      */     }
/* 2296 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2302 */     PageContext pageContext = _jspx_page_context;
/* 2303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2305 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2306 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2307 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2309 */     _jspx_th_tiles_005finsert_005f2.setAttribute("Header");
/* 2310 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2311 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2312 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2313 */       return true;
/*      */     }
/* 2315 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2321 */     PageContext pageContext = _jspx_page_context;
/* 2322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2324 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2325 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2326 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2328 */     _jspx_th_tiles_005finsert_005f3.setAttribute("LeftArea");
/* 2329 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2330 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2331 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2332 */       return true;
/*      */     }
/* 2334 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2340 */     PageContext pageContext = _jspx_page_context;
/* 2341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2343 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2344 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2345 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2347 */     _jspx_th_c_005fif_005f3.setTest("${empty firstrow}");
/* 2348 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2349 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2351 */         out.write("\n\n            <tr id=\"it360statusHide\">\n\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n\n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\" >&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t    \t\t\t<tr>\n\t    \t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                    <td width=\"98%\" class=\"msg-table-width\">");
/* 2352 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 2353 */           return true;
/* 2354 */         out.write("\n        \t\t");
/* 2355 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2356 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2360 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2361 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2362 */       return true;
/*      */     }
/* 2364 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2370 */     PageContext pageContext = _jspx_page_context;
/* 2371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2373 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2374 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2375 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2377 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 2379 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 2380 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2381 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2382 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2383 */       return true;
/*      */     }
/* 2385 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2391 */     PageContext pageContext = _jspx_page_context;
/* 2392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2394 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2395 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2396 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2398 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2400 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2401 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2402 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2403 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2404 */       return true;
/*      */     }
/* 2406 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2412 */     PageContext pageContext = _jspx_page_context;
/* 2413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2415 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2416 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2417 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2419 */     _jspx_th_c_005fif_005f4.setTest("${!empty firstrow}");
/* 2420 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2421 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2423 */         out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n\t    </table>\n\n\n\n\n\n            </td>\n            </tr>\n            </table></td>\n        </tr>\n        ");
/* 2424 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2425 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2429 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2430 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2431 */       return true;
/*      */     }
/* 2433 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2439 */     PageContext pageContext = _jspx_page_context;
/* 2440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2442 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2443 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2444 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2446 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2448 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2449 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2450 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2451 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2452 */       return true;
/*      */     }
/* 2454 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2455 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2460 */     PageContext pageContext = _jspx_page_context;
/* 2461 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2463 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2464 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2465 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2467 */     _jspx_th_tiles_005finsert_005f4.setAttribute("UserArea");
/* 2468 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2469 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2470 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2471 */       return true;
/*      */     }
/* 2473 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2474 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f5(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2479 */     PageContext pageContext = _jspx_page_context;
/* 2480 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2482 */     InsertTag _jspx_th_tiles_005finsert_005f5 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2483 */     _jspx_th_tiles_005finsert_005f5.setPageContext(_jspx_page_context);
/* 2484 */     _jspx_th_tiles_005finsert_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2486 */     _jspx_th_tiles_005finsert_005f5.setAttribute("footer");
/* 2487 */     int _jspx_eval_tiles_005finsert_005f5 = _jspx_th_tiles_005finsert_005f5.doStartTag();
/* 2488 */     if (_jspx_th_tiles_005finsert_005f5.doEndTag() == 5) {
/* 2489 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2490 */       return true;
/*      */     }
/* 2492 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2493 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\BasicLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */