/*    */ package org.apache.jsp.jsp.includes;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class HelpCard_jsp extends HttpJspBase implements JspSourceDependent
/*    */ {
/* 20 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 29 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 34 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 44 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 47 */     JspWriter out = null;
/* 48 */     Object page = this;
/* 49 */     JspWriter _jspx_out = null;
/* 50 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 54 */       response.setContentType("text/html");
/* 55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 57 */       _jspx_page_context = pageContext;
/* 58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 59 */       ServletConfig config = pageContext.getServletConfig();
/* 60 */       session = pageContext.getSession();
/* 61 */       out = pageContext.getOut();
/* 62 */       _jspx_out = out;
/*    */       
/* 64 */       out.write(10);
/* 65 */       out.write(10);
/*    */       
/* 67 */       String helpcardKey = request.getParameter("helpcardKey");
/* 68 */       String hideStyle = request.getParameter("hideStyle") != null ? request.getParameter("hideStyle") : "";
/*    */       
/* 70 */       if ((helpcardKey != null) && (!helpcardKey.equals("")))
/*    */       {
/*    */ 
/* 73 */         out.write("\n<a name=\"helpcard\"></a>\n\t<table width=\"99%\"  id=\"HelpDetails\"  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" class=\"");
/* 74 */         out.print(hideStyle);
/* 75 */         out.write("\">\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t\t<tr>\n\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 76 */         out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 77 */         out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\n\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t</table></td>\n\t\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t\t<td valign=\"top\">\n\t\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t    <tr>\n\t    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n\t      <tr>\n\t        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t        <tr>\n\t          <td class=\"hCardInnerTopLeft\"/>\n\t          <td class=\"hCardInnerTopBg\"/>\n\t          <td class=\"hCardInnerTopRight\"/>\n\t        </tr>\n\t        <tr>\n\n\t          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t                <td class=\"hCardInnerBoxBg product-help\">\n");
/* 78 */         out.write("\n\t\t\t\t\t\n\t\t\t\t\t\n\t                 <span class=\"bodytext\">\n\t\t\t\t\t\t");
/* 79 */         out.print(helpcardKey);
/* 80 */         out.write("\n\t\t\t\t\t\t\n\n    \n\n    \n\n   \n\n    \n\t\t\t\t\t\t \n\t\t\t\t\t\t\n\t\t\t\t\t\t</span>\n\t\t\t\t\n\n\t            </td>\n\n\t          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t        </tr>\n\n\t      </table></td>\n\t      </tr>\n\t     </table>\n\t     </td>\n\t  </tr>\n\t</table>\n\t</td>\n\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t\t</tr>\n\t\t\t</table>\n");
/*    */       }
/* 82 */       out.write(10);
/*    */     } catch (Throwable t) {
/* 84 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 85 */         out = _jspx_out;
/* 86 */         if ((out != null) && (out.getBufferSize() != 0))
/* 87 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 88 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 91 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\HelpCard_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */