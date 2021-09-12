/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.ErrorsTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.HiddenTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*     */ import org.apache.struts.taglib.html.SelectTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class Popup_005fNewThreshold_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  22 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
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
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  57 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  62 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  66 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  67 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  68 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  69 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  70 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  77 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  80 */     JspWriter out = null;
/*  81 */     Object page = this;
/*  82 */     JspWriter _jspx_out = null;
/*  83 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  87 */       response.setContentType("text/html");
/*  88 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  90 */       _jspx_page_context = pageContext;
/*  91 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  92 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  93 */       session = pageContext.getSession();
/*  94 */       out = pageContext.getOut();
/*  95 */       _jspx_out = out;
/*     */       
/*  97 */       out.write("<!--$Id$-->\n\n");
/*     */       
/*  99 */       String resID = request.getParameter("resourceid");
/* 100 */       String attrID = request.getParameter("attributeid");
/* 101 */       String haID = request.getParameter("haid");
/* 102 */       String wiz = request.getParameter("wiz");
/* 103 */       String exist = request.getParameter("exist");
/* 104 */       String thresholdid = request.getParameter("thresholdid");
/*     */       
/* 106 */       out.write("\n<html>\n<head>\n\n\n\n\n\n\n\n\n<title>Create & associate Threshold to attribute</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/* 107 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 109 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<script>\nfunction validateAndSubmit()\n{\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n                {\n                        var name = document.AMActionForm.elements[i].name;\n                        var value = document.AMActionForm.elements[i].value;\n\t\t\tif(name==\"displayname\")\n\t\t\t{\n\t\t\t     if(trimAll(value)==\"\")\n\t\t\t     {\n\t\t\t     \twindow.alert('Please provide the Threshold Name');\n\t\t\t     \treturn false;\n\t\t\t     }\n\t\t\t     if(displayNameHasQuotes(trimAll(value),\"");
/* 110 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("am.webclient.threshold.alertsingleqoute"));
/* 111 */       out.write("\"))\n\t\t\t     {\n\t\t\t      \treturn false;\n\t\t\t     }\n\t\t\t     \n\t\t\t     if(!checkSpecialCharacter(trimAll(value),\"");
/* 112 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("am.webclient.specialchar.alert.thresholdname"));
/* 113 */       out.write("\"))\n        \t\t {\n        \t\t \treturn false;\n         \t\t }\t\t\t     \n\t\t\t     \n                        }\n\t\t\telse if(name==\"criticalthresholdvalue\")\n                        {\n                         \tif((trimAll(value)==\"\") || (isPositiveInteger(value)==false))\n                                {\n        \t\t\t\talert('Please enter a positive integer value for the Critical Alert Threshold value field');                            \n        \t\t\t\treturn false;\n                                }\n                        }\n                        else if(name==\"warningthresholdvalue\")\n                        {\n                         \tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n                                {\n        \t\t\t\talert('Please enter a positive integer value for the Warning Alert Threshold value field');                            \n        \t\t\t\treturn false;\n                                }\n                        }\n                        else if(name==\"infothresholdvalue\")\n                        {\n                         \t\n");
/* 114 */       out.write("                         \tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n                                {\n        \t\t\t\talert('Please enter a positive integer value for the Clear Alert Threshold value field');                            \n        \t\t\t\treturn false;\n                                }\n                        }\n \t\t\telse if(name==\"consecutive_criticalpolls\")\n\t\t\t{\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer (between 1 and 10) for Critical Alert consecutive polls field');\n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t         {\n\t\t\t              \twindow.alert('Polls to retry for Crtical Alert should be a Integer between (1-10)');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t    }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_warningpolls\")\n\t\t\t {\n\t\t\t   if(trimAll(value)!=\"\")\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer value (between 1 and 10) for the Warning Alert consecutive polls field');                            \n");
/* 115 */       out.write("\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t\twindow.alert('Polls to retry for Warning Alert should be a Integer value between (1-10)');\n\t\t\t\t\treturn false;\n\t\t\t         }\n\t\t\t     }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_clearpolls\")\n\t\t\t {\n\t\t\t  \t\n\t\t\t    if(trimAll(value)!=\"\")\n\t\t\t    {\n\t\t\t   \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('Please enter a positive integer value (between 1 and 10) for the Clear Alert consecutive polls field');                            \n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t         else if((value>10)||(value<1))\n\t\t\t\t {\n\t\t\t\t        window.alert('Polls to retry for Clear Alert should be a Integer value between (1-10)');\n\t\t\t\t        return false;\n\t\t\t         }\n\t\t\t     }\n                        }\n\n        \t}\n\t}\n\tdocument.AMActionForm.submit();\n}\nfunction fnFramechange(optvalue)\n{\n\nreturn (window.iframe_thresh.location.href=optvalue);\n\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n</head>\n");
/*     */       
/* 117 */       com.adventnet.appmanager.db.AMConnectionPool pool = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/* 118 */       String redirectTo = request.getParameter("redirectTo");
/* 119 */       String admin = request.getParameter("admin");
/*     */       
/* 121 */       out.write("\n<body>\n");
/* 122 */       if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
/*     */         return;
/* 124 */       out.write(10);
/*     */       
/* 126 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 127 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 128 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 130 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/* 131 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 132 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 134 */           out.write(10);
/* 135 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 137 */           out.write(10);
/* 138 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 140 */           out.write(10);
/* 141 */           if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 143 */           out.write("\n<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 144 */           out.print(resID);
/* 145 */           out.write("\">\n<input name=\"attributeid\" type=\"hidden\" value=\"");
/* 146 */           out.print(attrID);
/* 147 */           out.write("\">\n<input name=\"haid\" type=\"hidden\" value=\"");
/* 148 */           out.print(haID);
/* 149 */           out.write("\">\n<input name=\"redirectTo\" type=\"hidden\" value=\"");
/* 150 */           out.print(redirectTo);
/* 151 */           out.write("\">\n<input name=\"thresholdid\" type=\"hidden\" value=\"");
/* 152 */           out.print(thresholdid);
/* 153 */           out.write("\">\n<input name=\"admin\" type=\"hidden\" value=\"");
/* 154 */           out.print(admin);
/* 155 */           out.write("\">\n<input name=\"exist\" type=\"hidden\" value=\"");
/* 156 */           out.print(exist);
/* 157 */           out.write(34);
/* 158 */           out.write(62);
/* 159 */           out.write(10);
/*     */           
/* 161 */           if (wiz != null)
/*     */           {
/*     */ 
/* 164 */             out.write("\n<input name=\"wiz\" type=\"hidden\" value=\"");
/* 165 */             out.print(wiz);
/* 166 */             out.write(34);
/* 167 */             out.write(62);
/* 168 */             out.write(10);
/*     */           }
/*     */           
/*     */ 
/* 172 */           out.write("\n<input name=\"method\" type=\"hidden\" value=\"createThresholdFromPopup\">\n");
/*     */           
/* 174 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 175 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 176 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 178 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 179 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 180 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 182 */               out.write(" \n   ");
/*     */               
/* 184 */               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 185 */               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 186 */               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 188 */               _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */               
/* 190 */               _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 191 */               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 192 */               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 193 */                 String msg = null;
/* 194 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 195 */                   out = _jspx_page_context.pushBody();
/* 196 */                   _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 197 */                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */                 }
/* 199 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 201 */                   out.write("\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n              <tr> \n                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n                <td width=\"95%\" class=\"message\"> ");
/* 202 */                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */                     return;
/* 204 */                   out.write("</td>\n              </tr>\n            </table>\n            <br>\n    ");
/* 205 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 206 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 207 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 210 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 211 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 214 */               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 215 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */               }
/*     */               
/* 218 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 219 */               out.write(10);
/* 220 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 221 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 225 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 226 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*     */           }
/*     */           
/* 229 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 230 */           out.write(" \n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"6%\"><div id=\"newicon\" style=\"display:block;\"><span class=\"bodytextbold\"><img src=\"/images/");
/* 231 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 233 */           out.write("/txt_createnew_high.gif\" width=\"94\" height=\"18\"></span></div><div id=\"newicon1\" style=\"display:none;\" onclick=\"javascript:hideDiv('newicon1$conficon$conf'),showDiv('newicon$conficon1$new');\"><img src=\"/images/");
/* 234 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 236 */           out.write("/txt_createnew_normal.gif\" width=\"94\" height=\"18\"></div></td>\n    <td width=\"94%\"><div id=\"conficon\" style=\"display:none;\"><span class=\"bodytextbold\"><img src=\"/images/");
/* 237 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 239 */           out.write("/txt_associateexisting_high.gif\" ></span></div><div id=\"conficon1\" style=\"display:block;\" onclick=\"javascript:hideDiv('newicon$conficon1$new'),showDiv('newicon1$conficon$conf');\"><img src=\"/images/");
/* 240 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 242 */           out.write("/txt_associateexisting_normal.gif\" ></div></td>\n  </tr>\n  <tr>\n    <td background=\"/images/");
/* 243 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 245 */           out.write("/topstrip.gif\" colspan=\"2\" height=\"7\"><img src=\"/images/spacer.gif\" width=\"1\" height=\"7\"></td></tr>\n</table>\n<br>\n<div id=\"new\" style=\"DISPLAY:block\">\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrborder\">\n  <tr > \n    <td height=\"23\" colspan=\"3\"  class=\"tableheadingbborder\">New Threshold Profile Details</td>\n  </tr>\n    <tr> \n         <td class=\"whitegrayborderbr\" width=\"30%\" class=\"bodytext\">&nbsp;Name:</td>\n         <td class=\"whitegrayborder\" colspan=\"2\" width=\"70%\">\n             <input name=\"displayname\" type=\"text\"></input>\n         </td>\n    </tr>  \n\t<tr> \n\t\t <td class=\"yellowgrayborderbr\" class=\"bodytext\">&nbsp;Description:</td>\n\t\t <td class=\"yellowgrayborderbr\" colspan=\"2\">\n\t\t\t <input name=\"description\" type=\"text\" size=\"40\"></input>\n\t\t </td>\n\t</tr>  \n    <tr> \n          <td class=\"whitegrayborderbr\" width=\"35%\" valign=\"top\"><img src=\"/images/icon_legend_critical.gif\" width=\"12\" height=\"11\" hspace=\"2\" vspace=\"0\">Critical Alert if value </td>\n          <td valign=\"top\" class=\"whitegrayborderbr\" width=\"30%\"> \n");
/* 246 */           out.write("               ");
/* 247 */           if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 249 */           out.write(" \n          </td>\n         <td class=\"whitegrayborderbr\" valign=\"top\" class=\"bodytext\" width=\"35%\">\n             ");
/* 250 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 252 */           out.write(" for ");
/* 253 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 255 */           out.write(" consecutive poll(s)\n         </td>\n    </tr>\n    <tr>\n       <td valign=\"top\" class=\"yellowgrayborderbr\"><img src=\"/images/icon_legend_warning.gif\" width=\"12\" height=\"11\" hspace=\"2\">Warning Alert if value </td>\n       <td class=\"yellowgrayborderbr\"valign=\"top\" class=\"bodytext\">\n    \t  ");
/* 256 */           if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 258 */           out.write("\n       </td>\n       <td class=\"yellowgrayborderbr\" valign=\"top\" class=\"bodytext\">\n       \t  ");
/* 259 */           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 261 */           out.write("  for ");
/* 262 */           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 264 */           out.write(" consecutive poll(s)\n    \t</td>\n    </tr>\n    <tr>\n        <td valign=\"top\" class=\"whitegrayborderbr\"><img src=\"/images/icon_legend_clear.gif\" width=\"12\" height=\"11\" hspace=\"2\">Clear  Alert if value</td>\n        <td class=\"whitegrayborderbr\" valign=\"top\">\n           ");
/* 265 */           if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 267 */           out.write(" \n        </td>\n        <td class=\"whitegrayborderbr\" valign=\"top\"> \n             ");
/* 268 */           if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 270 */           out.write("  for ");
/* 271 */           if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 273 */           out.write(" consecutive poll(s)\n        </td>\n    </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr> \n    <td align=\"center\" class=\"tablebottom\">\n    <input name=\"submitbutton\" value=\"Create Threshold & Associate\" type=\"button\" class=\"buttons\" onClick=\"javascript:validateAndSubmit()\">\n    <input name=\"Button\" type=\"button\" class=\"buttons\" value=\"Close\" onClick=\"javascript:window.parent.close();\">\n    </td>\n  </tr>\n</table>\n");
/* 274 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 275 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 279 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 280 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 283 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 284 */         out.write("\n</div>\n<div id=\"conf\" style=\"DISPLAY:none\">\n<form name=\"form1\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"reportperiod\">\n    <tr> \n      <td class=\"bodytext\"> <font face =\"Arial\" size = \"1\"> <b>Select Profile : </b></font></td> ");
/* 285 */         out.write("\n    <td width=\"68%\">\n\t  <select name=\"select\" class=\"formtext\" onChange=\"fnFramechange(document.form1.select.options[this.selectedIndex].value);\">\n");
/*     */         
/* 287 */         String defaultID = "";
/*     */         try
/*     */         {
/* 290 */           java.sql.ResultSet result = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt("select ID,NAME from AM_THRESHOLDCONFIG where ID <> 1 && ID <> 2  AND (DESCRIPTION <>'##Threshod for URL##') and TYPE!=3");
/* 291 */           while (result.next())
/*     */           {
/* 293 */             String selected = "";
/* 294 */             if ((exist != null) && (!exist.equals("null")))
/*     */             {
/* 296 */               if (result.getString("ID").equals(thresholdid))
/*     */               {
/* 298 */                 defaultID = request.getParameter("thresholdid");
/* 299 */                 selected = "selected";
/*     */               }
/*     */             }
/* 302 */             else if (defaultID.equals(""))
/*     */             {
/* 304 */               defaultID = String.valueOf(result.getInt("ID"));
/* 305 */               selected = "selected";
/*     */             }
/*     */             
/*     */ 
/* 309 */             out.write(" \n\t <option value=\"/showActionProfiles.do?method=getThresholdDetails&frompopupNewThreshold=true&thresholdid=");
/* 310 */             out.print(result.getInt("ID"));
/* 311 */             out.write("&resourceid=");
/* 312 */             out.print(resID);
/* 313 */             out.write("&attributeid=");
/* 314 */             out.print(attrID);
/* 315 */             out.write("&haid=");
/* 316 */             out.print(haID);
/* 317 */             out.write("&redirectTo=");
/* 318 */             out.print(java.net.URLEncoder.encode(redirectTo + ""));
/* 319 */             out.write(34);
/* 320 */             out.write(32);
/* 321 */             out.print(selected);
/* 322 */             out.write(62);
/* 323 */             out.print(result.getString("NAME"));
/* 324 */             out.write("</option>\n");
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 330 */           e.printStackTrace();
/*     */         }
/*     */         
/* 333 */         out.write("\n\t\t\n      </select></td>\n  </tr>  \n</table>\n  <img src=\"/images/spacer.gif\" width=\"1\" height=\"10\"> \n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr> \n    <td height=\"18\"  class=\"bodytext\">\n\t");
/*     */         
/* 335 */         if (!defaultID.equals(""))
/*     */         {
/*     */ 
/* 338 */           out.write("\n\t\t<iframe name=\"iframe_thresh\" id=\"iframe_thresh\" frameborder=\"0\" src=\"/showActionProfiles.do?method=getThresholdDetails&thresholdid=");
/* 339 */           out.print(defaultID);
/* 340 */           out.write("&frompopupNewThreshold=true&resourceid=");
/* 341 */           out.print(resID);
/* 342 */           out.write("&attributeid=");
/* 343 */           out.print(attrID);
/* 344 */           out.write("&haid=");
/* 345 */           out.print(haID);
/* 346 */           out.write("&redirectTo=");
/* 347 */           out.print(java.net.URLEncoder.encode(redirectTo + ""));
/* 348 */           out.write("\" width=\"100%\" height=\"260\" vspace=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n\t");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 354 */           out.write("\n\t<span class=\"bodytextbold\">No Threshold Profiles created.</span>\n\t");
/*     */         }
/*     */         
/*     */ 
/* 358 */         out.write("\n\t</td>\n  </tr>\n  </table>\n</form>\n</div>\n");
/*     */         
/* 360 */         if ((exist != null) && (!exist.equals("null")))
/*     */         {
/* 362 */           out.write("\n<script>\nhideDiv('newicon$conficon1$new');\nshowDiv('newicon1$conficon$conf');\n</script>\n");
/*     */         }
/* 364 */         out.write("\n</body>\n</html>\n");
/*     */       }
/* 366 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 367 */         out = _jspx_out;
/* 368 */         if ((out != null) && (out.getBufferSize() != 0))
/* 369 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 370 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 373 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 379 */     PageContext pageContext = _jspx_page_context;
/* 380 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 382 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 383 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 384 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 386 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 388 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 389 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 390 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 391 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 392 */       return true;
/*     */     }
/* 394 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 395 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 400 */     PageContext pageContext = _jspx_page_context;
/* 401 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 403 */     ErrorsTag _jspx_th_html_005ferrors_005f0 = (ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(ErrorsTag.class);
/* 404 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 405 */     _jspx_th_html_005ferrors_005f0.setParent(null);
/* 406 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 407 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 408 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 409 */       return true;
/*     */     }
/* 411 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 412 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 417 */     PageContext pageContext = _jspx_page_context;
/* 418 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 420 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 421 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 422 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 424 */     _jspx_th_html_005fhidden_005f0.setProperty("criticalthresholdmessage");
/* 425 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 426 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 427 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 428 */       return true;
/*     */     }
/* 430 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 431 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 436 */     PageContext pageContext = _jspx_page_context;
/* 437 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 439 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 440 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 441 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 443 */     _jspx_th_html_005fhidden_005f1.setProperty("warningthresholdmessage");
/* 444 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 445 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 446 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 447 */       return true;
/*     */     }
/* 449 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 450 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 455 */     PageContext pageContext = _jspx_page_context;
/* 456 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 458 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 459 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 460 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 462 */     _jspx_th_html_005fhidden_005f2.setProperty("infothresholdmessage");
/* 463 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 464 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 465 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 466 */       return true;
/*     */     }
/* 468 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 469 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 474 */     PageContext pageContext = _jspx_page_context;
/* 475 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 477 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 478 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 479 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 481 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/* 482 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 483 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 484 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 485 */       return true;
/*     */     }
/* 487 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 488 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 493 */     PageContext pageContext = _jspx_page_context;
/* 494 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 496 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 497 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 498 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 500 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*     */     
/* 502 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 503 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 504 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 505 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 506 */       return true;
/*     */     }
/* 508 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 509 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 514 */     PageContext pageContext = _jspx_page_context;
/* 515 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 517 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 518 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 519 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 521 */     _jspx_th_c_005fout_005f2.setValue("${selectedskin}");
/*     */     
/* 523 */     _jspx_th_c_005fout_005f2.setDefault("${initParam.defaultSkin}");
/* 524 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 525 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 526 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 527 */       return true;
/*     */     }
/* 529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 530 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 535 */     PageContext pageContext = _jspx_page_context;
/* 536 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 538 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 539 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 540 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 542 */     _jspx_th_c_005fout_005f3.setValue("${selectedskin}");
/*     */     
/* 544 */     _jspx_th_c_005fout_005f3.setDefault("${initParam.defaultSkin}");
/* 545 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 546 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 547 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 548 */       return true;
/*     */     }
/* 550 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 551 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 556 */     PageContext pageContext = _jspx_page_context;
/* 557 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 559 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 560 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 561 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 563 */     _jspx_th_c_005fout_005f4.setValue("${selectedskin}");
/*     */     
/* 565 */     _jspx_th_c_005fout_005f4.setDefault("${initParam.defaultSkin}");
/* 566 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 567 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 568 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 569 */       return true;
/*     */     }
/* 571 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 572 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 577 */     PageContext pageContext = _jspx_page_context;
/* 578 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 580 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 581 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 582 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 584 */     _jspx_th_c_005fout_005f5.setValue("${selectedskin}");
/*     */     
/* 586 */     _jspx_th_c_005fout_005f5.setDefault("${initParam.defaultSkin}");
/* 587 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 588 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 589 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 590 */       return true;
/*     */     }
/* 592 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 593 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 598 */     PageContext pageContext = _jspx_page_context;
/* 599 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 601 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 602 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 603 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 605 */     _jspx_th_html_005fselect_005f0.setProperty("criticalthresholdcondition");
/*     */     
/* 607 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 608 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 609 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 610 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 611 */         out = _jspx_page_context.pushBody();
/* 612 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 613 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 616 */         out.write(" \n                   ");
/* 617 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 618 */           return true;
/* 619 */         out.write("\n               ");
/* 620 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 621 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 624 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 625 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 628 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 629 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 630 */       return true;
/*     */     }
/* 632 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 633 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 638 */     PageContext pageContext = _jspx_page_context;
/* 639 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 641 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 642 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 643 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*     */     
/* 645 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("conditions");
/* 646 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 647 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 648 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 649 */       return true;
/*     */     }
/* 651 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 652 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 657 */     PageContext pageContext = _jspx_page_context;
/* 658 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 660 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 661 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 662 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 664 */     _jspx_th_html_005ftext_005f0.setProperty("criticalthresholdvalue");
/*     */     
/* 666 */     _jspx_th_html_005ftext_005f0.setSize("5");
/*     */     
/* 668 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*     */     
/* 670 */     _jspx_th_html_005ftext_005f0.setMaxlength("100");
/* 671 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 672 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 673 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 674 */       return true;
/*     */     }
/* 676 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 677 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 682 */     PageContext pageContext = _jspx_page_context;
/* 683 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 685 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 686 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 687 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 689 */     _jspx_th_html_005ftext_005f1.setProperty("consecutive_criticalpolls");
/*     */     
/* 691 */     _jspx_th_html_005ftext_005f1.setSize("2");
/*     */     
/* 693 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*     */     
/* 695 */     _jspx_th_html_005ftext_005f1.setMaxlength("2");
/* 696 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 697 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 698 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 699 */       return true;
/*     */     }
/* 701 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 702 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 707 */     PageContext pageContext = _jspx_page_context;
/* 708 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 710 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 711 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 712 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 714 */     _jspx_th_html_005fselect_005f1.setProperty("warningthresholdcondition");
/*     */     
/* 716 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 717 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 718 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 719 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 720 */         out = _jspx_page_context.pushBody();
/* 721 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 722 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 725 */         out.write(" \n              ");
/* 726 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 727 */           return true;
/* 728 */         out.write("\n           ");
/* 729 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 730 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 733 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 734 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 737 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 738 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 739 */       return true;
/*     */     }
/* 741 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 742 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 747 */     PageContext pageContext = _jspx_page_context;
/* 748 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 750 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 751 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 752 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*     */     
/* 754 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("conditions");
/* 755 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 756 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 757 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 758 */       return true;
/*     */     }
/* 760 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 761 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 766 */     PageContext pageContext = _jspx_page_context;
/* 767 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 769 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 770 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 771 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 773 */     _jspx_th_html_005ftext_005f2.setProperty("warningthresholdvalue");
/*     */     
/* 775 */     _jspx_th_html_005ftext_005f2.setSize("5");
/*     */     
/* 777 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*     */     
/* 779 */     _jspx_th_html_005ftext_005f2.setMaxlength("100");
/* 780 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 781 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 782 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 783 */       return true;
/*     */     }
/* 785 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 786 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 791 */     PageContext pageContext = _jspx_page_context;
/* 792 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 794 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 795 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 796 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 798 */     _jspx_th_html_005ftext_005f3.setProperty("consecutive_warningpolls");
/*     */     
/* 800 */     _jspx_th_html_005ftext_005f3.setSize("2");
/*     */     
/* 802 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*     */     
/* 804 */     _jspx_th_html_005ftext_005f3.setMaxlength("2");
/* 805 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 806 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 807 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 808 */       return true;
/*     */     }
/* 810 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 811 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 816 */     PageContext pageContext = _jspx_page_context;
/* 817 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 819 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 820 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 821 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 823 */     _jspx_th_html_005fselect_005f2.setProperty("infothresholdcondition");
/*     */     
/* 825 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/* 826 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 827 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 828 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 829 */         out = _jspx_page_context.pushBody();
/* 830 */         _jspx_th_html_005fselect_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 831 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 834 */         out.write(" \n                ");
/* 835 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 836 */           return true;
/* 837 */         out.write(" \n           ");
/* 838 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 839 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 842 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 843 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 846 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 847 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 848 */       return true;
/*     */     }
/* 850 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 851 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 856 */     PageContext pageContext = _jspx_page_context;
/* 857 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 859 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 860 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 861 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*     */     
/* 863 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("conditions");
/* 864 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 865 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 866 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 867 */       return true;
/*     */     }
/* 869 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 870 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 875 */     PageContext pageContext = _jspx_page_context;
/* 876 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 878 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 879 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 880 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 882 */     _jspx_th_html_005ftext_005f4.setProperty("infothresholdvalue");
/*     */     
/* 884 */     _jspx_th_html_005ftext_005f4.setSize("5");
/*     */     
/* 886 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*     */     
/* 888 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/* 889 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 890 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 891 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 892 */       return true;
/*     */     }
/* 894 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 895 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 900 */     PageContext pageContext = _jspx_page_context;
/* 901 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 903 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 904 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 905 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 907 */     _jspx_th_html_005ftext_005f5.setProperty("consecutive_clearpolls");
/*     */     
/* 909 */     _jspx_th_html_005ftext_005f5.setSize("2");
/*     */     
/* 911 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*     */     
/* 913 */     _jspx_th_html_005ftext_005f5.setMaxlength("2");
/* 914 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 915 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 916 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 917 */       return true;
/*     */     }
/* 919 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 920 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fNewThreshold_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */