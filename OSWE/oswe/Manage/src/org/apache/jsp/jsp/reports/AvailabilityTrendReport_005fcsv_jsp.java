/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
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
/*     */ public final class AvailabilityTrendReport_005fcsv_jsp extends HttpJspBase implements JspSourceDependent
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
/*     */     throws IOException, ServletException
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
/*  85 */       response.setHeader("Content-Disposition", "attachment;filename=AvailabilityTrendReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
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
/* 132 */         ArrayList Values = (ArrayList)request.getAttribute("allvalues");
/* 133 */         ArrayList time = (ArrayList)request.getAttribute("displaytime");
/*     */         
/* 135 */         out.write(10);
/* 136 */         out.print(FormatUtil.getString("Time Periods"));
/* 137 */         out.write(44);
/*     */         
/* 139 */         for (int i = 12; i >= 0; i--)
/*     */         {
/* 141 */           String timetodisplay = time.get(i).toString();
/*     */           
/* 143 */           out.print(timetodisplay);
/* 144 */           out.write(44);
/*     */         }
/* 146 */         out.write(10);
/* 147 */         out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 148 */         out.write(44);
/* 149 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "12" }));
/* 150 */         out.write(44);
/* 151 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "11" }));
/* 152 */         out.write(44);
/* 153 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "10" }));
/* 154 */         out.write(44);
/* 155 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "9" }));
/* 156 */         out.write(44);
/* 157 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "8" }));
/* 158 */         out.write(44);
/* 159 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "7" }));
/* 160 */         out.write(44);
/* 161 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "6" }));
/* 162 */         out.write(44);
/* 163 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "5" }));
/* 164 */         out.write(44);
/* 165 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "4" }));
/* 166 */         out.write(44);
/* 167 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "3" }));
/* 168 */         out.write(44);
/* 169 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "2" }));
/* 170 */         out.write(44);
/* 171 */         out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.periodvalue.text", new String[] { "1" }));
/* 172 */         out.write(44);
/* 173 */         out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 174 */         out.write(10);
/* 175 */         out.write(10);
/* 176 */         for (int i = 0; i < Values.size(); i++) {
/* 177 */           ArrayList a1 = (ArrayList)Values.get(i);
/* 178 */           String mgname = a1.get(2).toString();
/*     */           
/* 180 */           String type = a1.get(3).toString();
/* 181 */           HashMap data = (HashMap)a1.get(4);
/* 182 */           if ("HAI".equals(type))
/*     */           {
/* 184 */             out.print(mgname);
/* 185 */             for (int j = 12; j >= 0; j--) {
/* 186 */               int key = j + 1;
/* 187 */               String keyName = key + "";
/* 188 */               String uptime = data.get(keyName).toString();
/* 189 */               out.write(44);
/* 190 */               out.print(uptime);
/*     */             }
/* 192 */             out.write(32);
/* 193 */             out.write(10);
/*     */           }
/*     */         }
/* 196 */         out.write(32);
/* 197 */         out.write(10);
/* 198 */         out.write(10);
/*     */       }
/* 200 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 201 */         out = _jspx_out;
/* 202 */         if ((out != null) && (out.getBufferSize() != 0))
/* 203 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 204 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 207 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 213 */     PageContext pageContext = _jspx_page_context;
/* 214 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 216 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 217 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 218 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 220 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 222 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 223 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 224 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 225 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 226 */       return true;
/*     */     }
/* 228 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 229 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilityTrendReport_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */