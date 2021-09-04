/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class CAMLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public String getHelpLink(String key)
/*      */   {
/*   38 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   39 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   42 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   43 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   44 */       set = AMConnectionPool.executeQueryStmt(query);
/*   45 */       if (set.next())
/*      */       {
/*   47 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   50 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   56 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*   73 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   66 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   75 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   81 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(6);
/*   82 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   83 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*   84 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   85 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   86 */     _jspx_dependants.put("/jsp/includes/IT360Includes.jspf", Long.valueOf(1473429417000L));
/*   87 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
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
/*  110 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  114 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  130 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  134 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  135 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  136 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  138 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  139 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  140 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  141 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  142 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  143 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  144 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  145 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  146 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  147 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  148 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  155 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  158 */     JspWriter out = null;
/*  159 */     Object page = this;
/*  160 */     JspWriter _jspx_out = null;
/*  161 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  165 */       response.setContentType("text/html;charset=UTF-8");
/*  166 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  168 */       _jspx_page_context = pageContext;
/*  169 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  170 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  171 */       session = pageContext.getSession();
/*  172 */       out = pageContext.getOut();
/*  173 */       _jspx_out = out;
/*      */       
/*  175 */       out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  176 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  178 */       String resourceid = request.getParameter("resourceid");
/*  179 */       String searchQuery = request.getParameter("query");
/*  180 */       String uri = (String)request.getAttribute("uri");
/*  181 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  182 */         searchQuery = "";
/*      */       }
/*  184 */       String addtotitle = "";
/*  185 */       if (Constants.isMinimizedversion())
/*      */       {
/*  187 */         addtotitle = " " + Constants.getCategorytype() + " Edition";
/*      */       }
/*  189 */       String responseTimeMessage = FormatUtil.getString("webclient.server.responsetime");
/*  190 */       String systemTimeMessage = FormatUtil.getString("webclient.server.systemtime");
/*  191 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  193 */         responseTimeMessage = FormatUtil.getString("webclient.server.responsetime.managedserver");
/*  194 */         systemTimeMessage = FormatUtil.getString("webclient.server.systemtime.managedserver");
/*      */       }
/*      */       
/*      */ 
/*  198 */       out.write(10);
/*  199 */       Properties applications = null;
/*  200 */       synchronized (application) {
/*  201 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  202 */         if (applications == null) {
/*  203 */           applications = new Properties();
/*  204 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  207 */       out.write("    \n<html>\n<head>\n<title>");
/*  208 */       out.print(FormatUtil.getString("am.webclient.MGAM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  209 */       out.print(addtotitle);
/*  210 */       out.write(32);
/*  211 */       out.write(45);
/*  212 */       out.write(32);
/*  213 */       if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */         return;
/*  215 */       out.write("</title>\n");
/*  216 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  217 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  219 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  220 */       out.write(10);
/*  221 */       out.write(10);
/*  222 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  223 */       out.write(10);
/*  224 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  225 */       out.print(request.getContextPath());
/*  226 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  227 */       out.print(request.getContextPath());
/*  228 */       out.write("'); //No I18N\n</script>\n");
/*  229 */       if (Constants.isIt360) {
/*  230 */         out.write("<script src='");
/*  231 */         out.print(request.getContextPath());
/*  232 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  234 */       out.write(10);
/*  235 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  237 */       out.write("  \n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<script>   \n\nfunction logout()\n{\n\tlocation.href=\"/Logout.do\"; \n}\n \n function refer(str)\n {  \n \tlocation.href=str\n }\n\nfunction fnOpenNewWindow(url)\n{\n\tfnOpenNewWindowWithHeightWidth(url,'720','460');\n}\n\nfunction fnOpenNewWindowWithHeightWidth(url,wwidth,hheight)\n{\n\n\tvar d=new Date();\n\tvar window2=window.open(url+'&sid='+d,'secondWindow','resizable=yes,scrollbars=yes,width='+wwidth+',height='+hheight);\n\twindow2.focus();\n}\n\nfunction fnOpenWindow(url)\n{\n  var win=window.open(url,'','resizable=no,scrollbars=yes,width=760,height=420');\n  win.focus();\n}\nfunction showAlarms(resourceid)\n{\n\tvar d=new Date();\t\n\tMM_openBrWindow('/RecentAlarms.do?method=getAlarmsForResource&resourceid='+resourceid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\t\n}\n\nfunction doInitStuffOnBodyLoad() {\n\n    setFocusProperTextField();\n    if (window.myOnLoad)\n    {\n\tmyOnLoad();// any JSP can implement the\tmethod called myOnLoad() and it will get called dynamically :-)\n");
/*  238 */       out.write("    }\n    \n\n}\n \nfunction setFocusProperTextField() {\n    \n\n    var pos = document.forms.length;\n    if(pos > 0) { \n\n          if(document.forms.length >=2) {\n              pos = 1; // assuming 0 has search, hence 2rd might be the relevant one we need. \n          } \n          else\n          {\n          return \n          }\n\n          \n            for(i=0;i<document.forms[pos].elements.length;i++) {\n\n                if(document.forms[pos].elements[i].type =='text') {               \n\t\t\ttry \n\t\t\t{\n\t\t\t\tdocument.forms[pos].elements[i].focus(); \n\t\t\t\tbreak;\n\t\t\t}\n\t\t\tcatch (e) {}                    \n                    \t\n                }\n            }       \n    \t}\n}\n  \n </script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n</head>\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad();\">\n\n");
/*      */       
/*      */ 
/*  241 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  242 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  243 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  245 */       out.write(10);
/*      */       
/*  247 */       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  248 */       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  249 */       _jspx_th_c_005fif_005f1.setParent(null);
/*      */       
/*  251 */       _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  252 */       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  253 */       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */         for (;;) {
/*  255 */           out.write(10);
/*      */           
/*  257 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */           
/*  259 */           out.write(10);
/*  260 */           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  261 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  265 */       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  266 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */       }
/*      */       else {
/*  269 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  270 */         out.write(10);
/*  271 */         out.write(10);
/*  272 */         out.write(10);
/*      */         
/*  274 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  275 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  276 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  277 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  278 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  280 */             out.write("\n    ");
/*      */             
/*  282 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  283 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  284 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  286 */             _jspx_th_c_005fwhen_005f0.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  287 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  288 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  290 */                 out.write("\n\t<div id=\"userAreaContainerDiv\">    \n\t<div id=\"dhtmltooltip\"></div>\n        <div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\t\n\n\t");
/*  291 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  292 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  293 */                 out.write(10);
/*  294 */                 out.write(10);
/*  295 */                 out.write(10);
/*  296 */                 out.write(32);
/*      */                 
/*  298 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  299 */                 if (helpKey == null)
/*      */                 {
/*  301 */                   String tileName = request.getParameter("TileName");
/*  302 */                   if (tileName != null)
/*      */                   {
/*  304 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  306 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  308 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  310 */                       helpKey = "Send E-mail";
/*      */                     }
/*  312 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  314 */                       helpKey = "Send SMS";
/*      */                     }
/*  316 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  318 */                       helpKey = "Execute Program";
/*      */                     }
/*  320 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  322 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  327 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  328 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  330 */                 java.util.Enumeration en = request.getParameterNames();
/*  331 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  332 */                 while (en.hasMoreElements()) {
/*  333 */                   String reqKey = (String)en.nextElement();
/*  334 */                   String reqValue = request.getParameter(reqKey);
/*  335 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  339 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  341 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*  343 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  345 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  349 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  353 */                 out.println("\";");
/*      */                 
/*  355 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  357 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  358 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  359 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  361 */                 _jspx_th_c_005fif_005f2.setTest("${selectedscheme == 'slim'}");
/*  362 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  363 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  365 */                     out.write(10);
/*  366 */                     out.write(10);
/*      */                     
/*  368 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  370 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  372 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  373 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  374 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  375 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  377 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  378 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  379 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  380 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  381 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  382 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  383 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  384 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  385 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  386 */                       out.print(getHelpLink(helpKey));
/*  387 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  388 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  389 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  390 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  391 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  393 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  394 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  395 */                       out.write("</a>");
/*  396 */                       if (request.getRemoteUser() != null)
/*  397 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  398 */                         out.println("&nbsp;");
/*  399 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  401 */                     out.write(32);
/*  402 */                     out.write(32);
/*  403 */                     out.write(10);
/*      */                     
/*  405 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  407 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  409 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  410 */                       out.write(10);
/*  411 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  413 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  414 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  415 */                       out.write("</a></td>\n  ");
/*      */                       
/*  417 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  418 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  419 */                       _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  421 */                       _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  422 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  423 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/*  425 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  426 */                           if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                             return;
/*  428 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  429 */                           out.write("\n  </a></td>\n  ");
/*  430 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  431 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  435 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  436 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/*  439 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  440 */                       out.write(10);
/*  441 */                       out.write(32);
/*  442 */                       out.write(32);
/*      */                       
/*  444 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  445 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  446 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  448 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  449 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  450 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  452 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  454 */                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  455 */                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  456 */                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  457 */                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  458 */                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                             for (;;) {
/*  460 */                               out.write("\n   \t\t ");
/*      */                               
/*  462 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  463 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  464 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*  466 */                               _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  467 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  468 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/*  470 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  471 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                                     return;
/*  473 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  474 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  475 */                                   out.write("</a>\n   \t\t ");
/*  476 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  477 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  481 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  482 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/*  485 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  486 */                               out.write("\n   \t \t ");
/*      */                               
/*  488 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  489 */                               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  490 */                               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/*  491 */                               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  492 */                               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                 for (;;) {
/*  494 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  495 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/*  497 */                                   out.write("\" class=\"staticlinks\">");
/*  498 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  499 */                                   out.write("</a>\n   \t\t ");
/*  500 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  501 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  505 */                               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  506 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                               }
/*      */                               
/*  509 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  510 */                               out.write("\n   \t");
/*  511 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  512 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  516 */                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  517 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                           }
/*      */                           
/*  520 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  521 */                           out.write("\n\t</td> \n  ");
/*  522 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  523 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  527 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  528 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  531 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  532 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  533 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  534 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  535 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  536 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  537 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  538 */                       out.write("</a></td>\n\t");
/*      */                       
/*  540 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  541 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  542 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  544 */                       _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  545 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  546 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  548 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  549 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  550 */                           out.write("</a></td>\n\t");
/*  551 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  552 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  556 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  557 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  560 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  561 */                       out.write(10);
/*  562 */                       out.write(9);
/*      */                       
/*  564 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  565 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  566 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  568 */                       _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  569 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  570 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  572 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  573 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  574 */                           out.write("</a></td>\n\t");
/*  575 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  576 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  580 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  581 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  584 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  585 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  586 */                       out.print(getHelpLink(helpKey));
/*  587 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  588 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  589 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  590 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  591 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  593 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  594 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  595 */                       out.write("</a>");
/*  596 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  598 */                         out.write("&nbsp;");
/*  599 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  600 */                         out.write("\n    \t\t\t\t\t");
/*  601 */                       } else { out.println("&nbsp;"); }
/*  602 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  604 */                     out.write(10);
/*  605 */                     out.write(32);
/*  606 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  607 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  611 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  612 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  615 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  616 */                 out.write(" \n\n\t");
/*  617 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()))
/*      */                 {
/*  619 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  621 */                 out.write("\n\n</table>\n");
/*  622 */                 out.write("\n    \n\t \n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr> \n\n    <td valign=\"top\"> \n\t  \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     \n        \n        ");
/*      */                 
/*      */ 
/*  625 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/*  627 */                   String messagetosay = "";
/*  628 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/*  630 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  631 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/*  633 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/*  635 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/*  637 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/*  640 */                     out.write("\n                        <tr> \n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  641 */                     out.print(messagetosay);
/*  642 */                     out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n                <tr>\n                <td>&nbsp;\n                </td>\n                </tr>\n                \n                    ");
/*      */                   }
/*      */                   
/*      */ 
/*  646 */                   out.write("\n\t\t\t<tr> \n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\"><div class=\"centring\"> <span>");
/*      */                   
/*  648 */                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  649 */                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/*  650 */                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  652 */                   _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN,USERS");
/*  653 */                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/*  654 */                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                     for (;;) {
/*  656 */                       out.write("\n\t\t\t<div>");
/*  657 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  658 */                       out.write("</div>\n\t\t\t");
/*  659 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/*  660 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  664 */                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/*  665 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                   }
/*      */                   
/*  668 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*  669 */                   out.write(10);
/*      */                   
/*  671 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  672 */                   _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  673 */                   _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  675 */                   _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN,USERS");
/*  676 */                   int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  677 */                   if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                     for (;;) {
/*  679 */                       out.write("\n\t\t\t<div>");
/*  680 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  681 */                       out.write("</div>\n\t\t\t");
/*  682 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  683 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  687 */                   if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  688 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                   }
/*      */                   
/*  691 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  692 */                   out.write("\n</span></div></td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\t\n\t\t");
/*      */ 
/*      */                 }
/*  695 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/*  698 */                   out.write("\n\t\t<tr> \n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  699 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  700 */                   out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\t\t  </td>\n\t\t</tr>\t\t\n\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*  706 */                   out.write("  \n        ");
/*      */                   
/*  708 */                   MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  709 */                   _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  710 */                   _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  712 */                   _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                   
/*  714 */                   _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  715 */                   int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  716 */                   if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  717 */                     String msg = null;
/*  718 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  719 */                       out = _jspx_page_context.pushBody();
/*  720 */                       _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  721 */                       _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                     }
/*  723 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/*  725 */                       out.write("         \n        <tr> \n          <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n            \n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t    \t\t\t<tr>\n\t    \t\t    <td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                    <td class=\"msg-table-width\"> ");
/*  726 */                       if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                         return;
/*  728 */                       out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n            \n           \n            </td>\n            </tr>\n            </table>\n            \n            \n            \n            \n            </td>\n        </tr>\n        ");
/*  729 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  730 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*  731 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  734 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  735 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  738 */                   if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  739 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                   }
/*      */                   
/*  742 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  743 */                   out.write(32);
/*      */                   
/*  745 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  746 */                   _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  747 */                   _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  749 */                   _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  750 */                   int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  751 */                   if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                     for (;;) {
/*  753 */                       out.write(" \n        <tr> \n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*      */                       
/*  755 */                       MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  756 */                       _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  757 */                       _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                       
/*  759 */                       _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                       
/*  761 */                       _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  762 */                       int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  763 */                       if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  764 */                         String msg = null;
/*  765 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  766 */                           out = _jspx_page_context.pushBody();
/*  767 */                           _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  768 */                           _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                         }
/*  770 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/*  772 */                           out.write(" \n                  ");
/*  773 */                           if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                             return;
/*  775 */                           out.write("<br>\n                  ");
/*  776 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  777 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/*  778 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  781 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  782 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  785 */                       if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  786 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                       }
/*      */                       
/*  789 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  790 */                       out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n</td>\n</tr>\n</table></td>\n        </tr>\n        ");
/*  791 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  792 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  796 */                   if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  797 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                   }
/*      */                   
/*  800 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  801 */                   out.write(" \n        ");
/*      */                 }
/*  803 */                 out.write("\n        <tr> \n          <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/*  804 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  806 */                 out.write("</td>\n        </tr>\n\t\t \n\t\n      </table></td>\n\t       ");
/*  807 */                 if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  809 */                 out.write("\n\n    </tr>\n </table>\n");
/*  810 */                 if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  812 */                 out.write(" \n     \t");
/*  813 */                 if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  815 */                 out.write(" \n   </table>\n    </div>\n    ");
/*  816 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  817 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  821 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  822 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  825 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  826 */             out.write(10);
/*  827 */             out.write(32);
/*  828 */             out.write(32);
/*      */             
/*  830 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  831 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  832 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f0);
/*  833 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  834 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  836 */                 out.write(10);
/*  837 */                 out.write(10);
/*  838 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/*  840 */                 out.write(" \n <div id=\"userAreaContainerDiv\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  \n  <tr> \n      ");
/*  841 */                 if (!OEMUtil.isRemove("hide.leftArea")) {
/*  842 */                   out.write("\n   \n\n    <td height=\"350\"  align=\"center\" valign=\"top\" class=\"leftcells\" > \n    \t\n\n\n       ");
/*  843 */                   if (_jspx_meth_tiles_005finsert_005f5(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/*  845 */                   out.write("\n      \n      </td>");
/*      */                 }
/*  847 */                 out.write("\n    <td valign=\"top\"> \n\t  \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    \n        \n        ");
/*      */                 
/*      */ 
/*  850 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/*  852 */                   String messagetosay = "";
/*  853 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/*  855 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  856 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/*  858 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/*  860 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/*  862 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/*  865 */                     out.write("\n                        <tr> \n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  866 */                     out.print(messagetosay);
/*  867 */                     out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n                <tr>\n                <td>&nbsp;\n                </td>\n                </tr>\n                \n                    ");
/*      */                   }
/*      */                   
/*      */ 
/*  871 */                   out.write("\n\t\t\t<tr> \n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">\n\n\t\t\t<div class=\"centring\"> <span>");
/*      */                   
/*  873 */                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  874 */                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/*  875 */                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  877 */                   _jspx_th_logic_005fpresent_005f7.setRole("ENTERPRISEADMIN,USERS");
/*  878 */                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/*  879 */                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                     for (;;) {
/*  881 */                       out.write("\n                        <div>");
/*  882 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  883 */                       out.write("</div>\n                        ");
/*  884 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/*  885 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  889 */                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/*  890 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                   }
/*      */                   
/*  893 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*  894 */                   out.write(10);
/*      */                   
/*  896 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  897 */                   _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  898 */                   _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  900 */                   _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN,USERS");
/*  901 */                   int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  902 */                   if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                     for (;;) {
/*  904 */                       out.write("\n                        <div>");
/*  905 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  906 */                       out.write("</div>\n                        ");
/*  907 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  908 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  912 */                   if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  913 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                   }
/*      */                   
/*  916 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*  917 */                   out.write("\n</span></div>\n\t\t\t</td></tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\t\n\t\t");
/*      */ 
/*      */                 }
/*  920 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/*  923 */                   out.write("\n\t\t<tr> \n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n\t\t  \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  924 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  925 */                   out.write("</td>\n\t\t \t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n        </tr>\t\t\n\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*  931 */                   out.write("       \n        ");
/*      */                   
/*  933 */                   MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  934 */                   _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/*  935 */                   _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  937 */                   _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                   
/*  939 */                   _jspx_th_html_005fmessages_005f2.setMessage("false");
/*  940 */                   int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/*  941 */                   if (_jspx_eval_html_005fmessages_005f2 != 0) {
/*  942 */                     String msg = null;
/*  943 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  944 */                       out = _jspx_page_context.pushBody();
/*  945 */                       _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/*  946 */                       _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                     }
/*  948 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/*  950 */                       out.write(" \n        ");
/*  951 */                       if (_jspx_meth_c_005fif_005f4(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/*  953 */                       out.write("\n\n              ");
/*  954 */                       if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/*  956 */                       out.write("<br>\n           \n        ");
/*  957 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/*  958 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*  959 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  962 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/*  963 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  966 */                   if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/*  967 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                   }
/*      */                   
/*  970 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/*  971 */                   out.write(" \n        ");
/*  972 */                   if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/*  974 */                   out.write("\n        ");
/*      */                   
/*  976 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  977 */                   _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  978 */                   _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  980 */                   _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  981 */                   int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  982 */                   if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                     for (;;) {
/*  984 */                       out.write(" \n        <tr> \n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"90%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*      */                       
/*  986 */                       MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  987 */                       _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/*  988 */                       _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                       
/*  990 */                       _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                       
/*  992 */                       _jspx_th_html_005fmessages_005f3.setMessage("true");
/*  993 */                       int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/*  994 */                       if (_jspx_eval_html_005fmessages_005f3 != 0) {
/*  995 */                         String msg = null;
/*  996 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/*  997 */                           out = _jspx_page_context.pushBody();
/*  998 */                           _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/*  999 */                           _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                         }
/* 1001 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/* 1003 */                           out.write(" \n                  ");
/* 1004 */                           if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                             return;
/* 1006 */                           out.write("\n                  ");
/* 1007 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/* 1008 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/* 1009 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1012 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1013 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1016 */                       if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/* 1017 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                       }
/*      */                       
/* 1020 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/* 1021 */                       out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n</td>\n</tr>\n</table></td>\n        </tr>\n        ");
/* 1022 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1023 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1027 */                   if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1028 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                   }
/*      */                   
/* 1031 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1032 */                   out.write("\n        ");
/*      */                 }
/* 1034 */                 out.write("\n        \n         ");
/*      */                 
/* 1036 */                 PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1037 */                 _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 1038 */                 _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/* 1040 */                 _jspx_th_logic_005fpresent_005f8.setRole("OPERATOR");
/* 1041 */                 int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 1042 */                 if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                   for (;;) {
/* 1044 */                     out.write("\n\t\t       \t\t\t\t \n\t\t\t");
/*      */                     
/* 1046 */                     String mes = request.getParameter("message");
/* 1047 */                     if ((mes != null) && (mes.equals("false")))
/*      */                     {
/* 1049 */                       out.write("\n\t\t\t<tr> \n\t\t\t<td height=\"46\" valign=\"top\" class=\"tdindent\"> \n\t\t\t<table cellpadding=\"0\" border=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n\t\t\t<tr>\n\t\t\t<td width=\"99%\">\t\t\n\t\t\t\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t\t                <td class=\"msg-table-width\">");
/* 1050 */                       out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 1051 */                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t\t</tr>\n</table>\n\t\t\t\n\t\t\t\t\n\t\t\t\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                     }
/* 1053 */                     out.write("\n\t\t\t\n\t");
/* 1054 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 1055 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1059 */                 if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 1060 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                 }
/*      */                 
/* 1063 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 1064 */                 out.write("\n        \n        <tr> \n          <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/* 1065 */                 if (_jspx_meth_tiles_005finsert_005f6(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1067 */                 out.write("</td>\n        </tr>\n        <tr> \n          <td height=\"20%\" colspan=\"2\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t\n\t       \n       \n      </table></td>\n      <td  valign=\"top\" class=\"leftcells\" width=\"15%\">");
/* 1068 */                 if (_jspx_meth_tiles_005finsert_005f7(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1070 */                 out.write("\n      </td>\n  </tr>\n</table>\n  ");
/* 1071 */                 if (_jspx_meth_tiles_005finsert_005f8(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1073 */                 out.write(" \n   </table>\n</div><!-- userAreaContainerDiv ends -->\n");
/* 1074 */                 if (_jspx_meth_tiles_005finsert_005f9(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1076 */                 out.write(10);
/*      */                 
/* 1078 */                 Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                 
/* 1080 */                 out.write(10);
/* 1081 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1082 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1086 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1087 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/* 1090 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1091 */             out.write(10);
/* 1092 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1093 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1097 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1098 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/* 1101 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1102 */           out.write(10);
/* 1103 */           out.write(10);
/* 1104 */           out.write(10);
/* 1105 */           if (Constants.isIt360) {
/* 1106 */             out.write("\n<script type=\"text/javascript\">\n\tvar iframe= parent.window.document.getElementById('_iframe_view');\n\tif(iframe != null)\n\t{\n\t\tvar iframeName = iframe.name;\n\t\tif(iframeName != null && iframeName == '_iframe_view')\n\t\t{\n\t\t\tvar frameDoc = iframe.contentDocument || iframe.contentWindow.document;\n\t\t\tvar e = frameDoc.getElementById(\"userAreaContainerDiv\");\n\t\t\tif(e!=null)\n\t\t\t{\n\t\t\t\te.id = \"userAreaContainerDiv_admin\";\n\t\t\t}\n\t\t\n\t\t\tvar footer = frameDoc.getElementById(\"footer-container\");\n\t\t\tif(footer!=null)\n\t\t\t{\n\t\t\t\tfooter.innerHTML=\"\";\n\t\t\t}\n\t\t}\n\t}\n</script>\n");
/*      */           }
/* 1108 */           out.write(10);
/* 1109 */           out.write(10);
/* 1110 */           out.write(10);
/* 1111 */           out.write(10);
/*      */           
/* 1113 */           if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1118 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 1119 */             out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/* 1120 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 1124 */           out.write("\n</body>\n</html>\n\n");
/*      */         }
/* 1126 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1127 */         out = _jspx_out;
/* 1128 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1129 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1130 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1133 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1139 */     PageContext pageContext = _jspx_page_context;
/* 1140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1142 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 1143 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 1144 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/* 1146 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 1147 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 1148 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 1149 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1150 */       return true;
/*      */     }
/* 1152 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1153 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1158 */     PageContext pageContext = _jspx_page_context;
/* 1159 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1161 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1162 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1163 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1165 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1167 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1168 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1169 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1170 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1171 */       return true;
/*      */     }
/* 1173 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1179 */     PageContext pageContext = _jspx_page_context;
/* 1180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1182 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1183 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1184 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 1186 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod && empty param.noreload}");
/* 1187 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1188 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1190 */         out.write(" \n <META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1191 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1192 */           return true;
/* 1193 */         out.write(34);
/* 1194 */         out.write(62);
/* 1195 */         out.write(10);
/* 1196 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1197 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1201 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1202 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1203 */       return true;
/*      */     }
/* 1205 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1211 */     PageContext pageContext = _jspx_page_context;
/* 1212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1214 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1215 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1216 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1218 */     _jspx_th_c_005fout_005f1.setValue("${reloadperiod}");
/* 1219 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1220 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1221 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1222 */       return true;
/*      */     }
/* 1224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1230 */     PageContext pageContext = _jspx_page_context;
/* 1231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1233 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1234 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1235 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1237 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1239 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1240 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1242 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1243 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1245 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1246 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1247 */             return true;
/* 1248 */           out.write("\"\" class=\"staticlinks\">");
/* 1249 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1250 */             return true;
/* 1251 */           out.write("</a></td>\n");
/* 1252 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1253 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1257 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1258 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1261 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1262 */         out = _jspx_page_context.popBody(); }
/* 1263 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1265 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1266 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1273 */     PageContext pageContext = _jspx_page_context;
/* 1274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1276 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1277 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1278 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1280 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABLINK}");
/* 1281 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1282 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1283 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1284 */       return true;
/*      */     }
/* 1286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1292 */     PageContext pageContext = _jspx_page_context;
/* 1293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1295 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1296 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1297 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1299 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 1300 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1301 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1303 */       return true;
/*      */     }
/* 1305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1311 */     PageContext pageContext = _jspx_page_context;
/* 1312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1314 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1315 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1316 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1318 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1320 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1321 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1323 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1324 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1326 */           out.write(10);
/* 1327 */           out.write(10);
/* 1328 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1329 */             return true;
/* 1330 */           out.write(10);
/* 1331 */           out.write(10);
/* 1332 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1333 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1337 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1338 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1341 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1342 */         out = _jspx_page_context.popBody(); }
/* 1343 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1345 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1346 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1353 */     PageContext pageContext = _jspx_page_context;
/* 1354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1356 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1357 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1358 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1359 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1360 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1362 */         out.write(10);
/* 1363 */         out.write(10);
/* 1364 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1365 */           return true;
/* 1366 */         out.write(10);
/* 1367 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1368 */           return true;
/* 1369 */         out.write(10);
/* 1370 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1371 */           return true;
/* 1372 */         out.write("\n\n\n\n\n");
/* 1373 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1378 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1379 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1380 */       return true;
/*      */     }
/* 1382 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1388 */     PageContext pageContext = _jspx_page_context;
/* 1389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1391 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1392 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1393 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1395 */     _jspx_th_c_005fwhen_005f1.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1396 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1397 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1399 */         out.write(10);
/* 1400 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1401 */           return true;
/* 1402 */         out.write(10);
/* 1403 */         out.write(32);
/* 1404 */         out.write(32);
/* 1405 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1406 */           return true;
/* 1407 */         out.write(10);
/* 1408 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1413 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1414 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1415 */       return true;
/*      */     }
/* 1417 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1423 */     PageContext pageContext = _jspx_page_context;
/* 1424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1426 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1427 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1428 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1430 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1431 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1432 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1434 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1435 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1436 */           return true;
/* 1437 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1438 */           return true;
/* 1439 */         out.write("\n  </a></td>\n  ");
/* 1440 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1445 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1446 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1447 */       return true;
/*      */     }
/* 1449 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1455 */     PageContext pageContext = _jspx_page_context;
/* 1456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1458 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1459 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1460 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1461 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1462 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1464 */         out.write("\n  \t\t");
/* 1465 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1466 */           return true;
/* 1467 */         out.write("\n  \t\t");
/* 1468 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1469 */           return true;
/* 1470 */         out.write("\n  \t\t");
/* 1471 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1472 */           return true;
/* 1473 */         out.write("\n  \t");
/* 1474 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1475 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1479 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1480 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1481 */       return true;
/*      */     }
/* 1483 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1489 */     PageContext pageContext = _jspx_page_context;
/* 1490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1492 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1493 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1494 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1496 */     _jspx_th_c_005fwhen_005f2.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1497 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1498 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1500 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1501 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1502 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1506 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1507 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1508 */       return true;
/*      */     }
/* 1510 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1516 */     PageContext pageContext = _jspx_page_context;
/* 1517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1519 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1520 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1521 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1523 */     _jspx_th_c_005fwhen_005f3.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1524 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1525 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1527 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1528 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1529 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1533 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1534 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1535 */       return true;
/*      */     }
/* 1537 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1543 */     PageContext pageContext = _jspx_page_context;
/* 1544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1546 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1547 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1548 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1549 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1550 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1552 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1553 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1554 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1558 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1559 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1560 */       return true;
/*      */     }
/* 1562 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1568 */     PageContext pageContext = _jspx_page_context;
/* 1569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1571 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1572 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1573 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 1575 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1576 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1577 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1578 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1579 */       return true;
/*      */     }
/* 1581 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1587 */     PageContext pageContext = _jspx_page_context;
/* 1588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1590 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1591 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1592 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1594 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1595 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1596 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1598 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1599 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1600 */           return true;
/* 1601 */         out.write("\n\t</td> \n  ");
/* 1602 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1603 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1607 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1608 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1609 */       return true;
/*      */     }
/* 1611 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1617 */     PageContext pageContext = _jspx_page_context;
/* 1618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1620 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1621 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1622 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 1623 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1624 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1626 */         out.write("\n   \t\t ");
/* 1627 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1628 */           return true;
/* 1629 */         out.write("\n   \t \t ");
/* 1630 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1631 */           return true;
/* 1632 */         out.write("\n   \t");
/* 1633 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1634 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1638 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1639 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1640 */       return true;
/*      */     }
/* 1642 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1648 */     PageContext pageContext = _jspx_page_context;
/* 1649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1651 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1652 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1653 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1655 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1656 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1657 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1659 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1660 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1661 */           return true;
/* 1662 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1663 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1664 */           return true;
/* 1665 */         out.write("</a>\n   \t\t ");
/* 1666 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1667 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1671 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1672 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1673 */       return true;
/*      */     }
/* 1675 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1681 */     PageContext pageContext = _jspx_page_context;
/* 1682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1684 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1685 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1686 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1688 */     _jspx_th_c_005fout_005f5.setValue("${globalconfig['defaultmonitorsview']}");
/* 1689 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1690 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1691 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1692 */       return true;
/*      */     }
/* 1694 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1700 */     PageContext pageContext = _jspx_page_context;
/* 1701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1703 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1704 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1705 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1707 */     _jspx_th_c_005fout_005f6.setValue("${tab.TABNAME}");
/* 1708 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1709 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1711 */       return true;
/*      */     }
/* 1713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1719 */     PageContext pageContext = _jspx_page_context;
/* 1720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1722 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1723 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1724 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1725 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1726 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1728 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1729 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1730 */           return true;
/* 1731 */         out.write("\" class=\"staticlinks\">");
/* 1732 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1733 */           return true;
/* 1734 */         out.write("</a>\n   \t\t ");
/* 1735 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1736 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1740 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1741 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1742 */       return true;
/*      */     }
/* 1744 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1750 */     PageContext pageContext = _jspx_page_context;
/* 1751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1753 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1754 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1755 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1757 */     _jspx_th_c_005fout_005f7.setValue("${globalconfig['defaultmonitorsview']}");
/* 1758 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1759 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1761 */       return true;
/*      */     }
/* 1763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1769 */     PageContext pageContext = _jspx_page_context;
/* 1770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1772 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1773 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1774 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1776 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 1777 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1778 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1779 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1780 */       return true;
/*      */     }
/* 1782 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1788 */     PageContext pageContext = _jspx_page_context;
/* 1789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1791 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1792 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1793 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1795 */     _jspx_th_c_005fwhen_005f5.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 1796 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1797 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1799 */         out.write(10);
/* 1800 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1801 */           return true;
/* 1802 */         out.write(10);
/* 1803 */         out.write(9);
/* 1804 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1805 */           return true;
/* 1806 */         out.write(10);
/* 1807 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1808 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1812 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1813 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1814 */       return true;
/*      */     }
/* 1816 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1822 */     PageContext pageContext = _jspx_page_context;
/* 1823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1825 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1826 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1827 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1829 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1830 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1831 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1833 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1834 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1835 */           return true;
/* 1836 */         out.write("</a></td>\n\t");
/* 1837 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1838 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1842 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1843 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1844 */       return true;
/*      */     }
/* 1846 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1852 */     PageContext pageContext = _jspx_page_context;
/* 1853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1855 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1856 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1857 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1859 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 1860 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1861 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1862 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1863 */       return true;
/*      */     }
/* 1865 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1871 */     PageContext pageContext = _jspx_page_context;
/* 1872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1874 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1875 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1876 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1878 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 1879 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1880 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1882 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1883 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1884 */           return true;
/* 1885 */         out.write("</a></td>\n\t");
/* 1886 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1887 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1891 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1892 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1893 */       return true;
/*      */     }
/* 1895 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1901 */     PageContext pageContext = _jspx_page_context;
/* 1902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1904 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1905 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1906 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 1908 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 1909 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1910 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1912 */       return true;
/*      */     }
/* 1914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1920 */     PageContext pageContext = _jspx_page_context;
/* 1921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1923 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1924 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1925 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1926 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1927 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 1929 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 1930 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1931 */           return true;
/* 1932 */         out.write("\" class=\"staticlinks\">\t");
/* 1933 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1934 */           return true;
/* 1935 */         out.write("</a></td>\n");
/* 1936 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1937 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1941 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1942 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1943 */       return true;
/*      */     }
/* 1945 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1951 */     PageContext pageContext = _jspx_page_context;
/* 1952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1954 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1955 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1956 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1958 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABLINK}");
/* 1959 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1960 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1961 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1962 */       return true;
/*      */     }
/* 1964 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1970 */     PageContext pageContext = _jspx_page_context;
/* 1971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1973 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1974 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1975 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1977 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABNAME}");
/* 1978 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1979 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1981 */       return true;
/*      */     }
/* 1983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1989 */     PageContext pageContext = _jspx_page_context;
/* 1990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1992 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1993 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1994 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 1995 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1996 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 1998 */         out.write("\n  \t\t");
/* 1999 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2000 */           return true;
/* 2001 */         out.write("\n  \t\t");
/* 2002 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2003 */           return true;
/* 2004 */         out.write("\n  \t\t");
/* 2005 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2006 */           return true;
/* 2007 */         out.write("\n  \t");
/* 2008 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2009 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2013 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2014 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2015 */       return true;
/*      */     }
/* 2017 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2023 */     PageContext pageContext = _jspx_page_context;
/* 2024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2026 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2027 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2028 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2030 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2031 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2032 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 2034 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 2035 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2040 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2041 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2042 */       return true;
/*      */     }
/* 2044 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2050 */     PageContext pageContext = _jspx_page_context;
/* 2051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2053 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2054 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2055 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2057 */     _jspx_th_c_005fwhen_005f7.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2058 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2059 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 2061 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2062 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2063 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2067 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2068 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2069 */       return true;
/*      */     }
/* 2071 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2077 */     PageContext pageContext = _jspx_page_context;
/* 2078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2080 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2081 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2082 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2083 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2084 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2086 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 2087 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2092 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2093 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2094 */       return true;
/*      */     }
/* 2096 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2102 */     PageContext pageContext = _jspx_page_context;
/* 2103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2105 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2106 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2107 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 2109 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 2110 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2111 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2113 */       return true;
/*      */     }
/* 2115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2121 */     PageContext pageContext = _jspx_page_context;
/* 2122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2124 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2125 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2126 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 2128 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2129 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2130 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2132 */       return true;
/*      */     }
/* 2134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2140 */     PageContext pageContext = _jspx_page_context;
/* 2141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2143 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2144 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2145 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2147 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2149 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2150 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2151 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2152 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2153 */       return true;
/*      */     }
/* 2155 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2161 */     PageContext pageContext = _jspx_page_context;
/* 2162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2164 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2165 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2166 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2168 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2170 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2171 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2172 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2173 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2174 */       return true;
/*      */     }
/* 2176 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2182 */     PageContext pageContext = _jspx_page_context;
/* 2183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2185 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2186 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2187 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2189 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 2190 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2191 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2192 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2193 */       return true;
/*      */     }
/* 2195 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2201 */     PageContext pageContext = _jspx_page_context;
/* 2202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2204 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2205 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2206 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2208 */     _jspx_th_c_005fif_005f3.setTest("${selectedscheme == 'slim'}");
/* 2209 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2210 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2212 */         out.write(" <td  valign=\"top\" class=\"leftcells\" width=\"15%\">");
/* 2213 */         if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 2214 */           return true;
/* 2215 */         out.write("\n      </td>");
/* 2216 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2221 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2222 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2223 */       return true;
/*      */     }
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2231 */     PageContext pageContext = _jspx_page_context;
/* 2232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2234 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2235 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2236 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2238 */     _jspx_th_tiles_005finsert_005f1.setAttribute("ServerLeftArea");
/* 2239 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2240 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2241 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2242 */       return true;
/*      */     }
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2250 */     PageContext pageContext = _jspx_page_context;
/* 2251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2253 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2254 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2255 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2257 */     _jspx_th_tiles_005finsert_005f2.setAttribute("CustomUserArea");
/* 2258 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2259 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2260 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2261 */       return true;
/*      */     }
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2269 */     PageContext pageContext = _jspx_page_context;
/* 2270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2272 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2273 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2274 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2276 */     _jspx_th_tiles_005finsert_005f3.setAttribute("footer");
/* 2277 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2278 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2279 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2280 */       return true;
/*      */     }
/* 2282 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2288 */     PageContext pageContext = _jspx_page_context;
/* 2289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2291 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2292 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2293 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2295 */     _jspx_th_tiles_005finsert_005f4.setAttribute("Header");
/* 2296 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2297 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2298 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2299 */       return true;
/*      */     }
/* 2301 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f5(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2307 */     PageContext pageContext = _jspx_page_context;
/* 2308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2310 */     InsertTag _jspx_th_tiles_005finsert_005f5 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2311 */     _jspx_th_tiles_005finsert_005f5.setPageContext(_jspx_page_context);
/* 2312 */     _jspx_th_tiles_005finsert_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2314 */     _jspx_th_tiles_005finsert_005f5.setAttribute("LeftArea");
/* 2315 */     int _jspx_eval_tiles_005finsert_005f5 = _jspx_th_tiles_005finsert_005f5.doStartTag();
/* 2316 */     if (_jspx_th_tiles_005finsert_005f5.doEndTag() == 5) {
/* 2317 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2318 */       return true;
/*      */     }
/* 2320 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2326 */     PageContext pageContext = _jspx_page_context;
/* 2327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2329 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2330 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2331 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2333 */     _jspx_th_c_005fif_005f4.setTest("${empty firstrow}");
/* 2334 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2335 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2337 */         out.write("\n        \n           <tr> \n\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n                <tr>\n                <td width=\"99%\">\n                \n                \n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t\t\t<tr>\n\t\t\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t\t\t<tr>\n\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t                <td class=\"msg-table-width\">");
/* 2338 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2339 */           return true;
/* 2340 */         out.write("\n        ");
/* 2341 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2342 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2346 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2347 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2348 */       return true;
/*      */     }
/* 2350 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2356 */     PageContext pageContext = _jspx_page_context;
/* 2357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2359 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2360 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2361 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2363 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 2365 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 2366 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2367 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2368 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2369 */       return true;
/*      */     }
/* 2371 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2377 */     PageContext pageContext = _jspx_page_context;
/* 2378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2380 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2381 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2382 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2384 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2386 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2387 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2388 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2389 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2390 */       return true;
/*      */     }
/* 2392 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2398 */     PageContext pageContext = _jspx_page_context;
/* 2399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2401 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2402 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2403 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2405 */     _jspx_th_c_005fif_005f5.setTest("${!empty firstrow}");
/* 2406 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2407 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2409 */         out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t</tr>\n</table>\n                \n                \n      \n                \n                </td>\n                </tr>\n                </table></td>\n        </tr>\n        ");
/* 2410 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2411 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2415 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2416 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2417 */       return true;
/*      */     }
/* 2419 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2425 */     PageContext pageContext = _jspx_page_context;
/* 2426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2428 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2429 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2430 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2432 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2434 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2435 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2436 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2437 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2438 */       return true;
/*      */     }
/* 2440 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f6(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2446 */     PageContext pageContext = _jspx_page_context;
/* 2447 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2449 */     InsertTag _jspx_th_tiles_005finsert_005f6 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2450 */     _jspx_th_tiles_005finsert_005f6.setPageContext(_jspx_page_context);
/* 2451 */     _jspx_th_tiles_005finsert_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2453 */     _jspx_th_tiles_005finsert_005f6.setAttribute("UserArea");
/* 2454 */     int _jspx_eval_tiles_005finsert_005f6 = _jspx_th_tiles_005finsert_005f6.doStartTag();
/* 2455 */     if (_jspx_th_tiles_005finsert_005f6.doEndTag() == 5) {
/* 2456 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2457 */       return true;
/*      */     }
/* 2459 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f7(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2465 */     PageContext pageContext = _jspx_page_context;
/* 2466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2468 */     InsertTag _jspx_th_tiles_005finsert_005f7 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2469 */     _jspx_th_tiles_005finsert_005f7.setPageContext(_jspx_page_context);
/* 2470 */     _jspx_th_tiles_005finsert_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2472 */     _jspx_th_tiles_005finsert_005f7.setAttribute("ServerLeftArea");
/* 2473 */     int _jspx_eval_tiles_005finsert_005f7 = _jspx_th_tiles_005finsert_005f7.doStartTag();
/* 2474 */     if (_jspx_th_tiles_005finsert_005f7.doEndTag() == 5) {
/* 2475 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2476 */       return true;
/*      */     }
/* 2478 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f8(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2484 */     PageContext pageContext = _jspx_page_context;
/* 2485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2487 */     InsertTag _jspx_th_tiles_005finsert_005f8 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2488 */     _jspx_th_tiles_005finsert_005f8.setPageContext(_jspx_page_context);
/* 2489 */     _jspx_th_tiles_005finsert_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2491 */     _jspx_th_tiles_005finsert_005f8.setAttribute("CustomUserArea");
/* 2492 */     int _jspx_eval_tiles_005finsert_005f8 = _jspx_th_tiles_005finsert_005f8.doStartTag();
/* 2493 */     if (_jspx_th_tiles_005finsert_005f8.doEndTag() == 5) {
/* 2494 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f8);
/* 2495 */       return true;
/*      */     }
/* 2497 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f8);
/* 2498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f9(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2503 */     PageContext pageContext = _jspx_page_context;
/* 2504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2506 */     InsertTag _jspx_th_tiles_005finsert_005f9 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2507 */     _jspx_th_tiles_005finsert_005f9.setPageContext(_jspx_page_context);
/* 2508 */     _jspx_th_tiles_005finsert_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2510 */     _jspx_th_tiles_005finsert_005f9.setAttribute("footer");
/* 2511 */     int _jspx_eval_tiles_005finsert_005f9 = _jspx_th_tiles_005finsert_005f9.doStartTag();
/* 2512 */     if (_jspx_th_tiles_005finsert_005f9.doEndTag() == 5) {
/* 2513 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f9);
/* 2514 */       return true;
/*      */     }
/* 2516 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f9);
/* 2517 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\CAMLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */