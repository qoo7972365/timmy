/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.PasswordTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class WMIHostDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  49 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  50 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  58 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  61 */     JspWriter out = null;
/*  62 */     Object page = this;
/*  63 */     JspWriter _jspx_out = null;
/*  64 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  68 */       response.setContentType("text/html;charset=UTF-8");
/*  69 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  71 */       _jspx_page_context = pageContext;
/*  72 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  73 */       ServletConfig config = pageContext.getServletConfig();
/*  74 */       session = pageContext.getSession();
/*  75 */       out = pageContext.getOut();
/*  76 */       _jspx_out = out;
/*     */       
/*  78 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  79 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  81 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n<script>\nfunction validateAndSubmit()\n{\n                        \n               \n               if(document.AMActionForm.host.value=='')\n               {\n                 alert(\"Hostname cannot be empty.\");\n                 document.AMActionForm.host.focus();\n                 return;\n               }\n               if(document.AMActionForm.username.value=='')\n               {\n                 alert(\"Username cannot be empty.\");\n                 document.AMActionForm.username.focus();\n                 return;\n               }\n               if(document.AMActionForm.password.value=='')\n               {\n                 alert(\"Password cannot be empty.\");\n                 document.AMActionForm.password.focus();\n                 return;\n               }\n               document.AMActionForm.submit();\n               window.opener.location.href=\"/WMIPerfCounters.do?&method=showDetails&resourceid=");
/*  82 */       out.print((String)request.getAttribute("resourceid"));
/*  83 */       out.write("\";\n               window.close();\n           \n }\n </script>\n<body>\n\n\n<html>\n<head>\n<title>\n");
/*  84 */       out.print(FormatUtil.getString("am.webclient.wmi.edithost"));
/*  85 */       out.write("\n\n</title>\n</head>\n");
/*     */       
/*  87 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/*  88 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  89 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/*  91 */       _jspx_th_html_005fform_005f0.setAction("/WMIPerfCounters.do?method=EditHost&action=update");
/*     */       
/*  93 */       _jspx_th_html_005fform_005f0.setMethod("post");
/*     */       
/*  95 */       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*  96 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  97 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/*  99 */           out.write("\n\n\n\n<input type=hidden name=\"hostid\" value=\"");
/* 100 */           out.print((String)request.getAttribute("hostid"));
/* 101 */           out.write("\"/>\n<input type=hidden name=\"resourceid\" value=\"");
/* 102 */           out.print((String)request.getAttribute("resourceid"));
/* 103 */           out.write("\"/>\n\n<table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"99%\">\n<tr>\n<td class=\"tableheadingbborder\" colspan=\"2\" height=\"19\">");
/* 104 */           out.print(FormatUtil.getString("am.webclient.wmi.edithost"));
/* 105 */           out.write("</td>\n</tr>\n            <tr> \n              <td height=28 width=\"25%\"  class=\"bodytext\">");
/* 106 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 107 */           out.write("<span class=\"mandatory\">*</span></td>\n              <td height=28 width=\"75%\"> ");
/* 108 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 110 */           out.write("</td>\n            </tr>\n            <tr> \n              <td height=\"28\" width=\"25%\" class=\"bodytext\">");
/* 111 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 112 */           out.write("<span class=\"mandatory\">*</span></td>\n              <td height=\"28\" width=\"75%\">");
/* 113 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 115 */           out.write("&nbsp;&nbsp; \n              </td>\n            </tr >\n            <tr> \n              <td width=\"25%\" height=\"28\" class=\"bodytext\">");
/* 116 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 117 */           out.write("<span class=\"mandatory\">*</span></td>\n              <td width=\"75%\" height=\"28\"> ");
/* 118 */           if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 120 */           out.write(" \n               </td>\n            </tr>\n            </table>\n            <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t      <tr>\n\t        <td width=\"20%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n\t        <td width=\"80%\" height=\"31\" align=\"left\"  class=\"tablebottom\">\n\t          <input name=\"addwmiperf\" type=\"button\" class=\"buttons\" value='");
/* 121 */           out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 122 */           out.write("' onClick=\"return validateAndSubmit()\"/> \n\t          &nbsp;&nbsp;<input type=\"button\" value=\"");
/* 123 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 124 */           out.write("\" class='buttons' onClick=\"window.close();\" />\n\t        </td>\n\t      </tr>\n</table>\n            \n ");
/* 125 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 126 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 130 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 131 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 134 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 135 */         out.write(" \n </body>\n </html>");
/*     */       }
/* 137 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 138 */         out = _jspx_out;
/* 139 */         if ((out != null) && (out.getBufferSize() != 0))
/* 140 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 141 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 144 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 150 */     PageContext pageContext = _jspx_page_context;
/* 151 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 153 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 154 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 155 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 157 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 159 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 160 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 161 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 162 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 163 */       return true;
/*     */     }
/* 165 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 171 */     PageContext pageContext = _jspx_page_context;
/* 172 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 174 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 175 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 176 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 178 */     _jspx_th_html_005ftext_005f0.setProperty("host");
/*     */     
/* 180 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*     */     
/* 182 */     _jspx_th_html_005ftext_005f0.setSize("15");
/* 183 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 184 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 185 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 186 */       return true;
/*     */     }
/* 188 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 194 */     PageContext pageContext = _jspx_page_context;
/* 195 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 197 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 198 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 199 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 201 */     _jspx_th_html_005ftext_005f1.setProperty("username");
/*     */     
/* 203 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*     */     
/* 205 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 206 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 207 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 208 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 209 */       return true;
/*     */     }
/* 211 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 212 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 217 */     PageContext pageContext = _jspx_page_context;
/* 218 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 220 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 221 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 222 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 224 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*     */     
/* 226 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*     */     
/* 228 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 229 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 230 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 231 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 232 */       return true;
/*     */     }
/* 234 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 235 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WMIHostDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */