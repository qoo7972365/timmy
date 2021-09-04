/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class SitemapLeftLinks_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  51 */     JspWriter out = null;
/*  52 */     Object page = this;
/*  53 */     JspWriter _jspx_out = null;
/*  54 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  58 */       response.setContentType("text/html;charset=UTF-8");
/*  59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  61 */       _jspx_page_context = pageContext;
/*  62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  63 */       ServletConfig config = pageContext.getServletConfig();
/*  64 */       session = pageContext.getSession();
/*  65 */       out = pageContext.getOut();
/*  66 */       _jspx_out = out;
/*     */       
/*  68 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  71 */       String resourceName = request.getParameter("type");
/*  72 */       String appname = request.getParameter("name");
/*  73 */       String haid = request.getParameter("haid");
/*     */       
/*     */ 
/*  76 */       out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/*  77 */       out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/*  78 */       out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/*  79 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  81 */       out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n\t  <tr> \n\t  \n    <td colspan=\"2\"  class=\"quicknote\"> ");
/*  82 */       out.print(FormatUtil.getString("am.webclient.siteleftlink.quicknote.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/*  83 */       out.write("</td>\n  </tr>\n</table>\n");
/*     */     } catch (Throwable t) {
/*  85 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  86 */         out = _jspx_out;
/*  87 */         if ((out != null) && (out.getBufferSize() != 0))
/*  88 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  89 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  92 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/*  98 */     PageContext pageContext = _jspx_page_context;
/*  99 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 101 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 102 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 103 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 105 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 107 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 108 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 109 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 110 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 111 */       return true;
/*     */     }
/* 113 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 114 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SitemapLeftLinks_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */