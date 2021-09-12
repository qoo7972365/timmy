/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.HiddenTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*     */ import org.apache.struts.taglib.html.SelectTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class Popup_005fNewPattern_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/*  62 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  64 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  65 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  66 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  67 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  68 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  69 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  76 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  79 */     JspWriter out = null;
/*  80 */     Object page = this;
/*  81 */     JspWriter _jspx_out = null;
/*  82 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  86 */       response.setContentType("text/html");
/*  87 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  89 */       _jspx_page_context = pageContext;
/*  90 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  91 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  92 */       session = pageContext.getSession();
/*  93 */       out = pageContext.getOut();
/*  94 */       _jspx_out = out;
/*     */       
/*  96 */       out.write("<!--$Id$-->\n\n");
/*     */       
/*  98 */       String resID = request.getParameter("resourceid");
/*  99 */       String attrID = request.getParameter("attributeid");
/* 100 */       String haID = request.getParameter("haid");
/* 101 */       String wiz = request.getParameter("wiz");
/* 102 */       String exist = request.getParameter("exist");
/* 103 */       String thresholdid = request.getParameter("thresholdid");
/*     */       
/* 105 */       out.write("\n<html>\n<head>\n\n\n\n\n\n\n\n\n<title>Create & associate Threshold to attribute</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/* 106 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 108 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<script>\nfunction validateAndSubmit()\n{\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n                {\n                        var name = document.AMActionForm.elements[i].name;\n                        var value = document.AMActionForm.elements[i].value;\n\t\t\tif(name==\"displayname\")\n\t\t\t{\n\t\t\t     if(trimAll(value)==\"\")\n\t\t\t     {\n\t\t\t     \twindow.alert('Please provide the Threshold Name');\n\t\t\t     \treturn false;\n\t\t\t     }\n\t\t\t     if(displayNameHasQuotes(trimAll(value),\"Please remove the single quote in Name field\"))\n\t\t\t     {\n\t\t\t      \treturn false;\n\t\t\t     }\n\t\t\t     \n                        }\n\t\t\telse if(name==\"criticalthresholdvalue\")\n                        {\n                         \tif((trimAll(value)==\"\"))\n                                {\n        \t\t\t\talert('Please enter a value for the Critical Alert Threshold value field');                            \n");
/* 109 */       out.write("        \t\t\t\treturn false;\n                                }\n                        }\n                        else if(name==\"warningthresholdvalue\")\n                        {\n                         \tif((trimAll(value)==\"\"))\n                                {\n        \t\t\t\talert('Please enter a value for the Warning Alert Threshold value field');                            \n        \t\t\t\treturn false;\n                                }\n                        }\n                        else if(name==\"infothresholdvalue\")\n                        {\n                         \t\n                         \tif((trimAll(value)==\"\"))\n                                {\n        \t\t\t\talert('Please enter a value for the Clear Alert Threshold value field');                            \n        \t\t\t\treturn false;\n                                }\n                        }\n                        else if(name==\"consecutive_criticalpolls\")\n\t\t\t{\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer (between 1 and 10) for Critical Alert consecutive polls field');\n");
/* 110 */       out.write("\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t         {\n\t\t\t              \twindow.alert('Polls to retry for Crtical Alert should be Integer value between (1-10)');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t    }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_warningpolls\")\n\t\t\t {\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer value (between 1 and 10) for the Warning Alert consecutive polls field');                            \n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t\twindow.alert('Polls to retry for Warning Alert should be a Integer value between (1-10)');\n\t\t\t\t\treturn false;\n\t\t\t         }\n\t\t\t     }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_clearpolls\")\n\t\t\t {\n\t\t\t  \t\n\t\t\t    if(trimAll(value)!=\"\")\n\t\t\t    {\n\t\t\t   \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer value (between 1 and 10) for the Clear Alert consecutive polls field');                            \n");
/* 111 */       out.write("\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t        window.alert('Polls to retry for Clear Alert should be a Integer value between (1-10)');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t     }\n                        }\n        \t}\n\t}\n\tdocument.AMActionForm.submit();\n}\nfunction fnFramechange(optvalue)\n{\n  return (window.iframe_thresh.location.href=optvalue);\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n</head>\n");
/*     */       
/* 113 */       com.adventnet.appmanager.db.AMConnectionPool pool = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/* 114 */       String redirectTo = request.getParameter("redirectTo");
/* 115 */       String admin = request.getParameter("admin");
/*     */       
/* 117 */       out.write("\n<body>\n");
/* 118 */       if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
/*     */         return;
/* 120 */       out.write(10);
/*     */       
/* 122 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 123 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 124 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 126 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/* 127 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 128 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 130 */           out.write(10);
/* 131 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 133 */           out.write(10);
/* 134 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 136 */           out.write(10);
/* 137 */           if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 139 */           out.write("\n<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 140 */           out.print(resID);
/* 141 */           out.write("\">\n<input name=\"attributeid\" type=\"hidden\" value=\"");
/* 142 */           out.print(attrID);
/* 143 */           out.write("\">\n<input name=\"haid\" type=\"hidden\" value=\"");
/* 144 */           out.print(haID);
/* 145 */           out.write("\">\n<input name=\"redirectTo\" type=\"hidden\" value=\"");
/* 146 */           out.print(redirectTo);
/* 147 */           out.write("\">\n<input name=\"thresholdid\" type=\"hidden\" value=\"");
/* 148 */           out.print(thresholdid);
/* 149 */           out.write("\">\n<input name=\"admin\" type=\"hidden\" value=\"");
/* 150 */           out.print(admin);
/* 151 */           out.write("\">\n<input name=\"exist\" type=\"hidden\" value=\"");
/* 152 */           out.print(exist);
/* 153 */           out.write(34);
/* 154 */           out.write(62);
/* 155 */           out.write(10);
/*     */           
/* 157 */           if (wiz != null)
/*     */           {
/*     */ 
/* 160 */             out.write("\n<input name=\"wiz\" type=\"hidden\" value=\"");
/* 161 */             out.print(wiz);
/* 162 */             out.write(34);
/* 163 */             out.write(62);
/* 164 */             out.write(10);
/*     */           }
/*     */           
/*     */ 
/* 168 */           out.write("\n<input name=\"method\" type=\"hidden\" value=\"createPatternFromPopup\">\n");
/*     */           
/* 170 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 171 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 172 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 174 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 175 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 176 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 178 */               out.write(" \n   ");
/*     */               
/* 180 */               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 181 */               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 182 */               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 184 */               _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */               
/* 186 */               _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 187 */               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 188 */               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 189 */                 String msg = null;
/* 190 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 191 */                   out = _jspx_page_context.pushBody();
/* 192 */                   _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 193 */                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */                 }
/* 195 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 197 */                   out.write("\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/* 198 */                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */                     return;
/* 200 */                   out.write("</td>\n              </tr>\n            </table>\n            <br>\n    ");
/* 201 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 202 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 203 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 206 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 207 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 210 */               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 211 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */               }
/*     */               
/* 214 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 215 */               out.write(10);
/* 216 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 217 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 221 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 222 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*     */           }
/*     */           
/* 225 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 226 */           out.write(" \n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"6%\"><div id=\"newicon\" style=\"display:block;\"><span class=\"bodytextbold\"><img src=\"/images/");
/* 227 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 229 */           out.write("/txt_createnew_high.gif\" width=\"94\" height=\"18\"></span></div><div id=\"newicon1\" style=\"display:none;\" onclick=\"javascript:hideDiv('newicon1$conficon$conf'),showDiv('newicon$conficon1$new');\"><img src=\"/images/");
/* 230 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 232 */           out.write("/txt_createnew_normal.gif\" width=\"94\" height=\"18\"></div></td>\n    <td width=\"94%\"><div id=\"conficon\" style=\"display:none;\"><span class=\"bodytextbold\"><img src=\"/images/");
/* 233 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 235 */           out.write("/txt_associateexisting_high.gif\" ></span></div><div id=\"conficon1\" style=\"display:block;\" onclick=\"javascript:hideDiv('newicon$conficon1$new'),showDiv('newicon1$conficon$conf');\"><img src=\"/images/");
/* 236 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 238 */           out.write("/txt_associateexisting_normal.gif\" ></div></td>\n  </tr>\n  <tr>\n    <td background=\"/images/");
/* 239 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 241 */           out.write("/topstrip.gif\" colspan=\"2\" height=\"7\"><img src=\"/images/spacer.gif\" width=\"1\" height=\"7\"></td></tr>\n</table>\n<br>\n<div id=\"new\" style=\"DISPLAY:block\">\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrborder\">\n  <tr > \n    <td height=\"23\" colspan=\"3\"  class=\"tableheadingbborder\">New Threshold Profile Details [For String values] </td>\n  </tr>\n    <tr> \n         <td class=\"whitegrayborderbr\" width=\"30%\" class=\"bodytext\">&nbsp;Name:</td>\n         <td class=\"whitegrayborder\" colspan=\"2\" width=\"70%\">\n             <input name=\"displayname\" type=\"text\"></input>\n         </td>\n    </tr>  \n\t<tr> \n\t\t <td class=\"yellowgrayborderbr\" class=\"bodytext\">&nbsp;Description:</td>\n\t\t <td class=\"yellowgrayborderbr\" colspan=\"2\">\n\t\t\t <input name=\"description\" type=\"text\" size=\"40\"></input>\n\t\t </td>\n\t</tr>  \n    <tr> \n          <td class=\"whitegrayborderbr\" width=\"35%\" valign=\"top\"><img src=\"/images/icon_legend_critical.gif\" width=\"12\" height=\"11\" hspace=\"2\" vspace=\"0\">Critical Alert if value</td>\n");
/* 242 */           out.write("          <td valign=\"top\" class=\"whitegrayborderbr\" width=\"30%\"> \n               ");
/* 243 */           if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 245 */           out.write(" \n          </td>\n         <td class=\"whitegrayborderbr\" valign=\"top\" class=\"bodytext\" width=\"35%\">\n             ");
/* 246 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 248 */           out.write("  for ");
/* 249 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 251 */           out.write(" consecutive poll(s)\n         </td>\n    </tr>\n    <tr>\n       <td valign=\"top\" class=\"yellowgrayborderbr\"><img src=\"/images/icon_legend_warning.gif\" width=\"12\" height=\"11\" hspace=\"2\">Warning Alert if value</td>\n       <td class=\"yellowgrayborderbr\"valign=\"top\" class=\"bodytext\">\n    \t  ");
/* 252 */           if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 254 */           out.write("\n       </td>\n       <td class=\"yellowgrayborderbr\" valign=\"top\" class=\"bodytext\">\n       \t  ");
/* 255 */           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 257 */           out.write("  for ");
/* 258 */           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 260 */           out.write(" consecutive poll(s)\n    \t</td>\n    </tr>\n    <tr>\n        <td valign=\"top\" class=\"whitegrayborderbr\"><img src=\"/images/icon_legend_clear.gif\" width=\"12\" height=\"11\" hspace=\"2\">Clear Alert if value</td>\n        <td class=\"whitegrayborderbr\" valign=\"top\">\n           ");
/* 261 */           if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 263 */           out.write(" \n        </td>\n        <td class=\"whitegrayborderbr\" valign=\"top\"> \n             ");
/* 264 */           if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 266 */           out.write("  for ");
/* 267 */           if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 269 */           out.write(" consecutive poll(s)\n        </td>\n    </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr> \n    <td align=\"center\" class=\"tablebottom\">\n    <input name=\"submitbutton\" value=\"Create Threshold & Associate\" type=\"button\" class=\"buttons\" onClick=\"javascript:validateAndSubmit()\">\n    <input name=\"Button\" type=\"button\" class=\"buttons\" value=\"Close\" onClick=\"javascript:window.parent.close();\">\n    </td>\n  </tr>\n</table>\n");
/* 270 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 271 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 275 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 276 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 279 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 280 */         out.write("\n</div>\n<div id=\"conf\" style=\"DISPLAY:none\">\n<form name=\"form1\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"reportperiod\">\n    <tr> \n      <td class=\"bodytext\"> <font face =\"verdana\" size = \"1\"> <b>Select Profile : </b></font></td>\n    <td width=\"68%\">\n\t  <select name=\"select\" class=\"formtext\" onChange=\"fnFramechange(document.form1.select.options[this.selectedIndex].value);\">\n");
/*     */         
/* 282 */         String defaultID = "";
/*     */         try
/*     */         {
/* 285 */           java.sql.ResultSet result = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt("select ID,NAME from AM_THRESHOLDCONFIG where ID <> 1 && ID <> 2  AND (DESCRIPTION <>'##Threshod for URL##') and TYPE=3");
/* 286 */           while (result.next())
/*     */           {
/* 288 */             String selected = "";
/* 289 */             if ((exist != null) && (!exist.equals("null")))
/*     */             {
/* 291 */               if (result.getString("ID").equals(thresholdid))
/*     */               {
/* 293 */                 defaultID = thresholdid;
/* 294 */                 selected = "selected";
/*     */               }
/*     */               
/*     */ 
/*     */             }
/* 299 */             else if (defaultID.equals(""))
/*     */             {
/* 301 */               defaultID = String.valueOf(result.getInt("ID"));
/* 302 */               selected = "selected";
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 307 */             out.write(" \n\t <option value=\"/showActionProfiles.do?method=getPatternDetails&frompopupNewThreshold=true&thresholdid=");
/* 308 */             out.print(result.getInt("ID"));
/* 309 */             out.write("&resourceid=");
/* 310 */             out.print(resID);
/* 311 */             out.write("&attributeid=");
/* 312 */             out.print(attrID);
/* 313 */             out.write("&haid=");
/* 314 */             out.print(haID);
/* 315 */             out.write("&redirectTo=");
/* 316 */             out.print(java.net.URLEncoder.encode(redirectTo + ""));
/* 317 */             out.write(34);
/* 318 */             out.write(32);
/* 319 */             out.print(selected);
/* 320 */             out.write(62);
/* 321 */             out.print(result.getString("NAME"));
/* 322 */             out.write("</option>\n\t");
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 328 */           e.printStackTrace();
/*     */         }
/*     */         
/* 331 */         out.write("\n</select>\n\n</td>\n  </tr>  \n</table>\n<img src=\"/images/spacer.gif\" width=\"1\" height=\"10\"> \n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr> \n    <td height=\"18\"  class=\"bodytext\">\n\t");
/*     */         
/* 333 */         if (!defaultID.equals(""))
/*     */         {
/*     */ 
/* 336 */           out.write("\n\t\t<iframe name=\"iframe_thresh\" id=\"iframe_thresh\" frameborder=\"0\" src=\"/showActionProfiles.do?method=getPatternDetails&thresholdid=");
/* 337 */           out.print(defaultID);
/* 338 */           out.write("&frompopupNewThreshold=true&resourceid=");
/* 339 */           out.print(resID);
/* 340 */           out.write("&attributeid=");
/* 341 */           out.print(attrID);
/* 342 */           out.write("&haid=");
/* 343 */           out.print(haID);
/* 344 */           out.write("&redirectTo=");
/* 345 */           out.print(java.net.URLEncoder.encode(redirectTo + ""));
/* 346 */           out.write("\" width=\"100%\" height=\"260\" vspace=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n\t");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 352 */           out.write("\n\t\t<span class=\"bodytextbold\">No Threshold Profiles created.</span>\n\t");
/*     */         }
/*     */         
/*     */ 
/* 356 */         out.write("\n\t</td>\n  </tr>\n  </table>\n</form>\n</div>\n");
/*     */         
/* 358 */         if ((exist != null) && (!exist.equals("null")))
/*     */         {
/* 360 */           out.write("\n<script>\nhideDiv('newicon$conficon1$new');\nshowDiv('newicon1$conficon$conf');\n</script>\n");
/*     */         }
/* 362 */         out.write("\n</body>\n</html>\n");
/*     */       }
/* 364 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 365 */         out = _jspx_out;
/* 366 */         if ((out != null) && (out.getBufferSize() != 0))
/* 367 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 368 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 371 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 377 */     PageContext pageContext = _jspx_page_context;
/* 378 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 380 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 381 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 382 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 384 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 386 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 387 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 388 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 389 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 390 */       return true;
/*     */     }
/* 392 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 393 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 398 */     PageContext pageContext = _jspx_page_context;
/* 399 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 401 */     org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
/* 402 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 403 */     _jspx_th_html_005ferrors_005f0.setParent(null);
/* 404 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 405 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 406 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 407 */       return true;
/*     */     }
/* 409 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 410 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 415 */     PageContext pageContext = _jspx_page_context;
/* 416 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 418 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 419 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 420 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 422 */     _jspx_th_html_005fhidden_005f0.setProperty("criticalthresholdmessage");
/* 423 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 424 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 425 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 426 */       return true;
/*     */     }
/* 428 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 429 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 434 */     PageContext pageContext = _jspx_page_context;
/* 435 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 437 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 438 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 439 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 441 */     _jspx_th_html_005fhidden_005f1.setProperty("warningthresholdmessage");
/* 442 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 443 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 444 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 445 */       return true;
/*     */     }
/* 447 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 448 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 453 */     PageContext pageContext = _jspx_page_context;
/* 454 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 456 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 457 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 458 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 460 */     _jspx_th_html_005fhidden_005f2.setProperty("infothresholdmessage");
/* 461 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 462 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 463 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 464 */       return true;
/*     */     }
/* 466 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 467 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 472 */     PageContext pageContext = _jspx_page_context;
/* 473 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 475 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 476 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 477 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 479 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/* 480 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 481 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 482 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 483 */       return true;
/*     */     }
/* 485 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 486 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 491 */     PageContext pageContext = _jspx_page_context;
/* 492 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 494 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 495 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 496 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 498 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*     */     
/* 500 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 501 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 502 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 503 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 504 */       return true;
/*     */     }
/* 506 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 507 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 512 */     PageContext pageContext = _jspx_page_context;
/* 513 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 515 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 516 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 517 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 519 */     _jspx_th_c_005fout_005f2.setValue("${selectedskin}");
/*     */     
/* 521 */     _jspx_th_c_005fout_005f2.setDefault("${initParam.defaultSkin}");
/* 522 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 523 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 524 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 525 */       return true;
/*     */     }
/* 527 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 528 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 533 */     PageContext pageContext = _jspx_page_context;
/* 534 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 536 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 537 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 538 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 540 */     _jspx_th_c_005fout_005f3.setValue("${selectedskin}");
/*     */     
/* 542 */     _jspx_th_c_005fout_005f3.setDefault("${initParam.defaultSkin}");
/* 543 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 544 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 546 */       return true;
/*     */     }
/* 548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 549 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 554 */     PageContext pageContext = _jspx_page_context;
/* 555 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 557 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 558 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 559 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 561 */     _jspx_th_c_005fout_005f4.setValue("${selectedskin}");
/*     */     
/* 563 */     _jspx_th_c_005fout_005f4.setDefault("${initParam.defaultSkin}");
/* 564 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 565 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 566 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 567 */       return true;
/*     */     }
/* 569 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 570 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 575 */     PageContext pageContext = _jspx_page_context;
/* 576 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 578 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 579 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 580 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 582 */     _jspx_th_c_005fout_005f5.setValue("${selectedskin}");
/*     */     
/* 584 */     _jspx_th_c_005fout_005f5.setDefault("${initParam.defaultSkin}");
/* 585 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 586 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 587 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 588 */       return true;
/*     */     }
/* 590 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 591 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 596 */     PageContext pageContext = _jspx_page_context;
/* 597 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 599 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 600 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 601 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 603 */     _jspx_th_html_005fselect_005f0.setProperty("criticalthresholdcondition");
/*     */     
/* 605 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 606 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 607 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 608 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 609 */         out = _jspx_page_context.pushBody();
/* 610 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 611 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 614 */         out.write(" \n                   ");
/* 615 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 616 */           return true;
/* 617 */         out.write("\n               ");
/* 618 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 619 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 622 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 623 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 626 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 627 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 628 */       return true;
/*     */     }
/* 630 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 631 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 636 */     PageContext pageContext = _jspx_page_context;
/* 637 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 639 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 640 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 641 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*     */     
/* 643 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("patternMatcherConditions");
/* 644 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 645 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 646 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 647 */       return true;
/*     */     }
/* 649 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 650 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 655 */     PageContext pageContext = _jspx_page_context;
/* 656 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 658 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 659 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 660 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 662 */     _jspx_th_html_005ftext_005f0.setProperty("criticalthresholdvalue");
/*     */     
/* 664 */     _jspx_th_html_005ftext_005f0.setSize("20");
/*     */     
/* 666 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*     */     
/* 668 */     _jspx_th_html_005ftext_005f0.setMaxlength("100");
/* 669 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 670 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 671 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 672 */       return true;
/*     */     }
/* 674 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 675 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 680 */     PageContext pageContext = _jspx_page_context;
/* 681 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 683 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 684 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 685 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 687 */     _jspx_th_html_005ftext_005f1.setProperty("consecutive_criticalpolls");
/*     */     
/* 689 */     _jspx_th_html_005ftext_005f1.setSize("2");
/*     */     
/* 691 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*     */     
/* 693 */     _jspx_th_html_005ftext_005f1.setMaxlength("2");
/* 694 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 695 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 696 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 697 */       return true;
/*     */     }
/* 699 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 700 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 705 */     PageContext pageContext = _jspx_page_context;
/* 706 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 708 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 709 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 710 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 712 */     _jspx_th_html_005fselect_005f1.setProperty("warningthresholdcondition");
/*     */     
/* 714 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 715 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 716 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 717 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 718 */         out = _jspx_page_context.pushBody();
/* 719 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 720 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 723 */         out.write(" \n              ");
/* 724 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 725 */           return true;
/* 726 */         out.write("\n           ");
/* 727 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 728 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 731 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 732 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 735 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 736 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 737 */       return true;
/*     */     }
/* 739 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 740 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 745 */     PageContext pageContext = _jspx_page_context;
/* 746 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 748 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 749 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 750 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*     */     
/* 752 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("patternMatcherConditions");
/* 753 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 754 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 755 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 756 */       return true;
/*     */     }
/* 758 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 759 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 764 */     PageContext pageContext = _jspx_page_context;
/* 765 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 767 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 768 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 769 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 771 */     _jspx_th_html_005ftext_005f2.setProperty("warningthresholdvalue");
/*     */     
/* 773 */     _jspx_th_html_005ftext_005f2.setSize("20");
/*     */     
/* 775 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*     */     
/* 777 */     _jspx_th_html_005ftext_005f2.setMaxlength("100");
/* 778 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 779 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 780 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 781 */       return true;
/*     */     }
/* 783 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 784 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 789 */     PageContext pageContext = _jspx_page_context;
/* 790 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 792 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 793 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 794 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 796 */     _jspx_th_html_005ftext_005f3.setProperty("consecutive_warningpolls");
/*     */     
/* 798 */     _jspx_th_html_005ftext_005f3.setSize("2");
/*     */     
/* 800 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*     */     
/* 802 */     _jspx_th_html_005ftext_005f3.setMaxlength("2");
/* 803 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 804 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 805 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 806 */       return true;
/*     */     }
/* 808 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 809 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 814 */     PageContext pageContext = _jspx_page_context;
/* 815 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 817 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 818 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 819 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 821 */     _jspx_th_html_005fselect_005f2.setProperty("infothresholdcondition");
/*     */     
/* 823 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/* 824 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 825 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 826 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 827 */         out = _jspx_page_context.pushBody();
/* 828 */         _jspx_th_html_005fselect_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 829 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 832 */         out.write(" \n                ");
/* 833 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 834 */           return true;
/* 835 */         out.write(" \n           ");
/* 836 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 837 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 840 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 841 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 844 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 845 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 846 */       return true;
/*     */     }
/* 848 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 849 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 854 */     PageContext pageContext = _jspx_page_context;
/* 855 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 857 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 858 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 859 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*     */     
/* 861 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("patternMatcherConditions");
/* 862 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 863 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 864 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 865 */       return true;
/*     */     }
/* 867 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 868 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 873 */     PageContext pageContext = _jspx_page_context;
/* 874 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 876 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 877 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 878 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 880 */     _jspx_th_html_005ftext_005f4.setProperty("infothresholdvalue");
/*     */     
/* 882 */     _jspx_th_html_005ftext_005f4.setSize("20");
/*     */     
/* 884 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*     */     
/* 886 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/* 887 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 888 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 889 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 890 */       return true;
/*     */     }
/* 892 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 893 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 898 */     PageContext pageContext = _jspx_page_context;
/* 899 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 901 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 902 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 903 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 905 */     _jspx_th_html_005ftext_005f5.setProperty("consecutive_clearpolls");
/*     */     
/* 907 */     _jspx_th_html_005ftext_005f5.setSize("2");
/*     */     
/* 909 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*     */     
/* 911 */     _jspx_th_html_005ftext_005f5.setMaxlength("2");
/* 912 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 913 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 914 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 915 */       return true;
/*     */     }
/* 917 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 918 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fNewPattern_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */