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
/*     */ public final class businessServiceGlobalView_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/*  60 */       response.setContentType("text/html;charset=UTF-8");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<!--$Id$-->\n\n\n\n<!-- This can be used as a common template for view settings -->\n  <div class=\"boxHead\">\n     ");
/*  71 */       out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/*  72 */       out.write("      \n     <div id=\"viewSettings\" class=\"iconSettings listComponent\">\n        ");
/*  73 */       out.print(FormatUtil.getString("view.settings"));
/*  74 */       out.write("\n        <ul class=\"settingsDropdown slideBoxUp\" style=\"display:none;\">\n           <li id=\"OpenDialogDisplayGlobalView\" onclick=\"setDefaultDisplayPropsForGlobalView()\">");
/*  75 */       out.print(FormatUtil.getString("am.webclient.flashview.displayprops.text"));
/*  76 */       out.write("</li>\t\n               <li id=\"CreateView\" onclick=\"javascript:createNewView()\">");
/*  77 */       out.print(FormatUtil.getString("am.webclient.new.view.creation"));
/*  78 */       out.write("</a></li> \n                     <li id=\"EditView\" onclick=\"javascript:editView()\">");
/*  79 */       out.print(FormatUtil.getString("am.webclient.edit.view"));
/*  80 */       out.write("</a></li>\t\n             <li id=\"DeleteView\" onclick=\"javascript:deleteView()\">");
/*  81 */       out.print(FormatUtil.getString("am.webclient.delete.view"));
/*  82 */       out.write("</a></li>\t\n           <li id='publishGlobalView' onClick=\"showExternalLink()\">");
/*  83 */       out.print(FormatUtil.getString("am.webclient.flashview.makepublic.text"));
/*  84 */       out.write("</li>\n            <li onClick=\"showExternalReadOnlyLink()\" id=\"readonly_publishGlobal_view\">");
/*  85 */       out.print(FormatUtil.getString("am.webclient.flashview.makepublic.readonly.text"));
/*  86 */       out.write("</li>\n        </ul>\n     </div>\n     <select id=\"availableViews\" onchange=\"javascript:redirectToSelectedView()\">\n       <option value=\"0\">---");
/*  87 */       out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/*  88 */       out.write("---</option>\n     </select>\n  </div>\n<!-- This can be used as a common template for view settings END -->\n\n\n<div class=\"GlobalMonitorGrp\">\n    <table width=\"100%\"  cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"tableGlobalMonitor\" id=\"globalViewForStatusOnly\">\n<!--\t<tr>\n\n     <td class=\"statusMonitorGlobal bgCritical\">\n       <div class=\"statusImgBlock\">\n           <a href=\"#\">\n           <img src=\"icon_monitor_status.png\"/>\n           <span>Applications Manager</span>\n           </a>\n       </div>\n     </td>\n\n</tr> -->\n\t </table>\n</div>\n\n\n  <!-- dialog display properties -->\n         <!-- dialog display properties -->\n<div id=\"dialogDisplayProGlobalView\" class=\"hidden\">\n  <h2 class=\"dialogHeading\"> ");
/*  89 */       out.print(FormatUtil.getString("am.webclient.flashview.displayprops.text"));
/*  90 */       out.write("</h2>\t  ");
/*  91 */       out.write("\n  <a href=\"#\" id=\"btnCloseDisplayGlobalView\" class=\"btnClose\">X</a>\t\t\t");
/*  92 */       out.write("\n  <div class=\"popupCon\">\n    <div class=\"form-group\">\n      <label for=\"Show_group_status\" class=\"control-label\">");
/*  93 */       out.print(FormatUtil.getString("am.webclient.flashview.show.mgonly.text"));
/*  94 */       out.write("</label>\n      \n        <div class=\" checkbox\">\n          <input type=\"checkbox\" name=\"monitorAndSubGroups\" checked id=\"Show_group_status\">\n          (");
/*  95 */       out.print(FormatUtil.getString("am.webclient.flashview.toplevelonly.help1.text"));
/*  96 */       out.write(")</div>\n      </div>\n    \n    <div class=\"form-group\">\n      <label for=\"monitor_rows\" class=\"control-label\">");
/*  97 */       out.print(FormatUtil.getString("am.webclient.flashview.noofrow.text"));
/*  98 */       out.write("</label>\n      <div class=\"inputBlock\">\n        <input type=\"text\" class=\"form-control\" id=\"monitor_rows\" placeholder=\"\">\n      </div>\n    </div>\n    <div class=\"form-group monitorOuterCon\">\n      <label class=\"control-label\">");
/*  99 */       out.print(FormatUtil.getString("webclient.fault.details.properties.AssociatedMonitorGroups"));
/* 100 */       out.write("</label>\n      <div class=\"monitorCon\">\n        <div class=\"monitorBox\" id=\"associatedMons\">\n        </div>\n      </div>\n    </div>\n    <div class=\"form-group btnBlockDisplay\">\n      <div class=\"inputBlock inputBlockOffset\">\n        <button class=\"btn\"   id=\"saveDisplayPropsInStatusOnly\" onclick=\"savePropsForGlobalView()\">");
/* 101 */       out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 102 */       out.write("</button>\n        <button id=\"btnCancelDisplayGlobalView\" class=\"btn\">");
/* 103 */       out.print(FormatUtil.getString("am.webclient.talkback.button.close"));
/* 104 */       out.write("</button>\n      </div>\n    </div>\n  </div>\n</div>\n\n         <!-- dialog display properties -->\n\n<script>\nvar selectedViewId;\nvar haid;\nvar displayAvailableViews;\n$(document).ready(function(){\n\t haid=");
/* 105 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 107 */       out.write(";\n\t selectedViewId='");
/* 108 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 110 */       out.write("';\n\t loadAvailableViews(selectedViewId);\n\t displayAvailableViews='");
/* 111 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("';\n\t var isReadOnly='");
/* 114 */       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */         return;
/* 116 */       out.write("';\n\t");
/* 117 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 119 */       out.write("\n    ");
/* 120 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 122 */       out.write("\n    ");
/* 123 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */         return;
/* 125 */       out.write("\n});\n\nvar noOfColumns ;\n\n/* this can be used as a common template for view Settings header */\n $(\"#viewSettings\").click(function(){\n   $(\".settingsDropdown\").slideToggle();  \n });\n                 //Animation slide\n $(\".settingsDropdown\").slideUp('slow','swing',function(){\t\t\t// No I18N\n });\nslideUpSelectBox();\n/* this can be used as a common template for view Settings header  - end*/\n\n$(function() {\n    $(\"#OpenDialogDisplayGlobalView\").click(function () {\n           $(\"#dialogDisplayProGlobalView\").dialog({\n             modal   : true, \n             //height  : 400, \n             width   : 1020,\n             show    : {effect: 'drop', direction: \"left\", duration: 500},\t\t\t// No I18N\n             hide    : {effect: 'drop', direction: \"right\", duration: 500},\t\t\t// No I18N\n             dialogClass : 'dialogCustom dialogGroupMon'\t\t\t\t\t\t\t// No I18N\n           });\n\n             $(\"#btnCloseDisplayGlobalView, #btnCancelDisplayGlobalView\").click(function(e) {\n\t\t\t\t $(\"#dialogDisplayProGlobalView\").dialog('close');\t\t\t\t\t\t\t\t\t// No I18N\n");
/* 126 */       out.write("\t\t\t\t\te.preventDefault();\n\t\t\t    });\n\t\t  });\n  \n      //dropdown\n       //Animation slide\n});\n\n/* Script switching to other view - graph view*/\nfunction savePropsForGlobalView(){\n\tvar isShowOnlyTopLevel = document.getElementById(\"Show_group_status\").checked;\n\t//Removinng if..else conditions \n\t//While changing global view to graph view, if we associate/deassociate a monitor group to/from a view, It should be reflected in graph view..\n\t//if(!isShowOnlyTopLevel){\n\t\tnodeImplementType = 'rectangleForBVWithSavedPos';\t//No I18N\t\n\t\t//updateShowTopLevelFlag(isShowOnlyTopLevel);\n\t//}else{\n\t\tvar i =0;\n\t\tvar monitorsSelected = [];\n\t\tvar noOfColumns = $('#monitor_rows').val();\n\t\t$('#associatedMons input[type=checkbox]').each(function( index ) {\n\t\t\tvar ele = $(this);\n\t\t\tisChecked = ele.is(':checked');\t//No I18N\n\t\t\tif(isChecked){\n\t\t\t\tvar resourceID = ele.val();\n\t\t\t\tmonitorsSelected[i++] = resourceID;\n\t\t\t}\n\t\t });\n\t\t updateMonitorGroupsToFilterList(monitorsSelected, noOfColumns);\n\t\t updateShowTopLevelFlag(isShowOnlyTopLevel);\n\t//}\n");
/* 127 */       out.write("\t}\n\nfunction loadAvailableViews(selectedViewId)\n{\n\t//alert(\"haid==>\"+haid);\n\tvar dataString = \"method=getAvailableViews&haid=\"+haid; //No I18N\n\t$.ajax({\n                type:\"POST\", //No I18N\n                url:\"/GraphicalView.do\", //No I18N\n                data:dataString,\n\t\t\t\tdataType: \"json\",//No I18N\n\t\t           success: function(response)\n\t\t           {\n\t\t        \t   var views = response['views'];\n\t\t        \t   var dispNames=response['dispNameList'];\n\t\t        \t   for(var i in dispNames)\n\t\t        \t   {\n\t\t        \t\t     var val = views[dispNames[i]];\n\t\t        \t\t     $('#availableViews').append($(\"<option></option>\").attr(\"value\",val).text(decodeURI(dispNames[i]))); \n\t\t        \t   }  \n\t\t        \t   $('#availableViews').val(selectedViewId);\n\t\t           }\n    });\n\t\n\t\n}\nfunction redirectToSelectedView()\n{   \n     \n\tif($('select[id=availableViews]').val()=='0')\n\t{\n\t\talert('");
/* 128 */       out.print(FormatUtil.getString("am.webclient.valid.view.selectionalert"));
/* 129 */       out.write("');\n    \t $('#availableViews').val(selectedViewId);\n    \t  return;\n    }\n      selectedViewId=$('select[id=availableViews]').val();\n      viewid=selectedViewId;\n    \n     // if(selectedViewId!='0')\n    \t//  {\n    \t  \n       \t  var dataString = \"method=getMGXML&haid=\"+haid+\"&viewid=\"+selectedViewId+\"&currentime=\"+");
/* 130 */       out.print(System.currentTimeMillis());
/* 131 */       out.write("+\"&isHtml=true\"; //No I18N    \n          $.ajax({\n                      type:\"POST\", //No I18N\n                      url:\"/GraphicalView.do\", //No I18N\n                      data:dataString,\n                        dataType: \"json\",//No I18N\n                      success: function(response)\n                      {  \n                          $('#infovis-canvaswidget').remove(); //No I18N\n                       // alert(response['SHOWTOPLEVELMGS']);\n                          if(response['SHOWTOPLEVELMGS']=='false')\n                    \t  {\n                        \t  $('#BVViewer').load('/jsp/businessServiceGraphTemplate.jsp?isReadOnly=false&displayAvailableViews='+displayAvailableViews+'&haid='+haid+'&selectedViewId='+selectedViewId);//No I18N\n                    \t  }\n                          else{\n                        \t  $('#BVViewer').load('/jsp/businessServiceGlobalView.jsp?isReadOnly=false&displayAvailableViews='+displayAvailableViews+'&haid='+haid+'&selectedViewId='+selectedViewId);\t\t //No I18N\n                          }\n");
/* 132 */       out.write("                        \n                      }\n          });\n    //}\n      \n      \n}\n\nfunction updateShowTopLevelFlag(isShowOnlyTopLevel){\n\t//alert(\"viewww\"+viewid);\n\tvar dataString = \"method=updateShowTopLevelFlag&haid=\"+haid_flash+\"&viewid=\"+selectedViewId+\"&showTopLevelFlag=\"+isShowOnlyTopLevel; //No I18N    \n\t\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t      data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t\t      success: function(response){\n\t\t\t\t$(\"#dialogDisplayProGlobalView\").dialog('destroy').remove(); \t\t\t\t\t// No I18N \n\t\t\t\t//var bvName=response.bvName;\n\t\t\t\t//alert(\"voe:\"+selectedViewId);\n\t\t\t\t//$('#BVViewer').load('/jsp/businessServiceGraphTemplate.jsp?isReadOnly='+isReadOnly+'&BVName='+decodeURI(bvName)+'&haid='+haid+'&selectedViewId='+selectedViewId);// No I18N\n\t\t\t\tredirectToSelectedView();\n\t\t\t\tdisplayProps = undefined;\n\t\t\t }\n\t    });\n}\n//var bvName;\nfunction updateMonitorGroupsToFilterList(monitorsSelected, noOfColumns){\n//\talert(\"selecViewId\"+selectedViewId);\n\tvar dataString = \"method=updateMonitorGroupsToFilterList&haid=\"+haid_flash+\"&viewid=\"+selectedViewId+\"&monitorList=\"+JSON.stringify(monitorsSelected)+\"&noOfColumns=\"+noOfColumns; //No I18N    \n");
/* 133 */       out.write("\t\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t      data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t\t      success: function(response){\n\t\t\t\t//$(\"#dialogDisplayProGlobalView\").dialog('destroy').remove(); // No I18N \t\t\n\t\t\t\t //bvName=response.bvName;// No I18N\n\t\t\t\t//$('#BVViewer').load('/jsp/businessServiceGlobalView.jsp?BVName='+decodeURI(bvName)+'&haid='+haid+'&selectedViewId='+selectedViewId);// No I18N\n\t\t\t }\n\t    });\n}\n\n//Appending the inner html for global view \n//var bvName=\"\";\n\n var dataString = \"method=getDataForGlobalView&haid=\"+haid_flash+\"&viewid=\"+selectedViewId+\"&currentime=\"+");
/* 134 */       out.print(System.currentTimeMillis());
/* 135 */       out.write("+\"&isHtml=true\"; //No I18N    \n$.ajax({\n           type:\"POST\", //No I18N\n           url:\"/GraphicalView.do\", //No I18N\n           data:dataString,\n\t\t\tdataType: \"json\",//No I18N\n           success: function(response)\n           {         \t\t\t\t\t\t\n\t\t\t\t$('#globalViewForStatusOnly').append(response['innerHtml']);\t\t\t\t// No I18N\n\t\t\t\tnoOfColumns = response['noOfColumns'];\t\t\t\t\t// No I18N\n\t\t\t\t$(\".statusMonitorGlobal\").width($('#BVViewer').width()/noOfColumns);\t\t// No I18N\n           }\n});\n\n\nfunction setDefaultDisplayPropsForGlobalView(){\n\tvar dataString = \"method=getDisplayPropsForGlobalGraph&haid=\"+haid_flash+\"&viewid=\"+selectedViewId; //No I18N    \n\t\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t      data:dataString,\n\t\t\t  dataType: \"json\",//No I18N\n\t\t      success: function(response){\n\t\t\t\t$(\"#associatedMons\").html(response['resultString']);\t\t// No I18N\n\t\t\t\t$('#monitor_rows').val(noOfColumns);\n\t\t\t }\n\t    });\n}\n\nfunction resetGlobalView(){\n\tvar dataString = \"method=updateMonitorGroupsToFilterList&haid=\"+haid_flash+\"&viewid=\"+viewid+\"&noOfColumns=\"+noOfColumns; //No I18N    \n");
/* 136 */       out.write("\t\t$.ajax({\n\t\t\t type:\"POST\", //No I18N\n\t\t      url:\"/GraphicalView.do\", //No I18N\n\t\t      data:dataString,\n\t\t\t dataType: \"json\",//No I18N\n\t\t      success: function(response){\n\t\t    \t//var bvName=response.bvName;// No I18N\n\t\t\t\t$('#BVViewer').load('/jsp/businessServiceGlobalView.jsp');\t\t\t\t\t// No I18N\n\t\t\t }\n\t    });\n}\n</script>");
/*     */     } catch (Throwable t) {
/* 138 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 139 */         out = _jspx_out;
/* 140 */         if ((out != null) && (out.getBufferSize() != 0))
/* 141 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 142 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 145 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 151 */     PageContext pageContext = _jspx_page_context;
/* 152 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 154 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 155 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 156 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 158 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 159 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 160 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 161 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 162 */       return true;
/*     */     }
/* 164 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 170 */     PageContext pageContext = _jspx_page_context;
/* 171 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 173 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 174 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 175 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 177 */     _jspx_th_c_005fout_005f1.setValue("${param.selectedViewId}");
/* 178 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 179 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 180 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 181 */       return true;
/*     */     }
/* 183 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 189 */     PageContext pageContext = _jspx_page_context;
/* 190 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 192 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 193 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 194 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 196 */     _jspx_th_c_005fout_005f2.setValue("${param.displayAvailableViews}");
/* 197 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 198 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 200 */       return true;
/*     */     }
/* 202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 208 */     PageContext pageContext = _jspx_page_context;
/* 209 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 211 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 212 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 213 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 215 */     _jspx_th_c_005fout_005f3.setValue("${param.isReadOnly}");
/* 216 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 217 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 218 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 219 */       return true;
/*     */     }
/* 221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 227 */     PageContext pageContext = _jspx_page_context;
/* 228 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 230 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 231 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 232 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 234 */     _jspx_th_c_005fif_005f0.setTest("${param.isReadOnly=='true'}");
/* 235 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 236 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 238 */         out.write("\n\t  $('#viewSettings').hide();\n\t  $('#availableViews').hide();\n    ");
/* 239 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 240 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 244 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 245 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 246 */       return true;
/*     */     }
/* 248 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 249 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 254 */     PageContext pageContext = _jspx_page_context;
/* 255 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 257 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 258 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 259 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 261 */     _jspx_th_c_005fif_005f1.setTest("${publishview=='true'}");
/* 262 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 263 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 265 */         out.write("\n    $('#CreateView').hide();\n    $('#EditView').hide();\n    $('#DeleteView').hide();\n  \t$('#availableViews').hide();\n    ");
/* 266 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 267 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 271 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 272 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 273 */       return true;
/*     */     }
/* 275 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 276 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 281 */     PageContext pageContext = _jspx_page_context;
/* 282 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 284 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 285 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 286 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 288 */     _jspx_th_c_005fif_005f2.setTest("${param.displayAvailableViews=='false'}");
/* 289 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 290 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 292 */         out.write("\n        $('#CreateView').hide();\n        $('#EditView').hide();\n        $('#DeleteView').hide();\n\t  \t$('#availableViews').hide();\n    ");
/* 293 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 294 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 298 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 299 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 300 */       return true;
/*     */     }
/* 302 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 303 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\businessServiceGlobalView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */