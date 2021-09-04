/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MyPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   22 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   28 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   29 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   30 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
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
/*   53 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   57 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   73 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   77 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   78 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   81 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*   82 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   83 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   84 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   87 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   89 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*   91 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   98 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  101 */     JspWriter out = null;
/*  102 */     Object page = this;
/*  103 */     JspWriter _jspx_out = null;
/*  104 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  108 */       response.setContentType("text/html");
/*  109 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  111 */       _jspx_page_context = pageContext;
/*  112 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  113 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  114 */       session = pageContext.getSession();
/*  115 */       out = pageContext.getOut();
/*  116 */       _jspx_out = out;
/*      */       
/*  118 */       out.write(10);
/*  119 */       out.write(10);
/*  120 */       out.write("\n\n\n\n\n\n\n");
/*  121 */       com.adventnet.appmanager.struts.beans.MyPageBean mygraph = null;
/*  122 */       mygraph = (com.adventnet.appmanager.struts.beans.MyPageBean)_jspx_page_context.getAttribute("mygraph", 1);
/*  123 */       if (mygraph == null) {
/*  124 */         mygraph = new com.adventnet.appmanager.struts.beans.MyPageBean();
/*  125 */         _jspx_page_context.setAttribute("mygraph", mygraph, 1);
/*      */       }
/*  127 */       out.write("\n\n\n\n<script type=\"text/javascript\" src=\"/template/TabDrag.js\"></script>\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n");
/*  128 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  130 */       out.write("\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/tooltip.js\"></script>\n<html>\n<head>\n");
/*  131 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */         return;
/*  133 */       out.write("\n</head>\n\n");
/*  134 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */         return;
/*  136 */       out.write("\n<body>\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n");
/*      */       
/*  138 */       SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  139 */       _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  140 */       _jspx_th_c_005fset_005f0.setParent(null);
/*      */       
/*  142 */       _jspx_th_c_005fset_005f0.setVar("isReadOnly");
/*  143 */       int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  144 */       if (_jspx_eval_c_005fset_005f0 != 0) {
/*  145 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/*  146 */           out = _jspx_page_context.pushBody();
/*  147 */           _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  148 */           _jspx_th_c_005fset_005f0.doInitBody();
/*      */         }
/*      */         for (;;) {
/*  151 */           out.print(com.adventnet.appmanager.util.DashboardUtil.isReadOnlyVewForUser(request.getRemoteUser(), (String)request.getAttribute("pageid")));
/*  152 */           int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  153 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  156 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/*  157 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  160 */       if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  161 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*      */       }
/*      */       else {
/*  164 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  165 */         out.write(10);
/*      */         
/*  167 */         if ((com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") == null))
/*      */         {
/*  169 */           response.sendRedirect("/showIT360Tile.do?TileName=IT360.CustomersSummary");
/*      */         }
/*      */         
/*  172 */         out.write(10);
/*  173 */         out.write(10);
/*      */         try
/*      */         {
/*  176 */           String selectedTab = "0";
/*  177 */           String productUrl = "localhost:9090";
/*      */           try
/*      */           {
/*  180 */             productUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
/*      */           }
/*      */           catch (Exception e) {
/*  183 */             e.printStackTrace();
/*      */           }
/*      */           
/*  186 */           out.write(10);
/*      */           
/*  188 */           if (Boolean.parseBoolean(com.adventnet.appmanager.util.Constants.expandWidgets))
/*      */           {
/*  190 */             out.write(10);
/*  191 */             out.write(9);
/*  192 */             if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
/*      */               return;
/*  194 */             out.write(10);
/*      */           }
/*      */           
/*  197 */           out.write("\n\n<script>\n\n\nvar widgetid = '';\nvar frameloaded = true;\nvar widgetorder = 0;\n\nwindow.onbeforeunload = function() {\n\n    if(!frameloaded){\n\n        var url = \"/MyPage.do?method=deleteembedurl&widgetid=\"+widgetid;\t\t//No I18N\n       \tvar http1=getHTTPObject();\n    \thttp1.onreadystatechange =function(){} ;\n    \thttp1.open(\"GET\",url, true);\n    \thttp1.send(null);\n\n   \treturn '");
/*  198 */           out.print(FormatUtil.getString("am.mypage.embed.url.delete.message"));
/*  199 */           out.write("'\n\n    }\n\n  }\n\n\nfunction frameonload(framestatus,widget){\n\n\twidgetid = widget;\n\n\tif(framestatus == 'editpagesubmit'){\n\n\t\tframeloaded = false;\n\n\t}else {\n\n\tframeloaded = true;\n}\n\n}\n\nfunction editWidget(widgetid)\n{\nfnOpenNewWindowWithHeightWidthPlacement('/MyPage.do?method=editWidget&pageid=");
/*  200 */           if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */             return;
/*  202 */           out.write("&widgetid='+widgetid,'1000','700',5,5); //No I18N\n}\nfunction setSize(widgetid,colWidth)\n{\n\t\n\tif ($(\"#\"+widgetid).length == 0){\n\t\t// return if the widget is not present\n\t\treturn;\n\t}\n\t\n var screenWidthAvailable  =");
/*  203 */           if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */             return;
/*  205 */           out.write(";\n var refblock = jQuery(\"#userAreaContainerDiv\").length?jQuery(\"#userAreaContainerDiv\"):jQuery('#containerDiv');\t//NO I18N\n // In popout actions containderDiv will be parent, elsewhere userAreaContainderDiv\n var scrWidth = (refblock.width() * screenWidthAvailable)/100 ;\t//NO I18N\n\n\t");
/*  206 */           if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */             return;
/*  208 */           out.write("\n  \tvar widDivWidth = parseInt((scrWidth*colWidth/100)-22);//No I18N\n  \tif(navigator.userAgent.indexOf(\"Chrome\")!=-1){\n  \t\tif(colWidth==40){\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-16);//No I18N\n  \t\t}\n  \t\telse if(colWidth == 25)\n  \t\t{\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-49);//No I18N\n  \t\t}\n  \t\telse{\n  \t\t\twidDivWidth = parseInt((scrWidth*colWidth/100)-18);//No I18N\n  \t\t}\n  \t\t\n  \t}\n  \t\n  \t//review:document.getElementById(widgetid).offsetParent.width = widDivWidth+\"px\";\n  \tdocument.getElementById(widgetid).style.width =  widDivWidth+\"px\";//document.getElementById(widgetID).offsetParent.offsetWidth;\n\tdocument.getElementById(widgetid).style.overflowX=\"auto\";\n\n\t");
/*  209 */           if (_jspx_meth_c_005fset_005f2(_jspx_page_context))
/*      */             return;
/*  211 */           out.write(10);
/*  212 */           out.write(9);
/*  213 */           out.write(9);
/*  214 */           if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */             return;
/*  216 */           out.write("\n\t  //      document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;padding:10px;height:180px'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\";\n\n}\n\nfunction setAsDefault(resourceid,forpage)\n{\n\tif(resourceid==null)\n\t{\n\t\tresourceid='0';//No I18N\n\t}\n\tif(forpage==null)\n\t{\n\t\tforpage='1';//No I18N\n\t}\n\tdocument.location.href='MyPage.do?method=setAsDefault&resourceid='+resourceid+'&dashboardtype=1&forpage='+forpage+'&pageid=");
/*  217 */           if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */             return;
/*  219 */           out.write("';\n}\nfunction publishDashboard(resourceid)\n{\n\tif(resourceid!=null)\n\t{\n\t\tshowURLInDialog('/MyPage.do?method=getPublishedKey&pageid=");
/*  220 */           if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */             return;
/*  222 */           out.write("&template_resid='+resourceid,'position=relative,srcElement=publishMghook,left=50,top=-10,title=");
/*  223 */           out.print(FormatUtil.getString("am.mypage.dashboard.public.dashboard.text"));
/*  224 */           out.write("');//No I18N\n\t}\n\telse\n\t{\n\t\t showURLInDialog('/MyPage.do?method=getPublishedKey&pageid=");
/*  225 */           if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*      */             return;
/*  227 */           out.write("','position=relative,srcElement=publishMghook,left=50,top=-10,title=");
/*  228 */           out.print(FormatUtil.getString("am.mypage.dashboard.public.dashboard.text"));
/*  229 */           out.write("');//No I18N\n\n\t}\n}\nfunction editMyPage(resourceid)\n{\n\tif(resourceid==null)\n\t{\n\t\t document.location.href='/MyPage.do?method=editMyPage&pageid=");
/*  230 */           if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */             return;
/*  232 */           out.write("&selectedTab=");
/*  233 */           out.print(selectedTab);
/*  234 */           out.write("';\n\t}\n\telse\n\t{\n\t\tdocument.location.href='/MyPage.do?template_resid='+resourceid+'&method=editMyPage&pageid=");
/*  235 */           if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */             return;
/*  237 */           out.write("&selectedTab=");
/*  238 */           out.print(selectedTab);
/*  239 */           out.write("';\n\t}\n}\nfunction getContent1(widgetid,colWidth)\n{\n//This method is called from js file:dont delete\n      setSize(widgetid,colWidth);\n      getContent2(widgetid);\n}\n\nfunction getContentwithDelay(widgetid,colWidth,delay)\n{\n\t\twidgetorder += 1;\n      setSize(widgetid,colWidth);\n      setTimeout('getContent2(\\\"'+widgetid+'\\\")',delay);\n\n}\n\nfunction getContent(widgetid,colWidth)\n{\n      setSize(widgetid,colWidth);\n      getContent2(widgetid);\n}\nfunction getContent2(widgetid)\n{\n\tvar pageid='");
/*  240 */           if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*      */             return;
/*  242 */           out.write("';\n\t");
/*  243 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*      */             return;
/*  245 */           out.write("\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n\nfunction getContent3(widgetid)\n{\n //       alert(\"getContent3 called...\");\n        ");
/*  246 */           if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
/*      */             return;
/*  248 */           out.write(10);
/*  249 */           out.write(9);
/*  250 */           if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */             return;
/*  252 */           out.write("\n\n \tvar pageid='");
/*  253 */           if (_jspx_meth_c_005fout_005f18(_jspx_page_context))
/*      */             return;
/*  255 */           out.write("';\n        var widDivWidth = document.getElementById(widgetid).offsetWidth;\n\tvar height = document.getElementById(widgetid).offsetHeight;\n\tvar imageLoc = parseInt((height-16)/2);\n\theight -= imageLoc;\n        document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;height:\"+height+\"px;margin-top:\"+imageLoc+\"px;'><img src='/images/LoadingTC.gif' style=''/></div>\";\n        ");
/*  256 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_page_context))
/*      */             return;
/*  258 */           out.write("\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n//this is duplicate function for the function getContent3 but will accept params\nfunction getContentwithParams(widgetid,params)\n{\n\t");
/*  259 */           if (_jspx_meth_c_005fset_005f6(_jspx_page_context))
/*      */             return;
/*  261 */           out.write(10);
/*  262 */           out.write(9);
/*  263 */           if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */             return;
/*  265 */           out.write("\n\n \tvar pageid='");
/*  266 */           if (_jspx_meth_c_005fout_005f23(_jspx_page_context))
/*      */             return;
/*  268 */           out.write("';\n        var widDivWidth = document.getElementById(widgetid).offsetWidth;\n\tvar height = document.getElementById(widgetid).offsetHeight;\n\tvar imageLoc = parseInt((height-16)/2);\n\theight -= imageLoc;\n        document.getElementById(widgetid).innerHTML=\"<div style='text-align:center;height:\"+height+\"px;margin-top:\"+imageLoc+\"px;'><img src='/images/LoadingTC.gif' style=''/></div>\";\n        ");
/*  269 */           if (_jspx_meth_c_005fchoose_005f3(_jspx_page_context))
/*      */             return;
/*  271 */           out.write("\n\tif(params!=null)\n\t{\n\turl=url+params;\n\t}\n\tvar http1=getHTTPObject();\n\thttp1.onreadystatechange =function () { setContent(http1,widgetid);} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n}\n\n\n\nfunction deleteWidget(widgetid)\n{\n        ");
/*  272 */           if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */             return;
/*  274 */           out.write("\n        ");
/*      */           
/*  276 */           org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  277 */           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  278 */           _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */           
/*  280 */           _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  281 */           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  282 */           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */             for (;;) {
/*  284 */               out.write("\n        if(!confirm(\"");
/*  285 */               out.print(FormatUtil.getString("am.mypage.widget.delete.confirm.text"));
/*  286 */               out.write("\"))\n        {\n        return;\n        }\n        url=\"/MyPage.do?method=deleteWidget&widgetid=\"+widgetid+\"&pageid=");
/*  287 */               if (_jspx_meth_c_005fout_005f27(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                 return;
/*  289 */               out.write("\";\n\tvar http2=getHTTPObject();\n\thttp2.onreadystatechange =function(){} ;\n\thttp2.open(\"GET\", url, true);\n\thttp2.send(null);\n\tdocument.getElementById(widgetid+\"_\").innerHTML = \"\";\n\t ");
/*  290 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  291 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  295 */           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  296 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */           }
/*      */           
/*  299 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  300 */           out.write("\n}\nfunction setContent(http1,widgetid)\n{\n\tif(http1.readyState == 4)\n \t{\n       \t   if( http1.status == 200)\n\t   {\n\t\tdocument.getElementById(widgetid).innerHTML = http1.responseText;\n\t\twidgetorder -= 1;\n\n\t\t$(\"#graphArea img\").load(function(){\n\t\t\t$(this).attr('width', $(this).width());// NO I18N\n\t\t\t$(this).attr('height', $(this).height());// NO I18N\n\t\t});\n\t\t\n\t\tif(widgetorder == 0 ){\n\t\t\tvar hashValue = document.location.hash\n\t\t\tif(hashValue == '#scrollWidget'){\n\t\t\tdocument.location.hash = '';\n\t\t\tsetTimeout(function() {\n\t\t\t\t\tvar widgetToScroll = '");
/*  301 */           if (_jspx_meth_c_005fout_005f28(_jspx_page_context))
/*      */             return;
/*  303 */           out.write("_'\n\t\t\t\t\tjQuery(window).scrollTop(jQuery('#'+widgetToScroll).offset().top);\t// NO I18N\n\t\t\t},500);\n\t\t\t\n\t\t\t}\n\t\t}\n\t\t//document.getElementById(widgetid).style.display='inline';\n\t\t//To load script inside the response JSP Page to this page's head tag. Follows ...\n\t\tvar innercontent = document.getElementById(widgetid);\n\t\tvar scriptTags = innercontent.getElementsByTagName(\"SCRIPT\");\n\t\tfor (var i = 0; i < scriptTags.length; i++)\n\t\t{\n\t\t\tvar scriptTag = document.createElement(\"SCRIPT\");\n\t\t\tscriptTag.type = \"text/javascript\";\n\t\t\tscriptTag.language = \"javascript\";\n\t\t\tif (scriptTags[i].src != \"\") { scriptTag.src = scriptTags[i].src;}\n\t\t\tscriptTag.text = scriptTags[i].text;\n\t\t\tif (typeof document.getElementsByTagName(\"HEAD\")[0] == \"undefined\")\n\t\t\t{\n\t\t\t\tdocument.createElement(\"HEAD\").appendChild(scriptTag)\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementsByTagName(\"HEAD\")[0].appendChild(scriptTag);\n\t\t\t}\n\t\t} // Ends loading script.\n\t   }\n\t }\n}\n\nfunction addWidgets()\n{\nfnOpenNewWindowWithHeightWidthPlacement('/MyPage.do?method=newWidgets&isPopup=true&pageid=");
/*  304 */           if (_jspx_meth_c_005fout_005f29(_jspx_page_context))
/*      */             return;
/*  306 */           out.write("','900','610',15,15);\t//No I18N\n}\n\nfunction setDisplayName(widgetid,name)\n{\ndocument.getElementById(\"widgetname#\"+widgetid).innerHTML=name;\n//alert(window.opener.document.getElementById(\"widgetname#");
/*  307 */           if (_jspx_meth_c_005fout_005f30(_jspx_page_context))
/*      */             return;
/*  309 */           out.write("\").innerHTML);\n\n}\n//script for menu\n\nvar hideDiv = \"\";\n\nfunction hideDropDiv(e){\nif (document.getElementById(e)!= \"null\" && document.getElementById!=null && document.getElementById != \"\")\n{\n\tdocument.getElementById(e).style.display = \"none\";\n}\n}\n\nfunction delayhideDropDiv(msec,whereto){\nclearhideDropDiv()\ndelayDropDiv=setTimeout(\"hideDropDiv('\"+whereto+\"')\",msec)\n}\n\n\nfunction clearhideDropDiv(){\nif (typeof delayDropDiv!=\"undefined\")\nclearTimeout(delayDropDiv)\n}\n\n\nfunction showthemall(id1)\n {\n\tvar disp = document.getElementById(id1);\n\tif (disp.style.display == '' || disp.style.display == 'none')\n\t{\n\t\teval(\"disp.style.display = 'block'\");\n\t}else\n\t{\n\t\teval(\"disp.style.display = 'none'\");\n\t}\n }\n//script for menu\n\n\n");
/*  310 */           out.write("\n\nfunction dragDropCallBack(res)\n{\n\tupdatePosition(\"NavBar\");\n}\n\n\nfunction updatePosition(sd)\n{\n\tvar items = document.getElementById(sd).getElementsByTagName(\"div\");\n\tvar colCounts = document.getElementById(sd).getElementsByTagName(\"td\");\n\tvar positionArray = new Array();\n\tvar classCount = ");
/*  311 */           if (_jspx_meth_c_005fout_005f31(_jspx_page_context))
/*      */             return;
/*  313 */           out.write(";\n\n\tvar sfr = new Array();\n\n\tfor (var i = 0; i<classCount; i++)\n\t{\n\t\tvar coun = \"col\"+(i+1);\n\t\tsfr = document.getElementById(coun).getElementsByTagName(\"div\");\n\t\tnewloc = i;\t\t\t\t\t\t\t\t//location of the first widget is same as columns index\n\t\tfor (var j = 0; j < sfr.length; j++)\n\t\t{\n\t\t\tif(sfr[j].className == 'tabdiv gridItem' && sfr[j].style.display == 'block')\n\t\t\t{\n\t\t\t\tpositionArray.push(sfr[j].getAttribute(\"id\")+newloc);\n\t\t\t\tnewloc+=classCount;\n\t\t\t}\n\t\t}\n\t}\n\n\tvar url = \"/MyPage.do?method=saveMyPageLayout&\";\n\turl= url+\"pageid=");
/*  314 */           if (_jspx_meth_c_005fout_005f32(_jspx_page_context))
/*      */             return;
/*  316 */           out.write("&positionArray=\"+positionArray+\"&sid=\"+Math.random();\n\tvar http3=getHTTPObject();\n\thttp3.onreadystatechange =function(){} ;\n\thttp3.open(\"GET\", url, true);\n\thttp3.send(null);\n}\n\nfunction popOut(template_resid)\n{\n\tvar popoutwindow;\n\tif(template_resid!=null)\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&template_resid='+template_resid+'&pageid=");
/*  317 */           if (_jspx_meth_c_005fout_005f33(_jspx_page_context))
/*      */             return;
/*  319 */           out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n\telse\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&pageid=");
/*  320 */           if (_jspx_meth_c_005fout_005f34(_jspx_page_context))
/*      */             return;
/*  322 */           out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n}\n\n//script from opmanager drag and drop ends\nfunction getNewBookMarkForm(widgetid,holder,bookmarkid)\n{\n    if(document.getElementById(\"widgetBokmarks_DivContainer\").style.display==\"none\"){\n    var http2=getHTTPObject();\n    http2.onreadystatechange=  function(){AjaxCallForbookMark(http2,widgetid,holder);};//No I18N\n    URL=\"/MyPage.do?method=getBookMarkForm&widgetid=\"+widgetid+\"&bookmarkid=\"+bookmarkid+\"&pageid=");
/*  323 */           if (_jspx_meth_c_005fout_005f35(_jspx_page_context))
/*      */             return;
/*  325 */           out.write("\";//No I18N\n    http2.open(\"GET\",URL,true);//No I18N\n    http2.send(null);\n    }\n    //else\n    //{\n\t//\n    //}\n\n}\nfunction AjaxCallForbookMark(http2,widgetid,holder)\n{\nif(http2.readyState == 4)\n \t{\n       \t   if( http2.status == 200)\n\t   {\n\t   document.getElementById(\"widgetBokmarks_DivContainer\").innerHTML=http2.responseText;//No I18N\n\t   //showFloatpopUp(holder,'widgetBokmarks_DivContainer_'+widgetid);//No I18N\n\t   var holderObj = document.getElementById(holder);//No I18N\n\t   var scrWidth = screen.width;//No I18N\n\t   var scrHeight = screen.height;    //No I18N\n\t   var posX = findPosX(holderObj)-62;//No I18N\n\t   var posY = findPosY(holderObj)-62;//No I18N\n\t   if(scrWidth-posX<500)\n\t   {\n\t     posX=scrWidth-600;\t //No I18N\n\t   }\n\t   if(posX<0)\n\t   {\n\t     posX=20;\t//No I18N\n\t   }\n\n\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.position=\"absolute\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.left=posX+\"px\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.top=posY+\"px\";//No I18N\n");
/*  326 */           out.write("\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.zindex=\"100\";//No I18N\n\t    document.getElementById(\"widgetBokmarks_DivContainer\").style.display=\"block\";//No I18N\n\t   }\n\t}\n\n}\n\nfunction showFloatpopUp(holder,widgetid)\n{\n        var http1=getHTTPObject();//No I18N\n        var url=\"/MyPage.do?method=getWidgetProperties&widgetid=\"+widgetid;//No I18N\n        \n        ");
/*  327 */           if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */             return;
/*  329 */           out.write("\n        \t\n        http1.onreadystatechange = function (){showDescription(http1,holder);};//No I18N\n\thttp1.open(\"GET\", url, true);//No I18N\n\thttp1.send(null);\n\n\n}\n\nfunction showDescription(http1,holder)\n{\n        if(http1.readyState == 4)\n\t{\n\tif( http1.status == 200)\n\t{\n\n         document.getElementById(\"widgetDescriptionDiv\").innerHTML=http1.responseText;//No I18N\n         var holderObj = document.getElementById(holder);//No I18N\n         var scrWidth = screen.width;     //No I18N\n\t var posX = findPosX(holderObj)-62;//No I18N\n\t var posY = findPosY(holderObj)-1;//No I18N\n\t if(scrWidth-posX<500)\n\t {\n\t posX=scrWidth-600;\t //No I18N\n\t }\n\t var finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\t showDialog(document.getElementById(\"widgetDescriptionDiv\").innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n\t}\n\t}\n\n}\nfunction saveBookmark()\n{\n        var url='/MyPage.do';//No I18N\n\tvar pageid='");
/*  330 */           if (_jspx_meth_c_005fout_005f37(_jspx_page_context))
/*      */             return;
/*  332 */           out.write("';//No I18N\n\tvar urltosave=document.urlform.url.value;//No I18N\n\tvar diaplayname=document.urlform.displayName.value;//No I18N\n\tvar widgetid=document.urlform.widgetid.value;//No I18N\n\tvar bookmarkid=document.urlform.bookmarkid.value;//No I18N\n\tvar description=document.urlform.description.value;//No I18N\n\tif(diaplayname.length == 0){\n\t\talert('");
/*  333 */           out.print(FormatUtil.getString("am.mypage.bookmark.displayname.alert.text"));
/*  334 */           out.write("')\n\t\treturn;\n\t}\t\n\tvar http1=getHTTPObject();//No I18N\n\n\thttp1.onreadystatechange = function (){bookmarkEdit(http1,widgetid);};//No I18N\n\thttp1.open(\"POST\", url, true);//No I18N\n\n\n\thttp1.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded\");//No I18N\n\thttp1.send('method=saveBookMark&widgetid='+widgetid+'&pageid='+pageid+'&bookmarkid='+bookmarkid+'&url='+escape(urltosave)+'&displayname='+escape(diaplayname)+'&description='+escape(description));//No I18N\n\n}\nfunction bookmarkEdit(http1,widgetid)\n{\n  if(http1.readyState == 4)\n   {\n       if( http1.status == 200)\n\t{\n\tdocument.getElementById('widgetBokmarks_DivContainer').style.display='none';//No I18N\n\tsetContent(http1,widgetid);//No I18N\n\t}\n   }\n}\nfunction deleteBookMark(widgetid,bookmarkid)\n{\n      if(!confirm(\"");
/*  335 */           out.print(FormatUtil.getString("am.mypage.bookmarkdelete.confirm.text"));
/*  336 */           out.write("\"))\n        {\n        return;//No I18N\n        }\n    var http2=getHTTPObject();//No I18N\n    http2.onreadystatechange=  function(){bookmarkEdit(http2,widgetid);};//No I18N\n    URL=\"/MyPage.do?method=deleteBookMark&widgetid=\"+widgetid+\"&bookmarkid=\"+bookmarkid+\"&pageid=");
/*  337 */           if (_jspx_meth_c_005fout_005f38(_jspx_page_context))
/*      */             return;
/*  339 */           out.write("\";//No I18N\n    http2.open(\"GET\",URL,true);//No I18N\n    http2.send(null);\n}\n</script>\n\n<style>\ntable#dropMenu{font-family:Arial, Helvetica, sans-serif; font-size:11px; border:1px #8eadd5 solid; width:150px;}\ntable#dropMenu tr td { background:#ffffff;  border-bottom:1px #BBCEE2 solid; }\ntable#dropMenu tr td a { color:#000000;text-decoration:none;display:block;padding:3px 6px 3px 8px; width:150px; }\ntable#dropMenu tr td a:hover { color:#000000; background:#eff9fe;  text-decoration:none;display:block; width:150px; }\ntable#dropMenu tr td>a:hover { width:146px; }\n</style>\n\n<script>\nfunction showMenuInDialog(holder, source) {\n\tvar holderObj = document.getElementById(holder);//No I18N\n\tvar posX = findPosX(holderObj)-62;//No I18N\n\tvar posY = findPosY(holderObj)-1;//No I18N\n\tvar finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\tshowDialog(document.getElementById(source).innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n}\nfunction deleteDashboard(template_resid)\n");
/*  340 */           out.write("{\nif(confirm('");
/*  341 */           out.print(FormatUtil.getString("am.mypage.delete.confirm.text"));
/*  342 */           out.write("'))\n{\n\tif(template_resid!=null)\n\t{\n\t\tdocument.location.href=\"/MyPage.do?template_resid=\"+template_resid+\"&method=deleteMyPage&pageid=");
/*  343 */           if (_jspx_meth_c_005fout_005f39(_jspx_page_context))
/*      */             return;
/*  345 */           out.write("\";//No I18N\n\t}\n\telse//No I18N\n\t{\n\t\tdocument.location.href=\"/MyPage.do?method=deleteMyPage&pageid=");
/*  346 */           if (_jspx_meth_c_005fout_005f40(_jspx_page_context))
/*      */             return;
/*  348 */           out.write("\";//No I18N\n\t}\n}\nelse\n{\nreturn;\n}\n}\n\nfunction toggleBookmarkImg(bookmarkid)\n{\n\t\tif(document.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display == 'inline')\n\t\t{\n\t\t\tdocument.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display='none';//No I18N\n\t\t\tdocument.getElementById(\"bookMarkEdit_\"+bookmarkid).style.display='none';//No I18N\n\t\t}\n\t\telse    //No I18N\n\t\t{\n\t\t\tdocument.getElementById(\"bookMarkDelete_\"+bookmarkid).style.display='inline';//No I18N\n\t\t\tdocument.getElementById(\"bookMarkEdit_\"+bookmarkid).style.display='inline';//No I18N\n\t\t}\n\n}\nfunction pickUnpickAlarm(widgetid,alarmid,pickorUnpick)\n{\nvar url='/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\nif(pickorUnpick=='pick')\n{\nvar url='/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\n}\nelse if(pickorUnpick=='unpick')\n{\nvar url='/fault/AlarmOperations.do?methodCall=unPickAlarm&selectedEntity='+alarmid+'&redirectto=/index.do';\n}\n$.get(url, function(data) {//No I18N\n");
/*  349 */           out.write("getContent3(widgetid)//No I18N\n});\n}\n</script>\n\n<!--For Tabs -->\n");
/*      */           
/*  351 */           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  352 */           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  353 */           _jspx_th_c_005fif_005f9.setParent(null);
/*      */           
/*  355 */           _jspx_th_c_005fif_005f9.setTest("${fromWhere=='dashboardpage'}");
/*  356 */           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  357 */           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */             for (;;) {
/*  359 */               out.write(10);
/*      */               
/*  361 */               String pagenames = "";
/*  362 */               String pageids = "";
/*  363 */               String functions = "";
/*  364 */               String tabs = "";
/*  365 */               java.util.ArrayList dashboardsinOrder = (java.util.ArrayList)request.getAttribute("dashboardsinOrder");
/*  366 */               selectedTab = (String)request.getAttribute("selectedTab");
/*      */               
/*  368 */               for (int i = 0; i < dashboardsinOrder.size(); i++)
/*      */               {
/*  370 */                 java.util.ArrayList singlerow = (java.util.ArrayList)dashboardsinOrder.get(i);
/*  371 */                 String pageid = (String)singlerow.get(0);
/*  372 */                 int pageid_int = -1;
/*  373 */                 try { pageid_int = Integer.parseInt(pageid);
/*  374 */                 } catch (Exception ex) { ex.printStackTrace(); }
/*  375 */                 if (i == 0)
/*      */                 {
/*  377 */                   pagenames = (String)singlerow.get(1);
/*  378 */                   pageids = (String)singlerow.get(0);
/*  379 */                   functions = "getHomePage";
/*  380 */                   tabs = i + "";
/*      */ 
/*      */ 
/*      */                 }
/*  384 */                 else if (pageid_int >= 0)
/*      */                 {
/*      */ 
/*      */ 
/*  388 */                   pagenames = pagenames + "," + (String)singlerow.get(1);
/*  389 */                   pageids = pageids + "," + (String)singlerow.get(0);
/*  390 */                   functions = functions + ",getpage";
/*  391 */                   tabs = tabs + "," + i;
/*      */                 }
/*      */               }
/*      */               
/*  395 */               out.write(10);
/*  396 */               out.write(10);
/*  397 */               JspRuntimeLibrary.include(request, response, "/jsp/MyPage_Tabs.jsp" + ("/jsp/MyPage_Tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pagenames), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tabs", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabs), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pagenames), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(functions), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("pageids", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(pageids), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedTab), request.getCharacterEncoding()), out, false);
/*  398 */               out.write(10);
/*  399 */               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  400 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  404 */           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  405 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */           }
/*      */           
/*  408 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  409 */           out.write("\n<!--For Tabs -->\n\n");
/*      */           
/*  411 */           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  412 */           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  413 */           _jspx_th_c_005fchoose_005f4.setParent(null);
/*  414 */           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  415 */           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */             for (;;) {
/*  417 */               out.write(10);
/*      */               
/*  419 */               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  420 */               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  421 */               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */               
/*  423 */               _jspx_th_c_005fwhen_005f4.setTest("${not empty pageid}");
/*  424 */               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  425 */               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                 for (;;) {
/*  427 */                   out.write("\n<div id=\"Main\">\n<div id=\"containerDiv\" class=\"txtGlobal\">\n");
/*  428 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                     return;
/*  430 */                   out.write("\n<!--<div class=\"clearFT\"></div>\t-->\n");
/*  431 */                   if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                     return;
/*  433 */                   out.write("\n  <tr>\n  ");
/*      */                   
/*  435 */                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  436 */                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  437 */                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                   
/*  439 */                   _jspx_th_c_005fforEach_005f0.setVar("widgetcolumn");
/*      */                   
/*  441 */                   _jspx_th_c_005fforEach_005f0.setItems("${widgetcolumns}");
/*      */                   
/*  443 */                   _jspx_th_c_005fforEach_005f0.setVarStatus("colcounter");
/*  444 */                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                   try {
/*  446 */                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  447 */                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                       for (;;) {
/*  449 */                         out.write(10);
/*  450 */                         out.write(32);
/*  451 */                         out.write(32);
/*      */                         
/*  453 */                         String stylefortd = "";
/*      */                         
/*  455 */                         out.write(10);
/*  456 */                         out.write(32);
/*  457 */                         out.write(32);
/*      */                         
/*  459 */                         IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  460 */                         _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/*  461 */                         _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                         
/*  463 */                         _jspx_th_c_005fif_005f11.setTest("${colcounter.first}");
/*  464 */                         int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/*  465 */                         if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                           for (;;) {
/*  467 */                             out.write(10);
/*  468 */                             out.write(32);
/*  469 */                             out.write(32);
/*      */                             
/*  471 */                             stylefortd = "style='padding-left:0px;'";
/*      */                             
/*  473 */                             out.write(10);
/*  474 */                             out.write(32);
/*  475 */                             out.write(32);
/*  476 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/*  477 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  481 */                         if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/*  482 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*  485 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*  486 */                         out.write(10);
/*  487 */                         out.write(10);
/*  488 */                         out.write(32);
/*      */                         
/*  490 */                         IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  491 */                         _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/*  492 */                         _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                         
/*  494 */                         _jspx_th_c_005fif_005f12.setTest("${colcounter.last}");
/*  495 */                         int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/*  496 */                         if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                           for (;;) {
/*  498 */                             out.write("\n   ");
/*      */                             
/*  500 */                             stylefortd = "style='padding-right:0px;'";
/*      */                             
/*  502 */                             out.write(10);
/*  503 */                             out.write(32);
/*  504 */                             out.write(32);
/*  505 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/*  506 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  510 */                         if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/*  511 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*  514 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*  515 */                         out.write("\n  <td width=\"");
/*  516 */                         if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*  518 */                         out.write("%\"  id=\"col");
/*  519 */                         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*  521 */                         out.write("\" valign=\"top\" align=\"center\"  ");
/*  522 */                         out.print(stylefortd);
/*  523 */                         out.write(">\n  ");
/*      */                         
/*  525 */                         ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  526 */                         _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/*  527 */                         _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                         
/*  529 */                         _jspx_th_c_005fforEach_005f1.setVar("widget");
/*      */                         
/*  531 */                         _jspx_th_c_005fforEach_005f1.setItems("${widgets}");
/*      */                         
/*  533 */                         _jspx_th_c_005fforEach_005f1.setVarStatus("counter");
/*  534 */                         int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                         try {
/*  536 */                           int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/*  537 */                           if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                             for (;;) {
/*  539 */                               out.write(10);
/*  540 */                               out.write(32);
/*  541 */                               out.write(32);
/*      */                               
/*  543 */                               IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  544 */                               _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  545 */                               _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                               
/*  547 */                               _jspx_th_c_005fif_005f13.setTest("${widget[2]==(colcounter.count-1)  }");
/*  548 */                               int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  549 */                               if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                 for (;;) {
/*  551 */                                   out.write("\n\n\t  <div id=\"");
/*  552 */                                   if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  554 */                                   out.write("_\"  class=\"tabdiv\" style=\"display:block;\" >\n\t\t<table class=\"lrtbdarkborder-dashboards\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"background-color:white;\">\n\t\t<tr height=\"26\">\n\t\t<td  class='tableHeader1' height=\"26\"  width=\"80%\" align=\"left\" style=\"cursor:move;\"><div style=\"float:left; width:17px; position:reltive; margin-top:2px;\"><img src=\"/images/icon_dashboard_drag.gif\"></div><div id=\"widgetname#");
/*  555 */                                   if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  557 */                                   out.write(34);
/*  558 */                                   out.write(62);
/*  559 */                                   if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  561 */                                   out.write("\t</td>\n\t\t<td class=\"widgetHeader\" height=\"26\"  width=\"20%\" align=\"right\">\n\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\t\t<tr>\n\t\t<td>\n\t\t  <a id=\"widgetDescriptionHook_");
/*  562 */                                   if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  564 */                                   out.write("\" class=\"infobutton widget_optn_space\" title=\"");
/*  565 */                                   out.print(FormatUtil.getString("am.mypage.widget.properties.text"));
/*  566 */                                   out.write("\" href=\"javascript:void(0)\"  onclick=\"showFloatpopUp('widgetDescriptionHook_");
/*  567 */                                   if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  569 */                                   out.write(39);
/*  570 */                                   out.write(44);
/*  571 */                                   out.write(39);
/*  572 */                                   if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  574 */                                   out.write("')\"></a>\n\t\t</td>\n\t\t<td>\n\t\t <a class=\"reloadbutton widget_optn_space\" title=\"");
/*  575 */                                   out.print(FormatUtil.getString("am.mypage.widget.reload.text"));
/*  576 */                                   out.write("\" href=\"javascript:void(0)\" onclick='javascript:getContent3(\"");
/*  577 */                                   if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  579 */                                   out.write("\")'></a>\n\t\t</td>\n\t\t");
/*      */                                   
/*  581 */                                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  582 */                                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  583 */                                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                   
/*  585 */                                   _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  586 */                                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  587 */                                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                     for (;;) {
/*  589 */                                       out.write(10);
/*  590 */                                       out.write(9);
/*  591 */                                       out.write(9);
/*      */                                       
/*  593 */                                       IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  594 */                                       _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  595 */                                       _jspx_th_c_005fif_005f14.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                       
/*  597 */                                       _jspx_th_c_005fif_005f14.setTest("${publishpage ne true && !isReadOnly}");
/*  598 */                                       int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  599 */                                       if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                         for (;;) {
/*  601 */                                           out.write("\n\t\t <td>\n\t\t <a class=\"editbutton widget_optn_space\" title=\"");
/*  602 */                                           out.print(FormatUtil.getString("am.mypage.widget.edit.text"));
/*  603 */                                           out.write("\" href=\"javascript:void(0)\"   onClick=\"javascript:editWidget('");
/*  604 */                                           if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*  675 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  606 */                                           out.write("')\"></a>\n\t\t </td>\n\t\t <td>\n\t\t <a href=\"javascript:void(0)\" title=\"");
/*  607 */                                           out.print(FormatUtil.getString("am.mypage.widget.delete.text"));
/*  608 */                                           out.write("\" class=\"closeButton widget_optn_space\" onclick=\"javascript:deleteWidget('");
/*  609 */                                           if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*  675 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*  611 */                                           out.write("')\"></a>\n\t\t </td>\n\t\t ");
/*  612 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  613 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/*  617 */                                       if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  618 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*  621 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  622 */                                       out.write(10);
/*  623 */                                       out.write(9);
/*  624 */                                       out.write(9);
/*  625 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  626 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/*  630 */                                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  631 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  634 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  635 */                                   out.write("\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td align=\"left\" valign=\"top\" colspan=\"2\">\n\t\t<div class=\"pos_relative\" style=\" overflow:auto; width:auto;display:inline-block;\"id=\"");
/*  636 */                                   if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  638 */                                   out.write("\">\n\t\t<div style='text-align:center;padding:10px;height:200px'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n </div></td>\n\t\t</tr>\n\t\t  </table>\n\t  </div>\n\t");
/*  639 */                                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  641 */                                   out.write("\n\t  <script>\n\t  \tjavascript:getContentwithDelay(\"");
/*  642 */                                   if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  644 */                                   out.write(34);
/*  645 */                                   out.write(44);
/*  646 */                                   if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*  648 */                                   out.write(",200)\n\t  </script>\n  ");
/*  649 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/*  650 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  654 */                               if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/*  655 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                               }
/*  658 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  659 */                               out.write(10);
/*  660 */                               out.write(32);
/*  661 */                               out.write(32);
/*  662 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/*  663 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  667 */                           if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */                             _jspx_th_c_005fforEach_005f1.doFinally();
/*  676 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/*  671 */                             int tmp4690_4689 = 0; int[] tmp4690_4687 = _jspx_push_body_count_c_005fforEach_005f1; int tmp4692_4691 = tmp4690_4687[tmp4690_4689];tmp4690_4687[tmp4690_4689] = (tmp4692_4691 - 1); if (tmp4692_4691 <= 0) break;
/*  672 */                             out = _jspx_page_context.popBody(); }
/*  673 */                           _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                         }
/*      */                         finally {}
/*      */                         
/*      */ 
/*  678 */                         out.write("\n  </td>\n  ");
/*  679 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  680 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  684 */                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/*  688 */                       int tmp4833_4832 = 0; int[] tmp4833_4830 = _jspx_push_body_count_c_005fforEach_005f0; int tmp4835_4834 = tmp4833_4830[tmp4833_4832];tmp4833_4830[tmp4833_4832] = (tmp4835_4834 - 1); if (tmp4835_4834 <= 0) break;
/*  689 */                       out = _jspx_page_context.popBody(); }
/*  690 */                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                   } finally {
/*  692 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/*  693 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                   }
/*  695 */                   out.write("\n  </tr>\n</table>\n\n</div>\n\n</div>\n\n");
/*  696 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  697 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  701 */               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  702 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */               }
/*      */               
/*  705 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  706 */               out.write(10);
/*      */               
/*  708 */               OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  709 */               _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  710 */               _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f4);
/*  711 */               int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  712 */               if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                 for (;;) {
/*  714 */                   out.write("\n<iframe src=\"");
/*  715 */                   out.print(productUrl + "/applications.do?PRINTER_FRIENDLY=true");
/*  716 */                   out.write("\" scrolling=\"no\" align=\"center\"  WIDTH=\"100%\" height=\"2000px\"   border=\"0\" frameborder=\"0\"> </iframe>\n\n");
/*  717 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  718 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  722 */               if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  723 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */               }
/*      */               
/*  726 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  727 */               out.write(10);
/*  728 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  729 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  733 */           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  734 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */           }
/*      */           
/*  737 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  738 */           out.write(10);
/*      */ 
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  743 */           ex.printStackTrace();
/*      */         }
/*      */         
/*  746 */         out.write(10);
/*  747 */         out.write("\n<table width=\"100%\">\n      <tr>\n      <td align=\"right\">\n<div style=\"display:none;\"   id=\"widgetDescriptionDiv\">\n</div>\n </td>\n<tr>\n</table>\n");
/*  748 */         out.write(10);
/*  749 */         out.write(10);
/*  750 */         out.write("\n <div style=\"display:none;\"   id=\"widgetBokmarks_DivContainer\">\n <div style=\"overflow: auto; height:280px;width: 500px;\">\n &nbsp;\n </div>\n</div>\n");
/*  751 */         out.write(10);
/*  752 */         out.write(10);
/*  753 */         if (_jspx_meth_c_005fif_005f16(_jspx_page_context))
/*      */           return;
/*  755 */         out.write(10);
/*  756 */         out.write(10);
/*      */         
/*  758 */         IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  759 */         _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/*  760 */         _jspx_th_c_005fif_005f17.setParent(null);
/*      */         
/*  762 */         _jspx_th_c_005fif_005f17.setTest("${isPopup=='true' || publishpage=='true'}");
/*  763 */         int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/*  764 */         if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */           for (;;) {
/*  766 */             out.write(10);
/*  767 */             request.setAttribute("systime", new java.util.Date());
/*  768 */             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr> \n    \t<td style=\"padding-left: 10px;\"><span class=\"footer\">");
/*  769 */             out.print(FormatUtil.getString("webclient.server.responsetime"));
/*  770 */             out.write("&nbsp;<span class=\"bodytextbold\">");
/*  771 */             if (_jspx_meth_am_005ftimestamp_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */               return;
/*  773 */             out.write("</span>");
/*  774 */             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */               return;
/*  776 */             out.write("</span></td>\t\t");
/*  777 */             out.write("\n        <td style=\"padding-right: 10px;\" align=\"right\"><span class=\"footer\">");
/*  778 */             out.print(FormatUtil.getString("webclient.server.systemtime"));
/*  779 */             out.write(32);
/*  780 */             out.write(58);
/*  781 */             out.write(32);
/*  782 */             if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */               return;
/*  784 */             out.write("</span>&nbsp;&nbsp;</td>\n    </tr>\n</table>\n<script type=\"text/javascript\">\n$(window).resize(function() \n{\n\tparent.window.document.location.reload(false);\t  \n});\n</script>\n");
/*  785 */             int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/*  786 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  790 */         if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/*  791 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*      */         }
/*      */         else {
/*  794 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*  795 */           out.write("\n\n<script type=\"text/javascript\">\n$(document).ready(function() {//No I18N\n\t");
/*  796 */           if (_jspx_meth_c_005fif_005f18(_jspx_page_context))
/*      */             return;
/*  798 */           out.write("\n}\n/* ");
/*  799 */           if (_jspx_meth_c_005fif_005f20(_jspx_page_context))
/*      */             return;
/*  801 */           out.write(" */\n);\n</script>\n");
/*      */         }
/*  803 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  804 */         out = _jspx_out;
/*  805 */         if ((out != null) && (out.getBufferSize() != 0))
/*  806 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  807 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  810 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  816 */     PageContext pageContext = _jspx_page_context;
/*  817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  819 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  820 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  821 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/*  823 */     _jspx_th_c_005fif_005f0.setTest("${publishpage eq true}");
/*  824 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  825 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/*  827 */         out.write("\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/Dialog.js\"></script>\n");
/*  828 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  829 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  833 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  834 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  835 */       return true;
/*      */     }
/*  837 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  843 */     PageContext pageContext = _jspx_page_context;
/*  844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  846 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  847 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  848 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/*  850 */     _jspx_th_c_005fif_005f1.setTest("${isPopup=='true' || publishpage eq true}");
/*  851 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  852 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/*  854 */         out.write(10);
/*  855 */         out.write(10);
/*  856 */         out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  857 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*  858 */           return true;
/*  859 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  860 */         out.write(10);
/*  861 */         out.write(10);
/*  862 */         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  863 */         out.write(10);
/*  864 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  865 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  869 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  870 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  871 */       return true;
/*      */     }
/*  873 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  874 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  879 */     PageContext pageContext = _jspx_page_context;
/*  880 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  882 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  883 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  884 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  886 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  888 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  889 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  890 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  891 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  892 */       return true;
/*      */     }
/*  894 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  900 */     PageContext pageContext = _jspx_page_context;
/*  901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  903 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  904 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  905 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/*  907 */     _jspx_th_c_005fif_005f2.setTest("${isPopup=='true' || publishpage eq true}");
/*  908 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  909 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/*  911 */         out.write("\n<title>");
/*  912 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*  913 */           return true;
/*  914 */         out.write("</title>\n");
/*  915 */         if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*  916 */           return true;
/*  917 */         out.write(10);
/*  918 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  919 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  923 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  924 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  925 */       return true;
/*      */     }
/*  927 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  933 */     PageContext pageContext = _jspx_page_context;
/*  934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  936 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  937 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  938 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/*  940 */     _jspx_th_c_005fout_005f1.setValue("${MyPageForm.displayName}");
/*  941 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  942 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  943 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  944 */       return true;
/*      */     }
/*  946 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  952 */     PageContext pageContext = _jspx_page_context;
/*  953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  955 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  956 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  957 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/*  959 */     _jspx_th_c_005fif_005f3.setTest("${!empty reloadperiod && !empty customreloadperiod}");
/*  960 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  961 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/*  963 */         out.write("\n\t<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/*  964 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*  965 */           return true;
/*  966 */         out.write(34);
/*  967 */         out.write(62);
/*  968 */         out.write(10);
/*  969 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  970 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  974 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  975 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  976 */       return true;
/*      */     }
/*  978 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  979 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  984 */     PageContext pageContext = _jspx_page_context;
/*  985 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  987 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  988 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  989 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/*  991 */     _jspx_th_c_005fout_005f2.setValue("${customreloadperiod}");
/*  992 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  993 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  994 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  995 */       return true;
/*      */     }
/*  997 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1003 */     PageContext pageContext = _jspx_page_context;
/* 1004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1006 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 1007 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1008 */     _jspx_th_c_005fset_005f1.setParent(null);
/*      */     
/* 1010 */     _jspx_th_c_005fset_005f1.setVar("expandWidgets");
/*      */     
/* 1012 */     _jspx_th_c_005fset_005f1.setValue("true");
/*      */     
/* 1014 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 1015 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1016 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1017 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1018 */       return true;
/*      */     }
/* 1020 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1026 */     PageContext pageContext = _jspx_page_context;
/* 1027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1029 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1030 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1031 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 1033 */     _jspx_th_c_005fout_005f3.setValue("${pageid}");
/* 1034 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1035 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1037 */       return true;
/*      */     }
/* 1039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1045 */     PageContext pageContext = _jspx_page_context;
/* 1046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1048 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1049 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1050 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 1052 */     _jspx_th_c_005fout_005f4.setValue("${param.screenwidth}");
/*      */     
/* 1054 */     _jspx_th_c_005fout_005f4.setDefault("100");
/* 1055 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1056 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1057 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1058 */       return true;
/*      */     }
/* 1060 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1066 */     PageContext pageContext = _jspx_page_context;
/* 1067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1069 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1070 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1071 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 1072 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1073 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1075 */         out.write(10);
/* 1076 */         out.write(9);
/* 1077 */         out.write(9);
/* 1078 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1079 */           return true;
/* 1080 */         out.write(10);
/* 1081 */         out.write(9);
/* 1082 */         out.write(9);
/* 1083 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1084 */           return true;
/* 1085 */         out.write(10);
/* 1086 */         out.write(9);
/* 1087 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1092 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1093 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1094 */       return true;
/*      */     }
/* 1096 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1102 */     PageContext pageContext = _jspx_page_context;
/* 1103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1105 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1106 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1107 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1109 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty expandWidgets && expandWidgets eq 'true'}");
/* 1110 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1111 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1113 */         out.write("\n\t\t\tscrWidth = parseInt(scrWidth*0.980);\n\t\t");
/* 1114 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1115 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1119 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1120 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1121 */       return true;
/*      */     }
/* 1123 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1129 */     PageContext pageContext = _jspx_page_context;
/* 1130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1132 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1133 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1134 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1135 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1136 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1138 */         out.write("\n\t\t\t");
/* 1139 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1140 */           return true;
/* 1141 */         out.write(10);
/* 1142 */         out.write(9);
/* 1143 */         out.write(9);
/* 1144 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1145 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1149 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1150 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1151 */       return true;
/*      */     }
/* 1153 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1159 */     PageContext pageContext = _jspx_page_context;
/* 1160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1162 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1163 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1164 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1166 */     _jspx_th_c_005fif_005f4.setTest("${isPopup ne 'true' &&  publishpage ne 'true' }");
/* 1167 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1168 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 1170 */         out.write("\n\t  \t\t\tscrWidth = parseInt(scrWidth*0.980);\n\t  \t\t");
/* 1171 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1172 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1176 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1177 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1178 */       return true;
/*      */     }
/* 1180 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1186 */     PageContext pageContext = _jspx_page_context;
/* 1187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1189 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 1190 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 1191 */     _jspx_th_c_005fset_005f2.setParent(null);
/*      */     
/* 1193 */     _jspx_th_c_005fset_005f2.setVar("template_resid");
/* 1194 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 1195 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 1196 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 1197 */       return true;
/*      */     }
/* 1199 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 1200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1205 */     PageContext pageContext = _jspx_page_context;
/* 1206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1208 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1209 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1210 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 1212 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.template_resid}");
/* 1213 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1214 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 1216 */         out.write("\n\t\t\t");
/* 1217 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 1218 */           return true;
/* 1219 */         out.write(10);
/* 1220 */         out.write(9);
/* 1221 */         out.write(9);
/* 1222 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1227 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1228 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1229 */       return true;
/*      */     }
/* 1231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1237 */     PageContext pageContext = _jspx_page_context;
/* 1238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1240 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1241 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 1242 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1244 */     _jspx_th_c_005fset_005f3.setVar("template_resid");
/* 1245 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 1246 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 1247 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1248 */         out = _jspx_page_context.pushBody();
/* 1249 */         _jspx_th_c_005fset_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1250 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1253 */         out.write("template_resid=");
/* 1254 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 1255 */           return true;
/* 1256 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 1257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1260 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1261 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1264 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 1265 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 1266 */       return true;
/*      */     }
/* 1268 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 1269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1274 */     PageContext pageContext = _jspx_page_context;
/* 1275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1277 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1278 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1279 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 1281 */     _jspx_th_c_005fout_005f5.setValue("${param.template_resid}");
/* 1282 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1283 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1284 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1285 */       return true;
/*      */     }
/* 1287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1293 */     PageContext pageContext = _jspx_page_context;
/* 1294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1296 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1297 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1298 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 1300 */     _jspx_th_c_005fout_005f6.setValue("${pageid}");
/* 1301 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1302 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1304 */       return true;
/*      */     }
/* 1306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1312 */     PageContext pageContext = _jspx_page_context;
/* 1313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1315 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1316 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1317 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 1319 */     _jspx_th_c_005fout_005f7.setValue("${pageid}");
/* 1320 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1321 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1323 */       return true;
/*      */     }
/* 1325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1331 */     PageContext pageContext = _jspx_page_context;
/* 1332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1334 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1335 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1336 */     _jspx_th_c_005fout_005f8.setParent(null);
/*      */     
/* 1338 */     _jspx_th_c_005fout_005f8.setValue("${pageid}");
/* 1339 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1340 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1341 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1342 */       return true;
/*      */     }
/* 1344 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1345 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1350 */     PageContext pageContext = _jspx_page_context;
/* 1351 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1353 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1354 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1355 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 1357 */     _jspx_th_c_005fout_005f9.setValue("${pageid}");
/* 1358 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1359 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1360 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1361 */       return true;
/*      */     }
/* 1363 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1364 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1369 */     PageContext pageContext = _jspx_page_context;
/* 1370 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1372 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1373 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1374 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 1376 */     _jspx_th_c_005fout_005f10.setValue("${pageid}");
/* 1377 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1378 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1379 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1380 */       return true;
/*      */     }
/* 1382 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1388 */     PageContext pageContext = _jspx_page_context;
/* 1389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1391 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1392 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1393 */     _jspx_th_c_005fout_005f11.setParent(null);
/*      */     
/* 1395 */     _jspx_th_c_005fout_005f11.setValue("${pageid}");
/* 1396 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1397 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1398 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1399 */       return true;
/*      */     }
/* 1401 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1407 */     PageContext pageContext = _jspx_page_context;
/* 1408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1410 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1411 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1412 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 1413 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1414 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1416 */         out.write(10);
/* 1417 */         out.write(9);
/* 1418 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1419 */           return true;
/* 1420 */         out.write(10);
/* 1421 */         out.write(9);
/* 1422 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1423 */           return true;
/* 1424 */         out.write(10);
/* 1425 */         out.write(9);
/* 1426 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1427 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1431 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1432 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1433 */       return true;
/*      */     }
/* 1435 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1436 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1441 */     PageContext pageContext = _jspx_page_context;
/* 1442 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1444 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1445 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1446 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1448 */     _jspx_th_c_005fwhen_005f1.setTest("${publishpage==\"true\"}");
/* 1449 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1450 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1452 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&columns=");
/* 1453 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1454 */           return true;
/* 1455 */         out.write("&pagekey=");
/* 1456 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1457 */           return true;
/* 1458 */         out.write("&randomnumber=\"+ Math.random()+'&'+'");
/* 1459 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1460 */           return true;
/* 1461 */         out.write("';\t\t// NO I18N\n\t");
/* 1462 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1463 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1467 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1468 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1469 */       return true;
/*      */     }
/* 1471 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1477 */     PageContext pageContext = _jspx_page_context;
/* 1478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1480 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1481 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1482 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1484 */     _jspx_th_c_005fout_005f12.setValue("${numberOfColumns}");
/* 1485 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1486 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1487 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1488 */       return true;
/*      */     }
/* 1490 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1496 */     PageContext pageContext = _jspx_page_context;
/* 1497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1499 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1500 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 1501 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1503 */     _jspx_th_c_005fout_005f13.setValue("${pagekey}");
/* 1504 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 1505 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 1506 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1507 */       return true;
/*      */     }
/* 1509 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1515 */     PageContext pageContext = _jspx_page_context;
/* 1516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1518 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1519 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 1520 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1522 */     _jspx_th_c_005fout_005f14.setValue("${template_resid}");
/* 1523 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 1524 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 1525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1526 */       return true;
/*      */     }
/* 1528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1529 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1534 */     PageContext pageContext = _jspx_page_context;
/* 1535 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1537 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1538 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1539 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1540 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1541 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1543 */         out.write("\n\turl=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&columns=");
/* 1544 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1545 */           return true;
/* 1546 */         out.write("&randomnumber=\"+ Math.random()+'&'+'");
/* 1547 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1548 */           return true;
/* 1549 */         out.write("';\t\t// NO I18N\n\t");
/* 1550 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1555 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1556 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1557 */       return true;
/*      */     }
/* 1559 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1565 */     PageContext pageContext = _jspx_page_context;
/* 1566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1568 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1569 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 1570 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1572 */     _jspx_th_c_005fout_005f15.setValue("${numberOfColumns}");
/* 1573 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 1574 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 1575 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1576 */       return true;
/*      */     }
/* 1578 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1584 */     PageContext pageContext = _jspx_page_context;
/* 1585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1587 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1588 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 1589 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1591 */     _jspx_th_c_005fout_005f16.setValue("${template_resid}");
/* 1592 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 1593 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 1594 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1595 */       return true;
/*      */     }
/* 1597 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1603 */     PageContext pageContext = _jspx_page_context;
/* 1604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1606 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 1607 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 1608 */     _jspx_th_c_005fset_005f4.setParent(null);
/*      */     
/* 1610 */     _jspx_th_c_005fset_005f4.setVar("template_resid");
/* 1611 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 1612 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 1613 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 1614 */       return true;
/*      */     }
/* 1616 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 1617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1622 */     PageContext pageContext = _jspx_page_context;
/* 1623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1625 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1626 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1627 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 1629 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.template_resid}");
/* 1630 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1631 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 1633 */         out.write(10);
/* 1634 */         out.write(9);
/* 1635 */         out.write(9);
/* 1636 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 1637 */           return true;
/* 1638 */         out.write(10);
/* 1639 */         out.write(9);
/* 1640 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1641 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1645 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1646 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1647 */       return true;
/*      */     }
/* 1649 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1655 */     PageContext pageContext = _jspx_page_context;
/* 1656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1658 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1659 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 1660 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 1662 */     _jspx_th_c_005fset_005f5.setVar("template_resid");
/* 1663 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 1664 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 1665 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 1666 */         out = _jspx_page_context.pushBody();
/* 1667 */         _jspx_th_c_005fset_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1668 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1671 */         out.write("template_resid=");
/* 1672 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 1673 */           return true;
/* 1674 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 1675 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1678 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 1679 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1682 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 1683 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 1684 */       return true;
/*      */     }
/* 1686 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 1687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1692 */     PageContext pageContext = _jspx_page_context;
/* 1693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1695 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1696 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 1697 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 1699 */     _jspx_th_c_005fout_005f17.setValue("${param.template_resid}");
/* 1700 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 1701 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 1702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1703 */       return true;
/*      */     }
/* 1705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1711 */     PageContext pageContext = _jspx_page_context;
/* 1712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1714 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1715 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 1716 */     _jspx_th_c_005fout_005f18.setParent(null);
/*      */     
/* 1718 */     _jspx_th_c_005fout_005f18.setValue("${pageid}");
/* 1719 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 1720 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 1721 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1722 */       return true;
/*      */     }
/* 1724 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1730 */     PageContext pageContext = _jspx_page_context;
/* 1731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1733 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1734 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1735 */     _jspx_th_c_005fchoose_005f2.setParent(null);
/* 1736 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1737 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1739 */         out.write(10);
/* 1740 */         out.write(9);
/* 1741 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1742 */           return true;
/* 1743 */         out.write(10);
/* 1744 */         out.write(9);
/* 1745 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1746 */           return true;
/* 1747 */         out.write(10);
/* 1748 */         out.write(9);
/* 1749 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1750 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1754 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1755 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1756 */       return true;
/*      */     }
/* 1758 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1764 */     PageContext pageContext = _jspx_page_context;
/* 1765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1767 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1768 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1769 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1771 */     _jspx_th_c_005fwhen_005f2.setTest("${publishpage==\"true\"}");
/* 1772 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1773 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1775 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&pagekey=");
/* 1776 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 1777 */           return true;
/* 1778 */         out.write("&randomnumber=\"+ Math.random()+\"&\"+\"");
/* 1779 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 1780 */           return true;
/* 1781 */         out.write("\";//No I18N\n        ");
/* 1782 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1783 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1787 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1788 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1789 */       return true;
/*      */     }
/* 1791 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1797 */     PageContext pageContext = _jspx_page_context;
/* 1798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1800 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1801 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 1802 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1804 */     _jspx_th_c_005fout_005f19.setValue("${pagekey}");
/* 1805 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 1806 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 1807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 1808 */       return true;
/*      */     }
/* 1810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 1811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1816 */     PageContext pageContext = _jspx_page_context;
/* 1817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1819 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1820 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 1821 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1823 */     _jspx_th_c_005fout_005f20.setValue("${template_resid}");
/* 1824 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 1825 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 1826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 1827 */       return true;
/*      */     }
/* 1829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 1830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1835 */     PageContext pageContext = _jspx_page_context;
/* 1836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1838 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1839 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1840 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1841 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1842 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 1844 */         out.write("\n        url=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&randomnumber=\"+ Math.random()+\"&\"+\"");
/* 1845 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 1846 */           return true;
/* 1847 */         out.write("\";\t//No I18N\n        ");
/* 1848 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1849 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1853 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1854 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1855 */       return true;
/*      */     }
/* 1857 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1863 */     PageContext pageContext = _jspx_page_context;
/* 1864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1866 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1867 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 1868 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1870 */     _jspx_th_c_005fout_005f21.setValue("${template_resid}");
/* 1871 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 1872 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 1873 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 1874 */       return true;
/*      */     }
/* 1876 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 1877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1882 */     PageContext pageContext = _jspx_page_context;
/* 1883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1885 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 1886 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1887 */     _jspx_th_c_005fset_005f6.setParent(null);
/*      */     
/* 1889 */     _jspx_th_c_005fset_005f6.setVar("template_resid");
/* 1890 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1891 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1892 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 1893 */       return true;
/*      */     }
/* 1895 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 1896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1901 */     PageContext pageContext = _jspx_page_context;
/* 1902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1904 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1905 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1906 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 1908 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.template_resid}");
/* 1909 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1910 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 1912 */         out.write(10);
/* 1913 */         out.write(9);
/* 1914 */         out.write(9);
/* 1915 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 1916 */           return true;
/* 1917 */         out.write(10);
/* 1918 */         out.write(9);
/* 1919 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1920 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1924 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1925 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1926 */       return true;
/*      */     }
/* 1928 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1934 */     PageContext pageContext = _jspx_page_context;
/* 1935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1937 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1938 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1939 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1941 */     _jspx_th_c_005fset_005f7.setVar("template_resid");
/* 1942 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1943 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1944 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1945 */         out = _jspx_page_context.pushBody();
/* 1946 */         _jspx_th_c_005fset_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1947 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1950 */         out.write("\"&template_resid=");
/* 1951 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 1952 */           return true;
/* 1953 */         out.write(34);
/* 1954 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1955 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1958 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1959 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1962 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1963 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1964 */       return true;
/*      */     }
/* 1966 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1972 */     PageContext pageContext = _jspx_page_context;
/* 1973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1975 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1976 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 1977 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 1979 */     _jspx_th_c_005fout_005f22.setValue("${param.template_resid}");
/* 1980 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 1981 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 1982 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 1983 */       return true;
/*      */     }
/* 1985 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 1986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1991 */     PageContext pageContext = _jspx_page_context;
/* 1992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1994 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1995 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 1996 */     _jspx_th_c_005fout_005f23.setParent(null);
/*      */     
/* 1998 */     _jspx_th_c_005fout_005f23.setValue("${pageid}");
/* 1999 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 2000 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 2001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2002 */       return true;
/*      */     }
/* 2004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2010 */     PageContext pageContext = _jspx_page_context;
/* 2011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2013 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2014 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2015 */     _jspx_th_c_005fchoose_005f3.setParent(null);
/* 2016 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2017 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 2019 */         out.write(10);
/* 2020 */         out.write(9);
/* 2021 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2022 */           return true;
/* 2023 */         out.write(10);
/* 2024 */         out.write(9);
/* 2025 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2026 */           return true;
/* 2027 */         out.write(10);
/* 2028 */         out.write(9);
/* 2029 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2030 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2034 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2035 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2036 */       return true;
/*      */     }
/* 2038 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2044 */     PageContext pageContext = _jspx_page_context;
/* 2045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2047 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2048 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2049 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2051 */     _jspx_th_c_005fwhen_005f3.setTest("${publishpage==\"true\"}");
/* 2052 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2053 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 2055 */         out.write("\n\turl=\"/publishPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&pagekey=");
/* 2056 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2057 */           return true;
/* 2058 */         out.write("&randomnumber=\"+Math.random()+\"");
/* 2059 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2060 */           return true;
/* 2061 */         out.write("\";\n\t");
/* 2062 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2063 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2067 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2068 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2069 */       return true;
/*      */     }
/* 2071 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2077 */     PageContext pageContext = _jspx_page_context;
/* 2078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2080 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2081 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 2082 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2084 */     _jspx_th_c_005fout_005f24.setValue("${pagekey}");
/* 2085 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 2086 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 2087 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2088 */       return true;
/*      */     }
/* 2090 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2096 */     PageContext pageContext = _jspx_page_context;
/* 2097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2099 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2100 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 2101 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2103 */     _jspx_th_c_005fout_005f25.setValue("${template_resid}");
/* 2104 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 2105 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 2106 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2107 */       return true;
/*      */     }
/* 2109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2115 */     PageContext pageContext = _jspx_page_context;
/* 2116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2118 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2119 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2120 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2121 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2122 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2124 */         out.write("\n        url=\"/MyPage.do?method=getWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid+\"&randomnumber=\"+Math.random()+\"");
/* 2125 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 2126 */           return true;
/* 2127 */         out.write("\";\n         ");
/* 2128 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2129 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2133 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2134 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2135 */       return true;
/*      */     }
/* 2137 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2143 */     PageContext pageContext = _jspx_page_context;
/* 2144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2146 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2147 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 2148 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2150 */     _jspx_th_c_005fout_005f26.setValue("${template_resid}");
/* 2151 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 2152 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 2153 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2154 */       return true;
/*      */     }
/* 2156 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2162 */     PageContext pageContext = _jspx_page_context;
/* 2163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2165 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2166 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2167 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2169 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2170 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2171 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2173 */         out.write("\n        alertUser();\n        ");
/* 2174 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2175 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2179 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2180 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2181 */       return true;
/*      */     }
/* 2183 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2184 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2189 */     PageContext pageContext = _jspx_page_context;
/* 2190 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2192 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2193 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 2194 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 2196 */     _jspx_th_c_005fout_005f27.setValue("${pageid}");
/* 2197 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 2198 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 2199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2200 */       return true;
/*      */     }
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2208 */     PageContext pageContext = _jspx_page_context;
/* 2209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2211 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2212 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 2213 */     _jspx_th_c_005fout_005f28.setParent(null);
/*      */     
/* 2215 */     _jspx_th_c_005fout_005f28.setValue("${maxWidget}");
/* 2216 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 2217 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 2218 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2219 */       return true;
/*      */     }
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2227 */     PageContext pageContext = _jspx_page_context;
/* 2228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2230 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2231 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 2232 */     _jspx_th_c_005fout_005f29.setParent(null);
/*      */     
/* 2234 */     _jspx_th_c_005fout_005f29.setValue("${pageid}");
/* 2235 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 2236 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 2237 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2238 */       return true;
/*      */     }
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2246 */     PageContext pageContext = _jspx_page_context;
/* 2247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2249 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2250 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 2251 */     _jspx_th_c_005fout_005f30.setParent(null);
/*      */     
/* 2253 */     _jspx_th_c_005fout_005f30.setValue("${param.widgetid}");
/* 2254 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 2255 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 2256 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2257 */       return true;
/*      */     }
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2260 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2265 */     PageContext pageContext = _jspx_page_context;
/* 2266 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2268 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2269 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 2270 */     _jspx_th_c_005fout_005f31.setParent(null);
/*      */     
/* 2272 */     _jspx_th_c_005fout_005f31.setValue("${MyPageForm.numberOfColumns}");
/* 2273 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 2274 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 2275 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2276 */       return true;
/*      */     }
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2284 */     PageContext pageContext = _jspx_page_context;
/* 2285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2287 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2288 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 2289 */     _jspx_th_c_005fout_005f32.setParent(null);
/*      */     
/* 2291 */     _jspx_th_c_005fout_005f32.setValue("${pageid}");
/* 2292 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 2293 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 2294 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2295 */       return true;
/*      */     }
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2303 */     PageContext pageContext = _jspx_page_context;
/* 2304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2306 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2307 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 2308 */     _jspx_th_c_005fout_005f33.setParent(null);
/*      */     
/* 2310 */     _jspx_th_c_005fout_005f33.setValue("${pageid}");
/* 2311 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 2312 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 2313 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 2314 */       return true;
/*      */     }
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 2317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2322 */     PageContext pageContext = _jspx_page_context;
/* 2323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2325 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2326 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 2327 */     _jspx_th_c_005fout_005f34.setParent(null);
/*      */     
/* 2329 */     _jspx_th_c_005fout_005f34.setValue("${pageid}");
/* 2330 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 2331 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 2332 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 2333 */       return true;
/*      */     }
/* 2335 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 2336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2341 */     PageContext pageContext = _jspx_page_context;
/* 2342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2344 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2345 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 2346 */     _jspx_th_c_005fout_005f35.setParent(null);
/*      */     
/* 2348 */     _jspx_th_c_005fout_005f35.setValue("${pageid}");
/* 2349 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 2350 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 2351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 2352 */       return true;
/*      */     }
/* 2354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 2355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2360 */     PageContext pageContext = _jspx_page_context;
/* 2361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2363 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2364 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2365 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 2367 */     _jspx_th_c_005fif_005f8.setTest("${publishpage==\"true\"}");
/* 2368 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2369 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2371 */         out.write("\n        \turl=\"/publishPage.do?method=getWidgetProperties&widgetid=\"+widgetid+\"&pagekey=");
/* 2372 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 2373 */           return true;
/* 2374 */         out.write("&randomnumber=\"+ Math.random();//No I18N\n        ");
/* 2375 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2376 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2380 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2381 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2382 */       return true;
/*      */     }
/* 2384 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2390 */     PageContext pageContext = _jspx_page_context;
/* 2391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2393 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2394 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 2395 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2397 */     _jspx_th_c_005fout_005f36.setValue("${pagekey}");
/* 2398 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 2399 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 2400 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 2401 */       return true;
/*      */     }
/* 2403 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 2404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2409 */     PageContext pageContext = _jspx_page_context;
/* 2410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2412 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2413 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 2414 */     _jspx_th_c_005fout_005f37.setParent(null);
/*      */     
/* 2416 */     _jspx_th_c_005fout_005f37.setValue("${pageid}");
/* 2417 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 2418 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 2419 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 2420 */       return true;
/*      */     }
/* 2422 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 2423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2428 */     PageContext pageContext = _jspx_page_context;
/* 2429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2431 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2432 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 2433 */     _jspx_th_c_005fout_005f38.setParent(null);
/*      */     
/* 2435 */     _jspx_th_c_005fout_005f38.setValue("${pageid}");
/* 2436 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 2437 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 2438 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 2439 */       return true;
/*      */     }
/* 2441 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 2442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2447 */     PageContext pageContext = _jspx_page_context;
/* 2448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2450 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2451 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 2452 */     _jspx_th_c_005fout_005f39.setParent(null);
/*      */     
/* 2454 */     _jspx_th_c_005fout_005f39.setValue("${pageid}");
/* 2455 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 2456 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 2457 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 2458 */       return true;
/*      */     }
/* 2460 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 2461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2466 */     PageContext pageContext = _jspx_page_context;
/* 2467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2469 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2470 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 2471 */     _jspx_th_c_005fout_005f40.setParent(null);
/*      */     
/* 2473 */     _jspx_th_c_005fout_005f40.setValue("${pageid}");
/* 2474 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 2475 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 2476 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 2477 */       return true;
/*      */     }
/* 2479 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 2480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2485 */     PageContext pageContext = _jspx_page_context;
/* 2486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2488 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2489 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2490 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2492 */     _jspx_th_c_005fif_005f10.setTest("${fromWhere=='mgtemplate'}");
/* 2493 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2494 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2496 */         out.write("\n<div>\n\t\t<div class=\"new-report-text padd-tbtm-7px\">");
/* 2497 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2498 */           return true;
/* 2499 */         out.write("</div>\n\t\t");
/* 2500 */         out.write("\n\n\n</div>\n");
/* 2501 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2502 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2506 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2507 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2508 */       return true;
/*      */     }
/* 2510 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2516 */     PageContext pageContext = _jspx_page_context;
/* 2517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2519 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2520 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 2521 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2523 */     _jspx_th_c_005fout_005f41.setValue("${MyPageForm.displayName}");
/* 2524 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 2525 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 2526 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 2527 */       return true;
/*      */     }
/* 2529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 2530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2535 */     PageContext pageContext = _jspx_page_context;
/* 2536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2538 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2539 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2540 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 2541 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2542 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 2544 */         out.write(10);
/* 2545 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2546 */           return true;
/* 2547 */         out.write(10);
/* 2548 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2549 */           return true;
/* 2550 */         out.write(10);
/* 2551 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2552 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2556 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2557 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2558 */       return true;
/*      */     }
/* 2560 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2566 */     PageContext pageContext = _jspx_page_context;
/* 2567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2569 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2570 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2571 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2573 */     _jspx_th_c_005fwhen_005f5.setTest("${publishpage==\"true\"}");
/* 2574 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2575 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2577 */         out.write("\n<table cellspacing=\"0\" cellpadding=\"5\" width=\"100%\"  id=\"NavBar\" border=\"0\">\n");
/* 2578 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2583 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2584 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2585 */       return true;
/*      */     }
/* 2587 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2593 */     PageContext pageContext = _jspx_page_context;
/* 2594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2596 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2597 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2598 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 2599 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2600 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2602 */         out.write("\n<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"  id=\"NavBar\" border=\"0\">\n");
/* 2603 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2604 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2608 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2609 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2610 */       return true;
/*      */     }
/* 2612 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2613 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2618 */     PageContext pageContext = _jspx_page_context;
/* 2619 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2621 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2622 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 2623 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2625 */     _jspx_th_c_005fout_005f42.setValue("${widgetcolumn}");
/* 2626 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 2627 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 2628 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 2629 */       return true;
/*      */     }
/* 2631 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 2632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2637 */     PageContext pageContext = _jspx_page_context;
/* 2638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2640 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2641 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 2642 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2644 */     _jspx_th_c_005fout_005f43.setValue("${colcounter.count}");
/* 2645 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 2646 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 2647 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 2648 */       return true;
/*      */     }
/* 2650 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 2651 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2656 */     PageContext pageContext = _jspx_page_context;
/* 2657 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2659 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2660 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 2661 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2663 */     _jspx_th_c_005fout_005f44.setValue("${widget[0]}");
/* 2664 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 2665 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 2666 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 2667 */       return true;
/*      */     }
/* 2669 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 2670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2675 */     PageContext pageContext = _jspx_page_context;
/* 2676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2678 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2679 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 2680 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2682 */     _jspx_th_c_005fout_005f45.setValue("${widget[0]}");
/* 2683 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 2684 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 2685 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 2686 */       return true;
/*      */     }
/* 2688 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 2689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2694 */     PageContext pageContext = _jspx_page_context;
/* 2695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2697 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2698 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 2699 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2701 */     _jspx_th_c_005fout_005f46.setValue("${widget[1]}");
/* 2702 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 2703 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 2704 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 2705 */       return true;
/*      */     }
/* 2707 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 2708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2713 */     PageContext pageContext = _jspx_page_context;
/* 2714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2716 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2717 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 2718 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2720 */     _jspx_th_c_005fout_005f47.setValue("${widget[0]}");
/* 2721 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 2722 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 2723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 2724 */       return true;
/*      */     }
/* 2726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 2727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2732 */     PageContext pageContext = _jspx_page_context;
/* 2733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2735 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2736 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 2737 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2739 */     _jspx_th_c_005fout_005f48.setValue("${widget[0]}");
/* 2740 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 2741 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 2742 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 2743 */       return true;
/*      */     }
/* 2745 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 2746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2751 */     PageContext pageContext = _jspx_page_context;
/* 2752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2754 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2755 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 2756 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2758 */     _jspx_th_c_005fout_005f49.setValue("${widget[0]}");
/* 2759 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 2760 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 2761 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 2762 */       return true;
/*      */     }
/* 2764 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 2765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2770 */     PageContext pageContext = _jspx_page_context;
/* 2771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2773 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2774 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 2775 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2777 */     _jspx_th_c_005fout_005f50.setValue("${widget[0]}");
/* 2778 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 2779 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 2780 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 2781 */       return true;
/*      */     }
/* 2783 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 2784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2789 */     PageContext pageContext = _jspx_page_context;
/* 2790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2792 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2793 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 2794 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 2796 */     _jspx_th_c_005fout_005f51.setValue("${widget[0]}");
/* 2797 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 2798 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 2799 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 2800 */       return true;
/*      */     }
/* 2802 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 2803 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2808 */     PageContext pageContext = _jspx_page_context;
/* 2809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2811 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2812 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 2813 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 2815 */     _jspx_th_c_005fout_005f52.setValue("${widget[0]}");
/* 2816 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 2817 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 2818 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 2819 */       return true;
/*      */     }
/* 2821 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 2822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2827 */     PageContext pageContext = _jspx_page_context;
/* 2828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2830 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2831 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 2832 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2834 */     _jspx_th_c_005fout_005f53.setValue("${widget[0]}");
/* 2835 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 2836 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 2837 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 2838 */       return true;
/*      */     }
/* 2840 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 2841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2846 */     PageContext pageContext = _jspx_page_context;
/* 2847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2849 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2850 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2851 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2853 */     _jspx_th_c_005fif_005f15.setTest("${publishpage==\"true\"}");
/* 2854 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2855 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 2857 */         out.write("\n\t<br>\n\t");
/* 2858 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2859 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2863 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2864 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2865 */       return true;
/*      */     }
/* 2867 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2873 */     PageContext pageContext = _jspx_page_context;
/* 2874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2876 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2877 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 2878 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2880 */     _jspx_th_c_005fout_005f54.setValue("${widget[0]}");
/* 2881 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 2882 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 2883 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 2884 */       return true;
/*      */     }
/* 2886 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 2887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2892 */     PageContext pageContext = _jspx_page_context;
/* 2893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2895 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2896 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 2897 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2899 */     _jspx_th_c_005fout_005f55.setValue("${widgetcolumn}");
/* 2900 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 2901 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 2902 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 2903 */       return true;
/*      */     }
/* 2905 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 2906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2911 */     PageContext pageContext = _jspx_page_context;
/* 2912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2914 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2915 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2916 */     _jspx_th_c_005fif_005f16.setParent(null);
/*      */     
/* 2918 */     _jspx_th_c_005fif_005f16.setTest("${isPopup=='true'}");
/* 2919 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2920 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2922 */         out.write("\n</body>\n</html>\n");
/* 2923 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2924 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2928 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2929 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2930 */       return true;
/*      */     }
/* 2932 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005ftimestamp_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2938 */     PageContext pageContext = _jspx_page_context;
/* 2939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2941 */     com.adventnet.appmanager.tags.LoadTime _jspx_th_am_005ftimestamp_005f0 = (com.adventnet.appmanager.tags.LoadTime)this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.get(com.adventnet.appmanager.tags.LoadTime.class);
/* 2942 */     _jspx_th_am_005ftimestamp_005f0.setPageContext(_jspx_page_context);
/* 2943 */     _jspx_th_am_005ftimestamp_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/* 2944 */     int _jspx_eval_am_005ftimestamp_005f0 = _jspx_th_am_005ftimestamp_005f0.doStartTag();
/* 2945 */     if (_jspx_th_am_005ftimestamp_005f0.doEndTag() == 5) {
/* 2946 */       this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 2947 */       return true;
/*      */     }
/* 2949 */     this._005fjspx_005ftagPool_005fam_005ftimestamp_005fnobody.reuse(_jspx_th_am_005ftimestamp_005f0);
/* 2950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2955 */     PageContext pageContext = _jspx_page_context;
/* 2956 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2958 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2959 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2960 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/* 2961 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2962 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2963 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2964 */         out = _jspx_page_context.pushBody();
/* 2965 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2966 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2969 */         out.write("milliseconds");
/* 2970 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2971 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2974 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2975 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2978 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2979 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2980 */       return true;
/*      */     }
/* 2982 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2988 */     PageContext pageContext = _jspx_page_context;
/* 2989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2991 */     org.apache.taglibs.standard.tag.el.fmt.FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (org.apache.taglibs.standard.tag.el.fmt.FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.FormatDateTag.class);
/* 2992 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 2993 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 2995 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${systime}");
/*      */     
/* 2997 */     _jspx_th_fmt_005fformatDate_005f0.setType("BOTH");
/* 2998 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 2999 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 3000 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3001 */       return true;
/*      */     }
/* 3003 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3009 */     PageContext pageContext = _jspx_page_context;
/* 3010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3012 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3013 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3014 */     _jspx_th_c_005fif_005f18.setParent(null);
/*      */     
/* 3016 */     _jspx_th_c_005fif_005f18.setTest("${not empty pageid}");
/* 3017 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3018 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 3020 */         out.write(10);
/* 3021 */         out.write(9);
/* 3022 */         out.write(9);
/* 3023 */         if (_jspx_meth_c_005fif_005f19(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 3024 */           return true;
/* 3025 */         out.write(10);
/* 3026 */         out.write(9);
/* 3027 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3028 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3032 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3033 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3034 */       return true;
/*      */     }
/* 3036 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3037 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3042 */     PageContext pageContext = _jspx_page_context;
/* 3043 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3045 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3046 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3047 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 3049 */     _jspx_th_c_005fif_005f19.setTest("${publishpage!=\"true\"}");
/* 3050 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3051 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 3053 */         out.write("\n\t\t\tloadDragandDrop();//in appmanager.js\n\t\t");
/* 3054 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3059 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3060 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3061 */       return true;
/*      */     }
/* 3063 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3069 */     PageContext pageContext = _jspx_page_context;
/* 3070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3072 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3073 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3074 */     _jspx_th_c_005fif_005f20.setParent(null);
/*      */     
/* 3076 */     _jspx_th_c_005fif_005f20.setTest("${publishpage eq true}");
/* 3077 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3078 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 3080 */         out.write("\n,\n$('a').live('click', function(e) \n{\n\treturn false;\n})\n");
/* 3081 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3082 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3086 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3087 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3088 */       return true;
/*      */     }
/* 3090 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3091 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */