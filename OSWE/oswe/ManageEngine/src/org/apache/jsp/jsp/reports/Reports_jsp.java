/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.tags.LoadTime;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.tiles.ComponentContext;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class Reports_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   83 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(5);
/*   84 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   85 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*   86 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   87 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   88 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  109 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  113 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  127 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  132 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  133 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  134 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  135 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  136 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  137 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  138 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  140 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.release();
/*  141 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  142 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  143 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  150 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  153 */     JspWriter out = null;
/*  154 */     Object page = this;
/*  155 */     JspWriter _jspx_out = null;
/*  156 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  160 */       response.setContentType("text/html;charset=UTF-8");
/*  161 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  163 */       _jspx_page_context = pageContext;
/*  164 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  165 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  166 */       session = pageContext.getSession();
/*  167 */       out = pageContext.getOut();
/*  168 */       _jspx_out = out;
/*      */       
/*  170 */       out.write("<!DOCTYPE html>\n");
/*  171 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  172 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*  173 */       out.write(10);
/*  174 */       out.write(10);
/*  175 */       out.write("\n\n\n\n\n");
/*      */       
/*  177 */       String addtotitle = "";
/*  178 */       if (Constants.getCategorytype().equals("LAMP")) {
/*  179 */         addtotitle = " Lamp Edition";
/*  180 */       } else if (Constants.getCategorytype().equals("DATABASE")) {
/*  181 */         addtotitle = " DATABASE Edition";
/*      */       }
/*      */       
/*  184 */       out.write("\n<html>\n<head>\n");
/*  185 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  186 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  188 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  189 */       out.write(10);
/*  190 */       out.write(10);
/*  191 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  192 */       out.write(10);
/*  193 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  194 */       out.print(request.getContextPath());
/*  195 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  196 */       out.print(request.getContextPath());
/*  197 */       out.write("'); //No I18N\n</script>\n");
/*  198 */       if (Constants.isIt360) {
/*  199 */         out.write("<script src='");
/*  200 */         out.print(request.getContextPath());
/*  201 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  203 */       out.write("\n<SCRIPT src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n");
/*      */       
/*  205 */       String containterAreaDiv = "userAreaContainerDiv_Temp";
/*  206 */       String footerJsp = "/jsp/footer.jsp";
/*  207 */       if (Constants.isIt360)
/*      */       {
/*  209 */         containterAreaDiv = "";
/*  210 */         footerJsp = "/jsp/test.jsp";
/*      */         
/*  212 */         out.write("\n\t\t<title>");
/*  213 */         out.print(OEMUtil.getOEMString("rebrand.product.name"));
/*  214 */         out.print(addtotitle);
/*  215 */         out.write(45);
/*  216 */         out.write(32);
/*  217 */         out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  218 */         out.write("</title>\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  224 */         out.write("\n\t\t<title>");
/*  225 */         out.print(OEMUtil.getOEMString("product.name"));
/*  226 */         out.print(addtotitle);
/*  227 */         out.write(45);
/*  228 */         out.write(32);
/*  229 */         out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  230 */         out.write("</title>\n");
/*      */       }
/*      */       
/*      */ 
/*  234 */       out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
/*      */       
/*  236 */       if (Constants.isIt360)
/*      */       {
/*  238 */         out.write("\n<style type=\"text/css\">\n.monitorsheading\n{\n\tdisplay: block;\n}\n</style>\n");
/*      */       }
/*      */       
/*      */ 
/*  242 */       out.write("\n\n\n<script>\n\nfunction logout()\n{\n\tlocation.href=\"/Logout.do\";\n}\n\n</script>\n</head>\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\"  >\n\n");
/*      */       
/*  244 */       request.setAttribute("systime", new Date());
/*      */       
/*  246 */       out.write(10);
/*  247 */       out.write(10);
/*  248 */       out.write(10);
/*      */       
/*  250 */       com.adventnet.appmanager.reporting.form.ReportForm frm = (com.adventnet.appmanager.reporting.form.ReportForm)request.getAttribute("ReportForm");
/*      */       
/*  252 */       String aMe = request.getParameter("actionMethod");
/*      */       
/*  254 */       request.setAttribute("reportmethod", aMe);
/*  255 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  256 */       isPrint = session.getAttribute("PRINTER_FRIENDLY") != null ? (String)session.getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  257 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  259 */       if ((PluginUtil.isPlugin()) && (!"getReportIndex".equalsIgnoreCase(aMe))) {
/*  260 */         request.setAttribute("PRINTER_FRIENDLY", "false");
/*      */       }
/*      */       
/*  263 */       String strTime = request.getParameter("strTime");
/*  264 */       String endTime = request.getParameter("endTime");
/*      */       
/*  266 */       out.write(10);
/*  267 */       out.write(10);
/*      */       
/*  269 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  270 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  271 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  273 */       _jspx_th_c_005fif_005f0.setTest("${reportmethod != \"getReportIndex\"}");
/*  274 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  275 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  277 */           out.write("\n<table class=\"darkheaderbg\" height=\"55\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td>\n<span class=\"headingboldwhite\">\n    ");
/*      */           
/*  279 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  280 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  281 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/*  282 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  283 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  285 */               out.write("\n    ");
/*  286 */               if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                 return;
/*  288 */               out.write("\n    ");
/*      */               
/*  290 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  291 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  292 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  293 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  294 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                 for (;;) {
/*  296 */                   out.write("\n\t  ");
/*  297 */                   out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  298 */                   out.write(" \n    ");
/*  299 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  300 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  304 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  305 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */               }
/*      */               
/*  308 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  309 */               out.write("\n     ");
/*  310 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  311 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  315 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  316 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/*  319 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  320 */           out.write("\n </span>\n</td>\n</tr>\n</table>\n");
/*  321 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  322 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  326 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  327 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  330 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  331 */         out.write(10);
/*  332 */         out.write(10);
/*      */         
/*  334 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  335 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  336 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  337 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  338 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  340 */             out.write("\n    ");
/*      */             
/*  342 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  343 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  344 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  346 */             _jspx_th_c_005fwhen_005f1.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  347 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  348 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */               for (;;) {
/*  350 */                 out.write("\n     <!-- The below line of code is included to fix issues while using the reports page in printer_friendly view. \n    Ideally we should avoid using documents.forms[] in pages. As workaround we included a dummy form in printer friendly view.-->\n    ");
/*  351 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  352 */                 out.write("\n    <script>\n      document.write('<form name=\"dummyform\"></form>');\n    </script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n  <tr>\n    <td width=\"100%\" valign=\"top\" class=\"tdindent\" >\n      <table width=\"98%\"   border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td>");
/*  353 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                   return;
/*  355 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/*  357 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  358 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  359 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                 
/*  361 */                 _jspx_th_c_005fif_005f1.setTest("${param.actionMethod != 'getCAMAttributes'}");
/*  362 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  363 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/*  365 */                     out.write("\n        ");
/*      */                     
/*  367 */                     if ((request.getParameter("bsm") != null) && (request.getParameter("bsm").equalsIgnoreCase("true")) && (request.getParameter("period").equals("4")))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*  372 */                       out.write("\n        <tr>\n          <td><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                       
/*  374 */                       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  375 */                       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  376 */                       _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  378 */                       _jspx_th_c_005fif_005f2.setTest("${(strTime !='0') && (strTime!=null)}");
/*  379 */                       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  380 */                       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                         for (;;) {
/*  382 */                           out.write(" \n                <td height=\"33\" class=\"arial11italics\">");
/*  383 */                           out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                           
/*  385 */                           out.write(32);
/*  386 */                           out.print(strTime);
/*  387 */                           out.write("\n                  ");
/*  388 */                           out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                           
/*  390 */                           out.write(32);
/*  391 */                           out.print(endTime);
/*  392 */                           out.write("</td>\n                ");
/*  393 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  394 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  398 */                       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  399 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                       }
/*      */                       
/*  402 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  403 */                       out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*      */                     }
/*      */                     else
/*      */                     {
/*  407 */                       out.write("\n        <tr>\n          <td><table width=\"98%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                       
/*  409 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  410 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  411 */                       _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                       
/*  413 */                       _jspx_th_c_005fif_005f3.setTest("${(strTime !='0') && (strTime!=null)}");
/*  414 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  415 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/*  417 */                           out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/*  418 */                           out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                           
/*  420 */                           out.write(32);
/*  421 */                           if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                             return;
/*  423 */                           out.write("\n                  ");
/*  424 */                           out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                           
/*  426 */                           out.write(32);
/*  427 */                           if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                             return;
/*  429 */                           out.write("</td>\n                ");
/*  430 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  431 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  435 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  436 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/*  439 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  440 */                       out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*      */                     }
/*      */                     
/*      */ 
/*  444 */                     out.write("\n        ");
/*  445 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  446 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  450 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  451 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/*  454 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  455 */                 out.write(" </table></td>\n    </tr>\n    ");
/*  456 */                 if (!PluginUtil.isPlugin()) {
/*  457 */                   out.write("\n\n        <tr>\n            <td height=\"20%\" class=\"tdindent\">\n            <table border=\"0\" cellpadding=\"0\" width=\"100%\"><tr>\n\t                <td align=\"left\">\n\t                <span class=\"bodytext\">");
/*  458 */                   if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                     return;
/*  460 */                   out.write("  <span class=\"bodytextbold\">");
/*  461 */                   if (_jspx_meth_am_005ftimestamp_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                     return;
/*  463 */                   out.write("</span> ");
/*  464 */                   out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                   
/*  466 */                   out.write("</span>\n\t                </td>\n\t                <td align=\"right\">\n\t                <span class=\"bodytext\">");
/*  467 */                   if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                     return;
/*  469 */                   out.write(32);
/*  470 */                   out.write(58);
/*  471 */                   out.write(32);
/*  472 */                   if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                     return;
/*  474 */                   out.write("</span>\n\t               </td>\n\t               </tr>\n           </table>\n            </td>\n    </tr>\n    ");
/*      */                 }
/*  476 */                 out.write("\n    ");
/*  477 */                 out.write("\n    ");
/*      */                 
/*      */ 
/*  480 */                 if (Constants.isIt360)
/*      */                 {
/*  482 */                   out.write("\n    <tr>\n\t    <td align=\"center\">");
/*  483 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/*  484 */                   out.write("</td>\n    </tr>\n    ");
/*      */                 }
/*      */                 
/*      */ 
/*  488 */                 out.write("\n    ");
/*  489 */                 out.write("\n\n\n</table>\n\n\n    ");
/*  490 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  491 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  495 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  496 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */             }
/*      */             
/*  499 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  500 */             out.write("\n\n    ");
/*      */             
/*  502 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  503 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  504 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  506 */             _jspx_th_c_005fwhen_005f2.setTest("${reportmethod == \"generateWeeklyMonthlyOutageReport\"}");
/*  507 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  508 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  510 */                 out.write("\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n   <td>\n <div id='hideleftarea' style='display:none'>\n     ");
/*  511 */                 if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  513 */                 out.write(" </div></td>\n\n    <td  valign=\"top\" class=\"tdindent\" >");
/*  514 */                 if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  516 */                 out.write("\n      <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"clear: left;\">\n\n        \n\t        <tr>\n\t          <td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n      \n        <tr>\n          <td colspan=\"2\">");
/*  517 */                 if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  519 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/*  521 */                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  522 */                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  523 */                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  525 */                 _jspx_th_c_005fif_005f4.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport'}");
/*  526 */                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  527 */                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                   for (;;) {
/*  529 */                     out.write("\n        <tr>\n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/*  531 */                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  532 */                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  533 */                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                     
/*  535 */                     _jspx_th_c_005fif_005f5.setTest("${(strTime !='0') && (strTime!=null)}");
/*  536 */                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  537 */                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                       for (;;) {
/*  539 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/*  540 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/*  542 */                         out.write("\n                  ");
/*  543 */                         if (_jspx_meth_fmt_005fformatDate_005f3(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                           return;
/*  545 */                         out.write(32);
/*  546 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/*  548 */                         out.write("\n                  ");
/*  549 */                         if (_jspx_meth_fmt_005fformatDate_005f4(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                           return;
/*  551 */                         out.write("</td>\n                ");
/*  552 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  553 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  557 */                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  558 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                     }
/*      */                     
/*  561 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  562 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*  563 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  564 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  568 */                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  569 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                 }
/*      */                 
/*  572 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  573 */                 out.write("\n\n\n      </table></td>\n  </tr>\n  ");
/*      */                 
/*  575 */                 request.setAttribute("systime", new Date());
/*      */                 
/*  577 */                 out.write("\n\n  <tr>\n\n    <td height=\"20%\" class=\"tdindent\" colspan='2'> <table border=\"0\" cellpadding=\"0\" width=\"100%\">\n        <tr>\n          <td align=\"left\"> <span class=\"bodytext\">");
/*  578 */                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  580 */                 out.write("\n            : <span class=\"bodytextbold\">");
/*  581 */                 if (_jspx_meth_am_005ftimestamp_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  583 */                 out.write("</span>\n            ");
/*  584 */                 out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                 
/*  586 */                 out.write(".</span>\n          </td>\n          <td align=\"right\"> <span class=\"bodytext\">");
/*  587 */                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  589 */                 out.write("\n            : ");
/*  590 */                 if (_jspx_meth_fmt_005fformatDate_005f5(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  592 */                 out.write("</span> </td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*  593 */                 out.write(10);
/*  594 */                 out.write(32);
/*  595 */                 out.write(32);
/*      */                 
/*  597 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                 {
/*  599 */                   out.write("\n    <tr>\n\t    <td></td>\n    <td align=\"center\">");
/*  600 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/*  601 */                   out.write("</td>\n  </tr>\n  ");
/*      */                 }
/*      */                 
/*      */ 
/*  605 */                 out.write("\n    ");
/*  606 */                 out.write("\n</table>\n\n\n    ");
/*  607 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  608 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  612 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  613 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  616 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  617 */             out.write("\n       ");
/*      */             
/*  619 */             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  620 */             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  621 */             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  623 */             _jspx_th_c_005fwhen_005f3.setTest("${reportmethod == \"generateAvailabilitySnapShotReport\"}");
/*  624 */             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  625 */             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */               for (;;) {
/*  627 */                 out.write("\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\"  cellpadding=\"0\">\n  <tr>\n    <td>\n <div id='hideleftarea' style='display:none'>\n      ");
/*  628 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  630 */                 out.write("</div></td>\n\n    <td  valign=\"top\" class=\"tdindent\" >");
/*  631 */                 if (_jspx_meth_tiles_005finsert_005f5(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  633 */                 out.write("\n      <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"clear: left;\">\n\n        <tr>\n                 <td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n       \n        <tr>\n          <td colspan=\"2\">");
/*  634 */                 if (_jspx_meth_tiles_005finsert_005f6(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  636 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/*  638 */                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  639 */                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  640 */                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                 
/*  642 */                 _jspx_th_c_005fif_005f6.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport'}");
/*  643 */                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  644 */                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                   for (;;) {
/*  646 */                     out.write("\n        <tr>\n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/*  648 */                     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  649 */                     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  650 */                     _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                     
/*  652 */                     _jspx_th_c_005fif_005f7.setTest("${(strTime !='0') && (strTime!=null)}");
/*  653 */                     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  654 */                     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                       for (;;) {
/*  656 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/*  657 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/*  659 */                         out.write("\n                  ");
/*  660 */                         if (_jspx_meth_fmt_005fformatDate_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                           return;
/*  662 */                         out.write(32);
/*  663 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/*  665 */                         out.write("\n                  ");
/*  666 */                         if (_jspx_meth_fmt_005fformatDate_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                           return;
/*  668 */                         out.write("</td>\n                ");
/*  669 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/*  670 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  674 */                     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/*  675 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                     }
/*      */                     
/*  678 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*  679 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*  680 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/*  681 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  685 */                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/*  686 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                 }
/*      */                 
/*  689 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*  690 */                 out.write("\n\n\n      </table></td>\n  </tr>\n  ");
/*      */                 
/*  692 */                 request.setAttribute("systime", new Date());
/*      */                 
/*  694 */                 out.write("\n\n  <tr>\n\n    <td height=\"20%\" class=\"tdindent\" colspan='2'> <table border=\"0\" cellpadding=\"0\" width=\"100%\">\n        <tr>\n          <td align=\"left\"> <span class=\"bodytext\">");
/*  695 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  697 */                 out.write("\n            : <span class=\"bodytextbold\">");
/*  698 */                 if (_jspx_meth_am_005ftimestamp_005f2(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  700 */                 out.write("</span>\n            ");
/*  701 */                 out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                 
/*  703 */                 out.write(".</span>\n          </td>\n          <td align=\"right\"> <span class=\"bodytext\">");
/*  704 */                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  706 */                 out.write("\n            : ");
/*  707 */                 if (_jspx_meth_fmt_005fformatDate_005f8(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                   return;
/*  709 */                 out.write("</span> </td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*  710 */                 out.write(10);
/*  711 */                 out.write(32);
/*  712 */                 out.write(32);
/*      */                 
/*  714 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                 {
/*  716 */                   out.write("\n  <tr>\n\t  <td> </td>\n\t  <td align=\"center\">");
/*  717 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/*  718 */                   out.write("</td>\n      </tr>\n  ");
/*      */                 }
/*      */                 
/*      */ 
/*  722 */                 out.write("\n    ");
/*  723 */                 out.write("\n</table>\n\n\n    ");
/*  724 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  725 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  729 */             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  730 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */             }
/*      */             
/*  733 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  734 */             out.write("\n       ");
/*      */             
/*  736 */             WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  737 */             _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  738 */             _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  740 */             _jspx_th_c_005fwhen_005f4.setTest("${reportmethod == \"generatePeriodAvailabilityReport\"}");
/*  741 */             int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  742 */             if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */               for (;;) {
/*  744 */                 out.write("\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>\n <div id='hideleftarea' style='display:none'>\n     ");
/*  745 */                 if (_jspx_meth_tiles_005finsert_005f7(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  747 */                 out.write("</div></td>\n\n    <td  valign=\"top\" class=\"tdindent\" >");
/*  748 */                 if (_jspx_meth_tiles_005finsert_005f8(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  750 */                 out.write("\n      <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"clear: left;\">\n\n        <tr>\n\t <td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n       \n        <tr>\n          <td colspan=\"2\">");
/*  751 */                 if (_jspx_meth_tiles_005finsert_005f9(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  753 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/*  755 */                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  756 */                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  757 */                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                 
/*  759 */                 _jspx_th_c_005fif_005f8.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport'}");
/*  760 */                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  761 */                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                   for (;;) {
/*  763 */                     out.write("\n        <tr>\n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/*  765 */                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  766 */                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  767 */                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f8);
/*      */                     
/*  769 */                     _jspx_th_c_005fif_005f9.setTest("${(strTime !='0') && (strTime!=null)}");
/*  770 */                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  771 */                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                       for (;;) {
/*  773 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/*  774 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/*  776 */                         out.write("\n                  ");
/*  777 */                         if (_jspx_meth_fmt_005fformatDate_005f9(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                           return;
/*  779 */                         out.write(32);
/*  780 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/*  782 */                         out.write("\n                  ");
/*  783 */                         if (_jspx_meth_fmt_005fformatDate_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                           return;
/*  785 */                         out.write("</td>\n                ");
/*  786 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  787 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  791 */                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  792 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                     }
/*      */                     
/*  795 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  796 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*  797 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  798 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  802 */                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  803 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                 }
/*      */                 
/*  806 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  807 */                 out.write("\n\n\n      </table></td>\n  </tr>\n  ");
/*      */                 
/*  809 */                 request.setAttribute("systime", new Date());
/*      */                 
/*  811 */                 out.write("\n\n  <tr>\n\n    <td height=\"20%\" class=\"tdindent\" colspan='2'> <table border=\"0\" cellpadding=\"0\" width=\"100%\">\n        <tr>\n          <td align=\"left\"> <span class=\"bodytext\">");
/*  812 */                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  814 */                 out.write("\n            : <span class=\"bodytextbold\">");
/*  815 */                 if (_jspx_meth_am_005ftimestamp_005f3(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  817 */                 out.write("</span>\n            ");
/*  818 */                 out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                 
/*  820 */                 out.write(".</span>\n          </td>\n          <td align=\"right\"> <span class=\"bodytext\">");
/*  821 */                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  823 */                 out.write("\n            : ");
/*  824 */                 if (_jspx_meth_fmt_005fformatDate_005f11(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                   return;
/*  826 */                 out.write("</span> </td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*  827 */                 out.write(10);
/*  828 */                 out.write(32);
/*  829 */                 out.write(32);
/*      */                 
/*  831 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                 {
/*  833 */                   out.write("\n    <tr>\n\t<td></td>\n    <td align=\"center\">");
/*  834 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/*  835 */                   out.write("</td>\n  </tr>\n  ");
/*      */                 }
/*      */                 
/*      */ 
/*  839 */                 out.write("\n    ");
/*  840 */                 out.write("\n\n</table>\n\n\n    ");
/*  841 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  842 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  846 */             if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  847 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */             }
/*      */             
/*  850 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  851 */             out.write(10);
/*  852 */             out.write(32);
/*  853 */             out.write(32);
/*      */             
/*  855 */             WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  856 */             _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  857 */             _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  859 */             _jspx_th_c_005fwhen_005f5.setTest("${reportmethod == \"generateGlanceReport\"}");
/*  860 */             int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  861 */             if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */               for (;;) {
/*  863 */                 out.write("\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  ");
/*  864 */                 out.write("\n\n\n              ");
/*  865 */                 out.write("\n\n\t<tr>\n\t <td>\n     <div id='hideleftarea' style='display:none'>\n     ");
/*  866 */                 if (_jspx_meth_tiles_005finsert_005f10(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  868 */                 out.write("</div></td></tr>\n    <td  valign=\"top\" class=\"tdindent\" >    ");
/*  869 */                 if (_jspx_meth_tiles_005finsert_005f11(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  871 */                 out.write("\n      <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"clear: left;\">\n\n<tr>\n                 <td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n\n          <td colspan=\"2\">");
/*  872 */                 if (_jspx_meth_tiles_005finsert_005f12(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  874 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/*  876 */                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  877 */                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/*  878 */                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                 
/*  880 */                 _jspx_th_c_005fif_005f10.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport'}");
/*  881 */                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/*  882 */                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                   for (;;) {
/*  884 */                     out.write("\n        <tr>\n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/*  886 */                     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  887 */                     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/*  888 */                     _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fif_005f10);
/*      */                     
/*  890 */                     _jspx_th_c_005fif_005f11.setTest("${(strTime !='0') && (strTime!=null)}");
/*  891 */                     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/*  892 */                     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                       for (;;) {
/*  894 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/*  895 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/*  897 */                         out.write("\n                  ");
/*  898 */                         if (_jspx_meth_fmt_005fformatDate_005f12(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                           return;
/*  900 */                         out.write(32);
/*  901 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/*  903 */                         out.write("\n                  ");
/*  904 */                         if (_jspx_meth_fmt_005fformatDate_005f13(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                           return;
/*  906 */                         out.write("</td>\n                ");
/*  907 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/*  908 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  912 */                     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/*  913 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                     }
/*      */                     
/*  916 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*  917 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/*  918 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/*  919 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  923 */                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/*  924 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                 }
/*      */                 
/*  927 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*  928 */                 out.write("\n\n\n      </table></td>\n  </tr>\n  ");
/*      */                 
/*  930 */                 request.setAttribute("systime", new Date());
/*      */                 
/*  932 */                 out.write("\n  <tr>\n   ");
/*  933 */                 out.write("\n    <td height=\"20%\" class=\"tdindent\"> <table border=\"0\" cellpadding=\"0\" width=\"100%\">\n        <tr>\n\t</br>\n          <td align=\"left\"> <span class=\"bodytext\">");
/*  934 */                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  936 */                 out.write("\n            : <span class=\"bodytextbold\">");
/*  937 */                 if (_jspx_meth_am_005ftimestamp_005f4(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  939 */                 out.write("</span> ");
/*  940 */                 out.write("\n            ");
/*  941 */                 out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                 
/*  943 */                 out.write(".</span>\n          </td>\n          <td align=\"right\"> <span class=\"bodytext\">");
/*  944 */                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  946 */                 out.write("\n            : ");
/*  947 */                 if (_jspx_meth_fmt_005fformatDate_005f14(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/*      */                   return;
/*  949 */                 out.write("</span> </td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*  950 */                 out.write(10);
/*  951 */                 out.write(32);
/*  952 */                 out.write(32);
/*      */                 
/*  954 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                 {
/*  956 */                   out.write("\n    <tr>\n\n    <td align=\"center\">");
/*  957 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/*  958 */                   out.write("</td>\n  </tr>\n  ");
/*      */                 }
/*      */                 
/*      */ 
/*  962 */                 out.write("\n    ");
/*  963 */                 out.write("\n\n</table>\n\t ");
/*  964 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  965 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  969 */             if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  970 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */             }
/*      */             
/*  973 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  974 */             out.write(10);
/*  975 */             out.write(9);
/*      */             
/*  977 */             WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  978 */             _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  979 */             _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  981 */             _jspx_th_c_005fwhen_005f6.setTest("${reportmethod != \"getReportIndex\"}");
/*  982 */             int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  983 */             if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */               for (;;) {
/*  985 */                 out.write("\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  ");
/*  986 */                 out.write("\n\n\n              ");
/*  987 */                 out.write("\n\t<tr>\n\t <td>\n     <div id='hideleftarea' style='display:none'>\n     ");
/*  988 */                 if (_jspx_meth_tiles_005finsert_005f13(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/*  990 */                 out.write("</div></td></tr>\n    <td  valign=\"top\"  >    ");
/*  991 */                 if (_jspx_meth_tiles_005finsert_005f14(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/*  993 */                 out.write("\n      <table width=\"99%\" style=\"clear: left;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n       ");
/*  994 */                 if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/*  996 */                 out.write("\n        <tr>\n          <td colspan=\"2\">");
/*  997 */                 if (_jspx_meth_tiles_005finsert_005f15(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/*  999 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/* 1001 */                 IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1002 */                 _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1003 */                 _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                 
/* 1005 */                 _jspx_th_c_005fif_005f13.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport' && param.actionMethod != 'generateLicUsageReport'}");
/* 1006 */                 int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1007 */                 if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                   for (;;) {
/* 1009 */                     out.write("\n        <tr>\n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/* 1011 */                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1012 */                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1013 */                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f13);
/*      */                     
/* 1015 */                     _jspx_th_c_005fif_005f14.setTest("${(strTime !='0') && (strTime!=null)}");
/* 1016 */                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1017 */                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                       for (;;) {
/* 1019 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/* 1020 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/* 1022 */                         out.write("\n                  ");
/* 1023 */                         if (_jspx_meth_fmt_005fformatDate_005f15(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                           return;
/* 1025 */                         out.write(32);
/* 1026 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/* 1028 */                         out.write("\n                  ");
/* 1029 */                         if (_jspx_meth_fmt_005fformatDate_005f16(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                           return;
/* 1031 */                         out.write("</td>\n                ");
/* 1032 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1033 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1037 */                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1038 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                     }
/*      */                     
/* 1041 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1042 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/* 1043 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1044 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1048 */                 if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1049 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                 }
/*      */                 
/* 1052 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1053 */                 out.write("\n\n\n      </table></td>\n  </tr>\n  ");
/*      */                 
/* 1055 */                 request.setAttribute("systime", new Date());
/*      */                 
/* 1057 */                 out.write("\n  <tr>\n   ");
/* 1058 */                 out.write("\n    <td height=\"20%\" class=\"tdindent\"> <table border=\"0\" cellpadding=\"0\" width=\"100%\">\n        <tr>\n\t</br>\n          <td align=\"left\"> <span class=\"bodytext\">");
/* 1059 */                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/* 1061 */                 out.write("\n            : <span class=\"bodytextbold\">");
/* 1062 */                 if (_jspx_meth_am_005ftimestamp_005f5(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/* 1064 */                 out.write("</span>\n            ");
/* 1065 */                 out.print(FormatUtil.getString("am.reporttab.footer.messagemillisec.text"));
/*      */                 
/* 1067 */                 out.write(".</span>\n          </td>\n          <td align=\"right\"> <span class=\"bodytext\">");
/* 1068 */                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/* 1070 */                 out.write("\n            : ");
/* 1071 */                 if (_jspx_meth_fmt_005fformatDate_005f17(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                   return;
/* 1073 */                 out.write("</span> </td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/* 1074 */                 out.write(10);
/* 1075 */                 out.write(32);
/* 1076 */                 out.write(32);
/*      */                 
/* 1078 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                 {
/* 1080 */                   out.write("\n    <tr>\n    <td align=\"center\">");
/* 1081 */                   JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp" + ("/jsp/footer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideServerRespTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/* 1082 */                   out.write("</td>\n  </tr>\n  ");
/*      */                 }
/*      */                 
/*      */ 
/* 1086 */                 out.write("\n    ");
/* 1087 */                 out.write("\n\n</table>\n\t ");
/* 1088 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1089 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1093 */             if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1094 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */             }
/*      */             
/* 1097 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1098 */             out.write(10);
/* 1099 */             out.write(9);
/*      */             
/* 1101 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1102 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1103 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 1104 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1105 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */               for (;;) {
/* 1107 */                 out.write("\n          \n");
/*      */                 
/* 1109 */                 IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1110 */                 _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1111 */                 _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                 
/* 1113 */                 _jspx_th_c_005fif_005f15.setTest("${selectedscheme == 'slim'}");
/* 1114 */                 int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1115 */                 if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                   for (;;) {
/* 1117 */                     out.write(10);
/* 1118 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/* 1119 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 1120 */                     out.write(10);
/* 1121 */                     out.write(10);
/* 1122 */                     out.write(10);
/* 1123 */                     out.write(32);
/*      */                     
/* 1125 */                     String helpKey = (String)request.getAttribute("HelpKey");
/* 1126 */                     if (helpKey == null)
/*      */                     {
/* 1128 */                       String tileName = request.getParameter("TileName");
/* 1129 */                       if (tileName != null)
/*      */                       {
/* 1131 */                         if (tileName.equals(".ThresholdConf"))
/*      */                         {
/* 1133 */                           helpKey = "New Threshold Profile";
/*      */                         }
/* 1135 */                         else if (tileName.equals(".EmailActions"))
/*      */                         {
/* 1137 */                           helpKey = "Send E-mail";
/*      */                         }
/* 1139 */                         else if (tileName.equals(".SMSActions"))
/*      */                         {
/* 1141 */                           helpKey = "Send SMS";
/*      */                         }
/* 1143 */                         else if (tileName.equals(".ExecProg"))
/*      */                         {
/* 1145 */                           helpKey = "Execute Program";
/*      */                         }
/* 1147 */                         else if (tileName.equals(".SendTrap"))
/*      */                         {
/* 1149 */                           helpKey = "Send Trap";
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/* 1154 */                     out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/* 1155 */                     out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                     
/* 1157 */                     Enumeration en = request.getParameterNames();
/* 1158 */                     out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/* 1159 */                     while (en.hasMoreElements()) {
/* 1160 */                       String reqKey = (String)en.nextElement();
/* 1161 */                       String reqValue = request.getParameter(reqKey);
/* 1162 */                       if (!reqKey.equals("message"))
/*      */                       {
/*      */ 
/*      */ 
/* 1166 */                         if (reqKey.equals("depDeviceId"))
/*      */                         {
/* 1168 */                           out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                         }
/* 1170 */                         else if (reqKey.equals("selectedMonitors"))
/*      */                         {
/* 1172 */                           out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                         }
/*      */                         else
/*      */                         {
/* 1176 */                           out.print("&" + reqKey + "=" + reqValue);
/*      */                         }
/*      */                       }
/*      */                     }
/* 1180 */                     out.println("\";");
/*      */                     
/* 1182 */                     out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                     
/* 1184 */                     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1185 */                     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 1186 */                     _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fif_005f15);
/*      */                     
/* 1188 */                     _jspx_th_c_005fif_005f16.setTest("${selectedscheme == 'slim'}");
/* 1189 */                     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 1190 */                     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                       for (;;) {
/* 1192 */                         out.write(10);
/* 1193 */                         out.write(10);
/*      */                         
/* 1195 */                         if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                         {
/* 1197 */                           request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                           
/* 1199 */                           out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/* 1200 */                           out.print(OEMUtil.getOEMString("am.header.logo"));
/* 1201 */                           out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/* 1202 */                           if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 1204 */                           out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/* 1205 */                           out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/* 1206 */                           out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/* 1207 */                           out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/* 1208 */                           out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/* 1209 */                           out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 1210 */                           out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/* 1211 */                           out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 1212 */                           out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/* 1213 */                           out.print(getHelpLink(helpKey));
/* 1214 */                           out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/* 1215 */                           if (!OEMUtil.isRemove("am.personalize.remove")) {
/* 1216 */                             out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/* 1217 */                             out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/* 1218 */                             out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                           }
/* 1220 */                           out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/* 1221 */                           out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/* 1222 */                           out.write("</a>");
/* 1223 */                           if (request.getRemoteUser() != null)
/* 1224 */                             out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/* 1225 */                             out.println("&nbsp;");
/* 1226 */                           out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                         }
/* 1228 */                         out.write(32);
/* 1229 */                         out.write(32);
/* 1230 */                         out.write(10);
/*      */                         
/* 1232 */                         if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                         {
/* 1234 */                           request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                           
/* 1236 */                           out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/* 1237 */                           out.write(10);
/* 1238 */                           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 1240 */                           out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/* 1241 */                           out.print(FormatUtil.getString("am.webclient.hometab.text"));
/* 1242 */                           out.write("</a></td>\n  ");
/*      */                           
/* 1244 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1245 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 1246 */                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 1248 */                           _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 1249 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 1250 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 1252 */                               out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1253 */                               if (_jspx_meth_c_005fchoose_005f5(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                 return;
/* 1255 */                               out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 1256 */                               out.write("\n  </a></td>\n  ");
/* 1257 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 1258 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1262 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 1263 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                           }
/*      */                           
/* 1266 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 1267 */                           out.write(10);
/* 1268 */                           out.write(32);
/* 1269 */                           out.write(32);
/*      */                           
/* 1271 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1272 */                           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1273 */                           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 1275 */                           _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 1276 */                           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1277 */                           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                             for (;;) {
/* 1279 */                               out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                               
/* 1281 */                               ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1282 */                               _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1283 */                               _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/* 1284 */                               int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1285 */                               if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                 for (;;) {
/* 1287 */                                   out.write("\n   \t\t ");
/*      */                                   
/* 1289 */                                   WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1290 */                                   _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1291 */                                   _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                   
/* 1293 */                                   _jspx_th_c_005fwhen_005f14.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1294 */                                   int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1295 */                                   if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                     for (;;) {
/* 1297 */                                       out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1298 */                                       if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f14, _jspx_page_context))
/*      */                                         return;
/* 1300 */                                       out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1301 */                                       out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 1302 */                                       out.write("</a>\n   \t\t ");
/* 1303 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1304 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1308 */                                   if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1309 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                   }
/*      */                                   
/* 1312 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1313 */                                   out.write("\n   \t \t ");
/*      */                                   
/* 1315 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1316 */                                   _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1317 */                                   _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1318 */                                   int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1319 */                                   if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                     for (;;) {
/* 1321 */                                       out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1322 */                                       if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                                         return;
/* 1324 */                                       out.write("\" class=\"staticlinks\">");
/* 1325 */                                       out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 1326 */                                       out.write("</a>\n   \t\t ");
/* 1327 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1328 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1332 */                                   if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1333 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                   }
/*      */                                   
/* 1336 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1337 */                                   out.write("\n   \t");
/* 1338 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1339 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1343 */                               if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1344 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                               }
/*      */                               
/* 1347 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1348 */                               out.write("\n\t</td> \n  ");
/* 1349 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1350 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1354 */                           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1355 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                           }
/*      */                           
/* 1358 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1359 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/* 1360 */                           out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 1361 */                           out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/* 1362 */                           out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/* 1363 */                           out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/* 1364 */                           out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/* 1365 */                           out.write("</a></td>\n\t");
/*      */                           
/* 1367 */                           PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1368 */                           _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 1369 */                           _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 1371 */                           _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 1372 */                           int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 1373 */                           if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                             for (;;) {
/* 1375 */                               out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1376 */                               out.print(FormatUtil.getString("am.webclient.admintab.text"));
/* 1377 */                               out.write("</a></td>\n\t");
/* 1378 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 1379 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1383 */                           if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 1384 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                           }
/*      */                           
/* 1387 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 1388 */                           out.write(10);
/* 1389 */                           out.write(9);
/*      */                           
/* 1391 */                           PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1392 */                           _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 1393 */                           _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 1395 */                           _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 1396 */                           int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 1397 */                           if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                             for (;;) {
/* 1399 */                               out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1400 */                               out.print(FormatUtil.getString("am.webclient.admintab.text"));
/* 1401 */                               out.write("</a></td>\n\t");
/* 1402 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 1403 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1407 */                           if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 1408 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                           }
/*      */                           
/* 1411 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 1412 */                           out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/* 1413 */                           out.print(getHelpLink(helpKey));
/* 1414 */                           out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/* 1415 */                           if (!OEMUtil.isRemove("am.personalize.remove")) {
/* 1416 */                             out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/* 1417 */                             out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/* 1418 */                             out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                           }
/* 1420 */                           out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/* 1421 */                           out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/* 1422 */                           out.write("</a>");
/* 1423 */                           if (request.getRemoteUser() != null)
/*      */                           {
/* 1425 */                             out.write("&nbsp;");
/* 1426 */                             out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/* 1427 */                             out.write("\n    \t\t\t\t\t");
/* 1428 */                           } else { out.println("&nbsp;"); }
/* 1429 */                           out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                         }
/* 1431 */                         out.write(10);
/* 1432 */                         out.write(32);
/* 1433 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 1434 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1438 */                     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 1439 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                     }
/*      */                     
/* 1442 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1443 */                     out.write(" \n\n\t");
/* 1444 */                     if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!PluginUtil.isPlugin()))
/*      */                     {
/* 1446 */                       out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                     }
/* 1448 */                     out.write("\n\n</table>\n");
/* 1449 */                     out.write("\n<form action=\"/Search.do\" style=\"display:inline;\">\n\n</form>\n");
/* 1450 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1451 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1455 */                 if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1456 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                 }
/*      */                 
/* 1459 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1460 */                 out.write(10);
/* 1461 */                 out.write(10);
/*      */                 
/* 1463 */                 IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1464 */                 _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1465 */                 _jspx_th_c_005fif_005f17.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                 
/* 1467 */                 _jspx_th_c_005fif_005f17.setTest("${selectedscheme != 'slim'}");
/* 1468 */                 int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1469 */                 if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                   for (;;) {
/* 1471 */                     out.write(10);
/*      */                     
/* 1473 */                     ComponentContext compContext = (ComponentContext)pageContext.getAttribute("org.apache.struts.taglib.tiles.CompContext", 2);
/*      */                     
/*      */ 
/*      */ 
/* 1477 */                     String headerPage = (String)compContext.getAttribute("Header");
/*      */                     
/* 1479 */                     if (headerPage != null)
/*      */                     {
/* 1481 */                       out.write(10);
/* 1482 */                       if (_jspx_meth_tiles_005finsert_005f16(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                         return;
/* 1484 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 1488 */                     out.write(10);
/* 1489 */                     out.write(10);
/* 1490 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1491 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1495 */                 if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1496 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                 }
/*      */                 
/* 1499 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1500 */                 out.write("\n\n<div id=\"");
/* 1501 */                 out.print(containterAreaDiv);
/* 1502 */                 out.write("\"> <!-- TODO : Temp fix of adding userAreaContainerDiv_Temp div, need to remove this and add Layout -->\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td  valign=\"top\" class=\"tdindent\" width=\"100%\">   ");
/* 1503 */                 out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n        <tr>\n\t<td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n       \n        <tr>\n          <td colspan=\"2\">");
/* 1504 */                 if (_jspx_meth_tiles_005finsert_005f17(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                   return;
/* 1506 */                 out.write("</td>\n        </tr>\n        ");
/*      */                 
/* 1508 */                 IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1509 */                 _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1510 */                 _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                 
/* 1512 */                 _jspx_th_c_005fif_005f18.setTest("${param.actionMethod != 'getCAMAttributes' && param.actionMethod != 'generateHASnapShotReport'}");
/* 1513 */                 int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1514 */                 if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                   for (;;) {
/* 1516 */                     out.write("\n        <tr>\n            \n          <td colspan=\"2\"><table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> ");
/*      */                     
/* 1518 */                     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1519 */                     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 1520 */                     _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fif_005f18);
/*      */                     
/* 1522 */                     _jspx_th_c_005fif_005f19.setTest("${(strTime !='0') && (strTime!=null)}");
/* 1523 */                     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 1524 */                     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                       for (;;) {
/* 1526 */                         out.write("\n                <td height=\"33\" class=\"arial11italics\">");
/* 1527 */                         out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/*      */                         
/* 1529 */                         out.write("\n                  ");
/* 1530 */                         if (_jspx_meth_fmt_005fformatDate_005f18(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                           return;
/* 1532 */                         out.write(32);
/* 1533 */                         out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/*      */                         
/* 1535 */                         out.write("\n                  ");
/* 1536 */                         if (_jspx_meth_fmt_005fformatDate_005f19(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                           return;
/* 1538 */                         out.write("</td>\n                ");
/* 1539 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 1540 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1544 */                     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 1545 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                     }
/*      */                     
/* 1548 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1549 */                     out.write(" </tr>\n            </table></td>\n        </tr>\n        ");
/* 1550 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1551 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1555 */                 if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1556 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                 }
/*      */                 
/* 1559 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1560 */                 out.write("\n      </table></td>\n  </tr>\n</table>\n</div><!-- userAreaContainerDiv_Temp ends -->\n");
/* 1561 */                 JspRuntimeLibrary.include(request, response, footerJsp, out, false);
/* 1562 */                 out.write(10);
/* 1563 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1564 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1568 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1569 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */             }
/*      */             
/* 1572 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1573 */             out.write(10);
/* 1574 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1575 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1579 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1580 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */         }
/*      */         else {
/* 1583 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1584 */           out.write(10);
/* 1585 */           out.write(10);
/* 1586 */           out.write(10);
/* 1587 */           out.write(10);
/*      */           
/* 1589 */           if (PluginUtil.isPlugin())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1594 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 1595 */             out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/* 1596 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 1600 */           out.write("\n</body>\n</html>\n");
/*      */         }
/* 1602 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1603 */         out = _jspx_out;
/* 1604 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1605 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1606 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1609 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1615 */     PageContext pageContext = _jspx_page_context;
/* 1616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1618 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1619 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1620 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1622 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1624 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1625 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1626 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1627 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1628 */       return true;
/*      */     }
/* 1630 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1636 */     PageContext pageContext = _jspx_page_context;
/* 1637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1639 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1640 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1641 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1643 */     _jspx_th_c_005fwhen_005f0.setTest("${heading !='0'}");
/* 1644 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1645 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1647 */         out.write("\n\t    ");
/* 1648 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 1649 */           return true;
/* 1650 */         out.write("\n     ");
/* 1651 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1656 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1657 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1658 */       return true;
/*      */     }
/* 1660 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1666 */     PageContext pageContext = _jspx_page_context;
/* 1667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1669 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1670 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1671 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1673 */     _jspx_th_c_005fout_005f1.setValue("${heading}");
/* 1674 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1675 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1676 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1677 */       return true;
/*      */     }
/* 1679 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1685 */     PageContext pageContext = _jspx_page_context;
/* 1686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1688 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 1689 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 1690 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1692 */     _jspx_th_tiles_005finsert_005f0.setAttribute("ReportContent");
/* 1693 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 1694 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 1695 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1696 */       return true;
/*      */     }
/* 1698 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1704 */     PageContext pageContext = _jspx_page_context;
/* 1705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1707 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1708 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 1709 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1711 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${strTime}");
/*      */     
/* 1713 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 1714 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 1715 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 1716 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 1717 */       return true;
/*      */     }
/* 1719 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 1720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1725 */     PageContext pageContext = _jspx_page_context;
/* 1726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1728 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1729 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 1730 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1732 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${endTime}");
/*      */     
/* 1734 */     _jspx_th_fmt_005fformatDate_005f1.setType("both");
/* 1735 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 1736 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 1737 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 1738 */       return true;
/*      */     }
/* 1740 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 1741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1746 */     PageContext pageContext = _jspx_page_context;
/* 1747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1749 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 1750 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1751 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/* 1752 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1753 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 1754 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1755 */         out = _jspx_page_context.pushBody();
/* 1756 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 1757 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1760 */         out.write("webclient.server.responsetime");
/* 1761 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 1762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1765 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1766 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1769 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1770 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1771 */       return true;
/*      */     }
/* 1773 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1779 */     PageContext pageContext = _jspx_page_context;
/* 1780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1782 */     LoadTime _jspx_th_am_005ftimestamp_005f0 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 1783 */     _jspx_th_am_005ftimestamp_005f0.setPageContext(_jspx_page_context);
/* 1784 */     _jspx_th_am_005ftimestamp_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/* 1785 */     int _jspx_eval_am_005ftimestamp_005f0 = _jspx_th_am_005ftimestamp_005f0.doStartTag();
/* 1786 */     if (_jspx_th_am_005ftimestamp_005f0.doEndTag() == 5) {
/* 1787 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 1788 */       return true;
/*      */     }
/* 1790 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 1791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1796 */     PageContext pageContext = _jspx_page_context;
/* 1797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1799 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 1800 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 1801 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/* 1802 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 1803 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 1804 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 1805 */         out = _jspx_page_context.pushBody();
/* 1806 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 1807 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1810 */         out.write("webclient.server.systemtime");
/* 1811 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 1812 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1815 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 1816 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1819 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 1820 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1821 */       return true;
/*      */     }
/* 1823 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1829 */     PageContext pageContext = _jspx_page_context;
/* 1830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1832 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1833 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 1834 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1836 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${systime}");
/*      */     
/* 1838 */     _jspx_th_fmt_005fformatDate_005f2.setType("BOTH");
/* 1839 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 1840 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 1841 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 1842 */       return true;
/*      */     }
/* 1844 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 1845 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1850 */     PageContext pageContext = _jspx_page_context;
/* 1851 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1853 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 1854 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 1855 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1857 */     _jspx_th_tiles_005finsert_005f1.setAttribute("ReportsLeftArea");
/* 1858 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 1859 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 1860 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 1861 */       return true;
/*      */     }
/* 1863 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 1864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1869 */     PageContext pageContext = _jspx_page_context;
/* 1870 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1872 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 1873 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 1874 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1876 */     _jspx_th_tiles_005finsert_005f2.setAttribute("ReportsRightArea");
/* 1877 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 1878 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 1879 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 1880 */       return true;
/*      */     }
/* 1882 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 1883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1888 */     PageContext pageContext = _jspx_page_context;
/* 1889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1891 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 1892 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 1893 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1895 */     _jspx_th_tiles_005finsert_005f3.setAttribute("ReportContent");
/* 1896 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 1897 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 1898 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 1899 */       return true;
/*      */     }
/* 1901 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 1902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f3(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1907 */     PageContext pageContext = _jspx_page_context;
/* 1908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1910 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f3 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1911 */     _jspx_th_fmt_005fformatDate_005f3.setPageContext(_jspx_page_context);
/* 1912 */     _jspx_th_fmt_005fformatDate_005f3.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1914 */     _jspx_th_fmt_005fformatDate_005f3.setValue("${strTime}");
/*      */     
/* 1916 */     _jspx_th_fmt_005fformatDate_005f3.setType("both");
/* 1917 */     int _jspx_eval_fmt_005fformatDate_005f3 = _jspx_th_fmt_005fformatDate_005f3.doStartTag();
/* 1918 */     if (_jspx_th_fmt_005fformatDate_005f3.doEndTag() == 5) {
/* 1919 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 1920 */       return true;
/*      */     }
/* 1922 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 1923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f4(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1928 */     PageContext pageContext = _jspx_page_context;
/* 1929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1931 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f4 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 1932 */     _jspx_th_fmt_005fformatDate_005f4.setPageContext(_jspx_page_context);
/* 1933 */     _jspx_th_fmt_005fformatDate_005f4.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1935 */     _jspx_th_fmt_005fformatDate_005f4.setValue("${endTime}");
/*      */     
/* 1937 */     _jspx_th_fmt_005fformatDate_005f4.setType("both");
/* 1938 */     int _jspx_eval_fmt_005fformatDate_005f4 = _jspx_th_fmt_005fformatDate_005f4.doStartTag();
/* 1939 */     if (_jspx_th_fmt_005fformatDate_005f4.doEndTag() == 5) {
/* 1940 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 1941 */       return true;
/*      */     }
/* 1943 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 1944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1949 */     PageContext pageContext = _jspx_page_context;
/* 1950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1952 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 1953 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 1954 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 1955 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 1956 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 1957 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 1958 */         out = _jspx_page_context.pushBody();
/* 1959 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 1960 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1963 */         out.write("webclient.server.responsetime");
/* 1964 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 1965 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1968 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 1969 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1972 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 1973 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1974 */       return true;
/*      */     }
/* 1976 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1982 */     PageContext pageContext = _jspx_page_context;
/* 1983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1985 */     LoadTime _jspx_th_am_005ftimestamp_005f1 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 1986 */     _jspx_th_am_005ftimestamp_005f1.setPageContext(_jspx_page_context);
/* 1987 */     _jspx_th_am_005ftimestamp_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 1988 */     int _jspx_eval_am_005ftimestamp_005f1 = _jspx_th_am_005ftimestamp_005f1.doStartTag();
/* 1989 */     if (_jspx_th_am_005ftimestamp_005f1.doEndTag() == 5) {
/* 1990 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f1);
/* 1991 */       return true;
/*      */     }
/* 1993 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f1);
/* 1994 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1999 */     PageContext pageContext = _jspx_page_context;
/* 2000 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2002 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2003 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2004 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2005 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2006 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 2007 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 2008 */         out = _jspx_page_context.pushBody();
/* 2009 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 2010 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2013 */         out.write("webclient.server.systemtime");
/* 2014 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 2015 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2018 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 2019 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2022 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 2023 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2024 */       return true;
/*      */     }
/* 2026 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f5(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2032 */     PageContext pageContext = _jspx_page_context;
/* 2033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2035 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f5 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2036 */     _jspx_th_fmt_005fformatDate_005f5.setPageContext(_jspx_page_context);
/* 2037 */     _jspx_th_fmt_005fformatDate_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2039 */     _jspx_th_fmt_005fformatDate_005f5.setValue("${systime}");
/*      */     
/* 2041 */     _jspx_th_fmt_005fformatDate_005f5.setType("BOTH");
/* 2042 */     int _jspx_eval_fmt_005fformatDate_005f5 = _jspx_th_fmt_005fformatDate_005f5.doStartTag();
/* 2043 */     if (_jspx_th_fmt_005fformatDate_005f5.doEndTag() == 5) {
/* 2044 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 2045 */       return true;
/*      */     }
/* 2047 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 2048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2053 */     PageContext pageContext = _jspx_page_context;
/* 2054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2056 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2057 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2058 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2060 */     _jspx_th_tiles_005finsert_005f4.setAttribute("ReportsLeftArea");
/* 2061 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2062 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2063 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2064 */       return true;
/*      */     }
/* 2066 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f5(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2072 */     PageContext pageContext = _jspx_page_context;
/* 2073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2075 */     InsertTag _jspx_th_tiles_005finsert_005f5 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2076 */     _jspx_th_tiles_005finsert_005f5.setPageContext(_jspx_page_context);
/* 2077 */     _jspx_th_tiles_005finsert_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2079 */     _jspx_th_tiles_005finsert_005f5.setAttribute("ReportsRightArea");
/* 2080 */     int _jspx_eval_tiles_005finsert_005f5 = _jspx_th_tiles_005finsert_005f5.doStartTag();
/* 2081 */     if (_jspx_th_tiles_005finsert_005f5.doEndTag() == 5) {
/* 2082 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2083 */       return true;
/*      */     }
/* 2085 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f6(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2091 */     PageContext pageContext = _jspx_page_context;
/* 2092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2094 */     InsertTag _jspx_th_tiles_005finsert_005f6 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2095 */     _jspx_th_tiles_005finsert_005f6.setPageContext(_jspx_page_context);
/* 2096 */     _jspx_th_tiles_005finsert_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2098 */     _jspx_th_tiles_005finsert_005f6.setAttribute("ReportContent");
/* 2099 */     int _jspx_eval_tiles_005finsert_005f6 = _jspx_th_tiles_005finsert_005f6.doStartTag();
/* 2100 */     if (_jspx_th_tiles_005finsert_005f6.doEndTag() == 5) {
/* 2101 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2102 */       return true;
/*      */     }
/* 2104 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2110 */     PageContext pageContext = _jspx_page_context;
/* 2111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2113 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f6 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2114 */     _jspx_th_fmt_005fformatDate_005f6.setPageContext(_jspx_page_context);
/* 2115 */     _jspx_th_fmt_005fformatDate_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2117 */     _jspx_th_fmt_005fformatDate_005f6.setValue("${strTime}");
/*      */     
/* 2119 */     _jspx_th_fmt_005fformatDate_005f6.setType("both");
/* 2120 */     int _jspx_eval_fmt_005fformatDate_005f6 = _jspx_th_fmt_005fformatDate_005f6.doStartTag();
/* 2121 */     if (_jspx_th_fmt_005fformatDate_005f6.doEndTag() == 5) {
/* 2122 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f6);
/* 2123 */       return true;
/*      */     }
/* 2125 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f6);
/* 2126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2131 */     PageContext pageContext = _jspx_page_context;
/* 2132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2134 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f7 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2135 */     _jspx_th_fmt_005fformatDate_005f7.setPageContext(_jspx_page_context);
/* 2136 */     _jspx_th_fmt_005fformatDate_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2138 */     _jspx_th_fmt_005fformatDate_005f7.setValue("${endTime}");
/*      */     
/* 2140 */     _jspx_th_fmt_005fformatDate_005f7.setType("both");
/* 2141 */     int _jspx_eval_fmt_005fformatDate_005f7 = _jspx_th_fmt_005fformatDate_005f7.doStartTag();
/* 2142 */     if (_jspx_th_fmt_005fformatDate_005f7.doEndTag() == 5) {
/* 2143 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f7);
/* 2144 */       return true;
/*      */     }
/* 2146 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f7);
/* 2147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2152 */     PageContext pageContext = _jspx_page_context;
/* 2153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2155 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2156 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 2157 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/* 2158 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 2159 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 2160 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 2161 */         out = _jspx_page_context.pushBody();
/* 2162 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 2163 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2166 */         out.write("webclient.server.responsetime");
/* 2167 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 2168 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2171 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 2172 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2175 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 2176 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 2177 */       return true;
/*      */     }
/* 2179 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 2180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f2(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2185 */     PageContext pageContext = _jspx_page_context;
/* 2186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2188 */     LoadTime _jspx_th_am_005ftimestamp_005f2 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2189 */     _jspx_th_am_005ftimestamp_005f2.setPageContext(_jspx_page_context);
/* 2190 */     _jspx_th_am_005ftimestamp_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/* 2191 */     int _jspx_eval_am_005ftimestamp_005f2 = _jspx_th_am_005ftimestamp_005f2.doStartTag();
/* 2192 */     if (_jspx_th_am_005ftimestamp_005f2.doEndTag() == 5) {
/* 2193 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f2);
/* 2194 */       return true;
/*      */     }
/* 2196 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f2);
/* 2197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2202 */     PageContext pageContext = _jspx_page_context;
/* 2203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2205 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2206 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 2207 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/* 2208 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 2209 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 2210 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 2211 */         out = _jspx_page_context.pushBody();
/* 2212 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 2213 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2216 */         out.write("webclient.server.systemtime");
/* 2217 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 2218 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2221 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 2222 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2225 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 2226 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 2227 */       return true;
/*      */     }
/* 2229 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 2230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f8(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2235 */     PageContext pageContext = _jspx_page_context;
/* 2236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2238 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f8 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2239 */     _jspx_th_fmt_005fformatDate_005f8.setPageContext(_jspx_page_context);
/* 2240 */     _jspx_th_fmt_005fformatDate_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2242 */     _jspx_th_fmt_005fformatDate_005f8.setValue("${systime}");
/*      */     
/* 2244 */     _jspx_th_fmt_005fformatDate_005f8.setType("BOTH");
/* 2245 */     int _jspx_eval_fmt_005fformatDate_005f8 = _jspx_th_fmt_005fformatDate_005f8.doStartTag();
/* 2246 */     if (_jspx_th_fmt_005fformatDate_005f8.doEndTag() == 5) {
/* 2247 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f8);
/* 2248 */       return true;
/*      */     }
/* 2250 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f8);
/* 2251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f7(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2256 */     PageContext pageContext = _jspx_page_context;
/* 2257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2259 */     InsertTag _jspx_th_tiles_005finsert_005f7 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2260 */     _jspx_th_tiles_005finsert_005f7.setPageContext(_jspx_page_context);
/* 2261 */     _jspx_th_tiles_005finsert_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2263 */     _jspx_th_tiles_005finsert_005f7.setAttribute("ReportsLeftArea");
/* 2264 */     int _jspx_eval_tiles_005finsert_005f7 = _jspx_th_tiles_005finsert_005f7.doStartTag();
/* 2265 */     if (_jspx_th_tiles_005finsert_005f7.doEndTag() == 5) {
/* 2266 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2267 */       return true;
/*      */     }
/* 2269 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f8(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2275 */     PageContext pageContext = _jspx_page_context;
/* 2276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2278 */     InsertTag _jspx_th_tiles_005finsert_005f8 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2279 */     _jspx_th_tiles_005finsert_005f8.setPageContext(_jspx_page_context);
/* 2280 */     _jspx_th_tiles_005finsert_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2282 */     _jspx_th_tiles_005finsert_005f8.setAttribute("ReportsRightArea");
/* 2283 */     int _jspx_eval_tiles_005finsert_005f8 = _jspx_th_tiles_005finsert_005f8.doStartTag();
/* 2284 */     if (_jspx_th_tiles_005finsert_005f8.doEndTag() == 5) {
/* 2285 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f8);
/* 2286 */       return true;
/*      */     }
/* 2288 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f8);
/* 2289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f9(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2294 */     PageContext pageContext = _jspx_page_context;
/* 2295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2297 */     InsertTag _jspx_th_tiles_005finsert_005f9 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2298 */     _jspx_th_tiles_005finsert_005f9.setPageContext(_jspx_page_context);
/* 2299 */     _jspx_th_tiles_005finsert_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2301 */     _jspx_th_tiles_005finsert_005f9.setAttribute("ReportContent");
/* 2302 */     int _jspx_eval_tiles_005finsert_005f9 = _jspx_th_tiles_005finsert_005f9.doStartTag();
/* 2303 */     if (_jspx_th_tiles_005finsert_005f9.doEndTag() == 5) {
/* 2304 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f9);
/* 2305 */       return true;
/*      */     }
/* 2307 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f9);
/* 2308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f9(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2313 */     PageContext pageContext = _jspx_page_context;
/* 2314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2316 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f9 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2317 */     _jspx_th_fmt_005fformatDate_005f9.setPageContext(_jspx_page_context);
/* 2318 */     _jspx_th_fmt_005fformatDate_005f9.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2320 */     _jspx_th_fmt_005fformatDate_005f9.setValue("${strTime}");
/*      */     
/* 2322 */     _jspx_th_fmt_005fformatDate_005f9.setType("both");
/* 2323 */     int _jspx_eval_fmt_005fformatDate_005f9 = _jspx_th_fmt_005fformatDate_005f9.doStartTag();
/* 2324 */     if (_jspx_th_fmt_005fformatDate_005f9.doEndTag() == 5) {
/* 2325 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f9);
/* 2326 */       return true;
/*      */     }
/* 2328 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f9);
/* 2329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2334 */     PageContext pageContext = _jspx_page_context;
/* 2335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2337 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f10 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2338 */     _jspx_th_fmt_005fformatDate_005f10.setPageContext(_jspx_page_context);
/* 2339 */     _jspx_th_fmt_005fformatDate_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2341 */     _jspx_th_fmt_005fformatDate_005f10.setValue("${endTime}");
/*      */     
/* 2343 */     _jspx_th_fmt_005fformatDate_005f10.setType("both");
/* 2344 */     int _jspx_eval_fmt_005fformatDate_005f10 = _jspx_th_fmt_005fformatDate_005f10.doStartTag();
/* 2345 */     if (_jspx_th_fmt_005fformatDate_005f10.doEndTag() == 5) {
/* 2346 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f10);
/* 2347 */       return true;
/*      */     }
/* 2349 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f10);
/* 2350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2355 */     PageContext pageContext = _jspx_page_context;
/* 2356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2358 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2359 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 2360 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 2361 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 2362 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 2363 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 2364 */         out = _jspx_page_context.pushBody();
/* 2365 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 2366 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2369 */         out.write("webclient.server.responsetime");
/* 2370 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 2371 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2374 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 2375 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2378 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 2379 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 2380 */       return true;
/*      */     }
/* 2382 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 2383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f3(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2388 */     PageContext pageContext = _jspx_page_context;
/* 2389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2391 */     LoadTime _jspx_th_am_005ftimestamp_005f3 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2392 */     _jspx_th_am_005ftimestamp_005f3.setPageContext(_jspx_page_context);
/* 2393 */     _jspx_th_am_005ftimestamp_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 2394 */     int _jspx_eval_am_005ftimestamp_005f3 = _jspx_th_am_005ftimestamp_005f3.doStartTag();
/* 2395 */     if (_jspx_th_am_005ftimestamp_005f3.doEndTag() == 5) {
/* 2396 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f3);
/* 2397 */       return true;
/*      */     }
/* 2399 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f3);
/* 2400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2405 */     PageContext pageContext = _jspx_page_context;
/* 2406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2408 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2409 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 2410 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 2411 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 2412 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 2413 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 2414 */         out = _jspx_page_context.pushBody();
/* 2415 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 2416 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2419 */         out.write("webclient.server.systemtime");
/* 2420 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 2421 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2424 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 2425 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2428 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 2429 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 2430 */       return true;
/*      */     }
/* 2432 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 2433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f11(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2438 */     PageContext pageContext = _jspx_page_context;
/* 2439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2441 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f11 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2442 */     _jspx_th_fmt_005fformatDate_005f11.setPageContext(_jspx_page_context);
/* 2443 */     _jspx_th_fmt_005fformatDate_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2445 */     _jspx_th_fmt_005fformatDate_005f11.setValue("${systime}");
/*      */     
/* 2447 */     _jspx_th_fmt_005fformatDate_005f11.setType("BOTH");
/* 2448 */     int _jspx_eval_fmt_005fformatDate_005f11 = _jspx_th_fmt_005fformatDate_005f11.doStartTag();
/* 2449 */     if (_jspx_th_fmt_005fformatDate_005f11.doEndTag() == 5) {
/* 2450 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f11);
/* 2451 */       return true;
/*      */     }
/* 2453 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f11);
/* 2454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f10(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2459 */     PageContext pageContext = _jspx_page_context;
/* 2460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2462 */     InsertTag _jspx_th_tiles_005finsert_005f10 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2463 */     _jspx_th_tiles_005finsert_005f10.setPageContext(_jspx_page_context);
/* 2464 */     _jspx_th_tiles_005finsert_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2466 */     _jspx_th_tiles_005finsert_005f10.setAttribute("ReportsLeftArea");
/* 2467 */     int _jspx_eval_tiles_005finsert_005f10 = _jspx_th_tiles_005finsert_005f10.doStartTag();
/* 2468 */     if (_jspx_th_tiles_005finsert_005f10.doEndTag() == 5) {
/* 2469 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f10);
/* 2470 */       return true;
/*      */     }
/* 2472 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f10);
/* 2473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f11(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2478 */     PageContext pageContext = _jspx_page_context;
/* 2479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2481 */     InsertTag _jspx_th_tiles_005finsert_005f11 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2482 */     _jspx_th_tiles_005finsert_005f11.setPageContext(_jspx_page_context);
/* 2483 */     _jspx_th_tiles_005finsert_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2485 */     _jspx_th_tiles_005finsert_005f11.setAttribute("ReportsRightArea");
/* 2486 */     int _jspx_eval_tiles_005finsert_005f11 = _jspx_th_tiles_005finsert_005f11.doStartTag();
/* 2487 */     if (_jspx_th_tiles_005finsert_005f11.doEndTag() == 5) {
/* 2488 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f11);
/* 2489 */       return true;
/*      */     }
/* 2491 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f11);
/* 2492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f12(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2497 */     PageContext pageContext = _jspx_page_context;
/* 2498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2500 */     InsertTag _jspx_th_tiles_005finsert_005f12 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2501 */     _jspx_th_tiles_005finsert_005f12.setPageContext(_jspx_page_context);
/* 2502 */     _jspx_th_tiles_005finsert_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2504 */     _jspx_th_tiles_005finsert_005f12.setAttribute("ReportContent");
/* 2505 */     int _jspx_eval_tiles_005finsert_005f12 = _jspx_th_tiles_005finsert_005f12.doStartTag();
/* 2506 */     if (_jspx_th_tiles_005finsert_005f12.doEndTag() == 5) {
/* 2507 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f12);
/* 2508 */       return true;
/*      */     }
/* 2510 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f12);
/* 2511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f12(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2516 */     PageContext pageContext = _jspx_page_context;
/* 2517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2519 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f12 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2520 */     _jspx_th_fmt_005fformatDate_005f12.setPageContext(_jspx_page_context);
/* 2521 */     _jspx_th_fmt_005fformatDate_005f12.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2523 */     _jspx_th_fmt_005fformatDate_005f12.setValue("${strTime}");
/*      */     
/* 2525 */     _jspx_th_fmt_005fformatDate_005f12.setType("both");
/* 2526 */     int _jspx_eval_fmt_005fformatDate_005f12 = _jspx_th_fmt_005fformatDate_005f12.doStartTag();
/* 2527 */     if (_jspx_th_fmt_005fformatDate_005f12.doEndTag() == 5) {
/* 2528 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f12);
/* 2529 */       return true;
/*      */     }
/* 2531 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f12);
/* 2532 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f13(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2537 */     PageContext pageContext = _jspx_page_context;
/* 2538 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2540 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f13 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2541 */     _jspx_th_fmt_005fformatDate_005f13.setPageContext(_jspx_page_context);
/* 2542 */     _jspx_th_fmt_005fformatDate_005f13.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2544 */     _jspx_th_fmt_005fformatDate_005f13.setValue("${endTime}");
/*      */     
/* 2546 */     _jspx_th_fmt_005fformatDate_005f13.setType("both");
/* 2547 */     int _jspx_eval_fmt_005fformatDate_005f13 = _jspx_th_fmt_005fformatDate_005f13.doStartTag();
/* 2548 */     if (_jspx_th_fmt_005fformatDate_005f13.doEndTag() == 5) {
/* 2549 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f13);
/* 2550 */       return true;
/*      */     }
/* 2552 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f13);
/* 2553 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2558 */     PageContext pageContext = _jspx_page_context;
/* 2559 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2561 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2562 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 2563 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/* 2564 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 2565 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 2566 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 2567 */         out = _jspx_page_context.pushBody();
/* 2568 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 2569 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2572 */         out.write("webclient.server.responsetime");
/* 2573 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 2574 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2577 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 2578 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2581 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 2582 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 2583 */       return true;
/*      */     }
/* 2585 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 2586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f4(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2591 */     PageContext pageContext = _jspx_page_context;
/* 2592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2594 */     LoadTime _jspx_th_am_005ftimestamp_005f4 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2595 */     _jspx_th_am_005ftimestamp_005f4.setPageContext(_jspx_page_context);
/* 2596 */     _jspx_th_am_005ftimestamp_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/* 2597 */     int _jspx_eval_am_005ftimestamp_005f4 = _jspx_th_am_005ftimestamp_005f4.doStartTag();
/* 2598 */     if (_jspx_th_am_005ftimestamp_005f4.doEndTag() == 5) {
/* 2599 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f4);
/* 2600 */       return true;
/*      */     }
/* 2602 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f4);
/* 2603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2608 */     PageContext pageContext = _jspx_page_context;
/* 2609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2611 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2612 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 2613 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/* 2614 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 2615 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 2616 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 2617 */         out = _jspx_page_context.pushBody();
/* 2618 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 2619 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2622 */         out.write("webclient.server.systemtime");
/* 2623 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 2624 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2627 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 2628 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2631 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 2632 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 2633 */       return true;
/*      */     }
/* 2635 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 2636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f14(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2641 */     PageContext pageContext = _jspx_page_context;
/* 2642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2644 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f14 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2645 */     _jspx_th_fmt_005fformatDate_005f14.setPageContext(_jspx_page_context);
/* 2646 */     _jspx_th_fmt_005fformatDate_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2648 */     _jspx_th_fmt_005fformatDate_005f14.setValue("${systime}");
/*      */     
/* 2650 */     _jspx_th_fmt_005fformatDate_005f14.setType("BOTH");
/* 2651 */     int _jspx_eval_fmt_005fformatDate_005f14 = _jspx_th_fmt_005fformatDate_005f14.doStartTag();
/* 2652 */     if (_jspx_th_fmt_005fformatDate_005f14.doEndTag() == 5) {
/* 2653 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f14);
/* 2654 */       return true;
/*      */     }
/* 2656 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f14);
/* 2657 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f13(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2662 */     PageContext pageContext = _jspx_page_context;
/* 2663 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2665 */     InsertTag _jspx_th_tiles_005finsert_005f13 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2666 */     _jspx_th_tiles_005finsert_005f13.setPageContext(_jspx_page_context);
/* 2667 */     _jspx_th_tiles_005finsert_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2669 */     _jspx_th_tiles_005finsert_005f13.setAttribute("ReportsLeftArea");
/* 2670 */     int _jspx_eval_tiles_005finsert_005f13 = _jspx_th_tiles_005finsert_005f13.doStartTag();
/* 2671 */     if (_jspx_th_tiles_005finsert_005f13.doEndTag() == 5) {
/* 2672 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f13);
/* 2673 */       return true;
/*      */     }
/* 2675 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f13);
/* 2676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f14(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2681 */     PageContext pageContext = _jspx_page_context;
/* 2682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2684 */     InsertTag _jspx_th_tiles_005finsert_005f14 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2685 */     _jspx_th_tiles_005finsert_005f14.setPageContext(_jspx_page_context);
/* 2686 */     _jspx_th_tiles_005finsert_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2688 */     _jspx_th_tiles_005finsert_005f14.setAttribute("ReportsRightArea");
/* 2689 */     int _jspx_eval_tiles_005finsert_005f14 = _jspx_th_tiles_005finsert_005f14.doStartTag();
/* 2690 */     if (_jspx_th_tiles_005finsert_005f14.doEndTag() == 5) {
/* 2691 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f14);
/* 2692 */       return true;
/*      */     }
/* 2694 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f14);
/* 2695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2700 */     PageContext pageContext = _jspx_page_context;
/* 2701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2703 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2704 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2705 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2707 */     _jspx_th_c_005fif_005f12.setTest("${reportmethod != \"generateIndividualGlanceReport\"}");
/* 2708 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2709 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2711 */         out.write("\n        \n<tr>\n<td colspan=\"2\" style=\"height:10px;\"></td>\n        </tr>\n       \n\t");
/* 2712 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2713 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2717 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2718 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2719 */       return true;
/*      */     }
/* 2721 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f15(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2727 */     PageContext pageContext = _jspx_page_context;
/* 2728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2730 */     InsertTag _jspx_th_tiles_005finsert_005f15 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2731 */     _jspx_th_tiles_005finsert_005f15.setPageContext(_jspx_page_context);
/* 2732 */     _jspx_th_tiles_005finsert_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2734 */     _jspx_th_tiles_005finsert_005f15.setAttribute("ReportContent");
/* 2735 */     int _jspx_eval_tiles_005finsert_005f15 = _jspx_th_tiles_005finsert_005f15.doStartTag();
/* 2736 */     if (_jspx_th_tiles_005finsert_005f15.doEndTag() == 5) {
/* 2737 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f15);
/* 2738 */       return true;
/*      */     }
/* 2740 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f15);
/* 2741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f15(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2746 */     PageContext pageContext = _jspx_page_context;
/* 2747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2749 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f15 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2750 */     _jspx_th_fmt_005fformatDate_005f15.setPageContext(_jspx_page_context);
/* 2751 */     _jspx_th_fmt_005fformatDate_005f15.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 2753 */     _jspx_th_fmt_005fformatDate_005f15.setValue("${strTime}");
/*      */     
/* 2755 */     _jspx_th_fmt_005fformatDate_005f15.setType("both");
/* 2756 */     int _jspx_eval_fmt_005fformatDate_005f15 = _jspx_th_fmt_005fformatDate_005f15.doStartTag();
/* 2757 */     if (_jspx_th_fmt_005fformatDate_005f15.doEndTag() == 5) {
/* 2758 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f15);
/* 2759 */       return true;
/*      */     }
/* 2761 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f15);
/* 2762 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f16(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2767 */     PageContext pageContext = _jspx_page_context;
/* 2768 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2770 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f16 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2771 */     _jspx_th_fmt_005fformatDate_005f16.setPageContext(_jspx_page_context);
/* 2772 */     _jspx_th_fmt_005fformatDate_005f16.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 2774 */     _jspx_th_fmt_005fformatDate_005f16.setValue("${endTime}");
/*      */     
/* 2776 */     _jspx_th_fmt_005fformatDate_005f16.setType("both");
/* 2777 */     int _jspx_eval_fmt_005fformatDate_005f16 = _jspx_th_fmt_005fformatDate_005f16.doStartTag();
/* 2778 */     if (_jspx_th_fmt_005fformatDate_005f16.doEndTag() == 5) {
/* 2779 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f16);
/* 2780 */       return true;
/*      */     }
/* 2782 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f16);
/* 2783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2788 */     PageContext pageContext = _jspx_page_context;
/* 2789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2791 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2792 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 2793 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/* 2794 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 2795 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 2796 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 2797 */         out = _jspx_page_context.pushBody();
/* 2798 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 2799 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2802 */         out.write("webclient.server.responsetime");
/* 2803 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 2804 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2807 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 2808 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2811 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f5(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     LoadTime _jspx_th_am_005ftimestamp_005f5 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2825 */     _jspx_th_am_005ftimestamp_005f5.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_am_005ftimestamp_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/* 2827 */     int _jspx_eval_am_005ftimestamp_005f5 = _jspx_th_am_005ftimestamp_005f5.doStartTag();
/* 2828 */     if (_jspx_th_am_005ftimestamp_005f5.doEndTag() == 5) {
/* 2829 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f5);
/* 2830 */       return true;
/*      */     }
/* 2832 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f5);
/* 2833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2838 */     PageContext pageContext = _jspx_page_context;
/* 2839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2841 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2842 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 2843 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/* 2844 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 2845 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 2846 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 2847 */         out = _jspx_page_context.pushBody();
/* 2848 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 2849 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2852 */         out.write("webclient.server.systemtime");
/* 2853 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 2854 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2857 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 2858 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2861 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 2862 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 2863 */       return true;
/*      */     }
/* 2865 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 2866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f17(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2871 */     PageContext pageContext = _jspx_page_context;
/* 2872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2874 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f17 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2875 */     _jspx_th_fmt_005fformatDate_005f17.setPageContext(_jspx_page_context);
/* 2876 */     _jspx_th_fmt_005fformatDate_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2878 */     _jspx_th_fmt_005fformatDate_005f17.setValue("${systime}");
/*      */     
/* 2880 */     _jspx_th_fmt_005fformatDate_005f17.setType("BOTH");
/* 2881 */     int _jspx_eval_fmt_005fformatDate_005f17 = _jspx_th_fmt_005fformatDate_005f17.doStartTag();
/* 2882 */     if (_jspx_th_fmt_005fformatDate_005f17.doEndTag() == 5) {
/* 2883 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f17);
/* 2884 */       return true;
/*      */     }
/* 2886 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f17);
/* 2887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2892 */     PageContext pageContext = _jspx_page_context;
/* 2893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2895 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2896 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2897 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2899 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 2901 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 2902 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 2904 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2905 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 2907 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 2908 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2909 */             return true;
/* 2910 */           out.write("\"\" class=\"staticlinks\">");
/* 2911 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2912 */             return true;
/* 2913 */           out.write("</a></td>\n");
/* 2914 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2915 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2919 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 2920 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2923 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 2924 */         out = _jspx_page_context.popBody(); }
/* 2925 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2927 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2928 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 2930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2935 */     PageContext pageContext = _jspx_page_context;
/* 2936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2938 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2939 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2940 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2942 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABLINK}");
/* 2943 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2944 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2946 */       return true;
/*      */     }
/* 2948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2954 */     PageContext pageContext = _jspx_page_context;
/* 2955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2957 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2958 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2959 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2961 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 2962 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2963 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2964 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2965 */       return true;
/*      */     }
/* 2967 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2973 */     PageContext pageContext = _jspx_page_context;
/* 2974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2976 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2977 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 2978 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2980 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 2982 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 2983 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 2985 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 2986 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 2988 */           out.write(10);
/* 2989 */           out.write(10);
/* 2990 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2991 */             return true;
/* 2992 */           out.write(10);
/* 2993 */           out.write(10);
/* 2994 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 2995 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2999 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 3000 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3003 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 3004 */         out = _jspx_page_context.popBody(); }
/* 3005 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 3007 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3008 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 3010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3015 */     PageContext pageContext = _jspx_page_context;
/* 3016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3018 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3019 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3020 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 3021 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3022 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 3024 */         out.write(10);
/* 3025 */         out.write(10);
/* 3026 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3027 */           return true;
/* 3028 */         out.write(10);
/* 3029 */         if (_jspx_meth_c_005fwhen_005f11(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3030 */           return true;
/* 3031 */         out.write(10);
/* 3032 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3033 */           return true;
/* 3034 */         out.write("\n\n\n\n\n");
/* 3035 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3040 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3041 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3042 */       return true;
/*      */     }
/* 3044 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3050 */     PageContext pageContext = _jspx_page_context;
/* 3051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3053 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3054 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3055 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3057 */     _jspx_th_c_005fwhen_005f7.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 3058 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3059 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 3061 */         out.write(10);
/* 3062 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3063 */           return true;
/* 3064 */         out.write(10);
/* 3065 */         out.write(32);
/* 3066 */         out.write(32);
/* 3067 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3068 */           return true;
/* 3069 */         out.write(10);
/* 3070 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3071 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3075 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3076 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3077 */       return true;
/*      */     }
/* 3079 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3080 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3085 */     PageContext pageContext = _jspx_page_context;
/* 3086 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3088 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3089 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3090 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 3092 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 3093 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3094 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3096 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 3097 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3098 */           return true;
/* 3099 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3100 */           return true;
/* 3101 */         out.write("\n  </a></td>\n  ");
/* 3102 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3103 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3107 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3108 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3109 */       return true;
/*      */     }
/* 3111 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3117 */     PageContext pageContext = _jspx_page_context;
/* 3118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3120 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3121 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3122 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 3123 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3124 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 3126 */         out.write("\n  \t\t");
/* 3127 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3128 */           return true;
/* 3129 */         out.write("\n  \t\t");
/* 3130 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3131 */           return true;
/* 3132 */         out.write("\n  \t\t");
/* 3133 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3134 */           return true;
/* 3135 */         out.write("\n  \t");
/* 3136 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3137 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3141 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3142 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3143 */       return true;
/*      */     }
/* 3145 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3151 */     PageContext pageContext = _jspx_page_context;
/* 3152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3154 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3155 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3156 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 3158 */     _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 3159 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3160 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 3162 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 3163 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3164 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3168 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3169 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3170 */       return true;
/*      */     }
/* 3172 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3178 */     PageContext pageContext = _jspx_page_context;
/* 3179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3181 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3182 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3183 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 3185 */     _jspx_th_c_005fwhen_005f9.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 3186 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3187 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 3189 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 3190 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3191 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3195 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3196 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3197 */       return true;
/*      */     }
/* 3199 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3205 */     PageContext pageContext = _jspx_page_context;
/* 3206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3208 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3209 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3210 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3211 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3212 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3214 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 3215 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3216 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3220 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3221 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3222 */       return true;
/*      */     }
/* 3224 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3230 */     PageContext pageContext = _jspx_page_context;
/* 3231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3233 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3234 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3235 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 3237 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 3238 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3239 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3240 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3241 */       return true;
/*      */     }
/* 3243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3249 */     PageContext pageContext = _jspx_page_context;
/* 3250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3252 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3253 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3254 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 3256 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 3257 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3258 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 3260 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 3261 */         if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3262 */           return true;
/* 3263 */         out.write("\n\t</td> \n  ");
/* 3264 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3265 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3269 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3270 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3271 */       return true;
/*      */     }
/* 3273 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3274 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3279 */     PageContext pageContext = _jspx_page_context;
/* 3280 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3282 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3283 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3284 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3285 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3286 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 3288 */         out.write("\n   \t\t ");
/* 3289 */         if (_jspx_meth_c_005fwhen_005f10(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3290 */           return true;
/* 3291 */         out.write("\n   \t \t ");
/* 3292 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3293 */           return true;
/* 3294 */         out.write("\n   \t");
/* 3295 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3296 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3300 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3301 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3302 */       return true;
/*      */     }
/* 3304 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f10(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3310 */     PageContext pageContext = _jspx_page_context;
/* 3311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3313 */     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3314 */     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3315 */     _jspx_th_c_005fwhen_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 3317 */     _jspx_th_c_005fwhen_005f10.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 3318 */     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3319 */     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */       for (;;) {
/* 3321 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 3322 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3323 */           return true;
/* 3324 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 3325 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3326 */           return true;
/* 3327 */         out.write("</a>\n   \t\t ");
/* 3328 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3333 */     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3334 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3335 */       return true;
/*      */     }
/* 3337 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3343 */     PageContext pageContext = _jspx_page_context;
/* 3344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3346 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3347 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3348 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 3350 */     _jspx_th_c_005fout_005f5.setValue("${globalconfig['defaultmonitorsview']}");
/* 3351 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3352 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3354 */       return true;
/*      */     }
/* 3356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3362 */     PageContext pageContext = _jspx_page_context;
/* 3363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3365 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3366 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3367 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 3369 */     _jspx_th_c_005fout_005f6.setValue("${tab.TABNAME}");
/* 3370 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3371 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3373 */       return true;
/*      */     }
/* 3375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3381 */     PageContext pageContext = _jspx_page_context;
/* 3382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3384 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3385 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3386 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 3387 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3388 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3390 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 3391 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3392 */           return true;
/* 3393 */         out.write("\" class=\"staticlinks\">");
/* 3394 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3395 */           return true;
/* 3396 */         out.write("</a>\n   \t\t ");
/* 3397 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3398 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3402 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3403 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3404 */       return true;
/*      */     }
/* 3406 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3412 */     PageContext pageContext = _jspx_page_context;
/* 3413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3415 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3416 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3417 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 3419 */     _jspx_th_c_005fout_005f7.setValue("${globalconfig['defaultmonitorsview']}");
/* 3420 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3421 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3422 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3423 */       return true;
/*      */     }
/* 3425 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3431 */     PageContext pageContext = _jspx_page_context;
/* 3432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3434 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3435 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3436 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 3438 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 3439 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3440 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3441 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3442 */       return true;
/*      */     }
/* 3444 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f11(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3450 */     PageContext pageContext = _jspx_page_context;
/* 3451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3453 */     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3454 */     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3455 */     _jspx_th_c_005fwhen_005f11.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3457 */     _jspx_th_c_005fwhen_005f11.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 3458 */     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3459 */     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */       for (;;) {
/* 3461 */         out.write(10);
/* 3462 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3463 */           return true;
/* 3464 */         out.write(10);
/* 3465 */         out.write(9);
/* 3466 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3467 */           return true;
/* 3468 */         out.write(10);
/* 3469 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3470 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3474 */     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3475 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3476 */       return true;
/*      */     }
/* 3478 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3484 */     PageContext pageContext = _jspx_page_context;
/* 3485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3487 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3488 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3489 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 3491 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3492 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3493 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3495 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 3496 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3497 */           return true;
/* 3498 */         out.write("</a></td>\n\t");
/* 3499 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3500 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3504 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3505 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3506 */       return true;
/*      */     }
/* 3508 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3514 */     PageContext pageContext = _jspx_page_context;
/* 3515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3517 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3518 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3519 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 3521 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 3522 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3523 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3524 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3525 */       return true;
/*      */     }
/* 3527 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3533 */     PageContext pageContext = _jspx_page_context;
/* 3534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3536 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3537 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3538 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 3540 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 3541 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3542 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3544 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 3545 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3546 */           return true;
/* 3547 */         out.write("</a></td>\n\t");
/* 3548 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3549 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3553 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3554 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3555 */       return true;
/*      */     }
/* 3557 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3563 */     PageContext pageContext = _jspx_page_context;
/* 3564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3566 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3567 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3568 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3570 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 3571 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3572 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3573 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3574 */       return true;
/*      */     }
/* 3576 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3582 */     PageContext pageContext = _jspx_page_context;
/* 3583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3585 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3586 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3587 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3588 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3589 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 3591 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 3592 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3593 */           return true;
/* 3594 */         out.write("\" class=\"staticlinks\">\t");
/* 3595 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3596 */           return true;
/* 3597 */         out.write("</a></td>\n");
/* 3598 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3599 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3603 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3604 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3605 */       return true;
/*      */     }
/* 3607 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3613 */     PageContext pageContext = _jspx_page_context;
/* 3614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3616 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3617 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3618 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 3620 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABLINK}");
/* 3621 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3622 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3624 */       return true;
/*      */     }
/* 3626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3632 */     PageContext pageContext = _jspx_page_context;
/* 3633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3635 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3636 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3637 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 3639 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABNAME}");
/* 3640 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3641 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3642 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3643 */       return true;
/*      */     }
/* 3645 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3651 */     PageContext pageContext = _jspx_page_context;
/* 3652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3654 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3655 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 3656 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 3657 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 3658 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 3660 */         out.write("\n  \t\t");
/* 3661 */         if (_jspx_meth_c_005fwhen_005f12(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 3662 */           return true;
/* 3663 */         out.write("\n  \t\t");
/* 3664 */         if (_jspx_meth_c_005fwhen_005f13(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 3665 */           return true;
/* 3666 */         out.write("\n  \t\t");
/* 3667 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 3668 */           return true;
/* 3669 */         out.write("\n  \t");
/* 3670 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3671 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3675 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3676 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3677 */       return true;
/*      */     }
/* 3679 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f12(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3685 */     PageContext pageContext = _jspx_page_context;
/* 3686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3688 */     WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3689 */     _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3690 */     _jspx_th_c_005fwhen_005f12.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 3692 */     _jspx_th_c_005fwhen_005f12.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 3693 */     int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3694 */     if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */       for (;;) {
/* 3696 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 3697 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3698 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3702 */     if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3703 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3704 */       return true;
/*      */     }
/* 3706 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f13(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3712 */     PageContext pageContext = _jspx_page_context;
/* 3713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3715 */     WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3716 */     _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3717 */     _jspx_th_c_005fwhen_005f13.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 3719 */     _jspx_th_c_005fwhen_005f13.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 3720 */     int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3721 */     if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */       for (;;) {
/* 3723 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 3724 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3725 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3729 */     if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3730 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3731 */       return true;
/*      */     }
/* 3733 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3739 */     PageContext pageContext = _jspx_page_context;
/* 3740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3742 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3743 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3744 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 3745 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3746 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 3748 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 3749 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3750 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3754 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3755 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3756 */       return true;
/*      */     }
/* 3758 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3764 */     PageContext pageContext = _jspx_page_context;
/* 3765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3767 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3768 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3769 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f14);
/*      */     
/* 3771 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 3772 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3773 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3774 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3775 */       return true;
/*      */     }
/* 3777 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3783 */     PageContext pageContext = _jspx_page_context;
/* 3784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3786 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3787 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3788 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3790 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 3791 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3792 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3794 */       return true;
/*      */     }
/* 3796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f16(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3802 */     PageContext pageContext = _jspx_page_context;
/* 3803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3805 */     InsertTag _jspx_th_tiles_005finsert_005f16 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 3806 */     _jspx_th_tiles_005finsert_005f16.setPageContext(_jspx_page_context);
/* 3807 */     _jspx_th_tiles_005finsert_005f16.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3809 */     _jspx_th_tiles_005finsert_005f16.setAttribute("Header");
/* 3810 */     int _jspx_eval_tiles_005finsert_005f16 = _jspx_th_tiles_005finsert_005f16.doStartTag();
/* 3811 */     if (_jspx_th_tiles_005finsert_005f16.doEndTag() == 5) {
/* 3812 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f16);
/* 3813 */       return true;
/*      */     }
/* 3815 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f16);
/* 3816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f17(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3821 */     PageContext pageContext = _jspx_page_context;
/* 3822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3824 */     InsertTag _jspx_th_tiles_005finsert_005f17 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 3825 */     _jspx_th_tiles_005finsert_005f17.setPageContext(_jspx_page_context);
/* 3826 */     _jspx_th_tiles_005finsert_005f17.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3828 */     _jspx_th_tiles_005finsert_005f17.setAttribute("ReportContent");
/* 3829 */     int _jspx_eval_tiles_005finsert_005f17 = _jspx_th_tiles_005finsert_005f17.doStartTag();
/* 3830 */     if (_jspx_th_tiles_005finsert_005f17.doEndTag() == 5) {
/* 3831 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f17);
/* 3832 */       return true;
/*      */     }
/* 3834 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f17);
/* 3835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f18(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3840 */     PageContext pageContext = _jspx_page_context;
/* 3841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3843 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f18 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 3844 */     _jspx_th_fmt_005fformatDate_005f18.setPageContext(_jspx_page_context);
/* 3845 */     _jspx_th_fmt_005fformatDate_005f18.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 3847 */     _jspx_th_fmt_005fformatDate_005f18.setValue("${strTime}");
/*      */     
/* 3849 */     _jspx_th_fmt_005fformatDate_005f18.setType("both");
/* 3850 */     int _jspx_eval_fmt_005fformatDate_005f18 = _jspx_th_fmt_005fformatDate_005f18.doStartTag();
/* 3851 */     if (_jspx_th_fmt_005fformatDate_005f18.doEndTag() == 5) {
/* 3852 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f18);
/* 3853 */       return true;
/*      */     }
/* 3855 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f18);
/* 3856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f19(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3861 */     PageContext pageContext = _jspx_page_context;
/* 3862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3864 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f19 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 3865 */     _jspx_th_fmt_005fformatDate_005f19.setPageContext(_jspx_page_context);
/* 3866 */     _jspx_th_fmt_005fformatDate_005f19.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 3868 */     _jspx_th_fmt_005fformatDate_005f19.setValue("${endTime}");
/*      */     
/* 3870 */     _jspx_th_fmt_005fformatDate_005f19.setType("both");
/* 3871 */     int _jspx_eval_fmt_005fformatDate_005f19 = _jspx_th_fmt_005fformatDate_005f19.doStartTag();
/* 3872 */     if (_jspx_th_fmt_005fformatDate_005f19.doEndTag() == 5) {
/* 3873 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f19);
/* 3874 */       return true;
/*      */     }
/* 3876 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f19);
/* 3877 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\Reports_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */