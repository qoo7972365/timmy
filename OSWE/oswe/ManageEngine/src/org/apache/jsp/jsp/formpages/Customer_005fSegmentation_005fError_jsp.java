/*     */ package org.apache.jsp.jsp.formpages;
/*     */ 
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.Principal;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class Customer_005fSegmentation_005fError_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     HttpSession session = null;
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
/*  68 */       out.write("<!DOCTYPE html>\n");
/*  69 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  70 */       out.write("\n\n\n\n\n\n");
/*  71 */       out.write(10);
/*     */       
/*  73 */       String faviconHref = "/favicon.ico";
/*  74 */       String productName = OEMUtil.getOEMString("product.name");
/*     */       
/*  76 */       productName = OEMUtil.getOEMString("rebrand.product.name");
/*     */       
/*     */ 
/*  79 */       faviconHref = "/favicon.ico?" + System.currentTimeMillis();
/*  80 */       String rebrandedId = "-1";
/*  81 */       if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*     */       {
/*  83 */         Object tmpObj1 = request.getSession().getAttribute("rebrandId");
/*  84 */         if (tmpObj1 != null)
/*     */         {
/*  86 */           rebrandedId = tmpObj1.toString();
/*     */         }
/*     */         else {
/*     */           try
/*     */           {
/*  91 */             String usrName = request.getUserPrincipal().getName();
/*     */             
/*     */ 
/*     */ 
/*  95 */             String className1 = "com.adventnet.console.common.util.ConsoleRemoteUtil";
/*  96 */             String methodName1 = "getUserInfo";
/*  97 */             Class conClass1 = Class.forName(className1);
/*  98 */             String className2 = "com.adventnet.console.UserInfo";
/*  99 */             String methodName2 = "getGroupName";
/* 100 */             Class conClass2 = Class.forName(className2);
/* 101 */             Class[] parameters = { String.class };
/* 102 */             Method myMethod = conClass1.getMethod(methodName1, parameters);
/* 103 */             Object[] arguments = { usrName };
/* 104 */             Object returnFromMethod1 = myMethod.invoke(null, arguments);
/* 105 */             Method it360Method = conClass2.getMethod(methodName2, new Class[0]);
/* 106 */             Object returnFromMethod2 = it360Method.invoke(returnFromMethod1, null);
/* 107 */             String rName = (String)returnFromMethod2;
/* 108 */             rebrandedId = CustomerManagementAPI.getRebrandIdForUser(usrName, rName);
/*     */           } catch (Exception e) {}
/*     */         }
/*     */       }
/* 112 */       if (!rebrandedId.equals("-1"))
/*     */       {
/* 114 */         faviconHref = "/custom/customimages/Custom_FaviconLogo_" + rebrandedId + ".ico?" + System.currentTimeMillis();
/*     */       }
/*     */       
/*     */ 
/* 118 */       out.write(10);
/* 119 */       out.write("\n<html>\n<head>\n<title>");
/* 120 */       out.print(productName);
/* 121 */       out.write("</title>\n\n<link REL=\"SHORTCUT ICON\" HREF=\"");
/* 122 */       out.print(faviconHref);
/* 123 */       out.write("\">\n\n\t<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n\n<table width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" align=\"center\">\n<tr><td>\n<img src=\"/images/am_logo.png\" alt=\"Icon\" >\n</td></tr>\n</table>\n<br>\n\n <table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\" align=\"center\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"/images/icon_monitor_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\">\n                ");
/* 124 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("Database Error Occurred while processing the request...."));
/* 125 */       out.write("\n                <br><br>\n                \n                </td>\n              </tr>\n\n            </table>\n\n\n</body>\n</html>\n\n  \n");
/*     */     } catch (Throwable t) {
/* 127 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 128 */         out = _jspx_out;
/* 129 */         if ((out != null) && (out.getBufferSize() != 0))
/* 130 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 131 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 134 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\Customer_005fSegmentation_005fError_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */