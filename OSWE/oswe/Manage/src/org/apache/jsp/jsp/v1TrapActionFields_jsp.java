/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class v1TrapActionFields_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  28 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  33 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
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
/*  63 */       out.write("<!-- $Id$ -->\n\n");
/*     */       
/*  65 */       String[] type = { "0", "1", "2", "3", "4", "5", "6" };
/*  66 */       String[] displayString = { "coldStart", "warmStart", "linkDown", "linkUp", "authenticationFailure", "egpNeighbourLoss", "enterpriseSpecific" };
/*     */       
/*  68 */       out.write("\n<script>\nfunction checkType()\n{\n\tif(document.AMActionForm.v1TrapGenericType.value==6)\n\t{\n\t\tshowDiv('specifictype');\n\t}\n\telse\n\t{\n\t\thideDiv('specifictype');\n\t}\n}\n/*onload = function()\n{\n\tif(document.AMActionForm.v1TrapGenericType.value==6)\n\t{\n\t\tshowDiv('specifictype');\n\t}\n\telse\n\t{\n\t\thideDiv('specifictype');\n\t}\n}*/\n\n</script>\n <tr>\n    <td class=\"bodytext label-align\">");
/*  69 */       out.print(FormatUtil.getString("am.webclient.newaction.trapcommunity"));
/*  70 */       out.write("</td>\n    <td class=\"bodytext\"><html:text property=\"trapCommunity\" size=\"40\" styleClass=\"formtext default\"  maxlength=\"50\"/></td>\n    <td class=\"bodytext\">&nbsp;</td>\n  </tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/*  71 */       out.print(FormatUtil.getString("am.webclient.newaction.generictype"));
/*  72 */       out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">\n      <html:select property=\"v1TrapGenericType\" styleClass=\"formtext normal\" onchange=\"javascript:checkType()\">\n      ");
/*     */       
/*  74 */       for (int i = 0; i < type.length; i++)
/*     */       {
/*     */ 
/*  77 */         out.write("\n         <html:option value=\"");
/*  78 */         out.print(type[i]);
/*  79 */         out.write(34);
/*  80 */         out.write(62);
/*  81 */         out.write(32);
/*  82 */         out.print(displayString[i]);
/*  83 */         out.write("\n         </html:option>\n      ");
/*     */       }
/*     */       
/*     */ 
/*  87 */       out.write("\n      </html:select>\n      </td>\n</tr>\n<tr>\n\t  <td width=\"100%\" colspan=\"3\">\n\t  <div id=\"specifictype\" style=\"display:none\">\n\t  <table width=\"99%\">\n\t  <tr>\n     <td class=\"bodytext label-align\" width=\"25%\">");
/*  88 */       out.print(FormatUtil.getString("am.webclient.newaction.specifictype"));
/*  89 */       out.write("</td>\n      <td class=\"bodytext\"><html:text property=\"v1TrapSpecificType\" size=\"40\" styleClass=\"formtext default\"  maxlength=\"50\"/></td>\n\t  </tr>\n\t  </table>\n\t  </div>\n\t  </td>\n</tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/*  90 */       out.print(FormatUtil.getString("am.webclient.newaction.enterprise"));
/*  91 */       out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\"><html:text property=\"v1TrapEnterprise\" size=\"40\" styleClass=\"formtext default\"  maxlength=\"50\"/></td>\n</tr>\n");
/*     */     } catch (Throwable t) {
/*  93 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  94 */         out = _jspx_out;
/*  95 */         if ((out != null) && (out.getBufferSize() != 0))
/*  96 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  97 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 100 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\v1TrapActionFields_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */