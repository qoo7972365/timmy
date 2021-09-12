/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ 
/*      */ public final class Popup_005fCompareHistoryData_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   33 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   39 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   40 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   41 */     _jspx_dependants.put("/jsp/includes/HistorydataPeriod.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   72 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   76 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  100 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  104 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  105 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  111 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  114 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  117 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  119 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  120 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  121 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*  122 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  123 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  124 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  125 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/*  126 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  133 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  136 */     JspWriter out = null;
/*  137 */     Object page = this;
/*  138 */     JspWriter _jspx_out = null;
/*  139 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  143 */       response.setContentType("text/html;charset=UTF-8");
/*  144 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  146 */       _jspx_page_context = pageContext;
/*  147 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  148 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  149 */       session = pageContext.getSession();
/*  150 */       out = pageContext.getOut();
/*  151 */       _jspx_out = out;
/*      */       
/*  153 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n \n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/*  154 */       SummaryBean sumgraph = null;
/*  155 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  156 */       if (sumgraph == null) {
/*  157 */         sumgraph = new SummaryBean();
/*  158 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/*  160 */       out.write(10);
/*  161 */       out.write(10);
/*  162 */       com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil graph = null;
/*  163 */       graph = (com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/*  164 */       if (graph == null) {
/*  165 */         graph = new com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil();
/*  166 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/*  168 */       out.write("\n\n\n<html>\n<head>\n<title>");
/*  169 */       out.print(FormatUtil.getString("am.webclient.historydata.text"));
/*  170 */       out.write("</title>\n");
/*  171 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  172 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  174 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  175 */       out.write(10);
/*  176 */       out.write("\n<script>\nonload = function() {\nvar p= ");
/*  177 */       out.print(request.getParameter("period"));
/*  178 */       out.write("\nif(p == 4)\n{\ndocument.getElementById(\"custPeriod\").style.display='block';\n}\n\n}\n function fnCheckCustomTime(check)\n {\n if(check.startDate.value=='')\n {\n alert(\"");
/*  179 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  180 */       out.write("\")\n return false\n }\n else if(check.endDate.value=='')\n {\n alert(\"");
/*  181 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  182 */       out.write("\")\n return false\n }\n var s=check.startDate.value;\n var e=check.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/*  183 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  184 */       out.write("\" );\n     return false;\n    }\n    \n return true\n }\n  function fnSetEndTime(a)\n{\n\tdocument.forms[1].endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\tdocument.forms[1].startDate.value=a;\n}\n \nfunction fnPeriod(periodform)\n{     \n \n\tvar p = periodform.period.value;\n\tif(p==4) //period  4  is meant for the custom period.\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='block';\n\t\talert(\"");
/*  185 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforcustomtime"));
/*  186 */       out.write("\")\n \t\treturn false\n\t}\n        document.forms[0].reporttype.value = \"html\"; // can only be html as first generate html and then save as pdf\n\tdocument.forms[0].submit();\n}\nfunction fnbHour(bHrcombo)\n{      \n        document.forms[2].reporttype.value = \"html\"; // can only be html as first generate html and then save as pdf\n\tdocument.forms[2].submit();\n}\nfunction generateReport(type)\n{\n\n\n");
/*  187 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  189 */       out.write("    \n\n");
/*  190 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */         return;
/*  192 */       out.write("\n}\nfunction generateCSVReport(type)\n{\n");
/*  193 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */         return;
/*  195 */       out.write("   \n\n");
/*  196 */       if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */         return;
/*  198 */       out.write("\n} \n</script>\n</head>\n\n<body>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tblbordergray\">\n  <tr>\n    <td width=\"78%\" align=\"right\" valign='top'>\n      ");
/*  199 */       out.write("<!-- $Id : $ -->\n\n");
/*      */       
/*  201 */       Properties pq = com.adventnet.appmanager.util.DBUtil.getRawValue();
/*  202 */       String RV = pq.getProperty("israw");
/*  203 */       String PV = pq.getProperty("rawvalue");
/*  204 */       String AID = request.getParameter("attributeid");
/*  205 */       String pageChk = request.getParameter("method");
/*      */       
/*  207 */       Boolean role = Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*      */       
/*      */ 
/*  210 */       out.write("\n\n<table width=\"100%\" border=\"0\" align=\"left\"  valign=\"top\" cellpadding=\"0\" cellspacing=\"0\"  >\n  <tr>\n    <td style=\"padding-left:5px;\">\n");
/*      */       
/*  212 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  213 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  214 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  216 */       _jspx_th_html_005fform_005f0.setAction("/showHistoryData.do");
/*      */       
/*  218 */       _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/*  219 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  220 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  222 */           out.write("\n    ");
/*      */           
/*  224 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  225 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  226 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  228 */           _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */           
/*  230 */           _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this.form)");
/*      */           
/*  232 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/*  233 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  234 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  235 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  236 */               out = _jspx_page_context.pushBody();
/*  237 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  238 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  241 */               out.write("\n    ");
/*  242 */               if ((AID != null) && ((AID.equals("1357")) || (AID.equals("1377")) || (AID.equals("1457")) || (AID.equals("1473")) || (AID.equals("708")) || (AID.equals("1394")) || (AID.equals("1107")) || (AID.equals("1207")) || (AID.equals("1307")) || (AID.equals("1657")) || (AID.equals("807")) || (AID.equals("1352")) || (AID.equals("1372")) || (AID.equals("1452")) || (AID.equals("1472")) || (AID.equals("702")) || (AID.equals("1392")) || (AID.equals("1102")) || (AID.equals("1202")) || (AID.equals("803")) || (AID.equals("1302")) || (AID.equals("1652"))))
/*      */               {
/*  244 */                 if ((RV != null) && (RV.equals("true"))) {
/*  245 */                   out.write("\n    ");
/*      */                   
/*  247 */                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  248 */                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  249 */                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                   
/*  251 */                   _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*      */                   
/*  253 */                   _jspx_th_html_005foption_005f0.setValue("14");
/*  254 */                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  255 */                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  256 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                   }
/*      */                   
/*  259 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*      */                 } }
/*  261 */               out.write("\n    ");
/*      */               
/*  263 */               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  264 */               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  265 */               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  267 */               _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */               
/*  269 */               _jspx_th_html_005foption_005f1.setValue("4");
/*  270 */               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  271 */               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  272 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */               }
/*      */               
/*  275 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  276 */               out.write("\t\n    ");
/*      */               
/*  278 */               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  279 */               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  280 */               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  282 */               _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */               
/*  284 */               _jspx_th_html_005foption_005f2.setValue("0");
/*  285 */               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  286 */               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  287 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */               }
/*      */               
/*  290 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  291 */               out.write(32);
/*  292 */               out.write(10);
/*  293 */               out.write(9);
/*      */               
/*  295 */               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  296 */               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  297 */               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  299 */               _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */               
/*  301 */               _jspx_th_html_005foption_005f3.setValue("3");
/*  302 */               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  303 */               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  304 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */               }
/*      */               
/*  307 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  308 */               out.write("\n    ");
/*      */               
/*  310 */               OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  311 */               _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  312 */               _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  314 */               _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */               
/*  316 */               _jspx_th_html_005foption_005f4.setValue("6");
/*  317 */               int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  318 */               if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  319 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */               }
/*      */               
/*  322 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  323 */               out.write(10);
/*  324 */               out.write(9);
/*      */               
/*  326 */               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  327 */               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  328 */               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  330 */               _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */               
/*  332 */               _jspx_th_html_005foption_005f5.setValue("-7");
/*  333 */               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  334 */               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  335 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */               }
/*      */               
/*  338 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  339 */               out.write(32);
/*  340 */               out.write(10);
/*  341 */               out.write(9);
/*      */               
/*  343 */               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  344 */               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  345 */               _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  347 */               _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */               
/*  349 */               _jspx_th_html_005foption_005f6.setValue("12");
/*  350 */               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  351 */               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  352 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */               }
/*      */               
/*  355 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  356 */               out.write("\n    ");
/*      */               
/*  358 */               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  359 */               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  360 */               _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  362 */               _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */               
/*  364 */               _jspx_th_html_005foption_005f7.setValue("7");
/*  365 */               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  366 */               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  367 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */               }
/*      */               
/*  370 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  371 */               out.write(10);
/*  372 */               out.write(9);
/*      */               
/*  374 */               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  375 */               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  376 */               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  378 */               _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */               
/*  380 */               _jspx_th_html_005foption_005f8.setValue("-30");
/*  381 */               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  382 */               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  383 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */               }
/*      */               
/*  386 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  387 */               out.write(32);
/*  388 */               out.write(10);
/*  389 */               out.write(9);
/*      */               
/*  391 */               OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  392 */               _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  393 */               _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  395 */               _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */               
/*  397 */               _jspx_th_html_005foption_005f9.setValue("11");
/*  398 */               int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  399 */               if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  400 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */               }
/*      */               
/*  403 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  404 */               out.write(10);
/*  405 */               out.write(9);
/*      */               
/*  407 */               OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  408 */               _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  409 */               _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  411 */               _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */               
/*  413 */               _jspx_th_html_005foption_005f10.setValue("9");
/*  414 */               int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  415 */               if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  416 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */               }
/*      */               
/*  419 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*  420 */               out.write("\n    ");
/*      */               
/*  422 */               OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  423 */               _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/*  424 */               _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  426 */               _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */               
/*  428 */               _jspx_th_html_005foption_005f11.setValue("8");
/*  429 */               int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/*  430 */               if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/*  431 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */               }
/*      */               
/*  434 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/*  435 */               out.write(10);
/*  436 */               out.write(9);
/*      */               
/*  438 */               OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  439 */               _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/*  440 */               _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  442 */               _jspx_th_html_005foption_005f12.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */               
/*  444 */               _jspx_th_html_005foption_005f12.setValue("-5");
/*  445 */               int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/*  446 */               if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/*  447 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12); return;
/*      */               }
/*      */               
/*  450 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12);
/*  451 */               out.write(10);
/*  452 */               out.write(9);
/*  453 */               if (pageChk.equals("getData"))
/*      */               {
/*      */ 
/*  456 */                 out.write(10);
/*  457 */                 out.write(9);
/*      */                 
/*  459 */                 OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  460 */                 _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/*  461 */                 _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  463 */                 _jspx_th_html_005foption_005f13.setKey(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*      */                 
/*  465 */                 _jspx_th_html_005foption_005f13.setValue("20");
/*  466 */                 int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/*  467 */                 if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/*  468 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                 }
/*      */                 
/*  471 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13);
/*  472 */                 out.write(10);
/*  473 */                 out.write(9);
/*      */               }
/*  475 */               out.write(10);
/*  476 */               out.write(32);
/*  477 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  478 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  481 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  482 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  485 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  486 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/*  489 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  490 */           out.write("\n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  491 */           out.print(request.getParameter("resourceid"));
/*  492 */           out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  493 */           out.print(request.getParameter("childid"));
/*  494 */           out.write("'>\n  \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  495 */           out.print(request.getParameter("resourcename"));
/*  496 */           out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  497 */           out.print(request.getParameter("attributeid"));
/*  498 */           out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  499 */           out.print(request.getParameter("method"));
/*  500 */           out.write(39);
/*  501 */           out.write(62);
/*  502 */           out.write(32);
/*  503 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  505 */           out.write(10);
/*  506 */           out.write(9);
/*  507 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  509 */           out.write(10);
/*  510 */           out.write(9);
/*  511 */           if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  513 */           out.write(10);
/*  514 */           out.write(9);
/*  515 */           if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  517 */           out.write(10);
/*  518 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  519 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  523 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  524 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/*  527 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  528 */         out.write("\n    </td>\n \t");
/*  529 */         if (pageChk.equals("getIndividualURLandCompareReportsData"))
/*      */         {
/*      */ 
/*  532 */           out.write("\n\t\t<td >\n");
/*      */           
/*  534 */           FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  535 */           _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/*  536 */           _jspx_th_html_005fform_005f1.setParent(null);
/*      */           
/*  538 */           _jspx_th_html_005fform_005f1.setAction("/showHistoryData.do?period=4");
/*  539 */           int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/*  540 */           if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */             for (;;) {
/*  542 */               out.write("\n\t<div id='custPeriod' style=\"Display:none\">\n\n      <table width=\"98%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n   \n  <td height=\"24\" class=\"bodytext\" >");
/*  543 */               out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  544 */               out.write(" \n</td>\n \n          <td height=\"40\"   > ");
/*  545 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  547 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  548 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  549 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                         Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                        });\n                           </SCRIPT></td>\n \n          <td  class=\"bodytext\"  >");
/*  550 */               out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  551 */               out.write("</td>\n  \n          <td height=\"39\"  colspan=2  > ");
/*  552 */               if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  554 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  555 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  556 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                       Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                       });\n             </SCRIPT> </td>\n  \n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  557 */               out.print(request.getParameter("resourceid"));
/*  558 */               out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  559 */               out.print(request.getParameter("childid"));
/*  560 */               out.write("'>\n \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  561 */               out.print(request.getParameter("resourcename"));
/*  562 */               out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  563 */               out.print(request.getParameter("attributeid"));
/*  564 */               out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  565 */               out.print(request.getParameter("method"));
/*  566 */               out.write("'>\n  <input type=\"hidden\" name=\"customstarttime\" >\n  <input type=\"hidden\" name=\"customendtime\" >\n  ");
/*  567 */               if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  569 */               out.write("\n    <td height=\"32\"  >\n      <input type=\"submit\" name=\"show\" value=\"");
/*  570 */               out.print(FormatUtil.getString("Go"));
/*  571 */               out.write("\" class=\"buttons\" onclick=\"return fnCheckCustomTime(this.form)\">\n    </td>\n\t\n\t\n  </tr>\n</table>\n</div>\n\t\n</td>\n");
/*  572 */               if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  574 */               out.write(10);
/*  575 */               out.write(9);
/*  576 */               int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/*  577 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  581 */           if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/*  582 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */           }
/*      */           
/*  585 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1);
/*  586 */           out.write(10);
/*  587 */           out.write(9);
/*      */         }
/*  589 */         out.write("\n</tr>\n");
/*  590 */         if (!pageChk.equals("getIndividualURLandCompareReportsData"))
/*      */         {
/*      */ 
/*  593 */           out.write("\n<tr>\n<td >\n");
/*      */           
/*  595 */           FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  596 */           _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/*  597 */           _jspx_th_html_005fform_005f2.setParent(null);
/*      */           
/*  599 */           _jspx_th_html_005fform_005f2.setAction("/showHistoryData.do?period=4");
/*  600 */           int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/*  601 */           if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */             for (;;) {
/*  603 */               out.write("\n\t<div id='custPeriod' style=\"Display:none\">\n\n      <table width=\"98%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n   \n  <td height=\"24\" class=\"bodytext\" >");
/*  604 */               out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  605 */               out.write(" \n</td>\n \n          <td height=\"40\"   > ");
/*  606 */               if (_jspx_meth_c_005fchoose_005f2(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  608 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  609 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  610 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                         Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                        });\n                           </SCRIPT></td>\n \n          <td  class=\"bodytext\"  >");
/*  611 */               out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  612 */               out.write("</td>\n  \n          <td height=\"39\"  colspan=2  > ");
/*  613 */               if (_jspx_meth_c_005fchoose_005f3(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  615 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  616 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  617 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                       Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                       });\n             </SCRIPT> </td>\n  \n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  618 */               out.print(request.getParameter("resourceid"));
/*  619 */               out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  620 */               out.print(request.getParameter("childid"));
/*  621 */               out.write("'>\n \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  622 */               out.print(request.getParameter("resourcename"));
/*  623 */               out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  624 */               out.print(request.getParameter("attributeid"));
/*  625 */               out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  626 */               out.print(request.getParameter("method"));
/*  627 */               out.write("'>\n  <input type=\"hidden\" name=\"customstarttime\" >\n  <input type=\"hidden\" name=\"customendtime\" >\n  ");
/*  628 */               if (_jspx_meth_html_005fhidden_005f6(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  630 */               out.write("\n    <td height=\"32\"  >\n      <input type=\"submit\" name=\"show\" value=\"");
/*  631 */               out.print(FormatUtil.getString("Go"));
/*  632 */               out.write("\" class=\"buttons\" onclick=\"return fnCheckCustomTime(this.form)\">\n\t\n    </td>\n  </tr>\n    \n</table>\n\t</div>\n\t");
/*  633 */               if (_jspx_meth_html_005fhidden_005f7(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  635 */               out.write(10);
/*  636 */               out.write(9);
/*  637 */               int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/*  638 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  642 */           if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/*  643 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */           }
/*      */           
/*  646 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f2);
/*  647 */           out.write(10);
/*  648 */           out.write(9);
/*      */         }
/*  650 */         out.write("\n</td>\n</tr>\n\n\n \n</table>\n\n\n\n");
/*  651 */         out.write("\n    </td>\n\t");
/*      */         
/*  653 */         FormTag _jspx_th_html_005fform_005f3 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  654 */         _jspx_th_html_005fform_005f3.setPageContext(_jspx_page_context);
/*  655 */         _jspx_th_html_005fform_005f3.setParent(null);
/*      */         
/*  657 */         _jspx_th_html_005fform_005f3.setAction("/showHistoryData.do?method=getIndividualURLandCompareReportsData");
/*      */         
/*  659 */         _jspx_th_html_005fform_005f3.setStyle("Display:inline");
/*  660 */         int _jspx_eval_html_005fform_005f3 = _jspx_th_html_005fform_005f3.doStartTag();
/*  661 */         if (_jspx_eval_html_005fform_005f3 != 0) {
/*      */           for (;;) {
/*  663 */             out.write("\n    \t\t<td width=\"20%\" align=\"right\" >\n\t\t\t\n\t\t\t\t");
/*  664 */             if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f3, _jspx_page_context))
/*      */               return;
/*  666 */             out.write("\n\t\t</td>\n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  667 */             out.print(request.getParameter("resourceid"));
/*  668 */             out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  669 */             out.print(request.getParameter("childid"));
/*  670 */             out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  671 */             out.print(request.getParameter("attributeid"));
/*  672 */             out.write("'>\n  <input type=\"hidden\" name=\"period\"  value='");
/*  673 */             out.print(request.getParameter("period"));
/*  674 */             out.write("'>\n   <input type=\"hidden\" name=\"method\" value='");
/*  675 */             out.print(request.getParameter("method"));
/*  676 */             out.write("'>\n  <input type=\"hidden\" name=\"startdate\" value='");
/*  677 */             out.print(request.getAttribute("startdate"));
/*  678 */             out.write("'>\n  <input type=\"hidden\" name=\"enddate\" value='");
/*  679 */             out.print(request.getAttribute("enddate"));
/*  680 */             out.write("'>\n  ");
/*  681 */             if (_jspx_meth_html_005fhidden_005f8(_jspx_th_html_005fform_005f3, _jspx_page_context))
/*      */               return;
/*  683 */             out.write(10);
/*  684 */             out.write(9);
/*  685 */             int evalDoAfterBody = _jspx_th_html_005fform_005f3.doAfterBody();
/*  686 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  690 */         if (_jspx_th_html_005fform_005f3.doEndTag() == 5) {
/*  691 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f3);
/*      */         }
/*      */         else {
/*  694 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f3);
/*  695 */           out.write("\n  \n    <td width=\"2%\" align=\"right\" ><a class=\"staticlinks\" href=\"javascript:generateReport('pdf')\"><img  align=\"absmiddle\"  src=\"images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" hspace=\"4\" title=\"");
/*  696 */           out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  697 */           out.write("\"></a></td>\n\t<td width=\"2%\" align=\"right\" ><a class=\"staticlinks\" href=\"javascript:generateReport('csv')\"><img  align=\"absmiddle\"  src=\"images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\" hspace=\"4\" title=\"");
/*  698 */           out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/*  699 */           out.write("\"></a></td>\n  </tr>\n</table>\n<br>\n\n");
/*      */           
/*  701 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  702 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  703 */           _jspx_th_c_005fif_005f4.setParent(null);
/*      */           
/*  705 */           _jspx_th_c_005fif_005f4.setTest("${STATUS!='SUCCESS'}");
/*  706 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  707 */           if (_jspx_eval_c_005fif_005f4 != 0)
/*      */           {
/*  709 */             out.write("\n\n<br>\n<br>\n<table align=\"center\" cellspacing=\"5\" width=\"100%\" >\n  <tr> \n    <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr> \n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/*  710 */             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */               return;
/*  712 */             out.write("</span></td>\n        </tr>\n");
/*      */             
/*  714 */             if (!com.adventnet.appmanager.util.OEMUtil.isOEM())
/*      */             {
/*      */ 
/*  717 */               out.write("\n        <tr> \n          <td align=\"right\" class=\"lightbg\"> <a href=\"http://");
/*  718 */               out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link"));
/*  719 */               out.write("#m4\" class=\"staticlinks\"> \n            ");
/*  720 */               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/*  721 */               out.write(" \n            >></a></td>\n        </tr>\n");
/*      */             }
/*  723 */             out.write("\n      </table></td>\n  </tr>\n</table>\n");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  736 */           else if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  737 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */           }
/*      */           else {
/*  740 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  741 */             out.write(10);
/*  742 */             out.write(10);
/*      */             
/*  744 */             com.adventnet.appmanager.struts.actions.ComparingSla cs = new com.adventnet.appmanager.struts.actions.ComparingSla();
/*  745 */             int sizeofseq = 3;
/*  746 */             String unit = (String)pageContext.findAttribute("unit");
/*  747 */             String period = request.getParameter("period");
/*  748 */             String valueforperiod = cs.getValueForPeriod(period);
/*  749 */             String bHr = (String)request.getAttribute("bRuleName");
/*  750 */             String bHr_id = (String)request.getAttribute("businessPeriod");
/*      */             
/*  752 */             out.write(10);
/*  753 */             out.write(10);
/*  754 */             if ((period != null) && (period.equals("14"))) {
/*  755 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td width=\"80%\"> <table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        ");
/*  756 */               if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                 return;
/*  758 */               out.write(32);
/*  759 */               if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
/*      */                 return;
/*  761 */               out.write(" \n        ");
/*  762 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  763 */               String resname = (String)request.getAttribute("resourcename");
/*  764 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/*  765 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/*  766 */               out.write("\n            <tr> \n          <td class=\"tableheadingbborder\">");
/*  767 */               out.print(FormatUtil.getString("webclient.performance.reports.header", new String[] { FormatUtil.getString(nameofmonitor), resname }));
/*  768 */               out.write("  \n          </td>\n        </tr>\n        ");
/*  769 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/*  770 */               if (rid != -1) {
/*  771 */                 out.write("\n        <tr> \n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> \n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/*  772 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/*  773 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/*  774 */                 out.print(resname);
/*  775 */                 out.write("</td>\n              </tr>\n              <tr> \n                <td class=\"yellowgrayborderbr\">");
/*  776 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/*  777 */                 out.write(" \n                </td>\n                <td class=\"yellowgrayborder\">");
/*  778 */                 out.print(FormatUtil.getString(nameofmonitor));
/*  779 */                 out.write(" \n                  ");
/*  780 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*  781 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*  782 */                 out.write("\n                </td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborderbr\">");
/*  783 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  784 */                 out.write(" \n                </td>\n                <td class=\"whitegrayborder\"> ");
/*  785 */                 out.print(startime);
/*  786 */                 out.write(" </td>\n              </tr>\n              <tr> \n                <td  class=\"yellowgrayborderbr\">");
/*  787 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  788 */                 out.write(" \n                </td>\n                <td class=\"yellowgrayborder\">");
/*  789 */                 out.print(endtime);
/*  790 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/*  792 */               out.write("\n          <tr> \n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> \n                <td width=\"96%\" align=\"right\"> \n                  ");
/*      */               
/*  794 */               if (request.getParameter("period").equals("-7"))
/*      */               {
/*      */ 
/*  797 */                 out.write("\n                  <img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  803 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/*  804 */                 out.print(request.getParameter("method"));
/*  805 */                 out.write("&resourceid=");
/*  806 */                 out.print(request.getParameter("resourceid"));
/*  807 */                 out.write("&childid=");
/*  808 */                 out.print(request.getParameter("childid"));
/*  809 */                 out.write("&keyids=");
/*  810 */                 out.print(request.getParameter("keyids"));
/*  811 */                 out.write("&attributeid=");
/*  812 */                 out.print(request.getParameter("attributeid"));
/*  813 */                 out.write("&period=-7&resourcename=");
/*  814 */                 out.print(request.getParameter("resourcename"));
/*  815 */                 out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  816 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/*  817 */                 out.write("\"></a> \n                  ");
/*      */               }
/*      */               
/*      */ 
/*  821 */               out.write("\n                </td>\n                <td width=\"4%\"> \n                  ");
/*      */               
/*  823 */               if (request.getParameter("period").equals("-30"))
/*      */               {
/*      */ 
/*  826 */                 out.write("\n                  <img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  832 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/*  833 */                 out.print(request.getParameter("method"));
/*  834 */                 out.write("&resourceid=");
/*  835 */                 out.print(request.getParameter("resourceid"));
/*  836 */                 out.write("&childid=");
/*  837 */                 out.print(request.getParameter("childid"));
/*  838 */                 out.write("&keyids=");
/*  839 */                 out.print(request.getParameter("keyids"));
/*  840 */                 out.write("&attributeid=");
/*  841 */                 out.print(request.getParameter("attributeid"));
/*  842 */                 out.write("&period=-30&resourcename=");
/*  843 */                 out.print(request.getParameter("resourcename"));
/*  844 */                 out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  845 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/*  846 */                 out.write("\"></a> \n                  ");
/*      */               }
/*      */               
/*      */ 
/*  850 */               out.write("\n                </td>\n              </tr>\n            </table></td>\n        </tr>\n     ");
/*  851 */               ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/*  852 */               if (rawvalues.size() > 0)
/*      */               {
/*      */ 
/*  855 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"));
/*      */               }
/*      */               
/*  858 */               String attUnit = "";
/*  859 */               if (!unit.equals(""))
/*      */               {
/*  861 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  866 */               out.write("\n        \n        <tr align=\"left\"> \n          <td width=\"100%\" height=\"50\"> \n            ");
/*      */               
/*  868 */               String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */               
/*  870 */               out.write("\n         ");
/*      */               
/*  872 */               TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/*  873 */               _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/*  874 */               _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */               
/*  876 */               _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("graph");
/*      */               
/*  878 */               _jspx_th_awolf_005ftimechart_005f0.setWidth("600");
/*      */               
/*  880 */               _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*      */               
/*  882 */               _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */               
/*  884 */               _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */               
/*  886 */               _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(temp);
/*      */               
/*  888 */               _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */               
/*  890 */               _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/*  891 */               int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/*  892 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/*  893 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  894 */                   out = _jspx_page_context.pushBody();
/*  895 */                   _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/*  896 */                   _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  899 */                   out.write(" \n            ");
/*  900 */                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/*  901 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  904 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  905 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  908 */               if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/*  909 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */               }
/*      */               
/*  912 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*  913 */               out.write(" \n           \n          </td>\n        </tr> \n        </table>\n        <br>\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n <tr> \n    <td class=\"tableheading\"  width='100%' colspan='3'>");
/*  914 */               out.print(FormatUtil.getString(nameofmonitor));
/*  915 */               out.write(" </td>\n  </tr>\n  \n  <tr> \n    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/*  916 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/*  917 */               out.write("</td>\n    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/*  918 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/*  919 */               out.write("</td>\n    <td width=\"25%\" class=\"columnheading\" align=\"center\">");
/*  920 */               out.print(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/*  921 */               out.write(" </td>\n    \n  </tr>\n  ");
/*  922 */               for (int i = 0; i < rawvalues.size(); i++) {
/*  923 */                 Properties row = (Properties)rawvalues.get(i);
/*  924 */                 String bgcolor = "";
/*  925 */                 String selectedColor = "class=\"selectedborder\"";
/*  926 */                 if (i % 2 == 0)
/*      */                 {
/*  928 */                   bgcolor = "class=\"whitegrayborder\"";
/*      */                 }
/*      */                 else
/*      */                 {
/*  932 */                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                 }
/*      */                 
/*  935 */                 out.write("\n  <tr> \n\t");
/*      */                 
/*  937 */                 long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/*  938 */                 String resourcetype = String.valueOf(request.getAttribute("type"));
/*  939 */                 String VAL = null;
/*  940 */                 VAL = String.valueOf(row.get("VALUE"));
/*  941 */                 pageContext.setAttribute("date1", new Date(archivedTime));
/*  942 */                 out.write("\n                \n                 <td ");
/*  943 */                 out.print(bgcolor);
/*  944 */                 out.write(" align=\"center\">");
/*  945 */                 if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*      */                   return;
/*  947 */                 out.write("</td>\n    <td ");
/*  948 */                 out.print(bgcolor);
/*  949 */                 out.write(" align=\"center\">");
/*  950 */                 if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_page_context))
/*      */                   return;
/*  952 */                 out.write("</td>\n         <td ");
/*  953 */                 out.print(bgcolor);
/*  954 */                 out.write(" align=\"center\">");
/*  955 */                 out.print(VAL);
/*  956 */                 out.write("</td>  </tr>\n  \n  ");
/*      */               }
/*  958 */               out.write("\n    </table> \n     <br>\n");
/*      */             } else {
/*  960 */               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td width=\"80%\"> <table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        ");
/*  961 */               if (_jspx_meth_c_005fset_005f2(_jspx_page_context))
/*      */                 return;
/*  963 */               out.write(32);
/*  964 */               if (_jspx_meth_c_005fset_005f3(_jspx_page_context))
/*      */                 return;
/*  966 */               out.write(" \n        ");
/*  967 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  968 */               String resname = (String)request.getAttribute("resourcename");
/*  969 */               String start_time = String.valueOf(((Date)request.getAttribute("starttime")).getTime());
/*      */               
/*  971 */               String end_time = String.valueOf(((Date)request.getAttribute("endtime")).getTime());
/*      */               
/*  973 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/*  974 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/*      */               
/*      */ 
/*  977 */               out.write("\n        ");
/*  978 */               if (request.getParameter("period").equals("4"))
/*      */               {
/*  980 */                 out.write("\n        <tr> \n          <td class=\"tableheadingbborder\"> \n\t");
/*      */                 
/*  982 */                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  983 */                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  984 */                 _jspx_th_c_005fchoose_005f4.setParent(null);
/*  985 */                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  986 */                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                   for (;;) {
/*  988 */                     out.write(" \n   \t\t");
/*      */                     
/*  990 */                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  991 */                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  992 */                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                     
/*  994 */                     _jspx_th_c_005fwhen_005f4.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/*  995 */                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  996 */                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                       for (;;) {
/*  998 */                         out.write(" \n\t\t\t");
/*  999 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 1000 */                         out.write("  \n   \t\t");
/* 1001 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1002 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1006 */                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1007 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                     }
/*      */                     
/* 1010 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1011 */                     out.write(" \n   \t\t");
/*      */                     
/* 1013 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1014 */                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1015 */                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1016 */                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1017 */                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                       for (;;) {
/* 1019 */                         out.write(" \n   \t \t\t");
/* 1020 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }), bHr }));
/* 1021 */                         out.write("  \n   \t\t");
/* 1022 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1023 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1027 */                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1028 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                     }
/*      */                     
/* 1031 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1032 */                     out.write(32);
/* 1033 */                     out.write(10);
/* 1034 */                     out.write(9);
/* 1035 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1036 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1040 */                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1041 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                 }
/*      */                 
/* 1044 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1045 */                 out.write("\n          </td>\n        </tr>\n        ");
/*      */               }
/*      */               else {
/* 1048 */                 out.write("\n        <tr> \n          <td class=\"tableheadingbborder\">\n\t");
/*      */                 
/* 1050 */                 ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1051 */                 _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1052 */                 _jspx_th_c_005fchoose_005f5.setParent(null);
/* 1053 */                 int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1054 */                 if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                   for (;;) {
/* 1056 */                     out.write(" \n   \t\t");
/*      */                     
/* 1058 */                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1059 */                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1060 */                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                     
/* 1062 */                     _jspx_th_c_005fwhen_005f5.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1063 */                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1064 */                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                       for (;;) {
/* 1066 */                         out.write(" \n\t\t\t");
/* 1067 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 1068 */                         out.write("  \n   \t\t");
/* 1069 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1070 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1074 */                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1075 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                     }
/*      */                     
/* 1078 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1079 */                     out.write(" \n   \t\t");
/*      */                     
/* 1081 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1082 */                     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1083 */                     _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1084 */                     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1085 */                     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                       for (;;) {
/* 1087 */                         out.write(" \n   \t \t\t");
/* 1088 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }), bHr }));
/* 1089 */                         out.write("  \n   \t\t");
/* 1090 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1091 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1095 */                     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1096 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                     }
/*      */                     
/* 1099 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1100 */                     out.write(32);
/* 1101 */                     out.write(10);
/* 1102 */                     out.write(9);
/* 1103 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1104 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1108 */                 if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1109 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                 }
/*      */                 
/* 1112 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1113 */                 out.write(" \n          </td>\n        </tr>\n        ");
/*      */               }
/* 1115 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/* 1116 */               if (rid != -1) {
/* 1117 */                 out.write("\n        <tr> \n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> \n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1118 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 1119 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/* 1120 */                 out.print(resname);
/* 1121 */                 out.write("</td>\n              </tr>\n              <tr> \n                <td class=\"yellowgrayborderbr\">");
/* 1122 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 1123 */                 out.write(" \n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1124 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1125 */                 out.write(" \n                  ");
/* 1126 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/* 1127 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 1128 */                 out.write("\n                </td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborderbr\">");
/* 1129 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1130 */                 out.write(" \n                </td>\n                <td class=\"whitegrayborder\"> ");
/* 1131 */                 out.print(startime);
/* 1132 */                 out.write(" </td>\n              </tr>\n              <tr> \n                <td  class=\"yellowgrayborderbr\">");
/* 1133 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1134 */                 out.write(" \n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1135 */                 out.print(endtime);
/* 1136 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/* 1138 */               out.write("\n        <tr> \n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr> \n                <td width=\"96%\" align=\"right\"> \n                  ");
/*      */               
/* 1140 */               if (request.getParameter("period").equals("-7"))
/*      */               {
/*      */ 
/* 1143 */                 out.write("\n                  <img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 1149 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/* 1150 */                 out.print(request.getParameter("method"));
/* 1151 */                 out.write("&resourceid=");
/* 1152 */                 out.print(request.getParameter("resourceid"));
/* 1153 */                 out.write("&childid=");
/* 1154 */                 out.print(request.getParameter("childid"));
/* 1155 */                 out.write("&keyids=");
/* 1156 */                 out.print(request.getParameter("keyids"));
/* 1157 */                 out.write("&attributeid=");
/* 1158 */                 out.print(request.getParameter("attributeid"));
/* 1159 */                 out.write("&period=-7&resourcename=");
/* 1160 */                 out.print(request.getParameter("resourcename"));
/* 1161 */                 out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 1162 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 1163 */                 out.write("\"></a> \n                  ");
/*      */               }
/*      */               
/*      */ 
/* 1167 */               out.write("\n                </td>\n                <td width=\"4%\"> \n                  ");
/*      */               
/* 1169 */               if (request.getParameter("period").equals("-30"))
/*      */               {
/*      */ 
/* 1172 */                 out.write("\n                  <img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 1178 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/* 1179 */                 out.print(request.getParameter("method"));
/* 1180 */                 out.write("&resourceid=");
/* 1181 */                 out.print(request.getParameter("resourceid"));
/* 1182 */                 out.write("&childid=");
/* 1183 */                 out.print(request.getParameter("childid"));
/* 1184 */                 out.write("&keyids=");
/* 1185 */                 out.print(request.getParameter("keyids"));
/* 1186 */                 out.write("&attributeid=");
/* 1187 */                 out.print(request.getParameter("attributeid"));
/* 1188 */                 out.write("&period=-30&resourcename=");
/* 1189 */                 out.print(request.getParameter("resourcename"));
/* 1190 */                 out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 1191 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 1192 */                 out.write("\"></a> \n                  ");
/*      */               }
/*      */               
/*      */ 
/* 1196 */               out.write("\n                </td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               
/*      */ 
/* 1199 */               ArrayList pdfdata = (ArrayList)request.getAttribute("pdfdata");
/* 1200 */               ArrayList list = (ArrayList)request.getAttribute("list");
/* 1201 */               java.util.Hashtable bhourDetail = (java.util.Hashtable)request.getAttribute("bhourDetail");
/* 1202 */               if (list != null)
/*      */               {
/* 1204 */                 sizeofseq = list.size();
/*      */               }
/*      */               
/* 1207 */               String graph_type = "graph";
/* 1208 */               String graph_height = "200";
/* 1209 */               String graph_width = "620";
/*      */               
/* 1211 */               String ShowData = (String)request.getAttribute("CompareUrl");
/* 1212 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/* 1214 */                 sumgraph.setResid(request.getParameter("childid"));
/* 1215 */                 sumgraph.setAttributeid(request.getParameter("attributeid"));
/* 1216 */                 String strtime = (String)request.getAttribute("STIME");
/* 1217 */                 String etime = (String)request.getAttribute("ETIME");
/* 1218 */                 sumgraph.setStarttime(strtime);
/* 1219 */                 sumgraph.setEndtime(etime);
/*      */                 
/*      */ 
/* 1222 */                 sumgraph.setArchivedforUrl(true);
/* 1223 */                 sumgraph.setCompareUrls(true);
/* 1224 */                 sumgraph.setBhrDetails(bhourDetail);
/* 1225 */                 String dailyRptStarttime = (String)request.getAttribute("dailyStime");
/* 1226 */                 String dailyRptEndtime = (String)request.getAttribute("dailyEtime");
/* 1227 */                 if ((dailyRptStarttime != null) && (!dailyRptStarttime.equals("0")))
/*      */                 {
/* 1229 */                   sumgraph.setDailyRptStarttime(dailyRptStarttime);
/* 1230 */                   sumgraph.setDailyRptEndtime(dailyRptEndtime);
/*      */                 }
/* 1232 */                 graph_type = "sumgraph";
/* 1233 */                 if (sizeofseq < 5)
/*      */                 {
/* 1235 */                   graph_height = "250";
/*      */                 }
/* 1237 */                 else if ((sizeofseq >= 5) && (sizeofseq < 10))
/*      */                 {
/* 1239 */                   graph_height = "400";
/*      */                 }
/* 1241 */                 else if ((sizeofseq >= 10) && (sizeofseq < 20))
/*      */                 {
/* 1243 */                   graph_height = "700";
/*      */                 }
/* 1245 */                 else if ((sizeofseq >= 20) && (sizeofseq < 30))
/*      */                 {
/* 1247 */                   graph_height = "800";
/*      */                 }
/* 1249 */                 else if ((sizeofseq >= 30) && (sizeofseq < 40))
/*      */                 {
/* 1251 */                   graph_height = "900";
/*      */                 }
/*      */                 else
/*      */                 {
/* 1255 */                   graph_height = "1000";
/*      */                 }
/*      */                 
/* 1258 */                 graph_width = "700";
/*      */ 
/*      */               }
/* 1261 */               else if (request.getParameter("period").equals("4"))
/*      */               {
/* 1263 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), request.getAttribute("customstarttime").toString(), request.getAttribute("customendtime").toString());
/*      */               }
/*      */               else {
/* 1266 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"));
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1271 */               if ((list != null) && (list.size() > 0))
/*      */               {
/* 1273 */                 pageContext.setAttribute("urlseqs", list);
/*      */               }
/*      */               
/* 1276 */               String attUnit = "";
/* 1277 */               if (!unit.equals(""))
/*      */               {
/* 1279 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/*      */ 
/* 1283 */               out.write("\n        <tr align=\"left\"> \n          <td width=\"100%\" height=\"50\"> \n            ");
/*      */               
/* 1285 */               String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */               
/* 1287 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/*      */ 
/* 1290 */                 out.write("\n            ");
/*      */                 
/* 1292 */                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1293 */                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 1294 */                 _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                 
/* 1296 */                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer(graph_type);
/*      */                 
/* 1298 */                 _jspx_th_awolf_005ftimechart_005f1.setWidth(graph_width);
/*      */                 
/* 1300 */                 _jspx_th_awolf_005ftimechart_005f1.setHeight(graph_height);
/*      */                 
/* 1302 */                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                 
/* 1304 */                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1306 */                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(temp);
/*      */                 
/* 1308 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomDateAxis(true);
/*      */                 
/* 1310 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomAngle(270.0D);
/*      */                 
/* 1312 */                 _jspx_th_awolf_005ftimechart_005f1.setShape(true);
/* 1313 */                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 1314 */                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 1315 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1316 */                     out = _jspx_page_context.pushBody();
/* 1317 */                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 1318 */                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1321 */                     out.write(" \n            ");
/* 1322 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 1323 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1326 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1327 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1330 */                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 1331 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                 }
/*      */                 
/* 1334 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 1335 */                 out.write(" \n            ");
/*      */               } else {
/* 1337 */                 out.write("\n            ");
/*      */                 
/* 1339 */                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1340 */                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 1341 */                 _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */                 
/* 1343 */                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer(graph_type);
/*      */                 
/* 1345 */                 _jspx_th_awolf_005ftimechart_005f2.setWidth(graph_width);
/*      */                 
/* 1347 */                 _jspx_th_awolf_005ftimechart_005f2.setHeight(graph_height);
/*      */                 
/* 1349 */                 _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                 
/* 1351 */                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1353 */                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(temp);
/*      */                 
/* 1355 */                 _jspx_th_awolf_005ftimechart_005f2.setCustomDateAxis(true);
/*      */                 
/* 1357 */                 _jspx_th_awolf_005ftimechart_005f2.setCustomAngle(270.0D);
/*      */                 
/* 1359 */                 _jspx_th_awolf_005ftimechart_005f2.setMovingAverage(FormatUtil.getString("am.webclient.730attribute.legendmovingaverage.text"));
/*      */                 
/* 1361 */                 _jspx_th_awolf_005ftimechart_005f2.setMovingAverageName(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"));
/*      */                 
/* 1363 */                 _jspx_th_awolf_005ftimechart_005f2.setShape(true);
/* 1364 */                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 1365 */                 if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 1366 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 1367 */                     out = _jspx_page_context.pushBody();
/* 1368 */                     _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 1369 */                     _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1372 */                     out.write(" \n            ");
/* 1373 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 1374 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1377 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 1378 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1381 */                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 1382 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                 }
/*      */                 
/* 1385 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 1386 */                 out.write(" \n            ");
/*      */               }
/* 1388 */               out.write("\n          </td>\n        </tr>\n        ");
/*      */               
/* 1390 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1391 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1392 */               _jspx_th_c_005fif_005f5.setParent(null);
/*      */               
/* 1394 */               _jspx_th_c_005fif_005f5.setTest("${!empty requestScope.maxvalue}");
/* 1395 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1396 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 1398 */                   out.write(" \n        <tr align=\"left\"> \n          ");
/*      */                   
/* 1400 */                   String minavgval = FormatUtil.formatNumber(request.getAttribute("minAvgValue"));
/* 1401 */                   String maxavgval = FormatUtil.formatNumber(request.getAttribute("maxAvgValue"));
/* 1402 */                   String avgvalue = FormatUtil.formatNumber(request.getAttribute("avgvalue"));
/*      */                   
/*      */ 
/*      */ 
/* 1406 */                   out.write("\n          <td class=\"bodytext\"> <br> <table border='0' align=left >\n              <tr > \n                <td width=\"16%\"  class='bodytext'> ");
/* 1407 */                   out.print(FormatUtil.getString(nameofmonitor));
/* 1408 */                   out.write(": \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left> ");
/* 1409 */                   out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/* 1410 */                   out.write(":&nbsp;&nbsp;");
/* 1411 */                   out.print(minavgval);
/* 1412 */                   out.write("&nbsp;&nbsp;");
/* 1413 */                   out.print(FormatUtil.getString(unit));
/* 1414 */                   out.write(" \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1415 */                   out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/* 1416 */                   out.write(":&nbsp;&nbsp;");
/* 1417 */                   out.print(maxavgval);
/* 1418 */                   out.write("&nbsp;&nbsp;");
/* 1419 */                   out.print(FormatUtil.getString(unit));
/* 1420 */                   out.write(" \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1421 */                   out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 1422 */                   out.write(":&nbsp;&nbsp;");
/* 1423 */                   out.print(avgvalue);
/* 1424 */                   out.write("&nbsp;&nbsp;");
/* 1425 */                   out.print(FormatUtil.getString(unit));
/* 1426 */                   out.write(" \n                </td>\n              </tr>\n              ");
/* 1427 */                   ArrayList threshold = (ArrayList)request.getAttribute("thresholdDetails");
/*      */                   
/* 1429 */                   if (threshold.size() > 0)
/*      */                   {
/* 1431 */                     String critical = (String)threshold.get(0);
/* 1432 */                     String warning = (String)threshold.get(1);
/* 1433 */                     String clear = (String)threshold.get(2);
/* 1434 */                     String units = FormatUtil.getString((String)threshold.get(3));
/*      */                     
/*      */ 
/* 1437 */                     out.write("\n              <tr > \n                <td class=\"bodytext\" width='16%'>");
/* 1438 */                     out.print(FormatUtil.getString("am.webclient.admin.thresholddetails.link"));
/* 1439 */                     out.write(": \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1440 */                     out.print(FormatUtil.getString("Critical"));
/* 1441 */                     out.write(":&nbsp;&nbsp;");
/* 1442 */                     out.print(critical);
/* 1443 */                     out.write("&nbsp;&nbsp;");
/* 1444 */                     out.print(FormatUtil.getString(unit));
/* 1445 */                     out.write(" \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1446 */                     out.print(FormatUtil.getString("Warning"));
/* 1447 */                     out.write(":&nbsp;&nbsp;");
/* 1448 */                     out.print(warning);
/* 1449 */                     out.write("&nbsp;&nbsp;");
/* 1450 */                     out.print(FormatUtil.getString(unit));
/* 1451 */                     out.write(" \n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1452 */                     out.print(FormatUtil.getString("Clear"));
/* 1453 */                     out.write(" \n                  :&nbsp;&nbsp;");
/* 1454 */                     out.print(clear);
/* 1455 */                     out.write("&nbsp;&nbsp;");
/* 1456 */                     out.print(FormatUtil.getString(unit));
/* 1457 */                     out.write(" \n                </td>\n              </tr>\n              ");
/*      */                   }
/* 1459 */                   out.write("\n            </table></td>\n        </tr>\n        ");
/* 1460 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1461 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1465 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1466 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 1469 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1470 */               out.write(" </table></td>\n  </tr>\n</table>\n\n<br>\n\n\n\n\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n <tr> \n    <td class=\"tableheading\"  width='100%' colspan='");
/* 1471 */               out.print(sizeofseq + 2);
/* 1472 */               out.write(39);
/* 1473 */               out.write(62);
/* 1474 */               out.print(FormatUtil.getString(nameofmonitor));
/* 1475 */               out.write(" </td>\n  </tr>\n  ");
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1481 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/* 1483 */                 out.write("\n\t<tr> \n    <td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 1484 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1485 */                 out.write("</td>\n    <td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 1486 */                 out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1487 */                 out.write("</td>\n   ");
/* 1488 */                 for (int y = 0; y < list.size(); y++)
/*      */                 {
/* 1490 */                   ArrayList d1 = (ArrayList)list.get(y);
/* 1491 */                   out.write("\n\t<td class=\"columnheadingrightborder\" title='");
/* 1492 */                   out.print((String)d1.get(1));
/* 1493 */                   out.write(39);
/* 1494 */                   out.write(32);
/* 1495 */                   out.write(62);
/* 1496 */                   out.print(y + 1);
/* 1497 */                   out.write(46);
/* 1498 */                   out.print(FormatUtil.getformatedText((String)d1.get(1), 30, 6));
/* 1499 */                   out.write("</td>\n    ");
/*      */                 }
/* 1501 */                 out.write("\n  </tr>\n   \n ");
/*      */                 
/*      */                 try
/*      */                 {
/* 1505 */                   for (int i = 0; i < pdfdata.size(); i++)
/*      */                   {
/* 1507 */                     String bgcolor = "";
/* 1508 */                     String selectedColor = "class=\"selectedborder\"";
/* 1509 */                     if (i % 2 == 0)
/*      */                     {
/* 1511 */                       bgcolor = "class=\"whitegrayborder\"";
/*      */                     }
/*      */                     else
/*      */                     {
/* 1515 */                       bgcolor = "class=\"yellowgrayborder\"";
/*      */                     }
/* 1517 */                     ArrayList a1 = (ArrayList)pdfdata.get(i);
/*      */                     
/* 1519 */                     long archivedTime = ((Long)a1.get(0)).longValue();
/* 1520 */                     pageContext.setAttribute("date", new Date(archivedTime));
/*      */                     
/*      */ 
/* 1523 */                     out.write("\n  <tr>\n\t<td ");
/* 1524 */                     out.print(bgcolor);
/* 1525 */                     out.write(" align=\"center\">");
/* 1526 */                     if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_page_context))
/*      */                       return;
/* 1528 */                     out.write("</td>\n    <td ");
/* 1529 */                     out.print(bgcolor);
/* 1530 */                     out.write(" align=\"center\">");
/* 1531 */                     if (_jspx_meth_fmt_005fformatDate_005f3(_jspx_page_context))
/*      */                       return;
/* 1533 */                     out.write("</td>\n");
/*      */                     
/* 1535 */                     for (int k = 1; k < a1.size(); k++)
/*      */                     {
/*      */ 
/* 1538 */                       for (int m = 0; m < list.size(); m++)
/*      */                       {
/* 1540 */                         ArrayList q1 = (ArrayList)list.get(m);
/*      */                         
/* 1542 */                         String key = (String)q1.get(0);
/* 1543 */                         java.util.HashMap h1 = (java.util.HashMap)a1.get(k);
/* 1544 */                         String avgvalues = null;
/*      */                         
/* 1546 */                         Properties p1 = (Properties)h1.get(key);
/*      */                         
/* 1548 */                         if (p1 != null)
/*      */                         {
/* 1550 */                           avgvalues = p1.getProperty("AVGVALUE");
/*      */                         }
/*      */                         else {
/* 1553 */                           avgvalues = "-";
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 1558 */                         out.write("\n<td ");
/* 1559 */                         out.print(bgcolor);
/* 1560 */                         out.write(" align=\"left\">");
/* 1561 */                         out.print(avgvalues);
/* 1562 */                         out.write("</td>\n");
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 1567 */                     out.write("\n  </tr>\n");
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/* 1574 */                   exc.printStackTrace();
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1580 */               out.write("\n  <tr> \n    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/* 1581 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1582 */               out.write("</td>\n    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/* 1583 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1584 */               out.write("</td>\n    <td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1585 */               out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 1586 */               out.write(32);
/* 1587 */               out.print(attUnit);
/* 1588 */               out.write("</td>\n    <td width=\"23%\" class=\"columnheading\" align=\"center\">");
/* 1589 */               out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 1590 */               out.write(32);
/* 1591 */               out.print(attUnit);
/* 1592 */               out.write("</td>\n    <td width=\"21%\" class=\"columnheading\" align=\"center\">");
/* 1593 */               out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 1594 */               out.write(32);
/* 1595 */               out.print(attUnit);
/* 1596 */               out.write("</td>\n  </tr>\n  ");
/*      */               
/* 1598 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 1599 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 1600 */               _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */               
/* 1602 */               _jspx_th_logic_005fiterate_005f0.setName("data");
/*      */               
/* 1604 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/* 1606 */               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */               
/* 1608 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.Properties");
/* 1609 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 1610 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 1611 */                 Properties row = null;
/* 1612 */                 Integer i = null;
/* 1613 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 1614 */                   out = _jspx_page_context.pushBody();
/* 1615 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 1616 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/* 1618 */                 row = (Properties)_jspx_page_context.findAttribute("row");
/* 1619 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                 for (;;) {
/* 1621 */                   out.write(10);
/* 1622 */                   out.write(32);
/* 1623 */                   out.write(32);
/*      */                   
/* 1625 */                   String bgcolor = "";
/* 1626 */                   String selectedColor = "class=\"selectedborder\"";
/* 1627 */                   if (i.intValue() % 2 == 0)
/*      */                   {
/* 1629 */                     bgcolor = "class=\"whitegrayborder\"";
/*      */                   }
/*      */                   else
/*      */                   {
/* 1633 */                     bgcolor = "class=\"yellowgrayborder\"";
/*      */                   }
/*      */                   
/* 1636 */                   out.write("\n   <tr> \n\t");
/*      */                   
/* 1638 */                   long archivedTime = ((Long)row.get("ARCHIVEDTIME")).longValue();
/* 1639 */                   String resourcetype = String.valueOf(request.getAttribute("type"));
/* 1640 */                   String avgVal = null;
/* 1641 */                   String minVal; String maxVal; if (resourcetype.equals("weblogic-server"))
/*      */                   {
/* 1643 */                     String minVal = String.valueOf(Long.parseLong(String.valueOf(row.get("MINVALUE"))) / 1024L);
/* 1644 */                     String maxVal = String.valueOf(Long.parseLong(String.valueOf(row.get("MAXVALUE"))) / 1024L);
/* 1645 */                     avgVal = String.valueOf(Long.parseLong(String.valueOf(row.get("AVGVALUE"))) / 1024L);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1649 */                     minVal = String.valueOf(row.get("MINVALUE"));
/* 1650 */                     maxVal = String.valueOf(row.get("MAXVALUE"));
/* 1651 */                     avgVal = String.valueOf(row.get("AVGVALUE"));
/*      */                   }
/*      */                   
/* 1654 */                   out.write(10);
/* 1655 */                   out.write(9);
/*      */                   
/*      */ 
/* 1658 */                   pageContext.setAttribute("date", new Date(archivedTime));
/* 1659 */                   pageContext.setAttribute("minVal", minVal);
/* 1660 */                   pageContext.setAttribute("maxVal", maxVal);
/* 1661 */                   pageContext.setAttribute("avgVal", avgVal);
/*      */                   
/* 1663 */                   out.write("\n\t\n    <td ");
/* 1664 */                   out.print(bgcolor);
/* 1665 */                   out.write(" align=\"center\">");
/* 1666 */                   if (_jspx_meth_fmt_005fformatDate_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 1668 */                   out.write("</td>\n    <td ");
/* 1669 */                   out.print(bgcolor);
/* 1670 */                   out.write(" align=\"center\">");
/* 1671 */                   if (_jspx_meth_fmt_005fformatDate_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 1673 */                   out.write("</td>\n\n");
/*      */                   
/* 1675 */                   ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1676 */                   _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1677 */                   _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fiterate_005f0);
/* 1678 */                   int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1679 */                   if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                     for (;;) {
/* 1681 */                       out.write("\n    ");
/*      */                       
/* 1683 */                       WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1684 */                       _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1685 */                       _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                       
/* 1687 */                       _jspx_th_c_005fwhen_005f6.setTest("${minvalue == minVal}");
/* 1688 */                       int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1689 */                       if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                         for (;;) {
/* 1691 */                           out.write("\n        <td ");
/* 1692 */                           out.print(bgcolor);
/* 1693 */                           out.write(" align=\"center\"><table align=\"center\"><tr><td class=\"selectedborder\" align=\"center\">");
/* 1694 */                           if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                             return;
/* 1696 */                           out.write("</td></tr></table></td>\n    ");
/* 1697 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1698 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1702 */                       if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1703 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                       }
/*      */                       
/* 1706 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1707 */                       out.write("\n    ");
/*      */                       
/* 1709 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1710 */                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1711 */                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1712 */                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1713 */                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                         for (;;) {
/* 1715 */                           out.write("\n        <td ");
/* 1716 */                           out.print(bgcolor);
/* 1717 */                           out.write(" align=\"center\">");
/* 1718 */                           if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                             return;
/* 1720 */                           out.write("</td>\n    ");
/* 1721 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1722 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1726 */                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1727 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                       }
/*      */                       
/* 1730 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1731 */                       out.write(10);
/* 1732 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1733 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1737 */                   if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1738 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                   }
/*      */                   
/* 1741 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1742 */                   out.write("\n\n\n\n");
/*      */                   
/* 1744 */                   ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1745 */                   _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1746 */                   _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_logic_005fiterate_005f0);
/* 1747 */                   int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1748 */                   if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                     for (;;) {
/* 1750 */                       out.write("\n    ");
/*      */                       
/* 1752 */                       WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1753 */                       _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1754 */                       _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                       
/* 1756 */                       _jspx_th_c_005fwhen_005f7.setTest("${maxvalue == maxVal}");
/* 1757 */                       int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1758 */                       if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                         for (;;) {
/* 1760 */                           out.write("\n        <td ");
/* 1761 */                           out.print(bgcolor);
/* 1762 */                           out.write(" align=\"center\"><table align=\"center\"><tr><td class=\"selectedborder\" align=\"center\">");
/* 1763 */                           if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/*      */                             return;
/* 1765 */                           out.write("</td></tr></table></td>\n    ");
/* 1766 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1767 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1771 */                       if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1772 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                       }
/*      */                       
/* 1775 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1776 */                       out.write("\n    ");
/*      */                       
/* 1778 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1779 */                       _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1780 */                       _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1781 */                       int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1782 */                       if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                         for (;;) {
/* 1784 */                           out.write("\n        <td ");
/* 1785 */                           out.print(bgcolor);
/* 1786 */                           out.write(" align=\"center\">");
/* 1787 */                           if (_jspx_meth_fmt_005fformatNumber_005f3(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/*      */                             return;
/* 1789 */                           out.write("</td>\n    ");
/* 1790 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1791 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1795 */                       if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1796 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                       }
/*      */                       
/* 1799 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1800 */                       out.write(10);
/* 1801 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1802 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1806 */                   if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1807 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                   }
/*      */                   
/* 1810 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1811 */                   out.write("\n    <td ");
/* 1812 */                   out.print(bgcolor);
/* 1813 */                   out.write(" align=\"right\">");
/* 1814 */                   if (_jspx_meth_fmt_005fformatNumber_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 1816 */                   out.write(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n  </tr>\n\n  ");
/* 1817 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 1818 */                   row = (Properties)_jspx_page_context.findAttribute("row");
/* 1819 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 1820 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1823 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 1824 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1827 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 1828 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/* 1831 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 1832 */               out.write(10);
/*      */               
/*      */ 
/*      */ 
/* 1836 */               out.write("\n\n</table>\n");
/*      */             }
/*      */             
/* 1839 */             out.write("\n<br>\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n     <td class=\"columnheading\"><b>");
/* 1840 */             out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 1841 */             out.write("</b><br></td>\n  </tr> \n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 1842 */             out.print(FormatUtil.getString("am.webclient.historydata.archiving.text"));
/* 1843 */             out.write("</b>: ");
/* 1844 */             out.print(FormatUtil.getString("am.webclient.historydata.archivingnote.text"));
/* 1845 */             out.write(".</td>\n  </tr>  \n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 1846 */             out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 1847 */             out.write("</b>: ");
/* 1848 */             out.print(FormatUtil.getString("am.webclient.historydata.minvaluenote.text"));
/* 1849 */             out.write(".<br><br></td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\"> <b>");
/* 1850 */             out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 1851 */             out.write(" </b>: ");
/* 1852 */             out.print(FormatUtil.getString("am.webclient.historydata.maxvaluenote.text"));
/* 1853 */             out.write(".<br><br></td>\n  </tr>\n  <tr>\n   <tr>\n     <td class=\"bodytext\"> <b>");
/* 1854 */             out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 1855 */             out.write(" </b>: ");
/* 1856 */             out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgnote.text"));
/* 1857 */             out.write(".</td>\n  </tr>  \n</table>\n");
/* 1858 */             response.setContentType("text/html;charset=UTF-8");
/* 1859 */             out.write(10);
/*      */             
/* 1861 */             if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*      */             {
/*      */ 
/* 1864 */               out.write("\n<script type=\"text/javascript\">\n\tvar _gaq = _gaq || [];\t\t\t\t\t\t\t//NO I18N\n\t_gaq.push(['_setAccount', 'UA-202658-68']);\t\t//NO I18N\n\t_gaq.push(['_trackPageview']);\t\t\t\t\t//NO I18N\n\n\t(function() {\n\tvar ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\t//NO I18N\n\tga.src = '//www.google-analytics.com/ga.js';\t//NO I18N\n\t\n\tvar s = document.getElementsByTagName('script')[0]; \t//NO I18N\n\ts.parentNode.insertBefore(ga, s);\t\t\t\t\t\t//NO I18N\n\t})();\n</script>\n");
/*      */             }
/*      */             
/*      */ 
/* 1868 */             out.write("\n</body>\n</html>\n");
/*      */           }
/* 1870 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1871 */         out = _jspx_out;
/* 1872 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1873 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1874 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1877 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1883 */     PageContext pageContext = _jspx_page_context;
/* 1884 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1886 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1887 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1888 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1890 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1892 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1893 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1894 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1895 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1896 */       return true;
/*      */     }
/* 1898 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1904 */     PageContext pageContext = _jspx_page_context;
/* 1905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1907 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1908 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1909 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 1911 */     _jspx_th_c_005fif_005f0.setTest("${param.period !='4'}");
/* 1912 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1913 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1915 */         out.write(" \n\n    document.forms[0].reporttype.value=type;\n    document.forms[0].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[0].submit();\n    document.forms[0].reporttype.value=\"html\";\n");
/* 1916 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1917 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1921 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1922 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1923 */       return true;
/*      */     }
/* 1925 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1931 */     PageContext pageContext = _jspx_page_context;
/* 1932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1934 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1935 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1936 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 1938 */     _jspx_th_c_005fif_005f1.setTest("${param.period =='4'}");
/* 1939 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1940 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1942 */         out.write(" \n    document.forms[1].reporttype.value=type;\n    document.forms[1].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[1].submit();\n    document.forms[1].reporttype.value=\"html\";\n");
/* 1943 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1944 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1948 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1949 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1950 */       return true;
/*      */     }
/* 1952 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1958 */     PageContext pageContext = _jspx_page_context;
/* 1959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1961 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1962 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1963 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 1965 */     _jspx_th_c_005fif_005f2.setTest("${param.period !='4'}");
/* 1966 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1967 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 1969 */         out.write("\n\n    document.forms[0].reporttype.value=type;\n    document.forms[0].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[0].submit();\n    document.forms[0].reporttype.value=\"html\";\n");
/* 1970 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1971 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1975 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1976 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1977 */       return true;
/*      */     }
/* 1979 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1985 */     PageContext pageContext = _jspx_page_context;
/* 1986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1988 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1989 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1990 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 1992 */     _jspx_th_c_005fif_005f3.setTest("${param.period =='4'}");
/* 1993 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1994 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1996 */         out.write("\n    document.forms[1].reporttype.value=type;\n    document.forms[1].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[1].submit();\n    document.forms[1].reporttype.value=\"html\";\n");
/* 1997 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1998 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2002 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2003 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2004 */       return true;
/*      */     }
/* 2006 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2007 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2012 */     PageContext pageContext = _jspx_page_context;
/* 2013 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2015 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2016 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2017 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2019 */     _jspx_th_html_005fhidden_005f0.setProperty("reporttype");
/*      */     
/* 2021 */     _jspx_th_html_005fhidden_005f0.setValue("html");
/* 2022 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2023 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2037 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2040 */     _jspx_th_html_005fhidden_005f1.setProperty("businessPeriod");
/*      */     
/* 2042 */     _jspx_th_html_005fhidden_005f1.setValue("oni");
/* 2043 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2044 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2045 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2046 */       return true;
/*      */     }
/* 2048 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2054 */     PageContext pageContext = _jspx_page_context;
/* 2055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2057 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2058 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 2059 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2061 */     _jspx_th_html_005fhidden_005f2.setProperty("confEndTime");
/*      */     
/* 2063 */     _jspx_th_html_005fhidden_005f2.setValue("");
/* 2064 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 2065 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 2066 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2067 */       return true;
/*      */     }
/* 2069 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2075 */     PageContext pageContext = _jspx_page_context;
/* 2076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2078 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2079 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 2080 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2082 */     _jspx_th_html_005fhidden_005f3.setProperty("confStartTime");
/*      */     
/* 2084 */     _jspx_th_html_005fhidden_005f3.setValue("");
/* 2085 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 2086 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 2087 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2088 */       return true;
/*      */     }
/* 2090 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2096 */     PageContext pageContext = _jspx_page_context;
/* 2097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2099 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2100 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2101 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 2102 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2103 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2105 */         out.write(32);
/* 2106 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2107 */           return true;
/* 2108 */         out.write(32);
/* 2109 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2110 */           return true;
/* 2111 */         out.write(32);
/* 2112 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2117 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2118 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2119 */       return true;
/*      */     }
/* 2121 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2127 */     PageContext pageContext = _jspx_page_context;
/* 2128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2130 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2131 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2132 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2134 */     _jspx_th_c_005fwhen_005f0.setTest("${'1' != '1'}");
/* 2135 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2136 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2138 */         out.write("\n            ");
/* 2139 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 2140 */           return true;
/* 2141 */         out.write("\n            ");
/* 2142 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2143 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2147 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2148 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2149 */       return true;
/*      */     }
/* 2151 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2157 */     PageContext pageContext = _jspx_page_context;
/* 2158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2160 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2161 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2162 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2164 */     _jspx_th_html_005ftext_005f0.setSize("14");
/*      */     
/* 2166 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 2168 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 2170 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 2172 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 2174 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtextselected");
/* 2175 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2176 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2177 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2178 */       return true;
/*      */     }
/* 2180 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2186 */     PageContext pageContext = _jspx_page_context;
/* 2187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2189 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2190 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2191 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2192 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2193 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2195 */         out.write(32);
/* 2196 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 2197 */           return true;
/* 2198 */         out.write("\n             ");
/* 2199 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2200 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2204 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2205 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2206 */       return true;
/*      */     }
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2214 */     PageContext pageContext = _jspx_page_context;
/* 2215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2217 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2218 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 2219 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2221 */     _jspx_th_html_005ftext_005f1.setSize("13");
/*      */     
/* 2223 */     _jspx_th_html_005ftext_005f1.setProperty("startDate");
/*      */     
/* 2225 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 2227 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 2229 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetStartTime(this.value)");
/* 2230 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 2231 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 2232 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2233 */       return true;
/*      */     }
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2241 */     PageContext pageContext = _jspx_page_context;
/* 2242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2244 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2245 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2246 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 2247 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2248 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2250 */         out.write(32);
/* 2251 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2252 */           return true;
/* 2253 */         out.write(32);
/* 2254 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2255 */           return true;
/* 2256 */         out.write(32);
/* 2257 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2262 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2263 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2264 */       return true;
/*      */     }
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2272 */     PageContext pageContext = _jspx_page_context;
/* 2273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2275 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2276 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2277 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2279 */     _jspx_th_c_005fwhen_005f1.setTest("${'1'!= '1'}");
/* 2280 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2281 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2283 */         out.write("\n            ");
/* 2284 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 2285 */           return true;
/* 2286 */         out.write("\n            ");
/* 2287 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2288 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2292 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2293 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2294 */       return true;
/*      */     }
/* 2296 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2302 */     PageContext pageContext = _jspx_page_context;
/* 2303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2305 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2306 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 2307 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2309 */     _jspx_th_html_005ftext_005f2.setProperty("endDate");
/*      */     
/* 2311 */     _jspx_th_html_005ftext_005f2.setSize("14");
/*      */     
/* 2313 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 2315 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 2317 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 2319 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtextselected");
/* 2320 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 2321 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 2322 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2323 */       return true;
/*      */     }
/* 2325 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2331 */     PageContext pageContext = _jspx_page_context;
/* 2332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2334 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2335 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2336 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2337 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2338 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2340 */         out.write(32);
/* 2341 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 2342 */           return true;
/* 2343 */         out.write("\n            ");
/* 2344 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2349 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2350 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2351 */       return true;
/*      */     }
/* 2353 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2359 */     PageContext pageContext = _jspx_page_context;
/* 2360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2362 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2363 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2364 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 2366 */     _jspx_th_html_005ftext_005f3.setProperty("endDate");
/*      */     
/* 2368 */     _jspx_th_html_005ftext_005f3.setSize("13");
/*      */     
/* 2370 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 2372 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 2374 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetEndTime(this.value)");
/* 2375 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2376 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2377 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2378 */       return true;
/*      */     }
/* 2380 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2381 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2386 */     PageContext pageContext = _jspx_page_context;
/* 2387 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2389 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2390 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 2391 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 2393 */     _jspx_th_html_005fhidden_005f4.setProperty("businessPeriod");
/*      */     
/* 2395 */     _jspx_th_html_005fhidden_005f4.setValue("oni");
/* 2396 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 2397 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 2398 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 2399 */       return true;
/*      */     }
/* 2401 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 2402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2407 */     PageContext pageContext = _jspx_page_context;
/* 2408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2410 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2411 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/* 2412 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 2414 */     _jspx_th_html_005fhidden_005f5.setProperty("reporttype");
/*      */     
/* 2416 */     _jspx_th_html_005fhidden_005f5.setValue("html");
/* 2417 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/* 2418 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/* 2419 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 2420 */       return true;
/*      */     }
/* 2422 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 2423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2428 */     PageContext pageContext = _jspx_page_context;
/* 2429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2431 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2432 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2433 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 2434 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2435 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 2437 */         out.write(32);
/* 2438 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2439 */           return true;
/* 2440 */         out.write(32);
/* 2441 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2442 */           return true;
/* 2443 */         out.write(32);
/* 2444 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2445 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2449 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2450 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2451 */       return true;
/*      */     }
/* 2453 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2459 */     PageContext pageContext = _jspx_page_context;
/* 2460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2462 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2463 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2464 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2466 */     _jspx_th_c_005fwhen_005f2.setTest("${'1' != '1'}");
/* 2467 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2468 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 2470 */         out.write("\n            ");
/* 2471 */         if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 2472 */           return true;
/* 2473 */         out.write("\n            ");
/* 2474 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2475 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2479 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2480 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2481 */       return true;
/*      */     }
/* 2483 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2489 */     PageContext pageContext = _jspx_page_context;
/* 2490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2492 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2493 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2494 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2496 */     _jspx_th_html_005ftext_005f4.setSize("14");
/*      */     
/* 2498 */     _jspx_th_html_005ftext_005f4.setProperty("startDate");
/*      */     
/* 2500 */     _jspx_th_html_005ftext_005f4.setStyleId("start");
/*      */     
/* 2502 */     _jspx_th_html_005ftext_005f4.setReadonly(true);
/*      */     
/* 2504 */     _jspx_th_html_005ftext_005f4.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 2506 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtextselected");
/* 2507 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2508 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2509 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2510 */       return true;
/*      */     }
/* 2512 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2513 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2518 */     PageContext pageContext = _jspx_page_context;
/* 2519 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2521 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2522 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2523 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2524 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2525 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2527 */         out.write(32);
/* 2528 */         if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 2529 */           return true;
/* 2530 */         out.write("\n             ");
/* 2531 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2532 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2536 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2537 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2538 */       return true;
/*      */     }
/* 2540 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2546 */     PageContext pageContext = _jspx_page_context;
/* 2547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2549 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2550 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 2551 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2553 */     _jspx_th_html_005ftext_005f5.setSize("13");
/*      */     
/* 2555 */     _jspx_th_html_005ftext_005f5.setProperty("startDate");
/*      */     
/* 2557 */     _jspx_th_html_005ftext_005f5.setStyleId("start");
/*      */     
/* 2559 */     _jspx_th_html_005ftext_005f5.setReadonly(true);
/*      */     
/* 2561 */     _jspx_th_html_005ftext_005f5.setOnchange("javascript:fnSetStartTime(this.value)");
/* 2562 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 2563 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 2564 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2565 */       return true;
/*      */     }
/* 2567 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2573 */     PageContext pageContext = _jspx_page_context;
/* 2574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2576 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2577 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2578 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 2579 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2580 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 2582 */         out.write(32);
/* 2583 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2584 */           return true;
/* 2585 */         out.write(32);
/* 2586 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2587 */           return true;
/* 2588 */         out.write(32);
/* 2589 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2590 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2594 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2595 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2596 */       return true;
/*      */     }
/* 2598 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2604 */     PageContext pageContext = _jspx_page_context;
/* 2605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2607 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2608 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2609 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2611 */     _jspx_th_c_005fwhen_005f3.setTest("${'1'!= '1'}");
/* 2612 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2613 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 2615 */         out.write("\n            ");
/* 2616 */         if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2617 */           return true;
/* 2618 */         out.write("\n            ");
/* 2619 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2620 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2624 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2625 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2626 */       return true;
/*      */     }
/* 2628 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2634 */     PageContext pageContext = _jspx_page_context;
/* 2635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2637 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2638 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 2639 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2641 */     _jspx_th_html_005ftext_005f6.setProperty("endDate");
/*      */     
/* 2643 */     _jspx_th_html_005ftext_005f6.setSize("14");
/*      */     
/* 2645 */     _jspx_th_html_005ftext_005f6.setStyleId("end");
/*      */     
/* 2647 */     _jspx_th_html_005ftext_005f6.setReadonly(true);
/*      */     
/* 2649 */     _jspx_th_html_005ftext_005f6.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 2651 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtextselected");
/* 2652 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 2653 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 2654 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2655 */       return true;
/*      */     }
/* 2657 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2663 */     PageContext pageContext = _jspx_page_context;
/* 2664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2666 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2667 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2668 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2669 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2670 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2672 */         out.write(32);
/* 2673 */         if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 2674 */           return true;
/* 2675 */         out.write("\n            ");
/* 2676 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2677 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2681 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2682 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2683 */       return true;
/*      */     }
/* 2685 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2686 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2691 */     PageContext pageContext = _jspx_page_context;
/* 2692 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2694 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2695 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 2696 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2698 */     _jspx_th_html_005ftext_005f7.setProperty("endDate");
/*      */     
/* 2700 */     _jspx_th_html_005ftext_005f7.setSize("13");
/*      */     
/* 2702 */     _jspx_th_html_005ftext_005f7.setStyleId("end");
/*      */     
/* 2704 */     _jspx_th_html_005ftext_005f7.setReadonly(true);
/*      */     
/* 2706 */     _jspx_th_html_005ftext_005f7.setOnchange("javascript:fnSetEndTime(this.value)");
/* 2707 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 2708 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 2709 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2710 */       return true;
/*      */     }
/* 2712 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f6(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2718 */     PageContext pageContext = _jspx_page_context;
/* 2719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2721 */     HiddenTag _jspx_th_html_005fhidden_005f6 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2722 */     _jspx_th_html_005fhidden_005f6.setPageContext(_jspx_page_context);
/* 2723 */     _jspx_th_html_005fhidden_005f6.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 2725 */     _jspx_th_html_005fhidden_005f6.setProperty("businessPeriod");
/*      */     
/* 2727 */     _jspx_th_html_005fhidden_005f6.setValue("oni");
/* 2728 */     int _jspx_eval_html_005fhidden_005f6 = _jspx_th_html_005fhidden_005f6.doStartTag();
/* 2729 */     if (_jspx_th_html_005fhidden_005f6.doEndTag() == 5) {
/* 2730 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 2731 */       return true;
/*      */     }
/* 2733 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 2734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f7(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2739 */     PageContext pageContext = _jspx_page_context;
/* 2740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2742 */     HiddenTag _jspx_th_html_005fhidden_005f7 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2743 */     _jspx_th_html_005fhidden_005f7.setPageContext(_jspx_page_context);
/* 2744 */     _jspx_th_html_005fhidden_005f7.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 2746 */     _jspx_th_html_005fhidden_005f7.setProperty("reporttype");
/*      */     
/* 2748 */     _jspx_th_html_005fhidden_005f7.setValue("html");
/* 2749 */     int _jspx_eval_html_005fhidden_005f7 = _jspx_th_html_005fhidden_005f7.doStartTag();
/* 2750 */     if (_jspx_th_html_005fhidden_005f7.doEndTag() == 5) {
/* 2751 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 2752 */       return true;
/*      */     }
/* 2754 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 2755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2760 */     PageContext pageContext = _jspx_page_context;
/* 2761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2763 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2764 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 2765 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f3);
/*      */     
/* 2767 */     _jspx_th_html_005fselect_005f1.setProperty("businessPeriod");
/*      */     
/* 2769 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */     
/* 2771 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 2773 */     _jspx_th_html_005fselect_005f1.setStyleId("per");
/*      */     
/* 2775 */     _jspx_th_html_005fselect_005f1.setOnchange("fnbHour(this)");
/* 2776 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 2777 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 2778 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2779 */         out = _jspx_page_context.pushBody();
/* 2780 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 2781 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2784 */         out.write("\n               \t\t\t\t");
/* 2785 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 2786 */           return true;
/* 2787 */         out.write("\n\t \t\t\t");
/* 2788 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2789 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2792 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2793 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2796 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2797 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2798 */       return true;
/*      */     }
/* 2800 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2806 */     PageContext pageContext = _jspx_page_context;
/* 2807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2809 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 2810 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 2811 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 2813 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("businessRuleNames");
/* 2814 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 2815 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 2816 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2817 */       return true;
/*      */     }
/* 2819 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f8(JspTag _jspx_th_html_005fform_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2825 */     PageContext pageContext = _jspx_page_context;
/* 2826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2828 */     HiddenTag _jspx_th_html_005fhidden_005f8 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2829 */     _jspx_th_html_005fhidden_005f8.setPageContext(_jspx_page_context);
/* 2830 */     _jspx_th_html_005fhidden_005f8.setParent((Tag)_jspx_th_html_005fform_005f3);
/*      */     
/* 2832 */     _jspx_th_html_005fhidden_005f8.setProperty("reporttype");
/*      */     
/* 2834 */     _jspx_th_html_005fhidden_005f8.setValue("html");
/* 2835 */     int _jspx_eval_html_005fhidden_005f8 = _jspx_th_html_005fhidden_005f8.doStartTag();
/* 2836 */     if (_jspx_th_html_005fhidden_005f8.doEndTag() == 5) {
/* 2837 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 2838 */       return true;
/*      */     }
/* 2840 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 2841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2846 */     PageContext pageContext = _jspx_page_context;
/* 2847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2849 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2850 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2851 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2853 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/* 2854 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2855 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2857 */       return true;
/*      */     }
/* 2859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2865 */     PageContext pageContext = _jspx_page_context;
/* 2866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2868 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2869 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2870 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 2872 */     _jspx_th_c_005fset_005f0.setVar("rname");
/* 2873 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2874 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2875 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2876 */         out = _jspx_page_context.pushBody();
/* 2877 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2878 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2881 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 2882 */           return true;
/* 2883 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2887 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2888 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2891 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2892 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2893 */       return true;
/*      */     }
/* 2895 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2901 */     PageContext pageContext = _jspx_page_context;
/* 2902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2904 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2905 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2906 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 2908 */     _jspx_th_c_005fout_005f2.setValue("${monitortype}");
/* 2909 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2910 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2912 */       return true;
/*      */     }
/* 2914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2920 */     PageContext pageContext = _jspx_page_context;
/* 2921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2923 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2924 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2925 */     _jspx_th_c_005fset_005f1.setParent(null);
/*      */     
/* 2927 */     _jspx_th_c_005fset_005f1.setVar("resourcename");
/* 2928 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2929 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2930 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2931 */         out = _jspx_page_context.pushBody();
/* 2932 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2933 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2936 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 2937 */           return true;
/* 2938 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2939 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2942 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2943 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2946 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2947 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2948 */       return true;
/*      */     }
/* 2950 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2956 */     PageContext pageContext = _jspx_page_context;
/* 2957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2959 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2960 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2961 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 2963 */     _jspx_th_c_005fout_005f3.setValue("${resourcename}");
/* 2964 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2965 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2966 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2967 */       return true;
/*      */     }
/* 2969 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2975 */     PageContext pageContext = _jspx_page_context;
/* 2976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2978 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2979 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 2980 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*      */     
/* 2982 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${date1}");
/*      */     
/* 2984 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("MMM d,yyyy");
/* 2985 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 2986 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 2987 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2988 */       return true;
/*      */     }
/* 2990 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2996 */     PageContext pageContext = _jspx_page_context;
/* 2997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2999 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3000 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 3001 */     _jspx_th_fmt_005fformatDate_005f1.setParent(null);
/*      */     
/* 3003 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${date1}");
/*      */     
/* 3005 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("H:mm");
/* 3006 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 3007 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 3008 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 3009 */       return true;
/*      */     }
/* 3011 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 3012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3017 */     PageContext pageContext = _jspx_page_context;
/* 3018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3020 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3021 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3022 */     _jspx_th_c_005fset_005f2.setParent(null);
/*      */     
/* 3024 */     _jspx_th_c_005fset_005f2.setVar("rname");
/* 3025 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3026 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3027 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3028 */         out = _jspx_page_context.pushBody();
/* 3029 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3030 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3033 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 3034 */           return true;
/* 3035 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3039 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3040 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3043 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3044 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3045 */       return true;
/*      */     }
/* 3047 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3053 */     PageContext pageContext = _jspx_page_context;
/* 3054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3056 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3057 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3058 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 3060 */     _jspx_th_c_005fout_005f4.setValue("${monitortype}");
/* 3061 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3062 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3063 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3064 */       return true;
/*      */     }
/* 3066 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3072 */     PageContext pageContext = _jspx_page_context;
/* 3073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3075 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3076 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3077 */     _jspx_th_c_005fset_005f3.setParent(null);
/*      */     
/* 3079 */     _jspx_th_c_005fset_005f3.setVar("resourcename");
/* 3080 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3081 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3082 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3083 */         out = _jspx_page_context.pushBody();
/* 3084 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3085 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3088 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 3089 */           return true;
/* 3090 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3091 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3094 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3095 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3098 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3099 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3100 */       return true;
/*      */     }
/* 3102 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3108 */     PageContext pageContext = _jspx_page_context;
/* 3109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3111 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3112 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3113 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 3115 */     _jspx_th_c_005fout_005f5.setValue("${resourcename}");
/* 3116 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3117 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3118 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3119 */       return true;
/*      */     }
/* 3121 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3127 */     PageContext pageContext = _jspx_page_context;
/* 3128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3130 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3131 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 3132 */     _jspx_th_fmt_005fformatDate_005f2.setParent(null);
/*      */     
/* 3134 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${date}");
/*      */     
/* 3136 */     _jspx_th_fmt_005fformatDate_005f2.setPattern("MMM d,yyyy");
/* 3137 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 3138 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 3139 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 3140 */       return true;
/*      */     }
/* 3142 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 3143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3148 */     PageContext pageContext = _jspx_page_context;
/* 3149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3151 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f3 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3152 */     _jspx_th_fmt_005fformatDate_005f3.setPageContext(_jspx_page_context);
/* 3153 */     _jspx_th_fmt_005fformatDate_005f3.setParent(null);
/*      */     
/* 3155 */     _jspx_th_fmt_005fformatDate_005f3.setValue("${date}");
/*      */     
/* 3157 */     _jspx_th_fmt_005fformatDate_005f3.setPattern("H:mm");
/* 3158 */     int _jspx_eval_fmt_005fformatDate_005f3 = _jspx_th_fmt_005fformatDate_005f3.doStartTag();
/* 3159 */     if (_jspx_th_fmt_005fformatDate_005f3.doEndTag() == 5) {
/* 3160 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 3161 */       return true;
/*      */     }
/* 3163 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 3164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3169 */     PageContext pageContext = _jspx_page_context;
/* 3170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3172 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f4 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3173 */     _jspx_th_fmt_005fformatDate_005f4.setPageContext(_jspx_page_context);
/* 3174 */     _jspx_th_fmt_005fformatDate_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3176 */     _jspx_th_fmt_005fformatDate_005f4.setValue("${date}");
/*      */     
/* 3178 */     _jspx_th_fmt_005fformatDate_005f4.setPattern("MMM d,yyyy");
/* 3179 */     int _jspx_eval_fmt_005fformatDate_005f4 = _jspx_th_fmt_005fformatDate_005f4.doStartTag();
/* 3180 */     if (_jspx_th_fmt_005fformatDate_005f4.doEndTag() == 5) {
/* 3181 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 3182 */       return true;
/*      */     }
/* 3184 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 3185 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f5(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3190 */     PageContext pageContext = _jspx_page_context;
/* 3191 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3193 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f5 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3194 */     _jspx_th_fmt_005fformatDate_005f5.setPageContext(_jspx_page_context);
/* 3195 */     _jspx_th_fmt_005fformatDate_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3197 */     _jspx_th_fmt_005fformatDate_005f5.setValue("${date}");
/*      */     
/* 3199 */     _jspx_th_fmt_005fformatDate_005f5.setPattern("H:mm");
/* 3200 */     int _jspx_eval_fmt_005fformatDate_005f5 = _jspx_th_fmt_005fformatDate_005f5.doStartTag();
/* 3201 */     if (_jspx_th_fmt_005fformatDate_005f5.doEndTag() == 5) {
/* 3202 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 3203 */       return true;
/*      */     }
/* 3205 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 3206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3211 */     PageContext pageContext = _jspx_page_context;
/* 3212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3214 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 3215 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 3216 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 3218 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${minVal}");
/* 3219 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 3220 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 3221 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 3222 */       return true;
/*      */     }
/* 3224 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 3225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3230 */     PageContext pageContext = _jspx_page_context;
/* 3231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3233 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 3234 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 3235 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3237 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${minVal}");
/* 3238 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 3239 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 3240 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 3241 */       return true;
/*      */     }
/* 3243 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 3244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3249 */     PageContext pageContext = _jspx_page_context;
/* 3250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3252 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 3253 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 3254 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 3256 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${maxVal}");
/* 3257 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 3258 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 3259 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 3260 */       return true;
/*      */     }
/* 3262 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 3263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f3(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3268 */     PageContext pageContext = _jspx_page_context;
/* 3269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3271 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 3272 */     _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 3273 */     _jspx_th_fmt_005fformatNumber_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 3275 */     _jspx_th_fmt_005fformatNumber_005f3.setValue("${maxVal}");
/* 3276 */     int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 3277 */     if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 3278 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 3279 */       return true;
/*      */     }
/* 3281 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 3282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3287 */     PageContext pageContext = _jspx_page_context;
/* 3288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3290 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 3291 */     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 3292 */     _jspx_th_fmt_005fformatNumber_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3294 */     _jspx_th_fmt_005fformatNumber_005f4.setValue("${avgVal}");
/*      */     
/* 3296 */     _jspx_th_fmt_005fformatNumber_005f4.setMinFractionDigits("2");
/*      */     
/* 3298 */     _jspx_th_fmt_005fformatNumber_005f4.setMaxFractionDigits("3");
/* 3299 */     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 3300 */     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 3301 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 3302 */       return true;
/*      */     }
/* 3304 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 3305 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fCompareHistoryData_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */