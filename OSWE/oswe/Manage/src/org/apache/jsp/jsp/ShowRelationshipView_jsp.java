/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class ShowRelationshipView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  56 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  59 */     JspWriter out = null;
/*  60 */     Object page = this;
/*  61 */     JspWriter _jspx_out = null;
/*  62 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  66 */       response.setContentType("text/html;charset=UTF-8");
/*  67 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  69 */       _jspx_page_context = pageContext;
/*  70 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  71 */       ServletConfig config = pageContext.getServletConfig();
/*  72 */       session = pageContext.getSession();
/*  73 */       out = pageContext.getOut();
/*  74 */       _jspx_out = out;
/*     */       
/*  76 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n<html>\n<head>\n");
/*  77 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  78 */       out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n<link href=\"/images/colpick.css\" rel=\"stylesheet\" type=\"text/css\"/>\n<link href=\"images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script type='text/javascript' src='../template/jit.js'></script>\n<script type='text/javascript' src='../template/jitSpaceTree.js'></script>\n<script type='text/javascript' src='../template/graphUtil.js'></script>\n\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n<script>\nvar st;\nvar haid_flash =\"0\";\nvar saveFlag = 0;\nvar displayProps;\nvar refreshInterval = 6000;\nvar isHover = false;\nif(");
/*  79 */       out.print(request.getAttribute("haid") != null);
/*  80 */       out.write(")\n{\nhaid_flash=");
/*  81 */       out.print((String)request.getAttribute("haid"));
/*  82 */       out.write(";\n}\nif(");
/*  83 */       out.print(request.getParameter("haid") != null);
/*  84 */       out.write(")\n{\nhaid_flash=");
/*  85 */       out.print(request.getParameter("haid"));
/*  86 */       out.write(";\n}\nvar viewid=\"-1\";\nif(");
/*  87 */       out.print(request.getAttribute("viewid") != null);
/*  88 */       out.write(")\n{\nviewid=");
/*  89 */       out.print((String)request.getAttribute("viewid"));
/*  90 */       out.write(";\n}\nif(");
/*  91 */       out.print(request.getParameter("viewid") != null);
/*  92 */       out.write(")\n{\nviewid=");
/*  93 */       out.print(request.getParameter("viewid"));
/*  94 */       out.write(";\n}\nfunction initPage(resourceID)\n{   \n\t");
/*  95 */       System.err.println("Inside initPage");
/*  96 */       out.write("\n    cacheid = (new Date()).getTime();\n    var dataString = \"method=getMGXML&haid=\"+haid_flash+\"&viewid=\"+viewid+\"&currentime=\"+");
/*  97 */       out.print(System.currentTimeMillis());
/*  98 */       out.write("+\"&isHtml=true\"; //No I18N    \n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                {         \t\t\t\t\t\n\t\t\t\t\tsaveFlag = response['saveFlag'];\n\t\t\t\t\tif(saveFlag != 0){\n\t\t\t\t\t\tsetDisplayPropsForGraph(response['backgroundColorVal'], response['lineColorVal'], response['textColorVal'],response['lineThickness'],response['lineTransparency']);\n\t\t\t\t\t\tnodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\n\t\t\t\t\t}\n\t\t\t\t\t//canvasX = \"");
/*  99 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 101 */       out.write("\";\n\t\t\t\t\t//canvasY = \"");
/* 102 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 104 */       out.write("\";\n\t\t\t\t\t canvasX = response['xCanvas'];\n                     canvasY = response['yCanvas'];\n                    st = initJITSpaceTree(response, 'infovis', true, '#infovis',saveFlag);\t//No I18N\n\t\t\t\t\t$('#progressSpanForCBV').hide();\n                }\n    });\n}\n\n\nfunction updateNodePosOnDrag(node, parentNode, parentDivWidth, parentDivHeight){\t\n\t//While changing nodes on default view, if viewid variable retains its previous selectedViewid value, it makes changes in that view with the displayname as empty..\n\tviewid=$('#availableViews').val();\n\tif(haid_flash==0)\n\t{\n\t if(viewid==0 || viewid==-1)\n\t\t{\n\t\talert('");
/* 105 */       out.print(FormatUtil.getString("am.webclient.notsaved.defaultview.changes.alert"));
/* 106 */       out.write("');\n\t\treturn;\n\t\t}\n\t}\t\n\tif(saveFlag == 0){\n\t\t// save config and save all coordinates \n\t\tsaveCurrentState(parentDivWidth, parentDivHeight);\t\n\t}else if(saveFlag == 1){\n\t\t// save all coordinates alone\n\t\tsaveAllCoordinates();\n\t}else{\n\t\t//check the parent coordinate pos - if thats not saved..\n\t//\tif(saved){\n\t\t\t\tupdateOnlyCurrentNode(node, parentNode);\n\t//\t}else{\n\t\t//\tsave all node in parent path.\n\t\t//}\n\t}\n}\n\nfunction updateOnlyCurrentNode(node, parentNode){\n\tvar relativeX = node.getData(\"nodeX\") - parentNode.getData(\"nodeX\");\t//No I18N\n\tvar relativeY = node.getData(\"nodeY\") - parentNode.getData(\"nodeY\");\t//No I18N\n\tvar nodeResId = node.getData(\"resourceID\");\t\t\t\t\t\t\t//No I18N\t\n\tvar parentNodeResId = parentNode.getData(\"resourceID\");\t\t\t\t//No I18N\t\n\tvar dataString = \"method=updateNodePosOnDrag&nodeResId=\"+nodeResId+\"&viewid=\"+viewid+\"&parentNodeResId=\"+parentNodeResId+\"&relativeX=\"+relativeX+\"&relativeY=\"+relativeY+\"&currentime=\"+");
/* 107 */       out.print(System.currentTimeMillis());
/* 108 */       out.write("+\"&isHtml=true\"; //No I18N    \n\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t\t url:\"/GraphicalView.do\", //No I18N\n\t\t\t data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n\t\t\t success: function(response)\n\t\t\t {         \n\t\n\t\t\t }\n    });\n}\n\nfunction saveAllCoordinates(){\n\t\tvar nodesForSave = getJITNodesToSave();\n\t\tvar pos = getCanvasPos();\n\t\tvar dataString = \"method=saveCoordinates&haid=\"+haid_flash+\"&currentime=\"+");
/* 109 */       out.print(System.currentTimeMillis());
/* 110 */       out.write("+\"&viewid=\"+viewid+\"&nodesForSave=\"+nodesForSave;  // No I18N\n\t\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                {         \n\t\t\t\t\n                }\n    });\n}\n\nfunction saveCurrentState(parentDivWidth, parentDivHeight){\n\t\tvar nodesForSave = getJITNodesToSave();\n\t\tvar pos = getCanvasPos();\n\t\tvar xPos = (pos.x/parentDivWidth) * 100;\n\t\tvar yPos = (pos.y/parentDivHeight) * 100;\n\t\tvar dataString = \"method=saveState&haid=\"+haid_flash+\"&currentime=\"+");
/* 111 */       out.print(System.currentTimeMillis());
/* 112 */       out.write("+\"&displayName=\"+$('#dispName_1').html()+\"&viewid=\"+viewid+\"&nodesForSave=\"+nodesForSave+\"&isHtml=true&toSave=true&xcanvas=\"+xPos+\"&ycanvas=\"+yPos;\t//No I18N\n\t\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                {\n\t\t\t\t\tviewid = response['viewid'];\n\t\t\t\t\tsaveFlag = 2;\n                }\n    });\n}\n\nfunction getJITJSON(){\n ");
/* 113 */       System.err.println("Inside getJITJSON");
/* 114 */       out.write("\n\nconsole.log(\"Inside getJITJSON\");\n\treturn st.json;\n}\n\n\nfunction setRecentStatusForJIT(haid, viewId){\nconsole.log(\"Inside setRecentStatusForJIT\");\n\tvar resourceIds = getAllJITResourceIds();\n\t//console.log(resourceIds);\n    var dataString = 'method=getLatestStatusForJIT&haid='+haid+'&viewid='+viewid+'&currentime='+");
/* 115 */       out.print(System.currentTimeMillis());
/* 116 */       out.write("+'&resourceIDs='+resourceIds; //No I18N    \n\t$.ajax(\n    {\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType:\"json\",//No I18N\n                success: function(response)\n                {         \n\t\t\t\t\tsetRecentStatusForJITNow(response);\n                }\n    });\n}\n\n\nfunction getAllJITResourceIds(){\nconsole.log(\"Inside getAllJITResourceIds\");\n\tvar jsonString = \"(\";\n\tst.graph.eachNode(function(node){\n\t\tjsonString += node.getData(\"resourceID\") + \",\";//No I18N\n\t});\n\tjsonString += \"0)\";\n\treturn jsonString;\n}\n\nfunction setRecentStatusForJITNow(json){\nconsole.log(\"Inside setRecentStatusForJITNow\");\n   st.graph.eachNode(function(node){\n\t\tvar statusForEachNode = json[node.getData(\"resourceID\")];\n\t\tif(statusForEachNode !== undefined){\n\t\t\tnode.setData(\"message\",statusForEachNode.message);//No I18N\n\t\t\tif(statusForEachNode.severity !== undefined){\n\t\t\t\t$('#'+node.id+' span#nameIdentifier').removeClass();\n\t\t\t\t$('#'+node.id+' span#nameIdentifier').addClass(statusForEachNode.severity);\n");
/* 117 */       out.write("\t\t\t}\n\t\t}\n\t});\n}\n\nfunction getJITNodesToSave(){\n\tvar jsonString = '{';\n\tvar counter = 0;\n\tvar listOfNodeIds = [];\n\tfor(var i in st.graph.nodes){\n\t\tvar id = st.graph.nodes[i].id;\n\t\tvar parentNodeID = st.graph.nodes[i].getData(\"parentNodeID\");\n\t\tvar parentID = st.graph.nodes[i].getData(\"parentID\");\n\t\tvar resourceID = st.graph.nodes[i].getData(\"resourceID\");\n\t\tvar pos = st.graph.nodes[i].pos.getc();\n\t\tvar posX = pos.x;\n\t\tvar posY = pos.y;\n\t\t\n\t\tif(parentNodeID != \"-1\"){\n\t\t\tvar parentNode = st.graph.getNode(parentNodeID);\n\t\t\tvar parentPos = parentNode.pos.getc();\n\t\t\tposX = posX - parentPos.x;\n\t\t\tposY = posY - parentPos.y;\n\t\t\tlistOfNodeIds[counter] = id;\n\t\t\tcounter++;\n\t\t}\n\t\tjsonString += \"\\\"parentID_\"+id+\"\\\":\\\"\"+parentID+\"\\\",\\\"resourceID_\"+id+\"\\\":\\\"\"+resourceID+\"\\\",\\\"posX_\"+id+\"\\\":\"+posX+\",\\\"posY_\"+id+\"\\\":\"+posY+\",\";// No I18N\n\t\t\n\t}\n\tjsonString +='\\\"totalNumberOfNodes\\\":'+counter+\",\\\"xcanvas\\\":\"+pos.x+\",\\\"ycanvas\\\":\"+pos.y+\",\";// No I18N\n\tjsonString +='\\\"nodeIdList\\\":'+\"[\"+listOfNodeIds.join(\",\")+\"]\";// No I18N\n\tjsonString += '}';\n");
/* 118 */       out.write("\treturn jsonString;\n}\n\nfunction assignChildPos(node){\n\n");
/* 119 */       System.err.println("Inside assignChildPos");
/* 120 */       out.write("\nconsole.log(\"Inside assignChildPos\");\n\t\tvar i = 0;\n\t\tvar total = 0;\n\t\tnode.eachSubnode(function(subnode){total++;});\n\t\tif(total == 0){\n\t\t\treturn;\n\t\t}\n\t\tvar radius = (total * 25)+100;\n\t\tvar parentPos = node.pos.getc(true);\n\t\tnode.eachSubnode(function(subnode){\n\t\t\tvar check = subnode.getData(\"nodeX\");\t\t\t// No I18N\n\t\t\tif(node.id != subnode.id && check ==\"default\"){\n\t\t\t\tvar radian = (i/(total-1)) * (Math.PI/2);\n\t\t\t\tradian = (isNaN(radian))?0:radian;\n\t\t\t\t\n\t\t\t\tvar x = Math.cos(radian) * radius + parentPos.x;\n\t\t\t\tvar y = Math.sin(radian) * radius + parentPos.y;\n\t\t\t\t\n\t\t\t\tsubnode.setData(\"nodeX\",x);// No I18N\n\t\t\t\tsubnode.setData(\"nodeY\",y);// No I18N\n\t\t\t\tsubnode.pos.setc(x,y,true);\n\t\t\t\ti++;\n\t\t\t}\n\t\t});\n}\n\nfunction setNodeCss(x,y){\n\n");
/* 121 */       System.err.println("Inside setNodeCss");
/* 122 */       out.write("\n\t\t$(\".node\").css(\"-moz-transform\", \"scale(\" + x +\",\" +  y + \")\");\t\t// No i18n\n\t\t$(\".node\").css(\"-webkit-transform\", \"scale(\" + x +\",\" + y + \")\");\t// No i18n\n\t\t$(\".node\").css(\"-ms-transform\", \"scale(\" + x +\",\" +  y + \")\");\t\t\t// No i18n\n\t\t$(\".node\").css(\"-o-transform\", \"scale(\" + x +\",\" +  y + \")\");\t\t\t// No i18n\n}\n\nfunction refreshResponse(){\n\t\tif(isHover){\n\t\t\treturn;\n\t\t}\n\t  $('#infovis-canvaswidget').remove(); //No I18N\n\t   cacheid = (new Date()).getTime();\n  var dataString = \"method=getMGXML&haid=\"+haid_flash+\"&viewid=\"+viewid+\"&currentime=\"+");
/* 123 */       out.print(System.currentTimeMillis());
/* 124 */       out.write("+\"&isHtml=true\"+\"&cacheid=\"+cacheid; //No I18N    \n\t$.ajax(\n  {\n              type:\"POST\", //No I18N\n              url:\"/GraphicalView.do\", //No I18N\n              data:dataString,\n              success: function(response)\n              {         \n\t\t\t    nodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\n\t\t\t    st = initJITSpaceTree(response, 'infovis', true, '#infovis', saveFlag);\t//No I18N\n              }\n  });\n}\t\n\nfunction setElementAccordingToBackground(backgroundColour, labelcolor){\n\n");
/* 125 */       System.err.println("Inside setElementAccordingToBackground");
/* 126 */       out.write("\n\t$('.iconBox i').css('background',backgroundColour);// No I18N\n\t$('.iconBox i a').css('color',labelcolor);// No I18N\n\t$('.iconBox span.groupMon').css('background',backgroundColour);// No I18N\n\t$('u.iconAddMinus, u.iconD').css('background-color',backgroundColour);// No I18N\n\t$('u.iconAddMinus, u.iconD').css('color', backgroundColour);// No I18N\n}\n\nfunction getDim(linethickness){\n\n");
/* 127 */       System.err.println("Inside getDim");
/* 128 */       out.write("\n\treturn ((parseFloat(linethickness)*2)+10.0);\n}\n\nfunction getLineStrength(dim){\n\n");
/* 129 */       System.err.println("Inside getLineStrength");
/* 130 */       out.write("\n\tdim = parseInt(dim);\n\tif(dim > 24){\n\t\treturn 4;\n\t}else if(dim < 15){\n\t\treturn 1;\n\t}else if(dim > 14 && dim < 20){\n\t\treturn 2;\n\t}else{\n\t\treturn 3;\n\t}\n}\n\nfunction setElementsAccordingToAttributes(){\n\n");
/* 131 */       System.err.println("Inside setElementsAccordingToAttributes");
/* 132 */       out.write("\n\tvar criticalMonitors = \"");
/* 133 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/* 135 */       out.write("\";\n\tif(criticalMonitors === \"true\"){\n\t\tvar ele = $(\"#showCritical\").prop(\"checked\",true);  //No I18N\n\t}\n}\n\n\nfunction changeGraphAsPerType(){\n\t $('#infovis-canvaswidget').remove(); //No I18N\n\tvar showCriticalMonitors = document.getElementById(\"showCritical\").checked;\n\tvar showTopOnly = document.getElementById(\"showTopLevel\").checked;\n\tvar showGroups = document.getElementById(\"showSubgroups\").checked;\n\tvar all = document.getElementById(\"all\").checked;\n\tchangeGraphAsPerCondition(showCriticalMonitors, showTopOnly, showGroups, all);\n}\n\n\n\t\t\nfunction setDefaultDisplayProps(){\n\tvar dName = $('#dispName_1').html();\n\t\n\tviewid=$('#availableViews').val();\n\t//if(displayProps == undefined){\n\t  \tvar dataString = \"method=getDisplayPropsForGraph&haid=\"+haid_flash+\"&viewid=\"+viewid; //No I18N    \n\t\t$.ajax(\n\t    {\n\t\t\t      type:\"POST\", //No I18N\n\t\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t\t      data:dataString,\n\t\t\t\t\tdataType: \"json\",//No I18N\n\t\t\t      success: function(response)\n\t\t\t      {    \n\t\t\t\t\t\n\t\t\t\t\tif(response['isConfigSaved'] == true){\n");
/* 136 */       out.write("\t\t\t\t\t\tdisplayProps = response['displayProps'];\n\t\t\t\t\t\tsetDisplayPropsForGraph(displayProps.backgroundColorVal, displayProps.lineColorVal, displayProps.textColorVal,displayProps.lineThickness,displayProps.lineTransparency);\n\t\t\t\t\t\tsetDisplayProperties(displayProps);\n\t\t\t\t\t}\n\t\t\t\t\telse\n\t\t\t\t    \t\t{\n\t\t\t\t    \t\t$('#display_name').val(response['displayName']);\n\t\t\t\t    \t\t}\n\t\t\t\t\t\tsetOtherColourProperties();\n\t\t\t\t\tif(haid_flash != 0){\n\t\t\t\t\t\t$('#display_name').val(dName+\"\");\n\t\t\t      }\n\t\t\t      }\n\t    });\n   /* }else if(saveFlag != 0){\n\t\tsetDisplayPropsForGraph(displayProps.backgroundColorVal, displayProps.lineColorVal, displayProps.textColorVal,displayProps.lineThickness,displayProps.lineTransparency);\n\t\tsetDisplayProperties(displayProps);\n\t\tsetOtherColourProperties();\n\t}else{\n\t\tsetOtherColourProperties();\n\t\t$('#display_name').val(displayProps.displayName);\n\t}\n\t*/\n\tif(displayProps != undefined && !displayProps.showLabel){\n\t\thideLabelForPreview();\n\t}\n}\n\nfunction setDefaultDisplayPropsForGlobalView(){\n\tvar dataString = \"method=getDisplayPropsForGraph&haid=\"+haid_flash+\"&viewid=\"+viewid; //No I18N    \n");
/* 137 */       out.write("\t$.ajax({\n\t\t\n\t});\n}\n\n$(document).ready(function(){\n\t setElementsAccordingToAttributes();\n});\n\nfunction saveDisplayProps(){\n\tchangeGraphAsPerDisplayProps();\n}\n\nfunction changeToGlobalView(selectedViewId){\n\tviewid=selectedViewId;\n\t\tvar isShowOnlyTopLevel = document.getElementById(\"editShowOnlyStatus\").checked;\n\t\tvar dataString = \"method=updateShowTopLevelFlag&haid=\"+haid_flash+\"&viewid=\"+viewid+\"&showTopLevelFlag=\"+isShowOnlyTopLevel; //No I18N    \n\t\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t      data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t\t      success: function(response){\t\t\t\t\n\t\t\t\t$(\"#dialogDisplayPro\").dialog('destroy').remove();\t\t\t\t\t\t\t\t\t// No I18N\n\t\t\t\tbusinessServiceTree = undefined;\n\t\t\t\t//var bvName=response.bvName;\n\t\t\t\t$('#BVViewer').load('/jsp/businessServiceGlobalView.jsp?isReadOnly=false&displayAvailableViews='+displayAvailableViews+'&haid='+haid_flash+'&selectedViewId='+viewid);\t\t\t\t //No I18N\n\t\t\t }\n\t    });\t\n}\n\nfunction changeGraphAsPerDisplayProps(selectedViewId){\n");
/* 138 */       out.write("\n\t $('#infovis-canvaswidget').remove(); //No I18N\n\t\n\tvar showCriticalMonitors = document.getElementById(\"editShowOnlyCriticalMonitors\").checked;\n\tvar showTopOnly = document.getElementById(\"editShowOnlyTopMGs\").checked;\n\tvar showGroups = document.getElementById(\"editShowOnlyMGs\").checked;\n\tvar showLabelForGraph = document.getElementById(\"editShowOnlyLabel\").checked;\n\tvar all = false;\n\tif(!showTopOnly && !showGroups){\t\n\t\tall = true;\n\t}\n\tvar bgcolor = $('#picker').val();\n\tvar lineColor = $('#pickerLine').val();\n\tvar labelColor = $('#pickerText').val();\n\tvar parentWidth = $('#infovis').width();\t\n\tvar parentHeight = $('#infovis').height();\n\tvar lineThickness = $('#editLineThicker').attr('value');\t//No I18N\t\t\n\tvar lineTransparency = $( \"#slider-range-min\" ).slider( \"value\" )/100;\t//No I18N\n//\talert(bgcolor)\n\tchangeCanvasBgForSt('infovis-canvas', 'redirectToDetails', bgcolor);\t//No I18N\t\t\n\tbackgroundDisplayColor = bgcolor;\n\tedgeLineDisplayColor = lineColor;\n\ttextDisplayColour = labelColor;\n\tgraphLineThickness = lineThickness;\n");
/* 139 */       out.write("\tgraphLineTransparency = lineTransparency;\n\tif(displayProps == undefined){\n\t\tdisplayProps = {};\n\t}\n\tdisplayProps.displayName = $('#display_name').val();\n\tdisplayProps.backgroundColorVal = bgcolor;\n\tdisplayProps.lineColorVal = lineColor;\n\tdisplayProps.textColorVal = labelColor;\n\tdisplayProps.lineThickness = lineThickness;\n\tdisplayProps.lineTransparency = lineTransparency;\n\tdisplayProps.showLabel = showLabelForGraph;\n\tdisplayProps.showOnlyMGs = showGroups;\n\tdisplayProps.showOnlyTopMGs = showTopOnly;\n\tdisplayProps.showOnlyCritical = showCriticalMonitors;\n\tdisplayProps.showOnlyMGStatus = false;\n\tvar pos = getCanvasPos();\n\tdisplayProps.xCanvas = ((pos.x/parentWidth) * 100);\n\tdisplayProps.yCanvas = ((pos.y/parentHeight) * 100);\t\n\tchangeGraphAsPerCondition(showCriticalMonitors, showTopOnly, showGroups, all, displayProps);\n\t//e.preventDefault();\n\t if(haid_flash == 0){\n\t\t\tvar showOnlyStatus = document.getElementById(\"editShowOnlyStatus\").checked;\n\t\t\tif(showOnlyStatus){\n\t\t\t\tchangeToGlobalView(selectedViewId);\n\t\t\t\t//return;\n");
/* 140 */       out.write("\t\t\t}\n\t\t}\n\t$(\"#dialogDisplayPro\").dialog('close');\t\t //No I18N\n}\n\n\nfunction changeGraphAsPerCondition(showCriticalMonitors, showTopOnly, showGroups, all, displayProps){\n\t\n\tvar dataString = \"method=getGraphAccordingToType&haid=\"+haid_flash+\"&viewid=\"+viewid+\"&currentime=\"+");
/* 141 */       out.print(System.currentTimeMillis());
/* 142 */       out.write("+\"&isHtml=true\"+\"&showTopOnly=\"+showTopOnly+\"&showGroups=\"+showGroups+\"&all=\"+all+\"&showCritical=\"+showCriticalMonitors+\"&onChangeViewProps=true&displayProps=\"+JSON.stringify(displayProps); //No I18N   \n\t//alert(dataString) \n\t$('#progressSpanForCBV').show();\n\t$.ajax(\n    {\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                { \n                \tnodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\n\t\t\t\t\tst = initJITSpaceTree(response, 'infovis', true, '#infovis', saveFlag);\t//No I18N\t\n\t\t\t\t\t$('#progressSpanForCBV').hide();\n                }\n    });\n}\n\nfunction updateCanvasPosOnDrag(translateOffsetX, translateOffsetY, graphObj,parentDivWidth, parentDivHeight){\n\tviewid=$('#availableViews').val();\n\t//haid_flash!=0 condition is used to create a view in MonitorGroups available in Home->MonitorGroups widget.\n\tif(graphObj.root != 'test1' && (haid_flash!='0' || viewid!=0)){\n");
/* 143 */       out.write("\t\tif(viewid=='0')\n\t\t\t{viewid='-1';\t\t\t\n\t\t\t}\n\t\tvar displayProperties = getDisplayPropertiesFromGraphObj(graphObj, parentDivWidth, parentDivHeight);\n\t\tvar dataString = \"method=updateCanvasPosOnDrag&resId=\"+haid_flash+\"&viewid=\"+viewid+\"&translateOffsetX=\"+translateOffsetX+\"&translateOffsetY=\"+translateOffsetY+\"&displayProps=\"+JSON.stringify(displayProperties)+\"&currentime=\"+");
/* 144 */       out.print(System.currentTimeMillis());
/* 145 */       out.write("; //No I18N    \n\t\t$.ajax({\n\t\t           type:\"POST\", //No I18N\n\t\t           url:\"/GraphicalView.do\", //No I18N\n\t\t           data:dataString,\n\t\t\t\t   dataType: \"json\",//No I18N\n\t\t           success: function(response)\n\t\t           {         \n\t\t\t\t\t\tif(viewid == -1){\n\t\t\t\t\t\t\tviewid = response['viewid'];\t\n\t\t\t\t\t\t\tsaveFlag = 1;\n\t\t\t\t\t\t}\n\t\t           }\n\t    });\n\t}\n}\n\nfunction createNewView(){\n\tfnOpenNewWindow('/GraphicalView.do?method=createNewView&fromWhere=hometab&viewid='+viewid+'&haid='+haid_flash,\"Flash View\",\"scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=250,screenY=25\");//No I18N\n}\nfunction editView()\n{\n\tif(selectedViewId=='0')\n\t\t{\n\t\tif($('#availableViews option').length=='1')\n\t\t{\n\t\t\talert('");
/* 146 */       out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 147 */       out.write("');\n\t\t\treturn;\n\t\t}\n\t\talert('");
/* 148 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 149 */       out.write("');\n\t\treturn;\n\t\t}\n\tfnOpenNewWindow('/GraphicalView.do?method=showEditView&fromWhere=hometab&viewid='+selectedViewId+'&haid='+haid_flash,\"Flash View\",\"scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=250,screenY=25\");//No I18N\n}\nfunction deleteView()\n{\n\tif(selectedViewId=='0')\n\t{\n\t\tif($('#availableViews option').length=='1')\n\t\t{\n\t\t\talert('");
/* 150 */       out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 151 */       out.write("');\n\t\t\treturn;\n\t\t}\n\talert('");
/* 152 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 153 */       out.write("');\n\treturn;\n\t}\n\tvar confirmDelete = confirm('");
/* 154 */       out.print(FormatUtil.getString("am.webclient.delete.confirm.msg"));
/* 155 */       out.write("');\n\t  \n    if(confirmDelete)\n    \t{\n\t//document.location.href=\"/GraphicalView.do?method=deleteView&fromWhere=hometab&haid=\"+haid_flash+\"&viewid=\"+selectedViewId;\n\tvar dataString = \"method=deleteView&fromWhere=hometab&haid=\"+haid_flash+\"&viewid=\"+selectedViewId+\"&isHtml=\"+true; //No I18N    \n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                {\n                    viewid=-1;\n                \t$('#BVViewer').load('/jsp/businessServiceGraphTemplate.jsp?isReadOnly=false&displayAvailableViews=true&haid=0&selectedViewId=0');//No I18N\n                }\n\t   });\n\t   }\n\t\n}\nfunction resetDesign(){\n\tif(haid_flash=='0' && selectedViewId=='0')\n\t{\n\t\tif($('#availableViews option').length=='1')\n\t\t{\n\t\t\talert('");
/* 156 */       out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 157 */       out.write("');\n\t\t\treturn;\n\t\t}\n\t\talert('");
/* 158 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 159 */       out.write("');\n\t\treturn;\n\t}\n\tvar dataString = \"method=resetDesign&haid=\"+haid_flash+\"&viewid=\"+selectedViewId+\"&isHtml=\"+true; //No I18N    \n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n                success: function(response)\n                {\n\t\t\t\t $('#infovis-canvaswidget').remove(); //No I18N\n\t\t\t\t backgroundDisplayColor = defaultDisplayProps.backgroundDisplayColor,\t\t//No I18N\n\t\t\t\t edgeLineDisplayColor = defaultDisplayProps.edgeLineDisplayColor,\t\t\t\t//No I18N\n\t\t\t\t textDisplayColour = defaultDisplayProps.textDisplayColour,\n\t\t\t\t graphLineThickness = defaultDisplayProps.graphLineThickness,\n\t\t\t\t graphLineTransparency = defaultDisplayProps.graphLineTransparency,\n\t\t\t\t canvasX = defaultDisplayProps.canvasX,\n\t\t\t\t canvasY = defaultDisplayProps.canvasY;\n\t\t\t\t saveFlag = 0;\n\t\t\t\t viewid = -1;\n                 nodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\n\t\t\t\t st = initJITSpaceTree(response, 'infovis', true, '#infovis', saveFlag);\t//No I18N\n");
/* 160 */       out.write("\t\t\t\t setInfovisWidth();\n\t\t\t     $('#infovis').css(\"height\", $(window).height());// No I18N\n\t\t\t\t cancelSettingsForGraph();\n                }\n    });\n}\n\n\nfunction setInfovisWidth(){\n\tif(haid_flash == 0){\n\t\t$('#infovis').css(\"width\", \"auto\");//No I18N\n\t}else{\t\n\t\t$('#infovis').css(\"width\", $(window).width() * (97/100)+\"px\");\t// No I18N\n\t}\n}\n\nfunction showExternalLink()\n{\n\tif(haid_flash=='0' && selectedViewId=='0')\n\t{\n\t\tif($('#availableViews option').length=='1')\n\t\t{\n\t\t\talert('");
/* 161 */       out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 162 */       out.write("');\n\t\t\treturn;\n\t\t}\n\talert('");
/* 163 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 164 */       out.write("');\n\treturn;\n\t}\n\telse if(selectedViewId=='0')\n\t\t{\n\t\talert('");
/* 165 */       out.print(FormatUtil.getString("am.webclient.create.newview.monitorgroup.msg"));
/* 166 */       out.write("');\n\t\treturn;\n\t\t}\n$('#publishDiv').show();\ndocument.getElementById(\"txtbx\").innerHTML='<textarea name=\"extlink\" rows=\"4\" cols=\"50\">&lt;iframe src=\"'+'");
/* 167 */       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */         return;
/* 169 */       out.write("'+'://'+'");
/* 170 */       if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*     */         return;
/* 172 */       out.write("'+':'+'");
/* 173 */       if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*     */         return;
/* 175 */       out.write("'+'/GraphicalView.do?publishview=true&amp;method=popUp&amp;haid='+haid_flash+'&viewid='+selectedViewId+'\" scrolling=\"no\" align=\"center\" height=\"400\" width=\"700\" border=\"0\" frameborder=\"0\"&gt; &lt;/iframe&gt;</textarea>'\n}\n\nfunction showExternalReadOnlyLink()\n{\n\tif(haid_flash=='0' && selectedViewId=='0')\n\t{\n\t\tif($('#availableViews option').length=='1')\n\t\t{\n\t\t\talert('");
/* 176 */       out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 177 */       out.write("');\n\t\t\treturn;\n\t\t}\n\talert('");
/* 178 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 179 */       out.write("');\n\treturn;\n\t}\n\telse if(selectedViewId=='0')\n\t\t{\n\t\talert('");
/* 180 */       out.print(FormatUtil.getString("am.webclient.create.newview.monitorgroup.msg"));
/* 181 */       out.write("');\n\t\treturn;\n\t\t}\n$('#publishDiv').show();\ndocument.getElementById(\"txtbx\").innerHTML='<textarea id=\"publishText\" name=\"extlink\" rows=\"4\" cols=\"50\">&lt;iframe src=\"'+'");
/* 182 */       if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*     */         return;
/* 184 */       out.write("'+'://'+'");
/* 185 */       if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*     */         return;
/* 187 */       out.write("'+':'+'");
/* 188 */       if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*     */         return;
/* 190 */       out.write("'+'/GraphicalView.do?publishview=true&amp;readOnly=true&amp;method=popUp&amp;haid='+haid_flash+'&viewid='+selectedViewId+'\" scrolling=\"no\" align=\"center\" height=\"400\" width=\"700\" border=\"0\" frameborder=\"0\"&gt; &lt;/iframe&gt;</textarea>'\n}\nfunction performOperationsAfterTemplateLoaded(){\n\tif(haid_flash == 0){\n\t//Making display name field as readOnly in ViewSettings->Display Properties.\n\t\t//$(\"#display_name\").attr(\"readonly\", false);//No I18N \n\t\tvar toAppend = \"<div class='form-group'><div class='checkbox'><label><input id='editShowOnlyStatus' type='checkbox' name='' onclick='togglePreview()'></input>\"+\"");
/* 191 */       out.print(FormatUtil.getString("am.webclient.flashview.show.mgonly.text"));
/* 192 */       out.write("\"+\"</label> </div></div>\";\n\t\t$('#graphChangeOptions').append(toAppend);\n\t\tsetInfovisWidth();//No I18N\n \t    $('#infovis').css(\"height\", $(window).height()+\"px\");//No I18N\n\t}\n\tnodeImplementType = 'rectangleForBVWithSavedPos';\t//No I18N\t\n\tinitPage(haid_flash);\n\t$('#infovis').mouseenter(function(){isHover = true;}).mouseleave(function(){isHover = false;});\n\tsetTimeout(\"scheduleRefresh()\",refreshInterval);\n}\n\n\nfunction scheduleRefresh(){\n\tsetRecentStatusForJIT(haid_flash, viewid);\n\tsetTimeout(\"scheduleRefresh()\",refreshInterval);\n}\n\n\nfunction togglePreview(){\n\t$('#graphPreviewDIV').toggle();\n}\n\nfunction cancelSettingsForGraph(){\n\tvar graph = getDefaultGraphForPreview();\n\tnodeImplementType = 'rectangleForBV';\t\t// No I18N\n\t$('#infovisTest-canvaswidget').remove(); //No I18N\n\tbusinessServiceTree = initJITSpaceTree(graph, 'infovisTest', false, '#graphPreviewDIV', false);\t//No I18N\n}\n\n/** This method unassociates a monitor - Used for delete \n\ticons in BUsiness view **/\n\nfunction removeMonitorsFromBV(monitorResourceId, haid, nodeId){\n");
/* 193 */       out.write("\tif(!confirm('");
/* 194 */       out.print(FormatUtil.getString("am.webclient.monitortype.jsalertforremovemg.text"));
/* 195 */       out.write("')){\n\t\treturn;\t\n\t}\n\tremoveNodeFromGraph(nodeId);\n\tvar dataString = \"method=removeMonitorsInBV&haid=\"+haid+\"&viewid=\"+viewid+\"&resourceId=\"+monitorResourceId; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n\t\t           success: function(response)\n\t\t           {\n\t\t\t\t\t\tnodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\t\t\t\t\t\t\n\t\t\t\t\t\tsetInfovisWidth();\t\t\t\t\t\t\n\t\t\t     \t\t$('#infovis').css(\"height\", $(window).height());// No I18N\n\t\t           }\n    });\n}\n\nfunction removeGroupFromBV(groupResourceId, nodeId){\n\tvar isGroup = groupResourceId == haid_flash;\n\tvar message = \"");
/* 196 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.delete.confirm.text"));
/* 197 */       out.write("\";\n\tif(isGroup){\n\t\tmessage = \"");
/* 198 */       out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletemg.text"));
/* 199 */       out.write("\";\n\t}\t\n\tif(!confirm(message)){\n\t\treturn;\t\n\t}\n\tdeleteNode(nodeId);\n\tvar dataString = \"method=removeGroupInBV&haid=\"+groupResourceId; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n\t\t           success: function(response)\n\t\t           {\n\t\t\t\t\t\tif(isGroup){\n\t\t\t\t\t\t\tdocument.location.href = \"/MyPage.do?method=viewDashBoard&toredirect=true\";\n\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\tnodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\t\t\t\t\t\t\n\t\t\t\t\t\t\tsetInfovisWidth();\t\t\t\t\t\t\n\t\t\t\t\t\t\t$('#infovis').css(\"height\", $(window).height());// No I18N\n\t\t\t\t\t\t}\n\t\t           }\n    });\n}\n\nfunction editGroupNode(resId, nodeId){\n\t$(\"#dialogAddForEdit\").dialog({\n\t\tmodal   : true, \n\t\theight  : 550, \n\t\twidth   : 1010,\n\t\tshow    : {effect: 'drop', direction: \"left\", duration: 500},\t\t\t\t\t\t\t\t\t\t// No I18N\n\t    hide    : {effect: 'drop', direction: \"right\", duration: 500},\t\t\t\t\t\t\t\t\t\t// No I18N\n\t    dialogClass : 'dialogCustom dialogAdd',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n");
/* 200 */       out.write("\t    open: function() {\n\t\t\t$(this).load('/jsp/addToBusinessServiceGraph.jsp?editAction=true&isBusinessView=true&nodeId='+nodeId+'&resId='+resId);//No I18N\n\t\t}\n\t});\n}\n\nfunction showGroupInfoForEdit(resId, currentNode){\n\tvar subGroupName = $('#customMonGroup').html();\n\t$(\"#editGraphSelectedType\").html(subGroupName);\n\tshowSubGroupDivs();\n\t$('#associateMonDiv').hide();\n\tvar dataString = \"method=editGroupForBV&haid=\"+resId; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t           success: function(response)\n\t           {\n\t\t\t\t setEditPage(response);\n\t           }\n    });\n}\n\nfunction setEditPage(resObj){\n\t$('#subGroupName').val(resObj[\"name\"]);\n\t$('#Description').val(resObj[\"description\"]);\n\tvar locationid = resObj[\"locationid\"];\n\t$('#locationid').val(locationid);\n\tvar allowners = {};\n\tvar selectedOwners = {};\n\tallowners = resObj[\"allowners\"];\n\tshowOwners(allowners);\n\tselectedOwners = resObj[\"selectedowners\"];\n");
/* 201 */       out.write("\tshowSelectedOwners(selectedOwners);\n}\n\n  // dialog add - Opened for edit div\nfunction callEditActionForGraph(parentNodeId) {\n\t $(\"#dialogAddForEdit\").dialog({\n        modal   : true, \n        height  : 550, \n        width   : 1010,\n        show    : {effect: 'drop', direction: \"left\", duration: 500},\t\t\t\t\t\t\t\t\t\t// No I18N\n        hide    : {effect: 'drop', direction: \"right\", duration: 500},\t\t\t\t\t\t\t\t\t\t// No I18N\n        dialogClass : 'dialogCustom dialogAdd',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n\t   \t open: function() {\n\t\t\t$(this).load('/jsp/addToBusinessServiceGraph.jsp?editAction=false&isBusinessView=true&nodeId='+parentNodeId);//No I18N\n\t   \t }\n      });\n}\n\nfunction addDetailsToGraph(type){\n\t/* type 1- Associate Monitor Group\n\t\t2 - Add custom Subgroup  3- add dependent group */\n     var subGroupName=$('#subGroupName').val();\n\n\tif(!validateForSubGroups(subGroupName, type)){\n\t\t$('.addCreate').animate({\n        scrollTop: $('#subGroupName').offset().top-500\n\t\t},{\n       complete : function(){ $('#subGroupName').focus();}\n\t\t}, 500);\n");
/* 202 */       out.write("\t\treturn ;\n\t}\n\tif(type === '1'){\t\t\n\t\tvar monDetails = getMonitorList(editNodeParentId);\n\t\tcurrentNode = st.graph.getNode(editNodeParentId);\n\t\tvar haid = currentNode.getData(\"resourceID\");//No I18N\t\t\t\t\n\t\tassociateMonitorToBV(editNodeParentId, monDetails, haid);\n\t}else if(type === '2'){\n\t\taddCustomSubgroupToBV(editNodeParentId, SUBGROUPTYPE, subGroupName, \"");
/* 203 */       out.print(FormatUtil.getString("am.webclient.reports.excel.subgroup.text"));
/* 204 */       out.write("\");\n\t}else if(type === '3'){\n\t\taddDependentGroupToBV(editNodeParentId);\n\t}else{\n\t\tvar groupTypeName = $('#editGraphSelectedType').text();\n\t\taddCustomSubgroupToBV(editNodeParentId, type, subGroupName, groupTypeName);\n\t}\n}\n\nfunction getMonitorList(){\n\tvar monDetails = {};\n\tvar monList = [];\n\tvar healthKeySet = [];\n\tvar typeList = [];\n\tvar displayNameList = [];\n\tvar imagePathList = [];\n\t$('#monitorListToBeAssociated input:checked').each(function( index ) {\n\t//$(\"#monitorListAssociated li\").each(function( index ) {\n\t\tvar liParent = $(this).parent().parent();\n\t\tvar resourceId = $(this).val();\n\t\tdisplayNameList[index] = $(this).parent().text();\t\t\n\t\tmonList[index] = resourceId;\n\t\thealthKeySet[index] = $(this).parent().parent().val();\t\t\n\t\ttypeList[index] = $('#typeOfMonitor_'+resourceId).attr(\"value\");//No I18N\n\t\timagePathList[index] = $('#typeOfMonitor_'+resourceId+' img').attr(\"src\");//No I18N\n \t});\t\n\tmonList = monList.join(\",\");\n\thealthKeySet = healthKeySet.join(\",\");\n\tmonDetails['monList']=monList;\n\tmonDetails['healthKeySet']=healthKeySet;\n");
/* 205 */       out.write("\tmonDetails['typeList']=typeList;\n\tmonDetails['displayNameList']=displayNameList;\n\tmonDetails['imagePathList']=imagePathList;\n\treturn monDetails;\n}\n\nfunction associateMonitorToBV(associateToNodeId, monList, associateToHaid){\n\tvar mList = monList['monList'];\n\tvar healthKeyList = monList['healthKeySet'];\n\tvar typeList = monList['typeList'];\n\tvar dispNameList = monList['displayNameList'];\n\tvar imagePathList = monList['imagePathList'];\n\tvar dataString = \"method=associateMonitorToBVAction&haid=\"+associateToHaid+\"&monList=\"+mList+\"&healthKeySet=\"+healthKeyList+\"&typeList=\"+typeList+\"&displayNameList=\"+dispNameList+\"&imagePathList=\"+imagePathList+\"&nodeId=\"+associateToNodeId+\"&nextNodeId=\"+idForGraph+\"&nodeIdentifier=\"+nodeIdentifier; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t           success: function(response){\n\t\t\t\tvar success = response['success'];\n\t\t\t\tif(success){\n\t\t\t\t\tidForGraph = response['nextNodeId'];\t\t\t\t\t\n");
/* 206 */       out.write("\t\t\t\t\tst.addSubtree(response['subTree'], 'nothing', {//No I18N\n\t\t\t\t\t\t onComplete: function() {\n\t\t\t\t\t\t\tsetElementAccordingToBackground(backgroundDisplayColor, textDisplayColour);\n\t\t\t\t\t\t }\n\t\t      \t\t});\n\t\t\t\t}\n\t           }\n    });\n}\n\nfunction addCustomSubgroupToBV(editNodeParentId, type, subGroupName, groupTypeName){\n\tcurrentNode = st.graph.getNode(editNodeParentId);\n\tvar pos = currentNode.pos.getc();\n\tvar haid = currentNode.getData(\"resourceID\");//No I18N\n\tvar monDetails =  getMonitorList();\n\tvar dataForNode={};\n\tvar descriptionText=$('#Description').val();\n\tvar owners=getSelectedOwners();\n\tvar locationid = $('#locationid').val();\n\tvar subGroupId = nodeIdentifier+idForGraph;\n\tdataForNode={$description:descriptionText,$nodeType:SUBGROUPTYPE,$groupType:type,$displayName:subGroupName,$parentNodeID:editNodeParentId, $message:getMessageForTooltip(subGroupName, groupTypeName),$owners:owners,$locationid:locationid,$nodeX:'default',$nodeY:'default',$parentNodeX:pos.x, $parentNodeY:pos.y};//No I18N\n\tvar dataString = \"method=createSubGroupTreeToBVAction&haid=\"+haid+\"&subGroupData=\"+JSON.stringify(dataForNode)+\"&type=HAI\"+\"&nodeId=\"+subGroupId; //No I18N\n");
/* 207 */       out.write("\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t           success: function(response){\n\t\t\t\t\tvar groupNode={};\n\t\t\t\t\tvar addNode = {};\n\t\t\t\t\tvar displayName = response['groupDivNode'];\n\t\t\t\t\tidForGraph++;\t\t\t\t\t\n\t\t\t\t\thaid = response['createdHaid'];\n\t\t\t\t\tdataForNode['$resourceID']=haid;//No I18N\n\t\t\t\t\tgroupNode={id:editNodeParentId, children:[{id:subGroupId,name:displayName, data:dataForNode,children:[]}]};\n\t\t\t\t\tst.addSubtree(groupNode, 'nothing', {//No I18N\n\t\t\t\t\t\t onComplete: function() {\n\t\t\t\t\t\t\tsetElementAccordingToBackground(backgroundDisplayColor, textDisplayColour);\n\t\t\t\t\t\t }\n\t\t\t\t\t});\n\t\t\t\t\thaid = response['createdHaid'];\n\t\t\t\t\tassociateMonitorToBV(subGroupId, monDetails, haid);\n\t           }\n    });\n    \n}\n\nfunction addDependentGroupToBV(id){\n\tcurrentNode = st.graph.getNode(editNodeParentId);\n\tvar haid = currentNode.getData(\"resourceID\");//No I18N\n\tvar i=0;\n\tvar nodeList = [];\n\tvar haidDispNameList = [];\n");
/* 208 */       out.write("\t$(\"#allMonitorGroups input[type=checkbox]\").each(function(index ) {\n\t\tvar ele = $(this);\n\t\tvar value = ele.val();\n\t\tisChecked = ele.is(':checked');\t//No I18N\n\t\tif(isChecked){\n\t\t\tvalue = ele.val();\n\t\t\tnodeList[i] = value;\n\t\t\thaidDispNameList[i] = ele.parent().children().next().val();\n\t\t\ti++;\n\t\t}\n\t});\n\tnodeList = nodeList.join(\",\");\n\tvar dataString = \"method=createDependentGroupBVAction&haid=\"+haid+\"&dependentList=\"+nodeList+\"&nodeId=\"+editNodeParentId+\"&nextNodeId=\"+idForGraph+\"&nodeIdentifier=\"+nodeIdentifier+\"&haidDispNameList=\"+haidDispNameList; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t           success: function(response){\n\t\t\t\tvar success = response['message'];//No I18N\n\t\t\t\tif(success){\n\t\t\t\t\tidForGraph = response['nextNodeId'];\n\t\t\t\t\tst.addSubtree(response['subTree'], 'nothing', {//No I18N\n\t\t\t\t\t\t onComplete: function() {\n\t\t\t\t\t\t\tsetElementAccordingToBackground(backgroundDisplayColor, textDisplayColour);\n");
/* 209 */       out.write("\t\t\t\t\t\t }\n\t\t      \t\t});\n\t\t\t\t}\n\t           }\n    });\n}\n\n</script>\n\n</head>\n<body onload=\"\" >\n\n<!--<div class=\"filterMon\">\n\t\t\t\t\t\t\t<ul>\n\t\t\t\t\t\t\t\t<li>\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" onChange=\"javascript:changeGraphAsPerType();\" id=\"showCritical\"><label>show Critical</label>\n\t\t\t\t\t\t\t\t\t <input type=\"radio\" onChange=\"javascript:changeGraphAsPerType();\" value=\"1\" id=\n\"showTopLevel\" name=\"hirrarchy\"><label>Top Level</label>\n\t\t\t\t\t\t\t\t\t <input type=\"radio\" onChange=\"javascript:changeGraphAsPerType();\" value=\"2\" id=\n\"showSubgroups\" name=\"hirrarchy\"><label>Hide Monitors</label>\n           <input type=\"radio\" onChange=\"javascript:changeGraphAsPerType();\" value=\"0\" id=\n\"all\" name=\"hirrarchy\"><label>all</label></li>   </ul></div>-->\n\n<div  id=\"publishDiv\" class='boxCon publishViewBlock hidden'>\n\t<div class=\"boxHead\">");
/* 210 */       out.print(FormatUtil.getString("am.webclient.flashview.makepublic.text"));
/* 211 */       out.write("\n\t\t<a href=\"#\" class=\"btnClose\" id='publishClose'>X</a> ");
/* 212 */       out.write("\n\t</div>\n\t<div class=\"conInner\" id=\"txtbx\"></div>\n</div>\n<div id=\"BVViewer\"></div>\n\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\" style=\"display:none\"><img src=\"/images/arrow2.gif\"></div>\n<script>\nvar jsAtt = ");
/* 213 */       if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*     */         return;
/* 215 */       out.write(";\nvar displayAvailableViews=");
/* 216 */       if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*     */         return;
/* 218 */       out.write(";\n//var bvName= '");
/* 219 */       if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*     */         return;
/* 221 */       out.write("';\nvar haid= '");
/* 222 */       if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*     */         return;
/* 224 */       out.write("';\nvar viewid='");
/* 225 */       if (_jspx_meth_c_005fout_005f13(_jspx_page_context))
/*     */         return;
/* 227 */       out.write(39);
/* 228 */       out.write(59);
/* 229 */       out.write(10);
/* 230 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 232 */       out.write(10);
/* 233 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 235 */       out.write("\n$('#publishClose').click(function(){\n\t$('#publishDiv').hide();\n});\n</script>\n</body>\n\n</html>\n");
/*     */     } catch (Throwable t) {
/* 237 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 238 */         out = _jspx_out;
/* 239 */         if ((out != null) && (out.getBufferSize() != 0))
/* 240 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 241 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 244 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 250 */     PageContext pageContext = _jspx_page_context;
/* 251 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 253 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 254 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 255 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 257 */     _jspx_th_c_005fout_005f0.setValue("${FlashForm.xcanvas}");
/* 258 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 259 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 261 */       return true;
/*     */     }
/* 263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 264 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 269 */     PageContext pageContext = _jspx_page_context;
/* 270 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 272 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 273 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 274 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 276 */     _jspx_th_c_005fout_005f1.setValue("${FlashForm.ycanvas}");
/* 277 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 278 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 280 */       return true;
/*     */     }
/* 282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 288 */     PageContext pageContext = _jspx_page_context;
/* 289 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 291 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 292 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 293 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 295 */     _jspx_th_c_005fout_005f2.setValue("${ FlashForm.showCriticalMonitors}");
/* 296 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 297 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 299 */       return true;
/*     */     }
/* 301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 307 */     PageContext pageContext = _jspx_page_context;
/* 308 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 310 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 311 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 312 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 314 */     _jspx_th_c_005fout_005f3.setValue("${pageContext.request.scheme}");
/* 315 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 316 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 318 */       return true;
/*     */     }
/* 320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 321 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 326 */     PageContext pageContext = _jspx_page_context;
/* 327 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 329 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 330 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 331 */     _jspx_th_c_005fout_005f4.setParent(null);
/*     */     
/* 333 */     _jspx_th_c_005fout_005f4.setValue("${pageContext.request.serverName}");
/* 334 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 335 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 337 */       return true;
/*     */     }
/* 339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 340 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 345 */     PageContext pageContext = _jspx_page_context;
/* 346 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 348 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 349 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 350 */     _jspx_th_c_005fout_005f5.setParent(null);
/*     */     
/* 352 */     _jspx_th_c_005fout_005f5.setValue("${pageContext.request.serverPort}");
/* 353 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 354 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 356 */       return true;
/*     */     }
/* 358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 359 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 364 */     PageContext pageContext = _jspx_page_context;
/* 365 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 367 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 368 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 369 */     _jspx_th_c_005fout_005f6.setParent(null);
/*     */     
/* 371 */     _jspx_th_c_005fout_005f6.setValue("${pageContext.request.scheme}");
/* 372 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 373 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 375 */       return true;
/*     */     }
/* 377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 378 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 383 */     PageContext pageContext = _jspx_page_context;
/* 384 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 386 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 387 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 388 */     _jspx_th_c_005fout_005f7.setParent(null);
/*     */     
/* 390 */     _jspx_th_c_005fout_005f7.setValue("${pageContext.request.serverName}");
/* 391 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 392 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 394 */       return true;
/*     */     }
/* 396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 397 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 402 */     PageContext pageContext = _jspx_page_context;
/* 403 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 405 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 406 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 407 */     _jspx_th_c_005fout_005f8.setParent(null);
/*     */     
/* 409 */     _jspx_th_c_005fout_005f8.setValue("${pageContext.request.serverPort}");
/* 410 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 411 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 412 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 413 */       return true;
/*     */     }
/* 415 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 416 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 421 */     PageContext pageContext = _jspx_page_context;
/* 422 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 424 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 425 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 426 */     _jspx_th_c_005fout_005f9.setParent(null);
/*     */     
/* 428 */     _jspx_th_c_005fout_005f9.setValue("${isReadOnly}");
/* 429 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 430 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 432 */       return true;
/*     */     }
/* 434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 435 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 440 */     PageContext pageContext = _jspx_page_context;
/* 441 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 443 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 444 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 445 */     _jspx_th_c_005fout_005f10.setParent(null);
/*     */     
/* 447 */     _jspx_th_c_005fout_005f10.setValue("${displayAvailableViews}");
/* 448 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 449 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 451 */       return true;
/*     */     }
/* 453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 454 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 459 */     PageContext pageContext = _jspx_page_context;
/* 460 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 462 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 463 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 464 */     _jspx_th_c_005fout_005f11.setParent(null);
/*     */     
/* 466 */     _jspx_th_c_005fout_005f11.setValue("${bvName}");
/* 467 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 468 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 469 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 470 */       return true;
/*     */     }
/* 472 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 473 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 478 */     PageContext pageContext = _jspx_page_context;
/* 479 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 481 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 482 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 483 */     _jspx_th_c_005fout_005f12.setParent(null);
/*     */     
/* 485 */     _jspx_th_c_005fout_005f12.setValue("${haid}");
/* 486 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 487 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 488 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 489 */       return true;
/*     */     }
/* 491 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 492 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f13(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 497 */     PageContext pageContext = _jspx_page_context;
/* 498 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 500 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 501 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 502 */     _jspx_th_c_005fout_005f13.setParent(null);
/*     */     
/* 504 */     _jspx_th_c_005fout_005f13.setValue("${viewid}");
/* 505 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 506 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 507 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 508 */       return true;
/*     */     }
/* 510 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 511 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 516 */     PageContext pageContext = _jspx_page_context;
/* 517 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 519 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 520 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 521 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 523 */     _jspx_th_c_005fif_005f0.setTest("${FlashForm.showTopLevelMgs=='true'}");
/* 524 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 525 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 527 */         out.write("\n$('#BVViewer').load('/jsp/businessServiceGlobalView.jsp?isReadOnly='+jsAtt+'&displayAvailableViews='+displayAvailableViews+'&haid='+haid_flash+'&selectedViewId='+viewid);\t\t //No I18N\n");
/* 528 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 529 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 533 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 534 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 535 */       return true;
/*     */     }
/* 537 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 538 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 543 */     PageContext pageContext = _jspx_page_context;
/* 544 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 546 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 547 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 548 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 550 */     _jspx_th_c_005fif_005f1.setTest("${FlashForm.showTopLevelMgs=='false'}");
/* 551 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 552 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 554 */         out.write("\n//alert(\"graph\");\nif(viewid==-1)\n\t{\n$('#BVViewer').load('/jsp/businessServiceGraphTemplate.jsp?isReadOnly='+jsAtt+'&displayAvailableViews='+displayAvailableViews+'&haid='+haid_flash+'&selectedViewId=0');//No I18N\n\t}\nelse\n\t{\n\t$('#BVViewer').load('/jsp/businessServiceGraphTemplate.jsp?isReadOnly='+jsAtt+'&displayAvailableViews='+displayAvailableViews+'&haid='+haid_flash+'&selectedViewId='+viewid);//No I18N\n\t}\n");
/* 555 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 556 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 560 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 561 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 562 */       return true;
/*     */     }
/* 564 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 565 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowRelationshipView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */