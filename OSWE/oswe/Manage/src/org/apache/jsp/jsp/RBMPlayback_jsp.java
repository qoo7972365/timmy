/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RBMPlayback_jsp
/*     */   extends HttpJspBase
/*     */   implements JspSourceDependent
/*     */ {
/*  30 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  54 */     HttpSession session = null;
/*     */     
/*     */ 
/*  57 */     JspWriter out = null;
/*  58 */     Object page = this;
/*  59 */     JspWriter _jspx_out = null;
/*  60 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  64 */       response.setContentType("text/html");
/*  65 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  67 */       _jspx_page_context = pageContext;
/*  68 */       ServletContext application = pageContext.getServletContext();
/*  69 */       ServletConfig config = pageContext.getServletConfig();
/*  70 */       session = pageContext.getSession();
/*  71 */       out = pageContext.getOut();
/*  72 */       _jspx_out = out;
/*     */       
/*  74 */       out.write("<!-- $Id$ \n\n\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n\n");
/*     */       
/*     */ 
/*     */ 
/*  78 */       String connected = request.getParameter("connected");
/*     */       
/*  80 */       String clientname = "";
/*  81 */       String clientproxyname = "";
/*  82 */       Cookie[] cookies = request.getCookies();
/*  83 */       if (cookies != null)
/*     */       {
/*  85 */         for (int i = 0; i < cookies.length; i++)
/*     */         {
/*  87 */           if (cookies[i].getName().equalsIgnoreCase("clientproxyname"))
/*     */           {
/*  89 */             clientproxyname = cookies[i].getValue();
/*     */           }
/*  91 */           if (cookies[i].getName().equalsIgnoreCase("clientname"))
/*     */           {
/*  93 */             clientname = cookies[i].getValue();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  98 */       String client = clientproxyname + "_" + clientname;
/*  99 */       if ((clientproxyname == null) || (clientproxyname.equalsIgnoreCase("")))
/*     */       {
/* 101 */         client = "rbm_rbm";
/* 102 */         connected = "false";
/*     */       }
/*     */       else
/*     */       {
/* 106 */         connected = "true";
/*     */       }
/* 108 */       System.out.println("Client : " + client);
/* 109 */       if ((connected == null) || (connected.equalsIgnoreCase("false")))
/*     */       {
/* 111 */         connected = "false";
/*     */       }
/*     */       else
/*     */       {
/* 115 */         String clientName = request.getParameter("ind");
/* 116 */         String clientProxyName = request.getParameter("pxy");
/* 117 */         String usr = request.getParameter("usr");
/* 118 */         Cookie clientNameCookie = new Cookie("clientname", clientName + "__" + usr);
/* 119 */         clientNameCookie.setPath("/");
/* 120 */         response.addCookie(clientNameCookie);
/*     */         
/* 122 */         Cookie clientProxyNameCookie = new Cookie("clientproxyname", clientProxyName);
/* 123 */         clientProxyNameCookie.setPath("/");
/* 124 */         response.addCookie(clientProxyNameCookie);
/*     */         
/*     */ 
/* 127 */         Cookie usrNameCookie = new Cookie("username", usr);
/* 128 */         usrNameCookie.setPath("/");
/* 129 */         response.addCookie(usrNameCookie);
/*     */         
/* 131 */         if ((clientProxyName == null) || (clientName == null) || (usr == null))
/*     */         {
/* 133 */           if ((client == null) || (client.equalsIgnoreCase("")))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 138 */             client = clientProxyName + "_" + clientName + "__" + usr;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 145 */       String mode = request.getParameter("mode");
/* 146 */       String playbackReasourceId = request.getParameter("resourceid");
/* 147 */       if (playbackReasourceId == null)
/*     */       {
/* 149 */         playbackReasourceId = "";
/*     */       }
/* 151 */       System.out.println("[ Play back Resource Id ] : " + playbackReasourceId);
/* 152 */       if (mode == null)
/*     */       {
/* 154 */         mode = "record";
/*     */       }
/*     */       
/* 157 */       String script = request.getParameter("script");
/*     */       
/*     */ 
/* 160 */       out.write("\n\n</head>\n\n<script>\nvar scriptName=\"");
/* 161 */       out.print(script);
/* 162 */       out.write("\";\nvar http=\"\";\nvar connected = \"");
/* 163 */       out.print(connected);
/* 164 */       out.write("\";\nvar testPlayback = false;\nvar http1=\"\";\nfunction getHTTPObject() \n{\n\tvar xmlhttp;\n \t/*@cc_on\n  \t@if (@_jscript_version >= 5)\n  \t  try \n  \t  {\n  \t    xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");//No I18N\n  \t  } catch (e) \n  \t  {\n  \t    try \n  \t    {\n  \t      xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");//No I18N\n  \t    } catch (E) \n  \t    {\n  \t      xmlhttp = false;//No I18N\n  \t    }\n  \t  }\n  \t@else\n  \txmlhttp = false;\n\t  @end @*/\n\tif (!xmlhttp && typeof XMLHttpRequest != 'undefined') \n\t{\n\t  try \n          {\n      \t    xmlhttp = new XMLHttpRequest();//No I18N\n    \t  } catch (e) \n    \t  {\n      \t    xmlhttp = false;\n    \t  }\n  \t}\n\treturn xmlhttp;\n}\n\nfunction changeState(val)\n{\n\tif(val==\"recordOff\")\n\t{\n\t\tgetAllScripts();\n\t}\n\tscriptframe.changeState(val);\n}\n\n\nfunction change(val,ind,pxy,usr)\n{\n\tif(val==true)\n\t{\n\t\tsendConnectionDetails(ind,pxy,usr);\n\t}\n}\n\n\nfunction sendConnectionDetails(ind,pxy,usr)\n{\n\thttp = getHTTPObject();\n\tvar url=\"ScriptUpdator.jsp?method=connectiondetails&ind=\"+ind+\"&usr=\"+usr+\"&pxy=\"+pxy;//No I18N\n");
/* 165 */       out.write("\thttp.open(\"GET\", url, true);//No I18N\n\thttp.onreadystatechange = connectionResult; //No I18N\n\thttp.send(null);\n\n}\n\nfunction connectionResult()\n{\n\tif(http.readyState == 4) \n\t{\n\t\tconnected = \"true\";//No I18N\n\t\tif(\"");
/* 166 */       out.print(mode);
/* 167 */       out.write("\"==\"playback\" || testPlayback)\n\t\t{\n\t\t\tdocument.getElementById(\"scriptframe\").src=\"StartPlay.jsp?scriptname=\"+scriptName+\"&resourceid=");
/* 168 */       out.print(playbackReasourceId);
/* 169 */       out.write("\";//No I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"scriptframe\").src=\"StartRecord.jsp?scriptname=\"+scriptName;//No I18N\n\t\t}\n\t}\n}\n\n\n\nfunction checkForPlayback()\n{\n\tif(\"");
/* 170 */       out.print(mode);
/* 171 */       out.write("\"==\"playback\")\n\t{\n\t\tdocument.getElementById(\"scriptframe\").src=\"DetectToolBarIE.jsp\";//No I18N\n\t}\n\n}\n</script>\n\n\n<body onload=\"checkForPlayback();\">\n<form name=\"rbmform\" method=post >\n<table width=\"60%\" align=center>\n\t<tr>\n\t<td colspan=2 align=center class=\"bodytext\" ><b>Real Browser Monitoring </b><br>Currently playing the script :\t<b> ");
/* 172 */       out.print(script);
/* 173 */       out.write(" </b></h2>\n\t</td>\n\t</tr>\n<tr>\n<td colspan=2 height=\"400\" width=\"100%\" >\n\t\t<iframe src=\"\" frameborder=\"0\" id=\"scriptframe\" name=\"scriptframe\" width=\"100%\" height=\"400\" scrolling=\"auto\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n</td>\n</tr>\n</table>\n</form>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 175 */       if (!(t instanceof SkipPageException)) {
/* 176 */         out = _jspx_out;
/* 177 */         if ((out != null) && (out.getBufferSize() != 0))
/* 178 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 179 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 182 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RBMPlayback_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */