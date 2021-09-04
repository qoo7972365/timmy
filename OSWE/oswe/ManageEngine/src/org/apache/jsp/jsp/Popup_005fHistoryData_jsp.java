/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
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
/*      */ public final class Popup_005fHistoryData_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   36 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   42 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   43 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   44 */     _jspx_dependants.put("/jsp/includes/HistorydataPeriod.jspf", Long.valueOf(1473429417000L));
/*   45 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur;
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
/*   77 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  106 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  114 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  117 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  118 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  119 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  121 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  122 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  123 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  125 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/*  126 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  127 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  128 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*  129 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  130 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  131 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  132 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/*  133 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  140 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  143 */     JspWriter out = null;
/*  144 */     Object page = this;
/*  145 */     JspWriter _jspx_out = null;
/*  146 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  150 */       response.setContentType("text/html;charset=UTF-8");
/*  151 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  153 */       _jspx_page_context = pageContext;
/*  154 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  155 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  156 */       session = pageContext.getSession();
/*  157 */       out = pageContext.getOut();
/*  158 */       _jspx_out = out;
/*      */       
/*  160 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  161 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  162 */       out.write("\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/validation.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n\n\n\n\n");
/*  163 */       SummaryBean sumgraph = null;
/*  164 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  165 */       if (sumgraph == null) {
/*  166 */         sumgraph = new SummaryBean();
/*  167 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/*  169 */       out.write(10);
/*  170 */       out.write(10);
/*  171 */       HistoryDataGraphUtil graph = null;
/*  172 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/*  173 */       if (graph == null) {
/*  174 */         graph = new HistoryDataGraphUtil();
/*  175 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/*  177 */       out.write("\n\n\n<html>\n\n<head>\n<title>");
/*  178 */       out.print(FormatUtil.getString("am.webclient.historydata.text"));
/*  179 */       out.write("</title>\n");
/*      */       
/*  181 */       graph.setIsConfMonitor(request.getAttribute("isConfMonitor") != null ? (Boolean)request.getAttribute("isConfMonitor") : Boolean.valueOf(false));
/*  182 */       if (request.getAttribute("frompop") == null)
/*      */       {
/*      */ 
/*  185 */         out.write("\n\n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/*  186 */         out.print(FormatUtil.getString("am.webclient.historydata.text"));
/*  187 */         out.write(32);
/*  188 */         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.chooseresourcefor.text"));
/*  189 */         out.write(32);
/*  190 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/*  192 */         out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n<br>\n");
/*      */       }
/*      */       
/*      */ 
/*  196 */       out.write(10);
/*  197 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  198 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */         return;
/*  200 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  201 */       out.write(10);
/*  202 */       out.write("\n<script>\n\nvar customstarttime = escape(\"");
/*  203 */       out.print(request.getAttribute("startDate"));
/*  204 */       out.write("\");\nvar customendtime = escape(\"");
/*  205 */       out.print(request.getAttribute("endDate"));
/*  206 */       out.write("\");\nvar confStartTime=escape(\"");
/*  207 */       out.print(request.getAttribute("confStartTime") != null ? request.getAttribute("confStartTime") : "");
/*  208 */       out.write("\");\nvar confEndTime=escape(\"");
/*  209 */       out.print(request.getAttribute("confEndTime") != null ? request.getAttribute("confEndTime") : "");
/*  210 */       out.write("\");\nvar tab = ");
/*  211 */       out.print(request.getAttribute("tabsel"));
/*  212 */       out.write(" ;\n\n function fnCheckCustomTime(check)\n {\n if(check.startDate.value=='')\n {\n alert(\"");
/*  213 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  214 */       out.write("\")\n return false\n }\n else if(check.endDate.value=='')\n {\n alert(\"");
/*  215 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  216 */       out.write("\")\n return false\n }\n var s=check.startDate.value;\n var e=check.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/*  217 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  218 */       out.write("\" );\n     return false;\n    }\n    customstarttime=check.startDate.value;\n    customendtime=check.endDate.value;\n return true\n }\n  function fnSetEndTime(a)\n{\n\tdocument.forms[1].endDate.value=a;\n\tdocument.forms[1].customendtime.value=a;\n\n}\nfunction fnSetStartTime(a)\n{\n\n\tdocument.forms[1].startDate.value=a;\n\tdocument.forms[1].customstarttime.value=a;\n\n}\n\n// function fnPeriod(periodcombo)\n\n//{\n  //      document.forms[0].reporttype.value = \"html\"; // can only be html as first generate html and then save as pdf\n\t//document.forms[0].submit();\n//}\nvar perd = ");
/*  219 */       out.print(request.getAttribute("period"));
/*  220 */       out.write(";\nvar bHr = \"oni\"; //NO I18N\n\nfunction openEnabledReporsWindow() {\n\tvar resId = document.forms[0].resourceid.value;\n\tvar attrbId = document.forms[0].attributeid.value;;\t\n\tvar resType = '");
/*  221 */       out.print(request.getAttribute("servertype"));
/*  222 */       out.write("';\n\tvar url = \"/customReports.do?method=showCustomReports&popup=true&selectedType=\"+resType+\"&PRINTER_FRIENDLY=true\"; // NO I18N\n\turl = url + \"&resourceid=\"+resId+\"&attributeid=\"+attrbId; // NO I18N\n\tMM_openBrWindow(url,\"ExecutionTimeStatistic\",\"width=900,height=600,top=140,left=200,scrollbars=yes,resizable=yes\"); // NO I18N\n}\n\n function fnbHour(bHrSel)\n{\n\t \n\tvar m = document.forms[0].method.value;\n  \tvar r = document.forms[0].resourceid.value;\n  \tvar c = document.forms[0].childid.value;\n  \tvar n = document.forms[0].resourcename.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = perd;\n\tvar b = bHrSel.value;\n\t//alert(p);\n\tbHr = b;\n\tif(tab ==1)\n\t{\t\n\tif(p==20 && confStartTime!=\"\" && confEndTime!=\"\"){\n\t\turl=\"/showHistoryData.do?method=\"+m+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+b+\"&todaytime=\"+");
/*  223 */       out.print(System.currentTimeMillis());
/*  224 */       out.write("+\"&confEndTime=\"+unescape(confEndTime)+\"&confStartTime=\"+unescape(confStartTime); //NO I18N\n\n\t}\n\telse{\n\t\turl=\"/showHistoryData.do?method=\"+m+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+b+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  225 */       out.print(System.currentTimeMillis());
/*  226 */       out.write("; //NO I18N\n\t}\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getResp;\n    http.send(null);\n\n\t}\n\telse if (tab == 3)\n\t{\n\t\tgetHistSgmntByDayData();\n\t}\n\telse if (tab == 4)\n\t{\n\t\tgetStdDeviationData();\n\t}\n}\n\n function fnchangeAttrib(attribSel,type)\n{\n\tvar m = document.forms[0].method.value;\n  \tvar r = document.forms[0].resourceid.value;\n  \tvar c = document.forms[0].childid.value;\n  \tvar n = document.forms[0].resourcename.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = perd;\n\tvar b = attribSel.value;\n\n\t// start\n\t// type \"0\", from report enabled attributes list;\n\t// type \"1\", from all attributes list\n\t\n\tif (type == 0) {\n\t\t// get the attribute which is selected from the report enabled attributes list\n\t\t// and make that as selected in All-Attributes-List\n\t\tvar allList = document.forms[2].allPerformanceAttrbs;\n\t\tif (allList != null) {\n\t\t\tfor (var i=0;i<allList.length;i++) {\n\t\t\t\tvar val = allList.options[i].value;\n\t\t\t\tif (val == b) {\n\t\t\t\t\tallList[i].selected = true;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n");
/*  227 */       out.write("\t} else {\n\t\t// get the attribute which is selected from the All-Attributes-List\n\t\t// and make that as selected in Report-Enabled-Attributes-List\n\t\tvar seven = document.forms[2].sevenThirtyAttrib;\n\t\tif (seven != null) {\n\t\t\tfor (var i=0;i<seven.length;i++) {\n\t\t\t\tvar val = seven.options[i].value;\n\t\t\t\tif (val == b) {\n\t\t\t\t\tseven[i].selected = true;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\t// end\n\t\n\tvar sep=b.indexOf(\"#\");\n\n        r=b.substr(0,sep);\n        document.forms[0].resourceid.value = r;\n\tdocument.forms[1].resourceid.value = r;\n\n        a=b.substr(sep+1);\n\tdocument.forms[0].attributeid.value = a;\n\tdocument.forms[1].attributeid.value = a;\n\tif(tab ==1)\n\t{\n\t\tloadTabs(perd);\n\t\tvar http1=getHTTPObject();\n\t\tvar isChildMonitorAttribute=\"false\";\n\t\tvar restype=\"");
/*  228 */       out.print(com.adventnet.appmanager.reporting.ReportUtilities.getResourceType(request.getParameter("resourceid")));
/*  229 */       out.write("\";\t\n\t\tURL=\"/showHistoryData.do?method=ischildAttribute&resourceid=\"+r+\"&attributeid=\"+a;// No I18N\n\t\thttp1.open(\"GET\",URL,true);\n\t\thttp1.onreadystatechange=function(){\n\t\t\tif(http1.readyState == 4){\n\t\t\t\tisChildMonitorAttribute=http1.responseText;\t\t\t\n\t\t\t\tif(isChildMonitorAttribute==\"false\"){\n\t\t\t\t\n\t\t\t\tif(p==20 && confStartTime!=\"\" && confEndTime!=\"\"){\n\t\t\t\t\turl=\"/showHistoryData.do?method=\"+m+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&todaytime=\"+");
/*  230 */       out.print(System.currentTimeMillis());
/*  231 */       out.write("+\"&confEndTime=\"+unescape(confEndTime)+\"&confStartTime=\"+unescape(confStartTime); //NO I18N\n\t\t\t\t\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\turl=\"/showHistoryData.do?method=\"+m+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  232 */       out.print(System.currentTimeMillis());
/*  233 */       out.write("; //NO I18N\n\t\t\t\t}\t\t\t\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getResp;\n    http.send(null);\n\t\t\t\t}\n\t\t\t\telse if(isChildMonitorAttribute==\"true\"){\n\t\t\t\t\tdocument.forms[0].action=\"/showHistoryData.do?method=getChildGraph&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  234 */       out.print(System.currentTimeMillis());
/*  235 */       out.write("+\"&resType=\"+restype;\n\t\t\t\t\tdocument.forms[0].submit();\n\n\n\t\t\t\t}\n\n\t\t\t}\n\t\t}\n\t\thttp1.send(null);\n\n\n\n\n\t}\n\telse if(tab == 2)\n\t{\n\t\tgetHistSgmntByHrData()\n\t}\n\telse if (tab == 3)\n\t{\n\t\tgetHistSgmntByDayData();\n\t}\n\telse if (tab == 4)\n\t{\n\t\tgetStdDeviationData();\n\t}\n\telse if (tab == 5)\n\t{\n\t\tgetHeatData()\n\t}\n}\nfunction generateReport(type)\n{\n\t\n");
/*  236 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  238 */       out.write(10);
/*  239 */       out.write(10);
/*  240 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */         return;
/*  242 */       out.write("\n}\n\n\nfunction fnPeriod(periodform)\n {\n\tvar p = periodform.period.value;\n\t// start\n\t// if the period is not equal to 20 (means not \"show polled data\"), check whether the report is enabled.\n\t// show the error information if the report is not enabled.\n\tif (p != 20) {\n\t\tvar allAttrbsDiv = document.getElementById(\"attrib_div2\");\n\t\tif (allAttrbsDiv != null && typeof(allAttrbsDiv)!=\"undefined\" && allAttrbsDiv.style.display == \"block\") {\n\t\t\tvar allList = document.forms[2].allPerformanceAttrbs;\n\t\t\tif (allList != null) {\n\t\t\t\tvar exist = false;\n\t\t\t\tvar val1 =  allList.options[allList.selectedIndex].value;\n\t\t\t\tvar seventhirty = document.forms[2].sevenThirtyAttrib;\n\t\t\t\tif (seventhirty != null) {\n\t\t\t\t\tfor (var i=0;i<seventhirty.length;i++) {\n\t\t\t\t\t\tvar val2 = seventhirty.options[i].value;\n\t\t\t\t\t\tif (val1 == val2) {\n\t\t\t\t\t\t\texist = true;\n\t\t\t\t\t\t\tbreak;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif (exist == false) {\n\t\t\t\t\t// here means, the report is not enabled for the selected attribute.\n\t\t\t\t\tperiodform.period.options.selectedIndex=periodform.period.options.length-1;\n");
/*  243 */       out.write("\t\t\t\t\t//document.getElementById(\"regular\").style.display=\"none\"; // hide the data div\n\t\t\t\t\tdocument.getElementById(\"showOneTab\").style.display='none'; // hide the history tab.\n\t\t\t\t\tdocument.getElementById(\"repo_not_enab_info_div\").style.display=\"block\";// display information.\n\t\t\t\t\treturn false;\n\t\t\t\t}\t\t\t\n\t\t\t}\n\t\t}\n\t} else {\n\t\t// hide the error infor div.\n\t\tdocument.getElementById(\"repo_not_enab_info_div\").style.display=\"none\";\n\t\tdocument.getElementById(\"showOneTab\").style.display='none'; // hide the history tab.\n\t}\n\t// - end\n\t\n\tperd = p;\n\t //alert(\"the form is==>\"+periodform.name);\n\tif(p==4) //period  4  is meant for the custom period.\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='block';\n\t\tdocument.getElementById(\"attrib_div1\").style.display='block';\n\t\tdocument.getElementById(\"attrib_div2\").style.display='none';\n\t\talert(\"");
/*  244 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforcustomtime"));
/*  245 */       out.write("\")\n \t\treturn false\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='none';\n\t\tif (p == 20) {\n\t\t\t// show all attributes if period is 20 (means show polled data);hide report report enabled attributes list\n\t\t\tdocument.getElementById(\"attrib_div1\").style.display='none';\n\t\t\tdocument.getElementById(\"attrib_div2\").style.display='block';\n\t\t} else {\n\t\t\t// For other period, only show the report enabled attributes list\n\t\t\tdocument.getElementById(\"attrib_div1\").style.display='block';\n\t\t\tdocument.getElementById(\"attrib_div2\").style.display='none';\n\t\t}\n\t}\n// \tif(p==20)\n//   \t         {\n//   \t                 document.getElementById(\"pdfdiv\").style.display='none';\n//   \t         }\n \tif(tab == 1)\n\t{\n  \t\t//alert(\"Haiiiiiiiiiiiiiiiiii\");\n  \t\tvar m = periodform.method.value;\n\t\t//alert(m);\n\t\t//alert(bHr_val+\"888888888888\"+p);\n  \t\tvar r = periodform.resourceid.value;\n  \t\tvar c = periodform.childid.value;\n  \t\tvar n = periodform.resourcename.value;\n  \t\tvar a = periodform.attributeid.value;\n\t\t//alert(m);\n\t\tloadTabs(perd);\n");
/*  246 */       out.write("\t\turl=\"/showHistoryData.do?method=\"+m+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&todaytime=\"+");
/*  247 */       out.print(System.currentTimeMillis());
/*  248 */       out.write("; //NO I18N\n\t\tif(p==20 && confStartTime!=\"\" && confEndTime!=\"\"){\n\t\t\turl=url+\"&confEndTime=\"+unescape(confEndTime)+\"&confStartTime=\"+unescape(confStartTime); //NO I18N\n\t\t\n\t\t}\t\t\n    \t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getResp;\n    \t\thttp.send(null);\n      \t}\n\telse if (tab == 2)\n\t{\n\t\tgetHistSgmntByHrData();\n\t}\n\telse if (tab == 3)\n\t{\n\t\tgetHistSgmntByDayData();\n\t}\n\telse if (tab == 4)\n\t{\n\t\tgetStdDeviationData();\n\t}\n\n  }\n\n  function loadTabs(Period)\n  {\n\t\tif(Period==\"20\")\n\t\t{\n    \t\tdocument.getElementById(\"showAllTabs\").style.display='none';\n    \t\tdocument.getElementById(\"showOneTab\").style.display='none';\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"showAllTabs\").style.display='block';\n    \t\tdocument.getElementById(\"showOneTab\").style.display='none';\n\t\t}\n\t\tif (document.getElementById(\"repo_not_enab_info_div\") != null) {\n\t\t\tdocument.getElementById(\"repo_not_enab_info_div\").style.display=\"none\";\n\t\t}\n  }\n\n   function getResp()\n  {\n \tif(http.readyState == 4)\n \t{\n   \t\tvar result = http.responseText;\n");
/*  249 */       out.write("    \t\tdocument.getElementById(\"regular\").innerHTML=result;\n    \t\tdocument.getElementById(\"regular\").style.display='block';\n    \t\tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n    \t\tdocument.getElementById(\"segmentByDay\").style.display=\"none\";\n\t\tdocument.getElementById(\"heat\").style.display=\"none\";\n    \t\tdocument.getElementById(\"stdDevn\").style.display=\"none\";\n\t\tdocument.getElementById(\"prddiv\").style.display=\"block\";\n\t\tdocument.getElementById(\"bhrdiv\").style.display=\"block\";\n\n\t\tvar p=document.ReportForm[0].period.value;\n  \t                         //alert(p);\n//   \t \tif(p==20)\n//   \t  \t{\n//   \t         \tdocument.getElementById(\"pdfdiv\").style.display='none';\n//   \t  \t}\n//   \t \telse\n//   \t  \t{\n// \t\t\tdocument.getElementById(\"pdfdiv\").style.display=\"block\";\n// \t  \t}\n    \t}\n  }\n\n\nfunction getRegularHistData()\n{\n\n\t//var len=document.getElementById(\"period\").options.length; //not working in firefox\n\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n");
/*  250 */       out.write("\t//alert(\"value==>\"+val);\n\tvar adminserver=");
/*  251 */       out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  252 */       out.write("\n\t//alert(adminserver);\n\t\tif(val!=\"20\" && !adminserver)\n\t\t{\n\t\t//alert(len);\n\t\tvar list=document.ReportForm[0].period;\n\t\tvar newOption=document.createElement('option');\n\t\tnewOption.text='");
/*  253 */       out.print(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*  254 */       out.write("';\n        newOption.value=\"20\";\n        list.add(newOption,len.value);\n\t    }\n\tvar r = document.forms[0].resourceid.value;\n\t//alert(\"RESID=\"+r);\n  \tvar c = ");
/*  255 */       out.print(request.getParameter("childid"));
/*  256 */       out.write(";\n\t//alert(\"CHILDID=\"+c);\n  \tvar n = '");
/*  257 */       out.print(request.getParameter("resourcename"));
/*  258 */       out.write("';\n\t//alert(\"RNAME=\"+n);\n  \tvar a = document.forms[0].attributeid.value;\n\t//alert(\"AID=\"+a);\n  \ttab = 1;\n\t//url=\"/showHistoryData.do?method=getData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=1&todaytime=\"+");
/*  259 */       out.print(System.currentTimeMillis());
/*  260 */       out.write(";\n\turl=\"/showHistoryData.do?method=getData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=1&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  261 */       out.print(System.currentTimeMillis());
/*  262 */       out.write("; //NO I18N\n\ttry{\n     \t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getRegularDataResp;\n    \t\thttp.send(null);\n\t}\n\tcatch(e){}\n}\n\nfunction getRegularDataResp()\n{\n\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\t//alert (result);\n\n\tdocument.getElementById(\"regular\").innerHTML=result;\n    \tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByDay\").style.display=\"none\";\n    \tdocument.getElementById(\"stdDevn\").style.display=\"none\";\n\tdocument.getElementById(\"heat\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\tdocument.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"block\";\n \tdocument.getElementById(\"pdfdiv\").style.display=\"block\";\n\tdocument.getElementById(\"regular\").style.display=\"block\";\n\n    }\n }\nfunction getHistSgmntByHrData()\n{\n\t//var len=document.getElementById(\"period\").options.length;\n\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n");
/*  263 */       out.write("\t//alert(\"value==>\"+val);\n\t\tif(val==\"20\")\n\t\t{\n\t\t\t//alert(len);\n\t\tvar list=document.ReportForm[0].period;\n\t\tlist.remove(len-1);\n     \t}\n  \tvar r = document.forms[0].resourceid.value;\n\t//alert(\"RESID=\"+r);\n  \tvar c = ");
/*  264 */       out.print(request.getParameter("childid"));
/*  265 */       out.write(";\n\t//alert(c);\n  \tvar n = '");
/*  266 */       out.print(request.getParameter("resourcename"));
/*  267 */       out.write("';\n\t//alert(n);\n  \tvar a = document.forms[0].attributeid.value;\n\t//alert(\"AID=\"+a);\n  \ttab = 2;\n\turl=\"/showHistoryData.do?method=getsegmentByHourData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=2&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  268 */       out.print(System.currentTimeMillis());
/*  269 */       out.write("; //NO I18N\n\ttry{\n      http.open(\"GET\",url,true);\n    http.onreadystatechange = getSgmntByHrDataResp;\n    http.send(null);\n\t}\n\tcatch (e) {}\n}\nfunction getSgmntByHrDataResp()\n{\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n   //\talert(result);\n    \tdocument.getElementById(\"segmentByHour\").innerHTML=result;\n    \tdocument.getElementById(\"regular\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByDay\").style.display=\"none\";\n    \tdocument.getElementById(\"stdDevn\").style.display=\"none\";\n\tdocument.getElementById(\"heat\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n \tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"segmentByHour\").style.display=\"block\";\n    }\n }\n\nfunction getHistSgmntByDayData()\n{\n\t//var len=document.getElementById(\"period\").options.length;\n\tvar len=document.ReportForm[0].period.length;\n");
/*  270 */       out.write("\tvar val=document.ReportForm[0].period.options[len-1].value;\n\t//alert(\"value==>\"+val);\n\t\tif(val==\"20\")\n\t\t{\n\t\t\t//alert(len);\n\t\tvar list=document.ReportForm[0].period;\n\t\tlist.remove(len-1);\n     \t}\n\tvar r = document.forms[0].resourceid.value;\n\t//alert(\"resouceid=\"+r);\n  \tvar c = ");
/*  271 */       out.print(request.getParameter("childid"));
/*  272 */       out.write(";\n\t//alert(\"childid=\"+c);\n  \tvar n = '");
/*  273 */       out.print(request.getParameter("resourcename"));
/*  274 */       out.write("';\n\t//alert(\"RNAME=\"+n);\n  \tvar a = document.forms[0].attributeid.value;\n\t//alert(\"ATTID=\"+a);\n   \t//perd = p;\n\t//alert(\"PERIOD=\"+perd);\n\ttab = 3;\n\turl=\"/showHistoryData.do?method=getsegmentByWeekData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=3&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  275 */       out.print(System.currentTimeMillis());
/*  276 */       out.write("; //NO I18N\n\ttry{\n      \t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getSgmntByWkDataResp;\n    \t\thttp.send(null);\n\t}\n\tcatch (e) {}\n}\n\nfunction getSgmntByWkDataResp()\n{\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\n    \tdocument.getElementById(\"segmentByDay\").innerHTML=result;\n    \tdocument.getElementById(\"regular\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n    \tdocument.getElementById(\"stdDevn\").style.display=\"none\";\n\tdocument.getElementById(\"heat\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n \tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"block\";\n\tdocument.getElementById(\"segmentByDay\").style.display=\"block\";\n    }\n }\nfunction getStdDeviationData()\n{\n\t//var len=document.getElementById(\"period\").options.length;\n\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n");
/*  277 */       out.write("\t//alert(\"value==>\"+val);\n\t\tif(val==\"20\")\n\t\t{\n\t\t\t//alert(len);\n\t\tvar list=document.ReportForm[0].period;\n\t\tlist.remove(len-1);\n     \t}\n\tvar r = document.forms[0].resourceid.value;\n  \tvar c = '");
/*  278 */       out.print(request.getParameter("childid"));
/*  279 */       out.write("';\n  \tvar n = '");
/*  280 */       out.print(request.getParameter("resourcename"));
/*  281 */       out.write("';\n  \tvar a = document.forms[0].attributeid.value;\n   \t//perd = p;\n\ttab = 4;\n\turl=\"/showHistoryData.do?method=getStandardDeviationData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=4&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  282 */       out.print(System.currentTimeMillis());
/*  283 */       out.write("; //NO I18N\n\ttry{\n      \t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getStdDevnResp;\n    \t\thttp.send(null);\n\t}\n\tcatch (e) {}\n\n}\nfunction getStdDevnResp()\n{\nif(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n   \t//alert(result);\n    \tdocument.getElementById(\"stdDevn\").innerHTML=result;\n    \tdocument.getElementById(\"regular\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n\tdocument.getElementById(\"heat\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByDay\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n \tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"block\";\n\tdocument.getElementById(\"stdDevn\").style.display=\"block\";\n    }\n\n}\nfunction getHeatData()\n{\n\tvar r =  document.forms[0].resourceid.value;\n  \tvar c = '");
/*  284 */       out.print(request.getParameter("childid"));
/*  285 */       out.write("';\n  \tvar n = '");
/*  286 */       out.print(request.getParameter("resourcename"));
/*  287 */       out.write("';\n  \tvar a = document.forms[0].attributeid.value;\n\ttab=5;\n\turl=\"/showHistoryData.do?method=getheatData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=1&frompop=true&tabsel=5&userThresh=0&todaytime=\"+");
/*  288 */       out.print(System.currentTimeMillis());
/*  289 */       out.write("; //NO I18N\n\ttry{\n      http.open(\"GET\",url,true);\n    http.onreadystatechange = getHeatResp;\n    http.send(null);\n\t}\n\tcatch (e) {}\n\n}\nfunction getHeatResp()\n{\nif(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n   \t//alert(result);\n    \tdocument.getElementById(\"heat\").innerHTML=result;\n    \tdocument.getElementById(\"regular\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n\tdocument.getElementById(\"stdDevn\").style.display=\"none\";\n    \tdocument.getElementById(\"segmentByDay\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n \tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"prddiv\").style.display=\"none\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\tdocument.getElementById(\"heat\").style.display=\"block\";\n    }\n\n}\nvar userThresh=0;\nfunction fnPer(periodcombo)\n\t{\n\tvar r = document.forms[0].resourceid.value;\n  \tvar c = '");
/*  290 */       out.print(request.getParameter("childid"));
/*  291 */       out.write("';\n  \tvar n = '");
/*  292 */       out.print(request.getParameter("resourcename"));
/*  293 */       out.write("';\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = periodcombo.value;\n\tif(userThresh != 1)\n\t{\n\turl=\"/showHistoryData.do?method=getheatData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&tabsel=5&userThresh=0&todaytime=\"+");
/*  294 */       out.print(System.currentTimeMillis());
/*  295 */       out.write("; //NO I18N\n\t}\n\telse\n\t{\n\turl=\"/showHistoryData.do?method=getheatData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&tabsel=5&userThresh=\"+userThresh+\"&isThresh=\"+isThreshconf+\"&threshC1=\"+threshC1+\"&threshV1=\"+threshV1+\"&threshC2=\"+threshC2+\"&threshV2=\"+threshV2+\"&threshC3=\"+threshC3+\"&threshV3=\"+threshV3+\"&todaytime=\"+");
/*  296 */       out.print(System.currentTimeMillis());
/*  297 */       out.write("; //NO I18N\n\t}\n\ttry{\n      http.open(\"GET\",url,true);\n    http.onreadystatechange = getHeatResp;\n    http.send(null);\n\t}\n\tcatch (e) {}\n\t}\n\n\tvar isThreshconf;\n\tvar threshC1;\n\tvar threshV1;\n\tvar threshC2;\n\tvar threshV2;\n\tvar threshC3;\n\tvar threshV3;\nfunction updateHeatThresh(p,isThresh)\n\t{\n\n\t\t if((trimAll(document.heatthreshForm.threshvalue1.value)==\"\") || (isPositiveInteger(document.heatthreshForm.threshvalue1.value)==false))\n                  {\n        \t\talert(\"");
/*  298 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  299 */       out.write("\");         return false;\n                  }\n\t\t else if((trimAll(document.heatthreshForm.threshvalue2.value)==\"\") || (isPositiveInteger(document.heatthreshForm.threshvalue2.value)==false))\n\t\t {\n\t\t\talert(\"");
/*  300 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  301 */       out.write("\");\n\t\t }\n\t\t else if((trimAll(document.heatthreshForm.threshvalue3.value)==\"\") || (isPositiveInteger(document.heatthreshForm.threshvalue3.value)==false))\n\t\t{\n\t\t\talert(\"");
/*  302 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  303 */       out.write("\");\n\n\t\t}\n\t\telse\n\t\t{\n\t\tuserThresh=1;\n\t\tvar r = '");
/*  304 */       out.print(request.getParameter("resourceid"));
/*  305 */       out.write("';\n  \t\tvar c = '");
/*  306 */       out.print(request.getParameter("childid"));
/*  307 */       out.write("';\n  \t\tvar n = '");
/*  308 */       out.print(request.getParameter("resourcename"));
/*  309 */       out.write("';\n  \t\tvar a = '");
/*  310 */       out.print(request.getParameter("attributeid"));
/*  311 */       out.write("';\n\t\tisThreshconf = isThresh;\n\t\tthreshC1 = document.heatthreshForm.threshcondn1.value;\n\t\tthreshV1 = document.heatthreshForm.threshvalue1.value;\n\t\tthreshC2 = document.heatthreshForm.threshcondn2.value;\n\t\tthreshV2 = document.heatthreshForm.threshvalue2.value;\n\t\tthreshC3 = document.heatthreshForm.threshcondn3.value;\n\t\tthreshV3 = document.heatthreshForm.threshvalue3.value;\n\n\t\turl=\"/showHistoryData.do?method=getheatData&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&tabsel=5&userThresh=\"+userThresh+\"&isThresh=\"+isThresh+\"&threshC1=\"+threshC1+\"&threshV1=\"+threshV1+\"&threshC2=\"+threshC2+\"&threshV2=\"+threshV2+\"&threshC3=\"+threshC3+\"&threshV3=\"+threshV3+\"&todaytime=\"+");
/*  312 */       out.print(System.currentTimeMillis());
/*  313 */       out.write("; //NO I18N\n\n\t\ttry{\n      \t\t\thttp.open(\"GET\",url,true);\n    \t\t\thttp.onreadystatechange = getHeatResp;\n    \t\t\thttp.send(null);\n\t\t}\n\t\tcatch (e) {}\n\t\t}\n\t}\nonload = function() {\nvar p= ");
/*  314 */       out.print(request.getParameter("period"));
/*  315 */       out.write("\nif(p == 4)\n{\ndocument.getElementById(\"custPeriod\").style.display='block';\ndocument.getElementById(\"attrib_div1\").style.display='block';// custome period only for history. so keep report enabled attributes list\ndocument.getElementById(\"attrib_div2\").style.display='none';\n}\nelse\n{\n\tif (p == 20) {\n\t\t// show all attributes if period is 20 (means show polled data);hide report report enabled attributes list\n\t\tdocument.getElementById(\"attrib_div1\").style.display='none';\n\t\tdocument.getElementById(\"attrib_div2\").style.display='block';\n\t} else {\n\t\t// For other period, only show the report enabled attributes list\n\t\tdocument.getElementById(\"attrib_div1\").style.display='block';\n\t\tdocument.getElementById(\"attrib_div2\").style.display='none';\n\t}\n}\ntry{\nloadTabs(p);//No I18N\n}\ncatch(e)\n{}\n}\n\n</script>\n\n\n</head>\n\n<body>\n\n\n");
/*      */       
/*  317 */       String resourceName = (String)pageContext.getAttribute("rname");
/*  318 */       String peri = request.getParameter("period");
/*      */       
/*  320 */       if (request.getAttribute("frompop") == null)
/*      */       {
/*      */ 
/*  323 */         out.write("\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tblbordergray-report\" align=\"center\">\n\n  <tr>\n\n    <td  align=\"right\" valign='top'  width=\"30%\" style=\"padding-left:5px;\" >\n\t<div id='prddiv' style=\"Display:block\">\n\t      ");
/*  324 */         out.write("<!-- $Id : $ -->\n\n");
/*      */         
/*  326 */         Properties pq = com.adventnet.appmanager.util.DBUtil.getRawValue();
/*  327 */         String RV = pq.getProperty("israw");
/*  328 */         String PV = pq.getProperty("rawvalue");
/*  329 */         String AID = request.getParameter("attributeid");
/*  330 */         String pageChk = request.getParameter("method");
/*      */         
/*  332 */         Boolean role = Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*      */         
/*      */ 
/*  335 */         out.write("\n\n<table width=\"100%\" border=\"0\" align=\"left\"  valign=\"top\" cellpadding=\"0\" cellspacing=\"0\"  >\n  <tr>\n    <td style=\"padding-left:5px;\">\n");
/*      */         
/*  337 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  338 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  339 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  341 */         _jspx_th_html_005fform_005f0.setAction("/showHistoryData.do");
/*      */         
/*  343 */         _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/*  344 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  345 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  347 */             out.write("\n    ");
/*      */             
/*  349 */             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  350 */             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  351 */             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  353 */             _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */             
/*  355 */             _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this.form)");
/*      */             
/*  357 */             _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/*  358 */             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  359 */             if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  360 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  361 */                 out = _jspx_page_context.pushBody();
/*  362 */                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  363 */                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  366 */                 out.write("\n    ");
/*  367 */                 if ((AID != null) && ((AID.equals("1357")) || (AID.equals("1377")) || (AID.equals("1457")) || (AID.equals("1473")) || (AID.equals("708")) || (AID.equals("1394")) || (AID.equals("1107")) || (AID.equals("1207")) || (AID.equals("1307")) || (AID.equals("1657")) || (AID.equals("807")) || (AID.equals("1352")) || (AID.equals("1372")) || (AID.equals("1452")) || (AID.equals("1472")) || (AID.equals("702")) || (AID.equals("1392")) || (AID.equals("1102")) || (AID.equals("1202")) || (AID.equals("803")) || (AID.equals("1302")) || (AID.equals("1652"))))
/*      */                 {
/*  369 */                   if ((RV != null) && (RV.equals("true"))) {
/*  370 */                     out.write("\n    ");
/*      */                     
/*  372 */                     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  373 */                     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  374 */                     _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                     
/*  376 */                     _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*      */                     
/*  378 */                     _jspx_th_html_005foption_005f0.setValue("14");
/*  379 */                     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  380 */                     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  381 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                     }
/*      */                     
/*  384 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*      */                   } }
/*  386 */                 out.write("\n    ");
/*      */                 
/*  388 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  389 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  390 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  392 */                 _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                 
/*  394 */                 _jspx_th_html_005foption_005f1.setValue("4");
/*  395 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  396 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  397 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/*  400 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  401 */                 out.write("\t\n    ");
/*      */                 
/*  403 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  404 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  405 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  407 */                 _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                 
/*  409 */                 _jspx_th_html_005foption_005f2.setValue("0");
/*  410 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  411 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  412 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/*  415 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  416 */                 out.write(32);
/*  417 */                 out.write(10);
/*  418 */                 out.write(9);
/*      */                 
/*  420 */                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  421 */                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  422 */                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  424 */                 _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                 
/*  426 */                 _jspx_th_html_005foption_005f3.setValue("3");
/*  427 */                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  428 */                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  429 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                 }
/*      */                 
/*  432 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  433 */                 out.write("\n    ");
/*      */                 
/*  435 */                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  436 */                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  437 */                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  439 */                 _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                 
/*  441 */                 _jspx_th_html_005foption_005f4.setValue("6");
/*  442 */                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  443 */                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  444 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                 }
/*      */                 
/*  447 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  448 */                 out.write(10);
/*  449 */                 out.write(9);
/*      */                 
/*  451 */                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  452 */                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  453 */                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  455 */                 _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                 
/*  457 */                 _jspx_th_html_005foption_005f5.setValue("-7");
/*  458 */                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  459 */                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  460 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                 }
/*      */                 
/*  463 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  464 */                 out.write(32);
/*  465 */                 out.write(10);
/*  466 */                 out.write(9);
/*      */                 
/*  468 */                 OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  469 */                 _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  470 */                 _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  472 */                 _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                 
/*  474 */                 _jspx_th_html_005foption_005f6.setValue("12");
/*  475 */                 int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  476 */                 if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  477 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                 }
/*      */                 
/*  480 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  481 */                 out.write("\n    ");
/*      */                 
/*  483 */                 OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  484 */                 _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  485 */                 _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  487 */                 _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                 
/*  489 */                 _jspx_th_html_005foption_005f7.setValue("7");
/*  490 */                 int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  491 */                 if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  492 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                 }
/*      */                 
/*  495 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  496 */                 out.write(10);
/*  497 */                 out.write(9);
/*      */                 
/*  499 */                 OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  500 */                 _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  501 */                 _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  503 */                 _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                 
/*  505 */                 _jspx_th_html_005foption_005f8.setValue("-30");
/*  506 */                 int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  507 */                 if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  508 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                 }
/*      */                 
/*  511 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  512 */                 out.write(32);
/*  513 */                 out.write(10);
/*  514 */                 out.write(9);
/*      */                 
/*  516 */                 OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  517 */                 _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  518 */                 _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  520 */                 _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                 
/*  522 */                 _jspx_th_html_005foption_005f9.setValue("11");
/*  523 */                 int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  524 */                 if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  525 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                 }
/*      */                 
/*  528 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  529 */                 out.write(10);
/*  530 */                 out.write(9);
/*      */                 
/*  532 */                 OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  533 */                 _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  534 */                 _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  536 */                 _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                 
/*  538 */                 _jspx_th_html_005foption_005f10.setValue("9");
/*  539 */                 int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  540 */                 if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  541 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                 }
/*      */                 
/*  544 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*  545 */                 out.write("\n    ");
/*      */                 
/*  547 */                 OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  548 */                 _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/*  549 */                 _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  551 */                 _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                 
/*  553 */                 _jspx_th_html_005foption_005f11.setValue("8");
/*  554 */                 int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/*  555 */                 if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/*  556 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                 }
/*      */                 
/*  559 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/*  560 */                 out.write(10);
/*  561 */                 out.write(9);
/*      */                 
/*  563 */                 OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  564 */                 _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/*  565 */                 _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  567 */                 _jspx_th_html_005foption_005f12.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                 
/*  569 */                 _jspx_th_html_005foption_005f12.setValue("-5");
/*  570 */                 int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/*  571 */                 if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/*  572 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                 }
/*      */                 
/*  575 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12);
/*  576 */                 out.write(10);
/*  577 */                 out.write(9);
/*  578 */                 if (pageChk.equals("getData"))
/*      */                 {
/*      */ 
/*  581 */                   out.write(10);
/*  582 */                   out.write(9);
/*      */                   
/*  584 */                   OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  585 */                   _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/*  586 */                   _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                   
/*  588 */                   _jspx_th_html_005foption_005f13.setKey(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*      */                   
/*  590 */                   _jspx_th_html_005foption_005f13.setValue("20");
/*  591 */                   int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/*  592 */                   if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/*  593 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                   }
/*      */                   
/*  596 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13);
/*  597 */                   out.write(10);
/*  598 */                   out.write(9);
/*      */                 }
/*  600 */                 out.write(10);
/*  601 */                 out.write(32);
/*  602 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  603 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  606 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  607 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  610 */             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  611 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */             }
/*      */             
/*  614 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  615 */             out.write("\n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  616 */             out.print(request.getParameter("resourceid"));
/*  617 */             out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  618 */             out.print(request.getParameter("childid"));
/*  619 */             out.write("'>\n  \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  620 */             out.print(request.getParameter("resourcename"));
/*  621 */             out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  622 */             out.print(request.getParameter("attributeid"));
/*  623 */             out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  624 */             out.print(request.getParameter("method"));
/*  625 */             out.write(39);
/*  626 */             out.write(62);
/*  627 */             out.write(32);
/*  628 */             if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  630 */             out.write(10);
/*  631 */             out.write(9);
/*  632 */             if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  634 */             out.write(10);
/*  635 */             out.write(9);
/*  636 */             if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  638 */             out.write(10);
/*  639 */             out.write(9);
/*  640 */             if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  642 */             out.write(10);
/*  643 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  644 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  648 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  649 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */         }
/*      */         
/*  652 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  653 */         out.write("\n    </td>\n \t");
/*  654 */         if (pageChk.equals("getIndividualURLandCompareReportsData"))
/*      */         {
/*      */ 
/*  657 */           out.write("\n\t\t<td >\n");
/*      */           
/*  659 */           FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  660 */           _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/*  661 */           _jspx_th_html_005fform_005f1.setParent(null);
/*      */           
/*  663 */           _jspx_th_html_005fform_005f1.setAction("/showHistoryData.do?period=4");
/*  664 */           int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/*  665 */           if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */             for (;;) {
/*  667 */               out.write("\n\t<div id='custPeriod' style=\"Display:none\">\n\n      <table width=\"98%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n   \n  <td height=\"24\" class=\"bodytext\" >");
/*  668 */               out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  669 */               out.write(" \n</td>\n \n          <td height=\"40\"   > ");
/*  670 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  672 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  673 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  674 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                         Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                        });\n                           </SCRIPT></td>\n \n          <td  class=\"bodytext\"  >");
/*  675 */               out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  676 */               out.write("</td>\n  \n          <td height=\"39\"  colspan=2  > ");
/*  677 */               if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  679 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  680 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  681 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                       Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                       });\n             </SCRIPT> </td>\n  \n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  682 */               out.print(request.getParameter("resourceid"));
/*  683 */               out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  684 */               out.print(request.getParameter("childid"));
/*  685 */               out.write("'>\n \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  686 */               out.print(request.getParameter("resourcename"));
/*  687 */               out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  688 */               out.print(request.getParameter("attributeid"));
/*  689 */               out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  690 */               out.print(request.getParameter("method"));
/*  691 */               out.write("'>\n  <input type=\"hidden\" name=\"customstarttime\" >\n  <input type=\"hidden\" name=\"customendtime\" >\n  ");
/*  692 */               if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  694 */               out.write("\n    <td height=\"32\"  >\n      <input type=\"submit\" name=\"show\" value=\"");
/*  695 */               out.print(FormatUtil.getString("Go"));
/*  696 */               out.write("\" class=\"buttons\" onclick=\"return fnCheckCustomTime(this.form)\">\n    </td>\n\t\n\t\n  </tr>\n</table>\n</div>\n\t\n</td>\n");
/*  697 */               if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  699 */               out.write(10);
/*  700 */               out.write(9);
/*  701 */               int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/*  702 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  706 */           if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/*  707 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */           }
/*      */           
/*  710 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1);
/*  711 */           out.write(10);
/*  712 */           out.write(9);
/*      */         }
/*  714 */         out.write("\n</tr>\n");
/*  715 */         if (!pageChk.equals("getIndividualURLandCompareReportsData"))
/*      */         {
/*      */ 
/*  718 */           out.write("\n<tr>\n<td >\n");
/*      */           
/*  720 */           FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  721 */           _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/*  722 */           _jspx_th_html_005fform_005f2.setParent(null);
/*      */           
/*  724 */           _jspx_th_html_005fform_005f2.setAction("/showHistoryData.do?period=4");
/*  725 */           int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/*  726 */           if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */             for (;;) {
/*  728 */               out.write("\n\t<div id='custPeriod' style=\"Display:none\">\n\n      <table width=\"98%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n   \n  <td height=\"24\" class=\"bodytext\" >");
/*  729 */               out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  730 */               out.write(" \n</td>\n \n          <td height=\"40\"   > ");
/*  731 */               if (_jspx_meth_c_005fchoose_005f2(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  733 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  734 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  735 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                         Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                        });\n                           </SCRIPT></td>\n \n          <td  class=\"bodytext\"  >");
/*  736 */               out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  737 */               out.write("</td>\n  \n          <td height=\"39\"  colspan=2  > ");
/*  738 */               if (_jspx_meth_c_005fchoose_005f3(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  740 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  741 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  742 */               out.write("\"></a>\n            <SCRIPT type=text/javascript>\n                       Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                        showsTime      :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                        timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                       });\n             </SCRIPT> </td>\n  \n  <input type=\"hidden\" name=\"resourceid\" value='");
/*  743 */               out.print(request.getParameter("resourceid"));
/*  744 */               out.write("'>\n  <input type=\"hidden\" name=\"childid\" value='");
/*  745 */               out.print(request.getParameter("childid"));
/*  746 */               out.write("'>\n \n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  747 */               out.print(request.getParameter("resourcename"));
/*  748 */               out.write("'>\n  <input type=\"hidden\" name=\"attributeid\" value='");
/*  749 */               out.print(request.getParameter("attributeid"));
/*  750 */               out.write("'>\n  <input type=\"hidden\" name=\"method\" value='");
/*  751 */               out.print(request.getParameter("method"));
/*  752 */               out.write("'>\n  <input type=\"hidden\" name=\"customstarttime\" >\n  <input type=\"hidden\" name=\"customendtime\" >\n  ");
/*  753 */               if (_jspx_meth_html_005fhidden_005f6(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  755 */               out.write("\n    <td height=\"32\"  >\n      <input type=\"submit\" name=\"show\" value=\"");
/*  756 */               out.print(FormatUtil.getString("Go"));
/*  757 */               out.write("\" class=\"buttons\" onclick=\"return fnCheckCustomTime(this.form)\">\n\t\n    </td>\n  </tr>\n    \n</table>\n\t</div>\n\t");
/*  758 */               if (_jspx_meth_html_005fhidden_005f7(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                 return;
/*  760 */               out.write(10);
/*  761 */               out.write(9);
/*  762 */               int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/*  763 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  767 */           if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/*  768 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */           }
/*      */           
/*  771 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f2);
/*  772 */           out.write(10);
/*  773 */           out.write(9);
/*      */         }
/*  775 */         out.write("\n</td>\n</tr>\n\n\n \n</table>\n\n\n\n");
/*  776 */         out.write("\n\t</div>\n    </td>\n\n\n");
/*      */         
/*  778 */         FormTag _jspx_th_html_005fform_005f3 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  779 */         _jspx_th_html_005fform_005f3.setPageContext(_jspx_page_context);
/*  780 */         _jspx_th_html_005fform_005f3.setParent(null);
/*      */         
/*  782 */         _jspx_th_html_005fform_005f3.setAction("/showHistoryData.do");
/*      */         
/*  784 */         _jspx_th_html_005fform_005f3.setStyle("Display:inline");
/*  785 */         int _jspx_eval_html_005fform_005f3 = _jspx_th_html_005fform_005f3.doStartTag();
/*  786 */         if (_jspx_eval_html_005fform_005f3 != 0) {
/*      */           for (;;) {
/*  788 */             out.write("\n    \t<td align=\"left\" width=\"46%\" valign=\"top\">\n\t<div id='attrib_div1'>\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t\t<tr width=\"45%\">\n\t\t<td class=\"bodytext\"  align=\"right\">\n\n\t\t");
/*  789 */             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/*  790 */             out.write(": &nbsp;\n\t\t</td>\n\t\t<td class=\"bodytext\"  align=\"left\" valign=\"top\" width=\"60%\">\n\t\t");
/*  791 */             if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f3, _jspx_page_context))
/*      */               return;
/*  793 */             out.write("\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t</div>\n\t<div id='attrib_div2'>\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t\t<tr width=\"45%\">\n\t\t<td class=\"bodytext\"  align=\"right\">\n\n\t\t");
/*  794 */             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/*  795 */             out.write(": &nbsp;\n\t\t</td>\n\t\t<td class=\"bodytext\"  align=\"left\" valign=\"top\" width=\"60%\">\n\t\t");
/*  796 */             if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f3, _jspx_page_context))
/*      */               return;
/*  798 */             out.write("\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t</div>\n\t</td>\n    \t<td width=\"18%\" align=\"right\" valign=\"top\">\n\t\t<div id='bhrdiv'>\n\t\t");
/*  799 */             if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f3, _jspx_page_context))
/*      */               return;
/*  801 */             out.write("\n\t\t</div>\n    \t</td>\n\n\n\n\n    <td width=\"6%\" align=\"left\" valign=\"top\">\n\t<div id='pdfdiv'>\n<a class=\"staticlinks\" href=\"javascript:generateReport('pdf')\"><img  align=\"absmiddle\"  src=\"images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" hspace=\"4\" title=\"");
/*  802 */             out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  803 */             out.write("\"></a>\n\t<a class=\"staticlinks\" href=\"javascript:generateReport('csv')\"><img  align=\"absmiddle\" src=\"../images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\"  title=\"");
/*  804 */             out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/*  805 */             out.write("\"></a>\n\n</div></td>\n  </tr>\n\n\t");
/*  806 */             int evalDoAfterBody = _jspx_th_html_005fform_005f3.doAfterBody();
/*  807 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  811 */         if (_jspx_th_html_005fform_005f3.doEndTag() == 5) {
/*  812 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f3); return;
/*      */         }
/*      */         
/*  815 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f3);
/*  816 */         out.write("\n</table>\n\n<div id='showAllTabs' style=\"Display:block;padding-left:1%;\">\n<br>\n");
/*  817 */         JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab,am.webclient.historydatareport.segmentbyhr.tab,am.webclient.historydatareport.segmentbywk.tab,am.webclient.historydatareport.stdDevn.tab,am.webclient.historydatareport.heatchart.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab,am.webclient.historydatareport.segmentbyhr.tab,am.webclient.historydatareport.segmentbywk.tab,am.webclient.historydatareport.stdDevn.tab,am.webclient.historydatareport.heatchart.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRegularHistData,getHistSgmntByHrData,getHistSgmntByDayData,getStdDeviationData,getHeatData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()), out, true);
/*  818 */         out.write("\n</div>\n<div id='showOneTab' style=\"Display:none\">\n<br>\n");
/*  819 */         JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRegularHistData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()), out, true);
/*  820 */         out.write("\n</div>\n\n<div id=\"heat\">\n</div>\n<div id=\"repo_not_enab_info_div\" style=\"display:none\">\n\t<br>\n\t<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"messageboxfailure\" align=\"center\">\n\t  <tr>\n\t  ");
/*  821 */         if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer) {
/*  822 */           out.write("\n\t\t\t<td class=\"message\" style=\"height:35px\">");
/*  823 */           out.print(FormatUtil.getString("am.webclient.reports.attribute.notenabled.text"));
/*  824 */           out.write(".&nbsp;<a href='javascript:openEnabledReporsWindow();' class=\"staticlinks\">");
/*  825 */           out.print(FormatUtil.getString("am.webclient.common.clickhere.text"));
/*  826 */           out.write("</a> ");
/*  827 */           out.print(FormatUtil.getString("am.webclient.reports.enable.text"));
/*  828 */           out.write(".</td>\n\t");
/*      */         } else {
/*  830 */           out.write("\n\t<td class=\"message\" style=\"height:35px\">");
/*  831 */           out.print(FormatUtil.getString("am.webclient.reports.attribute.notenabled.text"));
/*  832 */           out.write(".</td>\n\t");
/*      */         }
/*  834 */         out.write("\t\n            <td class=\"message\" style=\"height:35px\" align=\"center\"><img src=\"/images/cross.gif\" onclick='javascrip:hideDiv(\"repo_not_enab_info_div\");' style=\"cursor:pointer\"></td>\n\t </tr>\n\t</table>\n</div>\n\n\n<br>\n<div id='regular'>\n");
/*      */       }
/*      */       
/*      */ 
/*  838 */       out.write(10);
/*      */       
/*  840 */       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  841 */       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  842 */       _jspx_th_c_005fif_005f2.setParent(null);
/*      */       
/*  844 */       _jspx_th_c_005fif_005f2.setTest("${STATUS!='SUCCESS'}");
/*  845 */       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  846 */       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */         for (;;) {
/*  848 */           out.write("\n<br>\n<br>\n<table align=\"center\" cellspacing=\"5\" width=\"100%\" >\n  <tr>\n    <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr>\n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/*  849 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */             return;
/*  851 */           out.write("</span></td>\n        </tr>\n ");
/*      */           
/*  853 */           if (!com.adventnet.appmanager.util.OEMUtil.isOEM())
/*      */           {
/*      */ 
/*  856 */             out.write("\n        <tr>\n          <td align=\"right\" class=\"lightbg\"> <a href=\"http:");
/*  857 */             out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link"));
/*  858 */             out.write("/no-reports-for-7-and-30-icons.html\" class=\"staticlinks\">\n            ");
/*  859 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/*  860 */             out.write("\n            >></a></td>\n        </tr>\n");
/*      */           }
/*  862 */           out.write("\n      </table></td>\n  </tr>\n</table>\n");
/*  863 */           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  864 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  868 */       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  869 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */       }
/*      */       else {
/*  872 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  873 */         out.write(10);
/*  874 */         out.write(10);
/*      */         
/*  876 */         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  877 */         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  878 */         _jspx_th_c_005fif_005f3.setParent(null);
/*      */         
/*  880 */         _jspx_th_c_005fif_005f3.setTest("${STATUS=='SUCCESS'}");
/*  881 */         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  882 */         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */           for (;;) {
/*  884 */             out.write(10);
/*      */             
/*  886 */             com.adventnet.appmanager.struts.actions.ComparingSla cs = new com.adventnet.appmanager.struts.actions.ComparingSla();
/*  887 */             int sizeofseq = 3;
/*  888 */             String unit = (String)pageContext.findAttribute("unit");
/*  889 */             String period = request.getParameter("period");
/*  890 */             String valueforperiod = cs.getValueForPeriod(period);
/*      */             
/*  892 */             out.write(10);
/*  893 */             out.write(10);
/*  894 */             if ((period != null) && (period.equals("14"))) {
/*  895 */               out.write("\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"100%\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        ");
/*  896 */               if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/*  898 */               out.write(32);
/*  899 */               if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/*  901 */               out.write("\n        ");
/*  902 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  903 */               String resname = (String)request.getAttribute("resourcename");
/*  904 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/*  905 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/*      */               
/*      */ 
/*      */ 
/*  909 */               out.write("\n            <tr>\n          <td class=\"tableheadingbborder\">\n\n\t\t");
/*  910 */               out.print(FormatUtil.getString("webclient.performance.reports.header", new String[] { FormatUtil.getString(nameofmonitor), resname }));
/*  911 */               out.write("\n          </td>\n        </tr>\n        ");
/*  912 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/*  913 */               if (rid != -1) {
/*  914 */                 out.write("\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/*  915 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/*  916 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/*  917 */                 out.print(resname);
/*  918 */                 out.write("\n\t\t</td>\n              </tr>\n              <tr>\n                <td class=\"yellowgrayborderbr\">");
/*  919 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/*  920 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/*  921 */                 out.print(FormatUtil.getString(nameofmonitor));
/*  922 */                 out.write("\n                  ");
/*  923 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*  924 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*  925 */                 out.write("\n                </td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborderbr\">");
/*  926 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  927 */                 out.write("\n                </td>\n                <td class=\"whitegrayborder\"> ");
/*  928 */                 out.print(startime);
/*  929 */                 out.write(" </td>\n              </tr>\n              <tr>\n                <td  class=\"yellowgrayborderbr\">");
/*  930 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  931 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/*  932 */                 out.print(endtime);
/*  933 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/*  935 */               out.write("\n          <tr>\n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\">\n                  ");
/*      */               
/*  937 */               if (request.getParameter("period").equals("-7"))
/*      */               {
/*      */ 
/*  940 */                 out.write("\n                  <img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  946 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/*  947 */                 out.print(request.getParameter("method"));
/*  948 */                 out.write("&resourceid=");
/*  949 */                 out.print(request.getParameter("resourceid"));
/*  950 */                 out.write("&childid=");
/*  951 */                 out.print(request.getParameter("childid"));
/*  952 */                 out.write("&keyids=");
/*  953 */                 out.print(request.getParameter("keyids"));
/*  954 */                 out.write("&attributeid=");
/*  955 */                 out.print(request.getParameter("attributeid"));
/*  956 */                 out.write("&period=-7&resourcename=");
/*  957 */                 out.print(request.getParameter("resourcename"));
/*  958 */                 out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  959 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/*  960 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/*  964 */               out.write("\n                </td>\n                <td width=\"4%\">\n                  ");
/*      */               
/*  966 */               if (request.getParameter("period").equals("-30"))
/*      */               {
/*      */ 
/*  969 */                 out.write("\n                  <img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  975 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/*  976 */                 out.print(request.getParameter("method"));
/*  977 */                 out.write("&resourceid=");
/*  978 */                 out.print(request.getParameter("resourceid"));
/*  979 */                 out.write("&childid=");
/*  980 */                 out.print(request.getParameter("childid"));
/*  981 */                 out.write("&keyids=");
/*  982 */                 out.print(request.getParameter("keyids"));
/*  983 */                 out.write("&attributeid=");
/*  984 */                 out.print(request.getParameter("attributeid"));
/*  985 */                 out.write("&period=-30&resourcename=");
/*  986 */                 out.print(request.getParameter("resourcename"));
/*  987 */                 out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  988 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/*  989 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/*  993 */               out.write("\n                </td>\n              </tr>\n            </table></td>\n        </tr>\n     ");
/*  994 */               ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/*  995 */               if (rawvalues.size() > 0)
/*      */               {
/*      */ 
/*  998 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"));
/*      */               }
/*      */               
/* 1001 */               String attUnit = "";
/* 1002 */               if (!unit.equals(""))
/*      */               {
/* 1004 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1009 */               out.write("\n\n        <tr align=\"left\">\n          <td width=\"100%\" height=\"50\" align=\"center\">\n            ");
/*      */               
/* 1011 */               String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */               
/* 1013 */               out.write("\n         ");
/*      */               
/* 1015 */               TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1016 */               _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 1017 */               _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */               
/* 1019 */               _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("graph");
/*      */               
/* 1021 */               _jspx_th_awolf_005ftimechart_005f0.setWidth("600");
/*      */               
/* 1023 */               _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*      */               
/* 1025 */               _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */               
/* 1027 */               _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */               
/* 1029 */               _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(temp);
/*      */               
/* 1031 */               _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */               
/* 1033 */               _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/* 1034 */               int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 1035 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 1036 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 1037 */                   out = _jspx_page_context.pushBody();
/* 1038 */                   _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 1039 */                   _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 1042 */                   out.write("\n            ");
/* 1043 */                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 1044 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1047 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 1048 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1051 */               if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 1052 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */               }
/*      */               
/* 1055 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 1056 */               out.write(32);
/* 1057 */               out.write("\n\n          </td>\n        </tr>\n        </table>\n        <br>\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n <tr>\n    <td class=\"tableheadingbborder\"  width='100%' colspan='3'>");
/* 1058 */               out.print(FormatUtil.getString(nameofmonitor));
/* 1059 */               out.write(" </td>\n  </tr>\n\n  <tr>\n    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/* 1060 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1061 */               out.write("</td>\n    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/* 1062 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1063 */               out.write("</td>\n    <td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1064 */               out.print(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/* 1065 */               out.write(" </td>\n\n  </tr>\n  ");
/* 1066 */               for (int i = 0; i < rawvalues.size(); i++) {
/* 1067 */                 Properties row = (Properties)rawvalues.get(i);
/* 1068 */                 String bgcolor = "";
/* 1069 */                 String selectedColor = "class=\"selectedborder\"";
/* 1070 */                 if (i % 2 == 0)
/*      */                 {
/* 1072 */                   bgcolor = "class=\"whitegrayborder\"";
/*      */                 }
/*      */                 else
/*      */                 {
/* 1076 */                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                 }
/*      */                 
/* 1079 */                 out.write("\n  <tr>\n\t");
/*      */                 
/* 1081 */                 long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/* 1082 */                 String resourcetype = String.valueOf(request.getAttribute("type"));
/* 1083 */                 String VAL = null;
/* 1084 */                 VAL = String.valueOf(row.get("VALUE"));
/* 1085 */                 pageContext.setAttribute("date1", new Date(archivedTime));
/* 1086 */                 out.write("\n\n                 <td ");
/* 1087 */                 out.print(bgcolor);
/* 1088 */                 out.write(" align=\"center\">");
/* 1089 */                 if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                   return;
/* 1091 */                 out.write("</td>\n    <td ");
/* 1092 */                 out.print(bgcolor);
/* 1093 */                 out.write(" align=\"center\">");
/* 1094 */                 if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                   return;
/* 1096 */                 out.write("</td>\n         <td ");
/* 1097 */                 out.print(bgcolor);
/* 1098 */                 out.write(" align=\"center\">");
/* 1099 */                 out.print(VAL);
/* 1100 */                 out.write("</td>  </tr>\n\n  ");
/*      */               }
/* 1102 */               out.write("\n    </table>\n     <br>\n"); }
/* 1103 */             if ((period != null) && (period.equals("20"))) {
/* 1104 */               out.write("\n<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"100%\">\n\t    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t        ");
/* 1105 */               if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/* 1107 */               out.write(32);
/* 1108 */               if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/* 1110 */               out.write("\n\t        ");
/* 1111 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/* 1112 */               String resname = (String)request.getAttribute("resourcename");
/* 1113 */               ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/*      */               
/* 1115 */               Properties STime = (Properties)rawvalues.get(rawvalues.size() - 1);
/* 1116 */               long startTime = ((Long)STime.get("COLLECTIONTIME")).longValue();
/*      */               
/* 1118 */               Properties ETime = (Properties)rawvalues.get(0);
/* 1119 */               long endTime = ((Long)ETime.get("COLLECTIONTIME")).longValue();
/*      */               
/*      */ 
/* 1122 */               String startime = FormatUtil.formatDT(startTime + "");
/* 1123 */               String endtime = FormatUtil.formatDT(endTime + "");
/* 1124 */               String bHr = (String)request.getAttribute("bRuleName");
/*      */               
/*      */ 
/*      */ 
/* 1128 */               out.write("\n\t          <tr>\n\t\t          <td class=\"tableheadingbborder\">\n\t\t\t\t\t");
/*      */               
/* 1130 */               ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1131 */               _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1132 */               _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f3);
/* 1133 */               int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1134 */               if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                 for (;;) {
/* 1136 */                   out.write("\n\t\t\t\t   \t\t");
/*      */                   
/* 1138 */                   WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1139 */                   _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1140 */                   _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                   
/* 1142 */                   _jspx_th_c_005fwhen_005f4.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1143 */                   int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1144 */                   if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                     for (;;) {
/* 1146 */                       out.write("\n\t\t\t\t\t\t\t");
/* 1147 */                       out.print(FormatUtil.getString("am.webclient.historydatareport.polledDataheading.text", new String[] { FormatUtil.getString(nameofmonitor), startime, endtime }));
/* 1148 */                       out.write("\n\t\t\t\t   \t\t");
/* 1149 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1150 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1154 */                   if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1155 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                   }
/*      */                   
/* 1158 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1159 */                   out.write("\n\t\t\t\t   \t\t");
/*      */                   
/* 1161 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1162 */                   _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1163 */                   _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1164 */                   int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1165 */                   if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                     for (;;) {
/* 1167 */                       out.write("\n\t\t\t\t   \t \t\t");
/* 1168 */                       out.print(FormatUtil.getString("am.webclient.historydatareport.polledData.bHrheading.text", new String[] { FormatUtil.getString(nameofmonitor), bHr }));
/* 1169 */                       out.write("\n\t\t\t\t   \t\t");
/* 1170 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1171 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1175 */                   if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1176 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                   }
/*      */                   
/* 1179 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1180 */                   out.write("\n\t\t\t\t\t");
/* 1181 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1182 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1186 */               if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1187 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */               }
/*      */               
/* 1190 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1191 */               out.write("\n\t\t          </td>\n        \t</tr>\n\t        ");
/* 1192 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/* 1193 */               if (rid != -1)
/*      */               {
/* 1195 */                 String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + rid + "&period=0&Report=true&resourceType=Monitors&resid=" + rid;
/* 1196 */                 java.util.List allSecondLevelAttribute = com.adventnet.appmanager.util.ReportUtil.getAllSecondLevelAttribute();
/*      */                 
/* 1198 */                 out.write("\n\t\t        <tr>\n\t\t          <td>\n\t\t\t          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1199 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 1200 */                 out.write("</td>\n\t\t\t                <td width=\"78%\" class=\"whitegrayborder\">");
/* 1201 */                 out.print(resname);
/* 1202 */                 out.write("\n\t\t\t\t\t");
/*      */                 
/* 1204 */                 if ((allSecondLevelAttribute != null) && (!allSecondLevelAttribute.contains(request.getParameter("attributeid"))))
/*      */                 {
/*      */ 
/* 1207 */                   out.write("\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 1208 */                   out.print(glanceUrl);
/* 1209 */                   out.write("')\"><img  align=\"absmiddle\"  src=\"images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 1210 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1211 */                   out.write("\" hspace=\"4\" title=\"");
/* 1212 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1213 */                   out.write("\"></a>\n\t\t\t\t\t");
/*      */                 }
/*      */                 
/* 1216 */                 out.write("\n\t\t\t\t\t</td>\n\t\t\t              </tr>\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"yellowgrayborderbr\">");
/* 1217 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 1218 */                 out.write(" </td>\n\t\t\t                <td width=\"78%\" class=\"yellowgrayborder\">");
/* 1219 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1220 */                 out.write("\n\n\t\t\t                  ");
/* 1221 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*      */                 {
/* 1223 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*      */                 }
/* 1225 */                 out.write("\n\t\t\t                </td>\n\t\t\t              </tr>\n\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1226 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1227 */                 out.write("\n\t\t\t                </td>\n\t\t\t                <td width=\"78%\" class=\"whitegrayborder\"> ");
/* 1228 */                 out.print(startime);
/* 1229 */                 out.write(" </td>\n\t\t\t              </tr>\n\t\t\t              <tr>\n\t\t\t                <td  width=\"22%\" class=\"yellowgrayborderbr\">");
/* 1230 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1231 */                 out.write("\n\t\t\t                </td>\n\t\t\t                <td width=\"78%\" class=\"yellowgrayborder\">");
/* 1232 */                 out.print(endtime);
/* 1233 */                 out.write("</td>\n\t\t\t              </tr>\n\n\t\t\t         </table>\n\t\t\t      </td>\n\t\t        </tr>\n         \t\t  ");
/*      */                 
/* 1235 */                 if (rawvalues.size() > 0)
/*      */                 {
/* 1237 */                   String bhr = FormatUtil.getString((String)pageContext.findAttribute("businessPeriod"));
/*      */                   
/* 1239 */                   graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), bhr);
/*      */                 }
/*      */                 
/* 1242 */                 String attUnit = "";
/* 1243 */                 if (!unit.equals(""))
/*      */                 {
/* 1245 */                   attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */                 }
/*      */                 
/*      */ 
/* 1249 */                 out.write("\n\t              <tr align=\"left\">\n\t\t\t          <td width=\"100%\" height=\"50\" align='center'>\n\t\t\t            ");
/*      */                 
/* 1251 */                 String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */                 
/* 1253 */                 out.write("\n\t\t\t         \t");
/*      */                 
/* 1255 */                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1256 */                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 1257 */                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                 
/* 1259 */                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("graph");
/*      */                 
/* 1261 */                 _jspx_th_awolf_005ftimechart_005f1.setWidth("850");
/*      */                 
/* 1263 */                 _jspx_th_awolf_005ftimechart_005f1.setHeight("300");
/*      */                 
/* 1265 */                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                 
/* 1267 */                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1269 */                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(temp);
/*      */                 
/* 1271 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomDateAxis(true);
/*      */                 
/* 1273 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomAngle(270.0D);
/* 1274 */                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 1275 */                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 1276 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1277 */                     out = _jspx_page_context.pushBody();
/* 1278 */                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 1279 */                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1282 */                     out.write("\n\t\t\t            ");
/* 1283 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 1284 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1287 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1288 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1291 */                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 1292 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                 }
/*      */                 
/* 1295 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 1296 */                 out.write(9);
/* 1297 */                 out.write(9);
/* 1298 */                 out.write("\n\t\t\t          </td>\n\t\t\t      </tr>\n\t\t\t      <tr>\n\t\t\t      \t<td width=\"100%\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"8\"></td>\n\t\t\t      </tr>\n\t\t\t</table><br>\n\t\t        \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t\t\t\t\t\t <tr>\n\t\t\t\t\t\t    <td class=\"tableheadingbborder\"  width='100%' colspan='3'>");
/* 1299 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1300 */                 out.write(" </td>\n\t\t\t\t\t\t  </tr>\n\n\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/* 1301 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1302 */                 out.write("</td>\n\t\t\t\t\t\t    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/* 1303 */                 out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1304 */                 out.write("</td>\n\n\t\t\t\t\t\t    ");
/* 1305 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*      */                 {
/* 1307 */                   out.write("\n\t\t \t\t\t\t\t\t\t<td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1308 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.value.text"));
/* 1309 */                   out.write("\n\t\t\t \t\t\t\t\t\t\t ");
/* 1310 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 1311 */                   out.write("\n\t\t\t \t\t\t\t\t\t </td>\n\t\t\t \t\t\t\t\t");
/*      */                 } else {
/* 1313 */                   out.write("\n\t\t\t \t\t\t\t\t\t<td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1314 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.value.text"));
/* 1315 */                   out.write(" </td>\n\t\t\t \t\t\t\t\t\t");
/*      */                 }
/* 1317 */                 out.write("\n\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t  ");
/* 1318 */                 for (int i = 0; i < rawvalues.size(); i++) {
/* 1319 */                   Properties row = (Properties)rawvalues.get(i);
/* 1320 */                   String bgcolor = "";
/* 1321 */                   String selectedColor = "class=\"selectedborder\"";
/* 1322 */                   if (i % 2 == 0)
/*      */                   {
/* 1324 */                     bgcolor = "class=\"whitegrayborder\"";
/*      */                   }
/*      */                   else
/*      */                   {
/* 1328 */                     bgcolor = "class=\"yellowgrayborder\"";
/*      */                   }
/*      */                   
/*      */ 
/* 1332 */                   out.write("\n\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t");
/*      */                   
/* 1334 */                   long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/* 1335 */                   String resourcetype = String.valueOf(request.getAttribute("type"));
/* 1336 */                   String VAL = null;
/* 1337 */                   VAL = (String)row.get("VALUE");
/* 1338 */                   pageContext.setAttribute("date1", new Date(archivedTime));
/* 1339 */                   out.write("\n\n\t\t\t\t\t\t                 <td ");
/* 1340 */                   out.print(bgcolor);
/* 1341 */                   out.write(" align=\"center\">");
/* 1342 */                   if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                     return;
/* 1344 */                   out.write("</td>\n\t\t\t\t\t\t    <td ");
/* 1345 */                   out.print(bgcolor);
/* 1346 */                   out.write(" align=\"center\">");
/* 1347 */                   if (_jspx_meth_fmt_005fformatDate_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                     return;
/* 1349 */                   out.write("</td>\n\t\t\t\t\t\t         <td ");
/* 1350 */                   out.print(bgcolor);
/* 1351 */                   out.write("  align=\"center\">");
/* 1352 */                   out.print(VAL);
/* 1353 */                   out.write("</td>  </tr>\n\n\t\t\t\t\t\t  ");
/*      */                 }
/* 1355 */                 out.write("\n\t\t\t\t\t\t    </table><br>\n\t        ");
/*      */               }
/* 1357 */               out.write("\n\t  </td>\n\t </tr>\n</table>\n");
/*      */             } else {
/* 1359 */               out.write("\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"100%\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        ");
/* 1360 */               if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/* 1362 */               out.write(32);
/* 1363 */               if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                 return;
/* 1365 */               out.write("\n        ");
/* 1366 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/* 1367 */               String resname = (String)request.getAttribute("resourcename");
/* 1368 */               String start_time = String.valueOf(((Date)request.getAttribute("starttime")).getTime());
/*      */               
/* 1370 */               String end_time = String.valueOf(((Date)request.getAttribute("endtime")).getTime());
/*      */               
/* 1372 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/* 1373 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/* 1374 */               String bHr = (String)request.getAttribute("bRuleName");
/*      */               
/* 1376 */               out.write("\n        ");
/* 1377 */               if (request.getParameter("period").equals("4"))
/*      */               {
/* 1379 */                 out.write("\n        <tr>\n          <td class=\"tableheadingbborder\">\n\t");
/*      */                 
/* 1381 */                 ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1382 */                 _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1383 */                 _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fif_005f3);
/* 1384 */                 int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1385 */                 if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                   for (;;) {
/* 1387 */                     out.write("\n   \t\t");
/*      */                     
/* 1389 */                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1390 */                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1391 */                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                     
/* 1393 */                     _jspx_th_c_005fwhen_005f5.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1394 */                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1395 */                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                       for (;;) {
/* 1397 */                         out.write("\n\t\t\t");
/* 1398 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 1399 */                         out.write("\n   \t\t");
/* 1400 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1401 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1405 */                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1406 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                     }
/*      */                     
/* 1409 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1410 */                     out.write("\n   \t\t");
/*      */                     
/* 1412 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1413 */                     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1414 */                     _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1415 */                     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1416 */                     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                       for (;;) {
/* 1418 */                         out.write("\n   \t \t\t");
/* 1419 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }), bHr }));
/* 1420 */                         out.write("\n   \t\t");
/* 1421 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1422 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1426 */                     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1427 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                     }
/*      */                     
/* 1430 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1431 */                     out.write(10);
/* 1432 */                     out.write(9);
/* 1433 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1434 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1438 */                 if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1439 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                 }
/*      */                 
/* 1442 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1443 */                 out.write("\n          </td>\n        </tr>\n        ");
/*      */               }
/*      */               else {
/* 1446 */                 out.write("\n        <tr>\n          <td class=\"tableheadingbborder\">\n\t");
/*      */                 
/* 1448 */                 ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1449 */                 _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1450 */                 _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f3);
/* 1451 */                 int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1452 */                 if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                   for (;;) {
/* 1454 */                     out.write("\n   \t\t");
/*      */                     
/* 1456 */                     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1457 */                     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1458 */                     _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                     
/* 1460 */                     _jspx_th_c_005fwhen_005f6.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1461 */                     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1462 */                     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                       for (;;) {
/* 1464 */                         out.write("\n\t\t\t");
/* 1465 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 1466 */                         out.write("\n   \t\t");
/* 1467 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1468 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1472 */                     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1473 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                     }
/*      */                     
/* 1476 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1477 */                     out.write("\n   \t\t");
/*      */                     
/* 1479 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1480 */                     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1481 */                     _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1482 */                     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1483 */                     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                       for (;;) {
/* 1485 */                         out.write("\n   \t \t\t");
/* 1486 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }), bHr }));
/* 1487 */                         out.write("\n   \t\t");
/* 1488 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1489 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1493 */                     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1494 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                     }
/*      */                     
/* 1497 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1498 */                     out.write(10);
/* 1499 */                     out.write(9);
/* 1500 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1501 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1505 */                 if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1506 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                 }
/*      */                 
/* 1509 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1510 */                 out.write("\n          </td>\n        </tr>\n        ");
/*      */               }
/* 1512 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/* 1513 */               if (rid != -1) {
/* 1514 */                 String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + rid + "&period=0&Report=true&resourceType=Monitors&resid=" + rid;
/* 1515 */                 java.util.List allSecondLevelAttribute = com.adventnet.appmanager.util.ReportUtil.getAllSecondLevelAttribute();
/*      */                 
/* 1517 */                 out.write("\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1518 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 1519 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/* 1520 */                 out.print(resname);
/* 1521 */                 out.write(10);
/* 1522 */                 out.write(9);
/* 1523 */                 out.write(9);
/*      */                 
/* 1525 */                 if ((allSecondLevelAttribute != null) && (!allSecondLevelAttribute.contains(request.getParameter("attributeid"))))
/*      */                 {
/*      */ 
/* 1528 */                   out.write("\n\t\t&nbsp;\n\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 1529 */                   out.print(glanceUrl);
/* 1530 */                   out.write("')\"><img  align=\"absmiddle\"  src=\"images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 1531 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1532 */                   out.write("\" hspace=\"4\" title=\"");
/* 1533 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1534 */                   out.write("\"></a>\n\t\t");
/*      */                 }
/*      */                 
/*      */ 
/* 1538 */                 out.write("\n\t\t</td>\n              </tr>\n              <tr>\n                <td class=\"yellowgrayborderbr\">");
/* 1539 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 1540 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1541 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1542 */                 out.write("\n                  ");
/* 1543 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/* 1544 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 1545 */                 out.write("\n                </td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborderbr\">");
/* 1546 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1547 */                 out.write("\n                </td>\n                <td class=\"whitegrayborder\"> ");
/* 1548 */                 out.print(startime);
/* 1549 */                 out.write(" </td>\n              </tr>\n              <tr>\n                <td  class=\"yellowgrayborderbr\">");
/* 1550 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1551 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1552 */                 out.print(endtime);
/* 1553 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/* 1555 */               out.write("\n        <tr>\n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\">\n                  ");
/*      */               
/* 1557 */               if (request.getParameter("period").equals("-7"))
/*      */               {
/*      */ 
/* 1560 */                 out.write("\n                  <img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 1566 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/* 1567 */                 out.print(request.getParameter("method"));
/* 1568 */                 out.write("&resourceid=");
/* 1569 */                 out.print(request.getParameter("resourceid"));
/* 1570 */                 out.write("&childid=");
/* 1571 */                 out.print(request.getParameter("childid"));
/* 1572 */                 out.write("&keyids=");
/* 1573 */                 out.print(request.getParameter("keyids"));
/* 1574 */                 out.write("&attributeid=");
/* 1575 */                 out.print(request.getParameter("attributeid"));
/* 1576 */                 out.write("&period=-7&resourcename=");
/* 1577 */                 out.print(request.getParameter("resourcename"));
/* 1578 */                 out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 1579 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 1580 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/* 1584 */               out.write("\n                </td>\n                <td width=\"4%\">\n                  ");
/*      */               
/* 1586 */               if (request.getParameter("period").equals("-30"))
/*      */               {
/*      */ 
/* 1589 */                 out.write("\n                  <img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 1595 */                 out.write("\n                  <a href='../showHistoryData.do?method=");
/* 1596 */                 out.print(request.getParameter("method"));
/* 1597 */                 out.write("&resourceid=");
/* 1598 */                 out.print(request.getParameter("resourceid"));
/* 1599 */                 out.write("&childid=");
/* 1600 */                 out.print(request.getParameter("childid"));
/* 1601 */                 out.write("&keyids=");
/* 1602 */                 out.print(request.getParameter("keyids"));
/* 1603 */                 out.write("&attributeid=");
/* 1604 */                 out.print(request.getParameter("attributeid"));
/* 1605 */                 out.write("&period=-30&resourcename=");
/* 1606 */                 out.print(request.getParameter("resourcename"));
/* 1607 */                 out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 1608 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 1609 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/* 1613 */               out.write("\n                </td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               
/*      */ 
/* 1616 */               ArrayList pdfdata = (ArrayList)request.getAttribute("pdfdata");
/* 1617 */               ArrayList list = (ArrayList)request.getAttribute("list");
/*      */               
/* 1619 */               if (list != null)
/*      */               {
/* 1621 */                 sizeofseq = list.size();
/*      */               }
/* 1623 */               String bRule = (String)request.getAttribute("businessPeriod");
/* 1624 */               String graph_type = "graph";
/* 1625 */               String graph_height = "200";
/* 1626 */               String graph_width = "800";
/*      */               
/* 1628 */               String ShowData = (String)request.getAttribute("CompareUrl");
/* 1629 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/* 1631 */                 sumgraph.setResid(request.getParameter("childid"));
/* 1632 */                 sumgraph.setAttributeid(request.getParameter("attributeid"));
/* 1633 */                 String strtime = (String)request.getAttribute("STIME");
/* 1634 */                 String etime = (String)request.getAttribute("ETIME");
/* 1635 */                 sumgraph.setStarttime(strtime);
/* 1636 */                 sumgraph.setEndtime(etime);
/*      */                 
/*      */ 
/* 1639 */                 sumgraph.setArchivedforUrl(true);
/* 1640 */                 sumgraph.setCompareUrls(true);
/* 1641 */                 String dailyRptStarttime = (String)request.getAttribute("dailyStime");
/* 1642 */                 String dailyRptEndtime = (String)request.getAttribute("dailyEtime");
/* 1643 */                 if ((dailyRptStarttime != null) && (!dailyRptStarttime.equals("0")))
/*      */                 {
/* 1645 */                   sumgraph.setDailyRptStarttime(dailyRptStarttime);
/* 1646 */                   sumgraph.setDailyRptEndtime(dailyRptEndtime);
/*      */                 }
/* 1648 */                 graph_type = "sumgraph";
/* 1649 */                 if (sizeofseq < 5)
/*      */                 {
/* 1651 */                   graph_height = "250";
/*      */                 }
/* 1653 */                 else if ((sizeofseq >= 5) && (sizeofseq < 10))
/*      */                 {
/* 1655 */                   graph_height = "400";
/*      */                 }
/* 1657 */                 else if ((sizeofseq >= 10) && (sizeofseq < 20))
/*      */                 {
/* 1659 */                   graph_height = "700";
/*      */                 }
/* 1661 */                 else if ((sizeofseq >= 20) && (sizeofseq < 30))
/*      */                 {
/* 1663 */                   graph_height = "800";
/*      */                 }
/* 1665 */                 else if ((sizeofseq >= 30) && (sizeofseq < 40))
/*      */                 {
/* 1667 */                   graph_height = "900";
/*      */                 }
/*      */                 else
/*      */                 {
/* 1671 */                   graph_height = "1000";
/*      */                 }
/*      */                 
/* 1674 */                 graph_width = "700";
/*      */ 
/*      */               }
/* 1677 */               else if (request.getParameter("period").equals("4"))
/*      */               {
/* 1679 */                 if ((bRule != null) && (!bRule.equals("oni")))
/*      */                 {
/* 1681 */                   graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), request.getAttribute("customstarttime").toString(), request.getAttribute("customendtime").toString(), bRule);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1685 */                   graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), request.getAttribute("customstarttime").toString(), request.getAttribute("customendtime").toString());
/*      */                 }
/*      */                 
/*      */               }
/* 1689 */               else if ((bRule != null) && (!bRule.equals("oni")))
/*      */               {
/* 1691 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), bRule);
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 1696 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"));
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 1702 */               if ((list != null) && (list.size() > 0))
/*      */               {
/* 1704 */                 pageContext.setAttribute("urlseqs", list);
/*      */               }
/*      */               
/* 1707 */               String attUnit = "";
/* 1708 */               if (!unit.equals(""))
/*      */               {
/* 1710 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/*      */ 
/* 1714 */               out.write("\n        <tr align=\"left\">\n          <td width=\"100%\" height=\"50\" align='center'>\n            ");
/*      */               
/* 1716 */               String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */               
/* 1718 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/*      */ 
/*      */ 
/* 1722 */                 out.write("\n            ");
/*      */                 
/* 1724 */                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1725 */                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 1726 */                 _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f3);
/*      */                 
/* 1728 */                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer(graph_type);
/*      */                 
/* 1730 */                 _jspx_th_awolf_005ftimechart_005f2.setWidth(graph_width);
/*      */                 
/* 1732 */                 _jspx_th_awolf_005ftimechart_005f2.setHeight(graph_height);
/*      */                 
/* 1734 */                 _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                 
/* 1736 */                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1738 */                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(temp);
/*      */                 
/* 1740 */                 _jspx_th_awolf_005ftimechart_005f2.setCustomDateAxis(true);
/*      */                 
/* 1742 */                 _jspx_th_awolf_005ftimechart_005f2.setCustomAngle(270.0D);
/*      */                 
/* 1744 */                 _jspx_th_awolf_005ftimechart_005f2.setShape(true);
/* 1745 */                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 1746 */                 if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 1747 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 1748 */                     out = _jspx_page_context.pushBody();
/* 1749 */                     _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 1750 */                     _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1753 */                     out.write("\n            ");
/* 1754 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 1755 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1758 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 1759 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1762 */                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 1763 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                 }
/*      */                 
/* 1766 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 1767 */                 out.write(32);
/* 1768 */                 out.write("\n            ");
/*      */               }
/*      */               else
/*      */               {
/* 1772 */                 out.write("\n            ");
/*      */                 
/* 1774 */                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1775 */                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 1776 */                 _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fif_005f3);
/*      */                 
/* 1778 */                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer(graph_type);
/*      */                 
/* 1780 */                 _jspx_th_awolf_005ftimechart_005f3.setWidth(graph_width);
/*      */                 
/* 1782 */                 _jspx_th_awolf_005ftimechart_005f3.setHeight(graph_height);
/*      */                 
/* 1784 */                 _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                 
/* 1786 */                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1788 */                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(temp);
/*      */                 
/* 1790 */                 _jspx_th_awolf_005ftimechart_005f3.setCustomDateAxis(true);
/*      */                 
/* 1792 */                 _jspx_th_awolf_005ftimechart_005f3.setCustomAngle(270.0D);
/*      */                 
/* 1794 */                 _jspx_th_awolf_005ftimechart_005f3.setMovingAverage(FormatUtil.getString("am.webclient.730attribute.legendmovingaverage.text"));
/*      */                 
/* 1796 */                 _jspx_th_awolf_005ftimechart_005f3.setMovingAverageName(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"));
/*      */                 
/* 1798 */                 _jspx_th_awolf_005ftimechart_005f3.setShape(true);
/* 1799 */                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 1800 */                 if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 1801 */                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 1802 */                     out = _jspx_page_context.pushBody();
/* 1803 */                     _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 1804 */                     _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1807 */                     out.write("\n            ");
/* 1808 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 1809 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1812 */                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 1813 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1816 */                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 1817 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                 }
/*      */                 
/* 1820 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 1821 */                 out.write(32);
/* 1822 */                 out.write("\n            ");
/*      */               }
/* 1824 */               out.write("\n          </td>\n        </tr>\n        ");
/*      */               
/* 1826 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1827 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1828 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */               
/* 1830 */               _jspx_th_c_005fif_005f4.setTest("${!empty requestScope.maxvalue}");
/* 1831 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1832 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 1834 */                   out.write("\n        <tr align=\"left\">\n          ");
/*      */                   
/* 1836 */                   String minavgval = FormatUtil.formatNumber(request.getAttribute("minAvgValue"));
/* 1837 */                   String maxavgval = FormatUtil.formatNumber(request.getAttribute("maxAvgValue"));
/* 1838 */                   String avgvalue = FormatUtil.formatNumber(request.getAttribute("avgvalue"));
/*      */                   
/*      */ 
/*      */ 
/* 1842 */                   out.write("\n          <td class=\"bodytext\"> <br> <table border='0' align=left >\n              <tr >\n                <td width=\"16%\"  class='bodytext'> ");
/* 1843 */                   out.print(FormatUtil.getString(nameofmonitor));
/* 1844 */                   out.write(":\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left> ");
/* 1845 */                   out.print(FormatUtil.getString("am.webclient.common.minimum.average.text"));
/* 1846 */                   out.write(":&nbsp;&nbsp;");
/* 1847 */                   out.print(minavgval);
/* 1848 */                   out.write("&nbsp;&nbsp;");
/* 1849 */                   out.print(FormatUtil.getString(unit));
/* 1850 */                   out.write("\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1851 */                   out.print(FormatUtil.getString("am.webclient.common.maximum.average.text"));
/* 1852 */                   out.write(":&nbsp;&nbsp;");
/* 1853 */                   out.print(maxavgval);
/* 1854 */                   out.write("&nbsp;&nbsp;");
/* 1855 */                   out.print(FormatUtil.getString(unit));
/* 1856 */                   out.write("\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1857 */                   out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 1858 */                   out.write(":&nbsp;&nbsp;");
/* 1859 */                   out.print(avgvalue);
/* 1860 */                   out.write("&nbsp;&nbsp;");
/* 1861 */                   out.print(FormatUtil.getString(unit));
/* 1862 */                   out.write("\n                </td>\n                <td width=\"24%\"  class='bodytext' align=left>\n\t\t\t\t\t");
/*      */                   
/* 1864 */                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1865 */                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1866 */                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                   
/* 1868 */                   _jspx_th_c_005fif_005f5.setTest("${!empty NFPERCENTILE}");
/* 1869 */                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1870 */                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                     for (;;) {
/* 1872 */                       out.write("\n\t\t\t\t\t\t");
/* 1873 */                       out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.nfpercentile.text"));
/* 1874 */                       out.write(":&nbsp;&nbsp;");
/* 1875 */                       if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                         return;
/* 1877 */                       out.write("\n\t\t\t\t\t");
/* 1878 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1879 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1883 */                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1884 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                   }
/*      */                   
/* 1887 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1888 */                   out.write("\n                </td>\n              </tr>\n              ");
/* 1889 */                   ArrayList threshold = (ArrayList)request.getAttribute("thresholdDetails");
/*      */                   
/* 1891 */                   if (threshold.size() > 0)
/*      */                   {
/* 1893 */                     String critical = (String)threshold.get(0);
/* 1894 */                     String warning = (String)threshold.get(1);
/* 1895 */                     String clear = (String)threshold.get(2);
/* 1896 */                     String units = FormatUtil.getString((String)threshold.get(3));
/*      */                     
/*      */ 
/* 1899 */                     out.write("\n              <tr >\n                <td class=\"bodytext\" width='16%'>");
/* 1900 */                     out.print(FormatUtil.getString("am.webclient.admin.thresholddetails.link"));
/* 1901 */                     out.write(":\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1902 */                     out.print(FormatUtil.getString("Critical"));
/* 1903 */                     out.write(":&nbsp;&nbsp;");
/* 1904 */                     out.print(critical);
/* 1905 */                     out.write("&nbsp;&nbsp;");
/* 1906 */                     out.print(FormatUtil.getString(unit));
/* 1907 */                     out.write("\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1908 */                     out.print(FormatUtil.getString("Warning"));
/* 1909 */                     out.write(":&nbsp;&nbsp;");
/* 1910 */                     out.print(warning);
/* 1911 */                     out.write("&nbsp;&nbsp;");
/* 1912 */                     out.print(FormatUtil.getString(unit));
/* 1913 */                     out.write("\n                </td>\n                <td width=\"20%\"  class='bodytext' align=left>");
/* 1914 */                     out.print(FormatUtil.getString("Clear"));
/* 1915 */                     out.write("\n                  :&nbsp;&nbsp;");
/* 1916 */                     out.print(clear);
/* 1917 */                     out.write("&nbsp;&nbsp;");
/* 1918 */                     out.print(FormatUtil.getString(unit));
/* 1919 */                     out.write("\n                </td>\n                <td width=\"24%\"  class='bodytext' align=left>&nbsp;</td>\n              </tr>\n              ");
/*      */                   }
/* 1921 */                   out.write("\n            </table></td>\n        </tr>\n        ");
/* 1922 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1923 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1927 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1928 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 1931 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1932 */               out.write(" </table></td>\n  </tr>\n</table>\n\n<br>\n\n\n\n\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align=\"center\">\n <tr>\n    <td class=\"tableheadingbborder\"  width='100%' colspan='");
/* 1933 */               out.print(sizeofseq + 2);
/* 1934 */               out.write(39);
/* 1935 */               out.write(62);
/* 1936 */               out.print(FormatUtil.getString(nameofmonitor));
/* 1937 */               out.write(" </td>\n  </tr>\n  ");
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1943 */               if ((ShowData != null) && (ShowData.equals("true")))
/*      */               {
/* 1945 */                 out.write("\n\t<tr>\n    <td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 1946 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1947 */                 out.write("</td>\n    <td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 1948 */                 out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1949 */                 out.write("</td>\n   ");
/* 1950 */                 for (int y = 0; y < list.size(); y++)
/*      */                 {
/* 1952 */                   ArrayList d1 = (ArrayList)list.get(y);
/* 1953 */                   out.write("\n\t<td class=\"columnheadingrightborder\" title='");
/* 1954 */                   out.print((String)d1.get(1));
/* 1955 */                   out.write(39);
/* 1956 */                   out.write(32);
/* 1957 */                   out.write(62);
/* 1958 */                   out.print(y + 1);
/* 1959 */                   out.write(46);
/* 1960 */                   out.print(FormatUtil.getTrimmedText((String)d1.get(1), 30));
/* 1961 */                   out.write("</td>\n    ");
/*      */                 }
/* 1963 */                 out.write("\n  </tr>\n\n ");
/*      */                 
/*      */                 try
/*      */                 {
/* 1967 */                   for (int i = 0; i < pdfdata.size(); i++)
/*      */                   {
/* 1969 */                     String bgcolor = "";
/* 1970 */                     String selectedColor = "class=\"selectedborder\"";
/* 1971 */                     if (i % 2 == 0)
/*      */                     {
/* 1973 */                       bgcolor = "class=\"whitegrayborder\"";
/*      */                     }
/*      */                     else
/*      */                     {
/* 1977 */                       bgcolor = "class=\"yellowgrayborder\"";
/*      */                     }
/* 1979 */                     ArrayList a1 = (ArrayList)pdfdata.get(i);
/*      */                     
/* 1981 */                     long archivedTime = ((Long)a1.get(0)).longValue();
/* 1982 */                     pageContext.setAttribute("date", new Date(archivedTime));
/*      */                     
/*      */ 
/* 1985 */                     out.write("\n  <tr>\n\t<td ");
/* 1986 */                     out.print(bgcolor);
/* 1987 */                     out.write(" align=\"center\">");
/* 1988 */                     if (_jspx_meth_fmt_005fformatDate_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                       return;
/* 1990 */                     out.write("</td>\n    <td ");
/* 1991 */                     out.print(bgcolor);
/* 1992 */                     out.write(" align=\"center\">");
/* 1993 */                     if (_jspx_meth_fmt_005fformatDate_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                       return;
/* 1995 */                     out.write("</td>\n");
/*      */                     
/* 1997 */                     for (int k = 1; k < a1.size(); k++)
/*      */                     {
/*      */ 
/* 2000 */                       for (int m = 0; m < list.size(); m++)
/*      */                       {
/* 2002 */                         ArrayList q1 = (ArrayList)list.get(m);
/*      */                         
/* 2004 */                         String key = (String)q1.get(0);
/* 2005 */                         java.util.HashMap h1 = (java.util.HashMap)a1.get(k);
/* 2006 */                         String avgvalues = null;
/*      */                         
/* 2008 */                         Properties p1 = (Properties)h1.get(key);
/*      */                         
/* 2010 */                         if (p1 != null)
/*      */                         {
/* 2012 */                           avgvalues = p1.getProperty("AVGVALUE");
/* 2013 */                           avgvalues = FormatUtil.formatNumber(avgvalues);
/*      */                         }
/*      */                         else {
/* 2016 */                           avgvalues = "-";
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 2021 */                         out.write("\n<td ");
/* 2022 */                         out.print(bgcolor);
/* 2023 */                         out.write(" align=\"center\">");
/* 2024 */                         out.print(avgvalues);
/* 2025 */                         out.write("</td>\n");
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 2030 */                     out.write("\n  </tr>\n");
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/* 2037 */                   exc.printStackTrace();
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 2043 */               out.write("\n  <tr>\n    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/* 2044 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 2045 */               out.write("</td>\n    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/* 2046 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 2047 */               out.write("</td>\n    <td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 2048 */               out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 2049 */               out.write(32);
/* 2050 */               out.print(attUnit);
/* 2051 */               out.write("</td>\n    <td width=\"23%\" class=\"columnheading\" align=\"center\">");
/* 2052 */               out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 2053 */               out.write(32);
/* 2054 */               out.print(attUnit);
/* 2055 */               out.write("</td>\n    <td width=\"21%\" class=\"columnheading\" align=\"center\">");
/* 2056 */               out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 2057 */               out.write(32);
/* 2058 */               out.print(attUnit);
/* 2059 */               out.write("</td>\n  </tr>\n  ");
/*      */               
/* 2061 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2062 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2063 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */               
/* 2065 */               _jspx_th_logic_005fiterate_005f0.setName("data");
/*      */               
/* 2067 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/* 2069 */               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */               
/* 2071 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.Properties");
/* 2072 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2073 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2074 */                 Properties row = null;
/* 2075 */                 Integer i = null;
/* 2076 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2077 */                   out = _jspx_page_context.pushBody();
/* 2078 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2079 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/* 2081 */                 row = (Properties)_jspx_page_context.findAttribute("row");
/* 2082 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                 for (;;) {
/* 2084 */                   out.write(10);
/* 2085 */                   out.write(32);
/* 2086 */                   out.write(32);
/*      */                   
/* 2088 */                   String bgcolor = "";
/* 2089 */                   String selectedColor = "class=\"selectedborder\"";
/* 2090 */                   if (i.intValue() % 2 == 0)
/*      */                   {
/* 2092 */                     bgcolor = "class=\"whitegrayborder\"";
/*      */                   }
/*      */                   else
/*      */                   {
/* 2096 */                     bgcolor = "class=\"yellowgrayborder\"";
/*      */                   }
/*      */                   
/* 2099 */                   out.write("\n   <tr>\n\t");
/*      */                   
/* 2101 */                   long archivedTime = ((Long)row.get("ARCHIVEDTIME")).longValue();
/* 2102 */                   String resourcetype = String.valueOf(request.getAttribute("type"));
/* 2103 */                   String avgVal = null;
/* 2104 */                   String minVal; String maxVal; if (resourcetype.equals("weblogic-server"))
/*      */                   {
/* 2106 */                     String minVal = String.valueOf(Long.parseLong(String.valueOf(row.get("MINVALUE"))) / 1024L);
/* 2107 */                     String maxVal = String.valueOf(Long.parseLong(String.valueOf(row.get("MAXVALUE"))) / 1024L);
/* 2108 */                     avgVal = String.valueOf(Long.parseLong(String.valueOf(row.get("AVGVALUE"))) / 1024L);
/*      */                   }
/*      */                   else
/*      */                   {
/* 2112 */                     minVal = String.valueOf(row.get("MINVALUE"));
/* 2113 */                     maxVal = String.valueOf(row.get("MAXVALUE"));
/* 2114 */                     avgVal = String.valueOf(row.get("AVGVALUE"));
/*      */                   }
/*      */                   
/* 2117 */                   out.write(10);
/* 2118 */                   out.write(9);
/*      */                   
/*      */ 
/* 2121 */                   pageContext.setAttribute("date", new Date(archivedTime));
/* 2122 */                   pageContext.setAttribute("minVal", minVal);
/* 2123 */                   pageContext.setAttribute("maxVal", maxVal);
/* 2124 */                   pageContext.setAttribute("avgVal", avgVal);
/*      */                   
/* 2126 */                   out.write("\n\n    <td ");
/* 2127 */                   out.print(bgcolor);
/* 2128 */                   out.write(" align=\"center\">");
/* 2129 */                   if (_jspx_meth_fmt_005fformatDate_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 2131 */                   out.write("</td>\n    <td ");
/* 2132 */                   out.print(bgcolor);
/* 2133 */                   out.write(" align=\"center\">");
/* 2134 */                   if (_jspx_meth_fmt_005fformatDate_005f7(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 2136 */                   out.write("</td>\n\n\n\n");
/*      */                   
/* 2138 */                   ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2139 */                   _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 2140 */                   _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_logic_005fiterate_005f0);
/* 2141 */                   int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 2142 */                   if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                     for (;;) {
/* 2144 */                       out.write("\n    ");
/*      */                       
/* 2146 */                       WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2147 */                       _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2148 */                       _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                       
/* 2150 */                       _jspx_th_c_005fwhen_005f7.setTest("${minVal < 0 && param.attributeid < 10000}");
/* 2151 */                       int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2152 */                       if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                         for (;;) {
/* 2154 */                           out.write("\n        <td ");
/* 2155 */                           out.print(bgcolor);
/* 2156 */                           out.write(" align=\"center\">-</td>\n    ");
/* 2157 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2158 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2162 */                       if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2163 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                       }
/*      */                       
/* 2166 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2167 */                       out.write(10);
/* 2168 */                       out.write(32);
/*      */                       
/* 2170 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2171 */                       _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 2172 */                       _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 2173 */                       int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 2174 */                       if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                         for (;;) {
/* 2176 */                           out.write(10);
/* 2177 */                           out.write(9);
/*      */                           
/* 2179 */                           ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2180 */                           _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 2181 */                           _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_c_005fotherwise_005f7);
/* 2182 */                           int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 2183 */                           if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                             for (;;) {
/* 2185 */                               out.write("\n    \t\t");
/*      */                               
/* 2187 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2188 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2189 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                               
/* 2191 */                               _jspx_th_c_005fwhen_005f8.setTest("${minvalue == minVal}");
/* 2192 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2193 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/* 2195 */                                   out.write("\n        \t\t<td ");
/* 2196 */                                   out.print(bgcolor);
/* 2197 */                                   out.write(" align=\"center\"><table align=\"center\"><tr><td class=\"selectedborder\" align=\"center\">");
/* 2198 */                                   if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                                     return;
/* 2200 */                                   out.write("</td></tr></table></td>\n    \t\t");
/* 2201 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2202 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2206 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2207 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/* 2210 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2211 */                               out.write("\n    \t\t");
/*      */                               
/* 2213 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2214 */                               _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 2215 */                               _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 2216 */                               int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 2217 */                               if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                 for (;;) {
/* 2219 */                                   out.write("\n        \t\t<td ");
/* 2220 */                                   out.print(bgcolor);
/* 2221 */                                   out.write(" align=\"center\">");
/* 2222 */                                   if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fotherwise_005f8, _jspx_page_context))
/*      */                                     return;
/* 2224 */                                   out.write("</td>\n    \t\t");
/* 2225 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 2226 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2230 */                               if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 2231 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                               }
/*      */                               
/* 2234 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 2235 */                               out.write("\n  \t");
/* 2236 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 2237 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2241 */                           if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 2242 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                           }
/*      */                           
/* 2245 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 2246 */                           out.write(10);
/* 2247 */                           out.write(32);
/* 2248 */                           out.write(32);
/* 2249 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 2250 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2254 */                       if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 2255 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                       }
/*      */                       
/* 2258 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 2259 */                       out.write(10);
/* 2260 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 2261 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2265 */                   if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 2266 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                   }
/*      */                   
/* 2269 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 2270 */                   out.write(10);
/* 2271 */                   out.write(10);
/* 2272 */                   out.write(10);
/*      */                   
/* 2274 */                   ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2275 */                   _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 2276 */                   _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_logic_005fiterate_005f0);
/* 2277 */                   int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 2278 */                   if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                     for (;;) {
/* 2280 */                       out.write("\n    ");
/*      */                       
/* 2282 */                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2283 */                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 2284 */                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                       
/* 2286 */                       _jspx_th_c_005fwhen_005f9.setTest("${maxVal < 0 && param.attributeid < 10000}");
/* 2287 */                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 2288 */                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                         for (;;) {
/* 2290 */                           out.write("\n        <td ");
/* 2291 */                           out.print(bgcolor);
/* 2292 */                           out.write(" align=\"center\">-</td>\n    ");
/* 2293 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 2294 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2298 */                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 2299 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                       }
/*      */                       
/* 2302 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 2303 */                       out.write(10);
/* 2304 */                       out.write(32);
/*      */                       
/* 2306 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2307 */                       _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 2308 */                       _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 2309 */                       int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 2310 */                       if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                         for (;;) {
/* 2312 */                           out.write(10);
/* 2313 */                           out.write(9);
/*      */                           
/* 2315 */                           ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2316 */                           _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 2317 */                           _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_c_005fotherwise_005f9);
/* 2318 */                           int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 2319 */                           if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                             for (;;) {
/* 2321 */                               out.write("\n    \t\t");
/*      */                               
/* 2323 */                               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2324 */                               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 2325 */                               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                               
/* 2327 */                               _jspx_th_c_005fwhen_005f10.setTest("${maxvalue == maxVal}");
/* 2328 */                               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 2329 */                               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                 for (;;) {
/* 2331 */                                   out.write("\n        \t\t<td ");
/* 2332 */                                   out.print(bgcolor);
/* 2333 */                                   out.write(" align=\"center\"><table align=\"center\"><tr><td class=\"selectedborder\" align=\"center\">");
/* 2334 */                                   if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                                     return;
/* 2336 */                                   out.write("</td></tr></table></td>\n    \t\t");
/* 2337 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 2338 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2342 */                               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 2343 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                               }
/*      */                               
/* 2346 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 2347 */                               out.write("\n    \t\t");
/*      */                               
/* 2349 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2350 */                               _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 2351 */                               _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 2352 */                               int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 2353 */                               if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                 for (;;) {
/* 2355 */                                   out.write("\n        \t\t<td ");
/* 2356 */                                   out.print(bgcolor);
/* 2357 */                                   out.write(" align=\"center\">");
/* 2358 */                                   if (_jspx_meth_fmt_005fformatNumber_005f3(_jspx_th_c_005fotherwise_005f10, _jspx_page_context))
/*      */                                     return;
/* 2360 */                                   out.write("</td>\n    \t\t");
/* 2361 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 2362 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2366 */                               if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 2367 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                               }
/*      */                               
/* 2370 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 2371 */                               out.write(10);
/* 2372 */                               out.write(9);
/* 2373 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 2374 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2378 */                           if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 2379 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                           }
/*      */                           
/* 2382 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 2383 */                           out.write("\n   ");
/* 2384 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 2385 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2389 */                       if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 2390 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                       }
/*      */                       
/* 2393 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 2394 */                       out.write(10);
/* 2395 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 2396 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2400 */                   if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 2401 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                   }
/*      */                   
/* 2404 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 2405 */                   out.write("\n    <td ");
/* 2406 */                   out.print(bgcolor);
/* 2407 */                   out.write(" align=\"center\">");
/* 2408 */                   if (_jspx_meth_fmt_005fformatNumber_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 2410 */                   out.write(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n  </tr>\n\n  ");
/* 2411 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2412 */                   row = (Properties)_jspx_page_context.findAttribute("row");
/* 2413 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 2414 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2417 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2418 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2421 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2422 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/* 2425 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2426 */               out.write(10);
/*      */               
/*      */ 
/*      */ 
/* 2430 */               out.write("\n\n</table>\n\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n  <tr>\n     <td class=\"columnheading\"><b>");
/* 2431 */               out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 2432 */               out.write("</b><br></td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 2433 */               out.print(FormatUtil.getString("am.webclient.historydata.archiving.text"));
/* 2434 */               out.write("</b>: ");
/* 2435 */               out.print(FormatUtil.getString("am.webclient.historydata.archivingnote.text"));
/* 2436 */               out.write(".</td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 2437 */               out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 2438 */               out.write("</b>: ");
/* 2439 */               out.print(FormatUtil.getString("am.webclient.historydata.minvaluenote.text"));
/* 2440 */               out.write(".<br><br></td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\"> <b>");
/* 2441 */               out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 2442 */               out.write(" </b>: ");
/* 2443 */               out.print(FormatUtil.getString("am.webclient.historydata.maxvaluenote.text"));
/* 2444 */               out.write(".<br><br></td>\n  </tr>\n  <tr>\n   <tr>\n     <td class=\"bodytext\"> <b>");
/* 2445 */               out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 2446 */               out.write(" </b>: ");
/* 2447 */               out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgnote.text"));
/* 2448 */               out.write(".</td>\n  </tr>\n</table>\n<br>\n\n");
/*      */             }
/*      */             
/* 2451 */             out.write(10);
/* 2452 */             out.write(10);
/* 2453 */             response.setContentType("text/html;charset=UTF-8");
/* 2454 */             out.write(10);
/* 2455 */             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2456 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2460 */         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2461 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */         }
/*      */         else {
/* 2464 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2465 */           out.write("\n</div>\n");
/*      */           
/* 2467 */           if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*      */           {
/*      */ 
/* 2470 */             out.write("\n<script type=\"text/javascript\">\n\tvar _gaq = _gaq || [];\t\t\t\t\t\t\t//NO I18N\n\t_gaq.push(['_setAccount', 'UA-202658-68']);\t\t//NO I18N\n\t_gaq.push(['_trackPageview']);\t\t\t\t\t//NO I18N\n\n\t(function() {\n\tvar ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\t//NO I18N\n\tga.src = '//www.google-analytics.com/ga.js';\t//NO I18N\n\t\n\tvar s = document.getElementsByTagName('script')[0]; \t//NO I18N\n\ts.parentNode.insertBefore(ga, s);\t\t\t\t\t\t//NO I18N\n\t})();\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 2474 */           out.write(10);
/*      */           
/* 2476 */           if (request.getAttribute("frompop") == null)
/*      */           {
/*      */ 
/* 2479 */             out.write("\n\n\n<div id=\"segmentByHour\">\n</div>\n<div id=\"segmentByDay\">\n</div>\n<div id=\"stdDevn\">\n\n</div>\n<div id=\"heat\">\n</div>\n\n");
/*      */           }
/*      */           
/*      */ 
/* 2483 */           out.write("\n</body>\n<script>\n$(document).ready(function(){\n\tsortSelectItems(\"allPerformanceAttrbs\");// NO I18N\t\n\tjQuery(\".chosenselect\").chosen();\t\t// NO I18N\t\n});\n\n\t</script>\n</html>\n");
/*      */         }
/* 2485 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2486 */         out = _jspx_out;
/* 2487 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2488 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2489 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2492 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2498 */     PageContext pageContext = _jspx_page_context;
/* 2499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2501 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2502 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2503 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2505 */     _jspx_th_c_005fout_005f0.setValue("${resourcename}");
/* 2506 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2507 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2508 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2509 */       return true;
/*      */     }
/* 2511 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2517 */     PageContext pageContext = _jspx_page_context;
/* 2518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2520 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2521 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2522 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2524 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 2526 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 2527 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2528 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2529 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2530 */       return true;
/*      */     }
/* 2532 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2538 */     PageContext pageContext = _jspx_page_context;
/* 2539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2541 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2542 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2543 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 2545 */     _jspx_th_c_005fif_005f0.setTest("${param.period !='4'}");
/* 2546 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2547 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2549 */         out.write("\n\n    document.forms[0].reporttype.value=type;\n    document.forms[0].businessPeriod.value=document.forms[2].businessPeriod.value;\n\tif(confEndTime!=\"\" && confStartTime!=\"\"){ \n    \tdocument.forms[0].confEndTime.value=confEndTime;\n    \tdocument.forms[0].confStartTime.value=confStartTime;    \n\t}\n    document.forms[0].submit();\n    document.forms[0].reporttype.value=\"html\";\n");
/* 2550 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2555 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2556 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2557 */       return true;
/*      */     }
/* 2559 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2565 */     PageContext pageContext = _jspx_page_context;
/* 2566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2568 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2569 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2570 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 2572 */     _jspx_th_c_005fif_005f1.setTest("${param.period =='4'}");
/* 2573 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2574 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2576 */         out.write("\n    document.forms[1].reporttype.value=type;\n    document.forms[1].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[1].submit();\n    document.forms[1].reporttype.value=\"html\";\n");
/* 2577 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2578 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2582 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2583 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2584 */       return true;
/*      */     }
/* 2586 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2592 */     PageContext pageContext = _jspx_page_context;
/* 2593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2595 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2596 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2597 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2599 */     _jspx_th_html_005fhidden_005f0.setProperty("reporttype");
/*      */     
/* 2601 */     _jspx_th_html_005fhidden_005f0.setValue("html");
/* 2602 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2603 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2604 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2605 */       return true;
/*      */     }
/* 2607 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2613 */     PageContext pageContext = _jspx_page_context;
/* 2614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2616 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2617 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2618 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2620 */     _jspx_th_html_005fhidden_005f1.setProperty("businessPeriod");
/*      */     
/* 2622 */     _jspx_th_html_005fhidden_005f1.setValue("oni");
/* 2623 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2624 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2625 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2626 */       return true;
/*      */     }
/* 2628 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2634 */     PageContext pageContext = _jspx_page_context;
/* 2635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2637 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2638 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 2639 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2641 */     _jspx_th_html_005fhidden_005f2.setProperty("confEndTime");
/*      */     
/* 2643 */     _jspx_th_html_005fhidden_005f2.setValue("");
/* 2644 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 2645 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 2646 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2647 */       return true;
/*      */     }
/* 2649 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 2650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2655 */     PageContext pageContext = _jspx_page_context;
/* 2656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2658 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2659 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 2660 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2662 */     _jspx_th_html_005fhidden_005f3.setProperty("confStartTime");
/*      */     
/* 2664 */     _jspx_th_html_005fhidden_005f3.setValue("");
/* 2665 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 2666 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 2667 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2668 */       return true;
/*      */     }
/* 2670 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2676 */     PageContext pageContext = _jspx_page_context;
/* 2677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2679 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2680 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2681 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 2682 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2683 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2685 */         out.write(32);
/* 2686 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2687 */           return true;
/* 2688 */         out.write(32);
/* 2689 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2690 */           return true;
/* 2691 */         out.write(32);
/* 2692 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2693 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2697 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2698 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2699 */       return true;
/*      */     }
/* 2701 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2707 */     PageContext pageContext = _jspx_page_context;
/* 2708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2710 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2711 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2712 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2714 */     _jspx_th_c_005fwhen_005f0.setTest("${'1' != '1'}");
/* 2715 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2716 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2718 */         out.write("\n            ");
/* 2719 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 2720 */           return true;
/* 2721 */         out.write("\n            ");
/* 2722 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2723 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2727 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2728 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2729 */       return true;
/*      */     }
/* 2731 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2737 */     PageContext pageContext = _jspx_page_context;
/* 2738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2740 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2741 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2742 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2744 */     _jspx_th_html_005ftext_005f0.setSize("14");
/*      */     
/* 2746 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 2748 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 2750 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 2752 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 2754 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtextselected");
/* 2755 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2756 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2757 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2758 */       return true;
/*      */     }
/* 2760 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2766 */     PageContext pageContext = _jspx_page_context;
/* 2767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2769 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2770 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2771 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2772 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2773 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2775 */         out.write(32);
/* 2776 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 2777 */           return true;
/* 2778 */         out.write("\n             ");
/* 2779 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2780 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2784 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2785 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2786 */       return true;
/*      */     }
/* 2788 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2794 */     PageContext pageContext = _jspx_page_context;
/* 2795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2797 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2798 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 2799 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2801 */     _jspx_th_html_005ftext_005f1.setSize("13");
/*      */     
/* 2803 */     _jspx_th_html_005ftext_005f1.setProperty("startDate");
/*      */     
/* 2805 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 2807 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 2809 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetStartTime(this.value)");
/* 2810 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 2811 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2825 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 2827 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2828 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2830 */         out.write(32);
/* 2831 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2832 */           return true;
/* 2833 */         out.write(32);
/* 2834 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2835 */           return true;
/* 2836 */         out.write(32);
/* 2837 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2838 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2842 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2843 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2844 */       return true;
/*      */     }
/* 2846 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2852 */     PageContext pageContext = _jspx_page_context;
/* 2853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2855 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2856 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2857 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2859 */     _jspx_th_c_005fwhen_005f1.setTest("${'1'!= '1'}");
/* 2860 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2861 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2863 */         out.write("\n            ");
/* 2864 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 2865 */           return true;
/* 2866 */         out.write("\n            ");
/* 2867 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2868 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2872 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2873 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2874 */       return true;
/*      */     }
/* 2876 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2882 */     PageContext pageContext = _jspx_page_context;
/* 2883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2885 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2886 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 2887 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2889 */     _jspx_th_html_005ftext_005f2.setProperty("endDate");
/*      */     
/* 2891 */     _jspx_th_html_005ftext_005f2.setSize("14");
/*      */     
/* 2893 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 2895 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 2897 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 2899 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtextselected");
/* 2900 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 2901 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 2902 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2903 */       return true;
/*      */     }
/* 2905 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2911 */     PageContext pageContext = _jspx_page_context;
/* 2912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2914 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2915 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2916 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2917 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2918 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2920 */         out.write(32);
/* 2921 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 2922 */           return true;
/* 2923 */         out.write("\n            ");
/* 2924 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2925 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2929 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2930 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2931 */       return true;
/*      */     }
/* 2933 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2939 */     PageContext pageContext = _jspx_page_context;
/* 2940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2942 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2943 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2944 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 2946 */     _jspx_th_html_005ftext_005f3.setProperty("endDate");
/*      */     
/* 2948 */     _jspx_th_html_005ftext_005f3.setSize("13");
/*      */     
/* 2950 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 2952 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 2954 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetEndTime(this.value)");
/* 2955 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2956 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2957 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2958 */       return true;
/*      */     }
/* 2960 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2966 */     PageContext pageContext = _jspx_page_context;
/* 2967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2969 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2970 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 2971 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 2973 */     _jspx_th_html_005fhidden_005f4.setProperty("businessPeriod");
/*      */     
/* 2975 */     _jspx_th_html_005fhidden_005f4.setValue("oni");
/* 2976 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 2977 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2991 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 2994 */     _jspx_th_html_005fhidden_005f5.setProperty("reporttype");
/*      */     
/* 2996 */     _jspx_th_html_005fhidden_005f5.setValue("html");
/* 2997 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/* 2998 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/* 2999 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 3000 */       return true;
/*      */     }
/* 3002 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 3003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3008 */     PageContext pageContext = _jspx_page_context;
/* 3009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3011 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3012 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3013 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 3014 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3015 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 3017 */         out.write(32);
/* 3018 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 3019 */           return true;
/* 3020 */         out.write(32);
/* 3021 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 3022 */           return true;
/* 3023 */         out.write(32);
/* 3024 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3025 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3029 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3030 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3031 */       return true;
/*      */     }
/* 3033 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3039 */     PageContext pageContext = _jspx_page_context;
/* 3040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3042 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3043 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3044 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3046 */     _jspx_th_c_005fwhen_005f2.setTest("${'1' != '1'}");
/* 3047 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3048 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 3050 */         out.write("\n            ");
/* 3051 */         if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 3052 */           return true;
/* 3053 */         out.write("\n            ");
/* 3054 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3059 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3060 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3061 */       return true;
/*      */     }
/* 3063 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3069 */     PageContext pageContext = _jspx_page_context;
/* 3070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3072 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3073 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3074 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3076 */     _jspx_th_html_005ftext_005f4.setSize("14");
/*      */     
/* 3078 */     _jspx_th_html_005ftext_005f4.setProperty("startDate");
/*      */     
/* 3080 */     _jspx_th_html_005ftext_005f4.setStyleId("start");
/*      */     
/* 3082 */     _jspx_th_html_005ftext_005f4.setReadonly(true);
/*      */     
/* 3084 */     _jspx_th_html_005ftext_005f4.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 3086 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtextselected");
/* 3087 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3088 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3089 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3090 */       return true;
/*      */     }
/* 3092 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3098 */     PageContext pageContext = _jspx_page_context;
/* 3099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3101 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3102 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3103 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3104 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3105 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3107 */         out.write(32);
/* 3108 */         if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 3109 */           return true;
/* 3110 */         out.write("\n             ");
/* 3111 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3112 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3116 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3117 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3118 */       return true;
/*      */     }
/* 3120 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3126 */     PageContext pageContext = _jspx_page_context;
/* 3127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3129 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3130 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3131 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 3133 */     _jspx_th_html_005ftext_005f5.setSize("13");
/*      */     
/* 3135 */     _jspx_th_html_005ftext_005f5.setProperty("startDate");
/*      */     
/* 3137 */     _jspx_th_html_005ftext_005f5.setStyleId("start");
/*      */     
/* 3139 */     _jspx_th_html_005ftext_005f5.setReadonly(true);
/*      */     
/* 3141 */     _jspx_th_html_005ftext_005f5.setOnchange("javascript:fnSetStartTime(this.value)");
/* 3142 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3143 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3144 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3145 */       return true;
/*      */     }
/* 3147 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3153 */     PageContext pageContext = _jspx_page_context;
/* 3154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3156 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3157 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3158 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 3159 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3160 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 3162 */         out.write(32);
/* 3163 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 3164 */           return true;
/* 3165 */         out.write(32);
/* 3166 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 3167 */           return true;
/* 3168 */         out.write(32);
/* 3169 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3170 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3174 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3175 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3176 */       return true;
/*      */     }
/* 3178 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3184 */     PageContext pageContext = _jspx_page_context;
/* 3185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3187 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3188 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3189 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 3191 */     _jspx_th_c_005fwhen_005f3.setTest("${'1'!= '1'}");
/* 3192 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3193 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 3195 */         out.write("\n            ");
/* 3196 */         if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 3197 */           return true;
/* 3198 */         out.write("\n            ");
/* 3199 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3200 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3204 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3205 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3206 */       return true;
/*      */     }
/* 3208 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3214 */     PageContext pageContext = _jspx_page_context;
/* 3215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3217 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3218 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3219 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3221 */     _jspx_th_html_005ftext_005f6.setProperty("endDate");
/*      */     
/* 3223 */     _jspx_th_html_005ftext_005f6.setSize("14");
/*      */     
/* 3225 */     _jspx_th_html_005ftext_005f6.setStyleId("end");
/*      */     
/* 3227 */     _jspx_th_html_005ftext_005f6.setReadonly(true);
/*      */     
/* 3229 */     _jspx_th_html_005ftext_005f6.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 3231 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtextselected");
/* 3232 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3233 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3234 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3235 */       return true;
/*      */     }
/* 3237 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3243 */     PageContext pageContext = _jspx_page_context;
/* 3244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3246 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3247 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3248 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3249 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3250 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3252 */         out.write(32);
/* 3253 */         if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 3254 */           return true;
/* 3255 */         out.write("\n            ");
/* 3256 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3261 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3262 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3263 */       return true;
/*      */     }
/* 3265 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3271 */     PageContext pageContext = _jspx_page_context;
/* 3272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3274 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3275 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 3276 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 3278 */     _jspx_th_html_005ftext_005f7.setProperty("endDate");
/*      */     
/* 3280 */     _jspx_th_html_005ftext_005f7.setSize("13");
/*      */     
/* 3282 */     _jspx_th_html_005ftext_005f7.setStyleId("end");
/*      */     
/* 3284 */     _jspx_th_html_005ftext_005f7.setReadonly(true);
/*      */     
/* 3286 */     _jspx_th_html_005ftext_005f7.setOnchange("javascript:fnSetEndTime(this.value)");
/* 3287 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 3288 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 3289 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3290 */       return true;
/*      */     }
/* 3292 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f6(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3298 */     PageContext pageContext = _jspx_page_context;
/* 3299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3301 */     HiddenTag _jspx_th_html_005fhidden_005f6 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 3302 */     _jspx_th_html_005fhidden_005f6.setPageContext(_jspx_page_context);
/* 3303 */     _jspx_th_html_005fhidden_005f6.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 3305 */     _jspx_th_html_005fhidden_005f6.setProperty("businessPeriod");
/*      */     
/* 3307 */     _jspx_th_html_005fhidden_005f6.setValue("oni");
/* 3308 */     int _jspx_eval_html_005fhidden_005f6 = _jspx_th_html_005fhidden_005f6.doStartTag();
/* 3309 */     if (_jspx_th_html_005fhidden_005f6.doEndTag() == 5) {
/* 3310 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 3311 */       return true;
/*      */     }
/* 3313 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 3314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f7(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3319 */     PageContext pageContext = _jspx_page_context;
/* 3320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3322 */     HiddenTag _jspx_th_html_005fhidden_005f7 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 3323 */     _jspx_th_html_005fhidden_005f7.setPageContext(_jspx_page_context);
/* 3324 */     _jspx_th_html_005fhidden_005f7.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 3326 */     _jspx_th_html_005fhidden_005f7.setProperty("reporttype");
/*      */     
/* 3328 */     _jspx_th_html_005fhidden_005f7.setValue("html");
/* 3329 */     int _jspx_eval_html_005fhidden_005f7 = _jspx_th_html_005fhidden_005f7.doStartTag();
/* 3330 */     if (_jspx_th_html_005fhidden_005f7.doEndTag() == 5) {
/* 3331 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 3332 */       return true;
/*      */     }
/* 3334 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 3335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3340 */     PageContext pageContext = _jspx_page_context;
/* 3341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3343 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 3344 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3345 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f3);
/*      */     
/* 3347 */     _jspx_th_html_005fselect_005f1.setProperty("sevenThirtyAttrib");
/*      */     
/* 3349 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext chosenselect");
/*      */     
/* 3351 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 3353 */     _jspx_th_html_005fselect_005f1.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 3355 */     _jspx_th_html_005fselect_005f1.setOnblur("this.style.width='100%'");
/*      */     
/* 3357 */     _jspx_th_html_005fselect_005f1.setOnchange("this.style.width='100%';fnchangeAttrib(this,0)");
/* 3358 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3359 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3360 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3361 */         out = _jspx_page_context.pushBody();
/* 3362 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3363 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3366 */         out.write("\n                ");
/* 3367 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 3368 */           return true;
/* 3369 */         out.write("\n\t \t ");
/* 3370 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3371 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3374 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3375 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3378 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3379 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f1);
/* 3380 */       return true;
/*      */     }
/* 3382 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f1);
/* 3383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3388 */     PageContext pageContext = _jspx_page_context;
/* 3389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3391 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3392 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3393 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 3395 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("sevenThirtyAttribCln");
/* 3396 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3397 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3398 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3399 */       return true;
/*      */     }
/* 3401 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3407 */     PageContext pageContext = _jspx_page_context;
/* 3408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3410 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 3411 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3412 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f3);
/*      */     
/* 3414 */     _jspx_th_html_005fselect_005f2.setProperty("allPerformanceAttrbs");
/*      */     
/* 3416 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext chosenselect");
/*      */     
/* 3418 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%;height:auto");
/*      */     
/* 3420 */     _jspx_th_html_005fselect_005f2.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 3422 */     _jspx_th_html_005fselect_005f2.setOnblur("this.style.width='100%'");
/*      */     
/* 3424 */     _jspx_th_html_005fselect_005f2.setOnchange("this.style.width='100%';fnchangeAttrib(this,1)");
/* 3425 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3426 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3427 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3428 */         out = _jspx_page_context.pushBody();
/* 3429 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3430 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3433 */         out.write("\n                ");
/* 3434 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 3435 */           return true;
/* 3436 */         out.write("\n\t \t ");
/* 3437 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3438 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3441 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3442 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3445 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3446 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f2);
/* 3447 */       return true;
/*      */     }
/* 3449 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f2);
/* 3450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3455 */     PageContext pageContext = _jspx_page_context;
/* 3456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3458 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3459 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3460 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3462 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("allPerformanceAttrbsCln");
/* 3463 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3464 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3465 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3466 */       return true;
/*      */     }
/* 3468 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3474 */     PageContext pageContext = _jspx_page_context;
/* 3475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3477 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 3478 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3479 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f3);
/*      */     
/* 3481 */     _jspx_th_html_005fselect_005f3.setProperty("businessPeriod");
/*      */     
/* 3483 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 3485 */     _jspx_th_html_005fselect_005f3.setStyle("width:100%");
/*      */     
/* 3487 */     _jspx_th_html_005fselect_005f3.setStyleId("per");
/*      */     
/* 3489 */     _jspx_th_html_005fselect_005f3.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 3491 */     _jspx_th_html_005fselect_005f3.setOnblur("this.style.width='100%'");
/*      */     
/* 3493 */     _jspx_th_html_005fselect_005f3.setOnchange("this.style.width='100%';fnbHour(this)");
/* 3494 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3495 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3496 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3497 */         out = _jspx_page_context.pushBody();
/* 3498 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3499 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3502 */         out.write("\n               ");
/* 3503 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 3504 */           return true;
/* 3505 */         out.write("\n\t \t");
/* 3506 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3507 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3510 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3511 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3514 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3515 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f3);
/* 3516 */       return true;
/*      */     }
/* 3518 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f3);
/* 3519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3524 */     PageContext pageContext = _jspx_page_context;
/* 3525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3527 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3528 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3529 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 3531 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("businessRuleNames");
/* 3532 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3533 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3534 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3535 */       return true;
/*      */     }
/* 3537 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3543 */     PageContext pageContext = _jspx_page_context;
/* 3544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3546 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3547 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3548 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3550 */     _jspx_th_c_005fout_005f2.setValue("${STATUS}");
/* 3551 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3552 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3553 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3554 */       return true;
/*      */     }
/* 3556 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3562 */     PageContext pageContext = _jspx_page_context;
/* 3563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3565 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3566 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3567 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3569 */     _jspx_th_c_005fset_005f0.setVar("rname");
/* 3570 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3571 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3572 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3573 */         out = _jspx_page_context.pushBody();
/* 3574 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3575 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3578 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3579 */           return true;
/* 3580 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3581 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3584 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3585 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3588 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3589 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3590 */       return true;
/*      */     }
/* 3592 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3598 */     PageContext pageContext = _jspx_page_context;
/* 3599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3601 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3602 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3603 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3605 */     _jspx_th_c_005fout_005f3.setValue("${monitortype}");
/* 3606 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3607 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3608 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3609 */       return true;
/*      */     }
/* 3611 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3617 */     PageContext pageContext = _jspx_page_context;
/* 3618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3620 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3621 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3622 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3624 */     _jspx_th_c_005fset_005f1.setVar("resourcename");
/* 3625 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3626 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3627 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3628 */         out = _jspx_page_context.pushBody();
/* 3629 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3630 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3633 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 3634 */           return true;
/* 3635 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3639 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3640 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3643 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3644 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3645 */       return true;
/*      */     }
/* 3647 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3648 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3653 */     PageContext pageContext = _jspx_page_context;
/* 3654 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3656 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3657 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3658 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3660 */     _jspx_th_c_005fout_005f4.setValue("${resourcename}");
/* 3661 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3662 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3663 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3664 */       return true;
/*      */     }
/* 3666 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3667 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3672 */     PageContext pageContext = _jspx_page_context;
/* 3673 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3675 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3676 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 3677 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3679 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${date1}");
/*      */     
/* 3681 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("MMM d,yyyy");
/* 3682 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 3683 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 3684 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3685 */       return true;
/*      */     }
/* 3687 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 3688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3693 */     PageContext pageContext = _jspx_page_context;
/* 3694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3696 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3697 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 3698 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3700 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${date1}");
/*      */     
/* 3702 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("H:mm");
/* 3703 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 3704 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 3705 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 3706 */       return true;
/*      */     }
/* 3708 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 3709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3714 */     PageContext pageContext = _jspx_page_context;
/* 3715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3717 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3718 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3719 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3721 */     _jspx_th_c_005fset_005f2.setVar("rname");
/* 3722 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3723 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3724 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3725 */         out = _jspx_page_context.pushBody();
/* 3726 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3727 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3730 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 3731 */           return true;
/* 3732 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3733 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3736 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3737 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3740 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3741 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3742 */       return true;
/*      */     }
/* 3744 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3750 */     PageContext pageContext = _jspx_page_context;
/* 3751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3753 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3754 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3755 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 3757 */     _jspx_th_c_005fout_005f5.setValue("${monitortype}");
/* 3758 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3759 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3761 */       return true;
/*      */     }
/* 3763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3769 */     PageContext pageContext = _jspx_page_context;
/* 3770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3772 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3773 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3774 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3776 */     _jspx_th_c_005fset_005f3.setVar("resourcename");
/* 3777 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3778 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3779 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3780 */         out = _jspx_page_context.pushBody();
/* 3781 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3782 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3785 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 3786 */           return true;
/* 3787 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3788 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3791 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3792 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3795 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3796 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3797 */       return true;
/*      */     }
/* 3799 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3805 */     PageContext pageContext = _jspx_page_context;
/* 3806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3808 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3809 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3810 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 3812 */     _jspx_th_c_005fout_005f6.setValue("${resourcename}");
/* 3813 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3814 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3815 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3816 */       return true;
/*      */     }
/* 3818 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3824 */     PageContext pageContext = _jspx_page_context;
/* 3825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3827 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3828 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 3829 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3831 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${date1}");
/*      */     
/* 3833 */     _jspx_th_fmt_005fformatDate_005f2.setPattern("MMM d,yyyy");
/* 3834 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 3835 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 3836 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 3837 */       return true;
/*      */     }
/* 3839 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 3840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3845 */     PageContext pageContext = _jspx_page_context;
/* 3846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3848 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f3 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3849 */     _jspx_th_fmt_005fformatDate_005f3.setPageContext(_jspx_page_context);
/* 3850 */     _jspx_th_fmt_005fformatDate_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3852 */     _jspx_th_fmt_005fformatDate_005f3.setValue("${date1}");
/*      */     
/* 3854 */     _jspx_th_fmt_005fformatDate_005f3.setPattern("H:mm");
/* 3855 */     int _jspx_eval_fmt_005fformatDate_005f3 = _jspx_th_fmt_005fformatDate_005f3.doStartTag();
/* 3856 */     if (_jspx_th_fmt_005fformatDate_005f3.doEndTag() == 5) {
/* 3857 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 3858 */       return true;
/*      */     }
/* 3860 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 3861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3866 */     PageContext pageContext = _jspx_page_context;
/* 3867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3869 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3870 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3871 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3873 */     _jspx_th_c_005fset_005f4.setVar("rname");
/* 3874 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3875 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3876 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3877 */         out = _jspx_page_context.pushBody();
/* 3878 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3879 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3882 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 3883 */           return true;
/* 3884 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3885 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3888 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3889 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3892 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3893 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3894 */       return true;
/*      */     }
/* 3896 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3902 */     PageContext pageContext = _jspx_page_context;
/* 3903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3905 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3906 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3907 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 3909 */     _jspx_th_c_005fout_005f7.setValue("${monitortype}");
/* 3910 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3911 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3912 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3913 */       return true;
/*      */     }
/* 3915 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3921 */     PageContext pageContext = _jspx_page_context;
/* 3922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3924 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3925 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3926 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3928 */     _jspx_th_c_005fset_005f5.setVar("resourcename");
/* 3929 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3930 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 3931 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3932 */         out = _jspx_page_context.pushBody();
/* 3933 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 3934 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3937 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 3938 */           return true;
/* 3939 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 3940 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3943 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3944 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3947 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3948 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 3949 */       return true;
/*      */     }
/* 3951 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 3952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3957 */     PageContext pageContext = _jspx_page_context;
/* 3958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3960 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3961 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3962 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 3964 */     _jspx_th_c_005fout_005f8.setValue("${resourcename}");
/* 3965 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3966 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3967 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3968 */       return true;
/*      */     }
/* 3970 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3976 */     PageContext pageContext = _jspx_page_context;
/* 3977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3979 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3980 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3981 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3983 */     _jspx_th_c_005fout_005f9.setValue("${NFPERCENTILE}");
/* 3984 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3985 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3986 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3987 */       return true;
/*      */     }
/* 3989 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3990 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3995 */     PageContext pageContext = _jspx_page_context;
/* 3996 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3998 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f4 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 3999 */     _jspx_th_fmt_005fformatDate_005f4.setPageContext(_jspx_page_context);
/* 4000 */     _jspx_th_fmt_005fformatDate_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4002 */     _jspx_th_fmt_005fformatDate_005f4.setValue("${date}");
/*      */     
/* 4004 */     _jspx_th_fmt_005fformatDate_005f4.setPattern("MMM d,yyyy");
/* 4005 */     int _jspx_eval_fmt_005fformatDate_005f4 = _jspx_th_fmt_005fformatDate_005f4.doStartTag();
/* 4006 */     if (_jspx_th_fmt_005fformatDate_005f4.doEndTag() == 5) {
/* 4007 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 4008 */       return true;
/*      */     }
/* 4010 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 4011 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4016 */     PageContext pageContext = _jspx_page_context;
/* 4017 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4019 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f5 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 4020 */     _jspx_th_fmt_005fformatDate_005f5.setPageContext(_jspx_page_context);
/* 4021 */     _jspx_th_fmt_005fformatDate_005f5.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4023 */     _jspx_th_fmt_005fformatDate_005f5.setValue("${date}");
/*      */     
/* 4025 */     _jspx_th_fmt_005fformatDate_005f5.setPattern("H:mm");
/* 4026 */     int _jspx_eval_fmt_005fformatDate_005f5 = _jspx_th_fmt_005fformatDate_005f5.doStartTag();
/* 4027 */     if (_jspx_th_fmt_005fformatDate_005f5.doEndTag() == 5) {
/* 4028 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 4029 */       return true;
/*      */     }
/* 4031 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 4032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4037 */     PageContext pageContext = _jspx_page_context;
/* 4038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4040 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f6 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 4041 */     _jspx_th_fmt_005fformatDate_005f6.setPageContext(_jspx_page_context);
/* 4042 */     _jspx_th_fmt_005fformatDate_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 4044 */     _jspx_th_fmt_005fformatDate_005f6.setValue("${date}");
/*      */     
/* 4046 */     _jspx_th_fmt_005fformatDate_005f6.setPattern("MMM d,yyyy");
/* 4047 */     int _jspx_eval_fmt_005fformatDate_005f6 = _jspx_th_fmt_005fformatDate_005f6.doStartTag();
/* 4048 */     if (_jspx_th_fmt_005fformatDate_005f6.doEndTag() == 5) {
/* 4049 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f6);
/* 4050 */       return true;
/*      */     }
/* 4052 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f6);
/* 4053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f7(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4058 */     PageContext pageContext = _jspx_page_context;
/* 4059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4061 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f7 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 4062 */     _jspx_th_fmt_005fformatDate_005f7.setPageContext(_jspx_page_context);
/* 4063 */     _jspx_th_fmt_005fformatDate_005f7.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 4065 */     _jspx_th_fmt_005fformatDate_005f7.setValue("${date}");
/*      */     
/* 4067 */     _jspx_th_fmt_005fformatDate_005f7.setPattern("H:mm");
/* 4068 */     int _jspx_eval_fmt_005fformatDate_005f7 = _jspx_th_fmt_005fformatDate_005f7.doStartTag();
/* 4069 */     if (_jspx_th_fmt_005fformatDate_005f7.doEndTag() == 5) {
/* 4070 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f7);
/* 4071 */       return true;
/*      */     }
/* 4073 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f7);
/* 4074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4079 */     PageContext pageContext = _jspx_page_context;
/* 4080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4082 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 4083 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 4084 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 4086 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${minVal}");
/* 4087 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 4088 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 4089 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 4090 */       return true;
/*      */     }
/* 4092 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 4093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4098 */     PageContext pageContext = _jspx_page_context;
/* 4099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4101 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 4102 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 4103 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 4105 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${minVal}");
/* 4106 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 4107 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 4108 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 4109 */       return true;
/*      */     }
/* 4111 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 4112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4117 */     PageContext pageContext = _jspx_page_context;
/* 4118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4120 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 4121 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 4122 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 4124 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${maxVal}");
/* 4125 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 4126 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 4127 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 4128 */       return true;
/*      */     }
/* 4130 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 4131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f3(JspTag _jspx_th_c_005fotherwise_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4136 */     PageContext pageContext = _jspx_page_context;
/* 4137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4139 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 4140 */     _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 4141 */     _jspx_th_fmt_005fformatNumber_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f10);
/*      */     
/* 4143 */     _jspx_th_fmt_005fformatNumber_005f3.setValue("${maxVal}");
/* 4144 */     int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 4145 */     if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 4146 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 4147 */       return true;
/*      */     }
/* 4149 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 4150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4155 */     PageContext pageContext = _jspx_page_context;
/* 4156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4158 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 4159 */     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 4160 */     _jspx_th_fmt_005fformatNumber_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 4162 */     _jspx_th_fmt_005fformatNumber_005f4.setValue("${avgVal}");
/*      */     
/* 4164 */     _jspx_th_fmt_005fformatNumber_005f4.setMinFractionDigits("2");
/*      */     
/* 4166 */     _jspx_th_fmt_005fformatNumber_005f4.setMaxFractionDigits("3");
/* 4167 */     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 4168 */     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 4169 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 4170 */       return true;
/*      */     }
/* 4172 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fminFractionDigits_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 4173 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fHistoryData_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */