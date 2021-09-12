/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.AMServerStartup;
/*     */ import com.adventnet.appmanager.util.AppManagerPing;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
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
/*     */ public final class HostNameDiscovery_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  25 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  49 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  52 */     JspWriter out = null;
/*  53 */     Object page = this;
/*  54 */     JspWriter _jspx_out = null;
/*  55 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  59 */       response.setContentType("text/html; charset=UTF-8");
/*  60 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  62 */       _jspx_page_context = pageContext;
/*  63 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  64 */       ServletConfig config = pageContext.getServletConfig();
/*  65 */       session = pageContext.getSession();
/*  66 */       out = pageContext.getOut();
/*  67 */       _jspx_out = out;
/*     */       
/*  69 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  71 */       String givenHost = request.getParameter("hostName");
/*  72 */       StringTokenizer tokenizehosts = new StringTokenizer(givenHost, ",");
/*  73 */       ArrayList list = new ArrayList();
/*  74 */       ArrayList list1 = new ArrayList();
/*  75 */       String flag = null;
/*  76 */       String hostInvalid = FormatUtil.getString("am.webclient.hostdiscovery.invalidhost");
/*  77 */       String hostDown = FormatUtil.getString("am.webclient.hostdiscovery.systemdown");
/*  78 */       while (tokenizehosts.hasMoreTokens())
/*     */       {
/*  80 */         String hostIp = tokenizehosts.nextToken().trim();
/*  81 */         String givenhost = hostIp;
/*     */         try
/*     */         {
/*  84 */           if (hostIp.equals("localhost"))
/*     */           {
/*  86 */             hostIp = AMServerStartup.ipaddress;
/*     */           }
/*  88 */           hostIp = InetAddress.getByName(hostIp).getHostAddress();
/*  89 */           if (InetAddress.getByName(hostIp).isLoopbackAddress())
/*     */           {
/*  91 */             hostIp = AMServerStartup.ipaddress;
/*     */           }
/*  93 */           new AppManagerPing();Properties result = AppManagerPing.ping(hostIp);
/*  94 */           String setFlag = result.getProperty("result");
/*  95 */           if (!setFlag.equals("true"))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */             list.add(givenhost);
/*     */             
/* 103 */             list1.add(hostDown);
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 107 */           list.add(givenhost);
/*     */           
/* 109 */           list1.add(hostInvalid);
/*     */         }
/*     */       }
/*     */       
/* 113 */       String text = " ";
/* 114 */       if (list.size() > 0)
/*     */       {
/* 116 */         int listSize = list.size();
/* 117 */         for (int i = 0; i < listSize; i++)
/*     */         {
/* 119 */           text = text + list.get(i) + " : " + list1.get(i) + "<br>";
/*     */         }
/*     */       }
/*     */       
/* 123 */       out.print(text);
/*     */       
/* 125 */       out.write("\n\n\n\n\n\n\n\n\n\n");
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


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostNameDiscovery_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */