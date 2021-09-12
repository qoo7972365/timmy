/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ReportPeriod_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   29 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   35 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/*   36 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   58 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   77 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   82 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   83 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   84 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   88 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  101 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  104 */     JspWriter out = null;
/*  105 */     Object page = this;
/*  106 */     JspWriter _jspx_out = null;
/*  107 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  111 */       response.setContentType("text/html");
/*  112 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  114 */       _jspx_page_context = pageContext;
/*  115 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  116 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  117 */       session = pageContext.getSession();
/*  118 */       out = pageContext.getOut();
/*  119 */       _jspx_out = out;
/*      */       
/*  121 */       out.write("<!--$Id$-->\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/validation.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n \n \n  \n  \n ");
/*  122 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  123 */       out.write("\n<script type=\"text/javascript\" src=\"../../../template/jquery.multiselect.min.js\"></script>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../images/jquery.multiselect.css\" />\n<script>\nfunction formReloadOnSelectColumns()\n{\n\tvar columns = jQuery(\"#multipleSelect\").serialize() // NO I18N\n\tvar arr = new Array()\n\tvar columnsList = ''\n\tarr = columns.split(\"&\")\n\tfor (var i in arr) {\n\t\tcolumnsList += arr[i].substring(arr[i].indexOf(\"=\")+1)+','\n\t}\n\tdocument.forms[0].columnsAdded.value=columnsList;\n\tdocument.forms[0].submit();\t\n}\n\njQuery(document).ready(function(){\n\tvar beforeselect;\n\tvar nonetext = '");
/*  124 */       out.print(FormatUtil.getString("am.reporttab.mssql.reports.selectcolumns.text"));
/*  125 */       out.write("';\n\tvar selectText = '");
/*  126 */       out.print(FormatUtil.getString("am.reporttab.mssql.reports.columns.text"));
/*  127 */       out.write("';\n \t$(\"#multipleSelect\").multiselect({ // NO I18N\n \t \theader: false,\n \t \tselectedList: 100,\n \t \tnoneSelectedText:nonetext,\n \t \tselectedText:'# '+selectText,\n \t \tbeforeopen: function(){\n\t\t\tbeforeselect = jQuery(\"#multipleSelect\").serialize(); // NO I18N\n \t \t},\n \t \tclose: function(event){ \t \t\n\t\t\tvar afterselect = jQuery(\"#multipleSelect\").serialize(); // NO I18N\n\t\t\tif(beforeselect != afterselect && afterselect.length > 0){\n\t\t\t\tformReloadOnSelectColumns();\n\t\t\t}\n \t \t}\n \t });\n\t jQuery('.ui-icon').css('background-image','none'); // NO I18N\n     jQuery(\".ui-multiselect\").css({ // NO I18N\n \t 'height':'20', // NO I18N\n \t 'padding':'0px,0,2px,4px' // NO I18N\n \t });\n \t jQuery('.ui-widget').css({ // NO I18N\n \t 'font-family':'Arial,Helvetica,sans-serif', // NO I18N\n \t 'font-size':'11px' // NO I18N\n \t});\n \tjQuery('.ui-multiselect').removeClass('ui-corner-all ');// NO I18N\n \tjQuery(\".ui-multiselect span:last\").addClass('trimlongstring'); // NO I18N\n \t/* jQuery(\".ui-multiselect .ui-state-active\").css(); */ // NO I18N\n\tjQuery('#multipleSelect').change(function(){ // NO I18N\n");
/*  128 */       out.write("\t\t$(\"#multipleSelect\").multiselect({ // NO I18N\n\t\t\tselectedList: 2\n\t\t});\n \t});\n\t$(\".trimlongstring\").each(function () { //NO I18N\n \t \tif ($(this).text().length > 25) {\n \t \t\t$(this).text($(this).text().substring(0, 25)+'....');\n \t \t}\n \t});\n});\t\n\t\t\n</script>\n\n<style>\n.ui-multiselect, .ui-icon{background-image: url(/images/icon_dropdown.gif); background-repeat: no-repeat; background-position: right center;}\n.ui-multiselect{position:relative; top:2px;}\n@media screen and (-webkit-min-device-pixel-ratio:0){\n\tbody:nth-of-type(1) .ui-multiselect {position:relative; top:0px;}\n}\nhtml* .ui-multiselect{position:relative; top:0px;}\n.trimlongstring { color: #595959; }\n.ui-state-default{background-color:#FFFFFF;}\n</style>\n\n");
/*      */       
/*  130 */       com.adventnet.appmanager.reporting.form.ReportForm frm = (com.adventnet.appmanager.reporting.form.ReportForm)request.getAttribute("ReportForm");
/*  131 */       String sType = frm.getResourceType();
/*  132 */       String capacityplanning = (String)request.getAttribute("capacityPlanningFilter");
/*  133 */       if ("true".equals(capacityplanning)) {
/*  134 */         sType = (String)request.getAttribute("capacityPlanningResourceTypes");
/*      */       }
/*      */       
/*  137 */       com.adventnet.appmanager.customfields.MyFields fields = new com.adventnet.appmanager.customfields.MyFields();
/*  138 */       ArrayList tags = com.adventnet.appmanager.customfields.MyFields.getDollarTags(false, sType, request, false);
/*      */       
/*  140 */       request.setAttribute("tags", tags);
/*  141 */       String aMe = request.getParameter("actionMethod");
/*  142 */       String hid = request.getParameter("workingdays");
/*      */       
/*  144 */       java.util.Properties rawproperties = com.adventnet.appmanager.util.DBUtil.getRawValueDetails();
/*  145 */       String RV = rawproperties.getProperty("israw");
/*  146 */       String PV = rawproperties.getProperty("rawvalue");
/*  147 */       String ATT = request.getParameter("attribute");
/*  148 */       String ATTNAME = (String)request.getAttribute("attributeDispalyName");
/*      */       
/*  150 */       ArrayList Blist = frm.getBusinessrules();
/*  151 */       boolean topVisible = true;
/*  152 */       boolean onlyPDF = false;
/*  153 */       boolean isPeriod = true;
/*  154 */       boolean isExcel = false;
/*  155 */       if (sType != null) {
/*  156 */         if ((aMe.equals("generateHAAvailabilityReport")) || (aMe.equals("generateHAHealthReport")) || (aMe.equals("generateHAResponseTimeReport")) || (aMe.equals("generateHASnapShotReport")) || (aMe.equals("generateHASnapShotReportWithHostName")) || (aMe.equals("generateEventSummary")) || ((hid != null) && (hid.equals("true"))))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  163 */           topVisible = false;
/*      */         }
/*      */         
/*  166 */         if (("generateWeeklyMonthlyOutageReport".equals(aMe)) || ("generatePeriodAvailabilityReport".equals(aMe)) || ("generateMSSQLGeneralDetailsReport".equals(aMe))) {
/*  167 */           topVisible = false;
/*  168 */           isPeriod = false;
/*  169 */           isExcel = true;
/*      */         }
/*      */         
/*  172 */         if ("generatePeriodAvailabilityDowntimeReport".equals(aMe)) {
/*  173 */           topVisible = false;
/*  174 */           isPeriod = false;
/*  175 */           isExcel = true;
/*      */         }
/*      */         
/*  178 */         if ((aMe.equals("generateHASnapShotReport")) || (aMe.equals("generateHASnapShotReportWithHostName")))
/*      */         {
/*  180 */           onlyPDF = true;
/*  181 */           isExcel = true;
/*      */         }
/*  183 */         if (aMe.equals("generateAvailabilitySnapShotReport"))
/*      */         {
/*  185 */           topVisible = false;
/*  186 */           isExcel = true;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  191 */       if (aMe.equalsIgnoreCase("generateLicUsageReport"))
/*      */       {
/*  193 */         topVisible = false;
/*  194 */         onlyPDF = true;
/*  195 */         isExcel = false;
/*  196 */         pageContext.setAttribute("csv", "not needed");
/*      */       }
/*      */       
/*  199 */       if ((aMe.equals("generateGlanceReport")) || (aMe.equals("generateIndividualGlanceReport")))
/*      */       {
/*  201 */         topVisible = false;
/*  202 */         isExcel = false;
/*      */       }
/*  204 */       if (aMe.equals("generateUnderSizedMonitors"))
/*      */       {
/*  206 */         isExcel = true;
/*      */       }
/*      */       
/*  209 */       if (aMe.equals("generateIndividualReportCapacityPlanning"))
/*      */       {
/*  211 */         topVisible = false;
/*      */       }
/*      */       
/*  214 */       pageContext.setAttribute("topVisible", Boolean.valueOf(topVisible));
/*  215 */       pageContext.setAttribute("onlyPDF", Boolean.valueOf(onlyPDF));
/*  216 */       pageContext.setAttribute("isPeriod", Boolean.valueOf(isPeriod));
/*  217 */       pageContext.setAttribute("isExcel", Boolean.valueOf(isExcel));
/*      */       
/*  219 */       out.write("\n\n\n  \n\n<div id=\"ReportsDiv\">\n\n");
/*      */       
/*  221 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  222 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  223 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  225 */       _jspx_th_html_005fform_005f0.setAction("/showReports.do");
/*      */       
/*  227 */       _jspx_th_html_005fform_005f0.setStyle("width:100%; padding:0px 0px 0px 0px margin:0px; height:10px;");
/*  228 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  229 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  231 */           out.write("\n\n\n<table width=\"100%\" height=\"35\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=\"reports-head-tile\" style=\"padding:5px 0px 8px 0px; \">\n  <tr>\t\n  ");
/*      */           
/*  233 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  234 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  235 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  236 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  237 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  239 */               out.write("\n    ");
/*      */               
/*  241 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  242 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  243 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  245 */               _jspx_th_c_005fwhen_005f0.setTest("${onlyPDF == true }");
/*  246 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  247 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  249 */                   out.write("\n\t<td width=\"60%\" style=\"padding-left: 10px;\">\n\t<span id=\"showCustomFieldFilter\">\n   <span class=\"bodytext\">");
/*  250 */                   out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/*  251 */                   out.write("</span>\n   <span><select name=\"groupviewfilterby\"  id=\"groupviewfilterby\" class='formtext' style='background-color:#FFF8C6;' onchange='loadURLType(this.options[this.selectedIndex].value,this,\"FilterBy\")'>\n\t\t\t\t\t\t\t<option selected=\"selected\" style=\"background-color: #FFF8C6\" value=\"-\">-");
/*  252 */                   out.print(FormatUtil.getString("am.myfield.choosevalue.text"));
/*  253 */                   out.write("-</option> ");
/*  254 */                   out.write("\n\t\t\t\t\t\t\t");
/*  255 */                   if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                     return;
/*  257 */                   out.write("\n                            </select></span>\n                            <span id=\"FilterBy\" >\n                             </span>\n     </span>\n\t</td>\n     <td align=\"right\" width=\"40%\"  style=\"padding-right:10px;\">");
/*  258 */                   if (isExcel) {
/*  259 */                     out.write("<a  href=\"javascript:generateReport('excel')\"><img  align=\"center\"  src=\"../images/icon_excel.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  260 */                     out.print(FormatUtil.getString("Excel Report"));
/*  261 */                     out.write("\"></a>&nbsp;");
/*      */                   }
/*  263 */                   out.write("\n     <a  href=\"javascript:generateReport('pdf')\"><img  align=\"center\"  src=\"../images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  264 */                   out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  265 */                   out.write("\"></a>&nbsp;&nbsp;");
/*      */                   
/*  267 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  268 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  269 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  271 */                   _jspx_th_c_005fif_005f0.setTest("${csv==null}");
/*  272 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  273 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/*  275 */                       out.write("<a class=\"staticlinks\" href=\"javascript:generateCSVReport()\"><img  align=\"center\" src=\"../images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\" title=\"");
/*  276 */                       out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/*  277 */                       out.write("\"></a>");
/*  278 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  279 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  283 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  284 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/*  287 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  288 */                   if (!request.isUserInRole("OPERATOR")) {
/*  289 */                     out.write("&nbsp;<a  href=\"javascript:generateEmailReport('emailpdf')\"><img  align=\"center\" src=\"../images/email.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  290 */                     out.print(FormatUtil.getString("Email Report"));
/*  291 */                     out.write("\" ></a> ");
/*      */                   }
/*  293 */                   out.write("\n      <a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"../../images/icon_reports_small.gif\" width=\"16\" height=\"16\" border=\"0\" align=\"absbottom\" title=\"");
/*  294 */                   out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/*  295 */                   out.write("\"> </a>  \n    </td>\n   ");
/*  296 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  297 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  301 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  302 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  305 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  306 */               out.write("\n   ");
/*      */               
/*  308 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  309 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  310 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  311 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  312 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                 for (;;) {
/*  314 */                   out.write("\n   \t\t<td align=\"left\" class=\"bodytext\" style=\"padding-left:10px; padding-top:5px;\" >\n   \t\t  ");
/*      */                   
/*  316 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  317 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  318 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fotherwise_005f0);
/*  319 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  320 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  322 */                       out.write("\n   \t\t  \t  ");
/*      */                       
/*  324 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  325 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  326 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  328 */                       _jspx_th_c_005fwhen_005f1.setTest("${topVisible == true }");
/*  329 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  330 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  332 */                           out.write("\n  \n   \t \n\t\t\t\t     \n\t\t\t\t     \n\t\t\t\t     <span class=\"bodytext\">");
/*  333 */                           out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/*  334 */                           out.write("</span>\n\t\t\t\t    \n\t\t\t\t     <span>");
/*      */                           
/*  336 */                           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  337 */                           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  338 */                           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                           
/*  340 */                           _jspx_th_html_005fselect_005f0.setProperty("numberOfRows");
/*      */                           
/*  342 */                           _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnRows(this)");
/*      */                           
/*  344 */                           _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*  345 */                           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  346 */                           if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  347 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  348 */                               out = _jspx_page_context.pushBody();
/*  349 */                               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  350 */                               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  353 */                               out.write("  \t \n\t\t\t\t       ");
/*      */                               
/*  355 */                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  356 */                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  357 */                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  359 */                               _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.common.topten.text"));
/*      */                               
/*  361 */                               _jspx_th_html_005foption_005f0.setValue("10");
/*  362 */                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  363 */                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  364 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                               }
/*      */                               
/*  367 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*  368 */                               out.write(32);
/*      */                               
/*  370 */                               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  371 */                               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  372 */                               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  374 */                               _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.common.toptwenty.text"));
/*      */                               
/*  376 */                               _jspx_th_html_005foption_005f1.setValue("20");
/*  377 */                               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  378 */                               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  379 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                               }
/*      */                               
/*  382 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  383 */                               out.write("  \t \n\t\t\t\t       ");
/*      */                               
/*  385 */                               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  386 */                               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  387 */                               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  389 */                               _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.common.topfifty.text"));
/*      */                               
/*  391 */                               _jspx_th_html_005foption_005f2.setValue("50");
/*  392 */                               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  393 */                               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  394 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                               }
/*      */                               
/*  397 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  398 */                               out.write(32);
/*      */                               
/*  400 */                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  401 */                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  402 */                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  404 */                               _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.monitortab.all.text"));
/*      */                               
/*  406 */                               _jspx_th_html_005foption_005f3.setValue("-1");
/*  407 */                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  408 */                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  409 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                               }
/*      */                               
/*  412 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  413 */                               out.write("  \t \n\t\t\t\t       ");
/*  414 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  415 */                                 out.write("\n\t\t\t\t      \t\t");
/*      */                                 
/*  417 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  418 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  419 */                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                 
/*  421 */                                 _jspx_th_c_005fif_005f1.setTest("${not empty tags}");
/*  422 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  423 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/*  425 */                                     out.write("\t\t\t\t        \t\n\t\t\t\t        \t\t<optgroup  label=\"");
/*  426 */                                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/*  427 */                                     out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t\t        \t\t");
/*  428 */                                     if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/*  430 */                                     out.write("\t\t\t\t           \n\t\t\t\t       \t\t");
/*  431 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  432 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  436 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  437 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                 }
/*      */                                 
/*  440 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  441 */                                 out.write("\n\t\t\t\t       \t");
/*      */                               }
/*  443 */                               out.write("\n\t\t\t\t        ");
/*  444 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  445 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  448 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  449 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  452 */                           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  453 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                           }
/*      */                           
/*  456 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  457 */                           out.write("\n\t\t\t\t        </span>\n\t\t\t\t        <span id=\"FilterBy\"></span>\n\t\t\t\t        \n      \t\t");
/*  458 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  459 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  463 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  464 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  467 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  468 */                       out.write("\n      \t\t");
/*      */                       
/*  470 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  471 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  472 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  473 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  474 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  476 */                           out.write("\n      \n      \n\t\t\t\t      ");
/*      */                           
/*  478 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  479 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  480 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/*  482 */                           _jspx_th_c_005fif_005f2.setTest("${not empty tags}");
/*  483 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  484 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/*  486 */                               out.write("\n\t\t\t\t      \n\t\t\t\t  \n\t\t\t\t\n\t\t\t\t <span id=\"showCustomFieldFilter\">\n\t\t\t\t  <span >");
/*  487 */                               out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/*  488 */                               out.write("\n\t\t\t\t   &nbsp;&nbsp;<select name=\"groupviewfilterby\"  id=\"groupviewfilterby\" class='formtext' style='background-color:#FFF8C6;' onchange='loadURLType(this.options[this.selectedIndex].value,this,\"FilterBy\")'>\n\t\t\t\t\t\t\t\t\t\t\t<option selected=\"selected\" style=\"background-color: #FFF8C6\" value=\"-\">-");
/*  489 */                               out.print(FormatUtil.getString("am.myfield.choosevalue.text"));
/*  490 */                               out.write("-</option> ");
/*  491 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*  492 */                               if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                 return;
/*  494 */                               out.write("\n\t\t\t\t                            </select></span>\n\t\t\t\t  \n\t\t\t\t  <span id=\"FilterBy\"></span>\n\t\t\t\t </span>\n\t\t\t\t  \n\t\t\t\t ");
/*  495 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  496 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  500 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  501 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/*  504 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  505 */                           out.write("\n     \n      \n    \t\t");
/*  506 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  507 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  511 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  512 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  515 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  516 */                       out.write("\n    ");
/*  517 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  518 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  522 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  523 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  526 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  527 */                   out.write("\n    \n    ");
/*      */                   
/*  529 */                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  530 */                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  531 */                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fotherwise_005f0);
/*  532 */                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  533 */                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                     for (;;) {
/*  535 */                       out.write("\n    \t");
/*      */                       
/*  537 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  538 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  539 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                       
/*  541 */                       _jspx_th_c_005fwhen_005f2.setTest("${isPeriod == false }");
/*  542 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  543 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/*  545 */                           out.write("\n    \n    \n     ");
/*  546 */                           if (("generatePeriodAvailabilityReport".equals(aMe)) || ("generatePeriodAvailabilityDowntimeReport".equals(aMe))) {
/*  547 */                             out.write("\n     ");
/*      */                             
/*  549 */                             SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  550 */                             _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/*  551 */                             _jspx_th_html_005fselect_005f1.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                             
/*  553 */                             _jspx_th_html_005fselect_005f1.setProperty("interval");
/*      */                             
/*  555 */                             _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */                             
/*  557 */                             _jspx_th_html_005fselect_005f1.setOnchange("javascript:getComboForOutage(this)");
/*  558 */                             int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/*  559 */                             if (_jspx_eval_html_005fselect_005f1 != 0) {
/*  560 */                               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  561 */                                 out = _jspx_page_context.pushBody();
/*  562 */                                 _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/*  563 */                                 _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  566 */                                 out.write("\n    ");
/*      */                                 
/*  568 */                                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  569 */                                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  570 */                                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                 
/*  572 */                                 _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("Day"));
/*      */                                 
/*  574 */                                 _jspx_th_html_005foption_005f4.setValue("day");
/*  575 */                                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  576 */                                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  577 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                 }
/*      */                                 
/*  580 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  581 */                                 out.write(" \n     ");
/*      */                                 
/*  583 */                                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  584 */                                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  585 */                                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                 
/*  587 */                                 _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("Week"));
/*      */                                 
/*  589 */                                 _jspx_th_html_005foption_005f5.setValue("week");
/*  590 */                                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  591 */                                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  592 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                 }
/*      */                                 
/*  595 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  596 */                                 out.write("    \n         ");
/*      */                                 
/*  598 */                                 OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  599 */                                 _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  600 */                                 _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                 
/*  602 */                                 _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("Month"));
/*      */                                 
/*  604 */                                 _jspx_th_html_005foption_005f6.setValue("month");
/*  605 */                                 int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  606 */                                 if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  607 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                 }
/*      */                                 
/*  610 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  611 */                                 out.write("\n        ");
/*  612 */                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/*  613 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  616 */                               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  617 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  620 */                             if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/*  621 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                             }
/*      */                             
/*  624 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/*  625 */                             out.write(32);
/*  626 */                             out.write(10);
/*      */                           }
/*  628 */                           out.write("\n\n     ");
/*  629 */                           if ("generateWeeklyMonthlyOutageReport".equals(aMe)) {
/*  630 */                             out.write("\n   \n     ");
/*      */                             
/*  632 */                             SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  633 */                             _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/*  634 */                             _jspx_th_html_005fselect_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                             
/*  636 */                             _jspx_th_html_005fselect_005f2.setProperty("interval");
/*      */                             
/*  638 */                             _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */                             
/*  640 */                             _jspx_th_html_005fselect_005f2.setOnchange("javascript:getComboForOutage(this)");
/*  641 */                             int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/*  642 */                             if (_jspx_eval_html_005fselect_005f2 != 0) {
/*  643 */                               if (_jspx_eval_html_005fselect_005f2 != 1) {
/*  644 */                                 out = _jspx_page_context.pushBody();
/*  645 */                                 _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/*  646 */                                 _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  649 */                                 out.write("\n   \n     ");
/*      */                                 
/*  651 */                                 OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  652 */                                 _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  653 */                                 _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                 
/*  655 */                                 _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("Week"));
/*      */                                 
/*  657 */                                 _jspx_th_html_005foption_005f7.setValue("week");
/*  658 */                                 int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  659 */                                 if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  660 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                 }
/*      */                                 
/*  663 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  664 */                                 out.write("    \n         ");
/*      */                                 
/*  666 */                                 OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  667 */                                 _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  668 */                                 _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                 
/*  670 */                                 _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("Month"));
/*      */                                 
/*  672 */                                 _jspx_th_html_005foption_005f8.setValue("month");
/*  673 */                                 int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  674 */                                 if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  675 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                 }
/*      */                                 
/*  678 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  679 */                                 out.write("\n         ");
/*      */                                 
/*  681 */                                 OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  682 */                                 _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  683 */                                 _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                 
/*  685 */                                 _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("Custom Period"));
/*      */                                 
/*  687 */                                 _jspx_th_html_005foption_005f9.setValue("customtime");
/*  688 */                                 int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  689 */                                 if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  690 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                 }
/*      */                                 
/*  693 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  694 */                                 out.write("\n        ");
/*  695 */                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/*  696 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  699 */                               if (_jspx_eval_html_005fselect_005f2 != 1) {
/*  700 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  703 */                             if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/*  704 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                             }
/*      */                             
/*  707 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/*  708 */                             out.write(32);
/*  709 */                             out.write(10);
/*  710 */                             out.write(32);
/*      */                           }
/*  712 */                           out.write("\n \n\n");
/*  713 */                           if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                             return;
/*  715 */                           out.write("\n\n\n    </td>\n\t");
/*  716 */                           if ((com.adventnet.appmanager.util.Constants.sqlManager) && ("generateMSSQLGeneralDetailsReport".equals(aMe))) {
/*  717 */                             out.write("\n\t<td width=\"13%\" align=\"right\"  style=\"padding-right:10px;\">\n\t<div width=\"100%\" align=\"right\" style=\"padding-right:2px;\">   \n\t<select id=\"multipleSelect\" class=\"ui-monitor-select\" name=\"multipleSelect\" multiple=\"multiple\" >\n\t<optgroup label='");
/*  718 */                             out.print(FormatUtil.getString("am.reporttab.mssql.reports.allcolumns.text"));
/*  719 */                             out.write("'>\n \t ");
/*      */                             try {
/*  721 */                               HashMap map = (HashMap)request.getAttribute("columnHeadings");
/*  722 */                               ArrayList checkBoxList = (ArrayList)request.getAttribute("checkBoxList");
/*  723 */                               String[] selectedColumns = (String[])map.get("selectedColumns");
/*  724 */                               for (int i = 0; i < selectedColumns.length; i++) {
/*  725 */                                 String selected = "";
/*  726 */                                 if ((checkBoxList != null) && (checkBoxList.contains(selectedColumns[i]))) {
/*  727 */                                   selected = "selected";
/*      */                                 }
/*  729 */                                 out.write("\t\t\n\t\t\t\t<option ");
/*  730 */                                 out.print(selected);
/*  731 */                                 out.write(" value=\"");
/*  732 */                                 out.print(selectedColumns[i]);
/*  733 */                                 out.write(34);
/*  734 */                                 out.write(62);
/*  735 */                                 out.print((String)map.get(selectedColumns[i]));
/*  736 */                                 out.write("</option>\n\t\t");
/*      */                               }
/*      */                             } catch (Exception e) {
/*  739 */                               e.printStackTrace();
/*      */                             }
/*      */                             
/*  742 */                             out.write("\n \t \t</optgroup> \t \t\t \t \t\n \t \t</select>\n\t</div>\n\t</td>\n\t");
/*      */                           }
/*  744 */                           out.write("\n   <td width=\"13%\" align=\"right\"  style=\"padding-right:10px;\">\n\n\t\t");
/*      */                           
/*  746 */                           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  747 */                           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  748 */                           _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                           
/*  750 */                           _jspx_th_c_005fif_005f4.setTest("${isExcel == true }");
/*  751 */                           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  752 */                           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                             for (;;) {
/*  754 */                               out.write("\n \t\t\t<a  href=\"javascript:generateReport('excel')\"><img  align=\"center\"  src=\"../images/icon_excel.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  755 */                               out.print(FormatUtil.getString("Excel Report"));
/*  756 */                               out.write("\"></a> \n  \t\t");
/*  757 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  758 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  762 */                           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  763 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                           }
/*      */                           
/*  766 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  767 */                           out.write("\n  \t\t\n    <a  href=\"javascript:generateReport('pdf')\"><img  align=\"center\"  src=\"../images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  768 */                           out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  769 */                           out.write("\"></a> \n\t");
/*  770 */                           if (!"generatePeriodAvailabilityDowntimeReport".equals(aMe)) {
/*  771 */                             out.write(10);
/*  772 */                             out.write(9);
/*      */                             
/*  774 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  775 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  776 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                             
/*  778 */                             _jspx_th_c_005fif_005f5.setTest("${csv==null}");
/*  779 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  780 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/*  782 */                                 out.write("<a class=\"staticlinks\" href=\"javascript:generateCSVReport()\"><img  align=\"center\" src=\"../images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\" title=\"");
/*  783 */                                 out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/*  784 */                                 out.write("\"></a>");
/*  785 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  786 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  790 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  791 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/*  794 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  795 */                             out.write(10);
/*  796 */                             out.write(9);
/*      */                           }
/*  798 */                           out.write(10);
/*  799 */                           out.write(9);
/*  800 */                           if (!request.isUserInRole("OPERATOR")) {
/*  801 */                             out.write("&nbsp;<a  href=\"javascript:generateEmailReport('emailpdf')\"><img  align=\"center\" src=\"../images/email.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  802 */                             out.print(FormatUtil.getString("Email Report"));
/*  803 */                             out.write("\" ></a> ");
/*      */                           }
/*  805 */                           out.write("\n       <a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"../../images/icon_reports_small.gif\" width=\"16\" height=\"16\" border=\"0\" align=\"middle\" title=\"");
/*  806 */                           out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/*  807 */                           out.write("\"> </a>  \n\n    </td>\n\n<tr><td colspan=\"2\">\n<div id='showcustom'>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"bodytext\" style=\"white-space: nowrap;\">         \n   <td width=\"32%\" align=\"left\"   >");
/*  808 */                           out.print(FormatUtil.getString("am.webclient.reports.outage.lastperiod.text"));
/*  809 */                           out.write(58);
/*  810 */                           out.write(32);
/*  811 */                           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  812 */                           out.write(32);
/*  813 */                           out.write(32);
/*  814 */                           if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                             return;
/*  816 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=startTrigger title=\"");
/*  817 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  818 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"start\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n\n");
/*  819 */                           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  820 */                           out.write(32);
/*  821 */                           if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                             return;
/*  823 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=endTrigger title=\"");
/*  824 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  825 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"end\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n\n</td>\n\n    <td width=\"45%\" align=\"left\" >\n\n");
/*  826 */                           out.print(FormatUtil.getString("am.webclient.reports.outage.thisperiod.text"));
/*  827 */                           out.write(58);
/*  828 */                           out.write(32);
/*  829 */                           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  830 */                           out.write(32);
/*  831 */                           out.write(32);
/*  832 */                           if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                             return;
/*  834 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=startTrigger1 title=\"");
/*  835 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  836 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"start1\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"startTrigger1\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n\n");
/*  837 */                           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  838 */                           out.write(32);
/*  839 */                           if (_jspx_meth_c_005fchoose_005f6(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                             return;
/*  841 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=endTrigger1 title=\"");
/*  842 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  843 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"end1\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"endTrigger1\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n\n <input type=\"button\" name=\"show\" value=\"");
/*  844 */                           out.print(FormatUtil.getString("Go"));
/*  845 */                           out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:outageReload()\">\n</td>\n</table>\n</div>\n</td>\n                                                                  \n    ");
/*  846 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  847 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  851 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  852 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/*  855 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  856 */                       out.write("\n    ");
/*      */                       
/*  858 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  859 */                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  860 */                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f2);
/*  861 */                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  862 */                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                         for (;;) {
/*  864 */                           out.write("\n    \n");
/*      */                           
/*  866 */                           ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  867 */                           _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  868 */                           _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_c_005fotherwise_005f6);
/*  869 */                           int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  870 */                           if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                             for (;;) {
/*  872 */                               out.write("\n    ");
/*      */                               
/*  874 */                               WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  875 */                               _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  876 */                               _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                               
/*  878 */                               _jspx_th_c_005fwhen_005f7.setTest("${requestScope.ReportForm.startDate != ''}");
/*  879 */                               int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  880 */                               if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                 for (;;) {
/*  882 */                                   out.write("\n        <span>");
/*      */                                   
/*  884 */                                   SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  885 */                                   _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/*  886 */                                   _jspx_th_html_005fselect_005f4.setParent(_jspx_th_c_005fwhen_005f7);
/*      */                                   
/*  888 */                                   _jspx_th_html_005fselect_005f4.setProperty("period");
/*      */                                   
/*  890 */                                   _jspx_th_html_005fselect_005f4.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/*  892 */                                   _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/*  893 */                                   int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/*  894 */                                   if (_jspx_eval_html_005fselect_005f4 != 0) {
/*  895 */                                     if (_jspx_eval_html_005fselect_005f4 != 1) {
/*  896 */                                       out = _jspx_page_context.pushBody();
/*  897 */                                       _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/*  898 */                                       _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  901 */                                       out.write("\n        ");
/*  902 */                                       if (((ATT != null) && ((ATT.equals("cpuid")) || (ATT.equals("mem")))) || ((ATTNAME != null) && ((ATTNAME.equals("CPU Utilization")) || (ATTNAME.equals("Memory Usage"))))) {
/*  903 */                                         out.write("\n        ");
/*  904 */                                         if ((RV != null) && (RV.equals("true"))) {
/*  905 */                                           out.write("\n    ");
/*      */                                           
/*  907 */                                           OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  908 */                                           _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  909 */                                           _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                           
/*  911 */                                           _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*      */                                           
/*  913 */                                           _jspx_th_html_005foption_005f10.setValue("14");
/*  914 */                                           int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  915 */                                           if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  916 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                           }
/*      */                                           
/*  919 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*      */                                         } }
/*  921 */                                       out.write(10);
/*  922 */                                       out.write(9);
/*  923 */                                       out.write(32);
/*      */                                       
/*  925 */                                       OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  926 */                                       _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/*  927 */                                       _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/*  929 */                                       _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                                       
/*  931 */                                       _jspx_th_html_005foption_005f11.setValue("4");
/*  932 */                                       int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/*  933 */                                       if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/*  934 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                       }
/*      */                                       
/*  937 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/*  938 */                                       out.write("\t\n        ");
/*      */                                       
/*  940 */                                       OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  941 */                                       _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/*  942 */                                       _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/*  944 */                                       _jspx_th_html_005foption_005f12.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/*  946 */                                       _jspx_th_html_005foption_005f12.setValue("0");
/*  947 */                                       int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/*  948 */                                       if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/*  949 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                       }
/*      */                                       
/*  952 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12);
/*  953 */                                       out.write("\n        ");
/*      */                                       
/*  955 */                                       OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  956 */                                       _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/*  957 */                                       _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/*  959 */                                       _jspx_th_html_005foption_005f13.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/*  961 */                                       _jspx_th_html_005foption_005f13.setValue("3");
/*  962 */                                       int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/*  963 */                                       if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/*  964 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                       }
/*      */                                       
/*  967 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13);
/*  968 */                                       out.write("    \n        ");
/*      */                                       
/*  970 */                                       OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  971 */                                       _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/*  972 */                                       _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/*  974 */                                       _jspx_th_html_005foption_005f14.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/*  976 */                                       _jspx_th_html_005foption_005f14.setValue("6");
/*  977 */                                       int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/*  978 */                                       if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/*  979 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                       }
/*      */                                       
/*  982 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f14);
/*  983 */                                       out.write("    \n        ");
/*      */                                       
/*  985 */                                       OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  986 */                                       _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/*  987 */                                       _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/*  989 */                                       _jspx_th_html_005foption_005f15.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/*  991 */                                       _jspx_th_html_005foption_005f15.setValue("1");
/*  992 */                                       int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/*  993 */                                       if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/*  994 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                       }
/*      */                                       
/*  997 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f15);
/*  998 */                                       out.write("\n          ");
/*      */                                       
/* 1000 */                                       OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1001 */                                       _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 1002 */                                       _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1004 */                                       _jspx_th_html_005foption_005f16.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 1006 */                                       _jspx_th_html_005foption_005f16.setValue("12");
/* 1007 */                                       int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 1008 */                                       if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 1009 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                       }
/*      */                                       
/* 1012 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f16);
/* 1013 */                                       out.write("   \n        ");
/*      */                                       
/* 1015 */                                       OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1016 */                                       _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 1017 */                                       _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1019 */                                       _jspx_th_html_005foption_005f17.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 1021 */                                       _jspx_th_html_005foption_005f17.setValue("7");
/* 1022 */                                       int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 1023 */                                       if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 1024 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                       }
/*      */                                       
/* 1027 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f17);
/* 1028 */                                       out.write("    \n        ");
/*      */                                       
/* 1030 */                                       OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1031 */                                       _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 1032 */                                       _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1034 */                                       _jspx_th_html_005foption_005f18.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 1036 */                                       _jspx_th_html_005foption_005f18.setValue("2");
/* 1037 */                                       int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 1038 */                                       if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 1039 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                       }
/*      */                                       
/* 1042 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f18);
/* 1043 */                                       out.write("\n         ");
/*      */                                       
/* 1045 */                                       OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1046 */                                       _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 1047 */                                       _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1049 */                                       _jspx_th_html_005foption_005f19.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 1051 */                                       _jspx_th_html_005foption_005f19.setValue("11");
/* 1052 */                                       int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 1053 */                                       if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 1054 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                       }
/*      */                                       
/* 1057 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f19);
/* 1058 */                                       out.write("\n        ");
/*      */                                       
/* 1060 */                                       OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1061 */                                       _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 1062 */                                       _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1064 */                                       _jspx_th_html_005foption_005f20.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 1066 */                                       _jspx_th_html_005foption_005f20.setValue("9");
/* 1067 */                                       int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 1068 */                                       if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 1069 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                       }
/*      */                                       
/* 1072 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f20);
/* 1073 */                                       out.write("    \n        ");
/*      */                                       
/* 1075 */                                       OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1076 */                                       _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 1077 */                                       _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1079 */                                       _jspx_th_html_005foption_005f21.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 1081 */                                       _jspx_th_html_005foption_005f21.setValue("8");
/* 1082 */                                       int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 1083 */                                       if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 1084 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                       }
/*      */                                       
/* 1087 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f21);
/* 1088 */                                       out.write("    \n         ");
/*      */                                       
/* 1090 */                                       OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1091 */                                       _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 1092 */                                       _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                       
/* 1094 */                                       _jspx_th_html_005foption_005f22.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 1096 */                                       _jspx_th_html_005foption_005f22.setValue("5");
/* 1097 */                                       int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 1098 */                                       if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 1099 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                       }
/*      */                                       
/* 1102 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f22);
/* 1103 */                                       out.write("\n        ");
/* 1104 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 1105 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1108 */                                     if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 1109 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1112 */                                   if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 1113 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                                   }
/*      */                                   
/* 1116 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 1117 */                                   out.write("  </span>      \n    ");
/* 1118 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1119 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1123 */                               if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1124 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                               }
/*      */                               
/* 1127 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1128 */                               out.write(10);
/* 1129 */                               out.write(32);
/* 1130 */                               out.write(32);
/*      */                               
/* 1132 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1133 */                               _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1134 */                               _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1135 */                               int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1136 */                               if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                 for (;;) {
/* 1138 */                                   out.write("\n   <span> ");
/*      */                                   
/* 1140 */                                   SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 1141 */                                   _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 1142 */                                   _jspx_th_html_005fselect_005f5.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                                   
/* 1144 */                                   _jspx_th_html_005fselect_005f5.setProperty("period");
/*      */                                   
/* 1146 */                                   _jspx_th_html_005fselect_005f5.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/* 1148 */                                   _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/* 1149 */                                   int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 1150 */                                   if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 1151 */                                     if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1152 */                                       out = _jspx_page_context.pushBody();
/* 1153 */                                       _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 1154 */                                       _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 1157 */                                       out.write("\n    ");
/* 1158 */                                       if (((ATT != null) && ((ATT.equals("cpuid")) || (ATT.equals("mem")))) || ((ATTNAME != null) && ((ATTNAME.equals("CPU Utilization")) || (ATTNAME.equals("Memory Usage"))))) {
/* 1159 */                                         out.write("\n    ");
/* 1160 */                                         if ((RV != null) && (RV.equals("true"))) {
/* 1161 */                                           out.write("\n    ");
/*      */                                           
/* 1163 */                                           OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1164 */                                           _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 1165 */                                           _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 1167 */                                           _jspx_th_html_005foption_005f23.setKey(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*      */                                           
/* 1169 */                                           _jspx_th_html_005foption_005f23.setValue("14");
/* 1170 */                                           int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 1171 */                                           if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 1172 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                           }
/*      */                                           
/* 1175 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f23);
/*      */                                         } }
/* 1177 */                                       out.write("\n        ");
/*      */                                       
/* 1179 */                                       OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1180 */                                       _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 1181 */                                       _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1183 */                                       _jspx_th_html_005foption_005f24.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                                       
/* 1185 */                                       _jspx_th_html_005foption_005f24.setValue("4");
/* 1186 */                                       int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 1187 */                                       if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 1188 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                       }
/*      */                                       
/* 1191 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f24);
/* 1192 */                                       out.write("\t\n    \t");
/*      */                                       
/* 1194 */                                       OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1195 */                                       _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 1196 */                                       _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1198 */                                       _jspx_th_html_005foption_005f25.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/* 1200 */                                       _jspx_th_html_005foption_005f25.setValue("0");
/* 1201 */                                       int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 1202 */                                       if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 1203 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                       }
/*      */                                       
/* 1206 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f25);
/* 1207 */                                       out.write("\n        ");
/*      */                                       
/* 1209 */                                       OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1210 */                                       _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 1211 */                                       _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1213 */                                       _jspx_th_html_005foption_005f26.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/* 1215 */                                       _jspx_th_html_005foption_005f26.setValue("3");
/* 1216 */                                       int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 1217 */                                       if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 1218 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                       }
/*      */                                       
/* 1221 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f26);
/* 1222 */                                       out.write("\n        ");
/*      */                                       
/* 1224 */                                       OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1225 */                                       _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 1226 */                                       _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1228 */                                       _jspx_th_html_005foption_005f27.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/* 1230 */                                       _jspx_th_html_005foption_005f27.setValue("6");
/* 1231 */                                       int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 1232 */                                       if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 1233 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                       }
/*      */                                       
/* 1236 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f27);
/* 1237 */                                       out.write("    \n        ");
/*      */                                       
/* 1239 */                                       OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1240 */                                       _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 1241 */                                       _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1243 */                                       _jspx_th_html_005foption_005f28.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/* 1245 */                                       _jspx_th_html_005foption_005f28.setValue("1");
/* 1246 */                                       int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 1247 */                                       if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 1248 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                       }
/*      */                                       
/* 1251 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f28);
/* 1252 */                                       out.write("\n          ");
/*      */                                       
/* 1254 */                                       OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1255 */                                       _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 1256 */                                       _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1258 */                                       _jspx_th_html_005foption_005f29.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 1260 */                                       _jspx_th_html_005foption_005f29.setValue("12");
/* 1261 */                                       int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 1262 */                                       if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 1263 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                       }
/*      */                                       
/* 1266 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f29);
/* 1267 */                                       out.write("   \n        ");
/*      */                                       
/* 1269 */                                       OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1270 */                                       _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 1271 */                                       _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1273 */                                       _jspx_th_html_005foption_005f30.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 1275 */                                       _jspx_th_html_005foption_005f30.setValue("7");
/* 1276 */                                       int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 1277 */                                       if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 1278 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                       }
/*      */                                       
/* 1281 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f30);
/* 1282 */                                       out.write("    \n        ");
/*      */                                       
/* 1284 */                                       OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1285 */                                       _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 1286 */                                       _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1288 */                                       _jspx_th_html_005foption_005f31.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 1290 */                                       _jspx_th_html_005foption_005f31.setValue("2");
/* 1291 */                                       int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 1292 */                                       if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 1293 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                       }
/*      */                                       
/* 1296 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f31);
/* 1297 */                                       out.write("\n        ");
/*      */                                       
/* 1299 */                                       OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1300 */                                       _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 1301 */                                       _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1303 */                                       _jspx_th_html_005foption_005f32.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 1305 */                                       _jspx_th_html_005foption_005f32.setValue("11");
/* 1306 */                                       int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 1307 */                                       if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 1308 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                       }
/*      */                                       
/* 1311 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f32);
/* 1312 */                                       out.write("\n        ");
/*      */                                       
/* 1314 */                                       OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1315 */                                       _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 1316 */                                       _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1318 */                                       _jspx_th_html_005foption_005f33.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 1320 */                                       _jspx_th_html_005foption_005f33.setValue("9");
/* 1321 */                                       int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 1322 */                                       if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 1323 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                       }
/*      */                                       
/* 1326 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f33);
/* 1327 */                                       out.write("   \n        ");
/*      */                                       
/* 1329 */                                       OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1330 */                                       _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 1331 */                                       _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1333 */                                       _jspx_th_html_005foption_005f34.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 1335 */                                       _jspx_th_html_005foption_005f34.setValue("8");
/* 1336 */                                       int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 1337 */                                       if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 1338 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                       }
/*      */                                       
/* 1341 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f34);
/* 1342 */                                       out.write("    \n        ");
/*      */                                       
/* 1344 */                                       OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 1345 */                                       _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 1346 */                                       _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                       
/* 1348 */                                       _jspx_th_html_005foption_005f35.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 1350 */                                       _jspx_th_html_005foption_005f35.setValue("5");
/* 1351 */                                       int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 1352 */                                       if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 1353 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                       }
/*      */                                       
/* 1356 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f35);
/* 1357 */                                       out.write("\n    ");
/* 1358 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 1359 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1362 */                                     if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1363 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1366 */                                   if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 1367 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                                   }
/*      */                                   
/* 1370 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 1371 */                                   out.write("      </span>  \n    ");
/* 1372 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1373 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1377 */                               if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1378 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                               }
/*      */                               
/* 1381 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1382 */                               out.write(10);
/* 1383 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1384 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1388 */                           if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1389 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                           }
/*      */                           
/* 1392 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1393 */                           out.write("\n   \n");
/*      */                           
/* 1395 */                           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1396 */                           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1397 */                           _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                           
/* 1399 */                           _jspx_th_c_005fif_005f6.setTest("${!empty param.ischildReport && param.ischildReport!=null && param.ischildReport=='true'}");
/* 1400 */                           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1401 */                           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                             for (;;) {
/* 1403 */                               out.write("\n<span>\n&nbsp;");
/* 1404 */                               out.print(FormatUtil.getString("Select Attribute:"));
/* 1405 */                               out.write("\n\n\t ");
/* 1406 */                               if (_jspx_meth_html_005fselect_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                 return;
/* 1408 */                               out.write("\n</span>\n");
/* 1409 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1410 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1414 */                           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1415 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                           }
/*      */                           
/* 1418 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1419 */                           out.write("\n\n\n\n\n\n\n\n\n      \n    \n    <span id=\"cust\" style=\"display:none\"> &nbsp; ");
/* 1420 */                           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 1421 */                           out.write(32);
/* 1422 */                           if (_jspx_meth_c_005fchoose_005f8(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                             return;
/* 1424 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=startTrigger title=\"");
/* 1425 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 1426 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"start\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n   \n  \n   ");
/* 1427 */                           if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                             return;
/* 1429 */                           out.write("    \n    \n\n    ");
/* 1430 */                           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 1431 */                           out.write(32);
/* 1432 */                           out.write(32);
/* 1433 */                           if (_jspx_meth_c_005fchoose_005f9(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                             return;
/* 1435 */                           out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=endTrigger title=\"");
/* 1436 */                           out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 1437 */                           out.write("\"></a> \n      <SCRIPT type=text/javascript>\n\t\t\t\t\t    Calendar.setup({\n\t\t\t\t        inputField     :    \"end\",     // id of the input field\n\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\t\t\t        singleClick    :    true\n\t\t\t\t\t    });\n\t\t\t\t\t </SCRIPT>\n\n <input type=\"button\" name=\"show\" value=\"");
/* 1438 */                           out.print(FormatUtil.getString("Go"));
/* 1439 */                           out.write("\" class=\"buttons btn_highlt\" onClick=\"javascript:reload()\"> \n</span>\n");
/*      */                           
/* 1441 */                           if (aMe.equals("generateHAAvailabilityReport"))
/*      */                           {
/*      */ 
/* 1444 */                             out.write("\n    ");
/* 1445 */                             if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                               return;
/* 1447 */                             out.write(10);
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 1453 */                             out.write(10);
/* 1454 */                             if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                               return;
/* 1456 */                             out.write(10);
/*      */                           }
/*      */                           
/*      */ 
/* 1460 */                           out.write("\n   \n</td>\n      <td width=\"13%\" align=\"right\"  style=\"padding-right:10px;\">\n    ");
/*      */                           
/* 1462 */                           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1463 */                           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1464 */                           _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                           
/* 1466 */                           _jspx_th_c_005fif_005f9.setTest("${isExcel == true }");
/* 1467 */                           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1468 */                           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                             for (;;) {
/* 1470 */                               out.write("\n    <a href=\"javascript:generateReport('excel')\"><img  align=\"center\"  src=\"../images/icon_excel.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/* 1471 */                               out.print(FormatUtil.getString("Excel Report"));
/* 1472 */                               out.write("\"></a> \n    ");
/* 1473 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1474 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1478 */                           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1479 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                           }
/*      */                           
/* 1482 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1483 */                           out.write("\n    \n    <a  href=\"javascript:generateReport('pdf')\"><img  align=\"center\"  src=\"../images/icon_pdf.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/* 1484 */                           out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/* 1485 */                           out.write("\"></a> ");
/*      */                           
/* 1487 */                           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1488 */                           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1489 */                           _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fotherwise_005f6);
/*      */                           
/* 1491 */                           _jspx_th_c_005fif_005f10.setTest("${csv==null}");
/* 1492 */                           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1493 */                           if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                             for (;;) {
/* 1495 */                               out.write("<a class=\"staticlinks\" href=\"javascript:generateCSVReport()\"><img  align=\"center\" src=\"../images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\" title=\"");
/* 1496 */                               out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/* 1497 */                               out.write("\"></a>");
/* 1498 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1499 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1503 */                           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1504 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                           }
/*      */                           
/* 1507 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1508 */                           if (!request.isUserInRole("OPERATOR")) {
/* 1509 */                             out.write(" <a  href=\"javascript:generateEmailReport('emailpdf')\"><img  align=\"center\" src=\"../images/email.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/* 1510 */                             out.print(FormatUtil.getString("Email Report"));
/* 1511 */                             out.write("\" ></a> ");
/*      */                           }
/* 1513 */                           out.write("\n    <a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"../../images/icon_reports_small.gif\" width=\"16\" height=\"16\" border=\"0\" align=\"absbottom\" title=\"");
/* 1514 */                           out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/* 1515 */                           out.write("\"> </a> \n    </td>\n  \n    \n      ");
/* 1516 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1517 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1521 */                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1522 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                       }
/*      */                       
/* 1525 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1526 */                       out.write("\n      ");
/* 1527 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1528 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1532 */                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1533 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                   }
/*      */                   
/* 1536 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1537 */                   out.write(10);
/* 1538 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1539 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1543 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1544 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */               }
/*      */               
/* 1547 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1548 */               out.write(10);
/* 1549 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1550 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1554 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1555 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/* 1558 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1559 */           out.write("\n  </tr>\n</table>\n\n\n");
/* 1560 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1561 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1565 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1566 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 1569 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1570 */         out.write("\n\n</div>\n<script>\nfunction getNewWindow(url, title, width, height, params) {\n\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\t// NO I18N\n\t\t\n        if (params) { winParms += \",\" + params; }\n\t\t\n        try {\n             return (newwindow = window.open(url, title, winParms));\n            \n            \n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n            \n        }\n}\n\nfunction fnchangeAttrib(attribSel){\n\t\n\tvar m = \"getData\";\t\t// NO I18N\n  \t\n\tvar isChildMonitorAttribute=\"false\";\n\tvar restype=\"");
/* 1571 */         out.print(request.getParameter("resourceType"));
/* 1572 */         out.write("\";\n\tvar r = \"");
/* 1573 */         out.print(request.getParameter("resourceid"));
/* 1574 */         out.write("\";\n\tvar b = attribSel.value;\t\n\tvar sep=b.indexOf(\"#\");        \n\tvar a=b.substr(sep+1);\n\t\n\tvar http1=getHTTPObject();\n\tURL=\"/showHistoryData.do?method=ischildAttribute&resourceid=\"+r+\"&attributeid=\"+a;// No I18N\n\thttp1.open(\"GET\",URL,true);\n\thttp1.onreadystatechange=function(){\t\t\n\t\tif(http1.readyState == 4){\n\t\t\tisChildMonitorAttribute=http1.responseText;\n\t\t\tif(isChildMonitorAttribute==\"false\"){\t\t\t\t\n\t\t\t\tdocument.forms[0].action=\"/showHistoryData.do?method=getData&resourceid=\"+r+\"&attributeid=\"+a+\"&period=-7\";\n\t\t\t\tdocument.forms[0].submit();\n\t\t\t\t\n\t\t\t}\n\t\t\telse if(isChildMonitorAttribute==\"true\"){\t\t\t\t\t\t\t\t\n\t\t\t\tdocument.forms[0].action=\"/showReports.do?actionMethod=generateTrendReport&period=1&Report=true&resourceType=\"+restype+\"&workingdays=false&attribute=\"+a+\"&resourceid=\"+r+\"&ischildReport=true\";\n\t\t\t\tdocument.forms[0].submit();\n\t\t\t\t\n\t\t\t\t\t\t\n\t\t\t}\n\t\t}\n\t};\n\thttp1.send(null);\n\t\n}\n\nfunction openPrintWindow(title, width, height, params) {\n\t\n    var    url = window.location.href;\n    ");
/*      */         
/* 1576 */         java.util.Enumeration en = request.getParameterNames();
/*      */         
/* 1578 */         out.write("\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n    \t\n\tdocument.forms[0].target=title;\n        document.forms[0].PRINTER_FRIENDLY.value='true';\n    \n        document.forms[0].target='_top';\n        document.forms[0].PRINTER_FRIENDLY.value='false';\n               ");
/*      */         
/*      */ 
/* 1581 */         out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/* 1582 */         while (en.hasMoreElements()) {
/* 1583 */           String reqKey = (String)en.nextElement();
/* 1584 */           String reqValue = request.getParameter(reqKey);
/* 1585 */           if ((!reqKey.equals("message")) && (!reqKey.equals("mailMsg")))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1591 */             out.print("&" + reqKey + "=" + reqValue);
/*      */           }
/*      */         }
/* 1594 */         out.println("\";");
/*      */         
/* 1596 */         out.write("\n                    if (url.indexOf(\"?\") != -1) {\n\t                url=url + \"&\" + urlToAdd;\n\t            } else {\n\t                url=url + \"?\" + urlToAdd;\n            }\n        var newwindow = getNewWindow(url,title,width,height,params);\n       \n        newwindow.focus();\n        return newwindow;\n    } else {\n   \n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\t\t// NO I18N\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\t\t// NO I18N\n            }\n            \n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n   \n}\nonload = function() {\n\n}\n\n\nfunction showCustomFieldValues(http1,divname)\n{\n        if(http1.readyState==4)\n        {\n        \t\n        \tjQuery(\"#\"+divname).html(http1.responseText)\n        \tjQuery(\"#\"+divname).show()\n        }\n}\n\nfunction loadURLType(option,obj,divname){\n\t\n        \n\tif(option.indexOf(\"SYSTEMDATA\") != -1 || option.indexOf(\"USERDATA\") != -1 || option.indexOf(\"LOCATION_NAME\") != -1 || option.indexOf(\"USERNAME\") != -1 || option.indexOf(\"VALUEID\") != -1){\n");
/* 1597 */         out.write("\t\tvar http1=getHTTPObject();\n\t\thttp1.onreadystatechange= function (){showCustomFieldValues(http1,divname);};//No I18N\n\t\tvar monitorType = \"");
/* 1598 */         out.print(request.getParameter("resourceType"));
/* 1599 */         out.write("\";\n\t\t");
/*      */         
/* 1601 */         IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1602 */         _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1603 */         _jspx_th_c_005fif_005f11.setParent(null);
/*      */         
/* 1605 */         _jspx_th_c_005fif_005f11.setTest("${capacityPlanningFilter == true}");
/* 1606 */         int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1607 */         if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */           for (;;) {
/* 1609 */             out.write("\n\t\tmonitorType = \"");
/* 1610 */             out.print(request.getAttribute("capacityPlanningResourceTypes"));
/* 1611 */             out.write("\"\n\t\t");
/* 1612 */             int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1613 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1617 */         if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1618 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*      */         }
/*      */         else {
/* 1621 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1622 */           out.write("\n\t\tif(option.indexOf(\"$\") != -1){\n\t\t\t\n\t\t\t");
/* 1623 */           if (_jspx_meth_c_005fchoose_005f10(_jspx_page_context))
/*      */             return;
/* 1625 */           out.write("\n\t\t\t\n\t\t\tURL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&optionSel=\"+option.substring(option.indexOf(\"$\")+1)+\"&monitortype=\"+monitorType; // NO I18N\n\n\t\t}else{\n                        URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&forBulkAssign=false&monitortype=\"+monitorType+\"&optionSel=\";//No I18N\n\t\t}\n\t\t\n                                http1.open(\"GET\",URL,true);//No I18N\n                        \t\thttp1.send(null);//No I18N\n\t\t\n\t}else{\n\t\tjQuery('#'+divname).hide();\n\t}\n\t}\n\n                           $(document).ready(function() {//No I18N\n                        \t   \n                        \t\tjQuery(\"#cust\").hide()\t\t// NO I18N\n                        \t\tvar p= '");
/* 1626 */           if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */             return;
/* 1628 */           out.write("' \n                        \t\tif(p == 4)\n                        \t\t{\n                        \t\t\tjQuery(\"#cust\").show()\t\t// NO I18N\n                        \t\t}\n                        \t\tvar s=document.forms[0].interval.value;\n                        \t\t         if(s=='customtime'){\n                        \t\t        \t jQuery(\"#showcustom\").show()\t// NO I18N\n                        \t\t         }else{\n                        \t\t        \t jQuery(\"#showcustom\").hide()\t// NO I18N\n                        \t\t}\n\n                        \t   \n                        \t   \n                        \t   var showfilter = 'false'\n                        \t   \n\t\t\t\t\t\t\t\tshowfilter = '");
/* 1629 */           if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */             return;
/* 1631 */           out.write("'\n\t\t\t\t\t\t\t\tif(showfilter == 'true'){\n\t\t\t\t\t\t\t\t\tjQuery(\"#showCustomFieldFilter\").show();\t// NO I18N\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 1632 */           if (_jspx_meth_c_005fif_005f12(_jspx_page_context))
/*      */             return;
/* 1634 */           out.write("\n                           \t\t\t\n\t\t\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\t\t\tjQuery(\"#showCustomFieldFilter\").hide();\t\t// NO I18N\n\t\t\t\t\t\t\t\t}\n                        });\n                </script>\n");
/*      */         }
/* 1636 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1637 */         out = _jspx_out;
/* 1638 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1639 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1640 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1643 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1649 */     PageContext pageContext = _jspx_page_context;
/* 1650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1652 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1653 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1654 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1656 */     _jspx_th_c_005fforEach_005f0.setVar("eachtag");
/*      */     
/* 1658 */     _jspx_th_c_005fforEach_005f0.setItems("${tags}");
/* 1659 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1661 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1662 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1664 */           out.write("\n\t\t\t\t\t\t\t<option  style=\"background-color: #FFF8C6\" value=\"");
/* 1665 */           boolean bool; if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1666 */             return true;
/* 1667 */           out.write(34);
/* 1668 */           out.write(62);
/* 1669 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1670 */             return true;
/* 1671 */           out.write("</option>\n                            ");
/* 1672 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1673 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1677 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1678 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1681 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f0; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 1682 */         out = _jspx_page_context.popBody(); }
/* 1683 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1685 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1686 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1693 */     PageContext pageContext = _jspx_page_context;
/* 1694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1696 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1697 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1698 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1700 */     _jspx_th_c_005fout_005f0.setValue("${eachtag.labelalias}");
/* 1701 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1702 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1704 */       return true;
/*      */     }
/* 1706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1712 */     PageContext pageContext = _jspx_page_context;
/* 1713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1715 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1716 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1717 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1719 */     _jspx_th_c_005fout_005f1.setValue("${eachtag.label}");
/* 1720 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1721 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1722 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1723 */       return true;
/*      */     }
/* 1725 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1731 */     PageContext pageContext = _jspx_page_context;
/* 1732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1734 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1735 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1736 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1738 */     _jspx_th_c_005fforEach_005f1.setVar("eachtag");
/*      */     
/* 1740 */     _jspx_th_c_005fforEach_005f1.setItems("${tags}");
/* 1741 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1743 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1744 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1746 */           out.write("\n\t\t\t\t\t\t\t\t\t<option  style=\"background-color: #FFF8C6\" value=\"");
/* 1747 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1748 */             return true;
/* 1749 */           out.write(34);
/* 1750 */           out.write(62);
/* 1751 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1752 */             return true;
/* 1753 */           out.write("</option>\n\t\t\t\t           \t\t");
/* 1754 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1755 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1759 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1760 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1763 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f1; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 1764 */         out = _jspx_page_context.popBody(); }
/* 1765 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1767 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1768 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1775 */     PageContext pageContext = _jspx_page_context;
/* 1776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1778 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1779 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1780 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1782 */     _jspx_th_c_005fout_005f2.setValue("${eachtag.labelalias}");
/* 1783 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1784 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1786 */       return true;
/*      */     }
/* 1788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1794 */     PageContext pageContext = _jspx_page_context;
/* 1795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1797 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1798 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1799 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1801 */     _jspx_th_c_005fout_005f3.setValue("${eachtag.label}");
/* 1802 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1803 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1805 */       return true;
/*      */     }
/* 1807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1813 */     PageContext pageContext = _jspx_page_context;
/* 1814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1816 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1817 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 1818 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1820 */     _jspx_th_c_005fforEach_005f2.setVar("eachtag");
/*      */     
/* 1822 */     _jspx_th_c_005fforEach_005f2.setItems("${tags}");
/* 1823 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 1825 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 1826 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 1828 */           out.write("\n\t\t\t\t\t\t\t\t\t\t\t<option  style=\"background-color: #FFF8C6\" value=\"");
/* 1829 */           boolean bool; if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1830 */             return true;
/* 1831 */           out.write(34);
/* 1832 */           out.write(62);
/* 1833 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1834 */             return true;
/* 1835 */           out.write("</option>\n\t\t\t\t                            ");
/* 1836 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 1837 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1841 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 1842 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1845 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f2; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 1846 */         out = _jspx_page_context.popBody(); }
/* 1847 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 1849 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 1850 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 1852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1857 */     PageContext pageContext = _jspx_page_context;
/* 1858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1860 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1861 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1862 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1864 */     _jspx_th_c_005fout_005f4.setValue("${eachtag.labelalias}");
/* 1865 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1866 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1868 */       return true;
/*      */     }
/* 1870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1876 */     PageContext pageContext = _jspx_page_context;
/* 1877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1879 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1880 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1881 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1883 */     _jspx_th_c_005fout_005f5.setValue("${eachtag.label}");
/* 1884 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1885 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1887 */       return true;
/*      */     }
/* 1889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1895 */     PageContext pageContext = _jspx_page_context;
/* 1896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1898 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1899 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1900 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1902 */     _jspx_th_c_005fif_005f3.setTest("${isExcel == true }");
/* 1903 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1904 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1906 */         out.write("\n    ");
/* 1907 */         if (_jspx_meth_logic_005fnotEmpty_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 1908 */           return true;
/* 1909 */         out.write(10);
/* 1910 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1911 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1915 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1916 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1917 */       return true;
/*      */     }
/* 1919 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1925 */     PageContext pageContext = _jspx_page_context;
/* 1926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1928 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 1929 */     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 1930 */     _jspx_th_logic_005fnotEmpty_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1932 */     _jspx_th_logic_005fnotEmpty_005f0.setName("ReportForm");
/*      */     
/* 1934 */     _jspx_th_logic_005fnotEmpty_005f0.setProperty("businessrules");
/* 1935 */     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 1936 */     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */       for (;;) {
/* 1938 */         out.write("\n    \n    ");
/* 1939 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/* 1940 */           return true;
/* 1941 */         out.write(" \n\n    ");
/* 1942 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 1943 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1947 */     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 1948 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 1949 */       return true;
/*      */     }
/* 1951 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 1952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1957 */     PageContext pageContext = _jspx_page_context;
/* 1958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1960 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 1961 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 1962 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 1964 */     _jspx_th_html_005fselect_005f3.setProperty("businessPeriod");
/*      */     
/* 1966 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 1968 */     _jspx_th_html_005fselect_005f3.setStyle("width:180px; margin-bottom:2px;");
/*      */     
/* 1970 */     _jspx_th_html_005fselect_005f3.setOnchange("javascript:fnBusinessRule(this)");
/* 1971 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 1972 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 1973 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1974 */         out = _jspx_page_context.pushBody();
/* 1975 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 1976 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1979 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 1980 */           return true;
/* 1981 */         out.write(32);
/* 1982 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 1983 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1986 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1987 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1990 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 1991 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 1992 */       return true;
/*      */     }
/* 1994 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 1995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2000 */     PageContext pageContext = _jspx_page_context;
/* 2001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2003 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2004 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 2005 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 2007 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("businessRuleNames");
/* 2008 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 2009 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 2010 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2011 */       return true;
/*      */     }
/* 2013 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 2014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2019 */     PageContext pageContext = _jspx_page_context;
/* 2020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2022 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2023 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2024 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2025 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2026 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 2028 */         out.write(32);
/* 2029 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2030 */           return true;
/* 2031 */         out.write(32);
/* 2032 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2033 */           return true;
/* 2034 */         out.write(32);
/* 2035 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2036 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2040 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2041 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2042 */       return true;
/*      */     }
/* 2044 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2050 */     PageContext pageContext = _jspx_page_context;
/* 2051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2053 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2054 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2055 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2057 */     _jspx_th_c_005fwhen_005f3.setTest("${requestScope.ReportForm.thisstart != ''}");
/* 2058 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2059 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 2061 */         out.write(" \n      ");
/* 2062 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 2063 */           return true;
/* 2064 */         out.write(" \n      ");
/* 2065 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2066 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2070 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2071 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2072 */       return true;
/*      */     }
/* 2074 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2080 */     PageContext pageContext = _jspx_page_context;
/* 2081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2083 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2084 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2085 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 2087 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 2089 */     _jspx_th_html_005ftext_005f0.setProperty("thisstart");
/*      */     
/* 2091 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 2093 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 2095 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetLastStartTime(this.value)");
/*      */     
/* 2097 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 2098 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2099 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2100 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2101 */       return true;
/*      */     }
/* 2103 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2109 */     PageContext pageContext = _jspx_page_context;
/* 2110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2112 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2113 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2114 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2115 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2116 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2118 */         out.write(32);
/* 2119 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 2120 */           return true;
/* 2121 */         out.write(" \n      ");
/* 2122 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2123 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2127 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2128 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2129 */       return true;
/*      */     }
/* 2131 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2137 */     PageContext pageContext = _jspx_page_context;
/* 2138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2140 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2141 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 2142 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 2144 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*      */     
/* 2146 */     _jspx_th_html_005ftext_005f1.setProperty("thisstart");
/*      */     
/* 2148 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 2150 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 2152 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetLastStartTime(this.value)");
/*      */     
/* 2154 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 2155 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 2156 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 2157 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2158 */       return true;
/*      */     }
/* 2160 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2166 */     PageContext pageContext = _jspx_page_context;
/* 2167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2169 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2170 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2171 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2172 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2173 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2175 */         out.write(32);
/* 2176 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2177 */           return true;
/* 2178 */         out.write(32);
/* 2179 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2180 */           return true;
/* 2181 */         out.write(32);
/* 2182 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2183 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2187 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2188 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2189 */       return true;
/*      */     }
/* 2191 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2197 */     PageContext pageContext = _jspx_page_context;
/* 2198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2200 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2201 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2202 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2204 */     _jspx_th_c_005fwhen_005f4.setTest("${requestScope.ReportForm.thisend != ''}");
/* 2205 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2206 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 2208 */         out.write(" \n      ");
/* 2209 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 2210 */           return true;
/* 2211 */         out.write(" \n      ");
/* 2212 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2213 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2217 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2218 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2219 */       return true;
/*      */     }
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2227 */     PageContext pageContext = _jspx_page_context;
/* 2228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2230 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2231 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 2232 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2234 */     _jspx_th_html_005ftext_005f2.setProperty("thisend");
/*      */     
/* 2236 */     _jspx_th_html_005ftext_005f2.setSize("15");
/*      */     
/* 2238 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 2240 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 2242 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetLastEndTime(this.value)");
/*      */     
/* 2244 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/* 2245 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 2246 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 2247 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2248 */       return true;
/*      */     }
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2256 */     PageContext pageContext = _jspx_page_context;
/* 2257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2259 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2260 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2261 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2262 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2263 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 2265 */         out.write(32);
/* 2266 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 2267 */           return true;
/* 2268 */         out.write(" \n      ");
/* 2269 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2270 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2274 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2275 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2276 */       return true;
/*      */     }
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2284 */     PageContext pageContext = _jspx_page_context;
/* 2285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2287 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2288 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2289 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2291 */     _jspx_th_html_005ftext_005f3.setProperty("thisend");
/*      */     
/* 2293 */     _jspx_th_html_005ftext_005f3.setSize("15");
/*      */     
/* 2295 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 2297 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 2299 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetLastEndTime(this.value)");
/*      */     
/* 2301 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/* 2302 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2303 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2304 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2305 */       return true;
/*      */     }
/* 2307 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2313 */     PageContext pageContext = _jspx_page_context;
/* 2314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2316 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2317 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2318 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2319 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2320 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 2322 */         out.write(32);
/* 2323 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2324 */           return true;
/* 2325 */         out.write(32);
/* 2326 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 2327 */           return true;
/* 2328 */         out.write(32);
/* 2329 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2330 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2334 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2335 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2336 */       return true;
/*      */     }
/* 2338 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2344 */     PageContext pageContext = _jspx_page_context;
/* 2345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2347 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2348 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2349 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2351 */     _jspx_th_c_005fwhen_005f5.setTest("${requestScope.ReportForm.laststart != ''}");
/* 2352 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2353 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2355 */         out.write(" \n      ");
/* 2356 */         if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/* 2357 */           return true;
/* 2358 */         out.write(" \n      ");
/* 2359 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2360 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2364 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2365 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2366 */       return true;
/*      */     }
/* 2368 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2374 */     PageContext pageContext = _jspx_page_context;
/* 2375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2377 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2378 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2379 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 2381 */     _jspx_th_html_005ftext_005f4.setSize("10");
/*      */     
/* 2383 */     _jspx_th_html_005ftext_005f4.setProperty("laststart");
/*      */     
/* 2385 */     _jspx_th_html_005ftext_005f4.setStyleId("start");
/*      */     
/* 2387 */     _jspx_th_html_005ftext_005f4.setReadonly(true);
/*      */     
/* 2389 */     _jspx_th_html_005ftext_005f4.setOnchange("javascript:fnSetThisStartTime(this.value)");
/*      */     
/* 2391 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/* 2392 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2393 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2394 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2395 */       return true;
/*      */     }
/* 2397 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2403 */     PageContext pageContext = _jspx_page_context;
/* 2404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2406 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2407 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2408 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 2409 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2410 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2412 */         out.write(32);
/* 2413 */         if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 2414 */           return true;
/* 2415 */         out.write(" \n      ");
/* 2416 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2421 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2422 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2423 */       return true;
/*      */     }
/* 2425 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2431 */     PageContext pageContext = _jspx_page_context;
/* 2432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2434 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2435 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 2436 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 2438 */     _jspx_th_html_005ftext_005f5.setSize("15");
/*      */     
/* 2440 */     _jspx_th_html_005ftext_005f5.setProperty("laststart");
/*      */     
/* 2442 */     _jspx_th_html_005ftext_005f5.setStyleId("start1");
/*      */     
/* 2444 */     _jspx_th_html_005ftext_005f5.setReadonly(true);
/*      */     
/* 2446 */     _jspx_th_html_005ftext_005f5.setOnchange("javascript:fnSetThisStartTime(this.value)");
/*      */     
/* 2448 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/* 2449 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 2450 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 2451 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2452 */       return true;
/*      */     }
/* 2454 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2455 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2460 */     PageContext pageContext = _jspx_page_context;
/* 2461 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2463 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2464 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2465 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2466 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2467 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 2469 */         out.write(32);
/* 2470 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 2471 */           return true;
/* 2472 */         out.write(32);
/* 2473 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 2474 */           return true;
/* 2475 */         out.write(32);
/* 2476 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 2477 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2481 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 2482 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 2483 */       return true;
/*      */     }
/* 2485 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 2486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2491 */     PageContext pageContext = _jspx_page_context;
/* 2492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2494 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2495 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2496 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 2498 */     _jspx_th_c_005fwhen_005f6.setTest("${requestScope.ReportForm.lastend != ''}");
/* 2499 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2500 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 2502 */         out.write(" \n      ");
/* 2503 */         if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/* 2504 */           return true;
/* 2505 */         out.write(" \n      ");
/* 2506 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2507 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2511 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2512 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2513 */       return true;
/*      */     }
/* 2515 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2516 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2521 */     PageContext pageContext = _jspx_page_context;
/* 2522 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2524 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2525 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 2526 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 2528 */     _jspx_th_html_005ftext_005f6.setProperty("lastend");
/*      */     
/* 2530 */     _jspx_th_html_005ftext_005f6.setSize("15");
/*      */     
/* 2532 */     _jspx_th_html_005ftext_005f6.setStyleId("end");
/*      */     
/* 2534 */     _jspx_th_html_005ftext_005f6.setReadonly(true);
/*      */     
/* 2536 */     _jspx_th_html_005ftext_005f6.setOnchange("javascript:fnSetThisEndTime(this.value)");
/*      */     
/* 2538 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/* 2539 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 2540 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 2541 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2542 */       return true;
/*      */     }
/* 2544 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2550 */     PageContext pageContext = _jspx_page_context;
/* 2551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2553 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2554 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2555 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 2556 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2557 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 2559 */         out.write(32);
/* 2560 */         if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/* 2561 */           return true;
/* 2562 */         out.write(" \n      ");
/* 2563 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2564 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2568 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2569 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2570 */       return true;
/*      */     }
/* 2572 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2578 */     PageContext pageContext = _jspx_page_context;
/* 2579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2581 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2582 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 2583 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2585 */     _jspx_th_html_005ftext_005f7.setProperty("lastend");
/*      */     
/* 2587 */     _jspx_th_html_005ftext_005f7.setSize("15");
/*      */     
/* 2589 */     _jspx_th_html_005ftext_005f7.setStyleId("end1");
/*      */     
/* 2591 */     _jspx_th_html_005ftext_005f7.setReadonly(true);
/*      */     
/* 2593 */     _jspx_th_html_005ftext_005f7.setOnchange("javascript:fnSetThisEndTime(this.value)");
/*      */     
/* 2595 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/* 2596 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 2597 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 2598 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2599 */       return true;
/*      */     }
/* 2601 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2607 */     PageContext pageContext = _jspx_page_context;
/* 2608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2610 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 2611 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 2612 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2614 */     _jspx_th_html_005fselect_005f6.setProperty("sevenThirtyAttrib");
/*      */     
/* 2616 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext");
/*      */     
/* 2618 */     _jspx_th_html_005fselect_005f6.setStyle("width:100%");
/*      */     
/* 2620 */     _jspx_th_html_005fselect_005f6.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 2622 */     _jspx_th_html_005fselect_005f6.setOnblur("this.style.width='100%'");
/*      */     
/* 2624 */     _jspx_th_html_005fselect_005f6.setOnchange("this.style.width='100%';fnchangeAttrib(this)");
/* 2625 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 2626 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 2627 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 2628 */         out = _jspx_page_context.pushBody();
/* 2629 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 2630 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2633 */         out.write(" \n        \t");
/* 2634 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 2635 */           return true;
/* 2636 */         out.write("\n        ");
/* 2637 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 2638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2641 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 2642 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2645 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 2646 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f6);
/* 2647 */       return true;
/*      */     }
/* 2649 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f6);
/* 2650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2655 */     PageContext pageContext = _jspx_page_context;
/* 2656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2658 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2659 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 2660 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 2662 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("sevenThirtyAttribCln");
/* 2663 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 2664 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 2665 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 2666 */       return true;
/*      */     }
/* 2668 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 2669 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f8(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2674 */     PageContext pageContext = _jspx_page_context;
/* 2675 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2677 */     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2678 */     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 2679 */     _jspx_th_c_005fchoose_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/* 2680 */     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 2681 */     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */       for (;;) {
/* 2683 */         out.write(32);
/* 2684 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 2685 */           return true;
/* 2686 */         out.write(32);
/* 2687 */         if (_jspx_meth_c_005fotherwise_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 2688 */           return true;
/* 2689 */         out.write(32);
/* 2690 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 2691 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2695 */     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 2696 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 2697 */       return true;
/*      */     }
/* 2699 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 2700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2705 */     PageContext pageContext = _jspx_page_context;
/* 2706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2708 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2709 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2710 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/*      */     
/* 2712 */     _jspx_th_c_005fwhen_005f8.setTest("${requestScope.ReportForm.startDate != ''}");
/* 2713 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2714 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 2716 */         out.write(32);
/* 2717 */         out.write(" \n      ");
/* 2718 */         if (_jspx_meth_html_005ftext_005f8(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/* 2719 */           return true;
/* 2720 */         out.write(" \n      ");
/* 2721 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2722 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2726 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2727 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2728 */       return true;
/*      */     }
/* 2730 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2731 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2736 */     PageContext pageContext = _jspx_page_context;
/* 2737 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2739 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2740 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 2741 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 2743 */     _jspx_th_html_005ftext_005f8.setSize("8");
/*      */     
/* 2745 */     _jspx_th_html_005ftext_005f8.setProperty("startDate");
/*      */     
/* 2747 */     _jspx_th_html_005ftext_005f8.setStyleId("start");
/*      */     
/* 2749 */     _jspx_th_html_005ftext_005f8.setReadonly(true);
/*      */     
/* 2751 */     _jspx_th_html_005ftext_005f8.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 2753 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/* 2754 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 2755 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 2756 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 2757 */       return true;
/*      */     }
/* 2759 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 2760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2765 */     PageContext pageContext = _jspx_page_context;
/* 2766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2768 */     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2769 */     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 2770 */     _jspx_th_c_005fotherwise_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/* 2771 */     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 2772 */     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */       for (;;) {
/* 2774 */         out.write(32);
/* 2775 */         if (_jspx_meth_html_005ftext_005f9(_jspx_th_c_005fotherwise_005f8, _jspx_page_context))
/* 2776 */           return true;
/* 2777 */         out.write(" \n      ");
/* 2778 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 2779 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2783 */     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 2784 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 2785 */       return true;
/*      */     }
/* 2787 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 2788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2793 */     PageContext pageContext = _jspx_page_context;
/* 2794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2796 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2797 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 2798 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 2800 */     _jspx_th_html_005ftext_005f9.setSize("12");
/*      */     
/* 2802 */     _jspx_th_html_005ftext_005f9.setProperty("startDate");
/*      */     
/* 2804 */     _jspx_th_html_005ftext_005f9.setStyleId("start");
/*      */     
/* 2806 */     _jspx_th_html_005ftext_005f9.setReadonly(true);
/*      */     
/* 2808 */     _jspx_th_html_005ftext_005f9.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 2810 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext");
/* 2811 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 2812 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 2813 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 2814 */       return true;
/*      */     }
/* 2816 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 2817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2822 */     PageContext pageContext = _jspx_page_context;
/* 2823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2825 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2826 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2827 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 2829 */     _jspx_th_c_005fif_005f7.setTest("${empty param.ischildReport || param.ischildReport==null}");
/* 2830 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2831 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2833 */         out.write("\n  \t &nbsp;&nbsp;&nbsp;\n   ");
/* 2834 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2839 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2840 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2841 */       return true;
/*      */     }
/* 2843 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f9(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2849 */     PageContext pageContext = _jspx_page_context;
/* 2850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2852 */     ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2853 */     _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 2854 */     _jspx_th_c_005fchoose_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/* 2855 */     int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 2856 */     if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */       for (;;) {
/* 2858 */         out.write(32);
/* 2859 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f9, _jspx_page_context))
/* 2860 */           return true;
/* 2861 */         out.write(32);
/* 2862 */         if (_jspx_meth_c_005fotherwise_005f9(_jspx_th_c_005fchoose_005f9, _jspx_page_context))
/* 2863 */           return true;
/* 2864 */         out.write(32);
/* 2865 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 2866 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2870 */     if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 2871 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 2872 */       return true;
/*      */     }
/* 2874 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 2875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2880 */     PageContext pageContext = _jspx_page_context;
/* 2881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2883 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2884 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 2885 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/*      */     
/* 2887 */     _jspx_th_c_005fwhen_005f9.setTest("${requestScope.ReportForm.endDate != ''}");
/* 2888 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 2889 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 2891 */         out.write(" \n      ");
/* 2892 */         if (_jspx_meth_html_005ftext_005f10(_jspx_th_c_005fwhen_005f9, _jspx_page_context))
/* 2893 */           return true;
/* 2894 */         out.write(" \n      ");
/* 2895 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 2896 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2900 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 2901 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 2902 */       return true;
/*      */     }
/* 2904 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 2905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_c_005fwhen_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2910 */     PageContext pageContext = _jspx_page_context;
/* 2911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2913 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2914 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 2915 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f9);
/*      */     
/* 2917 */     _jspx_th_html_005ftext_005f10.setProperty("endDate");
/*      */     
/* 2919 */     _jspx_th_html_005ftext_005f10.setSize("8");
/*      */     
/* 2921 */     _jspx_th_html_005ftext_005f10.setStyleId("end");
/*      */     
/* 2923 */     _jspx_th_html_005ftext_005f10.setReadonly(true);
/*      */     
/* 2925 */     _jspx_th_html_005ftext_005f10.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 2927 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext");
/* 2928 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 2929 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 2930 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 2931 */       return true;
/*      */     }
/* 2933 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 2934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f9(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2939 */     PageContext pageContext = _jspx_page_context;
/* 2940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2942 */     OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2943 */     _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 2944 */     _jspx_th_c_005fotherwise_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/* 2945 */     int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 2946 */     if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */       for (;;) {
/* 2948 */         out.write(32);
/* 2949 */         if (_jspx_meth_html_005ftext_005f11(_jspx_th_c_005fotherwise_005f9, _jspx_page_context))
/* 2950 */           return true;
/* 2951 */         out.write(" \n      ");
/* 2952 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 2953 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2957 */     if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 2958 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 2959 */       return true;
/*      */     }
/* 2961 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 2962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_c_005fotherwise_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2967 */     PageContext pageContext = _jspx_page_context;
/* 2968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2970 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 2971 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 2972 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f9);
/*      */     
/* 2974 */     _jspx_th_html_005ftext_005f11.setProperty("endDate");
/*      */     
/* 2976 */     _jspx_th_html_005ftext_005f11.setSize("12");
/*      */     
/* 2978 */     _jspx_th_html_005ftext_005f11.setStyleId("end");
/*      */     
/* 2980 */     _jspx_th_html_005ftext_005f11.setReadonly(true);
/*      */     
/* 2982 */     _jspx_th_html_005ftext_005f11.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 2984 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext");
/* 2985 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 2986 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 2987 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 2988 */       return true;
/*      */     }
/* 2990 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 2991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2996 */     PageContext pageContext = _jspx_page_context;
/* 2997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2999 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3000 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3001 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3003 */     _jspx_th_logic_005fnotEmpty_005f1.setName("ReportForm");
/*      */     
/* 3005 */     _jspx_th_logic_005fnotEmpty_005f1.setProperty("businessrules");
/* 3006 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3007 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 3009 */         out.write("\n   \n     &nbsp;");
/* 3010 */         if (_jspx_meth_html_005fselect_005f7(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 3011 */           return true;
/* 3012 */         out.write(" \n   \n    ");
/* 3013 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3014 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3018 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3019 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3020 */       return true;
/*      */     }
/* 3022 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3028 */     PageContext pageContext = _jspx_page_context;
/* 3029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3031 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3032 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 3033 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 3035 */     _jspx_th_html_005fselect_005f7.setProperty("businessPeriod");
/*      */     
/* 3037 */     _jspx_th_html_005fselect_005f7.setStyleClass("formtext");
/*      */     
/* 3039 */     _jspx_th_html_005fselect_005f7.setStyle("width:180px");
/*      */     
/* 3041 */     _jspx_th_html_005fselect_005f7.setOnchange("javascript:fnBusinessRule(this)");
/* 3042 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 3043 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 3044 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3045 */         out = _jspx_page_context.pushBody();
/* 3046 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 3047 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3050 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 3051 */           return true;
/* 3052 */         out.write(32);
/* 3053 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 3054 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3057 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3058 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3061 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 3062 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 3063 */       return true;
/*      */     }
/* 3065 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 3066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3071 */     PageContext pageContext = _jspx_page_context;
/* 3072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3074 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3075 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3076 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 3078 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("businessRuleNames");
/* 3079 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3080 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3081 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3082 */       return true;
/*      */     }
/* 3084 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3090 */     PageContext pageContext = _jspx_page_context;
/* 3091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3093 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3094 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3095 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3097 */     _jspx_th_c_005fif_005f8.setTest("${isExcel == true }");
/* 3098 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3099 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3101 */         out.write("\n    ");
/* 3102 */         if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 3103 */           return true;
/* 3104 */         out.write(10);
/* 3105 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3106 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3110 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3111 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3112 */       return true;
/*      */     }
/* 3114 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3120 */     PageContext pageContext = _jspx_page_context;
/* 3121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3123 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3124 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3125 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 3127 */     _jspx_th_logic_005fnotEmpty_005f2.setName("ReportForm");
/*      */     
/* 3129 */     _jspx_th_logic_005fnotEmpty_005f2.setProperty("businessrules");
/* 3130 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3131 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 3133 */         out.write("\n   \n     &nbsp;");
/* 3134 */         if (_jspx_meth_html_005fselect_005f8(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 3135 */           return true;
/* 3136 */         out.write(" \n   \n    ");
/* 3137 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3138 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3142 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3143 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3144 */       return true;
/*      */     }
/* 3146 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f8(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3152 */     PageContext pageContext = _jspx_page_context;
/* 3153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3155 */     SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3156 */     _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 3157 */     _jspx_th_html_005fselect_005f8.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 3159 */     _jspx_th_html_005fselect_005f8.setProperty("businessPeriod");
/*      */     
/* 3161 */     _jspx_th_html_005fselect_005f8.setStyleClass("formtext1");
/*      */     
/* 3163 */     _jspx_th_html_005fselect_005f8.setStyle("width:180px");
/*      */     
/* 3165 */     _jspx_th_html_005fselect_005f8.setOnchange("javascript:fnBusinessRule(this)");
/* 3166 */     int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 3167 */     if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 3168 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 3169 */         out = _jspx_page_context.pushBody();
/* 3170 */         _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 3171 */         _jspx_th_html_005fselect_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3174 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f8, _jspx_page_context))
/* 3175 */           return true;
/* 3176 */         out.write(32);
/* 3177 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 3178 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3181 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 3182 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3185 */     if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 3186 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8);
/* 3187 */       return true;
/*      */     }
/* 3189 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8);
/* 3190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3195 */     PageContext pageContext = _jspx_page_context;
/* 3196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3198 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3199 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 3200 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f8);
/*      */     
/* 3202 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("businessRuleNames");
/* 3203 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 3204 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 3205 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3206 */       return true;
/*      */     }
/* 3208 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3214 */     PageContext pageContext = _jspx_page_context;
/* 3215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3217 */     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3218 */     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3219 */     _jspx_th_c_005fchoose_005f10.setParent(null);
/* 3220 */     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3221 */     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */       for (;;) {
/* 3223 */         out.write("\n\t\t\t\t");
/* 3224 */         if (_jspx_meth_c_005fwhen_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 3225 */           return true;
/* 3226 */         out.write("\n\t\t\t\t");
/* 3227 */         if (_jspx_meth_c_005fotherwise_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 3228 */           return true;
/* 3229 */         out.write("\n\t\t\t");
/* 3230 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3235 */     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3236 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3237 */       return true;
/*      */     }
/* 3239 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3245 */     PageContext pageContext = _jspx_page_context;
/* 3246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3248 */     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3249 */     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3250 */     _jspx_th_c_005fwhen_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/*      */     
/* 3252 */     _jspx_th_c_005fwhen_005f10.setTest("${topVisible == true}");
/* 3253 */     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3254 */     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */       for (;;) {
/* 3256 */         out.write("\n\t\t\t\t\tjQuery(\"[name=customFieldNumberOfRows]\").val(jQuery(\"[name=numberOfRows]\").val())\t// NO I18N\n\t\t\t\t\tjQuery(\"[name=numberOfRows]\").val(option.substring(0,option.indexOf(\"$\")))\t\t// NO I18N\n\t\t\t\t\tjQuery(\"[name=numberOfRows]\").css('backgroundColor','#FFF8C6')\t\t// NO I18N\n\t\t\t\t");
/* 3257 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3262 */     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3263 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3264 */       return true;
/*      */     }
/* 3266 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3272 */     PageContext pageContext = _jspx_page_context;
/* 3273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3275 */     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3276 */     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3277 */     _jspx_th_c_005fotherwise_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/* 3278 */     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3279 */     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */       for (;;) {
/* 3281 */         out.write("\n\t\t\t\t\tjQuery(\"#groupviewfilterby\").val(option.substring(0,option.indexOf(\"$\")))\t\t// NO I18N\n\t\t\t\t");
/* 3282 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3287 */     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3288 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3289 */       return true;
/*      */     }
/* 3291 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3297 */     PageContext pageContext = _jspx_page_context;
/* 3298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3300 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3301 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3302 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 3304 */     _jspx_th_c_005fout_005f6.setValue("${param.period}");
/* 3305 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3306 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3307 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3308 */       return true;
/*      */     }
/* 3310 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3316 */     PageContext pageContext = _jspx_page_context;
/* 3317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3319 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3320 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3321 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 3323 */     _jspx_th_c_005fout_005f7.setValue("${param.showcfFilter}");
/* 3324 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3325 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3326 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3327 */       return true;
/*      */     }
/* 3329 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3335 */     PageContext pageContext = _jspx_page_context;
/* 3336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3338 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3339 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3340 */     _jspx_th_c_005fif_005f12.setParent(null);
/*      */     
/* 3342 */     _jspx_th_c_005fif_005f12.setTest("${param.customfield == true}");
/* 3343 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3344 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 3346 */         out.write("\n                        \t    \tloadURLType('");
/* 3347 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 3348 */           return true;
/* 3349 */         out.write("','','FilterBy')\t\t// NO I18N\n                        \t    \t");
/* 3350 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3351 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3355 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3356 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3357 */       return true;
/*      */     }
/* 3359 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3365 */     PageContext pageContext = _jspx_page_context;
/* 3366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3368 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3369 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3370 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 3372 */     _jspx_th_c_005fout_005f8.setValue("${param.customFieldValue}");
/* 3373 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3374 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3375 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3376 */       return true;
/*      */     }
/* 3378 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3379 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\ReportPeriod_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */