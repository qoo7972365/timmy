/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HAAlert_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   private String findReplace(String str, String find, String replace)
/*     */   {
/*  28 */     String des = new String();
/*  29 */     while (str.indexOf(find) != -1) {
/*  30 */       des = des + str.substring(0, str.indexOf(find));
/*  31 */       des = des + replace;
/*  32 */       str = str.substring(str.indexOf(find) + find.length());
/*     */     }
/*  34 */     des = des + str;
/*  35 */     return des;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  40 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  49 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  53 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  54 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  64 */     HttpSession session = null;
/*     */     
/*     */ 
/*  67 */     JspWriter out = null;
/*  68 */     Object page = this;
/*  69 */     JspWriter _jspx_out = null;
/*  70 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  74 */       response.setContentType("text/html");
/*  75 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  77 */       _jspx_page_context = pageContext;
/*  78 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  79 */       ServletConfig config = pageContext.getServletConfig();
/*  80 */       session = pageContext.getSession();
/*  81 */       out = pageContext.getOut();
/*  82 */       _jspx_out = out;
/*     */       
/*  84 */       out.write("<!-- $Id$-->\n\n\n\n");
/*     */       
/*  86 */       String haName = request.getParameter("appName");
/*  87 */       int haid = -1;
/*  88 */       String url = "/applications.do";
/*     */       try
/*     */       {
/*  91 */         String query = "select RESOURCEID from AM_ManagedObject where TYPE='HAI' and RESOURCENAME='" + findReplace(haName, "'", "\\'") + "'";
/*     */         
/*  93 */         System.out.println("the query in HAAlert:" + query);
/*  94 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*  95 */         ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*  96 */         if (set.next())
/*     */         {
/*  98 */           haid = set.getInt("RESOURCEID");
/*  99 */           url = "/fault/AMAlarmView.do?displayName=Alerts&haid=" + haid;
/*     */         }
/* 101 */         set.close();
/*     */       }
/*     */       catch (Throwable ex) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */       out.write("\n\n<script>\n     location.href=\"");
/* 112 */       out.print(url);
/* 113 */       out.write("\";\n</script>   \n          \n\n");
/* 114 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 116 */       if (!(t instanceof SkipPageException)) {
/* 117 */         out = _jspx_out;
/* 118 */         if ((out != null) && (out.getBufferSize() != 0))
/* 119 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 120 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 123 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HAAlert_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */