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
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class jmxnotification_005fnombeans_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
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
/*  30 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  34 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("\n\n\n\n\n\n<link href=\"/images/");
/*  68 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  70 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp;\n        ");
/*  71 */       out.print(FormatUtil.getString("am.webclient.jmxnotification.createnewjmx"));
/*  72 */       out.write("</td>\n    </tr>\n  </table>\n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"99%\" class=\"lrtbdarkborder\">\n  <tr>\n  <td colspan=\"3\" class=\"whitegrayborder\">&nbsp; ");
/*  73 */       out.print(FormatUtil.getString("am.webclient.jmxnotification.nojmxnotification"));
/*  74 */       out.write(". <a href=\"javascript:history.back();\"  class=\"staticlinks\">");
/*  75 */       out.print(FormatUtil.getString("am.webclient.newaction.mbeanback"));
/*  76 */       out.write("</a></td> \n  </tr>\n </table>\n");
/*     */     } catch (Throwable t) {
/*  78 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  79 */         out = _jspx_out;
/*  80 */         if ((out != null) && (out.getBufferSize() != 0))
/*  81 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  82 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  85 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/*  91 */     PageContext pageContext = _jspx_page_context;
/*  92 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/*  94 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  95 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  96 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/*  98 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 100 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 101 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 102 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 104 */       return true;
/*     */     }
/* 106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\jmxnotification_005fnombeans_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */