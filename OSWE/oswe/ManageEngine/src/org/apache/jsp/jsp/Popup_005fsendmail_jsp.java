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
/*     */ public final class Popup_005fsendmail_jsp extends HttpJspBase implements JspSourceDependent
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
/*  68 */       out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  69 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  71 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n<html>\n<head><title>");
/*  72 */       out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"));
/*  73 */       out.write("</title></head>\n<body >\n");
/*  74 */       String fromadd = request.getParameter("from");
/*  75 */       String toadd = request.getParameter("to");
/*  76 */       String status = (String)request.getAttribute("result");
/*     */       
/*  78 */       String success = FormatUtil.getString("am.webclient.popup.sendmail.sucess.message");
/*  79 */       String failure = FormatUtil.getString("am.webclient.popup.sendmail.failure.message");
/*     */       
/*  81 */       out.write("\n  \n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n          ");
/*  82 */       if (status == null)
/*     */       {
/*  84 */         out.write("\n\t    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t      <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n        <td width=\"95%\" class=\"bodytext\"  >");
/*  85 */         out.print(success);
/*  86 */         out.write(" \n         </td>\n         ");
/*     */       }
/*     */       else
/*     */       {
/*  90 */         out.write("\n                  <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t      <img src=\"/images/icon_message_failure.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n        <td width=\"95%\" class=\"bodytext\"  >");
/*  91 */         out.print(status);
/*  92 */         out.write("\n                  ");
/*     */       }
/*  94 */       out.write("\n\t</tr>\n\t</table>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/*  96 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  97 */         out = _jspx_out;
/*  98 */         if ((out != null) && (out.getBufferSize() != 0))
/*  99 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 100 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 103 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 109 */     PageContext pageContext = _jspx_page_context;
/* 110 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 112 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 113 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 114 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 116 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 118 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 119 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 120 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 122 */       return true;
/*     */     }
/* 124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 125 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fsendmail_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */