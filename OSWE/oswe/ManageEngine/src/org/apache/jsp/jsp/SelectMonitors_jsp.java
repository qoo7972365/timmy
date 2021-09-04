/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class SelectMonitors_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  40 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  52 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  58 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  59 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  60 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  69 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  72 */     JspWriter out = null;
/*  73 */     Object page = this;
/*  74 */     JspWriter _jspx_out = null;
/*  75 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  79 */       response.setContentType("text/html;charset=UTF-8");
/*  80 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  82 */       _jspx_page_context = pageContext;
/*  83 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  84 */       ServletConfig config = pageContext.getServletConfig();
/*  85 */       session = pageContext.getSession();
/*  86 */       out = pageContext.getOut();
/*  87 */       _jspx_out = out;
/*     */       
/*  89 */       out.write("<!-- $Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  90 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  92 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/*     */       
/*  94 */       String haid = request.getParameter("haid");
/*  95 */       String divName = request.getParameter("divName");
/*  96 */       String countVsSeverity = request.getParameter("countSeverity");
/*     */       
/*  98 */       String tab = request.getParameter("tab");
/*  99 */       String tabSelected = "am.webclient.monitorgroup.alarmrule.associatedmonitorstab.heading.text";
/* 100 */       tab = (tab == null) || (tab.trim().length() == 0) ? "1" : tab;
/* 101 */       tabSelected = tab.equals("2") ? "am.webclient.monitorgroup.alarmrule.nonassociatedmonitorstab.heading.text" : tabSelected;
/*     */       
/* 103 */       out.write("\n\n<script>\nvar countVsSeverity=\"");
/* 104 */       out.print(countVsSeverity);
/* 105 */       out.write("\"; //No I18N\nvar isAjax=false;\nvar isNodeList=true;\nvar selected=window.opener.document.getElementsByName(\"selectedMonitors\"+countVsSeverity); //No I18N\n\nfunction checkAndSubmit(str)\n{\n\tvar selectedElementsName=\"\"; //No I18N\n\tvar selectedElementsId=\"\" //No I18N\n\tvar imagepaths=\"\"; //No I18N\n\tvar selectedMOs=document.frmMOSelection.selectedMonitors;\n\tvar selectedMONames=document.frmMOSelection.names;\n\tvar selectedMOImgPath=document.frmMOSelection.imagePaths;\n\t\n\tif(isAjax)\n\t{\n\t\tselectedMOs=document.frmMOSelection.selectedMonitorsFromAjax;\n\t\tselectedMONames=document.frmMOSelection.namesFromAjax;\n\t\tselectedMOImgPath=document.frmMOSelection.imagePathsFromAjax;\n\t}\n\tif(selectedMOs[0]==undefined)\n\t{\n\t\tselectedElementsId=selectedMOs.value;\n\t\tselectedElementsName=selectedMONames.value;\n\t\timagepaths=selectedMOImgPath.value;\n\t}\n\telse\n\t{\n\t\tfor(i=0;i<selectedMOs.length;i++)\n\t\t{\n\t\t\tif(selectedMOs[i].checked)\n\t\t\t{\n\t\t\t\tselectedElementsId+=selectedMOs[i].value+\",\"; //No I18N\n\t\t\t\tselectedElementsName+=selectedMONames[i].value+\",\"; //No I18N\n");
/* 106 */       out.write("\t\t\t\timagepaths+=selectedMOImgPath[i].value+\",\"; //No I18N\n\t\t\t}\n\t\t}\n\t}\n\t\n\tif(selectedElementsId.trim()==\"\")\n\t{\n\t\tif(str=='cancel')\n\t\t{\n\t\t\tif(selected.length<=0)\n\t\t\t{\n\t\t\t\twindow.opener.document.getElementById(\"selectMonType");
/* 107 */       out.print(countVsSeverity);
/* 108 */       out.write("\")[0].selected=true;\n\t\t\t\twindow.opener.loadTextBox('");
/* 109 */       out.print(countVsSeverity);
/* 110 */       out.write("');\n\t\t\t\twindow.opener.document.getElementById(\"count");
/* 111 */       out.print(countVsSeverity);
/* 112 */       out.write("\").value=1;\n\t\t\t}\n\t\t\twindow.close();\n\t\t\treturn;\n\t\t}\n\t\telse\n\t\t{\n\t\t\talert('");
/* 113 */       out.print(FormatUtil.getString("am.webclient.hostResource.associate.alert1.text"));
/* 114 */       out.write("');\n\t\t\treturn;\n\t\t}\n\t} else if(str=='cancel') { //No I18N\n\t\twindow.close();\n\t\treturn;\t\t\n\t}\n\t\n\t\t\n\t\n\tvar selectMOType=0;\n\tfor(i=0;i<document.frmMOSelection.radiobutton.length;i++)\n\t{\n\t\tif(document.frmMOSelection.radiobutton[i].checked==true)\n\t\t{\n\t\t\tselectMOType=document.frmMOSelection.radiobutton[i].value; //No I18N\n\t\t}\n\t}\n\t\n\n\tif(selectMOType==0)\n\t{\n\t\tvar validationResult=validateStringForAlpahbetandNegativeValues(document.frmMOSelection.moCount.value);\n\t\tif(validationResult!='success')\n\t\t{\n\t\t\tif(validationResult=='string')\n\t\t\t{\n\t\t\t\talert('");
/* 115 */       out.print(FormatUtil.getString("am.webclient.rule.nonnumericalert"));
/* 116 */       out.write("');//no i18n\n\t\t\t}\n\t\t\telse if(validationResult=='negative')\n\t\t\t{\n\t\t\t\talert('");
/* 117 */       out.print(FormatUtil.getString("am.webclient.rule.zeroconfiguration.alert"));
/* 118 */       out.write("');//no i18n\n\t\t\t}\n\t\t\tdocument.frmMOSelection.moCount.focus();\n\t\t\treturn;\n\t\t}\n\t\twindow.opener.document.getElementById(\"selectedMonitorCount");
/* 119 */       out.print(countVsSeverity);
/* 120 */       out.write("\").value=document.frmMOSelection.moCount.value;\t\t\t\n\t\twindow.opener.document.getElementById(\"moCount");
/* 121 */       out.print(countVsSeverity);
/* 122 */       out.write("\").value=document.frmMOSelection.moCount.value;\n\n\t}\n\telse\n\t{\n\t\twindow.opener.document.getElementById(\"selectedMonitorCount");
/* 123 */       out.print(countVsSeverity);
/* 124 */       out.write("\").value=\"-1\";\n\t\twindow.opener.document.getElementById(\"moCount\"+countVsSeverity).value=\"-1\";\n\n\t}\n\n\t\n\twindow.opener.document.getElementById(\"selectedMonitorType");
/* 125 */       out.print(countVsSeverity);
/* 126 */       out.write("\").value=selectMOType;\n\twindow.opener.document.getElementById(\"selectedMon");
/* 127 */       out.print(countVsSeverity);
/* 128 */       out.write("\").innerHTML=buildDiv(selectedElementsId,selectedElementsName,imagepaths,countVsSeverity); //No I18N\n        window.opener.document.getElementById(\"selectedMon");
/* 129 */       out.print(countVsSeverity);
/* 130 */       out.write("\").style.display=\"block\"; //No I18N\n        window.close();\n}\n\n\n\nfunction invokeOperations()\n{\n\tURL=\"/showresource.do?method=selectMonitorFilter&haid=");
/* 131 */       out.print(haid);
/* 132 */       out.write("&attributetype=");
/* 133 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 135 */       out.write("&type=\"+document.getElementById(\"montypes\").value; //No I18N\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = getOperationsOutput;\n\thttp1.open(\"GET\", URL, true);\n\thttp1.send(null);\n\treturn false;\t\n}\n\nfunction getOperationsOutput()\n{\n\tif(http1.readyState == 4 && http1.status == 200)\n\t{\n\t\tdocument.getElementById(\"defaultMonitors\").innerHTML=http1.responseText;//No I18N\n\t\tisAjax=true;\n\t\tcheckSelectedMonitorsAjax()\n\t}\n}\n\nfunction ToggleAllElements(e,t)\n{\n\tif(isAjax)\n\t{\n\t\tt=document.frmMOSelection.selectedMonitorsFromAjax;\n\t}\n\tif(t[0]==undefined)\n\t{\n\t\tisNodeList=false;\n\t\tt.checked=true;\n\t}\n\telse\n\t{\n\t\tfor(i=0; i<t.length; i++) \n\t\t{\n\t\t\tt[i].checked=e.checked; \n\t\t}\n\t}\n}\n\nfunction checkSelectedMonitors()\n{\n\tfor(i=0;i<document.frmMOSelection.selectedMonitors.length;i++)\n\t{\n\t\tdocument.frmMOSelection.selectedMonitors[i];\n\t\tfor(j=0;j<selected.length;j++)\n\t\t{\n\t\t\tif(selected[j].value==document.frmMOSelection.selectedMonitors[i].value)\n\t\t\t{\n\t\t\t\tdocument.frmMOSelection.selectedMonitors[i].checked=true;\n\t\t\t}\n\t\t}\n\t}\n}\n\nfunction checkSelectedMonitorsAjax()\n");
/* 136 */       out.write("{\n\tif(document.frmMOSelection.selectedMonitorsFromAjax[0]!=undefined)\n\t{\n\t\tfor(i=0;i<document.frmMOSelection.selectedMonitorsFromAjax.length;i++)\n\t\t{\n\t\t\tdocument.frmMOSelection.selectedMonitorsFromAjax[i];\n\t\t\tfor(j=0;j<selected.length;j++)\n\t\t\t{\n\t\t\t\tif(selected[j].value==document.frmMOSelection.selectedMonitorsFromAjax[i].value)\n\t\t\t\t{\n\t\t\t\t\tdocument.frmMOSelection.selectedMonitorsFromAjax[i].checked=true;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\telse\n\t{\n\t\tfor(j=0;j<selected.length;j++)\n\t\t{\n\t\t\tif(selected[j].value==document.frmMOSelection.selectedMonitorsFromAjax.value)\n\t\t\t{\n\t\t\t\tdocument.frmMOSelection.selectedMonitorsFromAjax.checked=true;\n\t\t\t}\n\t\t}\n\t}\n}\n\nfunction disableTextBox(ele)\n{\n\tele.disabled=true;\n}\n\nfunction enableTextBox(ele)\n{\n\tele.disabled=false;\n}\n\nfunction SetValues()\n{\n\tif(window.opener.document.getElementById(\"moCount\"+countVsSeverity)==null)\n\t{\n\t\treturn;\n\t}\n\tdocument.frmMOSelection.moCount.value=window.opener.document.getElementById(\"moCount\"+countVsSeverity).value;\n\tdocument.frmMOSelection.radiobutton[1].checked=true;\n");
/* 137 */       out.write("\tif(document.frmMOSelection.moCount.value==-1)\n\t{\n\t\tdocument.frmMOSelection.radiobutton[0].checked=true;\n\t\tdocument.frmMOSelection.moCount.disabled=true;\t\n\t\tdocument.frmMOSelection.moCount.value=1;\t\n\t}\n}\n</script>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<form name='frmMOSelection' action=\"/manageApplications.do\">\n\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 138 */       out.print(haid);
/* 139 */       out.write("\"/>\n\t<input type=\"hidden\" name=\"method\" value=\"addSelectedMonitors\"/>\n\t<input type='hidden' name=\"dependentMonitor\" value=\"-1\"/>\n\t<input type='hidden' name=\"attributetype\" value=\"");
/* 140 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/* 142 */       out.write("\"/>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n \t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 143 */       out.print(FormatUtil.getString("am.webclient.monitorgroup.alarmrule.select.monitor.title.text"));
/* 144 */       out.write("</span></td>\n \t</tr>\n</table>\t\n\n");
/*     */       
/* 146 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 147 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 148 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */       
/* 150 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 151 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 152 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */         for (;;) {
/* 154 */           out.write(10);
/*     */           
/* 156 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 157 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 158 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 160 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*     */           
/* 162 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.monitorgroup.alarmrule.select.monitor.title.text"));
/* 163 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 164 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 165 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*     */           }
/*     */           
/* 168 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 169 */           out.write(32);
/* 170 */           out.write(10);
/* 171 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 173 */           out.write(10);
/* 174 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 176 */           out.write(10);
/*     */           
/* 178 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 179 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 180 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 182 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*     */           
/* 184 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 185 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 186 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 187 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 188 */               out = _jspx_page_context.pushBody();
/* 189 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 190 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 193 */               out.write("\n<input type=\"hidden\" id=\"selectedTab\" id=\"selectedTab\" value=\"");
/* 194 */               out.print(tab);
/* 195 */               out.write("\">\n<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n<td valign=\"top\">\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n<tr>\n<td style=\"padding-top:3px;\" class=\"AlarmboxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tr>\n<td>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tr>\n<td class=\"AlarmInnerTopLeft\"/>\n<td class=\"AlarmInnerTopBg\"/>\n<td class=\"AlarmInnerTopRight\"/>\n</tr>\n<tr>\n\t<td  height=\"10\"><img src=\"../images/spacer.gif\" width=\"0\" height=\"10\"></td>\n</tr>\n<tr>\n\t<td colspan=3>\n\t \t<table width='100%' class=\"AlarmInnerBoxBg\">\n\t \t\t<tr>\n\t \t\t\t<td> \t \t\t\t\t\n\t \t\t\t\t<span class='bodytextbold'>&nbsp; ");
/* 196 */               out.print(FormatUtil.getString("am.webclient.filterby.text"));
/* 197 */               out.write(" &nbsp;</span>\n\t\t\t\t\t<select name=\"montypes\" id=\"montypes\" class=\"formtext\" onchange=\"javascript:invokeOperations()\">\n\t\t\t\t\t     <option selected=\"true\" value=\"selectactions\">");
/* 198 */               out.print(FormatUtil.getString("All Monitors"));
/* 199 */               out.write("</option>\n\t\t\t\t\t     ");
/*     */               
/* 201 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 202 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 203 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*     */               
/* 205 */               _jspx_th_logic_005fiterate_005f0.setName("types");
/*     */               
/* 207 */               _jspx_th_logic_005fiterate_005f0.setId("types");
/*     */               
/* 209 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*     */               
/* 211 */               _jspx_th_logic_005fiterate_005f0.setType("String");
/* 212 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 213 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 214 */                 String types = null;
/* 215 */                 Integer j = null;
/* 216 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 217 */                   out = _jspx_page_context.pushBody();
/* 218 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 219 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                 }
/* 221 */                 types = (String)_jspx_page_context.findAttribute("types");
/* 222 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*     */                 for (;;) {
/* 224 */                   out.write("\n\t\t\t\t\t     <option value=\"");
/* 225 */                   out.print(types);
/* 226 */                   out.write(34);
/* 227 */                   out.write(62);
/* 228 */                   out.print(FormatUtil.getString(types));
/* 229 */                   out.write("</option>\n\t\t\t\t\t     ");
/* 230 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 231 */                   types = (String)_jspx_page_context.findAttribute("types");
/* 232 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 233 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 236 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 237 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 240 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 241 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */               }
/*     */               
/* 244 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 245 */               out.write("\n\t\t\t\t\t</select>\n\t \t\t\t</td>\n     \t\t\t<td>\n\t     \t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t     \t\t\t<tr>\n\t     \t\t\t<td>\n\t     \t\t\t\t<input name=\"radiobutton\" value=\"1\" type=\"radio\" onclick=\"disableTextBox(moCount)\">\n\t     \t\t\t</td>\n\t     \t\t\t<td>\n\t     \t\t\t\t <span class=\"bodytext\">");
/* 246 */               out.print(FormatUtil.getString("am.webclient.configurealert.allselectedparameters"));
/* 247 */               out.write(" </span>\n\t     \t\t\t</td>\n\t     \t\t\t<td>\t\n\t     \t\t\t\t<input name=\"radiobutton\" value=\"0\" checked=\"checked\" type=\"radio\" onclick=\"enableTextBox(moCount)\">\n\t     \t\t\t</td>\n\t     \t\t\t<td>\n\t     \t\t\t\t<span class=\"bodytext\"> ");
/* 248 */               out.print(FormatUtil.getString("am.webclient.configurealert.dependsonany"));
/* 249 */               out.write("&nbsp;  </span> <input name=\"moCount\" value=\"1\" type=\"text\" maxLength='4' size='2'> <span class=\"bodytext\">");
/* 250 */               out.print(FormatUtil.getString("am.webclient.configurealert.selectedparameters"));
/* 251 */               out.write(" </span>\n\t       \t\t\t</td>\n\t       \t\t\t</tr>\n\t       \t\t\t</table>\n       \t\t\t</td>\n\t \t\t</tr>\n\t \t</table>\n\t \t<script>SetValues()</script>\n\t</td>\n</tr>\n<tr>\n<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n<td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n<tr>\n<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t<table class=\"lrtborder\"  style=\"background-color:#fff\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr>\n\t\t\t\t<td align=\"left\" class=\"columnheadingnotop\" colspan=\"2\" width=\"70%\"><input type=\"checkbox\" name=\"selectAll\" onClick=\"ToggleAllElements(this,this.form.selectedMonitors)\"> ");
/* 252 */               out.print(FormatUtil.getString("am.webclient.footer.monitorslink.text"));
/* 253 */               out.write(" </td>\n\t\t\t\t<td align=\"left\" class=\"columnheadingnotop\" width=\"30%\">");
/* 254 */               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 255 */               out.write("</td>\n\t\t\t</tr>\t\t\n\t</table>\n\t\t<div id='defaultMonitors' style=\"DISPLAY: block;\">\n\t\t<table class=\"lrbborder\"  style=\"background-color:#fff\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t");
/*     */               
/* 257 */               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 258 */               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 259 */               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*     */               
/* 261 */               _jspx_th_logic_005fiterate_005f1.setName("allMonitors");
/*     */               
/* 263 */               _jspx_th_logic_005fiterate_005f1.setId("row");
/*     */               
/* 265 */               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*     */               
/* 267 */               _jspx_th_logic_005fiterate_005f1.setType("java.util.HashMap");
/* 268 */               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 269 */               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 270 */                 HashMap row = null;
/* 271 */                 Integer j = null;
/* 272 */                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 273 */                   out = _jspx_page_context.pushBody();
/* 274 */                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 275 */                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*     */                 }
/* 277 */                 row = (HashMap)_jspx_page_context.findAttribute("row");
/* 278 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*     */                 for (;;) {
/* 280 */                   out.write("\n\t\t\t<tr>\n\t\t\t    ");
/* 281 */                   String name = (String)row.get("displayname");
/* 282 */                   String resourceid = (String)row.get("resourceid");
/* 283 */                   String type = (String)row.get("type");
/* 284 */                   if (type.equalsIgnoreCase("HAI"))
/*     */                   {
/* 286 */                     type = FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.text");
/*     */                   }
/* 288 */                   String imgpath = (String)row.get("imagepath");
/* 289 */                   out.write("\n\t\t\t    <td width=\"70%\" align=\"left\" colspan=\"2\" class=\"whitegrayborder\" valign=\"center\">\n\t\t\t    \n\t\t\t\t<input type=\"checkbox\" id=\"selectedMonitors\" name=\"selectedMonitors\" value=\"");
/* 290 */                   out.print(resourceid);
/* 291 */                   out.write("\">&nbsp;&nbsp;&nbsp;");
/* 292 */                   out.print(FormatUtil.getTrimmedText(name, 50));
/* 293 */                   out.write("\n\t\t\t\t<input type=\"hidden\" id=\"names\" value=\"");
/* 294 */                   out.print(FormatUtil.getTrimmedText(name, 40));
/* 295 */                   out.write("\">\n\t\t\t    </td>\n\t\t\t    <td width=\"30%\" class=\"whitegrayborder\">\n\t\t\t\t<img src='");
/* 296 */                   out.print(imgpath);
/* 297 */                   out.write("'>&nbsp;");
/* 298 */                   out.print(type);
/* 299 */                   out.write("\n\t\t\t\t<input type=\"hidden\" id=\"imagePaths\" value=\"");
/* 300 */                   out.print(imgpath);
/* 301 */                   out.write("\">\n\t\t\t    </td>\n\t\t\t</tr>\n\t\t\t");
/* 302 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 303 */                   row = (HashMap)_jspx_page_context.findAttribute("row");
/* 304 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 305 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 308 */                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 309 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 312 */               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 313 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*     */               }
/*     */               
/* 316 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 317 */               out.write("\n\t</table></div>\n\t<script>checkSelectedMonitors()</script>\n\t<table class=\"lrbborder\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t   <td class=\"tablebottom\" colspan=\"3\" align='center'>\n\t\t\t<input type=\"button\" align=\"center\" class=\"buttons\"  value=\"");
/* 318 */               out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 319 */               out.write("\" onclick=\"checkAndSubmit('submit')\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t<input type=\"button\" align=\"center\" class=\"buttons\"  value=\"");
/* 320 */               out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 321 */               out.write("\" onclick=\"checkAndSubmit('cancel')\">\n\t\t   </td>\n\t</tr>\n\t</table>\n</td>\n</tr>\n\n<tr>\n<td colspan='3' height='10'></td>\n</tr>\n\n</table>\n</td>\n<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n</tr>\n<tr>\n<td class=\"AlarmCardMainBtmLeft\"/>\n<td class=\"AlarmCardMainBtmBg\"/>\n<td class=\"AlarmCardMainBtmRight\"/>\n</tr>\n</table>\n");
/* 322 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 323 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 326 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 327 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 330 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 331 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*     */           }
/*     */           
/* 334 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 335 */           out.write(10);
/* 336 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 338 */           out.write(10);
/* 339 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 340 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 344 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 345 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */       }
/*     */       else {
/* 348 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 349 */         out.write("\n\n</form>\n\n");
/*     */       }
/* 351 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 352 */         out = _jspx_out;
/* 353 */         if ((out != null) && (out.getBufferSize() != 0))
/* 354 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 355 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 358 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 364 */     PageContext pageContext = _jspx_page_context;
/* 365 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 367 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 368 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 369 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 371 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 373 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 374 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 375 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 376 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 377 */       return true;
/*     */     }
/* 379 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 380 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 385 */     PageContext pageContext = _jspx_page_context;
/* 386 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 388 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 389 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 390 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 392 */     _jspx_th_c_005fout_005f1.setValue("${attributeType}");
/* 393 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 394 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 395 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 396 */       return true;
/*     */     }
/* 398 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 399 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 404 */     PageContext pageContext = _jspx_page_context;
/* 405 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 407 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 408 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 409 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 411 */     _jspx_th_c_005fout_005f2.setValue("${attributeType}");
/* 412 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 413 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 414 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 415 */       return true;
/*     */     }
/* 417 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 418 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 423 */     PageContext pageContext = _jspx_page_context;
/* 424 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 426 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 427 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 428 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 430 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*     */     
/* 432 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 433 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 434 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 435 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 436 */       return true;
/*     */     }
/* 438 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 444 */     PageContext pageContext = _jspx_page_context;
/* 445 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 447 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody.get(PutTag.class);
/* 448 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 449 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 451 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*     */     
/* 453 */     _jspx_th_tiles_005fput_005f2.setType("string");
/*     */     
/* 455 */     _jspx_th_tiles_005fput_005f2.setValue("");
/* 456 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 457 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 458 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 459 */       return true;
/*     */     }
/* 461 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 462 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 467 */     PageContext pageContext = _jspx_page_context;
/* 468 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 470 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 471 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 472 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 474 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*     */     
/* 476 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/blank.html");
/* 477 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 478 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 479 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 480 */       return true;
/*     */     }
/* 482 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 483 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SelectMonitors_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */