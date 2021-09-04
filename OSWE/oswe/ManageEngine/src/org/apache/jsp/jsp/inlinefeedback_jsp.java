/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class inlinefeedback_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
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
/*  56 */       response.setContentType("text/html");
/*  57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  59 */       _jspx_page_context = pageContext;
/*  60 */       ServletContext application = pageContext.getServletContext();
/*  61 */       ServletConfig config = pageContext.getServletConfig();
/*  62 */       session = pageContext.getSession();
/*  63 */       out = pageContext.getOut();
/*  64 */       _jspx_out = out;
/*     */       
/*  66 */       out.write("<!-- $Id$-->\n\n");
/*  67 */       out.write("\n<script language=\"JavaScript1.2\">\nfunction firstCheck()\n{\nvar message='Thank you for your feedback';\n\tif(document.forms[\"inlinefeedback\"].Description.value.trim() == '')\n\t{\n\t\tmessage=\"Please provide your feedback\";\n\t\talert('");
/*  68 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.alert.message"));
/*  69 */       out.write("');\n\t\treturn false;\n\t}\nsendFeedback('");
/*  70 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.thankyou"));
/*  71 */       out.write("');\n}\n</script>\n");
/*     */       
/*  73 */       String uri1 = (String)request.getAttribute("uri");
/*  74 */       String qs1 = (String)request.getAttribute("qs");
/*  75 */       String context1 = uri1 + "?" + qs1;
/*     */       
/*  77 */       String display = "none";
/*  78 */       Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/*  79 */       Object showFeedback = globals.get("showFeedback");
/*  80 */       if ((Constants.getGlobalObject("SMTP") != null) && ((showFeedback == null) || (((String)showFeedback).equals("true"))) && (!OEMUtil.isOEM()))
/*     */       {
/*  82 */         display = "block";
/*     */       }
/*  84 */       if ((request.getParameter("hideArea") != null) && (request.getParameter("hideArea").equals("true")))
/*     */       {
/*  86 */         display = "none";
/*     */       }
/*     */       
/*     */ 
/*  90 */       out.write("\n\n\n<div id=\"feedback\" style=\"display:");
/*  91 */       out.print(display);
/*  92 */       out.write("\">\n\n<form name=\"inlinefeedback\" method=\"post\" action=\"\">\n<input type=\"hidden\" name=\"To\" value=\"");
/*  93 */       out.print(OEMUtil.getOEMString("product.talkback.mailid"));
/*  94 */       out.write("\">\n<input type=\"hidden\" name=\"Subject\" value=\"Inline Feedback - ");
/*  95 */       out.print(OEMUtil.getOEMString("product.name"));
/*  96 */       out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"submit\">\n<input type=\"hidden\" name=\"context\" value=\"");
/*  97 */       out.print(java.net.URLEncoder.encode(context1));
/*  98 */       out.write("\">\n    <div id=\"result\" style=\"display:none;\">\n            <table border=\"0\" width=\"100%\">\n              <tr align=\"center\">\n                <td class=\"bodytextbold\"><span id=\"result1\" style=\"font-weight:bold;color:red;font-size:11px\"></span></td>\n              </tr>\n            </table>\n          </div>\n\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmHdrTopLeft\"/>\n\t<td class=\"AlarmHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t<tr>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\"><span class=\"AlarmHdrTxt\"><b style=\"float:left; margin-bottom:3px;\">");
/*  99 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.message"));
/* 100 */       out.write(" </b><a href=\"javascript:hideFeedback()\"><img border=\"0\" src=\"/images/cross.gif\" title=\"");
/* 101 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.delete.tooltip"));
/* 102 */       out.write("\"></a></span></td>\n\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\"></td>\n\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t</tr>\n\t</table></td>\n\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t<td valign=\"top\">\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td >\n\t<!--text -->\n\t</td>\n\t</tr>\n\t<tr>\n\t<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"AlarmInnerTopLeft\"/>\n\t<td class=\"AlarmInnerTopBg\"/>\n\t<td class=\"AlarmInnerTopRight\"/>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t<td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\n\t<tr class=\"bodytext\">\n\t<td rowspan=\"3\" align=\"left\" valign=\"top\"><textarea class=\"formtextarea\" name=\"Description\" rows=\"5\" cols=\"55\"></textarea>\n");
/* 103 */       out.write("\t</td>\n\t<td>");
/* 104 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.name"));
/* 105 */       out.write("</td>\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Name\"></td>\n\t<td>&nbsp;</td>\n\t<td>&nbsp;</td>\n\t<td height=\"10\" align=\"right\"></td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td>");
/* 106 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.email"));
/* 107 */       out.write("</td>\n\t");
/*     */       
/* 109 */       String emailAddress = Constants.EMAIL_ADDRESS;
/*     */       
/* 111 */       out.write("\n\t<td><input class=\"formtext\" type=\"text\" size=\"25\" name=\"Email\" value=\"");
/* 112 */       out.print(emailAddress);
/* 113 */       out.write("\"></td>\n\t<td>&nbsp;&nbsp;&nbsp; </td>\n\t<td>&nbsp;</td>\n\t</tr>\n\t<tr class=\"bodytext\">\n\t<td height=\"21\">&nbsp; </td>\n\t<td colspan=\"3\" class=\"arial9\"><input name=\"button\" type=\"button\" class=\"buttons\" onClick=\"firstCheck()\" value=\"");
/* 114 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.send"));
/* 115 */       out.write("\">\n\t");
/* 116 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.infomessage"));
/* 117 */       out.write("</td>\n\t</tr>\n\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n\n\t</table>\n\n\t</td>\n\t<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\t</tr>\n\n\t</table></td>\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t</td>\n\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t<td class=\"AlarmCardMainBtmBg\"/>\n\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t</tr>\n\t</table>\n\n\n\n</form>\n</div>\n");
/*     */     } catch (Throwable t) {
/* 119 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 120 */         out = _jspx_out;
/* 121 */         if ((out != null) && (out.getBufferSize() != 0))
/* 122 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 123 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 126 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\inlinefeedback_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */