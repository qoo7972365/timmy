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
/*     */ public final class OutageReport_005fcsv_jsp extends HttpJspBase implements JspSourceDependent
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
/*  85 */       response.setHeader("Content-Disposition", "attachment;filename=OutageReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/*     */       
/*  87 */       out.write(32);
/*  88 */       out.write(10);
/*     */       
/*  90 */       OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.get(OutTag.class);
/*  91 */       _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  92 */       _jspx_th_c_005fout_005f0.setParent(null);
/*     */       
/*  94 */       _jspx_th_c_005fout_005f0.setValue("${heading}");
/*     */       
/*  96 */       _jspx_th_c_005fout_005f0.setEscapeXml("false");
/*  97 */       int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  98 */       if (_jspx_eval_c_005fout_005f0 != 0) {
/*  99 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 100 */           out = _jspx_page_context.pushBody();
/* 101 */           _jspx_th_c_005fout_005f0.setBodyContent((BodyContent)out);
/* 102 */           _jspx_th_c_005fout_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 105 */           out.print(FormatUtil.getString("webclient.performance.reports.commonheader"));
/* 106 */           int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/* 107 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 110 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 111 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 114 */       if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 115 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/*     */       }
/*     */       else {
/* 118 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/* 119 */         out.write(10);
/* 120 */         request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/* 121 */         out.write(10);
/* 122 */         out.write(34);
/* 123 */         out.print(FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text"));
/* 124 */         out.write(32);
/* 125 */         out.write(58);
/* 126 */         out.write(32);
/* 127 */         if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */           return;
/* 129 */         out.write(34);
/* 130 */         out.write(10);
/*     */         
/* 132 */         ArrayList Values = (ArrayList)request.getAttribute("mgvalues");
/* 133 */         String attri = (String)request.getAttribute("timeperiod");
/* 134 */         String custom = (String)request.getAttribute("CUSTOM");
/* 135 */         Properties timeprops = (Properties)request.getAttribute("timeprops");
/* 136 */         String t1 = "";
/* 137 */         String t2 = "";
/*     */         
/* 139 */         if ((custom != null) && (custom.equals("true"))) {
/* 140 */           t1 = "(" + timeprops.getProperty("previousfrom") + " - " + timeprops.getProperty("previousto") + ")";
/* 141 */           t2 = "(" + timeprops.getProperty("currentfrom") + " - " + timeprops.getProperty("currentto") + ")";
/*     */ 
/*     */ 
/*     */         }
/* 145 */         else if ("month".equals(attri)) {
/* 146 */           t1 = FormatUtil.getString("Last Month");
/* 147 */           t2 = FormatUtil.getString("This Month");
/*     */         }
/*     */         else
/*     */         {
/* 151 */           t1 = FormatUtil.getString("Last Week");
/* 152 */           t2 = FormatUtil.getString("This Week");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 157 */         out.write(10);
/* 158 */         out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 159 */         out.write(32);
/* 160 */         out.write(44);
/* 161 */         out.print(FormatUtil.getString("am.webclient.reports.outagetime.compariosonreport.tableheading.thisperiod.text", new String[] { t1 }));
/* 162 */         out.write(44);
/* 163 */         out.print(FormatUtil.getString("am.webclient.reports.outagetime.compariosonreport.tableheading.lastperiod.text", new String[] { t1 }));
/* 164 */         out.write(32);
/* 165 */         out.write(44);
/* 166 */         out.print(FormatUtil.getString("am.webclient.reports.numberoutagetime.compariosonreport.text", new String[] { t2 }));
/* 167 */         out.write(44);
/* 168 */         out.print(FormatUtil.getString("am.webclient.reports.durationoutagetime.compariosonreport.text", new String[] { t2 }));
/* 169 */         out.write(32);
/* 170 */         out.write(44);
/* 171 */         out.print(FormatUtil.getString("am.webclient.reports.change.compariosonreport.text"));
/* 172 */         out.write(44);
/* 173 */         out.print(FormatUtil.getString("am.webclient.reports.percentageoutagetime.compariosonreport.text"));
/* 174 */         out.write(44);
/* 175 */         out.print(FormatUtil.getString("am.webclient.reports.difference.compariosonreport.text"));
/* 176 */         out.write(44);
/* 177 */         out.print(FormatUtil.getString("am.webclient.reports.percentageoutagetime.compariosonreport.text"));
/* 178 */         out.write(10);
/*     */         
/* 180 */         if ((Values != null) && (Values.size() > 0)) {
/*     */           try {
/* 182 */             out.write("\n   ");
/*     */             
/* 184 */             for (int i = 0; i < Values.size(); i++) {
/* 185 */               ArrayList a1 = (ArrayList)Values.get(i);
/* 186 */               String mgname = a1.get(1).toString();
/* 187 */               String LastOutage = a1.get(2).toString();
/* 188 */               String LastDuration = a1.get(3).toString();
/* 189 */               String ThisOutage = a1.get(4).toString();
/* 190 */               String ThisDuration = a1.get(5).toString();
/* 191 */               String cg = a1.get(6).toString();
/* 192 */               String cgpercent = a1.get(7).toString();
/* 193 */               String diff = ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(a1.get(8).toString())));
/* 194 */               String diffpercent = a1.get(9).toString();
/* 195 */               String type = a1.get(10).toString();
/* 196 */               if ("HAI".equals(type))
/*     */               {
/* 198 */                 out.print(mgname);
/* 199 */                 out.write(44);
/* 200 */                 out.print(ThisOutage);
/* 201 */                 out.write(44);
/* 202 */                 out.print(ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(ThisDuration))));
/* 203 */                 out.write(44);
/* 204 */                 out.print(LastOutage);
/* 205 */                 out.write(44);
/* 206 */                 out.print(ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(LastDuration))));
/* 207 */                 out.write(44);
/* 208 */                 out.print(cg);
/* 209 */                 out.write(44);
/* 210 */                 out.print(cgpercent);
/* 211 */                 out.write(37);
/* 212 */                 out.write(44);
/* 213 */                 out.print(diff);
/* 214 */                 out.write(44);
/* 215 */                 out.print(diffpercent);
/* 216 */                 out.write("%\n    ");
/*     */               }
/*     */             }
/* 219 */             out.write(10);
/*     */           } catch (Exception ex) {
/* 221 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/* 225 */         out.write(10);
/* 226 */         out.write(10);
/*     */       }
/* 228 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 229 */         out = _jspx_out;
/* 230 */         if ((out != null) && (out.getBufferSize() != 0))
/* 231 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 232 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 235 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 241 */     PageContext pageContext = _jspx_page_context;
/* 242 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 244 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 245 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 246 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 248 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 250 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 251 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 252 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 253 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 254 */       return true;
/*     */     }
/* 256 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 257 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\OutageReport_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */