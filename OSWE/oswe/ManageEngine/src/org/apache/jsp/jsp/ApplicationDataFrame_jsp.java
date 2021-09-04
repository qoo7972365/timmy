/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class ApplicationDataFrame_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  27 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  31 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  32 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  42 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  45 */     JspWriter out = null;
/*  46 */     Object page = this;
/*  47 */     JspWriter _jspx_out = null;
/*  48 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  52 */       response.setContentType("text/html");
/*  53 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  55 */       _jspx_page_context = pageContext;
/*  56 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  57 */       ServletConfig config = pageContext.getServletConfig();
/*  58 */       session = pageContext.getSession();
/*  59 */       out = pageContext.getOut();
/*  60 */       _jspx_out = out;
/*     */       
/*     */ 
/*  63 */       String webappname = request.getParameter("webappname");
/*  64 */       String resourceName = request.getParameter("resourceName");
/*  65 */       String type = request.getParameter("type");
/*  66 */       if (type == null) {
/*  67 */         type = "WEBAPP";
/*     */       }
/*  69 */       out.write(10);
/*  70 */       out.write(10);
/*     */       
/*  72 */       if (type.equals("WEBAPP"))
/*     */       {
/*     */ 
/*  75 */         out.write("\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"1%\" bgcolor=\"EEEEEE\"><img src=\"../images/spacer.gif\" width=\"16\" height=\"16\"></td>\n    <td width=\"14%\"   class=\"selectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/*  76 */         out.print(request.getParameter("resourceName"));
/*  77 */         out.write("&type=WEBAPP&webappname=");
/*  78 */         out.print(webappname);
/*  79 */         out.write("\" class=\"selectedmenu\">Web Applications</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"10%\" class=\"unselectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/*  80 */         out.print(request.getParameter("resourceName"));
/*  81 */         out.write("&type=EJB&webappname=");
/*  82 */         out.print(webappname);
/*  83 */         out.write("\" class=\"unselectedmenu\">EJB Details </a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"18%\"  class=\"unselectedmenu\"><a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/*  84 */         out.print(request.getParameter("resourceName"));
/*  85 */         out.write("&type=JDBC&webappname=");
/*  86 */         out.print(webappname);
/*  87 */         out.write("\" class=\"unselectedmenu\">DataBase Connections</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"57%\" bgcolor=\"#EEEEEE\">&nbsp;</td>\n</tr>\n</table>\n");
/*     */ 
/*     */       }
/*  90 */       else if (type.equals("EJB"))
/*     */       {
/*     */ 
/*  93 */         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"1%\" bgcolor=\"EEEEEE\"><img src=\"../images/spacer.gif\" width=\"16\" height=\"16\"></td>\n     <td width=\"14%\"   class=\"unselectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/*  94 */         out.print(request.getParameter("resourceName"));
/*  95 */         out.write("&type=WEBAPP&webappname=");
/*  96 */         out.print(webappname);
/*  97 */         out.write("\" class=\"unselectedmenu\">Web Applications</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"10%\" class=\"selectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/*  98 */         out.print(request.getParameter("resourceName"));
/*  99 */         out.write("&type=EJB&webappname=");
/* 100 */         out.print(webappname);
/* 101 */         out.write("\" class=\"selectedmenu\">EJB Details </a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"18%\"  class=\"unselectedmenu\"><a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/* 102 */         out.print(request.getParameter("resourceName"));
/* 103 */         out.write("&type=JDBC&webappname=");
/* 104 */         out.print(webappname);
/* 105 */         out.write("\" class=\"unselectedmenu\">DataBase Connections</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"57%\" bgcolor=\"#EEEEEE\">&nbsp;</td>\n</tr>\n</table>\n");
/*     */ 
/*     */       }
/* 108 */       else if (type.equals("JDBC"))
/*     */       {
/*     */ 
/* 111 */         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"1%\" bgcolor=\"EEEEEE\"><img src=\"../images/spacer.gif\" width=\"16\" height=\"16\"></td>\n\t<td width=\"14%\"   class=\"unselectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/* 112 */         out.print(request.getParameter("resourceName"));
/* 113 */         out.write("&type=WEBAPP&webappname=");
/* 114 */         out.print(webappname);
/* 115 */         out.write("\" class=\"unselectedmenu\">Web Applications</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"10%\" class=\"unselectedmenu\"> <a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/* 116 */         out.print(request.getParameter("resourceName"));
/* 117 */         out.write("&type=EJB&webappname=");
/* 118 */         out.print(webappname);
/* 119 */         out.write("\" class=\"unselectedmenu\">EJB Details </a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"18%\"  class=\"selectedmenu\"><a href=\"../jsp/ShowApplicationData.jsp?resourceName=");
/* 120 */         out.print(request.getParameter("resourceName"));
/* 121 */         out.write("&type=JDBC&webappname=");
/* 122 */         out.print(webappname);
/* 123 */         out.write("\" class=\"selectedmenu\">DataBase Connections</a></td>\n    <td width=\"0%\"><img src=\"../images/spacer.gif\" width=\"2\" height=\"10\"></td>\n    <td width=\"57%\" bgcolor=\"#EEEEEE\">&nbsp;</td>\n</tr>\n</table>\n");
/*     */       }
/*     */       
/*     */ 
/* 127 */       out.write(10);
/* 128 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 130 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 131 */         out = _jspx_out;
/* 132 */         if ((out != null) && (out.getBufferSize() != 0))
/* 133 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 134 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 137 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ApplicationDataFrame_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */