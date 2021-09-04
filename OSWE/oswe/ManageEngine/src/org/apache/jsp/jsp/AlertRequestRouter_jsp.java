/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.management.log.LogUser;
/*     */ import com.adventnet.nms.fe.alert.AlertSessionBean;
/*     */ import com.adventnet.nms.jsp.JspUtility;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class AlertRequestRouter_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  25 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
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
/*  49 */     HttpSession session = null;
/*     */     
/*     */ 
/*  52 */     JspWriter out = null;
/*  53 */     Object page = this;
/*  54 */     JspWriter _jspx_out = null;
/*  55 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  59 */       response.setContentType("text/html");
/*  60 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*     */       
/*  62 */       _jspx_page_context = pageContext;
/*  63 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  64 */       ServletConfig config = pageContext.getServletConfig();
/*  65 */       session = pageContext.getSession();
/*  66 */       out = pageContext.getOut();
/*  67 */       _jspx_out = out;
/*     */       
/*  69 */       out.write("\n<HTML>\n<HEAD>\n\n\n\n\n\n<TITLE>");
/*  70 */       out.print(NmsUtil.GetString("Alert Request Router"));
/*  71 */       out.write("</TITLE>\n<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\"../template/nmshtmlui.css\">\n</HEAD>\n<BODY>\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  73 */       AlertSessionBean alertSession = com.adventnet.nms.fe.alert.AlertFE.getAlertSession();
/*  74 */       Properties parameters = JspUtility.parseRequestData(request);
/*  75 */       String entity = parameters.getProperty("altentity");
/*  76 */       String userName = (String)session.getAttribute("userName");
/*     */       
/*  78 */       String header = "<CENTER><FONT ID=cap>" + NmsUtil.GetString("Alert Details") + "</FONT><HR>";
/*  79 */       String unAuthorizedMsg = header + "<font class='redFont'>" + "Sorry ! You are not authorized to perform this operation" + "</font>";
/*  80 */       String errorMessage = header + "<H3>" + NmsUtil.GetString("An error occured while processing your request. Please refer logs for more details") + "</H3>";
/*  81 */       String backButton = "<P><A HREF='javascript:history.back()' OnMouseOut='window.status=\"\";return true' OnMouseOver=\"window.status='" + NmsUtil.GetString("Go Back to Previous") + "';return true\"><IMG SRC='../images/back.png' ALT='" + NmsUtil.GetString("Back") + "' BORDER='0'></A></P></CENTER>";
/*     */       
/*  83 */       if ((entity == null) || (entity.equals("")))
/*     */       {
/*     */ 
/*  86 */         out.write("\n\n\t\t");
/*  87 */         out.print(header);
/*  88 */         out.write("\n\t\t<H3>");
/*  89 */         out.print(NmsUtil.GetString("No Alert Entity is mentioned."));
/*  90 */         out.write("</H3>\n\t\t");
/*  91 */         out.print(backButton);
/*  92 */         out.write(10);
/*  93 */         out.write(9);
/*  94 */         out.write(9);
/*     */       }
/*     */       else
/*     */       {
/*  98 */         if (parameters.containsKey("Clear"))
/*     */         {
/* 100 */           if (!GenericUtility.isAuthorized(userName, "Clear Alerts"))
/*     */           {
/*     */ 
/* 103 */             out.write("\n\t\t\t");
/* 104 */             out.print(unAuthorizedMsg);
/* 105 */             out.write("\n\t\t\t");
/* 106 */             out.print(backButton);
/* 107 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/* 111 */           alertSession.clearAlert(userName, entity);
/*     */           try
/*     */           {
/* 114 */             out.write(10);
/* 115 */             out.write(9);
/* 116 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 117 */             out.write(10);
/* 118 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 122 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 124 */             out.write("\n\t\t\t");
/* 125 */             out.print(errorMessage);
/* 126 */             out.write("\n\t\t\t");
/* 127 */             out.print(backButton);
/* 128 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 132 */         else if (parameters.containsKey("Delete"))
/*     */         {
/* 134 */           if (!GenericUtility.isAuthorized(userName, "Delete Alerts"))
/*     */           {
/*     */ 
/* 137 */             out.write("\n\t\t\t");
/* 138 */             out.print(unAuthorizedMsg);
/* 139 */             out.write("\n\t\t\t");
/* 140 */             out.print(backButton);
/* 141 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/*     */           try
/*     */           {
/* 147 */             alertSession.deleteAlert(userName, entity);
/*     */             
/* 149 */             out.write("\n\t\t\t");
/* 150 */             out.print(header);
/* 151 */             out.write("\n\t\t\t<H3>");
/* 152 */             out.print(NmsUtil.GetString("Request successfully processed.The Alert was successfully deleted"));
/* 153 */             out.write("</H3>\n\t\t\t");
/* 154 */             out.print(backButton);
/* 155 */             out.write("\n\t\t\t");
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 159 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/*     */ 
/* 162 */             out.write("\n\t\t\t");
/* 163 */             out.print(errorMessage);
/* 164 */             out.write("\n\t\t\t");
/* 165 */             out.print(backButton);
/* 166 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 170 */         else if (parameters.containsKey("Pickup"))
/*     */         {
/* 172 */           if (!GenericUtility.isAuthorized(userName, "Alert Pickup"))
/*     */           {
/*     */ 
/* 175 */             out.write("\n\t\t\t");
/* 176 */             out.print(unAuthorizedMsg);
/* 177 */             out.write("\n\t\t\t");
/* 178 */             out.print(backButton);
/* 179 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/* 183 */           alertSession.pickUpAlert(userName, entity);
/*     */           
/*     */           try
/*     */           {
/* 187 */             out.write(10);
/* 188 */             out.write(9);
/* 189 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 190 */             out.write(10);
/* 191 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 195 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 197 */             out.write("\n\t\t\t\t");
/* 198 */             out.print(errorMessage);
/* 199 */             out.write("\n\t\t\t\t");
/* 200 */             out.print(backButton);
/* 201 */             out.write("\n\t\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 205 */         else if (parameters.containsKey("Unpick"))
/*     */         {
/* 207 */           if (!GenericUtility.isAuthorized(userName, "Alert Pickup"))
/*     */           {
/*     */ 
/* 210 */             out.write("\n\t\t\t");
/* 211 */             out.print(unAuthorizedMsg);
/* 212 */             out.write("\n\t\t\t");
/* 213 */             out.print(backButton);
/* 214 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/* 218 */           alertSession.unPickAlert(userName, entity);
/*     */           
/*     */           try
/*     */           {
/* 222 */             out.write(10);
/* 223 */             out.write(9);
/* 224 */             out.write(9);
/* 225 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 226 */             out.write(10);
/* 227 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 231 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 233 */             out.write("\n\t\t\t");
/* 234 */             out.print(errorMessage);
/* 235 */             out.write("\n\t\t\t");
/* 236 */             out.print(backButton);
/* 237 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 241 */         else if (parameters.containsKey("Annotate"))
/*     */         {
/* 243 */           if (!GenericUtility.isAuthorized(userName, "Get Alert Annotation"))
/*     */           {
/*     */ 
/* 246 */             out.write("\n\t\t\t");
/* 247 */             out.print(unAuthorizedMsg);
/* 248 */             out.write("\n\t\t\t");
/* 249 */             out.print(backButton);
/* 250 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/* 254 */           if (!parameters.getProperty("Annotate").equals(""))
/*     */           {
/* 256 */             alertSession.updateNotes(userName, entity, parameters.getProperty("Annotate"));
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 261 */             out.write(10);
/* 262 */             out.write(9);
/* 263 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 264 */             out.write(10);
/* 265 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 269 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 271 */             out.write("  \n\t\t\t<H3>Annotation failed</H3>\n\t\t\t");
/* 272 */             out.print(backButton);
/* 273 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 277 */         else if (parameters.containsKey("Refresh"))
/*     */         {
/*     */ 
/*     */           try
/*     */           {
/* 282 */             out.write(10);
/* 283 */             out.write(9);
/* 284 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 285 */             out.write(10);
/* 286 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 290 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 292 */             out.write("\n\t\t\t");
/* 293 */             out.print(errorMessage);
/* 294 */             out.write("\n\t\t\t");
/* 295 */             out.print(backButton);
/* 296 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 300 */         else if (parameters.containsKey("Events"))
/*     */         {
/*     */ 
/*     */           try
/*     */           {
/* 305 */             out.write(10);
/* 306 */             out.write(9);
/* 307 */             JspRuntimeLibrary.include(request, response, "/jsp/GetEvents.jsp" + ("/jsp/GetEvents.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 308 */             out.write(10);
/* 309 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 313 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 315 */             out.write("\n\t\t\t");
/* 316 */             out.print(errorMessage);
/* 317 */             out.write("\n\t\t\t");
/* 318 */             out.print(backButton);
/* 319 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 323 */         else if (parameters.containsKey("history"))
/*     */         {
/* 325 */           if (!GenericUtility.isAuthorized(userName, "Get Alert History"))
/*     */           {
/*     */ 
/* 328 */             out.write("\n\t\t\t");
/* 329 */             out.print(unAuthorizedMsg);
/* 330 */             out.write("\n\t\t\t");
/* 331 */             out.print(backButton);
/* 332 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 339 */             out.write(10);
/* 340 */             out.write(9);
/* 341 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 342 */             out.write(10);
/* 343 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 347 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 349 */             out.write("\n\t\t\t");
/* 350 */             out.print(errorMessage);
/* 351 */             out.write("\n\t\t\t");
/* 352 */             out.print(backButton);
/* 353 */             out.write("\n\t\t\t");
/*     */           }
/*     */           
/*     */         }
/* 357 */         else if (parameters.containsKey("annotation"))
/*     */         {
/* 359 */           if (!GenericUtility.isAuthorized(userName, "Get Alert Annotation"))
/*     */           {
/*     */ 
/* 362 */             out.write("\n\t\t\t");
/* 363 */             out.print(unAuthorizedMsg);
/* 364 */             out.write("\n\t\t\t");
/* 365 */             out.print(backButton);
/* 366 */             out.write("\n\t\t\t"); return;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 373 */             out.write(10);
/* 374 */             out.write(9);
/* 375 */             JspRuntimeLibrary.include(request, response, "/jsp/AlertDetails.jsp" + ("/jsp/AlertDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("entity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(entity), request.getCharacterEncoding()), out, true);
/* 376 */             out.write(10);
/* 377 */             out.write(9);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 381 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in AlertRequestRouter: "), e);
/*     */             
/* 383 */             out.write("\n\t\t\t");
/* 384 */             out.print(errorMessage);
/* 385 */             out.write("\n\t\t\t");
/* 386 */             out.print(backButton);
/* 387 */             out.write("\n\t\t\t");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 392 */         out.write("    \n</BODY>\n</HTML>\t           \n");
/*     */       }
/* 394 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 395 */         out = _jspx_out;
/* 396 */         if ((out != null) && (out.getBufferSize() != 0))
/* 397 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 398 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 401 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AlertRequestRouter_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */