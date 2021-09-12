/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ public final class MapViewInclude_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*   34 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   40 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/*   41 */   static { _jspx_dependants.put("/jsp/MapViewEditor.jsp", Long.valueOf(1473429417000L));
/*   42 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*   43 */     _jspx_dependants.put("/jsp/MapViewDisplay.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   57 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   61 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   68 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   72 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   75 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*   76 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*   77 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*   84 */     HttpSession session = null;
/*      */     
/*      */ 
/*   87 */     JspWriter out = null;
/*   88 */     Object page = this;
/*   89 */     JspWriter _jspx_out = null;
/*   90 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   94 */       response.setContentType("text/html; charset=UTF-8");
/*   95 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   97 */       _jspx_page_context = pageContext;
/*   98 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   99 */       ServletConfig config = pageContext.getServletConfig();
/*  100 */       session = pageContext.getSession();
/*  101 */       out = pageContext.getOut();
/*  102 */       _jspx_out = out;
/*      */       
/*  104 */       out.write("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n\n\n<!--$Id$ -->\n");
/*  105 */       String isPopUp = request.getParameter("isPopUp");
/*  106 */       String fromEditTopoWidget = request.getParameter("fromEditTopoWidget");
/*      */       
/*  108 */       if (((isPopUp != null) && (isPopUp.equals("true"))) || ((fromEditTopoWidget != null) && (fromEditTopoWidget.equals("true"))))
/*      */       {
/*  110 */         out.write(10);
/*  111 */         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  112 */         out.write(10);
/*  113 */         String method = request.getParameter("method");
/*  114 */         String widgetId = (String)request.getAttribute("widgetid");
/*      */         
/*      */         try
/*      */         {
/*  118 */           if ((method != null) && (method.equalsIgnoreCase("showMap")))
/*      */           {
/*  120 */             out.write(10);
/*  121 */             out.write("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n\n\n\n");
/*  122 */             out.write("\n<head>\n");
/*  123 */             out.write("\n<title>");
/*  124 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  125 */             out.write("</title>\n\n<style type=\"text/css\"></style>\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n\n\n\n</head>\n<body>\n\n<div id=\"delConfirmBox\" class=\"confirm-box\">\n<h3>");
/*  126 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.title"));
/*  127 */             out.write("</h3>\n<div class=\"confirm-cont\">\n<p>");
/*  128 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.text"));
/*  129 */             out.write("</p>\n<p><div class=\"confirm-input\"><input id=\"toDeleteBSG\" name=\"\" type=\"checkbox\" value=\"\" class=\"confirm-checkbox\" checked=\"checked\"/></div><div id='bsgText'>");
/*  130 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirmation.bsg"));
/*  131 */             out.write("</div></p>\n<p style=\"text-align:center;\"><input name=\"\" id=\"deleteOKMap\" type=\"button\" value=\"OK\" class=\"confirm-but\"/> <input name=\"\" id=\"deleteCancelMap\" type=\"button\" value=\"Cancel\" class=\"confirm-but\"/></p>\n</div>\n</div>\n<div id=\"confirm-overlay\"></div>\n\n\n\n\t<div id=\"tooltip\" style=\"width:300px;display:none;position:absolute;\"> <span id=\"pointedarrow\" class=\"green-arrow\">&nbsp;</span>\n      <div id=\"colorofbox\" class=\"green-box\">\n        <h1 id=\"DetailHeading\"></h1>\n        <div class=\"mapbodytext\"> </div>\n      </div>\n    </div>\n<div id=\"main\">\n<div id=\"zoomingsec\">\n        <div id=\"z-plus-minus\">\n          <div id=\"arrow-box\">\n            <div id=\"arrow-plus-icon\" class=\"arrow-plus-icon\"><a href=\"#\" title='");
/*  132 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomin"));
/*  133 */             out.write(39);
/*  134 */             out.write(62);
/*  135 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  136 */             out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:2px 0 0;\">\n            <div id=\"arrow-minus-icon\" class=\"arrow-minus-icon\"><a href=\"#\" title='");
/*  137 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomout"));
/*  138 */             out.write(39);
/*  139 */             out.write(62);
/*  140 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  141 */             out.write("</a></div>\n          </div>\n        </div>\n        <div id=\"zoom-arrows\">\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-top\" class=\"arrow-icon-top\"><a href=\"#\" title='");
/*  142 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  143 */             out.write(39);
/*  144 */             out.write(62);
/*  145 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  146 */             out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\">\n            <div id=\"arrow-icon-left\" class=\"arrow-icon-left\"><a href=\"#\" title='");
/*  147 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  148 */             out.write(39);
/*  149 */             out.write(62);
/*  150 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  151 */             out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:0 1px;\">\n            <div id=\"arrow-icon-center\"><a href=\"#\" title='");
/*  152 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  153 */             out.write(39);
/*  154 */             out.write(62);
/*  155 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  156 */             out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\">\n\t\t  <div id=\"arrow-icon-right\" class=\"arrow-icon-right\"><a href=\"#\" title='");
/*  157 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/*  158 */             out.write(39);
/*  159 */             out.write(62);
/*  160 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/*  161 */             out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-bottom\" class=\"arrow-icon-bottom\"><a href=\"#\" title='");
/*  162 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/*  163 */             out.write(39);
/*  164 */             out.write(32);
/*  165 */             out.write(62);
/*  166 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/*  167 */             out.write("</a></div>\n          </div>\n        </div>\n</div>\n\n\n  <div class=\"map-border\">\n    <div id=\"mapName\" style=\"float:left\"></div>\n   ");
/*      */             
/*  169 */             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  170 */             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  171 */             _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */             
/*  173 */             _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/*  174 */             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  175 */             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */               for (;;) {
/*  177 */                 out.write("<div style=\"float:right\" class=\"btnout1\" onmouseout=\"hideActionmenu()\" onclick=\"showActionmenu()\">\n\n");
/*  178 */                 out.print(FormatUtil.getString("am.mypage.widgettypes.topologymapwidget.action.text"));
/*  179 */                 out.write("<img width=\"7\" vspace=\"2\" height=\"4\" border=\"0\" src=\"/images/icon_black_arrow.gif\" valign=\"middle\">\n\n\t<div style=\"display:none;\" id=\"action_drop\" class=\"action-but-border1\" onmouseout=\"hideActionmenu()\" onmouseover=\"showActionmenu()\"><ul><li>  <a id=\"modify\" href=\"#\">");
/*  180 */                 out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.edit"));
/*  181 */                 out.write(" </a></li><li>\n <a id=\"del\" href=\"#\">");
/*  182 */                 out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.delete"));
/*  183 */                 out.write("</a></li></ul></div>\n\n    </div>");
/*  184 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  185 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  189 */             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  190 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */             }
/*      */             
/*  193 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  194 */             out.write("\n  </div>  \n   <div id=\"parentcontainer\">\n    ");
/*  195 */             out.write("\n    \n    <div id=\"shapecontainer\">\n    \n\n      <div>\n        <div id=\"inner_shape\" style=\"height:600px;-moz-user-select:none;pointer-events:none;\">\n          \n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n      \n</body>\n</html>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapviewshapeconfig.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script type='text/javascript' src='/template/appmanager.js'></script>\n<script>\n\n\tvar zoom = 100;\n\tvar zoomoutscale = 1;\n\tvar zoomcount = 0;\n\tvar horizontalMove = 0;\n\tvar verticalMove = 0;\n\n\t$(document).ready(function(){\n\t\tShape.init();\n\t\tShapeConfig.init();\n\t\tdraw();\n\t});\n\n\tfunction draw()\n\t{\n\n\t\t$('#del').click(function(ev) { //No I18N\n\t\t\t$('#confirm-overlay').show();//No I18N\n\t\t\tvar mapViewName = encodeURIComponent($('#mapName').text());//No I18N\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=isSubGroup\",    //No I18N\n\t\t\t\tdata : \"mapViewName=\"+mapViewName,   //No I18N\n");
/*  196 */             out.write("\t\t\t\tsuccess : function(msg) {\n\t\t\t\t\tmsg = msg.replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (msg == \"TRUE\")//No I18N\n\t\t\t\t\t{\n\t\t\t\t\t\t$('#toDeleteBSG').hide();\t//No I18N\t\t\n\t\t\t\t\t\t$('#bsgText').text('");
/*  197 */             out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deletesubgroup"));
/*  198 */             out.write("'); // No I18N\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t});\n\t\t\t$('#delConfirmBox').show();//No I18N\n\t\t});\n\n\t\t$('#deleteOKMap').click(function(ev) { //No I18N\n\t\t\tvar isBSGToBeDeleted = $('#toDeleteBSG').is(\":checked\");//No I18N\n\t\t\tvar mapViewName1 = $('#mapName').text();//No I18N\n\t\t\tvar widgetid = ");
/*  199 */             out.print(request.getParameter("widgetid"));
/*  200 */             out.write(";//No I18N\n\t\t\tvar isPopUp = ");
/*  201 */             out.print(request.getParameter("isPopUp"));
/*  202 */             out.write(";//No I18N\n\t\t\tvar pageid= ");
/*  203 */             out.print(request.getParameter("pageid"));
/*  204 */             out.write(";\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=deleteMapView\",    //No I18N\n\t\t\t\tdata : {\"mapViewName\" : mapViewName1,\"bgToBeDeleted\" : isBSGToBeDeleted,\"widgetid\" : widgetid},   //No I18N\n\t\t\t\tsuccess : function(msg) {\n\t\t\t\t}\n\t\t\t});\t\t\t\n\t\t\twindow.location.href='/MyPage.do?method=getWidget&pageid='+pageid+'&isPopUp=true&widgetid='+widgetid+'&fromDeleteMap=true';//No I18N\t\t\t\n\t\t\tparent.location.href=parent.location.href;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\twindow.location.href = \"/MyPage.do?method=viewDashBoard\";//No I18N\t\t\t\t\n\t\t\t}\n\t\t});\n\t\t$('#deleteCancelMap').click(function(ev) { //No I18N\n\t\t\t$('#delConfirmBox').hide();//No I18N\n\t\t\t$('#confirm-overlay').hide();//No I18N\n\t\t});\n\n\t\t$('#arrow-plus-icon').click(function()//No I18N\n\t\t{\n\t\t\tif($('#arrow-plus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\tzoomcount = zoomcount + 1;\n\t\t\tvar shapecontainer = ShapeEditor.config.container; //ignorei18n_start\n");
/*  205 */             out.write("\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/*  206 */             out.write("\n\t\t\t//shapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/*  207 */             out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/*  208 */             out.write("\n\t\t\t\n\t\t\tif (zoomcount == 4){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-plus-icon').addClass(\"arrow-box-disable\");");
/*  209 */             out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -3){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/*  210 */             out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-minus-icon').click(function() ");
/*  211 */             out.write("\n\t\t{\n\t\t\tif($('#arrow-minus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tzoom = zoom - 20;\n\t\t\tzoomoutscale = zoomoutscale - 0.2;\n\t\t\tzoomcount = zoomcount - 1;\n\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/*  212 */             out.write("\n\t\t\tshapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/*  213 */             out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/*  214 */             out.write("\n\t\t\t\n\t\t\tif (zoomcount == 3){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/*  215 */             out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -4){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-minus-icon').addClass(\"arrow-box-disable\");");
/*  216 */             out.write("\n\t\t\t}\n\t\t\t\n\t  \t});\n\n\t\t$('#arrow-icon-center').click(function(){ ");
/*  217 */             out.write("\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tshapecontainer[0].style['webkitTransform'] = 'scale(1.0)';\n\t\t\tshapecontainer[0].style['msTransform'] = 'scale(1.0)';\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(1.0)\")\n\t\t\t$('#shapecontainer').css(\"zoom\",\"100%\");\n\t\t\t$('#shapecontainer').css(\"margin\",\"0px\");\n\t\t\tzoom = 100;\n\t\t\tzoomoutscale = 1;\n\t\t\tzoomcount = 0;\n\t\t\thorizontalMove = 0;\n\t\t\tverticalMove = 0;\n\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/*  218 */             out.write("\n\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/*  219 */             out.write("\n\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/*  220 */             out.write("\n\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/*  221 */             out.write("\n\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/*  222 */             out.write("\n\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/*  223 */             out.write("\n\t  \t});\n\t\n\t\t$('#arrow-icon-left').click(function(){\n\t\t\tif($('#arrow-icon-left').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"+=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove + 1;\n\t\t\tif (horizontalMove == 10){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-left').addClass(\"arrow-box-disable\");");
/*  224 */             out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -9){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/*  225 */             out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-right').click(function(){\n\t\t\tif($('#arrow-icon-right').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"-=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove - 1;\n\n\t\t\tif (horizontalMove == 9){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/*  226 */             out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -10){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-right').addClass(\"arrow-box-disable\");");
/*  227 */             out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-bottom').click(function(){\n\t\t\tif($('#arrow-icon-bottom').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"-=50px\"\n\t\t\t});\n\t\t\tverticalMove = verticalMove - 1;\n\n\t\t\tif (verticalMove == 4){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/*  228 */             out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -5){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-bottom').addClass(\"arrow-box-disable\");");
/*  229 */             out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-top').click(function(){\n\t\t\tif($('#arrow-icon-top').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"+=50px\"\n\t\t\t});\n\n\t\t\tverticalMove = verticalMove + 1;\n\t\t\tif (verticalMove == 5){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-top').addClass(\"arrow-box-disable\");");
/*  230 */             out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -4){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/*  231 */             out.write("\n\t\t\t}\n\t  \t});\n\n\t\tvar drawContainer = ShapeEditor.config.drawcontainer;\n\t\t$(drawContainer).click(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.click( data );\n\t\t});\n\n\t\tvar toolTip = $('#tooltip');\n\n\t\t$(drawContainer).mousemove(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t}\n\t\t\tif( !data ){\n\t\t\t\ttoolTip.hide();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.mousemove( data, event, toolTip );\n        \t});\n");
/*  232 */             out.write("\n\t\t//mouseout for tooltip\n\t\t$(\".shapegroup\").live(\"mouseout\", function(ev){//No I18N\n\t\t\t$('#tooltip').hide(); //No I18N\n\t\t});\n\n\t\t$('#modify').click(function( ev ){\n\t\t\tvar link = $(this);\n\t\t\tvar mapViewName = $('#mapName').text();//No I18N\n\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\t\t\t\n\t\t\tvar isPopUp = ");
/*  233 */             out.print(request.getParameter("isPopUp"));
/*  234 */             out.write(";//No I18N\n\t\t\tvar widgetid = ");
/*  235 */             out.print(request.getParameter("widgetid"));
/*  236 */             out.write(";//No I18N\n\t\t\tvar isSubMap = ");
/*  237 */             out.print(request.getParameter("isSubMap"));
/*  238 */             out.write(";//No I18N\t\t\t\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t\tfnOpenNewWindowWithHeightWidth(\"/mapViewAction.do?method=showMapViewEditor&mapViewName=\"+encodedMapViewName+\"&admin=true&type=modify&isPopUp=true&PRINTER_FRIENDLY=true&widgetid=\"+widgetid+\"&isSubMap=\"+isSubMap,'1000','660');//No I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\tlink.attr('href', '/mapViewAction.do?method=showMapViewEditor&mapViewName='+encodedMapViewName+'&admin=true&type=modify'); // No I18N\n\t\t\t}\t\t\n\t\t});\t\n\n\t\tvar jsonString = ");
/*  239 */             out.print(((JSONObject)request.getAttribute("jsonObj")).toString());
/*  240 */             out.write(";\n\t\tvar jsonText = JSON.stringify(jsonString);\n\t\tvar jsonObj = eval('(' + jsonText + ')');\n\t\t//alert(\"The jsonString \"+jsonText);\n\n\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t$('#mapName').html(mapViewName);\n\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\tvar myObject = JSON.parse(shape);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n\t                        myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n         \t                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n");
/*  241 */             out.write("\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\t\n\t\t\telse if(status == \"5\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"unmanaged\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"148\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t} \n\t\t\t\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\tvar showLabel = myObject.it360Props.showLabel; //No I18N\n");
/*  242 */             out.write("\t\t\tif (showLabel == \"TRUE\") //No I18N\n\t\t\t{\n\t\t\t\t$.ShapeData.text.show(myObject);//No I18N\t\n\t\t\t}else{\n\t\t\t\t$.ShapeData.text.hide(myObject);//No I18N\t\n\t\t\t}\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t}\n\t\t\telse if(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n");
/*  243 */             out.write("                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\t\n\t\t\tif(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n\t\t                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n");
/*  244 */             out.write("                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t} \n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape')); //ignorei18n_end\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\t\n\t\t}\n\t}\n\n</script>\n");
/*  245 */             out.write(10);
/*      */           } else {
/*  247 */             out.write(10);
/*  248 */             out.write("\n<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n\n\n\n\n\n\n\n\n\n\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n\n<title>");
/*  249 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  250 */             out.write("</title>\n\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n\n");
/*      */             
/*  252 */             boolean isMSP = EnterpriseUtil.isIt360MSPEdition();
/*  253 */             String widgetid = request.getParameter("widgetid");
/*      */             
/*  255 */             out.write(10);
/*  256 */             out.write(10);
/*  257 */             out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  258 */             out.write("\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n<script type=\"text/javascript\" src='/template/zshapes_editor.js'></script>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapvieweditor.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script>\n\n\tfunction saveBGImage()\n\t{\n\t\tdocument.MapViewForm.submit();\n\t\treturn;\n\t}\n\n\tvar isContainerModified = false;\n\n\t$(document).ready(function()\n\t{\n\t\tinit();\n\t\t$.fn.setUpMap($('#shapecontainer')); //ignorei18n_start\n\t\tupdateSelectedBgImage();\n\t\thandleForEdit();\n\t});\n\n\tfunction handleForEdit()\n\t{\n\t\tvar type = \"");
/*  259 */             if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */               return;
/*  261 */             out.write("\";\n\t\tif (type == \"modify\")\n\t\t{\n\t\t\t");
/*      */             
/*  263 */             String jObjString = null;
/*  264 */             JSONObject jObj = (JSONObject)request.getAttribute("jsonObj");
/*  265 */             if (jObj != null) {
/*  266 */               jObjString = jObj.toString();
/*      */             }
/*  268 */             out.write(";\n\t\t\t\n\t\t\tvar jsonString = ");
/*  269 */             out.print(jObjString);
/*  270 */             out.write(";\n\t\t\tvar jsonText = JSON.stringify(jsonString);\n\t\t\tvar jsonObj = eval('(' + jsonText + ')');\n\n\t\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t\t$('#map-heading h2').html('");
/*  271 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewedit"));
/*  272 */             out.write("'+mapViewName);\n\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t$('#overlay').hide();\n\t\t\t$('#businesName').val(mapViewName);\n\t\t\t$('#mapLocation').val(imgPath);\n\n\t\t\t$('#mapName').html(mapViewName);\n\t\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\tvar showLabel = myObject.it360Props.showLabel;\n\t\t\t\tif (showLabel == \"TRUE\")\n\t\t\t\t{\n\t\t\t\t\t$.ShapeData.text.show(myObject);\t\n\t\t\t\t}else{\n\t\t\t\t\t$.ShapeData.text.hide(myObject);\t\n\t\t\t\t}\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n");
/*  273 */             out.write("\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t}\n\t}\n\t\n\tfunction updateSelectedBgImage()\n\t{\n\t\t$('#bgBrowseFile').change(function(e){\n  \t\t$in=$(this);\n\t\tvar selectedImage = $in.val();\n\t\tvar selectElement = document.getElementById('mapLocation');\n\t\tvar newOption = document.createElement('option');\n\t\tvar selectedImage = selectedImage.substr(0, selectedImage.lastIndexOf('.'));\n\t\tnewOption.text = selectedImage;\n\t\tnewOption.value = selectedImage;\n\t\ttry {\n                    selectElement.add(newOption, null);\n                } catch(x) {\n                    selectElement.add(newOption);\n\t    \t}\n\t\tnewOption.selected = true;\n\t\t});\n\t}\n\t\n\tfunction init()\n\t{\n\t\tShape.init();\n\t\tShapeEvent.init();\n\t\t\t\n                $('#link').click(function( ev ){\n");
/*  274 */             out.write("                \t\n                \t//Returns if it has only one Device\n                \tvar devArray = new Array();\n        \t\t    var totalShapes = $.ShapeData.shapes;\n        \t\t    for (var key in totalShapes)\n        \t\t    {\n        \t\t\tvar obj = totalShapes[key];\n        \t\t\tif (obj.it360Props.category == \"DEVICE\")\n        \t\t\t{\t\n        \t\t\t\tvar deviceName = obj.it360Props.name;\n        \t\t\t\tdevArray.push(deviceName);\n        \t\t\t}\n        \t\t    }\n\n        \t\t    if (devArray.length == 1)\n        \t\t    {\n\t\t\t\t\t\t$(\".status-message\").html('");
/*  275 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkcreationcriteria"));
/*  276 */             out.write("');\n        \t\t\t\t$(\".status-message\").show();\n        \t\t\t\t$(\".status-message\").fadeOut(3500);\n        \t\t    \treturn;\n        \t\t    }\n                \t\n                    var json = {};\n                    json['shapeObjType'] = \"CONNECTOR\";\n                    json['type'] = \"LINE\";\n\t\t    var it360Props = {};\n\t\t    it360Props['category'] = \"CONNECTOR\";\n\t\t    json['it360Props']=it360Props;\n                    $.ShapeData.setShapeDetails(JSON.stringify(json));\n                });\n\n\t\tvar container = ShapeEditor.config.container;\n\t\tcontainer.bind(\"modified\", function(ev, data){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeModified( data );\n\t\t});\n\t\tcontainer.bind(\"shape_created\", function( ev, data ){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeCreated(data);\n\t\t});\n\n\t\t$('#closeMapLink').click(function(){ \n\t\t\tvar isPopUp = ");
/*  277 */             out.print(request.getParameter("isPopUp"));
/*  278 */             out.write(";\n\t\t\tvar widgetid = ");
/*  279 */             out.print(request.getParameter("widgetid"));
/*  280 */             out.write(";\t\t\t\n\t\t\tvar pageid= ");
/*  281 */             out.print(request.getParameter("pageid"));
/*  282 */             out.write(";\n\t\t\tvar mapName = '");
/*  283 */             out.print(request.getParameter("mapViewName"));
/*  284 */             out.write("';\n\t\t\tvar fromEditTopoWidget = ");
/*  285 */             out.print(request.getParameter("fromEditTopoWidget"));
/*  286 */             out.write(";\n\t\t\tvar isSubMap = ");
/*  287 */             out.print(request.getParameter("isSubMap"));
/*  288 */             out.write("; \t\t\t\t\n\t\t\t\tif(isPopUp !=null)\n\t\t\t\t{\n\t\t\t\t\tif(isPopUp && !isSubMap)\n\t\t\t\t\t{//invoked when editing map from widget itself.\t\t\t\t\t\n\t\t\t\t\t\tvar url = (window.location != window.parent.location) ? document.referrer: document.location;\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&oldtab=0&type=view&PRINTER_FRIENDLY=true&isPopUp=\"+isPopUp+\"&widgetid=\"+widgetid;\t\t\n\t\t\t\t\t\twindow.close();\t\t\n\t\t\t\t\t}\n\t\t\t\t\telse if(isSubMap)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&type=view&PRINTER_FRIENDLY=true&isPopUp=true&widgetid=\"+widgetid+\"&isSubMap=true\";\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif(fromEditTopoWidget!=null){//from edit topo widget - this will be invoked when create new topology map is invoked from editwidget page. \n\t\t\t\t\tif(fromEditTopoWidget)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"MyPage.do?method=editWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid;\t\t\t\t\t\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\t\t\t\n");
/*  289 */             out.write("\t\t\t\tif (isContainerModified)\n\t\t\t\t{\n\t\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\t\tvar r=confirm('");
/*  290 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  291 */             out.write("');\n\t\t\t\t\tif (r==true)\n\t  \t\t\t\t{\n\t\t\t\t\t\tsaveMap();\t\t\t\t\n\t  \t\t\t\t}\n\t\t\t\t\telse\n\t  \t\t\t\t{\t\t\t\t\n\t\t\t\t\t\twindow.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\"; \n\t  \t\t\t\t}\n\t\t\t\t\tisContainerModified = false;\t\n\t\t\t\t}\n\t\t\t});\n\n\t\t//Action for radio buttons available in Properties sheet\n\t\t$(\".popup-body input[type='radio']\").click(function() \n\t\t{\n        \t\tvar test = $(this).val();\n\t\t\t$(\"div.radio-div\").hide();\n\t\t        $(\"#\"+test).show();\n    \t\t}); \n\n\t\t//toggle toolbar\n\t\t$(\"#toolbar-nav\").click(function() {\n\t\t$(\".tool-txt-show\").toggleClass(\"tool-txt-hide\");\n\t\t$(\".tools-arrow\").toggle()\n\t\t$(\".nav-td2\").toggleClass(\"nav-td1\");\n\t\t\t});\n\t\t\n\t\t\n\t\t//On Pressing ESC key hide the pop-up\n\t\t$(document).keypress(function(e) \n\t\t{ \n\t\t\tif (e.keyCode == 27) \n\t\t\t{\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"mapAndDeviceBlock\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\twindow.location.href=\"/MyPage.do?method=viewDashBoard\";\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"PropertiesLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#mapSettingsBlock').hide();\n");
/*  292 */             out.write("\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"AddDeviceLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\n\t\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t\t$('#availDevices').empty();\n\t\t\t\t$('#selectedDevices').empty();\n\n\t\t\t\t$('#devicePropertiesCancel').click();\n\t\t\t\t$('#linePropertiesCancel').click();\n\t\t\t\t$('#scPropertiesCancel').click();\n\t\t\t\t\n\t\t\t\tShapeEditor.draw.clear();\n    \t\t\t}\n\t\t});\n\t\t\t\n\n\t\t$('#open').click(function( ev ){\n\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/*  293 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  294 */             out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//window.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\";\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\n\t\t\t}\n\t\t\tshowmenu();\n\t\t\tvar myDate = new Date();\n\t\t\t$.get(\"/mapViewAction.do?method=getListOfMapViews&myDate=\"+myDate, function(data){\n\t\t\t\tvar str_array = data.split(',');\n\t\t\t\t$newList = $(\"<ul />\");\n\t\t\t\tvar currentMapName = $('#businesName').val();\n\t\t\t\t\n\t\t\t\tfor(var i = 0; i < str_array.length; i++)\n\t\t\t\t{\n   \t\t\t\t\t// Trim the excess whitespace.\n\t\t\t\t\tvar mapViewName = str_array[i].replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (mapViewName != \"\" && mapViewName != currentMapName)\n\t\t\t\t\t{\n\t\t\t\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\n\t\t\t\t\t\t$newList.append(\"<li><a href='/mapViewAction.do?method=showMapViewEditor&mapViewName=\" +encodedMapViewName+\"&admin=true&type=modify'>\" + mapViewName + \"</a></li>\");\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\t$('#dropdown').append($newList);\n\t\t\t});\n\t\t});\n\n\n\t\t$('#newMap').click(function( ev ){//ignorei18n_end\n");
/*  295 */             out.write("\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/*  296 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  297 */             out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\t\t\t\t\n\t\t\t}\n\t\t\twindow.location.href = \"/mapViewAction.do?method=showMapViewEditor&admin=true&type=new\";\n\t\t});\n\t\t\n\t\t$('#save').click(function( ev )//No I18N \n\t\t{\n\t\t\tsaveMap();\n\t\t\tisContainerModified = false;\n\t\t});\n\n\t\tfunction saveMap()\n\t\t{\n\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\tvar jsonText = JSON.stringify(shapes);\n\t\t\tvar myObject = eval('(' + jsonText + ')');\n\n\t\t\tvar deviceShapes = new Array();\n\t\t\tvar scShapes = new Array();\n\t\t\tvar connectorShapes = new Array();\n\t\t\tfor (var key in shapes)\n\t\t\t{\n\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\tvar devJson = {};\n\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\tif (jsonObj.it360Props.category == \"DEVICE\")\n\t\t\t\t{\n\t\t\t\t\tdeviceShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"SHORTCUT\")\n\t\t\t\t{\n\t\t\t\t\tscShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t{\n\t\t\t\t\tjsonObj.it360Props.source=jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\tjsonObj.it360Props.destination=jsonObj.nvOProps.nvODProps.end.id;\n");
/*  298 */             out.write("\t\t\t\t\tconnectorShapes.push(devJson);\n\t\t\t\t}\n\t\t\t}\n\t\t\t\n\t\t\tvar mapView = {};\n\t\t\tvar fileName = document.getElementById('bgBrowseFile').value;\n\t\t\tif(fileName != \"\")\n\t\t\t{\n\t\t\t\tvar index = fileName.lastIndexOf(\"\\\\\");\n\t\t\t\tfileName = fileName.substring(index+1);\n\t\t\t}else{\n\t\t\t\tfileName = $('#mapLocation option:selected').val();\t//No I18N \n\t\t\t}\n\n\t\t\tmapView['bgImage'] = fileName;//No I18N \n\t\t\tmapView['mapViewName'] = $('#businesName').val();//No I18N \n\t\t\tmapView['widgetid'] = ");
/*  299 */             out.print(request.getParameter("widgetid"));
/*  300 */             out.write(";//No I18N\t\t\t\n\t\t\tmapView['fromWhere'] = '");
/*  301 */             out.print(request.getParameter("fromWhere"));
/*  302 */             out.write("';//No I18N\n\t\t\t\n\t\t\tvar finalObj = {};\n\t\t\tfinalObj['MAPVIEW'] = mapView;\n\t        finalObj['MAPVIEWDEVICE'] = deviceShapes;\n\t        finalObj['MAPVIEWLINK'] = connectorShapes;\n\t\t\tfinalObj['MAPVIEWSHORTCUT'] = scShapes;\n\t\t\tvar jsonStringToServer = encodeURIComponent(JSON.stringify(finalObj));\t\t\t\n\n\t\t\t$.ajax({\n\t\t            type:       \"post\",//No I18N \n            \t\turl:        \"/mapViewAction.do?method=saveMapView\",//No I18N \n\t\t            data:       \"data=\" + jsonStringToServer,//No I18N \n\t\t\t    success:    function(msg) \n\t\t\t    {\n\t\t\t\t\t$(\".status-message\").html(msg);//No I18N \n\t\t\t\t\t$(\".status-message\").show();//No I18N \n\t\t\t        $(\".status-message\").fadeOut(2500);\t//No I18N\t\t\t\n\t\t\t    }\n\t\t\t});\n\t\t}\n\t\t\n\t\t$('#availDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#availDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#selectedDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n");
/*  303 */             out.write("\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/*  304 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  305 */             out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/*  306 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  307 */             out.write("'+\"(\"+selDevSize+\")\");\n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#selDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#selectedDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#availDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/*  308 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  309 */             out.write("'+\"(\"+availDevSize+\")\");//No I18N \n\t\t\t$('#selDevicesText').text('");
/*  310 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  311 */             out.write("'+\"(\"+selDevSize+\")\");//No I18N \n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#window').scroll(function(){\t//No I18N \n\t\t\t//alert(\"Inside mouse scroll\");\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\");//No I18N \n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\");//No I18N \n\t\t});\n\n\t\t$('#availSelectAll').click(function(){//No I18N \n\t\t\tif($('#availSelectAll').is(\":checked\"))\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#selectedSelectAll').click(function(){//No I18N \n\t\t\tif($('#selectedSelectAll').is(\":checked\"))//No I18N \n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#customer').change(function(){//No I18N \n\t\t\t$('#availDevices').empty();//No I18N \n");
/*  312 */             out.write("\t\t\t$('#selectedDevices').empty();//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val(); //No I18N \n\t\t\tloadSites(customerName);\n\t\t\t\n\t\t});\n\n\t\t$('#site').change(function(){//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\t$('#devFilter').val(\"ALL\");//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\t\t\t\n\t\t});\t\n\n\t\t$('#devFilter').change(function(){//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tif(");
/*  313 */             out.print(isMSP);
/*  314 */             out.write("){\n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\tloadDeviceFilter(category);\n\t\t\t}\n\t\t});\n\t}\n\n\tfunction siteValidation()\n\t{\n\t\tif(");
/*  315 */             out.print(isMSP);
/*  316 */             out.write(")\n\t\t{\n\t\t\tif ($('#site option:selected').val() == undefined || $('#site option:selected').val() == \"\")\n\t\t\t{\n\t\t\t\t$('#site-val').show();\t//No I18N \n                                $('#site-val').delay(3000).fadeOut();//No I18N \t\t\t\t\t\t\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t}\t\n\n\tfunction launchMapHelp()\n\t{\n\t\twindow.open(\"/BSHELP/help/meitms/maps/maps.html\");\n\t} \n\n\tfunction del()\n\t{\n\t\tvar selectedShapes = ShapeEditor.select.shapes;\n\t\tfor(x=0; x< selectedShapes.length; x++)\n\t\t{\n\t\t\tvar shapeData = selectedShapes[x].data;\n\t\t\tvar id = selectedShapes[x].data.nvOProps.nvDProps.id;\n\t\t\tvar type = selectedShapes[x].data.type;\n\n\t\t\tif (shapeData.it360Props.category == \"SHORTCUT\")\n\t\t\t{\n\t\t\t\tvar r=confirm('");
/*  317 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deletescalert"));
/*  318 */             out.write(39);
/*  319 */             out.write(41);
/*  320 */             out.write(59);
/*  321 */             out.write("\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\t$.ShapeData.removeShape(id);\n\t\t\t\t\tdelete $.ShapeData.shapes[id];\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//Do not do anything\n  \t\t\t\t}\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\t$.ShapeData.removeShape(id);\n\t\t\tdelete $.ShapeData.shapes[id];\n\n\t\t\tif(type == \"SHAPE\")\n\t\t\t{\n\t\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\t\tfor (var key in shapes)\n\t\t\t\t{\n\t\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\t\tvar devJson = {};\n\t\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\t\tif (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t\t{\n\t\t\t\t\t\tvar startid= jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\t\tvar endid = jsonObj.nvOProps.nvODProps.end.id;\n\t\t\t\t\t\tif(startid == id || endid == id)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar connecterid = jsonObj.nvOProps.nvDProps.id;\n\t\t\t\t\t\t\t$.ShapeData.removeShape(connecterid);\n\t\t\t\t\t\t\tdelete $.ShapeData.shapes[connecterid];\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\n\n\tfunction selectBrowsedImage()\n\t{\n\t\tdocument.getElementById('bgBrowseFile').click();\n\t}\t\t\n\n\t$(function() \n\t{\n        //$('#mapAndDeviceBlock').draggable();\n\t    //$('#lineProperties').draggable();\n\t\t//$('#scProperties').draggable();\n");
/*  322 */             out.write("\t\t//$('#deviceProperties').draggable(); \n    \t});//ignorei18n_end \n</script>\n</head>\n\n<body>\n<div id=\"am.webclient.jsp.mapvieweditor.availdevices\" style=\"display:none\">");
/*  323 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  324 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.selecteddevices\" style=\"display:none\">");
/*  325 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  326 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.adddevices\" style=\"display:none\">");
/*  327 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevices"));
/*  328 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.allsites\" style=\"display:none\">");
/*  329 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/*  330 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.alldevices\" style=\"display:none\">");
/*  331 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.alldevices"));
/*  332 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.select\" style=\"display:none\">");
/*  333 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.select"));
/*  334 */             out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.mapviewprops\" style=\"display:none\">");
/*  335 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewprops"));
/*  336 */             out.write("</div>\n<div id=\"am.webclient.js.mapview.interfacesfor\" style=\"display:none\">");
/*  337 */             out.print(FormatUtil.getString("am.webclient.js.mapview.interfacesfor"));
/*  338 */             out.write("</div>\n<div id=\"overlay\"></div>\n<div id=\"maincontainer\">\n  <div id=\"menu-section\">\n    <div id=\"dropdown\" onmouseover=\"showmenu()\" onmouseout=\"hidemenu()\" style=\"display:none;\"> </div>\n    <div class=\"menus\">\n      <ul>\n      \t");
/*  339 */             if (widgetid == null)
/*      */             {
/*  341 */               out.write("\n        <li><a id=\"newMap\" title='");
/*  342 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/*  343 */               out.write("'><span>");
/*  344 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/*  345 */               out.write("</span></a></li>\n        <li><a id=\"open\" href=\"#\" title='");
/*  346 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/*  347 */               out.write("' onmouseout=\"hidemenu()\"><span>");
/*  348 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/*  349 */               out.write("</span></a></li>\n        ");
/*      */             }
/*  351 */             out.write("\n        <li><a id=\"save\" href=\"#\" title='");
/*  352 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/*  353 */             out.write("'><span>");
/*  354 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/*  355 */             out.write("</span></a></li>\n        <!--<li><a href=\"#\" title=\"Close\" id=\"closeMapLink\">Close</a></li>-->\n        <li><a id=\"closeMapLink\" href=\"#\" title='");
/*  356 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/*  357 */             out.write("'><span>");
/*  358 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/*  359 */             out.write("</span></a></li>\n      </ul>\n    </div>\n    \n\t<div id=\"saveMessage\" class=\"status-message\"></div>\t\n    \n    <div id=\"map-heading\">\n      <h2>");
/*  360 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapview"));
/*  361 */             out.write("</h2>\n    </div>\n  </div>\n  <div id=\"bodycontainer\">\n    <div id=\"mapAndDeviceBlock\" class=\"popup\">\n      <div class=\"popup-header\">\n<div><span class=\"newmap-hea\">");
/*  362 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.newmv"));
/*  363 */             out.write("</span>\n\t<span class=\"closeButton\" id=\"crossClose\"></span></div>       \n      </div>\n      <div class=\"popup-body\">\n        <div id=\"mapSettingsBlock\" class=\"popup-mainsection\">\n\t  <div class=\"error\" id=\"name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/*  364 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvnamepopup"));
/*  365 */             out.write(" </div>\n    \t</div>\n\t\t<div class=\"error\" id=\"name-length\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  366 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.namelength"));
/*  367 */             out.write("</div>");
/*  368 */             out.write("\n        </div>    \n        <div class=\"error\" id=\"name-check\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  369 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.nameexists"));
/*  370 */             out.write("</div>\n        </div>\n        <div class=\"error\" id=\"char-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  371 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.specialcharerr"));
/*  372 */             out.write("</div>");
/*  373 */             out.write("\n        </div> \n          <p>\n            <label for=\"businesName\" class=\"form-txt\">");
/*  374 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvname"));
/*  375 */             out.write("</label>\n            <input type=\"text\" value=\"\" name=\"businesName\" id=\"businesName\"/>\n          </p>         \n\t    <form name=\"FILE_UPLOAD_FORM\" action=\"/FileUpload.do?method=saveBGMap\" method=\"post\" enctype=\"multipart/form-data\">\n        <p>\n              <label>");
/*  376 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbg"));
/*  377 */             out.write("</label>\n              <span id=\"browse-sec\"> <span class=\"file-browse\">\n\t\t\t      <input type=\"file\" class=\"file\" name=\"bgBrowseFile\" id=\"bgBrowseFile\" align=\"absmiddle\" size=\"15\"/>\n\t\t\t      <iframe id=\"upload_target\" name=\"upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n              <select id=\"mapLocation\" name=\"mapLocation\"> \n                <option value=\"\">");
/*  378 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbgmap"));
/*  379 */             out.write("</option>\n                ");
/*      */             
/*  381 */             ArrayList<String> imagesArray = MapViewUtil.getListOfBGImages();
/*  382 */             int noOfImages = imagesArray.size();
/*  383 */             for (int i = 0; i < noOfImages; i++)
/*      */             {
/*  385 */               String imageName = (String)imagesArray.get(i);
/*      */               
/*  387 */               out.write("\n                <option value='");
/*  388 */               out.print(imageName);
/*  389 */               out.write(39);
/*  390 */               out.write(62);
/*  391 */               out.print(imageName);
/*  392 */               out.write("</option>\n                ");
/*      */             }
/*      */             
/*      */ 
/*  396 */             out.write("\n\t\t</select>\n\t      </span> </span> </span> </p> </form>\n         \n        </div>       \n          \n<div style=\"clear:both;\"></div>\n\n        <div id=\"addDevicesBlock\">\n         <h4 class=\"add-device-hea\">");
/*  397 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectdevice"));
/*  398 */             out.write("</h4>\n    \n    \t <div id=\"customerSite\">\n         \n     \t\t");
/*      */             
/*  400 */             if (isMSP)
/*      */             {
/*      */ 
/*  403 */               out.write("\t\n          <p>\n\t  <div class=\"error\" id=\"site-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/*  404 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsitepopup"));
/*  405 */               out.write(" </div>\n          </div>\n            <label for=\"site\" class=\"form-txt\">");
/*  406 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsite"));
/*  407 */               out.write("</label>\n            <select id=\"site\" name=\"site\">\n\t\t    ");
/*      */               
/*  409 */               String custName = request.getParameter("custName");
/*  410 */               if (custName == null)
/*      */               {
/*  412 */                 Properties custProp = null;
/*  413 */                 if (request.getSession().getAttribute("custProp") != null)
/*      */                 {
/*  415 */                   custProp = (Properties)request.getSession().getAttribute("custProp");
/*      */                 }
/*  417 */                 Enumeration custEnum = custProp.keys();
/*  418 */                 while (custEnum.hasMoreElements())
/*      */                 {
/*  420 */                   custName = custEnum.nextElement().toString();
/*      */                 }
/*      */               }
/*  423 */               String username = EnterpriseUtil.getLoggedInUserName(request);
/*  424 */               ArrayList<String> sitesArray = MapViewUtil.getSitesListForCustomer(custName, username);
/*  425 */               int noOfSites = sitesArray.size();
/*      */               
/*  427 */               out.write("\n\t\t   <option value='All Sites'>");
/*  428 */               out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/*  429 */               out.write("</option> ");
/*  430 */               out.write("\n\t\t   ");
/*      */               
/*  432 */               for (int i = 0; i < noOfSites; i++)
/*      */               {
/*  434 */                 String siteName = (String)sitesArray.get(i);
/*      */                 
/*  436 */                 out.write("\n                <option value='");
/*  437 */                 out.print(siteName);
/*  438 */                 out.write(39);
/*  439 */                 out.write(62);
/*  440 */                 out.print(siteName);
/*  441 */                 out.write("</option>\n         ");
/*      */               }
/*      */               
/*      */ 
/*  445 */               out.write("\n </select> </p>  <script>loadCustAvailAndSelectBox(\"\",\"All Sites\",\"All Monitors\");</script>  ");
/*  446 */               out.write("     \n\t\t");
/*      */             }
/*      */             
/*      */ 
/*  450 */             out.write("\t\t\t\n         \n           <p>\n            <label for=\"devFilter\" class=\"form-txt\">");
/*  451 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectcategory"));
/*  452 */             out.write("</label>\n            <select id=\"devFilter\" name=\"devFilter\">\n\t\t\t\t");
/*  453 */             if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */               return;
/*  455 */             out.write("\t\n\t        </select>         \n          </p>\n        </div>\n    \n    \t\n    \t  <div class=\"multi-select\">\t\n          <input type=\"hidden\" id=\"addDeviceOrigin\" name=\"addDeviceOrigin\" value=\"mapAndDeviceBlock\"/>\n            <span>\n            <label id=\"availDevicesText\">");
/*  456 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  457 */             out.write("(0)</label>\n            <select id=\"availDevices\" name=\"availDevices\" multiple size=\"7\">\n\t");
/*      */             
/*      */             HashMap<String, String> devicesMap;
/*  460 */             if (!isMSP)
/*      */             {
/*  462 */               devicesMap = MapViewUtil.getAllDevices1("ALL", null, request);
/*  463 */               for (String resId : devicesMap.keySet())
/*      */               {
/*  465 */                 String resourceId = resId;
/*  466 */                 String displayName = (String)devicesMap.get(resourceId);
/*      */                 
/*  468 */                 out.write("\n            <option value='");
/*  469 */                 out.print(resourceId);
/*  470 */                 out.write(39);
/*  471 */                 out.write(62);
/*  472 */                 out.print(displayName);
/*  473 */                 out.write("</option>\n     ");
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  478 */             out.write("\n\n\t <script>\n\t\t\tvar availDevSize = $('#availDevices option').length; ");
/*  479 */             out.write(" \n\t\t\tvar selDevSize = $('#selectedDevices option').length; ");
/*  480 */             out.write("\n\t\t\t$('#availDevicesText').text('");
/*  481 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  482 */             out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/*  483 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  484 */             out.write("'+\"(\"+selDevSize+\")\");\n\t\n\t</script>\n        </select>\n\t<p class=\"selectall\">\n\t<input id=\"availSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/>  <label class=\"select-label\">");
/*  485 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/*  486 */             out.write("</label></p>\n        </span>\n        \n        <span class=\"multiselect-but\">\n       \t\t<div><img src=\"/images/multiselect-arrow1.gif\" id=\"availDevButton\" /></div>\n\t\t<div><img src=\"/images/multiselect-arrow2.gif\" id=\"selDevButton\" /></div>\n        </span>\n        \n           <span>\n            <label id=\"selDevicesText\">");
/*  487 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  488 */             out.write("(0)</label>\n           <select id=\"selectedDevices\" name=\"\" multiple size=\"7\">\n        </select>\n\t<p class=\"selectall\"><input id=\"selectedSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/> <label class=\"select-label\">");
/*  489 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/*  490 */             out.write("</label></p>\n        </span>\n        \n        </div>\n        \n       \n        \n        </div>\n        \n        \n        \n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/*  491 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/*  492 */             out.write("' id=\"mapAndDevicePopupDone\" name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/*  493 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/*  494 */             out.write("' id=\"mapAndDevicePopupCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"deviceProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/*  495 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceprops"));
/*  496 */             out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossDevPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n       \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/*  497 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicename"));
/*  498 */             out.write("</label>\n            <input id=\"deviceName\" name=\"\" type=\"text\" />\n            <input name=\"\" type=\"checkbox\" id=\"showDevLabel\" value=\"\" checked=\"true\"/>\n          </p>\n          <p>\n            <label>");
/*  499 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/*  500 */             out.write("</label>\n            <input name=\"devIcons\" type=\"radio\" value=\"opt1\" id=\"rad-opt1\" checked>\n            <label class=\"radio-label\">");
/*  501 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/*  502 */             out.write("</label>\n            <input type=\"radio\" name=\"devIcons\" value=\"opt2\" id=\"rad-opt2\">\n            <label class=\"radio-label\">");
/*  503 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/*  504 */             out.write("</label>\n          </p>\n          <div id=\"opt1\" class=\"radio-div\" style=\"display:block;\">\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/*  505 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/*  506 */             out.write("</label>\n              <span> <img src=\"/images/square-nor.gif\" id=\"dev-icon-img1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"dev-icon-img2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"dev-icon-img3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"dev-icon-img4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"opt2\" class=\"radio-div\">\n           \n\t\t    <form name=\"IMAGE_UPLOAD_FORM\" action=\"/ImageUpload.do?method=saveDeviceImage\" method=\"post\" enctype=\"multipart/form-data\"> <p>\n                <label for=\"mapLocation\">");
/*  507 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceicons.select"));
/*  508 */             out.write("</label>\n\t\t<span id=\"browse-sec\"> <span class=\"file-browse\">\n              <input type=\"file\" name=\"BrowseDevices\" id=\"BrowseDevices\" align=\"absmiddle\" size=\"15\" />\n\t      <iframe id=\"image_upload_target\" name=\"image_upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n                <select id=\"deviceList\" name=\"deviceList\">\n                  <option>");
/*  509 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceimage.select"));
/*  510 */             out.write("</option>\n                  ");
/*      */             
/*  512 */             ArrayList<String> devImage = MapViewUtil.getListOfDeviceImages();
/*  513 */             int count = devImage.size();
/*  514 */             for (int i = 0; i < count; i++)
/*      */             {
/*  516 */               String imageName = (String)devImage.get(i);
/*      */               
/*  518 */               out.write("\n                  <option value='");
/*  519 */               out.print(imageName);
/*  520 */               out.write(39);
/*  521 */               out.write(62);
/*  522 */               out.print(imageName);
/*  523 */               out.write("</option> ");
/*  524 */               out.write("\n                  ");
/*      */             }
/*      */             
/*      */ 
/*  528 */             out.write("\n                </select>\n\t\t</span></span></span></p>\n\t</form>\n           \n            <!--div>\n              <p>\n                <label class=\"icon-label\"> ");
/*  529 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicebg.select"));
/*  530 */             out.write("</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" alt=\"RECT\" id=\"cus-icon-img1\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" alt=\"OVAL\" id=\"cus-icon-img2\"/><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" alt=\"DIAMOND\" id=\"cus-icon-img3\"/><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" alt=\"ISOSCELES_TRIANGLE\" id=\"cus-icon-img4\"/></span> </p>                \n            </div-->            \n          </div>\n          \n          \n                 <h4 class=\"add-device-hea\">");
/*  531 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/*  532 */             out.write("</h4>\n                 <p>\n            <label>");
/*  533 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/*  534 */             out.write("</label>\n            <select id=\"fontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option> ");
/*  535 */             out.write("\n\t      <option>Calibri</option> ");
/*  536 */             out.write("\n              <option>Comic San MS</option> ");
/*  537 */             out.write("\n              <option>Courier New</option> ");
/*  538 */             out.write("\n              <option>Georgia</option> ");
/*  539 */             out.write("\n              <option>Tahoma</option> ");
/*  540 */             out.write("\n              <option>Times New Roman</option> ");
/*  541 */             out.write("\n              <option>Trebuchet MS</option> ");
/*  542 */             out.write("\n              <option>Verdana</option> ");
/*  543 */             out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"fontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n          \n        </div>\n        <div class=\"popup-button\">\n          <input id=\"devicePropertiesDone\" name=\"\" value='");
/*  544 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/*  545 */             out.write("' type=\"button\" class=\"m-done-but\"/>\n          <input id=\"devicePropertiesCancel\" name=\"\" value='");
/*  546 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/*  547 */             out.write("' type=\"button\" class=\"m-cancel-but\" />\n        </div>\n      </div>\n    </div>\n    <div id=\"scProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/*  548 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scprops"));
/*  549 */             out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossScPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n        <div class=\"error\" id=\"sc-name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  550 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scnamecheck"));
/*  551 */             out.write("</div>\n          </div>\n          <p>\n            <label>");
/*  552 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scname"));
/*  553 */             out.write("</label>\n            <input id=\"scName\" name=\"\" type=\"text\" />\n          </p>\n          <div class=\"error\" id=\"sc-submap-val\"><span class=\"val-arrow\"></span>\n\t\t    <div class=\"error-box\"> ");
/*  554 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectmappopup"));
/*  555 */             out.write("</div>\n          </div>\n          <p>\n            <label>");
/*  556 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsubmap"));
/*  557 */             out.write("</label>\n\t    <span id=\"listsubmaps\"> </span> \n\t    </p>\n\n          <p>\n            <label>");
/*  558 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/*  559 */             out.write("</label>\n            <input id=\"sc-rad-opt1\" type=\"radio\" name=\"scIcons\" value=\"sc-opt1\" >\n            <label class=\"radio-label\">");
/*  560 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/*  561 */             out.write("</label>\n            <input id=\"sc-rad-opt2\" name=\"scIcons\" type=\"radio\" value=\"sc-opt2\" checked>\n            <label class=\"radio-label\">");
/*  562 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/*  563 */             out.write("</label>\n          </p>\n          <div id=\"sc-opt1\" class=\"radio-div\" >\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/*  564 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/*  565 */             out.write("</label>\n              <span><img src=\"/images/square-nor.gif\" id=\"scicon1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"scicon2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"scicon3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"scicon4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"sc-opt2\" class=\"radio-div\" style=\"display:block;\">\n            <div>\n              <p>\n                <label for=\"mapLocation\">");
/*  566 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscicons"));
/*  567 */             out.write("</label>\n                <select id=\"scDeviceList\">\n                  <option>");
/*  568 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscimage"));
/*  569 */             out.write("</option>\n\t\t \t\t  ");
/*      */             
/*  571 */             ArrayList<String> scImage = MapViewUtil.getListOfDeviceImages();
/*  572 */             int scCount = scImage.size();
/*  573 */             for (int i = 0; i < scCount; i++)
/*      */             {
/*  575 */               String imageName = (String)scImage.get(i);
/*      */               
/*  577 */               out.write("\n                  <option value='");
/*  578 */               out.print(imageName);
/*  579 */               out.write(39);
/*  580 */               out.write(62);
/*  581 */               out.print(imageName);
/*  582 */               out.write("</option> ");
/*  583 */               out.write("\n                  ");
/*      */             }
/*      */             
/*      */ 
/*  587 */             out.write("\n\t\t\t\t  <script>$('#scDeviceList').val(\"shortcut_icon.png\");</script>\n                </select>\n              </p>\n            </div>\n            <!--div>\n              <p>\n                <label class=\"icon-label\"> Select Device BG :</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" /></span> </p>\n            </div-->\n          </div>\n\t  <h4 class=\"add-device-hea\">");
/*  588 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/*  589 */             out.write("</h4>\n                 <p>\n            <label>");
/*  590 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/*  591 */             out.write("</label>\n            <select id=\"scFontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option>  ");
/*  592 */             out.write("\n\t      <option>Calibri</option> ");
/*  593 */             out.write("\n              <option>Comic San MS</option> ");
/*  594 */             out.write("\n              <option>Courier New</option> ");
/*  595 */             out.write("\n              <option>Georgia</option> ");
/*  596 */             out.write("\n              <option>Tahoma</option> ");
/*  597 */             out.write("\n              <option>Times New Roman</option> ");
/*  598 */             out.write("\n              <option>Trebuchet MS</option> ");
/*  599 */             out.write("\n              <option>Verdana</option> ");
/*  600 */             out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"scFontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n        </div>\n\t<p class=\"short-info\"><img src=\"/images/info.gif\" align=\"absmiddle\"/> ");
/*  601 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addscalert"));
/*  602 */             out.write(" </p>");
/*  603 */             out.write("\n        <input type=\"hidden\" id=\"scOrigin\" name=\"scOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/*  604 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/*  605 */             out.write("' id=\"scPropertiesDone\"  name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/*  606 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/*  607 */             out.write("' id=\"scPropertiesCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"lineProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/*  608 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkprops"));
/*  609 */             out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossLinePropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/*  610 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkname"));
/*  611 */             out.write(" </label>\n            <input name=\"textfield\" type=\"text\" id=\"linkName\" disabled=\"disabled\"/>\n          </p>\n\n\t  <p>\n            <label>");
/*  612 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkthickness"));
/*  613 */             out.write("</label>\n            <select id=\"linkThickness\">\n              <option>1</option>\n              <option>2</option>\n              <option>3</option>\n              <option>4</option>\n            </select>\n          </p>\n          <div>\n          <label>");
/*  614 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.getstatus"));
/*  615 */             out.write("</label>\n          <input id=\"srcIF\" name=\"radInterface\" type=\"radio\" value=\"srcIF\" checked/>\n          <label id=\"srcName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n          <select id=\"srcInterfaces\">\n          </select>\n          </span>\n\t</div>\n\t  <div style=\"clear:both;\"></div>\n\t  <div class=\"error1\" id=\"link-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/*  616 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectifpopup"));
/*  617 */             out.write(" </div>\n          </div>\t\n\t<div>\n          <label></label>\n          <input id=\"desIF\" name=\"radInterface\" type=\"radio\" value=\"desIF\" />\n          <label id=\"desName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n            <select id=\"desInterfaces\">\n            </select>\n          </span>\n        </div>\n\t</div>\n        <input type=\"hidden\" value=\"\" id=\"linkID\"/>\n        <input type=\"hidden\" id=\"linkOrigin\" name=\"linkOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/*  618 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/*  619 */             out.write("' id=\"linePropertiesDone\"  name=\"button\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/*  620 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/*  621 */             out.write("' id=\"linePropertiesCancel\" name=\"button\">\n        </div>\n      </div>\n    </div>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr>\n        <td width=\"43\" valign=\"top\" align=\"left\" class=\"nav-td2\" ><div id=\"map-toolbar\">\n            <ul>\n              <li id=\"toolbar-nav\">\n                <div class=\"map-tool-bg\"> <img src=\"/images/tools-arrow2.png\" class=\"tools-arrow\" title='");
/*  622 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.maximize"));
/*  623 */             out.write("'/><img src=\"/images/tools-arrow1.png\" class=\"tools-arrow\" style=\"display:none\" title='");
/*  624 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.minimize"));
/*  625 */             out.write("'/>\n                  <div class=\"tool-txt-show\">");
/*  626 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.tools"));
/*  627 */             out.write("</div>\n                </div>\n              </li>\n              <li id=\"addDeviceLink\" title='");
/*  628 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/*  629 */             out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-device.png\" />\n                  <div class=\"tool-txt-show\">");
/*  630 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/*  631 */             out.write("</div>\n                </div>\n              </li>\n              <li id=\"link\" title='");
/*  632 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/*  633 */             out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-link.png\"/>\n                  <div class=\"tool-txt-show\">");
/*  634 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/*  635 */             out.write("</div>\n                </div>\n              </li>\n              <li id=\"shortcut\" title='");
/*  636 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/*  637 */             out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-shortcut.png\"/>\n                  <div class=\"tool-txt-show\">");
/*  638 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/*  639 */             out.write("</div>\n                </div>\n              </li>\n               <li id=\"delete\" onClick='del()'; title='");
/*  640 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/*  641 */             out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-delete.png\"/>\n                  <div class=\"tool-txt-show\">");
/*  642 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/*  643 */             out.write("</div>\n                </div>\n              </li>\n              <li id=\"propertiesLink\" title='");
/*  644 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/*  645 */             out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-properties.png\"/>\n                  <div class=\"tool-txt-show\">");
/*  646 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/*  647 */             out.write("</div>\n                </div>\n              </li>\n              <li id=\"help\" onClick='launchMapHelp()'>\n                <div class=\"map-tool-bg\" title='");
/*  648 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/*  649 */             out.write("'> <img src=\"/images/m-help.png\"/>\n                  <div class=\"tool-txt-show\">");
/*  650 */             out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/*  651 */             out.write("</div>\n                </div>\n              </li>\n            </ul>\n          </div></td>\n        <td bgcolor=\"#FFFFFF\" width=\"auto\"><div style=\"position:relative; z-index:10;\">\n            <div id=\"parentcontainer\">\n              <div id=\"shapecontainer\">\n                <div id=\"inner_shape\" class=\"inner_shape\" style=\"z-index:2;height:600px;\"> </div>\n              </div>\n            </div>\n          </div></td>\n      </tr>\n    </table>\n  </div>\n</div>\n</body>\n</html>\n");
/*  652 */             out.write(10);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  657 */           ex.printStackTrace();
/*      */         }
/*      */         
/*  660 */         out.write(10);
/*  661 */         out.write(10);
/*  662 */         out.write(10);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  667 */         out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=7,IE=9\" />\n\n\n");
/*      */         
/*  669 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  670 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  671 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/*  673 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/*  674 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  675 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/*  677 */             out.write(10);
/*  678 */             if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  680 */             out.write(10);
/*  681 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  683 */             out.write(10);
/*  684 */             if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  686 */             out.write(10);
/*      */             
/*  688 */             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  689 */             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/*  690 */             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  692 */             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */             
/*  694 */             _jspx_th_tiles_005fput_005f3.setType("string");
/*  695 */             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/*  696 */             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/*  697 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  698 */                 out = _jspx_page_context.pushBody();
/*  699 */                 _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  700 */                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  703 */                 out.write(10);
/*  704 */                 String method = request.getParameter("method");
/*      */                 try
/*      */                 {
/*  707 */                   if ((method != null) && (method.equalsIgnoreCase("showMap")))
/*      */                   {
/*  709 */                     out.write(10);
/*  710 */                     out.write("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n\n\n\n");
/*  711 */                     out.write("\n<head>\n");
/*  712 */                     out.write("\n<title>");
/*  713 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  714 */                     out.write("</title>\n\n<style type=\"text/css\"></style>\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n\n\n\n</head>\n<body>\n\n<div id=\"delConfirmBox\" class=\"confirm-box\">\n<h3>");
/*  715 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.title"));
/*  716 */                     out.write("</h3>\n<div class=\"confirm-cont\">\n<p>");
/*  717 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.text"));
/*  718 */                     out.write("</p>\n<p><div class=\"confirm-input\"><input id=\"toDeleteBSG\" name=\"\" type=\"checkbox\" value=\"\" class=\"confirm-checkbox\" checked=\"checked\"/></div><div id='bsgText'>");
/*  719 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirmation.bsg"));
/*  720 */                     out.write("</div></p>\n<p style=\"text-align:center;\"><input name=\"\" id=\"deleteOKMap\" type=\"button\" value=\"OK\" class=\"confirm-but\"/> <input name=\"\" id=\"deleteCancelMap\" type=\"button\" value=\"Cancel\" class=\"confirm-but\"/></p>\n</div>\n</div>\n<div id=\"confirm-overlay\"></div>\n\n\n\n\t<div id=\"tooltip\" style=\"width:300px;display:none;position:absolute;\"> <span id=\"pointedarrow\" class=\"green-arrow\">&nbsp;</span>\n      <div id=\"colorofbox\" class=\"green-box\">\n        <h1 id=\"DetailHeading\"></h1>\n        <div class=\"mapbodytext\"> </div>\n      </div>\n    </div>\n<div id=\"main\">\n<div id=\"zoomingsec\">\n        <div id=\"z-plus-minus\">\n          <div id=\"arrow-box\">\n            <div id=\"arrow-plus-icon\" class=\"arrow-plus-icon\"><a href=\"#\" title='");
/*  721 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomin"));
/*  722 */                     out.write(39);
/*  723 */                     out.write(62);
/*  724 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  725 */                     out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:2px 0 0;\">\n            <div id=\"arrow-minus-icon\" class=\"arrow-minus-icon\"><a href=\"#\" title='");
/*  726 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomout"));
/*  727 */                     out.write(39);
/*  728 */                     out.write(62);
/*  729 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  730 */                     out.write("</a></div>\n          </div>\n        </div>\n        <div id=\"zoom-arrows\">\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-top\" class=\"arrow-icon-top\"><a href=\"#\" title='");
/*  731 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  732 */                     out.write(39);
/*  733 */                     out.write(62);
/*  734 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  735 */                     out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\">\n            <div id=\"arrow-icon-left\" class=\"arrow-icon-left\"><a href=\"#\" title='");
/*  736 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  737 */                     out.write(39);
/*  738 */                     out.write(62);
/*  739 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  740 */                     out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:0 1px;\">\n            <div id=\"arrow-icon-center\"><a href=\"#\" title='");
/*  741 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  742 */                     out.write(39);
/*  743 */                     out.write(62);
/*  744 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  745 */                     out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\">\n\t\t  <div id=\"arrow-icon-right\" class=\"arrow-icon-right\"><a href=\"#\" title='");
/*  746 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/*  747 */                     out.write(39);
/*  748 */                     out.write(62);
/*  749 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/*  750 */                     out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-bottom\" class=\"arrow-icon-bottom\"><a href=\"#\" title='");
/*  751 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/*  752 */                     out.write(39);
/*  753 */                     out.write(32);
/*  754 */                     out.write(62);
/*  755 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/*  756 */                     out.write("</a></div>\n          </div>\n        </div>\n</div>\n\n\n  <div class=\"map-border\">\n    <div id=\"mapName\" style=\"float:left\"></div>\n   ");
/*      */                     
/*  758 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  759 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  760 */                     _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                     
/*  762 */                     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  763 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  764 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/*  766 */                         out.write("<div style=\"float:right\" class=\"btnout1\" onmouseout=\"hideActionmenu()\" onclick=\"showActionmenu()\">\n\n");
/*  767 */                         out.print(FormatUtil.getString("am.mypage.widgettypes.topologymapwidget.action.text"));
/*  768 */                         out.write("<img width=\"7\" vspace=\"2\" height=\"4\" border=\"0\" src=\"/images/icon_black_arrow.gif\" valign=\"middle\">\n\n\t<div style=\"display:none;\" id=\"action_drop\" class=\"action-but-border1\" onmouseout=\"hideActionmenu()\" onmouseover=\"showActionmenu()\"><ul><li>  <a id=\"modify\" href=\"#\">");
/*  769 */                         out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.edit"));
/*  770 */                         out.write(" </a></li><li>\n <a id=\"del\" href=\"#\">");
/*  771 */                         out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.delete"));
/*  772 */                         out.write("</a></li></ul></div>\n\n    </div>");
/*  773 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  774 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  778 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  779 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                     }
/*      */                     
/*  782 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  783 */                     out.write("\n  </div>  \n   <div id=\"parentcontainer\">\n    ");
/*  784 */                     out.write("\n    \n    <div id=\"shapecontainer\">\n    \n\n      <div>\n        <div id=\"inner_shape\" style=\"height:600px;-moz-user-select:none;pointer-events:none;\">\n          \n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n      \n</body>\n</html>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapviewshapeconfig.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script type='text/javascript' src='/template/appmanager.js'></script>\n<script>\n\n\tvar zoom = 100;\n\tvar zoomoutscale = 1;\n\tvar zoomcount = 0;\n\tvar horizontalMove = 0;\n\tvar verticalMove = 0;\n\n\t$(document).ready(function(){\n\t\tShape.init();\n\t\tShapeConfig.init();\n\t\tdraw();\n\t});\n\n\tfunction draw()\n\t{\n\n\t\t$('#del').click(function(ev) { //No I18N\n\t\t\t$('#confirm-overlay').show();//No I18N\n\t\t\tvar mapViewName = encodeURIComponent($('#mapName').text());//No I18N\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=isSubGroup\",    //No I18N\n\t\t\t\tdata : \"mapViewName=\"+mapViewName,   //No I18N\n");
/*  785 */                     out.write("\t\t\t\tsuccess : function(msg) {\n\t\t\t\t\tmsg = msg.replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (msg == \"TRUE\")//No I18N\n\t\t\t\t\t{\n\t\t\t\t\t\t$('#toDeleteBSG').hide();\t//No I18N\t\t\n\t\t\t\t\t\t$('#bsgText').text('");
/*  786 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deletesubgroup"));
/*  787 */                     out.write("'); // No I18N\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t});\n\t\t\t$('#delConfirmBox').show();//No I18N\n\t\t});\n\n\t\t$('#deleteOKMap').click(function(ev) { //No I18N\n\t\t\tvar isBSGToBeDeleted = $('#toDeleteBSG').is(\":checked\");//No I18N\n\t\t\tvar mapViewName1 = $('#mapName').text();//No I18N\n\t\t\tvar widgetid = ");
/*  788 */                     out.print(request.getParameter("widgetid"));
/*  789 */                     out.write(";//No I18N\n\t\t\tvar isPopUp = ");
/*  790 */                     out.print(request.getParameter("isPopUp"));
/*  791 */                     out.write(";//No I18N\n\t\t\tvar pageid= ");
/*  792 */                     out.print(request.getParameter("pageid"));
/*  793 */                     out.write(";\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=deleteMapView\",    //No I18N\n\t\t\t\tdata : {\"mapViewName\" : mapViewName1,\"bgToBeDeleted\" : isBSGToBeDeleted,\"widgetid\" : widgetid},   //No I18N\n\t\t\t\tsuccess : function(msg) {\n\t\t\t\t}\n\t\t\t});\t\t\t\n\t\t\twindow.location.href='/MyPage.do?method=getWidget&pageid='+pageid+'&isPopUp=true&widgetid='+widgetid+'&fromDeleteMap=true';//No I18N\t\t\t\n\t\t\tparent.location.href=parent.location.href;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\twindow.location.href = \"/MyPage.do?method=viewDashBoard\";//No I18N\t\t\t\t\n\t\t\t}\n\t\t});\n\t\t$('#deleteCancelMap').click(function(ev) { //No I18N\n\t\t\t$('#delConfirmBox').hide();//No I18N\n\t\t\t$('#confirm-overlay').hide();//No I18N\n\t\t});\n\n\t\t$('#arrow-plus-icon').click(function()//No I18N\n\t\t{\n\t\t\tif($('#arrow-plus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\tzoomcount = zoomcount + 1;\n\t\t\tvar shapecontainer = ShapeEditor.config.container; //ignorei18n_start\n");
/*  794 */                     out.write("\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/*  795 */                     out.write("\n\t\t\t//shapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/*  796 */                     out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/*  797 */                     out.write("\n\t\t\t\n\t\t\tif (zoomcount == 4){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-plus-icon').addClass(\"arrow-box-disable\");");
/*  798 */                     out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -3){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/*  799 */                     out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-minus-icon').click(function() ");
/*  800 */                     out.write("\n\t\t{\n\t\t\tif($('#arrow-minus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tzoom = zoom - 20;\n\t\t\tzoomoutscale = zoomoutscale - 0.2;\n\t\t\tzoomcount = zoomcount - 1;\n\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/*  801 */                     out.write("\n\t\t\tshapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/*  802 */                     out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/*  803 */                     out.write("\n\t\t\t\n\t\t\tif (zoomcount == 3){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/*  804 */                     out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -4){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-minus-icon').addClass(\"arrow-box-disable\");");
/*  805 */                     out.write("\n\t\t\t}\n\t\t\t\n\t  \t});\n\n\t\t$('#arrow-icon-center').click(function(){ ");
/*  806 */                     out.write("\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tshapecontainer[0].style['webkitTransform'] = 'scale(1.0)';\n\t\t\tshapecontainer[0].style['msTransform'] = 'scale(1.0)';\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(1.0)\")\n\t\t\t$('#shapecontainer').css(\"zoom\",\"100%\");\n\t\t\t$('#shapecontainer').css(\"margin\",\"0px\");\n\t\t\tzoom = 100;\n\t\t\tzoomoutscale = 1;\n\t\t\tzoomcount = 0;\n\t\t\thorizontalMove = 0;\n\t\t\tverticalMove = 0;\n\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/*  807 */                     out.write("\n\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/*  808 */                     out.write("\n\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/*  809 */                     out.write("\n\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/*  810 */                     out.write("\n\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/*  811 */                     out.write("\n\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/*  812 */                     out.write("\n\t  \t});\n\t\n\t\t$('#arrow-icon-left').click(function(){\n\t\t\tif($('#arrow-icon-left').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"+=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove + 1;\n\t\t\tif (horizontalMove == 10){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-left').addClass(\"arrow-box-disable\");");
/*  813 */                     out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -9){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/*  814 */                     out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-right').click(function(){\n\t\t\tif($('#arrow-icon-right').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"-=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove - 1;\n\n\t\t\tif (horizontalMove == 9){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/*  815 */                     out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -10){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-right').addClass(\"arrow-box-disable\");");
/*  816 */                     out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-bottom').click(function(){\n\t\t\tif($('#arrow-icon-bottom').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"-=50px\"\n\t\t\t});\n\t\t\tverticalMove = verticalMove - 1;\n\n\t\t\tif (verticalMove == 4){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/*  817 */                     out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -5){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-bottom').addClass(\"arrow-box-disable\");");
/*  818 */                     out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-top').click(function(){\n\t\t\tif($('#arrow-icon-top').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"+=50px\"\n\t\t\t});\n\n\t\t\tverticalMove = verticalMove + 1;\n\t\t\tif (verticalMove == 5){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-top').addClass(\"arrow-box-disable\");");
/*  819 */                     out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -4){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/*  820 */                     out.write("\n\t\t\t}\n\t  \t});\n\n\t\tvar drawContainer = ShapeEditor.config.drawcontainer;\n\t\t$(drawContainer).click(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.click( data );\n\t\t});\n\n\t\tvar toolTip = $('#tooltip');\n\n\t\t$(drawContainer).mousemove(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t}\n\t\t\tif( !data ){\n\t\t\t\ttoolTip.hide();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.mousemove( data, event, toolTip );\n        \t});\n");
/*  821 */                     out.write("\n\t\t//mouseout for tooltip\n\t\t$(\".shapegroup\").live(\"mouseout\", function(ev){//No I18N\n\t\t\t$('#tooltip').hide(); //No I18N\n\t\t});\n\n\t\t$('#modify').click(function( ev ){\n\t\t\tvar link = $(this);\n\t\t\tvar mapViewName = $('#mapName').text();//No I18N\n\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\t\t\t\n\t\t\tvar isPopUp = ");
/*  822 */                     out.print(request.getParameter("isPopUp"));
/*  823 */                     out.write(";//No I18N\n\t\t\tvar widgetid = ");
/*  824 */                     out.print(request.getParameter("widgetid"));
/*  825 */                     out.write(";//No I18N\n\t\t\tvar isSubMap = ");
/*  826 */                     out.print(request.getParameter("isSubMap"));
/*  827 */                     out.write(";//No I18N\t\t\t\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t\tfnOpenNewWindowWithHeightWidth(\"/mapViewAction.do?method=showMapViewEditor&mapViewName=\"+encodedMapViewName+\"&admin=true&type=modify&isPopUp=true&PRINTER_FRIENDLY=true&widgetid=\"+widgetid+\"&isSubMap=\"+isSubMap,'1000','660');//No I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\tlink.attr('href', '/mapViewAction.do?method=showMapViewEditor&mapViewName='+encodedMapViewName+'&admin=true&type=modify'); // No I18N\n\t\t\t}\t\t\n\t\t});\t\n\n\t\tvar jsonString = ");
/*  828 */                     out.print(((JSONObject)request.getAttribute("jsonObj")).toString());
/*  829 */                     out.write(";\n\t\tvar jsonText = JSON.stringify(jsonString);\n\t\tvar jsonObj = eval('(' + jsonText + ')');\n\t\t//alert(\"The jsonString \"+jsonText);\n\n\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t$('#mapName').html(mapViewName);\n\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\tvar myObject = JSON.parse(shape);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n\t                        myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n         \t                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n");
/*  830 */                     out.write("\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\t\n\t\t\telse if(status == \"5\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"unmanaged\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"148\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t} \n\t\t\t\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\tvar showLabel = myObject.it360Props.showLabel; //No I18N\n");
/*  831 */                     out.write("\t\t\tif (showLabel == \"TRUE\") //No I18N\n\t\t\t{\n\t\t\t\t$.ShapeData.text.show(myObject);//No I18N\t\n\t\t\t}else{\n\t\t\t\t$.ShapeData.text.hide(myObject);//No I18N\t\n\t\t\t}\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t}\n\t\t\telse if(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n");
/*  832 */                     out.write("                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\t\n\t\t\tif(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n\t\t                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n");
/*  833 */                     out.write("                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t} \n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape')); //ignorei18n_end\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\t\n\t\t}\n\t}\n\n</script>\n");
/*  834 */                     out.write(10);
/*      */                   } else {
/*  836 */                     out.write(10);
/*  837 */                     out.write("\n<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n\n\n\n\n\n\n\n\n\n\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n\n<title>");
/*  838 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  839 */                     out.write("</title>\n\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n\n");
/*      */                     
/*  841 */                     boolean isMSP = EnterpriseUtil.isIt360MSPEdition();
/*  842 */                     String widgetid = request.getParameter("widgetid");
/*      */                     
/*  844 */                     out.write(10);
/*  845 */                     out.write(10);
/*  846 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  847 */                     out.write("\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n<script type=\"text/javascript\" src='/template/zshapes_editor.js'></script>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapvieweditor.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script>\n\n\tfunction saveBGImage()\n\t{\n\t\tdocument.MapViewForm.submit();\n\t\treturn;\n\t}\n\n\tvar isContainerModified = false;\n\n\t$(document).ready(function()\n\t{\n\t\tinit();\n\t\t$.fn.setUpMap($('#shapecontainer')); //ignorei18n_start\n\t\tupdateSelectedBgImage();\n\t\thandleForEdit();\n\t});\n\n\tfunction handleForEdit()\n\t{\n\t\tvar type = \"");
/*  848 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/*  850 */                     out.write("\";\n\t\tif (type == \"modify\")\n\t\t{\n\t\t\t");
/*      */                     
/*  852 */                     String jObjString = null;
/*  853 */                     JSONObject jObj = (JSONObject)request.getAttribute("jsonObj");
/*  854 */                     if (jObj != null) {
/*  855 */                       jObjString = jObj.toString();
/*      */                     }
/*  857 */                     out.write(";\n\t\t\t\n\t\t\tvar jsonString = ");
/*  858 */                     out.print(jObjString);
/*  859 */                     out.write(";\n\t\t\tvar jsonText = JSON.stringify(jsonString);\n\t\t\tvar jsonObj = eval('(' + jsonText + ')');\n\n\t\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t\t$('#map-heading h2').html('");
/*  860 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewedit"));
/*  861 */                     out.write("'+mapViewName);\n\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t$('#overlay').hide();\n\t\t\t$('#businesName').val(mapViewName);\n\t\t\t$('#mapLocation').val(imgPath);\n\n\t\t\t$('#mapName').html(mapViewName);\n\t\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\tvar showLabel = myObject.it360Props.showLabel;\n\t\t\t\tif (showLabel == \"TRUE\")\n\t\t\t\t{\n\t\t\t\t\t$.ShapeData.text.show(myObject);\t\n\t\t\t\t}else{\n\t\t\t\t\t$.ShapeData.text.hide(myObject);\t\n\t\t\t\t}\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n");
/*  862 */                     out.write("\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t}\n\t}\n\t\n\tfunction updateSelectedBgImage()\n\t{\n\t\t$('#bgBrowseFile').change(function(e){\n  \t\t$in=$(this);\n\t\tvar selectedImage = $in.val();\n\t\tvar selectElement = document.getElementById('mapLocation');\n\t\tvar newOption = document.createElement('option');\n\t\tvar selectedImage = selectedImage.substr(0, selectedImage.lastIndexOf('.'));\n\t\tnewOption.text = selectedImage;\n\t\tnewOption.value = selectedImage;\n\t\ttry {\n                    selectElement.add(newOption, null);\n                } catch(x) {\n                    selectElement.add(newOption);\n\t    \t}\n\t\tnewOption.selected = true;\n\t\t});\n\t}\n\t\n\tfunction init()\n\t{\n\t\tShape.init();\n\t\tShapeEvent.init();\n\t\t\t\n                $('#link').click(function( ev ){\n");
/*  863 */                     out.write("                \t\n                \t//Returns if it has only one Device\n                \tvar devArray = new Array();\n        \t\t    var totalShapes = $.ShapeData.shapes;\n        \t\t    for (var key in totalShapes)\n        \t\t    {\n        \t\t\tvar obj = totalShapes[key];\n        \t\t\tif (obj.it360Props.category == \"DEVICE\")\n        \t\t\t{\t\n        \t\t\t\tvar deviceName = obj.it360Props.name;\n        \t\t\t\tdevArray.push(deviceName);\n        \t\t\t}\n        \t\t    }\n\n        \t\t    if (devArray.length == 1)\n        \t\t    {\n\t\t\t\t\t\t$(\".status-message\").html('");
/*  864 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkcreationcriteria"));
/*  865 */                     out.write("');\n        \t\t\t\t$(\".status-message\").show();\n        \t\t\t\t$(\".status-message\").fadeOut(3500);\n        \t\t    \treturn;\n        \t\t    }\n                \t\n                    var json = {};\n                    json['shapeObjType'] = \"CONNECTOR\";\n                    json['type'] = \"LINE\";\n\t\t    var it360Props = {};\n\t\t    it360Props['category'] = \"CONNECTOR\";\n\t\t    json['it360Props']=it360Props;\n                    $.ShapeData.setShapeDetails(JSON.stringify(json));\n                });\n\n\t\tvar container = ShapeEditor.config.container;\n\t\tcontainer.bind(\"modified\", function(ev, data){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeModified( data );\n\t\t});\n\t\tcontainer.bind(\"shape_created\", function( ev, data ){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeCreated(data);\n\t\t});\n\n\t\t$('#closeMapLink').click(function(){ \n\t\t\tvar isPopUp = ");
/*  866 */                     out.print(request.getParameter("isPopUp"));
/*  867 */                     out.write(";\n\t\t\tvar widgetid = ");
/*  868 */                     out.print(request.getParameter("widgetid"));
/*  869 */                     out.write(";\t\t\t\n\t\t\tvar pageid= ");
/*  870 */                     out.print(request.getParameter("pageid"));
/*  871 */                     out.write(";\n\t\t\tvar mapName = '");
/*  872 */                     out.print(request.getParameter("mapViewName"));
/*  873 */                     out.write("';\n\t\t\tvar fromEditTopoWidget = ");
/*  874 */                     out.print(request.getParameter("fromEditTopoWidget"));
/*  875 */                     out.write(";\n\t\t\tvar isSubMap = ");
/*  876 */                     out.print(request.getParameter("isSubMap"));
/*  877 */                     out.write("; \t\t\t\t\n\t\t\t\tif(isPopUp !=null)\n\t\t\t\t{\n\t\t\t\t\tif(isPopUp && !isSubMap)\n\t\t\t\t\t{//invoked when editing map from widget itself.\t\t\t\t\t\n\t\t\t\t\t\tvar url = (window.location != window.parent.location) ? document.referrer: document.location;\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&oldtab=0&type=view&PRINTER_FRIENDLY=true&isPopUp=\"+isPopUp+\"&widgetid=\"+widgetid;\t\t\n\t\t\t\t\t\twindow.close();\t\t\n\t\t\t\t\t}\n\t\t\t\t\telse if(isSubMap)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&type=view&PRINTER_FRIENDLY=true&isPopUp=true&widgetid=\"+widgetid+\"&isSubMap=true\";\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif(fromEditTopoWidget!=null){//from edit topo widget - this will be invoked when create new topology map is invoked from editwidget page. \n\t\t\t\t\tif(fromEditTopoWidget)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"MyPage.do?method=editWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid;\t\t\t\t\t\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\t\t\t\n");
/*  878 */                     out.write("\t\t\t\tif (isContainerModified)\n\t\t\t\t{\n\t\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\t\tvar r=confirm('");
/*  879 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  880 */                     out.write("');\n\t\t\t\t\tif (r==true)\n\t  \t\t\t\t{\n\t\t\t\t\t\tsaveMap();\t\t\t\t\n\t  \t\t\t\t}\n\t\t\t\t\telse\n\t  \t\t\t\t{\t\t\t\t\n\t\t\t\t\t\twindow.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\"; \n\t  \t\t\t\t}\n\t\t\t\t\tisContainerModified = false;\t\n\t\t\t\t}\n\t\t\t});\n\n\t\t//Action for radio buttons available in Properties sheet\n\t\t$(\".popup-body input[type='radio']\").click(function() \n\t\t{\n        \t\tvar test = $(this).val();\n\t\t\t$(\"div.radio-div\").hide();\n\t\t        $(\"#\"+test).show();\n    \t\t}); \n\n\t\t//toggle toolbar\n\t\t$(\"#toolbar-nav\").click(function() {\n\t\t$(\".tool-txt-show\").toggleClass(\"tool-txt-hide\");\n\t\t$(\".tools-arrow\").toggle()\n\t\t$(\".nav-td2\").toggleClass(\"nav-td1\");\n\t\t\t});\n\t\t\n\t\t\n\t\t//On Pressing ESC key hide the pop-up\n\t\t$(document).keypress(function(e) \n\t\t{ \n\t\t\tif (e.keyCode == 27) \n\t\t\t{\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"mapAndDeviceBlock\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\twindow.location.href=\"/MyPage.do?method=viewDashBoard\";\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"PropertiesLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#mapSettingsBlock').hide();\n");
/*  881 */                     out.write("\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"AddDeviceLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\n\t\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t\t$('#availDevices').empty();\n\t\t\t\t$('#selectedDevices').empty();\n\n\t\t\t\t$('#devicePropertiesCancel').click();\n\t\t\t\t$('#linePropertiesCancel').click();\n\t\t\t\t$('#scPropertiesCancel').click();\n\t\t\t\t\n\t\t\t\tShapeEditor.draw.clear();\n    \t\t\t}\n\t\t});\n\t\t\t\n\n\t\t$('#open').click(function( ev ){\n\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/*  882 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  883 */                     out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//window.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\";\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\n\t\t\t}\n\t\t\tshowmenu();\n\t\t\tvar myDate = new Date();\n\t\t\t$.get(\"/mapViewAction.do?method=getListOfMapViews&myDate=\"+myDate, function(data){\n\t\t\t\tvar str_array = data.split(',');\n\t\t\t\t$newList = $(\"<ul />\");\n\t\t\t\tvar currentMapName = $('#businesName').val();\n\t\t\t\t\n\t\t\t\tfor(var i = 0; i < str_array.length; i++)\n\t\t\t\t{\n   \t\t\t\t\t// Trim the excess whitespace.\n\t\t\t\t\tvar mapViewName = str_array[i].replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (mapViewName != \"\" && mapViewName != currentMapName)\n\t\t\t\t\t{\n\t\t\t\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\n\t\t\t\t\t\t$newList.append(\"<li><a href='/mapViewAction.do?method=showMapViewEditor&mapViewName=\" +encodedMapViewName+\"&admin=true&type=modify'>\" + mapViewName + \"</a></li>\");\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\t$('#dropdown').append($newList);\n\t\t\t});\n\t\t});\n\n\n\t\t$('#newMap').click(function( ev ){//ignorei18n_end\n");
/*  884 */                     out.write("\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/*  885 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/*  886 */                     out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\t\t\t\t\n\t\t\t}\n\t\t\twindow.location.href = \"/mapViewAction.do?method=showMapViewEditor&admin=true&type=new\";\n\t\t});\n\t\t\n\t\t$('#save').click(function( ev )//No I18N \n\t\t{\n\t\t\tsaveMap();\n\t\t\tisContainerModified = false;\n\t\t});\n\n\t\tfunction saveMap()\n\t\t{\n\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\tvar jsonText = JSON.stringify(shapes);\n\t\t\tvar myObject = eval('(' + jsonText + ')');\n\n\t\t\tvar deviceShapes = new Array();\n\t\t\tvar scShapes = new Array();\n\t\t\tvar connectorShapes = new Array();\n\t\t\tfor (var key in shapes)\n\t\t\t{\n\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\tvar devJson = {};\n\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\tif (jsonObj.it360Props.category == \"DEVICE\")\n\t\t\t\t{\n\t\t\t\t\tdeviceShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"SHORTCUT\")\n\t\t\t\t{\n\t\t\t\t\tscShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t{\n\t\t\t\t\tjsonObj.it360Props.source=jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\tjsonObj.it360Props.destination=jsonObj.nvOProps.nvODProps.end.id;\n");
/*  887 */                     out.write("\t\t\t\t\tconnectorShapes.push(devJson);\n\t\t\t\t}\n\t\t\t}\n\t\t\t\n\t\t\tvar mapView = {};\n\t\t\tvar fileName = document.getElementById('bgBrowseFile').value;\n\t\t\tif(fileName != \"\")\n\t\t\t{\n\t\t\t\tvar index = fileName.lastIndexOf(\"\\\\\");\n\t\t\t\tfileName = fileName.substring(index+1);\n\t\t\t}else{\n\t\t\t\tfileName = $('#mapLocation option:selected').val();\t//No I18N \n\t\t\t}\n\n\t\t\tmapView['bgImage'] = fileName;//No I18N \n\t\t\tmapView['mapViewName'] = $('#businesName').val();//No I18N \n\t\t\tmapView['widgetid'] = ");
/*  888 */                     out.print(request.getParameter("widgetid"));
/*  889 */                     out.write(";//No I18N\t\t\t\n\t\t\tmapView['fromWhere'] = '");
/*  890 */                     out.print(request.getParameter("fromWhere"));
/*  891 */                     out.write("';//No I18N\n\t\t\t\n\t\t\tvar finalObj = {};\n\t\t\tfinalObj['MAPVIEW'] = mapView;\n\t        finalObj['MAPVIEWDEVICE'] = deviceShapes;\n\t        finalObj['MAPVIEWLINK'] = connectorShapes;\n\t\t\tfinalObj['MAPVIEWSHORTCUT'] = scShapes;\n\t\t\tvar jsonStringToServer = encodeURIComponent(JSON.stringify(finalObj));\t\t\t\n\n\t\t\t$.ajax({\n\t\t            type:       \"post\",//No I18N \n            \t\turl:        \"/mapViewAction.do?method=saveMapView\",//No I18N \n\t\t            data:       \"data=\" + jsonStringToServer,//No I18N \n\t\t\t    success:    function(msg) \n\t\t\t    {\n\t\t\t\t\t$(\".status-message\").html(msg);//No I18N \n\t\t\t\t\t$(\".status-message\").show();//No I18N \n\t\t\t        $(\".status-message\").fadeOut(2500);\t//No I18N\t\t\t\n\t\t\t    }\n\t\t\t});\n\t\t}\n\t\t\n\t\t$('#availDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#availDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#selectedDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n");
/*  892 */                     out.write("\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/*  893 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  894 */                     out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/*  895 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  896 */                     out.write("'+\"(\"+selDevSize+\")\");\n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#selDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#selectedDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#availDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/*  897 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  898 */                     out.write("'+\"(\"+availDevSize+\")\");//No I18N \n\t\t\t$('#selDevicesText').text('");
/*  899 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  900 */                     out.write("'+\"(\"+selDevSize+\")\");//No I18N \n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#window').scroll(function(){\t//No I18N \n\t\t\t//alert(\"Inside mouse scroll\");\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\");//No I18N \n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\");//No I18N \n\t\t});\n\n\t\t$('#availSelectAll').click(function(){//No I18N \n\t\t\tif($('#availSelectAll').is(\":checked\"))\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#selectedSelectAll').click(function(){//No I18N \n\t\t\tif($('#selectedSelectAll').is(\":checked\"))//No I18N \n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#customer').change(function(){//No I18N \n\t\t\t$('#availDevices').empty();//No I18N \n");
/*  901 */                     out.write("\t\t\t$('#selectedDevices').empty();//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val(); //No I18N \n\t\t\tloadSites(customerName);\n\t\t\t\n\t\t});\n\n\t\t$('#site').change(function(){//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\t$('#devFilter').val(\"ALL\");//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\t\t\t\n\t\t});\t\n\n\t\t$('#devFilter').change(function(){//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tif(");
/*  902 */                     out.print(isMSP);
/*  903 */                     out.write("){\n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\tloadDeviceFilter(category);\n\t\t\t}\n\t\t});\n\t}\n\n\tfunction siteValidation()\n\t{\n\t\tif(");
/*  904 */                     out.print(isMSP);
/*  905 */                     out.write(")\n\t\t{\n\t\t\tif ($('#site option:selected').val() == undefined || $('#site option:selected').val() == \"\")\n\t\t\t{\n\t\t\t\t$('#site-val').show();\t//No I18N \n                                $('#site-val').delay(3000).fadeOut();//No I18N \t\t\t\t\t\t\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t}\t\n\n\tfunction launchMapHelp()\n\t{\n\t\twindow.open(\"/BSHELP/help/meitms/maps/maps.html\");\n\t} \n\n\tfunction del()\n\t{\n\t\tvar selectedShapes = ShapeEditor.select.shapes;\n\t\tfor(x=0; x< selectedShapes.length; x++)\n\t\t{\n\t\t\tvar shapeData = selectedShapes[x].data;\n\t\t\tvar id = selectedShapes[x].data.nvOProps.nvDProps.id;\n\t\t\tvar type = selectedShapes[x].data.type;\n\n\t\t\tif (shapeData.it360Props.category == \"SHORTCUT\")\n\t\t\t{\n\t\t\t\tvar r=confirm('");
/*  906 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deletescalert"));
/*  907 */                     out.write(39);
/*  908 */                     out.write(41);
/*  909 */                     out.write(59);
/*  910 */                     out.write("\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\t$.ShapeData.removeShape(id);\n\t\t\t\t\tdelete $.ShapeData.shapes[id];\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//Do not do anything\n  \t\t\t\t}\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\t$.ShapeData.removeShape(id);\n\t\t\tdelete $.ShapeData.shapes[id];\n\n\t\t\tif(type == \"SHAPE\")\n\t\t\t{\n\t\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\t\tfor (var key in shapes)\n\t\t\t\t{\n\t\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\t\tvar devJson = {};\n\t\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\t\tif (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t\t{\n\t\t\t\t\t\tvar startid= jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\t\tvar endid = jsonObj.nvOProps.nvODProps.end.id;\n\t\t\t\t\t\tif(startid == id || endid == id)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar connecterid = jsonObj.nvOProps.nvDProps.id;\n\t\t\t\t\t\t\t$.ShapeData.removeShape(connecterid);\n\t\t\t\t\t\t\tdelete $.ShapeData.shapes[connecterid];\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\n\n\tfunction selectBrowsedImage()\n\t{\n\t\tdocument.getElementById('bgBrowseFile').click();\n\t}\t\t\n\n\t$(function() \n\t{\n        //$('#mapAndDeviceBlock').draggable();\n\t    //$('#lineProperties').draggable();\n\t\t//$('#scProperties').draggable();\n");
/*  911 */                     out.write("\t\t//$('#deviceProperties').draggable(); \n    \t});//ignorei18n_end \n</script>\n</head>\n\n<body>\n<div id=\"am.webclient.jsp.mapvieweditor.availdevices\" style=\"display:none\">");
/*  912 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/*  913 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.selecteddevices\" style=\"display:none\">");
/*  914 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/*  915 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.adddevices\" style=\"display:none\">");
/*  916 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevices"));
/*  917 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.allsites\" style=\"display:none\">");
/*  918 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/*  919 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.alldevices\" style=\"display:none\">");
/*  920 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.alldevices"));
/*  921 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.select\" style=\"display:none\">");
/*  922 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.select"));
/*  923 */                     out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.mapviewprops\" style=\"display:none\">");
/*  924 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewprops"));
/*  925 */                     out.write("</div>\n<div id=\"am.webclient.js.mapview.interfacesfor\" style=\"display:none\">");
/*  926 */                     out.print(FormatUtil.getString("am.webclient.js.mapview.interfacesfor"));
/*  927 */                     out.write("</div>\n<div id=\"overlay\"></div>\n<div id=\"maincontainer\">\n  <div id=\"menu-section\">\n    <div id=\"dropdown\" onmouseover=\"showmenu()\" onmouseout=\"hidemenu()\" style=\"display:none;\"> </div>\n    <div class=\"menus\">\n      <ul>\n      \t");
/*  928 */                     if (widgetid == null)
/*      */                     {
/*  930 */                       out.write("\n        <li><a id=\"newMap\" title='");
/*  931 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/*  932 */                       out.write("'><span>");
/*  933 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/*  934 */                       out.write("</span></a></li>\n        <li><a id=\"open\" href=\"#\" title='");
/*  935 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/*  936 */                       out.write("' onmouseout=\"hidemenu()\"><span>");
/*  937 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/*  938 */                       out.write("</span></a></li>\n        ");
/*      */                     }
/*  940 */                     out.write("\n        <li><a id=\"save\" href=\"#\" title='");
/*  941 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/*  942 */                     out.write("'><span>");
/*  943 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/*  944 */                     out.write("</span></a></li>\n        <!--<li><a href=\"#\" title=\"Close\" id=\"closeMapLink\">Close</a></li>-->\n        <li><a id=\"closeMapLink\" href=\"#\" title='");
/*  945 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/*  946 */                     out.write("'><span>");
/*  947 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/*  948 */                     out.write("</span></a></li>\n      </ul>\n    </div>\n    \n\t<div id=\"saveMessage\" class=\"status-message\"></div>\t\n    \n    <div id=\"map-heading\">\n      <h2>");
/*  949 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapview"));
/*  950 */                     out.write("</h2>\n    </div>\n  </div>\n  <div id=\"bodycontainer\">\n    <div id=\"mapAndDeviceBlock\" class=\"popup\">\n      <div class=\"popup-header\">\n<div><span class=\"newmap-hea\">");
/*  951 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.newmv"));
/*  952 */                     out.write("</span>\n\t<span class=\"closeButton\" id=\"crossClose\"></span></div>       \n      </div>\n      <div class=\"popup-body\">\n        <div id=\"mapSettingsBlock\" class=\"popup-mainsection\">\n\t  <div class=\"error\" id=\"name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/*  953 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvnamepopup"));
/*  954 */                     out.write(" </div>\n    \t</div>\n\t\t<div class=\"error\" id=\"name-length\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  955 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.namelength"));
/*  956 */                     out.write("</div>");
/*  957 */                     out.write("\n        </div>    \n        <div class=\"error\" id=\"name-check\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  958 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.nameexists"));
/*  959 */                     out.write("</div>\n        </div>\n        <div class=\"error\" id=\"char-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/*  960 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.specialcharerr"));
/*  961 */                     out.write("</div>");
/*  962 */                     out.write("\n        </div> \n          <p>\n            <label for=\"businesName\" class=\"form-txt\">");
/*  963 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvname"));
/*  964 */                     out.write("</label>\n            <input type=\"text\" value=\"\" name=\"businesName\" id=\"businesName\"/>\n          </p>         \n\t    <form name=\"FILE_UPLOAD_FORM\" action=\"/FileUpload.do?method=saveBGMap\" method=\"post\" enctype=\"multipart/form-data\">\n        <p>\n              <label>");
/*  965 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbg"));
/*  966 */                     out.write("</label>\n              <span id=\"browse-sec\"> <span class=\"file-browse\">\n\t\t\t      <input type=\"file\" class=\"file\" name=\"bgBrowseFile\" id=\"bgBrowseFile\" align=\"absmiddle\" size=\"15\"/>\n\t\t\t      <iframe id=\"upload_target\" name=\"upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n              <select id=\"mapLocation\" name=\"mapLocation\"> \n                <option value=\"\">");
/*  967 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbgmap"));
/*  968 */                     out.write("</option>\n                ");
/*      */                     
/*  970 */                     ArrayList<String> imagesArray = MapViewUtil.getListOfBGImages();
/*  971 */                     int noOfImages = imagesArray.size();
/*  972 */                     for (int i = 0; i < noOfImages; i++)
/*      */                     {
/*  974 */                       String imageName = (String)imagesArray.get(i);
/*      */                       
/*  976 */                       out.write("\n                <option value='");
/*  977 */                       out.print(imageName);
/*  978 */                       out.write(39);
/*  979 */                       out.write(62);
/*  980 */                       out.print(imageName);
/*  981 */                       out.write("</option>\n                ");
/*      */                     }
/*      */                     
/*      */ 
/*  985 */                     out.write("\n\t\t</select>\n\t      </span> </span> </span> </p> </form>\n         \n        </div>       \n          \n<div style=\"clear:both;\"></div>\n\n        <div id=\"addDevicesBlock\">\n         <h4 class=\"add-device-hea\">");
/*  986 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectdevice"));
/*  987 */                     out.write("</h4>\n    \n    \t <div id=\"customerSite\">\n         \n     \t\t");
/*      */                     
/*  989 */                     if (isMSP)
/*      */                     {
/*      */ 
/*  992 */                       out.write("\t\n          <p>\n\t  <div class=\"error\" id=\"site-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/*  993 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsitepopup"));
/*  994 */                       out.write(" </div>\n          </div>\n            <label for=\"site\" class=\"form-txt\">");
/*  995 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsite"));
/*  996 */                       out.write("</label>\n            <select id=\"site\" name=\"site\">\n\t\t    ");
/*      */                       
/*  998 */                       String custName = request.getParameter("custName");
/*  999 */                       if (custName == null)
/*      */                       {
/* 1001 */                         Properties custProp = null;
/* 1002 */                         if (request.getSession().getAttribute("custProp") != null)
/*      */                         {
/* 1004 */                           custProp = (Properties)request.getSession().getAttribute("custProp");
/*      */                         }
/* 1006 */                         Enumeration custEnum = custProp.keys();
/* 1007 */                         while (custEnum.hasMoreElements())
/*      */                         {
/* 1009 */                           custName = custEnum.nextElement().toString();
/*      */                         }
/*      */                       }
/* 1012 */                       String username = EnterpriseUtil.getLoggedInUserName(request);
/* 1013 */                       ArrayList<String> sitesArray = MapViewUtil.getSitesListForCustomer(custName, username);
/* 1014 */                       int noOfSites = sitesArray.size();
/*      */                       
/* 1016 */                       out.write("\n\t\t   <option value='All Sites'>");
/* 1017 */                       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/* 1018 */                       out.write("</option> ");
/* 1019 */                       out.write("\n\t\t   ");
/*      */                       
/* 1021 */                       for (int i = 0; i < noOfSites; i++)
/*      */                       {
/* 1023 */                         String siteName = (String)sitesArray.get(i);
/*      */                         
/* 1025 */                         out.write("\n                <option value='");
/* 1026 */                         out.print(siteName);
/* 1027 */                         out.write(39);
/* 1028 */                         out.write(62);
/* 1029 */                         out.print(siteName);
/* 1030 */                         out.write("</option>\n         ");
/*      */                       }
/*      */                       
/*      */ 
/* 1034 */                       out.write("\n </select> </p>  <script>loadCustAvailAndSelectBox(\"\",\"All Sites\",\"All Monitors\");</script>  ");
/* 1035 */                       out.write("     \n\t\t");
/*      */                     }
/*      */                     
/*      */ 
/* 1039 */                     out.write("\t\t\t\n         \n           <p>\n            <label for=\"devFilter\" class=\"form-txt\">");
/* 1040 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectcategory"));
/* 1041 */                     out.write("</label>\n            <select id=\"devFilter\" name=\"devFilter\">\n\t\t\t\t");
/* 1042 */                     if (_jspx_meth_c_005fforEach_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 1044 */                     out.write("\t\n\t        </select>         \n          </p>\n        </div>\n    \n    \t\n    \t  <div class=\"multi-select\">\t\n          <input type=\"hidden\" id=\"addDeviceOrigin\" name=\"addDeviceOrigin\" value=\"mapAndDeviceBlock\"/>\n            <span>\n            <label id=\"availDevicesText\">");
/* 1045 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 1046 */                     out.write("(0)</label>\n            <select id=\"availDevices\" name=\"availDevices\" multiple size=\"7\">\n\t");
/*      */                     
/*      */                     HashMap<String, String> devicesMap;
/* 1049 */                     if (!isMSP)
/*      */                     {
/* 1051 */                       devicesMap = MapViewUtil.getAllDevices1("ALL", null, request);
/* 1052 */                       for (String resId : devicesMap.keySet())
/*      */                       {
/* 1054 */                         String resourceId = resId;
/* 1055 */                         String displayName = (String)devicesMap.get(resourceId);
/*      */                         
/* 1057 */                         out.write("\n            <option value='");
/* 1058 */                         out.print(resourceId);
/* 1059 */                         out.write(39);
/* 1060 */                         out.write(62);
/* 1061 */                         out.print(displayName);
/* 1062 */                         out.write("</option>\n     ");
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 1067 */                     out.write("\n\n\t <script>\n\t\t\tvar availDevSize = $('#availDevices option').length; ");
/* 1068 */                     out.write(" \n\t\t\tvar selDevSize = $('#selectedDevices option').length; ");
/* 1069 */                     out.write("\n\t\t\t$('#availDevicesText').text('");
/* 1070 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 1071 */                     out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/* 1072 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 1073 */                     out.write("'+\"(\"+selDevSize+\")\");\n\t\n\t</script>\n        </select>\n\t<p class=\"selectall\">\n\t<input id=\"availSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/>  <label class=\"select-label\">");
/* 1074 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/* 1075 */                     out.write("</label></p>\n        </span>\n        \n        <span class=\"multiselect-but\">\n       \t\t<div><img src=\"/images/multiselect-arrow1.gif\" id=\"availDevButton\" /></div>\n\t\t<div><img src=\"/images/multiselect-arrow2.gif\" id=\"selDevButton\" /></div>\n        </span>\n        \n           <span>\n            <label id=\"selDevicesText\">");
/* 1076 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 1077 */                     out.write("(0)</label>\n           <select id=\"selectedDevices\" name=\"\" multiple size=\"7\">\n        </select>\n\t<p class=\"selectall\"><input id=\"selectedSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/> <label class=\"select-label\">");
/* 1078 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/* 1079 */                     out.write("</label></p>\n        </span>\n        \n        </div>\n        \n       \n        \n        </div>\n        \n        \n        \n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 1080 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 1081 */                     out.write("' id=\"mapAndDevicePopupDone\" name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 1082 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 1083 */                     out.write("' id=\"mapAndDevicePopupCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"deviceProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 1084 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceprops"));
/* 1085 */                     out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossDevPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n       \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/* 1086 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicename"));
/* 1087 */                     out.write("</label>\n            <input id=\"deviceName\" name=\"\" type=\"text\" />\n            <input name=\"\" type=\"checkbox\" id=\"showDevLabel\" value=\"\" checked=\"true\"/>\n          </p>\n          <p>\n            <label>");
/* 1088 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/* 1089 */                     out.write("</label>\n            <input name=\"devIcons\" type=\"radio\" value=\"opt1\" id=\"rad-opt1\" checked>\n            <label class=\"radio-label\">");
/* 1090 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/* 1091 */                     out.write("</label>\n            <input type=\"radio\" name=\"devIcons\" value=\"opt2\" id=\"rad-opt2\">\n            <label class=\"radio-label\">");
/* 1092 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/* 1093 */                     out.write("</label>\n          </p>\n          <div id=\"opt1\" class=\"radio-div\" style=\"display:block;\">\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/* 1094 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/* 1095 */                     out.write("</label>\n              <span> <img src=\"/images/square-nor.gif\" id=\"dev-icon-img1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"dev-icon-img2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"dev-icon-img3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"dev-icon-img4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"opt2\" class=\"radio-div\">\n           \n\t\t    <form name=\"IMAGE_UPLOAD_FORM\" action=\"/ImageUpload.do?method=saveDeviceImage\" method=\"post\" enctype=\"multipart/form-data\"> <p>\n                <label for=\"mapLocation\">");
/* 1096 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceicons.select"));
/* 1097 */                     out.write("</label>\n\t\t<span id=\"browse-sec\"> <span class=\"file-browse\">\n              <input type=\"file\" name=\"BrowseDevices\" id=\"BrowseDevices\" align=\"absmiddle\" size=\"15\" />\n\t      <iframe id=\"image_upload_target\" name=\"image_upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n                <select id=\"deviceList\" name=\"deviceList\">\n                  <option>");
/* 1098 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceimage.select"));
/* 1099 */                     out.write("</option>\n                  ");
/*      */                     
/* 1101 */                     ArrayList<String> devImage = MapViewUtil.getListOfDeviceImages();
/* 1102 */                     int count = devImage.size();
/* 1103 */                     for (int i = 0; i < count; i++)
/*      */                     {
/* 1105 */                       String imageName = (String)devImage.get(i);
/*      */                       
/* 1107 */                       out.write("\n                  <option value='");
/* 1108 */                       out.print(imageName);
/* 1109 */                       out.write(39);
/* 1110 */                       out.write(62);
/* 1111 */                       out.print(imageName);
/* 1112 */                       out.write("</option> ");
/* 1113 */                       out.write("\n                  ");
/*      */                     }
/*      */                     
/*      */ 
/* 1117 */                     out.write("\n                </select>\n\t\t</span></span></span></p>\n\t</form>\n           \n            <!--div>\n              <p>\n                <label class=\"icon-label\"> ");
/* 1118 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicebg.select"));
/* 1119 */                     out.write("</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" alt=\"RECT\" id=\"cus-icon-img1\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" alt=\"OVAL\" id=\"cus-icon-img2\"/><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" alt=\"DIAMOND\" id=\"cus-icon-img3\"/><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" alt=\"ISOSCELES_TRIANGLE\" id=\"cus-icon-img4\"/></span> </p>                \n            </div-->            \n          </div>\n          \n          \n                 <h4 class=\"add-device-hea\">");
/* 1120 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/* 1121 */                     out.write("</h4>\n                 <p>\n            <label>");
/* 1122 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/* 1123 */                     out.write("</label>\n            <select id=\"fontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option> ");
/* 1124 */                     out.write("\n\t      <option>Calibri</option> ");
/* 1125 */                     out.write("\n              <option>Comic San MS</option> ");
/* 1126 */                     out.write("\n              <option>Courier New</option> ");
/* 1127 */                     out.write("\n              <option>Georgia</option> ");
/* 1128 */                     out.write("\n              <option>Tahoma</option> ");
/* 1129 */                     out.write("\n              <option>Times New Roman</option> ");
/* 1130 */                     out.write("\n              <option>Trebuchet MS</option> ");
/* 1131 */                     out.write("\n              <option>Verdana</option> ");
/* 1132 */                     out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"fontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n          \n        </div>\n        <div class=\"popup-button\">\n          <input id=\"devicePropertiesDone\" name=\"\" value='");
/* 1133 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 1134 */                     out.write("' type=\"button\" class=\"m-done-but\"/>\n          <input id=\"devicePropertiesCancel\" name=\"\" value='");
/* 1135 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 1136 */                     out.write("' type=\"button\" class=\"m-cancel-but\" />\n        </div>\n      </div>\n    </div>\n    <div id=\"scProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 1137 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scprops"));
/* 1138 */                     out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossScPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n        <div class=\"error\" id=\"sc-name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/* 1139 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scnamecheck"));
/* 1140 */                     out.write("</div>\n          </div>\n          <p>\n            <label>");
/* 1141 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scname"));
/* 1142 */                     out.write("</label>\n            <input id=\"scName\" name=\"\" type=\"text\" />\n          </p>\n          <div class=\"error\" id=\"sc-submap-val\"><span class=\"val-arrow\"></span>\n\t\t    <div class=\"error-box\"> ");
/* 1143 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectmappopup"));
/* 1144 */                     out.write("</div>\n          </div>\n          <p>\n            <label>");
/* 1145 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsubmap"));
/* 1146 */                     out.write("</label>\n\t    <span id=\"listsubmaps\"> </span> \n\t    </p>\n\n          <p>\n            <label>");
/* 1147 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/* 1148 */                     out.write("</label>\n            <input id=\"sc-rad-opt1\" type=\"radio\" name=\"scIcons\" value=\"sc-opt1\" >\n            <label class=\"radio-label\">");
/* 1149 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/* 1150 */                     out.write("</label>\n            <input id=\"sc-rad-opt2\" name=\"scIcons\" type=\"radio\" value=\"sc-opt2\" checked>\n            <label class=\"radio-label\">");
/* 1151 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/* 1152 */                     out.write("</label>\n          </p>\n          <div id=\"sc-opt1\" class=\"radio-div\" >\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/* 1153 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/* 1154 */                     out.write("</label>\n              <span><img src=\"/images/square-nor.gif\" id=\"scicon1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"scicon2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"scicon3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"scicon4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"sc-opt2\" class=\"radio-div\" style=\"display:block;\">\n            <div>\n              <p>\n                <label for=\"mapLocation\">");
/* 1155 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscicons"));
/* 1156 */                     out.write("</label>\n                <select id=\"scDeviceList\">\n                  <option>");
/* 1157 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscimage"));
/* 1158 */                     out.write("</option>\n\t\t \t\t  ");
/*      */                     
/* 1160 */                     ArrayList<String> scImage = MapViewUtil.getListOfDeviceImages();
/* 1161 */                     int scCount = scImage.size();
/* 1162 */                     for (int i = 0; i < scCount; i++)
/*      */                     {
/* 1164 */                       String imageName = (String)scImage.get(i);
/*      */                       
/* 1166 */                       out.write("\n                  <option value='");
/* 1167 */                       out.print(imageName);
/* 1168 */                       out.write(39);
/* 1169 */                       out.write(62);
/* 1170 */                       out.print(imageName);
/* 1171 */                       out.write("</option> ");
/* 1172 */                       out.write("\n                  ");
/*      */                     }
/*      */                     
/*      */ 
/* 1176 */                     out.write("\n\t\t\t\t  <script>$('#scDeviceList').val(\"shortcut_icon.png\");</script>\n                </select>\n              </p>\n            </div>\n            <!--div>\n              <p>\n                <label class=\"icon-label\"> Select Device BG :</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" /></span> </p>\n            </div-->\n          </div>\n\t  <h4 class=\"add-device-hea\">");
/* 1177 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/* 1178 */                     out.write("</h4>\n                 <p>\n            <label>");
/* 1179 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/* 1180 */                     out.write("</label>\n            <select id=\"scFontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option>  ");
/* 1181 */                     out.write("\n\t      <option>Calibri</option> ");
/* 1182 */                     out.write("\n              <option>Comic San MS</option> ");
/* 1183 */                     out.write("\n              <option>Courier New</option> ");
/* 1184 */                     out.write("\n              <option>Georgia</option> ");
/* 1185 */                     out.write("\n              <option>Tahoma</option> ");
/* 1186 */                     out.write("\n              <option>Times New Roman</option> ");
/* 1187 */                     out.write("\n              <option>Trebuchet MS</option> ");
/* 1188 */                     out.write("\n              <option>Verdana</option> ");
/* 1189 */                     out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"scFontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n        </div>\n\t<p class=\"short-info\"><img src=\"/images/info.gif\" align=\"absmiddle\"/> ");
/* 1190 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addscalert"));
/* 1191 */                     out.write(" </p>");
/* 1192 */                     out.write("\n        <input type=\"hidden\" id=\"scOrigin\" name=\"scOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 1193 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 1194 */                     out.write("' id=\"scPropertiesDone\"  name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 1195 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 1196 */                     out.write("' id=\"scPropertiesCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"lineProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 1197 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkprops"));
/* 1198 */                     out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossLinePropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/* 1199 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkname"));
/* 1200 */                     out.write(" </label>\n            <input name=\"textfield\" type=\"text\" id=\"linkName\" disabled=\"disabled\"/>\n          </p>\n\n\t  <p>\n            <label>");
/* 1201 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkthickness"));
/* 1202 */                     out.write("</label>\n            <select id=\"linkThickness\">\n              <option>1</option>\n              <option>2</option>\n              <option>3</option>\n              <option>4</option>\n            </select>\n          </p>\n          <div>\n          <label>");
/* 1203 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.getstatus"));
/* 1204 */                     out.write("</label>\n          <input id=\"srcIF\" name=\"radInterface\" type=\"radio\" value=\"srcIF\" checked/>\n          <label id=\"srcName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n          <select id=\"srcInterfaces\">\n          </select>\n          </span>\n\t</div>\n\t  <div style=\"clear:both;\"></div>\n\t  <div class=\"error1\" id=\"link-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/* 1205 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectifpopup"));
/* 1206 */                     out.write(" </div>\n          </div>\t\n\t<div>\n          <label></label>\n          <input id=\"desIF\" name=\"radInterface\" type=\"radio\" value=\"desIF\" />\n          <label id=\"desName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n            <select id=\"desInterfaces\">\n            </select>\n          </span>\n        </div>\n\t</div>\n        <input type=\"hidden\" value=\"\" id=\"linkID\"/>\n        <input type=\"hidden\" id=\"linkOrigin\" name=\"linkOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 1207 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 1208 */                     out.write("' id=\"linePropertiesDone\"  name=\"button\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 1209 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 1210 */                     out.write("' id=\"linePropertiesCancel\" name=\"button\">\n        </div>\n      </div>\n    </div>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr>\n        <td width=\"43\" valign=\"top\" align=\"left\" class=\"nav-td2\" ><div id=\"map-toolbar\">\n            <ul>\n              <li id=\"toolbar-nav\">\n                <div class=\"map-tool-bg\"> <img src=\"/images/tools-arrow2.png\" class=\"tools-arrow\" title='");
/* 1211 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.maximize"));
/* 1212 */                     out.write("'/><img src=\"/images/tools-arrow1.png\" class=\"tools-arrow\" style=\"display:none\" title='");
/* 1213 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.minimize"));
/* 1214 */                     out.write("'/>\n                  <div class=\"tool-txt-show\">");
/* 1215 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.tools"));
/* 1216 */                     out.write("</div>\n                </div>\n              </li>\n              <li id=\"addDeviceLink\" title='");
/* 1217 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/* 1218 */                     out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-device.png\" />\n                  <div class=\"tool-txt-show\">");
/* 1219 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/* 1220 */                     out.write("</div>\n                </div>\n              </li>\n              <li id=\"link\" title='");
/* 1221 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/* 1222 */                     out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-link.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 1223 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/* 1224 */                     out.write("</div>\n                </div>\n              </li>\n              <li id=\"shortcut\" title='");
/* 1225 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/* 1226 */                     out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-shortcut.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 1227 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/* 1228 */                     out.write("</div>\n                </div>\n              </li>\n               <li id=\"delete\" onClick='del()'; title='");
/* 1229 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/* 1230 */                     out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-delete.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 1231 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/* 1232 */                     out.write("</div>\n                </div>\n              </li>\n              <li id=\"propertiesLink\" title='");
/* 1233 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/* 1234 */                     out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-properties.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 1235 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/* 1236 */                     out.write("</div>\n                </div>\n              </li>\n              <li id=\"help\" onClick='launchMapHelp()'>\n                <div class=\"map-tool-bg\" title='");
/* 1237 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/* 1238 */                     out.write("'> <img src=\"/images/m-help.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 1239 */                     out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/* 1240 */                     out.write("</div>\n                </div>\n              </li>\n            </ul>\n          </div></td>\n        <td bgcolor=\"#FFFFFF\" width=\"auto\"><div style=\"position:relative; z-index:10;\">\n            <div id=\"parentcontainer\">\n              <div id=\"shapecontainer\">\n                <div id=\"inner_shape\" class=\"inner_shape\" style=\"z-index:2;height:600px;\"> </div>\n              </div>\n            </div>\n          </div></td>\n      </tr>\n    </table>\n  </div>\n</div>\n</body>\n</html>\n");
/* 1241 */                     out.write(10);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ex)
/*      */                 {
/* 1246 */                   ex.printStackTrace();
/*      */                 }
/*      */                 
/* 1249 */                 out.write(10);
/* 1250 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 1251 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 1254 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 1255 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 1258 */             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 1259 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */             }
/*      */             
/* 1262 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 1263 */             out.write(32);
/* 1264 */             out.write(10);
/* 1265 */             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 1267 */             out.write(32);
/* 1268 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 1269 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1273 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 1274 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */         }
/*      */         
/* 1277 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1278 */         out.write(10);
/*      */       }
/*      */       
/*      */ 
/* 1282 */       out.write(10);
/* 1283 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 1285 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1286 */         out = _jspx_out;
/* 1287 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1288 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1289 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1292 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1298 */     PageContext pageContext = _jspx_page_context;
/* 1299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1301 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1302 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1303 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1305 */     _jspx_th_c_005fout_005f0.setValue("${type}");
/* 1306 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1307 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1308 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1309 */       return true;
/*      */     }
/* 1311 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1317 */     PageContext pageContext = _jspx_page_context;
/* 1318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1320 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1321 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1322 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 1324 */     _jspx_th_c_005fforEach_005f0.setItems("${resourceTypes}");
/*      */     
/* 1326 */     _jspx_th_c_005fforEach_005f0.setVar("row");
/* 1327 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1329 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1330 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1332 */           out.write("\n\t\t\t\t\t");
/* 1333 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1334 */             return true;
/* 1335 */           out.write("\n\t\t\t\t");
/* 1336 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1337 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1341 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1342 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1345 */         int tmp187_186 = 0; int[] tmp187_184 = _jspx_push_body_count_c_005fforEach_005f0; int tmp189_188 = tmp187_184[tmp187_186];tmp187_184[tmp187_186] = (tmp189_188 - 1); if (tmp189_188 <= 0) break;
/* 1346 */         out = _jspx_page_context.popBody(); }
/* 1347 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1349 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1350 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1357 */     PageContext pageContext = _jspx_page_context;
/* 1358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1360 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1361 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1362 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1364 */     _jspx_th_c_005fforEach_005f1.setVar("type");
/*      */     
/* 1366 */     _jspx_th_c_005fforEach_005f1.setItems("${row}");
/* 1367 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1369 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1370 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1372 */           out.write("\n\t\t\t\t\t");
/* 1373 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1374 */             return true;
/* 1375 */           out.write("\n\t\t\t\t\t<option value=\"");
/* 1376 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1377 */             return true;
/* 1378 */           out.write(34);
/* 1379 */           out.write(62);
/* 1380 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1381 */             return true;
/* 1382 */           out.write("</option>\n\n\t\t\t\t\t");
/* 1383 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1384 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1388 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1389 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1392 */         int tmp280_279 = 0; int[] tmp280_277 = _jspx_push_body_count_c_005fforEach_005f1; int tmp282_281 = tmp280_277[tmp280_279];tmp280_277[tmp280_279] = (tmp282_281 - 1); if (tmp282_281 <= 0) break;
/* 1393 */         out = _jspx_page_context.popBody(); }
/* 1394 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1396 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1397 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1404 */     PageContext pageContext = _jspx_page_context;
/* 1405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1407 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1408 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1409 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1411 */     _jspx_th_c_005fout_005f1.setValue("${type}");
/* 1412 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1413 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1414 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1415 */       return true;
/*      */     }
/* 1417 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1423 */     PageContext pageContext = _jspx_page_context;
/* 1424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1426 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1427 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1428 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1430 */     _jspx_th_c_005fout_005f2.setValue("${type.key}");
/* 1431 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1432 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1433 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1434 */       return true;
/*      */     }
/* 1436 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1442 */     PageContext pageContext = _jspx_page_context;
/* 1443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1445 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1446 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1447 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1449 */     _jspx_th_c_005fout_005f3.setValue("${type.value}");
/* 1450 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1451 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1452 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1453 */       return true;
/*      */     }
/* 1455 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1461 */     PageContext pageContext = _jspx_page_context;
/* 1462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1464 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1465 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 1466 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1468 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 1470 */     _jspx_th_tiles_005fput_005f0.setValue("Map view");
/* 1471 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 1472 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 1473 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 1474 */       return true;
/*      */     }
/* 1476 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 1477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1482 */     PageContext pageContext = _jspx_page_context;
/* 1483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1485 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1486 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 1487 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1489 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 1491 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 1492 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 1493 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 1494 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 1495 */       return true;
/*      */     }
/* 1497 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 1498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1503 */     PageContext pageContext = _jspx_page_context;
/* 1504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1506 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1507 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 1508 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1510 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 1512 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/MapsLeftPage.jsp?method=showMonitorGroupView&group=All&selectedNetwork=null");
/* 1513 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 1514 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 1515 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 1516 */       return true;
/*      */     }
/* 1518 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 1519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1524 */     PageContext pageContext = _jspx_page_context;
/* 1525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1527 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1528 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1529 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1531 */     _jspx_th_c_005fout_005f4.setValue("${type}");
/* 1532 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1533 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1534 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1535 */       return true;
/*      */     }
/* 1537 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1543 */     PageContext pageContext = _jspx_page_context;
/* 1544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1546 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1547 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 1548 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1550 */     _jspx_th_c_005fforEach_005f2.setItems("${resourceTypes}");
/*      */     
/* 1552 */     _jspx_th_c_005fforEach_005f2.setVar("row");
/* 1553 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 1555 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 1556 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 1558 */           out.write("\n\t\t\t\t\t");
/* 1559 */           if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1560 */             return true;
/* 1561 */           out.write("\n\t\t\t\t");
/* 1562 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 1563 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1567 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 1568 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1571 */         int tmp193_192 = 0; int[] tmp193_190 = _jspx_push_body_count_c_005fforEach_005f2; int tmp195_194 = tmp193_190[tmp193_192];tmp193_190[tmp193_192] = (tmp195_194 - 1); if (tmp195_194 <= 0) break;
/* 1572 */         out = _jspx_page_context.popBody(); }
/* 1573 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 1575 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 1576 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 1578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1583 */     PageContext pageContext = _jspx_page_context;
/* 1584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1586 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1587 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 1588 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1590 */     _jspx_th_c_005fforEach_005f3.setVar("type");
/*      */     
/* 1592 */     _jspx_th_c_005fforEach_005f3.setItems("${row}");
/* 1593 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 1595 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 1596 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 1598 */           out.write("\n\t\t\t\t\t");
/* 1599 */           boolean bool; if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 1600 */             return true;
/* 1601 */           out.write("\n\t\t\t\t\t<option value=\"");
/* 1602 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 1603 */             return true;
/* 1604 */           out.write(34);
/* 1605 */           out.write(62);
/* 1606 */           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 1607 */             return true;
/* 1608 */           out.write("</option>\n\n\t\t\t\t\t");
/* 1609 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 1610 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1614 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 1615 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1618 */         int tmp280_279 = 0; int[] tmp280_277 = _jspx_push_body_count_c_005fforEach_005f3; int tmp282_281 = tmp280_277[tmp280_279];tmp280_277[tmp280_279] = (tmp282_281 - 1); if (tmp282_281 <= 0) break;
/* 1619 */         out = _jspx_page_context.popBody(); }
/* 1620 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 1622 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 1623 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 1625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 1630 */     PageContext pageContext = _jspx_page_context;
/* 1631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1633 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1634 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1635 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 1637 */     _jspx_th_c_005fout_005f5.setValue("${type}");
/* 1638 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1639 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1640 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1641 */       return true;
/*      */     }
/* 1643 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 1649 */     PageContext pageContext = _jspx_page_context;
/* 1650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1652 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1653 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1654 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 1656 */     _jspx_th_c_005fout_005f6.setValue("${type.key}");
/* 1657 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1658 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1659 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1660 */       return true;
/*      */     }
/* 1662 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 1668 */     PageContext pageContext = _jspx_page_context;
/* 1669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1671 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1672 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1673 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 1675 */     _jspx_th_c_005fout_005f7.setValue("${type.value}");
/* 1676 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1677 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1678 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1679 */       return true;
/*      */     }
/* 1681 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1682 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1687 */     PageContext pageContext = _jspx_page_context;
/* 1688 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1690 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1691 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 1692 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1694 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 1696 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 1697 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 1698 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 1699 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 1700 */       return true;
/*      */     }
/* 1702 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 1703 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MapViewInclude_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */