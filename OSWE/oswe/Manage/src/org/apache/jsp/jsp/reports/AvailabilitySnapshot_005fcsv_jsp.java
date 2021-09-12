/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AvailabilitySnapshot_005fcsv_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  29 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  48 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.release();
/*  53 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  60 */     HttpSession session = null;
/*     */     
/*     */ 
/*  63 */     JspWriter out = null;
/*  64 */     Object page = this;
/*  65 */     JspWriter _jspx_out = null;
/*  66 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  70 */       response.setContentType("text/html");
/*  71 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  73 */       _jspx_page_context = pageContext;
/*  74 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  75 */       ServletConfig config = pageContext.getServletConfig();
/*  76 */       session = pageContext.getSession();
/*  77 */       out = pageContext.getOut();
/*  78 */       _jspx_out = out;
/*     */       
/*  80 */       out.write(10);
/*  81 */       out.write(32);
/*  82 */       out.write(10);
/*     */       
/*  84 */       response.setContentType("text/html;charset=" + Constants.getCharSet());
/*  85 */       response.setHeader("Content-Disposition", "attachment;filename=AvailabilitySnapshotReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/*     */       
/*  87 */       out.write(10);
/*     */       
/*  89 */       OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.get(OutTag.class);
/*  90 */       _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  91 */       _jspx_th_c_005fout_005f0.setParent(null);
/*     */       
/*  93 */       _jspx_th_c_005fout_005f0.setValue("${heading}");
/*     */       
/*  95 */       _jspx_th_c_005fout_005f0.setEscapeXml("false");
/*  96 */       int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  97 */       if (_jspx_eval_c_005fout_005f0 != 0) {
/*  98 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/*  99 */           out = _jspx_page_context.pushBody();
/* 100 */           _jspx_th_c_005fout_005f0.setBodyContent((BodyContent)out);
/* 101 */           _jspx_th_c_005fout_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 104 */           out.print(FormatUtil.getString("webclient.performance.reports.commonheader"));
/* 105 */           int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/* 106 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 109 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 110 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 113 */       if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 114 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/*     */       }
/*     */       else {
/* 117 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/* 118 */         out.write(10);
/* 119 */         request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/* 120 */         out.write(10);
/*     */         
/* 122 */         out.write(10);
/* 123 */         out.write(34);
/* 124 */         out.print(FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text"));
/* 125 */         out.write(32);
/* 126 */         out.write(58);
/* 127 */         out.write(32);
/* 128 */         if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */           return;
/* 130 */         out.write(34);
/* 131 */         out.write(10);
/*     */         try {
/* 133 */           ArrayList AllValues = (ArrayList)request.getAttribute("AllValues");
/*     */           
/* 135 */           out.write(10);
/* 136 */           out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/* 137 */           out.write(44);
/* 138 */           out.print(FormatUtil.getString("Outages Duration"));
/* 139 */           out.write(44);
/* 140 */           out.print(FormatUtil.getString("Outages Count"));
/* 141 */           out.write(44);
/* 142 */           out.print(FormatUtil.getString("Warning/Critical Duration"));
/* 143 */           out.write(44);
/* 144 */           out.print(FormatUtil.getString("Warning/Critical Count"));
/* 145 */           out.write(44);
/* 146 */           out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 147 */           out.write(44);
/* 148 */           out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 149 */           out.write(44);
/* 150 */           out.print(FormatUtil.getString("am.webclient.reports.functionalhostname.text"));
/* 151 */           out.write(44);
/* 152 */           out.print(FormatUtil.getString("am.webclient.newaction.message"));
/* 153 */           out.write(44);
/* 154 */           out.write(10);
/* 155 */           int size = AllValues.size();
/* 156 */           for (int i = 0; i < size; i++)
/*     */           {
/* 158 */             ArrayList a1 = (ArrayList)AllValues.get(i);
/* 159 */             String resname = a1.get(1).toString();
/* 160 */             String type = a1.get(2).toString();
/* 161 */             ArrayList downtimesummary = (ArrayList)a1.get(3);
/* 162 */             for (int j = 0; j < downtimesummary.size(); j++) {
/* 163 */               Properties rows = (Properties)downtimesummary.get(j);
/* 164 */               if (rows != null) {
/* 165 */                 String from = (String)rows.get("downtime");
/* 166 */                 String to = (String)rows.get("uptime");
/* 167 */                 String hostname = (String)rows.get("moname");
/* 168 */                 String Totaldowntime = (String)rows.get("TotalDownTime");
/*     */                 
/* 170 */                 String countVaraible = " ";
/* 171 */                 if (!"".equals(Totaldowntime)) {
/* 172 */                   countVaraible = "1";
/* 173 */                   Totaldowntime = ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("TotalDownTime"))));
/*     */                 }
/* 175 */                 String attributeType = (String)rows.get("type");
/*     */                 
/* 177 */                 if (j >= 1)
/*     */                 {
/* 179 */                   out.write(45);
/*     */                 } else {
/* 181 */                   out.print(resname);
/*     */                 }
/* 183 */                 out.write(44);
/* 184 */                 if ("Availability".equals(attributeType)) {
/* 185 */                   out.print(Totaldowntime);
/* 186 */                   out.write(44);
/* 187 */                   out.print(countVaraible);
/* 188 */                   out.write(",-,-,");
/*     */                 }
/* 190 */                 if ("Health".equals(attributeType)) {
/* 191 */                   out.write("-,-,");
/* 192 */                   out.print(Totaldowntime);
/* 193 */                   out.write(44);
/* 194 */                   out.print(countVaraible);
/* 195 */                   out.write(44);
/*     */                 }
/* 197 */                 if ("TA".equals(attributeType)) {
/* 198 */                   out.print(ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("AvailabilityTotalDownTime")))));
/* 199 */                   out.write(44);
/* 200 */                   out.print((String)rows.get("availcount"));
/* 201 */                   out.write(44);
/* 202 */                   out.print(ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("HealthTotalDownTime")))));
/* 203 */                   out.write(44);
/* 204 */                   out.print((String)rows.get("healthcount"));
/* 205 */                   out.write(44);
/*     */                 }
/* 207 */                 out.write(10);
/* 208 */                 if ("TA".equals(attributeType)) {
/* 209 */                   out.write("-,-,-,");
/*     */                 } else {
/* 211 */                   out.print(FormatUtil.formatDT(from));
/* 212 */                   out.write(44);
/* 213 */                   out.print(FormatUtil.formatDT(to));
/* 214 */                   out.write(44);
/* 215 */                   out.print(hostname);
/* 216 */                   out.write(44);
/*     */                 }
/* 218 */                 if ("Health".equals(attributeType)) {
/* 219 */                   out.print((String)rows.get("message"));
/* 220 */                 } else if ("Availability".equals(attributeType)) {
/* 221 */                   out.print(FormatUtil.getString("am.webclient.reports.resourcedownmessage.text"));
/*     */                 } else {
/* 223 */                   out.print(FormatUtil.getString("am.webclient.reports.availabilitypercentagemessage.text", new String[] { (String)rows.get("Available") }));
/*     */                 }
/* 225 */                 out.write(10);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 231 */           ex.printStackTrace();
/*     */         }
/*     */         
/* 234 */         out.write("\n\n\n\n\n\n");
/*     */       }
/* 236 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 237 */         out = _jspx_out;
/* 238 */         if ((out != null) && (out.getBufferSize() != 0))
/* 239 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 240 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 243 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 249 */     PageContext pageContext = _jspx_page_context;
/* 250 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 252 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 253 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 254 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 256 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 258 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 259 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 260 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 261 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 262 */       return true;
/*     */     }
/* 264 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 265 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilitySnapshot_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */