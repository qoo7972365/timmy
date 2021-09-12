/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class MonitorLevelAttributes_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  50 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  57 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  60 */     JspWriter out = null;
/*  61 */     Object page = this;
/*  62 */     JspWriter _jspx_out = null;
/*  63 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  67 */       response.setContentType("text/html");
/*  68 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  70 */       _jspx_page_context = pageContext;
/*  71 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  72 */       ServletConfig config = pageContext.getServletConfig();
/*  73 */       session = pageContext.getSession();
/*  74 */       out = pageContext.getOut();
/*  75 */       _jspx_out = out;
/*     */       
/*  77 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*  78 */       if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*     */         return;
/*  80 */       out.write(10);
/*     */       
/*  82 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  83 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  84 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/*  86 */       _jspx_th_c_005fif_005f0.setTest("${monitorattributetype == \"mg\"}");
/*  87 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  88 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/*  90 */           out.write("\n<td width=\"37%\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" >\n\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"allMGRem2_1\">\n               \t\t\t ");
/*  91 */           out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/*  92 */           out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"allMGRem2_2\">\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:clickForm1('generateGlanceReport',document.forms[1].haid)\" class=\"new-report-link\"> ");
/*  93 */           out.print(FormatUtil.getString("am.webclient.reports.ataglance.mgreport"));
/*  94 */           out.write("</a>  <br /> <br />\t");
/*  95 */           out.write("\t\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\n\t\t\t\t<tr id='mghealthsnapshot'><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/*  96 */           out.print(FormatUtil.getString("am.webclient.reports.availabilityandhealthReport.text"));
/*  97 */           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='mghealthsnapshot'><td width=\"3%\"></td>\n\n\n\n\t\t\t\t<td height=\"20\" >\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:getSnapshot('generateHASnapShotReport')\" class=\"new-report-link\"> ");
/*  98 */           out.print(FormatUtil.getString("am.webclient.reports.list.detailedsnapshot.text"));
/*  99 */           out.write("</a><br><span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:getSnapshot('generateHASnapShotReportWithHostName')\" class=\"new-report-link\">");
/* 100 */           out.print(FormatUtil.getString("am.webclient.reports.list.criticalsnapshot.text"));
/* 101 */           out.write("</a><br><span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:getSnapshot('generateAvailabilitySnapShotReport')\" class=\"new-report-link\"> ");
/* 102 */           out.print(FormatUtil.getString("am.webclient.reports.StatusReport.text"));
/* 103 */           out.write("</a> <br /> <br />\t");
/* 104 */           out.write("\n                \t\t</td></tr>\n\t\t\t\t<tr id='mgavailability'>\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 105 */           out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 106 */           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='mgavailability'><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"allMGRem1_indi\">\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:clickForm1('generateHAAvailabilityReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 107 */           out.print(FormatUtil.getString("am.webclient.reports.availability.percentagereports.text"));
/* 108 */           out.write("</a>\t");
/* 109 */           out.write("\n    \t\t\t\t<br>\n\t\t\t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:getOutage('week','generateWeeklyMonthlyOutageReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 110 */           out.print(FormatUtil.getString("am.webclient.reports.OutageComparisonReport.text"));
/* 111 */           out.write("</a>\t");
/* 112 */           out.write("\n    \t\t\t\t<br><span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:getOutage('day','generatePeriodAvailabilityReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 113 */           out.print(FormatUtil.getString("am.webclient.reports.AvailabilityTrendReport.text"));
/* 114 */           out.write("</a>  <br />  ");
/* 115 */           out.write("\n  \t               <span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:getOutage('day','generatePeriodAvailabilityDowntimeReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 116 */           out.print(FormatUtil.getString("am.webclient.reports.AvailabilityTrend&DowntimeReport.text"));
/* 117 */           out.write("</a>  <br /> <br /> ");
/* 118 */           out.write("\n\t\t\t\t</div>\n\t\t\t\t<div id=\"allMGRem1_all\">\n\t\t\t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:getOutage('week','generateWeeklyMonthlyOutageReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 119 */           out.print(FormatUtil.getString("am.webclient.reports.OutageComparisonReport.text"));
/* 120 */           out.write("</a>\t");
/* 121 */           out.write("\n    \t\t\t\t<br><span style=\"color:#595959; font-size:11px; font-weight:normal;\"> &bull; </span> <a href=\"javascript:getOutage('day','generatePeriodAvailabilityReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 122 */           out.print(FormatUtil.getString("am.webclient.reports.AvailabilityTrendReport.text"));
/* 123 */           out.write("</a>  <br /> <br />\t");
/* 124 */           out.write("\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t</div>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\n\t\t\t</td>\n\n");
/* 125 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 126 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 130 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 131 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 134 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 135 */         out.write(10);
/* 136 */         out.write(10);
/*     */         
/* 138 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 139 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 140 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 142 */         _jspx_th_c_005fif_005f1.setTest("${monitorattributetype == \"customTypes\" }");
/* 143 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 144 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 146 */             out.write("\n<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 147 */             out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 148 */             out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickServiceForm1('glancereport','generateGlanceReport',document.forms[1].customservice)\" class=\"new-report-link\">");
/* 149 */             out.print(FormatUtil.getString("am.webclient.reports.ataglance.customtype"));
/* 150 */             out.write("</a>");
/* 151 */             out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 152 */             out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 153 */             out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].customservice)\" class=\"new-report-link\">");
/* 154 */             out.print(FormatUtil.getString("am.reporttab.customtype.availability"));
/* 155 */             out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 156 */             out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 157 */             out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].customservice)\">");
/* 158 */             out.print(FormatUtil.getString("am.reporttab.customtype.health"));
/* 159 */             out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n\n");
/* 160 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 161 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 165 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 166 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 169 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 170 */           out.write(10);
/* 171 */           out.write(10);
/*     */           
/* 173 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 174 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 175 */           _jspx_th_c_005fif_005f2.setParent(null);
/*     */           
/* 177 */           _jspx_th_c_005fif_005f2.setTest("${monitorattributetype == \"server\" }");
/* 178 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 179 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 181 */               out.write("\n\n<td width=\"49%\" valign=\"top\">\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 182 */               out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 183 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].system)\" class=\"new-report-link\">");
/* 184 */               out.print(FormatUtil.getString("am.webclient.reports.ataglance.servers"));
/* 185 */               out.write("</a>");
/* 186 */               out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 187 */               out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 188 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].system)\" class=\"new-report-link\">");
/* 189 */               out.print(FormatUtil.getString("am.reporttab.availablityofservers.text"));
/* 190 */               out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 191 */               out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 192 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].system)\">");
/* 193 */               out.print(FormatUtil.getString("am.reporttab.healthofservers.text"));
/* 194 */               out.write("</a>  <br /> <br />\n                \t\t</td></tr>\n\n                \t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 195 */               out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 196 */               out.write("\n\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].system)\">");
/* 197 */               out.print(FormatUtil.getString("am.reporttab.resofservers.text"));
/* 198 */               out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 199 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 200 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 204 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 205 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*     */           }
/*     */           else {
/* 208 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 209 */             out.write(10);
/* 210 */             out.write(10);
/*     */             
/* 212 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 213 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 214 */             _jspx_th_c_005fif_005f3.setParent(null);
/*     */             
/* 216 */             _jspx_th_c_005fif_005f3.setTest("${monitorattributetype == \"applicationserver\" }");
/* 217 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 218 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */               for (;;) {
/* 220 */                 out.write("\n<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 221 */                 out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 222 */                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 223 */                 out.print(FormatUtil.getString("am.webclient.reports.ataglance.appservers"));
/* 224 */                 out.write("</a>");
/* 225 */                 out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 226 */                 out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 227 */                 out.write(" </div></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 228 */                 out.print(FormatUtil.getString("am.reporttab.availablityofapp.text"));
/* 229 */                 out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 230 */                 out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 231 */                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].appserver)\">");
/* 232 */                 out.print(FormatUtil.getString("am.reporttab.healthofapp.text"));
/* 233 */                 out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\n                \t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"responsetime_1\">\n               \t\t\t");
/* 234 */                 out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 235 */                 out.write("</td>\n\t\t\t\t</div>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"responsetime_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 236 */                 out.print(FormatUtil.getString("am.reporttab.resofapp.text"));
/* 237 */                 out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"jvm_1\">\n               \t\t\t");
/* 238 */                 out.print(FormatUtil.getString("am.reporttab.memoryusage.text"));
/* 239 */                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"jvm_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('jvm','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 240 */                 out.print(FormatUtil.getString("am.reporttab.jvmofapp.text"));
/* 241 */                 out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"jdbc_1\">\n               \t\t\t");
/* 242 */                 out.print(FormatUtil.getString("am.reporttab.jdbc.text"));
/* 243 */                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"jdbc_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('jdbc','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 244 */                 out.print(FormatUtil.getString("am.reporttab.jdbcofapp.text"));
/* 245 */                 out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 246 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 247 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 251 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 252 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */             }
/*     */             else {
/* 255 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 256 */               out.write(10);
/* 257 */               out.write(10);
/*     */               
/* 259 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 260 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 261 */               _jspx_th_c_005fif_005f4.setParent(null);
/*     */               
/* 263 */               _jspx_th_c_005fif_005f4.setTest("${monitorattributetype == \"databaseserver\" }");
/* 264 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 265 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */                 for (;;) {
/* 267 */                   out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 268 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 269 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].dbserver)\" class=\"new-report-link\">");
/* 270 */                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.dbservers"));
/* 271 */                   out.write("</a>");
/* 272 */                   out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 273 */                   out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 274 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].dbserver)\" class=\"new-report-link\">");
/* 275 */                   out.print(FormatUtil.getString("am.reporttab.availablityofdb.text"));
/* 276 */                   out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 277 */                   out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 278 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].dbserver)\">");
/* 279 */                   out.print(FormatUtil.getString("am.reporttab.healthofdb.text"));
/* 280 */                   out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n                \t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 281 */                   out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 282 */                   out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('connectionTime','generateAttributeReport',document.forms[1].dbserver)\" class=\"new-report-link\">");
/* 283 */                   out.print(FormatUtil.getString("am.reporttab.conofdb.text"));
/* 284 */                   out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 285 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 286 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 290 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 291 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*     */               }
/*     */               else {
/* 294 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 295 */                 out.write(10);
/* 296 */                 out.write(10);
/*     */                 
/* 298 */                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 299 */                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 300 */                 _jspx_th_c_005fif_005f5.setParent(null);
/*     */                 
/* 302 */                 _jspx_th_c_005fif_005f5.setTest("${monitorattributetype == \"webservice\" }");
/* 303 */                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 304 */                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */                   for (;;) {
/* 306 */                     out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\">\n               \t\t\t ");
/* 307 */                     out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 308 */                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm2('glancereport','generateGlanceReport',document.forms[1].webservice,document.forms[1].WebServiceFilter)\" class=\"new-report-link\">");
/* 309 */                     out.print(FormatUtil.getString("am.webclient.reports.ataglance.webservices"));
/* 310 */                     out.write("</a>");
/* 311 */                     out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 312 */                     out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 313 */                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm2('availability','generateAvailabilityReport',document.forms[1].webservice,document.forms[1].WebServiceFilter)\" class=\"new-report-link\">");
/* 314 */                     out.print(FormatUtil.getString("am.reporttab.availablityofwebservices.text"));
/* 315 */                     out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 316 */                     out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 317 */                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm2('health','generateHealthReport',document.forms[1].webservice,document.forms[1].WebServiceFilter)\">");
/* 318 */                     out.print(FormatUtil.getString("am.reporttab.healthofwebservices.text"));
/* 319 */                     out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 320 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 321 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 325 */                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 326 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*     */                 }
/*     */                 else {
/* 329 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 330 */                   out.write(10);
/*     */                   
/* 332 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 333 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 334 */                   _jspx_th_c_005fif_005f6.setParent(null);
/*     */                   
/* 336 */                   _jspx_th_c_005fif_005f6.setTest("${monitorattributetype == \"webserver\" }");
/* 337 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 338 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */                     for (;;) {
/* 340 */                       out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 341 */                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 342 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].webserver)\" class=\"new-report-link\">");
/* 343 */                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.webservers"));
/* 344 */                       out.write("</a>");
/* 345 */                       out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 346 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 347 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].webserver)\" class=\"new-report-link\">");
/* 348 */                       out.print(FormatUtil.getString("am.reporttab.availablityofwebservers.text"));
/* 349 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 350 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 351 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].webserver)\">");
/* 352 */                       out.print(FormatUtil.getString("am.reporttab.healthofwebservers.text"));
/* 353 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 354 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 355 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 359 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 360 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*     */                   }
/*     */                   else {
/* 363 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 364 */                     out.write(10);
/* 365 */                     out.write(10);
/*     */                     
/* 367 */                     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 368 */                     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 369 */                     _jspx_th_c_005fif_005f7.setParent(null);
/*     */                     
/* 371 */                     _jspx_th_c_005fif_005f7.setTest("${monitorattributetype == \"url\" }");
/* 372 */                     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 373 */                     if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */                       for (;;) {
/* 375 */                         out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 376 */                         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 377 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].url)\" class=\"new-report-link\">");
/* 378 */                         out.print(FormatUtil.getString("am.webclient.reports.ataglance.urls"));
/* 379 */                         out.write("</a>");
/* 380 */                         out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 381 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 382 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].url)\" class=\"new-report-link\">");
/* 383 */                         out.print(FormatUtil.getString("am.reporttab.availablityofurls.text"));
/* 384 */                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 385 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 386 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].url)\">");
/* 387 */                         out.print(FormatUtil.getString("am.reporttab.healthofurls.text"));
/* 388 */                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 389 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 390 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 394 */                     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 395 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*     */                     }
/*     */                     else {
/* 398 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 399 */                       out.write(10);
/* 400 */                       out.write(10);
/*     */                       
/* 402 */                       IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 403 */                       _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 404 */                       _jspx_th_c_005fif_005f8.setParent(null);
/*     */                       
/* 406 */                       _jspx_th_c_005fif_005f8.setTest("${monitorattributetype == \"services\" }");
/* 407 */                       int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 408 */                       if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */                         for (;;) {
/* 410 */                           out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 411 */                           out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 412 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].service)\" class=\"new-report-link\">");
/* 413 */                           out.print(FormatUtil.getString("am.webclient.reports.ataglance.services"));
/* 414 */                           out.write("</a>");
/* 415 */                           out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 416 */                           out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 417 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].service)\" class=\"new-report-link\">");
/* 418 */                           out.print(FormatUtil.getString("am.reporttab.availablityofservices.text"));
/* 419 */                           out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 420 */                           out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 421 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].service)\">");
/* 422 */                           out.print(FormatUtil.getString("am.reporttab.healthofservices.text"));
/* 423 */                           out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 424 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 425 */                           if (evalDoAfterBody != 2)
/*     */                             break;
/*     */                         }
/*     */                       }
/* 429 */                       if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 430 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*     */                       }
/*     */                       else {
/* 433 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 434 */                         out.write(10);
/* 435 */                         out.write(10);
/* 436 */                         out.write(10);
/*     */                         
/* 438 */                         IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 439 */                         _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 440 */                         _jspx_th_c_005fif_005f9.setParent(null);
/*     */                         
/* 442 */                         _jspx_th_c_005fif_005f9.setTest("${monitorattributetype == \"forecast\" }");
/* 443 */                         int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 444 */                         if (_jspx_eval_c_005fif_005f9 != 0) {
/*     */                           for (;;) {
/* 446 */                             out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"2\" border=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 447 */                             out.print(FormatUtil.getString("Servers"));
/* 448 */                             out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:showforecastreport('711')\" class=\"new-report-link\">");
/* 449 */                             out.print(FormatUtil.getString("am.webclient.forecast.servers.disk.text"));
/* 450 */                             out.write("</a>");
/* 451 */                             out.write("  <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 452 */                             out.print(FormatUtil.getString("am.monitortab.databaseservers.text"));
/* 453 */                             out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:showforecastreport('3128')\" class=\"new-report-link\">");
/* 454 */                             out.print(FormatUtil.getString("am.webclient.forecast.mssql.datafile.text"));
/* 455 */                             out.write("</a><br /> <br />\n               \t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:showforecastreport('9353')\" class=\"new-report-link\">");
/* 456 */                             out.print(FormatUtil.getString("am.webclient.forecast.postgresql.dbsize.text"));
/* 457 */                             out.write("</a><br /> <br />\n               \t</td>\n                </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 458 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 459 */                             if (evalDoAfterBody != 2)
/*     */                               break;
/*     */                           }
/*     */                         }
/* 463 */                         if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 464 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*     */                         }
/*     */                         else {
/* 467 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 468 */                           out.write(10);
/* 469 */                           out.write(10);
/* 470 */                           out.write(10);
/*     */                           
/* 472 */                           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 473 */                           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 474 */                           _jspx_th_c_005fif_005f10.setParent(null);
/*     */                           
/* 476 */                           _jspx_th_c_005fif_005f10.setTest("${monitorattributetype == \"mailserver\" }");
/* 477 */                           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 478 */                           if (_jspx_eval_c_005fif_005f10 != 0) {
/*     */                             for (;;) {
/* 480 */                               out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 481 */                               out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 482 */                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].mailservice)\" class=\"new-report-link\">");
/* 483 */                               out.print(FormatUtil.getString("am.webclient.reports.ataglance.mailservers"));
/* 484 */                               out.write("</a>");
/* 485 */                               out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr id=\"Mavailability\"><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 486 */                               out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 487 */                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"Mavailability\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].mailservice)\" class=\"new-report-link\">");
/* 488 */                               out.print(FormatUtil.getString("am.reporttab.availablityofmail.text"));
/* 489 */                               out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 490 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 491 */                               if (evalDoAfterBody != 2)
/*     */                                 break;
/*     */                             }
/*     */                           }
/* 495 */                           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 496 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*     */                           }
/*     */                           else {
/* 499 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 500 */                             out.write(10);
/*     */                             
/* 502 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 503 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 504 */                             _jspx_th_c_005fif_005f11.setParent(null);
/*     */                             
/* 506 */                             _jspx_th_c_005fif_005f11.setTest("${monitorattributetype == \"javaserver\" }");
/* 507 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 508 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*     */                               for (;;) {
/* 510 */                                 out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 511 */                                 out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 512 */                                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].javaservice)\" class=\"new-report-link\">");
/* 513 */                                 out.print(FormatUtil.getString("am.webclient.reports.ataglance.java"));
/* 514 */                                 out.write("</a>");
/* 515 */                                 out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 516 */                                 out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 517 */                                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].javaservice)\" class=\"new-report-link\">");
/* 518 */                                 out.print(FormatUtil.getString("am.reporttab.availablityofjava.text"));
/* 519 */                                 out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 520 */                                 out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 521 */                                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].javaservice)\">");
/* 522 */                                 out.print(FormatUtil.getString("am.reporttab.healthofjava.text"));
/* 523 */                                 out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 524 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 525 */                                 if (evalDoAfterBody != 2)
/*     */                                   break;
/*     */                               }
/*     */                             }
/* 529 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 530 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*     */                             }
/*     */                             else {
/* 533 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 534 */                               out.write(10);
/*     */                               
/* 536 */                               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 537 */                               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 538 */                               _jspx_th_c_005fif_005f12.setParent(null);
/*     */                               
/* 540 */                               _jspx_th_c_005fif_005f12.setTest("${monitorattributetype == \"sapserver\" }");
/* 541 */                               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 542 */                               if (_jspx_eval_c_005fif_005f12 != 0) {
/*     */                                 for (;;) {
/* 544 */                                   out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 545 */                                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 546 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].sapservice)\" class=\"new-report-link\">");
/* 547 */                                   out.print(FormatUtil.getString("am.webclient.reports.ataglance.erp"));
/* 548 */                                   out.write("</a>");
/* 549 */                                   out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 550 */                                   out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 551 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].sapservice)\" class=\"new-report-link\">");
/* 552 */                                   out.print(FormatUtil.getString("am.reporttab.availablityofsap.text"));
/* 553 */                                   out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 554 */                                   out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 555 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].sapservice)\">");
/* 556 */                                   out.print(FormatUtil.getString("am.reporttab.healthofsap.text"));
/* 557 */                                   out.write("<br /> <br />\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t\t<div id=\"SAPleft\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n                \t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 558 */                                   out.print(FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/* 559 */                                   out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapcpu','generateAttributeReport',document.forms[1].sapservice)\">");
/* 560 */                                   out.print(FormatUtil.getString("am.reporttab.cpuofsap.text"));
/* 561 */                                   out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 562 */                                   out.print(FormatUtil.getString("am.reporttab.memoryusage.text"));
/* 563 */                                   out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapmemory','generateAttributeReport',document.forms[1].sapservice)\">");
/* 564 */                                   out.print(FormatUtil.getString("am.reporttab.memofsap.text"));
/* 565 */                                   out.write("<br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 566 */                                   out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 567 */                                   out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapdisk','generateAttributeReport',document.forms[1].sapservice)\">");
/* 568 */                                   out.print(FormatUtil.getString("am.reporttab.diskofsap.text"));
/* 569 */                                   out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 570 */                                   out.print(FormatUtil.getString("am.reporttab.shortname.ferestimeofsap.text"));
/* 571 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapferestime','generateAttributeReport',document.forms[1].sapservice)\">");
/* 572 */                                   out.print(FormatUtil.getString("am.reporttab.ferestimeofsap.text"));
/* 573 */                                   out.write("<br /> <br />\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t\t</div>\n\t\t\t</td>\n");
/* 574 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 575 */                                   if (evalDoAfterBody != 2)
/*     */                                     break;
/*     */                                 }
/*     */                               }
/* 579 */                               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 580 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*     */                               }
/*     */                               else {
/* 583 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 584 */                                 out.write(10);
/*     */                                 
/* 586 */                                 IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 587 */                                 _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 588 */                                 _jspx_th_c_005fif_005f13.setParent(null);
/*     */                                 
/* 590 */                                 _jspx_th_c_005fif_005f13.setTest("${monitorattributetype == \"middleware\" }");
/* 591 */                                 int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 592 */                                 if (_jspx_eval_c_005fif_005f13 != 0) {
/*     */                                   for (;;) {
/* 594 */                                     out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"3%\">&nbsp;</td>\n\t\t\t\t\t<td align=\"left\">&nbsp;</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 595 */                                     out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 596 */                                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 597 */                                     out.print(FormatUtil.getString("am.webclient.reports.ataglance.middleware"));
/* 598 */                                     out.write("</a>");
/* 599 */                                     out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr id=\"availability\"><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 600 */                                     out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 601 */                                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"availability\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 602 */                                     out.print(FormatUtil.getString("am.reporttab.availablityofapp.middleware.text"));
/* 603 */                                     out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr id=\"health\">\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 604 */                                     out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 605 */                                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"health\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].middlewareserver)\">");
/* 606 */                                     out.print(FormatUtil.getString("am.reporttab.healthofapp.middleware.text"));
/* 607 */                                     out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\n                \t\t<tr id=\"responsetime\">\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 608 */                                     out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 609 */                                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"responsetime\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 610 */                                     out.print(FormatUtil.getString("am.reporttab.resofapp.middleware.text"));
/* 611 */                                     out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t");
/*     */                                     
/* 613 */                                     if ((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("WEBLOGIC-Integration") != null))
/*     */                                     {
/*     */ 
/* 616 */                                       out.write("\n\t\t\t\t<tr id=\"wlijvm\">\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"wlijvm_1\">\n               \t\t\t");
/* 617 */                                       out.print(FormatUtil.getString("am.reporttab.shortname.jvm.text"));
/* 618 */                                       out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"wlijvm\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"wlijvm_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('wlijvm','generateAttributeReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 619 */                                       out.print(FormatUtil.getString("am.reporttab.jvmofapp.wli.text"));
/* 620 */                                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t");
/*     */                                     }
/*     */                                     
/*     */ 
/* 624 */                                     out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 625 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 626 */                                     if (evalDoAfterBody != 2)
/*     */                                       break;
/*     */                                   }
/*     */                                 }
/* 630 */                                 if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 631 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*     */                                 }
/*     */                                 else {
/* 634 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 635 */                                   out.write(10);
/* 636 */                                   out.write(10);
/*     */                                   
/* 638 */                                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 639 */                                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 640 */                                   _jspx_th_c_005fif_005f14.setParent(null);
/*     */                                   
/* 642 */                                   _jspx_th_c_005fif_005f14.setTest("${monitorattributetype == \"virtualserver\" }");
/* 643 */                                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 644 */                                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*     */                                     for (;;) {
/* 646 */                                       out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 647 */                                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 648 */                                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].virserver)\" class=\"new-report-link\">");
/* 649 */                                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.virtual"));
/* 650 */                                       out.write("</a>");
/* 651 */                                       out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 652 */                                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 653 */                                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].virserver)\" class=\"new-report-link\">");
/* 654 */                                       out.print(FormatUtil.getString("am.reporttab.availablityofvirtual.text"));
/* 655 */                                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 656 */                                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 657 */                                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].virserver)\">");
/* 658 */                                       out.print(FormatUtil.getString("am.reporttab.healthofvirtual.text"));
/* 659 */                                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 660 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 661 */                                       if (evalDoAfterBody != 2)
/*     */                                         break;
/*     */                                     }
/*     */                                   }
/* 665 */                                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 666 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*     */                                   }
/*     */                                   else {
/* 669 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 670 */                                     out.write(10);
/* 671 */                                     out.write(10);
/*     */                                     
/* 673 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 674 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 675 */                                     _jspx_th_c_005fif_005f15.setParent(null);
/*     */                                     
/* 677 */                                     _jspx_th_c_005fif_005f15.setTest("${monitorattributetype == \"cloudapps\" }");
/* 678 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 679 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*     */                                       for (;;) {
/* 681 */                                         out.write("\n<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 682 */                                         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 683 */                                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].cldApps)\" class=\"new-report-link\">");
/* 684 */                                         out.print(FormatUtil.getString("am.webclient.reports.ataglance.cloudapps"));
/* 685 */                                         out.write("</a>");
/* 686 */                                         out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 687 */                                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 688 */                                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" >\n                \t\t<a href=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].cldApps)\" class=\"new-report-link\">");
/* 689 */                                         out.print(FormatUtil.getString("am.reporttab.availablityofcloudapps.text"));
/* 690 */                                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 691 */                                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 692 */                                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].cldApps)\">");
/* 693 */                                         out.print(FormatUtil.getString("am.reporttab.healthofcloudapps.text"));
/* 694 */                                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t</td>\n");
/* 695 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 696 */                                         if (evalDoAfterBody != 2)
/*     */                                           break;
/*     */                                       }
/*     */                                     }
/* 700 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 701 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*     */                                     }
/*     */                                     else {
/* 704 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 705 */                                       out.write(10);
/*     */                                       
/* 707 */                                       IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 708 */                                       _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 709 */                                       _jspx_th_c_005fif_005f16.setParent(null);
/*     */                                       
/* 711 */                                       _jspx_th_c_005fif_005f16.setTest("${monitorattributetype == \"eummonitors\" }");
/* 712 */                                       int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 713 */                                       if (_jspx_eval_c_005fif_005f16 != 0) {
/*     */                                         for (;;) {
/* 715 */                                           out.write("\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"eumRepTable\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td height=\"20\"></td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 716 */                                           out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 717 */                                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\">\n\t\t\t\t\t\t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].eummonitors)\" class=\"new-report-link\">");
/* 718 */                                           out.print(FormatUtil.getString("am.webclient.eum.report.glancedesc"));
/* 719 */                                           out.write("</a>\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 720 */                                           out.print(FormatUtil.getString("am.webclient.eum.report.location"));
/* 721 */                                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\">\n\t\t\t\t\t\t\t\t<a href=\"javascript: callEUMSummary();\" class=\"new-report-link\">");
/* 722 */                                           out.print(FormatUtil.getString("am.webclient.eum.report.locationdesc"));
/* 723 */                                           out.write("</a><br/>\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 724 */                                           out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 725 */                                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\"><a\n\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].eummonitors)\"\n\t\t\t\t\t\t\t\tclass=\"new-report-link\">");
/* 726 */                                           out.print(FormatUtil.getString("am.webclient.eum.report.availabilitydesc"));
/* 727 */                                           out.write("</a><br> <br>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 728 */                                           out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 729 */                                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\"><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].eummonitors)\">");
/* 730 */                                           out.print(FormatUtil.getString("am.webclient.eum.report.healthdesc"));
/* 731 */                                           out.write("</a><br> <br>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table>\n");
/* 732 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 733 */                                           if (evalDoAfterBody != 2)
/*     */                                             break;
/*     */                                         }
/*     */                                       }
/* 737 */                                       if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 738 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/*     */                                       }
/*     */                                       else {
/* 741 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 742 */                                         out.write(10);
/* 743 */                                         if (_jspx_meth_c_005fif_005f17(_jspx_page_context))
/*     */                                           return;
/* 745 */                                         out.write(10);
/*     */                                       }
/* 747 */                                     } } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 748 */         out = _jspx_out;
/* 749 */         if ((out != null) && (out.getBufferSize() != 0))
/* 750 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 751 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 754 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 760 */     PageContext pageContext = _jspx_page_context;
/* 761 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 763 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 764 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 765 */     _jspx_th_c_005fset_005f0.setParent(null);
/*     */     
/* 767 */     _jspx_th_c_005fset_005f0.setVar("monitorattributetype");
/*     */     
/* 769 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 770 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 771 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 772 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 773 */         out = _jspx_page_context.pushBody();
/* 774 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 775 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 778 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 779 */           return true;
/* 780 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 781 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 784 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 785 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 788 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 789 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 790 */       return true;
/*     */     }
/* 792 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 793 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 798 */     PageContext pageContext = _jspx_page_context;
/* 799 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 801 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 802 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 803 */     _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 805 */     _jspx_th_c_005fout_005f0.setValue("${param.monitortype }");
/* 806 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 807 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 809 */       return true;
/*     */     }
/* 811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 812 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f17(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 817 */     PageContext pageContext = _jspx_page_context;
/* 818 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 820 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 821 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 822 */     _jspx_th_c_005fif_005f17.setParent(null);
/*     */     
/* 824 */     _jspx_th_c_005fif_005f17.setTest("${monitorattributetype == \"bottomdiv\" }");
/* 825 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 826 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*     */       for (;;) {
/* 828 */         out.write("\n<div id=\"sub2\">\n\t<ul id=\"sub_11\" class=\"none\">\n\t\t<li id=\"no_11\"><b class=\"vert\" ></b></li>\n\t</ul>\n\n\t<ul id=\"sub_12\" class=\"none\">\n\t\t<li id=\"no_12\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_13\" class=\"none\">\n\t\t<li id=\"no_13\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_14\" class=\"none\">\n\t\t<li id=\"no_14\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_15\" class=\"none\">\n\t\t<li id=\"no_15\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_16\" class=\"none\">\n\t\t<li id=\"no_16\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_17\" class=\"none\">\n\t\t<li id=\"no_17\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_18\" class=\"none\">\n\t\t<li id=\"no_18\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_19\" class=\"none\">\n\t\t<li id=\"no_19\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_20\" class=\"none\">\n\t\t<li id=\"no_20\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_21\" class=\"none\">\n\t\t<li id=\"no_21\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_22\" class=\"none\">\n\t\t<li id=\"no_22\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_23\" class=\"none\">\n\t\t<li id=\"no_23\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_24\" class=\"none\">\n\t\t<li id=\"no_24\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_25\" class=\"none\">\n\t\t<li id=\"no_25\"><b></b></li>\n");
/* 829 */         out.write("\t</ul>\n\t<ul id=\"sub_26\" class=\"none\">\n\t\t<li id=\"no_26\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_27\" class=\"none\">\n\t\t<li id=\"no_27\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_28\" class=\"none\">\n\t\t<li id=\"no_28\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_29\" class=\"none\">\n\t\t<li id=\"no_29\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_30\" class=\"none\">\n\t\t<li id=\"no_30\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_35\" class=\"none\">\n\t\t<li id=\"no_35\"><b></b></li>\n\t</ul>\n\n\t<ul id=\"sub_36\" class=\"none\">\n\t\t<li id=\"no_36\"><b></b></li>\n\t</ul>\n        <ul id=\"sub_38\" class=\"none\">\n\t\t<li id=\"no_38\"><b></b></li>\n\t</ul>\n\t<ul id=\"sub_37\" class=\"none\">\n\t\t<li id=\"no_37\"><b></b></li>\n\t</ul>\n\n</div>\n");
/* 830 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 831 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 835 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 836 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 837 */       return true;
/*     */     }
/* 839 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 840 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\MonitorLevelAttributes_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */