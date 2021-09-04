/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public final class ConfigureResource_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  29 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  34 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  44 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  47 */     JspWriter out = null;
/*  48 */     Object page = this;
/*  49 */     JspWriter _jspx_out = null;
/*  50 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  54 */       response.setContentType("text/html");
/*  55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  57 */       _jspx_page_context = pageContext;
/*  58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write(10);
/*  65 */       out.write(10);
/*     */       
/*  67 */       Properties props = new Properties();
/*  68 */       props.setProperty("resourceName", request.getParameter("resourceName"));
/*  69 */       props.setProperty("resourceType", request.getParameter("resourceType"));
/*  70 */       props.setProperty("targetName", request.getParameter("targetName"));
/*  71 */       props.setProperty("port", request.getParameter("port"));
/*  72 */       props.setProperty("clientType", request.getParameter("clientType"));
/*  73 */       props.setProperty("userName", request.getParameter("userName"));
/*  74 */       props.setProperty("passWord", request.getParameter("passWord"));
/*  75 */       props.setProperty("pollInterval", request.getParameter("pollInterval"));
/*  76 */       props.setProperty("componentName", request.getParameter("componentName"));
/*  77 */       AMDataCollectionHandler handler = AMDataCollectionHandler.getInstance();
/*  78 */       Properties result = handler.configureResource(props);
/*  79 */       if (result.getProperty("dcsuccessful").equals("true"))
/*     */       {
/*     */ 
/*  82 */         out.write(10);
/*  83 */         out.write(9);
/*     */         
/*  85 */         _jspx_page_context.forward("../jsp/ShowApplicationData.jsp");
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  95 */         out.write("\n<html>\n<body>\n\t<center><p><h2> Problem while configuring dataCollection for Resoure ");
/*  96 */         out.print(request.getParameter("resourceName"));
/*  97 */         out.write(". Error Message : ");
/*  98 */         out.print(result.getProperty("message"));
/*  99 */         out.write("</p></center>\n");
/*     */         
/*     */ 
/*     */ 
/* 103 */         out.write("\n</body> \n</html>\n");
/*     */       }
/* 105 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
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


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ConfigureResource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */