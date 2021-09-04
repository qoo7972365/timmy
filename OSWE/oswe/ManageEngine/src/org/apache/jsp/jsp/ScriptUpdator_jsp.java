/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.qengine.serverfwk.jsp.core.EventProcessor;
/*     */ import com.adventnet.qengine.web.server.core.EventHandler;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
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
/*     */ public final class ScriptUpdator_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  51 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("<!-- $Id$ -->\n\n\n\n\t\n\n\n\n\n\n\n");
/*     */       
/*  73 */       response.setContentType("text/html; charset=UTF-8");
/*     */       
/*  75 */       System.out.println("WEB FUNCTIONAL TEST MODIFICATION");
/*  76 */       String method = request.getParameter("method");
/*  77 */       String clientname = "";
/*  78 */       String clientproxyname = "";
/*  79 */       Cookie[] cookies = request.getCookies();
/*  80 */       String client = "";
/*  81 */       if (method.equalsIgnoreCase("connectiondetails"))
/*     */       {
/*  83 */         String clientName = request.getParameter("ind");
/*  84 */         String clientProxyName = request.getParameter("pxy");
/*  85 */         clientproxyname = clientProxyName;
/*  86 */         String usr = request.getParameter("usr");
/*  87 */         Cookie clientNameCookie = new Cookie("clientname", clientName + "__" + usr);
/*  88 */         clientNameCookie.setPath("/");
/*  89 */         response.addCookie(clientNameCookie);
/*     */         
/*  91 */         Cookie clientProxyNameCookie = new Cookie("clientproxyname", clientProxyName);
/*  92 */         clientProxyNameCookie.setPath("/");
/*  93 */         response.addCookie(clientProxyNameCookie);
/*     */         
/*  95 */         Cookie connectedCookie = new Cookie("connected", "true");
/*  96 */         connectedCookie.setPath("/");
/*  97 */         response.addCookie(connectedCookie);
/*     */         
/*  99 */         Cookie usrNameCookie = new Cookie("username", usr);
/* 100 */         usrNameCookie.setPath("/");
/* 101 */         response.addCookie(usrNameCookie);
/* 102 */         clientname = clientName + "__" + usr;
/* 103 */         client = clientProxyName + "_" + clientname;
/*     */       }
/* 105 */       else if (cookies != null)
/*     */       {
/* 107 */         for (int i = 0; i < cookies.length; i++)
/*     */         {
/* 109 */           if (cookies[i].getName().equalsIgnoreCase("clientproxyname"))
/*     */           {
/* 111 */             clientproxyname = cookies[i].getValue();
/*     */           }
/* 113 */           if (cookies[i].getName().equalsIgnoreCase("clientname"))
/*     */           {
/* 115 */             clientname = cookies[i].getValue();
/*     */           }
/*     */         }
/* 118 */         if (!clientname.equals(""))
/*     */         {
/* 120 */           Cookie clientNameCookie = new Cookie("clientname", clientname);
/* 121 */           clientNameCookie.setPath("/");
/* 122 */           response.addCookie(clientNameCookie);
/*     */         }
/* 124 */         if (!clientproxyname.equals(""))
/*     */         {
/* 126 */           Cookie proxyNameCookie = new Cookie("clientproxyname", clientproxyname);
/* 127 */           proxyNameCookie.setPath("/");
/* 128 */           response.addCookie(proxyNameCookie);
/*     */         }
/*     */       }
/* 131 */       if ((clientproxyname == null) || (clientproxyname.equals("")) || (clientproxyname.equals("null")))
/*     */       {
/* 133 */         String clientName = request.getParameter("ind");
/* 134 */         String clientProxyName = request.getParameter("pxy");
/* 135 */         clientproxyname = clientProxyName;
/* 136 */         String usr = request.getParameter("usr");
/* 137 */         Cookie clientNameCookie = new Cookie("clientname", clientName + "__" + usr);
/* 138 */         clientNameCookie.setPath("/");
/* 139 */         response.addCookie(clientNameCookie);
/*     */         
/* 141 */         Cookie clientProxyNameCookie = new Cookie("clientproxyname", clientProxyName);
/* 142 */         clientProxyNameCookie.setPath("/");
/* 143 */         response.addCookie(clientProxyNameCookie);
/*     */         
/*     */ 
/* 146 */         Cookie usrNameCookie = new Cookie("username", usr);
/* 147 */         usrNameCookie.setPath("/");
/* 148 */         response.addCookie(usrNameCookie);
/* 149 */         clientname = clientName + "__" + usr;
/* 150 */         client = clientProxyName + "_" + clientname;
/*     */       }
/* 152 */       client = clientproxyname + "_" + clientname;
/* 153 */       System.out.println("[Script Updator ] : CLient ::: " + client);
/*     */       
/* 155 */       EventProcessor processor = EventProcessor.getInstance(client);
/* 156 */       EventHandler eventHandler = null;
/* 157 */       if (processor != null)
/*     */       {
/* 159 */         eventHandler = (EventHandler)processor.getMessageHandler("WFT").getEventHandler();
/*     */       }
/* 161 */       String testDir = System.getProperty("test.dir");
/* 162 */       if (testDir == null) {
/* 163 */         testDir = ".";
/*     */       }
/*     */       
/*     */ 
/* 167 */       out.write(10);
/* 168 */       out.write(10);
/* 169 */       out.write(9);
/*     */       
/*     */ 
/* 172 */       response.setHeader("Cache-Control", "no-cache");
/* 173 */       String responseStr = "";
/* 174 */       String suiteName = "rbmsuite";
/* 175 */       System.out.println("method : " + method);
/* 176 */       if (method.equalsIgnoreCase("getscriptlines"))
/*     */       {
/*     */ 
/* 179 */         responseStr = "[SCRIPTLINES]" + eventHandler.sebean.setOrGetRecordModeText("get", "") + "[ENDOFSCRIPTLINES]";
/* 180 */         if (responseStr.equalsIgnoreCase(""))
/*     */         {
/* 182 */           responseStr = "";
/*     */         }
/* 184 */         responseStr = responseStr.replaceAll("[\"]", "\\\\\"");
/* 185 */         responseStr = responseStr.replaceAll("[\\n]", "<br>");
/* 186 */         System.out.println("ScriptLINNNNNN : " + responseStr);
/*     */       }
/* 188 */       else if (method.equalsIgnoreCase("savemonitor"))
/*     */       {
/* 190 */         String mon_name = request.getParameter("monitorname");
/* 191 */         String script = request.getParameter("scriptname");
/* 192 */         System.out.println("Monitor Name : " + mon_name);
/* 193 */         System.out.println("Script Name : " + script);
/*     */       }
/* 195 */       else if (method.equalsIgnoreCase("connectiondetails"))
/*     */       {
/* 197 */         processor.setTestTypeFromUI("WFT");
/* 198 */         boolean toolbarUpdationNeeded = processor.getToolBarUpdationNeeded("IE");
/* 199 */         System.out.println("Toolbar Updation  ::: " + toolbarUpdationNeeded);
/* 200 */         responseStr = "[RESULT]TOOLBAR:" + toolbarUpdationNeeded + "[RESULT]";
/*     */ 
/*     */       }
/* 203 */       else if (method.equalsIgnoreCase("getallscripts"))
/*     */       {
/* 205 */         String scripts = "[SCRIPTS]";
/*     */         
/*     */         try
/*     */         {
/* 209 */           File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts");
/* 210 */           if (f.exists())
/*     */           {
/* 212 */             File[] all = f.listFiles();
/* 213 */             for (int i = 0; i < all.length; i++)
/*     */             {
/*     */ 
/* 216 */               if (!all[i].isFile())
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/* 221 */                 scripts = scripts + ":;:" + all[i].getName();
/*     */               }
/*     */               
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 231 */           responseStr = "";
/*     */         }
/* 233 */         scripts = scripts + "[ENDOFSCRIPTS]";
/* 234 */         responseStr = scripts;
/*     */       }
/* 236 */       else if (method.equalsIgnoreCase("getallscriptslines"))
/*     */       {
/* 238 */         String scripts = "";
/* 239 */         String script = request.getParameter("scriptname");
/*     */         
/*     */         try
/*     */         {
/* 243 */           File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script + File.separator + script + ".wcs");
/* 244 */           if (f.exists())
/*     */           {
/* 246 */             FileInputStream fis = new FileInputStream(f);
/* 247 */             byte[] s = new byte[(int)f.length()];
/* 248 */             fis.read(s);
/* 249 */             fis.close();
/* 250 */             fis = null;
/* 251 */             scripts = "[SCRIPTLINES]" + new String(s) + "[ENDOFSCRIPTLINES]";
/*     */             
/* 253 */             responseStr = scripts;
/*     */           }
/*     */           else
/*     */           {
/* 257 */             responseStr = "[ERROR]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.notexists", new String[] { script }) + "[ERROR]";
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 263 */           e.printStackTrace();
/* 264 */           responseStr = "[ERROR]" + e.getMessage() + "[ERROR]";
/*     */         }
/*     */       }
/* 267 */       else if (method.equalsIgnoreCase("deletescript"))
/*     */       {
/* 269 */         String scripts = "";
/* 270 */         String script = request.getParameter("scriptname");
/*     */         
/*     */         try
/*     */         {
/* 274 */           AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */           
/* 276 */           ResultSet rs = AMConnectionPool.executeQueryStmt("select RESOURCEID,SCRIPT from AM_RBMDATA where SCRIPT='" + script + "' ");
/* 277 */           if (rs.next())
/*     */           {
/* 279 */             int resId = rs.getInt(1);
/* 280 */             responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.unabletodelete", new String[] { script }) + "[RESULT]";
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 285 */             File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script);
/* 286 */             System.out.println("Delete File path : " + f.getPath());
/* 287 */             boolean res = false;
/* 288 */             if (f.exists())
/*     */             {
/* 290 */               res = com.adventnet.testtools.common.util.FileUtil.deleteFile(f);
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 297 */             if (res)
/*     */             {
/* 299 */               responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.message.delete") + "[RESULT]";
/*     */             }
/*     */             else
/*     */             {
/* 303 */               responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.deleteerror") + "[RESULT]";
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 309 */           e.printStackTrace();
/* 310 */           responseStr = "[ERROR]" + e.getMessage() + "[ERROR]";
/*     */         }
/*     */       }
/* 313 */       else if (method.equalsIgnoreCase("renamescript"))
/*     */       {
/* 315 */         String scripts = "";
/* 316 */         String script = request.getParameter("scriptname");
/* 317 */         String newName = request.getParameter("newscriptname");
/*     */         
/*     */         try
/*     */         {
/* 321 */           AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */           
/*     */           try
/*     */           {
/* 325 */             File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script + File.separator + script + ".wcs");
/* 326 */             File nf = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script + File.separator + newName + ".wcs");
/* 327 */             if (f.exists())
/*     */             {
/* 329 */               f.renameTo(nf);
/*     */               
/* 331 */               File mf = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script + File.separator + script + ".map");
/* 332 */               if (mf.exists())
/*     */               {
/* 334 */                 File nmf = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script + File.separator + newName + ".map");
/* 335 */                 mf.renameTo(nmf);
/*     */               }
/*     */               
/* 338 */               File wf = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + script);
/* 339 */               File wnf = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + newName);
/* 340 */               wf.renameTo(wnf);
/* 341 */               AMConnectionPool.executeUpdateStmt("update AM_RBMDATA set SCRIPT='" + newName + "' where SCRIPT='" + script + "'");
/* 342 */               System.out.println("query : update AM_RBMDATA set SCRIPT='" + newName + "' where SCRIPT='" + script + "'");
/* 343 */               AMConnectionPool.executeUpdateStmt("update AM_RBMAGENTSCHEDULE set SCRIPT='" + newName + "' where SCRIPT='" + script + "'");
/* 344 */               System.out.println("query : update AM_RBMAGENTSCHEDULE set SCRIPT='" + newName + "' where SCRIPT='" + script + "'");
/* 345 */               responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.rename") + "[RESULT]";
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 350 */               responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.renameerror", new String[] { script }) + "[RESULT]";
/*     */             }
/*     */             
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 356 */             e.printStackTrace();
/* 357 */             responseStr = "[RESULT]" + FormatUtil.getString("am.webclient.rbm.scriptmanager.renameerror1") + "[RESULT]";
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 364 */           e.printStackTrace();
/* 365 */           responseStr = "[ERROR]" + e.getMessage() + "[ERROR]";
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 371 */       response.getWriter().write(responseStr);
/*     */       
/* 373 */       out.write("\n\n\n\n");
/*     */     } catch (Throwable t) {
/* 375 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 376 */         out = _jspx_out;
/* 377 */         if ((out != null) && (out.getBufferSize() != 0))
/* 378 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 379 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 382 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScriptUpdator_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */