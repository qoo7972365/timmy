/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public final class MapViewEditor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  25 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  31 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  32 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  49 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  53 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  61 */     HttpSession session = null;
/*     */     
/*     */ 
/*  64 */     JspWriter out = null;
/*  65 */     Object page = this;
/*  66 */     JspWriter _jspx_out = null;
/*  67 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  71 */       response.setContentType("text/html");
/*  72 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  74 */       _jspx_page_context = pageContext;
/*  75 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  76 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("\n<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\">\n\n\n\n\n\n\n\n\n\n\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n\n<title>");
/*  82 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.it360mv"));
/*  83 */       out.write("</title>\n\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/mapview.css\" />\n<!--[if lt IE 9\t]>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/mapviewiestyle.css\" />\n<![endif]-->\n<link type=\"text/css\" rel=\"stylesheet\" href=\"/images/shape.css\" />\n\n");
/*     */       
/*  85 */       boolean isMSP = com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition();
/*  86 */       String widgetid = request.getParameter("widgetid");
/*     */       
/*  88 */       out.write(10);
/*  89 */       out.write(10);
/*  90 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  91 */       out.write("\n<script type=\"text/javascript\" src='/template/zshapes.js'></script>\n<script type=\"text/javascript\" src='/template/zshapes_editor.js'></script>\n<script type='text/javascript' src='/template/shapedata.js'></script>\n<script type='text/javascript' src='/template/mapvieweditor.js'></script>\n<script type='text/javascript' src='/template/mapview.js'></script>\n<script>\n\n\tfunction saveBGImage()\n\t{\n\t\tdocument.MapViewForm.submit();\n\t\treturn;\n\t}\n\n\tvar isContainerModified = false;\n\n\t$(document).ready(function()\n\t{\n\t\tinit();\n\t\t$.fn.setUpMap($('#shapecontainer')); //ignorei18n_start\n\t\tupdateSelectedBgImage();\n\t\thandleForEdit();\n\t});\n\n\tfunction handleForEdit()\n\t{\n\t\tvar type = \"");
/*  92 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("\";\n\t\tif (type == \"modify\")\n\t\t{\n\t\t\t");
/*     */       
/*  96 */       String jObjString = null;
/*  97 */       JSONObject jObj = (JSONObject)request.getAttribute("jsonObj");
/*  98 */       if (jObj != null) {
/*  99 */         jObjString = jObj.toString();
/*     */       }
/* 101 */       out.write(";\n\t\t\t\n\t\t\tvar jsonString = ");
/* 102 */       out.print(jObjString);
/* 103 */       out.write(";\n\t\t\tvar jsonText = JSON.stringify(jsonString);\n\t\t\tvar jsonObj = eval('(' + jsonText + ')');\n\n\t\t\tvar mapViewName = jsonObj.MAPVIEWNAME;\n\t\t\tvar imgPath = jsonObj.BACKGROUNDIMAGE;\n\n\t\t\t$('#map-heading h2').html('");
/* 104 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewedit"));
/* 105 */       out.write("'+mapViewName);\n\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t$('#overlay').hide();\n\t\t\t$('#businesName').val(mapViewName);\n\t\t\t$('#mapLocation').val(imgPath);\n\n\t\t\t$('#mapName').html(mapViewName);\n\t\t\tvar bgImage = \"/images/maps/\"+imgPath;\n\t\t\t$('#shapecontainer').css('background','url('+bgImage+')');\n\t\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWDEVICE.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWDEVICE[x].DEVICEPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\tvar showLabel = myObject.it360Props.showLabel;\n\t\t\t\tif (showLabel == \"TRUE\")\n\t\t\t\t{\n\t\t\t\t\t$.ShapeData.text.show(myObject);\t\n\t\t\t\t}else{\n\t\t\t\t\t$.ShapeData.text.hide(myObject);\t\n\t\t\t\t}\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t\t$.ShapeData.text.changeText(myObject, myObject.it360Props.name);\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWLINK.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWLINK[x].LINKPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n");
/* 106 */       out.write("\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t\tfor(x=0; x< jsonObj.MAPVIEWSHORTCUT.length; x++){\n\t\t\t\tvar shape = jsonObj.MAPVIEWSHORTCUT[x].SHORTCUTPROPS;\n\t\t\t\tvar myObject = JSON.parse(shape, null);\n\t\t\t\tShape.create.draw(myObject,$('#inner_shape'));\n\t\t\t\t$.ShapeData.shapes[myObject.nvOProps.nvDProps.id]=myObject;\n\t\t\t}\n\n\t\t}\n\t}\n\t\n\tfunction updateSelectedBgImage()\n\t{\n\t\t$('#bgBrowseFile').change(function(e){\n  \t\t$in=$(this);\n\t\tvar selectedImage = $in.val();\n\t\tvar selectElement = document.getElementById('mapLocation');\n\t\tvar newOption = document.createElement('option');\n\t\tvar selectedImage = selectedImage.substr(0, selectedImage.lastIndexOf('.'));\n\t\tnewOption.text = selectedImage;\n\t\tnewOption.value = selectedImage;\n\t\ttry {\n                    selectElement.add(newOption, null);\n                } catch(x) {\n                    selectElement.add(newOption);\n\t    \t}\n\t\tnewOption.selected = true;\n\t\t});\n\t}\n\t\n\tfunction init()\n\t{\n\t\tShape.init();\n\t\tShapeEvent.init();\n\t\t\t\n                $('#link').click(function( ev ){\n");
/* 107 */       out.write("                \t\n                \t//Returns if it has only one Device\n                \tvar devArray = new Array();\n        \t\t    var totalShapes = $.ShapeData.shapes;\n        \t\t    for (var key in totalShapes)\n        \t\t    {\n        \t\t\tvar obj = totalShapes[key];\n        \t\t\tif (obj.it360Props.category == \"DEVICE\")\n        \t\t\t{\t\n        \t\t\t\tvar deviceName = obj.it360Props.name;\n        \t\t\t\tdevArray.push(deviceName);\n        \t\t\t}\n        \t\t    }\n\n        \t\t    if (devArray.length == 1)\n        \t\t    {\n\t\t\t\t\t\t$(\".status-message\").html('");
/* 108 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkcreationcriteria"));
/* 109 */       out.write("');\n        \t\t\t\t$(\".status-message\").show();\n        \t\t\t\t$(\".status-message\").fadeOut(3500);\n        \t\t    \treturn;\n        \t\t    }\n                \t\n                    var json = {};\n                    json['shapeObjType'] = \"CONNECTOR\";\n                    json['type'] = \"LINE\";\n\t\t    var it360Props = {};\n\t\t    it360Props['category'] = \"CONNECTOR\";\n\t\t    json['it360Props']=it360Props;\n                    $.ShapeData.setShapeDetails(JSON.stringify(json));\n                });\n\n\t\tvar container = ShapeEditor.config.container;\n\t\tcontainer.bind(\"modified\", function(ev, data){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeModified( data );\n\t\t});\n\t\tcontainer.bind(\"shape_created\", function( ev, data ){\n\t\t\tisContainerModified = true;\n\t\t\t$.ShapeData.shapeCreated(data);\n\t\t});\n\n\t\t$('#closeMapLink').click(function(){ \n\t\t\tvar isPopUp = ");
/* 110 */       out.print(request.getParameter("isPopUp"));
/* 111 */       out.write(";\n\t\t\tvar widgetid = ");
/* 112 */       out.print(request.getParameter("widgetid"));
/* 113 */       out.write(";\t\t\t\n\t\t\tvar pageid= ");
/* 114 */       out.print(request.getParameter("pageid"));
/* 115 */       out.write(";\n\t\t\tvar mapName = '");
/* 116 */       out.print(request.getParameter("mapViewName"));
/* 117 */       out.write("';\n\t\t\tvar fromEditTopoWidget = ");
/* 118 */       out.print(request.getParameter("fromEditTopoWidget"));
/* 119 */       out.write(";\n\t\t\tvar isSubMap = ");
/* 120 */       out.print(request.getParameter("isSubMap"));
/* 121 */       out.write("; \t\t\t\t\n\t\t\t\tif(isPopUp !=null)\n\t\t\t\t{\n\t\t\t\t\tif(isPopUp && !isSubMap)\n\t\t\t\t\t{//invoked when editing map from widget itself.\t\t\t\t\t\n\t\t\t\t\t\tvar url = (window.location != window.parent.location) ? document.referrer: document.location;\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&oldtab=0&type=view&PRINTER_FRIENDLY=true&isPopUp=\"+isPopUp+\"&widgetid=\"+widgetid;\t\t\n\t\t\t\t\t\twindow.close();\t\t\n\t\t\t\t\t}\n\t\t\t\t\telse if(isSubMap)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"/mapViewAction.do?method=showMap&mapViewName=\"+mapName+\"&admin=true&type=view&PRINTER_FRIENDLY=true&isPopUp=true&widgetid=\"+widgetid+\"&isSubMap=true\";\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif(fromEditTopoWidget!=null){//from edit topo widget - this will be invoked when create new topology map is invoked from editwidget page. \n\t\t\t\t\tif(fromEditTopoWidget)\n\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\twindow.opener.location.href=\"MyPage.do?method=editWidget&pageid=\"+pageid+\"&widgetid=\"+widgetid;\t\t\t\t\t\n\t\t\t\t\t\twindow.close();\n\t\t\t\t\t}\n\t\t\t\t}\t\t\t\n");
/* 122 */       out.write("\t\t\t\tif (isContainerModified)\n\t\t\t\t{\n\t\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\t\tvar r=confirm('");
/* 123 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/* 124 */       out.write("');\n\t\t\t\t\tif (r==true)\n\t  \t\t\t\t{\n\t\t\t\t\t\tsaveMap();\t\t\t\t\n\t  \t\t\t\t}\n\t\t\t\t\telse\n\t  \t\t\t\t{\t\t\t\t\n\t\t\t\t\t\twindow.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\"; \n\t  \t\t\t\t}\n\t\t\t\t\tisContainerModified = false;\t\n\t\t\t\t}\n\t\t\t});\n\n\t\t//Action for radio buttons available in Properties sheet\n\t\t$(\".popup-body input[type='radio']\").click(function() \n\t\t{\n        \t\tvar test = $(this).val();\n\t\t\t$(\"div.radio-div\").hide();\n\t\t        $(\"#\"+test).show();\n    \t\t}); \n\n\t\t//toggle toolbar\n\t\t$(\"#toolbar-nav\").click(function() {\n\t\t$(\".tool-txt-show\").toggleClass(\"tool-txt-hide\");\n\t\t$(\".tools-arrow\").toggle()\n\t\t$(\".nav-td2\").toggleClass(\"nav-td1\");\n\t\t\t});\n\t\t\n\t\t\n\t\t//On Pressing ESC key hide the pop-up\n\t\t$(document).keypress(function(e) \n\t\t{ \n\t\t\tif (e.keyCode == 27) \n\t\t\t{\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"mapAndDeviceBlock\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\twindow.location.href=\"/MyPage.do?method=viewDashBoard\";\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"PropertiesLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#mapSettingsBlock').hide();\n");
/* 125 */       out.write("\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\t\t\t\tif ($('#addDeviceOrigin').val()==\"AddDeviceLink\")\n\t\t\t\t{\n\t\t\t\t\t$('#mapAndDeviceBlock').hide();\n\t\t\t\t\t$('#overlay').hide();\n\t\t\t\t}\n\n\t\t\t\t$('#addDeviceOrigin').val(\"\");\n\t\t\t\t$('#availDevices').empty();\n\t\t\t\t$('#selectedDevices').empty();\n\n\t\t\t\t$('#devicePropertiesCancel').click();\n\t\t\t\t$('#linePropertiesCancel').click();\n\t\t\t\t$('#scPropertiesCancel').click();\n\t\t\t\t\n\t\t\t\tShapeEditor.draw.clear();\n    \t\t\t}\n\t\t});\n\t\t\t\n\n\t\t$('#open').click(function( ev ){\n\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/* 126 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/* 127 */       out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//window.location.href=\"/showIT360Tile.do?TileName=Tile.AdminConf\";\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\n\t\t\t}\n\t\t\tshowmenu();\n\t\t\tvar myDate = new Date();\n\t\t\t$.get(\"/mapViewAction.do?method=getListOfMapViews&myDate=\"+myDate, function(data){\n\t\t\t\tvar str_array = data.split(',');\n\t\t\t\t$newList = $(\"<ul />\");\n\t\t\t\tvar currentMapName = $('#businesName').val();\n\t\t\t\t\n\t\t\t\tfor(var i = 0; i < str_array.length; i++)\n\t\t\t\t{\n   \t\t\t\t\t// Trim the excess whitespace.\n\t\t\t\t\tvar mapViewName = str_array[i].replace(/^\\s*/, \"\").replace(/\\s*$/, \"\");\n\t\t\t\t\tif (mapViewName != \"\" && mapViewName != currentMapName)\n\t\t\t\t\t{\n\t\t\t\t\t\tvar encodedMapViewName = encodeURIComponent(mapViewName);\n\t\t\t\t\t\t$newList.append(\"<li><a href='/mapViewAction.do?method=showMapViewEditor&mapViewName=\" +encodedMapViewName+\"&admin=true&type=modify'>\" + mapViewName + \"</a></li>\");\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\t$('#dropdown').append($newList);\n\t\t\t});\n\t\t});\n\n\n\t\t$('#newMap').click(function( ev ){//ignorei18n_end\n");
/* 128 */       out.write("\t\t\tif (isContainerModified)\n\t\t\t{\n\t\t\t\t//Have to handle like YES, NO AND CANCEL instead of OK AND CANCEL\n\t\t\t\tvar r=confirm('");
/* 129 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemv"));
/* 130 */       out.write("');\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\tsaveMap();\n  \t\t\t\t}\n\t\t\t\tisContainerModified = false;\t\t\t\t\t\n\t\t\t}\n\t\t\twindow.location.href = \"/mapViewAction.do?method=showMapViewEditor&admin=true&type=new\";\n\t\t});\n\t\t\n\t\t$('#save').click(function( ev )//No I18N \n\t\t{\n\t\t\tsaveMap();\n\t\t\tisContainerModified = false;\n\t\t});\n\n\t\tfunction saveMap()\n\t\t{\n\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\tvar jsonText = JSON.stringify(shapes);\n\t\t\tvar myObject = eval('(' + jsonText + ')');\n\n\t\t\tvar deviceShapes = new Array();\n\t\t\tvar scShapes = new Array();\n\t\t\tvar connectorShapes = new Array();\n\t\t\tfor (var key in shapes)\n\t\t\t{\n\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\tvar devJson = {};\n\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\tif (jsonObj.it360Props.category == \"DEVICE\")\n\t\t\t\t{\n\t\t\t\t\tdeviceShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"SHORTCUT\")\n\t\t\t\t{\n\t\t\t\t\tscShapes.push(devJson);\n\t\t\t\t}\n\t\t\t\telse if (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t{\n\t\t\t\t\tjsonObj.it360Props.source=jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\tjsonObj.it360Props.destination=jsonObj.nvOProps.nvODProps.end.id;\n");
/* 131 */       out.write("\t\t\t\t\tconnectorShapes.push(devJson);\n\t\t\t\t}\n\t\t\t}\n\t\t\t\n\t\t\tvar mapView = {};\n\t\t\tvar fileName = document.getElementById('bgBrowseFile').value;\n\t\t\tif(fileName != \"\")\n\t\t\t{\n\t\t\t\tvar index = fileName.lastIndexOf(\"\\\\\");\n\t\t\t\tfileName = fileName.substring(index+1);\n\t\t\t}else{\n\t\t\t\tfileName = $('#mapLocation option:selected').val();\t//No I18N \n\t\t\t}\n\n\t\t\tmapView['bgImage'] = fileName;//No I18N \n\t\t\tmapView['mapViewName'] = $('#businesName').val();//No I18N \n\t\t\tmapView['widgetid'] = ");
/* 132 */       out.print(request.getParameter("widgetid"));
/* 133 */       out.write(";//No I18N\t\t\t\n\t\t\tmapView['fromWhere'] = '");
/* 134 */       out.print(request.getParameter("fromWhere"));
/* 135 */       out.write("';//No I18N\n\t\t\t\n\t\t\tvar finalObj = {};\n\t\t\tfinalObj['MAPVIEW'] = mapView;\n\t        finalObj['MAPVIEWDEVICE'] = deviceShapes;\n\t        finalObj['MAPVIEWLINK'] = connectorShapes;\n\t\t\tfinalObj['MAPVIEWSHORTCUT'] = scShapes;\n\t\t\tvar jsonStringToServer = encodeURIComponent(JSON.stringify(finalObj));\t\t\t\n\n\t\t\t$.ajax({\n\t\t            type:       \"post\",//No I18N \n            \t\turl:        \"/mapViewAction.do?method=saveMapView\",//No I18N \n\t\t            data:       \"data=\" + jsonStringToServer,//No I18N \n\t\t\t    success:    function(msg) \n\t\t\t    {\n\t\t\t\t\t$(\".status-message\").html(msg);//No I18N \n\t\t\t\t\t$(\".status-message\").show();//No I18N \n\t\t\t        $(\".status-message\").fadeOut(2500);\t//No I18N\t\t\t\n\t\t\t    }\n\t\t\t});\n\t\t}\n\t\t\n\t\t$('#availDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#availDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#selectedDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n");
/* 136 */       out.write("\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/* 137 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 138 */       out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/* 139 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 140 */       out.write("'+\"(\"+selDevSize+\")\");\n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#selDevButton').click(function()//No I18N \n\t\t{\n\t\t\t$('#selectedDevices option:selected').each( function() //No I18N \n\t\t\t{\n            \t\t\t$('#availDevices').append(\"<option value='\"+$(this).val()+\"'>\"+$(this).text()+\"</option>\");\n            \t\t\t$(this).remove();\n        \t\t});\n\t\t\tvar availDevSize = $('#availDevices option').length;//No I18N \n\t\t\tvar selDevSize = $('#selectedDevices option').length;//No I18N \n\t\t\t$('#availDevicesText').text('");
/* 141 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 142 */       out.write("'+\"(\"+availDevSize+\")\");//No I18N \n\t\t\t$('#selDevicesText').text('");
/* 143 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 144 */       out.write("'+\"(\"+selDevSize+\")\");//No I18N \n\t\t\t$('#availSelectAll').attr('checked', false);//No I18N \n\t\t\t$('#selectedSelectAll').attr('checked', false);//No I18N \n\t\t});\n\n\t\t$('#window').scroll(function(){\t//No I18N \n\t\t\t//alert(\"Inside mouse scroll\");\n\t\t\tzoom = zoom + 20;\n\t\t\tzoomoutscale = zoomoutscale+0.2;\n\t\t\t$('#shapecontainer').css(\"-moz-transform\",\"scale(\"+zoomoutscale+\")\");//No I18N \n\t\t\t$('#shapecontainer').css(\"zoom\",zoom+\"%\");//No I18N \n\t\t});\n\n\t\t$('#availSelectAll').click(function(){//No I18N \n\t\t\tif($('#availSelectAll').is(\":checked\"))\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#availDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#selectedSelectAll').click(function(){//No I18N \n\t\t\tif($('#selectedSelectAll').is(\":checked\"))//No I18N \n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','selected');//No I18N \n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#selectedDevices *').attr('selected','');//No I18N \n\t\t\t}\n\t  \t});\n\n\t\t$('#customer').change(function(){//No I18N \n\t\t\t$('#availDevices').empty();//No I18N \n");
/* 145 */       out.write("\t\t\t$('#selectedDevices').empty();//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val(); //No I18N \n\t\t\tloadSites(customerName);\n\t\t\t\n\t\t});\n\n\t\t$('#site').change(function(){//No I18N \n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\t$('#devFilter').val(\"ALL\");//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\t\t\t\n\t\t});\t\n\n\t\t$('#devFilter').change(function(){//No I18N \n\t\t\tvar category = $('#devFilter option:selected').val();//No I18N \n\t\t\tif(");
/* 146 */       out.print(isMSP);
/* 147 */       out.write("){\n\t\t\tvar customerName = $('#customer option:selected').val();//No I18N \n\t\t\tvar siteName = $('#site option:selected').val();//No I18N \n\t\t\tloadCustDeviceFilter(customerName,siteName,category);\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\tloadDeviceFilter(category);\n\t\t\t}\n\t\t});\n\t}\n\n\tfunction siteValidation()\n\t{\n\t\tif(");
/* 148 */       out.print(isMSP);
/* 149 */       out.write(")\n\t\t{\n\t\t\tif ($('#site option:selected').val() == undefined || $('#site option:selected').val() == \"\")\n\t\t\t{\n\t\t\t\t$('#site-val').show();\t//No I18N \n                                $('#site-val').delay(3000).fadeOut();//No I18N \t\t\t\t\t\t\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t}\t\n\n\tfunction launchMapHelp()\n\t{\n\t\twindow.open(\"/BSHELP/help/meitms/maps/maps.html\");\n\t} \n\n\tfunction del()\n\t{\n\t\tvar selectedShapes = ShapeEditor.select.shapes;\n\t\tfor(x=0; x< selectedShapes.length; x++)\n\t\t{\n\t\t\tvar shapeData = selectedShapes[x].data;\n\t\t\tvar id = selectedShapes[x].data.nvOProps.nvDProps.id;\n\t\t\tvar type = selectedShapes[x].data.type;\n\n\t\t\tif (shapeData.it360Props.category == \"SHORTCUT\")\n\t\t\t{\n\t\t\t\tvar r=confirm('");
/* 150 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deletescalert"));
/* 151 */       out.write(39);
/* 152 */       out.write(41);
/* 153 */       out.write(59);
/* 154 */       out.write("\n\t\t\t\tif (r==true)\n  \t\t\t\t{\n\t\t\t\t\t$.ShapeData.removeShape(id);\n\t\t\t\t\tdelete $.ShapeData.shapes[id];\n  \t\t\t\t}\n\t\t\t\telse\n  \t\t\t\t{\n\t\t\t\t\t//Do not do anything\n  \t\t\t\t}\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\n\t\t\t$.ShapeData.removeShape(id);\n\t\t\tdelete $.ShapeData.shapes[id];\n\n\t\t\tif(type == \"SHAPE\")\n\t\t\t{\n\t\t\t\tvar shapes = $.ShapeData.shapes;\n\t\t\t\tfor (var key in shapes)\n\t\t\t\t{\n\t\t\t\t\tvar jsonObj = shapes[key];\n\t\t\t\t\tvar devJson = {};\n\t\t\t\t\tdevJson[key]=jsonObj;\n\t\t\t\t\tif (jsonObj.it360Props.category == \"CONNECTOR\")\n\t\t\t\t\t{\n\t\t\t\t\t\tvar startid= jsonObj.nvOProps.nvODProps.start.id;\n\t\t\t\t\t\tvar endid = jsonObj.nvOProps.nvODProps.end.id;\n\t\t\t\t\t\tif(startid == id || endid == id)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar connecterid = jsonObj.nvOProps.nvDProps.id;\n\t\t\t\t\t\t\t$.ShapeData.removeShape(connecterid);\n\t\t\t\t\t\t\tdelete $.ShapeData.shapes[connecterid];\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\n\n\tfunction selectBrowsedImage()\n\t{\n\t\tdocument.getElementById('bgBrowseFile').click();\n\t}\t\t\n\n\t$(function() \n\t{\n        //$('#mapAndDeviceBlock').draggable();\n\t    //$('#lineProperties').draggable();\n\t\t//$('#scProperties').draggable();\n");
/* 155 */       out.write("\t\t//$('#deviceProperties').draggable(); \n    \t});//ignorei18n_end \n</script>\n</head>\n\n<body>\n<div id=\"am.webclient.jsp.mapvieweditor.availdevices\" style=\"display:none\">");
/* 156 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 157 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.selecteddevices\" style=\"display:none\">");
/* 158 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 159 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.adddevices\" style=\"display:none\">");
/* 160 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevices"));
/* 161 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.allsites\" style=\"display:none\">");
/* 162 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/* 163 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.alldevices\" style=\"display:none\">");
/* 164 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.alldevices"));
/* 165 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.select\" style=\"display:none\">");
/* 166 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.select"));
/* 167 */       out.write("</div>\n<div id=\"am.webclient.jsp.mapvieweditor.mapviewprops\" style=\"display:none\">");
/* 168 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapviewprops"));
/* 169 */       out.write("</div>\n<div id=\"am.webclient.js.mapview.interfacesfor\" style=\"display:none\">");
/* 170 */       out.print(FormatUtil.getString("am.webclient.js.mapview.interfacesfor"));
/* 171 */       out.write("</div>\n<div id=\"overlay\"></div>\n<div id=\"maincontainer\">\n  <div id=\"menu-section\">\n    <div id=\"dropdown\" onmouseover=\"showmenu()\" onmouseout=\"hidemenu()\" style=\"display:none;\"> </div>\n    <div class=\"menus\">\n      <ul>\n      \t");
/* 172 */       if (widgetid == null)
/*     */       {
/* 174 */         out.write("\n        <li><a id=\"newMap\" title='");
/* 175 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/* 176 */         out.write("'><span>");
/* 177 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.new"));
/* 178 */         out.write("</span></a></li>\n        <li><a id=\"open\" href=\"#\" title='");
/* 179 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/* 180 */         out.write("' onmouseout=\"hidemenu()\"><span>");
/* 181 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.open"));
/* 182 */         out.write("</span></a></li>\n        ");
/*     */       }
/* 184 */       out.write("\n        <li><a id=\"save\" href=\"#\" title='");
/* 185 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/* 186 */       out.write("'><span>");
/* 187 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.save"));
/* 188 */       out.write("</span></a></li>\n        <!--<li><a href=\"#\" title=\"Close\" id=\"closeMapLink\">Close</a></li>-->\n        <li><a id=\"closeMapLink\" href=\"#\" title='");
/* 189 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/* 190 */       out.write("'><span>");
/* 191 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.close"));
/* 192 */       out.write("</span></a></li>\n      </ul>\n    </div>\n    \n\t<div id=\"saveMessage\" class=\"status-message\"></div>\t\n    \n    <div id=\"map-heading\">\n      <h2>");
/* 193 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mapview"));
/* 194 */       out.write("</h2>\n    </div>\n  </div>\n  <div id=\"bodycontainer\">\n    <div id=\"mapAndDeviceBlock\" class=\"popup\">\n      <div class=\"popup-header\">\n<div><span class=\"newmap-hea\">");
/* 195 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.newmv"));
/* 196 */       out.write("</span>\n\t<span class=\"closeButton\" id=\"crossClose\"></span></div>       \n      </div>\n      <div class=\"popup-body\">\n        <div id=\"mapSettingsBlock\" class=\"popup-mainsection\">\n\t  <div class=\"error\" id=\"name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/* 197 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvnamepopup"));
/* 198 */       out.write(" </div>\n    \t</div>\n\t\t<div class=\"error\" id=\"name-length\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/* 199 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.namelength"));
/* 200 */       out.write("</div>");
/* 201 */       out.write("\n        </div>    \n        <div class=\"error\" id=\"name-check\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/* 202 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.nameexists"));
/* 203 */       out.write("</div>\n        </div>\n        <div class=\"error\" id=\"char-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/* 204 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.specialcharerr"));
/* 205 */       out.write("</div>");
/* 206 */       out.write("\n        </div> \n          <p>\n            <label for=\"businesName\" class=\"form-txt\">");
/* 207 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.mvname"));
/* 208 */       out.write("</label>\n            <input type=\"text\" value=\"\" name=\"businesName\" id=\"businesName\"/>\n          </p>         \n\t    <form name=\"FILE_UPLOAD_FORM\" action=\"/FileUpload.do?method=saveBGMap\" method=\"post\" enctype=\"multipart/form-data\">\n        <p>\n              <label>");
/* 209 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbg"));
/* 210 */       out.write("</label>\n              <span id=\"browse-sec\"> <span class=\"file-browse\">\n\t\t\t      <input type=\"file\" class=\"file\" name=\"bgBrowseFile\" id=\"bgBrowseFile\" align=\"absmiddle\" size=\"15\"/>\n\t\t\t      <iframe id=\"upload_target\" name=\"upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n              <select id=\"mapLocation\" name=\"mapLocation\"> \n                <option value=\"\">");
/* 211 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectbgmap"));
/* 212 */       out.write("</option>\n                ");
/*     */       
/* 214 */       ArrayList<String> imagesArray = MapViewUtil.getListOfBGImages();
/* 215 */       int noOfImages = imagesArray.size();
/* 216 */       for (int i = 0; i < noOfImages; i++)
/*     */       {
/* 218 */         String imageName = (String)imagesArray.get(i);
/*     */         
/* 220 */         out.write("\n                <option value='");
/* 221 */         out.print(imageName);
/* 222 */         out.write(39);
/* 223 */         out.write(62);
/* 224 */         out.print(imageName);
/* 225 */         out.write("</option>\n                ");
/*     */       }
/*     */       
/*     */ 
/* 229 */       out.write("\n\t\t</select>\n\t      </span> </span> </span> </p> </form>\n         \n        </div>       \n          \n<div style=\"clear:both;\"></div>\n\n        <div id=\"addDevicesBlock\">\n         <h4 class=\"add-device-hea\">");
/* 230 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectdevice"));
/* 231 */       out.write("</h4>\n    \n    \t <div id=\"customerSite\">\n         \n     \t\t");
/*     */       
/* 233 */       if (isMSP)
/*     */       {
/*     */ 
/* 236 */         out.write("\t\n          <p>\n\t  <div class=\"error\" id=\"site-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/* 237 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsitepopup"));
/* 238 */         out.write(" </div>\n          </div>\n            <label for=\"site\" class=\"form-txt\">");
/* 239 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsite"));
/* 240 */         out.write("</label>\n            <select id=\"site\" name=\"site\">\n\t\t    ");
/*     */         
/* 242 */         String custName = request.getParameter("custName");
/* 243 */         if (custName == null)
/*     */         {
/* 245 */           Properties custProp = null;
/* 246 */           if (request.getSession().getAttribute("custProp") != null)
/*     */           {
/* 248 */             custProp = (Properties)request.getSession().getAttribute("custProp");
/*     */           }
/* 250 */           java.util.Enumeration custEnum = custProp.keys();
/* 251 */           while (custEnum.hasMoreElements())
/*     */           {
/* 253 */             custName = custEnum.nextElement().toString();
/*     */           }
/*     */         }
/* 256 */         String username = com.adventnet.appmanager.util.EnterpriseUtil.getLoggedInUserName(request);
/* 257 */         ArrayList<String> sitesArray = MapViewUtil.getSitesListForCustomer(custName, username);
/* 258 */         int noOfSites = sitesArray.size();
/*     */         
/* 260 */         out.write("\n\t\t   <option value='All Sites'>");
/* 261 */         out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.allsites"));
/* 262 */         out.write("</option> ");
/* 263 */         out.write("\n\t\t   ");
/*     */         
/* 265 */         for (int i = 0; i < noOfSites; i++)
/*     */         {
/* 267 */           String siteName = (String)sitesArray.get(i);
/*     */           
/* 269 */           out.write("\n                <option value='");
/* 270 */           out.print(siteName);
/* 271 */           out.write(39);
/* 272 */           out.write(62);
/* 273 */           out.print(siteName);
/* 274 */           out.write("</option>\n         ");
/*     */         }
/*     */         
/*     */ 
/* 278 */         out.write("\n </select> </p>  <script>loadCustAvailAndSelectBox(\"\",\"All Sites\",\"All Monitors\");</script>  ");
/* 279 */         out.write("     \n\t\t");
/*     */       }
/*     */       
/*     */ 
/* 283 */       out.write("\t\t\t\n         \n           <p>\n            <label for=\"devFilter\" class=\"form-txt\">");
/* 284 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectcategory"));
/* 285 */       out.write("</label>\n            <select id=\"devFilter\" name=\"devFilter\">\n\t\t\t\t");
/* 286 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*     */         return;
/* 288 */       out.write("\t\n\t        </select>         \n          </p>\n        </div>\n    \n    \t\n    \t  <div class=\"multi-select\">\t\n          <input type=\"hidden\" id=\"addDeviceOrigin\" name=\"addDeviceOrigin\" value=\"mapAndDeviceBlock\"/>\n            <span>\n            <label id=\"availDevicesText\">");
/* 289 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 290 */       out.write("(0)</label>\n            <select id=\"availDevices\" name=\"availDevices\" multiple size=\"7\">\n\t");
/*     */       
/*     */       HashMap<String, String> devicesMap;
/* 293 */       if (!isMSP)
/*     */       {
/* 295 */         devicesMap = MapViewUtil.getAllDevices1("ALL", null, request);
/* 296 */         for (String resId : devicesMap.keySet())
/*     */         {
/* 298 */           String resourceId = resId;
/* 299 */           String displayName = (String)devicesMap.get(resourceId);
/*     */           
/* 301 */           out.write("\n            <option value='");
/* 302 */           out.print(resourceId);
/* 303 */           out.write(39);
/* 304 */           out.write(62);
/* 305 */           out.print(displayName);
/* 306 */           out.write("</option>\n     ");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 311 */       out.write("\n\n\t <script>\n\t\t\tvar availDevSize = $('#availDevices option').length; ");
/* 312 */       out.write(" \n\t\t\tvar selDevSize = $('#selectedDevices option').length; ");
/* 313 */       out.write("\n\t\t\t$('#availDevicesText').text('");
/* 314 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.availdevices"));
/* 315 */       out.write("'+\"(\"+availDevSize+\")\");\n\t\t\t$('#selDevicesText').text('");
/* 316 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 317 */       out.write("'+\"(\"+selDevSize+\")\");\n\t\n\t</script>\n        </select>\n\t<p class=\"selectall\">\n\t<input id=\"availSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/>  <label class=\"select-label\">");
/* 318 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/* 319 */       out.write("</label></p>\n        </span>\n        \n        <span class=\"multiselect-but\">\n       \t\t<div><img src=\"/images/multiselect-arrow1.gif\" id=\"availDevButton\" /></div>\n\t\t<div><img src=\"/images/multiselect-arrow2.gif\" id=\"selDevButton\" /></div>\n        </span>\n        \n           <span>\n            <label id=\"selDevicesText\">");
/* 320 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selecteddevices"));
/* 321 */       out.write("(0)</label>\n           <select id=\"selectedDevices\" name=\"\" multiple size=\"7\">\n        </select>\n\t<p class=\"selectall\"><input id=\"selectedSelectAll\" name=\"\" type=\"checkbox\" value=\"\"/> <label class=\"select-label\">");
/* 322 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectall"));
/* 323 */       out.write("</label></p>\n        </span>\n        \n        </div>\n        \n       \n        \n        </div>\n        \n        \n        \n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 324 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 325 */       out.write("' id=\"mapAndDevicePopupDone\" name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 326 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 327 */       out.write("' id=\"mapAndDevicePopupCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"deviceProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 328 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceprops"));
/* 329 */       out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossDevPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n       \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/* 330 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicename"));
/* 331 */       out.write("</label>\n            <input id=\"deviceName\" name=\"\" type=\"text\" />\n            <input name=\"\" type=\"checkbox\" id=\"showDevLabel\" value=\"\" checked=\"true\"/>\n          </p>\n          <p>\n            <label>");
/* 332 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/* 333 */       out.write("</label>\n            <input name=\"devIcons\" type=\"radio\" value=\"opt1\" id=\"rad-opt1\" checked>\n            <label class=\"radio-label\">");
/* 334 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/* 335 */       out.write("</label>\n            <input type=\"radio\" name=\"devIcons\" value=\"opt2\" id=\"rad-opt2\">\n            <label class=\"radio-label\">");
/* 336 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/* 337 */       out.write("</label>\n          </p>\n          <div id=\"opt1\" class=\"radio-div\" style=\"display:block;\">\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/* 338 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/* 339 */       out.write("</label>\n              <span> <img src=\"/images/square-nor.gif\" id=\"dev-icon-img1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"dev-icon-img2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"dev-icon-img3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"dev-icon-img4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"opt2\" class=\"radio-div\">\n           \n\t\t    <form name=\"IMAGE_UPLOAD_FORM\" action=\"/ImageUpload.do?method=saveDeviceImage\" method=\"post\" enctype=\"multipart/form-data\"> <p>\n                <label for=\"mapLocation\">");
/* 340 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceicons.select"));
/* 341 */       out.write("</label>\n\t\t<span id=\"browse-sec\"> <span class=\"file-browse\">\n              <input type=\"file\" name=\"BrowseDevices\" id=\"BrowseDevices\" align=\"absmiddle\" size=\"15\" />\n\t      <iframe id=\"image_upload_target\" name=\"image_upload_target\" src=\"\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n              <span class=\"fake-button\">\n                <select id=\"deviceList\" name=\"deviceList\">\n                  <option>");
/* 342 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deviceimage.select"));
/* 343 */       out.write("</option>\n                  ");
/*     */       
/* 345 */       ArrayList<String> devImage = MapViewUtil.getListOfDeviceImages();
/* 346 */       int count = devImage.size();
/* 347 */       for (int i = 0; i < count; i++)
/*     */       {
/* 349 */         String imageName = (String)devImage.get(i);
/*     */         
/* 351 */         out.write("\n                  <option value='");
/* 352 */         out.print(imageName);
/* 353 */         out.write(39);
/* 354 */         out.write(62);
/* 355 */         out.print(imageName);
/* 356 */         out.write("</option> ");
/* 357 */         out.write("\n                  ");
/*     */       }
/*     */       
/*     */ 
/* 361 */       out.write("\n                </select>\n\t\t</span></span></span></p>\n\t</form>\n           \n            <!--div>\n              <p>\n                <label class=\"icon-label\"> ");
/* 362 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.devicebg.select"));
/* 363 */       out.write("</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" alt=\"RECT\" id=\"cus-icon-img1\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" alt=\"OVAL\" id=\"cus-icon-img2\"/><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" alt=\"DIAMOND\" id=\"cus-icon-img3\"/><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" alt=\"ISOSCELES_TRIANGLE\" id=\"cus-icon-img4\"/></span> </p>                \n            </div-->            \n          </div>\n          \n          \n                 <h4 class=\"add-device-hea\">");
/* 364 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/* 365 */       out.write("</h4>\n                 <p>\n            <label>");
/* 366 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/* 367 */       out.write("</label>\n            <select id=\"fontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option> ");
/* 368 */       out.write("\n\t      <option>Calibri</option> ");
/* 369 */       out.write("\n              <option>Comic San MS</option> ");
/* 370 */       out.write("\n              <option>Courier New</option> ");
/* 371 */       out.write("\n              <option>Georgia</option> ");
/* 372 */       out.write("\n              <option>Tahoma</option> ");
/* 373 */       out.write("\n              <option>Times New Roman</option> ");
/* 374 */       out.write("\n              <option>Trebuchet MS</option> ");
/* 375 */       out.write("\n              <option>Verdana</option> ");
/* 376 */       out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"fontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n          \n        </div>\n        <div class=\"popup-button\">\n          <input id=\"devicePropertiesDone\" name=\"\" value='");
/* 377 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 378 */       out.write("' type=\"button\" class=\"m-done-but\"/>\n          <input id=\"devicePropertiesCancel\" name=\"\" value='");
/* 379 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 380 */       out.write("' type=\"button\" class=\"m-cancel-but\" />\n        </div>\n      </div>\n    </div>\n    <div id=\"scProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 381 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scprops"));
/* 382 */       out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossScPropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n        <div class=\"error\" id=\"sc-name-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\">");
/* 383 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scnamecheck"));
/* 384 */       out.write("</div>\n          </div>\n          <p>\n            <label>");
/* 385 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.scname"));
/* 386 */       out.write("</label>\n            <input id=\"scName\" name=\"\" type=\"text\" />\n          </p>\n          <div class=\"error\" id=\"sc-submap-val\"><span class=\"val-arrow\"></span>\n\t\t    <div class=\"error-box\"> ");
/* 387 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectmappopup"));
/* 388 */       out.write("</div>\n          </div>\n          <p>\n            <label>");
/* 389 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectsubmap"));
/* 390 */       out.write("</label>\n\t    <span id=\"listsubmaps\"> </span> \n\t    </p>\n\n          <p>\n            <label>");
/* 391 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.displayicons"));
/* 392 */       out.write("</label>\n            <input id=\"sc-rad-opt1\" type=\"radio\" name=\"scIcons\" value=\"sc-opt1\" >\n            <label class=\"radio-label\">");
/* 393 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons"));
/* 394 */       out.write("</label>\n            <input id=\"sc-rad-opt2\" name=\"scIcons\" type=\"radio\" value=\"sc-opt2\" checked>\n            <label class=\"radio-label\">");
/* 395 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.customicons"));
/* 396 */       out.write("</label>\n          </p>\n          <div id=\"sc-opt1\" class=\"radio-div\" >\n            <p>\n              <label for=\"mapLocation\" class=\"icon-label\">");
/* 397 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.defaulticons.select"));
/* 398 */       out.write("</label>\n              <span><img src=\"/images/square-nor.gif\" id=\"scicon1\" alt=\"RECT\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" id=\"scicon2\" alt=\"OVAL\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" id=\"scicon3\" alt=\"DIAMOND\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" id=\"scicon4\" alt=\"ISOSCELES_TRIANGLE\" width=\"39\" height=\"36\" /></span> </p>\n          </div>\n          <div id=\"sc-opt2\" class=\"radio-div\" style=\"display:block;\">\n            <div>\n              <p>\n                <label for=\"mapLocation\">");
/* 399 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscicons"));
/* 400 */       out.write("</label>\n                <select id=\"scDeviceList\">\n                  <option>");
/* 401 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectscimage"));
/* 402 */       out.write("</option>\n\t\t \t\t  ");
/*     */       
/* 404 */       ArrayList<String> scImage = MapViewUtil.getListOfDeviceImages();
/* 405 */       int scCount = scImage.size();
/* 406 */       for (int i = 0; i < scCount; i++)
/*     */       {
/* 408 */         String imageName = (String)scImage.get(i);
/*     */         
/* 410 */         out.write("\n                  <option value='");
/* 411 */         out.print(imageName);
/* 412 */         out.write(39);
/* 413 */         out.write(62);
/* 414 */         out.print(imageName);
/* 415 */         out.write("</option> ");
/* 416 */         out.write("\n                  ");
/*     */       }
/*     */       
/*     */ 
/* 420 */       out.write("\n\t\t\t\t  <script>$('#scDeviceList').val(\"shortcut_icon.png\");</script>\n                </select>\n              </p>\n            </div>\n            <!--div>\n              <p>\n                <label class=\"icon-label\"> Select Device BG :</label>\n                <span><img src=\"/images/square-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/circle-nor.gif\" width=\"40\" height=\"36\" /><img src=\"/images/diamond-nor.gif\" width=\"39\" height=\"36\" /><img src=\"/images/triangle-nor.gif\" width=\"39\" height=\"36\" /></span> </p>\n            </div-->\n          </div>\n\t  <h4 class=\"add-device-hea\">");
/* 421 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.labelsettings"));
/* 422 */       out.write("</h4>\n                 <p>\n            <label>");
/* 423 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.font"));
/* 424 */       out.write("</label>\n            <select id=\"scFontType\" name=\"\" style=\"float:left; width:150px; margin-right:3px;\">\n              <option>Arial</option>  ");
/* 425 */       out.write("\n\t      <option>Calibri</option> ");
/* 426 */       out.write("\n              <option>Comic San MS</option> ");
/* 427 */       out.write("\n              <option>Courier New</option> ");
/* 428 */       out.write("\n              <option>Georgia</option> ");
/* 429 */       out.write("\n              <option>Tahoma</option> ");
/* 430 */       out.write("\n              <option>Times New Roman</option> ");
/* 431 */       out.write("\n              <option>Trebuchet MS</option> ");
/* 432 */       out.write("\n              <option>Verdana</option> ");
/* 433 */       out.write("\n            </select>\n            <!--select name=\"\" style=\"float:left; width:60px;  margin-right:3px;\">\n              <option>Bold</option>\n              <option>Italic</option>\n              <option>Bold/Italic</option>\n            </select-->\n            <select id=\"scFontSize\" name=\"\" style=\"float:left; width:50px;  margin-right:3px;\">\n              <option>10</option>\n              <option>12</option>\n              <option>14</option>\n              <option>16</option>\n              <option>18</option>\n            </select>\n          </p>\n        </div>\n\t<p class=\"short-info\"><img src=\"/images/info.gif\" align=\"absmiddle\"/> ");
/* 434 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addscalert"));
/* 435 */       out.write(" </p>");
/* 436 */       out.write("\n        <input type=\"hidden\" id=\"scOrigin\" name=\"scOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 437 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 438 */       out.write("' id=\"scPropertiesDone\"  name=\"button\" style=\"cursor:pointer;\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 439 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 440 */       out.write("' id=\"scPropertiesCancel\" name=\"button\" style=\"cursor:pointer;\">\n        </div>\n      </div>\n    </div>\n    <div id=\"lineProperties\" class=\"device-popup\">\n      <div class=\"popup-header\">\n        <div>");
/* 441 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkprops"));
/* 442 */       out.write("<img src=\"/images/close-btn-hover.gif\" class=\"close-icon\" id=\"crossLinePropClose\" alt=\"\" width=\"12\" height=\"11\" /> </div>\n        \n      </div>\n      <div class=\"popup-body\">\n        <div class=\"popup-mainsection\">\n          <p>\n            <label>");
/* 443 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkname"));
/* 444 */       out.write(" </label>\n            <input name=\"textfield\" type=\"text\" id=\"linkName\" disabled=\"disabled\"/>\n          </p>\n\n\t  <p>\n            <label>");
/* 445 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.linkthickness"));
/* 446 */       out.write("</label>\n            <select id=\"linkThickness\">\n              <option>1</option>\n              <option>2</option>\n              <option>3</option>\n              <option>4</option>\n            </select>\n          </p>\n          <div>\n          <label>");
/* 447 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.getstatus"));
/* 448 */       out.write("</label>\n          <input id=\"srcIF\" name=\"radInterface\" type=\"radio\" value=\"srcIF\" checked/>\n          <label id=\"srcName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n          <select id=\"srcInterfaces\">\n          </select>\n          </span>\n\t</div>\n\t  <div style=\"clear:both;\"></div>\n\t  <div class=\"error1\" id=\"link-val\"><span class=\"val-arrow\"></span>\n            <div class=\"error-box\"> ");
/* 449 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.selectifpopup"));
/* 450 */       out.write(" </div>\n          </div>\t\n\t<div>\n          <label></label>\n          <input id=\"desIF\" name=\"radInterface\" type=\"radio\" value=\"desIF\" />\n          <label id=\"desName\" class=\"radio-label\"></label>\n          <span class=\"line-prop-selectbox\">\n            <select id=\"desInterfaces\">\n            </select>\n          </span>\n        </div>\n\t</div>\n        <input type=\"hidden\" value=\"\" id=\"linkID\"/>\n        <input type=\"hidden\" id=\"linkOrigin\" name=\"linkOrigin\" value=\"\"/>\n        <div class=\"popup-button\">\n          <input type=\"button\" class=\"m-done-but\" value='");
/* 451 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.done"));
/* 452 */       out.write("' id=\"linePropertiesDone\"  name=\"button\">\n          <input type=\"button\" class=\"m-cancel-but\" value='");
/* 453 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.cancel"));
/* 454 */       out.write("' id=\"linePropertiesCancel\" name=\"button\">\n        </div>\n      </div>\n    </div>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr>\n        <td width=\"43\" valign=\"top\" align=\"left\" class=\"nav-td2\" ><div id=\"map-toolbar\">\n            <ul>\n              <li id=\"toolbar-nav\">\n                <div class=\"map-tool-bg\"> <img src=\"/images/tools-arrow2.png\" class=\"tools-arrow\" title='");
/* 455 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.maximize"));
/* 456 */       out.write("'/><img src=\"/images/tools-arrow1.png\" class=\"tools-arrow\" style=\"display:none\" title='");
/* 457 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.minimize"));
/* 458 */       out.write("'/>\n                  <div class=\"tool-txt-show\">");
/* 459 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.tools"));
/* 460 */       out.write("</div>\n                </div>\n              </li>\n              <li id=\"addDeviceLink\" title='");
/* 461 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/* 462 */       out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-device.png\" />\n                  <div class=\"tool-txt-show\">");
/* 463 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.adddevice"));
/* 464 */       out.write("</div>\n                </div>\n              </li>\n              <li id=\"link\" title='");
/* 465 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/* 466 */       out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-link.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 467 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addlink"));
/* 468 */       out.write("</div>\n                </div>\n              </li>\n              <li id=\"shortcut\" title='");
/* 469 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/* 470 */       out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-add-shortcut.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 471 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addsc"));
/* 472 */       out.write("</div>\n                </div>\n              </li>\n               <li id=\"delete\" onClick='del()'; title='");
/* 473 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/* 474 */       out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-delete.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 475 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.delete"));
/* 476 */       out.write("</div>\n                </div>\n              </li>\n              <li id=\"propertiesLink\" title='");
/* 477 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/* 478 */       out.write("'>\n                <div class=\"map-tool-bg\"> <img src=\"/images/m-properties.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 479 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.addprops"));
/* 480 */       out.write("</div>\n                </div>\n              </li>\n              <li id=\"help\" onClick='launchMapHelp()'>\n                <div class=\"map-tool-bg\" title='");
/* 481 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/* 482 */       out.write("'> <img src=\"/images/m-help.png\"/>\n                  <div class=\"tool-txt-show\">");
/* 483 */       out.print(FormatUtil.getString("am.webclient.jsp.mapvieweditor.help"));
/* 484 */       out.write("</div>\n                </div>\n              </li>\n            </ul>\n          </div></td>\n        <td bgcolor=\"#FFFFFF\" width=\"auto\"><div style=\"position:relative; z-index:10;\">\n            <div id=\"parentcontainer\">\n              <div id=\"shapecontainer\">\n                <div id=\"inner_shape\" class=\"inner_shape\" style=\"z-index:2;height:600px;\"> </div>\n              </div>\n            </div>\n          </div></td>\n      </tr>\n    </table>\n  </div>\n</div>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 486 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 487 */         out = _jspx_out;
/* 488 */         if ((out != null) && (out.getBufferSize() != 0))
/* 489 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 490 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 493 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 499 */     PageContext pageContext = _jspx_page_context;
/* 500 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 502 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 503 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 504 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 506 */     _jspx_th_c_005fout_005f0.setValue("${type}");
/* 507 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 508 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 509 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 510 */       return true;
/*     */     }
/* 512 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 513 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 518 */     PageContext pageContext = _jspx_page_context;
/* 519 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 521 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 522 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 523 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */     
/* 525 */     _jspx_th_c_005fforEach_005f0.setItems("${resourceTypes}");
/*     */     
/* 527 */     _jspx_th_c_005fforEach_005f0.setVar("row");
/* 528 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 530 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 531 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) {
/* 533 */           out.write("\n\t\t\t\t\t");
/* 534 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 535 */             return true;
/* 536 */           out.write("\n\t\t\t\t");
/* 537 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 538 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 542 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 543 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 546 */         int tmp187_186 = 0; int[] tmp187_184 = _jspx_push_body_count_c_005fforEach_005f0; int tmp189_188 = tmp187_184[tmp187_186];tmp187_184[tmp187_186] = (tmp189_188 - 1); if (tmp189_188 <= 0) break;
/* 547 */         out = _jspx_page_context.popBody(); }
/* 548 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 550 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 551 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 553 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 558 */     PageContext pageContext = _jspx_page_context;
/* 559 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 561 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 562 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 563 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 565 */     _jspx_th_c_005fforEach_005f1.setVar("type");
/*     */     
/* 567 */     _jspx_th_c_005fforEach_005f1.setItems("${row}");
/* 568 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*     */     try {
/* 570 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 571 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*     */         for (;;) {
/* 573 */           out.write("\n\t\t\t\t\t");
/* 574 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 575 */             return true;
/* 576 */           out.write("\n\t\t\t\t\t<option value=\"");
/* 577 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 578 */             return true;
/* 579 */           out.write(34);
/* 580 */           out.write(62);
/* 581 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 582 */             return true;
/* 583 */           out.write("</option>\n\n\t\t\t\t\t");
/* 584 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 585 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 589 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 590 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 593 */         int tmp280_279 = 0; int[] tmp280_277 = _jspx_push_body_count_c_005fforEach_005f1; int tmp282_281 = tmp280_277[tmp280_279];tmp280_277[tmp280_279] = (tmp282_281 - 1); if (tmp282_281 <= 0) break;
/* 594 */         out = _jspx_page_context.popBody(); }
/* 595 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*     */     } finally {
/* 597 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 598 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */     }
/* 600 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 605 */     PageContext pageContext = _jspx_page_context;
/* 606 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 608 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 609 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 610 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 612 */     _jspx_th_c_005fout_005f1.setValue("${type}");
/* 613 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 614 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 615 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 616 */       return true;
/*     */     }
/* 618 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 619 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 624 */     PageContext pageContext = _jspx_page_context;
/* 625 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 627 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 628 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 629 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 631 */     _jspx_th_c_005fout_005f2.setValue("${type.key}");
/* 632 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 633 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 634 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 635 */       return true;
/*     */     }
/* 637 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 638 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 643 */     PageContext pageContext = _jspx_page_context;
/* 644 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 646 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 647 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 648 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 650 */     _jspx_th_c_005fout_005f3.setValue("${type.value}");
/* 651 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 652 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 653 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 654 */       return true;
/*     */     }
/* 656 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 657 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MapViewEditor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */