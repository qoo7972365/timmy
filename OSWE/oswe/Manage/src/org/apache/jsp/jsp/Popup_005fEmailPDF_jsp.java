/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
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
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.OptionTag;
/*     */ import org.apache.struts.taglib.html.RadioTag;
/*     */ import org.apache.struts.taglib.html.SelectTag;
/*     */ import org.apache.struts.taglib.html.TextareaTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class Popup_005fEmailPDF_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  45 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  58 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  66 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  67 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.release();
/*  68 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  69 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  76 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  79 */     JspWriter out = null;
/*  80 */     Object page = this;
/*  81 */     JspWriter _jspx_out = null;
/*  82 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  86 */       response.setContentType("text/html;charset=UTF-8");
/*  87 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  89 */       _jspx_page_context = pageContext;
/*  90 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  91 */       ServletConfig config = pageContext.getServletConfig();
/*  92 */       session = pageContext.getSession();
/*  93 */       out = pageContext.getOut();
/*  94 */       _jspx_out = out;
/*     */       
/*  96 */       out.write("<!--$Id$-->\n\n<html>\n<head>\n\n\n\n\n\n\n\n\n\n\n\n\n \n<title>");
/*  97 */       out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"));
/*  98 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/*  99 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 101 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<script>\nfunction sendMail()\n{\n\n if((document.ReportForm.emailid.value=='')||(document.ReportForm.emailid.value=='----------");
/* 102 */       out.print(FormatUtil.getString("am.webclient.popupemailpdf.dropdown"));
/* 103 */       out.write("------'))\n {\n    alert(\"");
/* 104 */       out.print(FormatUtil.getString("am.webclient.popupemailpdf.jsalert"));
/* 105 */       out.write("\");\n    document.ReportForm.emailid.value='';\n    document.ReportForm.emailid.focus();\n    return false;\n\n\n }\n if(document.ReportForm.emailReport){\nif(document.ReportForm.emailReport[0].checked){\ndocument.ReportForm.reportType.value='pdf';\n}else{\ndocument.ReportForm.reportType.value='excel';\n}\n}\n ");
/* 106 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 108 */       out.write("\ndocument.ReportForm.submit();\n}\n</script>\n</head>\n<body>\n     <table class=\"darkheaderbg\" height=\"55\" width=\"100%\" align=\"centre\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n           <tr>\n     <td>\n         <span class=\"headingboldwhite\"> ");
/* 109 */       out.print(FormatUtil.getString("Email Report"));
/* 110 */       out.write(" </span>\n      </td>\n           </tr>\n       </table>\n<br/>\n");
/*     */       
/* 112 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 113 */       String sucess = request.getParameter("sucess");
/* 114 */       String q1 = "select HOST from AM_MAILSETTINGS ";
/* 115 */       String q2 = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME as NAME,TOADDRESS,FROMADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 116 */       ResultSet rst = AMConnectionPool.executeQueryStmt(q1);
/* 117 */       ResultSet rst2 = AMConnectionPool.executeQueryStmt(q2);
/* 118 */       String actionmethod = request.getParameter("actionMethod");
/*     */       
/* 120 */       if (rst.next())
/*     */       {
/* 122 */         if ((sucess != null) && (sucess.equalsIgnoreCase("true")))
/*     */         {
/*     */ 
/* 125 */           out.write("\n\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n  <tr>\n      <td class='monitorinfoeven'><img src=\"/images/icon_message_success.gif\" align=\"absmiddle\"> &nbsp;<span class=\"bodytext\">\n        ");
/* 126 */           out.print(FormatUtil.getString("am.webclient.popup.sendmail.sucess.message"));
/* 127 */           out.write(".</span> </td>\n    </tr>\n </table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\n\t    <td width=\"100%\" align=center height='32' ><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 128 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 129 */           out.write("\" onClick=\"javascript:window.parent.close();\"></td>\n\t  </tr>\n\t  </table>\n\n");
/*     */         }
/*     */         else {
/* 132 */           out.write(10);
/* 133 */           out.write(10);
/* 134 */           if ((sucess != null) && (sucess.equalsIgnoreCase("false")))
/*     */           {
/*     */ 
/* 137 */             out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n   <tr>\n      <td class='monitorinfoeven'><img src=\"/images/icon_message_failure.gif\" align=\"absmiddle\"> &nbsp;<span class=\"bodytext\">\n        ");
/* 138 */             out.print(FormatUtil.getString("am.webclient.popupemailpdf.senadfailed"));
/* 139 */             out.write(".</span> </td>\n    </tr>\n\n</table>\n\n");
/*     */           }
/* 141 */           out.write("\n\n\n ");
/*     */           
/* 143 */           FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 144 */           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 145 */           _jspx_th_html_005fform_005f0.setParent(null);
/*     */           
/* 147 */           _jspx_th_html_005fform_005f0.setAction("/showReports.do?actionMethod=emailPDF");
/*     */           
/* 149 */           _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 150 */           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 151 */           if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */             for (;;) {
/* 153 */               out.write("\n \t   <input type=\"hidden\" name=\"resourceType\" value=\"");
/* 154 */               out.print(request.getParameter("resourceType"));
/* 155 */               out.write("\">\n       <input type=hidden name='period' value='");
/* 156 */               out.print(request.getParameter("period"));
/* 157 */               out.write("'>\n       <input type=hidden name='interval' value='");
/* 158 */               out.print(request.getParameter("inter"));
/* 159 */               out.write("'>\n       <input type=hidden name='brule' value='");
/* 160 */               out.print(request.getParameter("brule"));
/* 161 */               out.write("'>\n       <input type=hidden name='hid' value='");
/* 162 */               out.print(request.getParameter("hid"));
/* 163 */               out.write("'>\n       <input type=hidden name='attribute' value='");
/* 164 */               out.print(request.getParameter("attribute"));
/* 165 */               out.write("'>\n       <input type=hidden name='resid' value='");
/* 166 */               out.print(request.getParameter("resid"));
/* 167 */               out.write("'>\n          <input type=hidden name='report' value='");
/* 168 */               out.print(request.getParameter("report"));
/* 169 */               out.write("'>\n   <input type=hidden name='reportmethod' value='");
/* 170 */               out.print(request.getParameter("reportmethod"));
/* 171 */               out.write("'>\n       <input type=hidden name='haid' value='");
/* 172 */               out.print(request.getParameter("haid"));
/* 173 */               out.write("'>\n       <input type=hidden name='attributeName' value='");
/* 174 */               out.print(request.getParameter("attributeName"));
/* 175 */               out.write("'>\n       <input type=hidden name='aMethod' value='");
/* 176 */               out.print(request.getParameter("actionMethod"));
/* 177 */               out.write("'>\n       <input type=hidden name='unit' value='");
/* 178 */               out.print(request.getParameter("unit"));
/* 179 */               out.write("'>\n       <input type=hidden name='resourceid' value='");
/* 180 */               out.print(request.getParameter("resourceid"));
/* 181 */               out.write("'>\n       <input type=hidden name='resourceType' value='");
/* 182 */               out.print(request.getParameter("resourceType"));
/* 183 */               out.write("'>\n       <input type=hidden name='numberOfRows' value='");
/* 184 */               out.print(request.getParameter("numberOfRows"));
/* 185 */               out.write("'>\n       <input type=hidden name='startDate' value='");
/* 186 */               out.print(request.getParameter("startDate"));
/* 187 */               out.write("'>\n       <input type=hidden name='thisstart' value='");
/* 188 */               out.print(request.getParameter("thisstart"));
/* 189 */               out.write("'>\n       <input type=hidden name='thisend' value='");
/* 190 */               out.print(request.getParameter("thisend"));
/* 191 */               out.write("'>\n       <input type=hidden name='laststart' value='");
/* 192 */               out.print(request.getParameter("laststart"));
/* 193 */               out.write("'>\n       <input type=hidden name='lastend' value='");
/* 194 */               out.print(request.getParameter("lastend"));
/* 195 */               out.write("'>\n       <input type=hidden name='endDate' value='");
/* 196 */               out.print(request.getParameter("endDate"));
/* 197 */               out.write("'>\n        <input type=hidden name='emailpdf' value='true'>\n       <input type=hidden name='reportType' value='pdf'>\n       <input type=hidden name='pdfAttributeName' value='");
/* 198 */               out.print(request.getParameter("pdfAttributeName"));
/* 199 */               out.write("'>\n       <input type=hidden name='eumReport' value='");
/* 200 */               out.print(request.getParameter("eumReport"));
/* 201 */               out.write("'>\n    <input type=hidden name='mondaycapacity' value='");
/* 202 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 204 */               out.write("'>\n\t  <input type=hidden name='customfield' value='");
/* 205 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 207 */               out.write("'>\n\t    <input type=hidden name='customFieldValue' value='");
/* 208 */               if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 210 */               out.write("'>\n\t\t<input type=\"hidden\" name=\"customFieldNumberOfRows\" value='");
/* 211 */               if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 213 */               out.write("'>\n\t\t <input type=hidden name='month' value='");
/* 214 */               out.print(request.getParameter("month"));
/* 215 */               out.write("'>\n       <input type=hidden name='year' value='");
/* 216 */               out.print(request.getParameter("year"));
/* 217 */               out.write("'>\n       <input type=hidden name='licDuration' value='");
/* 218 */               out.print(request.getParameter("licDuration"));
/* 219 */               out.write("'>\n       <input type=hidden name='fromMonth' value='");
/* 220 */               out.print(request.getParameter("fromMonth"));
/* 221 */               out.write("'>\n       <input type=hidden name='toMonth' value='");
/* 222 */               out.print(request.getParameter("toMonth"));
/* 223 */               out.write("'>\n       <input type=hidden name='fromYear' value='");
/* 224 */               out.print(request.getParameter("fromYear"));
/* 225 */               out.write("'>\n       <input type=hidden name='toYear' value='");
/* 226 */               out.print(request.getParameter("toYear"));
/* 227 */               out.write("'>\n       <input type=\"hidden\" name=\"database\" value='");
/* 228 */               out.print(request.getParameter("database"));
/* 229 */               out.write("'>\n       <input type=\"hidden\" name=\"topqrycnt\" value='");
/* 230 */               out.print(request.getParameter("topqrycnt"));
/* 231 */               out.write("'>\n       <input type=\"hidden\" name=\"hrefname\" value='");
/* 232 */               out.print(request.getParameter("hrefname"));
/* 233 */               out.write("'>\n\t\t");
/* 234 */               if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 235 */                 out.write("\n\t\t\t<input type=\"hidden\" name=\"columnsAdded\" value='");
/* 236 */                 out.print(request.getParameter("columnsAdded"));
/* 237 */                 out.write("'>\n\t\t\t<input type=\"hidden\" name=\"columnHeadings\" value='");
/* 238 */                 out.print(request.getParameter("columnHeadings"));
/* 239 */                 out.write("'>\n\t\t\t<input type=\"hidden\" name=\"selectedColumns\" value='");
/* 240 */                 out.print(request.getParameter("selectedColumns"));
/* 241 */                 out.write("'>\n\t\t\t<input type=\"hidden\" name=\"fromDate\" value='");
/* 242 */                 out.print(request.getParameter("fromDate"));
/* 243 */                 out.write("'>\n\t\t\t<input type=\"hidden\" name=\"toDate\" value='");
/* 244 */                 out.print(request.getParameter("toDate"));
/* 245 */                 out.write("'>\n\t\t");
/*     */               }
/* 247 */               out.write("\n<script>\n function putEmail()\n{\n\nemailid=document.ReportForm.save.options[document.ReportForm.save.selectedIndex].text;\ndocument.ReportForm.emailid.value=emailid;\n\n\n}\n      </script>\n\n<table width=\"98%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td class=\"tableheadingbborder\" width=\"100%\" colspan='2'>");
/* 248 */               out.print(FormatUtil.getString("Email Report"));
/* 249 */               out.write("</td>\n  </tr>\n\n\n\n\n    <tr><td colspan=\"2\" height=\"10\"></td></tr>\n\n\n\n  <tr >\n   ");
/* 250 */               if (("generateHASnapShotReport".equals(actionmethod)) || ("generateHASnapShotReportWithHostName".equals(actionmethod)) || ("generateAvailabilitySnapShotReport".equals(actionmethod)) || ("generatePeriodAvailabilityReport".equals(actionmethod)) || ("generateWeeklyMonthlyOutageReport".equals(actionmethod)) || ("generatePeriodAvailabilityDowntimeReport".equals(actionmethod))) {
/* 251 */                 out.write("\n     <td class=\"bodytext\" width=\"45%\" colspan=\"2\" >&nbsp; ");
/* 252 */                 out.print(FormatUtil.getString("Report Type"));
/* 253 */                 out.write("&nbsp; &nbsp; ");
/* 254 */                 if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                   return;
/* 256 */                 out.write("&nbsp;");
/* 257 */                 out.print(FormatUtil.getString("PDF"));
/* 258 */                 out.write(" &nbsp;&nbsp; ");
/* 259 */                 if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                   return;
/* 261 */                 out.write("&nbsp;");
/* 262 */                 out.print(FormatUtil.getString("EXCEL"));
/* 263 */                 out.write(" </td>\n\n\t  ");
/*     */               } else {
/* 265 */                 out.write("\n\n\t  ");
/*     */               }
/* 267 */               out.write("\n  </tr>\n\n\n\n\n\n\n<tr><td colspan=\"2\" height=\"20\"></td></tr>\n    <tr>\n\t    <td  width=\"30%\" height='30'  class='bodytext' >&nbsp; ");
/* 268 */               out.print(FormatUtil.getString("am.webclient.newaction.toaddress"));
/* 269 */               out.write("<span class=\"mandatory\">*</span>\n\t\t</td>\n\t       <td  height=\"30\" class='bodytext'>\n   ");
/*     */               
/* 271 */               SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 272 */               _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 273 */               _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */               
/* 275 */               _jspx_th_html_005fselect_005f0.setProperty("save");
/*     */               
/* 277 */               _jspx_th_html_005fselect_005f0.setStyle("position:absolute; width:200px; clip:rect(0 200 22 180)");
/*     */               
/* 279 */               _jspx_th_html_005fselect_005f0.setOnchange("javascript:putEmail();");
/* 280 */               int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 281 */               if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 282 */                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 283 */                   out = _jspx_page_context.pushBody();
/* 284 */                   _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 285 */                   _jspx_th_html_005fselect_005f0.doInitBody();
/*     */                 }
/*     */                 for (;;) {
/* 288 */                   out.write("\n\t   ");
/*     */                   
/* 290 */                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 291 */                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 292 */                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*     */                   
/* 294 */                   _jspx_th_html_005foption_005f0.setValue("select");
/* 295 */                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 296 */                   if (_jspx_eval_html_005foption_005f0 != 0) {
/* 297 */                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 298 */                       out = _jspx_page_context.pushBody();
/* 299 */                       _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 300 */                       _jspx_th_html_005foption_005f0.doInitBody();
/*     */                     }
/*     */                     for (;;) {
/* 303 */                       out.write("\n\t   ----------");
/* 304 */                       out.print(FormatUtil.getString("am.webclient.popupemailpdf.dropdown"));
/* 305 */                       out.write("------\n\t   ");
/* 306 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 307 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/* 310 */                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 311 */                       out = _jspx_page_context.popBody();
/*     */                     }
/*     */                   }
/* 314 */                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 315 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*     */                   }
/*     */                   
/* 318 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 319 */                   out.write("\n\t   ");
/* 320 */                   while (rst2.next())
/*     */                   {
/* 322 */                     out.write("\n\t   ");
/*     */                     
/* 324 */                     OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 325 */                     _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 326 */                     _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*     */                     
/* 328 */                     _jspx_th_html_005foption_005f1.setValue(rst2.getString("FROMADDRESS"));
/* 329 */                     int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 330 */                     if (_jspx_eval_html_005foption_005f1 != 0) {
/* 331 */                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 332 */                         out = _jspx_page_context.pushBody();
/* 333 */                         _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 334 */                         _jspx_th_html_005foption_005f1.doInitBody();
/*     */                       }
/*     */                       for (;;) {
/* 337 */                         out.write("\n\t   ");
/* 338 */                         out.print(rst2.getString("NAME"));
/* 339 */                         out.write(58);
/* 340 */                         out.print(rst2.getString("TOADDRESS"));
/* 341 */                         out.write("\n\t   ");
/* 342 */                         int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 343 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/* 346 */                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 347 */                         out = _jspx_page_context.popBody();
/*     */                       }
/*     */                     }
/* 350 */                     if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 351 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*     */                     }
/*     */                     
/* 354 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 355 */                     out.write("\n\t   ");
/*     */                   }
/* 357 */                   out.write("\n\t   ");
/* 358 */                   int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 359 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 362 */                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 363 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 366 */               if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 367 */                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*     */               }
/*     */               
/* 370 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 371 */               out.write("\n   <input type=\"text\" name=\"emailid\" onChange=\"document.ReportForm.save.selectedindex=-1\" style=\"position:absolute;width:180px;border-right:0\"><br><br></td>\n\t   </td>\n</tr>\n\n\t<tr><td width=\"30%\"  height=\"10\" class='bodytext'>\n\t\t&nbsp;&nbsp;");
/* 372 */               out.print(FormatUtil.getString("am.webclient.emailpdf.comment.text"));
/* 373 */               out.write("\n\t</td>\n\t<td  height=\"10\" class='bodytext'>\n\t");
/* 374 */               if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 376 */               out.write("\n\t</td></tr>\n\t    <tr><td colspan=\"2\" height=\"10\"></td></tr>\n\n\t  <tr>\n\t    <td width=\"10%\" colspan=\"2\" align=\"center\"  height='32' class='tablebottom' > <input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 377 */               out.print(FormatUtil.getString("am.webclient.talkback.button.send"));
/* 378 */               out.write("\" onClick=\"javascript:sendMail();\"> <input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 379 */               out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 380 */               out.write("\" onClick=\"javascript:window.parent.close();\"></td>\n\n\t  </tr>\n\t</table>\n\n\n\n\n\n");
/* 381 */               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 382 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 386 */           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 387 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*     */           }
/*     */           
/* 390 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 391 */           out.write(10);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 396 */         out.write("\n<script>\nfunction openMail()\n{\n\n  window.opener.location.href='/adminAction.do?method=showMailServerConfiguration&admin=true';\n  window.close();\n}\n</script>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\"  align=\"center\" style=\"padding:10px;\">\n   <tr>\n      <td ><img src=\"/images/icon_message_failure.gif\" align=\"absmiddle\"> &nbsp;<span class=\"bodytext\">\n        ");
/* 397 */         out.print(FormatUtil.getString("am.webclient.popupemailpdf.smtpnotconfigured"));
/* 398 */         out.write("</span></td>\n    </tr>\n  \n  \n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n\n\t    <td width=\"100%\" align=center height='32' ><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 399 */         out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 400 */         out.write("\" onClick=\"javascript:window.parent.close();\"></td>\n\t  </tr>\n\t  </table>\n\n");
/*     */       }
/* 402 */       out.write("\n</body>\n\n</html>\n\n");
/*     */     } catch (Throwable t) {
/* 404 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 405 */         out = _jspx_out;
/* 406 */         if ((out != null) && (out.getBufferSize() != 0))
/* 407 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 408 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 411 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 417 */     PageContext pageContext = _jspx_page_context;
/* 418 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 420 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 421 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 422 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 424 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 426 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 427 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 428 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 429 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 430 */       return true;
/*     */     }
/* 432 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 433 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 438 */     PageContext pageContext = _jspx_page_context;
/* 439 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 441 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 442 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 443 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 445 */     _jspx_th_c_005fif_005f0.setTest("${param.customfield == \"true\"}");
/* 446 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 447 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 449 */         out.write("\n  document.ReportForm.numberOfRows.value=document.ReportForm.customFieldNumberOfRows.value;\n ");
/* 450 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 451 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 455 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 456 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 457 */       return true;
/*     */     }
/* 459 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 460 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 465 */     PageContext pageContext = _jspx_page_context;
/* 466 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 468 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 469 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 470 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 472 */     _jspx_th_c_005fout_005f1.setValue("${param.mondaycapacity}");
/* 473 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 474 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 475 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 476 */       return true;
/*     */     }
/* 478 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 479 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 484 */     PageContext pageContext = _jspx_page_context;
/* 485 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 487 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 488 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 489 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 491 */     _jspx_th_c_005fout_005f2.setValue("${param.customfield}");
/* 492 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 493 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 494 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 495 */       return true;
/*     */     }
/* 497 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 498 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 503 */     PageContext pageContext = _jspx_page_context;
/* 504 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 506 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 507 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 508 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 510 */     _jspx_th_c_005fout_005f3.setValue("${param.customFieldValue}");
/* 511 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 512 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 513 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 514 */       return true;
/*     */     }
/* 516 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 517 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 522 */     PageContext pageContext = _jspx_page_context;
/* 523 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 525 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 526 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 527 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 529 */     _jspx_th_c_005fout_005f4.setValue("${param.customfieldrows}");
/* 530 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 531 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 532 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 533 */       return true;
/*     */     }
/* 535 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 536 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 541 */     PageContext pageContext = _jspx_page_context;
/* 542 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 544 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 545 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 546 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 548 */     _jspx_th_html_005fradio_005f0.setProperty("emailReport");
/*     */     
/* 550 */     _jspx_th_html_005fradio_005f0.setValue("pdf");
/* 551 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 552 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 553 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 554 */       return true;
/*     */     }
/* 556 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 557 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 562 */     PageContext pageContext = _jspx_page_context;
/* 563 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 565 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 566 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 567 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 569 */     _jspx_th_html_005fradio_005f1.setProperty("emailReport");
/*     */     
/* 571 */     _jspx_th_html_005fradio_005f1.setValue("excel");
/* 572 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 573 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 574 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 575 */       return true;
/*     */     }
/* 577 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 578 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 583 */     PageContext pageContext = _jspx_page_context;
/* 584 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 586 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 587 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 588 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 590 */     _jspx_th_html_005ftextarea_005f0.setProperty("comment");
/*     */     
/* 592 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/*     */     
/* 594 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*     */     
/* 596 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/* 597 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 598 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 599 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 600 */       return true;
/*     */     }
/* 602 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 603 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fEmailPDF_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */