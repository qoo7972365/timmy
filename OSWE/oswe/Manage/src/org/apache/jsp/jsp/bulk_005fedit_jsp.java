/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class bulk_005fedit_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  40 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  56 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  57 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  58 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  65 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  68 */     JspWriter out = null;
/*  69 */     Object page = this;
/*  70 */     JspWriter _jspx_out = null;
/*  71 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  75 */       response.setContentType("text/html;charset=UTF-8");
/*  76 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  78 */       _jspx_page_context = pageContext;
/*  79 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  80 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  81 */       session = pageContext.getSession();
/*  82 */       out = pageContext.getOut();
/*  83 */       _jspx_out = out;
/*     */       
/*  85 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*  86 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  87 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  89 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  90 */       out.write(10);
/*  91 */       out.write("\n<title>");
/*  92 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("</title>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script>\n\nfunction editAttribs(){\n\n\t\t var change = confirm('");
/*  95 */       out.print(FormatUtil.getString("am.webclient.cam.warningmessage.text"));
/*  96 */       out.write("')\n\n\t\t\n\t\tif(change){\n\n                \tdocument.CAMDeleteAttribsForm.submit();\n\t\t}\n\t\t\n}\n\n\n\n</script>\n ");
/*     */       
/*  98 */       MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  99 */       _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 100 */       _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */       
/* 102 */       _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */       
/* 104 */       _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 105 */       int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 106 */       if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 107 */         String msg = null;
/* 108 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 109 */           out = _jspx_page_context.pushBody();
/* 110 */           _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 111 */           _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */         }
/* 113 */         msg = (String)_jspx_page_context.findAttribute("msg");
/*     */         for (;;) {
/* 115 */           out.write("\n                <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n                                  <tr>\n                                       <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" vspace =\"2\" hspace =\"2\"></td>\n                                        <td width=\"95%\" class=\"message\"> ");
/* 116 */           if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */             return;
/* 118 */           out.write("</td>\n                                  </tr>\n                                </table>\n                                <br>\n                ");
/* 119 */           int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 120 */           msg = (String)_jspx_page_context.findAttribute("msg");
/* 121 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 124 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 125 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 128 */       if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 129 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */       }
/*     */       else {
/* 132 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 133 */         out.write(10);
/* 134 */         out.write(10);
/* 135 */         out.write(10);
/*     */         
/* 137 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 138 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 139 */         _jspx_th_html_005fform_005f0.setParent(null);
/*     */         
/* 141 */         _jspx_th_html_005fform_005f0.setMethod("POST");
/*     */         
/* 143 */         _jspx_th_html_005fform_005f0.setAction("/CAMDeleteAttributes.do");
/* 144 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 145 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */           for (;;) {
/* 147 */             out.write("\n<input type=hidden name=\"method\" value=\"bulkEdit\"/>\n<input type=hidden name=\"selectedids\" value=\"");
/* 148 */             out.print(request.getParameter("values"));
/* 149 */             out.write("\"/>\n<input type=hidden name=\"screenid\" value=\"");
/* 150 */             out.print(request.getParameter("screenid"));
/* 151 */             out.write("\"/>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n    <td colspan=\"2\" width=\"100%\" height=\"31\" class=\"tableheading\" >&nbsp;");
/* 152 */             out.print(FormatUtil.getString("am.webclient.cam.editattribute.text"));
/* 153 */             out.write("</td>\n    </tr>\n    <tr>\n          <td width=\"20%\" class=\"bodytext\">&nbsp;</td>\n          <td class=\"bodytext\"> &nbsp;</td>\n    </tr>\n    <tr>\n              <td width=\"20%\" class=\"bodytext\">&nbsp;</td>\n              <td  class=\"bodytext\"> &nbsp;</td>\n    </tr>\n        <tr>\n              <td width =\"30%\" class = \"bodytext\" align=\"center\"> &nbsp;&nbsp;");
/* 154 */             out.print(FormatUtil.getString("am.webclient.cam.editattribute.type.text"));
/* 155 */             out.write("</td>\n                <td class=\"bodytext\" align=\"left\">\n        <select name =\"type\" class=\"formtext\">\n       <option value=\"1\" name=\"counter\">");
/* 156 */             out.print(FormatUtil.getString("am.webclient.cam.editattribute.counter.text"));
/* 157 */             out.write("</option>\n     <option value=\"0\" name=\"Noncounter\" selected>");
/* 158 */             out.print(FormatUtil.getString("am.webclient.cam.editattribute.noncounter.text"));
/* 159 */             out.write("</option>\n\n        </select>\n\n        </tr>\n        <tr>\n              <td width=\"20%\" class=\"bodytext\">&nbsp;</td>\n              <td  class=\"bodytext\"> &nbsp;</td>\n    </tr>\n\n        <tr>\n        <td colspan=\"2\" height=\"29\" align=\"center\" class=\"tablebottom\">\n<input type=\"button\" value='");
/* 160 */             out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 161 */             out.write("' class=\"buttons btn_highlt\" onClick=\"javascript: editAttribs();\"/>&nbsp;&nbsp;\n\n           <input type=\"button\" value='");
/* 162 */             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 163 */             out.write("' class='buttons btn_link' onClick = \"window.close();\"/>\n        </td>\n         </tr>\n\n</table>\n");
/* 164 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 165 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 169 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 170 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */         }
/*     */         else {
/* 173 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 174 */           out.write(10);
/*     */         }
/* 176 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 177 */         out = _jspx_out;
/* 178 */         if ((out != null) && (out.getBufferSize() != 0))
/* 179 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 180 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 183 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 189 */     PageContext pageContext = _jspx_page_context;
/* 190 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 192 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 193 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 194 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 196 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 198 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 199 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 200 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 201 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 202 */       return true;
/*     */     }
/* 204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 210 */     PageContext pageContext = _jspx_page_context;
/* 211 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 213 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 214 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 215 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 217 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.cam.editattribute.text");
/* 218 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 219 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 220 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 221 */       return true;
/*     */     }
/* 223 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 224 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 229 */     PageContext pageContext = _jspx_page_context;
/* 230 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 232 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 233 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 234 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 236 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/* 237 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 238 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 239 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 240 */       return true;
/*     */     }
/* 242 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 243 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\bulk_005fedit_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */