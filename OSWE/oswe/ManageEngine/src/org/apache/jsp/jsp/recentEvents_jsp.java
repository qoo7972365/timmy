/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
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
/*     */ 
/*     */ public final class recentEvents_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  45 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  53 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  68 */       ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!--$Id$-->\n\n\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\" width=\"100%\">\n                        <tr>\n                                <td  class=\"tableheadingbborder\" colspan=\"4\">");
/*  74 */       out.print(FormatUtil.getString("am.webclient.mg.eventsummary.onmetrics.text"));
/*  75 */       out.write("</td>\n                        </tr>\n                        ");
/*     */       
/*  77 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  78 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  79 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/*  81 */       _jspx_th_c_005fif_005f0.setTest("${not empty compareEventCount}");
/*  82 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  83 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/*  85 */           out.write("\n                        <tr>\n                                <td  class=\"columnheading\">");
/*  86 */           out.print(FormatUtil.getString("am.mypage.widget.momnitorstype.text"));
/*  87 */           out.write("</td>\n                                <td  class=\"columnheading\">");
/*  88 */           out.print(FormatUtil.getString("am.webclient.mg.metricname.text"));
/*  89 */           out.write("</td>\n                                <td  class=\"columnheading\">");
/*  90 */           out.print(FormatUtil.getString("am.webclient.mg.event.yesterday.count"));
/*  91 */           out.write("</td>\n                                <td  class=\"columnheading\">");
/*  92 */           out.print(FormatUtil.getString("am.webclient.mg.event.today.count"));
/*  93 */           out.write("</td>\n\n                        </tr>\n                        ");
/*  94 */           if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/*  96 */           out.write("\n                        ");
/*  97 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  98 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 102 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 103 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 106 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 107 */         out.write("\n                        ");
/*     */         
/* 109 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 110 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 111 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 113 */         _jspx_th_c_005fif_005f1.setTest("${empty compareEventCount}");
/* 114 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 115 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 117 */             out.write("\n                        <tr>\n                                <td  height=\"50\" align=\"center\" class=\"bodytext\">");
/* 118 */             out.print(FormatUtil.getString("am.webclient.mg.noevents.text"));
/* 119 */             out.write("</td>\n                        </tr>\n                        ");
/* 120 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 121 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 125 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 126 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 129 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 130 */           out.write("\n                </table>\n");
/* 131 */           out.write(10);
/*     */         }
/* 133 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 134 */         out = _jspx_out;
/* 135 */         if ((out != null) && (out.getBufferSize() != 0))
/* 136 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 137 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 140 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 146 */     PageContext pageContext = _jspx_page_context;
/* 147 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 149 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 150 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 151 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 153 */     _jspx_th_c_005fforEach_005f0.setVar("eachCategory");
/*     */     
/* 155 */     _jspx_th_c_005fforEach_005f0.setItems("${compareEventCount}");
/*     */     
/* 157 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 158 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 160 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 161 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) {
/* 163 */           out.write("\n                        <tr  onmouseout=\"this.className='mgsummaryHeader'\" onmouseover=\"this.className='mgsummaryHeaderHover'\" class=\"mgsummaryHeader\">\n                                <td  class=\"whitegrayborder\">");
/* 164 */           boolean bool; if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 165 */             return true;
/* 166 */           out.write("</td>\n                                <td  class=\"whitegrayborderbr\">");
/* 167 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 168 */             return true;
/* 169 */           out.write("</td>\n                                <td  class=\"whitegrayborder\">");
/* 170 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 171 */             return true;
/* 172 */           out.write("</td>\n                                <td  class=\"whitegrayborder\">");
/* 173 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 174 */             return true;
/* 175 */           out.write("</td>\n                        </tr>\n                        ");
/* 176 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 177 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 181 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 182 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 185 */         int tmp313_312 = 0; int[] tmp313_310 = _jspx_push_body_count_c_005fforEach_005f0; int tmp315_314 = tmp313_310[tmp313_312];tmp313_310[tmp313_312] = (tmp315_314 - 1); if (tmp315_314 <= 0) break;
/* 186 */         out = _jspx_page_context.popBody(); }
/* 187 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 189 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 190 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 197 */     PageContext pageContext = _jspx_page_context;
/* 198 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 200 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 201 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 202 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 204 */     _jspx_th_c_005fout_005f0.setValue("${eachCategory.resourcetypedisplayname}");
/* 205 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 206 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 208 */       return true;
/*     */     }
/* 210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 216 */     PageContext pageContext = _jspx_page_context;
/* 217 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 219 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 220 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 221 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 223 */     _jspx_th_c_005fout_005f1.setValue("${eachCategory.displayname}");
/* 224 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 225 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 226 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 227 */       return true;
/*     */     }
/* 229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 230 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 235 */     PageContext pageContext = _jspx_page_context;
/* 236 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 238 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 239 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 240 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 242 */     _jspx_th_c_005fout_005f2.setValue("${eachCategory.yesterdayEventCount}");
/* 243 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 244 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 245 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 246 */       return true;
/*     */     }
/* 248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 249 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 254 */     PageContext pageContext = _jspx_page_context;
/* 255 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 257 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 258 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 259 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 261 */     _jspx_th_c_005fout_005f3.setValue("${eachCategory.todayEventCount}");
/* 262 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 263 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 265 */       return true;
/*     */     }
/* 267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 268 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\recentEvents_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */