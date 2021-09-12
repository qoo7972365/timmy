/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class GMap_005faddlocation_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  28 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  42 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  52 */     HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/*  73 */       ManagedApplication mo = null;
/*  74 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/*  75 */       if (mo == null) {
/*  76 */         mo = new ManagedApplication();
/*  77 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*     */       }
/*  79 */       out.write(10);
/*     */       
/*     */ 
/*     */ 
/*  83 */       System.out.println("Simply");
/*  84 */       String xcoord = request.getParameter("xcoord");
/*  85 */       String ycoord = request.getParameter("ycoord");
/*  86 */       String location = request.getParameter("location");
/*  87 */       location = URLDecoder.decode(location);
/*  88 */       String type = "1";
/*  89 */       String query = "insert into AM_GMapCountryCoord values(" + DBQueryUtil.getIncrementedID("ID", "AM_GMapCountryCoord") + ",'" + location + "',1," + xcoord + "," + ycoord + ")";
/*  90 */       System.out.println(query);
/*  91 */       com.adventnet.appmanager.db.AMConnectionPool.executeUpdateStmt(query);
/*     */       
/*     */ 
/*     */ 
/*  95 */       ArrayList rows = mo.getPropertiesList("select ID from AM_GMapCountryCoord where NAME='" + location + "' and xcoord=" + xcoord);
/*     */       
/*  97 */       Properties prop = (Properties)rows.get(0);
/*  98 */       out.println(prop.get("ID"));
/*     */       
/*     */ 
/*     */ 
/* 102 */       out.write(10);
/* 103 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 105 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 106 */         out = _jspx_out;
/* 107 */         if ((out != null) && (out.getBufferSize() != 0))
/* 108 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 109 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 112 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GMap_005faddlocation_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */