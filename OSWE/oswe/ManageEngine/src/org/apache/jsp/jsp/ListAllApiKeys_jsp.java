/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class ListAllApiKeys_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  52 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html;charset=UTF-8");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n\n<title>");
/*  73 */       out.print(FormatUtil.getString("am.webclient.apikey.text"));
/*  74 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<LINK REL=\"SHORTCUT ICON\" HREF=\"/images/logo32x32.ico\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  75 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  77 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n</head>\n<body>\n<form name=\"userAPIKey\" method=\"POST\" onSubmit=\"close_window()\">\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/*  78 */       out.print(FormatUtil.getString("am.webclient.apikey.text"));
/*  79 */       out.write("</span></td>\n\t</tr>\n</table>\n<br>\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" class=\"lrtbdarkborder\" style=\"margin-top:10px;margin-left:7px;\">\n<tbody>\n<tr>\n\t<td height=\"18\" valign=\"top\" width=\"50%\"> \n\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t<tbody>\n\t\t\t\t<tr colspan=\"2\" class=\"tableheadingbborder\">\n\t\t\t\t\t<td class=\"tableheadingbborder\" width=\"25%\">");
/*  80 */       out.print(FormatUtil.getString("am.webclient.user.text"));
/*  81 */       out.write("</td>\n\t\t\t\t\t<td class=\"tableheadingbborder\" width=\"25%\">");
/*  82 */       out.print(FormatUtil.getString("am.webclient.userrole.text"));
/*  83 */       out.write("</td>\n\t\t\t\t\t<td class=\"tableheadingbborder\" width=\"50%\">");
/*  84 */       out.print(FormatUtil.getString("am.webclient.apikey.text"));
/*  85 */       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*     */       
/*  87 */       HashMap resultMap = com.adventnet.appmanager.util.Constants.getAPIKeysForAdmin();
/*  88 */       int resSize = resultMap.size();
/*  89 */       Properties resultProps = new Properties();
/*  90 */       for (int i = 0; i < resSize; i++)
/*     */       {
/*  92 */         resultProps = (Properties)resultMap.get(Integer.valueOf(i));
/*     */         
/*  94 */         out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"25%\">");
/*  95 */         out.print(resultProps.get("USERNAME"));
/*  96 */         out.write("</td>\n\t\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"25%\">");
/*  97 */         out.print(resultProps.get("GROUPNAME"));
/*  98 */         out.write("</td>\n\t\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"50%\">");
/*  99 */         out.print(resultProps.get("APIKEY"));
/* 100 */         out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/*     */       }
/*     */       
/*     */ 
/* 104 */       out.write("\n\t\t\t\t\n\t\t\t</tbody>\n\t\t</table>\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n <tr> \n\t\t\t\t\t<td colspan=\"3\" class=\"tablebottom\" align=\"center\"><input value=\"");
/* 105 */       out.print(FormatUtil.getString("am.webclient.eventlog.close.text"));
/* 106 */       out.write("\" name=\"btnClose\" style=\"width: 90px;\" class=\"buttons btn_link\" onClick=\"self.close()\" type=\"button\"></td>\n\t\t\t\t</tr>\n</table>\n\t\n\t</td>\n</tr>\t\n</tbody>\n</table>\n</form>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 108 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 109 */         out = _jspx_out;
/* 110 */         if ((out != null) && (out.getBufferSize() != 0))
/* 111 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 112 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 115 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 121 */     PageContext pageContext = _jspx_page_context;
/* 122 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 124 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 125 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 126 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 128 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 130 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 131 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 132 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 133 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 134 */       return true;
/*     */     }
/* 136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ListAllApiKeys_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */