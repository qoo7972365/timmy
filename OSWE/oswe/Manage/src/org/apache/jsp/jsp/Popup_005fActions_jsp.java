/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class Popup_005fActions_jsp extends HttpJspBase implements JspSourceDependent
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
/*  60 */       response.setContentType("text/html");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<!--$Id$-->\n\n<html>\n<head>\n\n\n\n\n\n\n\n\n\n\n\n<title>Action Details</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<script language=\"JavaScript1.2\">\n\tfunction fnFramechange(optvalue)\n\t{\n\treturn (window.iframe_actions.location.href=optvalue);\n\n\t}\n</script>\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  71 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  73 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  74 */       out.write("\n</head>\n\n<body>\n<form name=\"form1\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"reportperiod\">\n    <tr> \n      <td width=\"32%\" class=\"bodytext\">&nbsp;");
/*  75 */       out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/*  76 */       out.write("&nbsp;:</td>\n    <td width=\"68%\"><select name=\"select\" class=\"formtext\" onChange=\"fnFramechange(document.form1.select.options[this.selectedIndex].value);\">\n");
/*     */       
/*  78 */       String page1 = "";
/*     */       try
/*     */       {
/*  81 */         AMConnectionPool pool = AMConnectionPool.getInstance();
/*  82 */         ResultSet result = AMConnectionPool.executeQueryStmt("select ID,NAME from AM_ACTIONPROFILE where TYPE !=25 AND NAME NOT IN ('Marker','Restart The Service')");
/*  83 */         int i = 0;
/*  84 */         while (result.next())
/*     */         {
/*  86 */           if (i == 0)
/*     */           {
/*  88 */             page1 = String.valueOf(result.getInt("ID"));
/*     */             
/*     */ 
/*  91 */             out.write("\n\n\t\t\t<option value=\"/showActionProfiles.do?method=getActionDetails&actionid=");
/*  92 */             out.print(result.getInt("ID"));
/*  93 */             out.write("\" selected=\"true\">");
/*  94 */             out.print(result.getString("NAME"));
/*  95 */             out.write("</option>\n\t\t");
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/* 101 */             out.write("\n\n\t\t\t<option value=\"/showActionProfiles.do?method=getActionDetails&actionid=");
/* 102 */             out.print(result.getInt("ID"));
/* 103 */             out.write(34);
/* 104 */             out.write(62);
/* 105 */             out.print(result.getString("NAME"));
/* 106 */             out.write("</option>\n\n\t\t");
/*     */           }
/*     */           
/*     */ 
/* 110 */           out.write("\n\t\t\t");
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */       out.write("\n      </select></td>\n  </tr>\n\n\n</table>\n<BR>\n  <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n  \t");
/*     */       
/* 121 */       if (!page1.equals(""))
/*     */       {
/*     */ 
/* 124 */         out.write("\n    <tr> \n    <td height=\"18\" colspan=\"2\" class=\"bodytext\"> <iframe name=\"iframe_actions\" id=\"iframe_actions\"  frameborder=\"0\" src=\"/showActionProfiles.do?method=getActionDetails&actionid=");
/* 125 */         out.print(page1);
/* 126 */         out.write("\" width=\"100%\" height=\"230\" vspace=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe></td>\n  </tr>\n  ");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 132 */         out.write("\n\t\t\t<tr>\n\t\t\t<td height=\"18\" colspan=\"2\" class=\"bodytext\"> <table width=\"400\" height=\"230\" ><tr><td class=\"bodytext\" align=\"center\">");
/* 133 */         out.print(FormatUtil.getString("am.webclient.configurealert.noactions"));
/* 134 */         out.write("</td></tr></table></td>\n\t\t\t</tr>\n\t");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 139 */       out.write("\n  </table>\n</form>\n");
/* 140 */       response.setContentType("text/html;charset=UTF-8");
/* 141 */       out.write("\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 143 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 144 */         out = _jspx_out;
/* 145 */         if ((out != null) && (out.getBufferSize() != 0))
/* 146 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 147 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 150 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 156 */     PageContext pageContext = _jspx_page_context;
/* 157 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 159 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 160 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 161 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 163 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 165 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 166 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 167 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 168 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 169 */       return true;
/*     */     }
/* 171 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 172 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fActions_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */