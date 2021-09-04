/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class ThresholdCreationForwarder_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  44 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
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
/*  72 */       out.write("<!--$Id$-->\n\n<script>\n");
/*  73 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  75 */       out.write(10);
/*     */       
/*  77 */       String wiz = request.getParameter("wiz");
/*  78 */       if (wiz != null)
/*     */       {
/*  80 */         wiz = "&wiz=true";
/*     */       }
/*     */       else
/*     */       {
/*  84 */         wiz = "";
/*     */       }
/*  86 */       String haid = request.getParameter("haid");
/*  87 */       String resourceid = request.getParameter("resourceid");
/*  88 */       String redirectTo = request.getParameter("redirectTo");
/*  89 */       if ((redirectTo == null) || (redirectTo.equals("null")))
/*     */       {
/*  91 */         if ((haid != null) && (!haid.equals("null")))
/*     */         {
/*     */ 
/*  94 */           out.write("\n\t\twindow.parent.opener.fnReload(\"/showActionProfiles.do?method=getHAProfiles&haid=");
/*  95 */           out.print(haid);
/*  96 */           if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */             return;
/*  98 */           out.print(wiz);
/*  99 */           out.write("\");\n\t\twindow.parent.close();\n\t\t");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 105 */           out.write("\n\t\twindow.parent.opener.fnReload(\"/showActionProfiles.do?method=getResourceProfiles&resourceid=");
/* 106 */           out.print(resourceid);
/* 107 */           out.write("&monitor=true");
/* 108 */           if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */             return;
/* 110 */           out.write("\");\n\t\twindow.parent.close();\n\t\t");
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 117 */         out.write("\n\twindow.parent.opener.fnReload(\"");
/* 118 */         out.print(redirectTo);
/* 119 */         out.write("\");\n\twindow.parent.close();\n\t");
/*     */       }
/*     */       
/*     */ 
/* 123 */       out.write("\n</script>\n");
/*     */     } catch (Throwable t) {
/* 125 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 126 */         out = _jspx_out;
/* 127 */         if ((out != null) && (out.getBufferSize() != 0))
/* 128 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 129 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 132 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 138 */     PageContext pageContext = _jspx_page_context;
/* 139 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 141 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 142 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 143 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 145 */     _jspx_th_c_005fif_005f0.setTest("${param.admin=='true' }");
/* 146 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 147 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 149 */         out.write(10);
/* 150 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 151 */           return true;
/* 152 */         out.write(10);
/* 153 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 154 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 158 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 159 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 160 */       return true;
/*     */     }
/* 162 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 168 */     PageContext pageContext = _jspx_page_context;
/* 169 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 171 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 172 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 173 */     _jspx_th_c_005fset_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 175 */     _jspx_th_c_005fset_005f0.setVar("paramadmin");
/* 176 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 177 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 178 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 179 */         out = _jspx_page_context.pushBody();
/* 180 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 181 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 184 */         out.write("\n&admin=true\n");
/* 185 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 186 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 189 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 190 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 193 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 194 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 195 */       return true;
/*     */     }
/* 197 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 203 */     PageContext pageContext = _jspx_page_context;
/* 204 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 206 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 207 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 208 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 210 */     _jspx_th_c_005fout_005f0.setValue("${paramadmin}");
/*     */     
/* 212 */     _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 213 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 214 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 215 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 216 */       return true;
/*     */     }
/* 218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 219 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 224 */     PageContext pageContext = _jspx_page_context;
/* 225 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 227 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 228 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 229 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 231 */     _jspx_th_c_005fout_005f1.setValue("${paramadmin}");
/*     */     
/* 233 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 234 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 235 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 237 */       return true;
/*     */     }
/* 239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 240 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ThresholdCreationForwarder_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */