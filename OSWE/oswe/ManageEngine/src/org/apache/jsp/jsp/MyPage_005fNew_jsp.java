/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MyPage_005fNew_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   23 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   29 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   30 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   31 */     _jspx_dependants.put("/jsp/MyPage_AddWidgets.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   65 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   69 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   96 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  100 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  101 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  102 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  103 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  104 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  105 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  106 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  107 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction.release();
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  114 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.release();
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.release();
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.release();
/*  117 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  118 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*  119 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  120 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  122 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  123 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  124 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  125 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  132 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  135 */     JspWriter out = null;
/*  136 */     Object page = this;
/*  137 */     JspWriter _jspx_out = null;
/*  138 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  142 */       response.setContentType("text/html");
/*  143 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  145 */       _jspx_page_context = pageContext;
/*  146 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  147 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  148 */       session = pageContext.getSession();
/*  149 */       out = pageContext.getOut();
/*  150 */       _jspx_out = out;
/*      */       
/*  152 */       out.write("<!DOCTYPE html>\n");
/*  153 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  154 */       out.write(10);
/*  155 */       out.write("\n\n\n\n\n\n\n\n");
/*  156 */       response.setContentType("text/html;charset=UTF-8");
/*  157 */       request.setAttribute("HelpKey", "New Dashboard");
/*      */       
/*  159 */       out.write("\n<script type=\"text/javascript\" src=\"/template/mootools.js\"></script>\n<script>\nfunction submitForm()\n{\n\n        noOfColumns = document.MyPageForm.numberOfColumns.value;\n        if(noOfColumns>1)\n\t{\n\t\tsumOfColWidths = parseInt(document.MyPageForm.columnWidth1.value)+parseInt(document.MyPageForm.columnWidth2.value);\n\t\tif(noOfColumns>2)\n\t\t\tsumOfColWidths += parseInt(document.MyPageForm.columnWidth3.value);\n\t\tif(noOfColumns>3)\n\t\t\tsumOfColWidths += parseInt(document.MyPageForm.columnWidth4.value);\n\n\t\tif(sumOfColWidths != 100)\n\t\t{\n\t\t\talert('");
/*  160 */       out.print(FormatUtil.getString("am.mypage.newmypage.invalid.columnwidth.text"));
/*  161 */       out.write("');\n\t\t\tdocument.MyPageForm.columnWidth1.focus();\n\t\t\treturn false;\n\t\t}\n\t}\n\n\n");
/*      */       
/*  163 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  164 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  165 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  167 */       _jspx_th_c_005fif_005f0.setTest("${param.action!=\"editMyPage\"}");
/*  168 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  169 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  171 */           out.write(10);
/*  172 */           if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */             return;
/*  174 */           out.write("\n       if(trimAll(document.MyPageForm.displayName.value)=='')\n   \t{\n   \talert('");
/*  175 */           out.print(FormatUtil.getString("am.mypage.newpage.name.empty.alert"));
/*  176 */           out.write("');\n   \tdocument.MyPageForm.displayName.focus();\n   \treturn;\n   \t}\n   \tif(document.MyPageForm.selectedWidgets.value=='')\n   \t{\n   \talert(\"");
/*  177 */           out.print(FormatUtil.getString("am.mypage.newpage.widgets.notselected.alert"));
/*  178 */           out.write("\");\n   \treturn;\n   \t}\n\n\n\n\n");
/*  179 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  180 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  184 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  185 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  188 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  189 */         out.write("\ndocument.MyPageForm.submit();\nreturn;//No I18N\n}\n\nfunction getNewPagewithRelevantWidgets()\n{\n\n        if(document.MyPageForm.mgtemplatepage.checked)\n\t{\n        document.MyPageForm.pageType.value='mgtemplate';\n        templateselected=true;\n        globalselected=false;\n\t}\n\telse\n\t{\n         document.MyPageForm.pageType.value='globalpage';\n        globalselected=true;\n        templateselected=false;\n\t}\n\n\tvar pagename=document.MyPageForm.displayName.value;\n        var pagetype='globalpage';\n        if(templateselected)\n        {\n       \t\tpagetype='mgtemplate';\n        }\n");
/*  190 */         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */           return;
/*  192 */         out.write(10);
/*  193 */         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */           return;
/*  195 */         out.write("\n}\nfunction fillDefaultColumnWidth()\n{\n\tenableColumnWidthBoxes();\n\n\tnoOfColumns = document.MyPageForm.numberOfColumns.value;\n\tif(noOfColumns==2)\n\t\tdocument.MyPageForm.columnWidth1.value = document.MyPageForm.columnWidth2.value = 50;\n\tif(noOfColumns==3)\n\t{\n\t\tdocument.MyPageForm.columnWidth1.value = document.MyPageForm.columnWidth3.value = 30;\n\t\tdocument.MyPageForm.columnWidth2.value = 40;\n\t}\n\tif(noOfColumns==4)\n\t\tdocument.MyPageForm.columnWidth1.value = document.MyPageForm.columnWidth2.value = document.MyPageForm.columnWidth3.value = document.MyPageForm.columnWidth4.value = 25;\n\n}\n\nfunction enableColumnWidthBoxes()\n{\n\t//document.MyPageForm.dashboardName.focus();\n\tnoOfColumns = document.MyPageForm.numberOfColumns.value;\n\n\tdocument.MyPageForm.columnWidth1.disabled = document.MyPageForm.columnWidth2.disabled = document.MyPageForm.columnWidth3.disabled = document.MyPageForm.columnWidth4.disabled =true;\n\n\tif(noOfColumns>1)\n\t\tdocument.MyPageForm.columnWidth1.disabled = document.MyPageForm.columnWidth2.disabled = false;\n");
/*  196 */         out.write("\tif(noOfColumns>2)\n\t\tdocument.MyPageForm.columnWidth3.disabled = false;\n\tif(noOfColumns>3)\n\t\tdocument.MyPageForm.columnWidth4.disabled = false;\n}\n\nfunction checkIsNumeric(event)\n{\n\tvar key = getkey(event);\n\tif ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )\n\t\treturn true;\n\n\tvar keychar = String.fromCharCode(key);\n\tif (\"0123456789\".indexOf(keychar) != -1)\n\t\treturn true;\n\n\treturn false;\n}\n\nfunction getkey(event)\n{\n\tif (window.event)\n\t\treturn window.event.keyCode;\n\telse if (event)\n\t\treturn event.which;\n\telse\n\t\treturn null;\n}\n\nwindow.addEvent('load',function(){\n\n\t\t//widgetTree();\n\t\tenableColumnWidthBoxes();\n\t\t//fillDefaultColumnWidth();\n\t\t//setTreeTDWidth();\n\n\n\n});\n\nfunction getMonitorGroupsforTemplate(isallselected)\n{\n        if(isallselected=='1')\n        {\n        document.getElementById(\"monitorGroupsForTemplate\").style.display=\"none\";//No I18N\n        return;//No I18N\n        }\n        else\n        {\n         document.getElementById(\"monitorGroupsForTemplate\").style.display=\"block\";//No I18N\n");
/*  197 */         out.write("        }\n\t//url=\"/MyPage.do?method=getMgsForPage&randomnumber=\"+ Math.random();\n\t//var http1=getHTTPObject();\n\t//http1.onreadystatechange =function () { setMGs(http1);} ;\n\t//http1.open(\"GET\", url, true);\n\t//http1.send(null);\n}\n/*function setMGs(http1)\n{\nif(http1.readyState == 4)\n \t{\n       \t   if( http1.status == 200)\n\t   {\n\t   document.getElementById(\"monitorGroupsForTemplate\").innerHTML=http1.responseText\n\t   }\n\t}\n}*/\n\n</script>\n");
/*      */         
/*      */ 
/*  200 */         if (com.adventnet.appmanager.struts.actions.MyPageAction.forpage.equals("3"))
/*      */         {
/*  202 */           request.setAttribute("oldtab", "2");
/*      */         }
/*      */         
/*  205 */         out.write(10);
/*      */         
/*  207 */         org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/*  208 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  209 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/*  211 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  212 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  213 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/*  215 */             out.write(9);
/*  216 */             out.write(10);
/*  217 */             out.write(9);
/*  218 */             if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  220 */             out.write("\t\t \n\t");
/*  221 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  223 */             out.write("\t\t\n\t");
/*  224 */             if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  226 */             out.write(10);
/*  227 */             out.write(10);
/*  228 */             out.write(10);
/*      */             
/*  230 */             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  231 */             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/*  232 */             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  234 */             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */             
/*  236 */             _jspx_th_tiles_005fput_005f3.setType("string");
/*  237 */             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/*  238 */             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/*  239 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  240 */                 out = _jspx_page_context.pushBody();
/*  241 */                 _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  242 */                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  245 */                 out.write("\n\n\n<div style=\"padding: 5px 0px 25px 0px;\" class=\"itadmin-dotted-border\">\n<div class=\"header2\" style=\"float: left;\">\n");
/*  246 */                 if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  248 */                 out.write(10);
/*      */                 
/*  250 */                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  251 */                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  252 */                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  254 */                 _jspx_th_c_005fif_005f4.setTest("${param.action!=\"editMyPage\"}");
/*  255 */                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  256 */                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                   for (;;) {
/*  258 */                     out.write(10);
/*      */                     
/*  260 */                     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  261 */                     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  262 */                     _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f4);
/*  263 */                     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  264 */                     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                       for (;;) {
/*  266 */                         out.write(10);
/*      */                         
/*  268 */                         WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  269 */                         _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  270 */                         _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                         
/*  272 */                         _jspx_th_c_005fwhen_005f1.setTest("${MyPageForm.pageType=='businesspage'}");
/*  273 */                         int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  274 */                         if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                           for (;;) {
/*  276 */                             out.write(10);
/*  277 */                             out.print(FormatUtil.getString("am.mypage.dashboard.createnewbusiness.text"));
/*  278 */                             out.write(10);
/*  279 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  280 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  284 */                         if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  285 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                         }
/*      */                         
/*  288 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  289 */                         out.write(10);
/*      */                         
/*  291 */                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  292 */                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  293 */                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                         
/*  295 */                         _jspx_th_c_005fwhen_005f2.setTest("${MyPageForm.pageType=='mgtemplate'}");
/*  296 */                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  297 */                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                           for (;;) {
/*  299 */                             out.write(10);
/*  300 */                             out.print(FormatUtil.getString("am.mypage.dashboard.createnewtemplate.text"));
/*  301 */                             out.write(10);
/*  302 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  303 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  307 */                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  308 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                         }
/*      */                         
/*  311 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  312 */                         out.write(10);
/*      */                         
/*  314 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  315 */                         _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  316 */                         _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  317 */                         int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  318 */                         if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                           for (;;) {
/*  320 */                             out.write(10);
/*  321 */                             out.print(FormatUtil.getString("am.mypage.dashboard.createnew.text"));
/*  322 */                             out.write(10);
/*  323 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  324 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  328 */                         if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  329 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                         }
/*      */                         
/*  332 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  333 */                         out.write(10);
/*  334 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  335 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  339 */                     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  340 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                     }
/*      */                     
/*  343 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  344 */                     out.write(10);
/*  345 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  346 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  350 */                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  351 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                 }
/*      */                 
/*  354 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  355 */                 out.write("\n</div>\n</div>\n\n\n\n");
/*      */                 
/*  357 */                 org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  358 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  359 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  361 */                 _jspx_th_html_005fform_005f0.setAction("/MyPage.do?method=saveMyPage");
/*      */                 
/*  363 */                 _jspx_th_html_005fform_005f0.setTarget("_parent");
/*  364 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  365 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/*  367 */                     out.write(10);
/*  368 */                     out.write(10);
/*  369 */                     out.write(32);
/*  370 */                     if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  372 */                     out.write(10);
/*  373 */                     if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  375 */                     out.write("\n\n<div id=\"WidgetsDiv\" ");
/*  376 */                     if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  378 */                     out.write(" >\n\n\t<input type=\"hidden\" name=\"method\" value=\"saveMyPage\"/>\n\t<input type=\"hidden\" name=\"fromMg\" value=\"");
/*  379 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  381 */                     out.write("\"/>\n\t<input type=\"hidden\" name=\"templateResid\" value=\"");
/*  382 */                     if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  384 */                     out.write("\" />\n\t<input type=\"hidden\" name=\"action\" value=\"");
/*  385 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  387 */                     out.write("\"/>\n\t");
/*  388 */                     if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  390 */                     out.write("\n\t<div style=\"margin: 10px;\">\n\t\t\t<div class=\"creatDashDiv\">\n\t\t\t\t\t\t<table width=\"750\" cellspacing=\"3\" cellpadding=\"3\" border=\"0\" style=\"margin-left:50px;\" class=\"txtGlobal\">\n\t\t\t\t\t  \t\t  <tr>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\"  colspan=\"2\" class=\"bodytextbold\">");
/*  391 */                     out.print(FormatUtil.getString("am.mypage.dashboard.info.text"));
/*  392 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\"  colspan=\"5\" class=\"bodytextbold\">");
/*  393 */                     out.print(FormatUtil.getString("am.mypage.dashboard.layout.info.text"));
/*  394 */                     out.write("</td>\n\t\t\t\t\t  \t\t  </tr>\n\t\t\t\t\t  \t\t  <tr>\n\t\t\t\t\t  \t\t\t<td width=\"80\" valign=\"middle\" align=\"left\">");
/*  395 */                     out.print(FormatUtil.getString("am.mypage.dashboard.name.text"));
/*  396 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td valign=\"middle\" align=\"left\">");
/*  397 */                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  399 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\" nowrap>");
/*  400 */                     out.print(FormatUtil.getString("am.mypage.dashboard.noofcolumns.text"));
/*  401 */                     out.write(" </td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\">\n\t\t\t\t\t  \t\t\t     ");
/*  402 */                     if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  404 */                     out.write("\n\t\t\t\t\t\t\t\t   </td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t  </tr>\n\n\t\t\t\t\t  \t\t  <tr>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\" rowspan=\"3\">");
/*  405 */                     out.print(FormatUtil.getString("Description"));
/*  406 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\" rowspan=\"3\">");
/*  407 */                     if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  409 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\" rowspan=\"2\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td valign=\"top\" align=\"left\" colspan=\"5\" class=\"bodytextbold\">");
/*  410 */                     out.print(FormatUtil.getString("am.mypage.dashboard.columnwidget.text"));
/*  411 */                     out.write("</td>\n\t\t\t\t\t  \t\t  </tr>\n\t\t\t\t\t  \t\t  <tr>\n                                <td align=\"left\">");
/*  412 */                     out.print(FormatUtil.getString("am.mypage.dashboard.column1.text"));
/*  413 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">");
/*  414 */                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  416 */                     out.write("%</td>\n\t\t\t\t\t  \t\t\t<td width=\"10\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\" nowrap>");
/*  417 */                     out.print(FormatUtil.getString("am.mypage.dashboard.column2.text"));
/*  418 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\" nowrap>");
/*  419 */                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  421 */                     out.write("\n\t\t\t\t\t  \t\t    %</td>\n\t\t\t\t\t  \t\t  </tr>\n\t\t\t\t\t  \t\t  <tr>\n\t\t\t\t\t  \t\t\t<td align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">");
/*  422 */                     out.print(FormatUtil.getString("am.mypage.dashboard.column3.text"));
/*  423 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">");
/*  424 */                     if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  426 */                     out.write("\n\t\t\t\t\t  \t\t    %</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">&nbsp;</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">");
/*  427 */                     out.print(FormatUtil.getString("am.mypage.dashboard.column4.text"));
/*  428 */                     out.write("</td>\n\t\t\t\t\t  \t\t\t<td align=\"left\">");
/*  429 */                     if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  431 */                     out.write("\n\t\t\t\t\t  \t\t    %</td>\n\t\t\t\t\t  \t\t  </tr>\n\t\t\t\t\t  \t\t  ");
/*  432 */                     out.write("\n\t\t\t\t\t  \t\t  ");
/*      */                     
/*  434 */                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  435 */                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  436 */                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/*  438 */                     _jspx_th_c_005fif_005f8.setTest("${MyPageForm.pageType!=\"businesspage\"}");
/*  439 */                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  440 */                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                       for (;;) {
/*  442 */                         out.write("\n\t\t\t\t\t  \t\t   <tr>\n\t\t\t\t\t  \t\t  <td></td>\n\t\t\t\t\t  \t\t  <td  colspan=\"3\" valign=\"top\">&nbsp;");
/*  443 */                         if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  445 */                         out.write(32);
/*  446 */                         out.print(FormatUtil.getString("am.mypage.makethis.template.text"));
/*  447 */                         out.write("</td>\n\t\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t\t");
/*  448 */                         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  450 */                         out.write("\n\t\t\t\t\t\t\t");
/*      */                         
/*  452 */                         SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  453 */                         _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  454 */                         _jspx_th_c_005fset_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                         
/*  456 */                         _jspx_th_c_005fset_005f1.setVar("labelforselectmg");
/*  457 */                         int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  458 */                         if (_jspx_eval_c_005fset_005f1 != 0) {
/*  459 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/*  460 */                             out = _jspx_page_context.pushBody();
/*  461 */                             _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  462 */                             _jspx_th_c_005fset_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/*  465 */                             out.print("");
/*  466 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  467 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*  470 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/*  471 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/*  474 */                         if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  475 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                         }
/*      */                         
/*  478 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  479 */                         out.write("\n\t\t\t\t\t\t\t");
/*      */                         
/*  481 */                         IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  482 */                         _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  483 */                         _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f8);
/*      */                         
/*  485 */                         _jspx_th_c_005fif_005f9.setTest("${MyPageForm.pageType=='mgtemplate'}");
/*  486 */                         int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  487 */                         if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                           for (;;) {
/*  489 */                             out.write("\n\t\t\t\t\t\t\t ");
/*  490 */                             if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                               return;
/*  492 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/*  494 */                             SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  495 */                             _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  496 */                             _jspx_th_c_005fset_005f3.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/*  498 */                             _jspx_th_c_005fset_005f3.setVar("labelforselectmg");
/*  499 */                             int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  500 */                             if (_jspx_eval_c_005fset_005f3 != 0) {
/*  501 */                               if (_jspx_eval_c_005fset_005f3 != 1) {
/*  502 */                                 out = _jspx_page_context.pushBody();
/*  503 */                                 _jspx_th_c_005fset_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  504 */                                 _jspx_th_c_005fset_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  507 */                                 out.print(FormatUtil.getString("am.mypage.template.visibility"));
/*  508 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  509 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  512 */                               if (_jspx_eval_c_005fset_005f3 != 1) {
/*  513 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  516 */                             if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  517 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                             }
/*      */                             
/*  520 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  521 */                             out.write("\n\t\t\t\t\t\t\t");
/*  522 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  523 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  527 */                         if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  528 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                         }
/*      */                         
/*  531 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  532 */                         out.write("\n\n \t\t\t\t\t\t\t  <tr >\n\t\t\t\t\t\t\t <td valign=\"top\" class=\"bodytext\" nowrap=\"false\" style=\"padding-top: 10px;\">");
/*  533 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  535 */                         out.write("</td>\n\t\t\t\t\t\t\t  <td  align=\"left\" colspan=\"4\">\n\t\t\t\t\t  \t\t  ");
/*  536 */                         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  538 */                         out.write("\n\t\t\t\t\t  \t\t  <div id=\"allmgrow\" style='");
/*  539 */                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  541 */                         out.write("' >\n\t\t\t\t\t\t\t   <table >\n\t\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t  <td class=\"bodytext\">\n\t\t\t\t\t  \t\t  ");
/*  542 */                         if (_jspx_meth_html_005fradio_005f0(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  544 */                         out.write("<td class=\"bodytext\">");
/*  545 */                         out.print(FormatUtil.getString("am.mypage.select.monitorgroup.text"));
/*  546 */                         out.write("</td>\n\t\t\t\t\t\t\t  </td>\n\t\t\t\t\t\t         <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t <td class=\"bodytext\">\n                                                          ");
/*  547 */                         if (_jspx_meth_html_005fradio_005f1(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  549 */                         out.write("<td  class=\"bodytext\">");
/*  550 */                         out.print(FormatUtil.getString("am.mypage.selectall.mgs.text"));
/*  551 */                         out.write("</td>\n                                                          </td>\n\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t  </table>\n\t\t\t\t\t  \t\t  </div>\n\t\t\t\t\t  \t\t  ");
/*  552 */                         if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  554 */                         out.write("\n\t\t\t\t\t  \t\t  ");
/*  555 */                         if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                           return;
/*  557 */                         out.write("\n\t\t\t\t\t  \t\t      <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"60%\">\n\n\t\t\t\t\t\t\t      \t      ");
/*      */                         
/*  559 */                         java.util.ArrayList selectedlist = new java.util.ArrayList();
/*  560 */                         if (request.getAttribute("selectedMultiMgs_list") != null)
/*      */                         {
/*  562 */                           selectedlist = (java.util.ArrayList)request.getAttribute("selectedMultiMgs_list");
/*      */                         }
/*      */                         
/*  565 */                         out.write("\n\t\t\t\t\t\t\t      \t ");
/*      */                         
/*  567 */                         org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (org.apache.struts.taglib.logic.NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
/*  568 */                         _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  569 */                         _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                         
/*  571 */                         _jspx_th_logic_005fnotEmpty_005f0.setName("applications1");
/*  572 */                         int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  573 */                         if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                           for (;;) {
/*  575 */                             out.write("\n\t\t\t\t\t\t\t      \t     ");
/*      */                             
/*  577 */                             org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
/*  578 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  579 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                             
/*  581 */                             _jspx_th_logic_005fiterate_005f0.setName("applications1");
/*      */                             
/*  583 */                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                             
/*  585 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                             
/*  587 */                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*  588 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  589 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  590 */                               java.util.ArrayList row = null;
/*  591 */                               Integer j = null;
/*  592 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  593 */                                 out = _jspx_page_context.pushBody();
/*  594 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  595 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/*  597 */                               row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  598 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/*  600 */                                 out.write("\n\t\t\t\t\t\t\t      \t              ");
/*      */                                 
/*  602 */                                 String checked = "";
/*  603 */                                 String currentmg = (String)row.get(1);
/*  604 */                                 if (selectedlist.contains(currentmg))
/*      */                                 {
/*  606 */                                   checked = "checked=\"true\"";
/*      */                                 }
/*      */                                 
/*  609 */                                 out.write("\n\t\t\t\t\t\t\t      \t \t      <tr>\n\t\t\t\t\t\t\t\t\t\t<td>&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t      \t\t\t<td width=\"2%\" align=\"left\">\n\t\t\t\t\t\t\t      \t\t\t     <input type=\"checkbox\" name=\"selectedMultiMonitors\" ");
/*  610 */                                 out.print(checked);
/*  611 */                                 out.write(" value=\"");
/*  612 */                                 out.print((String)row.get(1));
/*  613 */                                 out.write("\" />\n\t\t\t\t\t\t\t      \t\t\t</td>\n\t\t\t\t\t\t\t      \t\t\t<td  >\n\t\t\t\t\t\t\t      \t\t\t<span class=\"bodytext\">&nbsp;");
/*  614 */                                 out.print((String)row.get(0));
/*  615 */                                 out.write("</span>\n\t\t\t\t\t\t\t      \t\t\t</td>\n\t\t\t\t\t\t\t      \t             </tr>\n\n\t\t\t\t\t\t\t      \t     ");
/*  616 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  617 */                                 row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  618 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*  619 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  622 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  623 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  626 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  627 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/*  630 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  631 */                             out.write("\n\t\t\t\t\t\t\t      \t ");
/*  632 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  633 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  637 */                         if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  638 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                         }
/*      */                         
/*  641 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  642 */                         out.write("\n\t\t\t\t\t\t\t            \t</select>\n \t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t  \t\t  </div>\n\t\t\t\t\t  \t\t  </td>\n\t\t\t\t\t\t\t  </tr>\n\t\t\t\t\t  \t\t  ");
/*  643 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  644 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  648 */                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  649 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                     }
/*      */                     
/*  652 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  653 */                     out.write("\n\t\t\t\t\t  \t\t   ");
/*  654 */                     out.write("\n\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</div>\n\n\t\t</div>\n\n\n\t");
/*  655 */                     request.setAttribute("Action", "createDashBoard");
/*  656 */                     out.write(10);
/*  657 */                     out.write(9);
/*      */                     
/*  659 */                     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  660 */                     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/*  661 */                     _jspx_th_c_005fif_005f12.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/*  663 */                     _jspx_th_c_005fif_005f12.setTest("${param.action!=\"editMyPage\"}");
/*  664 */                     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/*  665 */                     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                       for (;;) {
/*  667 */                         out.write(10);
/*  668 */                         out.write(10);
/*  669 */                         out.write(9);
/*      */                         
/*  671 */                         IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  672 */                         _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  673 */                         _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                         
/*  675 */                         _jspx_th_c_005fif_005f13.setTest("${MyPageForm.pageType==\"globalpage\" ||MyPageForm.pageType==\"mgtemplate\" || MyPageForm.pageType==\"businesspage\"}");
/*  676 */                         int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  677 */                         if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                           for (;;) {
/*  679 */                             out.write(10);
/*  680 */                             out.write(9);
/*  681 */                             request.setAttribute("invokedInline", "true");
/*  682 */                             out.write(10);
/*  683 */                             out.write(9);
/*  684 */                             out.write(10);
/*  685 */                             out.write("\n\n\n\n");
/*  686 */                             com.adventnet.appmanager.struts.beans.MyPageBean mygraph = null;
/*  687 */                             mygraph = (com.adventnet.appmanager.struts.beans.MyPageBean)_jspx_page_context.getAttribute("mygraph", 1);
/*  688 */                             if (mygraph == null) {
/*  689 */                               mygraph = new com.adventnet.appmanager.struts.beans.MyPageBean();
/*  690 */                               _jspx_page_context.setAttribute("mygraph", mygraph, 1);
/*      */                             }
/*  692 */                             out.write(10);
/*  693 */                             out.write(10);
/*  694 */                             response.setContentType("text/html;charset=UTF-8");
/*  695 */                             out.write(10);
/*  696 */                             out.write(10);
/*      */                             
/*  698 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  699 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  700 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  702 */                             _jspx_th_c_005fif_005f14.setTest("${Action!=\"createDashBoard\"}");
/*  703 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  704 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/*  706 */                                 out.write("\n<html>\n<head>\n<title>");
/*  707 */                                 out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/*  708 */                                 out.write("</title>\n</head>\n<body marginheight=0 marginwidth=0 leftmargin=0 topmargin=0 >\n");
/*  709 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  710 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  714 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  715 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/*  718 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  719 */                             out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*      */                             
/*      */ 
/*  722 */                             boolean isInvokedInline = request.getAttribute("invokedInline") != null;
/*  723 */                             if (!isInvokedInline)
/*      */                             {
/*  725 */                               out.write(10);
/*  726 */                               out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  727 */                               if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                 return;
/*  729 */                               out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  730 */                               out.write(10);
/*  731 */                               out.write("\n<script type=\"text/javascript\" src=\"/template/mootools.js\"></script>\n");
/*      */                             }
/*  733 */                             out.write("\n<script type=\"text/javascript\" src=\"/template/dhtmlTree/dhtmlXTree.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dhtmlTree/dhtmlXCommon.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"../template/appmanager.js\"></script>\n<script>\nvar http1;\nfunction validateAndSubmit()\n{\n");
/*      */                             
/*  735 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  736 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/*  737 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  739 */                             _jspx_th_c_005fif_005f15.setTest("${'editDashboard'== 'editDashboard'}");
/*  740 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/*  741 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/*  743 */                                 out.write("\n\n\tvar url=\"\";\t//No I18N\n\tvar pageid='");
/*  744 */                                 out.print(request.getAttribute("pageid"));
/*  745 */                                 out.write("';//No I18N\n\t");
/*      */                                 
/*  747 */                                 ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  748 */                                 _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  749 */                                 _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f15);
/*  750 */                                 int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  751 */                                 if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                   for (;;) {
/*  753 */                                     out.write(10);
/*  754 */                                     out.write(9);
/*      */                                     
/*  756 */                                     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  757 */                                     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  758 */                                     _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                     
/*  760 */                                     _jspx_th_c_005fwhen_005f3.setTest("${MyPageForm.pageType==\"businesspage\"}");
/*  761 */                                     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  762 */                                     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                       for (;;) {
/*  764 */                                         out.write("\n\t var selwidgets=\"\";//No I18N\n\t var length=document.MyPageForm.selectedWidgetsCheckbox.length;\n\t for(i=0;i<length;i++)\n\t {\n\t     if(document.MyPageForm.selectedWidgetsCheckbox[i].checked)\n\t     {\n\t     \tselwidgets=selwidgets+document.MyPageForm.selectedWidgetsCheckbox[i].value+','; //No I18N\n\t     }\n\t }\n         if(selwidgets=='')\n         {\n              alert(\"");
/*  765 */                                         out.print(FormatUtil.getString("am.mypage.widget.add.alert.text"));
/*  766 */                                         out.write("\");//No I18N\n\t      return false;//No I18N\n         }\n         url=\"/MyPage.do?method=addWidgets&pageid=\"+pageid+\"&selectedWidgets=\"+selwidgets+\"&randomnumber=\"+ Math.random();//No I18N\n\t");
/*  767 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  768 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  772 */                                     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  773 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                     }
/*      */                                     
/*  776 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  777 */                                     out.write(10);
/*  778 */                                     out.write(9);
/*      */                                     
/*  780 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  781 */                                     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  782 */                                     _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  783 */                                     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  784 */                                     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                       for (;;) {
/*  786 */                                         out.write("\n\tdocument.MyPageForm.selectedWidgets.value=tree.getAllCheckedLeafs();\n\tif(document.MyPageForm.selectedWidgets.value == \"\")\n\t{\n\t\talert(\"");
/*  787 */                                         out.print(FormatUtil.getString("am.mypage.widget.add.alert.text"));
/*  788 */                                         out.write("\");\n\t\treturn false;\n\t}\n\t//alert(\"test:\"+document.MyPageForm.selectedWidgets.value);\n\tvar selwidgets=document.MyPageForm.selectedWidgets.value;\n\turl=\"/MyPage.do?method=addWidgets&pageid=\"+pageid+\"&selectedWidgets=\"+selwidgets+\"&randomnumber=\"+ Math.random();\n\t");
/*  789 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  790 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  794 */                                     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  795 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                     }
/*      */                                     
/*  798 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  799 */                                     out.write(10);
/*  800 */                                     out.write(9);
/*  801 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  802 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  806 */                                 if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  807 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                 }
/*      */                                 
/*  810 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  811 */                                 out.write("\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange =function () { addSuccess();} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n\t//document.MyPageForm.submit();\n\t");
/*  812 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/*  813 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  817 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/*  818 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/*  821 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*  822 */                             out.write("\n}\nfunction addSuccess()\n{\nif(http1.readyState == 4)\n \t{\n       \t   if( http1.status == 200)\n\t   {\n   \t\t    \twindow.opener.location.hash = 'scrollWidget'\t\t//No I18N\n\t\t  \t\twindow.opener.location.reload();\n  \t\t\t\tdocument.getElementById(\"savediv\").style.display=\"block\";\n  \t   }\n  \t }\n}\nfunction widgetTree()\n{\n\ttree = new dhtmlXTreeObject(document.getElementById('widgetTree'),\"100%\",\"100%\",0);\n\ttree.setImagePath(\"/images/dhtmlTree/\");\n\ttree.enableCheckBoxes(1);\n \ttree.enableThreeStateCheckboxes(true);\n\ttree.setIconSize(1,1);\n\t//tree.setOnClickHandler(showWidgetImage);\n\ttree.loadXML(\"/webclient/");
/*  823 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                               return;
/*  825 */                             out.write("\"); //load root level from xml\n}\nfunction setTreeTDWidth()\n{\n\tvar containerWidth = document.getElementById(\"WidgetsDiv\").offsetWidth;\n\tdocument.getElementById('treeTd').style.width = containerWidth-360;\n}\n");
/*  826 */                             out.write(10);
/*  827 */                             if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                               return;
/*  829 */                             out.write("\n</script>\n<style>\n.header2 {\ncolor:#F26522;\nfont-family:Arial,Helvetica,sans-serif;\nfont-size:14px;\nfont-style:normal;\nfont-variant:normal;\nfont-weight:bold;\npadding:0px 3px;\ntext-decoration:none;\ntext-transform:none;\n}\n</style>\n");
/*      */                             
/*  831 */                             IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  832 */                             _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/*  833 */                             _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  835 */                             _jspx_th_c_005fif_005f21.setTest("${Action!=\"createDashBoard\"}");
/*  836 */                             int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/*  837 */                             if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                               for (;;) {
/*  839 */                                 out.write("\n<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/*  840 */                                 if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                                   return;
/*  842 */                                 out.write(32);
/*  843 */                                 out.write(58);
/*  844 */                                 out.write(32);
/*  845 */                                 out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/*  846 */                                 out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n\n</table>\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"5\" width=\"100%\">\n<tr>\n<td width=\"60%\"  valign=\"top\">\n\n");
/*  847 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/*  848 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  852 */                             if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/*  853 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                             }
/*      */                             
/*  856 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/*  857 */                             out.write("\n\n<div style=\"display: none;\" id=\"savediv\">\n            <table width=\"100%\" border=\"0\">\n              <tbody><tr align=\"center\">\n                <td class=\"bodytextbold\"><span style=\"font-weight: bold; color: rgb(0, 0, 0); font-size: 11px;\" id=\"saveresult\">");
/*  858 */                             out.print(FormatUtil.getString("am.mypage.widgets.add.success.text"));
/*  859 */                             out.write("</span></td>\n              </tr>\n            </tbody></table>\n          </div>\n\n\n");
/*  860 */                             out.write(10);
/*      */                             
/*  862 */                             IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  863 */                             _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/*  864 */                             _jspx_th_c_005fif_005f22.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  866 */                             _jspx_th_c_005fif_005f22.setTest("${MyPageForm.pageType!=\"businesspage\"}");
/*  867 */                             int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/*  868 */                             if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                               for (;;) {
/*  870 */                                 out.write("\n<div id=\"WidgetsDiv\" style=\"margin:10px;width:100%\">\n\t");
/*      */                                 
/*  872 */                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  873 */                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/*  874 */                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_c_005fif_005f22);
/*      */                                 
/*  876 */                                 _jspx_th_c_005fif_005f23.setTest("${Action!=\"createDashBoard\"}");
/*  877 */                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/*  878 */                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                   for (;;) {
/*  880 */                                     out.write("\n\t<form name=\"MyPageForm\" method=\"get\" action=\"/MyPage.do\">\n\t<input type=\"hidden\" name=\"pageid\" value=\"");
/*  881 */                                     out.print(request.getAttribute("pageid"));
/*  882 */                                     out.write("\" />\n\t<input type=\"hidden\" name=\"method\" value=\"addWidgets\"/>\n\t");
/*  883 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/*  884 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  888 */                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/*  889 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                 }
/*      */                                 
/*  892 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/*  893 */                                 out.write("\n\t<input type=\"hidden\" name=\"selectedWidgets\" value=\"\"/>\n\n\n\t  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"355\" ");
/*  894 */                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_c_005fif_005f22, _jspx_page_context))
/*      */                                   return;
/*  896 */                                 out.write(" style=\"margin-bottom: 40px;\"> ");
/*  897 */                                 out.write("\n\t\t  <tr>\n\t\t\t<td id=\"treeTd\"  align=\"left\" valign=\"top\">\n\t\t\t\t<div style=\"min-height:450px\" class=\"lrtbdarkborder\">\n\t\t\t\t<div class=\"tableheadingbborder\">");
/*  898 */                                 out.print(FormatUtil.getString("am.mypage.addwidgets.widgetlist.text"));
/*  899 */                                 out.write("</div>\n\t\t\t\t<div style=\"overflow-y: auto;padding:10px\" id=\"widgetTree\"></div>\n\t\t\t\t</div>\n\t\t\t</td>\n\n\t\t  </tr>\n\t  </table>\n        ");
/*  900 */                                 if (_jspx_meth_c_005fif_005f25(_jspx_th_c_005fif_005f22, _jspx_page_context))
/*      */                                   return;
/*  902 */                                 out.write("\n</div>\n");
/*  903 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/*  904 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  908 */                             if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/*  909 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                             }
/*      */                             
/*  912 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/*  913 */                             out.write(10);
/*  914 */                             out.write(10);
/*  915 */                             out.write(10);
/*  916 */                             out.write(10);
/*  917 */                             out.write(32);
/*      */                             
/*  919 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  920 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/*  921 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  923 */                             _jspx_th_c_005fif_005f26.setTest("${MyPageForm.pageType==\"businesspage\"}");
/*  924 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/*  925 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/*  927 */                                 out.write("\n <div id=\"WidgetsDiv\" style=\"margin-bottom: 10px;width:400px\">\n\t \t");
/*      */                                 
/*  929 */                                 IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  930 */                                 _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/*  931 */                                 _jspx_th_c_005fif_005f27.setParent(_jspx_th_c_005fif_005f26);
/*      */                                 
/*  933 */                                 _jspx_th_c_005fif_005f27.setTest("${Action!=\"createDashBoard\"}");
/*  934 */                                 int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/*  935 */                                 if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */                                   for (;;) {
/*  937 */                                     out.write("\n\t\t\t<form name=\"MyPageForm\" method=\"get\" action=\"/MyPage.do\">\n\t\t\t<input type=\"hidden\" name=\"pageid\" value=\"");
/*  938 */                                     out.print(request.getAttribute("pageid"));
/*  939 */                                     out.write("\" />\n\t\t\t<input type=\"hidden\" name=\"method\" value=\"addWidgets\"/>\n\t\t\t");
/*  940 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/*  941 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  945 */                                 if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/*  946 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*      */                                 }
/*      */                                 
/*  949 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/*  950 */                                 out.write("\n\t\t<input type=\"hidden\" name=\"selectedWidgets\" />\n\t \t<div style=\"margin:10px;width:100%\" >\n\t \t\t\t<div>\n\t \t\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"70\" ");
/*  951 */                                 if (_jspx_meth_c_005fif_005f28(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                   return;
/*  953 */                                 out.write(" style=\"margin-bottom: 10px;\">\n\t \t\t\t\t  <tr>\n\t \t\t\t\t\t<td  align=\"left\" valign=\"top\" width=\"100%\" style=\"width: 751px;\">\n\t \t\t\t\t\t\t<table class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"80%\">\n\t \t\t\t\t\t\t<tr>\n\t \t\t\t\t\t\t\t<td class=\"tableheadingbborder\"  style=\"width: 751px;\">\n\t \t\t\t\t\t\t\t");
/*  954 */                                 out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/*  955 */                                 out.write("\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\" >\n\t \t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t \t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"2\" >&nbsp;<span style='font-size:10pt;'>");
/*  956 */                                 out.print(FormatUtil.getString("am.webclient.mypage.widgettype.businessmetric"));
/*  957 */                                 out.write("</span\t>\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t \t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"1\" >&nbsp;<span style='font-size:10pt;'>");
/*  958 */                                 out.print(FormatUtil.getString("am.mypage.widgettypes.topn.monitors.text"));
/*  959 */                                 out.write("</span>\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr class=\"bodytext\">\n                                                                <td style=\"width: 751px;\">\n                                                                      <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"3\" >&nbsp;<span style='font-size:10pt'>");
/*  960 */                                 out.print(FormatUtil.getString("am.mypage.widgettypes.configurationdata.text"));
/*  961 */                                 out.write("</span>\n                                                              </td>\n                                                         </tr>\n\t\t\t\t\t\t\t<tr class=\"bodytext\">                    \n                                          \t\t\t<td style=\"width: 751px;\">\n                                                                      <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"301\" >&nbsp;<span style='font-size:10pt'>");
/*  962 */                                 out.print(FormatUtil.getString("am.mypage.widgettypes.url.include.text"));
/*  963 */                                 out.write("</span>\n                                                              </td>\n                                                         </tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"302\" >&nbsp;<span style='font-size:10pt;'>");
/*  964 */                                 out.print(FormatUtil.getString("am.mypage.widgettypes.bookmark.text"));
/*  965 */                                 out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"303\" >&nbsp;<span style='font-size:10pt;'>");
/*  966 */                                 out.print(FormatUtil.getString("am.mypage.widgettypes.customhtml.text"));
/*  967 */                                 out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t</table>\n\t \t\t\t\t\t</td>\n\n\t \t\t\t\t  </tr>\n\t \t\t\t  </table>\n\n\t \t\t\t</div>\n\t \t\t</div>\n\t         ");
/*  968 */                                 if (_jspx_meth_c_005fif_005f29(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                   return;
/*  970 */                                 out.write("\n\t </div>\n");
/*  971 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/*  972 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  976 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/*  977 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/*  980 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/*  981 */                             out.write(10);
/*  982 */                             out.write(10);
/*  983 */                             out.write(10);
/*      */                             
/*  985 */                             IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  986 */                             _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/*  987 */                             _jspx_th_c_005fif_005f30.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/*  989 */                             _jspx_th_c_005fif_005f30.setTest("${Action!=\"createDashBoard\"}");
/*  990 */                             int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/*  991 */                             if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                               for (;;) {
/*  993 */                                 out.write("\n<table width=\"100%\">\n\t <tr>\n\t<td align=\"center\">\n\n\t\t        ");
/*      */                                 
/*  995 */                                 org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  996 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  997 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f30);
/*      */                                 
/*  999 */                                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 1000 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1001 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 1003 */                                     out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:alertUser();\" value=\"&nbsp;&nbsp;");
/* 1004 */                                     out.print(FormatUtil.getString("am.mypage.addwidgets.add.button.text"));
/* 1005 */                                     out.write("&nbsp;&nbsp;\" >\n\t\t        ");
/* 1006 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1007 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1011 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1012 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 1015 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1016 */                                 out.write("\n\t\t        ");
/*      */                                 
/* 1018 */                                 org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/* 1019 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1020 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f30);
/*      */                                 
/* 1022 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 1023 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1024 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 1026 */                                     out.write("\n\t\t        <input type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:validateAndSubmit();\" value=\"&nbsp;&nbsp;");
/* 1027 */                                     out.print(FormatUtil.getString("am.mypage.addwidgets.add.button.text"));
/* 1028 */                                     out.write("&nbsp;&nbsp;\" >\n\t\t        ");
/* 1029 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1030 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1034 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1035 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 1038 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1039 */                                 out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:window.close();\" value=\"&nbsp;");
/* 1040 */                                 out.print(FormatUtil.getString("am.mypage.addwidgets.window.close.text"));
/* 1041 */                                 out.write("&nbsp;\" >\n\n\t</td>\n\t</tr>\n\t </table>\n\t");
/* 1042 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 1043 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1047 */                             if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 1048 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */                             }
/*      */                             
/* 1051 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 1052 */                             out.write(10);
/*      */                             
/* 1054 */                             IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1055 */                             _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 1056 */                             _jspx_th_c_005fif_005f31.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/* 1058 */                             _jspx_th_c_005fif_005f31.setTest("${Action!=\"createDashBoard\"}");
/* 1059 */                             int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 1060 */                             if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                               for (;;) {
/* 1062 */                                 out.write("\n</td>\n<td style=\"padding-top:9px;\"  width=\"40%\" valign=\"top\">\n<table width=\"95%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 1063 */                                 out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 1064 */                                 out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n      <tr>\n          <td class=\"txtSpace\">\n           ");
/* 1065 */                                 out.print(FormatUtil.getString("am.mypage.addwidgets.helpcard.header.text"));
/* 1066 */                                 out.write("\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\">\n                  ");
/* 1067 */                                 out.print(FormatUtil.getString("am.mypage.addwidgets.helpcard.text"));
/* 1068 */                                 out.write("\n            </td>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n</td>\n</tr>\n</table>\n\n</body>\n</html>\n");
/* 1069 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 1070 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1074 */                             if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 1075 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */                             }
/*      */                             
/* 1078 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 1079 */                             out.write(10);
/* 1080 */                             out.write(10);
/* 1081 */                             out.write(9);
/* 1082 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1083 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1087 */                         if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1088 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                         }
/*      */                         
/* 1091 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1092 */                         out.write("\n\n\n\t");
/* 1093 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 1094 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1098 */                     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 1099 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                     }
/*      */                     
/* 1102 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1103 */                     out.write("\n\n</div>\n      <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom lrbborder itadmin-no-decor\" style=\"height:32px;\">\n\t<tr>\n\t<td align=\"middle\">\n\t\t");
/*      */                     
/* 1105 */                     IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1106 */                     _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 1107 */                     _jspx_th_c_005fif_005f32.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 1109 */                     _jspx_th_c_005fif_005f32.setTest("${param.action==\"editMyPage\"}");
/* 1110 */                     int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 1111 */                     if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                       for (;;) {
/* 1113 */                         out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:submitForm();\" value=\"&nbsp;&nbsp;");
/* 1114 */                         out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 1115 */                         out.write("&nbsp;\" >\n\t\t");
/* 1116 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 1117 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1121 */                     if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 1122 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*      */                     }
/*      */                     
/* 1125 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 1126 */                     out.write(10);
/* 1127 */                     out.write(9);
/* 1128 */                     out.write(9);
/*      */                     
/* 1130 */                     IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1131 */                     _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 1132 */                     _jspx_th_c_005fif_005f33.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 1134 */                     _jspx_th_c_005fif_005f33.setTest("${param.action!=\"editMyPage\"}");
/* 1135 */                     int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 1136 */                     if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                       for (;;) {
/* 1138 */                         out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:submitForm();\" value=\"&nbsp;&nbsp;");
/* 1139 */                         out.print(FormatUtil.getString("am.mypage.dashboard.createnew.button.text"));
/* 1140 */                         out.write("&nbsp;\" >\n\t\t");
/* 1141 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 1142 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1146 */                     if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 1147 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*      */                     }
/*      */                     
/* 1150 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 1151 */                     out.write("\n\t\t\t&nbsp;<input type=\"button\" class=\"buttons btn_link\" onclick=\"javascript:history.back();\" value=\"");
/* 1152 */                     out.print(FormatUtil.getString("am.mypage.dashboard.cancel.button.text"));
/* 1153 */                     out.write("\" >\n\n\t</td>\n\t</tr>\n\t</table>\n<br>\n");
/*      */                     
/* 1155 */                     IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1156 */                     _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 1157 */                     _jspx_th_c_005fif_005f34.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 1159 */                     _jspx_th_c_005fif_005f34.setTest("${param.action==\"editMyPage\"}");
/* 1160 */                     int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 1161 */                     if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */                       for (;;) {
/* 1163 */                         out.write(10);
/* 1164 */                         out.write(10);
/* 1165 */                         out.write(10);
/* 1166 */                         if ((com.adventnet.appmanager.util.Constants.defaultDashboardIds != null) && (!com.adventnet.appmanager.util.Constants.defaultDashboardIds.contains(Integer.valueOf(Integer.parseInt(request.getParameter("pageid")))))) {
/* 1167 */                           out.write("\n\n\n<table border=\"0\" >\n<tr>\n<td class=\"bodytext\">\n");
/* 1168 */                           out.print(FormatUtil.getString("am.mypage.deletewidget.note"));
/* 1169 */                           out.write("\n</td>\n</table>\n\n");
/*      */                         }
/* 1171 */                         out.write(10);
/* 1172 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 1173 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1177 */                     if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 1178 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34); return;
/*      */                     }
/*      */                     
/* 1181 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 1182 */                     out.write("\n<br>\n<br>\n\n");
/* 1183 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1184 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1188 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1189 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/* 1192 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ftarget_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1193 */                 out.write(10);
/* 1194 */                 out.write(10);
/* 1195 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 1196 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 1199 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 1200 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 1203 */             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 1204 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */             }
/*      */             
/* 1207 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 1208 */             out.write("\n\n\n    ");
/* 1209 */             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 1211 */             out.write(10);
/* 1212 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 1213 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1217 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 1218 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */         }
/*      */         else {
/* 1221 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1222 */           out.write(10);
/*      */         }
/* 1224 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1225 */         out = _jspx_out;
/* 1226 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1227 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1228 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1231 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1237 */     PageContext pageContext = _jspx_page_context;
/* 1238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1240 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1241 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1242 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/* 1243 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1244 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1246 */         out.write(10);
/* 1247 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1248 */           return true;
/* 1249 */         out.write(10);
/* 1250 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1251 */           return true;
/* 1252 */         out.write(10);
/* 1253 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1254 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1258 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1259 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1260 */       return true;
/*      */     }
/* 1262 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1268 */     PageContext pageContext = _jspx_page_context;
/* 1269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1271 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1272 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1273 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1275 */     _jspx_th_c_005fwhen_005f0.setTest("${MyPageForm.pageType==\"businesspage\"}");
/* 1276 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1277 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1279 */         out.write("\n\n\tvar length=document.MyPageForm.selectedWidgetsCheckbox.length;\n\t for(i=0;i<length;i++)\n\t {\n\t     if(document.MyPageForm.selectedWidgetsCheckbox[i].checked)\n\t     {\n\t\t\tdocument.MyPageForm.selectedWidgets.value=document.MyPageForm.selectedWidgets.value+document.MyPageForm.selectedWidgetsCheckbox[i].value+\",\";//No I18N\n\t     }\n\t }\n\t//alert(\"selectedWidgets\"+document.MyPageForm.selectedWidgetsCheckbox[0].checked);\n");
/* 1280 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1281 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1285 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1286 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1287 */       return true;
/*      */     }
/* 1289 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1290 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1295 */     PageContext pageContext = _jspx_page_context;
/* 1296 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1298 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1299 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1300 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1301 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1302 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1304 */         out.write("\n\n\n\tdocument.MyPageForm.selectedWidgets.value=tree.getAllCheckedLeafs();\n\t//alert(document.MyPageForm.selectedWidgets.value);\n");
/* 1305 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1306 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1310 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1311 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1312 */       return true;
/*      */     }
/* 1314 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1320 */     PageContext pageContext = _jspx_page_context;
/* 1321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1323 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1324 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1325 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 1327 */     _jspx_th_c_005fif_005f1.setTest("${param.action!=\"editMyPage\"}");
/* 1328 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1329 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1331 */         out.write("\n        document.location.href='/MyPage.do?method=newMyPage&pagetype='+pagetype+'&displayName='+pagename;\n");
/* 1332 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1333 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1337 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1338 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1339 */       return true;
/*      */     }
/* 1341 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1347 */     PageContext pageContext = _jspx_page_context;
/* 1348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1350 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1351 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1352 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 1354 */     _jspx_th_c_005fif_005f2.setTest("${param.action==\"editMyPage\" && MyPageForm.pageType!=\"businesspage\"}");
/* 1355 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1356 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 1358 */         out.write("\ndocument.location.href='/MyPage.do?method=editMyPage&pagetype='+pagetype+'&pageid='+");
/* 1359 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 1360 */           return true;
/* 1361 */         out.write(";\n//var templateselected=document.MyPageForm.mgtemplatepage.checked;//No I18N\n//if(templateselected)\n//{\n//document.getElementById(\"allmgrow\").style.display=\"inline\";//No I18N\n//document.MyPageForm.allMGsSelectedForTemplatePage[0].checked=true;//No I18N\n//}\n//else\n//{\n//document.getElementById(\"allmgrow\").style.display=\"none\";//No I18N\n//document.getElementById(\"monitorGroupsForTemplate\").style.display=\"none\";//No I18N\n//}\n\n");
/* 1362 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1363 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1367 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1368 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1369 */       return true;
/*      */     }
/* 1371 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1377 */     PageContext pageContext = _jspx_page_context;
/* 1378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1380 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1381 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1382 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1384 */     _jspx_th_c_005fout_005f0.setValue("${param.pageid}");
/* 1385 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1386 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1387 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1388 */       return true;
/*      */     }
/* 1390 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1396 */     PageContext pageContext = _jspx_page_context;
/* 1397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1399 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1400 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 1401 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1403 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 1405 */     _jspx_th_tiles_005fput_005f0.setValue("My Page");
/* 1406 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 1407 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 1408 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 1409 */       return true;
/*      */     }
/* 1411 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 1412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1417 */     PageContext pageContext = _jspx_page_context;
/* 1418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1420 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 1421 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 1422 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1424 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 1426 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 1427 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 1428 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 1429 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 1430 */       return true;
/*      */     }
/* 1432 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 1433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1438 */     PageContext pageContext = _jspx_page_context;
/* 1439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1441 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 1442 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 1443 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 1445 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 1447 */     _jspx_th_tiles_005fput_005f2.setType("string");
/* 1448 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 1449 */     if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 1450 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 1451 */         out = _jspx_page_context.pushBody();
/* 1452 */         _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1453 */         _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1456 */         out.write(" &nbsp;\t\t\n\t");
/* 1457 */         int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 1458 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1461 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 1462 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1465 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 1466 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1467 */       return true;
/*      */     }
/* 1469 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1470 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1475 */     PageContext pageContext = _jspx_page_context;
/* 1476 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1478 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1479 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1480 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 1482 */     _jspx_th_c_005fif_005f3.setTest("${param.action==\"editMyPage\"}");
/* 1483 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1484 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1486 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 1487 */           return true;
/* 1488 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1489 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1493 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1494 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1495 */       return true;
/*      */     }
/* 1497 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1503 */     PageContext pageContext = _jspx_page_context;
/* 1504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1506 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1507 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1508 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1510 */     _jspx_th_c_005fout_005f1.setValue("${MyPageForm.displayName}");
/* 1511 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1512 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1513 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1514 */       return true;
/*      */     }
/* 1516 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1522 */     PageContext pageContext = _jspx_page_context;
/* 1523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1525 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 1526 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 1527 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1529 */     _jspx_th_html_005fhidden_005f0.setProperty("pageType");
/* 1530 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 1531 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 1532 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1533 */       return true;
/*      */     }
/* 1535 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1541 */     PageContext pageContext = _jspx_page_context;
/* 1542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1544 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1545 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1546 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1548 */     _jspx_th_c_005fif_005f5.setTest("${param.action==\"editMyPage\"}");
/* 1549 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1550 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 1552 */         out.write(10);
/* 1553 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 1554 */           return true;
/* 1555 */         out.write(10);
/* 1556 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1557 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1561 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1562 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1563 */       return true;
/*      */     }
/* 1565 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1571 */     PageContext pageContext = _jspx_page_context;
/* 1572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1574 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 1575 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 1576 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1578 */     _jspx_th_html_005fhidden_005f1.setProperty("selectedTab");
/* 1579 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 1580 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 1581 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1582 */       return true;
/*      */     }
/* 1584 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1585 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1590 */     PageContext pageContext = _jspx_page_context;
/* 1591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1593 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1594 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1595 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1597 */     _jspx_th_c_005fif_005f6.setTest("${action != 'addWidgets'}");
/* 1598 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1599 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 1601 */         out.write("class=\"tableOne itadmin-no-decor\"");
/* 1602 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1603 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1607 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1608 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1609 */       return true;
/*      */     }
/* 1611 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1617 */     PageContext pageContext = _jspx_page_context;
/* 1618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1620 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1621 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1622 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1624 */     _jspx_th_c_005fout_005f2.setValue("${fromMg}");
/*      */     
/* 1626 */     _jspx_th_c_005fout_005f2.setDefault("${\"false\"}");
/* 1627 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1628 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1629 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1630 */       return true;
/*      */     }
/* 1632 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1638 */     PageContext pageContext = _jspx_page_context;
/* 1639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1641 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1642 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1643 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1645 */     _jspx_th_c_005fout_005f3.setValue("${templateResid}");
/* 1646 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1647 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1648 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1649 */       return true;
/*      */     }
/* 1651 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1657 */     PageContext pageContext = _jspx_page_context;
/* 1658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1660 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1661 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1662 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1664 */     _jspx_th_c_005fout_005f4.setValue("${param.action}");
/* 1665 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1666 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1667 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1668 */       return true;
/*      */     }
/* 1670 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1676 */     PageContext pageContext = _jspx_page_context;
/* 1677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1679 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1680 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1681 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1683 */     _jspx_th_c_005fif_005f7.setTest("${param.action==\"editMyPage\"}");
/* 1684 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1685 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 1687 */         out.write("\n\t<input type=\"hidden\" name=\"pageid\" value=\"");
/* 1688 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 1689 */           return true;
/* 1690 */         out.write("\"/>\n        ");
/* 1691 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1692 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1696 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1697 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1698 */       return true;
/*      */     }
/* 1700 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1706 */     PageContext pageContext = _jspx_page_context;
/* 1707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1709 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1710 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1711 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1713 */     _jspx_th_c_005fout_005f5.setValue("${param.pageid}");
/* 1714 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1715 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1716 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1717 */       return true;
/*      */     }
/* 1719 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1725 */     PageContext pageContext = _jspx_page_context;
/* 1726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1728 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1729 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 1730 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1732 */     _jspx_th_html_005ftext_005f0.setProperty("displayName");
/*      */     
/* 1734 */     _jspx_th_html_005ftext_005f0.setSize("30");
/*      */     
/* 1736 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext normal");
/* 1737 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 1738 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 1739 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1740 */       return true;
/*      */     }
/* 1742 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1748 */     PageContext pageContext = _jspx_page_context;
/* 1749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1751 */     org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f0 = (org.apache.struts.taglib.html.SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(org.apache.struts.taglib.html.SelectTag.class);
/* 1752 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1753 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1755 */     _jspx_th_html_005fselect_005f0.setOnchange("fillDefaultColumnWidth()");
/*      */     
/* 1757 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext msmall");
/*      */     
/* 1759 */     _jspx_th_html_005fselect_005f0.setProperty("numberOfColumns");
/* 1760 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1761 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1762 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1763 */         out = _jspx_page_context.pushBody();
/* 1764 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1765 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1768 */         out.write("\n\t\t\t\t\t  \t\t\t        ");
/* 1769 */         if (_jspx_meth_html_005foption_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1770 */           return true;
/* 1771 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1772 */         if (_jspx_meth_html_005foption_005f1(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1773 */           return true;
/* 1774 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1775 */         if (_jspx_meth_html_005foption_005f2(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1776 */           return true;
/* 1777 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1778 */         if (_jspx_meth_html_005foption_005f3(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1779 */           return true;
/* 1780 */         out.write("\n\t\t\t\t\t\t\t\t     ");
/* 1781 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1782 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1785 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1786 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1789 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1790 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1791 */       return true;
/*      */     }
/* 1793 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1799 */     PageContext pageContext = _jspx_page_context;
/* 1800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1802 */     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1803 */     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1804 */     _jspx_th_html_005foption_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1806 */     _jspx_th_html_005foption_005f0.setValue("1");
/* 1807 */     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1808 */     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1809 */       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1810 */         out = _jspx_page_context.pushBody();
/* 1811 */         _jspx_th_html_005foption_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1812 */         _jspx_th_html_005foption_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1815 */         out.write(49);
/* 1816 */         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1817 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1820 */       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1821 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1824 */     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1825 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1826 */       return true;
/*      */     }
/* 1828 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f1(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1834 */     PageContext pageContext = _jspx_page_context;
/* 1835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1837 */     OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1838 */     _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1839 */     _jspx_th_html_005foption_005f1.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1841 */     _jspx_th_html_005foption_005f1.setValue("2");
/* 1842 */     int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1843 */     if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1844 */       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1845 */         out = _jspx_page_context.pushBody();
/* 1846 */         _jspx_th_html_005foption_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1847 */         _jspx_th_html_005foption_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1850 */         out.write(50);
/* 1851 */         int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1852 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1855 */       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1856 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1859 */     if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1860 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1861 */       return true;
/*      */     }
/* 1863 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f2(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1869 */     PageContext pageContext = _jspx_page_context;
/* 1870 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1872 */     OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1873 */     _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 1874 */     _jspx_th_html_005foption_005f2.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1876 */     _jspx_th_html_005foption_005f2.setValue("3");
/* 1877 */     int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 1878 */     if (_jspx_eval_html_005foption_005f2 != 0) {
/* 1879 */       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1880 */         out = _jspx_page_context.pushBody();
/* 1881 */         _jspx_th_html_005foption_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1882 */         _jspx_th_html_005foption_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1885 */         out.write(51);
/* 1886 */         int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 1887 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1890 */       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1891 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1894 */     if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 1895 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1896 */       return true;
/*      */     }
/* 1898 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f3(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1904 */     PageContext pageContext = _jspx_page_context;
/* 1905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1907 */     OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1908 */     _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 1909 */     _jspx_th_html_005foption_005f3.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1911 */     _jspx_th_html_005foption_005f3.setValue("4");
/* 1912 */     int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 1913 */     if (_jspx_eval_html_005foption_005f3 != 0) {
/* 1914 */       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1915 */         out = _jspx_page_context.pushBody();
/* 1916 */         _jspx_th_html_005foption_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1917 */         _jspx_th_html_005foption_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1920 */         out.write(52);
/* 1921 */         int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 1922 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1925 */       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1926 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1929 */     if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 1930 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 1931 */       return true;
/*      */     }
/* 1933 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 1934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1939 */     PageContext pageContext = _jspx_page_context;
/* 1940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1942 */     org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
/* 1943 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 1944 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1946 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 1948 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea default");
/*      */     
/* 1950 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/* 1951 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 1952 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 1953 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1954 */       return true;
/*      */     }
/* 1956 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1962 */     PageContext pageContext = _jspx_page_context;
/* 1963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1965 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.get(TextTag.class);
/* 1966 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1967 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1969 */     _jspx_th_html_005ftext_005f1.setProperty("columnWidth1");
/*      */     
/* 1971 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext msmall");
/*      */     
/* 1973 */     _jspx_th_html_005ftext_005f1.setOnkeypress("return checkIsNumeric(event)");
/* 1974 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1975 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1976 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1977 */       return true;
/*      */     }
/* 1979 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1985 */     PageContext pageContext = _jspx_page_context;
/* 1986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1988 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.get(TextTag.class);
/* 1989 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1990 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1992 */     _jspx_th_html_005ftext_005f2.setProperty("columnWidth2");
/*      */     
/* 1994 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext msmall");
/*      */     
/* 1996 */     _jspx_th_html_005ftext_005f2.setOnkeypress("return checkIsNumeric(event)");
/* 1997 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1998 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1999 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2000 */       return true;
/*      */     }
/* 2002 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2008 */     PageContext pageContext = _jspx_page_context;
/* 2009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2011 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.get(TextTag.class);
/* 2012 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2013 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2015 */     _jspx_th_html_005ftext_005f3.setProperty("columnWidth3");
/*      */     
/* 2017 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext msmall");
/*      */     
/* 2019 */     _jspx_th_html_005ftext_005f3.setDisabled(false);
/*      */     
/* 2021 */     _jspx_th_html_005ftext_005f3.setOnkeypress("return checkIsNumeric(event)");
/* 2022 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2023 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.get(TextTag.class);
/* 2037 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2040 */     _jspx_th_html_005ftext_005f4.setProperty("columnWidth4");
/*      */     
/* 2042 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext msmall");
/*      */     
/* 2044 */     _jspx_th_html_005ftext_005f4.setDisabled(false);
/*      */     
/* 2046 */     _jspx_th_html_005ftext_005f4.setOnkeypress("return checkIsNumeric(event)");
/* 2047 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2048 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2049 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2050 */       return true;
/*      */     }
/* 2052 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeypress_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2058 */     PageContext pageContext = _jspx_page_context;
/* 2059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2061 */     org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
/* 2062 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 2063 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2065 */     _jspx_th_html_005fcheckbox_005f0.setProperty("mgtemplatepage");
/*      */     
/* 2067 */     _jspx_th_html_005fcheckbox_005f0.setValue("mgtemplate");
/*      */     
/* 2069 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:getNewPagewithRelevantWidgets()");
/* 2070 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 2071 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 2072 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 2073 */       return true;
/*      */     }
/* 2075 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 2076 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2081 */     PageContext pageContext = _jspx_page_context;
/* 2082 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2084 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2085 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2086 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2088 */     _jspx_th_c_005fset_005f0.setVar("style");
/*      */     
/* 2090 */     _jspx_th_c_005fset_005f0.setValue("display:none");
/* 2091 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2092 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2093 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2094 */       return true;
/*      */     }
/* 2096 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2102 */     PageContext pageContext = _jspx_page_context;
/* 2103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2105 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2106 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2107 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2109 */     _jspx_th_c_005fset_005f2.setVar("style");
/*      */     
/* 2111 */     _jspx_th_c_005fset_005f2.setValue("display:inline");
/* 2112 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2113 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2114 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 2115 */       return true;
/*      */     }
/* 2117 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 2118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2123 */     PageContext pageContext = _jspx_page_context;
/* 2124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2126 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2127 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2128 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2130 */     _jspx_th_c_005fout_005f6.setValue("${labelforselectmg}");
/* 2131 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2132 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2133 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2134 */       return true;
/*      */     }
/* 2136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2142 */     PageContext pageContext = _jspx_page_context;
/* 2143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2145 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2146 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2147 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2149 */     _jspx_th_c_005fout_005f7.setValue("${allMGsSelectedForTemplatePage}");
/* 2150 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2151 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2152 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2153 */       return true;
/*      */     }
/* 2155 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2161 */     PageContext pageContext = _jspx_page_context;
/* 2162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2164 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2165 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2166 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2168 */     _jspx_th_c_005fout_005f8.setEscapeXml("false");
/*      */     
/* 2170 */     _jspx_th_c_005fout_005f8.setValue("${pageScope.style}");
/* 2171 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2172 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2173 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2174 */       return true;
/*      */     }
/* 2176 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2182 */     PageContext pageContext = _jspx_page_context;
/* 2183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2185 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 2186 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 2187 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2189 */     _jspx_th_html_005fradio_005f0.setProperty("allMGsSelectedForTemplatePage");
/*      */     
/* 2191 */     _jspx_th_html_005fradio_005f0.setValue("0");
/*      */     
/* 2193 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:getMonitorGroupsforTemplate(this.value);");
/* 2194 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 2195 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 2196 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 2197 */       return true;
/*      */     }
/* 2199 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 2200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2205 */     PageContext pageContext = _jspx_page_context;
/* 2206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2208 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 2209 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 2210 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2212 */     _jspx_th_html_005fradio_005f1.setProperty("allMGsSelectedForTemplatePage");
/*      */     
/* 2214 */     _jspx_th_html_005fradio_005f1.setValue("1");
/*      */     
/* 2216 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:getMonitorGroupsforTemplate(this.value);");
/* 2217 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 2218 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 2219 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 2220 */       return true;
/*      */     }
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 2223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2228 */     PageContext pageContext = _jspx_page_context;
/* 2229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2231 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2232 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2233 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2235 */     _jspx_th_c_005fif_005f10.setTest("${MyPageForm.allMGsSelectedForTemplatePage==\"0\"}");
/* 2236 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2237 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2239 */         out.write("\n\t\t\t\t\t  \t\t   <div id=\"monitorGroupsForTemplate\" style='");
/* 2240 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2241 */           return true;
/* 2242 */         out.write(";overflow: auto;   width: 100%; height: 100px;'>\n\t\t\t\t\t  \t\t  ");
/* 2243 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2248 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2249 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2250 */       return true;
/*      */     }
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2258 */     PageContext pageContext = _jspx_page_context;
/* 2259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2261 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2262 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2263 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2265 */     _jspx_th_c_005fout_005f9.setEscapeXml("false");
/*      */     
/* 2267 */     _jspx_th_c_005fout_005f9.setValue("${pageScope.style}");
/* 2268 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2269 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2270 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2271 */       return true;
/*      */     }
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2274 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2279 */     PageContext pageContext = _jspx_page_context;
/* 2280 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2282 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2283 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2284 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2286 */     _jspx_th_c_005fif_005f11.setTest("${MyPageForm.allMGsSelectedForTemplatePage==\"1\"}");
/* 2287 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2288 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2290 */         out.write("\n\t\t\t\t\t  \t\t  <div id=\"monitorGroupsForTemplate\"   style=\"display:none;overflow: auto;   width: 100%; height: 120px;\">\n\t\t\t\t\t  \t\t  ");
/* 2291 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2292 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2296 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2297 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2298 */       return true;
/*      */     }
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2306 */     PageContext pageContext = _jspx_page_context;
/* 2307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2309 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2310 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2311 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2313 */     _jspx_th_c_005fout_005f10.setValue("${selectedskin}");
/*      */     
/* 2315 */     _jspx_th_c_005fout_005f10.setDefault("${initParam.defaultSkin}");
/* 2316 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2317 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2318 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2319 */       return true;
/*      */     }
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2322 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2327 */     PageContext pageContext = _jspx_page_context;
/* 2328 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2330 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2331 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2332 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2334 */     _jspx_th_c_005fout_005f11.setValue("${widgetsFile}");
/* 2335 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2336 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2337 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2338 */       return true;
/*      */     }
/* 2340 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2341 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2346 */     PageContext pageContext = _jspx_page_context;
/* 2347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2349 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2350 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2351 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 2353 */     _jspx_th_c_005fif_005f16.setTest("${MyPageForm.pageType!=\"businesspage\"}");
/* 2354 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2355 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2357 */         out.write("\nwindow.addEvent('load',function(){\n\t");
/* 2358 */         if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2359 */           return true;
/* 2360 */         out.write(10);
/* 2361 */         out.write(9);
/* 2362 */         if (_jspx_meth_c_005fif_005f18(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2363 */           return true;
/* 2364 */         out.write(10);
/* 2365 */         out.write(9);
/* 2366 */         if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2367 */           return true;
/* 2368 */         out.write("\n});\n");
/* 2369 */         out.write(10);
/* 2370 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2371 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2375 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2376 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2377 */       return true;
/*      */     }
/* 2379 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2380 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2385 */     PageContext pageContext = _jspx_page_context;
/* 2386 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2388 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2389 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2390 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2392 */     _jspx_th_c_005fif_005f17.setTest("${action == 'createDashboard'}");
/* 2393 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2394 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 2396 */         out.write("\n\t\twidgetTree();\n\t\tenableColumnWidthBoxes();\n\t\tfillDefaultColumnWidth();\n\t\tsetTreeTDWidth();\n\t");
/* 2397 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2398 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2402 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2403 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2404 */       return true;
/*      */     }
/* 2406 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2412 */     PageContext pageContext = _jspx_page_context;
/* 2413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2415 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2416 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 2417 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2419 */     _jspx_th_c_005fif_005f18.setTest("${action == 'editDashboard'}");
/* 2420 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 2421 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 2423 */         out.write("\n\t\tenableColumnWidthBoxes();\n\t\t");
/* 2424 */         if (_jspx_meth_c_005fif_005f19(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 2425 */           return true;
/* 2426 */         out.write(10);
/* 2427 */         out.write(9);
/* 2428 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 2429 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2433 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 2434 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 2435 */       return true;
/*      */     }
/* 2437 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 2438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2443 */     PageContext pageContext = _jspx_page_context;
/* 2444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2446 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2447 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2448 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 2450 */     _jspx_th_c_005fif_005f19.setTest("${isDefault}");
/* 2451 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2452 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 2454 */         out.write(" document.DashboardForm.dashboardName.readOnly=true; ");
/* 2455 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2456 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2460 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2461 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2462 */       return true;
/*      */     }
/* 2464 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2470 */     PageContext pageContext = _jspx_page_context;
/* 2471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2473 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2474 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 2475 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2477 */     _jspx_th_c_005fif_005f20.setTest("${'true' == 'true'}");
/* 2478 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 2479 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 2481 */         out.write("\n\t\twidgetTree();\n\t\tsetTreeTDWidth();\n\t");
/* 2482 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 2483 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2487 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2488 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2489 */       return true;
/*      */     }
/* 2491 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2497 */     PageContext pageContext = _jspx_page_context;
/* 2498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2500 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2501 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2502 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 2504 */     _jspx_th_c_005fout_005f12.setValue("${pagename}");
/* 2505 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2506 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2507 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2508 */       return true;
/*      */     }
/* 2510 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2516 */     PageContext pageContext = _jspx_page_context;
/* 2517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2519 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2520 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 2521 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 2523 */     _jspx_th_c_005fif_005f24.setTest("${Action!=\"createDashBoard\"}");
/* 2524 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 2525 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 2527 */         out.write(" width=\"100%\" ");
/* 2528 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 2529 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2533 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 2534 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 2535 */       return true;
/*      */     }
/* 2537 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 2538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f25(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2543 */     PageContext pageContext = _jspx_page_context;
/* 2544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2546 */     IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2547 */     _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 2548 */     _jspx_th_c_005fif_005f25.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 2550 */     _jspx_th_c_005fif_005f25.setTest("${Action!=\"createDashBoard\"}");
/* 2551 */     int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 2552 */     if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */       for (;;) {
/* 2554 */         out.write("\n\t</form>\n\t");
/* 2555 */         int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 2556 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2560 */     if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 2561 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 2562 */       return true;
/*      */     }
/* 2564 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 2565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2570 */     PageContext pageContext = _jspx_page_context;
/* 2571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2573 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2574 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 2575 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 2577 */     _jspx_th_c_005fif_005f28.setTest("${Action!=\"createDashBoard\"}");
/* 2578 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 2579 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 2581 */         out.write(" width=\"100%\" ");
/* 2582 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 2583 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2587 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 2588 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 2589 */       return true;
/*      */     }
/* 2591 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 2592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2597 */     PageContext pageContext = _jspx_page_context;
/* 2598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2600 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2601 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 2602 */     _jspx_th_c_005fif_005f29.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 2604 */     _jspx_th_c_005fif_005f29.setTest("${Action!=\"createDashBoard\"}");
/* 2605 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 2606 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 2608 */         out.write("\n\t \t</form>\n\t \t");
/* 2609 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 2610 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2614 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 2615 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 2616 */       return true;
/*      */     }
/* 2618 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 2619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2624 */     PageContext pageContext = _jspx_page_context;
/* 2625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2627 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2628 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2629 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2631 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 2633 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 2634 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2635 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2636 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2637 */       return true;
/*      */     }
/* 2639 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2640 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyPage_005fNew_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */