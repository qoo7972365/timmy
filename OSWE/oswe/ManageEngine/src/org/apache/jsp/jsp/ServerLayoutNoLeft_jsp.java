/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
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
/*      */ public final class ServerLayoutNoLeft_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   private String getResourceType(String resourceid)
/*      */   {
/*   45 */     String type = "";
/*      */     try
/*      */     {
/*   48 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   49 */       String q1 = "select TYPE from AM_ManagedObject where resourceid=" + resourceid;
/*   50 */       ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/*   51 */       if (rs.next())
/*      */       {
/*   53 */         type = rs.getString(1);
/*      */       }
/*   55 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*   59 */       exc.printStackTrace();
/*      */     }
/*   61 */     return type;
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key)
/*      */   {
/*   66 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   67 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   70 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   71 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   72 */       set = AMConnectionPool.executeQueryStmt(query);
/*   73 */       if (set.next())
/*      */       {
/*   75 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   78 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   84 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*  101 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   94 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  103 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  109 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(6);
/*  110 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*  111 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*  112 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*  113 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*  114 */     _jspx_dependants.put("/jsp/includes/IT360Includes.jspf", Long.valueOf(1473429417000L));
/*  115 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
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
/*  139 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  143 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  144 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  145 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  146 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  147 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  148 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  150 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  151 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  152 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  153 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  154 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  155 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  156 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  157 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  158 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  159 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  160 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  164 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  165 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  166 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  167 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  168 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  169 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  170 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  171 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  172 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  173 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  174 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  175 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  176 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  177 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  178 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  179 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  186 */     HttpSession session = null;
/*      */     
/*      */ 
/*  189 */     JspWriter out = null;
/*  190 */     Object page = this;
/*  191 */     JspWriter _jspx_out = null;
/*  192 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  196 */       response.setContentType("text/html;charset=UTF-8");
/*  197 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  199 */       _jspx_page_context = pageContext;
/*  200 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  201 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  202 */       session = pageContext.getSession();
/*  203 */       out = pageContext.getOut();
/*  204 */       _jspx_out = out;
/*      */       
/*  206 */       out.write("<!DOCTYPE html>\n");
/*  207 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  208 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  210 */       String rtype = request.getParameter("type");
/*  211 */       boolean isConfMonitor = ConfMonitorConfiguration.getInstance().isConfMonitor(rtype);
/*      */       
/*  213 */       String resourceid = request.getParameter("resourceid");
/*  214 */       String searchQuery = request.getParameter("query");
/*  215 */       String uri = (String)request.getAttribute("uri");
/*  216 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  217 */         searchQuery = "";
/*      */       }
/*      */       
/*  220 */       out.write(10);
/*      */       
/*  222 */       SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  223 */       _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  224 */       _jspx_th_c_005fset_005f0.setParent(null);
/*      */       
/*  226 */       _jspx_th_c_005fset_005f0.setVar("isConfMonitor");
/*  227 */       int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  228 */       if (_jspx_eval_c_005fset_005f0 != 0) {
/*  229 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/*  230 */           out = _jspx_page_context.pushBody();
/*  231 */           _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  232 */           _jspx_th_c_005fset_005f0.doInitBody();
/*      */         }
/*      */         for (;;) {
/*  235 */           out.print(isConfMonitor);
/*  236 */           out.write(32);
/*  237 */           int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  238 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  241 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/*  242 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  245 */       if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  246 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*      */       }
/*      */       else {
/*  249 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  250 */         out.write(10);
/*  251 */         Properties applications = null;
/*  252 */         synchronized (application) {
/*  253 */           applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  254 */           if (applications == null) {
/*  255 */             applications = new Properties();
/*  256 */             _jspx_page_context.setAttribute("applications", applications, 4);
/*      */           }
/*      */         }
/*  259 */         out.write("\n<html>\n<head>\n");
/*      */         
/*  261 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  262 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  263 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/*  265 */         _jspx_th_c_005fif_005f0.setTest("${isConfMonitor == 'false'}");
/*  266 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  267 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/*  269 */             out.write(10);
/*  270 */             out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  271 */             out.write(10);
/*  272 */             out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  273 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */               return;
/*  275 */             out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  276 */             out.write(10);
/*  277 */             out.write(10);
/*  278 */             out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  279 */             out.print(request.getContextPath());
/*  280 */             out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  281 */             out.print(request.getContextPath());
/*  282 */             out.write("'); //No I18N\n</script>\n");
/*  283 */             if (Constants.isIt360) {
/*  284 */               out.write("<script src='");
/*  285 */               out.print(request.getContextPath());
/*  286 */               out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */             }
/*  288 */             out.write(10);
/*  289 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  290 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  294 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  295 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */         }
/*      */         else {
/*  298 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  299 */           out.write(10);
/*      */           
/*  301 */           if (Constants.isIt360)
/*      */           {
/*  303 */             out.write("\n<title>");
/*  304 */             out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  305 */             out.write("</title>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  310 */             out.write("\n<title>");
/*  311 */             out.print(com.adventnet.appmanager.util.EnterpriseUtil.getTitle());
/*  312 */             out.write(32);
/*  313 */             out.write(45);
/*  314 */             out.write(32);
/*  315 */             if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */               return;
/*  317 */             out.write("</title>\n");
/*      */           }
/*  319 */           out.write("\n\n\n  \t ");
/*  320 */           if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */             return;
/*  322 */           out.write("\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<script>\n\nfunction logout()\n{\n\tlocation.href=\"/Logout.do\";\n}\n\n function refer(str)\n {\n \tlocation.href=str\n }\n\nfunction fnOpenNewWindow(url)\n{\n\tfnOpenNewWindowWithHeightWidth(url,'720','460');\n}\n\nfunction fnOpenNewWindowWithHeightWidth(url,wwidth,hheight)\n{\n\n\tvar d=new Date();\n\tvar window2=window.open(url+'&sid='+d,'secondWindow','resizable=yes,scrollbars=yes,width='+wwidth+',height='+hheight);\n\twindow2.focus();\n}\n\nfunction fnOpenWindow(url)\n{\n  var win=window.open(url,'','resizable=no,scrollbars=yes,width=760,height=420');\n  win.focus();\n}\nfunction showAlarms(resourceid)\n{\n\tvar d=new Date();\n\tMM_openBrWindow('/RecentAlarms.do?method=getAlarmsForResource&resourceid='+resourceid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');//No I18N\n\n}\n\nfunction doInitStuffOnBodyLoad() {\n    setFocusProperTextField();\n    if (window.onload)\n    {\n\tmyOnLoad();// any JSP can implement the\tmethod called myOnLoad() and it will get called dynamically :-)\n");
/*  323 */           out.write("    }\n\n\n}\n\nfunction setFocusProperTextField() {\n\n\n    var pos = document.forms.length;\n    if(pos > 0) {\n\n          if(document.forms.length >=2) {\n              pos = 1; // assuming 0 has search, hence 2rd might be the relevant one we need.\n          }\n          else\n          {\n          return\n          }\n\n\n            for(i=0;i<document.forms[pos].elements.length;i++) {\n\n                if(document.forms[pos].elements[i].type =='text') {\n\t\t\ttry\n\t\t\t{\n\t\t\t\tdocument.forms[pos].elements[i].focus();\n\t\t\t\tbreak;\n\t\t\t}\n\t\t\tcatch (e) {}\n\n                }\n            }\n    \t}\n}\n\n </script>\n</head>\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" style=\"overflow: scroll;\" onLoad=\"doInitStuffOnBodyLoad();\">\n");
/*      */           
/*  325 */           String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  326 */           isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  327 */           request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */           
/*  329 */           out.write(10);
/*      */           
/*  331 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  332 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  333 */           _jspx_th_c_005fif_005f3.setParent(null);
/*      */           
/*  335 */           _jspx_th_c_005fif_005f3.setTest("${selectedscheme == 'slim'}");
/*  336 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  337 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */             for (;;) {
/*  339 */               out.write(10);
/*      */               
/*  341 */               request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */               
/*  343 */               out.write(10);
/*  344 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  345 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  349 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  350 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */           }
/*      */           else {
/*  353 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  354 */             out.write(10);
/*  355 */             out.write(10);
/*  356 */             out.write(10);
/*  357 */             out.write(10);
/*  358 */             out.write(10);
/*      */             
/*  360 */             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  361 */             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  362 */             _jspx_th_c_005fchoose_005f1.setParent(null);
/*  363 */             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  364 */             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */               for (;;) {
/*  366 */                 out.write("\n    ");
/*      */                 
/*  368 */                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  369 */                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  370 */                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                 
/*  372 */                 _jspx_th_c_005fwhen_005f1.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  373 */                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  374 */                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                   for (;;) {
/*  376 */                     out.write("\n\t<div id=\"userAreaContainerDiv\">\n    <div id=\"dhtmltooltip\"></div>\n   <div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n\t");
/*      */                     
/*  378 */                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  379 */                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  380 */                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                     
/*  382 */                     _jspx_th_c_005fif_005f4.setTest("${selectedscheme == 'slim'}");
/*  383 */                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  384 */                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                       for (;;) {
/*  386 */                         out.write("\n              ");
/*  387 */                         out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  388 */                         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  389 */                         out.write(10);
/*  390 */                         out.write(10);
/*  391 */                         out.write(10);
/*  392 */                         out.write(32);
/*      */                         
/*  394 */                         String helpKey = (String)request.getAttribute("HelpKey");
/*  395 */                         if (helpKey == null)
/*      */                         {
/*  397 */                           String tileName = request.getParameter("TileName");
/*  398 */                           if (tileName != null)
/*      */                           {
/*  400 */                             if (tileName.equals(".ThresholdConf"))
/*      */                             {
/*  402 */                               helpKey = "New Threshold Profile";
/*      */                             }
/*  404 */                             else if (tileName.equals(".EmailActions"))
/*      */                             {
/*  406 */                               helpKey = "Send E-mail";
/*      */                             }
/*  408 */                             else if (tileName.equals(".SMSActions"))
/*      */                             {
/*  410 */                               helpKey = "Send SMS";
/*      */                             }
/*  412 */                             else if (tileName.equals(".ExecProg"))
/*      */                             {
/*  414 */                               helpKey = "Execute Program";
/*      */                             }
/*  416 */                             else if (tileName.equals(".SendTrap"))
/*      */                             {
/*  418 */                               helpKey = "Send Trap";
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                         
/*  423 */                         out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  424 */                         out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                         
/*  426 */                         Enumeration en = request.getParameterNames();
/*  427 */                         out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  428 */                         while (en.hasMoreElements()) {
/*  429 */                           String reqKey = (String)en.nextElement();
/*  430 */                           String reqValue = request.getParameter(reqKey);
/*  431 */                           if (!reqKey.equals("message"))
/*      */                           {
/*      */ 
/*      */ 
/*  435 */                             if (reqKey.equals("depDeviceId"))
/*      */                             {
/*  437 */                               out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                             }
/*  439 */                             else if (reqKey.equals("selectedMonitors"))
/*      */                             {
/*  441 */                               out.print("&" + reqKey + "=" + java.net.URLEncoder.encode(reqValue));
/*      */                             }
/*      */                             else
/*      */                             {
/*  445 */                               out.print("&" + reqKey + "=" + reqValue);
/*      */                             }
/*      */                           }
/*      */                         }
/*  449 */                         out.println("\";");
/*      */                         
/*  451 */                         out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                         
/*  453 */                         IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  454 */                         _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  455 */                         _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                         
/*  457 */                         _jspx_th_c_005fif_005f5.setTest("${selectedscheme == 'slim'}");
/*  458 */                         int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  459 */                         if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                           for (;;) {
/*  461 */                             out.write(10);
/*  462 */                             out.write(10);
/*      */                             
/*  464 */                             if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                             {
/*  466 */                               request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                               
/*  468 */                               out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  469 */                               out.print(OEMUtil.getOEMString("am.header.logo"));
/*  470 */                               out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  471 */                               if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                 return;
/*  473 */                               out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  474 */                               out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  475 */                               out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  476 */                               out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  477 */                               out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  478 */                               out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  479 */                               out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  480 */                               out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  481 */                               out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  482 */                               out.print(getHelpLink(helpKey));
/*  483 */                               out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  484 */                               if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  485 */                                 out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  486 */                                 out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  487 */                                 out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                               }
/*  489 */                               out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  490 */                               out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  491 */                               out.write("</a>");
/*  492 */                               if (request.getRemoteUser() != null)
/*  493 */                                 out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  494 */                                 out.println("&nbsp;");
/*  495 */                               out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                             }
/*  497 */                             out.write(32);
/*  498 */                             out.write(32);
/*  499 */                             out.write(10);
/*      */                             
/*  501 */                             if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                             {
/*  503 */                               request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                               
/*  505 */                               out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  506 */                               out.write(10);
/*  507 */                               if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                 return;
/*  509 */                               out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  510 */                               out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  511 */                               out.write("</a></td>\n  ");
/*      */                               
/*  513 */                               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  514 */                               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  515 */                               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f5);
/*      */                               
/*  517 */                               _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  518 */                               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  519 */                               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                 for (;;) {
/*  521 */                                   out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  522 */                                   if (_jspx_meth_c_005fchoose_005f5(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                     return;
/*  524 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  525 */                                   out.write("\n  </a></td>\n  ");
/*  526 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  527 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  531 */                               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  532 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                               }
/*      */                               
/*  535 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  536 */                               out.write(10);
/*  537 */                               out.write(32);
/*  538 */                               out.write(32);
/*      */                               
/*  540 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  541 */                               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  542 */                               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f5);
/*      */                               
/*  544 */                               _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/*  545 */                               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  546 */                               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                 for (;;) {
/*  548 */                                   out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                                   
/*  550 */                                   ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  551 */                                   _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  552 */                                   _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*  553 */                                   int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  554 */                                   if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                     for (;;) {
/*  556 */                                       out.write("\n   \t\t ");
/*      */                                       
/*  558 */                                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  559 */                                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/*  560 */                                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                       
/*  562 */                                       _jspx_th_c_005fwhen_005f9.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  563 */                                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/*  564 */                                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                         for (;;) {
/*  566 */                                           out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  567 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f9, _jspx_page_context))
/*      */                                             return;
/*  569 */                                           out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  570 */                                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  571 */                                           out.write("</a>\n   \t\t ");
/*  572 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/*  573 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/*  577 */                                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/*  578 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                       }
/*      */                                       
/*  581 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/*  582 */                                       out.write("\n   \t \t ");
/*      */                                       
/*  584 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  585 */                                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  586 */                                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/*  587 */                                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  588 */                                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                         for (;;) {
/*  590 */                                           out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  591 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                                             return;
/*  593 */                                           out.write("\" class=\"staticlinks\">");
/*  594 */                                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  595 */                                           out.write("</a>\n   \t\t ");
/*  596 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  597 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/*  601 */                                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  602 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                       }
/*      */                                       
/*  605 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  606 */                                       out.write("\n   \t");
/*  607 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  608 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/*  612 */                                   if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  613 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                                   }
/*      */                                   
/*  616 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  617 */                                   out.write("\n\t</td> \n  ");
/*  618 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  619 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  623 */                               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  624 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                               }
/*      */                               
/*  627 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  628 */                               out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  629 */                               out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  630 */                               out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  631 */                               out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  632 */                               out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  633 */                               out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  634 */                               out.write("</a></td>\n\t");
/*      */                               
/*  636 */                               PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  637 */                               _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  638 */                               _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f5);
/*      */                               
/*  640 */                               _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/*  641 */                               int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  642 */                               if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                 for (;;) {
/*  644 */                                   out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  645 */                                   out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  646 */                                   out.write("</a></td>\n\t");
/*  647 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  648 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  652 */                               if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  653 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                               }
/*      */                               
/*  656 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  657 */                               out.write(10);
/*  658 */                               out.write(9);
/*      */                               
/*  660 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  661 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  662 */                               _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f5);
/*      */                               
/*  664 */                               _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/*  665 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  666 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/*  668 */                                   out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  669 */                                   out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  670 */                                   out.write("</a></td>\n\t");
/*  671 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  672 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  676 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  677 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                               }
/*      */                               
/*  680 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  681 */                               out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  682 */                               out.print(getHelpLink(helpKey));
/*  683 */                               out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  684 */                               if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  685 */                                 out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  686 */                                 out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  687 */                                 out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                               }
/*  689 */                               out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  690 */                               out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  691 */                               out.write("</a>");
/*  692 */                               if (request.getRemoteUser() != null)
/*      */                               {
/*  694 */                                 out.write("&nbsp;");
/*  695 */                                 out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  696 */                                 out.write("\n    \t\t\t\t\t");
/*  697 */                               } else { out.println("&nbsp;"); }
/*  698 */                               out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                             }
/*  700 */                             out.write(10);
/*  701 */                             out.write(32);
/*  702 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  703 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  707 */                         if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  708 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                         }
/*      */                         
/*  711 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  712 */                         out.write(" \n\n\t");
/*  713 */                         if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!PluginUtil.isPlugin()))
/*      */                         {
/*  715 */                           out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                         }
/*  717 */                         out.write("\n\n</table>\n");
/*  718 */                         out.write(10);
/*  719 */                         out.write(9);
/*  720 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  721 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  725 */                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  726 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                     }
/*      */                     
/*  729 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  730 */                     out.write("\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n\n    <td valign=\"top\">\n\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     \t\t\t\t");
/*      */                     
/*  732 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  733 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  734 */                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                     
/*  736 */                     _jspx_th_c_005fif_005f6.setTest("${(empty param.fromTab)}");
/*  737 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  738 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/*  740 */                         out.write("\n        ");
/*      */                         
/*      */ 
/*      */ 
/*  744 */                         if (Constants.isIt360)
/*      */                         {
/*  746 */                           String isReqFromCentral = request.getParameter("fromCentral");
/*  747 */                           request.setAttribute("isReqFromCentral", isReqFromCentral);
/*      */                         }
/*      */                         
/*  750 */                         if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                         {
/*      */ 
/*  753 */                           out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/*  754 */                           out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  755 */                           out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n\n\n\n\n  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                         }
/*  758 */                         else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                         {
/*  760 */                           String messagetosay = "";
/*  761 */                           if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                           {
/*  763 */                             messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  764 */                             if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                             {
/*  766 */                               messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                             }
/*  768 */                             if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                             {
/*  770 */                               messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                             }
/*      */                             
/*  773 */                             out.write("\n                        <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:5px 5px; 5px; 5px;\">\n<tr>\n<td>\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr>\n<td valign=\"top\"  class=\"message-bg1-blue\"></td>\n<td><div id=\"message-tbrd-blue\"></div></td>\n<td valign=\"top\"  class=\"message-bg2-blue\"></td>\n</tr>\n<tr>\n<td  class=\"message-lbrd-blue\"> </td>\n<td class=\"message-td-bg\"><div class=\"centring\"><span> ");
/*  774 */                             out.print(messagetosay);
/*  775 */                             out.write("</span></div></td>\n<td class=\"message-rbrd-blue\"></td>\n</tr>\n<tr>\n<td  class=\"message-bg3-blue\"></td>\n<td><div id=\"message-bbrd-blue\"></div> </td>\n<td  class=\"message-bg4-blue\"></td>\n</tr>\n\n\n</table>\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n                <tr>\n                <td>&nbsp;\n                </td>\n                </tr>\n\n                    ");
/*      */                           }
/*      */                           
/*      */ 
/*  779 */                           out.write("\n\t\t\t<tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" >\n\t\t\t<tr>\n\n                <td colspan=\"2\" class=\"msg-table-width\"><div class=\"centring\"><span>");
/*      */                           
/*  781 */                           PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  782 */                           _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/*  783 */                           _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/*  785 */                           _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN,USERS");
/*  786 */                           int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/*  787 */                           if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                             for (;;) {
/*  789 */                               out.write("\n\t\t\t<div>");
/*  790 */                               out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  791 */                               out.write("</div>\n\t\t\t");
/*  792 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/*  793 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  797 */                           if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/*  798 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                           }
/*      */                           
/*  801 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*  802 */                           out.write("\n\t\t\t");
/*      */                           
/*  804 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  805 */                           _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  806 */                           _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/*  808 */                           _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN,USERS");
/*  809 */                           int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  810 */                           if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                             for (;;) {
/*  812 */                               out.write("\n\t\t\t<div>");
/*  813 */                               out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  814 */                               out.write("</div>\n\t\t\t");
/*  815 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  816 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  820 */                           if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  821 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                           }
/*      */                           
/*  824 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  825 */                           out.write("</div></td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/*  832 */                           out.write("\n        ");
/*      */                           
/*  834 */                           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  835 */                           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  836 */                           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/*  838 */                           _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                           
/*  840 */                           _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  841 */                           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  842 */                           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  843 */                             String msg = null;
/*  844 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  845 */                               out = _jspx_page_context.pushBody();
/*  846 */                               _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  847 */                               _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                             }
/*  849 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                             for (;;) {
/*  851 */                               out.write("\n        ");
/*  852 */                               if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                 return;
/*  854 */                               out.write("\n        ");
/*  855 */                               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  856 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/*  857 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  860 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  861 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  864 */                           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  865 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                           }
/*      */                           
/*  868 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  869 */                           out.write(" \n        ");
/*      */                           
/*  871 */                           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  872 */                           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  873 */                           _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/*  875 */                           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  876 */                           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  877 */                           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                             for (;;) {
/*  879 */                               out.write("\n        <tr>\n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n           <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t    \t\t\t<tr>\n\t    \t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                    <td class=\"msg-table-width\">");
/*      */                               
/*  881 */                               MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  882 */                               _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  883 */                               _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                               
/*  885 */                               _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                               
/*  887 */                               _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  888 */                               int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  889 */                               if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  890 */                                 String msg = null;
/*  891 */                                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  892 */                                   out = _jspx_page_context.pushBody();
/*  893 */                                   _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  894 */                                   _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                                 }
/*  896 */                                 msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                 for (;;) {
/*  898 */                                   out.write("\n                  \t");
/*  899 */                                   if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                     return;
/*  901 */                                   out.write("\n                  ");
/*  902 */                                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  903 */                                   msg = (String)_jspx_page_context.findAttribute("msg");
/*  904 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*  907 */                                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  908 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/*  911 */                               if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  912 */                                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                               }
/*      */                               
/*  915 */                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  916 */                               out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n\n            </td>\n            </tr>\n\t\t            \t\t</table>\n\t\t\t            \t</td>\n        </tr>\n        ");
/*  917 */                               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  918 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  922 */                           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  923 */                             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                           }
/*      */                           
/*  926 */                           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  927 */                           out.write("\n        ");
/*      */                         }
/*  929 */                         out.write("\n\t    \t");
/*  930 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/*  931 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  935 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/*  936 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                     }
/*      */                     
/*  939 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*  940 */                     out.write("\n        <tr>\n          <td height=\"90%\" colspan=\"2\" valign=\"top\">");
/*  941 */                     if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                       return;
/*  943 */                     out.write("</td>\n        </tr>\n        ");
/*  944 */                     if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                       return;
/*  946 */                     out.write("\n\t        </table>\n  \t\t\t</td>\n\t       ");
/*  947 */                     if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                       return;
/*  949 */                     out.write("\n    </tr>\n </table>\n");
/*      */                     
/*  951 */                     Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                     
/*  953 */                     out.write("\n\t</div> <!-- userAreaContainerDiv ends -->\n    ");
/*  954 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  955 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  959 */                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  960 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                 }
/*      */                 
/*  963 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  964 */                 out.write(10);
/*  965 */                 out.write(32);
/*  966 */                 out.write(32);
/*      */                 
/*  968 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  969 */                 _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  970 */                 _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*  971 */                 int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  972 */                 if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                   for (;;) {
/*  974 */                     out.write(10);
/*  975 */                     if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/*  977 */                     out.write("\n<div id=\"userAreaContainerDiv\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   ");
/*  978 */                     if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/*  980 */                     out.write("\t \n    <td id=\"detailstd\" valign=\"top\" width=\"80%\">    \t\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t");
/*      */                     
/*  982 */                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  983 */                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  984 */                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                     
/*  986 */                     _jspx_th_c_005fif_005f14.setTest("${(empty param.fromTab)}");
/*  987 */                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  988 */                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                       for (;;) {
/*  990 */                         out.write("\n\n        ");
/*      */                         
/*  992 */                         String addOnMsg = "";
/*  993 */                         FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/*      */                         
/*  995 */                         if (rtype.equals("OfficeSharePointServer"))
/*      */                         {
/*  997 */                           if ((!fed.isSharePoint()) || (
/*      */                           
/*      */ 
/*      */ 
/* 1001 */                             (FreeEditionDetails.getAddOnEvalDaysProps() != null) && (FreeEditionDetails.getAddOnEvalDaysProps().getProperty("OfficeSharePoint") != null))) {
/* 1002 */                             String nDays = FreeEditionDetails.getAddOnEvalDaysProps().getProperty("OfficeSharePoint");
/* 1003 */                             addOnMsg = FormatUtil.getString("am.addon.remainingdays.msg", new String[] { "OfficeSharePointServer", nDays });
/*      */                           }
/*      */                         }
/* 1006 */                         else if (rtype.equals("WebsphereMQ"))
/*      */                         {
/* 1008 */                           if ((!fed.isMqSeries()) || (
/*      */                           
/*      */ 
/* 1011 */                             (FreeEditionDetails.getAddOnEvalDaysProps() != null) && (FreeEditionDetails.getAddOnEvalDaysProps().getProperty("WebsphereMQ") != null))) {
/* 1012 */                             String nDays = FreeEditionDetails.getAddOnEvalDaysProps().getProperty("WebsphereMQ");
/* 1013 */                             addOnMsg = FormatUtil.getString("am.addon.remainingdays.msg", new String[] { "WebsphereMQ", nDays });
/*      */                           }
/*      */                         }
/* 1016 */                         if (!addOnMsg.equals(""))
/*      */                         {
/*      */ 
/* 1019 */                           out.write("\n                 <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n<tr>\n<td width=\"99%\">\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/* 1020 */                           out.print(addOnMsg);
/* 1021 */                           out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n                 ");
/*      */                         }
/*      */                         
/* 1024 */                         out.write(10);
/* 1025 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1026 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1030 */                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1031 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                     }
/*      */                     
/* 1034 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1035 */                     out.write("\n              \n");
/*      */                     
/* 1037 */                     boolean allowEdit = (request.getParameter("allowEdit") != null) && (request.getParameter("allowEdit").equalsIgnoreCase("true"));
/* 1038 */                     boolean isMonFullyConfigured = (request.getParameter("fullyConfigured") != null) && (request.getParameter("fullyConfigured").equalsIgnoreCase("true"));
/*      */                     
/* 1040 */                     if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)) && (request.getParameter("fromTab") == null))
/*      */                     {
/*      */ 
/* 1043 */                       out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t  \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"100%\">\n<tr>\n<td width=\"100%\">\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\">");
/* 1044 */                       out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/* 1045 */                       out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n        </tr>\n\t\t");
/*      */ 
/*      */                     }
/* 1048 */                     else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))) && (request.getParameter("fromTab") == null))
/*      */                     {
/* 1050 */                       String messagetosay = "";
/* 1051 */                       if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                       {
/* 1053 */                         messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/* 1054 */                         if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                         {
/* 1056 */                           messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                         }
/* 1058 */                         if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                         {
/* 1060 */                           messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                         }
/*      */                         
/* 1063 */                         out.write("\n                     <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"100%\">\n<tr>\n<td width=\"100%\">\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n             <td class=\"msg-table-width\">");
/* 1064 */                         out.print(messagetosay);
/* 1065 */                         out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n             <tr>\n             <td>&nbsp;\n             </td>\n             </tr>\n\n                 ");
/*      */                       }
/*      */                       
/*      */ 
/* 1069 */                       out.write("\n\t\t\t<tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"100%\">\n<tr>\n<td width=\"100%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"msg-table-style\" border=\"0\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\"></td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n\t\t\t<tr>\n\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n             <td colspan=\"2\" class=\"msg-table-width\">");
/*      */                       
/* 1071 */                       PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1072 */                       _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 1073 */                       _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                       
/* 1075 */                       _jspx_th_logic_005fpresent_005f7.setRole("ENTERPRISEADMIN,USERS");
/* 1076 */                       int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 1077 */                       if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                         for (;;) {
/* 1079 */                           out.write("\n\t\t\t  <div>");
/* 1080 */                           out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/* 1081 */                           out.write("</div>\n\t\t\t  ");
/* 1082 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 1083 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1087 */                       if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 1088 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                       }
/*      */                       
/* 1091 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 1092 */                       out.write("\n\t\t\t  ");
/*      */                       
/* 1094 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1095 */                       _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 1096 */                       _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                       
/* 1098 */                       _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN,USERS");
/* 1099 */                       int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 1100 */                       if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                         for (;;) {
/* 1102 */                           out.write("\n\t\t\t  <div>");
/* 1103 */                           out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/* 1104 */                           out.write("</div>\n\t\t\t  ");
/* 1105 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 1106 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1110 */                       if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 1111 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                       }
/*      */                       
/* 1114 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 1115 */                       out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                     }
/* 1118 */                     else if ((request.getParameter("fromTab") == null) && (!isMonFullyConfigured) && (allowEdit))
/*      */                     {
/*      */ 
/* 1121 */                       out.write("\n\t <tr>\n\t<td colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t<table height=\"46\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin: 0px 0px 0px 0px;\" width=\"100%\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"100%\">\n\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"msg-table-style\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t\t\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t\t\t\t\t<td width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_getmoredata.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                       
/* 1123 */                       ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1124 */                       _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1125 */                       _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_c_005fotherwise_005f6);
/* 1126 */                       int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1127 */                       if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                         for (;;) {
/* 1129 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/* 1131 */                           WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1132 */                           _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1133 */                           _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                           
/* 1135 */                           _jspx_th_c_005fwhen_005f10.setTest("${param.eumChild == 'true'}");
/* 1136 */                           int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1137 */                           if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                             for (;;) {
/* 1139 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"msg-table-width\">");
/* 1140 */                               out.print(FormatUtil.getString("am.webclient.configure.credential.link.text", new String[] { (String)request.getAttribute("parentEumMonId"), request.getParameter("type") }));
/* 1141 */                               out.write("</td>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t");
/* 1142 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1143 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1147 */                           if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1148 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                           }
/*      */                           
/* 1151 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1152 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/* 1154 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1155 */                           _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1156 */                           _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1157 */                           int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1158 */                           if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                             for (;;) {
/* 1160 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"msg-table-width\">");
/* 1161 */                               out.print(FormatUtil.getString("am.webclient.configure.credential.link.text", new String[] { resourceid, request.getParameter("type") }));
/* 1162 */                               out.write("</td>\n\t\t\t\t\t\t\t\t\t\t");
/* 1163 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1164 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1168 */                           if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1169 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                           }
/*      */                           
/* 1172 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1173 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1174 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1175 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1179 */                       if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1180 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                       }
/*      */                       
/* 1183 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1184 */                       out.write("\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t\t\t\t\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t\t\t\t\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table></td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t</tr>\n\t");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 1189 */                       String msgStyle = "display:block";
/* 1190 */                       String divID = "showMsg";
/* 1191 */                       if (request.getParameter("fromTab") != null) {
/* 1192 */                         msgStyle = "display:none";
/* 1193 */                         divID = "showMsgForTab";
/*      */                       }
/*      */                       
/*      */ 
/* 1197 */                       out.write("\n     ");
/*      */                       
/* 1199 */                       IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1200 */                       _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1201 */                       _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                       
/* 1203 */                       _jspx_th_c_005fif_005f15.setTest("${(empty param.fromTab)}");
/* 1204 */                       int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1205 */                       if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                         for (;;) {
/* 1207 */                           out.write("\n        ");
/*      */                           
/* 1209 */                           MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1210 */                           _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1211 */                           _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                           
/* 1213 */                           _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                           
/* 1215 */                           _jspx_th_html_005fmessages_005f2.setMessage("false");
/* 1216 */                           int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1217 */                           if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1218 */                             String msg = null;
/* 1219 */                             if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1220 */                               out = _jspx_page_context.pushBody();
/* 1221 */                               _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1222 */                               _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                             }
/* 1224 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                             for (;;) {
/* 1226 */                               out.write("\n        ");
/* 1227 */                               if (_jspx_meth_c_005fif_005f16(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                                 return;
/* 1229 */                               out.write("\n\n              ");
/* 1230 */                               if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                                 return;
/* 1232 */                               out.write("<br>\n\n        ");
/* 1233 */                               int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1234 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/* 1235 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1238 */                             if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1239 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1242 */                           if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1243 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                           }
/*      */                           
/* 1246 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1247 */                           out.write("\n        ");
/* 1248 */                           if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                             return;
/* 1250 */                           out.write(10);
/* 1251 */                           out.write(9);
/* 1252 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1253 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1257 */                       if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1258 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                       }
/*      */                       
/* 1261 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1262 */                       out.write("\n        ");
/*      */                       
/* 1264 */                       MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1265 */                       _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1266 */                       _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                       
/* 1268 */                       _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1269 */                       int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1270 */                       if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                         for (;;) {
/* 1272 */                           out.write("\n        <tr>           \n          <td colspan=\"2\" valign=\"top\" class=\"tdindent\"> <div id=\"");
/* 1273 */                           out.print(divID);
/* 1274 */                           out.write("\" style=\"");
/* 1275 */                           out.print(msgStyle);
/* 1276 */                           out.write("\"><table height=\"46\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"100%\">\n<tr>\n<td width=\"100%\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"msg-table-style\" >\n\t<tr>\n\t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n<tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t<tr>\n\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n                <td class=\"msg-table-width\"><div id=\"htmlMessage\">");
/*      */                           
/* 1278 */                           MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1279 */                           _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/* 1280 */                           _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                           
/* 1282 */                           _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                           
/* 1284 */                           _jspx_th_html_005fmessages_005f3.setMessage("true");
/* 1285 */                           int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/* 1286 */                           if (_jspx_eval_html_005fmessages_005f3 != 0) {
/* 1287 */                             String msg = null;
/* 1288 */                             if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1289 */                               out = _jspx_page_context.pushBody();
/* 1290 */                               _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/* 1291 */                               _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                             }
/* 1293 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                             for (;;) {
/* 1295 */                               out.write("\n                  ");
/* 1296 */                               if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                                 return;
/* 1298 */                               out.write("</div> \n                  ");
/* 1299 */                               int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/* 1300 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/* 1301 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1304 */                             if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1305 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1308 */                           if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/* 1309 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                           }
/*      */                           
/* 1312 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/* 1313 */                           out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table></div></td> \n        </tr>\n        ");
/* 1314 */                           int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1315 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1319 */                       if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1320 */                         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                       }
/*      */                       
/* 1323 */                       this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1324 */                       out.write("\n       \n        ");
/*      */                     }
/* 1326 */                     out.write("\n        <div id=\"showMessage\" style=\"display:none\"></div>\n        \n\t");
/*      */                     
/* 1328 */                     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1329 */                     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1330 */                     _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                     
/* 1332 */                     _jspx_th_c_005fif_005f18.setTest("${(empty param.fromTab)}");
/* 1333 */                     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1334 */                     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                       for (;;) {
/* 1336 */                         out.write("\n        ");
/*      */                         
/* 1338 */                         PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1339 */                         _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 1340 */                         _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f18);
/*      */                         
/* 1342 */                         _jspx_th_logic_005fpresent_005f8.setRole("OPERATOR");
/* 1343 */                         int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 1344 */                         if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                           for (;;) {
/* 1346 */                             out.write("\n\n\t     \t\t\t");
/*      */                             
/* 1348 */                             String mes = request.getParameter("message");
/* 1349 */                             if ((mes != null) && (mes.equals("false")))
/*      */                             {
/* 1351 */                               out.write("\n\t     \t\t\t<tr>\n\t\t\t\t\t          <td colspan=\"2\" class=\"tdindent\" valign=\"top\" height=\"46\"> <table style=\"margin: 5px;\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t<td width=\"100%\">\n\n\t\t\t\t\t<table class=\"msg-table-style\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n\t\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t\t\t<td class=\"msg-status-tp-left-corn\" width=\"7\"></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t\t\t\t<td class=\"msg-status-tp-right-corn\" width=\"9\"></td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t\t\t\t<td class=\"msg-table-width\" width=\"98%\">\n\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t\t\t\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t\t\t\t\t                <td class=\"msg-table-width\">\n\t\t\t\t\t                 ");
/* 1352 */                               out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 1353 */                               out.write("\n\t\t\t\t\t                  </td>\n\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t</tbody></table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t\t\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t\t\t\t<td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody></table>\n\n\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody></table></td>\n\t        </tr>\n\t     \t\t\t");
/*      */                             }
/* 1355 */                             out.write("\n\n     \t\t");
/* 1356 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 1357 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1361 */                         if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 1362 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                         }
/*      */                         
/* 1365 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 1366 */                         out.write("     \t\t\t\n\t\t");
/* 1367 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1368 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1372 */                     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1373 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                     }
/*      */                     
/* 1376 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1377 */                     out.write("\n\t\t\t\n        <tr>\n          <td height=\"90%\" colspan=\"2\" valign=\"top\"> ");
/* 1378 */                     if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/* 1380 */                     out.write("</td>\n        </tr>\n        <tr>\n          <td height=\"20%\" colspan=\"2\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n      </table></td>\n       ");
/* 1381 */                     if (_jspx_meth_c_005fif_005f19(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/* 1383 */                     out.write("\n  </tr>\n</table>\n\n</div> <!-- userAreaContainerDiv ends -->\n");
/* 1384 */                     if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/* 1386 */                     out.write(10);
/*      */                     
/* 1388 */                     Constants.checkAndLogSlowQuery(request, 60000L);
/*      */                     
/* 1390 */                     out.write(10);
/* 1391 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1392 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1396 */                 if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1397 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                 }
/*      */                 
/* 1400 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1401 */                 out.write(10);
/* 1402 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1403 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1407 */             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1408 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */             }
/*      */             else {
/* 1411 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1412 */               out.write("\n<script type=\"text/javascript\">\n  try\n  {\n    form = document.forms[0];\n    if(form && form.action.indexOf(\"Search.do\")<0)  //NO I18N\n    {\n      $('body').prepend('<form id=\"mySearch\" action=\"/Search.do\" style=\"display:none\"></form>');   //No I18N\n    }\n  }\n  catch(err){\n\n  }\n</script>\n");
/* 1413 */               out.write(10);
/* 1414 */               out.write(10);
/* 1415 */               if (Constants.isIt360) {
/* 1416 */                 out.write("\n<script type=\"text/javascript\">\n\tvar iframe= parent.window.document.getElementById('_iframe_view');\n\tif(iframe != null)\n\t{\n\t\tvar iframeName = iframe.name;\n\t\tif(iframeName != null && iframeName == '_iframe_view')\n\t\t{\n\t\t\tvar frameDoc = iframe.contentDocument || iframe.contentWindow.document;\n\t\t\tvar e = frameDoc.getElementById(\"userAreaContainerDiv\");\n\t\t\tif(e!=null)\n\t\t\t{\n\t\t\t\te.id = \"userAreaContainerDiv_admin\";\n\t\t\t}\n\t\t\n\t\t\tvar footer = frameDoc.getElementById(\"footer-container\");\n\t\t\tif(footer!=null)\n\t\t\t{\n\t\t\t\tfooter.innerHTML=\"\";\n\t\t\t}\n\t\t}\n\t}\n</script>\n");
/*      */               }
/* 1418 */               out.write(10);
/* 1419 */               out.write(10);
/* 1420 */               out.write(10);
/* 1421 */               out.write(10);
/*      */               
/* 1423 */               if (PluginUtil.isPlugin())
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/* 1428 */                 out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 1429 */                 out.print(com.manageengine.appmanager.plugin.RequestUtil.getURL("", request.getServerName()));
/* 1430 */                 out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*      */               }
/*      */               
/*      */ 
/* 1434 */               out.write("\n</body>\n</html>\n\n");
/*      */             }
/* 1436 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1437 */         out = _jspx_out;
/* 1438 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1439 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1440 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1443 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1449 */     PageContext pageContext = _jspx_page_context;
/* 1450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1452 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1453 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1454 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1456 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1458 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1459 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1460 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1461 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1462 */       return true;
/*      */     }
/* 1464 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1470 */     PageContext pageContext = _jspx_page_context;
/* 1471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1473 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 1474 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 1475 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/* 1477 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 1478 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 1479 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 1480 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1481 */       return true;
/*      */     }
/* 1483 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1489 */     PageContext pageContext = _jspx_page_context;
/* 1490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1492 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1493 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1494 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 1496 */     _jspx_th_c_005fif_005f1.setTest("${empty param.noreload && not isConfMonitor}");
/* 1497 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1498 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1500 */         out.write("\n\t  \t ");
/* 1501 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1502 */           return true;
/* 1503 */         out.write("\n  \t ");
/* 1504 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1505 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1509 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1510 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1511 */       return true;
/*      */     }
/* 1513 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1519 */     PageContext pageContext = _jspx_page_context;
/* 1520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1522 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1523 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1524 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 1525 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1526 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1528 */         out.write("\n\t  \t \t\t");
/* 1529 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1530 */           return true;
/* 1531 */         out.write("\n\t  \t \t\t");
/* 1532 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1533 */           return true;
/* 1534 */         out.write("\n\t  \t ");
/* 1535 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1536 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1540 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1541 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1542 */       return true;
/*      */     }
/* 1544 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1550 */     PageContext pageContext = _jspx_page_context;
/* 1551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1553 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1554 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1555 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1557 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty reloadperiod}");
/* 1558 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1559 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1561 */         out.write("\n\t  \t \t\t\t<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1562 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 1563 */           return true;
/* 1564 */         out.write("\">\n\t  \t \t\t\t\n\t  \t \t\t");
/* 1565 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1566 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1570 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1571 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1572 */       return true;
/*      */     }
/* 1574 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1580 */     PageContext pageContext = _jspx_page_context;
/* 1581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1583 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1584 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1585 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1587 */     _jspx_th_c_005fout_005f1.setValue("${reloadperiod}");
/* 1588 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1589 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1590 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1591 */       return true;
/*      */     }
/* 1593 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1594 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1599 */     PageContext pageContext = _jspx_page_context;
/* 1600 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1602 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1603 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1604 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1605 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1606 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1608 */         out.write("\n\t  \t \t\t\t");
/* 1609 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1610 */           return true;
/* 1611 */         out.write("\t  \t \t\t\t\n\t  \t \t\t");
/* 1612 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1617 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1618 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1619 */       return true;
/*      */     }
/* 1621 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1627 */     PageContext pageContext = _jspx_page_context;
/* 1628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1630 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1631 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1632 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1634 */     _jspx_th_c_005fif_005f2.setTest("${!empty customreloadperiod}");
/* 1635 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1636 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 1638 */         out.write("\n\t\t\t\t\t\t<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1639 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 1640 */           return true;
/* 1641 */         out.write("\">\n\t\t\t\t\t");
/* 1642 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1647 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1648 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1649 */       return true;
/*      */     }
/* 1651 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1657 */     PageContext pageContext = _jspx_page_context;
/* 1658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1660 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1661 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1662 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1664 */     _jspx_th_c_005fout_005f2.setValue("${customreloadperiod}");
/* 1665 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1666 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1667 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1668 */       return true;
/*      */     }
/* 1670 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1676 */     PageContext pageContext = _jspx_page_context;
/* 1677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1679 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1680 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1681 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1683 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1685 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1686 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1688 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1689 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1691 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1692 */           boolean bool; if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1693 */             return true;
/* 1694 */           out.write("\"\" class=\"staticlinks\">");
/* 1695 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1696 */             return true;
/* 1697 */           out.write("</a></td>\n");
/* 1698 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1699 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1703 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1704 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1707 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1708 */         out = _jspx_page_context.popBody(); }
/* 1709 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1711 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1712 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1719 */     PageContext pageContext = _jspx_page_context;
/* 1720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1722 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1723 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1724 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1726 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABLINK}");
/* 1727 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1728 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1729 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1730 */       return true;
/*      */     }
/* 1732 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1738 */     PageContext pageContext = _jspx_page_context;
/* 1739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1741 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1742 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1743 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1745 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1746 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1747 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1748 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1749 */       return true;
/*      */     }
/* 1751 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1757 */     PageContext pageContext = _jspx_page_context;
/* 1758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1760 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1761 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1762 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1764 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1766 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1767 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1769 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1770 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1772 */           out.write(10);
/* 1773 */           out.write(10);
/* 1774 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1775 */             return true;
/* 1776 */           out.write(10);
/* 1777 */           out.write(10);
/* 1778 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1779 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1783 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1784 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1787 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1788 */         out = _jspx_page_context.popBody(); }
/* 1789 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1791 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1792 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1799 */     PageContext pageContext = _jspx_page_context;
/* 1800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1802 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1803 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1804 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1805 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1806 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1808 */         out.write(10);
/* 1809 */         out.write(10);
/* 1810 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1811 */           return true;
/* 1812 */         out.write(10);
/* 1813 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1814 */           return true;
/* 1815 */         out.write(10);
/* 1816 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1817 */           return true;
/* 1818 */         out.write("\n\n\n\n\n");
/* 1819 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1820 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1824 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1825 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1826 */       return true;
/*      */     }
/* 1828 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1834 */     PageContext pageContext = _jspx_page_context;
/* 1835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1837 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1838 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1839 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1841 */     _jspx_th_c_005fwhen_005f2.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1842 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1843 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1845 */         out.write(10);
/* 1846 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1847 */           return true;
/* 1848 */         out.write(10);
/* 1849 */         out.write(32);
/* 1850 */         out.write(32);
/* 1851 */         if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1852 */           return true;
/* 1853 */         out.write(10);
/* 1854 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1855 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1859 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1860 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1861 */       return true;
/*      */     }
/* 1863 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1869 */     PageContext pageContext = _jspx_page_context;
/* 1870 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1872 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1873 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1874 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1876 */     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1877 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1878 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1880 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1881 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1882 */           return true;
/* 1883 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_logic_005fpresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1884 */           return true;
/* 1885 */         out.write("\n  </a></td>\n  ");
/* 1886 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1887 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1891 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1892 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1893 */       return true;
/*      */     }
/* 1895 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1901 */     PageContext pageContext = _jspx_page_context;
/* 1902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1904 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1905 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1906 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 1907 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1908 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1910 */         out.write("\n  \t\t");
/* 1911 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1912 */           return true;
/* 1913 */         out.write("\n  \t\t");
/* 1914 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1915 */           return true;
/* 1916 */         out.write("\n  \t\t");
/* 1917 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1918 */           return true;
/* 1919 */         out.write("\n  \t");
/* 1920 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1921 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1925 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1926 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1927 */       return true;
/*      */     }
/* 1929 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1935 */     PageContext pageContext = _jspx_page_context;
/* 1936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1938 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1939 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1940 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1942 */     _jspx_th_c_005fwhen_005f3.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1943 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1944 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1946 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1947 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1948 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1952 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1953 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1954 */       return true;
/*      */     }
/* 1956 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1962 */     PageContext pageContext = _jspx_page_context;
/* 1963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1965 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1966 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1967 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1969 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1970 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1971 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1973 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1974 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1975 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1979 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1980 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1981 */       return true;
/*      */     }
/* 1983 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1989 */     PageContext pageContext = _jspx_page_context;
/* 1990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1992 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1993 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1994 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1995 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1996 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1998 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1999 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2000 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2004 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2005 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2006 */       return true;
/*      */     }
/* 2008 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2014 */     PageContext pageContext = _jspx_page_context;
/* 2015 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2017 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2018 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2019 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 2021 */     _jspx_th_c_005fout_005f5.setValue("${tab.TABNAME}");
/* 2022 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2023 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2037 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2040 */     _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 2041 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2042 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2044 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 2045 */         if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2046 */           return true;
/* 2047 */         out.write("\n\t</td> \n  ");
/* 2048 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2049 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2053 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2054 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2055 */       return true;
/*      */     }
/* 2057 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2063 */     PageContext pageContext = _jspx_page_context;
/* 2064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2066 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2067 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2068 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 2069 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2070 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2072 */         out.write("\n   \t\t ");
/* 2073 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2074 */           return true;
/* 2075 */         out.write("\n   \t \t ");
/* 2076 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2077 */           return true;
/* 2078 */         out.write("\n   \t");
/* 2079 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2080 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2084 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2085 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2086 */       return true;
/*      */     }
/* 2088 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2094 */     PageContext pageContext = _jspx_page_context;
/* 2095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2097 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2098 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2099 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2101 */     _jspx_th_c_005fwhen_005f5.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2102 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2103 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2105 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 2106 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2107 */           return true;
/* 2108 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2109 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2110 */           return true;
/* 2111 */         out.write("</a>\n   \t\t ");
/* 2112 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2117 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2118 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2119 */       return true;
/*      */     }
/* 2121 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2127 */     PageContext pageContext = _jspx_page_context;
/* 2128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2130 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2131 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2132 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2134 */     _jspx_th_c_005fout_005f6.setValue("${globalconfig['defaultmonitorsview']}");
/* 2135 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2136 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2138 */       return true;
/*      */     }
/* 2140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2146 */     PageContext pageContext = _jspx_page_context;
/* 2147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2149 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2150 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2151 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2153 */     _jspx_th_c_005fout_005f7.setValue("${tab.TABNAME}");
/* 2154 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2155 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2156 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2157 */       return true;
/*      */     }
/* 2159 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2165 */     PageContext pageContext = _jspx_page_context;
/* 2166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2168 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2169 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2170 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2171 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2172 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2174 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 2175 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2176 */           return true;
/* 2177 */         out.write("\" class=\"staticlinks\">");
/* 2178 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2179 */           return true;
/* 2180 */         out.write("</a>\n   \t\t ");
/* 2181 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2182 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2186 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2187 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2188 */       return true;
/*      */     }
/* 2190 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2196 */     PageContext pageContext = _jspx_page_context;
/* 2197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2199 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2200 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2201 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2203 */     _jspx_th_c_005fout_005f8.setValue("${globalconfig['defaultmonitorsview']}");
/* 2204 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2205 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2206 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2207 */       return true;
/*      */     }
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2215 */     PageContext pageContext = _jspx_page_context;
/* 2216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2218 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2219 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2220 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2222 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 2223 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2224 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2225 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2226 */       return true;
/*      */     }
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2234 */     PageContext pageContext = _jspx_page_context;
/* 2235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2237 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2238 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2239 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2241 */     _jspx_th_c_005fwhen_005f6.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 2242 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2243 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 2245 */         out.write(10);
/* 2246 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2247 */           return true;
/* 2248 */         out.write(10);
/* 2249 */         out.write(9);
/* 2250 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2251 */           return true;
/* 2252 */         out.write(10);
/* 2253 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2254 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2258 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2259 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2260 */       return true;
/*      */     }
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2268 */     PageContext pageContext = _jspx_page_context;
/* 2269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2271 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2272 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2273 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2275 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2276 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2277 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2279 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 2280 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2281 */           return true;
/* 2282 */         out.write("</a></td>\n\t");
/* 2283 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2284 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2288 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2289 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2290 */       return true;
/*      */     }
/* 2292 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2298 */     PageContext pageContext = _jspx_page_context;
/* 2299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2301 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2302 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2303 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2305 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 2306 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2307 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2308 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2309 */       return true;
/*      */     }
/* 2311 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2317 */     PageContext pageContext = _jspx_page_context;
/* 2318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2320 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2321 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2322 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2324 */     _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 2325 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2326 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 2328 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 2329 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2330 */           return true;
/* 2331 */         out.write("</a></td>\n\t");
/* 2332 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2333 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2337 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2338 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2339 */       return true;
/*      */     }
/* 2341 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2347 */     PageContext pageContext = _jspx_page_context;
/* 2348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2350 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2351 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2352 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 2354 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABNAME}");
/* 2355 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2356 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2357 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2358 */       return true;
/*      */     }
/* 2360 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2366 */     PageContext pageContext = _jspx_page_context;
/* 2367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2369 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2370 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2371 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2372 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2373 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2375 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 2376 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2377 */           return true;
/* 2378 */         out.write("\" class=\"staticlinks\">\t");
/* 2379 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2380 */           return true;
/* 2381 */         out.write("</a></td>\n");
/* 2382 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2383 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2387 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2388 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2389 */       return true;
/*      */     }
/* 2391 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2397 */     PageContext pageContext = _jspx_page_context;
/* 2398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2400 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2401 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2402 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2404 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABLINK}");
/* 2405 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2406 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2407 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2408 */       return true;
/*      */     }
/* 2410 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2416 */     PageContext pageContext = _jspx_page_context;
/* 2417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2419 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2420 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2421 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2423 */     _jspx_th_c_005fout_005f13.setValue("${tab.TABNAME}");
/* 2424 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2425 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2426 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2427 */       return true;
/*      */     }
/* 2429 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2435 */     PageContext pageContext = _jspx_page_context;
/* 2436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2438 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2439 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2440 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/* 2441 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2442 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 2444 */         out.write("\n  \t\t");
/* 2445 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2446 */           return true;
/* 2447 */         out.write("\n  \t\t");
/* 2448 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2449 */           return true;
/* 2450 */         out.write("\n  \t\t");
/* 2451 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2452 */           return true;
/* 2453 */         out.write("\n  \t");
/* 2454 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2455 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2459 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2460 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2461 */       return true;
/*      */     }
/* 2463 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2469 */     PageContext pageContext = _jspx_page_context;
/* 2470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2472 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2473 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2474 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2476 */     _jspx_th_c_005fwhen_005f7.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2477 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2478 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 2480 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 2481 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2482 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2486 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2487 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2488 */       return true;
/*      */     }
/* 2490 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2496 */     PageContext pageContext = _jspx_page_context;
/* 2497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2499 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2500 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2501 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2503 */     _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2504 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2505 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 2507 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2508 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2509 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2513 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2514 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2515 */       return true;
/*      */     }
/* 2517 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2523 */     PageContext pageContext = _jspx_page_context;
/* 2524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2526 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2527 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2528 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 2529 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2530 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2532 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 2533 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2538 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2539 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2540 */       return true;
/*      */     }
/* 2542 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2548 */     PageContext pageContext = _jspx_page_context;
/* 2549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2551 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2552 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2553 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f9);
/*      */     
/* 2555 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2556 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2557 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2558 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2559 */       return true;
/*      */     }
/* 2561 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2567 */     PageContext pageContext = _jspx_page_context;
/* 2568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2570 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2571 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 2572 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2574 */     _jspx_th_c_005fout_005f15.setValue("${globalconfig['defaultmonitorsview']}");
/* 2575 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 2576 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 2577 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2578 */       return true;
/*      */     }
/* 2580 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2586 */     PageContext pageContext = _jspx_page_context;
/* 2587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2589 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2590 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2591 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2593 */     _jspx_th_c_005fif_005f7.setTest("${(empty param.fromTab)}");
/* 2594 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2595 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2597 */         out.write("\n        <tr>\n          <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> \n          <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n            <tr>\n            <td width=\"99%\">\n\n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"msg-table-style\" >\n\t    \t<tr>\n\t    \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t    \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t    \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t    \t</tr>\n\t    <tr>\n\t    \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t    \t<td  width=\"98%\" class=\"msg-table-width\">\n\t    \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t    \t\t\t<tr>\n\t    \t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                    <td class=\"msg-table-width\">");
/* 2598 */         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 2599 */           return true;
/* 2600 */         out.write("</td>\n\t    \t\t\t</tr>\n\t    \t\t</table>\n\t    \t</td>\n\t    \t<td class=\"msg-status-right-bg\"></td>\n\t    </tr>\n\t    <tr>\n\t    <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t    <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t    </tr>\n</table>\n\n\n            </td>\n            </tr>\n            </table>\n            </td>\n        </tr>\n        ");
/* 2601 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2602 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2606 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2607 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2608 */       return true;
/*      */     }
/* 2610 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2616 */     PageContext pageContext = _jspx_page_context;
/* 2617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2619 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2620 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2621 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2623 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2625 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2626 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2627 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2628 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2629 */       return true;
/*      */     }
/* 2631 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2637 */     PageContext pageContext = _jspx_page_context;
/* 2638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2640 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2641 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2642 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2644 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2646 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2647 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2648 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2649 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2650 */       return true;
/*      */     }
/* 2652 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2653 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2658 */     PageContext pageContext = _jspx_page_context;
/* 2659 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2661 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2662 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2663 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2665 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 2666 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2667 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2668 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2669 */       return true;
/*      */     }
/* 2671 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2672 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2677 */     PageContext pageContext = _jspx_page_context;
/* 2678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2680 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2681 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2682 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2684 */     _jspx_th_c_005fif_005f8.setTest("${(empty param.fromTab)}");
/* 2685 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2686 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2688 */         out.write("\n\t\t <tr>\n          <td height=\"20%\" colspan=\"2\" valign=\"bottom\">\n          \t");
/* 2689 */         if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 2690 */           return true;
/* 2691 */         out.write(" \t\n          </td>\n        </tr>\n        ");
/* 2692 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2693 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2697 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2698 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2699 */       return true;
/*      */     }
/* 2701 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2707 */     PageContext pageContext = _jspx_page_context;
/* 2708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2710 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2711 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2712 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2714 */     _jspx_th_c_005fif_005f9.setTest("${(empty param.fromTab)}");
/* 2715 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2716 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2718 */         out.write("\n       \t\t   \t");
/* 2719 */         if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 2720 */           return true;
/* 2721 */         out.write("\n       \t\t  ");
/* 2722 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2723 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2727 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2728 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2729 */       return true;
/*      */     }
/* 2731 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2737 */     PageContext pageContext = _jspx_page_context;
/* 2738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2740 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2741 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2742 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2744 */     _jspx_th_c_005fif_005f10.setTest("${selectedscheme == 'slim'}");
/* 2745 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2746 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2748 */         out.write(32);
/* 2749 */         if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2750 */           return true;
/* 2751 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2752 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2756 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2757 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2758 */       return true;
/*      */     }
/* 2760 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2766 */     PageContext pageContext = _jspx_page_context;
/* 2767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2769 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2770 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2771 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2773 */     _jspx_th_tiles_005finsert_005f1.setAttribute("footer");
/* 2774 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2775 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2776 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2777 */       return true;
/*      */     }
/* 2779 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2785 */     PageContext pageContext = _jspx_page_context;
/* 2786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2788 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2789 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2790 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2792 */     _jspx_th_c_005fif_005f11.setTest("${selectedscheme == 'slim'}");
/* 2793 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2794 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2796 */         out.write(" \n       \t\t<td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t  \t  <img src=\"/images/spacer.gif\"/>\n     \t\t</td>\n      \t\t");
/* 2797 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2802 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2803 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2804 */       return true;
/*      */     }
/* 2806 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2812 */     PageContext pageContext = _jspx_page_context;
/* 2813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2815 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2816 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2817 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2819 */     _jspx_th_c_005fif_005f12.setTest("${(empty param.fromTab)}");
/* 2820 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2821 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2823 */         out.write(10);
/* 2824 */         if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 2825 */           return true;
/* 2826 */         out.write(10);
/* 2827 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2828 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2832 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2833 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2834 */       return true;
/*      */     }
/* 2836 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2842 */     PageContext pageContext = _jspx_page_context;
/* 2843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2845 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2846 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2847 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 2849 */     _jspx_th_tiles_005finsert_005f2.setAttribute("Header");
/* 2850 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2851 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2865 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2868 */     _jspx_th_c_005fif_005f13.setTest("${(empty param.fromTab)}");
/* 2869 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 2870 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 2872 */         out.write(" \n\t<td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t  \t  <img src=\"/images/spacer.gif\"/>\n\t</td>\n\t");
/* 2873 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 2874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2878 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 2879 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2880 */       return true;
/*      */     }
/* 2882 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2888 */     PageContext pageContext = _jspx_page_context;
/* 2889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2891 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2892 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2893 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2895 */     _jspx_th_c_005fif_005f16.setTest("${empty firstrow}");
/* 2896 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2897 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2899 */         out.write("\n\n           <tr>\n\t          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\n\t          <table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"100%\">\n                <tr>\n                <td width=\"100%\">\n\n\n\n               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"msg-table-style\" >\n\t       \t<tr>\n\t       \t\t<td width=\"7\" class=\"msg-status-tp-left-corn\"></td>\n\t       \t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t       \t\t<td width=\"9\" class=\"msg-status-tp-right-corn\"></td>\n\t       \t</tr>\n\t       <tr>\n\t       \t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t       \t<td width=\"98%\" class=\"msg-table-width\">\n\t       \t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">\n\t       \t\t\t<tr>\n\t       \t\t\t\t<td class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">&nbsp;</td>\n\t                       <td class=\"msg-table-width\">");
/* 2900 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2901 */           return true;
/* 2902 */         out.write("\n        ");
/* 2903 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2908 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2909 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2910 */       return true;
/*      */     }
/* 2912 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2913 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2918 */     PageContext pageContext = _jspx_page_context;
/* 2919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2921 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2922 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2923 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2925 */     _jspx_th_c_005fset_005f1.setVar("firstrow");
/*      */     
/* 2927 */     _jspx_th_c_005fset_005f1.setValue("true");
/* 2928 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2929 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2930 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2931 */       return true;
/*      */     }
/* 2933 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2939 */     PageContext pageContext = _jspx_page_context;
/* 2940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2942 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2943 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2944 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2946 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2948 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2949 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2950 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2951 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2952 */       return true;
/*      */     }
/* 2954 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2960 */     PageContext pageContext = _jspx_page_context;
/* 2961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2963 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2964 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2965 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2967 */     _jspx_th_c_005fif_005f17.setTest("${!empty firstrow}");
/* 2968 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2969 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 2971 */         out.write("</td>\n\t       \t\t\t</tr>\n\t       \t\t</table>\n\t       \t</td>\n\t       \t<td class=\"msg-status-right-bg\"></td>\n\t       </tr>\n\t       <tr>\n\t       <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t       <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t       <td class=\"msg-status-btm-right-corn\" width=\"9\">&nbsp;</td>\n\t       </tr>\n</table>\n\n\n\n                </td>\n                </tr>\n                </table>\n\n</td>\n        </tr>\n\t");
/* 2972 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2973 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2977 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2991 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2994 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2996 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2997 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2998 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2999 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 3000 */       return true;
/*      */     }
/* 3002 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 3003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3008 */     PageContext pageContext = _jspx_page_context;
/* 3009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3011 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 3012 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 3013 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3015 */     _jspx_th_tiles_005finsert_005f3.setAttribute("UserArea");
/* 3016 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 3017 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 3018 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 3019 */       return true;
/*      */     }
/* 3021 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 3022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3027 */     PageContext pageContext = _jspx_page_context;
/* 3028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3030 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3031 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3032 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3034 */     _jspx_th_c_005fif_005f19.setTest("${(empty param.fromTab)}");
/* 3035 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3036 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 3038 */         out.write(" \n      \t<td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t  \t  <img src=\"/images/spacer.gif\"/>\n      </td>\n      ");
/* 3039 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3044 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3045 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3046 */       return true;
/*      */     }
/* 3048 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3054 */     PageContext pageContext = _jspx_page_context;
/* 3055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3057 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 3058 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 3059 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3061 */     _jspx_th_tiles_005finsert_005f4.setAttribute("footer");
/* 3062 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 3063 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 3064 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 3065 */       return true;
/*      */     }
/* 3067 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 3068 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ServerLayoutNoLeft_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */