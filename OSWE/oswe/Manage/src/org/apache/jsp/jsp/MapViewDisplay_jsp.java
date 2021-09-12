/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public final class MapViewDisplay_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  30 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  34 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  40 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n\n\n\n");
/*  68 */       out.write("\n<head>\n");
/*  69 */       out.write("\n<title>");
/*  70 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  71 */       out.write("</title>\n\n<style type=\"text/css\"></style>\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n\n\n\n</head>\n<body>\n\n<div id=\"delConfirmBox\" class=\"confirm-box\">\n<h3>");
/*  72 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.title"));
/*  73 */       out.write("</h3>\n<div class=\"confirm-cont\">\n<p>");
/*  74 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirm.text"));
/*  75 */       out.write("</p>\n<p><div class=\"confirm-input\"><input id=\"toDeleteBSG\" name=\"\" type=\"checkbox\" value=\"\" class=\"confirm-checkbox\" checked=\"checked\"/></div><div id='bsgText'>");
/*  76 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deleteconfirmation.bsg"));
/*  77 */       out.write("</div></p>\n<p style=\"text-align:center;\"><input name=\"\" id=\"deleteOKMap\" type=\"button\" value=\"OK\" class=\"confirm-but\"/> <input name=\"\" id=\"deleteCancelMap\" type=\"button\" value=\"Cancel\" class=\"confirm-but\"/></p>\n</div>\n</div>\n<div id=\"confirm-overlay\"></div>\n\n\n\n\t<div id=\"tooltip\" style=\"width:300px;display:none;position:absolute;\"> <span id=\"pointedarrow\" class=\"green-arrow\">&nbsp;</span>\n      <div id=\"colorofbox\" class=\"green-box\">\n        <h1 id=\"DetailHeading\"></h1>\n        <div class=\"mapbodytext\"> </div>\n      </div>\n    </div>\n<div id=\"main\">\n<div id=\"zoomingsec\">\n        <div id=\"z-plus-minus\">\n          <div id=\"arrow-box\">\n            <div id=\"arrow-plus-icon\" class=\"arrow-plus-icon\"><a href=\"#\" title='");
/*  78 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomin"));
/*  79 */       out.write(39);
/*  80 */       out.write(62);
/*  81 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  82 */       out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:2px 0 0;\">\n            <div id=\"arrow-minus-icon\" class=\"arrow-minus-icon\"><a href=\"#\" title='");
/*  83 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.zoomout"));
/*  84 */       out.write(39);
/*  85 */       out.write(62);
/*  86 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  87 */       out.write("</a></div>\n          </div>\n        </div>\n        <div id=\"zoom-arrows\">\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-top\" class=\"arrow-icon-top\"><a href=\"#\" title='");
/*  88 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  89 */       out.write(39);
/*  90 */       out.write(62);
/*  91 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movetop"));
/*  92 */       out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\">\n            <div id=\"arrow-icon-left\" class=\"arrow-icon-left\"><a href=\"#\" title='");
/*  93 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  94 */       out.write(39);
/*  95 */       out.write(62);
/*  96 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveleft"));
/*  97 */       out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\" style=\"margin:0 1px;\">\n            <div id=\"arrow-icon-center\"><a href=\"#\" title='");
/*  98 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/*  99 */       out.write(39);
/* 100 */       out.write(62);
/* 101 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.reset"));
/* 102 */       out.write("</a></div>\n          </div>\n          <div id=\"arrow-box\">\n\t\t  <div id=\"arrow-icon-right\" class=\"arrow-icon-right\"><a href=\"#\" title='");
/* 103 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/* 104 */       out.write(39);
/* 105 */       out.write(62);
/* 106 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.moveright"));
/* 107 */       out.write("</a></div>\n          </div>\n          <div style=\"clear:both; margin-bottom:1px;\"></div>\n          <div id=\"arrow-box\" style=\"margin-left:21px;\">\n            <div id=\"arrow-icon-bottom\" class=\"arrow-icon-bottom\"><a href=\"#\" title='");
/* 108 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/* 109 */       out.write(39);
/* 110 */       out.write(32);
/* 111 */       out.write(62);
/* 112 */       out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.movebottom"));
/* 113 */       out.write("</a></div>\n          </div>\n        </div>\n</div>\n\n\n  <div class=\"map-border\">\n    <div id=\"mapName\" style=\"float:left\"></div>\n   ");
/*     */       
/* 115 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 116 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 117 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */       
/* 119 */       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 120 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 121 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */         for (;;) {
/* 123 */           out.write("<div style=\"float:right\" class=\"btnout1\" onmouseout=\"hideActionmenu()\" onclick=\"showActionmenu()\">\n\n");
/* 124 */           out.print(FormatUtil.getString("am.mypage.widgettypes.topologymapwidget.action.text"));
/* 125 */           out.write("<img width=\"7\" vspace=\"2\" height=\"4\" border=\"0\" src=\"/images/icon_black_arrow.gif\" valign=\"middle\">\n\n\t<div style=\"display:none;\" id=\"action_drop\" class=\"action-but-border1\" onmouseout=\"hideActionmenu()\" onmouseover=\"showActionmenu()\"><ul><li>  <a id=\"modify\" href=\"#\">");
/* 126 */           out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.edit"));
/* 127 */           out.write(" </a></li><li>\n <a id=\"del\" href=\"#\">");
/* 128 */           out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.delete"));
/* 129 */           out.write("</a></li></ul></div>\n\n    </div>");
/* 130 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 131 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 135 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 136 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */       }
/*     */       else {
/* 139 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 140 */         out.write("\n  </div>  \n   <div id=\"parentcontainer\">\n    ");
/* 141 */         out.write("\n    \n    <div id=\"shapecontainer\">\n    \n\n      <div>\n        <div id=\"inner_shape\" style=\"height:600px;-moz-user-select:none;pointer-events:none;\">\n          \n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n      \n</body>\n</html>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapviewshapeconfig.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script type='text/javascript' src='/template/appmanager.js'></script>\n<script>\n\n\tvar zoom = 100;\n\tvar zoomoutscale = 1;\n\tvar zoomcount = 0;\n\tvar horizontalMove = 0;\n\tvar verticalMove = 0;\n\n\t$(document).ready(function(){\n\t\tShape.init();\n\t\tShapeConfig.init();\n\t\tdraw();\n\t});\n\n\tfunction draw()\n\t{\n\n\t\t$('#del').click(function(ev) { //No I18N\n\t\t\t$('#confirm-overlay').show();//No I18N\n\t\t\tvar mapViewName = encodeURIComponent($('#mapName').text());//No I18N\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=isSubGroup\",    //No I18N\n\t\t\t\tdata : \"mapViewName=\"+mapViewName,   //No I18N\n");
/* 142 */         out.write("\t\t\t\tsuccess : function(msg) {\n\t\t\t\t\tmsg = msg.replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (msg == \"TRUE\")//No I18N\n\t\t\t\t\t{\n\t\t\t\t\t\t$('#toDeleteBSG').hide();\t//No I18N\t\t\n\t\t\t\t\t\t$('#bsgText').text('");
/* 143 */         out.print(FormatUtil.getString("am.webclient.jsp.mapviewdisplay.deletesubgroup"));
/* 144 */         out.write("'); // No I18N\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t});\n\t\t\t$('#delConfirmBox').show();//No I18N\n\t\t});\n\n\t\t$('#deleteOKMap').click(function(ev) { //No I18N\n\t\t\tvar isBSGToBeDeleted = $('#toDeleteBSG').is(\":checked\");//No I18N\n\t\t\tvar mapViewName1 = $('#mapName').text();//No I18N\n\t\t\tvar widgetid = ");
/* 145 */         out.print(request.getParameter("widgetid"));
/* 146 */         out.write(";//No I18N\n\t\t\tvar isPopUp = ");
/* 147 */         out.print(request.getParameter("isPopUp"));
/* 148 */         out.write(";//No I18N\n\t\t\tvar pageid= ");
/* 149 */         out.print(request.getParameter("pageid"));
/* 150 */         out.write(";\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t$.ajax({\n\t\t\t\ttype : \"post\",         //No I18N\n\t\t\t\turl : \"/mapViewAction.do?method=deleteMapView\",    //No I18N\n\t\t\t\tdata : {\"mapViewName\" : mapViewName1,\"bgToBeDeleted\" : isBSGToBeDeleted,\"widgetid\" : widgetid},   //No I18N\n\t\t\t\tsuccess : function(msg) {\n\t\t\t\t}\n\t\t\t});\t\t\t\n\t\t\twindow.location.href='/MyPage.do?method=getWidget&pageid='+pageid+'&isPopUp=true&widgetid='+widgetid+'&fromDeleteMap=true';//No I18N\t\t\t\n\t\t\tparent.location.href=parent.location.href;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\twindow.location.href = \"/MyPage.do?method=viewDashBoard\";//No I18N\t\t\t\t\n\t\t\t}\n\t\t});\n\t\t$('#deleteCancelMap').click(function(ev) { //No I18N\n\t\t\t$('#delConfirmBox').hide();//No I18N\n\t\t\t$('#confirm-overlay').hide();//No I18N\n\t\t});\n\n\t\t$('#arrow-plus-icon').click(function()//No I18N\n\t\t{\n\t\t\tif($('#arrow-plus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\tzoomcount = zoomcount + 1;\n\t\t\tvar shapecontainer = ShapeEditor.config.container; //ignorei18n_start\n");
/* 151 */         out.write("\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/* 152 */         out.write("\n\t\t\t//shapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/* 153 */         out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/* 154 */         out.write("\n\t\t\t\n\t\t\tif (zoomcount == 4){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-plus-icon').addClass(\"arrow-box-disable\");");
/* 155 */         out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -3){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/* 156 */         out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-minus-icon').click(function() ");
/* 157 */         out.write("\n\t\t{\n\t\t\tif($('#arrow-minus-icon').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tzoom = zoom - 20;\n\t\t\tzoomoutscale = zoomoutscale - 0.2;\n\t\t\tzoomcount = zoomcount - 1;\n\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tvar scale = 'scale('+zoomoutscale+')'; ");
/* 158 */         out.write("\n\t\t\tshapecontainer[0].style['mozTransform'] = scale;\n\t\t\tshapecontainer[0].style['webkitTransform'] = scale;\n\t\t\tshapecontainer[0].style['msTransform'] = scale;\n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\"); ");
/* 159 */         out.write("\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\"); ");
/* 160 */         out.write("\n\t\t\t\n\t\t\tif (zoomcount == 3){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/* 161 */         out.write("\n\t\t\t}\n\n\t\t\tif (zoomcount == -4){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-minus-icon').addClass(\"arrow-box-disable\");");
/* 162 */         out.write("\n\t\t\t}\n\t\t\t\n\t  \t});\n\n\t\t$('#arrow-icon-center').click(function(){ ");
/* 163 */         out.write("\n\t\t\tvar shapecontainer = ShapeEditor.config.container;\n\t\t\tshapecontainer[0].style['webkitTransform'] = 'scale(1.0)';\n\t\t\tshapecontainer[0].style['msTransform'] = 'scale(1.0)';\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(1.0)\")\n\t\t\t$('#shapecontainer').css(\"zoom\",\"100%\");\n\t\t\t$('#shapecontainer').css(\"margin\",\"0px\");\n\t\t\tzoom = 100;\n\t\t\tzoomoutscale = 1;\n\t\t\tzoomcount = 0;\n\t\t\thorizontalMove = 0;\n\t\t\tverticalMove = 0;\n\t\t\t$('#arrow-minus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-minus-icon\");");
/* 164 */         out.write("\n\t\t\t$('#arrow-plus-icon').removeClass(\"arrow-box-disable\").addClass(\"arrow-plus-icon\");");
/* 165 */         out.write("\n\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/* 166 */         out.write("\n\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/* 167 */         out.write("\n\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/* 168 */         out.write("\n\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/* 169 */         out.write("\n\t  \t});\n\t\n\t\t$('#arrow-icon-left').click(function(){\n\t\t\tif($('#arrow-icon-left').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"+=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove + 1;\n\t\t\tif (horizontalMove == 10){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-left').addClass(\"arrow-box-disable\");");
/* 170 */         out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -9){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-right').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-right\");");
/* 171 */         out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-right').click(function(){\n\t\t\tif($('#arrow-icon-right').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginLeft' : \"-=50px\"\n\t\t\t});\n\t\t\thorizontalMove = horizontalMove - 1;\n\n\t\t\tif (horizontalMove == 9){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-left').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-left\");");
/* 172 */         out.write("\n\t\t\t}\n\n\t\t\tif (horizontalMove == -10){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-right').addClass(\"arrow-box-disable\");");
/* 173 */         out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-bottom').click(function(){\n\t\t\tif($('#arrow-icon-bottom').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"-=50px\"\n\t\t\t});\n\t\t\tverticalMove = verticalMove - 1;\n\n\t\t\tif (verticalMove == 4){\n\t\t\t\t//Enable the plus \n\t\t\t\t$('#arrow-icon-top').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-top\");");
/* 174 */         out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -5){\n\t\t\t\t//Disable the minus \n\t\t\t\t$('#arrow-icon-bottom').addClass(\"arrow-box-disable\");");
/* 175 */         out.write("\n\t\t\t}\n\t  \t});\n\n\t\t$('#arrow-icon-top').click(function(){\n\t\t\tif($('#arrow-icon-top').hasClass('arrow-box-disable'))\n\t\t\t{\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t$('#shapecontainer').animate({\n\t\t\t\t'marginTop' : \"+=50px\"\n\t\t\t});\n\n\t\t\tverticalMove = verticalMove + 1;\n\t\t\tif (verticalMove == 5){\n\t\t\t\t//Disable the plus\n\t\t\t\t$('#arrow-icon-top').addClass(\"arrow-box-disable\");");
/* 176 */         out.write("\n\t\t\t}\n\n\t\t\tif (verticalMove == -4){\n\t\t\t\t//Enable the minus\n\t\t\t\t$('#arrow-icon-bottom').removeClass(\"arrow-box-disable\").addClass(\"arrow-icon-bottom\");");
/* 177 */         out.write("\n\t\t\t}\n\t  \t});\n\n\t\tvar drawContainer = ShapeEditor.config.drawcontainer;\n\t\t$(drawContainer).click(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.click( data );\n\t\t});\n\n\t\tvar toolTip = $('#tooltip');\n\n\t\t$(drawContainer).mousemove(function(event){\n\t\t\tvar target = ShapeEventHandler.getTarget( event );\n\t\t\tvar box = $(target).parents(\".shape_selection\");\n\t\t\tvar data = undefined;\n\t\t\tif( box.length ){\n\t\t\t\tvar boxid = box.attr('box_id');\n\t\t\t\tif( boxid.indexOf(\"_\") != -1 ){\n\t\t\t\t\tboxid = boxid.split(\"_\")[1];\n\t\t\t\t}\n\t\t\t\tdata = ShapeEditor.config.get.shape(boxid);\n\t\t\t}\n\t\t\tif( !data ){\n\t\t\t\ttoolTip.hide();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tShapeEditor.config.eventTrigger.mousemove( data, event, toolTip );\n        \t});\n");
/* 178 */         out.write("\n\t\t//mouseout for tooltip\n\t\t$(\".shapegroup\").live(\"mouseout\", function(ev){//No I18N\n\t\t\t$('#tooltip').hide(); //No I18N\n\t\t});\n\n\t\t$('#modify').click(function( ev ){\n\t\t\tvar link = $(this);\n\t\t\tvar mapViewName = $('#mapName').text();//No I18N\n\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\t\t\t\n\t\t\tvar isPopUp = ");
/* 179 */         out.print(request.getParameter("isPopUp"));
/* 180 */         out.write(";//No I18N\n\t\t\tvar widgetid = ");
/* 181 */         out.print(request.getParameter("widgetid"));
/* 182 */         out.write(";//No I18N\n\t\t\tvar isSubMap = ");
/* 183 */         out.print(request.getParameter("isSubMap"));
/* 184 */         out.write(";//No I18N\t\t\t\n\t\t\tif(isPopUp !=null){\n\t\t\t\tif(isPopUp){\n\t\t\t\tfnOpenNewWindowWithHeightWidth(\"/mapViewAction.do?method=showMapViewEditor&mapViewName=\"+encodedMapViewName+\"&admin=true&type=modify&isPopUp=true&PRINTER_FRIENDLY=true&widgetid=\"+widgetid+\"&isSubMap=\"+isSubMap,'1000','660');//No I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\telse{\n\t\t\t\tlink.attr('href', '/mapViewAction.do?method=showMapViewEditor&mapViewName='+encodedMapViewName+'&admin=true&type=modify'); // No I18N\n\t\t\t}\t\t\n\t\t});\t\n\n\t\tvar jsonString = ");
/* 185 */         out.print(((JSONObject)request.getAttribute("jsonObj")).toString());
/* 186 */         out.write(";\n\t\tvar jsonText = JSON.stringify(jsonString);\n\t\tvar jsonObj = eval('(' + jsonText + ')');\n\t\t//alert(\"The jsonString \"+jsonText);\n\n\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t$('#mapName').html(mapViewName);\n\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\tvar myObject = JSON.parse(shape);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n\t                        myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n         \t                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n");
/* 187 */         out.write("\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\t\n\t\t\telse if(status == \"5\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t}\n\t\t\telse if (status == \"unmanaged\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"148\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"148\";\n\t\t\t\tmyObject.props.stroke.width=\"1\";\n\t\t\t} \n\t\t\t\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\tvar showLabel = myObject.it360Props.showLabel; //No I18N\n");
/* 188 */         out.write("\t\t\tif (showLabel == \"TRUE\") //No I18N\n\t\t\t{\n\t\t\t\t$.ShapeData.text.show(myObject);//No I18N\t\n\t\t\t}else{\n\t\t\t\t$.ShapeData.text.hide(myObject);//No I18N\t\n\t\t\t}\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\n\t\t\tif (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t}\n\t\t\telse if(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n");
/* 189 */         out.write("                myObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t}\n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                myObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t}\n\n\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++)\n\t\t{\n\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\tvar status = myObject.it360Props.status;\n\t\t\t\n\t\t\tif(status == \"5\")\t\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"0\";\n\t\t                myObject.props.stroke.fill.solid.color.rgb[1]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if(status == \"4\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"224\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"108\";\n");
/* 190 */         out.write("                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"4\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\t\t\telse if (status == \"1\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"255\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"0\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"0\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t} \n\t\t\telse if (status == \"nohealth\")\n\t\t\t{\n\t\t\t\tmyObject.props.stroke.fill.solid.color.rgb[0]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[1]=\"104\";\n                       \t\tmyObject.props.stroke.fill.solid.color.rgb[2]=\"104\";\n\t\t\t\tmyObject.props.stroke.width=\"2\";\n\t\t\t}\n\n\t\t\tShape.create.draw(myObject,$('#inner_shape')); //ignorei18n_end\n\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\t\n\t\t}\n\t}\n\n</script>\n");
/*     */       }
/* 192 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 193 */         out = _jspx_out;
/* 194 */         if ((out != null) && (out.getBufferSize() != 0))
/* 195 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 196 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 199 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MapViewDisplay_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */