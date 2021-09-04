/*     */ package org.apache.jsp.jsp.as400;
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
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class editsysvalue_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  29 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  34 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  44 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  47 */     JspWriter out = null;
/*  48 */     Object page = this;
/*  49 */     JspWriter _jspx_out = null;
/*  50 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  54 */       response.setContentType("text/html;charset=UTF-8");
/*  55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  57 */       _jspx_page_context = pageContext;
/*  58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write("<!--$Id$-->\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\n\tfunction OnValidatePort()\n\t{\n                document.ModifyServiceForm.submit();\n\t}\n</SCRIPT>\n\n\t\t\t");
/*     */       
/*  66 */       String type = request.getParameter("type");
/*  67 */       String displayname = request.getParameter("displayname");
/*  68 */       String port = request.getParameter("port");
/*  69 */       String resourceid = request.getParameter("resourceid");
/*  70 */       String dtype = request.getParameter("dtype");
/*     */       
/*  72 */       out.write("\n                        <form name=\"ModifyServiceForm\" action=\"/as400.do?method=modifysystemvalue&dtype=");
/*  73 */       out.print(dtype);
/*  74 */       out.write("&name=");
/*  75 */       out.print(displayname);
/*  76 */       out.write("&type=");
/*  77 */       out.print(type);
/*  78 */       out.write("&resourceid=");
/*  79 */       out.print(resourceid);
/*  80 */       out.write("\" method=\"POST\" >\n                            <input type=\"hidden\" name=\"displayname\" value=\"");
/*  81 */       out.print(displayname);
/*  82 */       out.write("\">\n                            <table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n                                <tr>\n                                    <td nowrap heigth=\"28\" width=\"40%\" class=\"bodytext\">");
/*  83 */       out.print(FormatUtil.getString("am.webclient.as400.name"));
/*  84 */       out.write("</td>\n\n\n                                    <td width=\"5\" heigth=\"28\"  class=\"bodytext\">:</td>\n                                    <td align=left heigth=\"28\" width=\"55%\" id=\"serviceNameForMod\" class=\"whitegrayborder\"> ");
/*  85 */       out.print(displayname);
/*  86 */       out.write("</td>\n                                </tr>\n                                <tr >\n                                    <td nowrap heigth=\"28\" width=\"40%\" class=\"bodytext\">");
/*  87 */       out.print(FormatUtil.getString("am.webclient.as400.value"));
/*  88 */       out.write("</td>\n                                    <td  heigth=\"28\" width=\"5%\" class=\"bodytext\">:</td>\n                                    <td align=left heigth=\"28\" width=\"55%\" class=\"bodytext\"><input type=\"text\" name=\"port\" class=\"formtext\" value=\"");
/*  89 */       out.print(port);
/*  90 */       out.write("\"></td>\n                                </tr>\n                                \n                                <tr>\n                                    <td colspan=\"3\" align=\"center\" width=\"100%\" style=\"padding: 10px 0px 0px 0px;\">\n                                         <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  91 */       out.print(FormatUtil.getString("am.webclient.common.save.text"));
/*  92 */       out.write("\" onClick=\"OnValidatePort()\"> </td>\n                                </tr>\n                            </table>\n                        </form>\n\t");
/*     */     } catch (Throwable t) {
/*  94 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  95 */         out = _jspx_out;
/*  96 */         if ((out != null) && (out.getBufferSize() != 0))
/*  97 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  98 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 101 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\editsysvalue_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */