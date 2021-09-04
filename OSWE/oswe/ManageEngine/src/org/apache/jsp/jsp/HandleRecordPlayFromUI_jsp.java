/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.qengine.serverfwk.jsp.core.EventProcessor;
/*     */ import com.adventnet.qengine.serverfwk.jsp.core.MessageHandlerInterface;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HandleRecordPlayFromUI_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  46 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  49 */     JspWriter out = null;
/*  50 */     Object page = this;
/*  51 */     JspWriter _jspx_out = null;
/*  52 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  56 */       response.setContentType("text/html; charset=utf-8");
/*  57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "", true, 8192, true);
/*     */       
/*  59 */       _jspx_page_context = pageContext;
/*  60 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  61 */       ServletConfig config = pageContext.getServletConfig();
/*  62 */       session = pageContext.getSession();
/*  63 */       out = pageContext.getOut();
/*  64 */       _jspx_out = out;
/*     */       
/*  66 */       out.write("<!-- $Id$-->\n\n\n\n");
/*     */       
/*  68 */       response.setContentType("text/html; charset=UTF-8");
/*  69 */       response.addHeader("Cache-Control", "no-cache");
/*  70 */       String clientname = "";
/*  71 */       String clientproxyname = "";
/*  72 */       Cookie[] cookies = request.getCookies();
/*  73 */       if (cookies != null)
/*     */       {
/*  75 */         for (int i = 0; i < cookies.length; i++)
/*     */         {
/*  77 */           if (cookies[i].getName().equalsIgnoreCase("clientproxyname"))
/*     */           {
/*  79 */             clientproxyname = cookies[i].getValue();
/*     */           }
/*  81 */           if (cookies[i].getName().equalsIgnoreCase("clientname"))
/*     */           {
/*  83 */             clientname = cookies[i].getValue();
/*     */           }
/*     */         }
/*     */       }
/*  87 */       String client = clientproxyname + "_" + clientname;
/*  88 */       System.out.println("Handle Record Play Evee client name is " + client);
/*  89 */       if ((clientproxyname == null) || (clientproxyname.equals("")) || (clientproxyname.equals("null")))
/*     */       {
/*  91 */         String clientName = request.getParameter("ind");
/*  92 */         String clientProxyName = request.getParameter("pxy");
/*  93 */         clientproxyname = clientProxyName;
/*  94 */         String usr = request.getParameter("usr");
/*  95 */         Cookie clientNameCookie = new Cookie("clientname", clientName + "__" + usr);
/*  96 */         clientNameCookie.setPath("/");
/*  97 */         response.addCookie(clientNameCookie);
/*     */         
/*  99 */         Cookie clientProxyNameCookie = new Cookie("clientproxyname", clientProxyName);
/* 100 */         clientProxyNameCookie.setPath("/");
/* 101 */         response.addCookie(clientProxyNameCookie);
/*     */         
/*     */ 
/* 104 */         Cookie usrNameCookie = new Cookie("username", usr);
/* 105 */         usrNameCookie.setPath("/");
/* 106 */         response.addCookie(usrNameCookie);
/* 107 */         clientname = clientName + "__" + usr;
/* 108 */         client = clientProxyName + "_" + clientname;
/*     */       }
/* 110 */       client = clientproxyname + "_" + clientname;
/* 111 */       System.out.println("[Handle Record Playback and  ] : CLient ::: " + client);
/* 112 */       EventProcessor processor = EventProcessor.getInstance(client);
/* 113 */       String currenttheme = processor.currentTheme;
/*     */       
/* 115 */       out.write("\n\n\n\n");
/*     */       
/* 117 */       String testtype = request.getParameter("ttype");
/* 118 */       String toDoType = request.getParameter("todotype");
/* 119 */       String toDoMode = request.getParameter("todomode");
/* 120 */       String isSuiteLevel = request.getParameter("level");
/* 121 */       String recordingMode = request.getParameter("recMode");
/* 122 */       String resourceId = request.getParameter("resourceid");
/*     */       
/* 124 */       String filterName = request.getParameter("filtername");
/* 125 */       String sequenceName = request.getParameter("sequencename");
/* 126 */       String sequenceType = "automatic";
/*     */       
/* 128 */       String rbmMode = request.getParameter("rbmmode");
/*     */       
/*     */ 
/*     */ 
/* 132 */       if (filterName != null)
/*     */       {
/* 134 */         sequenceType = "automatic";
/*     */       }
/* 136 */       else if (sequenceName != null)
/*     */       {
/* 138 */         sequenceType = "manual";
/*     */       }
/*     */       else
/*     */       {
/* 142 */         sequenceType = "automatic";
/*     */       }
/*     */       
/* 145 */       MessageHandlerInterface messageHandler = processor.getMessageHandler(testtype.toUpperCase());
/* 146 */       messageHandler.setFilterName(filterName);
/* 147 */       messageHandler.setSequenceName(sequenceName);
/* 148 */       messageHandler.setSequenceType(sequenceType);
/* 149 */       messageHandler.playStartFromUI(true);
/*     */       
/* 151 */       System.out.println("testtype is " + testtype + " todo is " + toDoType + " mode = " + toDoMode + " suite level is " + isSuiteLevel);
/*     */       
/*     */ 
/* 154 */       if (rbmMode == null)
/*     */       {
/* 156 */         rbmMode = "false";
/*     */       }
/* 158 */       else if (rbmMode.equalsIgnoreCase("true"))
/*     */       {
/*     */ 
/* 161 */         String scriptName = request.getParameter("scriptname");
/* 162 */         System.out.println("[ HandleRecordPlayFromUI  ] [ Script name : " + scriptName + "]");
/* 163 */         if (scriptName != null)
/*     */         {
/* 165 */           processor.setRBMDetails(scriptName, resourceId);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 171 */       if (toDoType == null)
/*     */       {
/* 173 */         toDoType = "play";
/*     */       }
/* 175 */       if (toDoType.equalsIgnoreCase("play"))
/*     */       {
/* 177 */         if (toDoMode == null)
/*     */         {
/* 179 */           toDoMode = "start";
/*     */         }
/* 181 */         if (toDoMode.equalsIgnoreCase("start"))
/*     */         {
/* 183 */           processor.sendStartPlayToNative();
/*     */         }
/*     */         else
/*     */         {
/* 187 */           processor.sendStopPlayToNative();
/*     */         }
/*     */       }
/* 190 */       else if (toDoType.equalsIgnoreCase("record"))
/*     */       {
/* 192 */         if (toDoMode == null)
/*     */         {
/* 194 */           toDoMode = "start";
/*     */         }
/* 196 */         if (toDoMode.equalsIgnoreCase("start"))
/*     */         {
/* 198 */           processor.sendStartRecordToNative(recordingMode);
/*     */         }
/*     */         else
/*     */         {
/* 202 */           processor.sendStopRecordToNative();
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 207 */       else if (toDoType.indexOf("closebrowser") != -1)
/*     */       {
/* 209 */         processor.sendErrorHandleToNative(toDoType);
/*     */       }
/*     */       
/* 212 */       response.getWriter().write("OK");
/*     */       
/*     */ 
/* 215 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 217 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 218 */         out = _jspx_out;
/* 219 */         if ((out != null) && (out.getBufferSize() != 0))
/* 220 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 221 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 224 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HandleRecordPlayFromUI_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */