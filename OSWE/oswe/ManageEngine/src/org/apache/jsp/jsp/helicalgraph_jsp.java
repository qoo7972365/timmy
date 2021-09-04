/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class helicalgraph_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  28 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  33 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  43 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  46 */     JspWriter out = null;
/*  47 */     Object page = this;
/*  48 */     JspWriter _jspx_out = null;
/*  49 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  53 */       response.setContentType("text/html");
/*  54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  56 */       _jspx_page_context = pageContext;
/*  57 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  58 */       ServletConfig config = pageContext.getServletConfig();
/*  59 */       session = pageContext.getSession();
/*  60 */       out = pageContext.getOut();
/*  61 */       _jspx_out = out;
/*     */       
/*  63 */       out.write("<!--$Id$-->\n\n\n\n\n\n");
/*     */       
/*  65 */       String tooltip = request.getParameter("tooltip");
/*  66 */       if (tooltip == null)
/*     */       {
/*  68 */         tooltip = "true";
/*     */       }
/*  70 */       String width = "40px";
/*  71 */       String height = "90px";
/*  72 */       if (request.getParameter("isConfMonitor") != null) {
/*  73 */         width = "60px";
/*  74 */         height = "120px";
/*     */       }
/*  76 */       String data = request.getParameter("percent");
/*  77 */       if ((data == null) || (data.equals("-")) || (data.trim().equals(""))) {
/*  78 */         out.write("\n\t  <table class=\"no-graph-dial\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"disabledtext\" align=\"center\">");
/*  79 */         out.print(com.adventnet.appmanager.util.FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*  80 */         out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n  ");
/*     */       }
/*     */       else {
/*     */         try {
/*  84 */           int percent = Integer.parseInt(data);
/*     */           
/*  86 */           out.write("\n    \t<table border=\"0\" style=\"background:black;\" cellpadding=\"7\">\n    \t<tr>\n    \t<td>\n\t    \t\n      <table width=\"");
/*  87 */           out.print(width);
/*  88 */           out.write("\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\n        <tr>\n          <td background=\"/images/bg_unusedjvm.gif\" height=\"");
/*  89 */           out.print(height);
/*  90 */           out.write("\" valign=\"bottom\">\n\t  <table height=\"");
/*  91 */           out.print(percent * 90 / 100);
/*  92 */           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"/images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n\t  ");
/*     */           
/*     */ 
/*  95 */           if (tooltip.equals("false"))
/*     */           {
/*     */ 
/*  98 */             out.write("\n          <td background=\"/images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"90\" >\n          ");
/*     */           }
/*     */           else
/*     */           {
/* 102 */             out.write("\n\t          <td background=\"/images/bg_unusedjvm.gif\" valign=\"bottom\" height=\"90\" title=\"");
/* 103 */             out.print(tooltip);
/* 104 */             out.write(32);
/* 105 */             out.write(45);
/* 106 */             out.write(32);
/* 107 */             out.print(percent);
/* 108 */             out.write("%\">\n\t          ");
/*     */           }
/* 110 */           out.write("\n\t  <table height=\"");
/* 111 */           out.print(percent * 90 / 100);
/* 112 */           out.write("px\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td background=\"/images/bg_usedjvm.gif\" width=\"21\"><!--img src=\"../images/spacer.gif\" width=\"21\" height=\"1\"--></td>\n              </tr>\n            </table>\n\t  </td>\n        </tr>\n        <tr>\n        <td colspan=\"2\" align=\"center\"><span style=\"color:#33ff33;font-weight:bold;font-size:11px;font-family: Arial, Helvetica, sans-serif;\">");
/* 113 */           out.print(percent);
/* 114 */           out.write("%</span></td>\n        </tr>\n      </table>\n      </td>\n      </tr>\n      </table>\n");
/*     */         } catch (NumberFormatException e) {
/* 116 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Throwable t) {
/* 120 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 121 */         out = _jspx_out;
/* 122 */         if ((out != null) && (out.getBufferSize() != 0))
/* 123 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 124 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 127 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\helicalgraph_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */