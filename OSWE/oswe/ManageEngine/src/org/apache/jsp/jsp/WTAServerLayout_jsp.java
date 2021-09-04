/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.tags.LoadTime;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
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
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class WTAServerLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public String getHelpLink(String key)
/*      */   {
/*   47 */     ret = "<a href=\"/help/index.html\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
/*   48 */     ResultSet set = null;
/*      */     try
/*      */     {
/*   51 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   52 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   53 */       set = AMConnectionPool.executeQueryStmt(query);
/*   54 */       if (set.next())
/*      */       {
/*   56 */         String helpLink = set.getString("LINK");
/*      */         try
/*      */         {
/*   59 */           set.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*   65 */         return "<a href=\"" + helpLink + "\" target=\"newwindow\" class=\"footer\" >" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a>";
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
/*   82 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*   75 */         set.close();
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   84 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   90 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(5);
/*   91 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*   92 */     _jspx_dependants.put("/jsp/inlinefeedback.jsp", Long.valueOf(1473429417000L));
/*   93 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   94 */     _jspx_dependants.put("/jsp/includes/top.jspf", Long.valueOf(1473429417000L));
/*   95 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  121 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  125 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  130 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  131 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  132 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  133 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  134 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  135 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  136 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  137 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  138 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  139 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  140 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  141 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  142 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  143 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  144 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  148 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  150 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  151 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  152 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  153 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  154 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  155 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  156 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  157 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  158 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  159 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  160 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  161 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  162 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.release();
/*  163 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  164 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*  165 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  172 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  175 */     JspWriter out = null;
/*  176 */     Object page = this;
/*  177 */     JspWriter _jspx_out = null;
/*  178 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  182 */       response.setContentType("text/html");
/*  183 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  185 */       _jspx_page_context = pageContext;
/*  186 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  187 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  188 */       session = pageContext.getSession();
/*  189 */       out = pageContext.getOut();
/*  190 */       _jspx_out = out;
/*      */       
/*  192 */       out.write("<!DOCTYPE html>\n");
/*  193 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  194 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  196 */       String resourceid = request.getParameter("resourceid");
/*  197 */       String searchQuery = request.getParameter("query");
/*  198 */       String uri = (String)request.getAttribute("uri");
/*  199 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/*  200 */         searchQuery = "";
/*      */       }
/*  202 */       String addtotitle = "";
/*  203 */       if (Constants.getCategorytype().equals("LAMP"))
/*      */       {
/*  205 */         addtotitle = " Lamp Edition";
/*      */       }
/*  207 */       else if (Constants.getCategorytype().equals("DATABASE"))
/*      */       {
/*  209 */         addtotitle = " DATABASE Edition";
/*      */       }
/*  211 */       String responseTimeMessage = FormatUtil.getString("webclient.server.responsetime");
/*  212 */       String systemTimeMessage = FormatUtil.getString("webclient.server.systemtime");
/*  213 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  215 */         responseTimeMessage = FormatUtil.getString("webclient.server.responsetime.managedserver");
/*  216 */         systemTimeMessage = FormatUtil.getString("webclient.server.systemtime.managedserver");
/*      */       }
/*      */       
/*  219 */       out.write(10);
/*  220 */       Properties applications = null;
/*  221 */       synchronized (application) {
/*  222 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  223 */         if (applications == null) {
/*  224 */           applications = new Properties();
/*  225 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */         }
/*      */       }
/*  228 */       out.write("\n<html>\n<head>\n<title>");
/*  229 */       out.print(FormatUtil.getString("am.webclient.MGAM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  230 */       out.print(addtotitle);
/*  231 */       out.write(32);
/*  232 */       out.write(45);
/*  233 */       out.write(32);
/*  234 */       if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*      */         return;
/*  236 */       out.write("</title>\n");
/*  237 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  238 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  240 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  241 */       out.write(10);
/*  242 */       out.write(10);
/*  243 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  244 */       out.write(10);
/*  245 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  246 */       out.print(request.getContextPath());
/*  247 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  248 */       out.print(request.getContextPath());
/*  249 */       out.write("'); //No I18N\n</script>\n");
/*  250 */       if (Constants.isIt360) {
/*  251 */         out.write("<script src='");
/*  252 */         out.print(request.getContextPath());
/*  253 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */       }
/*  255 */       out.write(10);
/*  256 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  258 */       out.write("\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<link href=\"/images/");
/*  259 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */         return;
/*  261 */       out.write("/wta.css\" rel=\"stylesheet\" type=\"text/css\">\n\n<script>\n\nfunction logout()\n{\n\tlocation.href=\"/Logout.do\";\n}\n\n function refer(str)\n {\n \tlocation.href=str\n }\n\nfunction fnOpenNewWindow(url)\n{\n\tfnOpenNewWindowWithHeightWidth(url,'720','460');\n}\n\nfunction fnOpenNewWindowWithHeightWidth(url,wwidth,hheight)\n{\n\n\tvar d=new Date();\n\tvar window2=window.open(url+'&sid='+d,'secondWindow','resizable=yes,scrollbars=yes,width='+wwidth+',height='+hheight);\n\twindow2.focus();\n}\n\nfunction fnOpenWindow(url)\n{\n  var win=window.open(url,'','resizable=no,scrollbars=yes,width=760,height=420');\n  win.focus();\n}\nfunction showAlarms(resourceid)\n{\n\tvar d=new Date();\n\tMM_openBrWindow('/RecentAlarms.do?method=getAlarmsForResource&resourceid='+resourceid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\n}\n\nfunction doInitStuffOnBodyLoad() {\n\n    setFocusProperTextField();\n    if (window.myOnLoad)\n    {\n\tmyOnLoad();// any JSP can implement the\tmethod called myOnLoad() and it will get called dynamically :-)\n    }\n\n\n}\n\nfunction setFocusProperTextField() {\n");
/*  262 */       out.write("\n\n    var pos = document.forms.length;\n    if(pos > 0) {\n\n          if(document.forms.length >=2) {\n              pos = 1; // assuming 0 has search, hence 2rd might be the relevant one we need.\n          }\n          else\n          {\n          return\n          }\n\n\n            for(i=0;i<document.forms[pos].elements.length;i++) {\n\n                if(document.forms[pos].elements[i].type =='text') {\n\t\t\ttry\n\t\t\t{\n\t\t\t\tdocument.forms[pos].elements[i].focus();\n\t\t\t\tbreak;\n\t\t\t}\n\t\t\tcatch (e) {}\n\n                }\n            }\n    \t}\n}\n\n    function toggleWTADiv(id)\n    {\n        var mon = document.getElementById(\"monitorinformation\");\n        var ele = document.getElementById(id);\n        if(ele.style.display == \"block\")\n        {\n            ele.style.display = \"none\";\n            mon.style.display = \"block\";\n        }\n        else {\n            ele.style.display = \"block\";\n            mon.style.display = \"none\";\n        }\n    }\n\n\nfunction submitForm()\n{\n\t");
/*  263 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  265 */       out.write(10);
/*  266 */       out.write(9);
/*  267 */       if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */         return;
/*  269 */       out.write("\n}\n</script>\n\n<script type=\"text/javascript\" src=\"/template/wta.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n</head>\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"doInitStuffOnBodyLoad();\">\n\n");
/*      */       
/*  271 */       String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  272 */       isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*  273 */       request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */       
/*  275 */       out.write(10);
/*      */       
/*  277 */       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  278 */       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  279 */       _jspx_th_c_005fif_005f1.setParent(null);
/*      */       
/*  281 */       _jspx_th_c_005fif_005f1.setTest("${selectedscheme == 'slim'}");
/*  282 */       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  283 */       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */         for (;;) {
/*  285 */           out.write(10);
/*      */           
/*  287 */           request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */           
/*  289 */           out.write(10);
/*  290 */           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  291 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  295 */       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  296 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */       }
/*      */       else {
/*  299 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  300 */         out.write(10);
/*      */         
/*  302 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  303 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  304 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  305 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  306 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  308 */             out.write("\n    ");
/*      */             
/*  310 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  311 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  312 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  314 */             _jspx_th_c_005fwhen_005f0.setTest("${PRINTER_FRIENDLY == \"true\"}");
/*  315 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  316 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  318 */                 out.write(10);
/*  319 */                 out.write(9);
/*  320 */                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*  321 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  322 */                 out.write(10);
/*  323 */                 out.write(10);
/*  324 */                 out.write(10);
/*  325 */                 out.write(32);
/*      */                 
/*  327 */                 String helpKey = (String)request.getAttribute("HelpKey");
/*  328 */                 if (helpKey == null)
/*      */                 {
/*  330 */                   String tileName = request.getParameter("TileName");
/*  331 */                   if (tileName != null)
/*      */                   {
/*  333 */                     if (tileName.equals(".ThresholdConf"))
/*      */                     {
/*  335 */                       helpKey = "New Threshold Profile";
/*      */                     }
/*  337 */                     else if (tileName.equals(".EmailActions"))
/*      */                     {
/*  339 */                       helpKey = "Send E-mail";
/*      */                     }
/*  341 */                     else if (tileName.equals(".SMSActions"))
/*      */                     {
/*  343 */                       helpKey = "Send SMS";
/*      */                     }
/*  345 */                     else if (tileName.equals(".ExecProg"))
/*      */                     {
/*  347 */                       helpKey = "Execute Program";
/*      */                     }
/*  349 */                     else if (tileName.equals(".SendTrap"))
/*      */                     {
/*  351 */                       helpKey = "Send Trap";
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  356 */                 out.write("\n\n\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction getNewWindow(url, title, width, height, params) {\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\nfunction openPrintWindow(title, width, height, params) {\n    var url = window.location.href;\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n        var newwindow = getNewWindow('#',title,width,height,params);\n        document.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n        document.forms[1].submit();\n        document.forms[1].target='_top';\n");
/*  357 */                 out.write("        document.forms[1].PRINTER_FRIENDLY.value='false';\n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */                 
/*  359 */                 Enumeration en = request.getParameterNames();
/*  360 */                 out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/*  361 */                 while (en.hasMoreElements()) {
/*  362 */                   String reqKey = (String)en.nextElement();
/*  363 */                   String reqValue = request.getParameter(reqKey);
/*  364 */                   if (!reqKey.equals("message"))
/*      */                   {
/*      */ 
/*      */ 
/*  368 */                     if (reqKey.equals("depDeviceId"))
/*      */                     {
/*  370 */                       out.print("&" + reqKey + "=" + URLEncoder.encode(reqValue));
/*      */                     }
/*  372 */                     else if (reqKey.equals("selectedMonitors"))
/*      */                     {
/*  374 */                       out.print("&" + reqKey + "=" + URLEncoder.encode(reqValue));
/*      */                     }
/*      */                     else
/*      */                     {
/*  378 */                       out.print("&" + reqKey + "=" + reqValue);
/*      */                     }
/*      */                   }
/*      */                 }
/*  382 */                 out.println("\";");
/*      */                 
/*  384 */                 out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n}\n\n\n  \n</script>\n\n\n<div id=\"dashboardsdiv\" style=\"display:none\"></div>\n");
/*      */                 
/*  386 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  387 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  388 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  390 */                 _jspx_th_c_005fif_005f2.setTest("${selectedscheme == 'slim'}");
/*  391 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  392 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  394 */                     out.write(10);
/*  395 */                     out.write(10);
/*      */                     
/*  397 */                     if ((request.isUserInRole("MANAGER")) || ((request.getAttribute("Layout") != null) && (request.getAttribute("Layout").equals("slim"))))
/*      */                     {
/*  399 */                       request.setAttribute("slatab", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "sla"));
/*      */                       
/*  401 */                       out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n    <td width=\"30%\" class=\"slimheader\"><img src=\"/images/");
/*  402 */                       out.print(OEMUtil.getOEMString("am.header.logo"));
/*  403 */                       out.write("\" hspace=\"0\" vspace=\"0\"></td>\n");
/*  404 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  406 */                       out.write("\n      \n<!--\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"staticlinks\">");
/*  407 */                       out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/*  408 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\" nowrap  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"staticlinks\">");
/*  409 */                       out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/*  410 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"staticlinks\">");
/*  411 */                       out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/*  412 */                       out.write("</a></td>\n      <td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"staticlinks\">");
/*  413 */                       out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/*  414 */                       out.write("</a></td>\n    -->\n    <td align=\"right\" width=\"25%\" class=\"slimheader\" >");
/*  415 */                       out.print(getHelpLink(helpKey));
/*  416 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  417 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  418 */                         out.write("<td align=\"right\" width=\"5%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  419 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  420 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  422 */                       out.write("\n     <td width=\"12%\" class=\"slimheader\" nowrap><a href=\"/Logout.do\" class=\"footer\">");
/*  423 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  424 */                       out.write("</a>");
/*  425 */                       if (request.getRemoteUser() != null)
/*  426 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() })); else
/*  427 */                         out.println("&nbsp;");
/*  428 */                       out.write("\n      </td>\n  </tr>\n  </table>\n");
/*      */                     }
/*  430 */                     out.write(32);
/*  431 */                     out.write(32);
/*  432 */                     out.write(10);
/*      */                     
/*  434 */                     if ((!request.isUserInRole("MANAGER")) && (request.getAttribute("Layout") == null))
/*      */                     {
/*  436 */                       request.setAttribute("taborder", com.adventnet.appmanager.personalize.AMPersonalize.getOrderedTabIdList(request, "admin"));
/*      */                       
/*  438 */                       out.write(" \t\n\t\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr >\n  <td width=\"30%\" class=\"slimheader\"><img src=\"/images/am_logo.png\" hspace=\"0\" vspace=\"0\"></td>");
/*  439 */                       out.write(10);
/*  440 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                         return;
/*  442 */                       out.write("\n\n  <!--\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/MyPage.do?method=viewDashBoard\" class=\"staticlinks\">");
/*  443 */                       out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  444 */                       out.write("</a></td>\n  ");
/*      */                       
/*  446 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  447 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/*  448 */                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  450 */                       _jspx_th_logic_005fpresent_005f4.setRole("OPERATOR");
/*  451 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/*  452 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/*  454 */                           out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/*  455 */                           if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                             return;
/*  457 */                           out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  458 */                           out.write("\n  </a></td>\n  ");
/*  459 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/*  460 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  464 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/*  465 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/*  468 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*  469 */                       out.write(10);
/*  470 */                       out.write(32);
/*  471 */                       out.write(32);
/*      */                       
/*  473 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  474 */                       _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  475 */                       _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  477 */                       _jspx_th_logic_005fnotPresent_005f2.setRole("OPERATOR");
/*  478 */                       int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  479 */                       if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                         for (;;) {
/*  481 */                           out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/*      */                           
/*  483 */                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  484 */                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  485 */                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*  486 */                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  487 */                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                             for (;;) {
/*  489 */                               out.write("\n   \t\t ");
/*      */                               
/*  491 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  492 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  493 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*  495 */                               _jspx_th_c_005fwhen_005f8.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/*  496 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  497 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/*  499 */                                   out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/*  500 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                                     return;
/*  502 */                                   out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/*  503 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  504 */                                   out.write("</a>\n   \t\t ");
/*  505 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  506 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  510 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  511 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/*  514 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  515 */                               out.write("\n   \t \t ");
/*      */                               
/*  517 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  518 */                               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  519 */                               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/*  520 */                               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  521 */                               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                 for (;;) {
/*  523 */                                   out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/*  524 */                                   if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/*  526 */                                   out.write("\" class=\"staticlinks\">");
/*  527 */                                   out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  528 */                                   out.write("</a>\n   \t\t ");
/*  529 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  530 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  534 */                               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  535 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                               }
/*      */                               
/*  538 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  539 */                               out.write("\n   \t");
/*  540 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  541 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  545 */                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  546 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                           }
/*      */                           
/*  549 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  550 */                           out.write("\n\t</td> \n  ");
/*  551 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  552 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  556 */                       if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  557 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                       }
/*      */                       
/*  560 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  561 */                       out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\" class=\"staticlinks\">");
/*  562 */                       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  563 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showReports.do?actionMethod=getReportIndex\" class=\"staticlinks\">");
/*  564 */                       out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  565 */                       out.write("</a></td>\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/common/serverinfo.do\" class=\"staticlinks\">");
/*  566 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  567 */                       out.write("</a></td>\n\t");
/*      */                       
/*  569 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  570 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/*  571 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  573 */                       _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/*  574 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/*  575 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/*  577 */                           out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/*  578 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  579 */                           out.write("</a></td>\n\t");
/*  580 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/*  581 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  585 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/*  586 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/*  589 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*  590 */                       out.write(10);
/*  591 */                       out.write(9);
/*      */                       
/*  593 */                       PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  594 */                       _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/*  595 */                       _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f2);
/*      */                       
/*  597 */                       _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN");
/*  598 */                       int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/*  599 */                       if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                         for (;;) {
/*  601 */                           out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/*  602 */                           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  603 */                           out.write("</a></td>\n\t");
/*  604 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/*  605 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  609 */                       if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/*  610 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                       }
/*      */                       
/*  613 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*  614 */                       out.write("\n\t-->\n    \n    \n    <td align=\"right\" width=\"15%\" class=\"slimheader\" >");
/*  615 */                       out.print(getHelpLink(helpKey));
/*  616 */                       out.write("</td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>\n    ");
/*  617 */                       if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  618 */                         out.write("<td align=\"right\" width=\"6%\" class=\"slimheader\" nowrap><a style=\"cursor: pointer\" onclick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/*  619 */                         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/*  620 */                         out.write("</a></td><td width=\"1%\" align=\"center\" class=\"slimheader\">|</td>");
/*      */                       }
/*  622 */                       out.write("\n    <td class=\"slimheader\" width=\"20%\" nowrap><a href=\"/Logout.do\"  class=\"footer\">");
/*  623 */                       out.print(FormatUtil.getString("am.webclient.logoutlink.text"));
/*  624 */                       out.write("</a>");
/*  625 */                       if (request.getRemoteUser() != null)
/*      */                       {
/*  627 */                         out.write("&nbsp;");
/*  628 */                         out.print(FormatUtil.getString("am.webclient.welcomeuser.text", new String[] { request.getRemoteUser() }));
/*  629 */                         out.write("\n    \t\t\t\t\t");
/*  630 */                       } else { out.println("&nbsp;"); }
/*  631 */                       out.write("\n    </td>\n  </tr>\n  </table>\n ");
/*      */                     }
/*  633 */                     out.write(10);
/*  634 */                     out.write(32);
/*  635 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  636 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  640 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  641 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  644 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  645 */                 out.write(" \n\n\t");
/*  646 */                 if (((OEMUtil.getOEMString("product.name") == null) || (!OEMUtil.getOEMString("product.name").equals("IT360"))) && (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()))
/*      */                 {
/*  648 */                   out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"3\" ><img src=\"../images/spacer.gif\" height=\"10\" width=\"5\"></td>\n\t\t</tr>\n\t");
/*      */                 }
/*  650 */                 out.write("\n\n</table>\n");
/*  651 */                 out.write("\n    <br>\n        <table width=\"100%\" border=\"00\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n\n    <td valign=\"top\">\n\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n\n       ");
/*      */                 
/*      */ 
/*      */ 
/*  655 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/*  657 */                   String messagetosay = "";
/*  658 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/*  660 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  661 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/*  663 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/*  665 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/*  667 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/*  670 */                     out.write("\n                        <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                          <td width=\"95%\" class=\"message\">");
/*  671 */                     out.print(messagetosay);
/*  672 */                     out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n                <tr>\n                <td>&nbsp;\n                </td>\n                </tr>\n\n                    ");
/*      */                   }
/*      */                   
/*      */ 
/*  676 */                   out.write("\n\t\t\t<tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t  <td width=\"95%\" class=\"message\">\n\n\t\t\t");
/*      */                   
/*  678 */                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  679 */                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/*  680 */                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  682 */                   _jspx_th_logic_005fpresent_005f7.setRole("ENTERPRISEADMIN,USERS");
/*  683 */                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/*  684 */                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                     for (;;) {
/*  686 */                       out.write("\n                        <div>");
/*  687 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/*  688 */                       out.write("</div>\n                        ");
/*  689 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/*  690 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  694 */                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/*  695 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                   }
/*      */                   
/*  698 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*  699 */                   out.write("\n                        ");
/*      */                   
/*  701 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  702 */                   _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  703 */                   _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  705 */                   _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN,USERS");
/*  706 */                   int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  707 */                   if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                     for (;;) {
/*  709 */                       out.write("\n                        <div>");
/*  710 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/*  711 */                       out.write("</div>\n                        ");
/*  712 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  713 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  717 */                   if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  718 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                   }
/*      */                   
/*  721 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*  722 */                   out.write("</div></td>\n\n\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                 }
/*  725 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/*  728 */                   out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t  <td width=\"95%\" class=\"message\">");
/*  729 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/*  730 */                   out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*  736 */                   out.write("\n        ");
/*      */                   
/*  738 */                   MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  739 */                   _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  740 */                   _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  742 */                   _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                   
/*  744 */                   _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  745 */                   int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  746 */                   if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  747 */                     String msg = null;
/*  748 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  749 */                       out = _jspx_page_context.pushBody();
/*  750 */                       _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  751 */                       _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                     }
/*  753 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/*  755 */                       out.write("\n        <tr>\n          <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" height=\"28\" class=\"message\">");
/*  756 */                       if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                         return;
/*  758 */                       out.write("</td>\n              </tr>\n            </table>\n            </td>\n        </tr>\n        ");
/*  759 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  760 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*  761 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  764 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  765 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  768 */                   if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  769 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                   }
/*      */                   
/*  772 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  773 */                   out.write(32);
/*      */                   
/*  775 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  776 */                   _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  777 */                   _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  779 */                   _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  780 */                   int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  781 */                   if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                     for (;;) {
/*  783 */                       out.write("\n        <tr>\n          <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                       
/*  785 */                       MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  786 */                       _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  787 */                       _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                       
/*  789 */                       _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                       
/*  791 */                       _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  792 */                       int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  793 */                       if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  794 */                         String msg = null;
/*  795 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  796 */                           out = _jspx_page_context.pushBody();
/*  797 */                           _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  798 */                           _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                         }
/*  800 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/*  802 */                           out.write("\n                  ");
/*  803 */                           if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                             return;
/*  805 */                           out.write("<br>\n                  ");
/*  806 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  807 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/*  808 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  811 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  812 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  815 */                       if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  816 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                       }
/*      */                       
/*  819 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  820 */                       out.write(" </td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*  821 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  822 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  826 */                   if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  827 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                   }
/*      */                   
/*  830 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  831 */                   out.write("\n        ");
/*      */                 }
/*  833 */                 out.write("\n        <tr>\n          <td height=\"20%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/*  834 */                 if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  836 */                 out.write("</td>\n        </tr>\n        <tr>\n          <td height=\"70%\" colspan=\"2\" valign=\"top\" class=\"tdindent\">");
/*  837 */                 if (_jspx_meth_tiles_005finsert_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  839 */                 out.write("</td>\n        </tr>\n        <tr>\n          <td height=\"10%\" colspan=\"2\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n\t<tr>\n\t<td colspan=\"2\" class=\"tdindent\">\n\t");
/*  840 */                 out.write("<!-- $Id$-->\n\n");
/*  841 */                 out.write("\n<script language=\"JavaScript1.2\">\nfunction firstCheck()\n{\nvar message='Thank you for your feedback';\n\tif(document.forms[\"inlinefeedback\"].Description.value.trim() == '')\n\t{\n\t\tmessage=\"Please provide your feedback\";\n\t\talert('");
/*  842 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.alert.message"));
/*  843 */                 out.write("');\n\t\treturn false;\n\t}\nsendFeedback('");
/*  844 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.thankyou"));
/*  845 */                 out.write("');\n}\n</script>\n");
/*      */                 
/*  847 */                 String uri1 = (String)request.getAttribute("uri");
/*  848 */                 String qs1 = (String)request.getAttribute("qs");
/*  849 */                 String context1 = uri1 + "?" + qs1;
/*      */                 
/*  851 */                 String display = "none";
/*  852 */                 Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/*  853 */                 Object showFeedback = globals.get("showFeedback");
/*  854 */                 if ((Constants.getGlobalObject("SMTP") != null) && ((showFeedback == null) || (((String)showFeedback).equals("true"))) && (!OEMUtil.isOEM()))
/*      */                 {
/*  856 */                   display = "block";
/*      */                 }
/*  858 */                 if ((request.getParameter("hideArea") != null) && (request.getParameter("hideArea").equals("true")))
/*      */                 {
/*  860 */                   display = "none";
/*      */                 }
/*      */                 
/*      */ 
/*  864 */                 out.write("\n\n\n<div id=\"feedback\" style=\"display:");
/*  865 */                 out.print(display);
/*  866 */                 out.write("\">\n\n<form name=\"inlinefeedback\" method=\"post\" action=\"\">\n<input type=\"hidden\" name=\"To\" value=\"");
/*  867 */                 out.print(OEMUtil.getOEMString("product.talkback.mailid"));
/*  868 */                 out.write("\">\n<input type=\"hidden\" name=\"Subject\" value=\"Inline Feedback - ");
/*  869 */                 out.print(OEMUtil.getOEMString("product.name"));
/*  870 */                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"submit\">\n<input type=\"hidden\" name=\"context\" value=\"");
/*  871 */                 out.print(URLEncoder.encode(context1));
/*  872 */                 out.write("\">\n    <div id=\"result\" style=\"display:none;\">\n            <table border=\"0\" width=\"100%\">\n              <tr align=\"center\">\n                <td class=\"bodytextbold\"><span id=\"result1\" style=\"font-weight:bold;color:red;font-size:11px\"></span></td>\n              </tr>\n            </table>\n          </div>\n\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmHdrTopLeft\"/>\n\t<td class=\"AlarmHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t<tr>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\"><span class=\"AlarmHdrTxt\"><b style=\"float:left; margin-bottom:3px;\">");
/*  873 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.message"));
/*  874 */                 out.write(" </b><a href=\"javascript:hideFeedback()\"><img border=\"0\" src=\"/images/cross.gif\" title=\"");
/*  875 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.delete.tooltip"));
/*  876 */                 out.write("\"></a></span></td>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\"></td>\n\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t</tr>\n\t</table></td>\n\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t<td valign=\"top\">\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td >\n\t<!--text -->\n\t</td>\n\t</tr>\n\t<tr>\n\t<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmInnerTopLeft\"/>\n\t<td class=\"AlarmInnerTopBg\"/>\n\t<td class=\"AlarmInnerTopRight\"/>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t<td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\n\t<tr class=\"bodytext\">\n\t<td rowspan=\"3\" align=\"left\" valign=\"top\"><textarea class=\"formtextarea\" name=\"Description\" rows=\"5\" cols=\"55\"></textarea>\n");
/*  877 */                 out.write("\t</td>\n\t<td>");
/*  878 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.name"));
/*  879 */                 out.write("</td>\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Name\"></td>\n\t<td>&nbsp;</td>\n\t<td>&nbsp;</td>\n\t<td height=\"10\" align=\"right\"></td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td>");
/*  880 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.email"));
/*  881 */                 out.write("</td>\n\t");
/*      */                 
/*  883 */                 String emailAddress = Constants.EMAIL_ADDRESS;
/*      */                 
/*  885 */                 out.write("\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Email\" value=\"");
/*  886 */                 out.print(emailAddress);
/*  887 */                 out.write("\"></td>\n\t<td>&nbsp;&nbsp;&nbsp; </td>\n\t<td>&nbsp;</td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td height=\"21\">&nbsp; </td>\n\t<td colspan=\"3\" class=\"arial9\"><input name=\"button\" type=\"button\" class=\"buttons\" onClick=\"firstCheck()\" value=\"");
/*  888 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.send"));
/*  889 */                 out.write("\">\n\t");
/*  890 */                 out.print(FormatUtil.getString("am.webclient.inlinefeedback.infomessage"));
/*  891 */                 out.write("</td>\n\t</tr>\n\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n\n\t</table>\n\n\t</td>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t</tr>\n\n\t</table></td>\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t</td>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t<td class=\"AlarmCardMainBtmBg\"/>\n\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t</tr>\n\t</table>\n\n\n\n</form>\n</div>\n");
/*  892 */                 out.write("\n\t</td>\n\t</tr>\n        <tr>\n\t\t");
/*      */                 
/*      */ 
/*  895 */                 request.setAttribute("systime", new java.util.Date());
/*      */                 try
/*      */                 {
/*  898 */                   long starttime = ((Long)request.getAttribute("starttime")).longValue();
/*  899 */                   long pageloadtime = System.currentTimeMillis() - starttime;
/*  900 */                   if (pageloadtime > 60000L)
/*      */                   {
/*  902 */                     String logmsg = "PAGELOADTIME for " + uri + " " + request.getQueryString() + " is " + pageloadtime;
/*  903 */                     Constants.logSlowPage(logmsg);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception exc) {}
/*  907 */                 out.write("\n\n          <td width=\"57%\" height=\"20%\" class=\"tdindent\"><span class=\"footer\">");
/*  908 */                 out.print(responseTimeMessage);
/*  909 */                 out.write(" <span class=\"bodytextbold\">");
/*  910 */                 if (_jspx_meth_am_005ftimestamp_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  912 */                 out.write("</span>\n         ");
/*      */                 
/*  914 */                 MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  915 */                 _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/*  916 */                 _jspx_th_fmt_005fmessage_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*  917 */                 int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/*  918 */                 if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/*  919 */                   if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  920 */                     out = _jspx_page_context.pushBody();
/*  921 */                     _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/*  922 */                     _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  925 */                     out.print(FormatUtil.getString("milliseconds"));
/*  926 */                     int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/*  927 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  930 */                   if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  931 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  934 */                 if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/*  935 */                   this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0); return;
/*      */                 }
/*      */                 
/*  938 */                 this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  939 */                 out.write("</span></td>\n          <td width=\"43%\" class=\"footer\" align=\"right\">");
/*  940 */                 out.print(systemTimeMessage);
/*  941 */                 out.write(32);
/*  942 */                 out.write(58);
/*  943 */                 out.write(32);
/*  944 */                 if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  946 */                 out.write("&nbsp;&nbsp;</td>\n        </tr>\n      </table></td>\n\n    </tr>\n </table>\n\n    ");
/*  947 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  948 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  952 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  953 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  956 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  957 */             out.write(10);
/*  958 */             out.write(32);
/*  959 */             out.write(32);
/*      */             
/*  961 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  962 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  963 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f0);
/*  964 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  965 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  967 */                 out.write(10);
/*  968 */                 out.write(10);
/*  969 */                 if (_jspx_meth_tiles_005finsert_005f2(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/*  971 */                 out.write("\n<!--table width=\"100%\" border=\"00\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td width=\"16%\" height=\"8\" class=\"leftcells\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n    <td width=\"84%\" height=\"8\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\n  </tr>\n</table-->\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <!--tr>\n    <td width=\"16%\" height=\"8\" class=\"leftcells\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n    <td width=\"84%\" height=\"8\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"5\"></td>\n  </tr-->\n\n  <tr>\n    ");
/*  972 */                 if (!OEMUtil.isRemove("hide.leftArea")) {
/*  973 */                   out.write("\n    <td id=\"leftpane\" width=\"16%\" height=\"505\"  align=\"center\" valign=\"top\" class=\"leftcells\">\n\n\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n              <td colspan=\"2\" >\n\n\t\t</td>\n\t</tr>\n    </table>\n      ");
/*  974 */                   if (_jspx_meth_tiles_005finsert_005f3(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/*  976 */                   out.write("<br>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"leftmnutables\">\n    \t<tr>\n    \t\t<td width=\"80%\" class=\"leftlinksquicknote\">");
/*  977 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/*  978 */                   out.write("</td>\n    \t\t<td width=\"20%\" class=\"leftlinksheading\">\n    \t\t\t<img src=\"/images/");
/*  979 */                   if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/*  981 */                   out.write("/img_quicknote.gif\" hspace=\"5\">\n    \t\t</td>\n  \t</tr>\n\t<tr>\n    \t\t<td colspan=\"2\"  class=\"quicknote\">\n      \t\t\t");
/*  982 */                   out.print(FormatUtil.getString("am.webclient.wta.quicknote"));
/*  983 */                   out.write("\n      \t\t</td>\n      \t</tr>\n    </table>\n\n    </td>\n<!--td style=\"background:blue;\"><br></td-->\n<td onMouseOver=\"this.style.cursor='pointer'\" valign=\"top\" onClick=\"showHideLeftPane()\" class=\"showhidepane\" style=\"padding-top:200px;\">\n<div id=\"img-divider\" class=\"hideimage\">&nbsp;</div>\n</td>\n\n");
/*      */                 }
/*  985 */                 out.write("\n    <td id=\"rightpane\" valign=\"top\" width=\"100%\"><br>\n\t  <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr>\n      <td colspan=\"2\">\n        <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n\t    ");
/*      */                 
/*  987 */                 Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */                 
/*  989 */                 String haid = request.getParameter("haid");
/*  990 */                 String haName = null;
/*  991 */                 if (haid != null)
/*      */                 {
/*  993 */                   haName = (String)ht.get(haid);
/*      */                 }
/*      */                 
/*  996 */                 out.write("\n            ");
/*      */                 
/*      */ 
/*  999 */                 String addOnMsg = "";
/* 1000 */                 FreeEditionDetails fed = FreeEditionDetails.getFreeEditionDetails();
/* 1001 */                 if ((!fed.isWebTransaction()) || (
/*      */                 
/*      */ 
/* 1004 */                   (FreeEditionDetails.getAddOnEvalDaysProps() != null) && (FreeEditionDetails.getAddOnEvalDaysProps().getProperty("J2EEWebTransaction") != null))) {
/* 1005 */                   String nDays = FreeEditionDetails.getAddOnEvalDaysProps().getProperty("J2EEWebTransaction");
/* 1006 */                   addOnMsg = FormatUtil.getString("am.addon.remainingdays.msg", new String[] { "J2EEWebTransaction", nDays });
/*      */                 }
/*      */                 
/* 1009 */                 if (!addOnMsg.equals(""))
/*      */                 {
/*      */ 
/* 1012 */                   out.write("\n                 <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                          <td width=\"95%\" class=\"message\">");
/* 1013 */                   out.print(addOnMsg);
/* 1014 */                   out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n                 ");
/*      */                 }
/*      */                 
/* 1017 */                 out.write("\n\t    ");
/*      */                 
/* 1019 */                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1020 */                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1021 */                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/* 1023 */                 _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (empty invalidhaid) && param.haid!='null'}");
/* 1024 */                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1025 */                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                   for (;;) {
/* 1027 */                     out.write("\n      \t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 1028 */                     out.print(BreadcrumbUtil.getHomePage(request));
/* 1029 */                     out.write(" &gt; ");
/* 1030 */                     out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 1031 */                     out.write(" &gt; <span class=\"bcactive\">");
/* 1032 */                     if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                       return;
/* 1034 */                     out.write(" </span></td>\n\t    ");
/* 1035 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1036 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1040 */                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1041 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                 }
/*      */                 
/* 1044 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1045 */                 out.write("\n\n\t    ");
/*      */                 
/* 1047 */                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1048 */                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1049 */                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                 
/* 1051 */                 _jspx_th_c_005fif_005f4.setTest("${empty param.haid || (!empty invalidhaid) || param.haid=='null'}");
/* 1052 */                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1053 */                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                   for (;;) {
/* 1055 */                     out.write("\n            <td class=\"bcsign\"  height=\"22\" valign=\"top\">&nbsp;");
/* 1056 */                     out.print(BreadcrumbUtil.getMonitorsPage());
/* 1057 */                     out.write(" &gt; ");
/* 1058 */                     out.print(BreadcrumbUtil.getMonitorResourceTypes("WTA"));
/* 1059 */                     out.write(" &gt; <span class=\"bcactive\">");
/* 1060 */                     if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                       return;
/* 1062 */                     out.write("</span></td>\n\t    ");
/* 1063 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1064 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1068 */                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1069 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                 }
/*      */                 
/* 1072 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1073 */                 out.write("\n        </tr>\n\t    <tr>\n\t\t    <td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t    </tr>\n\t    <tr>\n\t\t    <td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t    </tr>\n        </table>\n      </td>\n      </tr>\n      <tr>\n      <td width=\"80%\" style=\"vertical-align:top\">\n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n\n        ");
/*      */                 
/*      */ 
/* 1076 */                 if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid))))
/*      */                 {
/* 1078 */                   String messagetosay = "";
/* 1079 */                   if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("managemonitors")))
/*      */                   {
/* 1081 */                     messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/* 1082 */                     if (request.getAttribute("OperatorNotAllowed") != null)
/*      */                     {
/* 1084 */                       messagetosay = (String)request.getAttribute("OperatorNotAllowed");
/*      */                     }
/* 1086 */                     if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */                     {
/* 1088 */                       messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */                     }
/*      */                     
/* 1091 */                     out.write("\n                        <tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                          <td width=\"95%\" class=\"message\">");
/* 1092 */                     out.print(messagetosay);
/* 1093 */                     out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n                <tr>\n                <td>&nbsp;\n                </td>\n                </tr>\n\n                    ");
/*      */                   }
/*      */                   
/*      */ 
/* 1097 */                   out.write("\n\t\t\t<tr>\n\t\t  \t<td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\n\t\t\t<td width=\"95%\" class=\"message\">\n\t\t\t");
/*      */                   
/* 1099 */                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1100 */                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 1101 */                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1103 */                   _jspx_th_logic_005fpresent_005f8.setRole("ENTERPRISEADMIN,USERS");
/* 1104 */                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 1105 */                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                     for (;;) {
/* 1107 */                       out.write("\n                        ");
/* 1108 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.admin.text"));
/* 1109 */                       out.write("\n                        ");
/* 1110 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 1111 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1115 */                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 1116 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                   }
/*      */                   
/* 1119 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 1120 */                   out.write("\n                        ");
/*      */                   
/* 1122 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1123 */                   _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 1124 */                   _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1126 */                   _jspx_th_logic_005fnotPresent_005f4.setRole("ENTERPRISEADMIN,USERS");
/* 1127 */                   int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 1128 */                   if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                     for (;;) {
/* 1130 */                       out.write("\n                        ");
/* 1131 */                       out.print(FormatUtil.getString("am.webclient.unmanaged.message.text"));
/* 1132 */                       out.write("\n                        ");
/* 1133 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1134 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1138 */                   if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1139 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                   }
/*      */                   
/* 1142 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1143 */                   out.write("</td>\n\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */ 
/*      */                 }
/* 1146 */                 else if ((resourceid != null) && (!resourceid.equals("resourceid")) && (DataCollectionControllerUtil.underMaintenance(resourceid)))
/*      */                 {
/*      */ 
/* 1149 */                   out.write("\n\t\t<tr>\n\t\t  <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t  \t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t<tr>\n\t\t\t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t  <td width=\"95%\" class=\"message\">");
/* 1150 */                   out.print(DataCollectionControllerUtil.getMaintenanceMessage(resourceid));
/* 1151 */                   out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n        </tr>\n\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 1157 */                   out.write("\n        ");
/*      */                   
/* 1159 */                   MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1160 */                   _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1161 */                   _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1163 */                   _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                   
/* 1165 */                   _jspx_th_html_005fmessages_005f2.setMessage("false");
/* 1166 */                   int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1167 */                   if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1168 */                     String msg = null;
/* 1169 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1170 */                       out = _jspx_page_context.pushBody();
/* 1171 */                       _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1172 */                       _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                     }
/* 1174 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1176 */                       out.write("\n        ");
/* 1177 */                       if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/* 1179 */                       out.write("\n\n              ");
/* 1180 */                       if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                         return;
/* 1182 */                       out.write("<br>\n\n        ");
/* 1183 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1184 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1185 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1188 */                     if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1189 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1192 */                   if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1193 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                   }
/*      */                   
/* 1196 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1197 */                   out.write("\n        ");
/* 1198 */                   if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/* 1200 */                   out.write("\n        ");
/*      */                   
/* 1202 */                   MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1203 */                   _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1204 */                   _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                   
/* 1206 */                   _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1207 */                   int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1208 */                   if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 1210 */                       out.write("\n        <tr>\n        <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n        <tr>\n        <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n        <td width=\"95%\" class=\"message\"> ");
/*      */                       
/* 1212 */                       MessagesTag _jspx_th_html_005fmessages_005f3 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1213 */                       _jspx_th_html_005fmessages_005f3.setPageContext(_jspx_page_context);
/* 1214 */                       _jspx_th_html_005fmessages_005f3.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                       
/* 1216 */                       _jspx_th_html_005fmessages_005f3.setId("msg");
/*      */                       
/* 1218 */                       _jspx_th_html_005fmessages_005f3.setMessage("true");
/* 1219 */                       int _jspx_eval_html_005fmessages_005f3 = _jspx_th_html_005fmessages_005f3.doStartTag();
/* 1220 */                       if (_jspx_eval_html_005fmessages_005f3 != 0) {
/* 1221 */                         String msg = null;
/* 1222 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1223 */                           out = _jspx_page_context.pushBody();
/* 1224 */                           _jspx_th_html_005fmessages_005f3.setBodyContent((BodyContent)out);
/* 1225 */                           _jspx_th_html_005fmessages_005f3.doInitBody();
/*      */                         }
/* 1227 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/* 1229 */                           out.write("\n                  ");
/* 1230 */                           if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fmessages_005f3, _jspx_page_context))
/*      */                             return;
/* 1232 */                           out.write("<br>\n                  ");
/* 1233 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f3.doAfterBody();
/* 1234 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/* 1235 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1238 */                         if (_jspx_eval_html_005fmessages_005f3 != 1) {
/* 1239 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1242 */                       if (_jspx_th_html_005fmessages_005f3.doEndTag() == 5) {
/* 1243 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3); return;
/*      */                       }
/*      */                       
/* 1246 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f3);
/* 1247 */                       out.write(" </td>\n\n                  </tr>\n                  </table></td>\n                  </tr>\n        ");
/* 1248 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1249 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1253 */                   if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1254 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                   }
/*      */                   
/* 1257 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1258 */                   out.write("\n        ");
/*      */                 }
/* 1260 */                 out.write("\n        <tr>\n        <td align=\"center\">\n            ");
/* 1261 */                 if (_jspx_meth_tiles_005finsert_005f4(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1263 */                 out.write("\n        </td>\n        </tr>\n      </table>\n      </td>\n      <td  align=\"center\" valign=\"top\" class=\"leftcells\" width=\"20%\">");
/* 1264 */                 if (_jspx_meth_tiles_005finsert_005f5(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1266 */                 out.write("\n      </td>\n      </tr>\n      <tr>\n      <td  colspan=\"2\">\n        <table border=\"0\" width=\"100%\" cellspacing=\"0%\" cellpadding=\"0%\">\n        <tr>\n            <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n                 ");
/* 1267 */                 if (_jspx_meth_tiles_005finsert_005f6(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1269 */                 out.write("\n            </td>\n        </tr>\n        <tr>\n            <td height=\"20%\" colspan=\"2\" class=\"bodytext\">&nbsp;</td>\n        </tr>\n        ");
/* 1270 */                 if (OEMUtil.isRemove("hide.serverresp")) {
/* 1271 */                   out.write("\n\n\t<tr>\n\t<td colspan=\"2\" class=\"tdindent\">\n\t");
/* 1272 */                   out.write("<!-- $Id$-->\n\n");
/* 1273 */                   out.write("\n<script language=\"JavaScript1.2\">\nfunction firstCheck()\n{\nvar message='Thank you for your feedback';\n\tif(document.forms[\"inlinefeedback\"].Description.value.trim() == '')\n\t{\n\t\tmessage=\"Please provide your feedback\";\n\t\talert('");
/* 1274 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.alert.message"));
/* 1275 */                   out.write("');\n\t\treturn false;\n\t}\nsendFeedback('");
/* 1276 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.thankyou"));
/* 1277 */                   out.write("');\n}\n</script>\n");
/*      */                   
/* 1279 */                   String uri1 = (String)request.getAttribute("uri");
/* 1280 */                   String qs1 = (String)request.getAttribute("qs");
/* 1281 */                   String context1 = uri1 + "?" + qs1;
/*      */                   
/* 1283 */                   String display = "none";
/* 1284 */                   Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 1285 */                   Object showFeedback = globals.get("showFeedback");
/* 1286 */                   if ((Constants.getGlobalObject("SMTP") != null) && ((showFeedback == null) || (((String)showFeedback).equals("true"))) && (!OEMUtil.isOEM()))
/*      */                   {
/* 1288 */                     display = "block";
/*      */                   }
/* 1290 */                   if ((request.getParameter("hideArea") != null) && (request.getParameter("hideArea").equals("true")))
/*      */                   {
/* 1292 */                     display = "none";
/*      */                   }
/*      */                   
/*      */ 
/* 1296 */                   out.write("\n\n\n<div id=\"feedback\" style=\"display:");
/* 1297 */                   out.print(display);
/* 1298 */                   out.write("\">\n\n<form name=\"inlinefeedback\" method=\"post\" action=\"\">\n<input type=\"hidden\" name=\"To\" value=\"");
/* 1299 */                   out.print(OEMUtil.getOEMString("product.talkback.mailid"));
/* 1300 */                   out.write("\">\n<input type=\"hidden\" name=\"Subject\" value=\"Inline Feedback - ");
/* 1301 */                   out.print(OEMUtil.getOEMString("product.name"));
/* 1302 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"submit\">\n<input type=\"hidden\" name=\"context\" value=\"");
/* 1303 */                   out.print(URLEncoder.encode(context1));
/* 1304 */                   out.write("\">\n    <div id=\"result\" style=\"display:none;\">\n            <table border=\"0\" width=\"100%\">\n              <tr align=\"center\">\n                <td class=\"bodytextbold\"><span id=\"result1\" style=\"font-weight:bold;color:red;font-size:11px\"></span></td>\n              </tr>\n            </table>\n          </div>\n\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmHdrTopLeft\"/>\n\t<td class=\"AlarmHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t<tr>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\"><span class=\"AlarmHdrTxt\"><b style=\"float:left; margin-bottom:3px;\">");
/* 1305 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.message"));
/* 1306 */                   out.write(" </b><a href=\"javascript:hideFeedback()\"><img border=\"0\" src=\"/images/cross.gif\" title=\"");
/* 1307 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.delete.tooltip"));
/* 1308 */                   out.write("\"></a></span></td>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\"></td>\n\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t</tr>\n\t</table></td>\n\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t<td valign=\"top\">\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td >\n\t<!--text -->\n\t</td>\n\t</tr>\n\t<tr>\n\t<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmInnerTopLeft\"/>\n\t<td class=\"AlarmInnerTopBg\"/>\n\t<td class=\"AlarmInnerTopRight\"/>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t<td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\n\t<tr class=\"bodytext\">\n\t<td rowspan=\"3\" align=\"left\" valign=\"top\"><textarea class=\"formtextarea\" name=\"Description\" rows=\"5\" cols=\"55\"></textarea>\n");
/* 1309 */                   out.write("\t</td>\n\t<td>");
/* 1310 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.name"));
/* 1311 */                   out.write("</td>\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Name\"></td>\n\t<td>&nbsp;</td>\n\t<td>&nbsp;</td>\n\t<td height=\"10\" align=\"right\"></td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td>");
/* 1312 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.email"));
/* 1313 */                   out.write("</td>\n\t");
/*      */                   
/* 1315 */                   String emailAddress = Constants.EMAIL_ADDRESS;
/*      */                   
/* 1317 */                   out.write("\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Email\" value=\"");
/* 1318 */                   out.print(emailAddress);
/* 1319 */                   out.write("\"></td>\n\t<td>&nbsp;&nbsp;&nbsp; </td>\n\t<td>&nbsp;</td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td height=\"21\">&nbsp; </td>\n\t<td colspan=\"3\" class=\"arial9\"><input name=\"button\" type=\"button\" class=\"buttons\" onClick=\"firstCheck()\" value=\"");
/* 1320 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.send"));
/* 1321 */                   out.write("\">\n\t");
/* 1322 */                   out.print(FormatUtil.getString("am.webclient.inlinefeedback.infomessage"));
/* 1323 */                   out.write("</td>\n\t</tr>\n\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n\n\t</table>\n\n\t</td>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t</tr>\n\n\t</table></td>\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t</td>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t<td class=\"AlarmCardMainBtmBg\"/>\n\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t</tr>\n\t</table>\n\n\n\n</form>\n</div>\n");
/* 1324 */                   out.write("\n\t</td>\n\t</tr>\n        <tr>\n    \t\t");
/*      */                   
/* 1326 */                   request.setAttribute("systime", new java.util.Date());
/*      */                   try {
/* 1328 */                     long starttime = ((Long)request.getAttribute("starttime")).longValue();
/* 1329 */                     long pageloadtime = System.currentTimeMillis() - starttime;
/* 1330 */                     if (pageloadtime > 60000L)
/*      */                     {
/* 1332 */                       String logmsg = "PAGELOADTIME for " + uri + " " + request.getQueryString() + " is " + pageloadtime;
/* 1333 */                       Constants.logSlowPage(logmsg);
/*      */                     }
/*      */                   }
/*      */                   catch (Exception exc) {}
/* 1337 */                   out.write("\n\n            <td width=\"57%\" height=\"20%\" class=\"tdindent\">\n                <span class=\"footer\">");
/* 1338 */                   out.print(responseTimeMessage);
/* 1339 */                   out.write("\n                    <span class=\"bodytextbold\">");
/* 1340 */                   if (_jspx_meth_am_005ftimestamp_005f1(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/* 1342 */                   out.write("</span>");
/* 1343 */                   out.print(FormatUtil.getString("milliseconds"));
/* 1344 */                   out.write("\n                </span>\n            </td>\n            <td width=\"43%\" class=\"footer\" align=\"right\">\n                ");
/* 1345 */                   out.print(systemTimeMessage);
/* 1346 */                   out.write(32);
/* 1347 */                   out.write(58);
/* 1348 */                   out.write(32);
/* 1349 */                   if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                     return;
/* 1351 */                   out.write("&nbsp;&nbsp;\n            </td>\n        </tr>\n       ");
/*      */                 }
/* 1353 */                 out.write("\n        <tr>\n            <td height=\"20%\" colspan=\"2\" valign=\"bottom\"> ");
/* 1354 */                 if (_jspx_meth_tiles_005finsert_005f7(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                   return;
/* 1356 */                 out.write("</td>\n        </tr>\n        </table>\n      </td>\n      </tr>\n      </table>\n\n      </td>\n      <td><br></td>\n\n  </tr>\n</table>\n\n");
/* 1357 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1358 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1362 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1363 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/* 1366 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1367 */             out.write(10);
/* 1368 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1369 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1373 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1374 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/* 1377 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1378 */           out.write("\n\n</body>\n</html>\n\n");
/*      */         }
/* 1380 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1381 */         out = _jspx_out;
/* 1382 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1383 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1384 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1387 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1393 */     PageContext pageContext = _jspx_page_context;
/* 1394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1396 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 1397 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 1398 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*      */     
/* 1400 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 1401 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 1402 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 1403 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1404 */       return true;
/*      */     }
/* 1406 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 1407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1412 */     PageContext pageContext = _jspx_page_context;
/* 1413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1415 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1416 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1417 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1419 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1421 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1422 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1423 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1424 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1425 */       return true;
/*      */     }
/* 1427 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1433 */     PageContext pageContext = _jspx_page_context;
/* 1434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1436 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1437 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1438 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 1440 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod && empty param.noreload}");
/* 1441 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1442 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1444 */         out.write("\n <META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1445 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1446 */           return true;
/* 1447 */         out.write(34);
/* 1448 */         out.write(62);
/* 1449 */         out.write(10);
/* 1450 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1451 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1455 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1456 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1457 */       return true;
/*      */     }
/* 1459 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1465 */     PageContext pageContext = _jspx_page_context;
/* 1466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1468 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1469 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1470 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1472 */     _jspx_th_c_005fout_005f1.setValue("${reloadperiod}");
/* 1473 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1474 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1475 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1476 */       return true;
/*      */     }
/* 1478 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1484 */     PageContext pageContext = _jspx_page_context;
/* 1485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1487 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1488 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1489 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 1491 */     _jspx_th_c_005fout_005f2.setValue("${selectedskin}");
/*      */     
/* 1493 */     _jspx_th_c_005fout_005f2.setDefault("${initParam.defaultSkin}");
/* 1494 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1495 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1496 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1497 */       return true;
/*      */     }
/* 1499 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1505 */     PageContext pageContext = _jspx_page_context;
/* 1506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1508 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1509 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1510 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 1512 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 1513 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1514 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1516 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 1517 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1518 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1522 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1523 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1524 */       return true;
/*      */     }
/* 1526 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1532 */     PageContext pageContext = _jspx_page_context;
/* 1533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1535 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1536 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1537 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 1539 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 1540 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1541 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1543 */         out.write("\n\n\tvar poll=trimAll(document.forms[1].pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert(\"Polling Interval should be a number greater than 0.\");\n\t\treturn;\n\t}\n\n\tdocument.forms[1].submit();\n\t");
/* 1544 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1549 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1550 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1551 */       return true;
/*      */     }
/* 1553 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1559 */     PageContext pageContext = _jspx_page_context;
/* 1560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1562 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1563 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1564 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1566 */     _jspx_th_c_005fforEach_005f0.setVar("tab");
/*      */     
/* 1568 */     _jspx_th_c_005fforEach_005f0.setItems("${slatab}");
/* 1569 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1571 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1572 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1574 */           out.write("\n<td width=\"6%\" height=\"42\"  align=\"center\" class=\"slimheader\"><a href=\"");
/* 1575 */           boolean bool; if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1576 */             return true;
/* 1577 */           out.write("\"\" class=\"staticlinks\">");
/* 1578 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1579 */             return true;
/* 1580 */           out.write("</a></td>\n");
/* 1581 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1582 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1586 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1587 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1590 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 1591 */         out = _jspx_page_context.popBody(); }
/* 1592 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1594 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1595 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1602 */     PageContext pageContext = _jspx_page_context;
/* 1603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1605 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1606 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1607 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1609 */     _jspx_th_c_005fout_005f3.setValue("${tab.TABLINK}");
/* 1610 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1611 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1612 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1613 */       return true;
/*      */     }
/* 1615 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1621 */     PageContext pageContext = _jspx_page_context;
/* 1622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1624 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1625 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1626 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1628 */     _jspx_th_c_005fout_005f4.setValue("${tab.TABNAME}");
/* 1629 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1630 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1631 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1632 */       return true;
/*      */     }
/* 1634 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1640 */     PageContext pageContext = _jspx_page_context;
/* 1641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1643 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1644 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1645 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1647 */     _jspx_th_c_005fforEach_005f1.setVar("tab");
/*      */     
/* 1649 */     _jspx_th_c_005fforEach_005f1.setItems("${taborder}");
/* 1650 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1652 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1653 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1655 */           out.write(10);
/* 1656 */           out.write(10);
/* 1657 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1658 */             return true;
/* 1659 */           out.write(10);
/* 1660 */           out.write(10);
/* 1661 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1662 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1666 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1667 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1670 */         int tmp205_204 = 0; int[] tmp205_202 = _jspx_push_body_count_c_005fforEach_005f1; int tmp207_206 = tmp205_202[tmp205_204];tmp205_202[tmp205_204] = (tmp207_206 - 1); if (tmp207_206 <= 0) break;
/* 1671 */         out = _jspx_page_context.popBody(); }
/* 1672 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1674 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1675 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1682 */     PageContext pageContext = _jspx_page_context;
/* 1683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1685 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1686 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1687 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1688 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1689 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1691 */         out.write(10);
/* 1692 */         out.write(10);
/* 1693 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1694 */           return true;
/* 1695 */         out.write(10);
/* 1696 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1697 */           return true;
/* 1698 */         out.write(10);
/* 1699 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1700 */           return true;
/* 1701 */         out.write("\n\n\n\n\n");
/* 1702 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1703 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1707 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1708 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1709 */       return true;
/*      */     }
/* 1711 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1717 */     PageContext pageContext = _jspx_page_context;
/* 1718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1720 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1721 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1722 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1724 */     _jspx_th_c_005fwhen_005f1.setTest("${tab.TABID == MONITORTAB && tab.TABTYPE==1}");
/* 1725 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1726 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1728 */         out.write(10);
/* 1729 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1730 */           return true;
/* 1731 */         out.write(10);
/* 1732 */         out.write(32);
/* 1733 */         out.write(32);
/* 1734 */         if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1735 */           return true;
/* 1736 */         out.write(10);
/* 1737 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1742 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1743 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1744 */       return true;
/*      */     }
/* 1746 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1752 */     PageContext pageContext = _jspx_page_context;
/* 1753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1755 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1756 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1757 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1759 */     _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 1760 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1761 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1763 */         out.write("\n  <td width=\"6%\" class=\"slimheader\" align=\"center\">\n  \t");
/* 1764 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1765 */           return true;
/* 1766 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1767 */           return true;
/* 1768 */         out.write("\n  </a></td>\n  ");
/* 1769 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1770 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1774 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1775 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1776 */       return true;
/*      */     }
/* 1778 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1784 */     PageContext pageContext = _jspx_page_context;
/* 1785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1787 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1788 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1789 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/* 1790 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1791 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1793 */         out.write("\n  \t\t");
/* 1794 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1795 */           return true;
/* 1796 */         out.write("\n  \t\t");
/* 1797 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1798 */           return true;
/* 1799 */         out.write("\n  \t\t");
/* 1800 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1801 */           return true;
/* 1802 */         out.write("\n  \t");
/* 1803 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1804 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1808 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1809 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1810 */       return true;
/*      */     }
/* 1812 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1818 */     PageContext pageContext = _jspx_page_context;
/* 1819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1821 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1822 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1823 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1825 */     _jspx_th_c_005fwhen_005f2.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 1826 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1827 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1829 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 1830 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1831 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1835 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1836 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1837 */       return true;
/*      */     }
/* 1839 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1845 */     PageContext pageContext = _jspx_page_context;
/* 1846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1848 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1849 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1850 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1852 */     _jspx_th_c_005fwhen_005f3.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1853 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1854 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1856 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1857 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1858 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1862 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1863 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1864 */       return true;
/*      */     }
/* 1866 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1872 */     PageContext pageContext = _jspx_page_context;
/* 1873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1875 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1876 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1877 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1878 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1879 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1881 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 1882 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1883 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1887 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1888 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1889 */       return true;
/*      */     }
/* 1891 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1897 */     PageContext pageContext = _jspx_page_context;
/* 1898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1900 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1901 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1902 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1904 */     _jspx_th_c_005fout_005f5.setValue("${tab.TABNAME}");
/* 1905 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1906 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1907 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1908 */       return true;
/*      */     }
/* 1910 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1916 */     PageContext pageContext = _jspx_page_context;
/* 1917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1919 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1920 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1921 */     _jspx_th_logic_005fnotPresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1923 */     _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 1924 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1925 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 1927 */         out.write("\n   <td width=\"6%\" class=\"slimheader\" align=\"center\"> \n   \n   ");
/* 1928 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1929 */           return true;
/* 1930 */         out.write("\n\t</td> \n  ");
/* 1931 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1932 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1936 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1937 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1938 */       return true;
/*      */     }
/* 1940 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1946 */     PageContext pageContext = _jspx_page_context;
/* 1947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1949 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1950 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1951 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/* 1952 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1953 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1955 */         out.write("\n   \t\t ");
/* 1956 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1957 */           return true;
/* 1958 */         out.write("\n   \t \t ");
/* 1959 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1960 */           return true;
/* 1961 */         out.write("\n   \t");
/* 1962 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1963 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1967 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1968 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1969 */       return true;
/*      */     }
/* 1971 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1977 */     PageContext pageContext = _jspx_page_context;
/* 1978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1980 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1981 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1982 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1984 */     _jspx_th_c_005fwhen_005f4.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 1985 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1986 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1988 */         out.write("\n   \t\t <a href=\"/showresource.do?group=All&method=");
/* 1989 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1990 */           return true;
/* 1991 */         out.write("&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 1992 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1993 */           return true;
/* 1994 */         out.write("</a>\n   \t\t ");
/* 1995 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1996 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2000 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2001 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2002 */       return true;
/*      */     }
/* 2004 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2010 */     PageContext pageContext = _jspx_page_context;
/* 2011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2013 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2014 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2015 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2017 */     _jspx_th_c_005fout_005f6.setValue("${globalconfig['defaultmonitorsview']}");
/* 2018 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2019 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2021 */       return true;
/*      */     }
/* 2023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2029 */     PageContext pageContext = _jspx_page_context;
/* 2030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2032 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2033 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2034 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2036 */     _jspx_th_c_005fout_005f7.setValue("${tab.TABNAME}");
/* 2037 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2038 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2039 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2040 */       return true;
/*      */     }
/* 2042 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2048 */     PageContext pageContext = _jspx_page_context;
/* 2049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2051 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2052 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2053 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2054 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2055 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2057 */         out.write("\n   \t \t <a href=\"/showresource.do?group=All&method=");
/* 2058 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2059 */           return true;
/* 2060 */         out.write("\" class=\"staticlinks\">");
/* 2061 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2062 */           return true;
/* 2063 */         out.write("</a>\n   \t\t ");
/* 2064 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2069 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2070 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2071 */       return true;
/*      */     }
/* 2073 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2079 */     PageContext pageContext = _jspx_page_context;
/* 2080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2082 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2083 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2084 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 2086 */     _jspx_th_c_005fout_005f8.setValue("${globalconfig['defaultmonitorsview']}");
/* 2087 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2088 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2089 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2090 */       return true;
/*      */     }
/* 2092 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2098 */     PageContext pageContext = _jspx_page_context;
/* 2099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2101 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2102 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2103 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 2105 */     _jspx_th_c_005fout_005f9.setValue("${tab.TABNAME}");
/* 2106 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2107 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2108 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2109 */       return true;
/*      */     }
/* 2111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2117 */     PageContext pageContext = _jspx_page_context;
/* 2118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2120 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2121 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2122 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2124 */     _jspx_th_c_005fwhen_005f5.setTest("${tab.TABID == ADMINTAB && tab.TABTYPE==1}");
/* 2125 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2126 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2128 */         out.write(10);
/* 2129 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2130 */           return true;
/* 2131 */         out.write(10);
/* 2132 */         out.write(9);
/* 2133 */         if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2134 */           return true;
/* 2135 */         out.write(10);
/* 2136 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2137 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2141 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2142 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2143 */       return true;
/*      */     }
/* 2145 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2151 */     PageContext pageContext = _jspx_page_context;
/* 2152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2154 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2155 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2156 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2158 */     _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2159 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2160 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 2162 */         out.write("\n    <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"staticlinks\">");
/* 2163 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2164 */           return true;
/* 2165 */         out.write("</a></td>\n\t");
/* 2166 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2167 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2171 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2172 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2173 */       return true;
/*      */     }
/* 2175 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2181 */     PageContext pageContext = _jspx_page_context;
/* 2182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2184 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2185 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2186 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 2188 */     _jspx_th_c_005fout_005f10.setValue("${tab.TABNAME}");
/* 2189 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2190 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2191 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2192 */       return true;
/*      */     }
/* 2194 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2200 */     PageContext pageContext = _jspx_page_context;
/* 2201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2203 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2204 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2205 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2207 */     _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 2208 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2209 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 2211 */         out.write("\n\t<td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\" class=\"staticlinks\">");
/* 2212 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2213 */           return true;
/* 2214 */         out.write("</a></td>\n\t");
/* 2215 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2216 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2220 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2221 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2222 */       return true;
/*      */     }
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2230 */     PageContext pageContext = _jspx_page_context;
/* 2231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2233 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2234 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2235 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 2237 */     _jspx_th_c_005fout_005f11.setValue("${tab.TABNAME}");
/* 2238 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2239 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2240 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2241 */       return true;
/*      */     }
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2249 */     PageContext pageContext = _jspx_page_context;
/* 2250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2252 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2253 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2254 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2255 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2256 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2258 */         out.write("\n <td width=\"6%\" class=\"slimheader\" align=\"center\"><a href=\"");
/* 2259 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2260 */           return true;
/* 2261 */         out.write("\" class=\"staticlinks\">\t");
/* 2262 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2263 */           return true;
/* 2264 */         out.write("</a></td>\n");
/* 2265 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2266 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2270 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2271 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2272 */       return true;
/*      */     }
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2280 */     PageContext pageContext = _jspx_page_context;
/* 2281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2283 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2284 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2285 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2287 */     _jspx_th_c_005fout_005f12.setValue("${tab.TABLINK}");
/* 2288 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2289 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2290 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2291 */       return true;
/*      */     }
/* 2293 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2299 */     PageContext pageContext = _jspx_page_context;
/* 2300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2302 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2303 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2304 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2306 */     _jspx_th_c_005fout_005f13.setValue("${tab.TABNAME}");
/* 2307 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2308 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2309 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2310 */       return true;
/*      */     }
/* 2312 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2318 */     PageContext pageContext = _jspx_page_context;
/* 2319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2321 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2322 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2323 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/* 2324 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2325 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2327 */         out.write("\n  \t\t");
/* 2328 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2329 */           return true;
/* 2330 */         out.write("\n  \t\t");
/* 2331 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2332 */           return true;
/* 2333 */         out.write("\n  \t\t");
/* 2334 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2335 */           return true;
/* 2336 */         out.write("\n  \t");
/* 2337 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2338 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2342 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2343 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2344 */       return true;
/*      */     }
/* 2346 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2352 */     PageContext pageContext = _jspx_page_context;
/* 2353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2355 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2356 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2357 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2359 */     _jspx_th_c_005fwhen_005f6.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2360 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2361 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 2363 */         out.write("<a href=\"/showresource.do?method=showResourceTypesAll\" class=\"slimheader\">");
/* 2364 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2365 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2369 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2370 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2371 */       return true;
/*      */     }
/* 2373 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2379 */     PageContext pageContext = _jspx_page_context;
/* 2380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2382 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2383 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2384 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2386 */     _jspx_th_c_005fwhen_005f7.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypes'}");
/* 2387 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2388 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 2390 */         out.write("<a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"staticlinks\">");
/* 2391 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2392 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2396 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2397 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2398 */       return true;
/*      */     }
/* 2400 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2406 */     PageContext pageContext = _jspx_page_context;
/* 2407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2409 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2410 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2411 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2412 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2413 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2415 */         out.write("\n  \t\t\t<a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">\n  \t\t");
/* 2416 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2421 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2422 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2423 */       return true;
/*      */     }
/* 2425 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2431 */     PageContext pageContext = _jspx_page_context;
/* 2432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2434 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2435 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2436 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 2438 */     _jspx_th_c_005fout_005f14.setValue("${globalconfig['defaultmonitorsview']}");
/* 2439 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2440 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2441 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2442 */       return true;
/*      */     }
/* 2444 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2450 */     PageContext pageContext = _jspx_page_context;
/* 2451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2453 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2454 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 2455 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 2457 */     _jspx_th_c_005fout_005f15.setValue("${globalconfig['defaultmonitorsview']}");
/* 2458 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 2459 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 2460 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2461 */       return true;
/*      */     }
/* 2463 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2469 */     PageContext pageContext = _jspx_page_context;
/* 2470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2472 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2473 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2474 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2476 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2478 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2479 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2480 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2481 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2482 */       return true;
/*      */     }
/* 2484 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2485 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2490 */     PageContext pageContext = _jspx_page_context;
/* 2491 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2493 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2494 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2495 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2497 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2499 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2500 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2501 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2502 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2503 */       return true;
/*      */     }
/* 2505 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2511 */     PageContext pageContext = _jspx_page_context;
/* 2512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2514 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2515 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2516 */     _jspx_th_tiles_005finsert_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2518 */     _jspx_th_tiles_005finsert_005f0.setAttribute("Summary");
/* 2519 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2520 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2521 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2522 */       return true;
/*      */     }
/* 2524 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2530 */     PageContext pageContext = _jspx_page_context;
/* 2531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2533 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2534 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 2535 */     _jspx_th_tiles_005finsert_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2537 */     _jspx_th_tiles_005finsert_005f1.setAttribute("UserArea");
/* 2538 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 2539 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 2540 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2541 */       return true;
/*      */     }
/* 2543 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 2544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2549 */     PageContext pageContext = _jspx_page_context;
/* 2550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2552 */     LoadTime _jspx_th_am_005ftimestamp_005f0 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2553 */     _jspx_th_am_005ftimestamp_005f0.setPageContext(_jspx_page_context);
/* 2554 */     _jspx_th_am_005ftimestamp_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 2555 */     int _jspx_eval_am_005ftimestamp_005f0 = _jspx_th_am_005ftimestamp_005f0.doStartTag();
/* 2556 */     if (_jspx_th_am_005ftimestamp_005f0.doEndTag() == 5) {
/* 2557 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 2558 */       return true;
/*      */     }
/* 2560 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 2561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2566 */     PageContext pageContext = _jspx_page_context;
/* 2567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2569 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2570 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 2571 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2573 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${systime}");
/*      */     
/* 2575 */     _jspx_th_fmt_005fformatDate_005f0.setType("BOTH");
/* 2576 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 2577 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 2578 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2579 */       return true;
/*      */     }
/* 2581 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f2(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2587 */     PageContext pageContext = _jspx_page_context;
/* 2588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2590 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2591 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 2592 */     _jspx_th_tiles_005finsert_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2594 */     _jspx_th_tiles_005finsert_005f2.setAttribute("Header");
/* 2595 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 2596 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 2597 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2598 */       return true;
/*      */     }
/* 2600 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 2601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f3(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2606 */     PageContext pageContext = _jspx_page_context;
/* 2607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2609 */     InsertTag _jspx_th_tiles_005finsert_005f3 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2610 */     _jspx_th_tiles_005finsert_005f3.setPageContext(_jspx_page_context);
/* 2611 */     _jspx_th_tiles_005finsert_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2613 */     _jspx_th_tiles_005finsert_005f3.setAttribute("LeftArea");
/* 2614 */     int _jspx_eval_tiles_005finsert_005f3 = _jspx_th_tiles_005finsert_005f3.doStartTag();
/* 2615 */     if (_jspx_th_tiles_005finsert_005f3.doEndTag() == 5) {
/* 2616 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2617 */       return true;
/*      */     }
/* 2619 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f3);
/* 2620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2625 */     PageContext pageContext = _jspx_page_context;
/* 2626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2628 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2629 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 2630 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2632 */     _jspx_th_c_005fout_005f16.setValue("${selectedskin}");
/*      */     
/* 2634 */     _jspx_th_c_005fout_005f16.setDefault("${initParam.defaultSkin}");
/* 2635 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 2636 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 2637 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2638 */       return true;
/*      */     }
/* 2640 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2646 */     PageContext pageContext = _jspx_page_context;
/* 2647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2649 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2650 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 2651 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 2653 */     _jspx_th_c_005fout_005f17.setValue("${monitorname}");
/* 2654 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 2655 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 2656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 2657 */       return true;
/*      */     }
/* 2659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 2660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2665 */     PageContext pageContext = _jspx_page_context;
/* 2666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2668 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2669 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 2670 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2672 */     _jspx_th_c_005fout_005f18.setValue("${param.resourcename}");
/* 2673 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 2674 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 2675 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 2676 */       return true;
/*      */     }
/* 2678 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 2679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2684 */     PageContext pageContext = _jspx_page_context;
/* 2685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2687 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2688 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2689 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2691 */     _jspx_th_c_005fif_005f5.setTest("${empty firstrow}");
/* 2692 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2693 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2695 */         out.write("\n\n        <tr>\n        <td height=\"46\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n        <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n        <tr>\n        <td width=\"5%\" align=\"center\">\n        <img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n        </td>\n        <td width=\"95%\" class=\"message\">\n\n          ");
/* 2696 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 2697 */           return true;
/* 2698 */         out.write("\n        ");
/* 2699 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2700 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2704 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2705 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2706 */       return true;
/*      */     }
/* 2708 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2714 */     PageContext pageContext = _jspx_page_context;
/* 2715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2717 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2718 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2719 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2721 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 2723 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 2724 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2725 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2726 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2727 */       return true;
/*      */     }
/* 2729 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2735 */     PageContext pageContext = _jspx_page_context;
/* 2736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2738 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2739 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2740 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2742 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2744 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2745 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2746 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2747 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2748 */       return true;
/*      */     }
/* 2750 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2756 */     PageContext pageContext = _jspx_page_context;
/* 2757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2759 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2760 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2761 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2763 */     _jspx_th_c_005fif_005f6.setTest("${!empty firstrow}");
/* 2764 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2765 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 2767 */         out.write("\n        </td>\n        </tr>\n        </table>\n        </td>\n        </tr>\n        ");
/* 2768 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2769 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2773 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2774 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2775 */       return true;
/*      */     }
/* 2777 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fmessages_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2783 */     PageContext pageContext = _jspx_page_context;
/* 2784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2786 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2787 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2788 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f3);
/*      */     
/* 2790 */     _jspx_th_bean_005fwrite_005f3.setName("msg");
/*      */     
/* 2792 */     _jspx_th_bean_005fwrite_005f3.setFilter(false);
/* 2793 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2794 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2795 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2796 */       return true;
/*      */     }
/* 2798 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2799 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f4(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2804 */     PageContext pageContext = _jspx_page_context;
/* 2805 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2807 */     InsertTag _jspx_th_tiles_005finsert_005f4 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2808 */     _jspx_th_tiles_005finsert_005f4.setPageContext(_jspx_page_context);
/* 2809 */     _jspx_th_tiles_005finsert_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2811 */     _jspx_th_tiles_005finsert_005f4.setAttribute("Summary");
/* 2812 */     int _jspx_eval_tiles_005finsert_005f4 = _jspx_th_tiles_005finsert_005f4.doStartTag();
/* 2813 */     if (_jspx_th_tiles_005finsert_005f4.doEndTag() == 5) {
/* 2814 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2815 */       return true;
/*      */     }
/* 2817 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f4);
/* 2818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f5(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2823 */     PageContext pageContext = _jspx_page_context;
/* 2824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2826 */     InsertTag _jspx_th_tiles_005finsert_005f5 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2827 */     _jspx_th_tiles_005finsert_005f5.setPageContext(_jspx_page_context);
/* 2828 */     _jspx_th_tiles_005finsert_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2830 */     _jspx_th_tiles_005finsert_005f5.setAttribute("ServerLeftArea");
/* 2831 */     int _jspx_eval_tiles_005finsert_005f5 = _jspx_th_tiles_005finsert_005f5.doStartTag();
/* 2832 */     if (_jspx_th_tiles_005finsert_005f5.doEndTag() == 5) {
/* 2833 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2834 */       return true;
/*      */     }
/* 2836 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f5);
/* 2837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f6(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2842 */     PageContext pageContext = _jspx_page_context;
/* 2843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2845 */     InsertTag _jspx_th_tiles_005finsert_005f6 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2846 */     _jspx_th_tiles_005finsert_005f6.setPageContext(_jspx_page_context);
/* 2847 */     _jspx_th_tiles_005finsert_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2849 */     _jspx_th_tiles_005finsert_005f6.setAttribute("UserArea");
/* 2850 */     int _jspx_eval_tiles_005finsert_005f6 = _jspx_th_tiles_005finsert_005f6.doStartTag();
/* 2851 */     if (_jspx_th_tiles_005finsert_005f6.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f6);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f1(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     LoadTime _jspx_th_am_005ftimestamp_005f1 = (LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(LoadTime.class);
/* 2865 */     _jspx_th_am_005ftimestamp_005f1.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_am_005ftimestamp_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/* 2867 */     int _jspx_eval_am_005ftimestamp_005f1 = _jspx_th_am_005ftimestamp_005f1.doStartTag();
/* 2868 */     if (_jspx_th_am_005ftimestamp_005f1.doEndTag() == 5) {
/* 2869 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f1);
/* 2870 */       return true;
/*      */     }
/* 2872 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f1);
/* 2873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2878 */     PageContext pageContext = _jspx_page_context;
/* 2879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2881 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 2882 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 2883 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2885 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${systime}");
/*      */     
/* 2887 */     _jspx_th_fmt_005fformatDate_005f1.setType("BOTH");
/* 2888 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 2889 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 2890 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 2891 */       return true;
/*      */     }
/* 2893 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 2894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005finsert_005f7(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2899 */     PageContext pageContext = _jspx_page_context;
/* 2900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2902 */     InsertTag _jspx_th_tiles_005finsert_005f7 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 2903 */     _jspx_th_tiles_005finsert_005f7.setPageContext(_jspx_page_context);
/* 2904 */     _jspx_th_tiles_005finsert_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2906 */     _jspx_th_tiles_005finsert_005f7.setAttribute("footer");
/* 2907 */     int _jspx_eval_tiles_005finsert_005f7 = _jspx_th_tiles_005finsert_005f7.doStartTag();
/* 2908 */     if (_jspx_th_tiles_005finsert_005f7.doEndTag() == 5) {
/* 2909 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2910 */       return true;
/*      */     }
/* 2912 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f7);
/* 2913 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WTAServerLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */