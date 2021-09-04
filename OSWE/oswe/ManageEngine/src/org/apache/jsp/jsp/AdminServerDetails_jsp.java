/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class AdminServerDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  45 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  46 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  53 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html;charset=UTF-8");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  68 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!--$Id$-->\n<html>\n<head>\n\n\n\n\n\n\n\n\n\n<title>Admin Server Details</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/*  74 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  76 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/validation.js\" ></SCRIPT>\n<script>\n");
/*     */       
/*  78 */       if (request.getParameter("reloadParentWindow") != null)
/*     */       {
/*     */ 
/*  81 */         out.write("\n    window.close();\n    window.opener.location.href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general&addSuccessMessage=true\";\n");
/*     */       }
/*     */       
/*  84 */       out.write("\n\nfunction validateAndSubmit()\n{\n  if(document.AMActionForm.displayname.value=='')\n  {\n    alert(\"");
/*  85 */       out.print(FormatUtil.getString("am.webclient.managedserver.hostname.alert"));
/*  86 */       out.write("\");\n\tdocument.AMActionForm.displayname.select();\n\treturn false;\n  }\n  var temp = trimAll(document.AMActionForm.port.value);\n  if((temp == '') || !(isPositiveInteger(temp)))\n  {\n\t  alert(\"");
/*  87 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  88 */       out.write("\");\n\t  document.AMActionForm.port.select();\n\t  return;\n  }\n  if(confirm('");
/*  89 */       out.print(FormatUtil.getString("am.webclient.server.changetype.confirm"));
/*  90 */       out.write("'))\n  {\n    document.AMActionForm.submit();\n  }\n   return false;\n}\n</script>\n</head>\n\n<body>\n<!--");
/*     */       
/*  92 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  93 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  94 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/*  96 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  97 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  98 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 100 */           out.write("\n\t<input name=\"method\" type=\"hidden\" value=\"changeServerTypeToManaged\">\n");
/*     */           
/* 102 */           if (request.getAttribute("errorMessage") != null)
/*     */           {
/*     */ 
/* 105 */             out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"messagebox\" >\n\t <tr>\n\t  <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"/images/icon_message_failure.gif\" ></td>\n\t  <td><span class=\"bodytext\"> ");
/* 106 */             out.print(request.getAttribute("errorMessage"));
/* 107 */             out.write(" </span> </td>\n\t </tr>\n\t</table>\n\t<br>\n");
/*     */           }
/* 109 */           out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrbtborder\">\n<tr>\n <td height=\"23\" colspan=\"2\" class=\"tableheadingbborder\">");
/* 110 */           out.print(FormatUtil.getString("am.webclient.managedserver.admin.details"));
/* 111 */           out.write("</td>\n</tr>\n<tr >\n <td width=\"35%\" height=\"19\"  class=\"whitegrayborder\">");
/* 112 */           out.print(FormatUtil.getString("am.webclient.managedserver.admin.host"));
/* 113 */           out.write("</td>\n <td width=\"65%\"  class=\"whitegrayborder\">\n ");
/* 114 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 116 */           out.write(" </td>\n</tr>\n<tr >\n <td width=\"35%\" height=\"19\"  class=\"whitegrayborder\">");
/* 117 */           out.print(FormatUtil.getString("am.webclient.managedserver.admin.port"));
/* 118 */           out.write("</td>\n <td width=\"65%\"  class=\"whitegrayborder\">\n ");
/* 119 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 121 */           out.write(" </td>\n</tr>\n<tr>\n   <td width=\"48%\" class=\"tablebottom\" align=\"right\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 122 */           out.print(FormatUtil.getString("webclient.login.passwordexpiry.submit"));
/* 123 */           out.write("\" onClick=\"javascript:validateAndSubmit();\"></td>\n   <td width=\"52%\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/* 124 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 125 */           out.write("\" onClick=\"javascript:window.close();\"></td>\n </tr>\n</table>\n");
/* 126 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 127 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 131 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 132 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 135 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 136 */         out.write("-->\n<table width=\"100%\" height=\"60\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\" >\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 137 */         out.print(FormatUtil.getString("am.webclient.global.enterprise.text"));
/* 138 */         out.write("</span>\n</td>\n</tr>\n</table>\n\n<table width=\"98%\"  border=0 cellpadding=0 cellspacing=0 class=\"lrtbdarkborder\" align=\"center\" style=\"margin-top:10px;\">\n<tr>\n<td width=\"100%\">\n<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" >\n\n<tr>\n <td class=\"bodytext\">");
/* 139 */         out.print(FormatUtil.getString("am.webclient.managedserver.convert.message"));
/* 140 */         out.write("</td>\n</tr>\n<tr><td colsapn=\"2\"><img src=\"/images/spacer.gif\" height='2' width='1'></td></tr>\n<tr>\n<td width=\"52%\" align=\"center\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 141 */         out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 142 */         out.write("\" onClick=\"javascript:window.close();\"></td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</body>\n\n");
/*     */       }
/* 144 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 145 */         out = _jspx_out;
/* 146 */         if ((out != null) && (out.getBufferSize() != 0))
/* 147 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 148 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 151 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 157 */     PageContext pageContext = _jspx_page_context;
/* 158 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 160 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 161 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 162 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 164 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 166 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 167 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 168 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 169 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 170 */       return true;
/*     */     }
/* 172 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 178 */     PageContext pageContext = _jspx_page_context;
/* 179 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 181 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 182 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 183 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 185 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*     */     
/* 187 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*     */     
/* 189 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*     */     
/* 191 */     _jspx_th_html_005ftext_005f0.setMaxlength("40");
/* 192 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 193 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 194 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 195 */       return true;
/*     */     }
/* 197 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 203 */     PageContext pageContext = _jspx_page_context;
/* 204 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 206 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 207 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 208 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 210 */     _jspx_th_html_005ftext_005f1.setProperty("port");
/*     */     
/* 212 */     _jspx_th_html_005ftext_005f1.setSize("20");
/*     */     
/* 214 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*     */     
/* 216 */     _jspx_th_html_005ftext_005f1.setMaxlength("20");
/* 217 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 218 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 219 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 220 */       return true;
/*     */     }
/* 222 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 223 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AdminServerDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */