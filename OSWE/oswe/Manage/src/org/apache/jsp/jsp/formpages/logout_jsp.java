/*     */ package org.apache.jsp.jsp.formpages;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.UserSessionHandler;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class logout_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("<!DOCTYPE html>\n");
/*  68 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  69 */       out.write("\n\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  72 */       String param = request.getParameter("fromAdminAction");
/*     */       
/*  74 */       System.out.println(param);
/*     */       
/*  76 */       if ((param != null) && (param.equalsIgnoreCase("true")))
/*     */       {
/*  78 */         System.out.println("Params" + param);
/*     */       }
/*     */       else
/*     */       {
/*  82 */         System.out.println("Session are invalidated");
/*  83 */         String username = request.getRemoteUser();
/*     */         
/*  85 */         if (username != null)
/*     */         {
/*  87 */           UserSessionHandler us = UserSessionHandler.getInstance();
/*  88 */           UserSessionHandler.sessMap.remove(username);
/*     */         }
/*     */       }
/*     */       
/*  92 */       String context = (String)request.getAttribute("uri");
/*     */       
/*  94 */       if (context == null) context = "";
/*  95 */       session.invalidate();
/*     */       
/*  97 */       if ((context.equals("/jsp/Alarm.jsp")) || (context.equals("/jsp/MibBrowser.jsp")) || (context.equals("/jsp/RCA.jsp")))
/*     */       {
/*     */ 
/* 100 */         out.write("\n<script>\n\twindow.opener.logout();\n\twindow.close();\t\n</script>\n");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 106 */         if (request.isUserInRole("DEMO")) {
/* 107 */           Cookie[] cookies = request.getCookies();
/* 108 */           Cookie changeCookies = null;
/* 109 */           if (cookies != null) {
/* 110 */             for (int i = 0; i < cookies.length; i++) {
/* 111 */               changeCookies = cookies[i];
/* 112 */               changeCookies.setPath("/");
/* 113 */               changeCookies.setMaxAge(0);
/* 114 */               response.addCookie(changeCookies);
/*     */             }
/*     */           }
/*     */         }
/* 118 */         String showPreLogin = "false";
/* 119 */         if ((!Constants.sqlManager) && (!Constants.isIt360) && (!com.adventnet.appmanager.util.OEMUtil.isOEM()) && (!com.adventnet.appmanager.util.DBUtil.getGlobalConfigValue("doNotGoToLogoutPage").equalsIgnoreCase("true")))
/*     */         {
/* 121 */           showPreLogin = "true";
/*     */         }
/* 123 */         String url = "/webclient/common/jsp/home.jsp?showPreLogin=" + showPreLogin;
/* 124 */         if (AMAutomaticPortChanger.isSsoEnabled()) {
/* 125 */           url = "/webclient/common/jsp/home.jsp";
/*     */         }
/* 127 */         String mobile = (String)request.getSession().getAttribute("mobile");
/* 128 */         if ((mobile != null) && (mobile.equals("true")))
/*     */         {
/* 130 */           url = url + "isMobile=true";
/*     */         }
/* 132 */         if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER"))) {
/* 133 */           url = "https://www.manageengine.com/products/applications_manager/request-demo.html";
/*     */         }
/* 135 */         if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/* 136 */           String currenthostport = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 141 */           String caslogouturl = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort();
/* 142 */           String user = request.getRemoteUser();
/* 143 */           caslogouturl = caslogouturl + "/cas/logout?service=" + currenthostport + url;
/* 144 */           if (user != null) {
/* 145 */             caslogouturl = caslogouturl + "&user=" + user;
/*     */           }
/* 147 */           response.sendRedirect(caslogouturl);
/*     */         } else {
/* 149 */           response.sendRedirect(url);
/*     */         }
/*     */         
/*     */         return;
/*     */       }
/*     */       
/* 155 */       out.write(10);
/* 156 */       out.write(10);
/* 157 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 159 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 160 */         out = _jspx_out;
/* 161 */         if ((out != null) && (out.getBufferSize() != 0))
/* 162 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 163 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 166 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\logout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */