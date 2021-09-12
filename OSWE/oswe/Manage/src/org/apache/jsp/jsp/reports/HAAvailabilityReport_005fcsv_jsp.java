/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ 
/*     */ public final class HAAvailabilityReport_005fcsv_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.release();
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*  56 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  58 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  60 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  67 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  70 */     JspWriter out = null;
/*  71 */     Object page = this;
/*  72 */     JspWriter _jspx_out = null;
/*  73 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  77 */       response.setContentType("text/html");
/*  78 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  80 */       _jspx_page_context = pageContext;
/*  81 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  82 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  83 */       session = pageContext.getSession();
/*  84 */       out = pageContext.getOut();
/*  85 */       _jspx_out = out;
/*     */       
/*  87 */       out.write(10);
/*  88 */       out.write(32);
/*  89 */       out.write(10);
/*     */       
/*  91 */       response.setContentType("text/html;charset=" + com.adventnet.appmanager.util.Constants.getCharSet());
/*  92 */       response.setHeader("Content-Disposition", "attachment;filename=MonitorGroupAvailabilityReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/*     */       
/*  94 */       out.write(32);
/*  95 */       out.write(10);
/*     */       
/*  97 */       DBUtil db = new DBUtil();
/*  98 */       String MGVAL = db.getGlobalConfigValueForMGAvailability();
/*     */       
/* 100 */       out.write(10);
/*     */       
/* 102 */       OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.get(OutTag.class);
/* 103 */       _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 104 */       _jspx_th_c_005fout_005f0.setParent(null);
/*     */       
/* 106 */       _jspx_th_c_005fout_005f0.setValue("${heading}");
/*     */       
/* 108 */       _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 109 */       int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 110 */       if (_jspx_eval_c_005fout_005f0 != 0) {
/* 111 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 112 */           out = _jspx_page_context.pushBody();
/* 113 */           _jspx_th_c_005fout_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 114 */           _jspx_th_c_005fout_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 117 */           out.write(32);
/* 118 */           out.print(FormatUtil.getString("webclient.performance.reports.commonheader"));
/* 119 */           out.write(32);
/* 120 */           int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/* 121 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 124 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 125 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 128 */       if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 129 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/*     */       }
/*     */       else {
/* 132 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/* 133 */         out.write(10);
/* 134 */         request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/* 135 */         out.write("\n\n\" ");
/* 136 */         out.print(FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text"));
/* 137 */         out.write(32);
/* 138 */         out.write(58);
/* 139 */         out.write(32);
/* 140 */         if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */           return;
/* 142 */         out.write(34);
/* 143 */         out.write(10);
/* 144 */         if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*     */           return;
/* 146 */         out.write("\n        ");
/* 147 */         String overallname = (String)pageContext.getAttribute("overall");
/*     */         
/*     */ 
/* 150 */         out.write(10);
/* 151 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.heading.text", new String[] { overallname }));
/* 152 */         out.write(32);
/* 153 */         out.write(10);
/* 154 */         out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 155 */         out.write(32);
/* 156 */         out.write(45);
/* 157 */         out.write(32);
/* 158 */         if ("true".equalsIgnoreCase(MGVAL)) {
/* 159 */           out.write(32);
/* 160 */           if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */             return;
/* 162 */           out.write(32);
/*     */         }
/* 164 */         else if (_jspx_meth_c_005fout_005f3(_jspx_page_context)) {
/*     */           return;
/*     */         }
/* 167 */         out.write(37);
/* 168 */         out.write(44);
/* 169 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.downtime.text"));
/* 170 */         out.write(32);
/* 171 */         out.write(45);
/* 172 */         out.write(32);
/* 173 */         if ("true".equalsIgnoreCase(MGVAL)) {
/* 174 */           out.write(32);
/* 175 */           if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*     */             return;
/* 177 */           out.write(32);
/*     */         }
/* 179 */         else if (_jspx_meth_c_005fout_005f5(_jspx_page_context)) {
/*     */           return;
/*     */         }
/* 182 */         out.write(37);
/* 183 */         out.write(32);
/* 184 */         if (("true".equalsIgnoreCase(MGVAL)) && ("false".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity))) {
/* 185 */           out.write(44);
/* 186 */           out.print(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text"));
/* 187 */           out.write(32);
/* 188 */           out.write(45);
/* 189 */           out.write(32);
/* 190 */           if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*     */             return;
/* 192 */           out.write(37);
/* 193 */           out.write(44);
/* 194 */           out.print(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text"));
/* 195 */           out.write(32);
/* 196 */           out.write(45);
/* 197 */           out.write(32);
/* 198 */           if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*     */             return;
/* 200 */           out.write(37);
/* 201 */           out.write(32);
/*     */         }
/* 203 */         out.write("  \n\n\n");
/* 204 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.headingformonitors.text", new String[] { overallname }));
/* 205 */         out.write(10);
/* 206 */         out.write(10);
/* 207 */         out.print(FormatUtil.getString("am.reporttab.performancereport.heading.text"));
/* 208 */         out.write(44);
/* 209 */         out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 210 */         out.write(44);
/* 211 */         out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/* 212 */         out.write(44);
/* 213 */         out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/* 214 */         out.write(44);
/* 215 */         out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 216 */         out.write(" % \n");
/* 217 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*     */           return;
/* 219 */         out.write(32);
/* 220 */         out.write(10);
/*     */         
/* 222 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 223 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 224 */         _jspx_th_c_005fif_005f0.setParent(null);
/*     */         
/* 226 */         _jspx_th_c_005fif_005f0.setTest("${strTime !='0'}");
/* 227 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 228 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */           for (;;) {
/* 230 */             out.write(32);
/* 231 */             out.write(10);
/* 232 */             out.write(34);
/* 233 */             out.print(FormatUtil.getString("am.reporttab.footer.message.text"));
/* 234 */             out.write(32);
/* 235 */             if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */               return;
/* 237 */             out.write(32);
/* 238 */             out.print(FormatUtil.getString("am.reporttab.footer.messageto.text"));
/* 239 */             out.write(32);
/* 240 */             if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */               return;
/* 242 */             out.write(34);
/* 243 */             out.write(10);
/* 244 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 245 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 249 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 250 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */         }
/*     */         else
/* 253 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/* 255 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 256 */         out = _jspx_out;
/* 257 */         if ((out != null) && (out.getBufferSize() != 0))
/* 258 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 259 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 262 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 268 */     PageContext pageContext = _jspx_page_context;
/* 269 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 271 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 272 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 273 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 275 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 277 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 278 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 279 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 280 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 281 */       return true;
/*     */     }
/* 283 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 284 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 289 */     PageContext pageContext = _jspx_page_context;
/* 290 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 292 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 293 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 294 */     _jspx_th_c_005fset_005f0.setParent(null);
/*     */     
/* 296 */     _jspx_th_c_005fset_005f0.setVar("overall");
/* 297 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 298 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 299 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 300 */         out = _jspx_page_context.pushBody();
/* 301 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 302 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 305 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 306 */           return true;
/* 307 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 308 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 311 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 312 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 315 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 316 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 317 */       return true;
/*     */     }
/* 319 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 325 */     PageContext pageContext = _jspx_page_context;
/* 326 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 328 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 329 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 330 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 332 */     _jspx_th_c_005fout_005f1.setValue("${overAllAvailability.Name}");
/* 333 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 334 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 335 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 336 */       return true;
/*     */     }
/* 338 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 339 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 344 */     PageContext pageContext = _jspx_page_context;
/* 345 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 347 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 348 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 349 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 351 */     _jspx_th_c_005fout_005f2.setValue("${overAllAvailability.ServicesUpPercent}");
/* 352 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 353 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 355 */       return true;
/*     */     }
/* 357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 358 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 363 */     PageContext pageContext = _jspx_page_context;
/* 364 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 366 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 367 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 368 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 370 */     _jspx_th_c_005fout_005f3.setValue("${overAllAvailability.available}");
/* 371 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 372 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 373 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 374 */       return true;
/*     */     }
/* 376 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 377 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 382 */     PageContext pageContext = _jspx_page_context;
/* 383 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 385 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 386 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 387 */     _jspx_th_c_005fout_005f4.setParent(null);
/*     */     
/* 389 */     _jspx_th_c_005fout_005f4.setValue("${overAllAvailability.ServicesDownPercent}");
/* 390 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 391 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 392 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 393 */       return true;
/*     */     }
/* 395 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 396 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 401 */     PageContext pageContext = _jspx_page_context;
/* 402 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 404 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 405 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 406 */     _jspx_th_c_005fout_005f5.setParent(null);
/*     */     
/* 408 */     _jspx_th_c_005fout_005f5.setValue("${overAllAvailability.unavailable}");
/* 409 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 410 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 411 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 412 */       return true;
/*     */     }
/* 414 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 415 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 420 */     PageContext pageContext = _jspx_page_context;
/* 421 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 423 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 424 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 425 */     _jspx_th_c_005fout_005f6.setParent(null);
/*     */     
/* 427 */     _jspx_th_c_005fout_005f6.setValue("${overAllAvailability.ServicesUnMgPercent}");
/* 428 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 429 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 430 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 431 */       return true;
/*     */     }
/* 433 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 434 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 439 */     PageContext pageContext = _jspx_page_context;
/* 440 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 442 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 443 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 444 */     _jspx_th_c_005fout_005f7.setParent(null);
/*     */     
/* 446 */     _jspx_th_c_005fout_005f7.setValue("${overAllAvailability.ServicesSchPercent}");
/* 447 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 448 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 449 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 450 */       return true;
/*     */     }
/* 452 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 453 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 458 */     PageContext pageContext = _jspx_page_context;
/* 459 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 461 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 462 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 463 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */     
/* 465 */     _jspx_th_c_005fforEach_005f0.setVar("row");
/*     */     
/* 467 */     _jspx_th_c_005fforEach_005f0.setItems("${data}");
/*     */     
/* 469 */     _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/* 470 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 472 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 473 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) { boolean bool;
/* 475 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 476 */             return true;
/* 477 */           out.write(44);
/* 478 */           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 479 */             return true;
/* 480 */           out.write(44);
/* 481 */           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 482 */             return true;
/* 483 */           out.write(44);
/* 484 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 485 */             return true;
/* 486 */           out.write(44);
/* 487 */           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 488 */             return true;
/* 489 */           out.write(37);
/* 490 */           out.write(32);
/* 491 */           out.write(10);
/* 492 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 493 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 497 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 498 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 501 */         int tmp348_347 = 0; int[] tmp348_345 = _jspx_push_body_count_c_005fforEach_005f0; int tmp350_349 = tmp348_345[tmp348_347];tmp348_345[tmp348_347] = (tmp350_349 - 1); if (tmp350_349 <= 0) break;
/* 502 */         out = _jspx_page_context.popBody(); }
/* 503 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 505 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 506 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 508 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 513 */     PageContext pageContext = _jspx_page_context;
/* 514 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 516 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 517 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 518 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 520 */     _jspx_th_c_005fout_005f8.setValue("${row.Name}");
/*     */     
/* 522 */     _jspx_th_c_005fout_005f8.setEscapeXml("false");
/* 523 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 524 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 526 */       return true;
/*     */     }
/* 528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 529 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 534 */     PageContext pageContext = _jspx_page_context;
/* 535 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 537 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 538 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 539 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 541 */     _jspx_th_c_005fout_005f9.setValue("${row.totaldowntime}");
/* 542 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 543 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 544 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 545 */       return true;
/*     */     }
/* 547 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 548 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 553 */     PageContext pageContext = _jspx_page_context;
/* 554 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 556 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 557 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 558 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 560 */     _jspx_th_c_005fout_005f10.setValue("${row.mttr}");
/* 561 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 562 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 563 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 564 */       return true;
/*     */     }
/* 566 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 567 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 572 */     PageContext pageContext = _jspx_page_context;
/* 573 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 575 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 576 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 577 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 579 */     _jspx_th_c_005fout_005f11.setValue("${row.mtbf}");
/* 580 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 581 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 582 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 583 */       return true;
/*     */     }
/* 585 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 586 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 591 */     PageContext pageContext = _jspx_page_context;
/* 592 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 594 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 595 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 596 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 598 */     _jspx_th_c_005fout_005f12.setValue("${row.available}");
/* 599 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 600 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 601 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 602 */       return true;
/*     */     }
/* 604 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 605 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 610 */     PageContext pageContext = _jspx_page_context;
/* 611 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 613 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 614 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 615 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 617 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${strTime}");
/*     */     
/* 619 */     _jspx_th_fmt_005fformatDate_005f1.setType("both");
/* 620 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 621 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 622 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 623 */       return true;
/*     */     }
/* 625 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 626 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 631 */     PageContext pageContext = _jspx_page_context;
/* 632 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 634 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 635 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 636 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 638 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${endTime}");
/*     */     
/* 640 */     _jspx_th_fmt_005fformatDate_005f2.setType("both");
/* 641 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 642 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 643 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 644 */       return true;
/*     */     }
/* 646 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 647 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\HAAvailabilityReport_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */