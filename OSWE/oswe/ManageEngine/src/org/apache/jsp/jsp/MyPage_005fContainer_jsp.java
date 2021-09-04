/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
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
/*      */ public final class MyPage_005fContainer_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   27 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   33 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   34 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   35 */     _jspx_dependants.put("/jsp/MyPage.jsp", Long.valueOf(1473429417000L));
/*   36 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   62 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   66 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   85 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   89 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*   90 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*   91 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*   92 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   93 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   95 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   96 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*   97 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   98 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   99 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  100 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.release();
/*  101 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  102 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  103 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  104 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.release();
/*  105 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  106 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  113 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  116 */     JspWriter out = null;
/*  117 */     Object page = this;
/*  118 */     JspWriter _jspx_out = null;
/*  119 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  123 */       response.setContentType("text/html");
/*  124 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  126 */       _jspx_page_context = pageContext;
/*  127 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  128 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  129 */       session = pageContext.getSession();
/*  130 */       out = pageContext.getOut();
/*  131 */       _jspx_out = out;
/*      */       
/*  133 */       out.write("<!DOCTYPE html>\n");
/*  134 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  135 */       out.write("\n\n    \n\n\n\n\n\n");
/*  136 */       response.setContentType("text/html;charset=UTF-8");
/*  137 */       out.write(10);
/*      */       
/*  139 */       if (com.adventnet.appmanager.util.Constants.isFirstVisit)
/*      */       {
/*      */         try
/*      */         {
/*  143 */           com.adventnet.appmanager.db.AMConnectionPool pool = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/*  144 */           com.adventnet.appmanager.db.AMConnectionPool.executeUpdateStmt("UPDATE AM_GLOBALCONFIG SET VALUE='false' WHERE NAME='firstvisit'");
/*  145 */           com.adventnet.appmanager.util.Constants.isFirstVisit = false;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  149 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  155 */         if (com.adventnet.appmanager.struts.actions.MyPageAction.forpage.equals("3"))
/*      */         {
/*  157 */           request.setAttribute("oldtab", "2");
/*      */         }
/*      */         
/*  160 */         out.write(10);
/*      */         
/*  162 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  163 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  164 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/*  166 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  167 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  168 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/*  170 */             out.write(32);
/*  171 */             out.write(10);
/*      */             
/*  173 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  174 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  175 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  177 */             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */             
/*  179 */             _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.mypage.dashboards.text"));
/*  180 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  181 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  182 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */             }
/*      */             
/*  185 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  186 */             out.write(10);
/*  187 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  189 */             out.write(10);
/*  190 */             if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  192 */             out.write(10);
/*  193 */             out.write(10);
/*      */             
/*  195 */             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  196 */             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/*  197 */             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  199 */             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */             
/*  201 */             _jspx_th_tiles_005fput_005f3.setType("string");
/*  202 */             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/*  203 */             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/*  204 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  205 */                 out = _jspx_page_context.pushBody();
/*  206 */                 _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  207 */                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  210 */                 out.write(10);
/*  211 */                 out.write(10);
/*  212 */                 out.write(10);
/*  213 */                 out.write(10);
/*  214 */                 out.write(10);
/*  215 */                 out.write("\n\n\n\n\n\n\n");
/*  216 */                 com.adventnet.appmanager.struts.beans.MyPageBean mygraph = null;
/*  217 */                 mygraph = (com.adventnet.appmanager.struts.beans.MyPageBean)_jspx_page_context.getAttribute("mygraph", 1);
/*  218 */                 if (mygraph == null) {
/*  219 */                   mygraph = new com.adventnet.appmanager.struts.beans.MyPageBean();
/*  220 */                   _jspx_page_context.setAttribute("mygraph", mygraph, 1);
/*      */                 }
/*  222 */                 out.write("\n\n\n\n<script type=\"text/javascript\" src=\"/template/TabDrag.js\"></script>\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n");
/*  223 */                 if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  225 */                 out.write("\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/tooltip.js\"></script>\n<html>\n<head>\n");
/*  226 */                 if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  228 */                 out.write("\n</head>\n\n");
/*  229 */                 if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  231 */                 out.write("\n<body>\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n");
/*      */                 
/*  233 */                 SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  234 */                 _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  235 */                 _jspx_th_c_005fset_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  237 */                 _jspx_th_c_005fset_005f0.setVar("isReadOnly");
/*  238 */                 int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  239 */                 if (_jspx_eval_c_005fset_005f0 != 0) {
/*  240 */                   if (_jspx_eval_c_005fset_005f0 != 1) {
/*  241 */                     out = _jspx_page_context.pushBody();
/*  242 */                     _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  243 */                     _jspx_th_c_005fset_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  246 */                     out.print(com.adventnet.appmanager.util.DashboardUtil.isReadOnlyVewForUser(request.getRemoteUser(), (String)request.getAttribute("pageid")));
/*  247 */                     int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  248 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  251 */                   if (_jspx_eval_c_005fset_005f0 != 1) {
/*  252 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  255 */                 if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  256 */                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                 }
/*      */                 
/*  259 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  260 */                 out.write(10);
/*      */                 
/*  262 */                 if ((com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") == null))
/*      */                 {
/*  264 */                   response.sendRedirect("/showIT360Tile.do?TileName=IT360.CustomersSummary");
/*      */                 }
/*      */                 
/*  267 */                 out.write(10);
/*  268 */                 out.write(10);
/*      */                 try
/*      */                 {
/*  271 */                   String selectedTab = "0";
/*  272 */                   String productUrl = "localhost:9090";
/*      */                   try
/*      */                   {
/*  275 */                     productUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
/*      */                   }
/*      */                   catch (Exception e) {
/*  278 */                     e.printStackTrace();
/*      */                   }
/*      */                   
/*  281 */                   out.write(10);
/*      */                   
/*  283 */                   if (Boolean.parseBoolean(com.adventnet.appmanager.util.Constants.expandWidgets))
/*      */                   {
/*  285 */                     out.write(10);
/*  286 */                     out.write(9);
/*  287 */                     if (_jspx_meth_c_005fset_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/*  289 */                     out.write(10);
/*      */                   }
/*      */                   
/*  292 */                   out.write("\n\n<script>\n\n\nvar widgetid = '';\nvar frameloaded = true;\nvar widgetorder = 0;\n\nwindow.onbeforeunload = function() {\n\n    if(!frameloaded){\n\n        var url = \"/MyPage.do?method=deleteembedurl&widgetid=\"+widgetid;\t\t//No I18N\n       \tvar http1=getHTTPObject();\n    \thttp1.onreadystatechange =function(){} ;\n    \thttp1.open(\"GET\",url, true);\n    \thttp1.send(null);\n\n   \treturn '");
/*  293 */                   out.print(FormatUtil.getString("am.mypage.embed.url.delete.message"));
/*  294 */                   out.write("'\n\n    }\n\n  }\n\n\nfunction frameonload(framestatus,widget){\n\n\twidgetid = widget;\n\n\tif(framestatus == 'editpagesubmit'){\n\n\t\tframeloaded = false;\n\n\t}else {\n\n\tframeloaded = true;\n}\n\n}\n\nfunction editWidget(widgetid)\n{\nfnOpenNewWindowWithHeightWidthPlacement('/MyPage.do?method=editWidget&pageid=");
/*  295 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  297 */                   out.write("&widgetid='+widgetid,'1000','700',5,5); //No I18N\n}\nfunction setSize(widgetid,colWidth)\n{\n\t\n\tif ($(\"#\"+widgetid).length == 0){\n\t\t// return if the widget is not present\n\t\treturn;\n\t}\n\t\n var screenWidthAvailable  =");
/*  298 */                   if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  300 */                   out.write(";\n var refblock = jQuery(\"#userAreaContainerDiv\").length?jQuery(\"#userAreaContainerDiv\"):jQuery('#containerDiv');\t//NO I18N\n // In popout actions containderDiv will be parent, elsewhere userAreaContainderDiv\n var scrWidth = (refblock.width() * screenWidthAvailable)/100 ;\t//NO I18N\n\n\t");
/*  301 */                   if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  303 */                   out.write("\n  \tvar widDivWidth = parseInt((scrWidth*colWidth/100)-22);//No I18N\n  \tif(navigator.userAgent.indexOf(\"Chrome\")!=-1){\n  \t\tif(colWidth==40){\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-16);//No I18N\n  \t\t}\n  \t\telse if(colWidth == 25)\n  \t\t{\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-49);//No I18N\n  \t\t}\n  \t\telse{\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-18);//No I18N\n  \t\t}\n  \t\t\n  \t}\n  \t\n  \t//review:document.getElementById(widgetid).offsetParent.width = widDivWidth+\"px\";\n  \tdocument.getElementById(widgetid).style.width =  widDivWidth+\"px\";//document.getElementById(widgetID).offsetParent.offsetWidth;\n\tdocument.getElementById(widgetid).style.overflowX=\"auto\";\n\n\t");
/*  304 */                   if (_jspx_meth_c_005fset_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  306 */                   out.write(10);
/*  307 */                   out.write(9);
/*  308 */                   out.write(9);
/*  309 */                   if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  311 */                   out.write("\n\t  //      document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;padding:10px;height:180px'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\";\n\n}\n\nfunction setAsDefault(resourceid,forpage)\n{\n\tif(resourceid==null)\n\t{\n\t\tresourceid='0';//No I18N\n\t}\n\tif(forpage==null)\n\t{\n\t\tforpage='1';//No I18N\n\t}\n\tdocument.location.href='MyPage.do?method=setAsDefault&resourceid='+resourceid+'&dashboardtype=1&forpage='+forpage+'&pageid=");
/*  312 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  314 */                   out.write("';\n}\nfunction publishDashboard(resourceid)\n{\n\tif(resourceid!=null)\n\t{\n\t\tshowURLInDialog('/MyPage.do?method=getPublishedKey&pageid=");
/*  315 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  317 */                   out.write("&template_resid='+resourceid,'position=relative,srcElement=publishMghook,left=50,top=-10,title=");
/*  318 */                   out.print(FormatUtil.getString("am.mypage.dashboard.public.dashboard.text"));
/*  319 */                   out.write("');//No I18N\n\t}\n\telse\n\t{\n\t\t showURLInDialog('/MyPage.do?method=getPublishedKey&pageid=");
/*  320 */                   if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  322 */                   out.write("','position=relative,srcElement=publishMghook,left=50,top=-10,title=");
/*  323 */                   out.print(FormatUtil.getString("am.mypage.dashboard.public.dashboard.text"));
/*  324 */                   out.write("');//No I18N\n\n\t}\n}\nfunction editMyPage(resourceid)\n{\n\tif(resourceid==null)\n\t{\n\t\t document.location.href='/MyPage.do?method=editMyPage&pageid=");
/*  325 */                   if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  327 */                   out.write("&selectedTab=");
/*  328 */                   out.print(selectedTab);
/*  329 */                   out.write("';\n\t}\n\telse\n\t{\n\t\tdocument.location.href='/MyPage.do?template_resid='+resourceid+'&method=editMyPage&pageid=");
/*  330 */                   if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  332 */                   out.write("&selectedTab=");
/*  333 */                   out.print(selectedTab);
/*  334 */                   out.write("';\n\t}\n}\nfunction getContent1(widgetid,colWidth)\n{\n//This method is called from js file:dont delete\n      setSize(widgetid,colWidth);\n      getContent2(widgetid);\n}\n\nfunction getContentwithDelay(widgetid,colWidth,delay)\n{\n\t\twidgetorder += 1;\n      setSize(widgetid,colWidth);\n      setTimeout('getContent2(\\\"'+widgetid+'\\\")',delay);\n\n}\n\nfunction getContent(widgetid,colWidth)\n{\n      setSize(widgetid,colWidth);\n      getContent2(widgetid);\n}\nfunction getContent2(widgetid)\n{\n\tvar pageid='");
/*  335 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  337 */                   out.write("';\n\t");
/*  338 */                   if (_jspx_meth_c_005fchoose_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  340 */                   out.write("\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n\nfunction getContent3(widgetid)\n{\n //       alert(\"getContent3 called...\");\n        ");
/*  341 */                   if (_jspx_meth_c_005fset_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  343 */                   out.write(10);
/*  344 */                   out.write(9);
/*  345 */                   if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  347 */                   out.write("\n\n \tvar pageid='");
/*  348 */                   if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  350 */                   out.write("';\n        var widDivWidth = document.getElementById(widgetid).offsetWidth;\n\tvar height = document.getElementById(widgetid).offsetHeight;\n\tvar imageLoc = parseInt((height-16)/2);\n\theight -= imageLoc;\n        document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;height:\"+height+\"px;margin-top:\"+imageLoc+\"px;'><img src='/images/LoadingTC.gif' style=''/></div>\";\n        ");
/*  351 */                   if (_jspx_meth_c_005fchoose_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  353 */                   out.write("\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n//this is duplicate function for the function getContent3 but will accept params\nfunction getContentwithParams(widgetid,params)\n{\n\t");
/*  354 */                   if (_jspx_meth_c_005fset_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  356 */                   out.write(10);
/*  357 */                   out.write(9);
/*  358 */                   if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  360 */                   out.write("\n\n \tvar pageid='");
/*  361 */                   if (_jspx_meth_c_005fout_005f23(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  363 */                   out.write("';\n        var widDivWidth = document.getElementById(widgetid).offsetWidth;\n\tvar height = document.getElementById(widgetid).offsetHeight;\n\tvar imageLoc = parseInt((height-16)/2);\n\theight -= imageLoc;\n        document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;height:\"+height+\"px;margin-top:\"+imageLoc+\"px;'><img src='/images/LoadingTC.gif' style=''/></div>\";\n        ");
/*  364 */                   if (_jspx_meth_c_005fchoose_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  366 */                   out.write("\n\tif(params!=null)\n\t{\n\turl=url+params;\n\t}\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n\n\n\nfunction deleteWidget(widgetid)\n{\n        ");
/*  367 */                   if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  369 */                   out.write("\n        ");
/*      */                   
/*  371 */                   org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  372 */                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  373 */                   _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/*  375 */                   _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  376 */                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  377 */                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                     for (;;) {
/*  379 */                       out.write("\n        if(!confirm(\"");
/*  380 */                       out.print(FormatUtil.getString("am.mypage.widget.delete.confirm.text"));
/*  381 */                       out.write("\"))\n        {\n        return;\n        }\n        url=\"/MyPage.do?method=deleteWidget&widgetid=\"+widgetid+\"&pageid=");
/*  382 */                       if (_jspx_meth_c_005fout_005f27(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                         return;
/*  384 */                       out.write("\";\n\tvar http2=getHTTPObject();\n\thttp2.onreadystatechange =function(){} ;\n\thttp2.open(\"GET\", url, true);\n\thttp2.send(null);\n\tdocument.getElementById(widgetid+\"_\").innerHTML = \"\";\n\t ");
/*  385 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  386 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  390 */                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  391 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                   }
/*      */                   
/*  394 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  395 */                   out.write("\n}\nfunction setContent(http1,widgetid)\n{\n\tif(http1.readyState == 4)\n \t{\n       \t   if( http1.status == 200)\n\t   {\n\t\tdocument.getElementById(widgetid).innerHTML = http1.responseText;\n\t\twidgetorder -= 1;\n\n\t\t$(\"#graphArea img\").load(function(){\n\t\t\t$(this).attr('width', $(this).width());// NO I18N\n\t\t\t$(this).attr('height', $(this).height());// NO I18N\n\t\t});\n\t\t\n\t\tif(widgetorder == 0 ){\n\t\t\tvar hashValue = document.location.hash\n\t\t\tif(hashValue == '#scrollWidget'){\n\t\t\tdocument.location.hash = '';\n\t\t\tsetTimeout(function() {\n\t\t\t\t\tvar widgetToScroll = '");
/*  396 */                   if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  398 */                   out.write("_'\n\t\t\t\t\tjQuery(window).scrollTop(jQuery('#'+widgetToScroll).offset().top);\t// NO I18N\n\t\t\t},500);\n\t\t\t\n\t\t\t}\n\t\t}\n\t\t//document.getElementById(widgetid).style.display='inline';\n\t\t//To load script inside the response JSP Page to this page's head tag. Follows ...\n\t\tvar innercontent = document.getElementById(widgetid);\n\t\tvar scriptTags = innercontent.getElementsByTagName(\"SCRIPT\");\n\t\tfor (var i = 0; i < scriptTags.length; i++)\n\t\t{\n\t\t\tvar scriptTag = document.createElement(\"SCRIPT\");\n\t\t\tscriptTag.type = \"text/javascript\";\n\t\t\tscriptTag.language = \"javascript\";\n\t\t\tif (scriptTags[i].src != \"\") { scriptTag.src = scriptTags[i].src;}\n\t\t\tscriptTag.text = scriptTags[i].text;\n\t\t\tif (typeof document.getElementsByTagName(\"HEAD\")[0] == \"undefined\")\n\t\t\t{\n\t\t\t\tdocument.createElement(\"HEAD\").appendChild(scriptTag)\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementsByTagName(\"HEAD\")[0].appendChild(scriptTag);\n\t\t\t}\n\t\t} // Ends loading script.\n\t   }\n\t }\n}\n\nfunction addWidgets()\n{\nfnOpenNewWindowWithHeightWidthPlacement('/MyPage.do?method=newWidgets&isPopup=true&pageid=");
/*  399 */                   if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  401 */                   out.write("','900','610',15,15);\t//No I18N\n}\n\nfunction setDisplayName(widgetid,name)\n{\ndocument.getElementById(\"widgetname#\"+widgetid).innerHTML=name;\n//alert(window.opener.document.getElementById(\"widgetname#");
/*  402 */                   if (_jspx_meth_c_005fout_005f30(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  404 */                   out.write("\").innerHTML);\n\n}\n//script for menu\n\nvar hideDiv = \"\";\n\nfunction hideDropDiv(e){\nif (document.getElementById(e)!= \"null\" && document.getElementById!=null && document.getElementById != \"\")\n{\n\tdocument.getElementById(e).style.display = \"none\";\n}\n}\n\nfunction delayhideDropDiv(msec,whereto){\nclearhideDropDiv()\ndelayDropDiv=setTimeout(\"hideDropDiv('\"+whereto+\"')\",msec)\n}\n\n\nfunction clearhideDropDiv(){\nif (typeof delayDropDiv!=\"undefined\")\nclearTimeout(delayDropDiv)\n}\n\n\nfunction showthemall(id1)\n {\n\tvar disp = document.getElementById(id1);\n\tif (disp.style.display == '' || disp.style.display == 'none')\n\t{\n\t\teval(\"disp.style.display = 'block'\");\n\t}else\n\t{\n\t\teval(\"disp.style.display = 'none'\");\n\t}\n }\n//script for menu\n\n\n");
/*  405 */                   out.write("\n\nfunction dragDropCallBack(res)\n{\n\tupdatePosition(\"NavBar\");\n}\n\n\nfunction updatePosition(sd)\n{\n\tvar items = document.getElementById(sd).getElementsByTagName(\"div\");\n\tvar colCounts = document.getElementById(sd).getElementsByTagName(\"td\");\n\tvar positionArray = new Array();\n\tvar classCount = ");
/*  406 */                   if (_jspx_meth_c_005fout_005f31(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  408 */                   out.write(";\n\n\tvar sfr = new Array();\n\n\tfor (var i = 0; i<classCount; i++)\n\t{\n\t\tvar coun = \"col\"+(i+1);\n\t\tsfr = document.getElementById(coun).getElementsByTagName(\"div\");\n\t\tnewloc = i;\t\t\t\t\t\t\t\t//location of the first widget is same as columns index\n\t\tfor (var j = 0; j < sfr.length; j++)\n\t\t{\n\t\t\tif(sfr[j].className == 'tabdiv gridItem' && sfr[j].style.display == 'block')\n\t\t\t{\n\t\t\t\tpositionArray.push(sfr[j].getAttribute(\"id\")+newloc);\n\t\t\t\tnewloc+=classCount;\n\t\t\t}\n\t\t}\n\t}\n\n\tvar url = \"/MyPage.do?method=saveMyPageLayout&\";\n\turl= url+\"pageid=");
/*  409 */                   if (_jspx_meth_c_005fout_005f32(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  411 */                   out.write("&positionArray=\"+positionArray+\"&sid=\"+Math.random();\n\tvar http3=getHTTPObject();\n\thttp3.onreadystatechange =function(){} ;\n\thttp3.open(\"GET\", url, true);\n\thttp3.send(null);\n}\n\nfunction popOut(template_resid)\n{\n\tvar popoutwindow;\n\tif(template_resid!=null)\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&template_resid='+template_resid+'&pageid=");
/*  412 */                   if (_jspx_meth_c_005fout_005f33(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  414 */                   out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n\telse\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&pageid=");
/*  415 */                   if (_jspx_meth_c_005fout_005f34(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  417 */                   out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n}\n\n//script from opmanager drag and drop ends\nfunction getNewBookMarkForm(widgetid,holder,bookmarkid)\n{\n    if(document.getElementById(\"widgetBokmarks_DivContainer\").style.display==\"none\"){\n    var http2=getHTTPObject();\n    http2.onreadystatechange=  function(){AjaxCallForbookMark(http2,widgetid,holder);};//No I18N\n    URL=\"/MyPage.do?method=getBookMarkForm&widgetid=\"+widgetid+\"&bookmarkid=\"+bookmarkid+\"&pageid=");
/*  418 */                   if (_jspx_meth_c_005fout_005f35(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  420 */                   out.write("\";//No I18N\n    http2.open(\"GET\",URL,true);//No I18N\n    http2.send(null);\n    }\n    //else\n    //{\n\t//\n    //}\n\n}\nfunction AjaxCallForbookMark(http2,widgetid,holder)\n{\nif(http2.readyState == 4)\n \t{\n       \t   if( http2.status == 200)\n\t   {\n\t   document.getElementById(\"widgetBokmarks_DivContainer\").innerHTML=http2.responseText;//No I18N\n\t   //showFloatpopUp(holder,'widgetBokmarks_DivContainer_'+widgetid);//No I18N\n\t   var holderObj = document.getElementById(holder);//No I18N\n\t   var scrWidth = screen.width;//No I18N\n\t   var scrHeight = screen.height;    //No I18N\n\t   var posX = findPosX(holderObj)-62;//No I18N\n\t   var posY = findPosY(holderObj)-62;//No I18N\n\t   if(scrWidth-posX<500)\n\t   {\n\t     posX=scrWidth-600;\t //No I18N\n\t   }\n\t   if(posX<0)\n\t   {\n\t     posX=20;\t//No I18N\n\t   }\n\n\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.position=\"absolute\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.left=posX+\"px\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.top=posY+\"px\";//No I18N\n");
/*  421 */                   out.write("\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.zindex=\"100\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.display=\"block\";//No I18N\n\t   }\n\t}\n\n}\n\nfunction showFloatpopUp(holder,widgetid)\n{\n        var http1=getHTTPObject();//No I18N\n        var url=\"/MyPage.do?method=getWidgetProperties&widgetid=\"+widgetid;//No I18N\n        \n        ");
/*  422 */                   if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  424 */                   out.write("\n        \t\n        http1.onreadystatechange = function (){showDescription(http1,holder);};//No I18N\n\thttp1.open(\"GET\", url, true);//No I18N\n\thttp1.send(null);\n\n\n}\n\nfunction showDescription(http1,holder)\n{\n        if(http1.readyState == 4)\n\t{\n\tif( http1.status == 200)\n\t{\n\n         document.getElementById(\"widgetDescriptionDiv\").innerHTML=http1.responseText;//No I18N\n         var holderObj = document.getElementById(holder);//No I18N\n         var scrWidth = screen.width;     //No I18N\n\t var posX = findPosX(holderObj)-62;//No I18N\n\t var posY = findPosY(holderObj)-1;//No I18N\n\t if(scrWidth-posX<500)\n\t {\n\t posX=scrWidth-600;\t //No I18N\n\t }\n\t var finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\t showDialog(document.getElementById(\"widgetDescriptionDiv\").innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n\t}\n\t}\n\n}\nfunction saveBookmark()\n{\n        var url='/MyPage.do';//No I18N\n\tvar pageid='");
/*  425 */                   if (_jspx_meth_c_005fout_005f37(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  427 */                   out.write("';//No I18N\n\tvar urltosave=document.urlform.url.value;//No I18N\n\tvar diaplayname=document.urlform.displayName.value;//No I18N\n\tvar widgetid=document.urlform.widgetid.value;//No I18N\n\tvar bookmarkid=document.urlform.bookmarkid.value;//No I18N\n\tvar description=document.urlform.description.value;//No I18N\n\tif(diaplayname.length == 0){\n\t\talert('");
/*  428 */                   out.print(FormatUtil.getString("am.mypage.bookmark.displayname.alert.text"));
/*  429 */                   out.write("')\n\t\treturn;\n\t}\t\n\tvar http1=getHTTPObject();//No I18N\n\n\thttp1.onreadystatechange = function (){bookmarkEdit(http1,widgetid);};//No I18N\n\thttp1.open(\"POST\", url, true);//No I18N\n\n\n\thttp1.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");//No I18N\n\thttp1.send('method=saveBookMark&widgetid='+widgetid+'&pageid='+pageid+'&bookmarkid='+bookmarkid+'&url='+escape(urltosave)+'&displayname='+escape(diaplayname)+'&description='+escape(description));//No I18N\n\n}\nfunction bookmarkEdit(http1,widgetid)\n{\n  if(http1.readyState == 4)\n   {\n       if( http1.status == 200)\n\t{\n\tdocument.getElementById('widgetBokmarks_DivContainer').style.display='none';//No I18N\n\tsetContent(http1,widgetid);//No I18N\n\t}\n   }\n}\nfunction deleteBookMark(widgetid,bookmarkid)\n{\n      if(!confirm(\"");
/*  430 */                   out.print(FormatUtil.getString("am.mypage.bookmarkdelete.confirm.text"));
/*  431 */                   out.write("\"))\n        {\n        return;//No I18N\n        }\n    var http2=getHTTPObject();//No I18N\n    http2.onreadystatechange=  function(){bookmarkEdit(http2,widgetid);};//No I18N\n    URL=\"/MyPage.do?method=deleteBookMark&widgetid=\"+widgetid+\"&bookmarkid=\"+bookmarkid+\"&pageid=");
/*  432 */                   if (_jspx_meth_c_005fout_005f38(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  434 */                   out.write("\";//No I18N\n    http2.open(\"GET\",URL,true);//No I18N\n    http2.send(null);\n}\n</script>\n\n<style>\ntable#dropMenu{font-family:Arial, Helvetica, sans-serif; font-size:11px; border:1px #8eadd5 solid; width:150px;}\ntable#dropMenu tr td { background:#ffffff;  border-bottom:1px #BBCEE2 solid; }\ntable#dropMenu tr td a { color:#000000;text-decoration:none;display:block;padding:3px 6px 3px 8px; width:150px; }\ntable#dropMenu tr td a:hover { color:#000000; background:#eff9fe;  text-decoration:none;display:block; width:150px; }\ntable#dropMenu tr td>a:hover { width:146px; }\n</style>\n\n<script>\nfunction showMenuInDialog(holder, source) {\n\tvar holderObj = document.getElementById(holder);//No I18N\n\tvar posX = findPosX(holderObj)-62;//No I18N\n\tvar posY = findPosY(holderObj)-1;//No I18N\n\tvar finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\tshowDialog(document.getElementById(source).innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n}\nfunction deleteDashboard(template_resid)\n");
/*  435 */                   out.write("{\nif(confirm('");
/*  436 */                   out.print(FormatUtil.getString("am.mypage.delete.confirm.text"));
/*  437 */                   out.write("'))\n{\n\tif(template_resid!=null)\n\t{\n\t\tdocument.location.href=\"/MyPage.do?template_resid=\"+template_resid+\"&method=deleteMyPage&pageid=");
/*  438 */                   if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  440 */                   out.write("\";//No I18N\n\t}\n\telse//No I18N\n\t{\n\t\tdocument.location.href=\"/MyPage.do?method=deleteMyPage&pageid=");
/*  441 */                   if (_jspx_meth_c_005fout_005f40(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/*  443 */                   out.write("\";//No I18N\n\t}\n}\nelse\n{\nreturn;\n}\n}\n\nfunction toggleBookmarkImg(bookmarkid)\n{\n\t\tif(document.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display == 'inline')\n\t\t{\n\t\t\tdocument.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display='none';//No I18N\n\t\t\tdocument.getElementById(\"bookMarkEdit_\"+bookmarkid).style.display='none';//No I18N\n\t\t}\n\t\telse    //No I18N\n\t\t{\n\t\t\tdocument.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display='inline';//No I18N\n\t\t\tdocument.getElementById(\"bookMarkEdit_\"+bookmarkid).style.display='inline';//No I18N\n\t\t}\n\n}\nfunction pickUnpickAlarm(widgetid,alarmid,pickorUnpick)\n{\nvar url='/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\nif(pickorUnpick=='pick')\n{\nvar url='/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\n}\nelse if(pickorUnpick=='unpick')\n{\nvar url='/fault/AlarmOperations.do?methodCall=unPickAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\n}\n$.get(url, function(data) {//No I18N\n");
/*  444 */                   out.write("getContent3(widgetid)//No I18N\n});\n}\n</script>\n\n<!--For Tabs -->\n");
/*      */                   
/*  446 */                   IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  447 */                   _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  448 */                   _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/*  450 */                   _jspx_th_c_005fif_005f9.setTest("${fromWhere=='dashboardpage'}");
/*  451 */                   int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  452 */                   if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                     for (;;) {
/*  454 */                       out.write(10);
/*      */                       
/*  456 */                       String pagenames = "";
/*  457 */                       String pageids = "";
/*  458 */                       String functions = "";
/*  459 */                       String tabs = "";
/*  460 */                       ArrayList dashboardsinOrder = (ArrayList)request.getAttribute("dashboardsinOrder");
/*  461 */                       selectedTab = (String)request.getAttribute("selectedTab");
/*      */                       
/*  463 */                       for (int i = 0; i < dashboardsinOrder.size(); i++)
/*      */                       {
/*  465 */                         ArrayList singlerow = (ArrayList)dashboardsinOrder.get(i);
/*  466 */                         String pageid = (String)singlerow.get(0);
/*  467 */                         int pageid_int = -1;
/*  468 */                         try { pageid_int = Integer.parseInt(pageid);
/*  469 */                         } catch (Exception ex) { ex.printStackTrace(); }
/*  470 */                         if (i == 0)
/*      */                         {
/*  472 */                           pagenames = (String)singlerow.get(1);
/*  473 */                           pageids = (String)singlerow.get(0);
/*  474 */                           functions = "getHomePage";
/*  475 */                           tabs = i + "";
/*      */ 
/*      */ 
/*      */                         }
/*  479 */                         else if (pageid_int >= 0)
/*      */                         {
/*      */ 
/*      */ 
/*  483 */                           pagenames = pagenames + "," + (String)singlerow.get(1);
/*  484 */                           pageids = pageids + "," + (String)singlerow.get(0);
/*  485 */                           functions = functions + ",getpage";
/*  486 */                           tabs = tabs + "," + i;
/*      */                         }
/*      */                       }
/*      */                       
/*  490 */                       out.write(10);
/*  491 */                       out.write(10);
/*  492 */                       JspRuntimeLibrary.include(request, response, "/jsp/MyPage_Tabs.jsp" + ("/jsp/MyPage_Tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pagenames), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tabs", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabs), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pagenames), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(functions), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("pageids", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pageids), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedTab), request.getCharacterEncoding()), out, false);
/*  493 */                       out.write(10);
/*  494 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  495 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  499 */                   if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  500 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                   }
/*      */                   
/*  503 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  504 */                   out.write("\n<!--For Tabs -->\n\n");
/*      */                   
/*  506 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  507 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  508 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*  509 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  510 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/*  512 */                       out.write(10);
/*      */                       
/*  514 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  515 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  516 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/*  518 */                       _jspx_th_c_005fwhen_005f4.setTest("${not empty pageid}");
/*  519 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  520 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/*  522 */                           out.write("\n<div id=\"Main\">\n<div id=\"containerDiv\" class=\"txtGlobal\">\n");
/*  523 */                           if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                             return;
/*  525 */                           out.write("\n<!--<div class=\"clearFT\"></div>\t-->\n");
/*  526 */                           if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                             return;
/*  528 */                           out.write("\n  <tr>\n  ");
/*      */                           
/*  530 */                           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  531 */                           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  532 */                           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/*  534 */                           _jspx_th_c_005fforEach_005f0.setVar("widgetcolumn");
/*      */                           
/*  536 */                           _jspx_th_c_005fforEach_005f0.setItems("${widgetcolumns}");
/*      */                           
/*  538 */                           _jspx_th_c_005fforEach_005f0.setVarStatus("colcounter");
/*  539 */                           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                           try {
/*  541 */                             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  542 */                             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                               for (;;) {
/*  544 */                                 out.write(10);
/*  545 */                                 out.write(32);
/*  546 */                                 out.write(32);
/*      */                                 
/*  548 */                                 String stylefortd = "";
/*      */                                 
/*  550 */                                 out.write(10);
/*  551 */                                 out.write(32);
/*  552 */                                 out.write(32);
/*      */                                 
/*  554 */                                 IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  555 */                                 _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/*  556 */                                 _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/*  558 */                                 _jspx_th_c_005fif_005f11.setTest("${colcounter.first}");
/*  559 */                                 int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/*  560 */                                 if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                   for (;;) {
/*  562 */                                     out.write(10);
/*  563 */                                     out.write(32);
/*  564 */                                     out.write(32);
/*      */                                     
/*  566 */                                     stylefortd = "style='padding-left:0px;'";
/*      */                                     
/*  568 */                                     out.write(10);
/*  569 */                                     out.write(32);
/*  570 */                                     out.write(32);
/*  571 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/*  572 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  576 */                                 if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/*  577 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
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
/*  787 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/*  580 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*  581 */                                 out.write(10);
/*  582 */                                 out.write(10);
/*  583 */                                 out.write(32);
/*      */                                 
/*  585 */                                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  586 */                                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/*  587 */                                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/*  589 */                                 _jspx_th_c_005fif_005f12.setTest("${colcounter.last}");
/*  590 */                                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/*  591 */                                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                   for (;;) {
/*  593 */                                     out.write("\n   ");
/*      */                                     
/*  595 */                                     stylefortd = "style='padding-right:0px;'";
/*      */                                     
/*  597 */                                     out.write(10);
/*  598 */                                     out.write(32);
/*  599 */                                     out.write(32);
/*  600 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/*  601 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  605 */                                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/*  606 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
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
/*  787 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/*  609 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*  610 */                                 out.write("\n  <td width=\"");
/*  611 */                                 if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  787 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/*  613 */                                 out.write("%\"  id=\"col");
/*  614 */                                 if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  787 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/*  616 */                                 out.write("\" valign=\"top\" align=\"center\"  ");
/*  617 */                                 out.print(stylefortd);
/*  618 */                                 out.write(">\n  ");
/*      */                                 
/*  620 */                                 ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  621 */                                 _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/*  622 */                                 _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/*  624 */                                 _jspx_th_c_005fforEach_005f1.setVar("widget");
/*      */                                 
/*  626 */                                 _jspx_th_c_005fforEach_005f1.setItems("${widgets}");
/*      */                                 
/*  628 */                                 _jspx_th_c_005fforEach_005f1.setVarStatus("counter");
/*  629 */                                 int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                 try {
/*  631 */                                   int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/*  632 */                                   if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                     for (;;) {
/*  634 */                                       out.write(10);
/*  635 */                                       out.write(32);
/*  636 */                                       out.write(32);
/*      */                                       
/*  638 */                                       IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  639 */                                       _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  640 */                                       _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                       
/*  642 */                                       _jspx_th_c_005fif_005f13.setTest("${widget[2]==(colcounter.count-1)  }");
/*  643 */                                       int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  644 */                                       if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                         for (;;) {
/*  646 */                                           out.write("\n\n\t  <div id=\"");
/*  647 */                                           if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  649 */                                           out.write("_\"  class=\"tabdiv\" style=\"display:block;\" >\n\t\t<table class=\"lrtbdarkborder-dashboards\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"background-color:white;\">\n\t\t<tr height=\"26\">\n\t\t<td  class='tableHeader1' height=\"26\"  width=\"80%\" align=\"left\" style=\"cursor:move;\"><div style=\"float:left; width:17px; position:reltive; margin-top:2px;\"><img src=\"/images/icon_dashboard_drag.gif\"></div><div id=\"widgetname#");
/*  650 */                                           if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  652 */                                           out.write(34);
/*  653 */                                           out.write(62);
/*  654 */                                           if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  656 */                                           out.write("\t</td>\n\t\t<td class=\"widgetHeader\" height=\"26\"  width=\"20%\" align=\"right\">\n\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\t\t<tr>\n\t\t<td>\n\t\t  <a id=\"widgetDescriptionHook_");
/*  657 */                                           if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  659 */                                           out.write("\" class=\"infobutton widget_optn_space\" title=\"");
/*  660 */                                           out.print(FormatUtil.getString("am.mypage.widget.properties.text"));
/*  661 */                                           out.write("\" href=\"javascript:void(0)\"  onclick=\"showFloatpopUp('widgetDescriptionHook_");
/*  662 */                                           if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  664 */                                           out.write(39);
/*  665 */                                           out.write(44);
/*  666 */                                           out.write(39);
/*  667 */                                           if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  669 */                                           out.write("')\"></a>\n\t\t</td>\n\t\t<td>\n\t\t <a class=\"reloadbutton widget_optn_space\" title=\"");
/*  670 */                                           out.print(FormatUtil.getString("am.mypage.widget.reload.text"));
/*  671 */                                           out.write("\" href=\"javascript:void(0)\" onclick='javascript:getContent3(\"");
/*  672 */                                           if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  674 */                                           out.write("\")'></a>\n\t\t</td>\n\t\t");
/*      */                                           
/*  676 */                                           PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  677 */                                           _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  678 */                                           _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                           
/*  680 */                                           _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  681 */                                           int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  682 */                                           if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                             for (;;) {
/*  684 */                                               out.write(10);
/*  685 */                                               out.write(9);
/*  686 */                                               out.write(9);
/*      */                                               
/*  688 */                                               IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  689 */                                               _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  690 */                                               _jspx_th_c_005fif_005f14.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                               
/*  692 */                                               _jspx_th_c_005fif_005f14.setTest("${publishpage ne true && !isReadOnly}");
/*  693 */                                               int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  694 */                                               if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                                 for (;;) {
/*  696 */                                                   out.write("\n\t\t <td>\n\t\t <a class=\"editbutton widget_optn_space\" title=\"");
/*  697 */                                                   out.print(FormatUtil.getString("am.mypage.widget.edit.text"));
/*  698 */                                                   out.write("\" href=\"javascript:void(0)\"   onClick=\"javascript:editWidget('");
/*  699 */                                                   if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/*  701 */                                                   out.write("')\"></a>\n\t\t </td>\n\t\t <td>\n\t\t <a href=\"javascript:void(0)\" title=\"");
/*  702 */                                                   out.print(FormatUtil.getString("am.mypage.widget.delete.text"));
/*  703 */                                                   out.write("\" class=\"closeButton widget_optn_space\" onclick=\"javascript:deleteWidget('");
/*  704 */                                                   if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/*  706 */                                                   out.write("')\"></a>\n\t\t </td>\n\t\t ");
/*  707 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  708 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/*  712 */                                               if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  713 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
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
/*  770 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/*  716 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  717 */                                               out.write(10);
/*  718 */                                               out.write(9);
/*  719 */                                               out.write(9);
/*  720 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  721 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/*  725 */                                           if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  726 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
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
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  729 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  730 */                                           out.write("\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td align=\"left\" valign=\"top\" colspan=\"2\">\n\t\t<div class=\"pos_relative\" style=\" overflow:auto; width:auto;display:inline-block;\"id=\"");
/*  731 */                                           if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  733 */                                           out.write("\">\n\t\t<div style='text-align:center;padding:10px;height:200px'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n </div></td>\n\t\t</tr>\n\t\t  </table>\n\t  </div>\n\t");
/*  734 */                                           if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  736 */                                           out.write("\n\t  <script>\n\t  \tjavascript:getContentwithDelay(\"");
/*  737 */                                           if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*      */ 
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  739 */                                           out.write(34);
/*  740 */                                           out.write(44);
/*  741 */                                           if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
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
/*  770 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  743 */                                           out.write(",200)\n\t  </script>\n  ");
/*  744 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/*  745 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/*  749 */                                       if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/*  750 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*  753 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  754 */                                       out.write(10);
/*  755 */                                       out.write(32);
/*  756 */                                       out.write(32);
/*  757 */                                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/*  758 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/*  762 */                                   if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  770 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  771 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
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
/*  787 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Throwable _jspx_exception)
/*      */                                 {
/*      */                                   for (;;)
/*      */                                   {
/*  766 */                                     int tmp5207_5206 = 0; int[] tmp5207_5204 = _jspx_push_body_count_c_005fforEach_005f1; int tmp5209_5208 = tmp5207_5204[tmp5207_5206];tmp5207_5204[tmp5207_5206] = (tmp5209_5208 - 1); if (tmp5209_5208 <= 0) break;
/*  767 */                                     out = _jspx_page_context.popBody(); }
/*  768 */                                   _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                 }
/*      */                                 finally {}
/*      */                                 
/*      */ 
/*  773 */                                 out.write("\n  </td>\n  ");
/*  774 */                                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  775 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  779 */                             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  787 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/*      */                           }
/*      */                           catch (Throwable _jspx_exception)
/*      */                           {
/*      */                             for (;;)
/*      */                             {
/*  783 */                               int tmp5350_5349 = 0; int[] tmp5350_5347 = _jspx_push_body_count_c_005fforEach_005f0; int tmp5352_5351 = tmp5350_5347[tmp5350_5349];tmp5350_5347[tmp5350_5349] = (tmp5352_5351 - 1); if (tmp5352_5351 <= 0) break;
/*  784 */                               out = _jspx_page_context.popBody(); }
/*  785 */                             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                           } finally {
/*  787 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  788 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                           }
/*  790 */                           out.write("\n  </tr>\n</table>\n\n</div>\n\n</div>\n\n");
/*  791 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  792 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  796 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  797 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/*  800 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  801 */                       out.write(10);
/*      */                       
/*  803 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  804 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  805 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f4);
/*  806 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  807 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/*  809 */                           out.write("\n<iframe src=\"");
/*  810 */                           out.print(productUrl + "/applications.do?PRINTER_FRIENDLY=true");
/*  811 */                           out.write("\" scrolling=\"no\" align=\"center\"  WIDTH=\"100%\" height=\"2000px\"   border=\"0\" frameborder=\"0\"> </iframe>\n\n");
/*  812 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  813 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  817 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  818 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/*  821 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  822 */                       out.write(10);
/*  823 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  824 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  828 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  829 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/*  832 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  833 */                   out.write(10);
/*      */ 
/*      */                 }
/*      */                 catch (Exception ex)
/*      */                 {
/*  838 */                   ex.printStackTrace();
/*      */                 }
/*      */                 
/*  841 */                 out.write(10);
/*  842 */                 out.write("\n<table width=\"100%\">\n      <tr>\n      <td align=\"right\">\n<div style=\"display:none;\"   id=\"widgetDescriptionDiv\">\n</div>\n </td>\n<tr>\n</table>\n");
/*  843 */                 out.write(10);
/*  844 */                 out.write(10);
/*  845 */                 out.write("\n <div style=\"display:none;\"   id=\"widgetBokmarks_DivContainer\">\n <div style=\"overflow: auto; height:280px;width: 500px;\">\n &nbsp;\n </div>\n</div>\n");
/*  846 */                 out.write(10);
/*  847 */                 out.write(10);
/*  848 */                 if (_jspx_meth_c_005fif_005f16(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  850 */                 out.write(10);
/*  851 */                 out.write(10);
/*      */                 
/*  853 */                 IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  854 */                 _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/*  855 */                 _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  857 */                 _jspx_th_c_005fif_005f17.setTest("${isPopup=='true' || publishpage=='true'}");
/*  858 */                 int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/*  859 */                 if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                   for (;;) {
/*  861 */                     out.write(10);
/*  862 */                     request.setAttribute("systime", new java.util.Date());
/*  863 */                     out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr> \n    \t<td style=\"padding-left: 10px;\"><span class=\"footer\">");
/*  864 */                     out.print(FormatUtil.getString("webclient.server.responsetime"));
/*  865 */                     out.write("&nbsp;<span class=\"bodytextbold\">");
/*  866 */                     if (_jspx_meth_am_005ftimestamp_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/*  868 */                     out.write("</span>");
/*  869 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/*  871 */                     out.write("</span></td>\t\t");
/*  872 */                     out.write("\n        <td style=\"padding-right: 10px;\" align=\"right\"><span class=\"footer\">");
/*  873 */                     out.print(FormatUtil.getString("webclient.server.systemtime"));
/*  874 */                     out.write(32);
/*  875 */                     out.write(58);
/*  876 */                     out.write(32);
/*  877 */                     if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/*  879 */                     out.write("</span>&nbsp;&nbsp;</td>\n    </tr>\n</table>\n<script type=\"text/javascript\">\n$(window).resize(function() \n{\n\tparent.window.document.location.reload(false);\t  \n});\n</script>\n");
/*  880 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/*  881 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  885 */                 if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/*  886 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                 }
/*      */                 
/*  889 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*  890 */                 out.write("\n\n<script type=\"text/javascript\">\n$(document).ready(function() {//No I18N\n\t");
/*  891 */                 if (_jspx_meth_c_005fif_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  893 */                 out.write("\n}\n/* ");
/*  894 */                 if (_jspx_meth_c_005fif_005f20(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  896 */                 out.write(" */\n);\n</script>\n");
/*  897 */                 out.write("\n\n\n\n");
/*  898 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/*  899 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  902 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  903 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  906 */             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/*  907 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */             }
/*      */             
/*  910 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/*  911 */             out.write(10);
/*  912 */             out.write(10);
/*  913 */             out.write(10);
/*  914 */             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  916 */             out.write(10);
/*  917 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/*  918 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  922 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/*  923 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */         }
/*      */         
/*  926 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*  927 */         out.write(10);
/*  928 */         out.write(10);
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  933 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  936 */       out.write(10);
/*      */     } catch (Throwable t) {
/*  938 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  939 */         out = _jspx_out;
/*  940 */         if ((out != null) && (out.getBufferSize() != 0))
/*  941 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  942 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  945 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  951 */     PageContext pageContext = _jspx_page_context;
/*  952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  954 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  955 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/*  956 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/*  958 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/*  960 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/*  961 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/*  962 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/*  963 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/*  964 */       return true;
/*      */     }
/*  966 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/*  967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  972 */     PageContext pageContext = _jspx_page_context;
/*  973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  975 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  976 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  977 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/*  979 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/*  981 */     _jspx_th_tiles_005fput_005f2.setType("string");
/*  982 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  983 */     if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  984 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  985 */         out = _jspx_page_context.pushBody();
/*  986 */         _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  987 */         _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  990 */         out.write(32);
/*  991 */         out.write(10);
/*  992 */         int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/*  993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  996 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  997 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1000 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 1001 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1002 */       return true;
/*      */     }
/* 1004 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1010 */     PageContext pageContext = _jspx_page_context;
/* 1011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1013 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1014 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1015 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1017 */     _jspx_th_c_005fif_005f0.setTest("${publishpage eq true}");
/* 1018 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1019 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1021 */         out.write("\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/Dialog.js\"></script>\n");
/* 1022 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1023 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1027 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1028 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1029 */       return true;
/*      */     }
/* 1031 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1037 */     PageContext pageContext = _jspx_page_context;
/* 1038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1040 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1041 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1042 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1044 */     _jspx_th_c_005fif_005f1.setTest("${isPopup=='true' || publishpage eq true}");
/* 1045 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1046 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1048 */         out.write(10);
/* 1049 */         out.write(10);
/* 1050 */         out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 1051 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1052 */           return true;
/* 1053 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 1054 */         out.write(10);
/* 1055 */         out.write(10);
/* 1056 */         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 1057 */         out.write(10);
/* 1058 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1059 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1063 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1064 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1065 */       return true;
/*      */     }
/* 1067 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1073 */     PageContext pageContext = _jspx_page_context;
/* 1074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1076 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1077 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1078 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1080 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1082 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1083 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1084 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1085 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1086 */       return true;
/*      */     }
/* 1088 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1094 */     PageContext pageContext = _jspx_page_context;
/* 1095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1097 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1098 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1099 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1101 */     _jspx_th_c_005fif_005f2.setTest("${isPopup=='true' || publishpage eq true}");
/* 1102 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1103 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 1105 */         out.write("\n<title>");
/* 1106 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 1107 */           return true;
/* 1108 */         out.write("</title>\n");
/* 1109 */         if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 1110 */           return true;
/* 1111 */         out.write(10);
/* 1112 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1117 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1118 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1119 */       return true;
/*      */     }
/* 1121 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1127 */     PageContext pageContext = _jspx_page_context;
/* 1128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1130 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1131 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1132 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1134 */     _jspx_th_c_005fout_005f1.setValue("${MyPageForm.displayName}");
/* 1135 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1136 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1138 */       return true;
/*      */     }
/* 1140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1146 */     PageContext pageContext = _jspx_page_context;
/* 1147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1149 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1150 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1151 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1153 */     _jspx_th_c_005fif_005f3.setTest("${!empty reloadperiod && !empty customreloadperiod}");
/* 1154 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1155 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1157 */         out.write("\n\t<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 1158 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 1159 */           return true;
/* 1160 */         out.write(34);
/* 1161 */         out.write(62);
/* 1162 */         out.write(10);
/* 1163 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1164 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1168 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1169 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1170 */       return true;
/*      */     }
/* 1172 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1178 */     PageContext pageContext = _jspx_page_context;
/* 1179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1181 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1182 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1183 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1185 */     _jspx_th_c_005fout_005f2.setValue("${customreloadperiod}");
/* 1186 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1187 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1188 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1189 */       return true;
/*      */     }
/* 1191 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1197 */     PageContext pageContext = _jspx_page_context;
/* 1198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1200 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 1201 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1202 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1204 */     _jspx_th_c_005fset_005f1.setVar("expandWidgets");
/*      */     
/* 1206 */     _jspx_th_c_005fset_005f1.setValue("true");
/*      */     
/* 1208 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 1209 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1210 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1211 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1212 */       return true;
/*      */     }
/* 1214 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1215 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1220 */     PageContext pageContext = _jspx_page_context;
/* 1221 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1223 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1224 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1225 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1227 */     _jspx_th_c_005fout_005f3.setValue("${pageid}");
/* 1228 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1229 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1230 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1231 */       return true;
/*      */     }
/* 1233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1239 */     PageContext pageContext = _jspx_page_context;
/* 1240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1242 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1243 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1244 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1246 */     _jspx_th_c_005fout_005f4.setValue("${param.screenwidth}");
/*      */     
/* 1248 */     _jspx_th_c_005fout_005f4.setDefault("100");
/* 1249 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1250 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1251 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1252 */       return true;
/*      */     }
/* 1254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1260 */     PageContext pageContext = _jspx_page_context;
/* 1261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1263 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1264 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1265 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 1266 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1267 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1269 */         out.write(10);
/* 1270 */         out.write(9);
/* 1271 */         out.write(9);
/* 1272 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1273 */           return true;
/* 1274 */         out.write(10);
/* 1275 */         out.write(9);
/* 1276 */         out.write(9);
/* 1277 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1278 */           return true;
/* 1279 */         out.write(10);
/* 1280 */         out.write(9);
/* 1281 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1282 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1286 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1287 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1288 */       return true;
/*      */     }
/* 1290 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1296 */     PageContext pageContext = _jspx_page_context;
/* 1297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1299 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1300 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1301 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1303 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty expandWidgets && expandWidgets eq 'true'}");
/* 1304 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1305 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1307 */         out.write("\n\t\t\tscrWidth = parseInt(scrWidth*0.980);\n\t\t");
/* 1308 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1309 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1313 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1314 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1315 */       return true;
/*      */     }
/* 1317 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1323 */     PageContext pageContext = _jspx_page_context;
/* 1324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1326 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1327 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1328 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1329 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1330 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1332 */         out.write("\n\t\t\t");
/* 1333 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1334 */           return true;
/* 1335 */         out.write(10);
/* 1336 */         out.write(9);
/* 1337 */         out.write(9);
/* 1338 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1343 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1344 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1345 */       return true;
/*      */     }
/* 1347 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1353 */     PageContext pageContext = _jspx_page_context;
/* 1354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1356 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1357 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1358 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1360 */     _jspx_th_c_005fif_005f4.setTest("${isPopup ne 'true' &&  publishpage ne 'true' }");
/* 1361 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1362 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 1364 */         out.write("\n\t  \t\t\tscrWidth = parseInt(scrWidth*0.980);\n\t  \t\t");
/* 1365 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1366 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1370 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1371 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1372 */       return true;
/*      */     }
/* 1374 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1380 */     PageContext pageContext = _jspx_page_context;
/* 1381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1383 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 1384 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 1385 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1387 */     _jspx_th_c_005fset_005f2.setVar("template_resid");
/* 1388 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 1389 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 1390 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 1391 */       return true;
/*      */     }
/* 1393 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 1394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1399 */     PageContext pageContext = _jspx_page_context;
/* 1400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1402 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1403 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1404 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1406 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.template_resid}");
/* 1407 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1408 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 1410 */         out.write("\n\t\t\t");
/* 1411 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 1412 */           return true;
/* 1413 */         out.write(10);
/* 1414 */         out.write(9);
/* 1415 */         out.write(9);
/* 1416 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1421 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1422 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1423 */       return true;
/*      */     }
/* 1425 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1431 */     PageContext pageContext = _jspx_page_context;
/* 1432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1434 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1435 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 1436 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1438 */     _jspx_th_c_005fset_005f3.setVar("template_resid");
/* 1439 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 1440 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 1441 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1442 */         out = _jspx_page_context.pushBody();
/* 1443 */         _jspx_th_c_005fset_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1444 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1447 */         out.write("template_resid=");
/* 1448 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 1449 */           return true;
/* 1450 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 1451 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1454 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1455 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1458 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 1459 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 1460 */       return true;
/*      */     }
/* 1462 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 1463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1468 */     PageContext pageContext = _jspx_page_context;
/* 1469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1471 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1472 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1473 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 1475 */     _jspx_th_c_005fout_005f5.setValue("${param.template_resid}");
/* 1476 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1477 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1478 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1479 */       return true;
/*      */     }
/* 1481 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1487 */     PageContext pageContext = _jspx_page_context;
/* 1488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1490 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1491 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1492 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1494 */     _jspx_th_c_005fout_005f6.setValue("${pageid}");
/* 1495 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1496 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1497 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1498 */       return true;
/*      */     }
/* 1500 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1506 */     PageContext pageContext = _jspx_page_context;
/* 1507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1509 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1510 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1511 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1513 */     _jspx_th_c_005fout_005f7.setValue("${pageid}");
/* 1514 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1515 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1516 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1517 */       return true;
/*      */     }
/* 1519 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1525 */     PageContext pageContext = _jspx_page_context;
/* 1526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1528 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1529 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1530 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1532 */     _jspx_th_c_005fout_005f8.setValue("${pageid}");
/* 1533 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1534 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1535 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1536 */       return true;
/*      */     }
/* 1538 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1544 */     PageContext pageContext = _jspx_page_context;
/* 1545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1547 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1548 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1549 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1551 */     _jspx_th_c_005fout_005f9.setValue("${pageid}");
/* 1552 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1553 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1554 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1555 */       return true;
/*      */     }
/* 1557 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1563 */     PageContext pageContext = _jspx_page_context;
/* 1564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1566 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1567 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1568 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1570 */     _jspx_th_c_005fout_005f10.setValue("${pageid}");
/* 1571 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1572 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1573 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1574 */       return true;
/*      */     }
/* 1576 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1582 */     PageContext pageContext = _jspx_page_context;
/* 1583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1585 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1586 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1587 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1589 */     _jspx_th_c_005fout_005f11.setValue("${pageid}");
/* 1590 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1591 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1592 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1593 */       return true;
/*      */     }
/* 1595 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1601 */     PageContext pageContext = _jspx_page_context;
/* 1602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1604 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1605 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1606 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 1607 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1608 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1610 */         out.write(10);
/* 1611 */         out.write(9);
/* 1612 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1613 */           return true;
/* 1614 */         out.write(10);
/* 1615 */         out.write(9);
/* 1616 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1617 */           return true;
/* 1618 */         out.write(10);
/* 1619 */         out.write(9);
/* 1620 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1621 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1625 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1626 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1627 */       return true;
/*      */     }
/* 1629 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1635 */     PageContext pageContext = _jspx_page_context;
/* 1636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1638 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1639 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1640 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1642 */     _jspx_th_c_005fwhen_005f1.setTest("${publishpage==\"true\"}");
/* 1643 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1644 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1646 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&columns=");
/* 1647 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1648 */           return true;
/* 1649 */         out.write("&pagekey=");
/* 1650 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1651 */           return true;
/* 1652 */         out.write("&randomnumber=\"+ Math.random()+'&'+'");
/* 1653 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1654 */           return true;
/* 1655 */         out.write("';\t\t// NO I18N\n\t");
/* 1656 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1657 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1661 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1662 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1663 */       return true;
/*      */     }
/* 1665 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1671 */     PageContext pageContext = _jspx_page_context;
/* 1672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1674 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1675 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1676 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1678 */     _jspx_th_c_005fout_005f12.setValue("${numberOfColumns}");
/* 1679 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1680 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1681 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1682 */       return true;
/*      */     }
/* 1684 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1690 */     PageContext pageContext = _jspx_page_context;
/* 1691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1693 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1694 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 1695 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1697 */     _jspx_th_c_005fout_005f13.setValue("${pagekey}");
/* 1698 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 1699 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 1700 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1701 */       return true;
/*      */     }
/* 1703 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1709 */     PageContext pageContext = _jspx_page_context;
/* 1710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1712 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1713 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 1714 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1716 */     _jspx_th_c_005fout_005f14.setValue("${template_resid}");
/* 1717 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 1718 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 1719 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1720 */       return true;
/*      */     }
/* 1722 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1728 */     PageContext pageContext = _jspx_page_context;
/* 1729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1731 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1732 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1733 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1734 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1735 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1737 */         out.write("\n\turl=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&columns=");
/* 1738 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1739 */           return true;
/* 1740 */         out.write("&randomnumber=\"+ Math.random()+'&'+'");
/* 1741 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1742 */           return true;
/* 1743 */         out.write("';\t\t// NO I18N\n\t");
/* 1744 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1745 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1749 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1750 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1751 */       return true;
/*      */     }
/* 1753 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1759 */     PageContext pageContext = _jspx_page_context;
/* 1760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1762 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1763 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 1764 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1766 */     _jspx_th_c_005fout_005f15.setValue("${numberOfColumns}");
/* 1767 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 1768 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 1769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1770 */       return true;
/*      */     }
/* 1772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1778 */     PageContext pageContext = _jspx_page_context;
/* 1779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1781 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1782 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 1783 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1785 */     _jspx_th_c_005fout_005f16.setValue("${template_resid}");
/* 1786 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 1787 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 1788 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1789 */       return true;
/*      */     }
/* 1791 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1797 */     PageContext pageContext = _jspx_page_context;
/* 1798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1800 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 1801 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 1802 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1804 */     _jspx_th_c_005fset_005f4.setVar("template_resid");
/* 1805 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 1806 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 1807 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 1808 */       return true;
/*      */     }
/* 1810 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 1811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1816 */     PageContext pageContext = _jspx_page_context;
/* 1817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1819 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1820 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1821 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1823 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.template_resid}");
/* 1824 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1825 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 1827 */         out.write(10);
/* 1828 */         out.write(9);
/* 1829 */         out.write(9);
/* 1830 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 1831 */           return true;
/* 1832 */         out.write(10);
/* 1833 */         out.write(9);
/* 1834 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1839 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1840 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1841 */       return true;
/*      */     }
/* 1843 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1849 */     PageContext pageContext = _jspx_page_context;
/* 1850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1852 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1853 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 1854 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 1856 */     _jspx_th_c_005fset_005f5.setVar("template_resid");
/* 1857 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 1858 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 1859 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 1860 */         out = _jspx_page_context.pushBody();
/* 1861 */         _jspx_th_c_005fset_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1862 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1865 */         out.write("template_resid=");
/* 1866 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 1867 */           return true;
/* 1868 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 1869 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1872 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 1873 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1876 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 1877 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 1878 */       return true;
/*      */     }
/* 1880 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 1881 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1886 */     PageContext pageContext = _jspx_page_context;
/* 1887 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1889 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1890 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 1891 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 1893 */     _jspx_th_c_005fout_005f17.setValue("${param.template_resid}");
/* 1894 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 1895 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 1896 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1897 */       return true;
/*      */     }
/* 1899 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1905 */     PageContext pageContext = _jspx_page_context;
/* 1906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1908 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1909 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 1910 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1912 */     _jspx_th_c_005fout_005f18.setValue("${pageid}");
/* 1913 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 1914 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 1915 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1916 */       return true;
/*      */     }
/* 1918 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1924 */     PageContext pageContext = _jspx_page_context;
/* 1925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1927 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1928 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1929 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 1930 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1931 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1933 */         out.write(10);
/* 1934 */         out.write(9);
/* 1935 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1936 */           return true;
/* 1937 */         out.write(10);
/* 1938 */         out.write(9);
/* 1939 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1940 */           return true;
/* 1941 */         out.write(10);
/* 1942 */         out.write(9);
/* 1943 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1944 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1948 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1949 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1950 */       return true;
/*      */     }
/* 1952 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1958 */     PageContext pageContext = _jspx_page_context;
/* 1959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1961 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1962 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1963 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1965 */     _jspx_th_c_005fwhen_005f2.setTest("${publishpage==\"true\"}");
/* 1966 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1967 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1969 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&pagekey=");
/* 1970 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 1971 */           return true;
/* 1972 */         out.write("&randomnumber=\"+ Math.random()+\"&\"+\"");
/* 1973 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 1974 */           return true;
/* 1975 */         out.write("\";//No I18N\n        ");
/* 1976 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1977 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1981 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1982 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1983 */       return true;
/*      */     }
/* 1985 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1991 */     PageContext pageContext = _jspx_page_context;
/* 1992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1994 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1995 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 1996 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1998 */     _jspx_th_c_005fout_005f19.setValue("${pagekey}");
/* 1999 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 2000 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 2001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 2002 */       return true;
/*      */     }
/* 2004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 2005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2010 */     PageContext pageContext = _jspx_page_context;
/* 2011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2013 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2014 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 2015 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2017 */     _jspx_th_c_005fout_005f20.setValue("${template_resid}");
/* 2018 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 2019 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 2020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 2021 */       return true;
/*      */     }
/* 2023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 2024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2029 */     PageContext pageContext = _jspx_page_context;
/* 2030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2032 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2033 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2034 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2035 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2036 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2038 */         out.write("\n        url=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&randomnumber=\"+ Math.random()+\"&\"+\"");
/* 2039 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 2040 */           return true;
/* 2041 */         out.write("\";\t//No I18N\n        ");
/* 2042 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2043 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2047 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2048 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2049 */       return true;
/*      */     }
/* 2051 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2052 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2057 */     PageContext pageContext = _jspx_page_context;
/* 2058 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2060 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2061 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 2062 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2064 */     _jspx_th_c_005fout_005f21.setValue("${template_resid}");
/* 2065 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 2066 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 2067 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 2068 */       return true;
/*      */     }
/* 2070 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 2071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2076 */     PageContext pageContext = _jspx_page_context;
/* 2077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2079 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 2080 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 2081 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2083 */     _jspx_th_c_005fset_005f6.setVar("template_resid");
/* 2084 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 2085 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 2086 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 2087 */       return true;
/*      */     }
/* 2089 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 2090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2095 */     PageContext pageContext = _jspx_page_context;
/* 2096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2098 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2099 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2100 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2102 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.template_resid}");
/* 2103 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2104 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2106 */         out.write(10);
/* 2107 */         out.write(9);
/* 2108 */         out.write(9);
/* 2109 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 2110 */           return true;
/* 2111 */         out.write(10);
/* 2112 */         out.write(9);
/* 2113 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2114 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2118 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2119 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2120 */       return true;
/*      */     }
/* 2122 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2128 */     PageContext pageContext = _jspx_page_context;
/* 2129 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2131 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2132 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 2133 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2135 */     _jspx_th_c_005fset_005f7.setVar("template_resid");
/* 2136 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 2137 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 2138 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 2139 */         out = _jspx_page_context.pushBody();
/* 2140 */         _jspx_th_c_005fset_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2141 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2144 */         out.write("\"&template_resid=");
/* 2145 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 2146 */           return true;
/* 2147 */         out.write(34);
/* 2148 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 2149 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2152 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 2153 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2156 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 2157 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 2158 */       return true;
/*      */     }
/* 2160 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 2161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2166 */     PageContext pageContext = _jspx_page_context;
/* 2167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2169 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2170 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 2171 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 2173 */     _jspx_th_c_005fout_005f22.setValue("${param.template_resid}");
/* 2174 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 2175 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 2176 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 2177 */       return true;
/*      */     }
/* 2179 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 2180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2185 */     PageContext pageContext = _jspx_page_context;
/* 2186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2188 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2189 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 2190 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2192 */     _jspx_th_c_005fout_005f23.setValue("${pageid}");
/* 2193 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 2194 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 2195 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2196 */       return true;
/*      */     }
/* 2198 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2204 */     PageContext pageContext = _jspx_page_context;
/* 2205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2207 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2208 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2209 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 2210 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2211 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 2213 */         out.write(10);
/* 2214 */         out.write(9);
/* 2215 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2216 */           return true;
/* 2217 */         out.write(10);
/* 2218 */         out.write(9);
/* 2219 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2220 */           return true;
/* 2221 */         out.write(10);
/* 2222 */         out.write(9);
/* 2223 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2224 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2228 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2229 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2230 */       return true;
/*      */     }
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2238 */     PageContext pageContext = _jspx_page_context;
/* 2239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2241 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2242 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2243 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2245 */     _jspx_th_c_005fwhen_005f3.setTest("${publishpage==\"true\"}");
/* 2246 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2247 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 2249 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&pagekey=");
/* 2250 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2251 */           return true;
/* 2252 */         out.write("&randomnumber=\"+Math.random()+\"");
/* 2253 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2254 */           return true;
/* 2255 */         out.write("\";\n\t");
/* 2256 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2261 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2262 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2263 */       return true;
/*      */     }
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2271 */     PageContext pageContext = _jspx_page_context;
/* 2272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2274 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2275 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 2276 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2278 */     _jspx_th_c_005fout_005f24.setValue("${pagekey}");
/* 2279 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 2280 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 2281 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2282 */       return true;
/*      */     }
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2290 */     PageContext pageContext = _jspx_page_context;
/* 2291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2293 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2294 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 2295 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2297 */     _jspx_th_c_005fout_005f25.setValue("${template_resid}");
/* 2298 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 2299 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 2300 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2301 */       return true;
/*      */     }
/* 2303 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2309 */     PageContext pageContext = _jspx_page_context;
/* 2310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2312 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2313 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2314 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2315 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2316 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2318 */         out.write("\n        url=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&randomnumber=\"+Math.random()+\"");
/* 2319 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 2320 */           return true;
/* 2321 */         out.write("\";\n         ");
/* 2322 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2323 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2327 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2328 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2329 */       return true;
/*      */     }
/* 2331 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2332 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2337 */     PageContext pageContext = _jspx_page_context;
/* 2338 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2340 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2341 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 2342 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2344 */     _jspx_th_c_005fout_005f26.setValue("${template_resid}");
/* 2345 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 2346 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 2347 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2348 */       return true;
/*      */     }
/* 2350 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2356 */     PageContext pageContext = _jspx_page_context;
/* 2357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2359 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2360 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2361 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2363 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2364 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2365 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2367 */         out.write("\n        alertUser();\n        ");
/* 2368 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2369 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2373 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2374 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2375 */       return true;
/*      */     }
/* 2377 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2383 */     PageContext pageContext = _jspx_page_context;
/* 2384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2386 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2387 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 2388 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 2390 */     _jspx_th_c_005fout_005f27.setValue("${pageid}");
/* 2391 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 2392 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 2393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2394 */       return true;
/*      */     }
/* 2396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2402 */     PageContext pageContext = _jspx_page_context;
/* 2403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2405 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2406 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 2407 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2409 */     _jspx_th_c_005fout_005f28.setValue("${maxWidget}");
/* 2410 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 2411 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 2412 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2413 */       return true;
/*      */     }
/* 2415 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2421 */     PageContext pageContext = _jspx_page_context;
/* 2422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2424 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2425 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 2426 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2428 */     _jspx_th_c_005fout_005f29.setValue("${pageid}");
/* 2429 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 2430 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 2431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2432 */       return true;
/*      */     }
/* 2434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2440 */     PageContext pageContext = _jspx_page_context;
/* 2441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2443 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2444 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 2445 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2447 */     _jspx_th_c_005fout_005f30.setValue("${param.widgetid}");
/* 2448 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 2449 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 2450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2451 */       return true;
/*      */     }
/* 2453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2459 */     PageContext pageContext = _jspx_page_context;
/* 2460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2462 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2463 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 2464 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2466 */     _jspx_th_c_005fout_005f31.setValue("${MyPageForm.numberOfColumns}");
/* 2467 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 2468 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 2469 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2470 */       return true;
/*      */     }
/* 2472 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2478 */     PageContext pageContext = _jspx_page_context;
/* 2479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2481 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2482 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 2483 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2485 */     _jspx_th_c_005fout_005f32.setValue("${pageid}");
/* 2486 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 2487 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 2488 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2489 */       return true;
/*      */     }
/* 2491 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2497 */     PageContext pageContext = _jspx_page_context;
/* 2498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2500 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2501 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 2502 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2504 */     _jspx_th_c_005fout_005f33.setValue("${pageid}");
/* 2505 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 2506 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 2507 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 2508 */       return true;
/*      */     }
/* 2510 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 2511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2516 */     PageContext pageContext = _jspx_page_context;
/* 2517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2519 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2520 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 2521 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2523 */     _jspx_th_c_005fout_005f34.setValue("${pageid}");
/* 2524 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 2525 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 2526 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 2527 */       return true;
/*      */     }
/* 2529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 2530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2535 */     PageContext pageContext = _jspx_page_context;
/* 2536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2538 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2539 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 2540 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2542 */     _jspx_th_c_005fout_005f35.setValue("${pageid}");
/* 2543 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 2544 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 2545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 2546 */       return true;
/*      */     }
/* 2548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 2549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2554 */     PageContext pageContext = _jspx_page_context;
/* 2555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2557 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2558 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2559 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2561 */     _jspx_th_c_005fif_005f8.setTest("${publishpage==\"true\"}");
/* 2562 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2563 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2565 */         out.write("\n        \turl=\"/publishPage.do?method=getWidgetProperties&widgetid=\"+widgetid+\"&pagekey=");
/* 2566 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 2567 */           return true;
/* 2568 */         out.write("&randomnumber=\"+ Math.random();//No I18N\n        ");
/* 2569 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2570 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2574 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2575 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2576 */       return true;
/*      */     }
/* 2578 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2584 */     PageContext pageContext = _jspx_page_context;
/* 2585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2587 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2588 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 2589 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2591 */     _jspx_th_c_005fout_005f36.setValue("${pagekey}");
/* 2592 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 2593 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 2594 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 2595 */       return true;
/*      */     }
/* 2597 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 2598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2603 */     PageContext pageContext = _jspx_page_context;
/* 2604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2606 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2607 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 2608 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2610 */     _jspx_th_c_005fout_005f37.setValue("${pageid}");
/* 2611 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 2612 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 2613 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 2614 */       return true;
/*      */     }
/* 2616 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 2617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2622 */     PageContext pageContext = _jspx_page_context;
/* 2623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2625 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2626 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 2627 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2629 */     _jspx_th_c_005fout_005f38.setValue("${pageid}");
/* 2630 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 2631 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 2632 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 2633 */       return true;
/*      */     }
/* 2635 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 2636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2641 */     PageContext pageContext = _jspx_page_context;
/* 2642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2644 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2645 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 2646 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2648 */     _jspx_th_c_005fout_005f39.setValue("${pageid}");
/* 2649 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 2650 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 2651 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 2652 */       return true;
/*      */     }
/* 2654 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 2655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2660 */     PageContext pageContext = _jspx_page_context;
/* 2661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2663 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2664 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 2665 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2667 */     _jspx_th_c_005fout_005f40.setValue("${pageid}");
/* 2668 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 2669 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 2670 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 2671 */       return true;
/*      */     }
/* 2673 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 2674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2679 */     PageContext pageContext = _jspx_page_context;
/* 2680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2682 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2683 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2684 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2686 */     _jspx_th_c_005fif_005f10.setTest("${fromWhere=='mgtemplate'}");
/* 2687 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2688 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2690 */         out.write("\n<div>\n\t\t<div class=\"new-report-text padd-tbtm-7px\">");
/* 2691 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2692 */           return true;
/* 2693 */         out.write("</div>\n\t\t");
/* 2694 */         out.write("\n\n\n</div>\n");
/* 2695 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2696 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2700 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2701 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2702 */       return true;
/*      */     }
/* 2704 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2705 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2710 */     PageContext pageContext = _jspx_page_context;
/* 2711 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2713 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2714 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 2715 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2717 */     _jspx_th_c_005fout_005f41.setValue("${MyPageForm.displayName}");
/* 2718 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 2719 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 2720 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 2721 */       return true;
/*      */     }
/* 2723 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 2724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2729 */     PageContext pageContext = _jspx_page_context;
/* 2730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2732 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2733 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2734 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 2735 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2736 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 2738 */         out.write(10);
/* 2739 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2740 */           return true;
/* 2741 */         out.write(10);
/* 2742 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2743 */           return true;
/* 2744 */         out.write(10);
/* 2745 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2746 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2750 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2751 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2752 */       return true;
/*      */     }
/* 2754 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2760 */     PageContext pageContext = _jspx_page_context;
/* 2761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2763 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2764 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2765 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2767 */     _jspx_th_c_005fwhen_005f5.setTest("${publishpage==\"true\"}");
/* 2768 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2769 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2771 */         out.write("\n<table cellspacing=\"0\" cellpadding=\"5\" width=\"100%\"  id=\"NavBar\" border=\"0\">\n");
/* 2772 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2773 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2777 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2778 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2779 */       return true;
/*      */     }
/* 2781 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2782 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2787 */     PageContext pageContext = _jspx_page_context;
/* 2788 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2790 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2791 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2792 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 2793 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2794 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2796 */         out.write("\n<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"  id=\"NavBar\" border=\"0\">\n");
/* 2797 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2802 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2803 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2804 */       return true;
/*      */     }
/* 2806 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2812 */     PageContext pageContext = _jspx_page_context;
/* 2813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2815 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2816 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 2817 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2819 */     _jspx_th_c_005fout_005f42.setValue("${widgetcolumn}");
/* 2820 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 2821 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 2822 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 2823 */       return true;
/*      */     }
/* 2825 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 2826 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2831 */     PageContext pageContext = _jspx_page_context;
/* 2832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2834 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2835 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 2836 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2838 */     _jspx_th_c_005fout_005f43.setValue("${colcounter.count}");
/* 2839 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 2840 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 2841 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 2842 */       return true;
/*      */     }
/* 2844 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 2845 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2850 */     PageContext pageContext = _jspx_page_context;
/* 2851 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2853 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2854 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 2855 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2857 */     _jspx_th_c_005fout_005f44.setValue("${widget[0]}");
/* 2858 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 2859 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 2860 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 2861 */       return true;
/*      */     }
/* 2863 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 2864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2869 */     PageContext pageContext = _jspx_page_context;
/* 2870 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2872 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2873 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 2874 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2876 */     _jspx_th_c_005fout_005f45.setValue("${widget[0]}");
/* 2877 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 2878 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 2879 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 2880 */       return true;
/*      */     }
/* 2882 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 2883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2888 */     PageContext pageContext = _jspx_page_context;
/* 2889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2891 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2892 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 2893 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2895 */     _jspx_th_c_005fout_005f46.setValue("${widget[1]}");
/* 2896 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 2897 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 2898 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 2899 */       return true;
/*      */     }
/* 2901 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 2902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2907 */     PageContext pageContext = _jspx_page_context;
/* 2908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2910 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2911 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 2912 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2914 */     _jspx_th_c_005fout_005f47.setValue("${widget[0]}");
/* 2915 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 2916 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 2917 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 2918 */       return true;
/*      */     }
/* 2920 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 2921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2926 */     PageContext pageContext = _jspx_page_context;
/* 2927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2929 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2930 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 2931 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2933 */     _jspx_th_c_005fout_005f48.setValue("${widget[0]}");
/* 2934 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 2935 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 2936 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 2937 */       return true;
/*      */     }
/* 2939 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 2940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2945 */     PageContext pageContext = _jspx_page_context;
/* 2946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2948 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2949 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 2950 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2952 */     _jspx_th_c_005fout_005f49.setValue("${widget[0]}");
/* 2953 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 2954 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 2955 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 2956 */       return true;
/*      */     }
/* 2958 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 2959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2964 */     PageContext pageContext = _jspx_page_context;
/* 2965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2967 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2968 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 2969 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2971 */     _jspx_th_c_005fout_005f50.setValue("${widget[0]}");
/* 2972 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 2973 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 2974 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 2975 */       return true;
/*      */     }
/* 2977 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 2978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2983 */     PageContext pageContext = _jspx_page_context;
/* 2984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2986 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2987 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 2988 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 2990 */     _jspx_th_c_005fout_005f51.setValue("${widget[0]}");
/* 2991 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 2992 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 2993 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 2994 */       return true;
/*      */     }
/* 2996 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 2997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3002 */     PageContext pageContext = _jspx_page_context;
/* 3003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3005 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3006 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 3007 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 3009 */     _jspx_th_c_005fout_005f52.setValue("${widget[0]}");
/* 3010 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 3011 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 3012 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 3013 */       return true;
/*      */     }
/* 3015 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 3016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3021 */     PageContext pageContext = _jspx_page_context;
/* 3022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3024 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3025 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 3026 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3028 */     _jspx_th_c_005fout_005f53.setValue("${widget[0]}");
/* 3029 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 3030 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 3031 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 3032 */       return true;
/*      */     }
/* 3034 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 3035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3040 */     PageContext pageContext = _jspx_page_context;
/* 3041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3043 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3044 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3045 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3047 */     _jspx_th_c_005fif_005f15.setTest("${publishpage==\"true\"}");
/* 3048 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3049 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 3051 */         out.write("\n\t<br>\n\t");
/* 3052 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3053 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3057 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3058 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3059 */       return true;
/*      */     }
/* 3061 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3067 */     PageContext pageContext = _jspx_page_context;
/* 3068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3070 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3071 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 3072 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3074 */     _jspx_th_c_005fout_005f54.setValue("${widget[0]}");
/* 3075 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 3076 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 3077 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 3078 */       return true;
/*      */     }
/* 3080 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 3081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3086 */     PageContext pageContext = _jspx_page_context;
/* 3087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3089 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3090 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 3091 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3093 */     _jspx_th_c_005fout_005f55.setValue("${widgetcolumn}");
/* 3094 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 3095 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 3096 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 3097 */       return true;
/*      */     }
/* 3099 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 3100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3105 */     PageContext pageContext = _jspx_page_context;
/* 3106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3108 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3109 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3110 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3112 */     _jspx_th_c_005fif_005f16.setTest("${isPopup=='true'}");
/* 3113 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3114 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 3116 */         out.write("\n</body>\n</html>\n");
/* 3117 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3118 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3122 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3123 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3124 */       return true;
/*      */     }
/* 3126 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3132 */     PageContext pageContext = _jspx_page_context;
/* 3133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3135 */     com.adventnet.appmanager.tags.LoadTime _jspx_th_am_005ftimestamp_005f0 = (com.adventnet.appmanager.tags.LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(com.adventnet.appmanager.tags.LoadTime.class);
/* 3136 */     _jspx_th_am_005ftimestamp_005f0.setPageContext(_jspx_page_context);
/* 3137 */     _jspx_th_am_005ftimestamp_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/* 3138 */     int _jspx_eval_am_005ftimestamp_005f0 = _jspx_th_am_005ftimestamp_005f0.doStartTag();
/* 3139 */     if (_jspx_th_am_005ftimestamp_005f0.doEndTag() == 5) {
/* 3140 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 3141 */       return true;
/*      */     }
/* 3143 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 3144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3149 */     PageContext pageContext = _jspx_page_context;
/* 3150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3152 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3153 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3154 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/* 3155 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3156 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3157 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3158 */         out = _jspx_page_context.pushBody();
/* 3159 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3160 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3163 */         out.write("milliseconds");
/* 3164 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3165 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3168 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3169 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3172 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3173 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3174 */       return true;
/*      */     }
/* 3176 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3182 */     PageContext pageContext = _jspx_page_context;
/* 3183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3185 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 3186 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 3187 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3189 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${systime}");
/*      */     
/* 3191 */     _jspx_th_fmt_005fformatDate_005f0.setType("BOTH");
/* 3192 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 3193 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 3194 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3195 */       return true;
/*      */     }
/* 3197 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3203 */     PageContext pageContext = _jspx_page_context;
/* 3204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3206 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3207 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3208 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3210 */     _jspx_th_c_005fif_005f18.setTest("${not empty pageid}");
/* 3211 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3212 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 3214 */         out.write(10);
/* 3215 */         out.write(9);
/* 3216 */         out.write(9);
/* 3217 */         if (_jspx_meth_c_005fif_005f19(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 3218 */           return true;
/* 3219 */         out.write(10);
/* 3220 */         out.write(9);
/* 3221 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3222 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3226 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3227 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3228 */       return true;
/*      */     }
/* 3230 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3236 */     PageContext pageContext = _jspx_page_context;
/* 3237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3239 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3240 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3241 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 3243 */     _jspx_th_c_005fif_005f19.setTest("${publishpage!=\"true\"}");
/* 3244 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3245 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 3247 */         out.write("\n\t\t\tloadDragandDrop();//in appmanager.js\n\t\t");
/* 3248 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3249 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3253 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3254 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3255 */       return true;
/*      */     }
/* 3257 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3263 */     PageContext pageContext = _jspx_page_context;
/* 3264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3266 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3267 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3268 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3270 */     _jspx_th_c_005fif_005f20.setTest("${publishpage eq true}");
/* 3271 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3272 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 3274 */         out.write("\n,\n$('a').live('click', function(e) \n{\n\treturn false;\n})\n");
/* 3275 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3280 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3281 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3282 */       return true;
/*      */     }
/* 3284 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3290 */     PageContext pageContext = _jspx_page_context;
/* 3291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3293 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3294 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3295 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3297 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3299 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3300 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3301 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3302 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3303 */       return true;
/*      */     }
/* 3305 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3306 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyPage_005fContainer_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */