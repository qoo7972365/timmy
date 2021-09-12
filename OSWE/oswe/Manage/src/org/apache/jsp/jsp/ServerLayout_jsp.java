/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ServerLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   private String getResourceType(String resourceid)
/*      */   {
/*   44 */     String type = "";
/*      */     try
/*      */     {
/*   47 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   48 */       String q1 = "select TYPE from AM_ManagedObject where resourceid=" + resourceid;
/*   49 */       ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/*   50 */       if (rs.next())
/*      */       {
/*   52 */         type = rs.getString(1);
/*      */       }
/*   54 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*   58 */       exc.printStackTrace();
/*      */     }
/*   60 */     return type;
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key)
/*      */   {
/*   65 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   66 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   69 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   70 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   71 */       set = AMConnectionPool.executeQueryStmt(query);
/*   72 */       if (set.next())
/*      */       {
/*   74 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   77 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   83 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*  100 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   93 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  102 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  108 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(6);
/*  109 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*  110 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*  111 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*  112 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*  113 */     _jspx_dependants.put("/jsp/includes/IT360Includes.jspf", Long.valueOf(1473429417000L));
/*  114 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
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
/*  137 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  141 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  142 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  143 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  145 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  146 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  147 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  148 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  149 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  150 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  151 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  152 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  153 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  154 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  155 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  156 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  157 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  162 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  163 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  164 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  165 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  166 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  167 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  168 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  169 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  170 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  171 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  172 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  173 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  174 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  175 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  182 */     HttpSession session = null;
/*      */     
/*      */ 
/*  185 */     JspWriter out = null;
/*  186 */     Object page = this;
/*  187 */     JspWriter _jspx_out = null;
/*  188 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  192 */       response.setContentType("text/html;charset=UTF-8");
/*  193 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  195 */       _jspx_page_context = pageContext;
/*  196 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  197 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  198 */       session = pageContext.getSession();
/*  199 */       out = pageContext.getOut();
/*  200 */       _jspx_out = out;
/*      */       
/*  202 */       out.write("<!DOCTYPE html>\n");
/*  203 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  204 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  206 */       request.setAttribute("isPlugin", PluginUtil.isPlugin() + "");
/*      */       
/*      */ 
/*  209 */       String resourceid = request.getParameter("resourceid");
/*  210 */       String searchQuery = request.getParameter("query");
/*  211 */       String uri = (String)request.getAttribute("uri");
/*  212 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  213 */         searchQuery = "";
/*      */       }
/*  215 */       String hideLeftAreaStr = (String)request.getAttribute("hideLeftArea");
/*  216 */       boolean hideLeftArea = (hideLeftAreaStr != null) && (hideLeftAreaStr.equals("true"));
/*      */       
/*  218 */       out.write(10);
/*  219 */       Properties applications = null;
/*  220 */       synchronized (application) {
/*  221 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  222 */         if (applications == null) {
/*  223 */           applications = new Properties();
/*  224 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  227 */       out.write("\n<html>\n<head>\n");
/*  228 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  229 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  231 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  232 */       out.write(10);
/*  233 */       out.write(10);
/*  234 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  235 */       out.write(10);
/*  236 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  237 */       out.print(request.getContextPath());
/*  238 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  239 */       out.print(request.getContextPath());
/*  240 */       out.write("'); //No I18N\n</script>\n");
/*  241 */       if (Constants.isIt360) {
/*  242 */         out.write("<script src='");
/*  243 */         out.print(request.getContextPath());
/*  244 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  246 */       out.write(10);
/*      */       
/*  248 */       if (Constants.isIt360)
/*      */       {
/*  250 */         out.write("\n<title>");
/*  251 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  252 */         out.write("</title>\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  257 */         out.write("\n<title>");
/*  258 */         out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  259 */         out.write(32);
/*  260 */         out.write(45);
/*  261 */         out.write(32);
/*  262 */         if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */           return;
/*  264 */         out.write("</title>\n");
/*      */       }
/*  266 */       out.write(10);
/*  267 */       out.write(10);
/*  268 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  270 */       out.write("\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<script>\n\nfunction logout()\n{\n\tlocation.href=\"/Logout.do\";\n}\n\n function refer(str)\n {\n \tlocation.href=str\n }\n\nfunction fnOpenNewWindow(url)\n{\n\tfnOpenNewWindowWithHeightWidth(url,'720','460');\n}\n\nfunction fnOpenNewWindowWithHeightWidth(url,wwidth,hheight)\n{\n\n\tvar d=new Date();\n\tvar window2=window.open(url+'&sid='+d,'secondWindow','resizable=yes,scrollbars=yes,width='+wwidth+',height='+hheight);\n\twindow2.focus();\n}\n\nfunction fnOpenWindow(url)\n{\n  var win=window.open(url,'','resizable=no,scrollbars=yes,width=760,height=420');\n  win.focus();\n}\nfunction showAlarms(resourceid)\n{\n\tvar d=new Date();\n\tMM_openBrWindow('/RecentAlarms.do?method=getAlarmsForResource&resourceid='+resourceid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\n}\n\nfunction doInitStuffOnBodyLoad() {\n\n    setFocusProperTextField();\n    if (window.myOnLoad)\n    {\n\tmyOnLoad();// any JSP can implement the\tmethod called myOnLoad() and it will get called dynamically :-)\n");
/*  271 */       out.write("    }\n\n\n}\n\nfunction setFocusProperTextField() {\n\n\n    var pos = document.forms.length;\n    if(pos > 0) {\n\n          if(document.forms.length >=2) {\n              pos = 1; // assuming 0 has search, hence 2rd might be the relevant one we need.\n          }\n          else\n          {\n          return\n          }\n\n\n            for(i=0;i<document.forms[pos].elements.length;i++) {\n\n                if(document.forms[pos].elements[i].type =='text') {\n\t\t\ttry\n\t\t\t{\n\t\t\t\tdocument.forms[pos].elements[i].focus();\n\t\t\t\tbreak;\n\t\t\t}\n\t\t\tcatch (e) {}\n\n                }\n            }\n    \t}\n}\n\n </script>\n\n</head>\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad();\">\n");
/*      */       
/*  273 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  274 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  275 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  277 */       out.write(10);
/*      */       
/*  279 */       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  280 */       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  281 */       _jspx_th_c_005fif_005f1.setParent(null);
/*      */       
/*  283 */       _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  284 */       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  285 */       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */         for (;;) {
/*  287 */           out.write(10);
/*      */           
/*  289 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */           
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
/*  304 */         out.write(10);
/*  305 */         out.write(10);
/*  306 */         out.write(10);
/*      */         
/*  308 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  309 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  310 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  311 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  312 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  314 */             out.write("\n    ");
/*      */             
/*  316 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  317 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  318 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  320 */             _jspx_th_c_005fwhen_005f0.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  321 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  322 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  324 */                 out.write("\n\t<div id=\"userAreaContainerDiv\">\n    <div id=\"dhtmltooltip\"></div>\n   <div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n\n              ");
/*  325 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  326 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  327 */                 out.write(10);
/*  328 */                 out.write(10);
/*  329 */                 out.write(10);
/*  330 */                 out.write(32);
/*      */                 
/*  332 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  333 */                 if (helpKey == null)
/*      */                 {
/*  335 */                   String tileName = request.getParameter("TileName");
/*  336 */                   if (tileName != null)
/*      */                   {
/*  338 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  340 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  342 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  344 */                       helpKey = "Send E-mail";
/*      */                     }
/*  346 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  348 */                       helpKey = "Send SMS";
/*      */                     }
/*  350 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  352 */                       helpKey = "Execute Program";
/*      */                     }
/*  354 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  356 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  361 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  362 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  364 */                 Enumeration en = request.getParameterNames();
/*  365 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  366 */                 while (en.hasMoreElements()) {
/*  367 */                   String reqKey = (String)en.nextElement();
/*  368 */                   String reqValue = request.getParameter(reqKey);
/*  369 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  373 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  375 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*  377 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  379 */                       out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  383 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  387 */                 out.println("\";");
/*      */                 
/*  389 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  391 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  392 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  393 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  395 */                 _jspx_th_c_005fif_005f2.setTest("${selectedscheme == 'slim'}");
/*  396 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  397 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  399 */                     out.write(10);
/*  400 */                     out.write(10);
/*      */                     
/*  402 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  404 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  406 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  407 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  408 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  409 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  411 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  412 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  413 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  414 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  415 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  416 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  417 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  418 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  419 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  420 */                       out.print(getHelpLink(helpKey));
/*  421 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  422 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  423 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  424 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  425 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  427 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  428 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  429 */                       out.write("</a>");
/*  430 */                       if (request.getRemoteUser() != null)
/*  431 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  432 */                         out.println("&nbsp;");
/*  433 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  435 */                     out.write(32);
/*  436 */                     out.write(32);
/*  437 */                     out.write(10);
/*      */                     
/*  439 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  441 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  443 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  444 */                       out.write(10);
/*  445 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  447 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  448 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  449 */                       out.write("</a></td>\n  ");
/*      */                       
/*  451 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  452 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  453 */                       _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  455 */                       _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  456 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  457 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/*  459 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  460 */                           if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                             return;
/*  462 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  463 */                           out.write("\n  </a></td>\n  ");
/*  464 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  465 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  469 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  470 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/*  473 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  474 */                       out.write(10);
/*  475 */                       out.write(32);
/*  476 */                       out.write(32);
/*      */                       
/*  478 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  479 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  480 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  482 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  483 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  484 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  486 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  488 */                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  489 */                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  490 */                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  491 */                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  492 */                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                             for (;;) {
/*  494 */                               out.write("\n   \t\t ");
/*      */                               
/*  496 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  497 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  498 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*  500 */                               _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  501 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  502 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/*  504 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  505 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                                     return;
/*  507 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  508 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  509 */                                   out.write("</a>\n   \t\t ");
/*  510 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  511 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  515 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  516 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/*  519 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  520 */                               out.write("\n   \t \t ");
/*      */                               
/*  522 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  523 */                               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  524 */                               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/*  525 */                               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  526 */                               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                 for (;;) {
/*  528 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  529 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/*  531 */                                   out.write("\" class=\"staticlinks\">");
/*  532 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  533 */                                   out.write("</a>\n   \t\t ");
/*  534 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  535 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  539 */                               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  540 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                               }
/*      */                               
/*  543 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  544 */                               out.write("\n   \t");
/*  545 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  546 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  550 */                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  551 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                           }
/*      */                           
/*  554 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  555 */                           out.write("\n\t</td> \n  ");
/*  556 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  557 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  561 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  562 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  565 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  566 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  567 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  568 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  569 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  570 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  571 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  572 */                       out.write("</a></td>\n\t");
/*      */                       
/*  574 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  575 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  576 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  578 */                       _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  579 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  580 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  582 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  583 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  584 */                           out.write("</a></td>\n\t");
/*  585 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  586 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  590 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  591 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  594 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  595 */                       out.write(10);
/*  596 */                       out.write(9);
/*      */                       
/*  598 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  599 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  600 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  602 */                       _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  603 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  604 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  606 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  607 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  608 */                           out.write("</a></td>\n\t");
/*  609 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  610 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  614 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  615 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  618 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  619 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  620 */                       out.print(getHelpLink(helpKey));
/*  621 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  622 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  623 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  624 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  625 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  627 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  628 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  629 */                       out.write("</a>");
/*  630 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  632 */                         out.write("&nbsp;");
/*  633 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  634 */                         out.write("\n    \t\t\t\t\t");
/*  635 */                       } else { out.println("&nbsp;"); }
/*  636 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  638 */                     out.write(10);
/*  639 */                     out.write(32);
/*  640 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  641 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  645 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  646 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  649 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  650 */                 out.write(" \n\n\t");
/*  651 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!PluginUtil.isPlugin()))
/*      */                 {
/*  653 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  655 */                 out.write("\n\n</table>\n");
/*  656 */                 out.write("\n\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n\n    <td valign=\"top\">\n\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n\n        ");
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  665 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/*  668 */                   out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  669 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  670 */                   out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\n\n\n  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                 }
/*  673 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/*  675 */                   String messagetosay = "";
/*  676 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/*  678 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  679 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/*  681 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/*  683 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/*  685 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/*  688 */                     out.write("\n                      <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:5px 5px; 5px; 5px;\">\n<tr>\n<td>\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr>\n<td valign=\"top\"  class=\"message-bg1-blue\"></td>\n<td><div id=\"message-tbrd-blue\"></div></td>\n<td valign=\"top\"  class=\"message-bg2-blue\"></td>\n</tr>\n<tr>\n<td  class=\"message-lbrd-blue\"> </td>\n<td class=\"message-td-bg\"><div class=\"centring\"><span> ");
/*  689 */                     out.print(messagetosay);
/*  690 */                     out.write("</span></div></td>\n<td class=\"message-rbrd-blue\"></td>\n</tr>\n<tr>\n<td  class=\"message-bg3-blue\"></td>\n<td><div id=\"message-bbrd-blue\"></div> </td>\n<td  class=\"message-bg4-blue\"></td>\n</tr>\n\n\n</table>\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n              <tr>\n              <td>&nbsp;\n              </td>\n              </tr>\n\n                  ");
/*      */                   }
/*      */                   
/*      */ 
/*  694 */                   out.write("\n\t\t\t<tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t\t<tr>\n\n              <td colspan=\"2\" class=\"msg-table-width\"><div class=\"centring\"><span>");
/*      */                   
/*  696 */                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  697 */                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/*  698 */                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  700 */                   _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN,USERS");
/*  701 */                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/*  702 */                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                     for (;;) {
/*  704 */                       out.write("\n\t\t\t<div>");
/*  705 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  706 */                       out.write("</div>\n\t\t\t");
/*  707 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/*  708 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  712 */                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/*  713 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                   }
/*      */                   
/*  716 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*  717 */                   out.write("\n\t\t\t");
/*      */                   
/*  719 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  720 */                   _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  721 */                   _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  723 */                   _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN,USERS");
/*  724 */                   int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  725 */                   if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                     for (;;) {
/*  727 */                       out.write("\n\t\t\t<div>");
/*  728 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  729 */                       out.write("</div>\n\t\t\t");
/*  730 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  731 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  735 */                   if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  736 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                   }
/*      */                   
/*  739 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  740 */                   out.write("</div></td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*  747 */                   out.write("\n        ");
/*      */                   
/*  749 */                   MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  750 */                   _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  751 */                   _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  753 */                   _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                   
/*  755 */                   _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  756 */                   int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  757 */                   if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  758 */                     String msg = null;
/*  759 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  760 */                       out = _jspx_page_context.pushBody();
/*  761 */                       _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  762 */                       _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                     }
/*  764 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/*  766 */                       out.write("\n        <tr>\n          <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t    \t\t\t<tr>\n\t    \t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                    <td class=\"msg-table-width\">");
/*  767 */                       if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                         return;
/*  769 */                       out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n\n\n            </td>\n            </tr>\n            </table>\n            </td>\n        </tr>\n        ");
/*  770 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  771 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*  772 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  775 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  776 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  779 */                   if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  780 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                   }
/*      */                   
/*  783 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  784 */                   out.write(32);
/*      */                   
/*  786 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  787 */                   _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  788 */                   _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  790 */                   _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  791 */                   int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  792 */                   if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                     for (;;) {
/*  794 */                       out.write("\n        <tr>\n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t    \t\t\t<tr>\n\t    \t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                    <td class=\"msg-table-width\">");
/*      */                       
/*  796 */                       MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  797 */                       _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  798 */                       _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                       
/*  800 */                       _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                       
/*  802 */                       _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  803 */                       int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  804 */                       if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  805 */                         String msg = null;
/*  806 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  807 */                           out = _jspx_page_context.pushBody();
/*  808 */                           _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  809 */                           _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                         }
/*  811 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/*  813 */                           out.write("\n                  ");
/*  814 */                           if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                             return;
/*  816 */                           out.write("\n                  ");
/*  817 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  818 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/*  819 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  822 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  823 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  826 */                       if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  827 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                       }
/*      */                       
/*  830 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  831 */                       out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n\n            </td>\n            </tr>\n            </table></td>\n        </tr>\n        ");
/*  832 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  833 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  837 */                   if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  838 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                   }
/*      */                   
/*  841 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  842 */                   out.write("\n        ");
/*      */                 }
/*  844 */                 out.write("\n        <tr>\n          <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/*  845 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  847 */                 out.write("</td>\n        </tr>        \n      </table></td>\n\t       ");
/*  848 */                 if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  850 */                 out.write("\n\n    </tr>\n </table>\n");
/*      */                 
/*  852 */                 Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                 
/*  854 */                 out.write("\n\t</div>\n  ");
/*  855 */                 if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  857 */                 out.write("\n    ");
/*  858 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  859 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  863 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  864 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  867 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  868 */             out.write(10);
/*  869 */             out.write(32);
/*  870 */             out.write(32);
/*      */             
/*  872 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  873 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  874 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f0);
/*  875 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  876 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  878 */                 out.write(10);
/*  879 */                 out.write(10);
/*  880 */                 if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/*  882 */                 out.write("\n<div id=\"userAreaContainerDiv\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n  ");
/*  883 */                 if (Constants.sqlManager) {
/*  884 */                   out.write("\n\t<td width=\"85%\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n    <td width=\"15%\" ><img src=\"/images/spacer.gif\" height=\"10\"></td>\n  ");
/*      */                 } else {
/*  886 */                   out.write("\n    <td width=\"16%\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n    <td width=\"69%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\n    <td width=\"15%\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n\t");
/*      */                 }
/*  888 */                 out.write("\n  </tr>\n\n  <tr>\n\t<td valign=\"top\" width=\"85%\">\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n\n        ");
/*      */                 
/*  890 */                 String addOnMsg = "";
/*  891 */                 FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*  892 */                 String rtype = getResourceType(resourceid);
/*  893 */                 if (rtype.equals("OfficeSharePointServer"))
/*      */                 {
/*      */ 
/*  896 */                   if ((!fed.isSharePoint()) || (
/*      */                   
/*      */ 
/*      */ 
/*  900 */                     (FreeEditionDetails.getAddOnEvalDaysProps() != null) && (FreeEditionDetails.getAddOnEvalDaysProps().getProperty("OfficeSharePoint") != null))) {
/*  901 */                     String nDays = FreeEditionDetails.getAddOnEvalDaysProps().getProperty("OfficeSharePoint");
/*  902 */                     addOnMsg = FormatUtil.getString("am.addon.remainingdays.msg", new String[] { "OfficeSharePointServer", nDays });
/*      */                   }
/*      */                 }
/*  905 */                 else if (rtype.equals("WebsphereMQ"))
/*      */                 {
/*  907 */                   if ((!fed.isMqSeries()) || (
/*      */                   
/*      */ 
/*  910 */                     (FreeEditionDetails.getAddOnEvalDaysProps() != null) && (FreeEditionDetails.getAddOnEvalDaysProps().getProperty("WebsphereMQ") != null))) {
/*  911 */                     String nDays = FreeEditionDetails.getAddOnEvalDaysProps().getProperty("WebsphereMQ");
/*  912 */                     addOnMsg = FormatUtil.getString("am.addon.remainingdays.msg", new String[] { "WebsphereMQ", nDays });
/*      */                   }
/*      */                 }
/*  915 */                 if (!addOnMsg.equals(""))
/*      */                 {
/*      */ 
/*  918 */                   out.write("\n                 <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  919 */                   out.print(addOnMsg);
/*  920 */                   out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n                 ");
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  928 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/*  931 */                   out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t  \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  932 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  933 */                   out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n        </tr>\n\t\t");
/*      */ 
/*      */                 }
/*  936 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/*  938 */                   String messagetosay = "";
/*  939 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/*  941 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  942 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/*  944 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/*  946 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/*  948 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/*  951 */                     out.write("\n                            <tr>\n    \t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n    \t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n    <tr>\n    <td width=\"99%\">\n\n\n\n\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n    \t<tr>\n    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n    \t</tr>\n    <tr>\n    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n    \t<td  width=\"98%\" class=\"msg-table-width\">\n    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n    \t\t\t<tr>\n    \t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                    <td class=\"msg-table-width\">");
/*  952 */                     out.print(messagetosay);
/*  953 */                     out.write("</td>\n    \t\t\t</tr>\n    \t\t</table>\n    \t</td>\n    \t<td class=\"msg-status-right-bg\"></td>\n    </tr>\n    <tr>\n    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n    </tr>\n    </table>\n    </td>\n    </tr>\n    </table>\n    \t\t  </td>\n    \t\t</tr>\n                    <tr>\n                    <td>&nbsp;\n                    </td>\n                    </tr>\n\n                        ");
/*      */                   }
/*      */                   
/*      */ 
/*  957 */                   out.write("\n    \t\t\t<tr>\n    \t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n    \t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n    <tr>\n    <td width=\"99%\">\n\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" border=\"0\" >\n    \t<tr>\n    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n    \t</tr>\n    <tr>\n    \t<td class=\"msg-status-left-bg\"></td>\n    \t<td width=\"98%\" class=\"msg-table-width\">\n    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n    \t\t\t<tr>\n    \t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                    <td colspan=\"2\" class=\"msg-table-width\">");
/*      */                   
/*  959 */                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  960 */                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/*  961 */                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  963 */                   _jspx_th_logic_005fpresent_005f7.setRole("ENTERPRISEADMIN,USERS");
/*  964 */                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/*  965 */                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                     for (;;) {
/*  967 */                       out.write("\n    \t\t\t  <div>");
/*  968 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  969 */                       out.write("</div>\n    \t\t\t  ");
/*  970 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/*  971 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  975 */                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/*  976 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                   }
/*      */                   
/*  979 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*  980 */                   out.write("\n    \t\t\t  ");
/*      */                   
/*  982 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  983 */                   _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  984 */                   _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/*  986 */                   _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN,USERS");
/*  987 */                   int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  988 */                   if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                     for (;;) {
/*  990 */                       out.write("\n    \t\t\t  <div>");
/*  991 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  992 */                       out.write("</div>\n    \t\t\t  ");
/*  993 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  994 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  998 */                   if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  999 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                   }
/*      */                   
/* 1002 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 1003 */                   out.write("</td>\n    \t\t\t</tr>\n    \t\t</table>\n    \t</td>\n    \t<td class=\"msg-status-right-bg\"></td>\n    </tr>\n    <tr>\n    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n    </tr>\n    </table>\n\n    </td>\n    </tr>\n    </table>\n    \t\t  </td>\n    \t\t</tr>\n    \t\t");
/*      */ 
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 1010 */                   out.write("\n        ");
/*      */                   
/* 1012 */                   MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1013 */                   _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1014 */                   _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1016 */                   _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                   
/* 1018 */                   _jspx_th_html_005fmessages_005f2.setMessage("false");
/* 1019 */                   int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1020 */                   if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1021 */                     String msg = null;
/* 1022 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1023 */                       out = _jspx_page_context.pushBody();
/* 1024 */                       _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1025 */                       _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                     }
/* 1027 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1029 */                       out.write("\n        ");
/* 1030 */                       if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/* 1032 */                       out.write("\n\n              ");
/* 1033 */                       if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/* 1035 */                       out.write("<br>\n\n        ");
/* 1036 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1037 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1038 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1041 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1042 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1045 */                   if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1046 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                   }
/*      */                   
/* 1049 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1050 */                   out.write("\n        ");
/* 1051 */                   if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/* 1053 */                   out.write("\n        ");
/*      */                   
/* 1055 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1056 */                   _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1057 */                   _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1059 */                   _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1060 */                   int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1061 */                   if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 1063 */                       out.write("\n        <tr>\n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*      */                       
/* 1065 */                       MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1066 */                       _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/* 1067 */                       _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                       
/* 1069 */                       _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                       
/* 1071 */                       _jspx_th_html_005fmessages_005f3.setMessage("true");
/* 1072 */                       int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/* 1073 */                       if (_jspx_eval_html_005fmessages_005f3 != 0) {
/* 1074 */                         String msg = null;
/* 1075 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1076 */                           out = _jspx_page_context.pushBody();
/* 1077 */                           _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/* 1078 */                           _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                         }
/* 1080 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/* 1082 */                           out.write("\n                  ");
/* 1083 */                           if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                             return;
/* 1085 */                           out.write("\n                  ");
/* 1086 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/* 1087 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/* 1088 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1091 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1092 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1095 */                       if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/* 1096 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                       }
/*      */                       
/* 1099 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/* 1100 */                       out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table></td>\n        </tr>\n        ");
/* 1101 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1102 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1106 */                   if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1107 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                   }
/*      */                   
/* 1110 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1111 */                   out.write("\n        ");
/*      */                 }
/* 1113 */                 out.write("\n\n        ");
/*      */                 
/* 1115 */                 PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1116 */                 _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 1117 */                 _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/* 1119 */                 _jspx_th_logic_005fpresent_005f8.setRole("OPERATOR");
/* 1120 */                 int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 1121 */                 if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                   for (;;) {
/* 1123 */                     out.write("\n\n\t     \t\t\t");
/*      */                     
/* 1125 */                     String mes = request.getParameter("message");
/* 1126 */                     if ((mes != null) && (mes.equals("false")))
/*      */                     {
/* 1128 */                       out.write("\n\t     \t\t\t<tr>\n\t\t\t\t\t          <td colspan=\"2\" class=\"tdindent\" valign=\"top\" height=\"46\"> <table style=\"margin: 5px;\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t<td width=\"98%\">\n\n\t\t\t\t\t<table class=\"msg-table-style\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t\t\t<td class=\"msg-status-tp-left-corn\" width=\"7\"></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-tp-right-corn\" width=\"9\"></td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t\t\t\t<td class=\"msg-table-width\" width=\"98%\">\n\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t\t\t\t                <td class=\"msg-table-width\">\n\t\t\t\t\t                 ");
/* 1129 */                       out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 1130 */                       out.write("\n\t\t\t\t\t                  </td>\n\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t</tbody></table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t\t\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t\t\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody></table>\n\n\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody></table></td>\n\t        </tr>\n\t     \t\t\t");
/*      */                     }
/* 1132 */                     out.write("\n\n     \t\t");
/* 1133 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 1134 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1138 */                 if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 1139 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                 }
/*      */                 
/* 1142 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 1143 */                 out.write("\n\n        <tr>\n          <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/* 1144 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1146 */                 out.write("</td>\n        </tr>\n        <tr>\n          <td height=\"20%\" colspan=\"2\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n      </table></td>\n      <td  valign=\"top\" class=\"leftcells\" width=\"15%\">");
/* 1147 */                 if (_jspx_meth_tiles_005finsert_005f5(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1149 */                 out.write("\n      </td>\n  </tr>  \n</table>\n</div><!-- userAreaContainerDiv ends -->\n");
/* 1150 */                 if (_jspx_meth_tiles_005finsert_005f6(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1152 */                 out.write(10);
/*      */                 
/* 1154 */                 Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                 
/* 1156 */                 out.write(10);
/* 1157 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1158 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1162 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1163 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/* 1166 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1167 */             out.write(10);
/* 1168 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1169 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1173 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1174 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/* 1177 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1178 */           out.write("\n<script type=\"text/javascript\">\n\ttry\n  {\n    form = document.forms[0];\n    if(form && form.action.indexOf(\"Search.do\")<0)  //NO I18N\n    {\n      $('body').prepend('<form id=\"mySearch\" action=\"/Search.do\" style=\"display:none;\"></form>');   //No I18N\n    }\n  }\n  catch(err){\n\n  }\n</script>\n");
/* 1179 */           out.write(10);
/* 1180 */           out.write(10);
/* 1181 */           if (Constants.isIt360) {
/* 1182 */             out.write("\n<script type=\"text/javascript\">\n\tvar iframe= parent.window.document.getElementById('_iframe_view');\n\tif(iframe != null)\n\t{\n\t\tvar iframeName = iframe.name;\n\t\tif(iframeName != null && iframeName == '_iframe_view')\n\t\t{\n\t\t\tvar frameDoc = iframe.contentDocument || iframe.contentWindow.document;\n\t\t\tvar e = frameDoc.getElementById(\"userAreaContainerDiv\");\n\t\t\tif(e!=null)\n\t\t\t{\n\t\t\t\te.id = \"userAreaContainerDiv_admin\";\n\t\t\t}\n\t\t\n\t\t\tvar footer = frameDoc.getElementById(\"footer-container\");\n\t\t\tif(footer!=null)\n\t\t\t{\n\t\t\t\tfooter.innerHTML=\"\";\n\t\t\t}\n\t\t}\n\t}\n</script>\n");
/*      */           }
/* 1184 */           out.write(10);
/* 1185 */           out.write(10);
/* 1186 */           out.write(10);
/* 1187 */           out.write(10);
/*      */           
/* 1189 */           if (PluginUtil.isPlugin())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1194 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 1195 */             out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/* 1196 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 1200 */           out.write("\n</body>\n</html>\n\n");
/*      */         }
/* 1202 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1203 */         out = _jspx_out;
/* 1204 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1205 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1206 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1209 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1215 */     PageContext pageContext = _jspx_page_context;
/* 1216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1218 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1219 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1220 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1222 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1224 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1225 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1226 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1227 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1228 */       return true;
/*      */     }
/* 1230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1236 */     PageContext pageContext = _jspx_page_context;
/* 1237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1239 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 1240 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 1241 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/* 1243 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 1244 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 1245 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 1246 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1247 */       return true;
/*      */     }
/* 1249 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1255 */     PageContext pageContext = _jspx_page_context;
/* 1256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1258 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1259 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1260 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 1262 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod && empty param.noreload && !empty customreloadperiod}");
/* 1263 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1264 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1266 */         out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1267 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1268 */           return true;
/* 1269 */         out.write(34);
/* 1270 */         out.write(62);
/* 1271 */         out.write(10);
/* 1272 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1273 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1277 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1278 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1279 */       return true;
/*      */     }
/* 1281 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1287 */     PageContext pageContext = _jspx_page_context;
/* 1288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1290 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1291 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1292 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1294 */     _jspx_th_c_005fout_005f1.setValue("${customreloadperiod}");
/* 1295 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1296 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1297 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1298 */       return true;
/*      */     }
/* 1300 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1306 */     PageContext pageContext = _jspx_page_context;
/* 1307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1309 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1310 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1311 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1313 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1315 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1316 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1318 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1319 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1321 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1322 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1323 */             return true;
/* 1324 */           out.write("\"\" class=\"staticlinks\">");
/* 1325 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1326 */             return true;
/* 1327 */           out.write("</a></td>\n");
/* 1328 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1329 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1333 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1334 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1337 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1338 */         out = _jspx_page_context.popBody(); }
/* 1339 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1341 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1342 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1349 */     PageContext pageContext = _jspx_page_context;
/* 1350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1352 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1353 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1354 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1356 */     _jspx_th_c_005fout_005f2.setValue("${tab.TABLINK}");
/* 1357 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1358 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1359 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1360 */       return true;
/*      */     }
/* 1362 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1368 */     PageContext pageContext = _jspx_page_context;
/* 1369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1371 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1372 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1373 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1375 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABNAME}");
/* 1376 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1377 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1378 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1379 */       return true;
/*      */     }
/* 1381 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1387 */     PageContext pageContext = _jspx_page_context;
/* 1388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1390 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1391 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1392 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1394 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1396 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1397 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1399 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1400 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1402 */           out.write(10);
/* 1403 */           out.write(10);
/* 1404 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1405 */             return true;
/* 1406 */           out.write(10);
/* 1407 */           out.write(10);
/* 1408 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1409 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1413 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1414 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1417 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1418 */         out = _jspx_page_context.popBody(); }
/* 1419 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1421 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1422 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1429 */     PageContext pageContext = _jspx_page_context;
/* 1430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1432 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1433 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1434 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1435 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1436 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1438 */         out.write(10);
/* 1439 */         out.write(10);
/* 1440 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1441 */           return true;
/* 1442 */         out.write(10);
/* 1443 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1444 */           return true;
/* 1445 */         out.write(10);
/* 1446 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1447 */           return true;
/* 1448 */         out.write("\n\n\n\n\n");
/* 1449 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1450 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1454 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1455 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1456 */       return true;
/*      */     }
/* 1458 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1459 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1464 */     PageContext pageContext = _jspx_page_context;
/* 1465 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1467 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1468 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1469 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1471 */     _jspx_th_c_005fwhen_005f1.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1472 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1473 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1475 */         out.write(10);
/* 1476 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1477 */           return true;
/* 1478 */         out.write(10);
/* 1479 */         out.write(32);
/* 1480 */         out.write(32);
/* 1481 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1482 */           return true;
/* 1483 */         out.write(10);
/* 1484 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1485 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1489 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1490 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1491 */       return true;
/*      */     }
/* 1493 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1499 */     PageContext pageContext = _jspx_page_context;
/* 1500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1502 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1503 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1504 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1506 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1507 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1508 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1510 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1511 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1512 */           return true;
/* 1513 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1514 */           return true;
/* 1515 */         out.write("\n  </a></td>\n  ");
/* 1516 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1517 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1521 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1522 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1523 */       return true;
/*      */     }
/* 1525 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1531 */     PageContext pageContext = _jspx_page_context;
/* 1532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1534 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1535 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1536 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1537 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1538 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1540 */         out.write("\n  \t\t");
/* 1541 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1542 */           return true;
/* 1543 */         out.write("\n  \t\t");
/* 1544 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1545 */           return true;
/* 1546 */         out.write("\n  \t\t");
/* 1547 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1548 */           return true;
/* 1549 */         out.write("\n  \t");
/* 1550 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1555 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1556 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1557 */       return true;
/*      */     }
/* 1559 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1565 */     PageContext pageContext = _jspx_page_context;
/* 1566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1568 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1569 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1570 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1572 */     _jspx_th_c_005fwhen_005f2.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1573 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1574 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1576 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1577 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1578 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1582 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1583 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1584 */       return true;
/*      */     }
/* 1586 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1592 */     PageContext pageContext = _jspx_page_context;
/* 1593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1595 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1596 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1597 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1599 */     _jspx_th_c_005fwhen_005f3.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1600 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1601 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1603 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1604 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1605 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1609 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1610 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1611 */       return true;
/*      */     }
/* 1613 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1619 */     PageContext pageContext = _jspx_page_context;
/* 1620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1622 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1623 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1624 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1625 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1626 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1628 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1629 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1630 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1634 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1635 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1636 */       return true;
/*      */     }
/* 1638 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1644 */     PageContext pageContext = _jspx_page_context;
/* 1645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1647 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1648 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1649 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 1651 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1652 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1653 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1654 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1655 */       return true;
/*      */     }
/* 1657 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1663 */     PageContext pageContext = _jspx_page_context;
/* 1664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1666 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1667 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1668 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1670 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1671 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1672 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1674 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1675 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1676 */           return true;
/* 1677 */         out.write("\n\t</td> \n  ");
/* 1678 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1683 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1684 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1685 */       return true;
/*      */     }
/* 1687 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1693 */     PageContext pageContext = _jspx_page_context;
/* 1694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1696 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1697 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1698 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 1699 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1700 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1702 */         out.write("\n   \t\t ");
/* 1703 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1704 */           return true;
/* 1705 */         out.write("\n   \t \t ");
/* 1706 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1707 */           return true;
/* 1708 */         out.write("\n   \t");
/* 1709 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1710 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1714 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1715 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1716 */       return true;
/*      */     }
/* 1718 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1724 */     PageContext pageContext = _jspx_page_context;
/* 1725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1727 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1728 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1729 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1731 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1732 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1733 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1735 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1736 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1737 */           return true;
/* 1738 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1739 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1740 */           return true;
/* 1741 */         out.write("</a>\n   \t\t ");
/* 1742 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1743 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1747 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1748 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1749 */       return true;
/*      */     }
/* 1751 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1757 */     PageContext pageContext = _jspx_page_context;
/* 1758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1760 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1761 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1762 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1764 */     _jspx_th_c_005fout_005f5.setValue("${globalconfig['defaultmonitorsview']}");
/* 1765 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1766 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1767 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1768 */       return true;
/*      */     }
/* 1770 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1776 */     PageContext pageContext = _jspx_page_context;
/* 1777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1779 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1780 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1781 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1783 */     _jspx_th_c_005fout_005f6.setValue("${tab.TABNAME}");
/* 1784 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1785 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1786 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1787 */       return true;
/*      */     }
/* 1789 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1795 */     PageContext pageContext = _jspx_page_context;
/* 1796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1798 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1799 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1800 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1801 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1802 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1804 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 1805 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1806 */           return true;
/* 1807 */         out.write("\" class=\"staticlinks\">");
/* 1808 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1809 */           return true;
/* 1810 */         out.write("</a>\n   \t\t ");
/* 1811 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1812 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1816 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1817 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1818 */       return true;
/*      */     }
/* 1820 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1826 */     PageContext pageContext = _jspx_page_context;
/* 1827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1829 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1830 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1831 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1833 */     _jspx_th_c_005fout_005f7.setValue("${globalconfig['defaultmonitorsview']}");
/* 1834 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1835 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1836 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1837 */       return true;
/*      */     }
/* 1839 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1845 */     PageContext pageContext = _jspx_page_context;
/* 1846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1848 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1849 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1850 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1852 */     _jspx_th_c_005fout_005f8.setValue("${tab.TABNAME}");
/* 1853 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1854 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1855 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1856 */       return true;
/*      */     }
/* 1858 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1864 */     PageContext pageContext = _jspx_page_context;
/* 1865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1867 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1868 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1869 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1871 */     _jspx_th_c_005fwhen_005f5.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 1872 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1873 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1875 */         out.write(10);
/* 1876 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1877 */           return true;
/* 1878 */         out.write(10);
/* 1879 */         out.write(9);
/* 1880 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1881 */           return true;
/* 1882 */         out.write(10);
/* 1883 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1888 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1889 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1890 */       return true;
/*      */     }
/* 1892 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1898 */     PageContext pageContext = _jspx_page_context;
/* 1899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1901 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1902 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1903 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1905 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1906 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1907 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1909 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 1910 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1911 */           return true;
/* 1912 */         out.write("</a></td>\n\t");
/* 1913 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1914 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1918 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1919 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1920 */       return true;
/*      */     }
/* 1922 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1928 */     PageContext pageContext = _jspx_page_context;
/* 1929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1931 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1932 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1933 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1935 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 1936 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1937 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1938 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1939 */       return true;
/*      */     }
/* 1941 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1947 */     PageContext pageContext = _jspx_page_context;
/* 1948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1950 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1951 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1952 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1954 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 1955 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1956 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1958 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 1959 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1960 */           return true;
/* 1961 */         out.write("</a></td>\n\t");
/* 1962 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1963 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1967 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1968 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1969 */       return true;
/*      */     }
/* 1971 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1977 */     PageContext pageContext = _jspx_page_context;
/* 1978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1980 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1981 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1982 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 1984 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 1985 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1986 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1987 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1988 */       return true;
/*      */     }
/* 1990 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1996 */     PageContext pageContext = _jspx_page_context;
/* 1997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1999 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2000 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2001 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2002 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2003 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2005 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 2006 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2007 */           return true;
/* 2008 */         out.write("\" class=\"staticlinks\">\t");
/* 2009 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2010 */           return true;
/* 2011 */         out.write("</a></td>\n");
/* 2012 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2013 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2017 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2018 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2019 */       return true;
/*      */     }
/* 2021 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2027 */     PageContext pageContext = _jspx_page_context;
/* 2028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2030 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2031 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2032 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2034 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABLINK}");
/* 2035 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2036 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2037 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2038 */       return true;
/*      */     }
/* 2040 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2046 */     PageContext pageContext = _jspx_page_context;
/* 2047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2049 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2050 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2051 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2053 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABNAME}");
/* 2054 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2055 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2056 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2057 */       return true;
/*      */     }
/* 2059 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2060 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2065 */     PageContext pageContext = _jspx_page_context;
/* 2066 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2068 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2069 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2070 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 2071 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2072 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2074 */         out.write("\n  \t\t");
/* 2075 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2076 */           return true;
/* 2077 */         out.write("\n  \t\t");
/* 2078 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2079 */           return true;
/* 2080 */         out.write("\n  \t\t");
/* 2081 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2082 */           return true;
/* 2083 */         out.write("\n  \t");
/* 2084 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2089 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2090 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2091 */       return true;
/*      */     }
/* 2093 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2099 */     PageContext pageContext = _jspx_page_context;
/* 2100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2102 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2103 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2104 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2106 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2107 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2108 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 2110 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 2111 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2112 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2116 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2117 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2118 */       return true;
/*      */     }
/* 2120 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2126 */     PageContext pageContext = _jspx_page_context;
/* 2127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2129 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2130 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2131 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2133 */     _jspx_th_c_005fwhen_005f7.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2134 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2135 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 2137 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2138 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2139 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2143 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2144 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2145 */       return true;
/*      */     }
/* 2147 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2153 */     PageContext pageContext = _jspx_page_context;
/* 2154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2156 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2157 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2158 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2159 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2160 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2162 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 2163 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2164 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2168 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2169 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2170 */       return true;
/*      */     }
/* 2172 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2178 */     PageContext pageContext = _jspx_page_context;
/* 2179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2181 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2182 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2183 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 2185 */     _jspx_th_c_005fout_005f13.setValue("${globalconfig['defaultmonitorsview']}");
/* 2186 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2187 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2188 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2189 */       return true;
/*      */     }
/* 2191 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2197 */     PageContext pageContext = _jspx_page_context;
/* 2198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2200 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2201 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2202 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 2204 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2205 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2206 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2208 */       return true;
/*      */     }
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2216 */     PageContext pageContext = _jspx_page_context;
/* 2217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2219 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2220 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2221 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2223 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2225 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2226 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2227 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2228 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2229 */       return true;
/*      */     }
/* 2231 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2237 */     PageContext pageContext = _jspx_page_context;
/* 2238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2240 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2241 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2242 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2244 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2246 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2247 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2248 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2249 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2250 */       return true;
/*      */     }
/* 2252 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2258 */     PageContext pageContext = _jspx_page_context;
/* 2259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2261 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2262 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2263 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2265 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 2266 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2267 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2268 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2269 */       return true;
/*      */     }
/* 2271 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2277 */     PageContext pageContext = _jspx_page_context;
/* 2278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2280 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2281 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2282 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2284 */     _jspx_th_c_005fif_005f3.setTest("${selectedscheme == 'slim' || isPlugin == 'true'}");
/* 2285 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2286 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2288 */         out.write(" <td  valign=\"top\" class=\"leftcells\" width=\"15%\">");
/* 2289 */         if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 2290 */           return true;
/* 2291 */         out.write("\n      </td>");
/* 2292 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2293 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2297 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2298 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2299 */       return true;
/*      */     }
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2307 */     PageContext pageContext = _jspx_page_context;
/* 2308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2310 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2311 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2312 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2314 */     _jspx_th_tiles_005finsert_005f1.setAttribute("ServerLeftArea");
/* 2315 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2316 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2317 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2318 */       return true;
/*      */     }
/* 2320 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2326 */     PageContext pageContext = _jspx_page_context;
/* 2327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2329 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2330 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2331 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2333 */     _jspx_th_c_005fif_005f4.setTest("${selectedscheme == 'slim'}");
/* 2334 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2335 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2337 */         out.write(32);
/* 2338 */         if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2339 */           return true;
/* 2340 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2341 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2345 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2346 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2347 */       return true;
/*      */     }
/* 2349 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2355 */     PageContext pageContext = _jspx_page_context;
/* 2356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2358 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2359 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2360 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2362 */     _jspx_th_tiles_005finsert_005f2.setAttribute("footer");
/* 2363 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2364 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2365 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2366 */       return true;
/*      */     }
/* 2368 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2374 */     PageContext pageContext = _jspx_page_context;
/* 2375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2377 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2378 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2379 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2381 */     _jspx_th_tiles_005finsert_005f3.setAttribute("Header");
/* 2382 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2383 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2384 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2385 */       return true;
/*      */     }
/* 2387 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2393 */     PageContext pageContext = _jspx_page_context;
/* 2394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2396 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2397 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2398 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2400 */     _jspx_th_c_005fif_005f5.setTest("${empty firstrow}");
/* 2401 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2402 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2404 */         out.write("\n\n           <tr>\n\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\n\t          <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n                <tr>\n                <td width=\"99%\">\n\n\n\n               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t       \t<tr>\n\t       \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t       \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t       \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t       \t</tr>\n\t       <tr>\n\t       \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t       \t<td width=\"98%\" class=\"msg-table-width\">\n\t       \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t       \t\t\t<tr>\n\t       \t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                       <td class=\"msg-table-width\">");
/* 2405 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 2406 */           return true;
/* 2407 */         out.write("\n        ");
/* 2408 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2413 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2414 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2415 */       return true;
/*      */     }
/* 2417 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2423 */     PageContext pageContext = _jspx_page_context;
/* 2424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2426 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2427 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2428 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2430 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 2432 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 2433 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2434 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2435 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2436 */       return true;
/*      */     }
/* 2438 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2444 */     PageContext pageContext = _jspx_page_context;
/* 2445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2447 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2448 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2449 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2451 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2453 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2454 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2455 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2456 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2457 */       return true;
/*      */     }
/* 2459 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2465 */     PageContext pageContext = _jspx_page_context;
/* 2466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2468 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2469 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2470 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2472 */     _jspx_th_c_005fif_005f6.setTest("${!empty firstrow}");
/* 2473 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2474 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 2476 */         out.write("</td>\n\t       \t\t\t</tr>\n\t       \t\t</table>\n\t       \t</td>\n\t       \t<td class=\"msg-status-right-bg\"></td>\n\t       </tr>\n\t       <tr>\n\t       <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t       <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t       <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t       </tr>\n</table>\n\n\n\n                </td>\n                </tr>\n                </table>\n\n</td>\n        </tr>\n\t");
/* 2477 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2478 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2482 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2483 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2484 */       return true;
/*      */     }
/* 2486 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2487 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2492 */     PageContext pageContext = _jspx_page_context;
/* 2493 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2495 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2496 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2497 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2499 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2501 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2502 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2503 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2504 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2505 */       return true;
/*      */     }
/* 2507 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2513 */     PageContext pageContext = _jspx_page_context;
/* 2514 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2516 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2517 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2518 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2520 */     _jspx_th_tiles_005finsert_005f4.setAttribute("UserArea");
/* 2521 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2522 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2523 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2524 */       return true;
/*      */     }
/* 2526 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f5(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2532 */     PageContext pageContext = _jspx_page_context;
/* 2533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2535 */     InsertTag _jspx_th_tiles_005finsert_005f5 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2536 */     _jspx_th_tiles_005finsert_005f5.setPageContext(_jspx_page_context);
/* 2537 */     _jspx_th_tiles_005finsert_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2539 */     _jspx_th_tiles_005finsert_005f5.setAttribute("ServerLeftArea");
/* 2540 */     int _jspx_eval_tiles_005finsert_005f5 = _jspx_th_tiles_005finsert_005f5.doStartTag();
/* 2541 */     if (_jspx_th_tiles_005finsert_005f5.doEndTag() == 5) {
/* 2542 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2543 */       return true;
/*      */     }
/* 2545 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f6(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2551 */     PageContext pageContext = _jspx_page_context;
/* 2552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2554 */     InsertTag _jspx_th_tiles_005finsert_005f6 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2555 */     _jspx_th_tiles_005finsert_005f6.setPageContext(_jspx_page_context);
/* 2556 */     _jspx_th_tiles_005finsert_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2558 */     _jspx_th_tiles_005finsert_005f6.setAttribute("footer");
/* 2559 */     int _jspx_eval_tiles_005finsert_005f6 = _jspx_th_tiles_005finsert_005f6.doStartTag();
/* 2560 */     if (_jspx_th_tiles_005finsert_005f6.doEndTag() == 5) {
/* 2561 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2562 */       return true;
/*      */     }
/* 2564 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2565 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ServerLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */