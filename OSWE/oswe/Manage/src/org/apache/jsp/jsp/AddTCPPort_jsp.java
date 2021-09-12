/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionHandler;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.model.CollectData;
/*     */ import com.adventnet.nms.applnfw.discovery.server.ResourceTypeIfc;
/*     */ import com.adventnet.nms.applnfw.discovery.server.model.DiscoveryInfo;
/*     */ import com.adventnet.nms.applnfw.discovery.server.model.InetService;
/*     */ import com.adventnet.nms.topodb.TopoAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
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
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AddTCPPort_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  35 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  49 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  59 */     HttpSession session = null;
/*     */     
/*     */ 
/*  62 */     JspWriter out = null;
/*  63 */     Object page = this;
/*  64 */     JspWriter _jspx_out = null;
/*  65 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  69 */       response.setContentType("text/html");
/*  70 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  72 */       _jspx_page_context = pageContext;
/*  73 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  74 */       ServletConfig config = pageContext.getServletConfig();
/*  75 */       session = pageContext.getSession();
/*  76 */       out = pageContext.getOut();
/*  77 */       _jspx_out = out;
/*     */       
/*  79 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  82 */       String name = request.getParameter("host");
/*  83 */       String port = request.getParameter("port");
/*  84 */       String type = request.getParameter("type");
/*  85 */       String command = request.getParameter("command");
/*  86 */       String search = request.getParameter("search");
/*  87 */       String haid = request.getParameter("haid");
/*  88 */       String appname = request.getParameter("appname");
/*  89 */       TopoAPI topo = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*     */       
/*  91 */       if (type != null) {
/*  92 */         if (type.equals("delete")) {
/*  93 */           InetService mo1 = (InetService)topo.getByName(name + "-" + port);
/*  94 */           if (mo1 != null) {
/*  95 */             ApplnDataCollectionHandler.getInstance().deleteDataCollection(name + "-" + port, "PORT");
/*  96 */             topo.deleteObject(mo1);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 102 */         DiscoveryInfo discInfo = new DiscoveryInfo();
/* 103 */         discInfo.setTargetAddress(name);
/* 104 */         discInfo.setPortNo(Integer.parseInt(port));
/* 105 */         discInfo.setResourceType("Port");
/* 106 */         ResourceTypeIfc resTypeIfc = null;
/*     */         try {
/* 108 */           resTypeIfc = (ResourceTypeIfc)Class.forName("com.adventnet.appmanager.server.portmonitoring.discovery.PortDiscovery").newInstance();
/*     */         }
/*     */         catch (Exception e) {
/* 111 */           e.printStackTrace();
/*     */         }
/* 113 */         boolean result = resTypeIfc.checkResourceType(discInfo, 5);
/* 114 */         if (result) {
/* 115 */           InetService mo = new InetService();
/* 116 */           mo.setName(name + "-" + port);
/* 117 */           mo.setManaged(true);
/* 118 */           mo.setStatus(5);
/* 119 */           mo.setFailureThreshold(1);
/* 120 */           mo.setFailureCount(0);
/* 121 */           mo.setStatusChangeTime(System.currentTimeMillis());
/* 122 */           mo.setStatusUpdateTime(System.currentTimeMillis());
/* 123 */           mo.setTester("usertest");
/* 124 */           mo.setUClass("com.adventnet.nms.applnfw.statuspoll.server.ApplicationStatusPoller");
/* 125 */           mo.setStatusPollEnabled(false);
/* 126 */           mo.setIsContainer(false);
/* 127 */           mo.setIsGroup(false);
/* 128 */           mo.setClassname("InetService");
/* 129 */           Properties moProps = new Properties();
/* 130 */           moProps.setProperty("interfaceName", name);
/* 131 */           moProps.setProperty("targetName", name);
/* 132 */           moProps.setProperty("targetAddress", name);
/* 133 */           moProps.setProperty("displayName", name + "-" + port);
/* 134 */           moProps.setProperty("type", "Port-Test");
/* 135 */           moProps.setProperty("pollInterval", "140");
/* 136 */           mo.setPortNo(Integer.parseInt(port));
/* 137 */           mo.setProperties(moProps);
/* 138 */           topo.addObject(mo);
/* 139 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 140 */           if (!command.equals("")) {
/* 141 */             ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 142 */             CollectData data = null;
/*     */             try {
/* 144 */               data = api.getCollectData(name + "-" + port, "PORT");
/*     */             }
/*     */             catch (Exception e) {}
/*     */             
/*     */ 
/* 149 */             while (data == null) {
/*     */               try {
/* 151 */                 data = api.getCollectData(name + "-" + port, "PORT");
/*     */               }
/*     */               catch (Exception e) {}
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 159 */             String query = "update PortConfig set COMMAND='" + command + "' , SEARCH='" + search + "' where RESOURCENAME='" + name + "-" + port + "'";
/* 160 */             AMConnectionPool.executeUpdateStmt(query);
/*     */           }
/* 162 */           if (!haid.equals("null")) {
/* 163 */             Thread.sleep(1000L);
/* 164 */             String resid = "select RESOURCEID from AM_ManagedObject where RESOURCENAME='" + name + "-" + port + "' and TYPE='Port-Test'";
/* 165 */             ResultSet rs = AMConnectionPool.executeQueryStmt(resid);
/* 166 */             if (rs.next()) {
/* 167 */               int res = rs.getInt(1);
/* 168 */               String appaddquery = "insert into AM_PARENTCHILDMAPPER (PARENTID, CHILDID) values (" + haid + "," + res + ")";
/* 169 */               AMConnectionPool.executeUpdateStmt(appaddquery);
/*     */             }
/* 171 */             rs.close();
/*     */             
/*     */ 
/*     */ 
/* 175 */             out.write("\n\t\t\t\t");
/*     */             
/* 177 */             _jspx_page_context.forward("../jsp/showapplication.jsp?haid=<%=haid%>" + ("../jsp/showapplication.jsp?haid=<%=haid%>".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("appname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(appname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(haid), request.getCharacterEncoding())); return;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 187 */           out.write("\t\t\n\t\t\n\t\t\n\t\t");
/*     */           
/* 189 */           _jspx_page_context.forward("../jsp/networkdetails.jsp?network=Port-Test&appname=<%=appname%>&haid=<%=haid%>" + ("../jsp/networkdetails.jsp?network=Port-Test&appname=<%=appname%>&haid=<%=haid%>".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("appname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(appname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("network", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("Port-Test", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(haid), request.getCharacterEncoding())); return;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 197 */         String error = FormatUtil.getString("am.webclient.addmonitors.errormsg");
/*     */         
/* 199 */         out.write(10);
/* 200 */         out.write(9);
/* 201 */         out.write(9);
/*     */         
/* 203 */         _jspx_page_context.forward("../jsp/TCPMonitoring.jsp?appname=<%=appname%>&haid=<%=haid%>&error=<%=error%>" + ("../jsp/TCPMonitoring.jsp?appname=<%=appname%>&haid=<%=haid%>&error=<%=error%>".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("error", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(error), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("appname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(appname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(haid), request.getCharacterEncoding())); return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 211 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 213 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 214 */         out = _jspx_out;
/* 215 */         if ((out != null) && (out.getBufferSize() != 0))
/* 216 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 217 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 220 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AddTCPPort_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */