/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public final class wsmbreadcrumb_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  52 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t");
/*     */       
/*  74 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  75 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  76 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/*  78 */       _jspx_th_c_005fif_005f0.setTest("${param.method=='showdetails'}");
/*  79 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  80 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/*  82 */           out.write("\t\n       <td class=\"bcsign\" height=\"22\">");
/*  83 */           out.print(BreadcrumbUtil.getMonitorsPage());
/*  84 */           out.write(" &gt; ");
/*  85 */           out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/*  86 */           out.write(" &gt; <span class=\"bcactive\">");
/*  87 */           out.print(request.getAttribute("displayname"));
/*  88 */           out.write(" </span></td>\n\t");
/*  89 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  90 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/*  94 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  95 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/*  98 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  99 */         out.write(10);
/* 100 */         out.write(9);
/*     */         
/* 102 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 103 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 104 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 106 */         _jspx_th_c_005fif_005f1.setTest("${param.method=='manageoperations'}");
/* 107 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 108 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 110 */             out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 111 */             out.print(BreadcrumbUtil.getMonitorsPage());
/* 112 */             out.write(" &gt; ");
/* 113 */             out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 114 */             out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 115 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*     */               return;
/* 117 */             out.write(34);
/* 118 */             out.write(62);
/* 119 */             out.print(request.getAttribute("displayname"));
/* 120 */             out.write("</a> &gt; <span class=\"bcactive\">");
/* 121 */             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 122 */             out.write("</span>\n      </td>\n\t");
/* 123 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 124 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 128 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 129 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 132 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 133 */           out.write(10);
/* 134 */           out.write(10);
/* 135 */           out.write(9);
/*     */           
/* 137 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 138 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 139 */           _jspx_th_c_005fif_005f2.setParent(null);
/*     */           
/* 141 */           _jspx_th_c_005fif_005f2.setTest("${param.method=='showoperations'}");
/* 142 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 143 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 145 */               out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 146 */               out.print(BreadcrumbUtil.getMonitorsPage());
/* 147 */               out.write(" &gt; ");
/* 148 */               out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 149 */               out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 150 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*     */                 return;
/* 152 */               out.write(34);
/* 153 */               out.write(62);
/* 154 */               out.print(request.getAttribute("displayname"));
/* 155 */               out.write("</a> &gt; <span class=\"bcactive\">");
/* 156 */               out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 157 */               out.write("</span>\n      </td>\n\t");
/* 158 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 159 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 163 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 164 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*     */           }
/*     */           else {
/* 167 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 168 */             out.write(10);
/* 169 */             out.write(9);
/*     */             
/* 171 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 172 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 173 */             _jspx_th_c_005fif_005f3.setParent(null);
/*     */             
/* 175 */             _jspx_th_c_005fif_005f3.setTest("${param.method=='getSOAPInfo'}");
/* 176 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 177 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */               for (;;) {
/* 179 */                 out.write("\n\t  <td class=\"bcsign\" height=\"22\">\n\t   \t");
/* 180 */                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 181 */                 out.write(" &gt; ");
/* 182 */                 out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 183 */                 out.write(" &gt; <a class=\"bcinactive\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 184 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*     */                   return;
/* 186 */                 out.write(34);
/* 187 */                 out.write(62);
/* 188 */                 out.print(request.getAttribute("displayname"));
/* 189 */                 out.write(" </a> &gt; <span class=\"bcactive\">");
/* 190 */                 out.print(request.getAttribute("operationName"));
/* 191 */                 out.write("</span>\n\t");
/* 192 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 193 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 197 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 198 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */             }
/*     */             else {
/* 201 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 202 */               out.write("\n</tr>\n\t<tr>\n\t\t<td  class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"2px\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\t\n");
/*     */             }
/* 204 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 205 */         out = _jspx_out;
/* 206 */         if ((out != null) && (out.getBufferSize() != 0))
/* 207 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 208 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 211 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 217 */     PageContext pageContext = _jspx_page_context;
/* 218 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 220 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 221 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 222 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 224 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 225 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 226 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 227 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 228 */       return true;
/*     */     }
/* 230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 236 */     PageContext pageContext = _jspx_page_context;
/* 237 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 239 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 240 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 241 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 243 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 244 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 245 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 246 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 247 */       return true;
/*     */     }
/* 249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 250 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 255 */     PageContext pageContext = _jspx_page_context;
/* 256 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 258 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 259 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 260 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*     */     
/* 262 */     _jspx_th_c_005fout_005f2.setValue("${param.resId}");
/* 263 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 264 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 265 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 266 */       return true;
/*     */     }
/* 268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 269 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wsmbreadcrumb_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */