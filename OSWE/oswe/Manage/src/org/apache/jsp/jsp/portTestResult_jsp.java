/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.appmanager.util.DiagnosticsUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspApplicationContext;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ import org.apache.tomcat.InstanceManager;
/*    */ 
/*    */ public final class portTestResult_jsp extends HttpJspBase implements JspSourceDependent
/*    */ {
/* 21 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 30 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 34 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 35 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
/*    */   {
/* 45 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 48 */     JspWriter out = null;
/* 49 */     Object page = this;
/* 50 */     JspWriter _jspx_out = null;
/* 51 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 55 */       response.setContentType("text/html; charset=UTF-8");
/* 56 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 58 */       _jspx_page_context = pageContext;
/* 59 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 60 */       ServletConfig config = pageContext.getServletConfig();
/* 61 */       session = pageContext.getSession();
/* 62 */       out = pageContext.getOut();
/* 63 */       _jspx_out = out;
/*    */       
/* 65 */       out.write("\n\n\n\n\n\n\n\n");
/*    */       
/* 67 */       String hostName = request.getParameter("hostName");
/* 68 */       String port = request.getParameter("portNumber");
/* 69 */       new DiagnosticsUtil();Properties output1 = DiagnosticsUtil.portTest(hostName, Integer.parseInt(port));
/* 70 */       String portTestResult = output1.getProperty("result");
/* 71 */       String errorMessage = "";
/* 72 */       request.setAttribute("portTestResult", portTestResult);
/* 73 */       if (output1.getProperty("errorMessage") != null)
/*    */       {
/* 75 */         errorMessage = output1.getProperty("errorMessage");
/*    */       }
/*    */       else
/*    */       {
/* 79 */         errorMessage = "";
/*    */       }
/* 81 */       String text = "";
/* 82 */       text = errorMessage;
/* 83 */       out.print(text);
/*    */       
/* 85 */       out.write(10);
/*    */     } catch (Throwable t) {
/* 87 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 88 */         out = _jspx_out;
/* 89 */         if ((out != null) && (out.getBufferSize() != 0))
/* 90 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 91 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 94 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\portTestResult_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */