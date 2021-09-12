/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.discovery.SystemAvailability;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.discovery.server.ResourceTypeIfc;
/*     */ import com.adventnet.nms.applnfw.discovery.server.model.DiscoveryInfo;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
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
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HostServiceDiscovery_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   private String appendSystemDiscoveryMessage(DiscoveryInfo discInfo, String message)
/*     */   {
/*  30 */     Properties p = SystemAvailability.getSystemAvailabilityStats(discInfo);
/*     */     
/*  32 */     message = message + "<br>" + FormatUtil.getString("am.webclient.managermail.rootcause.text");
/*     */     
/*  34 */     String pingresult = p.getProperty("pingresult");
/*  35 */     if (pingresult != null) {
/*  36 */       message = message + "<br>" + p.getProperty("pingresult");
/*     */     }
/*  38 */     if ((pingresult == null) && 
/*  39 */       (p.getProperty("servicelookupresult") != null)) {
/*  40 */       message = message + "<br>" + p.getProperty("servicelookupresult");
/*     */     }
/*     */     
/*  43 */     return message;
/*     */   }
/*     */   
/*  46 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  55 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  59 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  60 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  70 */     HttpSession session = null;
/*     */     
/*     */ 
/*  73 */     JspWriter out = null;
/*  74 */     Object page = this;
/*  75 */     JspWriter _jspx_out = null;
/*  76 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  80 */       response.setContentType("text/html; charset=UTF-8");
/*  81 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  83 */       _jspx_page_context = pageContext;
/*  84 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  85 */       ServletConfig config = pageContext.getServletConfig();
/*  86 */       session = pageContext.getSession();
/*  87 */       out = pageContext.getOut();
/*  88 */       _jspx_out = out;
/*     */       
/*  90 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  92 */       DiscoveryInfo discoveryInfo = null;
/*  93 */       String target = request.getParameter("hostName");
/*  94 */       StringTokenizer tokenizehosts = new StringTokenizer(target, ",");
/*  95 */       String portNo = request.getParameter("portNumber");
/*  96 */       String version = request.getParameter("version");
/*  97 */       ArrayList list = new ArrayList();
/*  98 */       ArrayList list1 = new ArrayList();
/*  99 */       ArrayList list2 = new ArrayList();
/* 100 */       String text = "";
/* 101 */       while (tokenizehosts.hasMoreTokens())
/*     */       {
/* 103 */         String hostIp = tokenizehosts.nextToken().trim();
/* 104 */         String targetAddress = hostIp;
/* 105 */         StringTokenizer tokenizePorts = new StringTokenizer(portNo, ",");
/* 106 */         while (tokenizePorts.hasMoreTokens())
/*     */         {
/* 108 */           String hostPort = tokenizePorts.nextToken();
/* 109 */           String targetPort = hostPort;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */           String serverType = request.getParameter("typeToSend");
/* 120 */           DiscoveryInfo discInfo = new DiscoveryInfo();
/* 121 */           String discClass = null;
/* 122 */           String discClass1 = null;
/* 123 */           String discClass2 = null;
/* 124 */           String resourceType = null;
/* 125 */           String resourceType1 = null;
/* 126 */           String resourceType2 = null;
/* 127 */           if (!version.equals("undefined"))
/*     */           {
/* 129 */             if (serverType.equals("JBoss:8080"))
/*     */             {
/* 131 */               resourceType = "JBOSS-server";
/* 132 */               discClass = "com.adventnet.appmanager.server.discovery.jb.JBossDiscovery";
/*     */             }
/* 134 */             else if (serverType.equals("Tomcat:8080"))
/*     */             {
/* 136 */               resourceType = "Tomcat-server";
/* 137 */               discClass = "com.adventnet.appmanager.server.tomcat.discovery.TomcatDiscovery";
/*     */             }
/* 139 */             else if (serverType.equals("WEBLOGIC:7001"))
/*     */             {
/* 141 */               resourceType = "WEBLOGIC-server";
/* 142 */               discClass = "com.adventnet.appmanager.server.discovery.wlogic.WebLogicDiscovery";
/*     */             }
/* 144 */             else if (serverType.equals("WEBSPHERE:9080"))
/*     */             {
/* 146 */               resourceType = "WEB-server";
/* 147 */               discClass = "com.adventnet.appmanager.server.discovery.WebserverDiscovery";
/*     */             }
/* 149 */             else if (serverType.equals("SAP-CCMS"))
/*     */             {
/* 151 */               resourceType = "SAP-CCMS";
/* 152 */               discInfo.setUserProperty("logonClient", request.getParameter("logonClient"));
/* 153 */               discInfo.setUserProperty("systemNumber", request.getParameter("portNumber"));
/* 154 */               discInfo.setUserProperty("language", request.getParameter("language"));
/* 155 */               discInfo.setUserProperty("username", request.getParameter("username"));
/* 156 */               discInfo.setUserProperty("password", request.getParameter("password"));
/* 157 */               discInfo.setUserProperty("host", request.getParameter("targetAddress"));
/* 158 */               if (request.getParameter("routerString") != null) {
/* 159 */                 discInfo.setUserProperty("routerString", request.getParameter("routerString"));
/*     */               }
/* 161 */               discClass = "com.adventnet.appmanager.server.sap.discovery.SAPDiscovery";
/*     */             }
/* 163 */             else if (serverType.equals("Exchange:25"))
/*     */             {
/* 165 */               resourceType = "Exchange-server";
/* 166 */               discClass = "com.adventnet.appmanager.server.exchange.discovery.ExchangeDiscovery";
/*     */             }
/* 168 */             discInfo.setUserProperty("protocolVersion", request.getParameter("version"));
/*     */           }
/* 170 */           if (serverType.equals("WTA:55555"))
/*     */           {
/* 172 */             resourceType = "WTA";
/* 173 */             discClass = "com.adventnet.appmanager.server.wta.discovery.WTADiscovery";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/* 180 */           else if (serverType.equals("DB2:50000"))
/*     */           {
/* 182 */             resourceType = "DB2-server";
/* 183 */             discClass = "com.adventnet.appmanager.server.db2.discovery.DB2Discovery";
/*     */           }
/* 185 */           else if (serverType.equals("MSSQLDB:1433"))
/*     */           {
/* 187 */             resourceType = "MSSQL-DB-server";
/* 188 */             discClass = "com.adventnet.nms.appln.mssql.discovery.server.MssqlDiscovery";
/*     */           }
/* 190 */           else if (serverType.equals("SYBASEDB:5000"))
/*     */           {
/* 192 */             resourceType = "SYBASE-DB-server";
/* 193 */             discClass = "com.adventnet.appmanager.server.sybase.discovery.SybaseDiscovery";
/*     */           }
/* 195 */           else if (serverType.equals("MYSQLDB:3306"))
/*     */           {
/* 197 */             resourceType = "MYSQL-DB-server";
/* 198 */             discClass = "com.adventnet.appmanager.server.mysql.discovery.MysqlDiscovery";
/*     */           }
/* 200 */           else if (serverType.equals("ORACLEDB:1521"))
/*     */           {
/* 202 */             resourceType = "ORACLE-DB-server";
/* 203 */             discClass = "com.adventnet.nms.appln.oracle.discovery.server.OracleDiscovery";
/*     */           }
/* 205 */           else if (serverType.equals("APACHE:80"))
/*     */           {
/* 207 */             resourceType = "Apache-server";
/* 208 */             resourceType1 = "IIS-server";
/* 209 */             resourceType2 = "WEB-server";
/* 210 */             discClass = "com.adventnet.appmanager.server.discovery.ApacheDiscovery";
/* 211 */             discClass1 = "com.adventnet.appmanager.server.discovery.IISDiscovery";
/* 212 */             discClass2 = "com.adventnet.appmanager.server.discovery.WebserverDiscovery";
/*     */           }
/* 214 */           else if (serverType.equals("IIS:80"))
/*     */           {
/* 216 */             resourceType = "IIS-server";
/* 217 */             resourceType1 = "Apache-server";
/* 218 */             resourceType2 = "WEB-server";
/* 219 */             discClass = "com.adventnet.appmanager.server.discovery.IISDiscovery";
/* 220 */             discClass1 = "com.adventnet.appmanager.server.discovery.ApacheDiscovery";
/* 221 */             discClass2 = "com.adventnet.appmanager.server.discovery.WebserverDiscovery";
/*     */           }
/* 223 */           else if (serverType.equals("PHP:80"))
/*     */           {
/* 225 */             resourceType = "PHP";
/* 226 */             discClass = "com.adventnet.appmanager.server.discovery.PhpDiscovery";
/*     */           }
/* 228 */           else if (serverType.equals("WEB:80"))
/*     */           {
/* 230 */             resourceType = "WEB-server";
/* 231 */             resourceType1 = "Apache-server";
/* 232 */             resourceType2 = "IIS-server";
/* 233 */             discClass = "com.adventnet.appmanager.server.discovery.WebserverDiscovery";
/* 234 */             discClass1 = "com.adventnet.appmanager.server.discovery.ApacheDiscovery";
/* 235 */             discClass2 = "com.adventnet.appmanager.server.discovery.IISDiscovery";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/* 242 */           else if (serverType.equals("SERVICE:9090"))
/*     */           {
/* 244 */             resourceType = "Port-Test";
/* 245 */             discClass = "com.adventnet.appmanager.server.portmonitoring.discovery.PortDiscovery";
/*     */           }
/* 247 */           else if (serverType.equals("RMI:1099"))
/*     */           {
/* 249 */             resourceType = "RMI";
/* 250 */             discClass = "com.adventnet.appmanager.server.discovery.jmxrmi.JMXRMIDiscovery";
/*     */           }
/* 252 */           else if (serverType.equals("SNMP:161"))
/*     */           {
/* 254 */             resourceType = "SNMP";
/* 255 */             discClass = "com.adventnet.appmanager.server.discovery.snmp.SNMPDiscovery";
/*     */           }
/* 257 */           else if (serverType.equals("TELNET:23"))
/*     */           {
/* 259 */             resourceType = "TELNET";
/* 260 */             discClass = "com.adventnet.appmanager.server.portmonitoring.discovery.PortDiscovery";
/*     */           }
/* 262 */           else if (serverType.equals("ORACLEAS:7200"))
/*     */           {
/* 264 */             resourceType = "ORACLE-APP-server";
/* 265 */             discClass = "com.adventnet.appmanager.server.oracleas.discovery.OracleASDiscovery";
/*     */           }
/* 267 */           discInfo.setTargetAddress(targetAddress);
/* 268 */           discInfo.setResourceType(resourceType);
/* 269 */           int servicePort = -1;
/*     */           try
/*     */           {
/* 272 */             servicePort = Integer.parseInt(targetPort);
/*     */           }
/*     */           catch (Exception e) {}
/*     */           
/*     */ 
/*     */ 
/* 278 */           discInfo.setPortNo(servicePort);
/* 279 */           ResourceTypeIfc resTypeIfc = null;
/* 280 */           ResourceTypeIfc resTypeIfc1 = null;
/* 281 */           ResourceTypeIfc resTypeIfc2 = null;
/* 282 */           String serviceMessage = FormatUtil.getString("am.webclient.hostdiscovery.service");
/* 283 */           boolean serviceRunning = true;
/* 284 */           if (discClass != null)
/*     */           {
/*     */             try
/*     */             {
/* 288 */               resTypeIfc = (ResourceTypeIfc)Class.forName(discClass).newInstance();
/* 289 */               if (discClass1 != null)
/*     */               {
/* 291 */                 resTypeIfc1 = (ResourceTypeIfc)Class.forName(discClass1).newInstance();
/*     */               }
/* 293 */               if (discClass2 != null)
/*     */               {
/* 295 */                 resTypeIfc2 = (ResourceTypeIfc)Class.forName(discClass2).newInstance();
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 300 */               e.printStackTrace();
/*     */             }
/* 302 */             if (discClass != null)
/*     */             {
/* 304 */               serviceRunning = resTypeIfc.checkResourceType(discInfo, 5);
/*     */             }
/* 306 */             if ((serverType.equals("WEB:80")) || (serverType.equals("APACHE:80")) || (serverType.equals("IIS:80")))
/*     */             {
/* 308 */               if (!serviceRunning)
/*     */               {
/* 310 */                 discInfo.setResourceType(resourceType1);
/* 311 */                 serviceRunning = resTypeIfc1.checkResourceType(discInfo, 5);
/* 312 */                 if (!serviceRunning)
/*     */                 {
/* 314 */                   discInfo.setResourceType(resourceType2);
/* 315 */                   serviceRunning = resTypeIfc2.checkResourceType(discInfo, 5);
/*     */                 }
/*     */               }
/*     */             }
/* 319 */             if (discInfo.getUserProperty("error_message") != null)
/*     */             {
/* 321 */               if (!text.equals("")) text = text + "<br><br>";
/* 322 */               text = text + targetAddress + " : " + servicePort + " <br>" + FormatUtil.getString(discInfo.getUserProperty("error_message")) + "<br>";
/* 323 */               text = appendSystemDiscoveryMessage(discInfo, text);
/*     */ 
/*     */ 
/*     */             }
/* 327 */             else if (!serviceRunning)
/*     */             {
/* 329 */               list.add(targetAddress);
/* 330 */               list1.add(String.valueOf(servicePort));
/* 331 */               list2.add(appendSystemDiscoveryMessage(discInfo, serviceMessage));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 337 */       if (list.size() > 0)
/*     */       {
/* 339 */         int listSize = list.size();
/* 340 */         for (int i = 0; i < listSize; i++)
/*     */         {
/* 342 */           text = text + "<br>" + list.get(i) + " : " + list1.get(i) + " <br> " + list2.get(i) + "<br>";
/*     */         }
/*     */       }
/* 345 */       out.print(text);
/*     */       
/* 347 */       out.write(10);
/* 348 */       out.write(9);
/* 349 */       out.write(9);
/* 350 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 352 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 353 */         out = _jspx_out;
/* 354 */         if ((out != null) && (out.getBufferSize() != 0))
/* 355 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 356 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 359 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostServiceDiscovery_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */