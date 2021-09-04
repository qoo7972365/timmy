/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
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
/*      */ 
/*      */ public final class Popup_005fConfigurationData_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   35 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   41 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   42 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   70 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   74 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   95 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   99 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  105 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  106 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  108 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/*  113 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  115 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  116 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  117 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*  118 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  125 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  128 */     JspWriter out = null;
/*  129 */     Object page = this;
/*  130 */     JspWriter _jspx_out = null;
/*  131 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  135 */       response.setContentType("text/html;charset=UTF-8");
/*  136 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  138 */       _jspx_page_context = pageContext;
/*  139 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  140 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  141 */       session = pageContext.getSession();
/*  142 */       out = pageContext.getOut();
/*  143 */       _jspx_out = out;
/*      */       
/*  145 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/tooltip.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/validation.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n");
/*  146 */       com.adventnet.appmanager.reporting.form.ReportForm FlashForm = null;
/*  147 */       FlashForm = (com.adventnet.appmanager.reporting.form.ReportForm)_jspx_page_context.getAttribute("FlashForm", 2);
/*  148 */       if (FlashForm == null) {
/*  149 */         FlashForm = new com.adventnet.appmanager.reporting.form.ReportForm();
/*  150 */         _jspx_page_context.setAttribute("FlashForm", FlashForm, 2);
/*      */       }
/*  152 */       out.write(10);
/*  153 */       com.adventnet.appmanager.bean.SummaryBean sumgraph = null;
/*  154 */       sumgraph = (com.adventnet.appmanager.bean.SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  155 */       if (sumgraph == null) {
/*  156 */         sumgraph = new com.adventnet.appmanager.bean.SummaryBean();
/*  157 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/*  159 */       out.write(10);
/*  160 */       out.write(10);
/*  161 */       HistoryDataGraphUtil graph = null;
/*  162 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/*  163 */       if (graph == null) {
/*  164 */         graph = new HistoryDataGraphUtil();
/*  165 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/*  167 */       out.write("\n\n\n<html>\n<head>\n<title>");
/*  168 */       out.print(FormatUtil.getString("am.webclient.historydata.text"));
/*  169 */       out.write("</title>\n");
/*      */       
/*  171 */       if (request.getAttribute("frompop") == null)
/*      */       {
/*      */ 
/*  174 */         out.write("\n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/*  175 */         out.print(FormatUtil.getString("am.webclient.historydata.text"));
/*  176 */         out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n<br>\n");
/*      */       }
/*      */       
/*      */ 
/*  180 */       out.write(10);
/*  181 */       out.write(10);
/*  182 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  183 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  185 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  186 */       out.write(10);
/*  187 */       out.write("\n<script>\n\nvar customstarttime = escape(\"");
/*  188 */       out.print(request.getAttribute("startDate"));
/*  189 */       out.write("\");\nvar customendtime = escape(\"");
/*  190 */       out.print(request.getAttribute("endDate"));
/*  191 */       out.write("\");\nvar tab = ");
/*  192 */       out.print(request.getAttribute("tabsel"));
/*  193 */       out.write(" ;\nvar tblname ='");
/*  194 */       out.print(request.getAttribute("tblname"));
/*  195 */       out.write("';\n\nfunction addNote(textName,divName,divNote)\n{\n\tshowDiv(divName);\n\tshowDiv(divNote);\n\tdocument.getElementById(textName).disabled=false;\n\n}\n\nfunction cancelDowntimeReason(downtime,textName,divName,textReason)\n{\n    var reasonID = document.forms[0].resourceid.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar reason=document.getElementById(textName).value;\n    url=\"/showConfigurationData.do?method=cancelNote&tblname=\"+tblname+\"&downtime=\"+downtime+\"&reasonid=\"+a+\"&textValue=\"+encodeURIComponent(reason)+\"&resourceid=\"+reasonID+\"&date=\"+new Date(); ");
/*  196 */       out.write("\n\thttp.open(\"GET\",url,true); ");
/*  197 */       out.write("\n\thttp.onreadystatechange = new Function('if(http.readyState == 4 && http.status == 200){var respStr = http.responseText;document.getElementById(textName).value=respStr;}'); ");
/*  198 */       out.write("\n\thttp.send(null);\n\thideDiv(divName);\n\tdocument.getElementById(textName).style.border=\"0\"; ");
/*  199 */       out.write("\n}\n\nvar textName_1 = \"\";\nfunction insertDowntimeReason(downtime,textName,divName,textReason)\n{\n  if(document.getElementById(textName).value=='')\n   {\n     alert(\"");
/*  200 */       out.print(FormatUtil.getString("am.webclient.mysql.reason.text"));
/*  201 */       out.write("\");\n     document.getElementById(textName).focus();\n     showDiv(divName);\n   }\n   else\n   {\n\ttextName_1=textName;\n    var reasonID = document.forms[0].resourceid.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar reason=document.getElementById(textName).value;\n    url=\"/showConfigurationData.do?method=saveNote&tblname=\"+tblname+\"&downtime=\"+downtime+\"&reasonid=\"+a+\"&textValue=\"+encodeURIComponent(reason)+\"&resourceid=\"+reasonID+\"&date=\"+new Date(); ");
/*  202 */       out.write("\n\thttp.open(\"GET\",url,true); ");
/*  203 */       out.write("\n\thttp.onreadystatechange = handleResponse;\n\thttp.send(null);\n\thideDiv(divName);\n\tdocument.getElementById(textName).style.border=\"0\"; ");
/*  204 */       out.write("\n\t//document.getElementById(textName).disabled=true;\n  }\n}\n\nfunction handleResponse() {\n\tif(http.readyState == 4 && http.status == 200) {\n\t\tvar respStr = http.responseText;\n\t\tdocument.getElementById(textName_1).value=respStr;\n\t\tdocument.getElementById(textName_1).title=respStr;\n\t\t//document.getElementById(textName_1).onMouseOver=function (){ddrivetip(document.getElementById(textName_1),event,respStr,false,true,'#000000',200,'lightyellow');}\n\t\t//document.getElementById(textName_1).onmouseout=hideddrivetip();\n\t}\n}\n\n function fnCheckCustomTime(check)\n {\n if(check.startDate.value=='')\n {\n alert(\"");
/*  205 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  206 */       out.write("\")\n return false\n }\n else if(check.endDate.value=='')\n {\n alert(\"");
/*  207 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  208 */       out.write("\")\n return false\n }\n var s=check.startDate.value;\n var e=check.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/*  209 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  210 */       out.write("\" );\n     return false;\n    }\n    customstarttime=check.startDate.value;\n    customendtime=check.endDate.value;\n return true\n }\n  function fnSetEndTime(a)\n{\n\tdocument.forms[1].endDate.value=a;\n\tdocument.forms[1].customendtime.value=a;\n\n}\nfunction fnSetStartTime(a)\n{\n\n\tdocument.forms[1].startDate.value=a;\n\tdocument.forms[1].customstarttime.value=a;\n\n}\n\n\nvar perd = ");
/*  211 */       out.print(request.getAttribute("period"));
/*  212 */       out.write(";\nvar bHr = \"oni\";\n function fnbHour(bHrSel)\n{\n\tvar m = document.forms[0].method.value;\n  \tvar r = document.forms[0].resourceid.value;\n  \tvar c = document.forms[0].childid.value;\n  \tvar n = document.forms[0].resourcename.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = perd;\n\tvar b = bHrSel.value;\n\tbHr = b;\n\tif(tab ==1)\n\t{\n\turl=\"/showConfigurationData.do?method=\"+m+\"&tblname=\"+tblname+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+b+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  213 */       out.print(System.currentTimeMillis());
/*  214 */       out.write("; //NO I18N\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getResp;\n    http.send(null);\n\t}\n\telse if (tab == 3)\n\t{\n\t\tgetHistSgmntByDayData();\n\t}\n\telse if (tab == 4)\n\t{\n\t\tgetStdDeviationData();\n\t}\n}\n\n function fnchangeAttrib(attribSel)\n {\n\tvar m = document.forms[0].method.value;\n  \tvar r = document.forms[0].resourceid.value;\n  \tvar c = document.forms[0].childid.value;\n  \tvar n = document.forms[0].resourcename.value;\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = perd;\n\tvar b = attribSel.value;\n\tvar sep=b.indexOf(\"#\");\n\n        r=b.substr(0,sep);\n        document.forms[0].resourceid.value = r;\n\tdocument.forms[1].resourceid.value = r;\n\n        a=b.substr(sep+1); //NO I18N\n\tdocument.forms[0].attributeid.value = a;\n\tdocument.forms[1].attributeid.value = a;\n\n\tif(tab ==1)\n\t{\n\turl=\"/showConfigurationData.do?method=\"+m+\"&tblname=\"+tblname+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  215 */       out.print(System.currentTimeMillis());
/*  216 */       out.write("; //NO I18N\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getResp;\n    http.send(null);\n\t}\n\telse if(tab == 2)\n\t{\n\t\tgetGlobalData();\n\t}\n\n}\nfunction generateReport(type)\n{\nvar temp='';\ntemp=document.forms[0].attributeid.value; //NO I18N\n\n\tdocument.forms[0].reporttype.value=type; //NO I18N\n\tdocument.forms[0].method.value='getConfigurationData'; //NO I18N\n\tdocument.forms[0].altattributeid.value='false'; //NO I18N\n\tdocument.forms[0].tblname.value=tblname; //NO I18N\n\tif(type=='gv')\n\t{\n\tdocument.forms[0].method.value='getGlobalViewData'; //NO I18N\n\tdocument.forms[0].reporttype.value='pdf'; //NO I18N\n\t    if(document.forms[0].selectedattrid.value !='')\n\t    {\n\t\t document.forms[0].attributeid.value=''; //NO I18N\n\t     document.forms[0].altattributeid.value='true'; //NO I18N\n\t\t}\n\t}\n    document.forms[0].businessPeriod.value=document.forms[2].businessPeriod.value;\n    document.forms[0].submit();\n\tdocument.forms[0].attributeid.value=temp; //NO I18N\n\tdocument.forms[0].selectedattrid.value=''; //NO I18N\n    document.forms[0].reporttype.value=\"html\"; //NO I18N\n");
/*  217 */       out.write("\n}\n\n\nfunction fnPeriod(periodform)\n {\n\tvar p = periodform.period.value;\n\tperd = p;\t\n\tif(p==4) //period  4  is meant for the custom period.\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='block'; //NO I18N\n\t\tdocument.getElementById(\"attrib_div1\").style.display='block'; //NO I18N\n\t\tdocument.getElementById(\"pdfdiv\").style.display='none';\n\t  \tdocument.getElementById(\"pdfdiv_g\").style.display='none';\n\t\talert(\"");
/*  218 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforcustomtime"));
/*  219 */       out.write("\")\n \t\treturn false\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"custPeriod\").style.display='none'; //NO I18N\n\t\tdocument.getElementById(\"attrib_div1\").style.display='block'; //NO I18N\n\t\tdocument.getElementById(\"pdfdiv\").style.display='block';\n\t  \tdocument.getElementById(\"pdfdiv_g\").style.display='none';\n\t}\n\tif(p==20)\n    {\n  \t   document.getElementById(\"pdfdiv\").style.display='none';\n  \t   document.getElementById(\"pdfdiv_g\").style.display='block';\n    }\n \tif(tab == 1)\n\t{\n  \t\tvar m = periodform.method.value;\n  \t\tvar r = periodform.resourceid.value;\n  \t\tvar c = periodform.childid.value;\n  \t\tvar n = periodform.resourcename.value;\n  \t\tvar a = periodform.attributeid.value;\n\t\tloadTabs(perd);\n\t\turl=\"/showConfigurationData.do?method=\"+m+\"&tblname=\"+tblname+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&businessPeriod=\"+bHr+\"&todaytime=\"+");
/*  220 */       out.print(System.currentTimeMillis());
/*  221 */       out.write(";   //NO I18N\n\t\t\n\t\t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getResp;\n    \t\thttp.send(null);\n      \t}\n\t\telse if (tab == 2)\n\t\t{\n\t\tgetGlobalData();\n\t\t}\n\n  }\n\n  function loadTabs(Period)\n  {\n\t\tif(Period==\"20\")\n\t\t{\n    \t\tdocument.getElementById(\"showAllTabs\").style.display='none';\n    \t\tdocument.getElementById(\"showOneTab\").style.display='block';\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"showAllTabs\").style.display='block';\n    \t\tdocument.getElementById(\"showOneTab\").style.display='none';\n\t\t}\n\t\t\n  }\n\n   function getResp()\n  {\n \tif(http.readyState == 4)\n \t{\n   \t\tvar result = http.responseText;\n    \t\tdocument.getElementById(\"regular\").innerHTML=result;\n    \t\tdocument.getElementById(\"regular\").style.display=\"block\";\n    \t\tdocument.getElementById(\"segmentByHour\").style.display=\"none\";\n\t\t\t//document.getElementById(\"prddiv\").style.display=\"block\";\n\t\t\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\n\t\tvar p=document.ReportForm[0].period.value;\n\t\tif(p==4){\n\t\t\tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n");
/*  222 */       out.write("\t         \tdocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n\t\t}else if(p==20)\n  \t  \t{\n  \t         \tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n  \t         \tdocument.getElementById(\"pdfdiv_g\").style.display=\"block\";\n  \t  \t}\n  \t \telse\n  \t  \t{ \t \t\n  \t \t\t\tdocument.getElementById(\"pdfdiv\").style.display=\"block\"; \t \t\n\t\t\tdocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n\t  \t}\n  \t\n    \t}\n  }\n\nfunction getResourceForSelectionType()\n{\nvar selecttionType=document.getElementsByName('selecttionType')[0].value;  //NO I18N\nif(selecttionType=='ALL')\n{\n toggleDiv('mg') //NO I18N\n}\nelse\n{\n toggleDiv('mg') //NO I18N\n}\n}\n var r=''; //NO I18N\n var sidcol=\"\"; //NO I18N\n  function collectvalue()\n  {\n  var selecttionType=document.getElementsByName('selecttionType')[0].value;  //NO I18N\n  var gid='';\n\tif(selecttionType=='GROUP')\n\t{\n\t\tgid=document.getElementsByName('selectedMG')[0].value;  //NO I18N\n\t}\n\n\tvar a=document.getElementsByName('relatedAttributes');\n    var tc = 0;\n\n\t   for (var i =0; i < a.length; i++)\n");
/*  223 */       out.write("        {\n        var sid=new String(a[i].id);\n        if(document.getElementById(sid).checked)\n\t\t{\n\t\t\tif(tc == 0) {\n\t\t\t\tsidcol = sid;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tsidcol += \", \" +sid; //NO I18N\n\t\t\t}\n\t\t\ttc++;   //NO I18N\n        }\n        }\n\t\tif(sidcol=='')\n\t\t{\n\t\talert(\"");
/*  224 */       out.print(FormatUtil.getString("am.javaruntime.errmsg1"));
/*  225 */       out.write("\"); //NO I18N\n\t\treturn;\n\t\t}\n\t\tvar test=document.getElementsByName('selectedMonitorType')[0].value; //NO I18N\n\n\n\n  \tvar len=document.ReportForm[0].period.length;  //NO I18N\n\tvar val=document.ReportForm[0].period.options[len-1].value; //NO I18N\n\tvar adminserver=");
/*  226 */       out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  227 */       out.write("\n\t\t//if(val!=\"20\" && !adminserver)\n\t\t//{\n\t\t//var list=document.ReportForm[0].period; //NO I18N\n\t\t//var newOption=document.createElement('option'); //NO I18N\n\t\t//newOption.text='");
/*  228 */       out.print(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*  229 */       out.write("'; //NO I18N\n        //newOption.value=\"20\";       //NO I18N\n        //list.add(newOption,len.value);\n\t    //}\n\t r = document.forms[0].resourceid.value; //NO I18N\n\t document.forms[0].selectedattrid.value=sidcol; //NO I18N\n\t document.forms[0].pdfresid.value=r; //NO I18N\n  \tvar c = ");
/*  230 */       out.print(request.getParameter("childid"));
/*  231 */       out.write("; //NO I18N\n  \tvar n = ''; //NO I18N\n  \tvar a = document.forms[0].attributeid.value;\t //NO I18N\n  \ttab = 1; //NO I18N\n\turl=\"/showConfigurationData.do?method=getGlobalViewData&tblname=\"+tblname+\"&altattributeid=true&reporttype=html&groupid=\"+gid+\"&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+test+\"&selectedattrid=\"+sidcol+\"&period=\"+perd+\"&frompop=true&tabsel=1&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  232 */       out.print(System.currentTimeMillis());
/*  233 */       out.write("; //NO I18N\n\ttry{\n     \t\thttp.open(\"GET\",url,true);\n    \t\thttp.onreadystatechange = getAttrListRespc;\n    \t\thttp.send(null);\n\t}\n\tcatch(e){}\n  }\n function getAttrListRespc()\n{\n\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\n\tdocument.getElementById(\"segmentByHour\").innerHTML=result;\n\tdocument.getElementById(\"regular\").style.display=\"none\";\n\tdocument.getElementById(\"header\").style.display=\"none\";\n\tdocument.getElementById(\"header_g\").style.display=\"block\";\n    document.getElementById(\"segmentByHour\").style.display=\"block\";\n    document.getElementById(\"EditDiv\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t//document.getElementById(\"prddiv\").style.display=\"none\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"pdfdiv_g\").style.display=\"block\";\n\n    }\n }\n\nfunction getAttrList(test)\n{\n\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n");
/*  234 */       out.write("\tvar adminserver=");
/*  235 */       out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  236 */       out.write("\n\t\t//if(val!=\"20\" && !adminserver)\n\t\t//{\n\t\t//var list=document.ReportForm[0].period;\n\t\t//var newOption=document.createElement('option');\n\t\t//newOption.text='");
/*  237 */       out.print(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*  238 */       out.write("'; //NO I18N\n        //newOption.value=\"20\";  //NO I18N\n        //list.add(newOption,len.value);\n\t    //}\n\tvar r = document.forms[0].resourceid.value;\n  \tvar c = ");
/*  239 */       out.print(request.getParameter("childid"));
/*  240 */       out.write(";\n  \tvar n = '';\n  \tvar a = document.forms[0].attributeid.value;\n  \ttab = 1; //NO I18N\n\turl=\"/showConfigurationData.do?method=getGlobalViewData&tblname=\"+tblname+\"&reporttype=html&altattributeid=false&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+test+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=1&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  241 */       out.print(System.currentTimeMillis());
/*  242 */       out.write("; //NO I18N\n\ttry{\n     \t\thttp.open(\"GET\",url,true); //NO I18N\n    \t\thttp.onreadystatechange = getAttrListResp; //NO I18N\n    \t\thttp.send(null);\n\t}\n\tcatch(e){}\n}\nfunction getAttrListResp()\n{\n\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\n\tdocument.getElementById(\"segmentByHour\").innerHTML=result;\n\tdocument.getElementById(\"regular\").style.display=\"none\";\n\tdocument.getElementById(\"header\").style.display=\"none\";\n\tdocument.getElementById(\"header_g\").style.display=\"block\";\n    document.getElementById(\"segmentByHour\").style.display=\"block\";\n    document.getElementById(\"EditDiv\").style.display=\"block\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t//document.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\tdocument.getElementById(\"pdfdiv\").style.display=\"block\";\n\tdocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n\n    }\n }\n\nfunction getRegularHistData()\n{\n\n\t//var len=document.getElementById(\"period\").options.length; //not working in firefox\n");
/*  243 */       out.write("\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n\t//alert(\"value==>\"+val);\n\tvar adminserver=");
/*  244 */       out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  245 */       out.write("\n\t//alert(adminserver);\n\t\t//if(val!=\"20\" && !adminserver)\n\t\t//{\n\t\t//alert(len);\n\t\t//var list=document.ReportForm[0].period;\n\t\t//var newOption=document.createElement('option');\n\t\t//newOption.text='");
/*  246 */       out.print(FormatUtil.getString("am.webclient.historydata.period.showpolleddata.text"));
/*  247 */       out.write("';\n        //newOption.value=\"20\";\n        //list.add(newOption,len.value);\n\t    //}\n\tvar r = document.forms[0].resourceid.value;\n\t//alert(\"RESID=\"+r);\n  \tvar c = ");
/*  248 */       out.print(request.getParameter("childid"));
/*  249 */       out.write(";\n\t//alert(\"CHILDID=\"+c);\n  \tvar n = '");
/*  250 */       out.print(request.getParameter("resourcename"));
/*  251 */       out.write("';\n\t//alert(\"RNAME=\"+n);\n  \tvar a = document.forms[0].attributeid.value;\n\t//alert(\"AID=\"+a);\n  \ttab = 1; //NO I18N\n  \tvar restype='");
/*  252 */       out.print(String.valueOf(request.getParameter("restype")));
/*  253 */       out.write("';\n\turl=\"/showConfigurationData.do?method=getConfigurationData&tblname=\"+tblname+\"&reporttype=html&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=1&businessPeriod=\"+bHr+\"&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  254 */       out.print(System.currentTimeMillis());
/*  255 */       out.write("+\"&restype=\"+restype; //NO I18N\n\ttry{\n     \t\thttp.open(\"GET\",url,true); //NO I18N\n    \t\thttp.onreadystatechange = getRegularDataResp; //NO I18N\n    \t\tif(perd==\"4\"){\n    \t\t\n    \t\t\t\tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n    \t         \tdocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n    \t\t\t\n    \t\t}\n    \t\thttp.send(null);\n\t}\n\tcatch(e){}\n}\n\nfunction getRegularDataResp()\n{\n\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\t//alert (result);\n\n\tdocument.getElementById(\"regular\").innerHTML=result;\n    document.getElementById(\"segmentByHour\").style.display=\"none\";\n\tdocument.getElementById(\"header\").style.display=\"block\";\n\tdocument.getElementById(\"header_g\").style.display=\"none\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t//document.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\tif(perd!=4){\n\t\tdocument.getElementById(\"pdfdiv\").style.display=\"block\";\n\t}else{\n\t\tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n");
/*  256 */       out.write("\t}\n\tdocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n\tdocument.getElementById(\"regular\").style.display=\"block\";\n\t\n    }\n }\nfunction getGlobalData()\n{\n\tvar len=document.ReportForm[0].period.length;\n\tvar val=document.ReportForm[0].period.options[len-1].value;\n\t\tif(val==\"20\")\n\t\t{\n\t\tvar list=document.ReportForm[0].period;\n\t\tlist.remove(len-1); //NO I18N\n     \t}\n  \tvar r = document.forms[0].resourceid.value;\n  \tvar c = ");
/*  257 */       out.print(request.getParameter("childid"));
/*  258 */       out.write("; //NO I18N\n  \tvar n = '");
/*  259 */       out.print(request.getParameter("resourcename"));
/*  260 */       out.write("'; //NO I18N\n\tdocument.forms[0].altattributeid.value='false'; //NO I18N\n  \tvar a = document.forms[0].attributeid.value;\n  \ttab = 2; //NO I18N\n  \tvar restype='");
/*  261 */       out.print(String.valueOf(request.getParameter("restype")));
/*  262 */       out.write("';\n\turl=\"/showConfigurationData.do?method=getGlobalViewData&tblname=\"+tblname+\"&reporttype=html&altattributeid=false&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+perd+\"&frompop=true&tabsel=2&startDate=\"+unescape(customstarttime)+\"&endDate=\"+unescape(customendtime)+\"&todaytime=\"+");
/*  263 */       out.print(System.currentTimeMillis());
/*  264 */       out.write("+\"&restype=\"+restype; //NO I18N\n\ttry{\n      http.open(\"GET\",url,true); //NO I18N\n    http.onreadystatechange = getSgmntByHrDataResp;//NO I18N\n    http.send(null);\n\t}\n\tcatch (e) {}\n}\nfunction getSgmntByHrDataResp()\n{\n if(http.readyState == 4)\n    {\n   \tvar result = http.responseText;\n\n    document.getElementById(\"segmentByHour\").innerHTML=result;\n    document.getElementById(\"regular\").style.display=\"none\";\n    document.getElementById(\"header\").style.display=\"none\";\n    document.getElementById(\"header_g\").style.display=\"block\";\n\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\tdocument.getElementById(\"bhrdiv\").style.display=\"none\";\n\tdocument.getElementById(\"pdfdiv\").style.display=\"none\";\n\tdocument.getElementById(\"pdfdiv_g\").style.display=\"block\";\n\t//document.getElementById(\"prddiv\").style.display=\"block\";\n\tdocument.getElementById(\"segmentByHour\").style.display=\"block\";\n    }\n }\n\nvar userThresh=0;\nfunction fnPer(periodcombo)\n\t{\n\tvar r = document.forms[0].resourceid.value;\n  \tvar c = '");
/*  265 */       out.print(request.getParameter("childid"));
/*  266 */       out.write("';\n  \tvar n = '");
/*  267 */       out.print(request.getParameter("resourcename"));
/*  268 */       out.write("';\n  \tvar a = document.forms[0].attributeid.value;\n\tvar p = periodcombo.value;\n\tif(userThresh != 1)\n\t{\n\turl=\"/showConfigurationData.do?method=getheatData&reporttype=html&tblname=\"+tblname+\"&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&tabsel=5&userThresh=0&todaytime=\"+");
/*  269 */       out.print(System.currentTimeMillis());
/*  270 */       out.write("; //NO I18N\n\t}\n\telse\n\t{\n\turl=\"/showConfigurationData.do?method=getheatData&reporttype=html&tblname=\"+tblname+\"&resourceid=\"+r+\"&childid=\"+c+\"&resourcename=\"+n+\"&attributeid=\"+a+\"&period=\"+p+\"&frompop=true&tabsel=5&userThresh=\"+userThresh+\"&isThresh=\"+isThreshconf+\"&threshC1=\"+threshC1+\"&threshV1=\"+threshV1+\"&threshC2=\"+threshC2+\"&threshV2=\"+threshV2+\"&threshC3=\"+threshC3+\"&threshV3=\"+threshV3+\"&todaytime=\"+");
/*  271 */       out.print(System.currentTimeMillis());
/*  272 */       out.write("; //NO I18N\n\t}\n\ttry{\n      http.open(\"GET\",url,true); //NO I18N\n    http.onreadystatechange = getHeatResp; //NO I18N\n    http.send(null);\n\t}\n\tcatch (e) {}\n\t}\n\n\nonload = function() //NO I18N\n{\nvar p= ");
/*  273 */       out.print(request.getParameter("period"));
/*  274 */       out.write("\nif(p == 4)\n{\ndocument.getElementById(\"custPeriod\").style.display='block';\ndocument.getElementById(\"attrib_div1\").style.display='none';\ndocument.getElementById(\"pdfdiv\").style.display=\"none\";\ndocument.getElementById(\"pdfdiv_g\").style.display=\"none\";\n}\nelse\n{\ndocument.getElementById(\"attrib_div1\").style.display='block';\n}\n}\n</script>\n</head>\n<body>\n\n\n");
/*      */       
/*  276 */       String resourceName = (String)pageContext.getAttribute("rname");
/*  277 */       String peri = request.getParameter("period");
/*  278 */       if (request.getAttribute("frompop") == null)
/*      */       {
/*  280 */         Properties pq = com.adventnet.appmanager.util.DBUtil.getRawValue();
/*  281 */         String RV = pq.getProperty("israw");
/*  282 */         String PV = pq.getProperty("rawvalue");
/*  283 */         String AID = request.getParameter("attributeid");
/*  284 */         String pageChk = request.getParameter("method");
/*  285 */         Boolean role = Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*      */         
/*  287 */         out.write("\n   <div id=\"dhtmltooltip\"></div>\n   <div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tblbordergray-report\" align=\"center\">\n  <tr>\n\t\t<td id='header_g' style=\"display:none;padding-top:5px;padding-left:5px\">\n\t\t\t<a href=\"#\" class=\"staticlinks\" onClick=\"toggleDiv('EditDiv')\">");
/*  288 */         out.print(FormatUtil.getString("am.javaruntime.customizecolumns"));
/*  289 */         out.write("</a>\n\t\t </td>\n\n\t\t<td width=\"50%\" id=\"header\" style=\"display:block\">\n\t\t\t\t\t<table width=\"100%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" style=\"padding-top:5px;padding-left:5px;\">\n\t\t\t\t\t\t\t\t");
/*      */         
/*  291 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  292 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  293 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  295 */         _jspx_th_html_005fform_005f0.setAction("/showConfigurationData.do");
/*      */         
/*  297 */         _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/*  298 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  299 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  301 */             out.write("\n\t\t\t\t\t\t\t\t");
/*      */             
/*  303 */             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  304 */             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  305 */             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  307 */             _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */             
/*  309 */             _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this.form)");
/*      */             
/*  311 */             _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/*  312 */             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  313 */             if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  314 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  315 */                 out = _jspx_page_context.pushBody();
/*  316 */                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  317 */                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  320 */                 out.write("\n\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  322 */                 OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  323 */                 _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  324 */                 _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  326 */                 _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                 
/*  328 */                 _jspx_th_html_005foption_005f0.setValue("4");
/*  329 */                 int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  330 */                 if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  331 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                 }
/*      */                 
/*  334 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*  335 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  337 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  338 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  339 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  341 */                 _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                 
/*  343 */                 _jspx_th_html_005foption_005f1.setValue("0");
/*  344 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  345 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  346 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/*  349 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  350 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  352 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  353 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  354 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  356 */                 _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                 
/*  358 */                 _jspx_th_html_005foption_005f2.setValue("3");
/*  359 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  360 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  361 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/*  364 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  365 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  367 */                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  368 */                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  369 */                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  371 */                 _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                 
/*  373 */                 _jspx_th_html_005foption_005f3.setValue("6");
/*  374 */                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  375 */                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  376 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                 }
/*      */                 
/*  379 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  380 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  382 */                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  383 */                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  384 */                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  386 */                 _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                 
/*  388 */                 _jspx_th_html_005foption_005f4.setValue("-7");
/*  389 */                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  390 */                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  391 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                 }
/*      */                 
/*  394 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  395 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  397 */                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  398 */                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  399 */                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  401 */                 _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                 
/*  403 */                 _jspx_th_html_005foption_005f5.setValue("12");
/*  404 */                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  405 */                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  406 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                 }
/*      */                 
/*  409 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  410 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  412 */                 OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  413 */                 _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  414 */                 _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  416 */                 _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                 
/*  418 */                 _jspx_th_html_005foption_005f6.setValue("7");
/*  419 */                 int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  420 */                 if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  421 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                 }
/*      */                 
/*  424 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  425 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  427 */                 OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  428 */                 _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  429 */                 _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  431 */                 _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                 
/*  433 */                 _jspx_th_html_005foption_005f7.setValue("-30");
/*  434 */                 int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  435 */                 if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  436 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                 }
/*      */                 
/*  439 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  440 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  442 */                 OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  443 */                 _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  444 */                 _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  446 */                 _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                 
/*  448 */                 _jspx_th_html_005foption_005f8.setValue("11");
/*  449 */                 int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  450 */                 if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  451 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                 }
/*      */                 
/*  454 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  455 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  457 */                 OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  458 */                 _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  459 */                 _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  461 */                 _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                 
/*  463 */                 _jspx_th_html_005foption_005f9.setValue("9");
/*  464 */                 int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  465 */                 if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  466 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                 }
/*      */                 
/*  469 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  470 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  472 */                 OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  473 */                 _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  474 */                 _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  476 */                 _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                 
/*  478 */                 _jspx_th_html_005foption_005f10.setValue("8");
/*  479 */                 int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  480 */                 if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  481 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                 }
/*      */                 
/*  484 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*  485 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                 
/*  487 */                 OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  488 */                 _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/*  489 */                 _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  491 */                 _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                 
/*  493 */                 _jspx_th_html_005foption_005f11.setValue("-5");
/*  494 */                 int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/*  495 */                 if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/*  496 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                 }
/*      */                 
/*  499 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/*  500 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*  501 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  502 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  505 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  506 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  509 */             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  510 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */             }
/*      */             
/*  513 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  514 */             out.write("\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"resourceid\" value='");
/*  515 */             out.print(request.getParameter("resourceid"));
/*  516 */             out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"childid\" value='");
/*  517 */             out.print(request.getParameter("childid"));
/*  518 */             out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"resourcename\" value='");
/*  519 */             out.print(request.getParameter("resourcename"));
/*  520 */             out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"attributeid\" value='");
/*  521 */             out.print(request.getParameter("attributeid"));
/*  522 */             out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"method\" value='");
/*  523 */             out.print(request.getParameter("method"));
/*  524 */             out.write(39);
/*  525 */             out.write(62);
/*  526 */             out.write(32);
/*  527 */             if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  529 */             out.write("\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"pdfresid\" value=''>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"tblname\" value=''>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"selectedattrid\" value=''>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"groupid\" value=''>\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"altattributeid\" value='false'>\n\t\t\t\t\t\t\t\t\t\t\t");
/*  530 */             if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  532 */             out.write("\n\t\t\t\t\t\t\t\t\t");
/*  533 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  534 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  538 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  539 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */         }
/*      */         
/*  542 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  543 */         out.write("\n\t\t\t\t\t\t\t   </td>\t\t\t\t\t\n\n\n\t\t\t\t\t\t\t\t\t");
/*  544 */         if (!pageChk.equals("getIndividualURLandCompareReportsData"))
/*      */         {
/*      */ 
/*  547 */           out.write("\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<td id='custPeriod' style=\"display:none;padding-top:5px;padding-left:5px;\">\n\t\t\t\t\t\t\t\t\t");
/*      */           
/*  549 */           FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  550 */           _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/*  551 */           _jspx_th_html_005fform_005f1.setParent(null);
/*      */           
/*  553 */           _jspx_th_html_005fform_005f1.setAction("/showConfigurationData.do?period=4");
/*  554 */           int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/*  555 */           if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */             for (;;) {
/*  557 */               out.write("\n\t\t\t\t\t\t\t\t\t\t<table width=\"99%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"24\" class=\"bodytext\">");
/*  558 */               out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  559 */               out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:5px\" height=\"40\" nowrap> ");
/*  560 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  562 */               out.write(" <a href=\"javascript:void(0)\" ><IMG  src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  563 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  564 */               out.write("\"></a>\n\t\t\t\t\t\t\t\t\t\t\t\t<SCRIPT type=text/javascript>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Calendar.setup({\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tinputField     :    \"start\",     // id of the input field\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tshowsTime      :    true,\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tbutton         :    \"startTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\talign          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tsingleClick    :    true\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\t\t\t\t </SCRIPT>\n\t\t\t\t\t\t\t\t\t\t\t\t </td>\n\n\t\t\t\t\t\t\t\t\t\t\t\t  <td  class=\"bodytext\" style=\"padding-left:5px;\">");
/*  565 */               out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  566 */               out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t  <td style=\"padding-left:5px\" height=\"39\" nowrap> ");
/*  567 */               if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  569 */               out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  570 */               out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  571 */               out.write("\"></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<SCRIPT type=text/javascript>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   Calendar.setup({\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tinputField     :    \"end\",     // id of the input field\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tshowsTime      :    true,\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tbutton         :    \"endTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\talign          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tsingleClick    :    true\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   });\n\t\t\t\t\t\t\t\t\t\t\t\t\t </SCRIPT>\n\t\t\t\t\t\t\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"resourceid\" value='");
/*  572 */               out.print(request.getParameter("resourceid"));
/*  573 */               out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"childid\" value='");
/*  574 */               out.print(request.getParameter("childid"));
/*  575 */               out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"resourcename\" value='");
/*  576 */               out.print(request.getParameter("resourcename"));
/*  577 */               out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"attributeid\" value='");
/*  578 */               out.print(request.getParameter("attributeid"));
/*  579 */               out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"method\" value=\"getConfigurationData\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"customstarttime\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t  <input type=\"hidden\" name=\"customendtime\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t  ");
/*  580 */               if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  582 */               out.write("\n\t\t\t\t\t\t\t\t\t\t<td height=\"32\" style=\"padding-left:5px;\"><input type=\"submit\" name=\"show\" value=\"");
/*  583 */               out.print(FormatUtil.getString("Go"));
/*  584 */               out.write("\" class=\"buttons btn_highlt\" onClick=\"return fnCheckCustomTime(this.form)\"></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t");
/*  585 */               if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                 return;
/*  587 */               out.write("\n\t\t\t\t\t\t\t\t");
/*  588 */               int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/*  589 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  593 */           if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/*  594 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */           }
/*      */           
/*  597 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1);
/*  598 */           out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t");
/*      */         }
/*  600 */         out.write("\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\n\t</td>\t\t\n\t");
/*      */         
/*  602 */         FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  603 */         _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/*  604 */         _jspx_th_html_005fform_005f2.setParent(null);
/*      */         
/*  606 */         _jspx_th_html_005fform_005f2.setAction("/showConfigurationData.do");
/*      */         
/*  608 */         _jspx_th_html_005fform_005f2.setStyle("display:inline");
/*  609 */         int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/*  610 */         if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */           for (;;) {
/*  612 */             out.write("\t\n\t<td width=\"49%\" align=\"right\">\n\t\t\t<table table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ><tr><td width=\"94%\">\n\t\t\t\t\t\t<div id='attrib_div1'>\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext\"  width=\"55%\"  align=\"right\" style=\"padding-top:5px;\">\n\n\t\t\t\t\t\t\t");
/*  613 */             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/*  614 */             out.write(": &nbsp;\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\"  align=\"right\" valign=\"top\" width=\"45%\" style=\"padding-top:5px;\">\n\t\t\t\t\t\t\t");
/*  615 */             if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */               return;
/*  617 */             out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</div></td>\n\t\t\t\t\t<td width=\"3%\" id='pdfdiv' style=\"display:block;padding-top:5px;\" >\n\t\t\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:generateReport('pdf')\"><img  align=\"absmiddle\"  src=\"images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" hspace=\"4\" vspace=\"4\" title=\"");
/*  618 */             out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  619 */             out.write("\"\\></a>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"3%\" id='pdfdiv_g' style=\"display:none\" >\n\t\t\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:generateReport('gv')\"><img  align=\"absmiddle\"  src=\"images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" hspace=\"4\"  vspace=\"4\"  title=\"");
/*  620 */             out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  621 */             out.write("\"\\></a>\n\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t</table>\n\t</td>\n\n\n\n\n\n\t<td width=\"0%\" style=\"display:none\">\n\t<div id='bhrdiv' style=\"display:none\">\n\t");
/*  622 */             if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */               return;
/*  624 */             out.write("\n\t</td>\n \t");
/*  625 */             int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/*  626 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  630 */         if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/*  631 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */         }
/*      */         
/*  634 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2);
/*  635 */         out.write("\n\n  </tr>\n\n\n</table>\n\n\n\n\n\n\n\n\n\n<br>\n<div id='showAllTabs' style=\"Display:block;padding-left:1%;\">\n");
/*  636 */         JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab,am.javaruntime.globalview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab,am.javaruntime.globalview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRegularHistData,getGlobalData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()), out, true);
/*  637 */         out.write("\n</div>\n<div id='showOneTab' style=\"Display:none\">\n");
/*  638 */         JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getRegularHistData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.historydatareport.history.tab", request.getCharacterEncoding()), out, true);
/*  639 */         out.write("\n</div>\n\n<br>\n<div id='regular'>\n");
/*      */       }
/*      */       
/*      */ 
/*  643 */       out.write(10);
/*      */       
/*  645 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  646 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  647 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  649 */       _jspx_th_c_005fif_005f0.setTest("${STATUS!='SUCCESS'}");
/*  650 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  651 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  653 */           out.write("\n\n<br>\n<br>\n<table align=\"center\" cellspacing=\"5\" width=\"98%\" align=\"center\">\n  <tr>\n    <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr>\n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/*  654 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */             return;
/*  656 */           out.write("</span></td>\n        </tr>\n        <tr>\n          <td align=\"right\" class=\"lightbg\"> <a href=\"http://");
/*  657 */           out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link"));
/*  658 */           out.write("#m4\" class=\"staticlinks\">\n            ");
/*  659 */           out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/*  660 */           out.write("\n            >></a></td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n\n");
/*  661 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  662 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  666 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  667 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  670 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  671 */         out.write(10);
/*      */         
/*  673 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  674 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  675 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/*  677 */         _jspx_th_c_005fif_005f1.setTest("${STATUS=='SUCCESS'}");
/*  678 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  679 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/*  681 */             out.write(10);
/*      */             
/*  683 */             com.adventnet.appmanager.struts.actions.ComparingSla cs = new com.adventnet.appmanager.struts.actions.ComparingSla();
/*  684 */             int sizeofseq = 3;
/*  685 */             String unit = (String)pageContext.findAttribute("unit");
/*  686 */             String period = request.getParameter("period");
/*  687 */             String valueforperiod = cs.getValueForPeriod(period);
/*      */             
/*  689 */             out.write(10);
/*  690 */             out.write(10);
/*  691 */             if ((period != null) && (period.equals("14"))) {
/*  692 */               out.write("\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"80%\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        ");
/*  693 */               if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/*  695 */               out.write(32);
/*  696 */               if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/*  698 */               out.write("\n        ");
/*  699 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  700 */               String resname = (String)request.getAttribute("resourcename");
/*  701 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/*  702 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/*      */               
/*      */ 
/*      */ 
/*  706 */               out.write("\n            <tr>\n          <td class=\"tableheadingbborder\">\n\n\t\t");
/*  707 */               out.print(FormatUtil.getString("webclient.performance.reports.header", new String[] { FormatUtil.getString(nameofmonitor), resname }));
/*  708 */               out.write("\n          </td>\n        </tr>\n        ");
/*  709 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/*  710 */               if (rid != -1) {
/*  711 */                 out.write("\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/*  712 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/*  713 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/*  714 */                 out.print(resname);
/*  715 */                 out.write("\n\t\t</td>\n              </tr>\n              <tr>\n                <td class=\"yellowgrayborderbr\">");
/*  716 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/*  717 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/*  718 */                 out.print(FormatUtil.getString(nameofmonitor));
/*  719 */                 out.write("\n                  ");
/*  720 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals(""))) {
/*  721 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*      */                 }
/*      */                 
/*  724 */                 out.write("\n                </td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborderbr\">");
/*  725 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  726 */                 out.write("\n                </td>\n                <td class=\"whitegrayborder\"> ");
/*  727 */                 out.print(startime);
/*  728 */                 out.write(" </td>\n              </tr>\n              <tr>\n                <td  class=\"yellowgrayborderbr\">");
/*  729 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  730 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/*  731 */                 out.print(endtime);
/*  732 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/*  734 */               out.write("\n          <tr>\n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\">\n                  ");
/*      */               
/*  736 */               if (request.getParameter("period").equals("-7"))
/*      */               {
/*      */ 
/*  739 */                 out.write("\n                  <img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  745 */                 out.write("\n                  <a href='../showConfigurationData.do?method=");
/*  746 */                 out.print(request.getParameter("method"));
/*  747 */                 out.write("&resourceid=");
/*  748 */                 out.print(request.getParameter("resourceid"));
/*  749 */                 out.write("&childid=");
/*  750 */                 out.print(request.getParameter("childid"));
/*  751 */                 out.write("&keyids=");
/*  752 */                 out.print(request.getParameter("keyids"));
/*  753 */                 out.write("&attributeid=");
/*  754 */                 out.print(request.getParameter("attributeid"));
/*  755 */                 out.write("&period=-7&resourcename=");
/*  756 */                 out.print(request.getParameter("resourcename"));
/*  757 */                 out.write("'><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  758 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/*  759 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/*  763 */               out.write("\n                </td>\n                <td width=\"4%\">\n                  ");
/*      */               
/*  765 */               if (request.getParameter("period").equals("-30"))
/*      */               {
/*      */ 
/*  768 */                 out.write("\n                  <img src=\"../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\">\n                  ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/*  774 */                 out.write("\n                  <a href='../showConfigurationData.do?method=");
/*  775 */                 out.print(request.getParameter("method"));
/*  776 */                 out.write("&resourceid=");
/*  777 */                 out.print(request.getParameter("resourceid"));
/*  778 */                 out.write("&childid=");
/*  779 */                 out.print(request.getParameter("childid"));
/*  780 */                 out.write("&keyids=");
/*  781 */                 out.print(request.getParameter("keyids"));
/*  782 */                 out.write("&attributeid=");
/*  783 */                 out.print(request.getParameter("attributeid"));
/*  784 */                 out.write("&period=-30&resourcename=");
/*  785 */                 out.print(request.getParameter("resourcename"));
/*  786 */                 out.write("'><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  787 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/*  788 */                 out.write("\"></a>\n                  ");
/*      */               }
/*      */               
/*      */ 
/*  792 */               out.write("\n                </td>\n              </tr>\n            </table></td>\n        </tr>\n     ");
/*  793 */               ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/*  794 */               if (rawvalues.size() > 0)
/*      */               {
/*      */ 
/*  797 */                 graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"));
/*      */               }
/*      */               
/*  800 */               String attUnit = "";
/*  801 */               if (!unit.equals(""))
/*      */               {
/*  803 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  808 */               out.write("\n\n        <tr align=\"left\">\n          <td width=\"100%\" height=\"50\">\n            ");
/*      */               
/*  810 */               String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */               
/*  812 */               out.write("\n         ");
/*      */               
/*  814 */               TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/*  815 */               _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/*  816 */               _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */               
/*  818 */               _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("graph");
/*      */               
/*  820 */               _jspx_th_awolf_005ftimechart_005f0.setWidth("600");
/*      */               
/*  822 */               _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*      */               
/*  824 */               _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */               
/*  826 */               _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */               
/*  828 */               _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(temp);
/*      */               
/*  830 */               _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */               
/*  832 */               _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/*  833 */               int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/*  834 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/*  835 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  836 */                   out = _jspx_page_context.pushBody();
/*  837 */                   _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/*  838 */                   _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  841 */                   out.write("\n            ");
/*  842 */                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/*  843 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  846 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  847 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  850 */               if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/*  851 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */               }
/*      */               
/*  854 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*  855 */               out.write("\n\n          </td>\n        </tr>\n        </table>\n        <br>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n <tr>\n    <td class=\"tableheading\"  width='100%' colspan='3'>");
/*  856 */               out.print(FormatUtil.getString(nameofmonitor));
/*  857 */               out.write(" </td>\n  </tr>\n\n  <tr>\n    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/*  858 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/*  859 */               out.write("</td>\n    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/*  860 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/*  861 */               out.write("</td>\n    <td width=\"25%\" class=\"columnheading\" align=\"center\">");
/*  862 */               out.print(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/*  863 */               out.write(" </td>\n\n  </tr>\n  ");
/*  864 */               for (int i = 0; i < rawvalues.size(); i++) {
/*  865 */                 Properties row = (Properties)rawvalues.get(i);
/*  866 */                 String bgcolor = "";
/*  867 */                 String selectedColor = "class=\"selectedborder\"";
/*  868 */                 if (i % 2 == 0)
/*      */                 {
/*  870 */                   bgcolor = "class=\"whitegrayborder\"";
/*      */                 }
/*      */                 else
/*      */                 {
/*  874 */                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                 }
/*      */                 
/*  877 */                 out.write("\n  <tr>\n\t");
/*      */                 
/*  879 */                 long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/*  880 */                 String resourcetype = String.valueOf(request.getAttribute("type"));
/*  881 */                 String VAL = null;
/*  882 */                 VAL = String.valueOf(row.get("VALUE"));
/*  883 */                 pageContext.setAttribute("date1", new Date(archivedTime));
/*  884 */                 out.write("\n\n                 <td ");
/*  885 */                 out.print(bgcolor);
/*  886 */                 out.write(" align=\"center\">");
/*  887 */                 if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                   return;
/*  889 */                 out.write("</td>\n    <td ");
/*  890 */                 out.print(bgcolor);
/*  891 */                 out.write(" align=\"center\">");
/*  892 */                 if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                   return;
/*  894 */                 out.write("</td>\n         <td ");
/*  895 */                 out.print(bgcolor);
/*  896 */                 out.write(" align=\"center\">");
/*  897 */                 out.print(VAL);
/*  898 */                 out.write("</td>  </tr>\n\n  ");
/*      */               }
/*  900 */               out.write("\n    </table>\n     <br>\n\n\n\n\n\n\n\n\n\n\n\n"); }
/*  901 */             if ((period != null) && (period.equals("20"))) {
/*  902 */               out.write("\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"80%\">\n\t    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t        ");
/*  903 */               if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/*  905 */               out.write(32);
/*  906 */               if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/*  908 */               out.write("\n\t        ");
/*  909 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  910 */               String resname = (String)request.getAttribute("resourcename");
/*  911 */               ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/*      */               
/*  913 */               Properties STime = (Properties)rawvalues.get(rawvalues.size() - 1);
/*  914 */               long startTime = ((Long)STime.get("COLLECTIONTIME")).longValue();
/*      */               
/*  916 */               Properties ETime = (Properties)rawvalues.get(0);
/*  917 */               long endTime = ((Long)ETime.get("COLLECTIONTIME")).longValue();
/*      */               
/*      */ 
/*  920 */               String startime = FormatUtil.formatDT(startTime + "");
/*  921 */               String endtime = FormatUtil.formatDT(endTime + "");
/*  922 */               String bHr = (String)request.getAttribute("bRuleName");
/*      */               
/*      */ 
/*      */ 
/*  926 */               out.write("\n\t          <tr>\n\t\t          <td class=\"tableheadingbborder\">\n\t\t\t\t\t");
/*      */               
/*  928 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  929 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  930 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f1);
/*  931 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  932 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/*  934 */                   out.write("\n\t\t\t\t   \t\t");
/*      */                   
/*  936 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  937 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  938 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                   
/*  940 */                   _jspx_th_c_005fwhen_005f2.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/*  941 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  942 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                     for (;;) {
/*  944 */                       out.write("\n\t\t\t\t\t\t\t");
/*  945 */                       out.print(FormatUtil.getString("am.webclient.historydatareport.polledDataheading.text", new String[] { FormatUtil.getString(nameofmonitor), startime, endtime }));
/*  946 */                       out.write("\n\t\t\t\t   \t\t");
/*  947 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  948 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  952 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  953 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                   }
/*      */                   
/*  956 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  957 */                   out.write("\n\t\t\t\t   \t\t");
/*      */                   
/*  959 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  960 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  961 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  962 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  963 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/*  965 */                       out.write("\n\t\t\t\t   \t \t\t");
/*  966 */                       out.print(FormatUtil.getString("am.webclient.historydatareport.polledData.bHrheading.text", new String[] { FormatUtil.getString(nameofmonitor), bHr }));
/*  967 */                       out.write("\n\t\t\t\t   \t\t");
/*  968 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  969 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  973 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  974 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/*  977 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  978 */                   out.write("\n\t\t\t\t\t");
/*  979 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  980 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  984 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  985 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/*  988 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  989 */               out.write("\n\t\t          </td>\n        \t</tr>\n\t        ");
/*  990 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/*  991 */               if (rid != -1)
/*      */               {
/*  993 */                 String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + rid + "&period=0&Report=true&resourceType=Monitors&resid=" + rid;
/*  994 */                 java.util.List allSecondLevelAttribute = com.adventnet.appmanager.util.ReportUtil.getAllSecondLevelAttribute();
/*      */                 
/*  996 */                 out.write("\n\t\t        <tr>\n\t\t          <td>\n\t\t\t          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"whitegrayborderbr\">");
/*  997 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/*  998 */                 out.write("</td>\n\t\t\t                <td width=\"78%\" class=\"whitegrayborder\">");
/*  999 */                 out.print(resname);
/* 1000 */                 out.write("\n\t\t\t\t\t");
/*      */                 
/* 1002 */                 if ((allSecondLevelAttribute != null) && (!allSecondLevelAttribute.contains(request.getParameter("attributeid"))))
/*      */                 {
/*      */ 
/* 1005 */                   out.write("\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 1006 */                   out.print(glanceUrl);
/* 1007 */                   out.write("')\"><img  align=\"absmiddle\"  src=\"images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 1008 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1009 */                   out.write("\" hspace=\"4\" title=\"");
/* 1010 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 1011 */                   out.write("\"></a>\n\t\t\t\t\t");
/*      */                 }
/*      */                 
/* 1014 */                 out.write("\n\t\t\t\t\t</td>\n\t\t\t              </tr>\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"yellowgrayborderbr\">");
/* 1015 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 1016 */                 out.write(" </td>\n\t\t\t                <td width=\"78%\" class=\"yellowgrayborder\">");
/* 1017 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1018 */                 out.write("\n\n\t\t\t                  ");
/* 1019 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*      */                 {
/* 1021 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*      */                 }
/* 1023 */                 out.write("\n\t\t\t                </td>\n\t\t\t              </tr>\n\n\t\t\t              <tr>\n\t\t\t                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1024 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1025 */                 out.write("\n\t\t\t                </td>\n\t\t\t                <td width=\"78%\" class=\"whitegrayborder\"> ");
/* 1026 */                 out.print(startime);
/* 1027 */                 out.write(" </td>\n\t\t\t              </tr>\n\t\t\t              <tr>\n\t\t\t                <td  width=\"22%\" class=\"yellowgrayborderbr\">");
/* 1028 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1029 */                 out.write("\n\t\t\t                </td>\n\t\t\t                <td width=\"78%\" class=\"yellowgrayborder\">");
/* 1030 */                 out.print(endtime);
/* 1031 */                 out.write("</td>\n\t\t\t              </tr>\n\n\t\t\t         </table>\n\t\t\t      </td>\n\t\t        </tr>\n         \t\t  ");
/*      */                 
/* 1033 */                 if (rawvalues.size() > 0)
/*      */                 {
/* 1035 */                   String bhr = FormatUtil.getString((String)pageContext.findAttribute("businessPeriod"));
/*      */                   
/* 1037 */                   graph.setParam(request.getParameter("resourceid"), request.getParameter("attributeid"), request.getParameter("period"), bhr);
/*      */                 }
/*      */                 
/* 1040 */                 String attUnit = "";
/* 1041 */                 if (!unit.equals(""))
/*      */                 {
/* 1043 */                   attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */                 }
/*      */                 
/*      */ 
/* 1047 */                 out.write("\n\t              <tr align=\"left\">\n\t\t\t          <td width=\"100%\" height=\"50\">\n\t\t\t            ");
/*      */                 
/* 1049 */                 String temp = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + FormatUtil.getString(unit) + "     ";
/*      */                 
/* 1051 */                 out.write("\n\t\t\t         \t");
/*      */                 
/* 1053 */                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 1054 */                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 1055 */                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f1);
/*      */                 
/* 1057 */                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("graph");
/*      */                 
/* 1059 */                 _jspx_th_awolf_005ftimechart_005f1.setWidth("600");
/*      */                 
/* 1061 */                 _jspx_th_awolf_005ftimechart_005f1.setHeight("200");
/*      */                 
/* 1063 */                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                 
/* 1065 */                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                 
/* 1067 */                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(temp);
/*      */                 
/* 1069 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomDateAxis(true);
/*      */                 
/* 1071 */                 _jspx_th_awolf_005ftimechart_005f1.setCustomAngle(270.0D);
/* 1072 */                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 1073 */                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 1074 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1075 */                     out = _jspx_page_context.pushBody();
/* 1076 */                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 1077 */                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1080 */                     out.write("\n\t\t\t            ");
/* 1081 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 1082 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1085 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 1086 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1089 */                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 1090 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                 }
/*      */                 
/* 1093 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 1094 */                 out.write("\n\t\t\t          </td>\n\t\t\t      </tr>\n\t\t        <tr>\n\t\t        <br>\n\t\t        \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t\t\t\t\t\t <tr>\n\t\t\t\t\t\t    <td class=\"tableheading\"  width='100%' colspan='3'>");
/* 1095 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1096 */                 out.write(" </td>\n\t\t\t\t\t\t  </tr>\n\n\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t    <td width=\"17%\" class=\"columnheading\" align=\"center\">");
/* 1097 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1098 */                 out.write("</td>\n\t\t\t\t\t\t    <td width=\"14%\" class=\"columnheading\" align=\"center\">");
/* 1099 */                 out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1100 */                 out.write("</td>\n\n\t\t\t\t\t\t    ");
/* 1101 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*      */                 {
/* 1103 */                   out.write("\n\t\t \t\t\t\t\t\t\t<td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1104 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.value.text"));
/* 1105 */                   out.write("\n\t\t\t \t\t\t\t\t\t\t ");
/* 1106 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 1107 */                   out.write("\n\t\t\t \t\t\t\t\t\t </td>\n\t\t\t \t\t\t\t\t");
/*      */                 } else {
/* 1109 */                   out.write("\n\t\t\t \t\t\t\t\t\t<td width=\"25%\" class=\"columnheading\" align=\"center\">");
/* 1110 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.value.text"));
/* 1111 */                   out.write(" </td>\n\t\t\t \t\t\t\t\t\t");
/*      */                 }
/* 1113 */                 out.write("\n\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t  ");
/* 1114 */                 for (int i = 0; i < rawvalues.size(); i++) {
/* 1115 */                   Properties row = (Properties)rawvalues.get(i);
/* 1116 */                   String bgcolor = "";
/* 1117 */                   String selectedColor = "class=\"selectedborder\"";
/* 1118 */                   if (i % 2 == 0)
/*      */                   {
/* 1120 */                     bgcolor = "class=\"whitegrayborder\"";
/*      */                   }
/*      */                   else
/*      */                   {
/* 1124 */                     bgcolor = "class=\"yellowgrayborder\"";
/*      */                   }
/*      */                   
/* 1127 */                   out.write("\n\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t");
/*      */                   
/* 1129 */                   long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/* 1130 */                   String resourcetype = String.valueOf(request.getAttribute("type"));
/* 1131 */                   String VAL = null;
/* 1132 */                   VAL = String.valueOf(row.get("VALUE"));
/* 1133 */                   if (Integer.parseInt(VAL) < 0)
/*      */                   {
/* 1135 */                     VAL = "-";
/*      */                   }
/* 1137 */                   pageContext.setAttribute("date1", new Date(archivedTime));
/* 1138 */                   out.write("\n\n\t\t\t\t\t\t                 <td ");
/* 1139 */                   out.print(bgcolor);
/* 1140 */                   out.write(" align=\"center\">");
/* 1141 */                   if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                     return;
/* 1143 */                   out.write("</td>\n\t\t\t\t\t\t    <td ");
/* 1144 */                   out.print(bgcolor);
/* 1145 */                   out.write(" align=\"center\">");
/* 1146 */                   if (_jspx_meth_fmt_005fformatDate_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                     return;
/* 1148 */                   out.write("</td>\n\t\t\t\t\t\t         <td ");
/* 1149 */                   out.print(bgcolor);
/* 1150 */                   out.write(" align=\"center\">");
/* 1151 */                   out.print(VAL);
/* 1152 */                   out.write("</td>  </tr>\n\n\t\t\t\t\t\t  ");
/*      */                 }
/* 1154 */                 out.write("\n\t\t\t\t\t\t    </table>\n\t\t        </tr>\n\t        ");
/*      */               }
/* 1156 */               out.write("\n\t      </table>\n\t  </td>\n\t </tr>\n</table>\n");
/*      */             } else {
/* 1158 */               out.write("\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n  <tr>\n    <td width=\"80%\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        ");
/* 1159 */               if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/* 1161 */               out.write(32);
/* 1162 */               if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                 return;
/* 1164 */               out.write("\n           ");
/*      */               
/* 1166 */               String nameofmonitor = (String)pageContext.getAttribute("rname");
/* 1167 */               String resname = (String)request.getAttribute("resourcename");
/* 1168 */               String start_time = String.valueOf(((Date)request.getAttribute("starttime")).getTime());
/* 1169 */               String end_time = String.valueOf(((Date)request.getAttribute("endtime")).getTime());
/* 1170 */               String startime = FormatUtil.formatDT(((Date)request.getAttribute("starttime")).getTime() + "");
/* 1171 */               String endtime = FormatUtil.formatDT(((Date)request.getAttribute("endtime")).getTime() + "");
/* 1172 */               String bHr = (String)request.getAttribute("bRuleName");
/*      */               
/* 1174 */               out.write("\n          ");
/*      */               
/* 1176 */               if (request.getParameter("period").equals("4"))
/*      */               {
/*      */ 
/* 1179 */                 out.write("\n        <tr>\n          <td class=\"tableheadingbborder\">\n\n\t");
/*      */                 
/* 1181 */                 ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1182 */                 _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1183 */                 _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f1);
/* 1184 */                 int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1185 */                 if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                   for (;;) {
/* 1187 */                     out.write("\n   \t\t");
/*      */                     
/* 1189 */                     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1190 */                     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1191 */                     _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                     
/* 1193 */                     _jspx_th_c_005fwhen_005f3.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1194 */                     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1195 */                     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                       for (;;) {
/* 1197 */                         out.write("\n\t\t\t");
/* 1198 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 1199 */                         out.write("\n   \t\t");
/* 1200 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1201 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1205 */                     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1206 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                     }
/*      */                     
/* 1209 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1210 */                     out.write("\n   \t\t");
/*      */                     
/* 1212 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1213 */                     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1214 */                     _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1215 */                     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1216 */                     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                       for (;;) {
/* 1218 */                         out.write("\n   \t \t\t");
/* 1219 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }), bHr }));
/* 1220 */                         out.write("\n   \t\t");
/* 1221 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1222 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1226 */                     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1227 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                     }
/*      */                     
/* 1230 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1231 */                     out.write(10);
/* 1232 */                     out.write(9);
/* 1233 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1234 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1238 */                 if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1239 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                 }
/*      */                 
/* 1242 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1243 */                 out.write("\n          </td>\n        </tr>\n        ");
/*      */               }
/*      */               else {
/* 1246 */                 out.write("\n        <tr>\n          <td class=\"tableheadingbborder\">\n\t");
/*      */                 
/* 1248 */                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1249 */                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1250 */                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f1);
/* 1251 */                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1252 */                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                   for (;;) {
/* 1254 */                     out.write("\n   \t\t");
/*      */                     
/* 1256 */                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1257 */                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1258 */                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                     
/* 1260 */                     _jspx_th_c_005fwhen_005f4.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 1261 */                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1262 */                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                       for (;;) {
/* 1264 */                         out.write("\n\t\t\t");
/* 1265 */                         out.print(FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 1266 */                         out.write("\n   \t\t");
/* 1267 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1268 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1272 */                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1273 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                     }
/*      */                     
/* 1276 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1277 */                     out.write("\n   \t\t");
/*      */                     
/* 1279 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1280 */                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1281 */                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1282 */                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1283 */                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                       for (;;) {
/* 1285 */                         out.write("\n   \t \t\t");
/* 1286 */                         out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }), bHr }));
/* 1287 */                         out.write("\n   \t\t");
/* 1288 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1289 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1293 */                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1294 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                     }
/*      */                     
/* 1297 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1298 */                     out.write(10);
/* 1299 */                     out.write(9);
/* 1300 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1301 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1305 */                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1306 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                 }
/*      */                 
/* 1309 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1310 */                 out.write("\n          </td>\n        </tr>\n        ");
/*      */               }
/* 1312 */               int rid = Integer.parseInt(request.getParameter("resourceid"));
/* 1313 */               if (rid != -1) {
/* 1314 */                 String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + rid + "&period=0&Report=true&resourceType=Monitors&resid=" + rid;
/* 1315 */                 java.util.List allSecondLevelAttribute = com.adventnet.appmanager.util.ReportUtil.getAllSecondLevelAttribute();
/*      */                 
/* 1317 */                 out.write("\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 1318 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 1319 */                 out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\">");
/* 1320 */                 out.print(resname);
/* 1321 */                 out.write("\n\t\t</td>\n              </tr>\n              <tr>\n                <td class=\"yellowgrayborderbr\">");
/* 1322 */                 out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 1323 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1324 */                 out.print(FormatUtil.getString(nameofmonitor));
/* 1325 */                 out.write("\n                  ");
/* 1326 */                 if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/* 1327 */                   out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 1328 */                 out.write("\n                </td>\n              </tr>\n              <tr>\n                <td class=\"whitegrayborderbr\">");
/* 1329 */                 out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1330 */                 out.write("\n                </td>\n                <td class=\"whitegrayborder\"> ");
/* 1331 */                 out.print(startime);
/* 1332 */                 out.write(" </td>\n              </tr>\n              <tr>\n                <td  class=\"yellowgrayborderbr\">");
/* 1333 */                 out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1334 */                 out.write("\n                </td>\n                <td class=\"yellowgrayborder\">");
/* 1335 */                 out.print(endtime);
/* 1336 */                 out.write("</td>\n              </tr>\n            </table></td>\n        </tr>\n        ");
/*      */               }
/* 1338 */               out.write("\n\n            </table></td>\n        </tr>\n        ");
/*      */               
/* 1340 */               ArrayList pdfdata = (ArrayList)request.getAttribute("pdfdata");
/* 1341 */               ArrayList list = (ArrayList)request.getAttribute("list");
/*      */               
/* 1343 */               if (list != null)
/*      */               {
/* 1345 */                 sizeofseq = list.size();
/*      */               }
/* 1347 */               String bRule = (String)request.getAttribute("businessPeriod");
/* 1348 */               String ShowData = (String)request.getAttribute("CompareUrl");
/*      */               
/* 1350 */               if ((list != null) && (list.size() > 0))
/*      */               {
/* 1352 */                 pageContext.setAttribute("urlseqs", list);
/*      */               }
/*      */               
/* 1355 */               String attUnit = "";
/* 1356 */               if (!unit.equals(""))
/*      */               {
/* 1358 */                 attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*      */               }
/*      */               
/* 1361 */               out.write("\n\n     </table></td>\n  </tr>\n</table>\n\n\n\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align=\"center\">\n <tr>\n    <td class=\"tableheadingbborder\"  width='100%' colspan='");
/* 1362 */               out.print(sizeofseq + 4);
/* 1363 */               out.write(39);
/* 1364 */               out.write(62);
/* 1365 */               out.print(FormatUtil.getString(nameofmonitor));
/* 1366 */               out.write(" </td>\n  </tr>\n  <tr>\n    <td width=\"20%\" class=\"columnheading\" align=\"left\">");
/* 1367 */               out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 1368 */               out.write(32);
/* 1369 */               out.write(38);
/* 1370 */               out.write(32);
/* 1371 */               out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 1372 */               out.write("</td>\n    <td width=\"50%\" class=\"columnheading\" align=\"left\">");
/* 1373 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.value"));
/* 1374 */               out.write(32);
/* 1375 */               out.print(attUnit);
/* 1376 */               out.write("</td>\n    <td width=\"30%\" class=\"columnheading\" align=\"left\" style=\"padding-left:5px\">");
/* 1377 */               out.print(FormatUtil.getString("am.webclient.historydata.downtime.reason.text"));
/* 1378 */               out.write("</td>\n  </tr>\n  ");
/*      */               
/* 1380 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 1381 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 1382 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */               
/* 1384 */               _jspx_th_logic_005fiterate_005f0.setName("data");
/*      */               
/* 1386 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/* 1388 */               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */               
/* 1390 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.Properties");
/* 1391 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 1392 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 1393 */                 Properties row = null;
/* 1394 */                 Integer i = null;
/* 1395 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 1396 */                   out = _jspx_page_context.pushBody();
/* 1397 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 1398 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/* 1400 */                 row = (Properties)_jspx_page_context.findAttribute("row");
/* 1401 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                 for (;;) {
/* 1403 */                   out.write("\n\t  ");
/*      */                   
/* 1405 */                   String bgcolor = "";
/* 1406 */                   String selectedColor = "class=\"selectedborder\"";
/* 1407 */                   if (i.intValue() % 2 == 0) {
/* 1408 */                     bgcolor = "class=\"whitegrayborder\"";
/*      */                   } else {
/* 1410 */                     bgcolor = "class=\"yellowgrayborder\"";
/*      */                   }
/*      */                   
/* 1413 */                   out.write("\n   <tr>\n\t");
/*      */                   
/* 1415 */                   long archivedTime = ((Long)row.get("COLLECTIONTIME")).longValue();
/* 1416 */                   String resourcetype = String.valueOf(request.getAttribute("type"));
/* 1417 */                   String minVal = null;
/* 1418 */                   String reason = (String)row.get("REASON");
/* 1419 */                   minVal = (String)row.get("CONFVALUE");
/* 1420 */                   pageContext.setAttribute("date", new Date(archivedTime));
/* 1421 */                   pageContext.setAttribute("minVal", minVal);
/*      */                   
/* 1423 */                   out.write("\n    <td ");
/* 1424 */                   out.print(bgcolor);
/* 1425 */                   out.write(" align=\"left\">");
/* 1426 */                   if (_jspx_meth_fmt_005fformatDate_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 1428 */                   out.write(32);
/* 1429 */                   if (_jspx_meth_fmt_005fformatDate_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/* 1431 */                   out.write("</td>\n    <td ");
/* 1432 */                   out.print(bgcolor);
/* 1433 */                   out.write(" align=\"left\">");
/* 1434 */                   out.print(minVal);
/* 1435 */                   out.write("</td>\n\t<td ");
/* 1436 */                   out.print(bgcolor);
/* 1437 */                   out.write(" align=\"left\" >\n\n\t     <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n          <tr>\n\t\t\t <td width=\"5%\">\n\t\t\t\t\t<a style=\"cursor:pointer;\">\n\t\t\t\t\t\t  <img src=\"../images/editWidget.gif\" width=\"12\" height=\"12\" hspace=\"5\" border=\"0\" title=\"");
/* 1438 */                   out.print(FormatUtil.getString("Edit"));
/* 1439 */                   out.write("\" onClick=\"javascript:addNote('textNote");
/* 1440 */                   out.print(i);
/* 1441 */                   out.write("','divNote");
/* 1442 */                   out.print(i);
/* 1443 */                   out.write("','divImage");
/* 1444 */                   out.print(i);
/* 1445 */                   out.write("')\">\n\t\t\t\t\t </a>\n\t\t\t </td>\n\t\t\t <td width=\"60%\">\n\n\n\t\t\t\t");
/*      */                   
/* 1447 */                   if (!reason.equals("-"))
/*      */                   {
/*      */ 
/* 1450 */                     out.write("\n\t\t \t\t <div id=\"divNote");
/* 1451 */                     out.print(i);
/* 1452 */                     out.write("\" style=\"display:block; padding: 2px;\">\n\t\t\t\t <input id=\"textNote");
/* 1453 */                     out.print(i);
/* 1454 */                     out.write("\" style=\"border: 1px solid #BCC0C2;\" size=\"40\" type=\"text\" value=\"");
/* 1455 */                     out.print(reason);
/* 1456 */                     out.write("\" onMouseOver=\"ddrivetip(this,event,'");
/* 1457 */                     out.print(reason);
/* 1458 */                     out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\" class=\"formtext medium\">\n\t\t\t\t <input type=\"hidden\" id=\"textReason");
/* 1459 */                     out.print(i);
/* 1460 */                     out.write("\" size=\"1\"  value=\" \">\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                   }
/*      */                   else {
/* 1463 */                     out.write("\n\t\t\t\t <div id=\"divNote");
/* 1464 */                     out.print(i);
/* 1465 */                     out.write("\" style=\"display:none; padding: 2px;\">\n\t\t\t\t <input id=\"textNote");
/* 1466 */                     out.print(i);
/* 1467 */                     out.write("\" disabled=\"true\" size=\"40\" type=\"text\" value=\"\" class=\"formtext medium\">\n\t\t\t\t <input type=\"hidden\" id=\"textReason");
/* 1468 */                     out.print(i);
/* 1469 */                     out.write("\" size=\"1\"  value=\" \">\n\t\t\t\t</div>\n\n\t\t\t\t");
/*      */                   }
/* 1471 */                   out.write("\n\n\n\n\t\t\t </td>\n\t\t\t <td width=\"10%\">\n\t\t\t\t<div id=\"divImage");
/* 1472 */                   out.print(i);
/* 1473 */                   out.write("\" style=\"display:none; padding: 1px;\">\n\t\t\t\t  <a style=\"cursor:pointer;\">\n\t\t\t\t\t<img src=\"../images/save.gif\" width=\"12\" height=\"12\" hspace=\"0\" border=\"0\" title=\"");
/* 1474 */                   out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 1475 */                   out.write("\" onClick=\"javascript:insertDowntimeReason('");
/* 1476 */                   out.print(archivedTime);
/* 1477 */                   out.write("','textNote");
/* 1478 */                   out.print(i);
/* 1479 */                   out.write("','divImage");
/* 1480 */                   out.print(i);
/* 1481 */                   out.write("','textReason");
/* 1482 */                   out.print(i);
/* 1483 */                   out.write("')\">\n\t\t\t\t  </a>\n\t\t\t\t  <a style=\"cursor:pointer;\">\n\t\t\t\t\t<img src=\"../images/cross.gif\" width=\"12\" height=\"12\" hspace=\"0\" border=\"0\" title=\"");
/* 1484 */                   out.print(FormatUtil.getString("Cancel"));
/* 1485 */                   out.write("\" onClick=\"javascript:cancelDowntimeReason('");
/* 1486 */                   out.print(archivedTime);
/* 1487 */                   out.write("','textNote");
/* 1488 */                   out.print(i);
/* 1489 */                   out.write("','divImage");
/* 1490 */                   out.print(i);
/* 1491 */                   out.write("','textReason");
/* 1492 */                   out.print(i);
/* 1493 */                   out.write("')\">\n\t\t\t\t  </a>\n\t\t\t\t</div>\n\t\t\t </td>\n\t        </tr>\n\t\t  </table>\n\n\n  ");
/* 1494 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 1495 */                   row = (Properties)_jspx_page_context.findAttribute("row");
/* 1496 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 1497 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1500 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 1501 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1504 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 1505 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/* 1508 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 1509 */               out.write("\n</table>\n\n");
/*      */             }
/*      */             
/*      */ 
/* 1513 */             out.write(10);
/* 1514 */             out.write(10);
/* 1515 */             response.setContentType("text/html;charset=UTF-8");
/* 1516 */             out.write(10);
/* 1517 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1518 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1522 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1523 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */         }
/*      */         else {
/* 1526 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1527 */           out.write("\n</div>\n");
/*      */           
/* 1529 */           if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*      */           {
/*      */ 
/* 1532 */             out.write("\n<script type=\"text/javascript\">\n\tvar _gaq = _gaq || [];\t\t\t\t\t\t\t//NO I18N\n\t_gaq.push(['_setAccount', 'UA-202658-68']);\t\t//NO I18N\n\t_gaq.push(['_trackPageview']);\t\t\t\t\t//NO I18N\n\n\t(function() {\n\tvar ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\t//NO I18N\n\tga.src = '//www.google-analytics.com/ga.js';\t//NO I18N\n\t\n\tvar s = document.getElementsByTagName('script')[0]; \t//NO I18N\n\ts.parentNode.insertBefore(ga, s);\t\t\t\t\t\t//NO I18N\n\t})();\n</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 1536 */           out.write(10);
/*      */           
/* 1538 */           if (request.getAttribute("frompop") == null)
/*      */           {
/*      */ 
/* 1541 */             out.write("\n<div id=\"segmentByHour\">\n</div>\n");
/*      */           }
/*      */           
/*      */ 
/* 1545 */           out.write("\n</body>\n</html>\n\n");
/*      */         }
/* 1547 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1548 */         out = _jspx_out;
/* 1549 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1550 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1551 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1554 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1560 */     PageContext pageContext = _jspx_page_context;
/* 1561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1563 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1564 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1565 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1567 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1569 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1570 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1571 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1572 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1573 */       return true;
/*      */     }
/* 1575 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1581 */     PageContext pageContext = _jspx_page_context;
/* 1582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1584 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1585 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 1586 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1588 */     _jspx_th_html_005fhidden_005f0.setProperty("reporttype");
/*      */     
/* 1590 */     _jspx_th_html_005fhidden_005f0.setValue("html");
/* 1591 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 1592 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 1593 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1594 */       return true;
/*      */     }
/* 1596 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1602 */     PageContext pageContext = _jspx_page_context;
/* 1603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1605 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1606 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 1607 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1609 */     _jspx_th_html_005fhidden_005f1.setProperty("businessPeriod");
/*      */     
/* 1611 */     _jspx_th_html_005fhidden_005f1.setValue("oni");
/* 1612 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 1613 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 1614 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1615 */       return true;
/*      */     }
/* 1617 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1623 */     PageContext pageContext = _jspx_page_context;
/* 1624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1626 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1627 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1628 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 1629 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1630 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 1632 */         out.write(32);
/* 1633 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1634 */           return true;
/* 1635 */         out.write(32);
/* 1636 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 1637 */           return true;
/* 1638 */         out.write(32);
/* 1639 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1640 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1644 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1645 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1646 */       return true;
/*      */     }
/* 1648 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1654 */     PageContext pageContext = _jspx_page_context;
/* 1655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1657 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1658 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1659 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1661 */     _jspx_th_c_005fwhen_005f0.setTest("${'1' != '1'}");
/* 1662 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1663 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 1665 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1666 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 1667 */           return true;
/* 1668 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1669 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1670 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1674 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1675 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1676 */       return true;
/*      */     }
/* 1678 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1684 */     PageContext pageContext = _jspx_page_context;
/* 1685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1687 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1688 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 1689 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1691 */     _jspx_th_html_005ftext_005f0.setSize("14");
/*      */     
/* 1693 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 1695 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 1697 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 1699 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 1701 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtextselected");
/* 1702 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 1703 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 1704 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1705 */       return true;
/*      */     }
/* 1707 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1713 */     PageContext pageContext = _jspx_page_context;
/* 1714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1716 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1717 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1718 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1719 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1720 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1722 */         out.write(32);
/* 1723 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1724 */           return true;
/* 1725 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t ");
/* 1726 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1727 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1731 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1732 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1733 */       return true;
/*      */     }
/* 1735 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1741 */     PageContext pageContext = _jspx_page_context;
/* 1742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1744 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1745 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1746 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1748 */     _jspx_th_html_005ftext_005f1.setSize("13");
/*      */     
/* 1750 */     _jspx_th_html_005ftext_005f1.setProperty("startDate");
/*      */     
/* 1752 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 1754 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 1756 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetStartTime(this.value)");
/* 1757 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1758 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1759 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1760 */       return true;
/*      */     }
/* 1762 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1768 */     PageContext pageContext = _jspx_page_context;
/* 1769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1771 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1772 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1773 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 1774 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1775 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1777 */         out.write(32);
/* 1778 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1779 */           return true;
/* 1780 */         out.write(32);
/* 1781 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1782 */           return true;
/* 1783 */         out.write(32);
/* 1784 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1785 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1789 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1790 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1791 */       return true;
/*      */     }
/* 1793 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1799 */     PageContext pageContext = _jspx_page_context;
/* 1800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1802 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1803 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1804 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1806 */     _jspx_th_c_005fwhen_005f1.setTest("${'1'!= '1'}");
/* 1807 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1808 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1810 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1811 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1812 */           return true;
/* 1813 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1814 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1815 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1819 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1820 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1821 */       return true;
/*      */     }
/* 1823 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1829 */     PageContext pageContext = _jspx_page_context;
/* 1830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1832 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1833 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1834 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1836 */     _jspx_th_html_005ftext_005f2.setProperty("endDate");
/*      */     
/* 1838 */     _jspx_th_html_005ftext_005f2.setSize("14");
/*      */     
/* 1840 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 1842 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 1844 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 1846 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtextselected");
/* 1847 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1848 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1849 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1850 */       return true;
/*      */     }
/* 1852 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1858 */     PageContext pageContext = _jspx_page_context;
/* 1859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1861 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1862 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1863 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1864 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1865 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1867 */         out.write(32);
/* 1868 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1869 */           return true;
/* 1870 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1871 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1872 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1876 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1877 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1878 */       return true;
/*      */     }
/* 1880 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1881 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1886 */     PageContext pageContext = _jspx_page_context;
/* 1887 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1889 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1890 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 1891 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1893 */     _jspx_th_html_005ftext_005f3.setProperty("endDate");
/*      */     
/* 1895 */     _jspx_th_html_005ftext_005f3.setSize("13");
/*      */     
/* 1897 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 1899 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 1901 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetEndTime(this.value)");
/* 1902 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 1903 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 1904 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1905 */       return true;
/*      */     }
/* 1907 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1913 */     PageContext pageContext = _jspx_page_context;
/* 1914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1916 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1917 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 1918 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 1920 */     _jspx_th_html_005fhidden_005f2.setProperty("businessPeriod");
/*      */     
/* 1922 */     _jspx_th_html_005fhidden_005f2.setValue("oni");
/* 1923 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 1924 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 1925 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 1926 */       return true;
/*      */     }
/* 1928 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 1929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1934 */     PageContext pageContext = _jspx_page_context;
/* 1935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1937 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1938 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 1939 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 1941 */     _jspx_th_html_005fhidden_005f3.setProperty("reporttype");
/*      */     
/* 1943 */     _jspx_th_html_005fhidden_005f3.setValue("html");
/* 1944 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 1945 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 1946 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1947 */       return true;
/*      */     }
/* 1949 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1955 */     PageContext pageContext = _jspx_page_context;
/* 1956 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1958 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 1959 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1960 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 1962 */     _jspx_th_html_005fselect_005f1.setProperty("sevenThirtyAttrib");
/*      */     
/* 1964 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtextselected");
/*      */     
/* 1966 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 1968 */     _jspx_th_html_005fselect_005f1.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 1970 */     _jspx_th_html_005fselect_005f1.setOnblur("this.style.width='100%'");
/*      */     
/* 1972 */     _jspx_th_html_005fselect_005f1.setOnchange("this.style.width='100%';fnchangeAttrib(this)");
/* 1973 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1974 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1975 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1976 */         out = _jspx_page_context.pushBody();
/* 1977 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 1978 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1981 */         out.write("\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 1982 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 1983 */           return true;
/* 1984 */         out.write("\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t ");
/* 1985 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 1986 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1989 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1990 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1993 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 1994 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f1);
/* 1995 */       return true;
/*      */     }
/* 1997 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f1);
/* 1998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2003 */     PageContext pageContext = _jspx_page_context;
/* 2004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2006 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2007 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 2008 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 2010 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("sevenThirtyAttribCln");
/* 2011 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 2012 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 2013 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2014 */       return true;
/*      */     }
/* 2016 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2022 */     PageContext pageContext = _jspx_page_context;
/* 2023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2025 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 2026 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 2027 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 2029 */     _jspx_th_html_005fselect_005f2.setProperty("businessPeriod");
/*      */     
/* 2031 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */     
/* 2033 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%");
/*      */     
/* 2035 */     _jspx_th_html_005fselect_005f2.setStyleId("per");
/*      */     
/* 2037 */     _jspx_th_html_005fselect_005f2.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 2039 */     _jspx_th_html_005fselect_005f2.setOnblur("this.style.width='100%'");
/*      */     
/* 2041 */     _jspx_th_html_005fselect_005f2.setOnchange("this.style.width='100%';fnbHour(this)");
/* 2042 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 2043 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 2044 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 2045 */         out = _jspx_page_context.pushBody();
/* 2046 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 2047 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2050 */         out.write(10);
/* 2051 */         out.write(9);
/* 2052 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 2053 */           return true;
/* 2054 */         out.write(10);
/* 2055 */         out.write(9);
/* 2056 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 2057 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2060 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 2061 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2064 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 2065 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f2);
/* 2066 */       return true;
/*      */     }
/* 2068 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f2);
/* 2069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2074 */     PageContext pageContext = _jspx_page_context;
/* 2075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2077 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2078 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 2079 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 2081 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("businessRuleNames");
/* 2082 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 2083 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 2084 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 2085 */       return true;
/*      */     }
/* 2087 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 2088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2093 */     PageContext pageContext = _jspx_page_context;
/* 2094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2096 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2097 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2098 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2100 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/* 2101 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2102 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2104 */       return true;
/*      */     }
/* 2106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2112 */     PageContext pageContext = _jspx_page_context;
/* 2113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2115 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2116 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2117 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2119 */     _jspx_th_c_005fset_005f0.setVar("rname");
/* 2120 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2121 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2122 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2123 */         out = _jspx_page_context.pushBody();
/* 2124 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2125 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2128 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 2129 */           return true;
/* 2130 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2131 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2134 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2135 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2138 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2139 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2140 */       return true;
/*      */     }
/* 2142 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2148 */     PageContext pageContext = _jspx_page_context;
/* 2149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2151 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2152 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2153 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 2155 */     _jspx_th_c_005fout_005f2.setValue("${monitortype}");
/* 2156 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2157 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2159 */       return true;
/*      */     }
/* 2161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2167 */     PageContext pageContext = _jspx_page_context;
/* 2168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2170 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2171 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2172 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2174 */     _jspx_th_c_005fset_005f1.setVar("resourcename");
/* 2175 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2176 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2177 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2178 */         out = _jspx_page_context.pushBody();
/* 2179 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2180 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2183 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 2184 */           return true;
/* 2185 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2186 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2189 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2190 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2193 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2194 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2195 */       return true;
/*      */     }
/* 2197 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2203 */     PageContext pageContext = _jspx_page_context;
/* 2204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2206 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2207 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2208 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 2210 */     _jspx_th_c_005fout_005f3.setValue("${resourcename}");
/* 2211 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2212 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2213 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2214 */       return true;
/*      */     }
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2222 */     PageContext pageContext = _jspx_page_context;
/* 2223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2225 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2226 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 2227 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2229 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${date1}");
/*      */     
/* 2231 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("MMM d,yyyy");
/* 2232 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 2233 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 2234 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2235 */       return true;
/*      */     }
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 2238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2243 */     PageContext pageContext = _jspx_page_context;
/* 2244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2246 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2247 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 2248 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2250 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${date1}");
/*      */     
/* 2252 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("H:mm");
/* 2253 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 2254 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 2255 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 2256 */       return true;
/*      */     }
/* 2258 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 2259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2264 */     PageContext pageContext = _jspx_page_context;
/* 2265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2267 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2268 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2269 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2271 */     _jspx_th_c_005fset_005f2.setVar("rname");
/* 2272 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2273 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2274 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2275 */         out = _jspx_page_context.pushBody();
/* 2276 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2277 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2280 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 2281 */           return true;
/* 2282 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2286 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2287 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2290 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2291 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2292 */       return true;
/*      */     }
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2300 */     PageContext pageContext = _jspx_page_context;
/* 2301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2303 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2304 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2305 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 2307 */     _jspx_th_c_005fout_005f4.setValue("${monitortype}");
/* 2308 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2309 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2310 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2311 */       return true;
/*      */     }
/* 2313 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2319 */     PageContext pageContext = _jspx_page_context;
/* 2320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2322 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2323 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2324 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2326 */     _jspx_th_c_005fset_005f3.setVar("resourcename");
/* 2327 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2328 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2329 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2330 */         out = _jspx_page_context.pushBody();
/* 2331 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2332 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2335 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 2336 */           return true;
/* 2337 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2338 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2341 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2342 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2345 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2346 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2347 */       return true;
/*      */     }
/* 2349 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2355 */     PageContext pageContext = _jspx_page_context;
/* 2356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2358 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2359 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2360 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 2362 */     _jspx_th_c_005fout_005f5.setValue("${resourcename}");
/* 2363 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2364 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2365 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2366 */       return true;
/*      */     }
/* 2368 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2374 */     PageContext pageContext = _jspx_page_context;
/* 2375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2377 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2378 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 2379 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2381 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${date1}");
/*      */     
/* 2383 */     _jspx_th_fmt_005fformatDate_005f2.setPattern("MMM d,yyyy");
/* 2384 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 2385 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 2386 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 2387 */       return true;
/*      */     }
/* 2389 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 2390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2395 */     PageContext pageContext = _jspx_page_context;
/* 2396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2398 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f3 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2399 */     _jspx_th_fmt_005fformatDate_005f3.setPageContext(_jspx_page_context);
/* 2400 */     _jspx_th_fmt_005fformatDate_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2402 */     _jspx_th_fmt_005fformatDate_005f3.setValue("${date1}");
/*      */     
/* 2404 */     _jspx_th_fmt_005fformatDate_005f3.setPattern("H:mm");
/* 2405 */     int _jspx_eval_fmt_005fformatDate_005f3 = _jspx_th_fmt_005fformatDate_005f3.doStartTag();
/* 2406 */     if (_jspx_th_fmt_005fformatDate_005f3.doEndTag() == 5) {
/* 2407 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 2408 */       return true;
/*      */     }
/* 2410 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f3);
/* 2411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2416 */     PageContext pageContext = _jspx_page_context;
/* 2417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2419 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2420 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 2421 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2423 */     _jspx_th_c_005fset_005f4.setVar("rname");
/* 2424 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 2425 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 2426 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2427 */         out = _jspx_page_context.pushBody();
/* 2428 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 2429 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2432 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 2433 */           return true;
/* 2434 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 2435 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2438 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2439 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2442 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 2443 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2444 */       return true;
/*      */     }
/* 2446 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2452 */     PageContext pageContext = _jspx_page_context;
/* 2453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2455 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2456 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2457 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 2459 */     _jspx_th_c_005fout_005f6.setValue("${monitortype}");
/* 2460 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2461 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2463 */       return true;
/*      */     }
/* 2465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2471 */     PageContext pageContext = _jspx_page_context;
/* 2472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2474 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2475 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2476 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2478 */     _jspx_th_c_005fset_005f5.setVar("resourcename");
/* 2479 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2480 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 2481 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 2482 */         out = _jspx_page_context.pushBody();
/* 2483 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 2484 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2487 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 2488 */           return true;
/* 2489 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 2490 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2493 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 2494 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2497 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2498 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 2499 */       return true;
/*      */     }
/* 2501 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 2502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2507 */     PageContext pageContext = _jspx_page_context;
/* 2508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2510 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2511 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2512 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 2514 */     _jspx_th_c_005fout_005f7.setValue("${resourcename}");
/* 2515 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2516 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2518 */       return true;
/*      */     }
/* 2520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2526 */     PageContext pageContext = _jspx_page_context;
/* 2527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2529 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f4 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2530 */     _jspx_th_fmt_005fformatDate_005f4.setPageContext(_jspx_page_context);
/* 2531 */     _jspx_th_fmt_005fformatDate_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 2533 */     _jspx_th_fmt_005fformatDate_005f4.setValue("${date}");
/*      */     
/* 2535 */     _jspx_th_fmt_005fformatDate_005f4.setPattern("MMM d,yyyy");
/* 2536 */     int _jspx_eval_fmt_005fformatDate_005f4 = _jspx_th_fmt_005fformatDate_005f4.doStartTag();
/* 2537 */     if (_jspx_th_fmt_005fformatDate_005f4.doEndTag() == 5) {
/* 2538 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 2539 */       return true;
/*      */     }
/* 2541 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f4);
/* 2542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f5(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2547 */     PageContext pageContext = _jspx_page_context;
/* 2548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2550 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f5 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 2551 */     _jspx_th_fmt_005fformatDate_005f5.setPageContext(_jspx_page_context);
/* 2552 */     _jspx_th_fmt_005fformatDate_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 2554 */     _jspx_th_fmt_005fformatDate_005f5.setValue("${date}");
/*      */     
/* 2556 */     _jspx_th_fmt_005fformatDate_005f5.setPattern("H:mm");
/* 2557 */     int _jspx_eval_fmt_005fformatDate_005f5 = _jspx_th_fmt_005fformatDate_005f5.doStartTag();
/* 2558 */     if (_jspx_th_fmt_005fformatDate_005f5.doEndTag() == 5) {
/* 2559 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 2560 */       return true;
/*      */     }
/* 2562 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f5);
/* 2563 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fConfigurationData_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */