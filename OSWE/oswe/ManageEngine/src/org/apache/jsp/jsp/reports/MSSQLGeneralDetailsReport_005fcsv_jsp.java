/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ 
/*     */ public final class MSSQLGeneralDetailsReport_005fcsv_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.release();
/*  49 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
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
/*  77 */       out.write("\n\n\n\n\n\n\n\n\n\n \n\n\n\n\n\n");
/*     */       
/*     */ 
/*  80 */       response.setContentType("text/html;charset=" + com.adventnet.appmanager.util.Constants.getCharSet());
/*     */       
/*  82 */       response.setHeader("Content-Disposition", "attachment;filename=MSSQLGeneralDetailsReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/*     */       
/*     */ 
/*  85 */       out.write(10);
/*  86 */       out.write(10);
/*     */       
/*  88 */       OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.get(OutTag.class);
/*  89 */       _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  90 */       _jspx_th_c_005fout_005f0.setParent(null);
/*     */       
/*  92 */       _jspx_th_c_005fout_005f0.setValue("${heading}");
/*     */       
/*  94 */       _jspx_th_c_005fout_005f0.setEscapeXml("false");
/*  95 */       int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  96 */       if (_jspx_eval_c_005fout_005f0 != 0) {
/*  97 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/*  98 */           out = _jspx_page_context.pushBody();
/*  99 */           _jspx_th_c_005fout_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 100 */           _jspx_th_c_005fout_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 103 */           out.print(FormatUtil.getString("webclient.performance.reports.commonheader"));
/* 104 */           int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/* 105 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 108 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 109 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 112 */       if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 113 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/*     */       }
/*     */       else {
/* 116 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/* 117 */         out.write(10);
/* 118 */         out.write(10);
/* 119 */         request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/* 120 */         out.write(10);
/* 121 */         out.write(10);
/* 122 */         out.write(34);
/* 123 */         out.print(FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text"));
/* 124 */         out.write(32);
/* 125 */         out.write(58);
/* 126 */         out.write(32);
/* 127 */         if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */           return;
/* 129 */         out.write("\"\n\n\n\n");
/* 130 */         String[] selectedColumns = (String[])request.getAttribute("selectedColumns");
/*     */         
/* 132 */         HashMap map = (HashMap)request.getAttribute("columnHeadings");
/*     */         
/* 134 */         for (int i = 0; i < selectedColumns.length; i++)
/*     */         {
/* 136 */           if (i == selectedColumns.length - 1)
/*     */           {
/* 138 */             out.println((String)map.get(selectedColumns[i]));
/*     */           }
/*     */           else
/*     */           {
/* 142 */             out.print((String)map.get(selectedColumns[i]) + ",");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 148 */         ArrayList data = (ArrayList)request.getAttribute("data");
/*     */         
/* 150 */         for (int i = 0; i < data.size(); i++)
/*     */         {
/* 152 */           Properties p = (Properties)data.get(i);
/*     */           
/* 154 */           for (int j = 0; j < selectedColumns.length; j++)
/*     */           {
/* 156 */             if (j == selectedColumns.length - 1)
/*     */             {
/* 158 */               out.println(p.getProperty(selectedColumns[j]));
/*     */             }
/*     */             else
/*     */             {
/* 162 */               out.print(p.getProperty(selectedColumns[j]) + ",");
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */         out.write(10);
/* 172 */         out.write(10);
/*     */         
/* 174 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 175 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 176 */         _jspx_th_c_005fif_005f0.setParent(null);
/*     */         
/* 178 */         _jspx_th_c_005fif_005f0.setTest("${(strTime !='0') && (strTime!=null)}");
/* 179 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 180 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */           for (;;) {
/* 182 */             out.write(" \n\n\"");
/* 183 */             out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/* 184 */             out.write(32);
/* 185 */             if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */               return;
/* 187 */             out.write(32);
/* 188 */             out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/* 189 */             out.write(32);
/* 190 */             out.write(32);
/* 191 */             if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */               return;
/* 193 */             out.write(" \"\n\n");
/* 194 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 195 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 199 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 200 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */         }
/*     */         else {
/* 203 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 204 */           out.write("\n\n\n\n");
/*     */         }
/* 206 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 207 */         out = _jspx_out;
/* 208 */         if ((out != null) && (out.getBufferSize() != 0))
/* 209 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 210 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 213 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 219 */     PageContext pageContext = _jspx_page_context;
/* 220 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 222 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 223 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 224 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 226 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 228 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 229 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 230 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 231 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 232 */       return true;
/*     */     }
/* 234 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 240 */     PageContext pageContext = _jspx_page_context;
/* 241 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 243 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 244 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 245 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 247 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${strTime}");
/*     */     
/* 249 */     _jspx_th_fmt_005fformatDate_005f1.setType("both");
/* 250 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 251 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 252 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 253 */       return true;
/*     */     }
/* 255 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 256 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 261 */     PageContext pageContext = _jspx_page_context;
/* 262 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 264 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 265 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 266 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 268 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${endTime}");
/*     */     
/* 270 */     _jspx_th_fmt_005fformatDate_005f2.setType("both");
/* 271 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 272 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 273 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 274 */       return true;
/*     */     }
/* 276 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 277 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\MSSQLGeneralDetailsReport_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */