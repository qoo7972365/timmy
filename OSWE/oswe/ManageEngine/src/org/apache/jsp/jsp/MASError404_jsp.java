/*     */ package org.apache.jsp.jsp;
/*     */ 
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
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class MASError404_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
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
/*  34 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  40 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*  48 */     Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
/*  49 */     if (exception != null) {
/*  50 */       response.setStatus(500);
/*     */     }
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html;charset=UTF-8");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("\n\n\n\n\n\n");
/*     */       
/*  73 */       String parenturl = request.getHeader("Referer");
/*     */       
/*     */ 
/*  76 */       out.write("\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n<div class=\"error-page\">\n\t<p>\n\t\t<img border=\"0\" src=\"/images/am_logo.png\">\n   \t</p>\n\t<p class=\"error-txt\">");
/*  77 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  79 */       out.write("\n\t<a href='");
/*  80 */       out.print(parenturl);
/*  81 */       out.write(39);
/*  82 */       out.write(62);
/*  83 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("am.webclient.managedserver.goHome"));
/*  84 */       out.write("</a></p>");
/*  85 */       out.write("\n</div>\n</body>");
/*     */     } catch (Throwable t) {
/*  87 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  88 */         out = _jspx_out;
/*  89 */         if ((out != null) && (out.getBufferSize() != 0))
/*  90 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  91 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  94 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 100 */     PageContext pageContext = _jspx_page_context;
/* 101 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 103 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 104 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 105 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 107 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.managedserver.masDownErrorMsg");
/* 108 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 109 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 110 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 111 */       return true;
/*     */     }
/* 113 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 114 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MASError404_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */