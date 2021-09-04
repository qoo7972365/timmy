/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class UserError_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  50 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  53 */     JspWriter out = null;
/*  54 */     Object page = this;
/*  55 */     JspWriter _jspx_out = null;
/*  56 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  60 */       response.setContentType("text/html;charset=UTF-8");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  71 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  73 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n<body topmargin=\"0\" leftmargin=\"0\" rightmargin=\"0\">\n   <table width=\"100%\" height=\"50\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"darkheaderbg\">\n     <tr>\n     <td width=\"100%\" align=\"left\"><img src=\"/images/");
/*  74 */       out.print(com.adventnet.appmanager.util.OEMUtil.getOEMString("am.header.logo"));
/*  75 */       out.write("\"></td>\n     </tr>\n   </table><br /><br/>\n <table width=\"60%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n    ");
/*     */       
/*     */ 
/*  78 */       String errType = request.getParameter("errType");
/*  79 */       if ((errType != null) && (errType.equals("duplicatesession")))
/*     */       {
/*  81 */         System.out.println("DuplicateSEssion------>>>>>>>>" + errType);
/*     */         
/*  83 */         out.write("\n          <tr height=\"25\">\n            <td width=\"72%\" class=\"tableheadingbborder\"><b>");
/*  84 */         out.print(FormatUtil.getString("am.webclient.login.err.duplicatesession.text"));
/*  85 */         out.write("</b></td>\n          </tr> \n          <tr height=\"35\">               <!--<td class=\"msg-table-width-bg\" width=\"2%\"><img src=\"../images/icon_message_failure.gif\" /></td>\t-->\n            <td class=\"msg-table-width\" width=\"98%\" align=\"center\">\n              <table >\t\n                 <tr class=\"msg-table-width\" align=\"center\">\n                    <td > <img src=\"../images/icon_message_failure.gif\" />  </td>\n                    <td >    \t  \t       \n    \t      \t       ");
/*  86 */         out.print(FormatUtil.getString("am.webclient.login.err.duplicatesession.msg.text", new String[] { FormatUtil.getString(request.getParameter("username")), FormatUtil.getString(request.getParameter("remotehost")) }));
/*  87 */         out.write(" \n    \t\t       <a href=\"index.do?logoutuser=true\">");
/*  88 */         out.print(FormatUtil.getString("am.webclient.login.err.duplicatesession.terminate.existingsession.text"));
/*  89 */         out.write("\n    \t\t\t<!-- <script>alert('");
/*  90 */         out.print(FormatUtil.getString("am.webclient.login.err.duplicatesession.text"));
/*  91 */         out.write("');</script>  -->\n                    </td>\n                 </tr>\n\t     </table>\n\t    </td> \n   \t  </tr>  \n    ");
/*     */       }
/*  93 */       else if ((errType != null) && (errType.equals("accountlock")))
/*     */       {
/*  95 */         out.write("\n          <tr height=\"25\">\n            <td width=\"72%\" class=\"tableheadingbborder\"><b>");
/*  96 */         out.print(FormatUtil.getString("am.webclient.login.err.accountlock.msg.text"));
/*  97 */         out.write("</b></td>\n          </tr> \n          <tr height=\"35\">               <!--<td class=\"msg-table-width-bg\" width=\"2%\"><img src=\"../images/icon_message_failure.gif\" /></td>\t-->\n            <td class=\"msg-table-width\" width=\"98%\" align=\"center\">\n              <table >\t\n\t        <tr class=\"msg-table-width\" align=\"center\">\n\t           <td > <img src=\"../images/icon_message_failure.gif\" />  </td>\n                   <td >           \n                       ");
/*  98 */         String timeOut = request.getParameter("timeOut");
/*  99 */         long timeout = Long.parseLong(timeOut);
/* 100 */         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
/* 101 */         String unlockTime = sdf.format(new Date(timeout));
/*     */         
/* 103 */         out.write("\n                      ");
/* 104 */         out.print(FormatUtil.getString("am.webclient.login.err.accountlock.text", new String[] { FormatUtil.getString(request.getRemoteUser()) }));
/* 105 */         out.write(32);
/* 106 */         out.print(FormatUtil.getString("am.webclient.tooltip.accountunlocktime.text", new String[] { unlockTime }));
/* 107 */         out.write("\n                   </td>     <!--<script>alert('");
/* 108 */         out.print(FormatUtil.getString("am.webclient.login.err.accountlock.text"));
/* 109 */         out.write("');</script>  -->\n                </tr>\n              </table>  \n            </td>\n          </tr>\n    ");
/*     */       }
/*     */       
/*     */ 
/* 113 */       out.write("\n     \n \n<!-- <table width=\"100%\">-->\n  <tr height=\"35\">\n   <td class=\"msg-table-width\" width=\"98%\" align=\"center\"> \n         <a href=\"/jsp/formpages/logout.jsp?fromAdminAction=true\">");
/* 114 */       out.print(FormatUtil.getString("am.webclient.login.login.loginasdifferentuser.text"));
/* 115 */       out.write("\n    </td>   \n  </tr>\n </table>\n \n</body>");
/*     */     } catch (Throwable t) {
/* 117 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 118 */         out = _jspx_out;
/* 119 */         if ((out != null) && (out.getBufferSize() != 0))
/* 120 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 121 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 124 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 130 */     PageContext pageContext = _jspx_page_context;
/* 131 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 133 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 134 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 135 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 137 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 139 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 140 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 141 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 142 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 143 */       return true;
/*     */     }
/* 145 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UserError_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */