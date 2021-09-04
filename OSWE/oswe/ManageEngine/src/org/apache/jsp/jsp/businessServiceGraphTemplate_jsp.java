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
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class businessServiceGraphTemplate_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  50 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  53 */     JspWriter out = null;
/*  54 */     Object page = this;
/*  55 */     JspWriter _jspx_out = null;
/*  56 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  60 */       response.setContentType("application/json; charset=UTF-8");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<!--$Id$-->\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n   <html>\n      <head>\n         <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />         \n\t    <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n\t\t\n         <script type='text/javascript' src='../template/graphUtil.js'></script>\n         <script type='text/javascript' src='../template/jitSpaceTree.js'></script>\n         <script type='text/javascript'>\n\t\t\tvar nodeIdentifier = \"node_\"; //No I18N\n\n\n\t\tfunction getAllDisplayProps(){\n\t\t\tvar displayProps = {};\n\t\t\tvar parentWidth = $('#infovis').width();\t\n\t\t\tvar parentHeight = $('#infovis').height();\n\t\t\tdisplayProps.showLabel=$('#editShowOnlyLabel').is(\":checked\");// No I18N\n\t\t\tdisplayProps.showOnlyMGs=$('#editShowOnlyMGs').is(\":checked\");// No I18N\n\t\t\tdisplayProps.showOnlyTopMGs=$('#editShowOnlyTopMGs').is(\":checked\");// No I18N\n\t\t\tdisplayProps.showOnlyCritical=$('#editShowOnlyCriticalMonitors').is(\":checked\");// No I18N\n");
/*  71 */       out.write("\t\t\tdisplayProps.showOnlyMGStatus=$('#editShowOnlyMGStatus').is(\":checked\");// No I18N\n\t\t\tdisplayProps.backgroundColorVal = $('#picker').val();\n           \tdisplayProps.lineColorVal = $('#pickerLine').val();\n          \tdisplayProps.textColorVal = $('#pickerText').val();\n\t\t\tdisplayProps.lineThickness = $('#editLineThicker').attr('value');\t// No I18N\n\t\t\tdisplayProps.lineTransparency = $( \"#amount\" ).attr( 'transparent-value')/100;\t\t// No I18N\n\t\t\tvar pos = getCanvasPos();\n\t\t\tdisplayProps.xCanvas = ((pos.x/parentWidth) * 100);\n\t\t\tdisplayProps.yCanvas =(( pos.y/ parentHeight) * 100);\n\t\t\treturn displayProps;\n\t\t}\n\n\t\tfunction setGraphAccordingToConditions(){\n\t\t\tvar displayProps = getAllDisplayProps();\n\t\t\tvar previewTree = getPreview();\n\t\t\t     previewTree.graph.eachNode(function(node) { \n\t\t\t\t\tvar status =  node.getData('status');\t//No I18N\n\t\t\t\t\tvar level =  node._depth;\n\t\t\t\t\tvar type = node.getData('nodeType');//No I18N\n\t\t\t\t\tvar toAdd = checkwhetherToAdd(displayProps, status, level, type);\n\t\t\t\t\tif(toAdd){\n\t\t\t\t\t\tnode.setData('alpha', 1);//No I18N\n");
/*  72 */       out.write("\t\t\t\t\t}else{\n\t\t\t\t\t\tnode.setData('alpha', 0);//No I18N\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\tpreviewTree.plot();\n\t\t}\n\t\tfunction loadAvailableViews(selectedViewId)\n\t\t{\n\t\t\tvar dataString = \"method=getAvailableViews&haid=\"+haid; //No I18N\n\t\t\t$.ajax({\n\t\t                type:\"POST\", //No I18N\n\t\t                url:\"/GraphicalView.do\", //No I18N\n\t\t                data:dataString,\n\t\t\t\t\t\tdataType: \"json\",//No I18N\n\t\t\t\t           success: function(response)\n\t\t\t\t           {\n\t\t\t\t        \t   var views = response['views'];\n\t\t\t\t        \t   var dispNames=response['dispNameList'];\n\t\t\t\t        \t   for(var i in dispNames)\n\t\t\t\t        \t   {\n\t\t\t\t        \t\t   // console.log(\"i is:\"+dispNames[i]);\n\t\t\t\t        \t\t     var val = views[dispNames[i]];\n\t\t\t\t        \t\t     $('#availableViews').append($(\"<option></option>\").attr(\"value\",val).text(decodeURI(dispNames[i]))); \n\t\t\t\t        \t   }  \n\t\t\t\t        \t   $('#availableViews').val(selectedViewId);\n\t\t\t\t           }\n\t\t    });\n\t\t\t\n\t\t\t\n\t\t}\n\t\tfunction redirectToSelectedView()\n\t\t{   \n\t\t\t\n\t\t\tif($('select[id=availableViews]').val()=='0')\n");
/*  73 */       out.write("\t\t\t{\n\t\t\t\talert('");
/*  74 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/*  75 */       out.write("');\n\t\t    \t $('#availableViews').val(selectedViewId);\n\t        \t  return;\n\t\t    }\n\t\t\t\n              selectedViewId=$('select[id=availableViews]').val();\n            //  if(selectedViewId!='0')\n            \t//  {\n            \t  var dataString = \"method=getMGXML&haid=\"+haid+\"&viewid=\"+selectedViewId+\"&currentime=\"+");
/*  76 */       out.print(System.currentTimeMillis());
/*  77 */       out.write("+\"&isHtml=true\"; //No I18N    \n                  $.ajax({\n                              type:\"POST\", //No I18N\n                              url:\"/GraphicalView.do\", //No I18N\n                              data:dataString,\n                                dataType: \"json\",//No I18N\n                              success: function(response)\n                              {  \n                                  $('#infovis-canvaswidget').remove(); //No I18N\n                                  if(response['SHOWTOPLEVELMGS']=='true')\n                                \t  {\n                                      \t  $('#BVViewer').load('/jsp/businessServiceGlobalView.jsp?isReadOnly=false&displayAvailableViews='+displayAvailableViews+'&haid='+haid+'&selectedViewId='+selectedViewId);//No I18N\n                                \t  }\n                                  else\n                                \t  {\n                                  saveFlag = response['saveFlag'];\n                                  if(saveFlag != 0){\n                                      setDisplayPropsForGraph(response['backgroundColorVal'], response['lineColorVal'], response['textColorVal'],response['lineThickness'],response['lineTransparency']);\n");
/*  78 */       out.write("                                      nodeImplementType = 'rectangleForBVWithSavedPos'; //No I18N\n                                  }\n                                  canvasX = response['xCanvas'];\n                                  canvasY = response['yCanvas'];\n                                 \n                                  st = initJITSpaceTree(response, 'infovis', true, '#infovis',saveFlag);    //No I18N\n                                  $('#progressSpanForCBV').hide();\n                                  \n                                }\n                              }\n                  });\n           // }\n             \n\t\t\n\t\t}\n\t\t\n\t\tfunction checkwhetherToAdd(displayProps, status, level, type){\n\t\t\tvar topMGCondition = displayProps.showOnlyTopMGs && (!(level == 1 || level==0)  || (type == 'monitor'));\t//No I18N\n\t\t\tvar mgCondition = displayProps.showOnlyMGs && (type == 'monitor');\t\t//No I18N\n\t\t\tif(!displayProps.showOnlyTopMGs && !displayProps.showOnlyMGs && !displayProps.showOnlyCritical){\n\t\t\t\treturn true;\n");
/*  79 */       out.write("\t\t\t}\n\t\t\tif(displayProps.showOnlyCritical){\n\t\t\t\tif(status == \"stsUp\"  || topMGCondition ||  mgCondition){\n\t\t\t\t\treturn false;\t\t\t\t\n\t\t\t\t}else{\n\t\t\t\t\treturn true;\n\t\t\t\t}\n\t\t\t}else if(displayProps.showOnlyTopMGs){\n\t\t\t\tif(topMGCondition){\n\t\t\t\t\treturn false;\n\t\t\t\t}else{\t\n\t\t\t\t\treturn true;\n\t\t\t\t}\n\t\t\t}else if(displayProps.showOnlyMGs){\n\t\t\t\tif(mgCondition){\n\t\t\t\t\treturn false;\n\t\t\t\t}else{\n\t\t\t\t\treturn true;\n\t\t\t\t}\n\t\t\t}\n\t\t\treturn false;\n\t\t}\n         </script>\n      </head>\n      <body onload=\"\" >\n         <script>\n         var isReadOnly;\n         var selectedViewId;\n         var haid;\n         var displayAvailableViews;\n\t\t  $(document).ready(function(){\n\t\t\n\t\t\t  haid=");
/*  80 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write(";\n\t\t\t  selectedViewId='");
/*  83 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("';\n\t\t\t  loadAvailableViews(selectedViewId);\n\t\t\t  isReadOnly='");
/*  86 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/*  88 */       out.write("';\n\t\t\t  displayAvailableViews='");
/*  89 */       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */         return;
/*  91 */       out.write("';\n\t\t\t   ");
/*  92 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("\n\t\t       ");
/*  95 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/*  97 */       out.write("\n\t\t       ");
/*  98 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */         return;
/* 100 */       out.write("\n\t\t       $('#loader').hide();\n\t\t\t  //$('input:checkbox:checked').prop('checked',false);\t//No I18N\n\t\t\t  setOtherColourProperties();\n\t\t\t  $('#infovis').css(\"width\", $(window).width() * (97/100)+\"px\");\t// No I18N\n\t\t\t  $('#infovis').css(\"height\", $(window).height());// No I18N\n\t\t\t  performOperationsAfterTemplateLoaded();\n\t\t  });\n         </script>\n\n\n\n         <!-- Discovery Details -->\n         <div class=\"boxCon\">\n            <div class=\"boxHead\">\n               ");
/* 101 */       out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 102 */       out.write("  \n               <div id=\"viewSettings\" class=\"iconSettings listComponent\">\n                  ");
/* 103 */       out.print(FormatUtil.getString("view.settings"));
/* 104 */       out.write("\n                  <ul class=\"settingsDropdown slideBoxUp\" style=\"display:none;\">\n                     <li id=\"OpenDialogDisplay\">");
/* 105 */       out.print(FormatUtil.getString("am.webclient.flashview.displayprops.text"));
/* 106 */       out.write("</li>\t\n                     <li id=\"CreateView\" onclick=\"javascript:createNewView()\">");
/* 107 */       out.print(FormatUtil.getString("am.webclient.new.view.creation"));
/* 108 */       out.write("</a></li> \n                     <li id=\"EditView\" onclick=\"javascript:editView()\">");
/* 109 */       out.print(FormatUtil.getString("am.webclient.edit.view"));
/* 110 */       out.write("</a></li> \n                     <li id=\"DeleteView\" onclick=\"javascript:deleteView()\">");
/* 111 */       out.print(FormatUtil.getString("am.webclient.delete.view"));
/* 112 */       out.write("</a></li> \n                     <li onclick=\"javascript:resetDesign()\" id=\"reset_Design\">");
/* 113 */       out.print(FormatUtil.getString("am.webclient.flashview.resetdesign.text"));
/* 114 */       out.write("</li>\n                     <li onClick=\"showExternalLink()\" id=\"publish_view\">");
/* 115 */       out.print(FormatUtil.getString("am.webclient.flashview.makepublic.text"));
/* 116 */       out.write("</li>\n                     <li onClick=\"showExternalReadOnlyLink()\" id=\"readonly_publish_view\">");
/* 117 */       out.print(FormatUtil.getString("am.webclient.flashview.makepublic.readonly.text"));
/* 118 */       out.write("</li>\n                  </ul>\n               </div>\n               \n                 <select id=\"availableViews\" onchange=\"javascript:redirectToSelectedView()\">\n                 <option value=\"0\">---");
/* 119 */       out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 120 */       out.write("---</option>\n                 </select>\n                \n               \n            </div>\n            <!-- Graph Space-->\n          <div id=\"infovisContainer\" style=\"width:100%;height:100%\">\n\t\t <div id=\"infovis\">\n               <div class=\"zoomControl\">\n                  <div id=\"zoomIn\" class=\"zoomControls zoomIn\">+</div>\n                  <div id=\"zoomOut\" class=\"zoomControls zoomOut\">-</div>\n               </div>\n\t\t     <div class=\"loader\" id=\"progressSpanForCBV\"></div>\n            </div>\n\t\t</div>\n            <!-- Graph Space ends-->\n         </div>\n       \n         <!-- dialog display properties -->\n         <div id=\"dialogDisplayPro\" class=\"hidden\">\n            <h2 class=\"dialogHeading\"><span>");
/* 121 */       out.print(FormatUtil.getString("am.webclient.flashview.displayprops.text"));
/* 122 */       out.write("</span></h2>");
/* 123 */       out.write("\n            <a href=\"#\" id=\"btnCloseDisplay\" class=\"btnClose\">X</a> ");
/* 124 */       out.write("\n            <div class=\"popupCon\">\n               <div class=\"viewPropCon\" id=\"graphChangeOptions\">\n                  <div class=\"form-group\">\n                     <label for=\"display_name\" class=\"control-label\">");
/* 125 */       out.print(FormatUtil.getString("webclient.topo.objectdetails.displayname"));
/* 126 */       out.write("</label>\n                     <div class=\"inputBlock\">\n                        <input type=\"text\" class=\"form-control\" id=\"display_name\" placeholder=\"\" readonly>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <div class=\"checkbox\">\n                        <label>\n                        <input id=\"editShowOnlyLabel\" type=\"checkbox\" name=\"\" checked onclick=\"hideLabelForPreview()\"></input>\n                        ");
/* 127 */       out.print(FormatUtil.getString("am.webclient.flashview.showlabel.text"));
/* 128 */       out.write("</label>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <div class=\"checkbox\">\n                        <label>\n                        <input  id=\"editShowOnlyMGs\" type=\"checkbox\" name=\"monitorAndSubGroups\"></input>\n                        ");
/* 129 */       out.print(FormatUtil.getString("am.webclient.flashview.show.subgrouponly.text"));
/* 130 */       out.write("</label>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <div class=\"checkbox\">\n                        <label>\n                        <input id=\"editShowOnlyTopMGs\" type=\"checkbox\" name=\"topLevelSubGroup\">\n                        ");
/* 131 */       out.print(FormatUtil.getString("am.webclient.flashview.show.topsubgroup.text"));
/* 132 */       out.write("</label>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <div class=\"checkbox\">\n                        <label>\n                        <input id=\"editShowOnlyCriticalMonitors\" type=\"checkbox\" name=\"editShowOnlyCriticalMonitors\" >\n                        ");
/* 133 */       out.print(FormatUtil.getString("am.webclient.flashview.show.criticalonly.text"));
/* 134 */       out.write("</label>\n                     </div>\n                  </div>\n               </div>\n               <div class=\"displayPropCon\">\n                  <div class=\"form-group\">\n                     <label class=\"control-label\">");
/* 135 */       out.print(FormatUtil.getString("am.webclient.flashview.bgcolor.text"));
/* 136 */       out.write("</label>\n                     <div class=\"inputBlock\">\n                        <input type=\"text\" id=\"picker\" class=\"form-control\">\n                        <span class=\"changeColor\"></span>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <label class=\"control-label\">");
/* 137 */       out.print(FormatUtil.getString("am.webclient.flashview.linecolor.text"));
/* 138 */       out.write("</label>\n                     <div class=\"inputBlock\">\n                        <input type=\"text\" id=\"pickerLine\" class=\"form-control\">\n                        <span class=\"changeColorLine\"></span>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <label class=\"control-label\">");
/* 139 */       out.print(FormatUtil.getString("am.webclient.flashview.labelcolor.text"));
/* 140 */       out.write("</label>\n                     <div class=\"inputBlock\">\n                        <input type=\"text\" id=\"pickerText\" class=\"form-control\">\n                        <span class=\"changeColorText\"></span>\n                     </div>\n                  </div>\n                  <!-- <div class=\"form-group\">\n                     <label class=\"control-label\">Line Thickness</label>\n                     <div class=\"inputBlock\">\n                       <input type=\"text\" class=\"form-control\">\n                     </div>\n                     </div> -->\n                  <div class=\"form-group clearfix\">\n                     <label for=\"inputName\" class=\"control-label\">");
/* 141 */       out.print(FormatUtil.getString("am.webclient.flashview.linethickness.text"));
/* 142 */       out.write("</label>\n                     <div class=\"inputBlock\">\n                        <div class=\"lineThick component listComponent\">\n                           <span class=\"textValueLine\">1pt <i class=\"onePt\"></i></span>");
/* 143 */       out.write("\n                           <ul id=\"editLineThicker\" class=\"lineDropdown dropDown slideBoxUp\" style=\"display: none;\" value=\"2.5\">\n                              <li  data-value=\"2.5\">1pt <span class=\"onePt\"></span></li>");
/* 144 */       out.write("\n                              <li  data-value=\"5\">2pt <span class=\"twoPt\"></span></li>");
/* 145 */       out.write("\n                              <li  data-value=\"7.5\">3pt <span class=\"threePt\"></span></li>");
/* 146 */       out.write("\n                              <li  data-value=\"10\">4pt <span class=\"fourPt\"></span></li>");
/* 147 */       out.write("\n                           </ul>\n                        </div>\n                     </div>\n                  </div>\n                  <div class=\"form-group\">\n                     <label class=\"control-label\">");
/* 148 */       out.print(FormatUtil.getString("am.webclient.flashview.linetransparency.text"));
/* 149 */       out.write("</label>\n                     <div class=\"sliderBlock\">\n                        <div id=\"slider-range-min\"></div>\n                        <p>\n                           <input type=\"text\" id=\"amount\" readonly style=\"border:0; color:#555;\" value=\"\">\n                        </p>\n                     </div>\n                  </div>\n               </div>\n                <div class=\"graphPreview\" id=\"graphPreviewDIV\">\n                    <div id=\"infovisTest\"></div>\n                </div>\n               <div class=\"form-group btnBlockDisplay\">\n                  <div class=\"inputBlock inputBlockOffset\">\n                     <button class=\"btn\"  id=\"saveDisplayProps\" onclick=\"changeGraphAsPerDisplayProps(selectedViewId);\t\">");
/* 150 */       out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 151 */       out.write("</button>\n                     <button id=\"btnCancelDisplay\" class=\"btn\">");
/* 152 */       out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.cancel"));
/* 153 */       out.write("</button>\n                  </div>\n               </div>\n            </div>\n         </div>\n         <!-- dialog display properties -->\n         <div id=\"dialogAdd\" class=\"hidden\"></div>\n            <div id=\"dialogAddForEdit\" class=\"hidden\"></div>\n         <div id='loadingmessageForADM' class=\"loader\" style=\"display:none\"></div>\n         <!-- script section -->\n         <script>\n\n\n            // dialog credentials\n            $(function() {\n              $(\"#OpenDialogDisplay\").click(function () {\n            \t  ");
/*     */       
/* 155 */       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 156 */       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 157 */       _jspx_th_c_005fif_005f3.setParent(null);
/*     */       
/* 159 */       _jspx_th_c_005fif_005f3.setTest("${param.displayAvailableViews=='true'}");
/* 160 */       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 161 */       if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */         for (;;) {
/* 163 */           out.write("\n             \t if($('#availableViews').val()==0)\n             \t\t {\n                 \t\tif($('#availableViews option').length=='1')\n            \t\t\t\t{\n                 \t\t\t    alert('");
/* 164 */           out.print(FormatUtil.getString("am.webclient.create.newview.alert"));
/* 165 */           out.write("');\n            \t\t\t\t\treturn;\n            \t\t\t\t}\n             \t\talert('");
/* 166 */           out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 167 */           out.write("');\n             \t\t return;\n             \t\t }\n             \t ");
/* 168 */           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 169 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 173 */       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 174 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */       }
/*     */       else {
/* 177 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 178 */         out.write("\n                $(\"#dialogDisplayPro\").dialog({\n                  modal   : true, \n                  //height  : 400, \n                  width   : 1020,\n                  show    : {effect: 'drop', direction: \"left\", duration: 500},\t\t\t// No I18N\n                  hide    : {effect: 'drop', direction: \"right\", duration: 500},\t\t\t// No I18N\n                  dialogClass : 'dialogCustom dialogDisplay'\t\t\t\t\t\t\t// No I18N\n                });\n\t\t\t \tif(businessServiceTree === undefined){\n\t\t\t\t\tvar graph = getDefaultGraphForPreview();\n\t\t\t\t\tnodeImplementType = 'rectangleForBV';\t\t// No I18N\n\t\t\t\t\tbusinessServiceTree = initJITSpaceTree(graph, 'infovisTest', false, '#graphPreviewDIV', false);\t//No I18N\n\t\t\t\t}\n\t\t\t\tsetDefaultDisplayProps();\n              });\n              $(\"#btnCloseDisplay\").click(function(e) {\n                $(\"#dialogDisplayPro\").dialog('close');// No I18N\n\t\t\t\te.preventDefault();\n              });\n\t\t\n\t\t\t$(\"#btnCancelDisplay\").click(function(e) {\n\t\t\t\tcancelSettingsForGraph();\n              \t$(\"#dialogDisplayPro\").dialog('close');\t\t\t\t\t\t\t\t\t// No I18N\n");
/* 179 */         out.write("\t\t\t\te.preventDefault();\n              });\n            \n              // slider\n              $( \"#slider-range-min\" ).slider({\n                  range: \"min\",\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  value: 100,\n                  min: 0,\n                  max: 100,\n                  slide: function( event, ui ) {\n\t\t\t\t\n\t\t\t\t $( \"#amount\" ).attr( 'transparent-value', ui.value);\t\t\t// No I18N\n                    $( \"#amount\" ).val( ui.value +  \"%\" );\n\t\t\t\t\tsetLineTransparencyToPreview(ui.value);\n                  }\n                });\n\t\t\t\n            \t $( \"#amount\" ).attr( 'transparent-value', $( \"#slider-range-min\" ).slider( \"value\" ));\t\t// No I18N\n                $( \"#amount\" ).val( $( \"#slider-range-min\" ).slider( \"value\" ) + \"%\" );\n                //line thickness\n                $(\".lineThick\").click(function(){\n                  $(this).children('ul').slideToggle();  \t\t\t\t\t\t\t\t \t// No I18N\n                });\n                $('#editLineThicker li').click(function(){\n                  var Clickvalue = $(this).html();\n");
/* 180 */         out.write("\t\t\t      var value = $(this).data('value');\t\t\t\t//No I18N\n\t\t\t\t$('#editLineThicker').attr('value', value);\t\t// No I18N\n                  $('.textValueLine').html(Clickvalue);\n\t\t\t   \t  setLineThicknessToPreview(value);\n                });\n                //dropdown\n                 $(\"#viewSettings\").click(function(){\n                  $(\".settingsDropdown\").slideToggle();  \n                });\n                 //Animation slide\n                  $(\".settingsDropdown\").slideUp('slow','swing',function(){\t\t\t// No I18N\n                \n                });\n                  //bg color picker\n                 $('#picker').colpick({\n                  layout:'hex',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  submit:0,\n                  colorScheme:'dark',\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  onChange:function(hsb,hex,rgb,el,bySetColor) {\n                    $('.changeColor').css('background-color','#'+hex);\t\t\t\t// No I18N\n\t\t\t\tchangeCanvasBgForSt('infovisTest-canvas', 'previewLabelName', hex);\t\t\t// No I18N\n                    // Fill the text box just if the color was set using the picker, and not the colpickSetColor function.\n");
/* 181 */         out.write("                    if(!bySetColor) $(el).val('#'+hex);\n                  }\n                }).keyup(function(){\n                  $(this).colpickSetColor(this.value);\n                });\n            \n                 //line color picker\n                 $('#pickerLine').colpick({\n                  layout:'hex',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  submit:0,\n                  colorScheme:'dark',\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  onChange:function(hsb,hex,rgb,el,bySetColor) {\n                    $('.changeColorLine').css('background-color','#'+hex);\t\t\t// No I18N\n                    $(el).css('border-color','#'+hex);\t\t\t\t\t\t\t\t\t\t// No I18N\n\t\t\t\t\tbusinessServiceTree.canvas.viz.config.Edge.color = '#'+hex;\t\t\t\t// No I18N\n\t\t\t\t\tbusinessServiceTree.plot();\n                    if(!bySetColor) $(el).val('#'+hex);\n                    // Fill the text box just if the color was set using the picker, and not the colpickSetColor function.\n                  }\n                }).keyup(function(){\n                  $(this).colpickSetColor(this.value);\n");
/* 182 */         out.write("                });\n            \n                 //text color picker\n                 $('#pickerText').colpick({\n                  layout:'hex',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  submit:0,\n                  colorScheme:'dark',\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n                  onChange:function(hsb,hex,rgb,el,bySetColor) {\n                    $('.changeColorText').css('background-color','#'+hex);\t\t\t\t// No I18N\n                    $(el).css('border-color','#'+hex);\t\t\t\t\t\t\t\t\t\t\t// No I18N\n\t\t\t\t\t$('.previewLabelName').css('color','#'+hex);\t//No I18N\n                    // Fill the text box just if the color was set using the picker, and not the colpickSetColor function.\n                    if(!bySetColor) $(el).val('#'+hex);\n                  }\n                }).keyup(function(){\n                  $(this).colpickSetColor(this.value);\n                });\n            \n            });\n            \n\n\t\t  \tfunction doAfterComputeActions(){\n\t\t  \t\t/*actions in this method will be done after compute of graph*/\n\t\t\t\tif(displayProps != undefined){\n");
/* 183 */         out.write("\t\t\t\t\t\tif(!displayProps.showLabel){\n\t\t\t\t\t\t\thideLabelForPreview();\n\t\t\t\t\t\t}\n\t\t\t\t}\t\n\t\t\t\tif(jsAtt){\n\t\t\t\t\t\t$(\".iconAddNode\").remove();\n\t\t\t\t\t\t$(\".iconDelete\").remove();\n\t\t\t\t\t\t$(\".iconEdit\").remove();\n\t\t\t\t\t\t$('.redirectToDetails').removeAttr(\"href\");\n\t\t\t\t\t}\n \t\t\t}\n\n\t\t   $(\"#btnAddClose, #btnAddCancel\").click(function() {\n\t\t      $(\"#dialogAdd\").dialog('close');\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n              });\n\t\t slideUpSelectBox();\n\n\t\t$('#zoomIn').click(function() {\n\t\tvar val = st.controller.Navigation.zooming/1000; \n\t\tvar ans = 1 + (10 * val);\n\t\tst.canvas.scale(ans, ans);\n\t\tvar x = st.canvas.scaleOffsetX,\n\t\ty = st.canvas.scaleOffsetY;\n\t\tsetNodeCss(x,y);\n\t});\n\t$('#zoomOut').click(function() {\n\t\tvar val = st.controller.Navigation.zooming/1000; \n\t\tvar ans = 1 - (10 * val);\n\t\tst.canvas.scale(ans, ans);\n\t\tvar x = st.canvas.scaleOffsetX,\n\t\ty = st.canvas.scaleOffsetY;\n\t\tsetNodeCss(x,y);\n\t});\n\t\t\n\t$('#editShowOnlyMGs').click(function(){\n\t\t$('#editShowOnlyTopMGs').prop(\"checked\", false);\t//No I18N\n\t\tsetGraphAccordingToConditions();\n");
/* 184 */         out.write("\t});\n\t\n\t$('#editShowOnlyTopMGs').click(function(){\n\t\t$('#editShowOnlyMGs').prop(\"checked\", false);//No I18N\n\t\tsetGraphAccordingToConditions();\n\t});\n\n\t$('#editShowOnlyCriticalMonitors').click(function(){\n\t\tsetGraphAccordingToConditions();\n\t});\n\t\t\t\nfunction getJITNodes(){\n\tvar jsonString = '{';\n\tvar counter = 0;\n\tvar graphObj ={};\n    graphObj = getJSON();\n\tvar pos = getCanvasPos();\n\tvar listOfNodeIds = [];\n\tfor(var i in graphObj.graph.nodes){\n\t\tvar id = graphObj.graph.nodes[i].id;\n\t\tvar parentNodeID = graphObj.graph.nodes[i].getData(\"parentNodeID\");\n\t\tvar resourceID = \"-1\";\n\t\tvar pos = graphObj.graph.nodes[i].pos.getc();\n\t\tvar posX = pos.x;\n\t\tvar posY = pos.y;\n\t\tvar type = graphObj.graph.nodes[i].getData(\"nodeType\");\n\t\tif(parentNodeID != \"-1\" && id != nodeIdentifier+\"1\"){\n\t\t\tvar parentNode = graphObj.graph.getNode(parentNodeID);\n\t\t\tvar parentPos = parentNode.pos.getc();\n\t\t\tposX = posX - parentPos.x;\n\t\t\tposY = posY - parentPos.y;\n\t\t\t\tif(type != \"toEdit\"){\n\t\t\t\t\tjsonString += \"\\\"parentID_\"+id+\"\\\":\\\"\"+parentNodeID+\"\\\",\\\"resourceID_\"+id+\"\\\":\\\"\"+resourceID+\"\\\",\\\"posX_\"+id+\"\\\":\"+posX+\",\\\"posY_\"+id+\"\\\":\"+posY+\",\";// No I18N\n");
/* 185 */         out.write("\t\t\t\t\tlistOfNodeIds[counter] = id +\" \";\t\n\t\t\t\t\tcounter++;\n\t\t\t\t}\n\t\t}\n\t}\n\tjsonString +='\\\"totalNumberOfNodes\\\":'+counter+\",\";// No I18N\n\tjsonString +='\\\"nodeIdList\\\":'+\"[\"+listOfNodeIds.join(\",\")+\"]\";// No I18N\n\tjsonString += '}';\n\treturn jsonString;\n}\n\nfunction getDefaultGraphForPreview(){\n\treturn {\"id\":\"test1\",\"name\":\"<div class='iconBox'><span class='cloudBox'><img src='../images/icon_sg.png'></span><i><a class='previewLabelName' target='_blank'>Monitor Group</a></i></div>\",\"data\":{\"$nodeType\":\"group\",\"$groupName\":\"sasdsd\",\"$message\":\"<p> <label>Name : </label><b>sasdsd</b></p><p><label>Type :  </label><b>Group</b></p>\"},\"children\":[{\"id\":\"test2\",\"name\":\"<div class='iconBox'><span id='nameIdentifier' class=''><img src='../images/dependency/images/icon_monitors_apache.png'></span><i><a class='previewLabelName monDispName'  href='' target='_blank'>apache monitor</a></i></div>\",\"data\":{\"$resourceId\":\"10000090\",\"$parentNodeID\":\"1\",\"$nodeType\":\"monitor\",\"$displayName\":\"divya-1994-apache\",\"$message\":\"<p> <label>Name : </label><b>divya-1994-apache</b></p><p><label>Type :  </label><b>Apache</b></p>\", \"$status\":\"stsUp\"}},{\"id\":\"test3\",\"name\":\"<div class='iconBox'><span id='nameIdentifier' class=''><img src='../images/dependency/images/icon_monitors_urlmonitor.png'></span><i><a class='previewLabelName monDispName'  href='' target='_blank'>URL monitor</a></i></div>\",\"data\":{\"$resourceId\":\"10000030\",\"$parentNodeID\":\"1\",\"$nodeType\":\"monitor\",\"$displayName\":\"AppManager Home Page\",\"$message\":\"<p> <label>Name : </label><b>AppManager Home Page</b></p><p><label>Type :  </label><b>Url Monitor</b></p>\", \"$status\":\"stsUp\"}},{\"id\":\"test5\",\"name\":\"<div class='iconBox'><span class='iconDelete'></span><span class='iconEdit'></span><span id='nameIdentifier' class='groupMon stsDown'><img src='../images/icon_sg.png'></span><i><a class='previewLabelName'  href='' target=\\\"_blank\\\"> Application Server Group</a></i></div>\",\"data\":{\"$status\":\"stsDown\",\"$parentNodeID\":\"1\",\"$nodeType\":\"sub-group\",\"$displayName\":\"Application Server Group\",\"$groupType\":\"sub-group\",\"$message\":\"<p> <label>Name : </label><b>Application Server Group 1</b></p><p><label>Type :  </label><b>Sub-Group</b></p>\"},\"children\":[{\"id\":\"test6\",\"name\":\"<div class='iconBox'><span id='nameIdentifier' class=''><img src='../images/dependency/images/icon_monitors_weblogic.png'></span><i><a class='previewLabelName monDispName'  href='' target='_blank'> weblogic monitor</a></i></div>\",\"data\":{\"$resourceId\":\"10000167\",\"$parentNodeID\":\"6\",\"$nodeType\":\"monitor\",\"$displayName\":\"app-xp1(weblogic)\",\"$message\":\"<p> <label>Name : </label><b>app-xp1(weblogic)</b></p><p><label>Type :  </label><b>WebLogic</b></p>\", \"$status\":\"stsUp\"}}]},{\"id\":\"test12\",\"name\":\"<div class='iconBox'><span class='iconDelete'></span><span class='iconEdit'></span><span id='nameIdentifier' class='groupMon stsUp'><img src='../images/icon_sg.png'></span><i><a class='previewLabelName'  href='' target=\\\"_blank\\\"> Application Server Group 1</a></i></div>\",\"data\":{\"$status\":\"stsUp\",\"$parentNodeID\":\"1\",\"$nodeType\":\"sub-group\",\"$displayName\":\"Application Server Group\",\"$groupType\":\"sub-group\",\"$message\":\"<p> <label>Name : </label><b>Application Server Group 2</b></p><p><label>Type :  </label><b>Sub-Group</b></p>\"}},{\"id\":\"test5\",\"children\":[{\"id\":\"test9\",\"name\":\"<div class='iconBox'><span id='nameIdentifier' class='stsDown'><img src='../images/dependency/images/icon_monitors_jboss.png'></span><i><a class='previewLabelName monDispName'  href='' target='_blank'> jboss monitor</a></i></div>\",\"data\":{\"$resourceId\":\"10000294\",\"$parentNodeID\":\"6\",\"$nodeType\":\"monitor\",\"$displayName\":\"app-centos32-1(jboss)\",\"$message\":\"<p> <label>Name : </label><b>app-centos32-1(jboss)</b></p><p><label>Type :  </label><b>JBoss</b></p>\", \"$status\":\"stsDown\"}}, {\"id\":\"test10\",\"name\":\"<div class='iconBox'><span class='iconDelete'></span><span class='iconEdit'></span><span id='nameIdentifier' class='groupMon stsUp'><img src='../images/icon_sg.png'></span><i><a class='previewLabelName'  href='' target=\\\"_blank\\\"> Database Server Group</a></i></div>\",\"data\":{\"$status\":\"stsUp\",\"$parentNodeID\":\"1\",\"$nodeType\":\"sub-group\",\"$displayName\":\"Application Server Group\",\"$groupType\":\"sub-group\",\"$message\":\"<p> <label>Name : </label><b>Application Server Group </b></p><p><label>Type :  </label><b>Sub-Group</b></p>\"},\"children\":[{\"id\":\"test11\",\"name\":\"<div class='iconBox'><span id='nameIdentifier' class=''><img src='../images/dependency/images/icon_monitors_postgres.png'></span><i><a class='previewLabelName'  href='' target='_blank'> PostgreSQL Monitor</a></i></div>\",\"data\":{\"$status\":\"stsUp\",\"$displayName\":\"<div class='iconBox'><span id='nameIdentifier' class=''><img src='../images/dependency/images/icon_monitors_postgres.png'></span><i><a class='redirectToDetails'  href='' target='_blank'> 172.18.65.252_PostgreSQL</a></i></div>\",\"$resourceId\":\"10000165\",\"$nodeType\":\"monitor\",\"$parentNodeID\":1,\"$message\":\"<p> <label>Name : </label><b>172.18.65.252_PostgreSQL</b></p><p><label>Type :  </label><b>PostgreSQL</b></p>\",\"$alpha\":0.96875},\"children\":[]}]}]}]}; //No I18N\n");
/* 186 */         out.write("}\nfunction hideLabelForPreview(){\n\t$('.monDispName').toggle();\n}\n\nfunction setLineThicknessToPreview(val){\n\tvar dim = getDim(val);\n\tbusinessServiceTree.canvas.viz.config.Edge.dim = dim ;\t\t\t\t// No I18N\n\tbusinessServiceTree.canvas.viz.config.Edge.lineWidth = getLineStrength(dim);\n\tbusinessServiceTree.plot();\n}\n\nfunction setLineTransparencyToPreview(val){\n\tvar value = val/100;\n\tbusinessServiceTree.canvas.viz.config.Edge.alpha = value;\n\tbusinessServiceTree.plot();\n}\n         </script>\n    </body>\n\n\n\n\n</html>\n");
/*     */       }
/* 188 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 189 */         out = _jspx_out;
/* 190 */         if ((out != null) && (out.getBufferSize() != 0))
/* 191 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 192 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 195 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 201 */     PageContext pageContext = _jspx_page_context;
/* 202 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 204 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 205 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 206 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 208 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 209 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 210 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 211 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 212 */       return true;
/*     */     }
/* 214 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 220 */     PageContext pageContext = _jspx_page_context;
/* 221 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 223 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 224 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 225 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 227 */     _jspx_th_c_005fout_005f1.setValue("${param.selectedViewId}");
/* 228 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 229 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 230 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 231 */       return true;
/*     */     }
/* 233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 239 */     PageContext pageContext = _jspx_page_context;
/* 240 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 242 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 243 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 244 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 246 */     _jspx_th_c_005fout_005f2.setValue("${param.isReadOnly}");
/* 247 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 248 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 249 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 250 */       return true;
/*     */     }
/* 252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 253 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 258 */     PageContext pageContext = _jspx_page_context;
/* 259 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 261 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 262 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 263 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 265 */     _jspx_th_c_005fout_005f3.setValue("${param.displayAvailableViews}");
/* 266 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 267 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 268 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 269 */       return true;
/*     */     }
/* 271 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 272 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 277 */     PageContext pageContext = _jspx_page_context;
/* 278 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 280 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 281 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 282 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 284 */     _jspx_th_c_005fif_005f0.setTest("${param.isReadOnly=='true'}");
/* 285 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 286 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 288 */         out.write("\n\t\t      \t  $('#viewSettings').hide();\n\t\t      \t$('#availableViews').hide();\n\t\t       ");
/* 289 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 290 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 294 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 295 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 296 */       return true;
/*     */     }
/* 298 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 299 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 304 */     PageContext pageContext = _jspx_page_context;
/* 305 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 307 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 308 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 309 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 311 */     _jspx_th_c_005fif_005f1.setTest("${publishview=='true'}");
/* 312 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 313 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 315 */         out.write("\n\t\t       $('#CreateView').hide();\n\t\t       $('#EditView').hide();\n\t\t       $('#DeleteView').hide();\t\t       \n\t\t     \t$('#availableViews').hide();\n\t\t       ");
/* 316 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 317 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 321 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 322 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 323 */       return true;
/*     */     }
/* 325 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 326 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 331 */     PageContext pageContext = _jspx_page_context;
/* 332 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 334 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 335 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 336 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 338 */     _jspx_th_c_005fif_005f2.setTest("${param.displayAvailableViews=='false'}");
/* 339 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 340 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 342 */         out.write("\n\t\t            $('#CreateView').hide();\n\t\t            $('#EditView').hide();\n\t\t            $('#DeleteView').hide();\n\t\t      \t  \t$('#availableViews').hide();\n\t\t       ");
/* 343 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 344 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 348 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 349 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 350 */       return true;
/*     */     }
/* 352 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 353 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\businessServiceGraphTemplate_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */