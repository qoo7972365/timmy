/*    */ package org.apache.jsp.webclient.common.jsp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ import org.apache.jasper.runtime.TagHandlerPool;
/*    */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*    */ 
/*    */ public final class ErrorPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*    */ {
/* 18 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 29 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 33 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 34 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 35 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */   public void _jspDestroy() {
/* 39 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
/*    */   {
/* 46 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 49 */     JspWriter out = null;
/* 50 */     Object page = this;
/* 51 */     JspWriter _jspx_out = null;
/* 52 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 56 */       response.setContentType("text/html;charset=UTF-8");
/* 57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 59 */       _jspx_page_context = pageContext;
/* 60 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 61 */       ServletConfig config = pageContext.getServletConfig();
/* 62 */       session = pageContext.getSession();
/* 63 */       out = pageContext.getOut();
/* 64 */       _jspx_out = out;
/*    */       
/* 66 */       out.write("\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n\n\n\n\n<html>\n<head>\n   <title>Error page</title>\n</head>\n\n<body class=\"bgNone\">\n<table width=\"400\" border=\"0\" align=\"center\" cellpadding=\"6\" cellspacing=\"0\" class=\"botBorder\">\n  <tbody><tr> \n    <td width=\"370\"><img src=\"/webclient/common/images/trans.gif\" width=\"1\" height=\"1\"></td>\n  </tr>\n  <tr> \n    <td><table width=\"388\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"NobotBorder\">\n        <tbody><tr> \n          <td width=\"53\" align=\"left\" valign=\"top\"><img src=\"/webclient/common/images/error_icon.gif\" width=\"43\" height=\"43\"></td>\n          <td width=\"335\" align=\"left\" valign=\"middle\"><span class=\"boldText\">Description</span><br> \n            <span class=\"text\">\n                                ");
/* 67 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*    */         return;
/* 69 */       out.write("\n            </span> <br> <br> <a href=\"javascript:history.go(-1)\" class=\"errorText\">Click \n            Here</a><span class=\"boldText\"> to go back</span> </td>\n        </tr>\n      </tbody></table></td>\n  </tr>\n</tbody></table>\n</body>\n</html>\n");
/*    */     } catch (Throwable t) {
/* 71 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 72 */         out = _jspx_out;
/* 73 */         if ((out != null) && (out.getBufferSize() != 0))
/* 74 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 75 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 78 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*    */   {
/* 84 */     PageContext pageContext = _jspx_page_context;
/* 85 */     JspWriter out = _jspx_page_context.getOut();
/*    */     
/* 87 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 88 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 89 */     _jspx_th_c_005fout_005f0.setParent(null);
/*    */     
/* 91 */     _jspx_th_c_005fout_005f0.setValue("${ActionException}");
/* 92 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 93 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 94 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 95 */       return true;
/*    */     }
/* 97 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\ErrorPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */