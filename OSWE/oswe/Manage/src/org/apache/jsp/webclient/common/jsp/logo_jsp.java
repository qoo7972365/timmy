/*     */ package org.apache.jsp.webclient.common.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class logo_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  24 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  25 */   static { _jspx_dependants.put("/webclient/common/jspf/commonIncludes.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  51 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("<!-- $Id: logo.jsp,v 1.5 2003/11/08 09:03:52 bharathb Exp $ -->\n\n\n");
/*  72 */       out.write("\n\n</head>\n\n<body class=\"treeBg\">\n<table width=\"100%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td height=\"50\" valign=\"top\" class=\"tabsBg\"><a href=\"http://www.adventnet.com\" target=\"_blank\"><img src=\"/webclient/common/images/");
/*  73 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  75 */       out.write("/adventnet/logo_in.gif\" alt=\"AdventNet NMS 4\" border=\"0\" align=\"middle\" title=\"AdventNet NMS 4\"></a>&nbsp;</td>\n  </tr>\n</table>\n");
/*     */     } catch (Throwable t) {
/*  77 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  78 */         out = _jspx_out;
/*  79 */         if ((out != null) && (out.getBufferSize() != 0))
/*  80 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  81 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  84 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/*  90 */     PageContext pageContext = _jspx_page_context;
/*  91 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/*  93 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  94 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  95 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/*  97 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*  98 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  99 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 100 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 101 */       return true;
/*     */     }
/* 103 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\logo_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */