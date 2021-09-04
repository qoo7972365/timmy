/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.fault.AsyncSendMail;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import javax.mail.Address;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.SendFailedException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class sendfeedback_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  55 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  58 */     JspWriter out = null;
/*  59 */     Object page = this;
/*  60 */     JspWriter _jspx_out = null;
/*  61 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  65 */       response.setContentType("text/html;charset=UTF-8");
/*  66 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  68 */       _jspx_page_context = pageContext;
/*  69 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  70 */       ServletConfig config = pageContext.getServletConfig();
/*  71 */       session = pageContext.getSession();
/*  72 */       out = pageContext.getOut();
/*  73 */       _jspx_out = out;
/*     */       
/*  75 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n<html>\n<script>\nfunction fnSendAnother()\n{\n  location.href=\"/jsp/feedback.jsp?clearcomments=true&username=");
/*  76 */       out.print(request.getParameter("username"));
/*  77 */       out.write("&email=");
/*  78 */       out.print(request.getParameter("email"));
/*  79 */       out.write("\";\n}\n</script>\n<head>\n<title>Thank You1</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/*  80 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n</head>\n<body leftmargin=\"5\" topmargin=\"5\" marginwidth=\"3\" marginheight=\"3\">\n");
/*     */       
/*  84 */       String email = request.getParameter("email");
/*  85 */       String type = request.getParameter("type");
/*  86 */       String cc = request.getParameter("cc");
/*  87 */       if (cc == null)
/*     */       {
/*  89 */         cc = "";
/*     */       }
/*  91 */       else if (cc.equals("cc"))
/*     */       {
/*  93 */         cc = email;
/*     */       }
/*  95 */       Log log = org.apache.commons.logging.LogFactory.getLog("ShowResourceDetails");
/*  96 */       String comments = request.getParameter("comments");
/*  97 */       String username = request.getParameter("username");
/*  98 */       String context = request.getParameter("context");
/*  99 */       comments = comments + FormatUtil.getString("am.webclient.feedback.comments") + NmsUtil.GetString("WebNMS Version x.x") + FormatUtil.getString("am.webclient.feedbcak.comment1") + NmsUtil.GetString("product.build.number");
/* 100 */       comments = comments + FormatUtil.getString("am.webclient.feedback.comment2") + context;
/* 101 */       comments = comments + FormatUtil.getString("am.webclient.feedback.comment3");
/* 102 */       comments = comments + "\n ---------------------------------------------------------------------------------------------";
/* 103 */       for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();)
/*     */       {
/* 105 */         String headername = (String)e.nextElement();
/* 106 */         comments = comments + "\n " + headername + "        \t: " + request.getHeader(headername);
/*     */       }
/* 108 */       comments = comments + "\n ---------------------------------------------------------------------------------------------";
/* 109 */       String tomail = null;
/* 110 */       String sub = request.getParameter("subject");
/* 111 */       AsyncSendMail m = new AsyncSendMail(email, NmsUtil.GetString("product.talkback.mailid"), cc, sub);
/* 112 */       m.setUserName(username);
/* 113 */       m.setMessage(comments);
/* 114 */       boolean success = true;
/* 115 */       StringBuffer sbf = new StringBuffer();
/* 116 */       if (request.isUserInRole("DEMO"))
/*     */       {
/*     */ 
/* 119 */         System.out.println("Logging in as demo user");
/* 120 */         Thread th = new Thread(m);
/* 121 */         th.start();
/*     */       }
/*     */       else
/*     */       {
/* 125 */         System.out.println("Logging in as non -  demo user");
/*     */         Throwable ex;
/*     */         try
/*     */         {
/* 129 */           m.sendMail();
/* 130 */           System.out.println("Successfully send the message");
/*     */ 
/*     */         }
/*     */         catch (MessagingException mex)
/*     */         {
/* 135 */           success = false;
/* 136 */           ex = mex;
/*     */         }
/*     */         do
/*     */         {
/* 140 */           if ((ex instanceof SendFailedException))
/*     */           {
/* 142 */             SendFailedException sfex = (SendFailedException)ex;
/* 143 */             Address[] invalid = sfex.getInvalidAddresses();
/* 144 */             if ((invalid != null) && 
/* 145 */               (invalid != null))
/*     */             {
/* 147 */               for (int i = 0; i < invalid.length; i++)
/*     */               {
/* 149 */                 log.fatal("MAIL : Invalid addresses are ... " + invalid[i]);
/* 150 */                 sbf.append(FormatUtil.getString("am.webclient.feeback.invalidmessage") + invalid[i]);
/*     */               }
/*     */             }
/*     */             
/* 154 */             Address[] validUnsent = sfex.getValidUnsentAddresses();
/* 155 */             if ((validUnsent != null) && 
/* 156 */               (validUnsent != null)) {
/* 157 */               for (int i = 0; i < validUnsent.length; i++)
/*     */               {
/* 159 */                 log.fatal("MAIL Unsent Addresses are ..." + validUnsent[i]);
/*     */               }
/*     */             }
/*     */             
/* 163 */             Address[] validSent = sfex.getValidSentAddresses();
/* 164 */             if (validSent != null) {
/* 165 */               log.fatal("MAIL : Valid Sent Addresses");
/* 166 */               if (validSent != null) {
/* 167 */                 for (int i = 0; i < validSent.length; i++)
/*     */                 {
/* 169 */                   log.fatal("MAIL : Valid Sent addresses are..." + validSent[i]);
/*     */                 }
/*     */               }
/*     */             }
/* 173 */             sbf.append(FormatUtil.getString("am.webclient.feedback.message"));
/*     */           }
/* 175 */           else if ((ex instanceof javax.mail.AuthenticationFailedException))
/*     */           {
/* 177 */             log.fatal("MAIL : Authentication failed in SMTP server  for user.." + username);
/* 178 */             sbf.append(FormatUtil.getString("am.webclient.feedback.message2"));
/*     */           }
/* 180 */           else if ((ex instanceof java.net.UnknownHostException))
/*     */           {
/* 182 */             log.fatal("MAIL : SMTP Server specifed in Admin --> Configure Mail Server is not available --> ");
/* 183 */             sbf.append(FormatUtil.getString("am.webclient.feedback.message3"));
/*     */           }
/* 185 */           else if ((ex instanceof java.net.ConnectException))
/*     */           {
/* 187 */             log.fatal("MAIL : Invalid Port specified in Admin --> Configure Mail Server. Please provide the correct port number where your SMTP server is running --> ");
/* 188 */             sbf.append(FormatUtil.getString("am.webclient.feedback.message3"));
/*     */           }
/* 190 */         } while (((ex instanceof MessagingException)) && ((ex = ((MessagingException)ex).getNextException()) != null));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 198 */       out.write("\n<table width=\"455\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bordercolor=\"676767\" class=\"lrtbdarkborder\">\n    <tr > \n      <td height=\"27\"  align=\"center\" class=\"tableheadingbborder\">&nbsp;</td>\n    </tr>\n    <tr> \n    \t\n    <td height=\"181\" class = \"bodytext\"> \n    ");
/*     */       
/* 200 */       if (success)
/*     */       {
/*     */ 
/* 203 */         out.write("\n    Dear ");
/* 204 */         out.print(email);
/* 205 */         out.write(", <br>\n      <br>\n      Your request has been submitted to the Applications Manager Technical Assistance \n      Center. Technical Assistance people will look into this at the earliest. \n      ");
/*     */         
/* 207 */         out.println("The current time is ===>" + new Date().toString());
/*     */         
/* 209 */         out.write("\n      <br>\n      <br>\n      Thank you.<br>\n    \t<br>\n    \t\n    \t- Applications Manager Team.\n    \t");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 215 */         out.write("\n Dear ");
/* 216 */         out.print(email);
/* 217 */         out.write(", <br>\n      <br>\n      Unable to send your feedback due to the following reasons<br>\n      ");
/* 218 */         out.print(sbf.toString());
/* 219 */         out.write("\n      <br>\n      <br>\n      Thank you.<br>\n    \t<br>\n    \t\n    \t- Applications Manager Team.    \t \n    \t");
/*     */       }
/*     */       
/*     */ 
/* 223 */       out.write("\n    </td>\n    \n    </tr>\n    </table>\n<table width=\"455\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bordercolor=\"676767\" class=\"lrbborder\">\n    <tr > \n    <td height=\"27\"  align=\"center\" class=\"tablebottom\">\n    ");
/*     */       
/* 225 */       if (!success)
/*     */       {
/*     */ 
/* 228 */         out.write("\n  \t<input name=\"GoBack\" type=\"button\" class=\"buttons\" value=\"Go Back\" onClick=\"javascript:history.back();\">&nbsp;&nbsp; \n    ");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 234 */         out.write("\n       <input name=\"Send Another\" type=\"button\" class=\"buttons\" value=\"Send Another\" onClick=\"javascript:fnSendAnother()\">&nbsp;&nbsp; \n    ");
/*     */       }
/*     */       
/*     */ 
/* 238 */       out.write("\n    <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Close\" onClick=\"window.close();\"> \n    </td>\n     </tr>\n    </table>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 240 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 241 */         out = _jspx_out;
/* 242 */         if ((out != null) && (out.getBufferSize() != 0))
/* 243 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 244 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 247 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 253 */     PageContext pageContext = _jspx_page_context;
/* 254 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 256 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 257 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 258 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 260 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 262 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 263 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 264 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 265 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 266 */       return true;
/*     */     }
/* 268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 269 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sendfeedback_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */