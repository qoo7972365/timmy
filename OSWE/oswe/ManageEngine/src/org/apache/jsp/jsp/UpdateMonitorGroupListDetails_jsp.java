/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
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
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class UpdateMonitorGroupListDetails_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  28 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  33 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  43 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  46 */     JspWriter out = null;
/*  47 */     Object page = this;
/*  48 */     JspWriter _jspx_out = null;
/*  49 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  53 */       response.setContentType("text/html");
/*  54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  56 */       _jspx_page_context = pageContext;
/*  57 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  58 */       ServletConfig config = pageContext.getServletConfig();
/*  59 */       session = pageContext.getSession();
/*  60 */       out = pageContext.getOut();
/*  61 */       _jspx_out = out;
/*     */       
/*  63 */       out.write("<!--$Id$-->\n\n \n\n");
/*     */       
/*  65 */       AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/*     */       
/*     */ 
/*  68 */       String temp = request.getParameter("mo_list");
/*  69 */       int mo_list_count = Integer.parseInt(temp);
/*     */       
/*  71 */       String ME_del_query = "delete from StandaloneMonitorGroupList";
/*     */       try
/*     */       {
/*  74 */         AMConnectionPool.executeUpdateStmt(ME_del_query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  78 */         e.printStackTrace(); return;
/*     */       }
/*     */       
/*     */ 
/*  82 */       for (int i = 0; i < mo_list_count; i++)
/*     */       {
/*  84 */         String temp_chk = request.getParameter("ch_index" + i);
/*  85 */         if (temp_chk != null)
/*     */         {
/*     */ 
/*  88 */           String ME_query = "insert into StandaloneMonitorGroupList (monitor_group_ids) values (" + temp_chk + ")";
/*     */           try
/*     */           {
/*  91 */             AMConnectionPool.executeUpdateStmt(ME_query);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*  95 */             e.printStackTrace(); return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 101 */       out.write("\n\n<script>\nwindow.close();\nif (window.opener && !window.opener.closed)\n{\n\twindow.opener.location.reload();\n} \n</script>\n");
/*     */       
/*     */ 
/*     */ 
/* 105 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 107 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 108 */         out = _jspx_out;
/* 109 */         if ((out != null) && (out.getBufferSize() != 0))
/* 110 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 111 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 114 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UpdateMonitorGroupListDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */